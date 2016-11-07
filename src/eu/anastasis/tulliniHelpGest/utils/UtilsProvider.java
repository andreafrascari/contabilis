package eu.anastasis.tulliniHelpGest.utils;

import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.serena.persistence.CostantiArchitettura;
import eu.anastasis.serena.persistence.PersistenceConfiguration;


public class UtilsProvider {
	private static final Logger logger = Logger.getLogger(UtilsProvider.class);
	
	public  String formatImporto(float importo_forfait) {
		final Float f = new Float(importo_forfait);
		String theValue = null;
		try
		{
			final DecimalFormat currency = new DecimalFormat("#,###.00");
			theValue = currency.format(f);
		} catch (final Exception e)
		{
			logger.error("impossibile formattare importo " + new Float(f).toString() +": "+ e);
			return "0";
		}
		if (",00".equals(theValue))	{
			theValue = "0";
		}
		return theValue;
	}
	
	public String getDateTimeFormat() {
		String date_time_format=null;
			try {
				date_time_format = PersistenceConfiguration.GetInstance().getParametro(CostantiArchitettura.INTERFACE_DATETIME_FORMAT);
			} catch (SerenaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				date_time_format = "dd/MM/yyyy HH:mm:ss";
			}
		return date_time_format; 
	}

}
