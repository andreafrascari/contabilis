package eu.anastasis.tulliniHelpGest.modules;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import eu.anastasis.serena.application.core.modules.DefaultMethod;
import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.IndicizzazioneListino;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.MyIndicizzazioneListino;

import org.apache.log4j.Logger;


/**
 * Metodo che gestisce il post processing di un documento caricato, consistente nell'invio a chi di dovere
 * di mail/sms
 * @author afrascari
 *
 */
public class ResetIndicizzazioneCacheMethod extends DefaultMethod
{

	private static final String METHOD_NAME = "resetindicizzazionecache";
	
	private static final Logger logger = Logger.getLogger(ResetIndicizzazioneCacheMethod.class);
	
	public ResetIndicizzazioneCacheMethod(DefaultModule parentModule, String[] defaultParameters) {
		super(parentModule, defaultParameters);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String doMethod(HttpServletRequest request,
			HttpServletResponse response) throws SerenaException {
		
		new MyIndicizzazioneListino().reasetCache();
		return "Cache indicizzazione listino aggiornata!";
	}


	@Override
	protected String getName()
	{
		return METHOD_NAME;
	}

	
	
}

