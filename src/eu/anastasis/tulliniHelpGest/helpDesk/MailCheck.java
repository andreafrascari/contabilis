package eu.anastasis.tulliniHelpGest.helpDesk;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.mail.search.FlagTerm;

import org.apache.log4j.Logger;

import eu.anastasis.serena.application.index.util.ModuleParameterConfiguration;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.HDGruppoAssistenza;
import eu.anastasis.serena.common.SerenaDate;
import eu.anastasis.serena.exception.SerenaException;

/* try: 
 * 
 * 1) http://www.codejava.net/java-ee/javamail/receive-e-mail-messages-from-a-pop3-imap-server
 * 2) http://www.technicalkeeda.com/java/how-to-access-gmail-inbox-using-java-imap
 * 3) http://www.developer.com/java/data/monitoring-email-accounts-imap-in-java.html
 */

/**
 * Controlla la mailbox
 * 
 * @author afrascari
 */
public class MailCheck {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MailCheck.class);
	// private static final String KerioIP = "46.44.199.65";

	private static String AssistenzaFromWebSubjectIdentifier = "Contabilis: richiesta di assistenza tecnica";
	private static String EmptySubject = "Nessun oggetto";
	
	private static boolean DropIfOriginalNotForGroup = false;

	private static String IMAPS_VAL = "imaps";
	private static String IMAP_VAL = "imap";
	private static String PORT_993 = "993";
	private static String PORT_143 = "143";

	

	private void downloadEmails(HDGruppoAssistenza group)
			throws SerenaException {

		String host = group.getServer();
		String port = group.getPort();
		String protocol = PORT_993.equals(port) ? IMAPS_VAL : IMAP_VAL;
		String userName = group.getEmail();
		String password = group.getEmail_pwd();
		logger.debug("Checking emails for " + group.getEmail() +  " on " +protocol);
		
		/*
		Properties properties = getServerProperties(protocol, host, port);
		Session session = Session.getDefaultInstance(properties);

			// connects to the message store
			Store store = session.getStore(protocol);
			store.connect(userName, password);
		 */

		try {
		
		Properties props = System.getProperties();
		Store store = null;
		Session session = Session.getDefaultInstance(props, null);
			store = session.getStore(protocol);
			store.connect(host, userName, password);

			// opens the inbox folder
			Folder folderInbox = store.getFolder("INBOX");
			folderInbox.open(Folder.READ_WRITE);
			logger.debug("Connected.");

			// fetches ALL messages from server
//			Message[] messages = folderInbox.getMessages();
			
			Flags seen = new Flags(Flags.Flag.SEEN);
			FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
			Message[] messages = folderInbox.search(unseenFlagTerm);
			
			logger.debug("# of messages:  " + messages.length);

			for (int i = 0; i < messages.length; i++) {
				fetchMessage(messages[i], group);
			}

			// disconnect
			folderInbox.close(false);
			store.close();
		} catch (NoSuchProviderException ex) {
			System.out.println("No provider for protocol: " + protocol);
			ex.printStackTrace();
		} catch (MessagingException ex) {
			System.out.println("Could not connect to the message store");
			ex.printStackTrace();
		}
	}

	public void checkAllRegisteredAccounts() throws SerenaException {
		try {
			ConfigurazioneAssistenza cfgAs = ConfigurazioneAssistenza
					.getInstance();
			HashMap<String, HDGruppoAssistenza> gruppi_assistenza = cfgAs
					.getGruppi_assistenza_cache();
			for (HDGruppoAssistenza aGroup : gruppi_assistenza.values()) {
				downloadEmails(aGroup);
			}
		} catch (Exception e) {
			String msg = "Riportato un errore in checkAllRegisteredAccounts";
			logger.error(msg, e);
			throw new SerenaException(msg);
		}
	}

	protected boolean fromWebForm(String theSubject) {
		return theSubject.equals(AssistenzaFromWebSubjectIdentifier);
	}

	/**
	 * es: from "Andrea Frascari <afrascari@anastasis.it>" to
	 * "afrascari@anastasis.it"
	 * 
	 * @param theAddress
	 * @return
	 */
	public String unclutterMailAddress(String theAddress) {
		int firstIndex = theAddress.indexOf("<");
		if (firstIndex < 0)
			return theAddress;
		int secondIndex = theAddress.indexOf(">", firstIndex);
		return theAddress.substring(firstIndex + 1, secondIndex);
	}

	public String toIfDifferentFromThread(String theThreadEmail, String theThreadAlias,
			Address[] someAddresses) throws SerenaException {
		for (Address anAddress : someAddresses) {
			String cleanAddress = unclutterMailAddress(anAddress.toString());
			if (!cleanAddress.equals(theThreadEmail))
				return cleanAddress;
			if (!cleanAddress.equals(theThreadAlias))
				return cleanAddress;
		}
		return null;
	}

	public void fetchMessage(Message message, HDGruppoAssistenza group)
			throws SerenaException {
		try {
			
			message.setFlag( Flags.Flag.SEEN,true);
			
			Mail2Ticket ticketToBe = new Mail2Ticket();
			if (message.getReplyTo() != null && message.getReplyTo().length > 0) {
				// se c'e' il reply to ... uso quello
				ticketToBe.setFrom(unclutterMailAddress(message.getReplyTo()[0]
						.toString()));
			} else {
				ticketToBe.setFrom(unclutterMailAddress(message.getFrom()[0]
						.toString()));
			}

			Address[] to = message.getRecipients(Message.RecipientType.TO);
			
			String explicitTo = toIfDifferentFromThread(group.getEmail(), group.getAlias(),to);
			
			if (explicitTo!=null)	{
				logger.debug("Gruppo " + group.getEmail() + " riceve messaggio (originariamente) per " + explicitTo); 
				if (DropIfOriginalNotForGroup){
					logger.debug(" ... che NON viene processato");
					return;
				}
			}

			ticketToBe.setTo(group.getEmail());
			
			String theSubject = message.getSubject();
			if (theSubject == null || theSubject.isEmpty())
				theSubject = EmptySubject;
			ticketToBe.setSubject(theSubject);
			if (message.getReceivedDate() != null) {
				ticketToBe.setReceivedAt(new Timestamp(message
						.getReceivedDate().getTime()));
			} else {
				ticketToBe.setReceivedAt(new Timestamp((new java.util.Date())
						.getTime()));
			}

			Object content = message.getContent();
			String body = "";
			if (content instanceof Multipart) {

				Multipart multipart = (Multipart) message.getContent();

				for (int j = 0, n = multipart.getCount(); j < n; j++) {
					Part part = multipart.getBodyPart(j);

					String disposition = part.getDisposition();

					if ((disposition != null)
							&& (disposition.toLowerCase().equals(
									Part.ATTACHMENT) || disposition
									.toLowerCase().equals(Part.INLINE))) {
						ticketToBe.setAttachment(part);
					} else {
						MimeBodyPart mbp = (MimeBodyPart) part;
						if ((mbp.isMimeType("text/plain"))
								|| (mbp.isMimeType("TEXT/HTML"))) {
							body = retrieveContent(mbp);
						} else {
							try {
								Multipart mp = (Multipart) mbp.getContent();
								for (int x = 0; x < mp.getCount(); x++) {
									Part bp = mp.getBodyPart(x);
									body += retrieveContent(bp);
								}
							} catch (Exception e) {
								String msg = "Impossibile leggerne il contenuto: cercare la mail con subject "
										+ ticketToBe.getSubject()
										+ " del "
										+ new SerenaDate();
								logger.error(message, e);
								body = msg;
							}
						}

					}
				}
			} else {
				body = (String) content;
			}
			body = encode(body);
			ticketToBe.setBody(body);
			if (logger.isDebugEnabled()) {
				logger.trace("Received message " + ticketToBe.getSubject()
						+ " from " + ticketToBe.getFrom()
						+ ": now trying to turn it into a ticket...");

			}
			/*
			 * if (fromWebForm(ticketToBe.getSubject())) // create ticket from
			 * the web request that will be identified within the mail mody new
			 * Web2Ticket(ticketToBe.getBody(),
			 * ticketToBe.getTo()).createTicket(); else // create ticket form
			 * the mail itself
			 */
			ticketToBe.createTicket();
		} catch (Exception e) {
			String msg = "[MailCheck] error while receiveng mail for "
					+ group.getEmail() + " - thread still running: "
					+ e.toString();
			logger.fatal(msg, e);
			// resetting thread?!?!?!?!?!
		} catch (Error e) {
			String msg = "[MailCheck] error while receiveng mail for "
					+ group.getEmail() + " - thread still running: "
					+ e.toString();
			logger.fatal(msg, e);
			// resetting thread?!?!?!?!?!
		}
	}

	private String retrieveContent(Part part) throws IOException,
			MessagingException {
		return part.getContent().toString();
	}

	/**
	 * Converte le stringhe delle email dall'encoding delle email a UTF-8
	 * 
	 * @param dirtyContent
	 *            La stringa da encodare
	 * @return La stringa encodata
	 * @throws UnsupportedEncodingException
	 *             Problemi durante l'encoding
	 */
	private String encode(String dirtyContent)
			throws UnsupportedEncodingException {
		String encoding = ModuleParameterConfiguration.getInstance().getValue(
				TicketModule.MODULE_NAME, TicketModule.PARAM_POLLING_ENCODING,
				"UTF-8");
		if (encoding == null) {
			encoding = "UTF-8";
			logger.warn("Paramtero Polling encoding NON DEFINITO!!! using utf-8");
		}
		return new String(dirtyContent.getBytes(encoding));
	}

}
