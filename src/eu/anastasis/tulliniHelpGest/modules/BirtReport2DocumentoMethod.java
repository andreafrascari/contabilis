package eu.anastasis.tulliniHelpGest.modules;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import eu.anastasis.serena.application.core.modules.DefaultMethod;
import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.index.util.AttachmentBean;
import eu.anastasis.serena.application.modules.BirtReport.GiveMethod;
import eu.anastasis.serena.application.modules.BirtReport.ReportModule;
import eu.anastasis.serena.application.modules.BirtReport.engine.PdfStrategy;
import eu.anastasis.serena.application.modules.BirtReport.engine.ReportEngine;
import eu.anastasis.serena.application.modules.BirtReport.engine.ReportException;
import eu.anastasis.serena.common.SerenaDate;
import eu.anastasis.serena.constants.ConstantsBaseLibrary;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.dataManager.ObjectAlreadyExistsException;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.serena.presentation.templates.ActiveTemplate;
import eu.anastasis.serena.presentation.templates.TemplateFactory;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.DescrizioneDocumento;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Documento;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.TulliniHelpGestBeanDataManager;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;


/**
 * Da un birt report + dati descrittivi crea e registra un'istanza di Documento
 * @author afrascari
 *
 */
public class BirtReport2DocumentoMethod extends DefaultMethod
{

	private static final String METHOD_NAME = "birt2documento";
	
	private static final Logger logger = Logger.getLogger(BirtReport2DocumentoMethod.class);
	
	public BirtReport2DocumentoMethod(DefaultModule parentModule, String[] defaultParameters) {
		super(parentModule, defaultParameters);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String doMethod(HttpServletRequest request,
			HttpServletResponse response) throws SerenaException {
		ActiveTemplate template = new ActiveTemplate(TemplateFactory.getTemplate(
				retrieveDefaultTemplateContext(),retrieveTemplateContext(), getName()));
		try	{
			logger.info("BirtReport2DocumentoMethod.doMethod has been called");
			Documento d = new Documento();
			DescrizioneDocumento dd = new DescrizioneDocumento();
			d.setTitolo(request.getParameter("titolo"));
			d.setAnno_contabile(new Integer(new SerenaDate().getYear()).toString());
			dd.setTipologia_documento(request.getParameter("tipologia"));
			d.setTipologia(dd);
			d.setData_riferimento(new Date());
			String clientId = request.getParameter("cliente");	// parametro per presettare il workflow sul cliente
			String nomeReport = request.getParameter("report");
			String estensione = request.getParameter("estensione");
			String theClass = request.getParameter("class");
			String theID = request.getParameter("id");
			String attachmentID = null;
			String documentID = null;
			try	{
				attachmentID = registerAttachmentFromBirtReport(d.getTitolo(),nomeReport, theClass, theID, estensione, request);
				d.setAllegato(attachmentID);
				documentID = new TulliniHelpGestBeanDataManager().insertDocumento(request, d);
			} catch (IOException e) {
				// istanza di _sys_attach con questo nome gia esiste
				String errorMessage = "Documento dal titolo <a href=\"?q=object/detail&amp;p=Documento/_a_titolo/_v_"+ d.getTitolo()+ "\" title=\"vai al documento esistente per consultazione e/o modifiche\">" + d.getTitolo() + "</a> gi&agrave; presente nel sistema: impossibile sovrascriverlo.";
				logger.error(errorMessage, e);
				template.replaceTagInBlock("ERROR_MESSAGE", "RESULT_ERROR",errorMessage );
				template.publishBlock("RESULT_ERROR");
				template.publish();
				return template.getContenuto();
			} catch (ObjectAlreadyExistsException e) {
				// istanza di Documento con questo titolo gia esiste
				String errorMessage = "Documento dal titolo <a href=\"?q=object/detail&amp;p=Documento/_a_titolo/_v_"+ d.getTitolo()+ "\" title=\"vai al documento esistente per consultazione e/o modifiche\">" + d.getTitolo() + "</a> gi&agrave; presente nel sistema: impossibile sovrascriverlo.";
				logger.error(errorMessage, e);
				template.replaceTagInBlock("ERROR_MESSAGE", "RESULT_ERROR",errorMessage );
				template.publishBlock("RESULT_ERROR");
				template.publish();
				// rollback on allegato
				deleteAttachment(attachmentID,request);
				return template.getContenuto();
			}
			try	{
				updateIstanzaDominio(request,theClass,theID);
			}catch (SerenaException e)
				{
					String errorMessage = "Documento registrato correttamente. Non &egrave; stato possibile invece aggioranere l'istanza di "+theClass;
					logger.error(errorMessage, e);
					template.replaceTagInBlock("ERROR_MESSAGE", "RESULT_ERROR",errorMessage );
					template.publishBlock("RESULT_ERROR");
					template.publish();
					return template.getContenuto();
				}
			template.replaceTagInBlock("ID", "RESULT_SUCCESS",documentID);
			if (clientId!=null)
				template.replaceTagInBlock("CLIENTE", "RESULT_SUCCESS",clientId);
			template.publishBlock("RESULT_SUCCESS");
			template.publish();
			return template.getContenuto();
		}catch (Exception e)
		{
			String errorMessage = "Impossibile registrare il documento: contattare l'assistenza.";
			logger.error(errorMessage, e);
			template.replaceTagInBlock("ERROR_MESSAGE", "RESULT_ERROR",errorMessage );
			template.publishBlock("RESULT_ERROR");
			template.publish();
			return template.getContenuto();
		}
	}	
	
	/** per eventuali sottoclassi che desiderino effettuare update sull'aistanza a dominio dopo caricamento del documento
	 * @param request
	 * @param theClass
	 * @param theID
	 */
	protected void updateIstanzaDominio(HttpServletRequest request,
			String theClass, String theID) throws SerenaException {
		// TODO Auto-generated method stub
		
	}

	private boolean deleteAttachment(String attachmentID,HttpServletRequest request) {
		AttachmentBean anAttach = new AttachmentBean();
		anAttach.setID(attachmentID);
		try
		{
			if (anAttach.load(request))
			{
				// cancellazione fisica
				final File theFile = new File(anAttach.getMyPath() + anAttach.getSa_filename());
				theFile.delete();
				return anAttach.physicalDelete(request);
			} else
			{
				logger.error("Cannot load attach "+attachmentID+" for deletion");
				return false;
			}
		} catch (final Exception e)
		{
			logger.error("Cannot delete attach "+attachmentID);
			return false;
		}
	}

	private String registerAttachmentFromBirtReport(String pretitolo, String nomeReport, String theClass, String theID, String estensione, HttpServletRequest request)	throws IOException, SerenaException	{
		try	{
			AttachmentBean anAttach = new AttachmentBean();
			String titolo = pretitolo.replace(" ", "_")+"."+estensione;
			String filePath = anAttach.getMyPath()+"/"+titolo;
			// 1: bort report 2 file
			nomeReport+=".rptdesign";
			File designFile = new File(ConstantsBaseLibrary.realpath + "/" + ConstantsBaseLibrary.TEMPLATE_DIR + "/" + ReportModule.DEFAULT_NAME + "/" + GiveMethod.METHOD_NAME + "/" + theClass, nomeReport);
			File attachmentFile = new File(filePath); 
			if (attachmentFile.exists()){
				logger.error("File "+filePath+" alredy existent: cannot go on");
				throw new IOException("File "+filePath+" gia esistente");
			}
			OutputStream output = new FileOutputStream(attachmentFile);
			ReportEngine engine = ReportEngine.getInstance(request);
			Document theInstance = createInstance(request, engine,designFile,theClass,theID);
			engine.createReport(designFile, theInstance, output, PdfStrategy.OUTPUT_FORMAT);
			anAttach.setOperationInsert();
			//Default content type
			anAttach.setSa_content_type(AttachmentBean.PDF_CONTENT_TYPE);
			anAttach.setSa_size(attachmentFile.length());
			anAttach.setSa_filename(titolo);
			anAttach.setSa_alt_text(anAttach.getSa_filename());
			anAttach.setSa_link_text(anAttach.getSa_filename());
	    	anAttach.setSa_type("20");    	
	    	// INSERT ATTACHMENT
	    	String retID = anAttach.insert(request);
	    	logger.trace("File "+ anAttach.getSa_filename() + " has been saved");
			// 2: file to _system_attachment
			return retID;
		} catch (ReportException e) {
			logger.error("Problema ai report engine: "+e.getMessage());
			throw new SerenaException(e.getMessage());
		}  
	}


	private Document createInstance(HttpServletRequest request, ReportEngine engine,File designFile, String theClass,
			String theID) throws SerenaException {
	try	{
		Document dataQuery = engine.createQuery(designFile);
		Element theClassElement = (Element)dataQuery.selectSingleNode(".//"+theClass);
		Element theCondition = theClassElement.addElement(ConstantsXSerena.TAG_CONDITION);
		theCondition = theCondition.addElement("ID");
		theCondition.setText(theID);
		Document data = ApplicationLibrary.getData(dataQuery,request);
		
		Element dataElem = ApplicationLibrary.prepareDataForPresentation(data); 
		String[] messages={"",""};
		int res = ConstantsXSerena.getXserenaRequestResult(dataElem, messages,theClass);
		
		if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
		{
			logger.error("createInstance:"+messages[0]);
			throw new SerenaException(messages[0],"generic_sql_error");
		}
		else if (res==ConstantsXSerena.XSERENA_RESULT_EMPTY)
		{
			logger.error("createInstance: empty");
			throw new SerenaException(messages[0],"empty");
		}
		else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)
		{
			return data;
		} else {
			logger.error("createInstance: ?");
			throw new SerenaException(messages[0],"?");
		}
	} catch (Exception e) {
		String msg = "createInstance has resulted into exception:"+e.getMessage();
		logger.error(msg);
		throw new SerenaException(msg);
		}
	}

	@Override
	protected String getName()
	{
		return METHOD_NAME;
	}

	
	
}

