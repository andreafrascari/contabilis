/**
 * Questo file appartiene al progetto Sere-na - www.sere-na.it
 * @copyright Anastasis Soc. Coop. - www.anastasis.it
 */
package eu.anastasis.it.tullinidms.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import eu.anastasis.serena.application.index.SerenaServlet;

public class PhonecallServer extends SerenaServlet {

	private static final long serialVersionUID = 1999L;

	private static final Logger logger = Logger
			.getLogger(PhonecallServer.class);

	/********************************* SERVLET GET ************/
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String qs = request.getQueryString();
		qs = qs.replace("?", "&");
//		String redirUrl = "?q=ticket/phonecall&" + qs;
		String redirUrl = "/?q=ticket/phonecall";
		logger.debug("Phone call received: forwarding to " + redirUrl);
		RequestDispatcher rd = getServletContext().getRequestDispatcher(redirUrl);
// 		response.sendRedirect(redirUrl);
		rd.forward(request, response);
	}

}