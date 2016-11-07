package eu.anastasis.tulliniHelpGest.beans;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import eu.anastasis.it.tullinidms.modules.StoriaDocumento;
import eu.anastasis.serena.application.core.tasks.CronPersistenceHome;
import eu.anastasis.serena.application.modules.object.ObjectIndexer;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.common.BeanCache;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.serena.persistence.CostantiArchitettura;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Scadenza;

public class NotificaScadenza {
	
	private static final Logger logger = Logger.getLogger(NotificaScadenza.class);
	
	public static final String INSTANCE_NAME = "NotificaScadenza";
	private String id_operatore=null; 
	private String id_cliente=null;
	private String id_richiesta=null;
	private String data=null;
	private String oggetto=null;
	private String fatto=null;
	

	private String descrizione=null;
	
	private static final String FattoSI="si";
	private static final String FattoNO="no";
	
	
	public String getId_operatore() {
		return id_operatore;
	}


	public void setId_operatore(String id_operatore) {
		this.id_operatore = id_operatore;
	}


	public String getId_cliente() {
		return id_cliente;
	}


	public void setId_cliente(String id_cliente) {
		this.id_cliente = id_cliente;
	}


	public String getId_richiesta() {
		return id_richiesta;
	}


	public void setId_richiesta(String id_richiesta) {
		this.id_richiesta = id_richiesta;
	}


	public String getData() {
		return data;
	}


	public void setData(String data) {
		this.data = data;
	}


	public String getOggetto() {
		return oggetto;
	}


	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}


	public String getDescrizione() {
		return descrizione;
	}


	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getFatto() {
		return fatto;
	}


	public void setFatto(String fatto) {
		this.fatto = fatto;
	}

	public String insert() throws SerenaException	{
 		try	{
			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader("auto", ConstantsXSerena.ACTION_SET, INSTANCE_NAME);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_INSERT);
			
			Element anItem=null;
			
			if (this.getData()==null)	{
				String msg = "Cannot Insert empty notifica bean!";
				logger.error(msg);
				throw new SerenaException(msg);
			}
			anItem = currentElement.addElement("data");
			anItem.setText(this.getData());
			
			anItem = currentElement.addElement("oggetto_scadenza");
			anItem.setText(this.getOggetto());

			anItem = currentElement.addElement("descrizione_scadenza");
			anItem.setText(this.getDescrizione());
			
			anItem = currentElement.addElement("fatto");
			anItem.setText(FattoNO);

			anItem = currentElement.addElement("inverse_of_notifiche_generate");
			anItem.setText(this.getId_richiesta());

			if (this.getId_cliente()!=null)	{
				anItem = currentElement.addElement("inverse_of_avvisi_scadenze_cliente");
				anItem.setText(this.getId_cliente());				
			}

			if (this.getId_operatore()!=null)	{
				anItem = currentElement.addElement("inverse_of_notifiche_scadenze_operatore");
				anItem.setText(this.getId_operatore());				
			}

			Document data = new CronPersistenceHome().set(currentElement.getDocument());
			String[] messages2={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages2,INSTANCE_NAME);
			String newID = null;
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				throw new SerenaException(messages2[0]);
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)	{
				newID = messages2[1];
				ObjectIndexer oI = new ObjectIndexer( BeanCache.getInstance().getInterfaceBean(INSTANCE_NAME));
				oI.index(currentElement.getDocument(),newID);
			}
			return newID;
		} catch (Exception e) {
			String msg = "Cannot insert notifica "+this.getOggetto()+ " due to  error:"+e.getMessage();
			logger.error(msg);
			throw new SerenaException(msg);
		}	
		
	}
	
	public boolean deleteForMetascadenza(String idMeta) throws SerenaException	{
		try	{
			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(INSTANCE_NAME, ConstantsXSerena.ACTION_SET, INSTANCE_NAME);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_DELETE);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATOR, CostantiArchitettura.DELETE_PHYSICAL);
			
			Element condition = currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
			condition = condition.addElement("inverse_of_notifiche_generate");
			condition = condition.addElement(Scadenza.INSTANCE_NAME);
			condition = condition.addElement("ID");
			condition.setText(idMeta);
			
			Document data =  new CronPersistenceHome().set(currentElement.getDocument());
			String[] messages2={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages2,StoriaDocumento.MY_CLASS);
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("deleteForMetascadenzahas resulted into sql error:"+messages2[0]);
				throw new SerenaException(messages2[0],"generic_sql_error");
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)
			{
				return true;
			}
			else
				return false;
		} catch (Exception e) {
			logger.error("deleteForMetascadenzahas resulted into sql error"+e.getMessage());
			throw new SerenaException(e.getMessage());
		} 
	}
}
