package eu.anastasis.tulliniHelpGest.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import eu.anastasis.it.tullinidms.modules.Cliente;
import eu.anastasis.serena.application.core.tasks.CronPersistenceHome;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.index.util.ModuleParameterConfiguration;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.auth.exceptions.PermissionException;
import eu.anastasis.serena.common.IoLibrary;
import eu.anastasis.serena.common.SerenaDate;
import eu.anastasis.serena.constants.ConstantsBaseLibrary;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.dataManager.DataManagerException;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.tulliniHelpGest.beans.NotificaScadenza;
import eu.anastasis.tulliniHelpGest.beans.SuperCliente;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Attivita;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.MyOperatore;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Operatore;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Pratica;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.ProfiloDinamicoClienti;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Scadenza;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.TulliniHelpGestBinder;
import eu.anastasis.tulliniHelpGest.tasks.GenerationCache;
import eu.anastasis.tulliniHelpGest.utils.MailAndSmsSender;

public class MetascadenzeProcessor
{
	public static final String RulesFolder = ConstantsBaseLibrary.realpath + "/" + ConstantsBaseLibrary.TEMPLATE_DIR + "/rules/";
	public static final String QueryFile = "query_gen_scadenze.xml";
	protected static int CHECK_MONTHS_AHEAD = 3;
	protected static int GENERATE_MONTHS_AHEAD = 12;
	public static final String ID_FAKE_OPERATOR_METASCADENZE_DOUBLECHECK = "104";
	
//	MailAndSmsSender2 sender=null;
	MailAndSmsSender sender=null;

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MetascadenzeProcessor.class);

	private static final String RICORRENZA_GIORNALIERA = "giornaliera";
	private static final String RICORRENZA_SETTIMANALE = "settimanale";
	private static final String RICORRENZA_MENSILE = "mensile";
	private static final String RICORRENZA_TRIMESTRALE = "trimestrale";
	private static final String RICORRENZA_QUADRIMESTRALE = "quiadrimestrale";
	private static final String RICORRENZA_SEMESTRALE = "semestrale";
	private static final String RICORRENZA_ANNUALE = "annuale";

	protected static String date_format = null;
	private static CronPersistenceHome cph = null;

	protected static String DATASOURCE_NOTIFICHE_OGGI = "notifiche_di_oggi";

	protected static String DESTINATARIO_OPERATORE = "operatore";
	protected static String DESTINATARIO_CLIENTE = "cliente";

	protected static String TAG_DATA = "@DATA@";
	protected static String TAG_CODICE = "@CODICE@";
	protected static String TAG_BASEURL = "@BASEURL@";
	protected static String TAG_ID = "@ID@";
	protected static String LINK_PRATICA = "<br /><a href=\"" + TAG_BASEURL + "?q=object/detail&p=Pratica/_a_ID/_v_" + TAG_ID + "\" title=\"vai alla pratica (se loggata/o)\">Vai alla pratica " + TAG_CODICE + " (se loggata/o nel sistema)</a>";
	protected static String LINK_ATTIVITA = "<br /><a href=\"" + TAG_BASEURL + "?q=object/detail&p=Attivita/_a_ID/_v_" + TAG_ID + "\" title=\"vai alla attivita (se loggata/o)\">Vai alla attivita " + TAG_CODICE + " (se loggata/o nel sistema)</a>";

	/*
	private  MailAndSmsSender2 getMailSmsSender()	{
		if (this.sender==null){
			sender = new MailAndSmsSender2();
		}
		return sender;
	}
	*/
	private  MailAndSmsSender getMailSmsSender()	{
		if (this.sender==null){
			sender = new MailAndSmsSender();
		}
		return sender;
	}
	/**
	 * Dalla lista delle richieste ricorrenti, si tengono solo: - quelle di oggi
	 * (al netto del preavviso) se invio = true - quelle di oggi (con 3 mesi di
	 * anticipo) se invio = false
	 * 
	 * @param richieste
	 * @return
	 */
	public List<Element> processRicorrenti(Element richieste, boolean invio)
	{
		ArrayList<Element> res = new ArrayList<Element>();
		List<Element> allReqs = richieste.selectNodes(".//" + Scadenza.INSTANCE_NAME);
		for (Element tmpReq : allReqs)
		{
			Element aReq = (Element) tmpReq.clone();
			String data = aReq.elementText(Scadenza.SLOT_DATA);
			String ricorrenza = aReq.elementText(Scadenza.SLOT_RICORRENZA);
			String preavviso = aReq.elementText(Scadenza.SLOT_PREAVVISO_GG);
			SerenaDate fineRicorrenza = null;
			Element fineRicElem = aReq.element(Scadenza.SLOT_FINE_RICORRENZA);
			if (fineRicElem != null && !fineRicElem.getText().isEmpty())
			{
				fineRicorrenza = new SerenaDate(aReq.elementText(Scadenza.SLOT_FINE_RICORRENZA));
			}
			// data dalla scadenza
			SerenaDate dataScadenza = new SerenaDate(data);
			if (fineRicorrenza == null || dataScadenza.isBefore(fineRicorrenza))
			{
				int scadenzaDay = dataScadenza.getDay();
				int scadenzaDayOfweek = dataScadenza.getDayOfWeek();
				int scadenzaMonth = dataScadenza.getMonth();
				// data di oggi, meno il preavviso
				SerenaDate dataOggi = new SerenaDate();
				if (invio)
					dataOggi.addDays(new Integer(preavviso).intValue());
				else
					dataOggi.addMonths(new Integer(CHECK_MONTHS_AHEAD).intValue());
				int dataOggiDay = dataOggi.getDay();
				int dataOggiDayOfweek = dataOggi.getDayOfWeek();
				int dataOggiMonth = dataOggi.getMonth();
				// importante: la data scadenza "generata" è oggi + il preavviso
				// o i 3m
				aReq.element(Scadenza.SLOT_DATA).setText(dataOggi.toString());
				if (RICORRENZA_GIORNALIERA.equals(ricorrenza))
				{
					res.add((Element) aReq.clone());
				} else if (RICORRENZA_GIORNALIERA.equals(ricorrenza))
				{
					res.add(aReq);
				} else if (RICORRENZA_SETTIMANALE.equals(ricorrenza))
				{
					if (dataOggiDayOfweek == scadenzaDayOfweek)
						res.add(aReq);
				} else if (RICORRENZA_MENSILE.equals(ricorrenza))
				{
					if (dataOggiDay == scadenzaDay)
						res.add(aReq);
					else if (isMeseDi30(dataOggiMonth) && dataOggiDay == 30 && scadenzaDay == 31)
						res.add(aReq);
					else if (isMeseDi28(dataOggiMonth) && dataOggiDay == 28 && (scadenzaDay == 31 || scadenzaDay == 30))
						res.add(aReq);
				} else if (RICORRENZA_TRIMESTRALE.equals(ricorrenza))
				{
					if (dataOggiDay == scadenzaDay && ((dataOggiMonth - scadenzaMonth) % 3) == 0)
						res.add(aReq);
				} else if (RICORRENZA_QUADRIMESTRALE.equals(ricorrenza))
				{
					if (dataOggiDay == scadenzaDay && ((dataOggiMonth - scadenzaMonth) % 4) == 0)
						res.add(aReq);
				} else if (RICORRENZA_SEMESTRALE.equals(ricorrenza))
				{
					if (dataOggiDay == scadenzaDay && ((dataOggiMonth - scadenzaMonth) % 6) == 0)
						res.add(aReq);
				} else if (RICORRENZA_ANNUALE.equals(ricorrenza))
				{
					if (dataOggiDay == scadenzaDay && dataOggiMonth == scadenzaMonth)
						res.add(aReq);
				} else
				{
					logger.error("Ricorrenza " + ricorrenza + " non gestita!");
				}
			}
		}
		return res;
	}

	private boolean isMeseDi28(int dataOggiMonth)
	{
		return dataOggiMonth == 2;
	}

	private boolean isMeseDi30(int dataOggiMonth)
	{
		return (dataOggiMonth == 4 || dataOggiMonth == 6 || dataOggiMonth == 9 || dataOggiMonth == 11);
	}

	/**
	 * Attention!!! It's static
	 * 
	 * @return
	 */
	private CronPersistenceHome getMyCronPersistenceHome()
	{
		if (cph == null)
			cph = new CronPersistenceHome();
		return cph;
	}

	/**
	 * Fetch richieste di scadenze parametrizzato sul parametro "quali" che
	 * determina il datasource da utilizzare
	 * 
	 * @param quali
	 * @return
	 * @throws SerenaException
	 */
	public Element getRichiesteScadenze(String quali) throws SerenaException
	{
		try
		{
			logger.debug("[MemoReminder]-> is running");
			Document dynQuery = null;
			try
			{
				dynQuery = DocumentHelper.parseText(IoLibrary.readTextFile(RulesFolder + "/" + QueryFile));
			} catch (DocumentException e)
			{
				throw new SerenaException("Unable to parse rules file", e);
			} catch (IOException e)
			{
				throw new SerenaException("Unable to read rules file", e);
			}
			Element query = dynQuery.getRootElement();
			((Element) query.selectSingleNode(".//" + Scadenza.INSTANCE_NAME)).addAttribute(ConstantsXSerena.ATTR_FORCED_DATASOURCE, quali);

			Document data = getMyCronPersistenceHome().get(query.getDocument());

			Element dataElem = ApplicationLibrary.prepareDataForPresentation(data);
			String[] messages =
			{ "", "" };
			int res = ConstantsXSerena.getXserenaRequestResult(dataElem, messages, Scadenza.INSTANCE_NAME);

			if (res == ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("[MemoReminder]->getMemos2Process has resulted into sql error:" + messages[0]);
				throw new SerenaException(messages[0], "generic_sql_error");
			} else if (res == ConstantsXSerena.XSERENA_RESULT_EMPTY)
			{
				return null;
			} else if (res == ConstantsXSerena.XSERENA_RESULT_SUCCESS)
			{
				return dataElem;
			} else
				return null;
		} catch (PermissionException e)
		{
			logger.error("[MemoReminder]->getMemos2Process has resulted into sql error" + e.getMessage());
			throw new SerenaException(e.getMessage());
		} catch (DataManagerException e)
		{
			logger.error("[MemoReminder]->getMemos2Process has resulted into sql error" + e.getMessage());
			throw new SerenaException(e.getMessage());
		}
	}

	public void processRequest(Element aRequest, boolean invio, boolean record) throws SerenaException
	{
		processRequest(aRequest, invio, record, false);
	}

	public void processRequest(Element aRequest, boolean invio, boolean record, boolean allowsMultipleEntries) throws SerenaException
	{
		try
		{
			Element pratica = aRequest.element(Scadenza.SLOT_INVERSE_OF_PRATICA_SCADENZA).element(Pratica.INSTANCE_NAME);
			Element attivita = aRequest.element(Scadenza.SLOT_INVERSE_OF_ATTIVITA_SCADENZA).element(Attivita.INSTANCE_NAME);
			Element destinatari = aRequest.element(Scadenza.SLOT_PROFILI_CLIENTI);
			Element operatore = aRequest.element(Scadenza.SLOT_INVERSE_OF_OP_SCADENZE).element(Operatore.INSTANCE_NAME);
			Element cliente = aRequest.element(Scadenza.SLOT_INVERSE_OF_SCADENZE).element(Cliente.INSTANCE_NAME);

			Vector<String> destinatariString = new Vector<String>();
			if (destinatari != null)
			{
				// fetch lista destinatari:
				List<Element> destinatariDecos = destinatari.selectNodes(".//" + ProfiloDinamicoClienti.INSTANCE_NAME);
				for (Element aDest : destinatariDecos)
				{
					destinatariString.add(aDest.elementText(ProfiloDinamicoClienti.SLOT_QUERY));
				}
			}
			// analisi destinatari
			if (operatore != null || attivita != null || pratica != null)
			{
				processOperatore(aRequest, operatore, pratica, attivita, invio, record, allowsMultipleEntries);
				destinatariString.remove(DESTINATARIO_OPERATORE);
			}

			if (cliente != null)
			{
				processCliente(aRequest, cliente, pratica, attivita, invio, record, allowsMultipleEntries);
				destinatariString.remove(DESTINATARIO_CLIENTE);
			}

			// gruppo profilato
			if (!destinatariString.isEmpty())
			{
				processProfiledGroup(aRequest, destinatariString, invio, record, allowsMultipleEntries);
			}
		} catch (Exception e)
		{
			String msg = "processRequest - errore processando richiesta: " + e.toString();
			logger.error(msg);
			throw new SerenaException(msg);
		}
	}

	private void processProfiledGroup(Element aRequest, Vector<String> destinatariString, boolean invio, boolean record, boolean allowsMultipleEntries) throws SerenaException
	{
		try
		{
			logger.info("Running MetascadenzeProcessor.processProfiledGroup");
			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader("clientiprofilo", ConstantsXSerena.ACTION_GET, SuperCliente.INSTANCE_NAME);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_SELECT);
			currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET, ConstantsXSerena.TARGET_SPECIFIED);
			currentElement.addElement("ID");
			currentElement.addElement("cellulare");
			currentElement.addElement("email");
			currentElement.addElement("email2");
			currentElement.addElement("email3");
			currentElement.addElement("fax");
			currentElement.addElement("tipo_sollecito");
			currentElement.addElement("cliente");
			Element rootCond = currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
			Element currentCond = rootCond.addElement(eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Cliente.SLOT_CESSATA_ASSISTENZA_IL);
			currentCond.setText(ConstantsXSerena.VAL_NULL);
			currentCond = rootCond.addElement(ConstantsXSerena.TAG_OR);
			for (String aCond : destinatariString)
			{
				Element aCondElem = DocumentHelper.parseText(aCond).getRootElement();
				List<Element> siblings = aCondElem.elements();
				for (Element sib : siblings)
				{
					currentCond.add((Element) sib.clone());
				}
			}
			/* ACCROCCHIO DEPLOREVOLE - PUNTO DEBOLE */
			// have to get rid of excess deletion/activation_flag (out of
			// persisntece bug)
			List<Element> dF = currentElement.selectNodes(".//deletion_flag");
			for (Element aDF : dF)
			{
				aDF.getParent().remove(aDF);
			}
			dF = currentElement.selectNodes(".//activation_flag");
			for (Element aDF : dF)
			{
				aDF.getParent().remove(aDF);
			}
			dF = currentElement.selectNodes(".//and");
			for (Element aDF : dF)
			{
				if (aDF.elements().isEmpty())
				{
					aDF.getParent().remove(aDF);
				}
			}
			dF = currentElement.selectNodes(".//or");
			for (Element aDF : dF)
			{
				if (aDF.elements().isEmpty())
				{
					aDF.getParent().remove(aDF);
				}
			}
			String[] messages =
			{ "", "" };
			Document resultData = new CronPersistenceHome().get(currentElement.getDocument());
			Element data = ApplicationLibrary.prepareDataForPresentation(resultData);
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages, SuperCliente.INSTANCE_NAME);
			if (res == ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				String msg = "Cannot load client from profile " + messages[0];
				logger.error(msg);
				throw new SerenaException(msg);
			} else if (res == ConstantsXSerena.XSERENA_RESULT_EMPTY)
			{
				String msg = "Nessun cliente identificato dal profilo richiesto";
				logger.warn(msg);
			} else if (res == ConstantsXSerena.XSERENA_RESULT_SUCCESS)
			{
				boolean forceSMS = new Boolean(aRequest.elementText(Scadenza.SLOT_FORZA_SMS));
				GenerationCache g = new GenerationCache(new SerenaDate().toString());
				String id_richiesta = aRequest.elementText("ID");
				String date = aRequest.elementText(Scadenza.SLOT_DATA);
				// String preavviso =
				// aRequest.elementText(Scadenza.SLOT_PREAVVISO_GG);
				String oggetto = aRequest.elementText(Scadenza.SLOT_OGGETTO_SCADENZA);
				String descrizione = aRequest.elementText(Scadenza.SLOT_DESCRIZIONE_SCADENZA);
				oggetto = oggetto.replace(TAG_DATA, date);
				// processing all clients matching condition(s) in List
				List<Element> iClienti = data.selectNodes(".//SuperCliente");
				
				String listaClienti = "<br /><strong>Destinatari</strong>:<ul>";
				logger.info("sending to " + iClienti.size() + " clienti....");
				getMailSmsSender().initBulkSend();
				String refDate = new SerenaDate().toString();
				for (Element unCliente : iClienti)
				{
					Cliente unClienteObj = new Cliente();
					String theID = unCliente.elementText("ID");
					unClienteObj.setID(theID);
					unClienteObj.setNome(unCliente.elementText("cliente"));
					listaClienti += "<li>"+unClienteObj.getNome()+"</li>";
					String mydescrizione = descrizione.replace("@CLIENTE@", unClienteObj.getNome());
					String myoggetto = oggetto.replace("@CLIENTE@", unClienteObj.getNome());
					if (unCliente.element("tipo_sollecito") != null)
						unClienteObj.setTipo_sollecito(unCliente.elementText("tipo_sollecito"));
					if (unCliente.element("email") != null)
						unClienteObj.setEmail(unCliente.elementText("email"));
					if (unCliente.element("email2") != null)
						unClienteObj.setEmail2(unCliente.elementText("email2"));
					if (unCliente.element("email3") != null)
						unClienteObj.setEmail3(unCliente.elementText("email3"));
					if (unCliente.element("fax") != null)
						unClienteObj.setFax(unCliente.elementText("fax"));
					if (unCliente.element("cellulare") != null)
						unClienteObj.setCellulare(unCliente.elementText("cellulare"));
					try
					{
						if (invio)
						{
							// mail/fax/sms
							if (!g.itemSent4Client(theID, id_richiesta,refDate))
							{
								g.assertItemSent4Client(theID, id_richiesta,refDate);
								logger.debug("sending mail to  " +  unClienteObj.getEmail() + " on date " + refDate + " for metascadenza "+ id_richiesta);
								if (unClienteObj.notificheViaSms() || forceSMS)
									getMailSmsSender().sendSms(myoggetto, unClienteObj);
								else if (unClienteObj.notificheViaFax())
									getMailSmsSender().sendFax(myoggetto, mydescrizione, unClienteObj, null);
								else	{
									getMailSmsSender().sendMailInBulkLot(myoggetto, mydescrizione, unClienteObj);
								}
							}
						} 
						if (record)
						{
							// istanza di NotificaScadenza in bacheca
							if (allowsMultipleEntries || !g.itemStored4Client(theID, id_richiesta,refDate))
							{
								logger.debug("storing notifica for op  " +  unClienteObj.getCodicecliente() + " on date " + refDate + " for metascadenza "+ id_richiesta);
								g.assertItemSent4Client(theID, id_richiesta,refDate);
								insertNotifica(date, myoggetto, mydescrizione, null, unClienteObj.getID(), id_richiesta);
							}
						}
						

					} catch (Exception e)
					{
						String msg = "Exception processando profilo dinamico, cliente " + unClienteObj.getID() + ". Si procede, potrebbe essere semplice duplicato per questione SuperCliente " + e.getMessage();
						logger.warn(msg, e);
					}
				}
				// se NON innvio, inserimento SEMPRE notifica a Fabio:
				if (!invio)
				{
					String oggettoLink = " <a href='?q=object/detail&amp;p=Scadenza/_a_ID/_v_"+id_richiesta+"' title='consulta la metascadenza'>metascadenza relativa</a>";
					insertNotifica(date, oggetto+oggettoLink, descrizione+listaClienti+"</ul>", ID_FAKE_OPERATOR_METASCADENZE_DOUBLECHECK, null, id_richiesta);
				}


			}
		} catch (Exception e)
		{
			String msg = "Errore processando profilo dinamico. Attenzione: alcune scadenze sono state inserite!";
			logger.error(msg, e);
			throw new SerenaException(msg);
		}
	}

	/**
	 * prenotazione di scadenza per operatore: diretta, da pratica o da attività
	 * 
	 * @param aRequest
	 * @param operatore
	 * @param pratica
	 * @param attivita
	 */
	private void processOperatore(Element aRequest, Element operatore, Element pratica, Element attivita, boolean invio, boolean record, boolean allowsMultipleEntries) throws SerenaException
	{
		logger.info("Running MetascadenzeProcessor.processOperatore");
		try
		{
			GenerationCache g = new GenerationCache(new SerenaDate().toString());
			String id_richiesta = aRequest.elementText("ID");
			String data = aRequest.elementText(Scadenza.SLOT_DATA);
			// String preavviso =
			// aRequest.elementText(Scadenza.SLOT_PREAVVISO_GG);
			String oggetto = aRequest.elementText(Scadenza.SLOT_OGGETTO_SCADENZA);
			String descrizione = aRequest.elementText(Scadenza.SLOT_DESCRIZIONE_SCADENZA);
			String baseUrl = ModuleParameterConfiguration.getInstance().getValue("document", "DMS_URL");
			String link = "";
			String idOperatore = null;
			String oggettoRegistrazione = "";
			if (pratica != null)
			{
				String id_pratica = pratica.elementText("ID");
				String codice_pratica = pratica.elementText("titolo");
				link = LINK_PRATICA.replace(TAG_BASEURL, baseUrl);
				link = link.replace(TAG_ID, id_pratica);
				link = link.replace(TAG_CODICE, codice_pratica);
				oggetto = oggetto.replace(TAG_CODICE, codice_pratica);
				idOperatore = pratica.element("responsabile").element("Operatore").elementText("ID");
			} else if (attivita != null)
			{
				String id_attivita = attivita.elementText("ID");
				String codice_attivita = attivita.elementText("titolo");
				link = LINK_ATTIVITA.replace(TAG_BASEURL, baseUrl);
				link = link.replace(TAG_ID, id_attivita);
				link = link.replace(TAG_CODICE, codice_attivita);
				oggetto = oggetto.replace(TAG_CODICE, codice_attivita);
				idOperatore = attivita.element("assegnata_a").element("Operatore").elementText("ID");
			} else
			{
				idOperatore = operatore.elementText("ID");
			}
			logger.debug("processOperatore " + idOperatore + " on metascadenza " +id_richiesta);
			oggettoRegistrazione = oggetto.replace(TAG_DATA, link); // oggetto
																	// della
																	// registrazione:
																	// data
																	// inutile
																	// ... ma mi
																	// serve il
																	// link
			oggetto = oggetto.replace(TAG_DATA, data);
			String refDate = new SerenaDate().toString();
			if (invio)
			{
				if (!g.itemSent4Operator(idOperatore, id_richiesta,refDate))
				{
					g.assertItemSent4Operator(idOperatore, id_richiesta,refDate);
					MyOperatore questoOperatore = new MyOperatore().getInstance(idOperatore);
					descrizione += link;
					logger.debug("sending mail to  " +  questoOperatore.getPersonalEmail() + " on date " + refDate + " for metascadenza "+ id_richiesta);
					getMailSmsSender().sendMail(oggetto, descrizione, questoOperatore.getPersonalEmail()); // la mandiamo all'email personale, non di HG
				}
			}
			if (record)
			{
				// istanza di NotificaScadenza in bacheca
				if (allowsMultipleEntries || !g.itemStored4Operator(idOperatore, id_richiesta,refDate))
				{
					g.assertItemStored4Operator(idOperatore, id_richiesta,refDate);
					logger.debug("storing notifica for op  " +  idOperatore + " on date " + refDate + " for metascadenza "+ id_richiesta);
					insertNotifica(data, oggettoRegistrazione, descrizione, idOperatore, null, id_richiesta);
					insertNotifica(data, oggettoRegistrazione, descrizione, ID_FAKE_OPERATOR_METASCADENZE_DOUBLECHECK, null, id_richiesta);

				}
			}
		} catch (Exception e)
		{
			String msg = "processOperatore - errore processando richiesta: " + e.toString();
			logger.error(msg);
			throw new SerenaException(msg);
		}

	}

	/**
	 * prenotazione di scadenza per cliente: diretta, da pratica o da attività
	 * 
	 * @param aRequest
	 * @param cliente
	 * @param pratica
	 * @param attivita
	 */
	private void processCliente(Element aRequest, Element cliente, Element pratica, Element attivita, boolean invio, boolean record, boolean allowsMultipleEntries) throws SerenaException
	{
		logger.info("Running MetascadenzeProcessor.processCliente");
		try
		{
			GenerationCache g = new GenerationCache(new SerenaDate().toString());
			boolean forceSMS = new Boolean(aRequest.elementText(Scadenza.SLOT_FORZA_SMS));
			String id_richiesta = aRequest.elementText("ID");

			String data = aRequest.elementText(Scadenza.SLOT_DATA);
			// String preavviso =
			// aRequest.elementText(Scadenza.SLOT_PREAVVISO_GG);
			String oggetto = aRequest.elementText(Scadenza.SLOT_OGGETTO_SCADENZA);
			String descrizione = aRequest.elementText(Scadenza.SLOT_DESCRIZIONE_SCADENZA);
			String idCliente = null;
			if (pratica != null)
			{
				String codice_pratica = pratica.elementText("titolo");
				oggetto = oggetto.replace(TAG_CODICE, codice_pratica);
				idCliente = pratica.element("cliente_pratica").element("Cliente").elementText("ID");
			} else if (attivita != null)
			{
				String codice_attivita = attivita.elementText("titolo");
				oggetto = oggetto.replace(TAG_CODICE, codice_attivita);
				idCliente = attivita.element("inverse_of_attivita").element("Pratica").element("cliente_pratica").element("Cliente").elementText("ID");
			} else
			{
				idCliente = cliente.elementText("ID");
			}
			logger.debug("processCliente" + idCliente + " on metascadenza " +id_richiesta);
			oggetto = oggetto.replace(TAG_DATA, data);
			Cliente questoCliente = new Cliente().getInstance(idCliente);
			descrizione = descrizione.replace("@CLIENTE@", questoCliente.getNome());
			oggetto = oggetto.replace("@CLIENTE@", questoCliente.getNome());

			if (questoCliente == null)
			{
				logger.trace("Impossibile reperire cliente " + idCliente + ", probabilmente a causa di cessata assistenza.");
				return;
			}
			String refDate = new SerenaDate().toString();

			if (invio)
			{
				if (!g.itemSent4Client(idCliente, id_richiesta,refDate))
				{
					// non ancora spedito
					logger.debug("sending mail to  " +  questoCliente.getEmail() + " on date " + refDate + " for metascadenza "+ id_richiesta);
					g.assertItemSent4Client(idCliente, id_richiesta,refDate);
					// mail/fax/sms
					if (questoCliente.notificheViaSms() || forceSMS)
						getMailSmsSender().sendSms(oggetto, questoCliente);
					else if (questoCliente.notificheViaFax())
						getMailSmsSender().sendFax(oggetto, descrizione, questoCliente, null);
					else
						getMailSmsSender().sendMail(oggetto, descrizione, questoCliente);
					// aggiorna cache spedizioni
				}
			}
			if (record)
			{
				// istanza di NotificaScadenza in bacheca
				if (allowsMultipleEntries || !g.itemStored4Client(idCliente, id_richiesta,refDate))
				{
					logger.debug("storing notifica for op  " +  idCliente + " on date " + refDate + " for metascadenza "+ id_richiesta);
					g.assertItemStored4Client(idCliente, id_richiesta,refDate);
					insertNotifica(data, oggetto, descrizione, null, idCliente, id_richiesta);

					// double check:
					String listaClienti = "<br /><strong>Destinatari</strong>: "+questoCliente.getNome();
					String oggettoLink = " <a href='?q=object/detail&amp;p=Scadenza/_a_ID/_v_"+id_richiesta+"' title='consulta la metascadenza'>metascadenza relativa</a>";
					insertNotifica(data, oggetto+oggettoLink, descrizione+listaClienti+"</ul>", ID_FAKE_OPERATOR_METASCADENZE_DOUBLECHECK, null, id_richiesta);
				}
			}
		} catch (Exception e)
		{
			String msg = "processCliente - errore processando richiesta: " + e.toString();
			logger.error(msg);
			throw new SerenaException(msg);
		}
	}

	private void insertNotifica(String data, String oggetto, String testo, String operatore, String cliente, String richiesta) throws SerenaException
	{
		try	{
			NotificaScadenza theBean = new NotificaScadenza();
			theBean.setData(data);
			theBean.setOggetto(oggetto);
			theBean.setDescrizione(testo);
			theBean.setId_richiesta(richiesta);
			if (operatore != null)
				theBean.setId_operatore(operatore);
			if (cliente != null)
				theBean.setId_cliente(cliente);
			theBean.insert();
		} catch (Exception e){
			logger.warn("Notifica gia' presente (M-D-O-C): " + richiesta + " - " + data + " - "  + operatore + " - " +cliente);
		}
		}

	private void processRicorrenzaPerPrimiMesi(Element aReq) throws SerenaException
	{
		for (int i = 0; i < GENERATE_MONTHS_AHEAD; i++)
		{
			processRicorrenzaPerMese(i, aReq);
		}
	}

	private int getNumberOfDaysForMonthOfYear(int year, int month)
	{
		return new GregorianCalendar(year, month - 1, 1).getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	private void processRicorrenzaPerMese(int mese, Element metascad) throws SerenaException
	{
		try
		{
			Element aReq = (Element) metascad.clone();
			String data = aReq.elementText(Scadenza.SLOT_DATA);
			String ricorrenza = aReq.elementText(Scadenza.SLOT_RICORRENZA);
			SerenaDate fineRicorrenza = null;
			if (aReq.element(Scadenza.SLOT_FINE_RICORRENZA) != null && !aReq.element(Scadenza.SLOT_FINE_RICORRENZA).getText().isEmpty())
			{
				fineRicorrenza = new SerenaDate(aReq.elementText(Scadenza.SLOT_FINE_RICORRENZA));
			}

			// data dalla scadenza
			SerenaDate dataScadenza = new SerenaDate(data);
			int scadenzaDay = dataScadenza.getDay();
			int scadenzaDayOfweek = dataScadenza.getDayOfWeek();
			int scadenzaMonth = dataScadenza.getMonth();
			// data di oggi, meno il preavviso
			SerenaDate dataOggi = new SerenaDate();

			dataOggi.addMonths(mese);
			int monthDays = getNumberOfDaysForMonthOfYear(dataOggi.getYear(), dataOggi.getMonth());
			int loopFromDay = (mese == 0) ? dataOggi.getDay() : 1;

			for (int i = loopFromDay; i <= monthDays; i++)
			{
				String iStr = (i < 10) ? "0" + i : "" + i;
				String dataDaProcessareStr = iStr + "/" + dataOggi.getMonth() + "/" + dataOggi.getYear();
				SerenaDate dataDaProcessare = SerenaDate.parseDate(dataDaProcessareStr);
				if (fineRicorrenza == null || dataDaProcessare.isBefore(fineRicorrenza))
				{
					int dataOggiDayOfweek = dataDaProcessare.getDayOfWeek();
					int dataOggiMonth = dataDaProcessare.getMonth();
					int dataOggiDay = i;
					aReq.element(Scadenza.SLOT_DATA).setText(dataDaProcessare.toString());
					if (RICORRENZA_GIORNALIERA.equals(ricorrenza))
					{
						this.processRequest(aReq, false, true, true);
					} else if (RICORRENZA_SETTIMANALE.equals(ricorrenza))
					{
						if (dataOggiDayOfweek == scadenzaDayOfweek)
							this.processRequest(aReq, false, true, true);
					} else if (RICORRENZA_MENSILE.equals(ricorrenza))
					{
						if (dataOggiDay == scadenzaDay)
							this.processRequest(aReq, false, true, true);
						else if (isMeseDi30(dataOggiMonth) && dataOggiDay == 30 && scadenzaDay == 31)
							this.processRequest(aReq, false, true, true);
						else if (isMeseDi28(dataOggiMonth) && dataOggiDay == 28 && (scadenzaDay == 31 || scadenzaDay == 30))
							this.processRequest(aReq, false, true, true);
					} else if (RICORRENZA_TRIMESTRALE.equals(ricorrenza))
					{
						if (dataOggiDay == scadenzaDay && ((dataOggiMonth - scadenzaMonth) % 3) == 0)
							this.processRequest(aReq, false, true, true);
					} else if (RICORRENZA_QUADRIMESTRALE.equals(ricorrenza))
					{
						if (dataOggiDay == scadenzaDay && ((dataOggiMonth - scadenzaMonth) % 4) == 0)
							this.processRequest(aReq, false, true, true);
					} else if (RICORRENZA_SEMESTRALE.equals(ricorrenza))
					{
						if (dataOggiDay == scadenzaDay && ((dataOggiMonth - scadenzaMonth) % 6) == 0)
							this.processRequest(aReq, false, true, true);
					} else if (RICORRENZA_ANNUALE.equals(ricorrenza))
					{
						if (dataOggiDay == scadenzaDay && dataOggiMonth == scadenzaMonth)
							this.processRequest(aReq, false, true, true);
					} else
					{
						logger.error("Ricorrenza " + ricorrenza + " non gestita!");
					}
				}
			}
		} catch (Exception e)
		{
			String msg = "processOperatore - errore processando richiesta: " + e.toString();
			logger.error(msg);
			throw new SerenaException(msg, e);
		}
	}

	public void processMetascadenzaInsert(Element metascadenzaElem) throws SerenaException
	{
		metascadenzaElem = (Element) metascadenzaElem.selectSingleNode(".//" + Scadenza.INSTANCE_NAME);
		Scadenza metascadenzaObj = new TulliniHelpGestBinder().createScadenza(metascadenzaElem);
		if (metascadenzaObj.getRicorrenza() == null || metascadenzaObj.getRicorrenza().isEmpty())
		{
			this.processRequest(metascadenzaElem, false, true, true);
		} else
		{
			processRicorrenzaPerPrimiMesi(metascadenzaElem);
		}
	}

	/**
	 * cancellazione fisica di tutte le scasenze relative, anche antecedenti
	 * 
	 * @param metascadenzaElem
	 * @return
	 * @throws SerenaException
	 */
	public boolean processMetascadenzaDelete(String idMeta) throws SerenaException
	{
		return new NotificaScadenza().deleteForMetascadenza(idMeta);
	}

	public void processMetascadenzaUpdate(Element metascadenza) throws SerenaException
	{
		Element metascadenzaElem = (Element) metascadenza.selectSingleNode(".//" + Scadenza.INSTANCE_NAME);
		Scadenza metascadenzaObj = new TulliniHelpGestBinder().createScadenza(metascadenzaElem);
		if (processMetascadenzaDelete(metascadenzaObj.getId()))
		{
			processMetascadenzaInsert(metascadenza);
		} else
			throw new SerenaException("Cannot delete existing entries");
	}

}