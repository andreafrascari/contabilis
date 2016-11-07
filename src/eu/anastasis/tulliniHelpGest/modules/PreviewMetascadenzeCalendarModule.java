/**
 * Questo file appartiene al progetto Sere-na - www.sere-na.it
 * @copyright Anastasis Soc. Coop. - www.anastasis.it
 */
package eu.anastasis.tulliniHelpGest.modules;

import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.serena.presentation.templates.TemplateException;
import eu.anastasis.tulliniHelpGest.utils.MetascadenzeProcessor;

/**
 * Ridefinito per caricare oggetti notifiche per il solo operator ein sessione
 * Non legge più i paramteri da db tanto e tutto statico
 */
public class PreviewMetascadenzeCalendarModule extends OperatorCalendarModule 
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(PreviewMetascadenzeCalendarModule.class);

	private static final String MODULE_NAME = "previewMetascadenzeCalendar";
	

	@Override
	public String doMethod(String method, HttpServletRequest request, HttpServletResponse response) throws SerenaException
	{
		try
		{
			initDate(request);
			return this.getCalendar(request);
		} catch (TemplateException e)	
		{
			e.printStackTrace();
			return null;
		}
	}

	protected String getCalendarName() 
	{
		return "previewMetascadenzeCalendar";
	}

 	
	@Override
	public String getDefaultName() 
	{
		return MODULE_NAME;
	}
	
	protected String getIdOperatore(HttpServletRequest request) throws SerenaException
	{
		return MetascadenzeProcessor.ID_FAKE_OPERATOR_METASCADENZE_DOUBLECHECK;
	}

}
