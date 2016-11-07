package eu.anastasis.it.tullinidms.kerio;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * 
 */

/**
 * @author mcarnazzo
 *
 */
public class Contact
{
	private Map<String, String> attributes;
	private Vector<String> mails;
	
	public Contact()
	{
		this.attributes = new HashMap<String, String>();
		this.mails = new Vector<String>();
	}
	
	@Override
	public String toString()
	{
		String ret = "";
		for (String key : this.attributes.keySet())
		{
			String value = this.attributes.get(key);
			ret += key + " = " + value + "\n";
		}
		return ret;
	}
	
	public String get(String key)
	{
		return this.attributes.get(key);
	}
	
	public void put(String key, String value)
	{
		this.attributes.put(key, value);
	}
	
	boolean containsKey(String key){
		return this.attributes.containsKey(key);
	}
	
	public void addMail(String mail)
	{
		if (!this.mails.contains(mail))
			this.mails.add(mail);
	}
	
	public Vector getMails()	{
		return this.mails;
	}
	
}
