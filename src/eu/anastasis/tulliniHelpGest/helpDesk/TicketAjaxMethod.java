package eu.anastasis.tulliniHelpGest.helpDesk;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.google.gson.Gson;

import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.application.core.modules.JSONMethod;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.common.XSerenaLibrary;
import eu.anastasis.serena.dataManager.DataManagerException;
import eu.anastasis.serena.exceptions.JSONException;
import eu.anastasis.serena.query.DeleteQuery;
import eu.anastasis.serena.query.UpdateQuery;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.HDTicket;

public class TicketAjaxMethod extends JSONMethod {

	private static final Logger logger = Logger
			.getLogger(TicketAjaxMethod.class);

	public TicketAjaxMethod(DefaultModule parentModule,
			String[] defaultParameters) {
		super(parentModule, defaultParameters);
		// TODO Auto-generated constructor stub
	}

	public static final String METHOD_NAME = "ticketAjax";

	public static final String ACTION_DELETE_TICKET = "deleteTicket";
	public static final String ACTION_OPEN_TICKET = "openTicket";
	public static final String ACTION_DEMOTE_TICKET = "demoteTicket";

	@Override
	protected String getName() {
		return METHOD_NAME;
	}

	@Override
	public String doMethod(HttpServletRequest request,
			HttpServletResponse response) throws JSONException {
		try {
			String q = request.getParameter("action");
			String id = request.getParameter("id");
			logger.debug("TicketAjaxMethod called for " + q);
			if (ACTION_DELETE_TICKET.equals(q)) {
				deleteTicket(request, id);
			} else if (ACTION_OPEN_TICKET.equals(q)) {
				openTicket(request, id);
			} else if (ACTION_DEMOTE_TICKET.equals(q)) {
				demoteTicket(request, id);
			} else {
				throw new Exception("Azione " + q + " non gestita!");
			}

			return new Gson().toJson("OK");
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new JSONException(e.getMessage());
		}
	}

	private void demoteTicket(HttpServletRequest request, String id)
			throws Exception {
		UpdateQuery query = new UpdateQuery(HDTicket.INSTANCE_NAME, id);
		final Element firstClassElement = query.getFirstClassElement();
		Element toSet = firstClassElement.addElement(HDTicket.SLOT_PRIORITA_TICKET);
		toSet.setText(HDTicket.PRIORITA_TICKET__NORMALE);
		final Document response = ApplicationLibrary.setData(query, request);
		if (!XSerenaLibrary.isValidResult(response)) {
			throw new DataManagerException(response);
		}
	}

	private void openTicket(HttpServletRequest request, String id) 
			throws Exception {
		UpdateQuery query = new UpdateQuery(HDTicket.INSTANCE_NAME, id);
		final Element firstClassElement = query.getFirstClassElement();
		Element toSet = firstClassElement.addElement(HDTicket.SLOT_STATOTICKET);
		toSet.setText(HDTicket.STATOTICKET__APERTO);
		final Document response = ApplicationLibrary.setData(query, request);
		if (!XSerenaLibrary.isValidResult(response)) {
			throw new DataManagerException(response);
		}
	}

	private void deleteTicket(HttpServletRequest request, String id)
			throws Exception {
		DeleteQuery query = new DeleteQuery(HDTicket.INSTANCE_NAME, id);
		final Element firstClassElement = query.getFirstClassElement();
		query.setPhysicalDelete(firstClassElement);

		final Document response = ApplicationLibrary.setData(query, request);

		if (!XSerenaLibrary.isValidResult(response)) {
			throw new DataManagerException(response);
		}
	}

}
