package eu.anastasis.tulliniHelpGest.modules;


import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;


import eu.anastasis.serena.application.core.modules.AjaxMethod;
import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.index.util.ModuleParameterConfiguration;
import eu.anastasis.serena.auth.User;
import eu.anastasis.serena.common.XMessage;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.tulliniHelpGest.utils.MailAndSmsSender;

import org.apache.log4j.Logger;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


/**
 * Prenotazione di un attività pe ril tassametro
 * Si tratta di una coda FIFO di N elementi Attivita da "presentare" al tassametro
 * @author afrascari
 *
 */
public class ActivityNotificationAjaxMethod extends AjaxMethod
{

	private static final String METHOD_NAME = "notifyactivity";
	private static final String PAR_ID_ACTIVITY = "idatt";
	private static final String PAR_DESC_ACTIVITY = "nomeatt";
	private static final String PAR_NOTIFY_TO = "to";
	private static final String PAR_NOTIFY_EMAIL = "email";
	
	protected static String TAG_CODICE="@CODICE@";
	protected static String TAG_BASEURL="@BASEURL@";
	protected static String TAG_ID="@ID@";
	protected static String LINK_ATTIVITA="<a href=\""+TAG_BASEURL+"?q=object/detail&p=Attivita/_a_ID/_v_"+TAG_ID+"\" title=\"vai alla attivita (se loggata/o)\">Vai alla attivita  "+TAG_CODICE+" (se loggata/o nel sistema)</a>";
	
	private static final Logger logger = Logger.getLogger(ActivityNotificationAjaxMethod.class);
	
	public ActivityNotificationAjaxMethod(DefaultModule parentModule, String[] defaultParameters) {
		super(parentModule, defaultParameters);
		// TODO Auto-generated constructor stub
	}
	
	public ActivityNotificationAjaxMethod()	{
		super(null, null);
	}

	@Override
	public String doMethod(HttpServletRequest request,
			HttpServletResponse response) throws SerenaException {
		XMessage msg=null;
		// get from QS
		String idAttivita=getMethodParameter(request, PAR_ID_ACTIVITY);
		String nomeOperatore =getMethodParameter(request, PAR_NOTIFY_TO);
		String emailOperatore =getMethodParameter(request, PAR_NOTIFY_EMAIL);
		String nomeAttivita=getMethodParameter(request, PAR_DESC_ACTIVITY);
		
		try
		{
			msg=new XMessage("reply");
			Element mp=DocumentHelper.createElement("result");
			Element node = mp.addElement("message");
			msg.add(mp);
			// get from session
			User loggedUser = ApplicationLibrary.retrieveCurrentUser(request);
			String mittente = loggedUser.getUsername();
			String oggetto = "[DMS StudioTullini] Notifica attivita assegnata da un collega!";
			String messaggio = "Ciao " + nomeOperatore + "! Questo messaggio ti notifica che " + mittente + " ti ha assegnato l'attivita "+nomeAttivita+": ";
			String baseUrl = ModuleParameterConfiguration.getInstance().getValue("document","DMS_URL");
			String link = LINK_ATTIVITA.replace(TAG_BASEURL, baseUrl); 
			link = link.replace(TAG_ID, idAttivita);
			link = link.replace(TAG_CODICE, nomeAttivita);
			messaggio+=link;
			messaggio+="<br />DMS StudioTullini";
			boolean res = new MailAndSmsSender().sendMail(oggetto, messaggio, emailOperatore);
			if (!res){
				throw new SerenaException("mail operatore non presente o altro problema di invio");
			}
			node.setText("Notifica inviata ad operatore "+nomeOperatore);
		} catch (Exception e)
		{
			String errorMessage = "Impossibile inviare notifica operatore: "+e.getMessage();
			msg=XMessage.getMessaggioErrore(errorMessage);
			logger.error(errorMessage, e);
			return msg.asXML();
		}
		return msg.asXML();
	}


	@Override
	protected String getName()
	{
		return METHOD_NAME;
	}
	
	 
}

