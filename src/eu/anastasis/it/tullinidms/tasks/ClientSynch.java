package eu.anastasis.it.tullinidms.tasks;

import java.util.Collection;

import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import eu.anastasis.it.tullinidms.kerio.KerioLdapHandler;
import eu.anastasis.it.tullinidms.modules.Cliente;
import eu.anastasis.serena.application.index.Index;
import eu.anastasis.serena.application.modules.object.ObjectIndexer;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.common.BeanCache;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.dataManager.DataManagerHome;

/**
 * Lanciato da una sorta di cron settato da config_tasks, controlla gli operatori che hanno data_ultima_modifica_password 
 * a NULL (mai settato) o "scaduta" con riferimento la 196 (3 mesi) e ne mette activation_flag a 0.
 * In questo modo, gli operatori, al logon successivo, verranno ridirezionati al cambio password.
 * Invia inoltre una notifica via mail 7 giorni prima della scadenza 
 * @author mtassetti
 */
public class ClientSynch implements Runnable 
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ClientSynch.class);

	DataManagerHome persistence=Index.getPersistence().getRealPersistence();
	
//	private final static String PROVIDER_URL = "ldap://94.141.19.170"; old server
	private final static String PROVIDER_URL = "ldap://46.44.199.65";
	private final static String USERNAME = "fabio";
	private final static String PASSWORD = "GIORGIO2";

	private static boolean IS_ACTIVE = false;
			
	
	public void run() 
	{
		if (!IS_ACTIVE){
			logger.debug("ClientSynch IS NOT ACTIVE");
			return;
		}
		logger.debug("ClientSynch has sterted");
		KerioLdapHandler kerio = new KerioLdapHandler(PROVIDER_URL, USERNAME, PASSWORD);
		try
		{
			Collection<Cliente> contacts = kerio.getAllContacts();
			// List<Cliente> contacts = kerio.getFakeContacts();
			for (Cliente unCliente : contacts)
			{
				updateCliente(unCliente);
			}
		new Cliente().reasetCache();
		} catch (NamingException e)
		{
			e.printStackTrace();
			logger.fatal("CClientSynch cannot synchronise Kerio contacts: "+e.toString());
		} catch (Exception e2)
		{
			e2.printStackTrace();
			logger.fatal("ClientSynch cannot synchronise Kerio contacts: "+e2.toString());
		}				
	}

	private void updateCliente(Cliente unCliente) {
		try	{
			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader("agent", ConstantsXSerena.ACTION_SET, Cliente.MY_CLASS);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_UPDATE);
			
			Element condition = currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
			condition = condition.addElement("codice_cliente");
			condition.setText(unCliente.getCodicecliente());
			
			Element anItem=null;
			
			if (unCliente.getCellulare()!=null)	{
				anItem = currentElement.addElement("cellulare");
				anItem.setText(unCliente.getCellulare());
			}
			
			if (unCliente.getEmail()!=null)	{
				anItem = currentElement.addElement("email");
				anItem.setText(unCliente.getEmail());
			}

			if (unCliente.getEmail2()!=null)	{
				anItem = currentElement.addElement("email2");
				anItem.setText(unCliente.getEmail2());
			}
			
			if (unCliente.getEmail3()!=null)	{
				anItem = currentElement.addElement("email3");
				anItem.setText(unCliente.getEmail3());
			}

			if (unCliente.getFax()!=null)	{
				anItem = currentElement.addElement("fax");
				anItem.setText(unCliente.getFax());
			}

			anItem = currentElement.addElement("cliente");
			anItem.setText(unCliente.getNome());
			
			Document data = persistence.set(currentElement.getDocument());
			String[] messages2={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages2,Cliente.MY_CLASS);
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.fatal("cannot update client"+unCliente.getCodicecliente()+ " due to  error:"+messages2[0]);
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)	{
				ObjectIndexer oI = new ObjectIndexer( BeanCache.getInstance().getInterfaceBean(Cliente.MY_CLASS));
				oI.index(currentElement.getDocument(),messages2[1]);
			}
		} catch (Exception e) {
			logger.fatal("cannot update client"+unCliente.getCodicecliente()+ "  due to  error:"+e.getMessage());
		}				
	}

	
}