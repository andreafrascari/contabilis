/**
 * 
 */
package eu.anastasis.it.tullinidms.modules;

import eu.anastasis.serena.application.modules.login.LoginModule;

/**
 * @author mcarnazzo
 *
 */
public class Login4DandLModule extends LoginModule
{

	public static final String MODULE_NAME = "login4dandl";

	@Override
	protected void setUpMethods()
	{
		super.setUpMethods();
		addMethod(new Login4DandLMethod(this, getDefaultParameters()));
	}
	
	
}
