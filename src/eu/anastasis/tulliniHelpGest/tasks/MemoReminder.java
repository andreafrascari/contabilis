package eu.anastasis.tulliniHelpGest.tasks;

import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Scadenza;
import eu.anastasis.tulliniHelpGest.utils.MetascadenzeProcessor;

public class MemoReminder implements Runnable 
{
 
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MemoReminder.class);
	
	public void run() 
	{
		try
		{
			/**
			 * Fetch delle scadenze di oggi (al netto del preavviso dinamico) per invio notifiche
			 */
			logger.trace("MemoReminder is running..");
			MetascadenzeProcessor processor = new MetascadenzeProcessor();
			
			Element richieste = processor.getRichiesteScadenze("oneoff-today");
			if (richieste!=null)	{
				List<Element> requests= richieste.selectNodes(".//"+Scadenza.INSTANCE_NAME);
				for (Element aReq:requests)
				{
					processor.processRequest(aReq,true,true);	// per ogni richiesta, processa con invio = true e record = true
				}
			}
			/**
			 * Fetch delle scadenze con 3 mesi di anticipo per inserimento bacheca
			 */
			richieste = processor.getRichiesteScadenze("oneoff-3mAhead");
			if (richieste!=null)	{
				List<Element> requests= richieste.selectNodes(".//"+Scadenza.INSTANCE_NAME);
				for (Element aReq:requests)
				{
					processor.processRequest(aReq,false,true);	// per ogni richiesta, processa con invio = false ma insert in bacheca
				}
			}
			/**
			 * Fetch delle scadenze ricorrenti per 
			 */
			richieste = processor.getRichiesteScadenze("ricorrenti");
			if (richieste!=null)	{
				// A) al netto del preavviso dinamico, per invio notifiche (parametro a true)
				List<Element> toBeSent = processor.processRicorrenti(richieste,true);
				for (Element aReq:toBeSent)
				{
					processor.processRequest(aReq,true,true);	// per ogni richiesta, processa con invio = true, registrazione = true (alla peggio, esiste già)
				}
				// B) con 3 mesi di anticipo, per inserimento nelle bacheche (parametro a false)
				List<Element> toBeStored = processor.processRicorrenti(richieste,false);
				for (Element aReq:toBeStored)
				{
					processor.processRequest(aReq,false,true);	// per ogni richiesta, processa con invio = true, registrazione = false
				}
			}
			logger.trace("MemoReminder is quitting..");
		} catch (SerenaException e)
		{
			logger.error(e.getMessage());
		}
		
	}
	
}