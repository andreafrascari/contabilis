package eu.anastasis.tulliniHelpGest.utils;

import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import java.util.Map.Entry;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import org.apache.log4j.Logger;
import eu.anastasis.it.tullinidms.modules.Cliente;
import eu.anastasis.it.tullinidms.modules.StoriaDocumento;
import eu.anastasis.serena.application.index.util.ApplicationConfiguration;
import eu.anastasis.serena.application.index.util.AttachmentBean;
import eu.anastasis.serena.application.index.util.ModuleParameterConfiguration;
import eu.anastasis.serena.application.index.util.SmsSender;
import eu.anastasis.serena.constants.ConstantsEntityBean;
import eu.anastasis.serena.exception.ConnectionException;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.tulliniHelpGest.beans.StoricoInvii;

public class MailAndSmsSender extends MailHandler {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(MailAndSmsSender.class);
	protected static final String SMS_SENDER = "Sammali Roubaud";
	protected static final String FAX_SENDER = "webfax@contabilis.it";

	private String quality = null;
	private String act = null;

	public void setQualityHigh() {
		this.quality = "h";
	}

	public void setQualityHighWithNotification() {
		this.quality = "n";
	}

	public void setQualityMedium() {
		this.quality = "m";
	}

	public void setQualityLow() {
		this.quality = "l";
	}

	public void setAct(String actVal) {
		this.act = actVal;
	}

	public boolean sendMail(String oggetto, String testo, String email)
			throws SerenaException {
		String[] bcc = null;
		String[] cc = null;
		String content_type = MailHandler.CONTENT_TYPE_HTML;
		String from = null;
		try {
			from = ApplicationConfiguration.GetInstance().getParametro(
					ApplicationConfiguration.MAIL_SYSTEM_ADDRESS);
		} catch (SerenaException e) {
			logger.error("Cannot send mail as no system address has been specified in config_application");
			return false;
		}

		String[] recipients = { email };

		try {
			super.sendAndReturnMsg(from, null, recipients, cc, bcc, oggetto,
					testo, content_type, new String[0]);
			logger.info("Inviata mail a " + email + " con oggetto: " + oggetto);
			insertStoricoMail(oggetto, testo, email, null);
			return true;
		} catch (Exception e) {
			String msg = "Errore nell'invio della mail " + oggetto + " a "
					+ email;
			logger.error(msg);
			insertStoricoMail(oggetto, testo, email, "errore");
			return false;
		}
	}

	public String sendMail(String oggetto, String testo, Cliente questoCliente)
			throws SerenaException {
		String[] bcc = null;
		String[] cc = null;
		String content_type = MailHandler.CONTENT_TYPE_HTML;
		String from = null;
		try {
			from = ApplicationConfiguration.GetInstance().getParametro(
					ApplicationConfiguration.MAIL_SYSTEM_ADDRESS);
		} catch (SerenaException e) {
			logger.error("Cannot send mail as no system address has been specified in config_application");
			return "Nessun system address email configurato!";
		}

		Vector<String> emails = questoCliente.getEmails();
		if (emails.isEmpty()) {
			return "Impossibile inviare mail " + oggetto + " a "
					+ questoCliente.getNome() + ": nessuna email definita";
		}

		String msg = "";
		for (String anEmail : emails) {
			String[] recipients = { anEmail };

			// personalizzo il link con la mail destinataria
			String testoMail = testo.replace("@MAINEMAIL@", anEmail);

			try {
				super.sendAndReturnMsg(from, null, recipients, cc, bcc,
						oggetto, testoMail, content_type, new String[0]);
				logger.info("Inviata mail notifica/sollecito a "
						+ questoCliente.getNome() + " "
						+ questoCliente.getEmail() + ": " + oggetto);
				insertStoricoMail(oggetto, testoMail, anEmail, null);
			} catch (Exception e) {
				msg += "Errore nell'invio della mail " + oggetto + " a "
						+ questoCliente.getEmail() + "; ";
				logger.error(msg);
				insertStoricoMail(oggetto, testoMail, anEmail, "errore");
			}
		}
		if (msg.isEmpty())
			return null;
		else
			return msg;
	}
	
	/**
	 * Invia a tutte le mail del cliente Torna null se invio ok, messaggio di
	 * errore se errore/i
	 * 
	 * @param unaStoria
	 * @param questoCliente
	 * @return
	 * @throws SerenaException
	 */
	public String sendMail(StoriaDocumento unaStoria, Cliente questoCliente)
			throws SerenaException {
		String[] bcc = null;
		String[] cc = null;
		String content_type = MailHandler.CONTENT_TYPE_HTML;
		String from = null;
		try {
			from = ApplicationConfiguration.GetInstance().getParametro(
					ApplicationConfiguration.MAIL_SYSTEM_ADDRESS);
		} catch (SerenaException e) {
			logger.error("Cannot send mail as no system address has been specified in config_application");
			return "Nessun system address email configurato!";
		}

		Vector<String> emails = questoCliente.getEmails();
		if (emails.isEmpty()) {
			return "Impossibile inviare mail " + unaStoria.getOggetto_mail()
					+ " a " + questoCliente.getNome()
					+ ": nessuna email definita";
		}

		String msg = "";
		for (String anEmail : emails) {
			String[] recipients = { anEmail };

			// personalizzo il link con la mail destinataria
			String testoMail = unaStoria.getTesto_mail().replace("@MAINEMAIL@",
					anEmail);

			try {
				super.sendAndReturnMsg(from, null, recipients, cc, bcc,
						unaStoria.getOggetto_mail(), testoMail, content_type,
						new String[0]);
				logger.info("Inviata mail notifica/sollecito a "
						+ questoCliente.getNome() + " "
						+ questoCliente.getEmail() + ": "
						+ unaStoria.getOggetto_mail());
			} catch (Exception e) {
				msg += "Errore nell'invio della mail "
						+ unaStoria.getOggetto_mail() + " a "
						+ questoCliente.getEmail() + "; ";
				logger.error(msg);
			}
		}
		if (msg.isEmpty())
			return null;
		else
			return msg;
	}

	/**
	 * Come la precedente, ma spedisce all'interno di una sessione di spedizione
	 * (bulk lot) in cui l'autenticazione viene fatta una sola volta,
	 * preventivamente, con la initBulkSend
	 * 
	 * @param unaStoria
	 * @param questoCliente
	 * @return
	 * @throws SerenaException
	 */
	public String sendMailInBulkLot(StoriaDocumento unaStoria,
			Cliente questoCliente) throws SerenaException {
		String[] bcc = null;
		String[] cc = null;
		String content_type = MailHandler.CONTENT_TYPE_HTML;
		String from = null;
		try {
			from = ApplicationConfiguration.GetInstance().getParametro(
					ApplicationConfiguration.MAIL_SYSTEM_ADDRESS);
		} catch (SerenaException e) {
			logger.error("Cannot send mail as no system address has been specified in config_application");
			return "Nessun system address email configurato!";
		}

		Vector<String> emails = questoCliente.getEmails();
		if (emails.isEmpty()) {
			return "Impossibile inviare mail " + unaStoria.getOggetto_mail()
					+ " a " + questoCliente.getNome()
					+ ": nessuna email definita";
		}

		String msg = "";
		for (String anEmail : emails) {
			String[] recipients = { anEmail };

			// personalizzo il link con la mail destinataria
			String testoMail = unaStoria.getTesto_mail().replace("@MAINEMAIL@",
					anEmail);

			try {
				super.sendInBulkLotAndReturnMsg(from, null, recipients, cc,
						bcc, unaStoria.getOggetto_mail(), testoMail,
						content_type, new String[0]);
				logger.info("Bulk send: inviata mail a "
						+ questoCliente.getNome() + " "
						+ questoCliente.getEmail() + ": "
						+ unaStoria.getOggetto_mail());
				insertStoricoMail( unaStoria.getOggetto_mail(), testoMail, anEmail, null);
			} catch (Exception e) {
				msg += "Errore nell'invio della mail "
						+ unaStoria.getOggetto_mail() + " a "
						+ questoCliente.getEmail() + "; ";
				logger.error(msg);
			}
		}
		if (msg.isEmpty())
			return null;
		else
			return msg;
	}
	
	public String sendMailInBulkLot(String oggetto, String testo,
			Cliente questoCliente) throws SerenaException {
		String[] bcc = null;
		String[] cc = null;
		String content_type = MailHandler.CONTENT_TYPE_HTML;
		String from = null;
		try {
			from = ApplicationConfiguration.GetInstance().getParametro(
					ApplicationConfiguration.MAIL_SYSTEM_ADDRESS);
		} catch (SerenaException e) {
			logger.error("Cannot send mail as no system address has been specified in config_application");
			
		}

		Vector<String> emails = questoCliente.getEmails();
		if (emails.isEmpty()) {
			return "Impossibile inviare mail " + oggetto + " a "
					+ questoCliente.getNome() + ": nessuna email definita";
		}

		String msg = "";
		for (String anEmail : emails) {
			String[] recipients = { anEmail };

			// personalizzo il link con la mail destinataria
			String testoMail = testo.replace("@MAINEMAIL@", anEmail);

			try {
				super.sendAndReturnMsg(from, null, recipients, cc, bcc,
						oggetto, testoMail, content_type, new String[0]);
				logger.info("Bulk send: inviata mail a "
						+ questoCliente.getNome() + " "
						+ questoCliente.getEmail() + ": " + oggetto);
				insertStoricoMail(oggetto, testoMail, anEmail, null);
			} catch (Exception e) {
				msg += "Errore nell'invio della mail " + oggetto + " a "
						+ questoCliente.getEmail() + "; ";
				logger.error(msg);
				insertStoricoMail(oggetto, testoMail, anEmail, "errore");
			}
		}
		if (msg.isEmpty())
			return null;
		else
			return msg;
	}

	public String sendFax(StoriaDocumento unaStoria, Cliente questoCliente,
			String attachId) throws SerenaException {
		String[] bcc = null;
		String[] cc = null;
		String[] attachments = processAttachments(attachId);
		String content_type = MailHandler.CONTENT_TYPE_PLAIN_TEXT;
		String from = FAX_SENDER;

		if (questoCliente.getFax() == null || questoCliente.getFax().isEmpty()) {
			return "Impossibile inviare faxmail " + unaStoria.getOggetto_mail()
					+ " a " + questoCliente.getNome()
					+ ": nessuna fax definito";
		}

		String[] recipients = { questoCliente.getFax() };

		String testoMail = unaStoria.getTesto_mail();

		String msg = null;

		try {
			super.sendAndReturnMsg(from, null, recipients, cc, bcc,
					unaStoria.getOggetto_mail(), testoMail, content_type,
					attachments);
			logger.info("Inviato faxmail notifica/sollecito a "
					+ questoCliente.getNome() + " " + questoCliente.getEmail()
					+ ": " + unaStoria.getOggetto_mail());
		} catch (Exception e) {
			msg += "Errore nell'invio del faxmail "
					+ unaStoria.getOggetto_mail() + " a "
					+ questoCliente.getEmail() + "; ";
			logger.error(msg);
		}
		return msg;
	}

	protected String[] processAttachments(String attachId)
			throws SerenaException {
		String[] attaches = new String[1];
		AttachmentBean anAtBean = new AttachmentBean();
		anAtBean.setSa_type(new Integer(ConstantsEntityBean.CAMPO_ATTACHMENT)
				.toString());
		anAtBean.setID(attachId);
		String aPath = "";
		try {
			anAtBean.cronload();
			aPath = anAtBean.getMyPath();
		} catch (Exception e1) {
			e1.printStackTrace();
			logger.error("AttachmentBean cannot load attach " + attachId + ": "
					+ e1.getMessage());
			throw new SerenaException(e1.getMessage());
		}
		attaches[0] = aPath + anAtBean.getSa_filename();
		return attaches;
	}

	public String sendSms(StoriaDocumento unaStoria, Cliente questoCliente) {
		if (questoCliente.getCellulare() == null
				|| questoCliente.getCellulare().isEmpty()) {
			return "Impossibile inviare SMS a " + questoCliente.getNome()
					+ ": cellulare non definito";
		}
		SmsSender sender = new SmsSender();
		try {
			String[] smsNums = questoCliente.getCellulares();
			for (String sms : smsNums) {
				sender.send(SMS_SENDER, sms, unaStoria.getTesto_sms());
			}
			logger.info("Inviato SMS notifica/sollecito a "
					+ questoCliente.getNome() + " "
					+ questoCliente.getCellulare() + ": "
					+ unaStoria.getTesto_sms());
		} catch (ConnectionException e) {
			return "Errore invio SMS a " + questoCliente.getCellulare();
		}
		return null;
	}

	public String sendMailAttivazione(Cliente questoCliente, String testo,
			String oggetto) throws SerenaException {
		String[] bcc = null;
		String[] cc = null;
		String content_type = MailHandler.CONTENT_TYPE_HTML;
		String from = null;
		try {
			from = ApplicationConfiguration.GetInstance().getParametro(
					ApplicationConfiguration.MAIL_SYSTEM_ADDRESS);
		} catch (SerenaException e) {
			logger.error("Cannot send mail as no system address has been specified in config_application");
			return "Nessun system address email configurato!";
		}

		if (questoCliente.getEmail() == null
				|| questoCliente.getEmail().isEmpty()) {
			return "Impossibile inviare mail a " + questoCliente.getNome()
					+ ": email non definita";
		}
		String[] recipients = { questoCliente.getEmail() };

		MimeMessage theSentMsg = super.sendAndReturnMsg(from, null, recipients,
				cc, bcc, oggetto, testo, content_type, new String[0]);
		if (theSentMsg == null) {
			String msg = "Errore nell'invio della mail a "
					+ questoCliente.getEmail();
			logger.error(msg);
			return msg;
		} else {
			logger.info("Inviata mail attivazione account a "
					+ questoCliente.getNome() + " " + questoCliente.getEmail());
		}

		return null;
	}

	public String sendSmsAttivazione(Cliente theClient, String testo) {
		if (theClient.getCellulare() == null
				|| theClient.getCellulare().isEmpty()) {
			return "Impossibile inviare SMS a " + theClient.getNome()
					+ ": cellulare non definito";
		}
		SmsSender sender = new SmsSender();
		if (quality != null)
			sender.setQuality(quality);
		if (act != null)
			sender.setAct(act);
		try {
			String[] smsNums = theClient.getCellulares();
			for (String sms : smsNums) {
				sender.send(SMS_SENDER, sms, testo);
			}
			logger.info("Inviato SMS notifica/sollecito a "
					+ theClient.getNome() + " " + theClient + ": " + testo);
		} catch (ConnectionException e) {
			return "Errore invio SMS a " + theClient.getCellulare();
		}
		return null;
	}

	public String sendFaxAttivazione(Cliente questoCliente, String testo,
			String oggetto) throws SerenaException {
		String[] bcc = null;
		String[] cc = null;
		String content_type = MailHandler.CONTENT_TYPE_PLAIN_TEXT;
		String from = FAX_SENDER;

		if (questoCliente.getFax() == null || questoCliente.getFax().isEmpty()) {
			return "Impossibile inviare faxmail " + questoCliente.getNome()
					+ ": nessun fax definito";
		}

		String[] recipients = { questoCliente.getFax() };

		MimeMessage theSentMsg = super.sendAndReturnMsg(from, null, recipients,
				cc, bcc, oggetto, testo, content_type, new String[0]);
		if (theSentMsg == null) {
			String msg = "Errore nell'invio del faxmail a "
					+ questoCliente.getFax();
			logger.error(msg);
			return msg;
		} else {
			logger.info("Inviata faxmail attivazione account a "
					+ questoCliente.getNome() + " " + questoCliente.getFax());
		}

		return null;
	}

	public void initBulkSend() throws SerenaException {
		logger.info("initBulkSend");
		String from = null;
		try {
			from = ApplicationConfiguration.GetInstance().getParametro(
					ApplicationConfiguration.MAIL_SYSTEM_ADDRESS);
		} catch (Exception e) {
			String msg = "Cannot send mail as no system address has been specified in config_application";
			logger.fatal(msg);
			throw new SerenaException(msg);
		}

		final Properties props = new Properties();
		props.put("mail.smtp.host", getSmtpServer());
		props.put("mail.smtp.localhost", getSmtpServer());
		props.put("mail.from", from);

		final SimpleAuthenticator authenticator = getAuthenticator();
		if (authenticator != null) {
			final Map<String, String> authProps = authenticator.getProperties();
			for (final Entry<String, String> entry : authProps.entrySet()) {
				props.put(entry.getKey(), entry.getValue());
			}
		}

		session = Session.getInstance(props, authenticator);

	}


	public String sendSms(String messaggio, Cliente questoCliente) {
		if (questoCliente.getCellulare() == null
				|| questoCliente.getCellulare().isEmpty()) {
			return "Impossibile inviare SMS a " + questoCliente.getNome()
					+ ": cellulare non definito";
		}
		SmsSender sender = new SmsSender();
		if (quality != null)
			sender.setQuality(quality);
		if (act != null)
			sender.setAct(act);
		String[] smsNums = questoCliente.getCellulares();
		String msg = "";
		for (String sms : smsNums) {
			try {
				sender.send(SMS_SENDER, sms, messaggio);
				insertStoricoSms(messaggio, sms, null);
				logger.info("Inviato SMS notifica/sollecito a "
						+ questoCliente.getNome() + " " + sms + ": "
						+ messaggio);
			} catch (ConnectionException e) {
				insertStoricoSms(messaggio, sms, e.toString());
				String mmsg = "Errore invio SMS a " + sms;
				logger.error(mmsg);
				msg += mmsg;
			}
		}
		return msg.isEmpty() ? null : msg;
	}

	public String sendFax(String oggetto, String testoMail,
			Cliente questoCliente, String attachId) throws SerenaException {
		String[] bcc = null;
		String[] cc = null;
		String[] attachments = processAttachments(attachId);
		String content_type = MailHandler.CONTENT_TYPE_PLAIN_TEXT;
		String from = FAX_SENDER;

		if (questoCliente.getFax() == null || questoCliente.getFax().isEmpty()) {
			return "Impossibile inviare faxmail " + oggetto + " a "
					+ questoCliente.getNome() + ": nessuna fax definito";
		}

		String[] recipients = { questoCliente.getFax() };

		String msg = null;

		try {
			super.sendAndReturnMsg(from, null, recipients, cc, bcc, oggetto,
					testoMail, content_type, attachments);
			logger.info("Inviato faxmail notifica/sollecito a "
					+ questoCliente.getNome() + " " + questoCliente.getEmail()
					+ ": " + testoMail);
			insertStoricoFax(oggetto, testoMail, questoCliente.getFax(), null);
		} catch (Exception e) {
			msg += "Errore nell'invio del faxmail " + oggetto + " a "
					+ questoCliente.getEmail() + "; ";
			logger.error(msg);
			insertStoricoFax(oggetto, testoMail, questoCliente.getFax(),
					"errore");
		}
		return msg;
	}

	


	public String sendSms(String messaggio, String numero) {
		if (numero == null || numero.isEmpty()) {
			return "Impossibile inviare SMS: numero mancante.";
		}
		SmsSender sender = new SmsSender();
		if (quality != null)
			sender.setQuality(quality);
		if (act != null)
			sender.setAct(act);
		try {
			sender.send(SMS_SENDER, numero, messaggio);
			insertStoricoSms(messaggio, numero, null);
			logger.info("Inviato SMS a " + numero + ": " + messaggio);
		} catch (ConnectionException e) {
			insertStoricoSms(messaggio, numero, e.toString());
			String mmsg = "Errore invio SMS a " + numero;
			logger.error(mmsg);
			return mmsg;
		}
		return null;
	}

	/******************* storico ***********************/

	private void insertStoricoFax(String oggetto, String messaggio,
			String destinatario, String esito) {
		try {
			String storicizza = ModuleParameterConfiguration.getInstance()
					.getValue("document", "STORICO_FAX");
			if ("1".equals(storicizza)) {
				messaggio = "<strong>oggetto: " + oggetto + "</strong><br />"
						+ messaggio;
				insertStorico(messaggio, destinatario, esito,
						Cliente.SOLLECITO_FAX);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void insertStoricoMail(String oggetto, String messaggio,
			String destinatario, String esito) {
		try {
			String storicizza = ModuleParameterConfiguration.getInstance()
					.getValue("document", "STORICO_MAIL");
			if ("1".equals(storicizza)) {
				messaggio = "<strong>oggetto: " + oggetto + "</strong><br />"
						+ messaggio;
				insertStorico(messaggio, destinatario, esito,
						Cliente.SOLLECITO_MAIL);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void insertStoricoSms(String messaggio, String destinatario,
			String esito) {
		try {
			String storicizza = ModuleParameterConfiguration.getInstance()
					.getValue("document", "STORICO_SMs");
			if ("1".equals(storicizza)) {
				insertStorico(messaggio, destinatario, esito,
						Cliente.SOLLECITO_SMS);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void insertStorico(String messaggio, String destinatario,
			String esito, String canale) {
		try {
			StoricoInvii aBean = new StoricoInvii();
			aBean.setMessaggio(messaggio);
			aBean.setDestinatario(destinatario);
			aBean.setEsito(esito);
			aBean.setTipo_sollecito(canale);
			aBean.insert();
		} catch (Exception e) {
			// already logged
		}
	}

	

}
