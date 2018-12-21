/**
 * Questo file appartiene al progetto Sere-na - www.sere-na.it
 * 
 * @copyright Anastasis Soc. Coop. - www.anastasis.it
 */
package eu.anastasis.tulliniHelpGest.utils;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import eu.anastasis.serena.application.index.constants.ConstantsSession;
import eu.anastasis.serena.application.index.util.ApplicationConfiguration;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.auth.User;
import eu.anastasis.serena.constants.ConstantsEntityBean;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.exception.SerenaException;

/**
 * Gestore degli invii delle mail
 * 
 * @author afrascari
 */
public class MailHandler
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MailHandler.class);

	public static final String CONTENT_TYPE_PLAIN_TEXT = "text/plain";
	public static final String CONTENT_TYPE_HTML = "text/html";

	protected static final String GMAIL_SMTP_SERVER = "smtp.gmail.com";

	protected SimpleAuthenticator authenticator;
	protected String smtpServer = "localhost";
	protected String replyTo = null;
	
	protected Session session=null; // to keep an smtp session open for bulk send


	public MailHandler()
	{
		try
		{
			final String smtpServer = ApplicationConfiguration.GetInstance().getParametro(ApplicationConfiguration.SMTP_SERVER);
			setSmtpServer(smtpServer);
		} catch (final SerenaException e)
		{
			logger.error("Cannot read SMTP Server from config file");
		}

		final String username = ApplicationConfiguration.GetInstance().getParametro(ApplicationConfiguration.SMTP_USERNAME, null);
		final String password = ApplicationConfiguration.GetInstance().getParametro(ApplicationConfiguration.SMTP_PASSWORD, null);
		try	{
			replyTo = ApplicationConfiguration.GetInstance().getParametro(ApplicationConfiguration.MAIL_NOTIFICATION_RECIPIENTS, null);
		} catch (Exception e){
			logger.fatal("Cannot read replay-to from conf");
		}
		
		final boolean isGmailServer = GMAIL_SMTP_SERVER.equals(this.smtpServer);
		final boolean hasCredentials = (username != null) && (password != null);

		SimpleAuthenticator authenticator = null;
		if (isGmailServer)
		{
			if (!hasCredentials)
			{
				logger.error("E' stato impostato il server Gmail: bisogna impostare anche username e password");
			} else
			{
				authenticator = new GmailAuthenticator(username, password);
			}
		} else
		{
			if (hasCredentials)
			{
				authenticator = new SimpleAuthenticator(username, password);
			} else
			{
				logger.debug("Non ci sono credenzial SMPT in config_application: inviera' le mail senza autenticazione");
			}
		}
		setAuthenticator(authenticator);

	}

	/**
	 * Ricava la lista dei destinatari dalla configurazione dell'applicazione
	 * 
	 * @return L'array dei destinatari ricavato
	 * @throws SerenaException Problemi durante la lettura della configurazione
	 */
	public String[] retrieveRecipientArray() throws SerenaException
	{
		final String recipients = ApplicationConfiguration.GetInstance().getParametro(ApplicationConfiguration.MAIL_NOTIFICATION_RECIPIENTS);
		return recipients.split(",");
	}

	/**
	 * Spedizione messaggio tramite server smtp locale senza allegati (usa il
	 * plain/text come content type)
	 * 
	 * @param from Mittente
	 * @param to Array dei destinatari
	 * @param cc Array dei destinatari in copia
	 * @param bcc Array dei destinatari in copia nascosta
	 * @param subject Oggetto
	 * @param body Corpo
	 * @return Vero se l'invio e' andato a buon fine, falso altrimenti
	 */
	public boolean send(String from, String[] to, String[] cc, String[] bcc, String subject, String body)
	{
		return send(from, to, cc, bcc, subject, body, null);
	}

	/**
	 * Spedizione messaggio tramite server smtp locale senza allegati
	 * 
	 * @param from Mittente
	 * @param to Array dei destinatari
	 * @param cc Array dei destinatari in copia
	 * @param bcc Array dei destinatari in copia nascosta
	 * @param subject Oggetto
	 * @param body Corpo
	 * @param contentType Il content type da usare
	 * @return Vero se l'invio e' andato a buon fine, falso altrimenti
	 */
	public boolean send(String from, String[] to, String[] cc, String[] bcc, String subject, String body, String contentType)
	{
		return send(from, to, cc, bcc, subject, body, contentType, null);
	}

	/**
	 * Spedizione messaggio tramite server smtp locale Spedizione messaggio
	 * tramite server smtp locale senza allegati
	 * 
	 * @param from Mittente
	 * @param to Array dei destinatari
	 * @param cc Array dei destinatari in copia
	 * @param bcc Array dei destinatari in copia nascosta
	 * @param subject Oggetto
	 * @param body Corpo
	 * @param content_type Il content type da usare
	 * @param attachments Array degli allegati
	 * @return Vero se l'invio e' andato a buon fine, falso altrimenti
	 */
	public boolean send(String from, String[] to, String[] cc, String[] bcc, String subject, String body, String content_type, String[] attachments)
	{
		final String[] replyTos = checkAndFillReplyTo(null);
		return this.send(from, replyTos, to, cc, bcc, subject, body, content_type, attachments);
	}

	public boolean send(String from, String replyTo, String[] to, String[] cc, String[] bcc, String subject, String body, String content_type, String[] attachments)
	{
		final String[] replyTos = checkAndFillReplyTo(replyTo);
		return this.send(from, replyTos, to, cc, bcc, subject, body, content_type, attachments);
	}

	/**
	 * Spedizione messaggio tramite server smtp locale Spedizione messaggio
	 * tramite server smtp locale senza allegati
	 * 
	 * @param from Mittente
	 * @parama replyTo indirizzo automatico di risposta
	 * @param to Array dei destinatari
	 * @param cc Array dei destinatari in copia
	 * @param bcc Array dei destinatari in copia nascosta
	 * @param subject Oggetto
	 * @param body Corpo
	 * @param content_type Il content type da usare
	 * @param attachments Array degli allegati
	 * @return Vero se l'invio e' andato a buon fine, falso altrimenti
	 */
	public boolean send(String from, String[] replyTo, String[] to, String[] cc, String[] bcc, String subject, String body, String content_type, String[] attachments)
	{
		boolean ret;
		try
		{
			this.sendAndReturnMsg(from, replyTo, to, cc, bcc, subject, body, content_type, attachments);
			ret = true;
		} catch (final Exception e)
		{
			String recipient="sconosciuto";
			if(to!=null)
			{
				recipient=to[0];
			}
			logger.error("Non e' stato possibile inviare la mail a "+recipient, e);
			ret = false;
		}

		String recipients = "";
		if ((to != null) && (to.length > 0))
		{
			for (final String element : to)
			{
				recipients += element + ",";
			}
		}

		String message = "Mail inviata da " + from + " a " + recipients + " con esito ";
		if (ret)
		{
			message += "positivo.";
		} else
		{
			message += "NEGATIVO.";
		}
		logger.info(message);

		return ret;
	}

	public MimeMessage sendAndReturnMsg(String from, String[] replyTo, String[] to, String[] cc, String[] bcc, String subject, String body, String content_type, String[] attachments) throws SerenaException
	{
		final Properties props = new Properties();
		props.put("mail.smtp.host", getSmtpServer());
		props.put("mail.smtp.localhost", getSmtpServer());
		props.put("mail.from", from);

		final SimpleAuthenticator authenticator = getAuthenticator();
		if (authenticator != null)
		{
			final Map<String, String> authProps = authenticator.getProperties();
			for (final Entry<String, String> entry : authProps.entrySet())
			{
				props.put(entry.getKey(), entry.getValue());
			}
		}

		final Session session = Session.getInstance(props, authenticator);
		try
		{
			final MimeMessage msg = new MimeMessage(session);
			msg.setFrom();

			logger.trace("Invio messaggio " + subject + " da " + from);
			// to:
			InternetAddress[] addresses = new InternetAddress[to.length];
			for (int i = 0; i < to.length; i++)
			{
				addresses[i] = new InternetAddress(to[i]);
				logger.trace("a: " + to[i]);
			}
			msg.setRecipients(Message.RecipientType.TO, addresses);
			// cc:
			if (cc != null)
			{
				addresses = new InternetAddress[cc.length];
				for (int i = 0; i < cc.length; i++)
				{
					addresses[i] = new InternetAddress(cc[i]);
					logger.trace("cc: " + cc[i]);
				}
				msg.setRecipients(Message.RecipientType.CC, addresses);
			}
			// bcc:
			if (bcc != null)
			{
				addresses = new InternetAddress[bcc.length];
				for (int i = 0; i < bcc.length; i++)
				{
					addresses[i] = new InternetAddress(bcc[i]);
					logger.trace("bcc: " + bcc[i]);
				}
				msg.setRecipients(Message.RecipientType.BCC, addresses);
			}
			// reply to:
			if (replyTo==null){
				replyTo = checkAndFillReplyTo(null);
			}
			addresses = new InternetAddress[replyTo.length];
			for (int i = 0; i < replyTo.length; i++)
			{
				addresses[i] = new InternetAddress(replyTo[i]);
				logger.trace("reply to: " + replyTo[i]);
			}
			msg.setReplyTo(addresses);
			msg.setSubject(subject);
			msg.setSentDate(new Date());
			setMessageContent(msg, body, content_type, attachments);
			Transport.send(msg);
			logger.trace("Invio smtp ok per messaggio");
			return msg;
		} catch (final Exception e)
		{
			final String msg = "Errore invio messaggio: "+e.getMessage();
			logger.error(msg, e);
			throw new SerenaException(msg);
		}
	}
	
	
	public MimeMessage sendInBulkLotAndReturnMsg(String from, String[] replyTo, String[] to, String[] cc, String[] bcc, String subject, String body, String content_type, String[] attachments) throws SerenaException
	{
		try
		{
			final MimeMessage msg = new MimeMessage(session);
			msg.setFrom();

			logger.trace("Invio messaggio " + subject + " da " + from);
			// to:
			InternetAddress[] addresses = new InternetAddress[to.length];
			for (int i = 0; i < to.length; i++)
			{
				addresses[i] = new InternetAddress(to[i]);
				logger.trace("a: " + to[i]);
			}
			msg.setRecipients(Message.RecipientType.TO, addresses);
			// cc:
			if (cc != null)
			{
				addresses = new InternetAddress[cc.length];
				for (int i = 0; i < cc.length; i++)
				{
					addresses[i] = new InternetAddress(cc[i]);
					logger.trace("cc: " + cc[i]);
				}
				msg.setRecipients(Message.RecipientType.CC, addresses);
			}
			// bcc:
			if (bcc != null)
			{
				addresses = new InternetAddress[bcc.length];
				for (int i = 0; i < bcc.length; i++)
				{
					addresses[i] = new InternetAddress(bcc[i]);
					logger.trace("bcc: " + bcc[i]);
				}
				msg.setRecipients(Message.RecipientType.BCC, addresses);
			}
			// reply to:
			if (replyTo==null){
				replyTo = checkAndFillReplyTo(null);
			}
			addresses = new InternetAddress[replyTo.length];
			for (int i = 0; i < replyTo.length; i++)
			{
				addresses[i] = new InternetAddress(replyTo[i]);
				logger.trace("reply to: " + replyTo[i]);
			}
			msg.setReplyTo(addresses);
			msg.setSubject(subject);
			msg.setSentDate(new Date());
			setMessageContent(msg, body, content_type, attachments);
			Transport.send(msg);
			logger.trace("Invio smtp ok per messaggio");
			return msg;
		} catch (final Exception e)
		{
			final String msg = "Errore invio messaggio: "+e.getMessage();
			logger.error(msg, e);
			throw new SerenaException(msg);
		}
	}

	/**
	 * Notifica inserimento in base alla classe (leggendo info da file di
	 * configurazione)
	 * 
	 * @param aClass
	 * @param theUrl
	 * @param theId
	 * @param request
	 */
	public void insertionNotification(String aClass, String theUrl, String theId, HttpServletRequest request)
	{
		String anAuthor = "utente anonimo";
		final User currentUser = (User) request.getSession().getAttribute(ConstantsSession.USER);
		if (currentUser != null)
		{
			anAuthor = currentUser.getUsername();
		}
		try
		{
			final String classesToNotify = ApplicationConfiguration.GetInstance().getParametro(ApplicationConfiguration.MAIL_NOTIFICATION_CLASSES);
			final String system = ApplicationConfiguration.GetInstance().getParametro(ApplicationConfiguration.MAIL_SYSTEM_ADDRESS);
			final String portalTitle = ApplicationConfiguration.GetInstance().getParametro(ApplicationConfiguration.PORTAL_TITLE);
			if (classesToNotify.indexOf(aClass) > -1)
			{
				// ok, notification
				final String[] recipientArray = retrieveRecipientArray();
				final String subject = portalTitle + ": notifica inserimento " + aClass;
				String body = "E' stato inserito un oggetto " + aClass + " da " + anAuthor + "\n";
				// fetch xml oggetto
				// String xmlRequest="";
				Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(request.getSession().getId(), ConstantsXSerena.ACTION_GET, aClass);
				currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_SELECT);
				currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET, ConstantsXSerena.TARGET_ALL);
				currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET_LEVELS, "0");
				currentElement = currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
				currentElement = currentElement.addElement(ConstantsEntityBean.ID);
				currentElement.setText(theId);
				try
				{
					final Document resDoc = ApplicationLibrary.getData(currentElement.getDocument(), request);
					final String res = resDoc.asXML();
					final Element xmlClass = DocumentHelper.parseText(res).getRootElement().element(ConstantsXSerena.TAG_COMMAND).element(aClass);
					@SuppressWarnings("unchecked")
					final List<Element> attrs = xmlClass.elements();
					for (final Element anAttr : attrs)
					{
						if ((anAttr.getText() != null) && !anAttr.getText().equals(""))
						{
							body += anAttr.getText() + "\n";
						}
					}
				} catch (final Exception e)
				{
					logger.error("Error in fetch inserted object", e);
				}

				body += "link: " + request.getRequestURL() + theUrl;
				this.send(system, recipientArray, null, null, subject, body);
			}
		} catch (final SerenaException e)
		{
			logger.warn("Cannot read mail info from config file");
		}
	}

	protected void setMessageContent(MimeMessage msg, String text, String ctype, String[] attachments) throws MessagingException
	{
		if (ctype == null)
		{
			ctype = CONTENT_TYPE_PLAIN_TEXT;
		}

		final boolean hasAttachment = (attachments != null) && (attachments.length != 0);
		if (ctype.equals(CONTENT_TYPE_PLAIN_TEXT) && !hasAttachment)
		{
			msg.setText(text);
		} else
		{
			final MimeMultipart multipart = new MimeMultipart();

			final MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setText(text, null, "html");
			multipart.addBodyPart(htmlPart);

			if (attachments != null)
			{
				for (int i = 0; i < attachments.length; i++)
				{
					if ((attachments[i] != null) && !attachments[i].isEmpty())
					{
						final MimeBodyPart part = new MimeBodyPart();
						try
						{
							part.attachFile(attachments[i]);
						} catch (final IOException e)
						{
							logger.error("Errore allegando il file: " + attachments[i], e);
						}
						multipart.addBodyPart(part);
					}
				}
			}

			msg.setContent(multipart);
			// msg.saveChanges();
		}
	}

	/**
	 * @return Il server SMTP da usare
	 */
	protected String getSmtpServer()
	{
		return this.smtpServer;
	}

	/**
	 * Imposta il server SMTP da usare
	 * 
	 * @param smtpServer Il server da impostare
	 */
	protected void setSmtpServer(String smtpServer)
	{
		this.smtpServer = smtpServer;
	}

	/**
	 * @return L'autenticatore per il server SMTP (null se non e' necessaria
	 *         l'autenticazione)
	 */
	protected SimpleAuthenticator getAuthenticator()
	{
		return this.authenticator;
	}

	/**
	 * Imposta l'autenticatore per il server SMTP
	 * 
	 * @param authenticator L'autenticatore da impostare
	 */
	protected void setAuthenticator(SimpleAuthenticator authenticator)
	{
		this.authenticator = authenticator;
	}

	/**
	 * Autenticatore per il server SMTP
	 * 
	 * @author mcarnazzo
	 */
	protected class SimpleAuthenticator extends Authenticator
	{
		private PasswordAuthentication passwordAuthentication;
		private final Map<String, String> properties = new HashMap<String, String>();

		public SimpleAuthenticator(String username, String password)
		{
			getProperties().put("mail.smtp.auth", "true");
			if ((username != null) && (password != null))
			{
				this.passwordAuthentication = new PasswordAuthentication(username, password);
				getProperties().put("mail.smtp.submitter", this.passwordAuthentication.getUserName());
			}
		}

		public Map<String, String> getProperties()
		{
			return this.properties;
		}

		@Override
		protected PasswordAuthentication getPasswordAuthentication()
		{
			return this.passwordAuthentication;
		}
	}

	/**
	 * Autenticatore specifico per Gmail. Imposta la comunicazione via TLS
	 * 
	 * @author mcarnazzo
	 */
	public class GmailAuthenticator extends SimpleAuthenticator
	{
		public GmailAuthenticator(String username, String password)
		{
			super(username, password);

			getProperties().put("mail.smtp.starttls.enable", "true");
			getProperties().put("mail.smtp.port", "587");
		}
	}
	
	protected String[] checkAndFillReplyTo(String passedreplyto){
		final String[] replyTos = new String[1];
		replyTos[0] = passedreplyto!=null?passedreplyto:replyTo;
		return replyTos;
	}
}
