package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.exception.SerenaException;

 
public class MyProForma extends ProForma {

	private static final Logger logger = Logger.getLogger(MyProForma.class);
	
	public MyProForma(String id)	{
		super();
		this.setId(id);
	}
	
	public static Integer getNextNumber(String anno_contabile, HttpServletRequest request) throws SerenaException {
		return getNextNumber("max-numero", anno_contabile, request);
	}
		
	public static Integer getNextNumberStudio(String anno_contabile, HttpServletRequest request) throws SerenaException {
		return getNextNumber("max-numero-studio", anno_contabile, request);
	}

	public static Integer getNextNumberContabilis(String anno_contabile, HttpServletRequest request) throws SerenaException {
		return getNextNumber("max-numero-contabilis", anno_contabile, request);
	}

	/**
	 * Torna il prossimo numero per l'anno contabile passato a parametro
	 * @param anno_contabile
	 * @return
	 */
	public static Integer getNextNumber(String view, String anno_contabile, HttpServletRequest request) throws SerenaException {
		try {
			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(request.getSession().getId(), ConstantsXSerena.ACTION_GET, INSTANCE_NAME);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_SELECT);
			currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET, ConstantsXSerena.TARGET_SPECIFIED);
			currentElement.addAttribute(ConstantsXSerena.ATTR_FORCED_DATASOURCE,view);
			currentElement.addElement(SLOT_NUMERO);
			currentElement.addElement(SLOT_DATA);
			Element condition = currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
			condition = condition.addElement(SLOT_NOTE);
			condition.setText(anno_contabile);
			Document data = ApplicationLibrary.getData(currentElement.getDocument(),request);
			
			Element dataElem = ApplicationLibrary.prepareDataForPresentation(data); 
			String[] messages={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(dataElem, messages,INSTANCE_NAME);
			
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("getNextNumber has resulted into sql error:"+messages[0]);
				throw new SerenaException(messages[0],"generic_sql_error");
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_EMPTY)
			{
				return new Integer("1");
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)
			{
				Element theInstance = (Element)dataElem.selectSingleNode(INSTANCE_NAME);
				String currentId =theInstance.elementText(SLOT_NUMERO);
				if (currentId==null || currentId.isEmpty())
					return new Integer("1");
				else
					return new Integer((new Integer(currentId).intValue())+1);
			}
			else
				return null;
		} catch (Exception e) {
			String msg = "getNextNumber has resulted into sql error:"+e.getMessage();
			logger.error(msg);
			throw new SerenaException(msg);
		}
	}
	
	public void updatePagamento(String dataPagamento, HttpServletRequest request)	throws SerenaException	{
		try	{
			if (this.getId()==null)	{
				logger.error("updatePagamento non sa di quale proforma....");
				throw new SerenaException("Sono una proforma.... ma quale????");
			}
			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(request.getSession().getId(), ConstantsXSerena.ACTION_SET, INSTANCE_NAME);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_UPDATE);
			Element conElement =currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
			conElement =conElement.addElement("ID");
			conElement.setText(this.getId());
			currentElement =currentElement.addElement(SLOT_DATA_PAGAMENTO);
			currentElement.setText(dataPagamento);
			Document dataElem = ApplicationLibrary.setData(currentElement.getDocument(),request);
			
			String[] messages={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(dataElem, messages,INSTANCE_NAME);
			
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("updatePagamento has resulted into sql error:"+messages[0]);
				throw new SerenaException(messages[0],"generic_sql_error");
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)
			{
				;
			}
		} catch (Exception e) {
			logger.error("updatePagamento: ERRORE");
			throw new SerenaException("updatePagamento: ERRORE");
		} 
	}
	

}
