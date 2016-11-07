package eu.anastasis.tulliniHelpGest.helpDesk;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.google.gson.Gson;

import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.application.core.modules.JSONMethod;
import eu.anastasis.serena.application.core.tasks.CronPersistenceHome;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.exceptions.JSONException;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Cliente;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.TulliniHelpGestBinder;

/**
 * Metodo JSON per il recupero del bean Cliente da una qualsiasi delle
 * informazioni di contatto
 * 
 * @author andrea
 * 
 */
public class ClientFromContactInfoMethod extends JSONMethod {

	private static final Logger logger = Logger
			.getLogger(ClientFromContactInfoMethod.class);

	public ClientFromContactInfoMethod(DefaultModule parentModule,
			String[] defaultParameters) {
		super(parentModule, defaultParameters);
		// TODO Auto-generated constructor stub
	}

	public static final String METHOD_NAME = "clientFromContactInfo";

	@Override
	protected String getName() {
		return METHOD_NAME;
	}

	@Override
	public String doMethod(HttpServletRequest request,
			HttpServletResponse response) throws JSONException {
		Cliente unClienteObj = null;
		String info = request.getParameter("info");
		try {
			unClienteObj = clienteFromContact(request, info);
			if (unClienteObj == null) {
				// let's try skimming a leading zero
				info = info.substring(1);
				unClienteObj = clienteFromContact(request, info);
				if (unClienteObj == null) {
					throw new Exception("non riconoscibile da " + info);
				}
			}
			logger.trace("DONE!");
		} catch (Exception any) {
			String msg = "Errore in riconoscimento cliente: "
					+ any.getMessage();
			logger.error(msg);
			throw new JSONException(msg);
		}
		return new Gson().toJson(unClienteObj);
	}

	protected Cliente clienteFromContact(HttpServletRequest request, String info)
			throws Exception {
		Cliente unClienteObj = null;
		Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(
				request.getSession().getId(), ConstantsXSerena.ACTION_GET,
				Cliente.INSTANCE_NAME);
		currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION,
				ConstantsXSerena.VAL_SELECT);
		currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET,
				ConstantsXSerena.TARGET_ALL);
		currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET_LEVELS, "0");
		Element currentCond = currentElement
				.addElement(ConstantsXSerena.TAG_CONDITION);
		Element cessAss = currentCond.addElement("cessata_assistenza_il");
		cessAss.setText(ConstantsXSerena.VAL_NULL);

		Element theOr = currentCond.addElement(ConstantsXSerena.TAG_OR);
		Element anOr = theOr.addElement(Cliente.SLOT_CELLULARE);
		anOr.setText(info);
		anOr = theOr.addElement(Cliente.SLOT_TELEFONO);
		anOr.setText(info);
		anOr = theOr.addElement(Cliente.SLOT_EMAIL);
		anOr.setText(info);
		anOr = theOr.addElement(Cliente.SLOT_EMAIL2);
		anOr.setText(info);
		anOr = theOr.addElement(Cliente.SLOT_EMAIL3);
		anOr.setText(info);
		anOr = theOr.addElement(Cliente.SLOT_FAX);
		anOr.setText(info);

		String[] messages = { "", "" };
		Document resultData = new CronPersistenceHome().get(currentElement
				.getDocument());
		Element data = ApplicationLibrary
				.prepareDataForPresentation(resultData);
		int res = ConstantsXSerena.getXserenaRequestResult(data, messages,
				Cliente.INSTANCE_NAME);

		if (res == ConstantsXSerena.XSERENA_RESULT_ERROR) {
			logger.warn("Cannot look up for client from conatct " + messages[0]);
			throw new Exception(messages[0]);
		} else if (res == ConstantsXSerena.XSERENA_RESULT_EMPTY) {
			logger.error("Look-up for client from contact " + info
					+ " resulted in not-found");
			return null;
		} else if (res == ConstantsXSerena.XSERENA_RESULT_SUCCESS) {
			Element ilCliente = (Element) data.selectSingleNode(".//"
					+ Cliente.INSTANCE_NAME);
			unClienteObj = new TulliniHelpGestBinder().createCliente(ilCliente);

		}
		return unClienteObj;
	}

}
