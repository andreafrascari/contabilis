/**
 * 
 */
package eu.anastasis.serena.webservices;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import eu.anastasis.serena.application.core.tasks.CronPersistenceHome;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.common.SerenaDate;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.serena.persistence.CostantiArchitettura;
import eu.anastasis.serena.persistence.PersistenceConfiguration;
import eu.anastasis.tulliniHelpGest.modules.ActivityCacheAjaxMethod;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Attivita;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.MyOperatore;

/**
 * Webservice usato dal Document Agent
 * @author mcarnazzo
 */
public class HelpGestWebService extends PersistenceWebService
{
	
	private static final Logger logger = Logger.getLogger(HelpGestWebService.class);
	
	/**
	 * Tassametro:richiesta ultime attività da proporre all'utente
	 * @param userID
	 * @throws Exception
	 */
	public WsActivity[] getActivitiesForUser(String userID) throws Exception
	{
		ActivityCacheAjaxMethod theCache = new ActivityCacheAjaxMethod();
		List<Attivita> activities = theCache.getQueueForUserId(userID);
		
		WsActivity[] ret = null;
		if (activities != null)
		{
			ret = new WsActivity[activities.size()];
			for (int i = 0; i < activities.size(); i++)
			{
				Attivita activity = activities.get(i);
				ret[i] = new WsActivity(activity.getId(), activity.getTitolo());
			}
		}
		return ret;
	}
	
	/**
	 * Tassametro: registrazione sessione di attività
	 * @param userID (ID del _system_user!!!!!)
	 * @param activityId
	 * @param minutes
	 * @param diaryEntry
	 * @return null = OK; string di errore = errore
	 * @throws Exception
	 */
	public String recordSessionForActivity(String userID, String activityId, String minutes, String diaryEntry) throws Exception
	{
		
		if (userID==null || activityId==null ||  minutes==null|| diaryEntry==null)	{
			String msg = "Impossibile effettuare la registrazione - tutti i dati devono essere valorizzati: diario, minuti di lavoro (oltre a operatore e attivita)";
			logger.error(msg);
			return msg;
		}
		if (userID.isEmpty() || activityId.isEmpty() ||  minutes.isEmpty()|| diaryEntry.isEmpty())	{
			String msg = "Impossibile effettuare la registrazione - tutti i dati devono essere valorizzati: diario, minuti di lavoro (oltre a operatore e attivita)";
			logger.error(msg);
			return msg;
		}
		
		String sysUser = null; 
		Collection<MyOperatore> users = new MyOperatore().allInstances();
		List<WsUser> ret = new ArrayList<WsUser>();
		Iterator<MyOperatore> iterator = users.iterator();
		while (iterator.hasNext())
		{
			MyOperatore user = iterator.next();
			if ( user.getAccountId()!=null && user.getAccountId().equals(userID))	{
				sysUser = user.getAccountCreationUser();
				break;
			}
		}

		logger.trace("WS recordSessionForActivity from " + userID + " for act ID "+activityId+ ": sysuser is "+sysUser);
		
		String classe_sessione_attivita="LavoroSuAttivita";
		
		Element currentElement = ObjectUtils.getXserenaRequestStandardHeader("recordActivitySession", ConstantsXSerena.ACTION_SET, classe_sessione_attivita);
		currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_INSERT);
		
		Element anItem = currentElement.addElement("inverse_of_sessioni_di_lavoro");
		anItem = anItem.addElement("Attivita");
		anItem = anItem.addElement("ID");
		anItem.setText(activityId);

		anItem = currentElement.addElement("durata_minuti");
		anItem.setText(minutes);
        
		anItem = currentElement.addElement("data");
		String now = new SerenaDate().getDateAsString(getDateTimeFormat());
		anItem.setText(now);
	     
	    
		anItem = currentElement.addElement("diario");
		anItem.setText(diaryEntry);

		if (sysUser==null)	{
			logger.fatal("recordSessionForActivity: " + userID + " non corrisponde a nessun operatore!");
		} else {
			anItem = currentElement.addElement("creation_user");
			anItem.setText(sysUser);
		}


		try	{

			Document data = new CronPersistenceHome().set(currentElement.getDocument());
			String[] messages2={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages2,classe_sessione_attivita);
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				String msg = "Errore in fase di salvataggio della sessione di lavoro";
				logger.error(msg+messages2[0]);
				return msg;
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)	{
				return null;
			}
		} catch (Exception e) {
			String msg = "Errore in fase di salvataggio della sessione di lavoro";
			logger.error(msg+e.getMessage());
			return msg;
		}
		return null;	//ok
	}
	
	/**
	 * Torna un array con i bean Operatore (tutti gli operatori dello studio)
	 * @return
	 * @throws Exception
	 */
	public WsUser[] getStudioUsers() throws Exception {
		Collection<MyOperatore> users = new MyOperatore().allInstances();
		List<WsUser> ret = new ArrayList<WsUser>();
		Iterator<MyOperatore> iterator = users.iterator();
		while (iterator.hasNext())
		{
			MyOperatore user = iterator.next();
			if (user.getAccountId()!=null){
				ret.add(new WsUser(user.getAccountId(), user.getNome_e_cognome()));
			}
		}
		return ret.toArray(new WsUser[ret.size()]);
	}
	
	private String getDateTimeFormat() {
		String date_time_format=null;
			try {
				date_time_format = PersistenceConfiguration.GetInstance().getParametro(CostantiArchitettura.INTERFACE_DATETIME_FORMAT);
			} catch (SerenaException e) {
				date_time_format = "dd/MM/yyyy HH:mm:ss";
			}
		return date_time_format; 
	}
	
	
}
