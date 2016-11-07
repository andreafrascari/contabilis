/**
 * Questo file appartiene al progetto Sere-na - www.sere-na.it
 * 
 * @copyright Anastasis Soc. Coop. - www.anastasis.it
 */
package eu.anastasis.tulliniHelpGest.utils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import eu.anastasis.serena.common.SerenaDate;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.serena.presentation.functions.DefaultFunction;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.MyFattura;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.ProForma;

/**
 * Gestore della funzione GET_URL_PARAM per ricavare un parametro dall'URL
 * inserita dal client Es.: &#128012;FUN_GET_URL_PARAM(param=a,
 * pre=_a_ID/_c__system_cms_node/_a_ID/_v_)@
 * 
 * @author mcarnazzo
 */
public class NextFatturaFunction extends DefaultFunction
{
 
	private final static String FUNCTION_NAME = "FUN_NEXT_FATTURA";
	private final static String Competenza_Param = "competenza";

	@Override
	public String getFunctionName()
	{
		return FUNCTION_NAME;
	}

	@Override
	public String doMethod(HttpServletRequest request, Map<String, String> params) throws SerenaException
	{
		String anno_contabile = new Integer(new SerenaDate().getYear()).toString();
		final String competenza = retrieveParam(params, Competenza_Param , null);
		if (competenza==null)
			return MyFattura.getNextNumber(anno_contabile, request).toString();
		else if (competenza.equals(ProForma.COMPETENZA__CONTABILIS))
			return MyFattura.getNextNumberContabilis(anno_contabile, request).toString();
		else if (competenza.equals(ProForma.COMPETENZA__STUDIO))
			return MyFattura.getNextNumberStudio(anno_contabile, request).toString();
		else return "competenza non riconosciuta";
	}
}
