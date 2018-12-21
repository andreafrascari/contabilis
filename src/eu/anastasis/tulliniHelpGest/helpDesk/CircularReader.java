/**
 * 
 */
package eu.anastasis.tulliniHelpGest.helpDesk;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

import eu.anastasis.serena.logging.LogService;

/**
 * Reader circolare (legge da un reader, scrive su un writer)
 * usato dal binder degli oggetti per poter gestire XML grandi
 * @author mcarnazzo
 */
public class CircularReader extends PipedReader
{
	private static final Logger logger = Logger.getLogger(CircularReader.class);
	/**
	 * @param element L'element XML da leggere
	 * @throws IOException Problemi nel passare i dati dal reader al writer 
	 */
	public CircularReader(final Element element) 
	throws IOException
	{
		super();
		final PipedWriter out = new PipedWriter(this);
	
		new Thread(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					new XMLWriter(out).write(element);
					out.close();
				} catch (IOException e) 
				{
					logger.error("[CIRCULAR READER] Problemi nel passare i dati dal reader al writer" , e);
				}
			}
		}).start();
	}
}
