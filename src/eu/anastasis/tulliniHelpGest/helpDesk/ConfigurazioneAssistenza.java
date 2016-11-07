package eu.anastasis.tulliniHelpGest.helpDesk;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.auth.PersistenceContainer4Auth;
import eu.anastasis.tulliniHelpGest.modules.BirtReport2DocumentoMethod;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.HDGruppoAssistenza;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Operatore;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.TulliniHelpGestBinder;
import eu.anastasis.serena.common.EntityBean;
import eu.anastasis.serena.constants.ConstantsBaseLibrary;
import eu.anastasis.serena.constants.ConstantsEntityBean;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.dataManager.DataManagerHome;
import eu.anastasis.serena.exception.SerenaException;

public class ConfigurazioneAssistenza {

	/* class:
	 */
	private static ConfigurazioneAssistenza TheSingleton = null;

	private static final Logger logger = Logger.getLogger(ConfigurazioneAssistenza.class);
	
	public static ConfigurazioneAssistenza getInstance() throws SerenaException	{
		if (TheSingleton==null)	{
			TheSingleton = new ConfigurazioneAssistenza();
			TheSingleton.initGruppiAssistenzaCache();	
		}
		return TheSingleton;
	}

	/* instance
	 */
	private HashMap<String,HDGruppoAssistenza> gruppi_assistenza_cache = null;
	
	/**
	 * cache <mail,Gruppo>
	 * @return
	 */
	public HashMap<String, HDGruppoAssistenza> getGruppi_assistenza_cache() {
		return gruppi_assistenza_cache;
	}

	public  void Reset(){
		logger.info("Resetting cache gruppi assistenza (email)");
		gruppi_assistenza_cache = null;
	}

	private void initGruppiAssistenzaCache() throws SerenaException	{
	try	{
		if (gruppi_assistenza_cache==null)
			gruppi_assistenza_cache= new HashMap<String, HDGruppoAssistenza>();
		String theSession = "mail2ticket";
		Element query = ObjectUtils.getXserenaRequestStandardHeader(theSession, ConstantsXSerena.ACTION_GET,HDGruppoAssistenza.INSTANCE_NAME);
		query.addAttribute(ConstantsXSerena.ATTR_TARGET, ConstantsXSerena.TARGET_SPECIFIED);
		query.addAttribute(ConstantsXSerena.ATTR_TARGET_LEVELS, "1");
		query.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_SELECT);
		Element cond = query.addElement(ConstantsXSerena.TAG_CONDITION);
		cond = cond.addElement(ConstantsEntityBean.DELETION_FLAG);
		cond.setText("0");
		cond = cond.addElement(ConstantsEntityBean.ACTIVATION_FLAG);
		cond.setText("1");
		
		for (String groundSlot : new EntityBean(HDGruppoAssistenza.INSTANCE_NAME).getGroundSlotNames())
		{
			query.addElement(groundSlot);
		}		
	
		
		DataManagerHome pHome = PersistenceContainer4Auth.getPersistence(); 
		Document data  = pHome.get(query.getDocument());
		
		Element dataElem = ApplicationLibrary.prepareDataForPresentation(data);
		String[] messages={"",""};
		int res = ConstantsXSerena.getXserenaRequestResult(dataElem, messages,HDGruppoAssistenza.INSTANCE_NAME);
		
		if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
		{
			logger.error("[ConfigurazioneAssistenza]->initGruppiAssistenzaCache has resulted into sql error:"+messages[0]);
			throw new SerenaException(messages[0],"generic_sql_error");
		}
		else if (res==ConstantsXSerena.XSERENA_RESULT_EMPTY)
		{
			logger.error("[ConfigurazioneAssistenza]->initGruppiAssistenzaCache has found no gruops");
			throw new SerenaException(messages[0],"nessun_gruppo_assistenza");
		}
		else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)
		{
			List<Element> gruppi = dataElem.selectNodes(".//"+HDGruppoAssistenza.INSTANCE_NAME);
			for (Element anInstance:gruppi)	{
				HDGruppoAssistenza hdga = (HDGruppoAssistenza)new TulliniHelpGestBinder().createHDGruppoAssistenza(anInstance);
				gruppi_assistenza_cache.put(hdga.getEmail(), hdga);
			}
		}
	} catch (Exception e){
		String msg = "[ConfigurazioneAssistenza]->initGruppiAssistenzaCache has resulted into error: "+e.getMessage();
		logger.error(msg);
		throw new SerenaException(msg);
	}
	}
	
	public String getGruopIdFromMail(String mail){
		HDGruppoAssistenza theG= getGruopFromMail(mail);
		if (theG!=null)
			return theG.getId();
		else
			return null;
	}

	public HDGruppoAssistenza getGruopFromMail(String mail){
		HDGruppoAssistenza ret =this.gruppi_assistenza_cache.get(mail);
		if (ret!=null)
			{	return ret;	}
		// controllo di secondo livello sugli alias
		if (mail !=null && !mail.isEmpty())	{
			for (HDGruppoAssistenza aGroup: this.gruppi_assistenza_cache.values())	{	
				if (aGroup.getAlias()!=null && mail.equals(aGroup.getAlias()))
						{	return aGroup;	}
			}
		}
		return null;
	}

}
