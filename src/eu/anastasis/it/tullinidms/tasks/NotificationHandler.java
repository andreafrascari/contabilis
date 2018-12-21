package eu.anastasis.it.tullinidms.tasks;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import eu.anastasis.it.tullinidms.modules.Cliente;
import eu.anastasis.it.tullinidms.modules.StoriaDocumento;
import eu.anastasis.serena.application.index.Index;
import eu.anastasis.serena.application.index.util.ApplicationConfiguration;
import eu.anastasis.serena.application.index.util.MailHandler;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.dataManager.DataManagerHome;
import eu.anastasis.tulliniHelpGest.utils.MailAndSmsSender;
/**
 * Lanciato da una sorta di cron settato da config_tasks, controlla gli operatori che hanno data_ultima_modifica_password 
 * a NULL (mai settato) o "scaduta" con riferimento la 196 (3 mesi) e ne mette activation_flag a 0.
 * In questo modo, gli operatori, al logon successivo, verranno ridirezionati al cambio password.
 * Invia inoltre una notifica via mail 7 giorni prima della scadenza 
 * @author mtassetti
 */
public class NotificationHandler implements Runnable 
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(NotificationHandler.class);

	DataManagerHome persistence=Index.getPersistence().getRealPersistence();
	
	protected MailHandler mailer=new MailHandler();
	
	private static final String Non_scaricati_per_sollecito = "non-scaricati-per-sollecito";
	private static final String Non_pervenuti_per_sollecito = "non-pervenuti-per-sollecito";
	
	private static final String SOLLECITO_PREFIX_SHORT = "[SOLLECITO] ";
	private static final String SOLLECITO_PREFIX_LONG_DOWNLOAD = "<h4>Sollecito download documento inviato</h4>";
	private static final String SOLLECITO_PREFIX_LONG_INVIO = "<h4>Sollecito invio materiale richiesto</h4>";
		
	
	public void run() 
	{
		processWorkflowDasollecitare(Non_scaricati_per_sollecito);
		processWorkflowDasollecitare(Non_pervenuti_per_sollecito);		
	}


	private void processWorkflowDasollecitare(String qualiWorkflow) {
		// 1) fetch workflow da sollecitare
		try	{
			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader("fakse", ConstantsXSerena.ACTION_GET, StoriaDocumento.MY_CLASS);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_SELECT);
			currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET, ConstantsXSerena.TARGET_ALL);
			currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET_LEVELS, "2"); // mi servono anche cliente e documento
			currentElement.addAttribute(ConstantsXSerena.ATTR_FORCED_DATASOURCE, qualiWorkflow);
			
			Document resultData=persistence.get(currentElement.getDocument());
			List<Element> storieDaSollecitare= resultData.selectNodes(".//"+StoriaDocumento.MY_CLASS);
				int i=0;
				for (Element storiaElem: storieDaSollecitare)	{
					Cliente ilCliente = new Cliente();
					StoriaDocumento laStoria = new StoriaDocumento();
					
					Element clienteElem = (Element)storiaElem.selectSingleNode(".//Cliente");
					
					laStoria.setID(storiaElem.selectSingleNode(".//ID").getText());
					laStoria.setInviato_il(storiaElem.selectSingleNode(".//inviato_il").getText());
					laStoria.setOggetto_mail(SOLLECITO_PREFIX_SHORT+storiaElem.selectSingleNode(".//oggetto_mail").getText());
					String sollecitoEsteso = qualiWorkflow.equals(Non_scaricati_per_sollecito) ? SOLLECITO_PREFIX_LONG_DOWNLOAD : SOLLECITO_PREFIX_LONG_INVIO;
					laStoria.setTesto_mail(sollecitoEsteso+storiaElem.selectSingleNode(".//testo_mail").getText());
					laStoria.setTesto_sms(SOLLECITO_PREFIX_SHORT+storiaElem.selectSingleNode(".//testo_sms").getText());
									
					ilCliente.setID(clienteElem.selectSingleNode(".//ID").getText());
					ilCliente.setNome(clienteElem.selectSingleNode(".//cliente").getText());
					ilCliente.setNickname(clienteElem.selectSingleNode(".//nickname").getText());
					ilCliente.setEmail(clienteElem.selectSingleNode(".//email").getText());
					ilCliente.setCellulare(clienteElem.selectSingleNode(".//cellulare").getText());
					ilCliente.setTipo_sollecito(clienteElem.selectSingleNode(".//tipo_sollecito").getText());
					
					try	{
						// 2) invio sollecito
						String sendResult= null;
						MailAndSmsSender msh = new MailAndSmsSender();
						if (ilCliente.notificheViaSms())	
							sendResult = msh.sendSms(laStoria, ilCliente);
						else 
							sendResult = (ilCliente.notificheViaFax())?"notifiche via fax disattivate":msh.sendMail(laStoria, ilCliente);
						
						// 3) update
						currentElement = ObjectUtils.getXserenaRequestStandardHeader("fake"+ilCliente.getID(), ConstantsXSerena.ACTION_SET, StoriaDocumento.MY_CLASS);
						currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_UPDATE);
	
						String 	dateFormat = ApplicationConfiguration.GetInstance().getParametro(
								ApplicationConfiguration.INTERFACE_DATE_FORMAT);
						SimpleDateFormat myDateFormat = new SimpleDateFormat(dateFormat);
						
						Element theCond = currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
						Element anItem = theCond.addElement("ID");
						anItem.setText(laStoria.getID());
						
						if (sendResult==null)	{
							anItem = currentElement.addElement("sollecito_il");
							anItem.setText(myDateFormat.format(new Date()));
						} else {					
							anItem = currentElement.addElement("errore");
							anItem.setText("Tentativo fallito di sollecito il " + myDateFormat.format(new Date())+": "+sendResult);
	
						}						
						persistence.set(currentElement.getDocument());
					} catch  (Exception inInvioOupdate)	{
						String msg = "Errore in process workflow da sollecitare (in invio o update singola istanza)";
						inInvioOupdate.printStackTrace();
						logger.error(msg + inInvioOupdate.getMessage());
					}
				}		// fine Workflow
		} catch (Exception inFetch)	{
		String msg = "Errore in process workflow da sollecitare (in fetch istanze)";
		inFetch.printStackTrace();
		logger.error(msg + inFetch.getMessage());
	}
}
	
	
}