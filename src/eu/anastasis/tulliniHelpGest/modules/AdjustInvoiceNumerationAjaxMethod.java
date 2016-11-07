package eu.anastasis.tulliniHelpGest.modules;

import java.util.List;

import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import eu.anastasis.serena.application.core.modules.AjaxMethod;
import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.common.XMessage;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Fattura;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.ProForma;

/**
 * Metodo ajax per riallineare la numerazione delle fatture/proforma non spedite a partire da un certo numero
 * @author afrascari
 *
 */
public class AdjustInvoiceNumerationAjaxMethod extends AjaxMethod 
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AdjustInvoiceNumerationAjaxMethod.class);


	private static final String METHOD_NAME = "adjustinvoicenumeration";
	
	private static final String COMPETENZA_CONTABILIS= "contabilis";
	private static final String COMPETENZA_STUDIO= "studio";

	
	public AdjustInvoiceNumerationAjaxMethod(DefaultModule parentModule,String[] defaultParameters)
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
		String cosa=getMethodParameter(request,"cosa");
		String competenza=getMethodParameter(request,"competenza");
		String anno=getMethodParameter(request,"anno");
		competenza = (competenza.equals(COMPETENZA_CONTABILIS))?Fattura.COMPETENZA__CONTABILIS:Fattura.COMPETENZA__STUDIO;
		String numero=getMethodParameter(request,"numero");
		String adjusted = adjust(request,cosa,competenza,numero,anno);
		try
		{
			String messaggioOk = "Riallineamento "+cosa+" "+competenza+ " avvenuto correttamente su numeri: "+adjusted;
			msg=new XMessage("reply");
			Element mp=DocumentHelper.createElement(cosa);
			msg.add(mp);
			Element node = mp.addElement("esito");
			node.setText(messaggioOk);
		} catch (Exception e)
		{
			String messaggioErr = "Errore in riallineamento "+cosa+" "+competenza+ " avvenuto correttamente: "+e.getMessage();
			msg=XMessage.getMessaggioErrore(messaggioErr);
			logger.error(messaggioErr, e);
			return msg.asXML();
		}
		return msg.asXML();
	}

	/**
	 * Riallianea la numerazione le fatture/proforma sotraendo 1 a tutte quelle successive a "numero" 
	 * (quella che ha cambiato competenza) di stato "da validare"
	 * @param request
	 * @param classe
	 * @param competenza
	 * @param numero
	 * @throws SerenaException
	 * returns concatenation of numbers adjusted
	 */
	private String  adjust(HttpServletRequest request,  String classe, String competenza, String numero, String anno) throws SerenaException {
		String resStr = "";
		try	{
			/* can't do this as serena does not support updates like "numero = numero -1" (numero - 1 gets treated as string) 
			 * */ 
			/*
			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(request.getSession().getId(), ConstantsXSerena.ACTION_SET, classe);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_UPDATE);
			
			Element condition = currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
			Element item = condition.addElement(Fattura.SLOT_COMPETENZA);
			item.setText(competenza);
			item = condition.addElement(Fattura.SLOT_NUMERO);
			item.addAttribute(ConstantsXSerena.ATTR_OPERATOR, ConstantsXSerena.VAL_GREATER_THAN);
			item.setText(numero);
			if (ProForma.INSTANCE_NAME.equals(classe))	{
				item = condition.addElement(ProForma.SLOT_STATO_PROFORMA);
				item.setText(ProForma.STATO_PROFORMA__DA_VALIDARE);
			}
			item = currentElement.addElement(Fattura.SLOT_NUMERO);
			item.setText("numero -1");
			*/
			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(request.getSession().getId(), ConstantsXSerena.ACTION_GET, classe);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_SELECT);
			currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET_LEVELS, "0");
			currentElement.addAttribute(ConstantsXSerena.ATTR_ORDER_BY, Fattura.SLOT_NUMERO);
			
			Element condition = currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
			Element item = condition.addElement(Fattura.SLOT_COMPETENZA);
			item.setText(competenza);
			item = condition.addElement(Fattura.SLOT_ANNO_CONTABILE);
			item.setText(anno);
			item = condition.addElement(Fattura.SLOT_NUMERO);
			item.addAttribute(ConstantsXSerena.ATTR_OPERATOR, ConstantsXSerena.VAL_GREATER_THAN);
			item.setText(numero);
			if (ProForma.INSTANCE_NAME.equals(classe))	{
				item = condition.addElement(ProForma.SLOT_STATO_PROFORMA);
				item.setText(ProForma.STATO_PROFORMA__SPEDITA);
				item.addAttribute(ConstantsXSerena.ATTR_OPERATOR, ConstantsXSerena.VAL_NOT_EQUAL);
			}

			Document data = ApplicationLibrary.getData(currentElement.getDocument(),request);
			Element dataElem = ApplicationLibrary.prepareDataForPresentation(data);
			String[] messages={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(dataElem, messages,classe);
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("cannot adjust "+classe+": "+messages[0]);
				throw new SerenaException(messages[0]);
			}	else  if (res==ConstantsXSerena.XSERENA_RESULT_EMPTY)
			{ 
				return "";
			} else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)	{
				List<Element> itemList = dataElem.selectNodes(".//"+classe);
				for (Element instance:itemList){
					int intVal = new Integer(instance.elementText(Fattura.SLOT_NUMERO)).intValue();
					currentElement = ObjectUtils.getXserenaRequestStandardHeader(request.getSession().getId(), ConstantsXSerena.ACTION_SET, classe);
					currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_UPDATE);
					condition = currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
					item = condition.addElement("ID");
					item.setText(instance.elementText("ID"));
					item = currentElement.addElement(Fattura.SLOT_NUMERO);
					item.setText(new Integer(--intVal).toString());		
					data = ApplicationLibrary.setData(currentElement.getDocument(),request);
					String[] messages2={"",""};
					res = ConstantsXSerena.getXserenaRequestResult(data, messages2,classe);
					if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
					{
						logger.error("cannot adjust instance "+intVal+ " of "+classe+": "+messages2[0]);
						throw new SerenaException(messages2[0]);
					} else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)
					{ 
						logger.info("instance "+intVal+ " of "+classe+": has been adjusted");
						resStr +=intVal+";";
					};
				}
			}
			return resStr;
		} catch (Exception e) {
			logger.error("cannot adjust "+classe+": "+e.getMessage());
			throw new SerenaException(e.getMessage());
		}
		
	}
}
