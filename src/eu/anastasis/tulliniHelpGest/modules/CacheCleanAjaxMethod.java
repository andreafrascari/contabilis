package eu.anastasis.tulliniHelpGest.modules;

import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import eu.anastasis.serena.application.core.modules.AjaxMethod;
import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.application.index.util.ModuleParameterConfiguration;
import eu.anastasis.serena.common.XMessage;
import eu.anastasis.serena.exception.SerenaException;
/**
 * Metodo ajax resettare la cache specificata
 * @author afrascari
 *
 */
public class CacheCleanAjaxMethod extends AjaxMethod 
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CacheCleanAjaxMethod.class);


	private static final String METHOD_NAME = "cacheclean";
	
	
	private static final String CACHE_MODULE_PARAM_CONFIG = "ModuleParameterConfiguration";

	
	public CacheCleanAjaxMethod(DefaultModule parentModule,String[] defaultParameters)
	{
		super(parentModule, defaultParameters);
	}

	@Override
	protected String getName()
	{
		return METHOD_NAME;
	}

	@Override
	public String doMethod(HttpServletRequest request,HttpServletResponse response) throws SerenaException
	{
		XMessage msg=null;
		String what=getMethodParameter(request,"cosa");
		
		try
		{
			msg=new XMessage("reply");
			String res = "";
			if (what.equals(CACHE_MODULE_PARAM_CONFIG))	{
				ModuleParameterConfiguration.reset();
				res = "Cache resettata!";
			} else {
				res = "Cache " + what + " sconosciuta....";
			}
			Element mp=DocumentHelper.createElement("cache");
			msg.add(mp);
			Element node = mp.addElement("res");
			node.setText(res);
		} catch (Exception e)
		{
			String errorMessage = "Impossibile resettare cache" + what;
			msg=XMessage.getMessaggioErrore(errorMessage);
			logger.error(errorMessage, e);
			return msg.asXML();
		}
		return msg.asXML();
	}

	 
	 
}
