package eu.anastasis.it.tullinidms.modules;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import eu.anastasis.serena.application.core.modules.DefaultMethod;
import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.application.modules.object.ObjectMethod;
import eu.anastasis.serena.exception.SerenaException;


/**
 * Metodo che gestisce il post processing di un documento caricato, consistente nell'invio a chi di dovere
 * di mail/sms
 * @author afrascari
 *
 */
public class ResetClientsCacheMethod extends DefaultMethod
{

	private static final String METHOD_NAME = "resetclientscache";
	
	private static final Logger logger = Logger.getLogger(ObjectMethod.class);
	
	public ResetClientsCacheMethod(DefaultModule parentModule, String[] defaultParameters) {
		super(parentModule, defaultParameters);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String doMethod(HttpServletRequest request,
			HttpServletResponse response) throws SerenaException {
		
		new Cliente().reasetCache();
		return "Cache clienti aggiornata!";
	}


	@Override
	protected String getName()
	{
		return METHOD_NAME;
	}

	
	
}

