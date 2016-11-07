/**
 * 
 */
package eu.anastasis.serena.webservices;

/**
 * Wrapper delle attivita' USATO ESCLUSIVAMENTE DAL WEBSERVICE
 * @author mcarnazzo
 */
public class WsActivity
{
	private String id;
	private String description;

	
	public WsActivity(String id, String description)
	{
		super();
		this.id = id;
		this.description = description;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
}
