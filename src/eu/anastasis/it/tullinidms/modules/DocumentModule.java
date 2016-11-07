package eu.anastasis.it.tullinidms.modules;

import eu.anastasis.serena.application.core.modules.DefaultModule;


public class DocumentModule extends DefaultModule {
	
	public static final String DEFAULT_MODULE_NAME= "document";
	
	@Override
	public String getDefaultName()
	{
		// TODO Auto-generated method stub
		return DEFAULT_MODULE_NAME;
	}
	
	@Override
	protected void setUpMethods()
	{
		addMethod(new UploadAndSendMethod(this,getDefaultParameters()));
		addMethod(new CreateUserAccountMethod(this,getDefaultParameters()));
		addMethod(new ResetClientsCacheMethod(this,getDefaultParameters()));
		addMethod(new ResetMetadocumentsCacheMethod(this,getDefaultParameters()));
		addMethod(new Associate2ClientMethod(this,getDefaultParameters()));
		addMethod(new Associate2ClientAsAUIMethod(this,getDefaultParameters()));
	}
	
}
