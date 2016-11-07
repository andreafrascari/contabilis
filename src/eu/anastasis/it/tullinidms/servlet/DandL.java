/**
 * Questo file appartiene al progetto Sere-na - www.sere-na.it
 * @copyright Anastasis Soc. Coop. - www.anastasis.it
 */
package eu.anastasis.it.tullinidms.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import eu.anastasis.it.tullinidms.ConstantsTulliniDMS;
import eu.anastasis.it.tullinidms.modules.Cliente;
import eu.anastasis.it.tullinidms.modules.Documento;
import eu.anastasis.it.tullinidms.modules.Login4DandLModule;
import eu.anastasis.it.tullinidms.modules.RevisioneDocumento;
import eu.anastasis.it.tullinidms.modules.StoriaDocumento;
import eu.anastasis.serena.application.core.tasks.CronPersistenceHome;
import eu.anastasis.serena.application.index.SerenaServlet;
import eu.anastasis.serena.application.index.constants.ConstantsApplication;
import eu.anastasis.serena.application.index.util.ApplicationConfiguration;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.index.util.AttachmentBean;
import eu.anastasis.serena.application.modules.login.CallMethod;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.auth.User;
import eu.anastasis.serena.common.XMessage;
import eu.anastasis.serena.constants.ConstantsBaseLibrary;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.dataManager.DataManagerHome;
import eu.anastasis.serena.exception.SerenaException;

/**
 * Servlet gestione attachment Tullini DMD
 * Per codardia NON estende Attachment, ma logicamente dovrebbe.
 * Ha 2 metodi: 
 * 1) get -> download del file e log del download
 * 2) revision -> crea una nuova revisione di un attachment di Documento: dato l'attachment, ne registra una nuova revisione e libera lo slot del documento
 * @author afrascari
 *
 */
public class DandL extends SerenaServlet 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DandL.class);
	
	protected static final String DOJO_MESSAGE = "<textarea>@MESSAGE@</textarea>";

	protected static final String METHOD_GET = "get";
	protected static final String METHOD_REVISION = "revision";
	
	protected static final String PARAMETER_ID = "id";
	
	
	private static CronPersistenceHome cph = null;
	
	
	private CronPersistenceHome getMyCronPersistenceHome(){
		if (cph==null)
			cph = new CronPersistenceHome();
		return cph; 
	}

	
	/********************************* SERVLET GET ************/
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		String qParam = request.getParameter(ConstantsBaseLibrary.QUERY_STRING_QUERY_PARAM);
		if (qParam==null)
		{
			rispondi(XMessage.getMessaggioErrore("metodo non specificato"),false,response);
		}
		else if (qParam.equals(METHOD_GET))	{
			getFile(request,response); 
		} else if (qParam.equals(METHOD_REVISION))	{
			newRevision(request,response);
		} else 	
			rispondi(XMessage.getMessaggioErrore("metodo sconosciuto"),false,response);
  }
	
	
	


	/********************************* METODO GET (FILE) ************/
	/**
	 * Fetch del content del file da id
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	protected void getFile(HttpServletRequest request,HttpServletResponse response) throws IOException	{
		
		ServletOutputStream out = response.getOutputStream();
				
		String docID = request.getParameter(ConstantsTulliniDMS.DandLK_DOCPAR);
		String userID = request.getParameter(ConstantsTulliniDMS.DandLK_USERPAR);
		// lascio per retrocompatibilità ... e chissa' se potrebbe servire in futuro, ma poi "ridefinisco"
		String attachID = request.getParameter(ConstantsTulliniDMS.DandLK_ATTACHPAR);
		String fromEmail = request.getParameter(ConstantsTulliniDMS.DandLK_EMAILPAR);
		String workflowID = request.getParameter(ConstantsTulliniDMS.DandLK_WORKFLOWPAR);
		boolean forceLogon = (request.getParameter(ConstantsTulliniDMS.DandLK_FORCELOGONPAR)!=null);
		
		if (docID==null)
		{
			rispondi(XMessage.getMessaggioErrore("documento non specificato"),false, response);
		}
		
		// Check force logon
		User sessionUser =ApplicationLibrary.retrieveUser(request.getSession()); 
		// TODO manca un controllo del tipo "sessionUser.getId()!=userID"
		if (forceLogon && sessionUser.isAnonimous())
		{
			String targetUrl = "ModuleIndex?q=" + Login4DandLModule.MODULE_NAME + "/" + CallMethod.METHOD_NAME + "&";
			targetUrl += ConstantsTulliniDMS.DandLK_DOCPAR + "=" + docID + "&";
			targetUrl += ConstantsTulliniDMS.DandLK_USERPAR + "=" + userID + "&";
			// lascio per retrocompatibilità ... e chissa' se potrebbe servire in futuro, ma poi "ridefinisco"
			targetUrl += ConstantsTulliniDMS.DandLK_ATTACHPAR + "=" + attachID + "&";
			targetUrl += ConstantsTulliniDMS.DandLK_FORCELOGONPAR + "=" + forceLogon + "&";
			targetUrl += ConstantsTulliniDMS.DandLK_EMAILPAR + "=" + fromEmail + "&";
			targetUrl += ConstantsTulliniDMS.DandLK_WORKFLOWPAR+ "=" + workflowID;
			response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
			response.setHeader("Location", targetUrl);
		} else
		{
			// Che allegato restituisce? SEMPRE L'ultima versione, che si prende dal Documento
			AttachmentBean anAttach = createAttachmentBean(request,getMyCronPersistenceHome());
			// ridefinisco dal QS 
			try {
				attachID = getIDAllegatoCorrenteDocumento(docID);
			} catch (SerenaException e1) {
				rispondi(XMessage.getMessaggioErrore("Impossibile trovare allegato del documento"),false,response);
			}
			anAttach.setID(attachID);
			FileInputStream in = null;
			boolean downloadOK = true;
			try
			{
				if (anAttach.load(request))
				{
					response.setContentType(anAttach.getSa_content_type());

					File attachFile = new File(anAttach.getMyPath(), anAttach.getSa_filename());
					try
					{
						int size = new Long(attachFile.length()).intValue();
						if (size != 0)
						{
							response.setContentLength(size);
						}
					} catch (Exception dontCare) {}

					// da matteo:
					response.setHeader("Content-Disposition","attachment; filename=" +anAttach.getSa_filename());
					in = new FileInputStream(anAttach.getMyPath() + anAttach.getSa_filename());
				    int c;
				    while ((c = in.read()) != -1)
				    {
				    	out.write(c);
				    }
				} 
			} catch (Exception e)
			{
				logger.error("Errore durante la lettura del file", e);
				rispondi(XMessage.getMessaggioErrore("Errore in lettura del file"),false,response);
				downloadOK = false;
			} finally
			{
				if(in != null) 
				{
					in.close();
				}
			}
			
			// 3 update Storia per registrazione log
			if (userID!=null)	{
				
				// get ID cliente da ID utente 
				/* Attenzione, il clientID ha significato diverso se per link prodotto per la mail (=clientID) e dall'interfaccia web (userID).
				 * Nel secondo caso dobbiamo mapparlo al clientID
				 */
				String clientID = userID;
				
				try	{
					if (fromEmail==null)
						clientID = Cliente.getUSER_CLIENT_CACHE().get(userID);
					else if (!fromEmail.equals("1")) {	// 1 è il pregresso ...
						// registro download da mail solo se si tratta di email primaria:
						clientID = Cliente.getEMAIL_CACHE().get(fromEmail);
						}
					}catch (Exception e) {
						logger.error("Impossibile loggare download cliente in quanto il sistema non ha trovato corrispettiva per account" + userID);
						return;	
					}
				
				if (clientID==null)	{
						logger.error("Download cliente non loggato a causa di mail NON primaria "+fromEmail+" oppure in quanto il sistema non ha trovato corrispettiva per account" + userID);
						return;
					} 

				// solo se cliente specificato 
				String ret = "";	
				try	{
					// registrazione
					Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(request.getSession().getId(), ConstantsXSerena.ACTION_SET, StoriaDocumento.MY_CLASS);
					currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_UPDATE);
		 
					String 	dateFormat = ApplicationConfiguration.GetInstance().getParametro(
							ApplicationConfiguration.INTERFACE_DATE_FORMAT);
					SimpleDateFormat myDateFormat = new SimpleDateFormat(dateFormat);
					
					Element theCond = currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
					
					Element anItem = null;
					
					if (workflowID!=null){
						// se il workflow è specificato (da mail) si va direttamente li'....
						anItem = theCond.addElement("ID");
						anItem.setText(workflowID);
					} else	{
						// altrimenti, tutti i workflow associati alla copiia (doc,cliente)
						anItem = theCond.addElement("cliente_doc");
						anItem = anItem.addElement(Cliente.MY_CLASS);
						anItem = anItem.addElement("ID");
						anItem.setText(clientID);
						
						anItem = theCond.addElement("inverse_of_storia_documento");
						anItem = anItem.addElement(Documento.MY_CLASS);
						anItem = anItem.addElement("ID");
						anItem.setText(docID);
					}
					
					
					if (downloadOK)	{
						anItem = currentElement.addElement("scaricato_il");
						anItem.setText(myDateFormat.format(new Date()));
					} else {					
						anItem = currentElement.addElement("errore");
						anItem.setText("Tentativo fallito di scaricare il file il "+myDateFormat.format(new Date()));
					}
					anItem = currentElement.addElement("activation_flag");
					anItem.setText("1");
					
					Document data = ApplicationLibrary.setData(currentElement.getDocument(),request);
					String[] messages2={"",""};
					int res = ConstantsXSerena.getXserenaRequestResult(data, messages2,StoriaDocumento.MY_CLASS);
					if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
					{
						ret +=" . Errore invece in fase di salvataggio del log";
						logger.error("cannot insert StoriaDocumento for client "+clientID+ " due to  error:"+messages2[0]);
					}
					else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)	{
					}		
				} catch (Exception e) {
					logger.error("cannot update StoriaDocumento for client "+docID+ " due to  error:"+e.getMessage());
					ret +=" . Errore invece in fase di salvataggio del log";
				}
			}			
		}
	}

	/******************************** METHOD REVISION *******************************/

	
	private void newRevision(HttpServletRequest request,
			HttpServletResponse response)  throws IOException {
		String idFile = request.getParameter(PARAMETER_ID);
		XMessage outputMess;
		AttachmentBean anAttach = createAttachmentBean(request, getMyCronPersistenceHome());
		anAttach.setID(idFile);
		try 
		{
			if (anAttach.load(request))
			{
				// 1) cerca il Documento con questo attachm: ne resetta l'attach e ne ricorda l'ID doc
				Documento docInt = processDocumentoConAttach(anAttach, request);
				// 2: istanzia e registra nuova revisione documento con ID, data e autore dell'attach (veccchio)
				registerNewRevisione(docInt, anAttach, request);
				outputMess = new XMessage(XMessage.OK_TYPE);
			}
			else
			{
				throw new IllegalStateException();
			}
		} catch (Exception e) 
		{
			String errorMessage = "Impossibile caricare nuova revisione documento" + anAttach.getSa_filename();
			logger.error(errorMessage, e);
			outputMess = XMessage.getMessaggioErrore(errorMessage);
		}
		rispondi(outputMess,false,response);
	}

	private void registerNewRevisione(Documento doc, AttachmentBean anAttach, HttpServletRequest request)  throws SerenaException {
		// TODO Auto-generated method stub
		try	{
			// registrazione
			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(request.getSession().getId(), ConstantsXSerena.ACTION_SET, RevisioneDocumento.MY_CLASS);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_INSERT);
 
			Element anItem = currentElement.addElement("inverse_of_revisioni");
			anItem = anItem.addElement("Documento");
			anItem = anItem.addElement("ID");
			anItem.setText(doc.getID());
						
			anItem = currentElement.addElement("allegato");
			anItem = anItem.addElement("_system_attachment");
			anItem = anItem.addElement("ID");
			anItem.setText(doc.getAllegato());
			
			anItem = currentElement.addElement("last_modification_date");
			anItem.setText(doc.getLast_modification_date());
			
			anItem = currentElement.addElement("last_modification_user");
			anItem.setText(doc.getLast_modification_user());
			
			Document data = ApplicationLibrary.setData(currentElement.getDocument(),request);
			String[] messages2={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages2,RevisioneDocumento.MY_CLASS);
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("cannot insert revisione " + doc.getLast_modification_date() + " for doc " + doc.getID());
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)	{
			}		
		} catch (Exception e) {
			logger.error("cannot insert revisione " + doc.getLast_modification_date() + " for doc " + doc.getID());
			throw new SerenaException("cannot insert revisione " + doc.getLast_modification_date() + " for doc " + doc.getID());
		}
	}

	private Documento processDocumentoConAttach(AttachmentBean anAttach, HttpServletRequest request) throws SerenaException {
		
		Documento doc = new Documento();
		try {
			// 1 get documento
			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(request.getSession().getId(), ConstantsXSerena.ACTION_GET, Documento.MY_CLASS);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_SELECT);
			currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET, ConstantsXSerena.TARGET_ALL);
			currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET_LEVELS, "0");
			currentElement = currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
			currentElement = currentElement.addElement("allegato");
			currentElement = currentElement.addElement("_system_attachment");
			currentElement = currentElement.addElement("ID");
			currentElement.setText(anAttach.getID());
			
			String[] messages={"",""};
			Document resultData=ApplicationLibrary.getData(currentElement.getDocument(), request);
			Element data = ApplicationLibrary.prepareDataForPresentation(resultData);
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages, Documento.MY_CLASS);
	
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				throw new SerenaException(messages[0],"generic_sql_error");
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_EMPTY)
			{
				logger.error("Non trovo nessun documento con ID "+anAttach.getID());
				throw new SerenaException(messages[0],"nessun documento");
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)
			{
				Element theDocElem = (Element)data.selectSingleNode(".//Documento"); 
				doc.setID(theDocElem.elementText("ID"));
				doc.setLast_modification_user(theDocElem.elementText("last_modification_user"));
				doc.setLast_modification_date(theDocElem.elementText("last_modification_date"));
				doc.setAllegato(anAttach.getID());
				
				// 2: reset attach
				currentElement = ObjectUtils.getXserenaRequestStandardHeader(request.getSession().getId(), ConstantsXSerena.ACTION_SET, Documento.MY_CLASS);
				currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_UPDATE);
	 
				Element anItem = currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
				
				anItem = anItem.addElement("ID");
				anItem.setText(doc.getID());
								
				anItem = currentElement.addElement("allegato");
				anItem.setText("-1");
				
				Document resdata = ApplicationLibrary.setData(currentElement.getDocument(),request);
				
				String[] messages2={"",""};
				res = ConstantsXSerena.getXserenaRequestResult(resdata, messages2,Documento.MY_CLASS);
				if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
				{
					logger.error("errore in reset documento interno due to  error:"+messages2[0]);
					throw new SerenaException(messages[0],"errore in reset attachment documento interno");
				}
				else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)	{
					logger.debug("documento interno "+doc.getID()+ " resettato attachment con successo");
				}		
			}
		} catch  (Exception any)	{
			String msg = "Non trovo nessun documento con ID (oppure errore nel reset attach) "+anAttach.getID();
			any.printStackTrace();
			logger.error(msg + any.getMessage());
			throw new SerenaException(msg);
		}
	return doc;
	}
	
	

	protected AttachmentBean createAttachmentBean(HttpServletRequest request, DataManagerHome persistence)
	{
		return new AttachmentBean(request, persistence);
	}
	

	protected void rispondi(XMessage message, boolean dojo,HttpServletResponse response) throws IOException
	{
		ServletOutputStream out = response.getOutputStream();
		response.setContentType(ConstantsApplication.XML_CONTENT_TYPE);
		if (dojo)
		{

			out.print(DOJO_MESSAGE.replace("@MESSAGE@",message.asXML()));
		}
		else
		{
			out.print(message.asXML());
		}
		out.flush();
	}

	private String getIDAllegatoCorrenteDocumento(String idDoc) throws SerenaException	{
		logger.trace("Fetch ID allegato for doc "+idDoc);
		String ret = null;
		Element currentElement = ObjectUtils.getXserenaRequestStandardHeader("agent", ConstantsXSerena.ACTION_GET, Documento.MY_CLASS);
		currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_SELECT);
		currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET,ConstantsXSerena.TARGET_SPECIFIED);
		Element theCond= currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
		Element anItem = theCond.addElement("ID"); 
		anItem.setText(idDoc);
		currentElement.addElement("ID");
		currentElement.addElement("titolo");
		currentElement.addElement("data_riferimento");
		currentElement.addElement("last_modification_date");
		currentElement.addElement("last_modification_user");
		anItem = currentElement.addElement("allegato");
		anItem = anItem.addElement("_system_attachment");
		anItem = anItem.addElement("ID");
		
		String[] messages={"",""};
		Document resultData=getMyCronPersistenceHome().get(currentElement.getDocument());
		Element data = ApplicationLibrary.prepareDataForPresentation(resultData);
		int res = ConstantsXSerena.getXserenaRequestResult(data, messages,Documento.MY_CLASS);
	
		if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
		{				
			logger.error("ID allegato: "+messages[0]);
			throw new SerenaException(messages[0],"generic_sql_error");
		}
		else if (res==ConstantsXSerena.XSERENA_RESULT_EMPTY)
		{
			logger.error("ID allegato: doc does not exists");
			throw new SerenaException("ID allegato: doc does not exists");
		}
		else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)
		{
			logger.trace("Ok, doc found");
			Element theDocElem = (Element)data.selectSingleNode(".//Documento");
			Element allegato = (Element)theDocElem.selectSingleNode(".//_system_attachment");
			if (allegato!=null)	{
				ret =  allegato.elementText("ID");  
			} else	{
				logger.error("... but han no attachment!!!");
				throw new SerenaException("Doc has no attachment");
			}					
			// do I need any other data?
			// theDocElem.elementText("ID")
		}
		return ret;
	}

}