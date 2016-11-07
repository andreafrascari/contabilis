package eu.anastasis.tulliniHelpGest.beans;


import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import eu.anastasis.serena.application.core.tasks.CronPersistenceHome;
import eu.anastasis.serena.application.modules.object.ObjectIndexer;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.common.BeanCache;
import eu.anastasis.serena.common.SerenaDate;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.serena.persistence.CostantiArchitettura;
import eu.anastasis.serena.persistence.PersistenceConfiguration;
import eu.anastasis.tulliniHelpGest.utils.UtilsProvider;

public class StoricoInvii {
	
	private static final Logger logger = Logger.getLogger(StoricoInvii.class);
	
	public static final String MY_CLASS = "StoricoInvii";
	public static final String ESITO_OK= "OK";
	
	private String 	messaggio=null;
	private String 	tipo_sollecito=null;
	private String 	destinatario=null;
	private String 	esito=null;
	public String getMessaggio() {
		return messaggio;
	}
	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}
	public String getTipo_sollecito() {
		return tipo_sollecito;
	}
	public void setTipo_sollecito(String tipo_sollecito) {
		this.tipo_sollecito = tipo_sollecito;
	}
	public String getDestinatario() {
		return destinatario;
	}
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		if (esito==null || esito.isEmpty())
			this.esito = ESITO_OK;
		else
			this.esito = esito;
	}
	
	public String insert() throws SerenaException	{
 		try	{
			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader("auto", ConstantsXSerena.ACTION_SET, MY_CLASS);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_INSERT);
			
			Element anItem=null;
			
			if (this.getMessaggio()==null)	{
				String msg = "Cannot Insert empty storico bean!";
				logger.error(msg);
				throw new SerenaException(msg);
			}
			anItem = currentElement.addElement("data");
			String now = new SerenaDate().getDateAsString(new UtilsProvider().getDateTimeFormat());
			anItem.setText(now);
			
			anItem = currentElement.addElement("messaggio");
			anItem.setText(this.getMessaggio());

			anItem = currentElement.addElement("destinatario");
			anItem.setText(this.getDestinatario());

			anItem = currentElement.addElement("esito");
			anItem.setText(this.getEsito());

			anItem = currentElement.addElement("tipo_sollecito");
			anItem.setText(this.getTipo_sollecito());
			
			Document data = new CronPersistenceHome().set(currentElement.getDocument());
			String[] messages2={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages2,MY_CLASS);
			String newID = null;
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				throw new SerenaException(messages2[0]);
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)	{
				newID = messages2[1];
				ObjectIndexer oI = new ObjectIndexer( BeanCache.getInstance().getInterfaceBean(MY_CLASS));
				oI.index(currentElement.getDocument(),newID);
			}
			return newID;
		} catch (Exception e) {
			String msg = "Cannot insert storico invii  due to  error:"+e.getMessage();
			logger.error(msg);
			throw new SerenaException(msg);
		}	
		
	}
	
	
}
