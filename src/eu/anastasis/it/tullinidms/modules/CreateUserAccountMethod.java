package eu.anastasis.it.tullinidms.modules;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import eu.anastasis.serena.application.core.modules.DefaultMethod;
import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.index.util.ModuleParameterConfiguration;
import eu.anastasis.serena.application.modules.object.ObjectMethod;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.application.modules.userRegistration.PasswordGenerator;
import eu.anastasis.serena.constants.ConstantsEntityBean;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.serena.presentation.templates.ActiveTemplate;
import eu.anastasis.serena.presentation.templates.TemplateFactory;


/**
 * Metodo che gestisce il post processing di un documento caricato, consistente nell'invio a chi di dovere
 * di mail/sms
 * @author afrascari
 *
 */
public class CreateUserAccountMethod extends DefaultMethod
{

	private static final String METHOD_NAME = "createuseraccount";
	
	private static final String ClienteClass= Cliente.MY_CLASS;
	private static final String UserClass= "_system_user";
	private static final String IDPARAM= "ID";	
	private static final Logger logger = Logger.getLogger(ObjectMethod.class);
	
	public CreateUserAccountMethod(DefaultModule parentModule, String[] defaultParameters) {
		super(parentModule, defaultParameters);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String doMethod(HttpServletRequest request,
			HttpServletResponse response) throws SerenaException {
		
		ActiveTemplate template = new ActiveTemplate(TemplateFactory.getTemplate(
				retrieveDefaultTemplateContext(),retrieveTemplateContext(), ClienteClass)); 
		
		String idCliente = request.getParameter(IDPARAM);

		if (idCliente==null){
			template.replaceTagInBlock(ObjectMethod.TPL_ERROR_MESSAGE, ObjectMethod.TPL_ERROR_BLOCK, "Specificare ID Cliente");
			template.publishBlock(ObjectMethod.TPL_ERROR_BLOCK);
			template.publish();
			return template.getContenuto();
		}
		
		Cliente theClient = new Cliente().getInstance(idCliente);

		try{
			String username=this.getUsername(theClient);
			String pwd=PasswordGenerator.createPassword(); 
			String sF = this.getSecretPhrase(theClient);
			String sA = this.getSecretAnswer(theClient);
			
			
			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(request.getSession().getId(), ConstantsXSerena.ACTION_SET, ClienteClass);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_UPDATE);
			
			Element anItem = currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
			anItem = anItem.addElement(ConstantsEntityBean.ID);
			anItem.setText(idCliente);
			
			currentElement = currentElement.addElement("account");
			currentElement = currentElement.addElement(UserClass);		
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_INSERT);

			
			anItem = currentElement.addElement("user_user_id");
			anItem.setText(username);
			
			anItem = currentElement.addElement("user_password");
			anItem.setText(pwd);
			
			anItem = currentElement.addElement("user_email");
			anItem.setText(theClient.getEmail());
			
			anItem = currentElement.addElement("user_name");
			anItem.setText(theClient.getNome());
			
			anItem = currentElement.addElement("creation_user");
			anItem.setText(this.getUsername(theClient));
			
			anItem = currentElement.addElement("activation_flag");
			anItem.setText("0");

			
			anItem = currentElement.addElement("user_sercret_pharse");
			anItem.setText(sF);
			
			anItem = currentElement.addElement("user_secret_answer");
			anItem.setText(sA);
	
			currentElement = currentElement.addElement("user_groups");
			currentElement = currentElement.addElement("_system_group");		
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_INSERT);
			currentElement = currentElement.addElement("ID");
			currentElement.setText("3");
			
			String[] messages={"",""};
			Document resultData=ApplicationLibrary.setData(currentElement.getDocument(),request);
			Element data = ApplicationLibrary.prepareDataForPresentation(resultData);
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages, ClienteClass);
	
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("cannot create account for client " +idCliente+ ": "+ messages[0]);
				throw new SerenaException(messages[0],"generic_sql_error");
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)
			{
				try	{
					String msg =notifyClient(username,pwd,sF,sA,theClient); 
					logger.trace(msg);
					template.replaceTagInBlock("MESSAGE", ObjectMethod.TPL_SUCCESS_BLOCK, msg);
					template.publishBlock(ObjectMethod.TPL_SUCCESS_BLOCK);
				} catch (SerenaException e) {
					String msg ="Account " + this.getUsername(theClient) + " creata correttamente ma notifica fallita: "+e.getMessage()+". Controllare e correggere recapiti mail/sms del cliente ed effettuare la comunicazione manualmente. Non ricreare l'account!"; 
					logger.error(msg);  
					template.replaceTagInBlock("MESSAGE", "CONDITIONAL_SUCCESS", msg);
					template.publishBlock("CONDITIONAL_SUCCESS");
				}
			}
		} catch (Exception e) {
			logger.error("cannot create account for client " +idCliente+ ": "+ e.toString());
			throw new SerenaException("");
		}

		
		template.publish();
		return template.getContenuto();
	}

	


	private String notifyClient(String username, String pwd, String sF,
			String sA, Cliente theClient) throws SerenaException {
		if (theClient.notificheViaSms()){
			String testo=ModuleParameterConfiguration.getInstance().getValue("document","ACTIVATION_SMS");	
			testo = testo.replace("@NOME@",theClient.getNome());
			testo = testo.replace("@USERNAME@",username);
			testo = testo.replace("@PASSWORD@",pwd);
			testo = testo.replace("@SECRET_PHRASE@",sF);
			testo = testo.replace("@SECRET_ANSWER@",sA);
			String res = new MailAndSmsSender().sendSmsAttivazione(theClient, testo);
			if (res!=null)
				throw new SerenaException(res);
			else return "Account "+username+ " creata correttamente. Notifica spedita a "+theClient.getCellulare();				
		} else {
			String testo=ModuleParameterConfiguration.getInstance().getValue("document","ACTIVATION_MAIL");		
			String oggetto=ModuleParameterConfiguration.getInstance().getValue("document","ACTIVATION_MAIL_OBJECT");
			testo = testo.replace("@NOME@",theClient.getNome());
			testo = testo.replace("@USERNAME@",username);
			testo = testo.replace("@PASSWORD@",pwd);
			testo = testo.replace("@SECRET_PHRASE@",sF);
			testo = testo.replace("@SECRET_ANSWER@",sA);
			String res = new MailAndSmsSender().sendMailAttivazione(theClient, testo, oggetto);
			if (res!=null)
				throw new SerenaException(res);
			else return "Account "+username+ " creata correttamente. Notifica spedita a "+theClient.getEmail();				
		}	
	}

	private String getSecretAnswer(Cliente theClient) {
		return theClient.getCodicefiscale();
	}

	private String getSecretPhrase(Cliente theClient) {
		return "Il mio codice fiscale?";
	}

	private String getUsername(Cliente theClient) {
		return theClient.getCodicecliente_clean();
	}

	@Override
	protected String getName()
	{
		return METHOD_NAME;
	}

	
	
}

