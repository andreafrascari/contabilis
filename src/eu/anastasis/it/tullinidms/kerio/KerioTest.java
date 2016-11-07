package eu.anastasis.it.tullinidms.kerio;

import javax.servlet.ServletException;

import eu.anastasis.it.tullinidms.tasks.ClientSynch;
import eu.anastasis.serena.application.index.Index;


/**
 * @author mcarnazzo
 *
 */
public class KerioTest
{

	
	
	public static void main(String[] args)
	{
		try {
			(new Index()).init();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new ClientSynch().run();
	}
	
}
