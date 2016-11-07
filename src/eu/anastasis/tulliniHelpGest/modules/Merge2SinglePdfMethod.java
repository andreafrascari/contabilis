/**
 * Questo file appartiene al progetto Sere-na - www.sere-na.it
 * 
 * @copyright Anastasis Soc. Coop. - www.anastasis.it
 */
package eu.anastasis.tulliniHelpGest.modules;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import eu.anastasis.serena.application.core.modules.DefaultMethod;
import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.application.index.IndexQueryException;
import eu.anastasis.serena.application.index.constants.ConstantsSession;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.index.util.AttachmentBean;
import eu.anastasis.serena.application.modules.BirtReport.ReportTemplateFactory;
import eu.anastasis.serena.application.modules.BirtReport.engine.ReportEngine;
import eu.anastasis.serena.application.modules.BirtReport.engine.ReportException;
import eu.anastasis.serena.auth.exceptions.PermissionException;
import eu.anastasis.serena.common.BeanCache;
import eu.anastasis.serena.common.IoLibrary;
import eu.anastasis.serena.common.XSerenaLibrary;
import eu.anastasis.serena.common.XmlLibrary;
import eu.anastasis.serena.constants.ConstantsEntityBean;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.dataManager.DataManagerException;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.serena.presentation.components.ComponentLibrary;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Fattura;

/**
 * Metodo del modulo BirtReport: Crea il report e lo restituisce al client
 * 
 * @author mcarnazzo
 */
public class Merge2SinglePdfMethod extends DefaultMethod
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Merge2SinglePdfMethod.class);

	public static final String METHOD_NAME = "merge2pdf";
	protected static final String CLS_PARAM = "CLS";
	protected static final String DOC_PARAM = "DOC"; // E' DEPRECATO!!! Usare
														// TPL
	protected static final String TYPE_PARAM = "TYPE";
	protected static final String FILE_NAME_PARAM = "FILE";
	protected static final String XQUERY_PARAM = "XQUERY";
	protected static final String CONDITION_PARAM = "CONDITION";
	protected static final String ID_PARAM = "ID";
	protected static final String DEFAULT_TYPE = "rtf";
	protected static final String DATA_SOURCE = "data_source";

	protected static final String LESS_TAG_POSTFIX = "_to";
	protected static final String GREATER_TAG_POSTFIX = "_from";

	public Merge2SinglePdfMethod(DefaultModule parentModule, String[] defaultParameters)
	{
		super(parentModule, defaultParameters);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public String doMethod(HttpServletRequest request, HttpServletResponse response) throws SerenaException
	{
		final String className = getMethodParameter(request, CLS_PARAM);
		final File designFileContabilis = retrieveDesignFile(request, Fattura.COMPETENZA__CONTABILIS);
		final File designFileStudio = retrieveDesignFile(request, Fattura.COMPETENZA__STUDIO);
		final ReportEngine engine = ReportEngine.getInstance(request);
		final Document data = retrieveData(request, engine, designFileContabilis);
		final String type = retrieveType(request);
		@SuppressWarnings("rawtypes")
		final Map parameters = retrieveReportParameters(request, data, designFileContabilis);
		

		OutputStream ostream = null;
		
		AttachmentBean anAttach = new AttachmentBean();
		// split list xml into hand-made instance 
		List<Element> instances = data.selectNodes(".//"+className);
		// header missing ... a problem?
		String zipnameOnly = "Fatture.zip";
		String zipname = anAttach.getMyPath()+"/export_tmp/"+zipnameOnly;
		String outpath = anAttach.getMyPath()+"/export_tmp/Fattura@ID@.pdf"; 

		logger.debug("Creo zip fatture " + zipname);

		File zipfile=new File(zipname);
		 List<File> l = new ArrayList<File>();
		try
		{
			int i=0;
			for (Element anInstance: instances)	{
				
				final Document document = DocumentHelper.createDocument();
				document.setXMLEncoding("UTF-8");
				Element currentElement = document.addElement(ConstantsXSerena.TAG_SERENA);
				currentElement = currentElement.addElement(ConstantsXSerena.TAG_COMMAND);
				currentElement.addAttribute(ConstantsXSerena.ATTR_SERVICE_NAME, ConstantsXSerena.ACTION_GET);
				currentElement.add((Element)anInstance.clone());
				String id = anInstance.elementText("ID");
				String filename = outpath.replace("@ID@", id);
				//System.out.println("comp: "+anInstance.elementText(Fattura.SLOT_COMPETENZA));
				ostream = new FileOutputStream(filename);
				String thisCompetenza = anInstance.elementText(Fattura.SLOT_COMPETENZA);
				logger.debug("adding " + id+ "("+(++i)+" "+thisCompetenza);
				if (thisCompetenza.equals(Fattura.COMPETENZA__CONTABILIS))	{
					engine.createReport(designFileContabilis, currentElement.getDocument(), ostream, type, parameters);
				} else {
					engine.createReport(designFileStudio, currentElement.getDocument(), ostream, type, parameters);
				}
				ostream.flush();
				ostream.close();
				l.add(new File(outpath.replace("@ID@", id)));
			}
		
		} catch (final Exception e)
		{
			throw new SerenaException("Problemi nel creare il report", e);
		}

		try
		{
			new IoLibrary().zip(zipfile, l);
			response.setContentType("application/zip");
			response.addHeader("Content-Disposition", "attachment; filename="+zipnameOnly);
			OutputStream resout = response.getOutputStream();
			FileInputStream in = new FileInputStream(zipname);
			final byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) { //Reads up to buf.length bytes of data from this input stream into an array of bytes
			       resout.write(buf, 0, len);
			}
			resout.flush();
			resout.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * Ricava i parametri da passare al report. Se non viene sovrascritto, legge
	 * i parametri da query string
	 * 
	 * @param request La servlet request da cui leggere la query string
	 * @param designFile Il file birt
	 * @param data I dati su cui si sta lavorando
	 * @return La mappa <nome parametro, oggetto valore> con tutti i parametri
	 */
	@SuppressWarnings("rawtypes")
	protected Map retrieveReportParameters(HttpServletRequest request, Document data, File designFile)
	{
		return getMethodParameters(request);
	}

	/**
	 * Ricava il file .rtpdesign con il report
	 * 
	 * @param request La servlet request da analizzare
	 * @param className Il nome della classe su cui si sta lavorando
	 * @return Il file ricavato
	 */
	protected File retrieveDesignFile(HttpServletRequest request, String competenza) throws IndexQueryException, SerenaException
	{
		if (Fattura.COMPETENZA__CONTABILIS.equals(competenza))	{			
			return ReportTemplateFactory.getInstance().retrieveDesignFile("report", "give", "Fattura_Totali", "Fattura_CONTABILIS");
		} else if (Fattura.COMPETENZA__STUDIO.equals(competenza))	{
			return ReportTemplateFactory.getInstance().retrieveDesignFile("report", "give", "Fattura_Totali", "Fattura_STUDIO");
		} else {
			String msg ="Competenza " + competenza + " non riconosciuta!";
			logger.fatal(msg);
			throw new SerenaException(msg);
		}
	}

	/**
	 * Ricava il nome (senza esensione) che il file finale dovra' avere. Salvo
	 * sovrascrizioni, il nome verra' ricavato dal parametro di query string
	 * FILE o, in sua ssenza, dal nome del designFile
	 * 
	 * @param request La servlet request da analizzare
	 * @param designFile Il file con il report Birt
	 * @param data I dati su cui si sta lavorando
	 * @param parameters I parametri che verranno passati al report
	 * @return Il nome ricavato
	 */
	@SuppressWarnings("rawtypes")
	protected String retrieveOutFileName(HttpServletRequest request, File designFile, Document data, Map parameters)
	{
		String ret = getMethodParameter(request, FILE_NAME_PARAM, IoLibrary.getFileBasename(designFile));
		// Toglie i caratteri potenzialmente problematici dal nome del file
		ret = ret.replace(" ", "_").replace("/", "-");
		return ret;
	}

	/**
	 * Ricava i dati da mostrare nel report
	 * 
	 * @param request La ServletRequest
	 * @param engine Il motore per creare i report
	 * @param designFile Il file contenente il design del report
	 * @return I dati ricavati
	 * @throws SerenaException Problemi nel ricavare i dati
	 */
	protected Document retrieveData(HttpServletRequest request, ReportEngine engine, File designFile) throws SerenaException
	{
		Document ret = null;
		try
		{
			final Document query = createQuery(request, engine, designFile);
			ret = retrieveData(query, request);
			addConditionInMetadata(query, ret);
		} catch (final ReportException e)
		{
			throw new SerenaException("Problemi nell'analizzare i campi del report ", e);
		} catch (final PermissionException e)
		{
			throw new SerenaException("Operazione non permessa a questo utente", e);
		}

		return ret;
	}

	/**
	 * Crea la query per chiedere i dati al database. Se necessario, questa
	 * funzione viene sovrascritta da eventuali classi figlie
	 * 
	 * @param request La ServletRequest
	 * @param engine Il motore per creare i report
	 * @param designFile Il file contenente il design del report
	 * @return La query creata
	 * @throws ReportException Problemi durante l'analisi del report
	 */
	protected Document createQuery(HttpServletRequest request, ReportEngine engine, File designFile) throws ReportException
	{
		final Document ret = engine.createQuery(designFile);
		final Element instanceElement = XSerenaLibrary.selectFirstDataClass(ret);
		final Element conditionElement = createQueryCondition(request, instanceElement.getName());

		if (conditionElement != null)
		{
			instanceElement.add(conditionElement);
		}
		try
		{
			final String orderBy = BeanCache.getInstance().getEntityBean(instanceElement.getName()).elementText(ComponentLibrary.ORDER_BY);
			if (!orderBy.isEmpty())
			{
				XSerenaLibrary.addOrderBy(instanceElement, orderBy);
			}
		} catch (final DocumentException e)
		{
			// niente orderby...proseguo
		}

		final String datasource = getMethodOptionalParameter(request, DATA_SOURCE);
		if (datasource != null)
		{
			instanceElement.addAttribute(ConstantsXSerena.ATTR_FORCED_DATASOURCE, datasource);
		}
		try
		{
			addXmlMethodParameters(request, ret);
		} catch (final IndexQueryException e)
		{
			logger.warn("Problemi nell'aggiungere alla query i parametri XML di query string. Andiamo comunque avanti", e);
		}
		return ret;
	}

	/**
	 * Crea il tag condition da aggiungere alla query che richiede i dati. Se
	 * necessario, questa funzione viene sovrascritta da eventuali classi figlie
	 * 
	 * @param request La ServletRequest
	 * @return La condition creata
	 */
	@SuppressWarnings("unchecked")
	protected Element createQueryCondition(HttpServletRequest request, String rootClassName) throws ReportException
	{
		Element ret = null;

		try
		{
			/*
			 * Se tra i parametri e' stata passata la xquery, la condition la
			 * prende da li' (viene usata da ListMethod)
			 */
			// Il parametro XQUERY contene il puntatore ad una query in cache

			String xquery = null;
			final String queryId = request.getParameter(XQUERY_PARAM);

			if ((queryId != null) && !queryId.isEmpty())
			{
				final Vector<String> queryCache = (Vector<String>) request.getSession().getAttribute(ConstantsSession.LAST_QUERY_LIST_CACHE);
				try
				{
					xquery = queryCache.get(Integer.parseInt(queryId));
				} catch (final Exception e)
				{
					logger.error("CreateQueryCondition could not fetch query index");
					throw new ReportException("Cant get xquery from page form");
				}

				// Fa casini con gli operatori di minore e di maggiore: li
				// convertiamo
				// TODO Controllare che questa parte serva ancora
				xquery = xquery.replace("\"<\"", "\"&lt;\"");
				xquery = xquery.replace("\">\"", "\"&gt;\"");
				xquery = xquery.replace("\"<=\"", "\"&lt;=\"");
				xquery = xquery.replace("\">=\"", "\"&gt;=\"");

				try
				{
					// Prende la condition
					ret = (Element) DocumentHelper.parseText(xquery).getRootElement().clone();
				} catch (final DocumentException dontCare)
				{
				}
				if (!ret.getName().equals(ConstantsXSerena.TAG_CONDITION))
				{
					// Non contiene la condition -> conterra' tutta la query
					final Document doc = XSerenaLibrary.createXQuery(xquery);
					final Element instanceElement = XSerenaLibrary.selectFirstDataClass(doc);
					ret = (Element) XSerenaLibrary.selectConditionElement(instanceElement, false).clone();
				}
			}
		} catch (final NullPointerException dontCare)
		{
			logger.debug("CreateQueryCondition has caught a null pointer exception .... but doesn't care!");
		} catch (final Exception doCare)
		{
			logger.error("CreateQueryCondition has caught exception " + doCare.getMessage());
			throw new ReportException("Cant get xquery from page form");
		}

		// Se tra i parametri e' stato passato l'id, lo inserisce nella
		// condition
		final String id = getMethodOptionalParameter(request, ID_PARAM);
		if (id != null)
		{
			if (ret == null)
			{
				ret = DocumentHelper.createElement(ConstantsXSerena.TAG_CONDITION);
			}

			final Element idElement = ret.addElement(ConstantsEntityBean.ID);
			XmlLibrary.setCDATA(idElement, id);
		}

		return ret;
	}

	/**
	 * Ricava i dati dal database. Se necessario, questa funzione viene
	 * sovrascritta da eventuali classi figlie
	 * 
	 * @param query La query da inviare al database
	 * @param request La ServletRequest
	 * @return I dati ricavati
	 * @throws DataManagerException
	 * @throws PermissionException
	 */
	protected Document retrieveData(Document query, HttpServletRequest request) throws DataManagerException, PermissionException
	{
		final Document ret = ApplicationLibrary.getData(query, request);

		// Devo rimuovere result ancora una volta :(
		final Element resultElement = XSerenaLibrary.selectResultElement(ret);
		resultElement.getParent().remove(resultElement);

		return ret;
	}

	/**
	 * Aggiunge tra i metadati la condition usata per ottenere i dati TODO
	 * Dovrebbe farlo il Persistence
	 * 
	 * @param query La query inviata al db
	 * @param data I dati ricevuti dal db
	 */
	protected void addConditionInMetadata(Document query, Document data)
	{
		final Element firstInstanceInQuery = XSerenaLibrary.selectFirstDataClass(query);
		final Element conditionContainer = XSerenaLibrary.selectConditionElement(firstInstanceInQuery, false);
		final Element metaData = XSerenaLibrary.selectMetadataElement(data, true);
		convertDateConditionSlots(conditionContainer);
		metaData.add((Element) conditionContainer.clone());
	}

	/**
	 * Se gli slot in questione sono delle date con > o <, li converte
	 * rispettivamente in nomeSlot_from e nomeSlot_to, affinche' i report
	 * possano accedervi in maniera distina TODO Sarebbe meglio che fosse Birt a
	 * creare due campi diversi usando come condizione l'attributo operator:
	 * purtroppo pero' si incasina con l'=
	 * 
	 * @param conditionContainer Il contenitore degli slot
	 */
	@SuppressWarnings("unchecked")
	private void convertDateConditionSlots(Element conditionContainer)
	{
		final List<Element> conditionList = conditionContainer.elements();
		for (final Element conditionElement : conditionList)
		{
			final String operator = conditionElement.attributeValue(ConstantsXSerena.ATTR_OPERATOR);
			if (operator != null)
			{
				if (operator.equals(ConstantsXSerena.VAL_LESS_EQUAL_THAN) || operator.equals(ConstantsXSerena.VAL_LESS_THAN))
				{
					conditionElement.setName(conditionElement.getName() + LESS_TAG_POSTFIX);
				} else if (operator.equals(ConstantsXSerena.VAL_GREATER_EQUAL_THAN) || operator.equals(ConstantsXSerena.VAL_GREATER_THAN))
				{
					conditionElement.setName(conditionElement.getName() + GREATER_TAG_POSTFIX);
				}
			}
			convertDateConditionSlots(conditionElement);
		}
	}

	/**
	 * Ricava dalla Query String il tipo di documento richiesto. Se il parametro
	 * non e' presente, da' per scontato che sia un RTF
	 * 
	 * @param request La ServletRequest
	 * @return Il tipo ricavato
	 */
	private String retrieveType(HttpServletRequest request)
	{
		return getMethodParameter(request, TYPE_PARAM, DEFAULT_TYPE);
	}

	@Override
	protected String getName()
	{
		return METHOD_NAME;
	}
}
