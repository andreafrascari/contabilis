
package eu.anastasis.it.tullinidms.kerio;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.log4j.Logger;

import eu.anastasis.it.tullinidms.modules.Cliente;

/**
 * 
 */

/**
 * Test del server LDAP dello Studio Tullini
 * @author mcarnazzo
 */
public class KerioLdapHandler
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(KerioLdapHandler.class);
	
	private static final String SEARCH_BASE = "fn=ContactRoot";

	/**
	 * Kerio ha un limite di 200 risultati per ogni richiesta. Risolviamo con questo accrocchio:
	 * in questo array abbiamo tutti i potenziali 'inizia con' per il 'cn' cosi' da poter fare richieste con risultati piu' piccoli
	 * per poi mettere il tutto insieme
	 */
	private static String[] STARTS_WITH_FILTERS = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", 
		"0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

	
	private String providerUrl;
	private String username;
	private String password;
	
	private static final String UID_ATTRIBUTE = "ICQ";

	private static final String KERIO_NAME_ATTRIBUTE = "cn";
	private static final String KERIO_MAIL_ATTRIBUTE = "mail";
	private static final String KERIO_OTHERMAIL_ATTRIBUTE = "otherMailbox";
	private static final String KERIO_CELL_ATTRIBUTE = "mobile";
	private static final String KERIO_CODCLI_ATTRIBUTE = "ICQ";
	private static final String MESSAGENET_FAX_IDENTIFIER = "@fax.messagenet.it";
	
	private static final String KERIO_NOME_ATTRIBUTE = "givenName";
	private static final String KERIO_COGNOME_ATTRIBUTE = "sn";
	private static final String KERIO_MID_ATTRIBUTE = "middleName";
	
	public KerioLdapHandler(String providerUrl, String username, String password)
	{
		this.providerUrl = providerUrl;
		this.username = username;
		this.password = password;
	}
	
	
	private DirContext connect()
	throws NamingException
	{
		Properties env = new Properties();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, this.providerUrl);
		env.put(Context.SECURITY_PRINCIPAL, this.username);
		env.put(Context.SECURITY_CREDENTIALS, this.password);

		DirContext ret = new InitialDirContext(env);
		return ret;
	}
	
	public Collection<Cliente> getAllContacts() throws NamingException
	{
		Map<String, Cliente> ret = new HashMap<String, Cliente>();
		
		DirContext context = null;
		try
		{
			context = connect();
			
			logger.trace("KerioLdapHandler is starting client-fetch run");
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
			
			for (String letter : STARTS_WITH_FILTERS)
			{
				loadContacts(context, constraints, "uid=" + letter + "*", ret);				
			}

			logger.trace("KerioLdapHandler has finished client-fetchrun");
		} catch (NamingException e)
		{
			throw e;
		} finally
		{
			if (context != null)
			{
				try
				{
					context.close();
				} catch (NamingException e)
				{
					throw e;
				}
			}
		}

		return ret.values();
	}


	private void loadContacts(DirContext context, SearchControls constraints, String filter, Map<String, Cliente> contacts)
			throws NamingException
	{
		NamingEnumeration<SearchResult> results = context.search(SEARCH_BASE, filter, constraints);
		logger.debug("Loading contacts from Kerio");
		while (results.hasMoreElements())
		{
			// prima registriamo tutti gli attributi in una mappa
			Contact contact = new Contact();
			SearchResult result = results.next();
			Attributes attributes = result.getAttributes();
			
			NamingEnumeration<String> attributeIds = attributes.getIDs();
			while (attributeIds.hasMoreElements())
			{
				String attributeId = attributeIds.next();
				// System.out.println(attributeId);
				Attribute attribute = attributes.get(attributeId);
				@SuppressWarnings("rawtypes")
				NamingEnumeration e = attribute.getAll();
				while (e.hasMoreElements())
				{
					String attributeValue = e.next().toString();
					addCouple(contact, attributeId, attributeValue);
				}
			}
			String uid = contact.get(UID_ATTRIBUTE);
			//System.out.println("WHO AM I? "+uid);
			
			// poi, se si tratta di un cliente, istanziamo un Cliente vero e proprio
			if (isThisAclient(contact))	
			{
				Cliente unCliente = new Cliente();
				unCliente.setCodicecliente(contact.get(KERIO_CODCLI_ATTRIBUTE));
//				String email = contact.get(KERIO_MAIL_ATTRIBUTE);
				String cell = contact.get(KERIO_CELL_ATTRIBUTE);
				if (cell==null)
					cell="";
				try	{
					cell = cell.replaceAll("-","");
					cell = cell.replaceAll(" ","");
					cell = cell.replaceAll(",",", ");
				} catch (Exception e2) {
						// leave it!!!!
				}
				String nomeComposto = "";
				if (contact.get(KERIO_COGNOME_ATTRIBUTE)!=null)
					nomeComposto+=contact.get(KERIO_COGNOME_ATTRIBUTE)+ " ";
				if (contact.get(KERIO_MID_ATTRIBUTE)!=null)
					nomeComposto+=contact.get(KERIO_MID_ATTRIBUTE)+ " ";
				if (contact.get(KERIO_NOME_ATTRIBUTE)!=null)
					nomeComposto+=contact.get(KERIO_NOME_ATTRIBUTE);
				unCliente.setNome(nomeComposto);
				// unCliente.setNome(contact.get(KERIO_NAME_ATTRIBUTE));
				
				if (cell.isEmpty())
					unCliente.setCellulare("");
				else
					unCliente.setCellulare(cell);
				
				@SuppressWarnings("unchecked")
				Vector<String> emails = contact.getMails();
				int mailIndex=0;
				// azzero mail e fax:
				unCliente.setFax("");
				unCliente.setEmail("");
				unCliente.setEmail2("");
				unCliente.setEmail3("");
				
				for (String anEmail:emails)	{
					if (anEmail==null || anEmail.isEmpty())	{
						continue;
					}
					if (anEmail.contains(MESSAGENET_FAX_IDENTIFIER))
						unCliente.setFax(anEmail);
					else {
						switch (++mailIndex){
						case 1:	{
							unCliente.setEmail(anEmail);
							break;
							}
						case 2:	{
							unCliente.setEmail2(anEmail);
							break;
							}
						case 3:	{
							unCliente.setEmail3(anEmail);
							break;
							}
						}
						
					}
				}			
				contacts.put(uid, unCliente);
			}
		}		
	}

	/**
	 * This is because some attributes recurr more than once, and are therefor suffixed with an increasing number
	 * @param contact
	 * @param attributeId
	 * @param attributeValue
	 */
	private void addCouple(Contact contact, String attributeId, String attributeValue) {
		if (KERIO_OTHERMAIL_ATTRIBUTE.equals(attributeId) || KERIO_MAIL_ATTRIBUTE.equals(attributeId))	{
			//System.out.println("adding mail: "+attributeId+"->"+attributeValue);
			contact.addMail(attributeValue);
			}
		else contact.put(attributeId, attributeValue);
			return;
	}


	/**
	 * si tratta di un cliente se l'attributo KERIO_CODCLI_ATTRIBUTE è valorizzato
	 * @param contact
	 * @return
	 */
	private boolean isThisAclient(Contact contact) {
		String codcli =contact.get(KERIO_CODCLI_ATTRIBUTE); 
		boolean res = (codcli != null && !codcli.isEmpty());
		logger.trace("isThisAclient? " + codcli + "->" + res);
		return res;
	}
	

}
