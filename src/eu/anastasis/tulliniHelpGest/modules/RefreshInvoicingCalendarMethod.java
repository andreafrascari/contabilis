package eu.anastasis.tulliniHelpGest.modules;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import eu.anastasis.serena.application.core.modules.DefaultMethod;
import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.tulliniHelpGest.tasks.FillInvoicingCalendar;
 
import org.apache.log4j.Logger;


/**
 * @author afrascari
 *
 */
public class RefreshInvoicingCalendarMethod extends DefaultMethod
{

	private static final String METHOD_NAME = "refreshinvoicingcalendar";
	
	private static final Logger logger = Logger.getLogger(RefreshInvoicingCalendarMethod.class);
	
	public RefreshInvoicingCalendarMethod(DefaultModule parentModule, String[] defaultParameters) {
		super(parentModule, defaultParameters);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String doMethod(HttpServletRequest request,
			HttpServletResponse response) throws SerenaException {
	
		FillInvoicingCalendar therunner = new FillInvoicingCalendar();
		therunner.run();
		logger.trace("Invoicing calendar has been refreshed");
		return therunner.getMessage();
	}


	@Override
	protected String getName()
	{
		return METHOD_NAME;
	}

	
	
}

