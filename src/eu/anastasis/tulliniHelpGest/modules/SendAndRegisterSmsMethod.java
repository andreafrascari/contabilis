package eu.anastasis.tulliniHelpGest.modules;


import java.util.Date;

import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import eu.anastasis.it.tullinidms.modules.Cliente;
import eu.anastasis.serena.application.core.modules.DefaultMethod;
import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.common.SerenaDate;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.serena.persistence.utils.IdReservationCache;
import eu.anastasis.serena.presentation.templates.ActiveTemplate;
import eu.anastasis.serena.presentation.templates.TemplateFactory;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Sms;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.TulliniHelpGestBeanDataManager;
import eu.anastasis.tulliniHelpGest.utils.MailAndSmsSender;
import eu.anastasis.tulliniHelpGest.utils.UtilsProvider;

/**
 * Invio e registrazione SMS
 * @author afrascari
 *
 */
public class SendAndRegisterSmsMethod extends DefaultMethod 
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SendAndRegisterSmsMethod.class);


	private static final String METHOD_NAME = "sendregsms";

	public SendAndRegisterSmsMethod(DefaultModule parentModule,String[] defaultParameters)
	{
		super(parentModule, defaultParameters);
	}

	@Override
	protected String getName()
	{
		return METHOD_NAME;
	}

 	
	@SuppressWarnings("unchecked")
	@Override
	public String doMethod(HttpServletRequest request,HttpServletResponse response) throws SerenaException
	{
		ActiveTemplate template = new ActiveTemplate(TemplateFactory.getTemplate(
				retrieveDefaultTemplateContext(),retrieveTemplateContext(), getName()));
		try	{
			String clienteID = request.getParameter("INP_a_cliente");
			String numero = request.getParameter("INP_numero_destinatario");
			String messaggio = request.getParameter("INP_testo");
			String qualita = request.getParameter("INP_qualita");
			String note = request.getParameter("INP_note");
			Sms sms = new Sms();
			
			// 1. spedizione SMS
			MailAndSmsSender sender = new MailAndSmsSender();
			if (qualita.equals(Sms.QUALITA__ALTA))	{
				sender.setQualityHigh();
			} else if (qualita.equals(Sms.QUALITA__ALTA_CON_NOTIFICA))	{
					sender.setQualityHighWithNotification();
					String smsID = new IdReservationCache().guessNextId(Sms.INSTANCE_NAME); 
					sender.setAct(smsID);
			} else if (qualita.equals(Sms.QUALITA__MEDIA))	{
				sender.setQualityMedium();
			}else if (qualita.equals(Sms.QUALITA__BASSA))	{
				sender.setQualityLow();
			} 
			Cliente clienteDestinatario=null;
			String res = null;
			String destinatario = "";
			if (clienteID!=null && !clienteID.isEmpty())	{
				clienteDestinatario = new Cliente().getInstance(clienteID);
				destinatario = clienteDestinatario.getNome();
				numero = clienteDestinatario.getCellulare();
				res = sender.sendSms(messaggio, clienteDestinatario);
				eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Cliente clienteBean = new eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Cliente();
				clienteBean.setId(clienteDestinatario.getID());
				sms.setA_cliente(clienteBean);
			} else {
				destinatario = numero;
				res =  sender.sendSms(messaggio,numero);
			}

			// 2. registrazione SMS
			sms.setNumero_destinatario(numero);
			sms.setTesto(messaggio);
			sms.setQualita(qualita);
			String now = new SerenaDate().getDateAsString(new UtilsProvider().getDateTimeFormat());
			sms.setOra_spedizione(new Date());
			if (res !=null)	{
				if (note==null)
					note = res;
				else note = res + " - " + note;
			}
			sms.setNote(note);
			try	{
				new TulliniHelpGestBeanDataManager().insertSms(request, sms);
			} catch (Exception e) {
				String errorMessage = "SMS inviato a " + destinatario + "ma NON regiastrato a causa di un errore.";
				logger.error(errorMessage,e);
				template.replaceTagInBlock("ERROR_MESSAGE", "RESULT_ERROR",errorMessage );
				template.publishBlock("RESULT_ERROR");
				template.publish();
				return template.getContenuto();
			}
			if (res !=null)	{
				String errorMessage = "Impossibile inviare SMS a " + destinatario + ": "+res;
				logger.error(errorMessage);
				template.replaceTagInBlock("ERROR_MESSAGE", "RESULT_ERROR",errorMessage );
				template.publishBlock("RESULT_ERROR");
				template.publish();
			} else {			
				template.replaceTagInBlock("MESS", "RESULT_SUCCESS","SMS correttamente inviato a " + destinatario);
				template.publishBlock("RESULT_SUCCESS");
				template.publish();
			}
			
			return template.getContenuto();
			
		} catch (Exception e)
		{
			String errorMessage = "Impossibile inviare o registrare sms a causa di un errore: contattare assistenza.";
			logger.error(errorMessage, e);
			template.replaceTagInBlock("ERROR_MESSAGE", "RESULT_ERROR",errorMessage );
			template.publishBlock("RESULT_ERROR");
			template.publish();
			return template.getContenuto();
		}
	}
	
  
	 
}
