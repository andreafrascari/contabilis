package eu.anastasis.it.tullinidms.modules;

import java.util.Vector;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import eu.anastasis.serena.application.index.util.ApplicationConfiguration;
import eu.anastasis.serena.application.index.util.AttachmentBean;
import eu.anastasis.serena.application.index.util.MailHandler;
import eu.anastasis.serena.application.index.util.SmsSender;
import eu.anastasis.serena.constants.ConstantsEntityBean;
import eu.anastasis.serena.exception.ConnectionException;
import eu.anastasis.serena.exception.SerenaException;

public class MailAndSmsSender {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MailAndSmsSender.class);
	protected static final String SMS_SENDER = "Contabilis";
	protected static final String FAX_SENDER = "webfax@contabilis.it";


	/**
	 * Invia a tutte le mail del cliente
	 * Torna null se invio ok, messaggio di errore se errore/i
	 * @param unaStoria
	 * @param questoCliente
	 * @return
	 * @throws SerenaException
	 */
	public String sendMail(StoriaDocumento unaStoria, Cliente questoCliente) throws SerenaException {
			String[] bcc =null;
			String[] cc=null;
			String content_type=MailHandler.CONTENT_TYPE_HTML;			
			String from = null;
			try {
				from = ApplicationConfiguration.GetInstance().getParametro(ApplicationConfiguration.MAIL_SYSTEM_ADDRESS);
			} catch (SerenaException e) 
			{
				logger.error("Cannot send mail as no system address has been specified in config_application");
				return "Nessun system address email configurato!";
			}
			
			Vector<String>emails = questoCliente.getEmails();
			if (emails.isEmpty()) 	{
				return "Impossibile inviare mail " + unaStoria.getOggetto_mail() + " a " + questoCliente.getNome() + ": nessuna email definita";
			}
			
			String msg ="";
			MailHandler mailHandler=new MailHandler();
			for (String anEmail:emails){
				String[] recipients ={anEmail};
				
				// personalizzo il link con la mail destinataria
				String testoMail = unaStoria.getTesto_mail().replace("@MAINEMAIL@", anEmail);
				
				
				try	{
					mailHandler.sendAndReturnMsg(from, null,recipients,cc, bcc, unaStoria.getOggetto_mail(),testoMail, content_type, new String[0]);
					logger.info("Inviata mail notifica/sollecito a " + questoCliente.getNome() + " " + questoCliente.getEmail() +": " + unaStoria.getOggetto_mail());
				} catch (Exception e) {
					msg += "Errore nell'invio della mail " + unaStoria.getOggetto_mail() + " a " + questoCliente.getEmail()+"; ";
					logger.error(msg);
				}
			}
			if (msg.isEmpty())
				return null;
			else return msg;
	}
	
	public String sendFax(StoriaDocumento unaStoria, Cliente questoCliente, String attachId) throws SerenaException {
		String[] bcc =null;
		String[] cc=null;
		String[] attachments=processAttachments(attachId);
		String content_type=MailHandler.CONTENT_TYPE_PLAIN_TEXT;			
		String from = FAX_SENDER;
		
		if (questoCliente.getFax()==null || questoCliente.getFax().isEmpty()) 	{
			return "Impossibile inviare faxmail " + unaStoria.getOggetto_mail() + " a " + questoCliente.getNome() + ": nessuna fax definito";
		}
		
		String[] recipients ={questoCliente.getFax()};
			
		String testoMail = unaStoria.getTesto_mail();
			
		MailHandler mailHandler=new MailHandler();
			
		String msg= null;
		
		try	{
			mailHandler.sendAndReturnMsg(from, null, recipients,cc, bcc, unaStoria.getOggetto_mail(),testoMail, content_type, attachments);
			logger.info("Inviato faxmail notifica/sollecito a " + questoCliente.getNome() + " " + questoCliente.getEmail() +": " + unaStoria.getOggetto_mail());
		} catch (Exception e) {
			msg += "Errore nell'invio del faxmail " + unaStoria.getOggetto_mail() + " a " + questoCliente.getEmail()+"; ";
			logger.error(msg);
		}
		return msg;
}
	
	protected String[] processAttachments(String attachId) throws SerenaException	{
		String[] attaches = new String[1];
		AttachmentBean anAtBean = new AttachmentBean();
		anAtBean.setSa_type(new Integer(ConstantsEntityBean.CAMPO_ATTACHMENT).toString());
		anAtBean.setID(attachId);
		String aPath ="";
		try {
			anAtBean.cronload();
			aPath = anAtBean.getMyPath();
		} catch (Exception e1) {
			e1.printStackTrace();
			logger.error("AttachmentBean cannot load attach "+attachId+ ": "+e1.getMessage());
			throw new SerenaException(e1.getMessage());
		}
		attaches[0] = aPath+anAtBean.getSa_filename();
		return attaches;
	}

	public String sendSms(StoriaDocumento unaStoria, Cliente questoCliente) {
		if (questoCliente.getCellulare()==null || questoCliente.getCellulare().isEmpty())	{
			return "Impossibile inviare SMS a " + questoCliente.getNome() + ": cellulare non definito";
		}
		SmsSender sender = new SmsSender();
		try {
			String[] smsNums = questoCliente.getCellulares();
			for (String sms:smsNums){
				sender.send(SMS_SENDER,sms , unaStoria.getTesto_sms());
			}
			logger.info("Inviato SMS notifica/sollecito a " + questoCliente.getNome() + " " + questoCliente.getCellulare() +": " + unaStoria.getTesto_sms());
		} catch (ConnectionException e) {
			return "Errore invio SMS a " + questoCliente.getCellulare();
		}
		return null;
	}
	
	public String sendMailAttivazione(Cliente questoCliente, String testo, String oggetto) throws SerenaException {
		String[] bcc =null;
		String[] cc=null;
		String content_type=MailHandler.CONTENT_TYPE_HTML;			
		String from = null;
		try {
			from = ApplicationConfiguration.GetInstance().getParametro(ApplicationConfiguration.MAIL_SYSTEM_ADDRESS);
		} catch (SerenaException e) 
		{
			logger.error("Cannot send mail as no system address has been specified in config_application");
			return "Nessun system address email configurato!";
		}
		
		if (questoCliente.getEmail()==null || questoCliente.getEmail().isEmpty())	{
			return "Impossibile inviare mail a " + questoCliente.getNome() + ": email non definita";
		}
		String[] recipients ={questoCliente.getEmail()};
		
		MailHandler mailHandler=new MailHandler();
		
		MimeMessage theSentMsg = mailHandler.sendAndReturnMsg(from, null,recipients,cc, bcc, oggetto,testo, content_type, new String[0]);
		if(theSentMsg==null)
		{
			String msg = "Errore nell'invio della mail a " + questoCliente.getEmail();
			logger.error(msg);
			return msg;
		} else {
			logger.info("Inviata mail attivazione account a " + questoCliente.getNome() + " " + questoCliente.getEmail());
		}			
		
	return null;
	}

	public String sendSmsAttivazione(Cliente theClient, String testo) {
		if (theClient.getCellulare()==null || theClient.getCellulare().isEmpty())	{
			return "Impossibile inviare SMS a " + theClient.getNome() + ": cellulare non definito";
		}
		SmsSender sender = new SmsSender();
		try {
			String[] smsNums = theClient.getCellulares();
			for (String sms:smsNums){
				sender.send(SMS_SENDER,sms , testo);
			}
			logger.info("Inviato SMS notifica/sollecito a " + theClient.getNome() + " " + theClient +": " + testo);
		} catch (ConnectionException e) {
			return "Errore invio SMS a " + theClient.getCellulare();
		}
		return null;
	}
	
	public String sendFaxAttivazione(Cliente questoCliente, String testo, String oggetto) throws SerenaException {
		String[] bcc =null;
		String[] cc=null;
		String content_type=MailHandler.CONTENT_TYPE_PLAIN_TEXT;			
		String from = FAX_SENDER;
		
		if (questoCliente.getFax()==null || questoCliente.getFax().isEmpty()) 	{
			return "Impossibile inviare faxmail " + questoCliente.getNome() + ": nessun fax definito";
		}
		
		String[] recipients ={questoCliente.getFax()};
					
		MailHandler mailHandler=new MailHandler();
		
		MimeMessage theSentMsg = mailHandler.sendAndReturnMsg(from,null, recipients,cc, bcc, oggetto,testo, content_type, new String[0]);
		if(theSentMsg==null)
		{
			String msg = "Errore nell'invio del faxmail a " + questoCliente.getFax();
			logger.error(msg);
			return msg;
		} else {
			logger.info("Inviata faxmail attivazione account a " + questoCliente.getNome() + " " + questoCliente.getFax());
		}			
		
	return null;
	}
	
 
	
}
