package eu.anastasis.it.tullinidms.modules;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import eu.anastasis.it.tullinidms.ConstantsTulliniDMS;
import eu.anastasis.serena.application.core.modules.DefaultMethod;
import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.application.index.util.ApplicationConfiguration;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.index.util.ModuleParameterConfiguration;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.constants.ConstantsEntityBean;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.dataManager.ObjectAlreadyExistsException;
import eu.anastasis.serena.exception.NoConfParamException;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.serena.persistence.utils.IdReservationCache;
import eu.anastasis.serena.presentation.templates.ActiveTemplate;
import eu.anastasis.serena.presentation.templates.TemplateFactory;

/**
 * Metodo che gestisce il post processing di un documento caricato, consistente
 * nell'invio a chi di dovere di mail/sms
 * 
 * @author afrascari
 * 
 */
public class UploadAndSendMethod extends DefaultMethod {

	private static final String METHOD_NAME = "uploadandsend";

	private static final String DummyClass = DescrizioneDocumento.MY_CLASS;
	private static final String OperatoreClass = "Operatore";
	
	private static final String INPUT_PROFILI= "profili_clienti";
	
	private static final Logger logger = Logger
			.getLogger(UploadAndSendMethod.class);

	public UploadAndSendMethod(DefaultModule parentModule,
			String[] defaultParameters) {
		super(parentModule, defaultParameters);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String doMethod(HttpServletRequest request,
			HttpServletResponse response) throws SerenaException {

		HashMap<String, String> dataHolder = new HashMap<String, String>();

		ActiveTemplate template = new ActiveTemplate(
				TemplateFactory.getTemplate(retrieveDefaultTemplateContext(),
						retrieveTemplateContext(), DummyClass));

		String[] cats = null;

		StringBuffer output = new StringBuffer();
		try {

			// tutti i parametri di "input"
			for (Enumeration<String> params = request.getParameterNames(); params
					.hasMoreElements();) {
				String aParam = params.nextElement().toString();
				if (ObjectUtils.isInsertionAttribute(aParam)) {
					// insertion-parameter: adding node for class or attribute
					String clearParam = ObjectUtils.clearFromPrefix(aParam);
					if (INPUT_PROFILI.equals(clearParam)) {
						// esplicitamente, il parametro "multiplo" categorie
						cats = request.getParameterValues(aParam);
					} else {
						dataHolder
								.put(clearParam, request.getParameter(aParam));
					}

				}
			}

			// l'algoritmo comincia qui

			// 1: fetch id clienti destinatari

			Cliente[] destinatari = null;

			String azione_conseguente_caricamento = dataHolder
					.get("azione_conseguente_caricamento");
			// 2: copiamo i campi (hidden) relativi al documento

			String doc_tipologia = dataHolder.get("tipologia");
			String doc_ID = dataHolder.get("Documento_ID");
			String doc_titolo = dataHolder.get("Documento_titolo");
			String doc_abstract = dataHolder.get("Documento_abstract");
			String doc_attach = dataHolder.get("Documento_allegato");

			if (ConstantsTulliniDMS.actionTriggersWorkflow(azione_conseguente_caricamento)) {
				// mail di notifica al o ai clienti
				if (cats != null && cats.length >= 1 && cats[0] != "") {
					// specificate una o più categorie: da queste devo risalire
					// ad ID (e nome) dei singoli clienti
					destinatari = getDestinatari(cats, request);
				} else if (!"".equals(dataHolder.get("cliente_doc"))) {
					destinatari = getDestinatarioCliente(
							dataHolder.get("cliente_doc"), request);
				} else {
					output.append("Nessun cliente o tipologia clienti specificata!");
					template.replaceTag("MSG", output.toString());
					template.publish();
					return template.getContenuto();
				}
			} else if (azione_conseguente_caricamento
					.contains(ConstantsTulliniDMS.AZIONE_COMM_INTERNA_SEGRETERIA)) {
				destinatari = getEmailOperatoriStudio(true, request);
			} else if (azione_conseguente_caricamento
					.contains(ConstantsTulliniDMS.AZIONE_COMM_INTERNA_STUDIO)) {
				destinatari = getEmailOperatoriStudio(false, request);
			} else if (azione_conseguente_caricamento
					.contains(ConstantsTulliniDMS.AZIONE_NOTIFICA_MAILING_LIST)) {
				// mail di notifica agli indirizzi specificati nel campo
				// apposito
				destinatari = getDestinatariEspliciti(dataHolder
						.get("contenuto_azione"));
			} else {
				template.replaceTag("MSG", "Nessuna azione da compiere!");
				template.publish();
				return template.getContenuto();
			}

			if (azione_conseguente_caricamento
					.contains(ConstantsTulliniDMS.AZIONE_CARICAMENTO_SILENZIOSO_CLIENTE)) {
				return caricamentoSilnezioso(request, doc_ID, template,
						destinatari);
			}
			// controllo presenza dati mail/sms
			if (ConstantsTulliniDMS.actionTriggersWorkflow(azione_conseguente_caricamento) && dataHolder.get("template_sms").isEmpty()) {
				template.replaceTag("MSG", "Impostare i dati per SMS!");
				template.publish();
				return template.getContenuto();
			}
			if (dataHolder.get("template_oggetto_invio").isEmpty()) {
				template.replaceTag("MSG", "Impostare i dati per oggetto mail!");
				template.publish();
				return template.getContenuto();
			}
			if (dataHolder.get("template_testo_invio").isEmpty()) {
				template.replaceTag("MSG", "Impostare i dati per testo mail!");
				template.publish();
				return template.getContenuto();
			}

			// per ogni cliente:
			if (destinatari.length==0)	{
				template.replaceTag("MSG", "I criteri espressi non identificano nessun cliente");
				template.publish();
				return template.getContenuto();
			}
			for (Cliente questoCliente : destinatari) {

				// 1: allochiamo un istanza di StoriaDocumento
				StoriaDocumento unaStoria = new StoriaDocumento();

				// 2: copiamo i dati del cliente
				unaStoria.setCliente_doc(questoCliente.getID());

				// e anticipiamo l'ID del workflow che andremo ad inserire
				String willbeWorkflow = new IdReservationCache()
						.guessNextId(StoriaDocumento.MY_CLASS);

				// String doc_allegato = dataHolder.get("Documento_allegato");
				// String doc_allegato1 =
				// dataHolder.get("Documento_allegato_1");

				unaStoria.setDocumento(doc_ID);

				String ilTesto = null;

				String sendResult = null;

				// 4: notifiche e solleciti via sms o mail?
				if (questoCliente.notificheViaSms()) {

					// 4.1.1: notifiche via sms: copiamo semplicemente il testo
					// dell'sms e aggiungiamo titolo e tipo documento
					ilTesto = dataHolder.get("template_sms");
					ilTesto = ilTesto.replace("@TIPO@", doc_tipologia);
					ilTesto = ilTesto.replace("@TITOLO@", doc_titolo);
					ilTesto = ilTesto.replace("@NOME@",
							questoCliente.getNickname());
					unaStoria.setTesto_sms(ilTesto);

					// 4.2.2: invio sms
					sendResult = new MailAndSmsSender().sendSms(unaStoria,
							questoCliente);
					if (sendResult == null)
						output.append("<p>Inviato sms notifica/sollecito a "
								+ questoCliente.getNome());
					else
						output.append("<p>" + sendResult);

				} else {

					// 4.2.1: notifiche via mail

					// 4.2.2: copiamo l'oggetto della mail, eventualmente
					// personalizzandolo
					ilTesto = dataHolder.get("template_oggetto_invio");
					ilTesto = ilTesto.replace("@TIPO@", doc_tipologia);
					ilTesto = ilTesto.replace("@NOME@",
							questoCliente.getNickname());
					ilTesto = ilTesto.replace("@TITOLO@", doc_titolo);
					unaStoria.setOggetto_mail(ilTesto);

					// 4.2.3: copiamo il testo, eventualmente personalizzandolo
					ilTesto = dataHolder.get("template_testo_invio");
					ilTesto = ilTesto.replace("@NOME@",
							questoCliente.getNickname());
					ilTesto = ilTesto.replace("@TIPO@", doc_tipologia);
					ilTesto = ilTesto.replace("@TITOLO@", doc_titolo);
					ilTesto = ilTesto.replace("@ABSTRACT@", doc_abstract);
					// costruiamo il link del doc da scadicare

					// String baseUrl = request.getRequestURL().toString();
					// baseUrl =
					// baseUrl.replace("Index",ConstantsTulliniDMS.DOWNLOAD_SERVLET);
					String baseUrl = ModuleParameterConfiguration
							.getInstance().getValue("document", "DMS_URL");
					baseUrl = baseUrl
							+ ConstantsTulliniDMS.DOWNLOAD_SERVLET;

					String theLink = null;
					if (azione_conseguente_caricamento
							.contains(ConstantsTulliniDMS.AZIONE_MAIL_NOTIFICA_LINK_LOGON))
						theLink = ConstantsTulliniDMS.BASE_DOWNLOAD_USER_LINK_FORCELOGON;
					else
						theLink = ConstantsTulliniDMS.BASE_DOWNLOAD_USER_LINK_SKIPLOGON;

					theLink = theLink.replace("@BASE_DOWNLOAD_LINK@",
							baseUrl);
					theLink = theLink.replace("@DOCID@", doc_ID);
					theLink = theLink.replace("@ATTACHID@", doc_attach);
					theLink = theLink.replace("@WORKFLOWID@",
							willbeWorkflow);
					theLink = theLink.replace("@USERID@",
							questoCliente.getID());

					theLink = "<a href=\""
							+ theLink
							+ "\" title=\"clicca per scaricare il documento\">DMS Contabilis</a>";

					ilTesto = ilTesto.replace("@LINK@", theLink);

					unaStoria.setTesto_mail(ilTesto);

					// 4.2.4: invio mail (o fax)
					try {
						sendResult = (questoCliente.notificheViaFax()) ? new MailAndSmsSender()
								.sendFax(unaStoria, questoCliente, doc_attach)
								: new MailAndSmsSender().sendMail(unaStoria,
										questoCliente);
					} catch (Exception e) {
						logger.error("Cannot send mail: " + e.getMessage());
						sendResult = "Si sono verificati problemi di invio mail: controllare la configurazione del server con l'assistenza.";
					}
					if (sendResult == null)
						output.append("<p>Inviata mail notifica/sollecito a <a href=\"?q=object/detail&amp;p=Cliente/_a_ID/_v_"+questoCliente.getID()+"\" title=\"vai alla scheda del cliente\">"+ questoCliente.getNome()+"</a>");
					else
						output.append("<p>" + sendResult);

				}

				// 7: EVENTUALE registrazione oggetto StoriaDocumento con
				// risultato spedizione
				String sollecito = dataHolder.get("sollecito");
				if (ConstantsTulliniDMS
						.actionTriggersWorkflow(azione_conseguente_caricamento)) {
					unaStoria.setSollecito(sollecito);
					String res = registraStoriaDocumento(request, unaStoria,
							sendResult, questoCliente, true);
					output.append("<br />" + res + "<p>");
				}
			}

			template.replaceTag("MSG", output.toString());
			template.publish();
			return template.getContenuto();
		} catch (Exception e) {
			logger.error("Errore non gestito: " + e.getMessage());
			template.replaceTag("MSG",
					"Errore non gestito: si prega di contattare l'assistenza.");
			template.publish();
			return template.getContenuto();
		}
	}

	/**
	 * Metodo a sè perchè la gestione è completamente diversa, non prevedendo
	 * invio
	 * 
	 * @param request
	 * @param doc_ID
	 * @param template
	 * @param destinatari
	 * @return
	 * @throws SerenaException
	 */
	private String caricamentoSilnezioso(HttpServletRequest request,
			String doc_ID, ActiveTemplate template, Cliente[] destinatari)
			throws SerenaException {
		StringBuffer output = new StringBuffer();
		for (Cliente questoCliente : destinatari) {

			// 1: allochiamo un istanza di StoriaDocumento
			StoriaDocumento unaStoria = new StoriaDocumento();

			// 2: copiamo i dati del cliente
			unaStoria.setCliente_doc(questoCliente.getID());

			unaStoria.setDocumento(doc_ID);
			String res = registraStoriaDocumento(request, unaStoria, null,
					questoCliente, false);
			output.append("<br />" + res + "<p>");
		}
		template.replaceTag("MSG", output.toString());
		template.publish();
		return template.getContenuto();
	}

	private Cliente[] getDestinatariEspliciti(String emailList) {
		// TODO Auto-generated method stub
		String[] emails = emailList.split(",");
		Cliente[] clienteArray = new Cliente[emails.length];
		int i = 0;
		for (String anEmail : emails) {
			Cliente c = new Cliente();
			c.setEmail(anEmail);
			c.setNickname("");
			c.setNome("");
			c.setTipo_sollecito(Cliente.SOLLECITO_MAIL);
			clienteArray[i++] = c;
		}
		return clienteArray;
	}

	/**
	 * registra istanza di storia documento
	 * 
	 * @param request
	 * @param unaStoria
	 * @param sendResult
	 * @param questoCliente
	 * @param isWorkflow
	 *            - determina se si tratta di un vero workflow (quindi si
	 *            registrano dati invio ed esito) o semplice associazione col
	 *            cliente
	 * @return
	 * @throws SerenaException
	 */
	private String registraStoriaDocumento(HttpServletRequest request,
			StoriaDocumento unaStoria, String sendResult,
			Cliente questoCliente, boolean isRealWorkflow)
			throws SerenaException {

		logger.trace("Recording StoriaDocumento: as workflow? "
				+ isRealWorkflow);
		String ret = "";
		try {
			String dateFormat = ApplicationConfiguration.GetInstance()
					.getParametro(
							ApplicationConfiguration.INTERFACE_DATE_FORMAT);
			SimpleDateFormat myDateFormat = new SimpleDateFormat(dateFormat);
			unaStoria.setInviato_il(myDateFormat.format(new Date()));
		} catch (NoConfParamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (sendResult != null && !sendResult.isEmpty()) {
			unaStoria.setErrore(sendResult);
		}

		// registrazione
		Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(
				request.getSession().getId(), ConstantsXSerena.ACTION_SET,
				StoriaDocumento.MY_CLASS);
		currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION,
				ConstantsXSerena.VAL_INSERT);

		Element anItem = null;

		if (isRealWorkflow) { // solo per workflow
			if (unaStoria.getOggetto_mail() != null
					&& unaStoria.getOggetto_mail() != "") {
				anItem = currentElement.addElement("oggetto_mail");
				anItem.setText(unaStoria.getOggetto_mail());
			}

			if (unaStoria.getTesto_sms() != null
					&& unaStoria.getTesto_sms() != "") {
				anItem = currentElement.addElement("testo_sms");
				anItem.setText(unaStoria.getTesto_sms());
			}

			if (unaStoria.getTesto_mail() != null
					&& unaStoria.getTesto_mail() != "") {
				anItem = currentElement.addElement("testo_mail");
				anItem.setText(unaStoria.getTesto_mail());
			}

			anItem = currentElement.addElement("inviato_il");
			anItem.setText(unaStoria.getInviato_il());

			if (unaStoria.getErrore() != null && unaStoria.getErrore() != "") {
				anItem = currentElement.addElement("errore");
				anItem.setText(unaStoria.getErrore());
			}

			if (unaStoria.getSollecito() != null
					&& unaStoria.getSollecito() != "") {
				anItem = currentElement.addElement("sollecito");
				anItem.setText(unaStoria.getSollecito());
			}
		}
		anItem = currentElement.addElement("cliente_doc");
		anItem.setText(unaStoria.getCliente_doc());

		anItem = currentElement.addElement("inverse_of_storia_documento");
		anItem.setText(unaStoria.getDocumento());

		try {

			Document data = ApplicationLibrary.setData(
					currentElement.getDocument(), request);
			String[] messages2 = { "", "" };
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages2,
					StoriaDocumento.MY_CLASS);
			if (res == ConstantsXSerena.XSERENA_RESULT_ERROR) {
				ret += "Errore in fase di salvataggio del workflow";
				logger.error("cannot insert StoriaDocumento for client "
						+ unaStoria.getCliente_doc() + " due to  error:"
						+ messages2[0]);
			} else if (res == ConstantsXSerena.XSERENA_RESULT_SUCCESS) {
				ret += "Registrazione workflow avvenuta correttamente";
			}
		} catch (ObjectAlreadyExistsException e) {
			logger.error("cannot insert StoriaDocumento for client "
					+ unaStoria.getCliente_doc()
					+ " as it already exists ... how's THAT POSSIBLE?????");
			// Workflow già presente: non deve succedere!!!!!
		} catch (Exception e) {
			logger.error("cannot insert StoriaDocumento for client "
					+ unaStoria.getCliente_doc() + " due to  error:"
					+ e.getMessage());
			ret += "Errore invece in fase di salvataggio del workflow";
		}

		// output
		return ret;
	}

	/**
	 * Torna array di clienti filtrati dalla tipologia (cats)
	 * 
	 * @param cats
	 * @param request
	 * @return
	 * @throws SerenaException
	 */
	protected Cliente[] getDestinatari(String[] cats, HttpServletRequest request)
			throws SerenaException {
		Cliente[] clienteArray = null;

		try {
			Element currentElement = ObjectUtils
					.getXserenaRequestStandardHeader(request.getSession()
							.getId(), ConstantsXSerena.ACTION_GET,
							Cliente.MY_CLASS);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION,
					ConstantsXSerena.VAL_SELECT);
			currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET,
					ConstantsXSerena.TARGET_ALL);
			currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET_LEVELS,
					"0");
			currentElement = currentElement
					.addElement(ConstantsXSerena.TAG_CONDITION);
			currentElement = currentElement.addElement("tipo_cliente");
			currentElement = currentElement.addElement("_system_decode");
			currentElement = currentElement.addElement("sd_value");
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATOR,
					ConstantsXSerena.VAL_IN);
			StringBuffer inList = new StringBuffer();
			for (String x : cats)
				inList.append(x + ",");
			String whole = inList.toString();
			currentElement.setText(whole.substring(0, whole.length() - 1));

			String[] messages = { "", "" };
			Document resultData = ApplicationLibrary.getData(
					currentElement.getDocument(), request);
			Element data = ApplicationLibrary
					.prepareDataForPresentation(resultData);
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages,
					Cliente.MY_CLASS);

			if (res == ConstantsXSerena.XSERENA_RESULT_ERROR) {
				throw new SerenaException(messages[0], "generic_sql_error");
			} else if (res == ConstantsXSerena.XSERENA_RESULT_EMPTY) {
				clienteArray = new Cliente[0];
			} else if (res == ConstantsXSerena.XSERENA_RESULT_SUCCESS) {
				List<Element> iClienti = data.selectNodes(".//Cliente");
				clienteArray = new Cliente[new Integer(messages[1])];
				int i = 0;
				for (Element unCliente : iClienti) {
					Cliente ilCliente = new Cliente();
					ilCliente.setID(unCliente.elementText("ID"));
					ilCliente.setNome(unCliente.elementText("cliente"));
					ilCliente.setNickname(unCliente.elementText("nickname"));
					ilCliente.setEmail(unCliente.elementText("email"));
					ilCliente.setCellulare(unCliente.elementText("cellulare"));
					ilCliente.setTipo_sollecito(unCliente
							.elementText("tipo_sollecito"));
					clienteArray[i++] = ilCliente;
				}
			}
		} catch (Exception any) {
			String msg = "Could not fetch clienti for selected categories ";
			any.printStackTrace();
			logger.error(msg + any.getMessage());
			throw new SerenaException(msg);
		}

		return clienteArray;
	}

	/**
	 * Torna array con di clienti (improprio), ma in realtà sono operatori
	 * studio. Eventualmente solo la segreteria.
	 * 
	 * @param soloSegreteria
	 * @param request
	 * @return
	 * @throws SerenaException
	 */
	private Cliente[] getEmailOperatoriStudio(boolean soloSegreteria,
			HttpServletRequest request) throws SerenaException {
		Cliente[] clienteArray = null;

		try {
			Element currentElement = ObjectUtils
					.getXserenaRequestStandardHeader(request.getSession()
							.getId(), ConstantsXSerena.ACTION_GET,
							OperatoreClass);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION,
					ConstantsXSerena.VAL_SELECT);
			currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET,
					ConstantsXSerena.TARGET_SPECIFIED);
			currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET_LEVELS,
					"0");
			if (soloSegreteria) {
				Element cond = currentElement
						.addElement(ConstantsXSerena.TAG_CONDITION);
				cond = cond.addElement("ID");
				cond.setText(ConstantsTulliniDMS.ID_OPERATORE_SEGRETERIA);
			}
			currentElement.addElement("email");
			currentElement.addElement("ID");
			currentElement.addElement("nome_e_cognome");

			String[] messages = { "", "" };
			Document resultData = ApplicationLibrary.getData(
					currentElement.getDocument(), request);
			Element data = ApplicationLibrary
					.prepareDataForPresentation(resultData);
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages,
					OperatoreClass);

			if (res == ConstantsXSerena.XSERENA_RESULT_ERROR) {
				throw new SerenaException(messages[0], "generic_sql_error");
			} else if (res == ConstantsXSerena.XSERENA_RESULT_EMPTY) {

			} else if (res == ConstantsXSerena.XSERENA_RESULT_SUCCESS) {
				List<Element> operatori = data.selectNodes(".//Operatore");
				clienteArray = new Cliente[new Integer(messages[1])];
				int i = 0;
				for (Element unOp : operatori) {
					Cliente unCliente = new Cliente();
					unCliente.setID(unOp.elementText("ID"));
					unCliente.setNome(unOp.elementText("nome_e_cognome"));
					unCliente.setEmail(unOp.elementText("email"));
					clienteArray[i++] = unCliente;
				}
			}
		} catch (Exception any) {
			String msg = "Could not fetch operatori for selected categories ";
			any.printStackTrace();
			logger.error(msg + any.getMessage());
			throw new SerenaException(msg);
		}

		return clienteArray;
	}

	private Cliente[] getDestinatarioCliente(String theID,
			HttpServletRequest request) throws SerenaException {

		Cliente[] clienteArray = null;

		try {
			Element currentElement = ObjectUtils
					.getXserenaRequestStandardHeader(request.getSession()
							.getId(), ConstantsXSerena.ACTION_GET,
							Cliente.MY_CLASS);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION,
					ConstantsXSerena.VAL_SELECT);
			currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET,
					ConstantsXSerena.TARGET_ALL);
			currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET_LEVELS,
					"0");
			currentElement = currentElement
					.addElement(ConstantsXSerena.TAG_CONDITION);
			currentElement = currentElement.addElement(ConstantsEntityBean.ID);
			currentElement.setText(theID);

			String[] messages = { "", "" };
			Document resultData = ApplicationLibrary.getData(
					currentElement.getDocument(), request);
			Element data = ApplicationLibrary
					.prepareDataForPresentation(resultData);
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages,
					Cliente.MY_CLASS);

			if (res == ConstantsXSerena.XSERENA_RESULT_ERROR) {
				throw new SerenaException(messages[0], "generic_sql_error");
			} else if (res == ConstantsXSerena.XSERENA_RESULT_EMPTY) {
				clienteArray = new Cliente[0];
			} else if (res == ConstantsXSerena.XSERENA_RESULT_SUCCESS) {
				Cliente ilCliente = new Cliente();
				clienteArray = new Cliente[1];
				ilCliente.setID(theID);
				ilCliente
						.setNome(data.selectSingleNode(".//cliente").getText());
				ilCliente.setNickname(data.selectSingleNode(".//nickname")
						.getText());
				ilCliente.setTipo_sollecito(data.selectSingleNode(
						".//tipo_sollecito").getText());
				Element tmp = (Element) data.selectSingleNode(".//email");
				if (tmp != null)
					ilCliente.setEmail(tmp.getText());
				tmp = (Element) data.selectSingleNode(".//cellulare");
				if (tmp != null)
					ilCliente.setCellulare(tmp.getText());
				tmp = (Element) data.selectSingleNode(".//fax");
				if (tmp != null)
					ilCliente.setFax(tmp.getText());
				tmp = (Element) data.selectSingleNode(".//email2");
				if (tmp != null)
					ilCliente.setEmail2(tmp.getText());
				tmp = (Element) data.selectSingleNode(".//email3");
				if (tmp != null)
					ilCliente.setEmail3(tmp.getText());

				clienteArray[0] = ilCliente;
			}
		} catch (Exception any) {
			String msg = "Could not fetch CLiente with ID " + theID;
			logger.error(msg);
			throw new SerenaException(msg);
		}

		return clienteArray;
	}

	@Override
	protected String getName() {
		return METHOD_NAME;
	}

}
