package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import eu.anastasis.serena.application.core.tasks.CronPersistenceHome;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.index.util.ModuleParameterConfiguration;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.auth.User;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.tulliniHelpGest.helpDesk.TicketModule;

public class MyOperatore extends Operatore {
	
	private static final Logger logger = Logger.getLogger(MyOperatore.class);
	private static HashMap<String,MyOperatore> MYCACHE = null;
	
	
	private String accountId = null;
	private String accountCreationUser = null;
	private String personalEmail = null;
	
	
	public String getPersonalEmail() {
		return personalEmail;
	}

	public void setPersonalEmail(String personalEmail) {
		this.personalEmail = personalEmail;
	}

	public String getAccountCreationUser() {
		return accountCreationUser;
	}

	public void setAccountCreationUser(String accountCreationUser) {
		this.accountCreationUser = accountCreationUser;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String userId) {
		this.accountId = userId;
	}

	public MyOperatore getInstance(String ID) throws SerenaException {
		if (MYCACHE==null)
			loadCache();
		return MYCACHE.get(ID);
	}
	
	public  Collection<MyOperatore> allInstances()	throws SerenaException {
		if (MYCACHE==null)
			loadCache();
		return MYCACHE.values();
	}
	 
	
	public void reasetCache() throws SerenaException {
		loadCache();
	}

	public void loadCache() throws SerenaException {
		// TODO Auto-generated method stub
	try	{
		MYCACHE = new HashMap<String, MyOperatore>();
		
		logger.trace("Loading cache operatori...");
		Element currentElement = ObjectUtils.getXserenaRequestStandardHeader("opcache", ConstantsXSerena.ACTION_GET, INSTANCE_NAME);
		currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_SELECT);
		currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET, ConstantsXSerena.TARGET_SPECIFIED);
		// currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET_LEVELS, "0");
		currentElement.addElement("ID");
		currentElement.addElement(Operatore.SLOT_NOME_E_COGNOME);
		currentElement.addElement(Operatore.SLOT_CELLULARE);
		currentElement.addElement(Operatore.SLOT_EMAIL);
		currentElement.addElement(Operatore.SLOT_COSTO_ORARIO);
		currentElement.addElement(Operatore.SLOT_RIVENDITA_ORARIA);
		currentElement = currentElement.addElement("account_operatore");
		currentElement = currentElement.addElement("_system_user");
		currentElement.addElement("ID");
		currentElement.addElement("creation_user");
		currentElement.addElement("user_email");
		
		String[] messages={"",""};
		Document resultData=new CronPersistenceHome().get(currentElement.getDocument());
		Element data = ApplicationLibrary.prepareDataForPresentation(resultData);
		int res = ConstantsXSerena.getXserenaRequestResult(data, messages,INSTANCE_NAME);

		if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
		{
			logger.error("Cannot startup OperatorCache"+messages[0] );
			throw new SerenaException(messages[0],"generic_sql_error");
		}
		else if (res==ConstantsXSerena.XSERENA_RESULT_EMPTY)
		{
			logger.error("Cannot startup OperatorCache: NO OPERATOR FOUND");
			throw new SerenaException("nessun operatore");
		}
		else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)
		{
			List<Element> gliOperatori= data.selectNodes(INSTANCE_NAME);
			for (Element unOperatore: gliOperatori)	{
				MyOperatore unOperatoreObj = new MyOperatore();
				String theID = unOperatore.elementText("ID");
				unOperatoreObj.setId(theID);
				unOperatoreObj.setNome_e_cognome(unOperatore.elementText(Operatore.SLOT_NOME_E_COGNOME));
				if (unOperatore.element(Operatore.SLOT_EMAIL)!=null)	{
					unOperatoreObj.setEmail(unOperatore.elementText(Operatore.SLOT_EMAIL));
				}
				if (unOperatore.element(Operatore.SLOT_CELLULARE)!=null)
					unOperatoreObj.setCellulare(unOperatore.elementText(Operatore.SLOT_CELLULARE));			
				if (unOperatore.element(Operatore.SLOT_COSTO_ORARIO)!=null)
				{
					try
					{
						unOperatoreObj.setCosto_orario(new Float(unOperatore.elementText(Operatore.SLOT_COSTO_ORARIO)));
					} catch (NumberFormatException dontCare)
					{
						// Non verra' impostato
					}
				}
				if (unOperatore.element(Operatore.SLOT_RIVENDITA_ORARIA)!=null)
				{
					try
					{
						unOperatoreObj.setRivendita_oraria(new Float(unOperatore.elementText(Operatore.SLOT_RIVENDITA_ORARIA)));
					} catch (NumberFormatException dontCare)
					{
						// Non verra' impostato
					}
				}
				if (unOperatore.element("account_operatore").element("_system_user")!=null)	{						
					unOperatoreObj.setAccountId(unOperatore.element("account_operatore").element("_system_user").elementText("ID"));
					unOperatoreObj.setAccountCreationUser(unOperatore.element("account_operatore").element("_system_user").elementText("creation_user"));
					unOperatoreObj.setPersonalEmail(unOperatore.element("account_operatore").element("_system_user").elementText("user_email"));
				}
				MYCACHE.put(theID, unOperatoreObj);				
			}			
		}
		logger.trace("DONE!");
	} catch  (Exception any)	{
		String msg = "Could not load operator cache: "+any.getMessage();
		logger.error(msg);
		throw new SerenaException(msg);
		}
	}
	
	/**
	 * Torna l'Operatore corrispondente allo user con ID passata
	 * @param userID
	 * @return
	 * @throws SerenaException
	 */
	public MyOperatore getInstanceFromAccount(String userID) throws SerenaException {
		if (MYCACHE==null)
			loadCache();
		for (MyOperatore o:MYCACHE.values())	{
			 if (userID.equals(o.getAccountId()))
				return o;
		}
		return null;
	}
	
	public MyOperatore getInstanceFromEmail(String email) throws SerenaException {
		logger.debug("getInstanceFromEmail: "+email);
		if (email==null)	{
			return null;
		}
		if (MYCACHE==null)
			loadCache();
		for (MyOperatore o:MYCACHE.values())	{
			 if (email.equals(o.getEmail()))	{
				 logger.debug("retuning : "+o.getNome_e_cognome());
				return o;
			 }
		}
		return null;
	}
	
	/*
	 * Torna true se l'email dell'operatore appartiene alle email "collettive" (da configurazione)
	 */
	public boolean accessCollectiveEmails() throws SerenaException {
		return accessCollectiveEmails(this.email);
	}
	
	/*
	 * Torna true se l'indirizzo mail passato appartiene alle email "collettive" (da configurazione)
	 */
	public boolean accessCollectiveEmails(String anEmail) throws SerenaException {
		String mailCOllettive = ModuleParameterConfiguration.getInstance().getValue(TicketModule.MODULE_NAME, TicketModule.PARAM_HD_MAIL_COLLETTIVE);
		if (mailCOllettive!=null && !mailCOllettive.isEmpty())	{
			String[] mails = mailCOllettive.split(",");
			for (String aM:mails)	{
				aM = aM.trim();
				if (anEmail.contains(aM))	{
					return true;
				}
			}
		}
		return false;
	}
	

	
	public MyOperatore getInstanceSessionAccount(HttpServletRequest request) throws SerenaException {
		User loggedUser = ApplicationLibrary.retrieveCurrentUser(request);
		return getInstanceFromAccount(loggedUser.getId());
	}
	
}
