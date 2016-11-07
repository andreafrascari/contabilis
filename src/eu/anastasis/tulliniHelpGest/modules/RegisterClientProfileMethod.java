package eu.anastasis.tulliniHelpGest.modules;


import java.util.Vector;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import javax.servlet.http.HttpServletRequest;
import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.application.index.IndexQueryException;
import eu.anastasis.serena.application.index.constants.ConstantsSession;
import eu.anastasis.serena.application.modules.object.DetailEditMethod;
import eu.anastasis.serena.dataManager.DataManagerException;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.ProfiloDinamicoClienti;

 
public class RegisterClientProfileMethod extends DetailEditMethod 
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RegisterClientProfileMethod.class);


	private static final String METHOD_NAME = "registraprofiloclienti";
	private static final String XQUERY_PARAM = "xml";

	public RegisterClientProfileMethod(DefaultModule parentModule,String[] defaultParameters)
	{
		super(parentModule, defaultParameters);
	}

	@Override
	protected String getName()
	{
		return METHOD_NAME;
	}

	@Override
	protected Document buildQueryInsert(HttpServletRequest request) throws IndexQueryException, DataManagerException
	{
		final Document q = buildQueryInsertFromHttpPost(request);
		// ora se deve sostituire il contenuto dell'attributio query da indice a query in cache
		Element query = (Element)q.selectSingleNode(".//"+ProfiloDinamicoClienti.SLOT_QUERY);
		if (query==null | query.getText().isEmpty())	{
			throw new IndexQueryException("Query vuota");
		}
		String queryId = query.getText();
		String xml;
		try {
			xml = this.fetchQueryCondition(request, queryId);
		} catch (SerenaException e) {
			throw new IndexQueryException("Impossibile recuperare dalla cache query n. "+queryId);
		}
		query.setText(xml);
		return q;
	}
	
	protected String fetchQueryCondition(HttpServletRequest request, String queryId) throws SerenaException
	{
		String xquery = null;
		try
		{
			if ((queryId != null) && !queryId.isEmpty())
			{
				final Vector<String> queryCache = (Vector<String>) request.getSession().getAttribute(ConstantsSession.LAST_QUERY_LIST_CACHE);
				xquery = queryCache.get(Integer.parseInt(queryId));

				// Fa casini con gli operatori di minore e di maggiore: li
				// convertiamo
				// TODO Controllare che questa parte serva ancora
				xquery = xquery.replace("\"<\"", "\"&lt;\"");
				xquery = xquery.replace("\">\"", "\"&gt;\"");
				xquery = xquery.replace("\"<=\"", "\"&lt;=\"");
				xquery = xquery.replace("\">=\"", "\"&gt;=\"");

			} else {
				String message ="cannot retrieve param " + XQUERY_PARAM + " from post"; 
				logger.error(message);
				throw new SerenaException(message);
				}
			return xquery;
			}catch (final Exception e)
			{
				String message ="cannot fetch from cache xml query "+queryId;
				logger.error(message);
				throw new SerenaException(message,e);

			}				 
	}
   

	 
}
