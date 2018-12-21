package eu.anastasis.tulliniHelpGest.helpDesk;


import org.apache.log4j.Logger;

public class PollStarter implements Runnable  {

	private static final Logger logger = Logger.getLogger(MailCheck.class);
	
	public void run() 
	{
		try
		{
			new MailCheck().checkAllRegisteredAccounts();
		} catch (Exception e)
		{
			logger.error("[PollStarter] could not run since " + e.getMessage());
		}
		
	}

}
