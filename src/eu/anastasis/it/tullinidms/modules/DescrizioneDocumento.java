package eu.anastasis.it.tullinidms.modules;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import eu.anastasis.serena.application.core.tasks.CronPersistenceHome;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.modules.object.ObjectMethod;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.exception.SerenaException;

public class DescrizioneDocumento {
	
	private static final Logger logger = Logger.getLogger(ObjectMethod.class);
	
	public static final String MY_CLASS= "DescrizioneDocumento";
	
	private String tipologia_documento=null; 	
	private String visibilita=null; 	
	private String azione_conseguente_caricamento=null; 	
	private String sollecito=null; 	
	private String template_testo_invio=null; 	
	private String template_oggetto_invio=null; 	
	private String template_sms=null; 	
	private String contenuto_azione=null;
	
	private static HashMap<String,DescrizioneDocumento> MYCACHE = null;
	
	private static HashMap<String,Boolean> VISIBILTY_CACHE = null;
	public static HashMap<String, Boolean> getCLIENT_VISIBILTY_CACHE()throws SerenaException {
		if (VISIBILTY_CACHE==null)
			new DescrizioneDocumento().loadCache();
		return VISIBILTY_CACHE;
	}
	
	public String getTipologia_documento() {
		return tipologia_documento;
	}
	public void setTipologia_documento(String tipologia_documento) {
		this.tipologia_documento = tipologia_documento;
	}
	public String getVisibilita() {
		return visibilita;
	}
	public void setVisibilita(String visibilita) {
		this.visibilita = visibilita;
	}
	public String getAzione_conseguente_caricamento() {
		return azione_conseguente_caricamento;
	}
	public void setAzione_conseguente_caricamento(
			String azione_conseguente_caricamento) {
		this.azione_conseguente_caricamento = azione_conseguente_caricamento;
	}
	public String getSollecito() {
		return sollecito;
	}
	public void setSollecito(String sollecito) {
		this.sollecito = sollecito;
	}
	public String getTemplate_testo_invio() {
		return template_testo_invio;
	}
	public void setTemplate_testo_invio(String template_testo_invio) {
		this.template_testo_invio = template_testo_invio;
	}
	public String getTemplate_oggetto_invio() {
		return template_oggetto_invio;
	}
	public void setTemplate_oggetto_invio(String template_oggetto_invio) {
		this.template_oggetto_invio = template_oggetto_invio;
	}
	public String getContenuto_azione() {
		return contenuto_azione;
	}
	public void setContenuto_azione(String contenuto_azione) {
		this.contenuto_azione = contenuto_azione;
	}
	
	public String getTemplate_sms() {
		return template_sms;
	}
	public void setTemplate_sms(String template_sms) {
		this.template_sms = template_sms;
	}
	
	public DescrizioneDocumento getInstance(String tipologia) throws SerenaException {
		if (MYCACHE==null)
			loadCache();
		return MYCACHE.get(tipologia);
	}
	public void reasetCache() throws SerenaException {
		loadCache();
	}
	private void loadCache() throws SerenaException {
		// TODO Auto-generated method stub
	try	{
		MYCACHE = new HashMap<String, DescrizioneDocumento>();
		
		VISIBILTY_CACHE = new HashMap<String, Boolean>();
		
		Element currentElement = ObjectUtils.getXserenaRequestStandardHeader("metadocument cache", ConstantsXSerena.ACTION_GET, MY_CLASS);
		currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_SELECT);
		currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET, ConstantsXSerena.TARGET_ALL);
		currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET_LEVELS, "0");
		
		String[] messages={"",""};
		Document resultData=new CronPersistenceHome().get(currentElement.getDocument());
		Element data = ApplicationLibrary.prepareDataForPresentation(resultData);
		int res = ConstantsXSerena.getXserenaRequestResult(data, messages,MY_CLASS);

		if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
		{
			logger.error("Cannot load metadocument cache "+messages[0]);
			throw new SerenaException(messages[0],"generic_sql_error");
		}
		else if (res==ConstantsXSerena.XSERENA_RESULT_EMPTY)
		{
			logger.error("Cannot load metadocument cache: no metadoc found");
			throw new SerenaException(messages[0],"generic_sql_error");		
		}
		else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)
		{
			List<Element> metadocs = data.selectNodes(".//"+MY_CLASS);
			for (Element ametadoc:metadocs)	{
				DescrizioneDocumento metadoc = new DescrizioneDocumento();
				
				if (ametadoc.element("azione_conseguente_caricamento")!=null)
					metadoc.setAzione_conseguente_caricamento(ametadoc.elementText("azione_conseguente_caricamento"));
				
				if (ametadoc.element("contenuto_azione")!=null)
					metadoc.setContenuto_azione(ametadoc.elementText("contenuto_azione"));
				
				metadoc.setSollecito(ametadoc.elementText("sollecito"));
				
				if (ametadoc.element("template_oggetto_invio")!=null)
					metadoc.setTemplate_oggetto_invio(ametadoc.elementText("template_oggetto_invio"));
				
				if (ametadoc.element("template_testo_invio")!=null)
					metadoc.setTemplate_testo_invio(ametadoc.elementText("template_testo_invio"));
				
				if (ametadoc.element("template_sms")!=null)
					metadoc.setTemplate_sms(ametadoc.elementText("template_sms"));
				
				String tipologia = ametadoc.elementText("tipologia_documento");
				metadoc.setTipologia_documento(tipologia);
				
				String visibilita = ametadoc.elementText("visibilita");
				metadoc.setVisibilita(visibilita);
				
				MYCACHE.put(metadoc.getTipologia_documento(), metadoc);
				
				boolean visibility = false;
				try	{
					visibility = visibilita.contains("visibile cliente");
				} catch (Exception e) {
					// never mind ... it'll be false
					logger.error("cannot determine visibility for metadoc " + tipologia + ": assuming false");
				}
				VISIBILTY_CACHE.put(tipologia,new Boolean(visibility));
				
			}
		}
	} catch  (Exception any)	{
		String msg = "Could not load metadocument cache: "+any.getMessage();
		logger.error(msg);
		throw new SerenaException(msg);
	}
}

	
}
