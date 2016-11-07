package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import eu.anastasis.serena.application.core.tasks.CronPersistenceHome;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.common.SerenaDate;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.exception.SerenaException;

public class MyIndicizzazioneListino extends IndicizzazioneListino {
	
	private static final Logger logger = Logger.getLogger(MyIndicizzazioneListino.class);
	
	// #(anno, IndicizzazioneListino)
		private static HashMap<String,IndicizzazioneListino> MYCACHE = null;

	public IndicizzazioneListino getInstance(String ID) throws SerenaException {
		if (MYCACHE==null)
			loadCache();
		return MYCACHE.get(ID);
	}
	
	public  Collection<IndicizzazioneListino> allInstances()	throws SerenaException {
		if (MYCACHE==null)
			loadCache();
		return MYCACHE.values();
	}
	 	
	public void reasetCache() throws SerenaException {
		loadCache();
	}
	
	/**
	 * Attualizza una valore riferito ad una data in base all'indicizzazione dell'anno corrente 
	 * @param valore
	 * @param dataRiferimento
	 * @return
	 * @throws SerenaException
	 */
	public Float attualizzaValore(String valore, String dataRiferimento) throws SerenaException	{
		if (MYCACHE==null)
			loadCache();
		float valoreF = new Float(valore).floatValue();
		SerenaDate serenaDater = new SerenaDate(dataRiferimento);
		int annoListino = serenaDater.getYear();
		int annoCorrente = new SerenaDate().getYear();
		for (int anno=annoListino+1; anno<=annoCorrente; anno++){
			String annoIndex = new Integer(anno).toString();
			IndicizzazioneListino indicizzazioneAnno = MYCACHE.get(annoIndex);
			if (indicizzazioneAnno==null)	{
				String msg = "Anno "+annoIndex+" non indicizzato in configurazione sistema. E' necessario provvedere prima di generare le pratiche.";
				logger.error(msg);
				throw new SerenaException(msg);
			}
			// es: 2 che sta per 2%
			float annoIndice = indicizzazioneAnno.getIndice().floatValue();
			valoreF += (valoreF*annoIndice/100);
		}
		return new Float(valoreF);		
	}
	
	public void setIndice(java.lang.String indice) {
		this.indice = new Float(indice);
	}


	public void loadCache() throws SerenaException {
		// TODO Auto-generated method stub
	try	{
		MYCACHE = new HashMap<String, IndicizzazioneListino>();
		
		logger.trace("Loading cache indicizzazione listini...");
		Element currentElement = ObjectUtils.getXserenaRequestStandardHeader("indlistcache", ConstantsXSerena.ACTION_GET, INSTANCE_NAME);
		currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_SELECT);
		currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET, ConstantsXSerena.TARGET_ALL);
		currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET_LEVELS, "0");
		
		String[] messages={"",""};
		Document resultData=new CronPersistenceHome().get(currentElement.getDocument());
		Element data = ApplicationLibrary.prepareDataForPresentation(resultData);
		int res = ConstantsXSerena.getXserenaRequestResult(data, messages,INSTANCE_NAME);

		if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
		{
			logger.error("Cannot startup IndicizzazioneListiniCache "+messages[0] );
			throw new SerenaException(messages[0],"generic_sql_error");
		}
		else if (res==ConstantsXSerena.XSERENA_RESULT_EMPTY)
		{
			logger.error("Cannot startup IndicizzazioneListiniCache");
			throw new SerenaException("nessun Indicizzazione Listini ");
		}
		else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)
		{
			List<Element> gliIndici= data.selectNodes(".//"+INSTANCE_NAME);
			for (Element unIndice: gliIndici)	{
				MyIndicizzazioneListino unIndiceObj = new MyIndicizzazioneListino();
				String theID = unIndice.elementText("ID");
				unIndiceObj.setId(theID);
				String anno = unIndice.elementText(SLOT_ANNO_CONTABILE);
				unIndiceObj.setAnno_contabile(anno);
				unIndiceObj.setIndice(unIndice.elementText(SLOT_INDICE));			
				if (unIndice.element(SLOT_NOTE)!=null)
					unIndiceObj.setNote(unIndice.elementText(SLOT_NOTE));
				MYCACHE.put(anno, unIndiceObj);				
			}			
		}
		logger.trace("DONE!");
	} catch  (Exception any)	{
		String msg = "Could not load cache indicizzazione listini: "+any.getMessage();
		logger.error(msg);
		throw new SerenaException(msg);
		}
	}

}
