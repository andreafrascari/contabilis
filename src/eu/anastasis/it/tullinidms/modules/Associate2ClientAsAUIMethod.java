package eu.anastasis.it.tullinidms.modules;


import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.constants.ConstantsXSerena;


/**
 * Metodo che associa un documento ad un cliente (creando una StoriaDocumento)
 * @author afrascari
 *
 */
public class Associate2ClientAsAUIMethod extends Associate2ClientMethod
{

	private static final String METHOD_NAME = "associate2clientAsAUI";
	
	private static final Logger logger = Logger.getLogger(Associate2ClientAsAUIMethod.class);
	
	public Associate2ClientAsAUIMethod(DefaultModule parentModule, String[] defaultParameters) {
		super(parentModule, defaultParameters);
		// TODO Auto-generated constructor stub
	}


	 
	/**
	 * Marca il doc con tag AUI
	 * @param request
	 * @param doc_ID
	 * @return
	 */
	protected String updateDocumento(HttpServletRequest request, String doc_ID) {
		logger.trace("Updateing doc as AUI StoriaDocumento");		
		String ret = "";
		// registrazione
		Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(request.getSession().getId(), ConstantsXSerena.ACTION_SET, Documento.MY_CLASS);
		currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_UPDATE);
		
		Element anItem = null;

		anItem = currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
		anItem = anItem.addElement("ID");
		anItem.setText(doc_ID);
		anItem = currentElement.addElement("free_tag");
		anItem.setText(Documento.AUI_TAG);
				
		try	{

			Document data = ApplicationLibrary.setData(currentElement.getDocument(),request);
			String[] messages2={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages2,Documento.MY_CLASS);
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				ret +="Errore in fase update documento";
				logger.error("cannot update Documento "+doc_ID+ " due to  error:"+messages2[0]);
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)	{
				ret +="Documento taggato come "+Documento.AUI_TAG;
			}		
		}  catch (Exception e) {
			logger.error("cannot update Documento "+doc_ID+ " due to  error:"+e.getMessage());
			ret +="Errore invece in fase tagging del documento come "+Documento.AUI_TAG;
		}
		return "<br />"+ret;
	}

	@Override
	protected String getName()
	{
		return METHOD_NAME;
	}

	
	
}

