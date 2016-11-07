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
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.MyOperatore;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Operatore;

/**
 * Gestore della funzione GET_URL_PARAM per ricavare un parametro dall'URL
 * inserita dal client Es.: &#128012;FUN_GET_URL_PARAM(param=a,
 * pre=_a_ID/_c__system_cms_node/_a_ID/_v_)@
 * 
 * @author mcarnazzo
 */
public class OperatoreCorrenteFunction extends DefaultFunction
{
 
	private final static String FUNCTION_NAME = "FUN_OPERATORE_CORRENTE";

	private static final Logger logger = Logger.getLogger(OperatoreCorrenteFunction.class);

	@Override
	public String getFunctionName()
	{
		return FUNCTION_NAME;
	}

	@Override
	public String doMethod(HttpServletRequest request, Map<String, String> params) throws SerenaException
	{
		try {
			
			Operatore current = new MyOperatore().getInstanceFromAccount(ApplicationLibrary.retrieveCurrentUser(request).getId());
			if (current!=null){
				return current.getId();
			} else {return "-1";}
			
		} catch (Exception e) {
			String message ="impossibile reperire operatore corrente " + e.getMessage(); 
			logger.error(message);
			throw new SerenaException(message);
		}
	}
}
