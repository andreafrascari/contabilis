package eu.anastasis.tulliniHelpGest.helpDesk;

import org.apache.log4j.Logger;

import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.presentation.functions.FunctionParser;
import eu.anastasis.tulliniHelpGest.utils.NextFatturaFunction;

public class TicketModule extends DefaultModule {

		public static final String MODULE_NAME = "ticket";
		
		public static final String PARAM_TICKET_CCN_EMAIL_ADDRESS = "TICKET_CCN_EMAIL_ADDRESS";
		public static final String PARAM_POLLING_FREQUENCY = "POLLING_FREQUENCY";
		public static final String PARAM_POLLING_ENCODING = "POLLING_ENCODING";
		public static final String PARAM_HD_MAIL_COLLETTIVE = "HD_MAIL_COLLETTIVE";
		
		private static final Logger logger = Logger.getLogger(Mail2Ticket.class);
		
		@Override
		protected void setUpMethods()
		{
			addMethod(new SendMailMethod(this, getDefaultParameters()));
			addMethod(new PhoneCall(this, getDefaultParameters()));
			addMethod(new ClientFromContactInfoMethod(this, getDefaultParameters()));
			addMethod(new TelefonataCorrenteMethod(this, getDefaultParameters()));
			addMethod(new NewPhoneTicket(this, getDefaultParameters()));
			addMethod(new ThreadStep2SessioneDiLavoro(this, getDefaultParameters()));
			addMethod(new Risposta2RispostaStandardMethod(this, getDefaultParameters()));
			addMethod(new TicketAjaxMethod(this, getDefaultParameters()));
		
			addFunctionToPostparse(new TicketTotalMinutes());
			addFunctionToPostparse(new CurrentUserAccessCollectiveEmails());
		}
		

		
		@Override
		public String getDefaultName() 
		{
			return MODULE_NAME;
		}
	}

