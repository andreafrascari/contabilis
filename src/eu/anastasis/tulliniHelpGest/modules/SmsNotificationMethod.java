package eu.anastasis.tulliniHelpGest.modules;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import eu.anastasis.serena.application.core.modules.DefaultMethod;
import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.serena.persistence.CostantiArchitettura;
import eu.anastasis.serena.persistence.PersistenceConfiguration;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Sms;

/**
 * registrazione notifica ricezione SMS: invocato da form target di mobyt
 * @author afrascari
 *
 */
public class SmsNotificationMethod extends DefaultMethod 
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SmsNotificationMethod.class);


	private static final String METHOD_NAME = "smsnotification";
	private static final String STATO_OK = "Delivered";
	private static final String STATO_FAILED_TPL = "Messaggio non consegnato: @TEXT@";
	private static final String DATETIME_FORMAT = "dd-MM-yyyy HH:mm:ss";
	

	public SmsNotificationMethod(DefaultModule parentModule,String[] defaultParameters)
	{
		super(parentModule, defaultParameters);
	}

	@Override
	protected String getName()
	{
		return METHOD_NAME;
	}

 	
	@SuppressWarnings("unchecked")
	@Override
	public String doMethod(HttpServletRequest request,HttpServletResponse response) throws SerenaException
	{
		String msg =""; 
		try	{
			String date = request.getParameter("date");
			String time = request.getParameter("time");
			String id = request.getParameter("act");
			String status = request.getParameter("status");
			
			msg = "Ricevuta notifica sms: "+ date +"*"+ time +"*"+id+"*"+status;
			logger.info(msg);
			
			if (status==null || status.isEmpty())
				status = STATO_OK;			

			if (date==null || time==null || id==null || status==null)	{
				msg = "Ricevuta notifica sms ma manca almeno un attributo";
				logger.error(msg);
				return msg;
			} 

			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(request.getSession().getId(), ConstantsXSerena.ACTION_SET,Sms.INSTANCE_NAME);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_UPDATE);
			Element condition = currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
			condition = condition.addElement("ID");
			condition.setText(id);
			if (status.indexOf(STATO_OK)==0)	{
				// messaggio consegnato!
				currentElement = currentElement.addElement(Sms.SLOT_ORA_RICEZIONE);
				String dt = date + " "+time;	
				SimpleDateFormat myDateFormat = new SimpleDateFormat(DATETIME_FORMAT);
				final Date myDate = myDateFormat.parse(dt);
				myDateFormat = new SimpleDateFormat(PersistenceConfiguration.GetInstance().getParametro(CostantiArchitettura.INTERFACE_DATETIME_FORMAT));
				dt = myDateFormat.format(myDate);
				currentElement.setText(dt);
			} else {
				currentElement = currentElement.addElement(Sms.SLOT_NOTE);
				String failed = STATO_FAILED_TPL.replace("@TEXT@", status);
				currentElement.setText(failed);					
			}				
			Document data = ApplicationLibrary.setData(currentElement.getDocument(),request);
			String[] messages2={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages2,Sms.INSTANCE_NAME);
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				msg = "cannot record sms notification: "+messages2[0];
				logger.error(msg);
				return msg;
			}			
			return "OK";
		} catch (Exception e) {
			msg ="cannot record sms notification"+e.getMessage();
			logger.error(msg);
			return msg;
		}	
	}
	 
}
