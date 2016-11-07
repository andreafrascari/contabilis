package eu.anastasis.it.tullinidms.modules;


import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import eu.anastasis.serena.application.core.modules.DefaultMethod;
import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.dataManager.ObjectAlreadyExistsException;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.serena.presentation.templates.ActiveTemplate;
import eu.anastasis.serena.presentation.templates.TemplateFactory;


/**
 * Metodo che associa un documento ad un cliente (creando una StoriaDocumento)
 * @author afrascari
 *
 */
public class Associate2ClientMethod extends DefaultMethod
{

	private static final String METHOD_NAME = "associate2client";
	
	private static final Logger logger = Logger.getLogger(Associate2ClientMethod.class);
	
	public Associate2ClientMethod(DefaultModule parentModule, String[] defaultParameters) {
		super(parentModule, defaultParameters);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String doMethod(HttpServletRequest request,
			HttpServletResponse response) throws SerenaException {
		
		ActiveTemplate template = new ActiveTemplate(TemplateFactory.getTemplate(
				retrieveDefaultTemplateContext(),retrieveTemplateContext(), getName()));
		
		String ret = "";
		HashMap<String, String> dataHolder = new HashMap<String, String>();
		try	{
		
			// tutti i parametri di "input"
			for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements();) {
				String aParam = params.nextElement().toString();
				if (ObjectUtils.isInsertionAttribute(aParam)) {
					// insertion-parameter: adding node for class or attribute
					String clearParam = ObjectUtils.clearFromPrefix(aParam);
					dataHolder.put(clearParam, request.getParameter(aParam));
					}
				}
			
			String doc_ID = dataHolder.get("Documento_ID");
			String client_ID = dataHolder.get("cliente_doc");
			// oppure da get!!!!
			if (doc_ID==null && client_ID==null)	{
				doc_ID = request.getParameter("DOC_ID");
				client_ID = request.getParameter("CLI_ID");
			}
			if (doc_ID==null && client_ID==null)	{
				logger.error("Mancano identificativi di cliente e/o documento.");
				template.replaceTag("MSG",
						"Errore non gestito: si prega di contattare l'assistenza.");
				template.publish();
				return template.getContenuto();
			}
			StoriaDocumento unaStoria = new StoriaDocumento();
				
			unaStoria.setCliente_doc(client_ID);
							
			unaStoria.setDocumento(doc_ID);
			logger.trace("Recording StoriaDocumento");	
			// registrazione
			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(request.getSession().getId(), ConstantsXSerena.ACTION_SET, StoriaDocumento.MY_CLASS);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_INSERT);
			
			Element anItem = null;

			anItem = currentElement.addElement("cliente_doc");
			anItem.setText(unaStoria.getCliente_doc());
					
			anItem = currentElement.addElement("inverse_of_storia_documento");
			anItem.setText(unaStoria.getDocumento());
			try	{

				Document data = ApplicationLibrary.setData(currentElement.getDocument(),request);
				String[] messages2={"",""};
				int res = ConstantsXSerena.getXserenaRequestResult(data, messages2,StoriaDocumento.MY_CLASS);
				if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
				{
					ret +="Errore in fase di salvataggio del workflow";
					logger.error("cannot insert StoriaDocumento for client "+unaStoria.getCliente_doc()+ " due to  error:"+messages2[0]);
				}
				else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)	{
					ret +="Associazione avvenuta correttamente";
				}		
			} catch (ObjectAlreadyExistsException e)
			{
				logger.error("cannot insert StoriaDocumento for client "+unaStoria.getCliente_doc()+ " as it already exists");
				ret +="Associazione documento " + doc_ID + " con cliente " + client_ID + " gia presente nel sistema.";
			} catch (Exception e) {
				logger.error("cannot insert StoriaDocumento for client "+unaStoria.getCliente_doc()+ " due to  error:"+e.getMessage());
				ret +="Errore invece in fase di salvataggio del workflow";
			}
			ret += updateDocumento(request,doc_ID);	// ad uso e consumo di sottoclassi
			template.replaceTag("MSG", ret);
			template.replaceTag("DOC_ID", doc_ID);
			template.replaceTag("CLI_ID", client_ID);
			template.publish();
			return template.getContenuto();
		} catch (Exception e) {
			logger.error("Errore non gestito: " + e.getMessage());
			template.replaceTag("MSG",
					"Errore non gestito: si prega di contattare l'assistenza.");
			template.publish();
			return template.getContenuto();
		}
	}

	 
	/**
	 * qui non fa nulla
	 * @param request
	 * @param doc_ID
	 * @return
	 */
	protected String updateDocumento(HttpServletRequest request, String doc_ID) {
		return "";
	}

	@Override
	protected String getName()
	{
		return METHOD_NAME;
	}

	
	
}

