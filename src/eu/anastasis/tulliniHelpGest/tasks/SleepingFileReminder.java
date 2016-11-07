package eu.anastasis.tulliniHelpGest.tasks;

import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import eu.anastasis.serena.application.core.tasks.CronPersistenceHome;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.index.util.ModuleParameterConfiguration;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.auth.exceptions.PermissionException;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.dataManager.DataManagerException;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.serena.persistence.CostantiArchitettura;
import eu.anastasis.serena.persistence.PersistenceConfiguration;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Cliente;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.MyOperatore;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Operatore;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Pratica;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.TulliniHelpGestBinder;
import eu.anastasis.tulliniHelpGest.utils.MailAndSmsSender;


public class SleepingFileReminder implements Runnable 
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SleepingFileReminder.class);

	
	protected static String date_format=null;
	private static CronPersistenceHome cph = null;
	
	
	protected static String SleepingFileDataSource = "dormiente";
	
	protected static String TAG_CODICE="@CODICE@";
	protected static String TAG_BASEURL="@BASEURL@";
	protected static String TAG_ID="@ID@";
	protected static String ANTICIPO_SPESE = "ANTICIPO SPESE PER CLIENTI";
	protected static String LINK_PRATICA="<a href=\""+TAG_BASEURL+"?q=object/detail&p=Pratica/_a_ID/_v_"+TAG_ID+"\" title=\"vai alla pratica (se loggata/o)\">Vai alla pratica "+TAG_CODICE+" (se loggata/o nel sistema)</a>";
	
	public void run() 
	{
		try
		{
			/**
			 * Fetch delle scadenze di oggi (al netto del preavviso dinamico) per invio notifiche
			 */
			Element pratiche = this.getPraticheDormienti();
			TulliniHelpGestBinder binder = new TulliniHelpGestBinder();
			if (pratiche!=null)	{
				List<Element> requests= pratiche.selectNodes(".//"+Pratica.INSTANCE_NAME);
				for (Element element:requests)
				{
					Pratica p = binder.createPratica(element);
					processPratica(p);	
				}
			}
		} catch (SerenaException e)
		{
			logger.error(e.getMessage());
		}
		
	}
	
 

	/**
	 * Attention!!! It's static
	 * @return
	 */
	private CronPersistenceHome getMyCronPersistenceHome(){
		if (cph==null)
			cph = new CronPersistenceHome();
		return cph; 
	}

	private String getDateTimeFormat() {
		if (date_format==null)
			try {
				date_format = PersistenceConfiguration.GetInstance().getParametro(CostantiArchitettura.INTERFACE_DATE_FORMAT);
			} catch (SerenaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				date_format = "dd/MM/yyyy";
			}
		return date_format; 
	}

	/**
	 * Fetch pratiche dormienti (stato aperta, tipo ore o prestazione, ultima modifica + di 2 mesi fa)
	 * @return
	 * @throws SerenaException
	 */
	protected Element getPraticheDormienti() throws SerenaException	{
		try {
			logger.debug("[SleepingFileReminder]-> is running");
			
				Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(Pratica.INSTANCE_NAME, ConstantsXSerena.ACTION_GET, Pratica.INSTANCE_NAME);
				currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_SELECT);
				currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET, ConstantsXSerena.TARGET_ALL);
				currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET_LEVELS, "2");
				currentElement.addAttribute(ConstantsXSerena.ATTR_FORCED_DATASOURCE, SleepingFileDataSource);
				 
				// escluse le aniticipo spese!!!!
				currentElement = currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
				currentElement = currentElement.addElement(Pratica.SLOT_TITOLO);
				currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATOR, ConstantsXSerena.VAL_NOT_EQUAL);
				currentElement.setText(ANTICIPO_SPESE);
				
				Document data = getMyCronPersistenceHome().get(currentElement.getDocument());
				
				Element dataElem = ApplicationLibrary.prepareDataForPresentation(data);
				String[] messages={"",""};
				int res = ConstantsXSerena.getXserenaRequestResult(dataElem, messages,Pratica.INSTANCE_NAME);
				
				if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
				{
					logger.error("getPraticheDormienti has resulted into sql error:"+messages[0]);
					throw new SerenaException(messages[0],"generic_sql_error");
				}
				else if (res==ConstantsXSerena.XSERENA_RESULT_EMPTY)
				{
					return null;
				}
				else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)
				{
					return dataElem;
				}
				else
					return null;
			} catch (PermissionException e) {
				logger.error("getPraticheDormienti has resulted into sql error"+e.getMessage());
				throw new SerenaException(e.getMessage());
			} catch (DataManagerException e) {
				logger.error("getPraticheDormienti has resulted into sql error"+e.getMessage());
				throw new SerenaException(e.getMessage());
			}
		}
	
	 

	/**
	 * prenotazione di scadenza per operatore: diretta, da pratica o da attività
	 * @param aRequest
	 * @param operatore
	 * @param pratica
	 * @param attivita
	 */
	private void processPratica(Pratica p) {
		try	{
			String oggetto = "[DMS StudioTullini] - Reminder pratica aperta";
			Operatore operatoreResponsabile = p.getResponsabile();
			MyOperatore questoOperatore = new MyOperatore().getInstance(operatoreResponsabile.getId());
			Cliente cliente = p.getCliente_pratica();
			String nome = p.getTitolo();
			String id  = p.getId();
			String messaggio = "Ciao " + operatoreResponsabile.getNome_e_cognome() + "! Questo messaggio ti notifica che la pratica "+ nome + " del cliente " + cliente.getCliente() + " e' aperta e non ci stai lavorando da piu' di 2 mesi: ";
			String baseUrl = ModuleParameterConfiguration.getInstance().getValue("document","DMS_URL");
			String link = LINK_PRATICA.replace(TAG_BASEURL, baseUrl); 
			link = link.replace(TAG_ID, id);
			link = link.replace(TAG_CODICE, nome);
			messaggio+=link;
			messaggio+="<br />DMS StudioTullini";
			MailAndSmsSender sender = new MailAndSmsSender();
			boolean res = sender.sendMail(oggetto, messaggio, questoOperatore.getPersonalEmail()); // la mandiamo all'email personale, non di HG
			if (!res){
				String msg = "errore invio mail reminder operatore processando la pratica : "+p.toString();
				logger.error(msg);
			}
	} catch (Exception e) {
		String msg = "processPratica - errore processando la pratica : "+p.toString();
		logger.error(msg);
		}
	}
 	
 

}