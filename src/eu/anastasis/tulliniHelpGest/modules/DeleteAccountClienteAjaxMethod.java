package eu.anastasis.tulliniHelpGest.modules;


import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;


import eu.anastasis.serena.application.core.modules.AjaxMethod;
import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.common.XMessage;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.CalendarioFatturazione;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Cliente;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Scadenza;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


/**
 * Prenotazione di un attività pe ril tassametro
 * Si tratta di una coda FIFO di N elementi Attivita da "presentare" al tassametro
 * @author afrascari
 *
 */
public class DeleteAccountClienteAjaxMethod extends AjaxMethod
{

	private static final String METHOD_NAME = "deletecliente";
	private static final String PAR_ID_CLIENTE = "cliente";
	private static final String PAR_DISABLEACCOUNT = "disableaccount";
	private static final Logger logger = Logger.getLogger(DeleteAccountClienteAjaxMethod.class);
	private static final String UserClass= "_system_user";
	
	public DeleteAccountClienteAjaxMethod(DefaultModule parentModule, String[] defaultParameters) {
		super(parentModule, defaultParameters);
		// TODO Auto-generated constructor stub
	}
	
	public DeleteAccountClienteAjaxMethod()	{
		super(null, null);
	}

	@Override
	public String doMethod(HttpServletRequest request,
			HttpServletResponse response) throws SerenaException {
		XMessage msg=null;
		// get from QS
		String idCliente=getMethodParameter(request, PAR_ID_CLIENTE);
		boolean disAccount = getMethodParameter(request, PAR_DISABLEACCOUNT).equals("true");
		try
		{
			msg=new XMessage("reply");
			Element mp=DocumentHelper.createElement("result");
			Element node = mp.addElement("message");
			msg.add(mp);
			String mesg = "";
			if (disAccount)	{
				deleteAccount(request,idCliente);
				mesg = "Account cliente disabilitato; ";
			}
			deleteMetascadenze(request,idCliente);
			deleteCalendarioFatturazione(request,idCliente);
			mesg += "Metascadenze e Entry Calendario Fatturazione cancellate.";
			node.setText(mesg); 
		} catch (Exception e)
		{
			String errorMessage = "Impossibile disabilitare account cliente: "+e.getMessage();
			msg=XMessage.getMessaggioErrore(errorMessage);
			logger.error(errorMessage, e);
			return msg.asXML();
		}
		return msg.asXML();
	}


	private void deleteAccount(HttpServletRequest request, String idCliente) throws SerenaException {
		try	{
			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(request.getSession().getId(), ConstantsXSerena.ACTION_SET, UserClass);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_DELETE);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATOR, ConstantsXSerena.VAL_DELETE_PHYSICAL);
			
			Element condition = currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
			condition = condition.addElement("inverse_of_account");
			condition = condition.addElement("Cliente");
			condition = condition.addElement("ID");
			condition.setText(idCliente);
			Document data = ApplicationLibrary.setData(currentElement.getDocument(),request);
			String[] messages2={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages2,UserClass);
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("cannot delete account: "+messages2[0]);
				throw new SerenaException("cannot delete account: "+messages2[0]);
			}			
		} catch (Exception e) {
			logger.error("cannot delete account "+e.getMessage());
			throw new SerenaException("cannot delete account"+e.getMessage());
		}
	}
	
	private void deleteMetascadenze(HttpServletRequest request, String idCliente) throws SerenaException {
		try	{
			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(request.getSession().getId(), ConstantsXSerena.ACTION_SET, Scadenza.INSTANCE_NAME);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_DELETE);
			
			Element condition = currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
			condition = condition.addElement(Scadenza.SLOT_INVERSE_OF_SCADENZE);
			condition = condition.addElement(Cliente.INSTANCE_NAME);
			condition = condition.addElement("ID");
			condition.setText(idCliente);
			Document data = ApplicationLibrary.setData(currentElement.getDocument(),request);
			String[] messages2={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages2,Scadenza.INSTANCE_NAME);
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("cannot delete account: "+messages2[0]);
				throw new SerenaException("cannot delete Metascadenze: "+messages2[0]);
			}			
		} catch (Exception e) {
			logger.error("cannot delete account "+e.getMessage());
			throw new SerenaException("cannot delete Metascadenze"+e.getMessage());
		}
	}
	
	private void deleteCalendarioFatturazione(HttpServletRequest request, String idCliente) throws SerenaException {
		try	{
			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(request.getSession().getId(), ConstantsXSerena.ACTION_SET, CalendarioFatturazione.INSTANCE_NAME);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_DELETE);
			
			Element condition = currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
			condition = condition.addElement(CalendarioFatturazione.SLOT_PER_CLIENTE);
			condition = condition.addElement(Cliente.INSTANCE_NAME);
			condition = condition.addElement("ID");
			condition.setText(idCliente);
			Document data = ApplicationLibrary.setData(currentElement.getDocument(),request);
			String[] messages2={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages2,CalendarioFatturazione.INSTANCE_NAME);
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("cannot delete account: "+messages2[0]);
				throw new SerenaException("cannot delete CalendarioFatturazione: "+messages2[0]);
			}			
		} catch (Exception e) {
			logger.error("cannot delete account "+e.getMessage());
			throw new SerenaException("cannot delete CalendarioFatturazione"+e.getMessage());
		}
	}
	
	@Override
	protected String getName()
	{
		return METHOD_NAME;
	}
	
	 
}

