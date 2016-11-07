package eu.anastasis.tulliniHelpGest.modules;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.anastasis.it.tullinidms.modules.StoriaDocumento;
import eu.anastasis.serena.application.core.modules.DefaultMethod;
import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.auth.exceptions.PermissionException;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.dataManager.DataManagerException;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.serena.persistence.CostantiArchitettura;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.CalendarioFatturazione;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Cliente;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;


/**
 * Reset calendario di fatturazione (su anno corrente) per un cliente
 * @author afrascari
 *
 */
public class ResetInvoicingCalendarMethod4Client extends DefaultMethod
{

	private static final String METHOD_NAME = "resetinvoicingcalendar4client";
	
	private static final Logger logger = Logger.getLogger(ResetInvoicingCalendarMethod4Client.class);
	
	public ResetInvoicingCalendarMethod4Client(DefaultModule parentModule, String[] defaultParameters) {
		super(parentModule, defaultParameters);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String doMethod(HttpServletRequest request,
			HttpServletResponse response) throws SerenaException {
		String res = "?";
		try	{
			String id_cliente = request.getParameter("ID");
			res = (removeEntries(request, id_cliente))?"OK":"ERRORE";
		} catch (Exception e) {
			res = "ERRORE";
		}
		return res;
	}

	protected boolean removeEntries(HttpServletRequest request, String IDcliente) throws SerenaException	{
		try {
			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(request.getSession().getId(), ConstantsXSerena.ACTION_SET, CalendarioFatturazione.INSTANCE_NAME);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_DELETE);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATOR, CostantiArchitettura.DELETE_PHYSICAL);
			
			currentElement = currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
			currentElement = currentElement.addElement(CalendarioFatturazione.SLOT_PER_CLIENTE);
			currentElement = currentElement.addElement(Cliente.INSTANCE_NAME);
			currentElement = currentElement.addElement("ID");
			currentElement.setText(IDcliente);

			
			Document data = ApplicationLibrary.setData(currentElement.getDocument(),request);
			String[] messages2={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages2,StoriaDocumento.MY_CLASS);
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("resetinvoicingcalendar4client for invoicing calendar has resulted into sql error:"+messages2[0]);
				throw new SerenaException(messages2[0],"generic_sql_error");
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)
			{
				return true;
			}
			else
				return false;
		} catch (PermissionException  e) {
			logger.error("resetinvoicingcalendar4client for invoicing calendar has resulted into sql error"+e.getMessage());
			throw new SerenaException(e.getMessage());
		} catch (DataManagerException e) {
			logger.error("resetinvoicingcalendar4client for invoicing calendar has resulted into sql error"+e.getMessage());
			throw new SerenaException(e.getMessage());
		}
	}

	@Override
	protected String getName()
	{
		return METHOD_NAME;
	}

	
	
}

