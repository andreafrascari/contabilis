package eu.anastasis.tulliniHelpGest.helpDesk;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.serena.presentation.functions.DefaultFunction;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.HDThreadStep;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.HDTicket;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.LavoroSuAttivita;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.TulliniHelpGestBeanDataManager;
import eu.anastasis.tulliniHelpGest.utils.OperatoreCorrenteFunction;

public class TicketTotalMinutes extends DefaultFunction {
	
	private final static String FUNCTION_NAME = "FUN_TICKET_TOTAL_MINUTES";

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
			int min = 0;
			final String ticket = retrieveParam(params, "ID" , null);
			TulliniHelpGestBeanDataManager dm = new TulliniHelpGestBeanDataManager();
			HDTicket t = dm.getHDTicket(request, 4, ticket);
			for (HDThreadStep s: t.getThread())	{
				LavoroSuAttivita l = s.getContabilizzazione();
				if (l!=null){
					min+=l.getDurata_minuti().intValue();
				}
			}
			return new Integer(min).toString();
			
		} catch (Exception e) {
			String message ="impossibile reperire minutaggio ticket corrente " + e.getMessage(); 
			logger.error(message);
			return "0";
		}
	}
}


