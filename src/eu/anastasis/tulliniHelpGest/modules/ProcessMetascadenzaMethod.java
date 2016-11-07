package eu.anastasis.tulliniHelpGest.modules;

import java.io.IOException;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import eu.anastasis.serena.application.core.modules.DefaultMethod;
import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.auth.exceptions.PermissionException;
import eu.anastasis.serena.common.IoLibrary;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.dataManager.DataManagerException;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.serena.presentation.templates.ActiveTemplate;
import eu.anastasis.serena.presentation.templates.TemplateFactory;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Scadenza;
import eu.anastasis.tulliniHelpGest.utils.MetascadenzeProcessor;

 
public class ProcessMetascadenzaMethod extends DefaultMethod 
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ProcessMetascadenzaMethod.class);


	private static final String METHOD_NAME = "processmetascadenza";


	private static final String ID_SCADENZA = "id";
	private static final String METHOD = "method";
	private static final String METHOD_INSERT = "insert";
	private static final String METHOD_UPDATE = "update";
	private static final String METHOD_DELETE = "delete";

	public ProcessMetascadenzaMethod(DefaultModule parentModule,String[] defaultParameters)
	{
		super(parentModule, defaultParameters);
	}

	@Override
	protected String getName()
	{
		return METHOD_NAME;
	}

	/**
	 * Fetch richieste di scadenze parametrizzato sul parametro "quali" che determina il datasource da utilizzare
	 * @param quali
	 * @return
	 * @throws SerenaException
	 */
	protected Element getMetascadenza(HttpServletRequest request, String id) throws SerenaException	{
		try {
			Document dynQuery = null;
			try 
			{
				dynQuery = DocumentHelper.parseText(IoLibrary.readTextFile(MetascadenzeProcessor.RulesFolder+"/"+MetascadenzeProcessor.QueryFile));
			} catch (DocumentException e) {
				throw new SerenaException("Unable to parse rules file",e);
			} catch (IOException e) {
				throw new SerenaException("Unable to read rules file",e);
			}
			Element query =dynQuery.getRootElement(); 
			Element metaElem = ((Element)query.selectSingleNode(".//"+Scadenza.INSTANCE_NAME));
			metaElem = metaElem.addElement(ConstantsXSerena.TAG_CONDITION);
			metaElem = metaElem.addElement("ID");
			metaElem.setText(id);
			
			Document data = ApplicationLibrary.getData(query.getDocument(),request);
			
			Element dataElem = ApplicationLibrary.prepareDataForPresentation(data);
			String[] messages={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(dataElem, messages,Scadenza.INSTANCE_NAME);
			
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("getMetascadenza has resulted into sql error:"+messages[0]);
				throw new SerenaException(messages[0],"generic_sql_error");
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_EMPTY)
			{
				return null;
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)
			{
				return dataElem;
			}
			else
				return null;
		} catch (PermissionException e) {
			logger.error("getMetascadenza has resulted into sql error"+e.getMessage());
			throw new SerenaException(e.getMessage());
		} catch (DataManagerException e) {
			logger.error("getMetascadenza has resulted into sql error"+e.getMessage());
			throw new SerenaException(e.getMessage());
		}
	}
	 
 	
	@SuppressWarnings("unchecked")
	@Override
	public String doMethod(HttpServletRequest request,HttpServletResponse response) throws SerenaException
	{
		ActiveTemplate template = new ActiveTemplate(TemplateFactory.getTemplate(
				retrieveDefaultTemplateContext(),retrieveTemplateContext(), getName()));
		String mess="";
		try	{
			String idScadenza = request.getParameter(ID_SCADENZA);
			String method = request.getParameter(METHOD);
			
			if (idScadenza==null || method==null)	{
				mess= "Dati mancanti!";
				template.replaceTagInBlock("MESS", "RESULT_SUCCESS",mess );
				template.publishBlock("RESULT_SUCCESS");
				template.publish();
				return template.getContenuto();
			}
			Element metascadenzaElem = this.getMetascadenza(request,  idScadenza);
			
			MetascadenzeProcessor processor = new MetascadenzeProcessor();
			
			if (metascadenzaElem==null)	{
				processor.processMetascadenzaDelete(idScadenza);
			} else 	if (method.equals(METHOD_INSERT))	{
				processor.processMetascadenzaInsert(metascadenzaElem);
			} else  if (method.equals(METHOD_UPDATE))	{
				processor.processMetascadenzaUpdate(metascadenzaElem);
			} else 	if (method.equals(METHOD_DELETE))	{
				processor.processMetascadenzaDelete(idScadenza);
			}
			template.replaceTagInBlock("MESS", "RESULT_SUCCESS",mess );
			template.replaceTagInBlock("ID", "RESULT_SUCCESS",idScadenza);
			template.publishBlock("RESULT_SUCCESS");
			template.publish();
			return template.getContenuto();
			
		} catch (Exception e)
		{
			String errorMessage = "Impossibile processare la metascadenza per generare le notifiche a causa di un errore: contattare l'assistenza.";
			logger.error(errorMessage, e);
			template.replaceTagInBlock("ERROR_MESSAGE", "RESULT_ERROR",errorMessage );
			template.publishBlock("RESULT_ERROR");
			template.publish();
			return template.getContenuto();
		}
	}
	
  
	 
}
