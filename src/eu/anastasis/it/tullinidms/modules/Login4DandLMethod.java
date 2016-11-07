/**
 * 
 */
package eu.anastasis.it.tullinidms.modules;

import javax.servlet.http.HttpServletRequest;

import eu.anastasis.it.tullinidms.ConstantsTulliniDMS;
import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.application.modules.login.LoginMethod;

/**
 * @author mcarnazzo
 *
 */
public class Login4DandLMethod extends LoginMethod
{
	/**
	 * @param parentModule
	 * @param defaultParameters
	 */
	public Login4DandLMethod(DefaultModule parentModule, String[] defaultParameters)
	{
		super(parentModule, defaultParameters);
	}

 
	protected String createTargetUrl(HttpServletRequest request, String metaEnvParam, String calls)
	{
		String docID = request.getParameter(ConstantsTulliniDMS.DandLK_DOCPAR);
		String userID = request.getParameter(ConstantsTulliniDMS.DandLK_USERPAR);
		String attachID = request.getParameter(ConstantsTulliniDMS.DandLK_ATTACHPAR);
		boolean forceLogon = (request.getParameter(ConstantsTulliniDMS.DandLK_FORCELOGONPAR)!=null);
		
		String ret = "DandL?q=get&";
		ret += ConstantsTulliniDMS.DandLK_DOCPAR + "=" + docID + "&";
		ret += ConstantsTulliniDMS.DandLK_USERPAR + "=" + userID + "&";
		ret += ConstantsTulliniDMS.DandLK_ATTACHPAR + "=" + attachID + "&";
		ret += ConstantsTulliniDMS.DandLK_FORCELOGONPAR + "=" + forceLogon;

		return ret;
	}
}
