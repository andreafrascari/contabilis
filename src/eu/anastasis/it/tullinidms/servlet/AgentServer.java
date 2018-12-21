/**
 * Questo file appartiene al progetto Sere-na - www.sere-na.it
 * @copyright Anastasis Soc. Coop. - www.anastasis.it
 */
package eu.anastasis.it.tullinidms.servlet;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import eu.anastasis.it.tullinidms.ConstantsTulliniDMS;
import eu.anastasis.it.tullinidms.modules.Cliente;
import eu.anastasis.it.tullinidms.modules.DescrizioneDocumento;
import eu.anastasis.it.tullinidms.modules.Documento;
import eu.anastasis.it.tullinidms.modules.RevisioneDocumento;
import eu.anastasis.it.tullinidms.modules.StoriaDocumento;
import eu.anastasis.it.tullinidms.modules.Targa;
import eu.anastasis.serena.application.core.tasks.CronPersistenceHome;
import eu.anastasis.serena.application.index.IndexQueryException;
import eu.anastasis.serena.application.index.SerenaServlet;
import eu.anastasis.serena.application.index.util.ApplicationConfiguration;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.index.util.AttachmentBean;
import eu.anastasis.serena.application.index.util.ModuleParameterConfiguration;
import eu.anastasis.serena.application.modules.object.ObjectIndexer;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.common.BeanCache;
import eu.anastasis.serena.common.XSerenaLibrary;
import eu.anastasis.serena.constants.ConstantsBaseLibrary;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.dataManager.DataManagerHome;
import eu.anastasis.serena.exception.NoConfParamException;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.serena.persistence.utils.IdReservationCache;
import eu.anastasis.serena.query.SelectQuery;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.TulliniHelpGestBeanDataManager;
import eu.anastasis.tulliniHelpGest.utils.MailAndSmsSender;

/**
 * Servlet x agent Tullini 
 * upload da client, passando tipo e data doc (usa una chiave simmetrica), registrazione documento/revisione, trigger del workflow
 * @author afrascari
 *
 */
public class AgentServer extends SerenaServlet 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AgentServer.class);
	
	protected static final String METHOD_UPLOAD= "upload";
		
	protected static final String SKEY = "provolapiccante";
	protected static final String UP_RES_SI = "OK";
	protected static final String UP_RES_NO = "NO";
	protected static final String UP_RES_LOADED_BUT_NOT_SENT = "Documento Caricato ma non inviato/reso visibile al cliente causa errore";
	
	protected static final String PARAMETER_UP_FILE = "upload_file";
	protected static final String PARAMETER_UP_TIPO = "upload_tipo";
	protected static final String PARAMETER_UP_DATA = "upload_data";
	protected static final String PARAMETER_UP_SKEY = "upload_key";
	protected static final String PARAMETER_UP_ANNOCONTABILE = "upload_anno";
		
	private static CronPersistenceHome cph = null;
	
	private MailAndSmsSender mailAndSmsSender = null;
	
	private MailAndSmsSender getMailAndSmsSender()	{
		if (mailAndSmsSender==null)	{
			mailAndSmsSender = new MailAndSmsSender();
		}
		return mailAndSmsSender;
	}
	

	private CronPersistenceHome getMyCronPersistenceHome(){
		if (cph==null)
			cph = new CronPersistenceHome();
		return cph; 
	}
	
	/********************************* SERVLET POST ************/
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		String qParam = request.getParameter(ConstantsBaseLibrary.QUERY_STRING_QUERY_PARAM);
		if (qParam==null)
		{
			rispondiPlain(UP_RES_NO, response);
		}
		if (qParam.equals(METHOD_UPLOAD))	
		{
			upload(request,response); 
		} else
		{
			rispondiPlain(UP_RES_NO, response);
		}
	}
	
	/**
	 * AgentServe deve registrare documenti da cui NON si desume nessun cliente?
	 * @return
	 */
	private boolean registerDocumentForNonClient(){
		try	{
			// NO/YES/NO-REVISION
			String registraXNonClienti=ModuleParameterConfiguration.getInstance().getValue("document","REGISTER_DOCUMENT_FOR_NON_CLIENT");	
			return !(registraXNonClienti.contains("NO"));
		} catch(Exception e){
			return true;
		}
	}
	
	/******************************** METHOD UPLOAD *******************************/
	
	@SuppressWarnings("rawtypes")
	private void upload(HttpServletRequest request, HttpServletResponse response) throws IOException	{
		// TODO Auto-generated method stub
		logger.trace("Upload request received");
		
		// controllo chiave simmetrica
		String theKey = request.getParameter(PARAMETER_UP_SKEY);
		if (!SKEY.equals(theKey))	{
			logger.error("ATTENZIONE, upload con chiave simmetrica non autorizzata: "+theKey);
			rispondiPlain(UP_RES_NO, "Errore autenticazione agent", response);
			return;
		}
		
		// upload attachment
		Documento d = new Documento();
		
		if (ServletFileUpload.isMultipartContent(request))	
		{
			try 
			{
				//	 Create a factory for disk-based file items
				FileItemFactory factory = new DiskFileItemFactory();
				//	Create a new file upload handler
				ServletFileUpload upload = new ServletFileUpload(factory);
				//  Parse the request
				List items = upload.parseRequest(request);
				//	Process the uploaded items
				Iterator iter = items.iterator();
				AttachmentBean anAttach = createAttachmentBean(request,getMyCronPersistenceHome());
				anAttach.setOperationInsert();
				//Default content type
				anAttach.setSa_content_type(AttachmentBean.LINK_CONTENT_TYPE);
				anAttach.setSa_size(0);
				// Scorriamo prima tutti i campi non file (in modo da raccogliere informazioni sul campo - in particolare il tipo)
				while (iter.hasNext()) 
				{
				    FileItem item = (FileItem) iter.next();
				    if (item.isFormField()) 
				    {
				    	if (!processStandardFormField(item,anAttach,d))
				    	{
				    		return; // no error reported, cosidered as fake upload (=post received, but fields were empty as user didn't mean to upload anything)
				    	}
				    }
				}
				//Ora riavvolgiamo e cerchiamo il campo File
				iter= items.iterator();
				while (iter.hasNext())
				{
				    FileItem item = (FileItem) iter.next();
				    if (!item.isFormField())
				    {
				    	if (!registerDocumentForNonClient())	{
					    	String guessedFileName = item.getName();
			    			if (Cliente.workoutClient(guessedFileName)==null && Targa.workoutCar4Unumberplate(guessedFileName,d.getTipo())==null) {
						    	logger.debug("Non voglio registrare il doc perche' non riconosco un cliente (ne' una targa) da: " + guessedFileName);
								rispondiPlain(UP_RES_NO,"DMS impostato per NON registrare documento su cliente non riconosciuto", response);
								return;
			    			}
				    	}
				    	d.setTitolo(processStandardUploadedFile(item,anAttach));
				    }
				}

				anAttach.setSa_alt_text(anAttach.getSa_filename());
				anAttach.setSa_link_text(anAttach.getSa_filename());
		    	anAttach.setSa_type("20");
		    	
		    	// INSERT ATTACHMENT
		    	anAttach.setID(anAttach.blindUpdate(request));
		    	logger.trace("File "+ anAttach.getSa_filename() + " has been saved");
		    	boolean isNewDoc = false;
		    	// register document ... OR UPGRADE REVISION IF EXISTS ...
				try	{
					isNewDoc = registerDocumento(d,anAttach);
				} catch (SerenaException e) {
					// as a rollback, the attachment is deleted
					try	{
						anAttach.physicalDelete(request);
						new File(anAttach.getMyPath()+anAttach.getSa_filename()).delete();
					}catch (Exception e1) {
						logger.error("Unfortunately I could not delete attachment: "+anAttach.getSa_filename()+": "+e1.getMessage());
					}
					logger.trace("Rollback completetd");
					rispondiPlain(UP_RES_NO,"Impossibile registrare documento o revisione: documento esistente ma cancellato?", response);
				}
				logger.trace("Document "+ d.getTitolo() + " has been saved");
				
				try	{
					// work out client ID (if any)
					String clientID = Cliente.workoutClient(d.getTitolo());
					if (clientID!=null)	{
						// register StoriaDocumento in order for the client to see it
						if (shouldItriggerWorkflow(clientID,d.getTipo())) 	{
							if (!doesDocumentAlreadyHasWorkflow(clientID,d))	{
								logger.debug("TRIGGERING WORKFLOW 4 client " + clientID);
								triggerWorkflow(clientID,d);
							} else {
								logger.debug("WORKFLOW 4 client " + clientID + " HAS ALREADY BEEN TRIGGERED SOMETIME IN THE PAST ... NOT DOING IT AGAIN!");
							}
						} else if (shouldImakeDocVisible2Client(d,isNewDoc))	{
							makeDocVisibile2Client(clientID,d);
						}
					} else {
						logger.trace("Could not tell client from document name: lets try if it's a car4you ticket");
						Targa numberPlate = Targa.workoutCar4Unumberplate(d.getTitolo(),d.getTipo());
						if (numberPlate!=null){
							numberPlate.loadCliente(request);
							if (numberPlate.getCliente()==null)	{
								// 19 sett 15: prova: NON rispodiamo errore per evitare RE-INVIO: arriva invece il fatal
								// rispondiPlain(UP_RES_LOADED_BUT_NOT_SENT,"Documento caricato, ma targa " + numberPlate.getTarga()+ " non riconosciuta",response);
								// return;
							} else {
								triggerWorkflow(numberPlate.getCliente(),d);
							}
						} else {
							try	{
								logger.fatal("Documento caricato, ma cliente non riconosciuto da " + d.getTitolo()+" WS answer is "+UP_RES_SI);
							} catch (Exception e){
								;
							}
							//	rispondiPlain(UP_RES_LOADED_BUT_NOT_SENT,"Documento caricato, ma cliente non riconosciuto",response);
							// nov 15: non e' piu' errore ... altrimenti Agent continua amandare su il file!!! Per ora metto un fatal ...
							
							rispondiPlain(UP_RES_SI,"Documento caricato, ma cliente non riconosciuto",response);
							return;
						}
						
					}
				}catch (SerenaException e) {
					logger.error("Document has been saved but not sent/linked to client due to an error (see above)");
					rispondiPlain(UP_RES_LOADED_BUT_NOT_SENT,response);
				}
				
			} catch (Exception e) 
			{
				// errore nel caricamento
				logger.error("Errore in qualche punto del processo di upload documento da agent (vedi sopra): "+e.toString());
				rispondiPlain(UP_RES_NO,"Errore non specificato",response);
				return;
			}
		}
		rispondiPlain(UP_RES_SI,response);
	}
	
	/**
	 * SOLO SE il documento "INCORPORA" il nome di un cliente, le azioni NON sono A,B o C ma il metadoc è visbilie al cliente
	 */
	private void makeDocVisibile2Client(String clientID, Documento d) throws SerenaException {
		try	{
			logger.trace("Making document visibile for "+clientID);
			// allochiamo un istanza di StoriaDocumento
			StoriaDocumento unaStoria = new StoriaDocumento();			
			unaStoria.setCliente_doc(clientID);
			unaStoria.setDocumento(d.getID());
			
			Cliente questoCliente = new Cliente().getInstance(clientID); 
			
			// registrazione oggetto StoriaDocumento con risultato spedizione (solo se rischiesto sollecito)
			registerStoriaDocumento(unaStoria,null,questoCliente,d,false);
			
		} catch (Exception e) 
		{
			String msg = "errore nell'associazione documento-cliente x la visibilita "+e.getMessage();
			logger.error(msg);
			throw new SerenaException(msg);
		}
		
	}


	private boolean shouldImakeDocVisible2Client(Documento d, boolean isNewDoc) throws SerenaException {
		if (!isNewDoc)
			return false;	// doc is already visible
		else
			return DescrizioneDocumento.getCLIENT_VISIBILTY_CACHE().get(d.getTipo()).booleanValue();
	}


	private boolean shouldItriggerWorkflow(String clientID, String tipo_doc) throws SerenaException	{
		String azione_conseguente_caricamento = new DescrizioneDocumento().getInstance(tipo_doc).getAzione_conseguente_caricamento();
		boolean ret = 
				azione_conseguente_caricamento.contains(ConstantsTulliniDMS.AZIONE_MAIL_NOTIFICA) || 
					azione_conseguente_caricamento.contains(ConstantsTulliniDMS.AZIONE_MAIL_NOTIFICA_LINK) ||
						azione_conseguente_caricamento.contains(ConstantsTulliniDMS.AZIONE_MAIL_NOTIFICA_LINK_LOGON	);
		logger.trace("ShouldItriggerWorkflow for "+tipo_doc+" if guess for client is "+clientID + " and workflow action is "+azione_conseguente_caricamento +"? "+ ret);
		return ret;
	}
	
	private boolean doesDocumentAlreadyHasWorkflow(String clientID, Documento d) throws SerenaException	{
		try	{
			logger.debug("Checking if workflow exists for client "+clientID + " doc " + d.getTitolo()+ " FALSE WILL RESULT INTO NO FURTHER LOG");
			SelectQuery query = new SelectQuery(eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.StoriaDocumento.INSTANCE_NAME);
			query.addCondition(eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.StoriaDocumento.SLOT_CLIENTE_DOC, clientID);
			query.addCondition(eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.StoriaDocumento.SLOT_INVERSE_OF_STORIA_DOCUMENTO, d.getID());
			query.getRootClassElement().addElement("ID");		
			Document result=getMyCronPersistenceHome().get(query.getDocument());		
			final int rdim = XSerenaLibrary.selectResultDimension(result);
			if (rdim >= 1) {
				logger.debug("YES: WF EXISTS!!!");
				return true;	
			}
			return false;			
		} catch (Exception e) 
			{
				String msg = "Error while checking if workflow exists for doc "+e.getMessage();
				logger.error(msg);
				throw new SerenaException(msg);
			}
	}
	/**
	 * SOLO SE il documento "INCORPORA" il nome di un cliente, e le azioni sono A,B o C
	 * @param clientID
	 * @param visible2CLient
	 * @param d
	 * @param request
	 * @throws SerenaException
	 */
	private void triggerWorkflow(String clientID, Documento d) throws SerenaException	{
		try	{
			logger.trace("Triggering workflow for client "+clientID);
			// 1: allochiamo un istanza di StoriaDocumento
			StoriaDocumento unaStoria = new StoriaDocumento();			
			unaStoria.setCliente_doc(clientID);
						
			// e anticipiamo l'ID del workflow che andremo ad inserire
			String willbeWorkflow = new IdReservationCache().guessNextId(StoriaDocumento.MY_CLASS);
			unaStoria.setDocumento(d.getID());
			
			// fetch del metadocumento relativo
			DescrizioneDocumento metadoc = new DescrizioneDocumento().getInstance(d.getTipo());
			Cliente questoCliente = new Cliente().getInstance(clientID); 
			if (questoCliente==null)	{
				logger.trace("Impossibile reperire cliente "+clientID+ ", probabilmente a causa di cessata assistenza.");
				return;
			}
			String ilTesto =null;
			String sendResult = null;			
			
			// 4: notifiche e solleciti via sms o mail?
			if (questoCliente.notificheViaSms())	{
				
				// 4.1.1: notifiche via sms: copiamo semplicemente il testo dell'sms e aggiungiamo titolo e tipo documento
				ilTesto = metadoc.getTemplate_sms();
				ilTesto = ilTesto.replace("@TIPO@", metadoc.getTipologia_documento());
				ilTesto = ilTesto.replace("@TITOLO@", d.getTitolo());
				ilTesto = ilTesto.replace("@NOME@", questoCliente.getNickname());
				unaStoria.setTesto_sms(ilTesto);
				
				// 4.2.2: invio sms
				sendResult = getMailAndSmsSender().sendSms(unaStoria, questoCliente);
				
			} else {
			
				// 4.2.1: notifiche via mail
	
				// 4.2.2: copiamo l'oggetto della mail, eventualmente personalizzandolo
				ilTesto = metadoc.getTemplate_oggetto_invio();
				ilTesto = ilTesto.replace("@TIPO@", metadoc.getTipologia_documento());
				ilTesto = ilTesto.replace("@TITOLO@",d.getTitolo());
				ilTesto = ilTesto.replace("@NOME@", questoCliente.getNickname());
				unaStoria.setOggetto_mail(ilTesto);
				
				// 4.2.3: copiamo il testo, eventualmente personalizzandolo
				ilTesto =  metadoc.getTemplate_testo_invio();
				ilTesto = ilTesto.replace("@NOME@", questoCliente.getNickname());
				ilTesto = ilTesto.replace("@TIPO@", metadoc.getTipologia_documento());
				ilTesto = ilTesto.replace("@TITOLO@", d.getTitolo());
				ilTesto = ilTesto.replace("@ABSTRACT@", "");	 // impossibile da agent!
				if (metadoc.getAzione_conseguente_caricamento().contains(ConstantsTulliniDMS.AZIONE_MAIL_NOTIFICA_LINK_LOGON) || metadoc.getAzione_conseguente_caricamento().contains(ConstantsTulliniDMS.AZIONE_MAIL_NOTIFICA_LINK))
				{
					// costruiamo il link del doc da scadicare

					//String baseUrl = request.getRequestURL().toString();
					//baseUrl = baseUrl.replace("Index",ConstantsTulliniDMS.DOWNLOAD_SERVLET);
					//baseUrl = baseUrl.replace(ConstantsTulliniDMS.AGENT_SERVLET,ConstantsTulliniDMS.DOWNLOAD_SERVLET);
					String baseUrl = ModuleParameterConfiguration.getInstance().getValue("document","DMS_URL");
					baseUrl = baseUrl+ConstantsTulliniDMS.DOWNLOAD_SERVLET;
					
					String theLink = null;
					if (metadoc.getAzione_conseguente_caricamento().contains(ConstantsTulliniDMS.AZIONE_MAIL_NOTIFICA_LINK_LOGON))	
						theLink= ConstantsTulliniDMS.BASE_DOWNLOAD_USER_LINK_FORCELOGON;
					else
						theLink = ConstantsTulliniDMS.BASE_DOWNLOAD_USER_LINK_SKIPLOGON;
					
					theLink = theLink.replace("@BASE_DOWNLOAD_LINK@", baseUrl);
					theLink = theLink.replace("@DOCID@", d.getID());
					theLink = theLink.replace("@ATTACHID@", d.getAllegato());
					theLink = theLink.replace("@WORKFLOWID@", willbeWorkflow);
					theLink = theLink.replace("@USERID@", questoCliente.getID()); 	
					
					theLink = "<a href=\"" + theLink + "\" title=\"clicca per scaricare il documento\">DMS</a>"; 
						
					ilTesto = ilTesto.replace("@LINK@", theLink); 
				}
				unaStoria.setTesto_mail(ilTesto);
				
				// 4.2.4: invio mail (o fax)
				sendResult = (questoCliente.notificheViaFax())?getMailAndSmsSender().sendFax(unaStoria, questoCliente,d.getAllegato()):getMailAndSmsSender().sendMail(unaStoria, questoCliente);
			}

			logger.trace("Notification sent with result (null = OK) "+sendResult);
			
			// 7: registrazione oggetto StoriaDocumento con risultato spedizione (solo se rischiesto sollecito)
			String sollecito =metadoc.getSollecito(); 
			if (!metadoc.getAzione_conseguente_caricamento().equals(ConstantsTulliniDMS.AZIONE_NOTIFICA_MAILING_LIST))		{
				unaStoria.setSollecito(sollecito);
				registerStoriaDocumento(unaStoria,sendResult,questoCliente,d,true);			
			}
			
		} catch (Exception e) 
		{
			String msg = "Error while triggering  workflow "+e.getMessage();
			logger.error(msg);
			throw new SerenaException(msg);
		}
		
	}

	/**
	 * Se isWorkflow, StoriaDocumento funge solo da relazionatore fra documento e cliente: si distingue dai workflow perchè non ha data invio
	 * @param d
	 * @param clientID
	 */
	private void registerStoriaDocumento(StoriaDocumento unaStoria, String sendResult, Cliente client, Documento d, boolean isWorkflow) throws SerenaException {
	try	{
		
		logger.trace("Recording StoriaDocumento: as workflow? "+isWorkflow);

		// registrazione
		Element currentElement = ObjectUtils.getXserenaRequestStandardHeader("agent", ConstantsXSerena.ACTION_SET, StoriaDocumento.MY_CLASS);
		currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_INSERT);
		
		Element anItem = currentElement.addElement("cliente_doc");
		anItem = anItem.addElement(Cliente.MY_CLASS);
		anItem = anItem.addElement("ID");
		anItem.setText(client.getID());
		
		anItem = currentElement.addElement("inverse_of_storia_documento");
		anItem = anItem.addElement("Documento");
		anItem = anItem.addElement("ID");
		anItem.setText(d.getID());

		if (isWorkflow){	// solo per workflow
			try {
				String 	dateFormat = ApplicationConfiguration.GetInstance().getParametro(
						ApplicationConfiguration.INTERFACE_DATE_FORMAT);
				SimpleDateFormat myDateFormat = new SimpleDateFormat(dateFormat);
				unaStoria.setInviato_il(myDateFormat.format(new Date()));
			} catch (NoConfParamException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (sendResult!=null &&  !sendResult.isEmpty()) {
				unaStoria.setErrore(sendResult);	
			}

			if (unaStoria.getOggetto_mail()!=null && unaStoria.getOggetto_mail()!="")	{ 
				anItem = currentElement.addElement("oggetto_mail");
				anItem.setText(unaStoria.getOggetto_mail());
			}
			
			if (unaStoria.getTesto_sms()!=null && unaStoria.getTesto_sms()!="")	{ 
				anItem = currentElement.addElement("testo_sms");
				anItem.setText(unaStoria.getTesto_sms());
			}
			
			if (unaStoria.getTesto_mail()!=null && unaStoria.getTesto_mail()!="")	{ 
				anItem = currentElement.addElement("testo_mail");
				anItem.setText(unaStoria.getTesto_mail());
			}
					
			anItem = currentElement.addElement("inviato_il");
			anItem.setText(unaStoria.getInviato_il());
			
			if (unaStoria.getErrore()!=null && unaStoria.getErrore()!="")	{ 
				anItem = currentElement.addElement("errore");
				anItem.setText(unaStoria.getErrore());
			}
					
			if (unaStoria.getSollecito()!=null && unaStoria.getSollecito()!="")	{ 
				anItem = currentElement.addElement("sollecito");
				anItem.setText(unaStoria.getSollecito());
			}
		}
		
		Document data = getMyCronPersistenceHome().set(currentElement.getDocument());
		String[] messages2={"",""};
		int res = ConstantsXSerena.getXserenaRequestResult(data, messages2,StoriaDocumento.MY_CLASS);
		if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
		{
			String msg = "Cannot insert StoriaDocumento for client "+client.getID()+ " due to  error:"+messages2[0];
			logger.error(msg);
			throw new SerenaException(msg);
		}
		else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)	{
			logger.trace("StoriaDocumento has been recorded");
		}
	} catch (Exception e) {
		String msg = "Cannot insert StoriaDocumento for client "+client.getID()+ " due to  error:"+e.getMessage();
		logger.error(msg);
		throw new SerenaException(msg);
	}		
		
}



	/**
	 * Registra il documento, andando a vedere se si tratta di documento nuovo (torna true) o revisione (torna false)
	 * d ha per il momento solo il title
	 * @param d
	 * @param a
	 * @throws SerenaException
	 */
	private boolean registerDocumento(Documento d, AttachmentBean a) throws SerenaException 
	{
		boolean isNewDoc=false;
		try	
		{
			// new doc o revisione?
			logger.trace("Document exists?");
			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader("agent", ConstantsXSerena.ACTION_GET, Documento.MY_CLASS);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_SELECT);
			currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET,ConstantsXSerena.TARGET_SPECIFIED);
			Element theCond= currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
			Element anItem = theCond.addElement("titolo"); // la chiave è il titolo!!!!!!
			anItem.setText(d.getTitolo());
			currentElement.addElement("ID");
			currentElement.addElement("titolo");
			currentElement.addElement("data_riferimento");
			currentElement.addElement("last_modification_date");
			currentElement.addElement("last_modification_user");
			anItem = currentElement.addElement("allegato");
			anItem = anItem.addElement("_system_attachment");
			anItem = anItem.addElement("ID");
			
			String[] messages={"",""};
			Document resultData=getMyCronPersistenceHome().get(currentElement.getDocument());
			Element data = ApplicationLibrary.prepareDataForPresentation(resultData);
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages,Documento.MY_CLASS);

			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{				
				logger.error("Error while testing if doc exists: "+messages[0]);
				throw new SerenaException(messages[0],"generic_sql_error");
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_EMPTY)
			{
				registerNewDocumento(d, a); // doc non esiste -> new
				isNewDoc = true;
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)
			{
				logger.trace("YES: registering new revision");
				Element theDocElem = (Element)data.selectSingleNode(".//Documento"); 
				d.setID(theDocElem.elementText("ID"));
				d.setLast_modification_user(theDocElem.elementText("last_modification_user"));
				d.setLast_modification_date(theDocElem.elementText("last_modification_date"));
				d.setAllegato(a.getID());
				Element allegato = (Element)theDocElem.selectSingleNode(".//_system_attachment");
				if (allegato!=null)	{
					registerRevisione(d, a, allegato.elementText("ID"));  // doc esiste -> revisione
				} else {
					logger.warn("Acward situation: document " + d.getTitolo() + " exists but has no attachment");
					updateDocumento(d,a);
				}
				isNewDoc = false;
			}
			return isNewDoc;
		} catch (Exception e) {
				logger.error("Cannot register document from agent ... should now delete the attachment", e);
				throw new SerenaException("");
		}
	}
			
			
	private void updateDocumento(Documento d, AttachmentBean a) throws SerenaException {
		try	
		{
		Element currentElement = ObjectUtils.getXserenaRequestStandardHeader("agent", ConstantsXSerena.ACTION_SET, Documento.MY_CLASS);
		currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_UPDATE);
		Element theCond= currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
		Element anItem = theCond.addElement("ID"); // la chiave è il titolo!!!!!!
		anItem.setText(d.getID());
		
		anItem = currentElement.addElement("allegato");
		anItem = anItem.addElement("_system_attachment");
		anItem.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_INSERT);
		anItem = anItem.addElement("ID");
		anItem.setText(a.getID());
		
		String[] messages={"",""};
		Document resultData=getMyCronPersistenceHome().set(currentElement.getDocument());
		Element data = ApplicationLibrary.prepareDataForPresentation(resultData);
		int res = ConstantsXSerena.getXserenaRequestResult(data, messages, Documento.MY_CLASS);

		if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
		{
			logger.error("cannot updatedocument from agent " + messages[0]);
			throw new SerenaException(messages[0],"generic_sql_error");
		}
		else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)
		{
			logger.trace("document from agent " + a.getSa_filename() + " has been successfully updated");
			d.setID(messages[1]);	// set ID for future reference
		}
	} catch (Exception e) {
		logger.error("cannot register document from agent", e);
		throw new SerenaException("");
	}
	}

	private void registerRevisione(Documento doc, AttachmentBean a, String idAllegatoPrecedente) throws SerenaException{
		// TODO Auto-generated method stub
		try	{
			
			// 1: registrazione revisione
			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader("agent", ConstantsXSerena.ACTION_SET, RevisioneDocumento.MY_CLASS);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_INSERT);
 
			Element anItem = currentElement.addElement("inverse_of_revisioni");
			anItem = anItem.addElement("Documento");
			anItem = anItem.addElement("ID");
			anItem.setText(doc.getID());
						
			anItem = currentElement.addElement("allegato");
			anItem = anItem.addElement("_system_attachment");
			anItem = anItem.addElement("ID");
			anItem.setText(idAllegatoPrecedente);
			
			anItem = currentElement.addElement("last_modification_date");
			anItem.setText(doc.getLast_modification_date());
			
			anItem = currentElement.addElement("last_modification_user");
			anItem.setText(doc.getLast_modification_user());
			
			Document resdata = getMyCronPersistenceHome().set(currentElement.getDocument());
			String[] messages2={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(resdata, messages2,RevisioneDocumento.MY_CLASS);
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("Cannot insert revisione " + doc.getLast_modification_date() + " for doc " + doc.getID());
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)	{
				logger.trace("Revison " + a.getSa_filename() + " has been successfully registered");
			}	
			
			// 2: update documento with new id attach
			currentElement = ObjectUtils.getXserenaRequestStandardHeader("agent", ConstantsXSerena.ACTION_SET, Documento.MY_CLASS);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_UPDATE); 
			anItem = currentElement.addElement(ConstantsXSerena.TAG_CONDITION);			
			anItem = anItem.addElement("ID");
			anItem.setText(doc.getID());
			anItem = currentElement.addElement("allegato");
			anItem.setText(a.getID());
			resdata = getMyCronPersistenceHome().set(currentElement.getDocument());
			String[] messages={"",""};
			res = ConstantsXSerena.getXserenaRequestResult(resdata, messages,RevisioneDocumento.MY_CLASS);
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("Cannot update document with revision ID" + doc.getLast_modification_date() + " for doc " + doc.getID());
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)	{
				logger.trace("Document has been updated with revision ID");
			}		

		} catch (Exception e) {
			logger.error("Cannot insert Revisione " + doc.getLast_modification_date() + " for doc " + doc.getID());
			throw new SerenaException("Cannot insert revision " + doc.getLast_modification_date() + " for doc " + doc.getID());
		}
	}

	private void registerNewDocumento(Documento d, AttachmentBean a) throws SerenaException
	{
		try	
			{
			logger.trace("NO: registering new document");
			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader("agent", ConstantsXSerena.ACTION_SET, Documento.MY_CLASS);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_INSERT);
	
			Element anItem = currentElement.addElement("titolo");
			anItem.setText(d.getTitolo());
			
			anItem = currentElement.addElement("tipologia");
			anItem = anItem.addElement(DescrizioneDocumento.MY_CLASS);
			anItem = anItem.addElement("tipologia_documento");
			anItem.setText(d.getTipo());
			
			anItem = currentElement.addElement("allegato");
			anItem.setText(a.getID());
			
			anItem = currentElement.addElement("data_riferimento");
			anItem.setText(d.getData());
			
			if (d.getAnno_contabile() != null)
			{
				anItem = currentElement.addElement("anno_contabile");
				anItem.setText(d.getAnno_contabile());				
			}
			
			String[] messages={"",""};
			Document resultData=getMyCronPersistenceHome().set(currentElement.getDocument());
			Element data = ApplicationLibrary.prepareDataForPresentation(resultData);
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages, Documento.MY_CLASS);
	
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("cannot register document from agent " + messages[0]);
				throw new SerenaException(messages[0],"generic_sql_error");
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)
			{
				logger.trace("Document from agent " + a.getSa_filename() + " has been successfully registered");
				d.setID(messages[1]);	// set ID for future reference
				d.setAllegato(a.getID());
				try	{
					ObjectIndexer oI = new ObjectIndexer( BeanCache.getInstance().getInterfaceBean(Documento.MY_CLASS));
					oI.index(currentElement.getDocument(),messages[1]);
				} catch (Exception e) {
					logger.warn("Cannot index agent-uploaded document!");
				}
			}
		} catch (Exception e) {
			logger.error("cannot register document from agent", e);
			throw new SerenaException("");
		}
	}

	/**
	 * process upload form, stores file, returns attachment and original file name
	 * @param item
	 * @param anAttach
	 * @return
	 * @throws IndexQueryException
	 */
	protected String processStandardUploadedFile(FileItem item,AttachmentBean anAttach) throws IndexQueryException    {
	       String fieldName = item.getFieldName();
	       String fileName = item.getName();
	       String originalFileName = fileName;
	       String tryWith = "";
	       anAttach.setSa_content_type(item.getContentType());
	       anAttach.setSa_size(item.getSize());
	       logger.trace("Processing file field "+fieldName);
           try 
           {
        	   String thePath = anAttach.getMyPath();
               // stores file in repository
           int pos;
           while ((pos = fileName.indexOf("\\"))>-1) // upload  from IE passes local path
           {
               fileName = fileName.substring(pos+1,fileName.length());
           }
           // problema: se un file con lo stesso nome esiste gia', finche' ne trovo, aggiungo un _N al nome
           int instanceCounter=1;
           tryWith = fileName;
           tryWith=tryWith.replace(" ", "_");
           while (new File(thePath+tryWith).exists())
           {
               int dotPos = fileName.indexOf(".");
               if (dotPos>-1)
               {
            	   tryWith =fileName.substring(0,dotPos)+(instanceCounter++)+fileName.substring(dotPos,fileName.length());
               } else
               {
            	   tryWith =fileName+(instanceCounter++);
               }
               tryWith=tryWith.replace(" ", "_");
           }
           anAttach.setSa_filename(tryWith);
           File uploadedFile = new File(thePath+tryWith);
           item.write(uploadedFile);
           } catch (SerenaException e) {
               throw new IndexQueryException("Errore in scrittura file: " + e.getMessage());
           } catch (Exception e) {
               throw new IndexQueryException("Errore in scrittura file: " + e.toString());
           }
           logger.trace("Uploaded completed for document " + originalFileName + " whose attachment is called " +tryWith);
           return originalFileName;
	   }

	
	
	protected boolean processStandardFormField(FileItem item,AttachmentBean anAttach, Documento d)	{
	    String name = item.getFieldName();
	    String value = item.getString();
		logger.trace("Processing form field " + name + ": " + value);
				
	    if (name.equals(PARAMETER_UP_TIPO))	
	    {
	    	if (value==null || value.isEmpty())
	    	{
	    		return false; // fake upload
	    	}
	    	d.setTipo(value);
	    } else if (name.equals(PARAMETER_UP_DATA))	
	    {
	    	if (value==null || value.isEmpty())
	    	{
	    		return false; // fake upload
	    	}
	    	d.setData(value);
	    }  else if (name.equals(PARAMETER_UP_ANNOCONTABILE))	
	    {
	    	if (value==null || value.isEmpty())
	    	{
	    		// never mind
	    	}
	    	d.setAnno_contabile(value);
	    }  else
	    	logger.warn("Input field " + name + " was unexpected...");
	    return true;
	}
	
	

	protected AttachmentBean createAttachmentBean(HttpServletRequest request, DataManagerHome persistence)
	{
		return new AttachmentBean(request, persistence);
	}
	

	
	protected void rispondiPlain(String message,HttpServletResponse response) throws IOException
	{
		rispondiPlain(message,null, response);
	}
	
	protected void rispondiPlain(String message,String message2, HttpServletResponse response) throws IOException
	{
		ServletOutputStream out = response.getOutputStream();
		out.print(message);
		if (message2!=null)
			out.print(" - "+message2);	
		out.flush();
		out.close();
	}

	

	
}
	