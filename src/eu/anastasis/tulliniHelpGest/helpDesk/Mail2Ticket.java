package eu.anastasis.tulliniHelpGest.helpDesk;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.Part;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import eu.anastasis.serena.application.core.tasks.CronPersistenceHome;
import eu.anastasis.serena.application.functions.ConservativeHtmlParser;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.index.util.AttachmentBean;
import eu.anastasis.serena.application.index.util.ModuleParameterConfiguration;
import eu.anastasis.serena.application.modules.object.ObjectIndexer;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.auth.exceptions.PermissionException;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Cliente;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.HDGruppoAssistenza;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.HDThreadStep;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.HDTicket;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.MyOperatore;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Operatore;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.TulliniHelpGestBinder;
import eu.anastasis.serena.common.BeanCache;
import eu.anastasis.serena.common.EntityBean;
import eu.anastasis.serena.constants.ConstantsEntityBean;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.dataManager.DataManagerException;
import eu.anastasis.serena.exception.BindingException;
import eu.anastasis.serena.exception.SerenaException;

public class Mail2Ticket {
	
	private static final Logger logger = Logger.getLogger(Mail2Ticket.class);
	
	public static final String TICKET_PREFIX = "[CONTABILIS_T_";
	public static final String TICKET_POSTFIX = "_T]";
	
	public static final String TO_BE_FILLED = "da compilare...";
	public static final String PHONE_NUMBER_INVISIBLE = "telefono invisibile";
	
	private static final boolean PARSE_BODY_2_TXT = true;
	
	private String from=null;
	private String phone=null;

	private String to=null;
	private String subject=null;
	private String body=null;
	private String attachment=null;
	private Timestamp receivedAt = null;
	
	private static CronPersistenceHome cph = null;
	
	public Mail2Ticket()	{
		super();
	}

	/**
	 * Attention!!! It's static
	 * @return
	 */
	private CronPersistenceHome getMyCronPersistenceHome(){
		if (cph==null)
			cph = new CronPersistenceHome();
		return cph; 
	}
	
	public static String createTicketID(String id)	{
		return "Assistenza Contabilis "+TICKET_PREFIX+id+TICKET_POSTFIX;
	}
	/**
	 * isola l'ID del ticket (XYZ) dal subject nel formato [ANA_T_XYZ_T]
	 * @return
	 */
	public String getTicketID(){
		if (!this.ticketAlreadyOpen())
			return null;
		int startIndex = this.subject.indexOf(TICKET_PREFIX)+TICKET_PREFIX.length();
		int endIndex = this.subject.indexOf(TICKET_POSTFIX,startIndex);
		return this.subject.substring(startIndex,endIndex);
	}
	
	private void flatBody2Txt()	{
		try	{
			final ConservativeHtmlParser parser = new ConservativeHtmlParser();
			parser.parseHtml(this.body);
			this.body = parser.asText();
		} catch(Exception e)	{
			logger.error("Could not flat body to txt " + e.getMessage());
		}
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
		if (PARSE_BODY_2_TXT){
			this.flatBody2Txt();
		}
	}
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(Part attachment) throws SerenaException {
		this.attachment = this.saveAttachmentAndReturnID(attachment);
	}
	public Timestamp getReceivedAt() {
		return receivedAt;
	}
	public void setReceivedAt(Timestamp receivedAt) {
		this.receivedAt = receivedAt;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}

	public boolean ticketAlreadyOpen(){
		return this.subject.contains(TICKET_PREFIX);
	}
	
	protected String processMessage(){
		return this.body;
	}
	
	protected void append2ExistingTicket()	throws SerenaException {
	try	{
		// 1) fetching corresponding ticket
		HDTicket hdt0 = null;
		String theClass = HDTicket.INSTANCE_NAME;
		String theSession = "mail2ticket";
		Element query = ObjectUtils.getXserenaRequestStandardHeader(theSession, ConstantsXSerena.ACTION_GET,theClass);
		query.addAttribute(ConstantsXSerena.ATTR_TARGET_LEVELS, "0");
		Element theCond = query.addElement(ConstantsXSerena.TAG_CONDITION);
		Element tmp = theCond.addElement(ConstantsEntityBean.ID);
		tmp.setText(this.getTicketID());
		Document data = getMyCronPersistenceHome().get(query.getDocument());
		Element dataElem = ApplicationLibrary.prepareDataForPresentation(data);
		String[] messages={"",""};
		int res = ConstantsXSerena.getXserenaRequestResult(dataElem, messages,theClass);
		if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
		{
			logger.error("[append2ExistingTicket]->contactIsKnown has resulted into sql error:"+messages[0]);
			throw new SerenaException(messages[0],"generic_sql_error");
		}
		else if (res==ConstantsXSerena.XSERENA_RESULT_EMPTY)
		{
			logger.error("[append2ExistingTicket]->contactIsKnown cannot find expected:"+messages[0]);
			throw new SerenaException(messages[0],"generic_sql_error");
		}
		else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)
		{
			Element theInstance = (Element)data.selectSingleNode(".//"+theClass);
			hdt0 = (HDTicket)new  TulliniHelpGestBinder().createHDTicket(theInstance);
		}

		// 2) appending message to thread
		HDTicket hdt = new HDTicket();	// using fresh new instance, since the update is really on its sibling threadStep
		hdt.setStatoTicket(HDTicket.STATOTICKET__APERTO); // except status, which is forced as "open"
		hdt.setPriorita_ticket(HDTicket.PRIORITA_TICKET__URGENTE); // to make it evident that a new answer has arrived
		HDThreadStep hdts = new HDThreadStep();
		hdts.setTh_data(new Date());
		hdts.setCanale(HDThreadStep.CANALE__EMAIL);
		hdts.setTh_direzione(HDThreadStep.TH_DIREZIONE__CLIENTE_STUDIO);
		hdts.setTh_messaggio(this.body);
		hdts.setAllegato(this.attachment);
		// TODO: allegato
		ArrayList<HDThreadStep> theThread = new ArrayList<HDThreadStep>();
		theThread.add(hdts);
		hdt.setThread(theThread);
		Document hdtXml = new TulliniHelpGestBinder().createDocument(hdt);
		Element root = ((Element)hdtXml.selectSingleNode(".//"+HDTicket.INSTANCE_NAME));
		root.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_UPDATE);
		Element cond = root.addElement(ConstantsXSerena.TAG_CONDITION);
		cond=cond.addElement(ConstantsEntityBean.ID);
		cond.setText(hdt0.getId());
		((Element)hdtXml.selectSingleNode(".//"+HDThreadStep.INSTANCE_NAME)).addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_INSERT);
		query = ObjectUtils.getXserenaRequestStandardHeader(theSession, ConstantsXSerena.ACTION_SET,HDTicket.INSTANCE_NAME);
		Element daddy = query.getParent();
		daddy.remove(query);
		daddy.add(hdtXml.getRootElement());		
// 	TODO: in attesa della possibilità di formare utente cron:
		data = getMyCronPersistenceHome().set(daddy.getDocument());
		String[] messages2={"",""};
		res = ConstantsXSerena.getXserenaRequestResult(data, messages2,HDTicket.INSTANCE_NAME);
		if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
		{
			logger.error("[Mail2Ticket]->append2ExistingTicket has resulted into error:"+messages2[0]);
			throw new SerenaException(messages2[0],"generic_sql_error");
		}
		else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)	{
			logger.debug("[Mail2Ticket]->append2ExistingTicket has updated with a new thread step ticket "+hdt.getId());
			indexThreadStepFromTicket(messages2[1]);
		}		
	} catch (BindingException e) {
		logger.error("[Mail2Ticket]->append2ExistingTicket has resulted into sql error",e);
		throw new SerenaException("[Mail2Ticket]->append2ExistingTicket has resulted into sql error",e);
	} catch (PermissionException e) {
		logger.error("[Mail2Ticket]->append2ExistingTicket has resulted into sql error",e);
		throw new SerenaException("[Mail2Ticket]->append2ExistingTicket has resulted into sql error",e);
	} catch (DataManagerException e) {
		logger.error("[Mail2Ticket]->append2ExistingTicket has resulted into sql error",e);
		throw new SerenaException("[Mail2Ticket]->append2ExistingTicket has resulted into sql error",e);
	}
	}
	
	protected void append2PendingTicket(HDTicket hdt)	throws SerenaException {
		try	{
			HDThreadStep hdts = new HDThreadStep();
			hdts.setTh_data(new Date());
			hdts.setTh_messaggio(this.body);
			hdts.setCanale(HDThreadStep.CANALE__EMAIL);
			hdts.setTh_direzione(HDThreadStep.TH_DIREZIONE__CLIENTE_STUDIO);
			hdts.setAllegato(this.attachment);
			// TODO: allegato
			ArrayList<HDThreadStep> theThread = new ArrayList<HDThreadStep>();
			theThread.add(hdts);
			hdt.setThread(theThread);
			Document hdtXml = new TulliniHelpGestBinder().createDocument(hdt);
			Element root = ((Element)hdtXml.selectSingleNode(".//"+HDTicket.INSTANCE_NAME));
			root.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_UPDATE);
			Element cond = root.addElement(ConstantsXSerena.TAG_CONDITION);
			cond=cond.addElement(ConstantsEntityBean.ID);
			cond.setText(hdt.getId());
			((Element)hdtXml.selectSingleNode(".//"+HDThreadStep.INSTANCE_NAME)).addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_INSERT);
			Element query = ObjectUtils.getXserenaRequestStandardHeader("[Mail2Ticket]", ConstantsXSerena.ACTION_SET,HDTicket.INSTANCE_NAME);
			Element daddy = query.getParent();
			daddy.remove(query);
			daddy.add(hdtXml.getRootElement());
			Document data = getMyCronPersistenceHome().set(daddy.getDocument());
			String[] messages2={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages2,HDTicket.INSTANCE_NAME);
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("[Mail2Ticket]->append2PendingTicket has resulted into error:"+messages2[0]);
				throw new SerenaException(messages2[0],"generic_sql_error");
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)	{
				logger.debug("[Mail2Ticket]->append2PendingTicket has updated with a new thread step ticket "+hdt.getId());
				indexThreadStepFromTicket(messages2[1]);
			}		
		} catch (PermissionException e) {
			logger.error("[Mail2Ticket]->append2PendingTicket has resulted into sql error", e);
			throw new SerenaException("[Mail2Ticket]->append2PendingTicket has resulted into sql error",e);
		} catch (BindingException e) {
			logger.error("[Mail2Ticket]->append2PendingTicket has resulted into sql error", e);
			throw new SerenaException("[Mail2Ticket]->append2PendingTicket has resulted into sql error",e);
		} catch (DataManagerException e) {
			logger.error("[Mail2Ticket]->append2PendingTicket has resulted into sql error",e);
			throw new SerenaException("[Mail2Ticket]->append2PendingTicket has resulted into sql error",e);
		}
	}

	protected void open4Contact(Cliente theCli, String noClient) throws SerenaException	{
		try {
			logger.debug("Open 4 contact " + noClient + " - " + this.to);
			HDGruppoAssistenza gruppoAssistenza = ConfigurazioneAssistenza.getInstance().getGruopFromMail(this.to);
			HDTicket hdt = new HDTicket();
			hdt.setEmailAssistenza(gruppoAssistenza);			
			if (theCli!=null){
				hdt.setDaCliente(theCli);			
			} else if (noClient!=null && !noClient.isEmpty()) {
				hdt.setContattoNonRiconosciuto(noClient);
			} else {
				String err = "Non posso aprire un ticket, non avendo riconosciuto il cliente e non avendo dati";
				logger.error(err);
				throw new SerenaException(err);
			}
			hdt.setIdentificativo(this.subject);
			hdt.setStatoTicket(HDTicket.STATOTICKET__IN_ATTESA);
			hdt.setPriorita_ticket(HDTicket.PRIORITA_TICKET__NORMALE);
			hdt.setDataArrivo(new Date());
			MyOperatore whome = new MyOperatore().getInstanceFromEmail(this.to);
			if (whome==null)	{
				String err = "Nessun operatore corrisponde a email " + this.to + " ticket non aperto!";
				logger.fatal(err);
				throw new Exception(err);
			}
			if (!whome.accessCollectiveEmails())	{
				Operatore o = new Operatore();
				o.setId(whome.getId());
				hdt.setAssegnato_a(o);
				hdt.setStatoTicket(HDTicket.STATOTICKET__ASSEGNATO);
			}
			HDThreadStep hdts = new HDThreadStep();
			hdts.setTh_data(new Date());
			hdts.setCanale(HDThreadStep.CANALE__EMAIL);
			hdts.setTh_messaggio(this.processMessage());
			hdts.setTh_direzione(HDThreadStep.TH_DIREZIONE__CLIENTE_STUDIO);
			hdts.setAllegato(this.attachment);
			// TODO: allegato
			ArrayList<HDThreadStep> theThread = new ArrayList<HDThreadStep>();
			theThread.add(hdts);
			hdt.setThread(theThread);
			
			Document hdtXml = new TulliniHelpGestBinder().createDocument(hdt);
			
			Element root = ((Element)hdtXml.selectSingleNode(".//"+HDTicket.INSTANCE_NAME));
			root.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_INSERT);
		
			((Element)hdtXml.selectSingleNode(".//"+HDThreadStep.INSTANCE_NAME)).addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_INSERT);
			if (theCli!=null)	{
				((Element)hdtXml.selectSingleNode(".//"+Cliente.INSTANCE_NAME)).addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_INSERT);
			}
			String theSession = "mail2ticket";
			Element query = ObjectUtils.getXserenaRequestStandardHeader(theSession, ConstantsXSerena.ACTION_SET,HDTicket.INSTANCE_NAME);
			
			Element daddy = query.getParent();
			daddy.remove(query);
			daddy.add(hdtXml.getRootElement());
			
			Document data = getMyCronPersistenceHome().set(daddy.getDocument());
			
			String[] messages={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages,HDTicket.INSTANCE_NAME);
	
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("[Mail2Ticket]->open4Contact has resulted into error:"+messages[0]);
				throw new SerenaException(messages[0],"generic_sql_error");
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)	{
				logger.debug("[Mail2Ticket]->open4Contact has inserted ticket "+hdt.getIdentificativo());
				this.indexInstance(daddy.getDocument(), HDTicket.INSTANCE_NAME, messages[1]);
				this.indexContactFromTicket(messages[1]);
			}		
		} catch (Exception e) {
			logger.fatal("[Mail2Ticket]->open4Contact has resulted into generic", e);
			throw new SerenaException("[Mail2Ticket]->open4Contact has resulted into generic error",e);
		} 
	}


	protected void openFromScratch() throws SerenaException	{
		open4Contact(null,this.from);	
}

	/**
	 * Prova a reperire il contatto in base a email o numero di telefono
	 * @return null se non lo trova...
	 * @throws SerenaException
	 */
	protected Cliente contactIsKnown() throws SerenaException	{
	try {
		String theClass = Cliente.INSTANCE_NAME;
		String theSession = "mail2ticket";
		Element query = ObjectUtils.getXserenaRequestStandardHeader(theSession, ConstantsXSerena.ACTION_GET,theClass);
		query.addAttribute(ConstantsXSerena.ATTR_TARGET_LEVELS, "0");
		Element theCond = query.addElement(ConstantsXSerena.TAG_CONDITION);
		Element theOr = theCond.addElement(ConstantsXSerena.TAG_OR);
		if (this.phone!=null){
			if (this.phone.equals(PHONE_NUMBER_INVISIBLE))
				return null;
			// caso segreteria
			Element tmp = theOr.addElement(Cliente.SLOT_TELEFONO);
			tmp.setText(this.phone);
			tmp = theOr.addElement(Cliente.SLOT_CELLULARE);
			tmp.setText(this.phone);				
		} else {
			Element tmp = theOr.addElement(Cliente.SLOT_EMAIL);
			tmp.setText(this.from);
			tmp = theOr.addElement(Cliente.SLOT_EMAIL2);
			tmp.setText(this.from);
			tmp = theOr.addElement(Cliente.SLOT_EMAIL3);
			tmp.setText(this.from);	
			tmp = theOr.addElement(Cliente.SLOT_FAX);
			tmp.setText(this.from);	
		}
		Document data = getMyCronPersistenceHome().get(query.getDocument());
		
		Element dataElem = ApplicationLibrary.prepareDataForPresentation(data);
		String[] messages={"",""};
		int res = ConstantsXSerena.getXserenaRequestResult(dataElem, messages,theClass);
		
		if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
		{
			logger.error("[Mail2Ticket]->contactIsKnown has resulted into sql error:"+messages[0]);
			throw new SerenaException(messages[0],"generic_sql_error");
		}
		else if (res==ConstantsXSerena.XSERENA_RESULT_EMPTY)
		{
			// no contact has been found
			return null;
		}
		else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)
		{
			Element theInstance = (Element)data.selectSingleNode(".//"+theClass);
			Cliente hdc = (Cliente)new TulliniHelpGestBinder().createCliente(theInstance);
			return hdc;
		}
		else
			return null;
	} catch (PermissionException e) {
		logger.error("[Mail2Ticket]->contactIsKnown has resulted into sql error",e);
		throw new SerenaException("[Mail2Ticket]->contactIsKnown has resulted into sql error",e);
	}  catch (DataManagerException e) {
		logger.error("[Mail2Ticket]->contactIsKnown has resulted into sql error",e);
		throw new SerenaException("[Mail2Ticket]->contactIsKnown has resulted into sql error",e);
	}
}
	

	
	/**
	 * Cerca altri eventuali ticket in attesa per lo stesso client e... per concatenare tutto!
	 * @param theContact
	 * @return
	 * @throws SerenaException
	 */
	protected HDTicket existsPendingTicket(Cliente theContact)	throws SerenaException	{
		try {
			String theClass = HDTicket.INSTANCE_NAME;
			String theSession = "mail2ticket";
			Element query = ObjectUtils.getXserenaRequestStandardHeader(theSession, ConstantsXSerena.ACTION_GET,theClass);
			query.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_SELECT);
			query.addAttribute(ConstantsXSerena.ATTR_TARGET_LEVELS, "0");
			query.addAttribute(ConstantsXSerena.ATTR_ORDER_BY, ConstantsEntityBean.ID+" desc");
			Element theCond = query.addElement(ConstantsXSerena.TAG_CONDITION);
			
			Element tmp = theCond.addElement(HDTicket.SLOT_STATOTICKET);
			tmp.setText(HDTicket.STATOTICKET__IN_ATTESA);
			tmp = theCond.addElement(HDTicket.SLOT_DACLIENTE);
			/*
			tmp.setText(Cliente.INSTANCE_NAME);
			tmp = tmp.addElement(ConstantsEntityBean.ID);
			*/
			tmp.setText(theContact.getId());
			
			Document data = getMyCronPersistenceHome().get(query.getDocument());
			
			Element dataElem = ApplicationLibrary.prepareDataForPresentation(data);
			String[] messages={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(dataElem, messages,theClass);
			
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("[Mail2Ticket]->existsPendingTicket has resulted into sql error:"+messages[0]);
				throw new SerenaException(messages[0],"generic_sql_error");
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_EMPTY)
			{
				// no contact has been found
				return null;
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)
			{
				Element theInstance = (Element)data.selectSingleNode(".//"+theClass);
				HDTicket hdc = (HDTicket)new TulliniHelpGestBinder().createHDTicket(theInstance);
				return hdc;
			}
			else
				return null;
		} catch (PermissionException e) {
			logger.error("[Mail2Ticket]->clientIsKnown has resulted into sql error",e);
			throw new SerenaException("[Mail2Ticket]->clientIsKnown has resulted into sql error",e);
		} catch (DataManagerException e) {
			logger.error("[Mail2Ticket]->clientIsKnown has resulted into sql error",e);
			throw new SerenaException("[Mail2Ticket]->clientIsKnown has resulted into sql error",e);
		}
	}
	
	/**
	 * Saves file as Serena attachment and returns ID
	 * @param filename
	 * @param theStream
	 * @return
	 * @throws SerenaException
	 * @throws MessagingException 
	 * @throws IOException 
	 */
	protected String saveAttachmentAndReturnID(Part part) throws SerenaException	{	
	try {
		String fileName= part.getFileName();
		String contentType = part.getContentType();
		String contentSize = new Integer(part.getSize()).toString();
		
		InputStream theStream = part.getInputStream();
		String type = new Integer(ConstantsEntityBean.CAMPO_ATTACHMENT).toString();
		String thePath = new AttachmentBean().getMyPath();
        // stores file in repository
		int pos;
		while ((pos = fileName.indexOf("\\"))>-1) // upload  from IE passes local path
			fileName = fileName.substring(pos+1,fileName.length());
		// problema: se un file con lo stesso nome esiste gia', finche' ne trovo, aggiungo un _N al nome
		int instanceCounter=1;
		String tryWith = fileName;
		while (new File(thePath+tryWith).exists())    {
			int dotPos = fileName.indexOf(".");
			if (dotPos>-1)
				tryWith =fileName.substring(0,dotPos)+(instanceCounter++)+fileName.substring(dotPos,fileName.length());
			else
				tryWith =fileName+(instanceCounter++);
		}
		tryWith=tryWith.replace(" ", "_");
		
		String attachFileName = tryWith;
		File uploadedFile = new File(thePath+tryWith);
		OutputStream out = null;
		try {
			out = new FileOutputStream(uploadedFile);
		} catch (FileNotFoundException e) {
			String msg = "[Mail2Ticket]->saveAttachmentAndReturnID cannot find expected (as just created) file: "+thePath+tryWith;
			logger.error(msg);
			e.printStackTrace();
			throw new SerenaException(msg);
		}
		byte buf[] = new byte[1024];
		int len;
		try {
			while ((len = theStream.read(buf)) > 0)
				out.write(buf, 0, len);
			out.close();
			theStream.close();
		} catch (IOException e) {
			String msg = "[Mail2Ticket]->saveAttachmentAndReturnID cannot save attached file on local file system.";
			logger.error(msg,e);
			throw new SerenaException(msg,e);
		}
		
		logger.info("Saving file: " +attachFileName);
		
		// insert serena attachment
		 Element currentElement= ObjectUtils.getXserenaRequestStandardHeader("mail2ticket",ConstantsXSerena.ACTION_SET,ConstantsXSerena.TAG_ATTACHMENT);
		currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION,ConstantsXSerena.VAL_INSERT);
		
		Element aNewAttribute=currentElement.addElement("sa_alt_text");
		aNewAttribute.setText(attachFileName);
		aNewAttribute = currentElement.addElement("sa_link_text");
		aNewAttribute.setText(attachFileName);
		aNewAttribute = currentElement.addElement("sa_content_type");
		aNewAttribute.setText(contentType);
		aNewAttribute = currentElement.addElement("sa_filename");
		aNewAttribute.setText(attachFileName);
		aNewAttribute = currentElement.addElement("sa_type");
		aNewAttribute.setText(type);
		aNewAttribute = currentElement.addElement("sa_size");
		aNewAttribute.setText(contentSize);
			
		Document xmlRequest = currentElement.getDocument();
			
		Document res = this.getMyCronPersistenceHome().set(xmlRequest);
		String[] messages={"",""};
		if (ConstantsXSerena.getXserenaRequestResult(res, messages,ConstantsXSerena.TAG_ATTACHMENT)==ConstantsXSerena.XSERENA_RESULT_ERROR)
		{
			String msg = "[Mail2Ticket]->saveAttachmentAndReturnID cannot save data about attached file (Persistence returned XSERENA_RESULT_ERROR): "+messages[0];
			if(logger.isDebugEnabled())
			{
				logger.debug(xmlRequest.asXML());
			}
			throw new SerenaException(msg);
		}
		return messages[1]; // new id
	} catch (Exception e) {
		String msg = "[Mail2Ticket]->saveAttachmentAndReturnID cannot save attachment from mail ... NO LONGER RAISING ERROR!!!!!";
		logger.error(msg,e);
		// throw new SerenaException(msg,e);
		return "-1";
	}
}
	
	protected void indexContactFromTicket(String ticketID){
		try {
			String theClass = Cliente.INSTANCE_NAME;
			String theSession = "mail2ticket";
			Element query = ObjectUtils.getXserenaRequestStandardHeader(theSession, ConstantsXSerena.ACTION_GET,theClass);
			query.addAttribute(ConstantsXSerena.ATTR_TARGET_LEVELS, "0");
			Element tmp = query.addElement(ConstantsXSerena.TAG_CONDITION);
			tmp = tmp.addElement(Cliente.SLOT_CONTATTI_STUDIO);
			tmp = tmp.addElement(HDTicket.INSTANCE_NAME);
			tmp = tmp.addElement(ConstantsEntityBean.ID);
			tmp.setText(ticketID);
			
			for (String groundSlot : new EntityBean(Cliente.INSTANCE_NAME).getGroundSlotNames())
			{
				query.addElement(groundSlot);
			}		

			Document data = getMyCronPersistenceHome().get(query.getDocument());
			
			Element dataElem = ApplicationLibrary.prepareDataForPresentation(data);
			String[] messages={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(dataElem, messages,theClass);
			
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("[Mail2Ticket]->indexContactFromTicket has resulted into sql error:"+messages[0]);
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)
			{
				this.indexInstances(data, theClass);
			}
		} catch (PermissionException e) {
			logger.error("[Mail2Ticket]->indexContactFromTicket has resulted into sql error",e);
		} catch (DataManagerException e) {
			logger.error("[Mail2Ticket]->indexContactFromTicket has resulted into sql error",e);
		}	catch (Exception e) {
			logger.error("[Mail2Ticket]->indexContactFromTicket has resulted into error",e);
		}
	}
	
	protected void indexThreadStepFromTicket(String ticketID){
		try {
			String theClass = HDThreadStep.INSTANCE_NAME;
			String theSession = "mail2ticket";
			Element query = ObjectUtils.getXserenaRequestStandardHeader(theSession, ConstantsXSerena.ACTION_GET,theClass);
			query.addAttribute(ConstantsXSerena.ATTR_TARGET_LEVELS, "0");
			Element tmp = query.addElement(ConstantsXSerena.TAG_CONDITION);
			tmp = tmp.addElement(HDThreadStep.SLOT_INVERSE_OF_THREAD);
			tmp = tmp.addElement(HDTicket.INSTANCE_NAME);
			tmp = tmp.addElement(ConstantsEntityBean.ID);
			tmp.setText(ticketID);
			
			for (String groundSlot : new EntityBean(HDThreadStep.INSTANCE_NAME).getGroundSlotNames())
			{
				query.addElement(groundSlot);
			}		

			Document data = getMyCronPersistenceHome().get(query.getDocument());
			
			Element dataElem = ApplicationLibrary.prepareDataForPresentation(data);
			String[] messages={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(dataElem, messages,theClass);
			
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("[Mail2Ticket]->indexThreadStepFromTicket has resulted into sql error:"+messages[0]);
			} 
			else if (res==ConstantsXSerena.XSERENA_RESULT_EMPTY)
			{
				logger.error("[Mail2Ticket]->indexThreadStepFromTicket has found no instances of just inserted "+theClass);
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)
			{
				this.indexInstances(data, theClass);
			}
		} catch (PermissionException e) {
			logger.error("[Mail2Ticket]->indexThreadStepFromTicket has resulted into sql error",e);
		} catch (DataManagerException e) {
			logger.error("[Mail2Ticket]->indexThreadStepFromTicket has resulted into sql error",e);
		}	catch (Exception e) {
			logger.error("[Mail2Ticket]->indexThreadStepFromTicket has resulted into error",e);
		}
	}

	/**
	 * indicizzazione in search engine
	 * @param theDoc
	 * @param theClass
	 * @param theID
	 */
	protected void indexInstance(Document theDoc, String theClass, String theID)	{
		try {
			Element interfaceRoot = BeanCache.getInstance().getInterfaceBean(theClass);
			ObjectIndexer oI = new ObjectIndexer(interfaceRoot);
			oI.index(theDoc,theID); 

		} catch (DocumentException e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	/**
	 * indicizzazione in search engine
	 * @param theDoc
	 * @param theClass
	 * @param theID
	 */
	protected void indexInstances(Document theDoc, String theClass)	{
		try {
			Element interfaceRoot = BeanCache.getInstance().getInterfaceBean(theClass);
			ObjectIndexer oI = new ObjectIndexer(interfaceRoot);
			oI.indexInstances(theDoc.getRootElement()); 

		} catch (DocumentException e) {
			logger.error(e.getMessage(),e);
		}
	}



	/**
	 * guesses whether phone number is mobile
	 * @return
	 */
	protected boolean numberIsMobile()	{
		try	{			
			return ( this.phone!=null && !this.phone.equals(PHONE_NUMBER_INVISIBLE) && !this.phone.startsWith("0"));
		} catch (Exception e) {
			return false;
		}
	
	}

	/**
	 * si tratta di un messaggio dalla segreteria telòefonica?
	 * @return
	 */
	protected boolean isFromAnsweringMachine()	{
		try	{
			String answeringMachineEmail = ModuleParameterConfiguration.getInstance().getValue(TicketModule.MODULE_NAME, "ANSWERING_MACHINE_EMAIL");
			return this.from.equals(answeringMachineEmail);	
		} catch (Exception e) {
			logger.error("[Mail2Ticket]->isFromAnsweringMachine? could not detect",e);
			return false;
		}
	
	}
		
	/**
	 * Prova a desumere il numero di telefono del chiamante ... oppure torna null
	 * @return
	 */
	protected void grepPhoneNumberFromAnsweringMachine()	{
		try	{
			String phoneDetectionPattern = ModuleParameterConfiguration.getInstance().getValue(TicketModule.MODULE_NAME, "ANSWERING_MACHINE_PHONE_DETECTION_PATTERN");
			//	String phoneDetectionPattern = "[0-9]{4,20}";
			// trying to "grep" phone number from body:
			String contents = this.body;  
			Pattern strMatch = Pattern.compile(phoneDetectionPattern);
	        Matcher matcher = strMatch.matcher(contents);
	        if (matcher.find())	{
	            String matched = matcher.group();
	            // skipping initial zero
	            this.phone = matched.substring(1,matched.length()); 
	        } else 
	        	this.phone = PHONE_NUMBER_INVISIBLE;	// means .... from AM, but phone number was invisible
		} catch (Exception e) {
			logger.error("[Mail2Ticket]->grepPhoneNumberFromAnsweringMachine could not detect the number",e);		
			this.phone = PHONE_NUMBER_INVISIBLE;
		}
	}

	
	/** 
	* 1) gather possible ticketID from subject: if such a thing exists, we'll have to 
	* fetch the corresponding ticket to append the mail as a "thread step" (plus attach, if any)
	* 2) - point 1 was false - try to fetch either contact or client as "from" email addess; if any 
	* of the two exists, we'll open the ticket "on" them (remembering it takes a "product details"  for a "client" to go to a "ticket")
	* 3)  - point 2 was false - we'll create a new contact with the sole email (shall we try guess the name as well?)
	* and open the ticket on it
	*/
	public void createTicket() throws SerenaException 	{
		if (ticketAlreadyOpen())	{
			append2ExistingTicket();
			return;			
		}
		Cliente theCont = null;
		if (isFromAnsweringMachine())
			grepPhoneNumberFromAnsweringMachine();	// sets instance var phone number
		theCont = contactIsKnown();
		if (theCont!=null)	{	
			HDTicket aPendingTicket = existsPendingTicket(theCont);
			if (aPendingTicket!=null)
				append2PendingTicket(aPendingTicket);
			else
				open4Contact(theCont,null);
			return;	
		}
		openFromScratch();
	}

	

}
