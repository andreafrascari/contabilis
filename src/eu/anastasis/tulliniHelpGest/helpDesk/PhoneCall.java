package eu.anastasis.tulliniHelpGest.helpDesk;


import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import eu.anastasis.serena.application.core.modules.DefaultMethod;
import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.common.SerenaDate;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.HDChiamataTelefonica;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.TulliniHelpGestBeanDataManager;
import eu.anastasis.serena.exception.SerenaException;


/**
 * Metodo chiamato dal centralino asterix per registrare la chiamata corrente.
 * Aggiorna un registro statico
 * @author afrascari
 *
 */
public class PhoneCall extends DefaultMethod 

{
	
	
	/* TO TEST:
	 * 
	 * 1) localhost:8080/TulliniHelpGest/Index?q=ticket/phonecall&pbx_id=2225460514&key=1042&event=3&datetime=20150921095605235&domain=tullini%2Epbx&company=1&call_id=12172&call_id_ext=&call_status=1&calling=051226416&called=051239860&calling_id=1&calling_type=2 HTTP/1.1" 200 6773 "-" "Mozilla/3.0 (compatible; Indy Library)"

	 * 
	 * 2) localhost:8080/TulliniHelpGest/Index?q=ticket/phonecall&pbx_id=2225460514&key=1047&event=5&datetime=20150921095613619&domain=tullini%2Epbx&company=1&call_id=12172&call_id_ext=&call_status=4&calling=051226416&called=23&called_id=3&called_type=0 HTTP/1.1" 200 6693 "-" "Mozilla/3.0 (compatible; Indy Library)"
	 * 
	 * 3) localhost:8080/TulliniHelpGest/Index?q=ticket/phonecall&pbx_id=2225460514&key=1049&event=12&datetime=20150921095744878&domain=tullini%2Epbx&company=1&call_id=12172&call_id_ext=&call_status=7&outcome=1 HTTP/1.1" 200 6693 "-" "Mozilla/3.0 (compatible; Indy Library)" 
	 
	 */
	
	//TODO: ALTER TABLE `HDChiamataTelefonica` CHANGE `durata` `durata` INT( 10 ) NULL ;
	
	/* Esempio di flusso di ingresso:
	 
ingresso:
46.44.199.65 - - [21/Sep/2015:09:56:39 +0200] "GET /tdms/Index?q=ticket/phonecall?pbx_id=2225460514&key=1042&event=3&datetime=20150921095605235&domain=tullini%2Epbx&company=1&call_id=12172&call_id_ext=&call_status=1&calling=051226416&called=051239860&calling_id=1&calling_type=2 HTTP/1.1" 200 6773 "-" "Mozilla/3.0 (compatible; Indy Library)"

uscita:
46.44.199.65 - - [21/Sep/2015:09:56:40 +0200] "GET /tdms/Index?q=ticket/phonecall?pbx_id=2225460514&key=1043&event=5&datetime=20150921095605290&domain=tullini%2Epbx&company=1&call_id=12172&call_id_ext=&call_status=2&calling=051226416&called=051239860&called_id=1&called_type=3 HTTP/1.1" 200 6693 "-" "Mozilla/3.0 (compatible; Indy Library)"

libero:
46.44.199.65 - - [21/Sep/2015:09:56:41 +0200] "GET /tdms/Index?q=ticket/phonecall?pbx_id=2225460514&key=1044&event=6&datetime=20150921095606318&domain=tullini%2Epbx&company=1&call_id=12172&call_id_ext=&call_status=3&result=1&sender_id=1&sender_type=2&connect_id=1&connect_type=3&calling_id=1&calling_type=2&calling_local_id=226734&called_id=1&called_type=3&called_local_id=226740 HTTP/1.1" 200 6693 "-" "Mozilla/3.0 (compatible; Indy Library)"

deviazione:
46.44.199.65 - - [21/Sep/2015:09:56:48 +0200] "GET /tdms/Index+
deviazione:
46.44.199.65 - - [21/Sep/2015:09:56:48 +0200] "GET /tdms/Index?q=ticket/phonecall?pbx_id=2225460514&key=1046&event=5&datetime=20150921095613609&domain=tullini%2Epbx&company=1&call_id=12172&call_id_ext=&call_status=4&calling=051226416&called=21&called_id=2&called_type=0 HTTP/1.1" 200 6693 "-" "Mozilla/3.0 (compatible; Indy Library)"

deviazione:
46.44.199.65 - - [21/Sep/2015:09:56:49 +0200] "GET /tdms/Index?q=ticket/phonecall?pbx_id=2225460514&key=1047&event=5&datetime=20150921095613619&domain=tullini%2Epbx&company=1&call_id=12172&call_id_ext=&call_status=4&calling=051226416&called=22&called_id=3&called_type=0 HTTP/1.1" 200 6693 "-" "Mozilla/3.0 (compatible; Indy Library)"

libero:
46.44.199.65 - - [21/Sep/2015:09:56:51 +0200] "GET /tdms/Index?q=ticket/phonecall?pbx_id=2225460514&key=1048&event=6&datetime=20150921095616158&domain=tullini%2Epbx&company=1&call_id=12172&call_id_ext=&call_status=3&result=1&sender_id=2&sender_type=1&connect_id=3&connect_type=0&calling_id=1&calling_type=2&calling_local_id=226734&called_id=2&called_type=1&called_local_id=226745 HTTP/1.1" 200 6693 "-" "Mozilla/3.0 (compatible; Indy Library)"

sta terminando:
46.44.199.65 - - [21/Sep/2015:09:58:20 +0200] "GET /tdms/Index?q=ticket/phonecall?pbx_id=2225460514&key=1049&event=12&datetime=20150921095744878&domain=tullini%2Epbx&company=1&call_id=12172&call_id_ext=&call_status=7&outcome=1 HTTP/1.1" 200 6693 "-" "Mozilla/3.0 (compatible; Indy Library)"

*/
	 
	
	/* Esempio flusso in uscita:
46.44.199.65 - - [21/Sep/2015:15:39:41 +0200] "GET /tdms/Index?q=ticket/phonecall?pbx_id=2225460514&key=1141&event=3&datetime=20150921153840734&domain=tullini%2Epbx&company=1&call_id=12207&call_id_ext=&call_status=1&calling=21&called=3939949516&calling_id=2&calling_type=0 HTTP/1.1" 200 6773 "-" "Mozilla/3.0 (compatible; Indy Library)"
46.44.199.65 - - [21/Sep/2015:15:39:41 +0200] "GET /tdms/Index?q=ticket/phonecall?pbx_id=2225460514&key=1142&event=5&datetime=20150921153840905&domain=tullini%2Epbx&company=1&call_id=12207&call_id_ext=&call_status=1&calling=21&called=3939949516&called_id=4&called_type=2 HTTP/1.1" 200 6693 "-" "Mozilla/3.0 (compatible; Indy Library)"
46.44.199.65 - - [21/Sep/2015:15:39:43 +0200] "GET /tdms/Index?q=ticket/phonecall?pbx_id=2225460514&key=1143&event=6&datetime=20150921153842990&domain=tullini%2Epbx&company=1&call_id=12207&call_id_ext=&call_status=3&result=1&sender_id=2&sender_type=0&connect_id=4&connect_type=2&calling_id=2&calling_type=0&calling_local_id=238094&called_id=238092&called_type=5&called_local_id=238103 HTTP/1.1" 200 6693 "-" "Mozilla/3.0 (compatible; Indy Library)"
46.44.199.65 - - [21/Sep/2015:15:39:59 +0200] "GET /tdms/Index?q=ticket/phonecall?pbx_id=2225460514&key=1144&event=12&datetime=20150921153858662&domain=tullini%2Epbx&company=1&call_id=12207&call_id_ext=&call_status=7&outcome=1 HTTP/1.1" 200 6693 "-" "Mozilla/3.0 (compatible; Indy Library)"

	or
	

46.44.199.65 - - [21/Sep/2015:18:04:09 +0200] "GET /tdms/Index?q=ticket/phonecall?pbx_id=2225460514&key=1257&event=3&datetime=20150921180333253&domain=tullini%2Epbx&company=1&call_id=12247&call_id_ext=&call_status=1&calling=23&called=3394110163&calling_id=4&calling_type=0 HTTP/1.1" 200 6693 "-" "Mozilla/3.0 (compatible; Indy Library)"
46.44.199.65 - - [21/Sep/2015:18:04:09 +0200] "GET /tdms/Index?q=ticket/phonecall?pbx_id=2225460514&key=1258&event=5&datetime=20150921180333366&domain=tullini%2Epbx&company=1&call_id=12247&call_id_ext=&call_status=1&calling=23&called=3394110163&called_id=4&called_type=2 HTTP/1.1" 200 6693 "-" "Mozilla/3.0 (compatible; Indy Library)"
46.44.199.65 - - [21/Sep/2015:18:04:11 +0200] "GET /tdms/Index?q=ticket/phonecall?pbx_id=2225460514&key=1259&event=6&datetime=20150921180335322&domain=tullini%2Epbx&company=1&call_id=12247&call_id_ext=&call_status=3&result=1&sender_id=4&sender_type=0&connect_id=4&connect_type=2&calling_id=4&calling_type=0&calling_local_id=244985&called_id=244983&called_type=5&called_local_id=244990 HTTP/1.1" 200 6693 "-" "Mozilla/3.0 (compatible; Indy Library)"
46.44.199.65 - - [21/Sep/2015:18:04:56 +0200] "GET /tdms/Index?q=ticket/phonecall?pbx_id=2225460514&key=1260&event=12&datetime=20150921180419264&domain=tullini%2Epbx&company=1&call_id=12247&call_id_ext=&call_status=7&outcome=1 HTTP/1.1" 200 6693 "-" "Mozilla/3.0 (compatible; Indy Library)"

	 */
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(PhoneCall.class);
	private static final Boolean TRACK_EXTENSIONS = false;	// il numero locale e' l'esterno o l'interno?
	// se NON si cancella dal registro, resta in memoria anche dopo il riaggancio (il ticket si puo' quindi aprire dopo) ... fino alle 2 ore (dopo c'e' il purge)
	private static final Boolean REMOVE_FROM_REGISTER_UPON_HANGING = false;	
	
	public static final String METHOD_NAME = "phonecall";
		
	protected static final String P_CALL_ID = "call_id";
	protected static final String P_DATETIME = "datetime";
	protected static final String P_CALL_STATUS = "call_status";
	protected static final String P_CALLED = "called";
	protected static final String P_CALLING = "calling";
	
	protected static HashMap<String, String> StatusMap;
	protected static HashMap<String, String> ExtensionsMap;
	
	protected static HashMap<String, HDChiamataTelefonica> CallRegister = new HashMap<String, HDChiamataTelefonica>();
	
	public PhoneCall(DefaultModule parentModule, String[] defaultParameters) {
		super(parentModule, defaultParameters);
		initStatusMap();
		initExtensionsMap();
	}
			
	private void initStatusMap() {
		if (StatusMap==null)	{
			StatusMap = new  HashMap<String, String>();
			StatusMap.put("0", "libero");
			StatusMap.put("1", "in ingresso");
			StatusMap.put("2", "in uscita");
			StatusMap.put("3", "attivo");
			StatusMap.put("4", "deviazione");
			StatusMap.put("5", "sta rispondendo");
			StatusMap.put("6", "sta rifiutando");
			StatusMap.put("7", "sta terminado");
			StatusMap.put("8", "terminata");
		}
	}
	
	private void initExtensionsMap() {
		if (ExtensionsMap==null)	{
			ExtensionsMap = new  HashMap<String, String>();
			ExtensionsMap.put("21", "Silvia");
			ExtensionsMap.put("22", "Luana");
			ExtensionsMap.put("23", "Fabio");
			ExtensionsMap.put("24", "Michele");
			ExtensionsMap.put("25", "Cordless Bologna");
			ExtensionsMap.put("41", "Michela");
			ExtensionsMap.put("42", "Eleonora");
			ExtensionsMap.put("43", "Cordless Ferrara");

		}
	}

	@Override
	public String doMethod(HttpServletRequest request,
			HttpServletResponse response) throws SerenaException {
		try	{
			String callID = request.getParameter(P_CALL_ID);
			String chiamante = request.getParameter(P_CALLING);
			String chiamato = request.getParameter(P_CALLED);
			String datetime = request.getParameter(P_DATETIME);
			String statoId = request.getParameter(P_CALL_STATUS);
			String stato = StatusMap.get(statoId);
			String locale = "";
			String remoto = "";
			String log = "Call: " + callID + "; Calling: "+chiamante + "; Called: "+chiamato +"; Stato: " + stato + "; timestamp: "+ datetime + ";";
			logger.debug("[PhoneCall]"+ log );

			HDChiamataTelefonica theCall = new HDChiamataTelefonica(); 
			theCall.setDurata(1);
			theCall.setNote(log);
			if (statoId.equals("1"))	{
				// inizio chiamata
				if (isOutgoing(chiamante)){
					theCall.setStato_chiamata(HDChiamataTelefonica.STATO_CHIAMATA__INVIATA);	
					locale = chiamante;
					remoto = chiamato;
				} else {		
					theCall.setStato_chiamata(HDChiamataTelefonica.STATO_CHIAMATA__RICEVUTA);
					locale = chiamato;
					remoto = chiamante;
				}
				theCall.setNumero_locale(locale);
				theCall.setNumero_remoto(remoto);
				theCall.setData(new Date());
				CallRegister.put(callID, theCall);
				logger.debug("Adding call " + callID + " to register");
			} else if (TRACK_EXTENSIONS && statoId.equals("4")) {
				// deviazione a interno
				theCall = CallRegister.get(callID);
				if (theCall!=null)	{
					theCall.setNumero_locale(chiamato);   // sovrascrive con INTERNO
					logger.debug("Diverting call " + callID + " to local " + chiamato);
				}
				
			} else if (statoId.equals("7")) {
				// fine chiamata
				theCall = CallRegister.get(callID);
				if (REMOVE_FROM_REGISTER_UPON_HANGING)	{
					CallRegister.remove(callID);
					logger.debug("Removing call " + callID + " from register");
				}
				if (theCall!=null)	{
					SerenaDate sdNow = new SerenaDate();
					SerenaDate sdStart = new SerenaDate(theCall.getData());
					int secs = sdNow.diffInSeconds(sdStart);
					int min = secs / 60;
					theCall.setDurata(new Integer(min));
					new TulliniHelpGestBeanDataManager().insertHDChiamataTelefonica(request, theCall);
				}
				purgeCallRegister();
			} else {
				return "IGNORE";
			}
					

		} catch (Exception e)	{
			String msg = "Errore in ricezione http get from centralino " + e.getMessage();
			logger.error(msg);
		}
		return "OK";
	}

	private void purgeCallRegister() {
		for (String  callKey: CallRegister.keySet())	{
			HDChiamataTelefonica call = CallRegister.get(callKey);
			SerenaDate callTime = new SerenaDate(call.getData());
			if (new SerenaDate().diffInHours(callTime)>2)	{
				CallRegister.remove(callKey);
				logger.debug("CallRegister: purging call " + call.getData().toString() + " Register size is now "+ CallRegister.size());				
			}
		}
		
	}

	protected boolean isOutgoing(String caller)	{
		return ExtensionsMap.keySet().contains(caller);
	}
	
	
	@Override
	public String getName()	{
		return METHOD_NAME;
	}
	
	// ultima chiamata IN CORSO per il numero passato 
	public static HDChiamataTelefonica getCurrentCallFor(String localNumber)	{
		logger.debug("What's current call for " + localNumber + "?");
		for (String  callKey: CallRegister.keySet())	{
			HDChiamataTelefonica call = CallRegister.get(callKey);
			if (call.getNumero_locale().equals(localNumber))	{
				logger.debug("It's " + call.getNumero_remoto());
				return call;	
			}
		}
		return null;		
	}
	
	
}

