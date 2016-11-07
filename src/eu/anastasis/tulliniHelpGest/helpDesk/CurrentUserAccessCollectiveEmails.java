package eu.anastasis.tulliniHelpGest.helpDesk;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.serena.presentation.functions.DefaultFunction;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.MyOperatore;
import eu.anastasis.tulliniHelpGest.utils.OperatoreCorrenteFunction;

public class CurrentUserAccessCollectiveEmails extends DefaultFunction {
	
	private final static String FUNCTION_NAME = "FUN_ACCESS_COLLECTIVE_EMAILS";

	private static final Logger logger = Logger.getLogger(OperatoreCorrenteFunction.class);

	@Override
	public String getFunctionName()
	{
		return FUNCTION_NAME;
	}

	@Override
	public String doMethod(HttpServletRequest request, Map<String, String> params) throws SerenaException
	{
		try {
			MyOperatore o = new MyOperatore().getInstanceSessionAccount(request);
			return o.accessCollectiveEmails()?"1":"0";
			
		} catch (Exception e) {
			String message ="impossibile reperire operatore corrente" + e.getMessage(); 
			logger.error(message);
			return "0";
		}
	}
}


