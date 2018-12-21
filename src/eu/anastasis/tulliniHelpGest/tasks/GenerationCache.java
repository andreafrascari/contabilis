package eu.anastasis.tulliniHelpGest.tasks;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;


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
	
	protected ArrayList<String> initItemSent4Operator(String operatore)	{
		HashMap<String,ArrayList<String>> sent =operatorcache.get(SENT_KEY);
		if (!sent.containsKey(operatore))	{
			sent.put(operatore, new ArrayList<String>());
		}
		return sent.get(operatore);
	}
	
	protected ArrayList<String> initItemStored4Operator(String operatore)	{
		HashMap<String,ArrayList<String>> stored =operatorcache.get(STORED_KEY);
		if (!stored.containsKey(operatore))	{
			stored.put(operatore, new ArrayList<String>());
		}
		return stored.get(operatore);
	}
	
	protected ArrayList<String> initItemSent4Client(String client)	{
		HashMap<String,ArrayList<String>> sent =clientcache.get(SENT_KEY);
		if (!sent.containsKey(client))	{
			sent.put(client, new ArrayList<String>());
		}
		return sent.get(client);
	}
	
	protected ArrayList<String> initItemStored4Client(String client)	{
		HashMap<String,ArrayList<String>> stored =clientcache.get(STORED_KEY);
		if (!stored.containsKey(client))	{
			stored.put(client, new ArrayList<String>());
		}
		return stored.get(client);
	}

	public void assertItemSent4Operator(String operatore, String scadenza, String date){
		String thisInstance = scadenza+date;
		ArrayList<String> thisOp = initItemSent4Operator(operatore);
		thisOp.add(thisInstance);
	}
	
	public void assertItemStored4Operator(String operatore, String scadenza, String date){
		String thisInstance = scadenza+date;
		ArrayList<String> thisOp = initItemStored4Operator(operatore);
		thisOp.add(thisInstance);
	}
	
	public void assertItemSent4Client(String cliente, String scadenza, String date){
		String thisInstance = scadenza+date;
		ArrayList<String> thisCli = initItemSent4Client(cliente);
		thisCli.add(thisInstance);
	}
	
	public void assertItemStored4Client(String cliente, String scadenza, String date){
		String thisInstance = scadenza+date;
		ArrayList<String> thisCli = initItemStored4Client(cliente);
		thisCli.add(thisInstance);
	}
	
	public boolean itemStored4Operator(String operatore, String scadenza, String date){
		String thisInstance = scadenza+date;
		ArrayList<String> thisOp = initItemStored4Operator(operatore);
		return thisOp.contains(thisInstance);
	}
	
	/**
	 * ATTENZIONE: TODO probabile errore: date e' la data della scadenza ... quindi le ricorrenti le invia solo la prima volta!
	 * @param operatore
	 * @param scadenza
	 * @param date
	 * @return
	 */
	public boolean itemSent4Operator(String operatore, String scadenza, String date){
		String thisInstance = scadenza+date;
		ArrayList<String> thisOp = initItemSent4Operator(operatore);
		boolean res =thisOp.contains(thisInstance);
		logger.debug("itemSent4Operator " + operatore + " - " + scadenza + " - " + date+ ": "+ res);
		return res;
	}
	
	public boolean itemStored4Client(String cliente, String scadenza, String date)	{
		String thisInstance = scadenza+date;
		ArrayList<String> thisCli = initItemStored4Client(cliente);
		return thisCli.contains(thisInstance);
	}

	/**
	 * ATTENZIONE: TODO probabile errore: date e' la data della scadenza ... quindi le ricorrenti le invia solo la prima volta!
	 * @param operatore
	 * @param scadenza
	 * @param date
	 * @return
	 */
	public boolean itemSent4Client(String cliente, String scadenza, String date)	{
		String thisInstance = scadenza+date;
		ArrayList<String> thisCli = initItemSent4Client(cliente);
		boolean res =thisCli.contains(thisInstance);
		logger.debug("itemSent4Client " + cliente + " - " + scadenza + " - " + date+ ": "+ res);
		return res;
	}
	
}
