/**
 * 
 */
package eu.anastasis.serena.webservices;

import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import eu.anastasis.it.tullinidms.ConstantsTulliniDMS;
import eu.anastasis.it.tullinidms.modules.DescrizioneDocumento;
import eu.anastasis.serena.common.XSerenaLibrary;
import eu.anastasis.serena.query.SelectQuery;

/**
 * Webservice usato dal Document Agent
 * @author mcarnazzo
 */
public class TulliniWebService extends PersistenceWebService
{
	
	private static final Logger logger = Logger.getLogger(TulliniWebService.class);
	
	public String[] getTags() throws Exception
	{
		List<String> ret = new Vector<String>();
		logger.info("WS getTags has been called");
		SelectQuery query = new SelectQuery(DescrizioneDocumento.MY_CLASS);
		Document data = getData(query);
		List<Element> elements = XSerenaLibrary.selectDataClasses(data);
		for (Element element : elements)
		{
			String tag = element.elementText(ConstantsTulliniDMS.DOCUMENT_DESCRIPTION_TYPE_SLOT);
			ret.add(tag);
		}
		
		String[] retArray = new String[ret.size()];
		return ret.toArray(retArray);
	}
}
