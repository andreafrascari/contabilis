package eu.anastasis.tulliniHelpGest.tasks;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import eu.anastasis.tulliniHelpGest.utils.MetascadenzeProcessor;


public class GenerationCache {

	private static final Logger logger = Logger.getLogger(GenerationCache.class);

	/*
	 * le cache sono organizzate cosi:
	 * operatori: (#sent/stored, (#id_operatore,#id_scadenze))
	 * clienti: (#sent/stored, (#id_cliente,#id_scadenze))
	 */
	private static HashMap<String,HashMap<String,ArrayList<String>>> clientcache=null;
	private static HashMap<String,HashMap<String,ArrayList<String>>> operatorcache=null;
	private static String lastStoredDate=null;
	
	private static final String SENT_KEY="SENT";
	private static final String STORED_KEY="STORED";

	/** first request of the day resets cache
	 * 
	 * @param date
	 */
	
	public GenerationCache (String date)	{
		init(date);
	}
	
	private void init(String date)	{	
	if (!date.equals(lastStoredDate))	{
		lastStoredDate=date;
		operatorcache = new HashMap<String,HashMap<String,ArrayList<String>>>();
		operatorcache.put(STORED_KEY, new HashMap<String,ArrayList<String>>());
		operatorcache.put(SENT_KEY, new HashMap<String,ArrayList<String>>());
		clientcache = new HashMap<String,HashMap<String,ArrayList<String>>>();
		clientcache.put(STORED_KEY, new HashMap<String,ArrayList<String>>());
		clientcache.put(SENT_KEY, new HashMap<String,ArrayList<String>>());
		}
	}

	/**
	 * aggiungi item spedito alla cache dell'operatore
	 * @param date
	 * @param operatore
	 * @param scadenza
	 */
	public void assertItemSent4Operator(String operatore, String scadenza, String date){
		String thisInstance = scadenza+date;
		HashMap<String,ArrayList<String>> sent =operatorcache.get(SENT_KEY);
		if (!sent.containsKey(operatore))
			sent.put(operatore, new ArrayList<String>());		
		ArrayList<String> thisOp = sent.get(operatore);
		thisOp.add(thisInstance);
	}
	
	public void assertItemStored4Operator(String operatore, String scadenza, String date){
		String thisInstance = scadenza+date;
		HashMap<String,ArrayList<String>> sent =operatorcache.get(STORED_KEY);
		if (!sent.containsKey(operatore))
			sent.put(operatore, new ArrayList<String>());		
		ArrayList<String> thisOp = sent.get(operatore);
		thisOp.add(thisInstance);
	}
	
	public void assertItemSent4Client(String cliente, String scadenza, String date){
		String thisInstance = scadenza+date;
		HashMap<String,ArrayList<String>> sent =clientcache.get(SENT_KEY);
		if (!sent.containsKey(cliente))
			sent.put(cliente, new ArrayList<String>());		
		ArrayList<String> thisOp = sent.get(cliente);
		thisOp.add(thisInstance);
	}
	
	public void assertItemStored4Client(String cliente, String scadenza, String date){
		String thisInstance = scadenza+date;
		HashMap<String,ArrayList<String>> sent =clientcache.get(STORED_KEY);
		if (!sent.containsKey(cliente))
			sent.put(cliente, new ArrayList<String>());		
		ArrayList<String> thisOp = sent.get(cliente);
		thisOp.add(thisInstance);
	}
	
	public boolean itemStored4Operator(String operatore, String scadenza, String date){
		try	{
			String thisInstance = scadenza+date;
			return operatorcache.get(STORED_KEY).get(operatore).contains(thisInstance);
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * ATTENZIONE: TODO probabile errore: date e' la data della scadenza ... quindi le ricorrenti le invia solo la prima volta!
	 * @param operatore
	 * @param scadenza
	 * @param date
	 * @return
	 */
	public boolean itemSent4Operator(String operatore, String scadenza, String date){
		try	{
			String thisInstance = scadenza+date;
			boolean res = operatorcache.get(SENT_KEY).get(operatore).contains(thisInstance);
			logger.debug("Messaggio " + thisInstance + " gia' inviato ad operatore " + operatore + "?" + res);
			return res;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean itemStored4Client(String cliente, String scadenza, String date)	{
		try	{
			String thisInstance = scadenza+date;
			return clientcache.get(STORED_KEY).get(cliente).contains(thisInstance);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * ATTENZIONE: TODO probabile errore: date e' la data della scadenza ... quindi le ricorrenti le invia solo la prima volta!
	 * @param operatore
	 * @param scadenza
	 * @param date
	 * @return
	 */
	public boolean itemSent4Client(String cliente, String scadenza, String date)	{
		try	{
			String thisInstance = scadenza+date;
			return clientcache.get(SENT_KEY).get(cliente).contains(thisInstance);
		} catch (Exception e) {
			return false;
		}
	}
	
}
