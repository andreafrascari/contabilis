package eu.anastasis.tulliniHelpGest.helpDesk;



import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.google.gson.Gson;

import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.application.core.modules.JSONMethod;
import eu.anastasis.serena.application.core.tasks.CronPersistenceHome;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.auth.User;
import eu.anastasis.serena.constants.ConstantsEntityBean;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.dataManager.DataManagerException;
import eu.anastasis.serena.exceptions.JSONException;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.HDChiamataTelefonica;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Operatore;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.TulliniHelpGestBinder;

/**
 * Metodo JSON per il recupero della durata dell'ultima chiamata al/dal numero locale
 * @author andrea
 *
 */
public class TelefonataCorrenteMethod extends JSONMethod
{

	private static final Logger logger = Logger.getLogger(TelefonataCorrenteMethod.class);

	private static HashMap<String, String>PhoneNumbersRegister = new HashMap<String, String>();

	
	public TelefonataCorrenteMethod(DefaultModule parentModule, String[] defaultParameters)
	{
		super(parentModule, defaultParameters);
		// TODO Auto-generated constructor stub
	}

	public static final String METHOD_NAME = "ultimaTelefonata";

	@Override
	protected String getName()
	{
		return METHOD_NAME;
	}

	@Override
	public String doMethod(HttpServletRequest request, HttpServletResponse response) throws JSONException
	{
		User loggedUser = ApplicationLibrary.retrieveCurrentUser(request);
		String tel = getPhoneNumberFor(loggedUser.getId());
		HDChiamataTelefonica theCall = PhoneCall.getCurrentCallFor(tel);
		if (theCall!=null)	{
			return new Gson().toJson(theCall);
		} else {
			throw new JSONException("Nessuna chiamata trovata!");
		}
	}
	
	/**
	 * Cahces and returns phone number for user
	 * @param iD
	 * @return
	 */
	private String getPhoneNumberFor(String iD) 	{
		if (PhoneNumbersRegister.containsKey(iD))
			return PhoneNumbersRegister.get(iD);
		try {
			String theClass = Operatore.INSTANCE_NAME;
			String theSession = "whoscallingfunction";
			Element query = ObjectUtils.getXserenaRequestStandardHeader(theSession, ConstantsXSerena.ACTION_GET,theClass);
			query.addAttribute(ConstantsXSerena.ATTR_TARGET, ConstantsXSerena.TARGET_ALL);
			query.addAttribute(ConstantsXSerena.ATTR_TARGET_LEVELS, "0");
			Element theCond = query.addElement(ConstantsXSerena.TAG_CONDITION);
			theCond = theCond.addElement("account_operatore");
			theCond = theCond.addElement("_system_user");
			theCond = theCond.addElement(ConstantsEntityBean.ID);
			theCond.setText(iD);
			Document data = new CronPersistenceHome().get(query.getDocument());
			
			Element dataElem = ApplicationLibrary.prepareDataForPresentation(data);
			String[] messages={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(dataElem, messages,theClass);
			
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("[WhosCallingFunction]->getPhoneNumberFromOperator cant fetch phone number for operator:"+iD);
				return null;
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_EMPTY)
			{
				logger.error("[WhosCallingFunction]->getPhoneNumberFromOperator cant fetch phone number for operator:"+iD);
				return null;
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)
			{
				Element theInstance = (Element)data.selectSingleNode(".//"+theClass);
				Operatore hdc= (Operatore)new TulliniHelpGestBinder().createOperatore(theInstance);
				PhoneNumbersRegister.put(iD, hdc.getCellulare());
				return hdc.getCellulare();
			}
			else
				return null;
			} catch (DataManagerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("[WhosCallingFunction]->getPhoneNumberFromOperator cant fetch phone number for operator:"+e.toString());
				return null;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("[WhosCallingFunction]->getPhoneNumberFromOperator cant fetch phone number for operator:"+e.toString());
				return null;
			}

	}


	
}	
 