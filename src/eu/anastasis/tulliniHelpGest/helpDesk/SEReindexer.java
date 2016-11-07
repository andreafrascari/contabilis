package eu.anastasis.tulliniHelpGest.helpDesk;

import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Element;

import eu.anastasis.serena.application.core.tasks.CronPersistenceHome;
import eu.anastasis.serena.application.index.util.ApplicationConfiguration;
import eu.anastasis.serena.application.modules.object.ObjectIndexer;
import eu.anastasis.serena.application.modules.searchEngine.SearchEngineHome;
import eu.anastasis.serena.common.BeanCache;

public class SEReindexer implements Runnable 
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SEReindexer.class);
	private static CronPersistenceHome cph = null;
	
	/**
	 * Attention!!! It's static
	 * @return
	 */
	private CronPersistenceHome getMyCronPersistenceHome(){
		if (cph==null)
			cph = new CronPersistenceHome();
		return cph; 
	}
	
	public void run() 
	{
		try
		{
			logger.trace("Automatically Resettting SE");
			new SearchEngineHome().reset();
			logger.trace("Automatically Reindexing all register classes");
			List<String> theClasses = ApplicationConfiguration.GetInstance().getSearchEngineIndexableClasses();
			// anything I want to remove from list?
			for (String aClass: theClasses)
			{
				aClass = aClass.trim();
				Element interfaceRoot;
				try 
				{
					interfaceRoot = BeanCache.getInstance().getInterfaceBean(aClass);
					if (new ObjectIndexer(interfaceRoot).reindex_all_instances(getMyCronPersistenceHome()))
					{
						logger.trace("Succesfully reindexed: "+aClass);
					} else
					{
						logger.warn("Error while reindexing: "+aClass);
					}
				} catch (Exception e) 
				{
					logger.warn("Error while reindexing: "+aClass+" - "+e.getMessage());
				}
			}
			logger.trace("DONE: goodbye.");
		} catch (Exception e) 
		{
			logger.warn("Error while reindexing:"+e.getMessage());
		}
	}
	

}
