/**
 * Questo file appartiene al progetto Sere-na - www.sere-na.it
 * 
 * @copyright Anastasis Soc. Coop. - www.anastasis.it
 */
package eu.anastasis.tulliniHelpGest.utils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.auth.User;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.serena.presentation.functions.DefaultFunction;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Cliente;

/**
 * Gestore della funzione GET_URL_PARAM per ricavare un parametro dall'URL
 * inserita dal client Es.: &#128012;FUN_GET_URL_PARAM(param=a,
 * pre=_a_ID/_c__system_cms_node/_a_ID/_v_)@
 * 
 * @author mcarnazzo
 */
public class ClienteFromSysUserFunction extends DefaultFunction
{
 
	private final static String FUNCTION_NAME = "FUN_CLIENTE_FROM_SYS_USER";

	private static final Logger logger = Logger.getLogger(ClienteFromSysUserFunction.class);

	@Override
	public String getFunctionName()
	{
		return FUNCTION_NAME;
	}

	@Override
	public String doMethod(HttpServletRequest request, Map<String, String> params) throws SerenaException
	{
		User sessionUser =ApplicationLibrary.retrieveUser(request.getSession());
		
		try {
			
			
			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(request.getSession().getId(), ConstantsXSerena.ACTION_GET, Cliente.INSTANCE_NAME);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_SELECT);
			currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET_LEVELS, "0");
			
			Element cond=currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
			Element item = cond.addElement("account");
			item.setText(sessionUser.getId());
			
			Document data = ApplicationLibrary.getData(currentElement.getDocument(),request);
			
			Element dataElem = ApplicationLibrary.prepareDataForPresentation(data);
			String[] messages={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(dataElem, messages,Cliente.INSTANCE_NAME);
			
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				String message ="impossibile reperire cliente relativo ad sys user " + sessionUser.getName()+": "+messages[0]; 
				logger.error(message);
				throw new SerenaException(message);
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_EMPTY)
			{
				logger.error("impossibile reperire cliente relativo ad sys user " + sessionUser.getName()+": "+messages[0]);
				throw new SerenaException(messages[0],"generic_sql_error");
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)
			{
				return ((Element)dataElem.selectSingleNode(Cliente.INSTANCE_NAME)).elementText("ID");
			}
			else
				return null;
		} catch (Exception e) {
			String message ="impossibile reperire cliente relativo ad sys user " + sessionUser.getName(); 
			logger.error(message);
			throw new SerenaException(message);
		}
	}
}
