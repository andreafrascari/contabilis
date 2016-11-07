package eu.anastasis.tulliniHelpGest.helpDesk;

import java.util.Date;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import eu.anastasis.serena.application.core.modules.DefaultMethod;
import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.index.util.AttachmentBean;
import eu.anastasis.serena.application.index.util.MailHandler;
import eu.anastasis.serena.application.index.util.ModuleParameterConfiguration;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.auth.User;
import eu.anastasis.serena.auth.exceptions.PermissionException;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Cliente;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.HDGruppoAssistenza;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Operatore;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.HDRispostaStandard;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.HDThreadStep;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.HDTicket;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.TulliniHelpGestBinder;
import eu.anastasis.serena.common.EntityBean;
import eu.anastasis.serena.constants.ConstantsEntityBean;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.dataManager.DataManagerException;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.serena.presentation.templates.ActiveTemplate;
import eu.anastasis.serena.presentation.templates.TemplateException;
import eu.anastasis.serena.presentation.templates.TemplateFactory;


public class SendMailMethod extends DefaultMethod

{	
	
	public static final String METHOD_NAME = "sendmail";
	
	private static final String ID_PARAM = "ID";
	private static final String CLASS_PARAM = "CLS";
	private static final String RESET_PARAM = "RESET";
	
	private MailHandler mailHandler=null;
	
	private static final Logger logger = Logger.getLogger(SendMailMethod.class);
	
	public SendMailMethod(DefaultModule parentModule, String[] defaultParameters) {
		super(parentModule, defaultParameters);
		mailHandler=new MailHandler();
	}
		
	@Override
	public String doMethod(HttpServletRequest request, HttpServletResponse response) throws SerenaException {
		HDThreadStep hdts =null;
		try	{
			if (request.getParameter(RESET_PARAM)!=null && !request.getParameter(RESET_PARAM).isEmpty())	{
				ConfigurazioneAssistenza.getInstance().Reset();
				return "Cache gruppi assistenza cancellata!";
			}
			String theID = getMethodParameter(request, ID_PARAM);
			String className = getMethodParameter(request, CLASS_PARAM);
			if (HDThreadStep.INSTANCE_NAME.equals(className))	{
				String[] attaches = new String[2];
				hdts = this.fetchHDThreadStep(request, theID, attaches);
				this.send(request,hdts,attaches);			
			} else		{
				return "<div>Nessuna azione registrata per questa classe...</div>";
			}
		}catch (SerenaException s){
			logger.error("[SendMailMethod] could not send mail: "+s.getMessage());
			return "<div>Problemi durante l'invio dell'e-mail: controllare correttezza indirizzo destinatario.</div>";
		}
		//hdts.setMail_inviata(new Date());
		this.upgradeTicketThreadStep(hdts,request);
		logger.debug("[SendMailMethod] has sent a mail");
		return "<div>E-mail inviata correttamente!</div>";
	}
	
	/**
	 * update ticket thread step with sent mail date. Don't' mind if errors occur
	 * @param hdts
	 * @param request
	 */
	protected void upgradeTicketThreadStep(HDThreadStep hdts,HttpServletRequest request)	{
		try	{
			Element root = ObjectUtils.getXserenaRequestStandardHeader(request.getSession().getId(), ConstantsXSerena.ACTION_SET,HDThreadStep.INSTANCE_NAME);
			root.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_UPDATE);
			Element dir = root.addElement(HDThreadStep.SLOT_TH_DIREZIONE);
			dir.setText(HDThreadStep.TH_DIREZIONE__SPEDITO_STUDIO_CLIENTE);
			Element cond = root.addElement(ConstantsXSerena.TAG_CONDITION);
			cond=cond.addElement(ConstantsEntityBean.ID);
			cond.setText(hdts.getId());
			Document data = ApplicationLibrary.setData(root.getDocument(),request);
			String[] messages2={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages2,HDTicket.INSTANCE_NAME);
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("[SendMailMethod]->upgradeTicketThreadStep has resulted into error:"+messages2[0]);
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)	{
			}		
		} catch (PermissionException e) {
			logger.error("[SendMailMethod]->upgradeTicketThreadStep has resulted into permission error"+e.getMessage());
		} catch (DataManagerException e) {
			logger.error("[SendMailMethod]->upgradeTicketThreadStep has resulted into sql error"+e.getMessage());
		} catch (Exception e) {
			logger.error("[SendMailMethod]->upgradeTicketThreadStep has resulted into error"+e.getMessage());
		}
	}

	protected HDThreadStep fetchHDThreadStep(HttpServletRequest request,String theID, String[] attaches) throws SerenaException	{
	try	{
		// 1) fetching corresponding ticket
		HDThreadStep hdt = null;
		String theClass = HDThreadStep.INSTANCE_NAME;
		String theSession = "mail2ticket";
		Element query = ObjectUtils.getXserenaRequestStandardHeader(theSession, ConstantsXSerena.ACTION_GET,theClass);
		Element theCond = query.addElement(ConstantsXSerena.TAG_CONDITION);
		Element tmp = theCond.addElement(ConstantsEntityBean.ID);
		tmp.setText(theID);
		query.addAttribute(ConstantsXSerena.ATTR_TARGET, ConstantsXSerena.TARGET_SPECIFIED);
		query.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_SELECT);
		for (String groundSlot : new EntityBean(HDThreadStep.INSTANCE_NAME).getGroundSlotNames())
		{
			query.addElement(groundSlot);
		}
		Element theTicket = query.addElement(HDThreadStep.SLOT_INVERSE_OF_THREAD);
		theTicket = theTicket.addElement(HDTicket.INSTANCE_NAME);
		for (String groundSlot : new EntityBean(HDTicket.INSTANCE_NAME).getGroundSlotNames())
		{
			theTicket.addElement(groundSlot);
		}
		Element theContact = theTicket.addElement(HDTicket.SLOT_DACLIENTE);
		theContact = theContact.addElement(Cliente.INSTANCE_NAME);
		for (String groundSlot : new EntityBean(Cliente.INSTANCE_NAME).getGroundSlotNames())
		{
			theContact.addElement(groundSlot);
		}
		Element theGroup = theTicket.addElement(HDTicket.SLOT_EMAILASSISTENZA);
		theGroup = theGroup.addElement(HDGruppoAssistenza.INSTANCE_NAME);
		for (String groundSlot : new EntityBean(HDGruppoAssistenza.INSTANCE_NAME).getGroundSlotNames())
		{
			theGroup.addElement(groundSlot);
		}
		Element theOperator= theTicket.addElement(HDTicket.SLOT_ASSEGNATO_A);
		theOperator = theOperator.addElement(Operatore.INSTANCE_NAME);
		for (String groundSlot : new EntityBean(Operatore.INSTANCE_NAME).getGroundSlotNames())
		{
			theOperator.addElement(groundSlot);
		}
		Document data = ApplicationLibrary.getData(query.getDocument(),request);
		Element dataElem = ApplicationLibrary.prepareDataForPresentation(data);
		String[] messages={"",""};
		int res = ConstantsXSerena.getXserenaRequestResult(dataElem, messages,theClass);
		if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
		{
			logger.error("[SendMailMethod]->fetchHDThreadStep has resulted into sql error:"+messages[0]);
			throw new SerenaException(messages[0],"generic_sql_error");
		}
		else if (res==ConstantsXSerena.XSERENA_RESULT_EMPTY)
		{
			logger.error("[SendMailMethod]->fetchHDThreadStep cannot find expected:"+messages[0]);
			throw new SerenaException(messages[0],"generic_sql_error");
		}
		else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)
		{
			Element theInstance = (Element)data.selectSingleNode(".//"+theClass);
			// workarounds to have JibX process attachments: replace instance with ID
			// 1) HDRispostaStandard
			Element stdResp = (Element)theInstance.selectSingleNode(".//"+HDRispostaStandard.INSTANCE_NAME);
			if (stdResp!= null && stdResp.element(HDRispostaStandard.SLOT_ALLEGATO).element(ConstantsXSerena.TAG_ATTACHMENT)!=null)	{
				String theAttachFilename =  stdResp.element(HDRispostaStandard.SLOT_ALLEGATO).element(ConstantsXSerena.TAG_ATTACHMENT).elementText(ConstantsXSerena.TAG_ATTACHMENT_FILENAME);
				stdResp.remove(stdResp.element(HDRispostaStandard.SLOT_ALLEGATO));
				Element theNewAttach = stdResp.addElement(HDRispostaStandard.SLOT_ALLEGATO);
				theNewAttach.setText(theAttachFilename);
			}
			int attachCount=0;
			// 1) HDThreadStep 1 e 2
			stdResp = theInstance;
			if (stdResp.element(HDThreadStep.SLOT_ALLEGATO).element(ConstantsXSerena.TAG_ATTACHMENT)!=null)	{
				String theAttachFilename =  stdResp.element(HDThreadStep.SLOT_ALLEGATO).element(ConstantsXSerena.TAG_ATTACHMENT).elementText(ConstantsXSerena.TAG_ATTACHMENT_FILENAME);
				stdResp.remove(stdResp.element(HDThreadStep.SLOT_ALLEGATO));
				attaches[attachCount++]=theAttachFilename;
			}
			if (stdResp.element(HDThreadStep.SLOT_ALLEGATO_1).element(ConstantsXSerena.TAG_ATTACHMENT)!=null)	{
				String theAttachFilename =  stdResp.element(HDThreadStep.SLOT_ALLEGATO_1).element(ConstantsXSerena.TAG_ATTACHMENT).elementText(ConstantsXSerena.TAG_ATTACHMENT_FILENAME);
				stdResp.remove(stdResp.element(HDThreadStep.SLOT_ALLEGATO_1));
				attaches[attachCount++]=theAttachFilename;
			}
			
			hdt = (HDThreadStep)new TulliniHelpGestBinder().createHDThreadStep(theInstance);
		}
		return hdt;
	} catch (PermissionException e) {
		logger.error("[SendMailMethod]->fetchHDThreadStep has resulted into sql error"+e.getMessage());
		throw new SerenaException(e.getMessage());
	} catch (DataManagerException e) {
		logger.error("[SendMailMethod]->fetchHDThreadStep has resulted into sql error"+e.getMessage());
		throw new SerenaException(e.getMessage());
	}
}
	protected String[] processAttachments(String[] attaches)	{
		
		AttachmentBean anAtBean = new AttachmentBean();
		anAtBean.setSa_type(new Integer(ConstantsEntityBean.CAMPO_ATTACHMENT).toString());
		String aPath ="";
		try {
			aPath = anAtBean.getMyPath();
		} catch (SerenaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (attaches[0]!=null && !attaches[0].isEmpty())	
			attaches[0] = aPath+attaches[0];
		if (attaches[1]!=null && !attaches[1].isEmpty())	
			attaches[1] = aPath+attaches[1];
		return attaches;
	}

	// TODO da rivedere!!!!
	protected String processBody(HDThreadStep theThread) throws SerenaException	{
		ActiveTemplate template =null;
		try {
			template = new ActiveTemplate(TemplateFactory.getTemplate(
					retrieveDefaultTemplateContext(),retrieveTemplateContext(), getName()));
			String tmp = theThread.getTh_messaggio(); 
			tmp = tmp.replace("\n", "<br />");
			//tmp = tmp.replace("\n", "<br />");  //PEGO: nooo! ora è un campo editor,ce li mette da solo i br
			template.replaceTag("ANASTASIS_CUSTOM", tmp);
			try {
				template.replaceTag("WHO", theThread.getInverse_of_thread().getAssegnato_a().getNome_e_cognome());
				template.replaceTag("NOTE", "");

			} catch (Exception e) {
				logger.warn("[SendMailMethod] cannot attach FIRMA for current user ");
				template.replaceTag("WHO","");
				template.replaceTag("NOTE", "");
			}
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			String msg = "[SendMailMethod] cannot fetch mail template";
			logger.error(msg);
			throw new SerenaException(msg);
		}
		return template.getContenuto();
	}
	
	/**
	 * Spedisce mail relativa all'istanza del ThreadStep ... usando come from la MAIL dell'utente in sessione: 
	 * Questo per permettere risposte da mail diverse della generica di ricezione: il "reply-to" e' comunque impostato su quella!! 
	 * @param threadStep
	 * @param attaches
	 * @return
	 * @throws SerenaException
	 */
	public boolean send(HttpServletRequest request, HDThreadStep threadStep, String[] attaches) throws SerenaException
	{
		String[] bcc =null;
		String[] cc=null;
		String content_type=MailHandler.CONTENT_TYPE_PLAIN_TEXT;

		HDTicket hdt = threadStep.getInverse_of_thread();
		Cliente hdc =hdt.getDaCliente();
		
		String to=hdc.getEmail();
		User loggedUser = ApplicationLibrary.retrieveCurrentUser(request);
		String replyTo=hdt.getEmailAssistenza().getEmail();
		String from=loggedUser.getEmail();
		boolean isThereAmail1 =false;
		boolean isThereAmail2 =false;

		/*
		boolean isThereAmail1 =hdc.getEmail2()!=null && !hdc.getEmail2().isEmpty();
		boolean isThereAmail2 =hdc.getEmail3()!=null && !hdc.getEmail3().isEmpty();
		*/
		if (isThereAmail1 && !isThereAmail2) 	
			cc = new String[]{hdc.getEmail2()};
		else if (isThereAmail1 && isThereAmail2)
			cc = new String[]{hdc.getEmail2(),hdc.getEmail3()}; 
		String subject = Mail2Ticket.createTicketID(hdt.getId());
		String body = processBody(threadStep);
		String[] recipients ={to};
		
		String notifucationAddress = ModuleParameterConfiguration.getInstance().getValue(TicketModule.MODULE_NAME,TicketModule.PARAM_TICKET_CCN_EMAIL_ADDRESS, "");
		if (!notifucationAddress.isEmpty())
			bcc = new String[]{notifucationAddress};
		String[] attachments=processAttachments(attaches);
		String[] arrayReplyTo = {replyTo};
		logger.debug("Sending message " + subject + " from " + from + " to " + recipients[0]);
		MimeMessage theSentMsg = mailHandler.sendAndReturnMsg(from, arrayReplyTo, recipients,cc, bcc, subject,body, content_type, attachments);
		if(theSentMsg==null)
		{
			logger.error("[SendMailMethod]->send: Errore nell'invio della mail: "+subject + "a: "+to);
			throw new SerenaException("Errore nell'invio della mail.");
		}
		
		// try to post msg into sent folder (CANT GET IT WORK!)
		// postIntoSentFolder(theSentMsg,from);
		
		return true;
	}
	
	protected void postIntoSentFolder(MimeMessage message, String from)	{
		try	{
			final String sentFolder = "sent";
			Properties props = System.getProperties(); 
			HDGruppoAssistenza aGroup = ConfigurazioneAssistenza.getInstance().getGruopFromMail(from);
			Store store = null;
			if (aGroup.getServer().contains("gmail") || aGroup.getServer().contains("googlemail"))	{
//				props.setProperty("mail.store.protocol", "imaps");
				Session session = Session.getDefaultInstance(props, null);
				store = session.getStore("imaps");
				store.connect(aGroup.getServer(), aGroup.getEmail(), aGroup.getEmail_pwd());
			} else {
				Session session = Session.getInstance(props, null);
				store = session.getStore("imap");
				store.connect(aGroup.getServer(), aGroup.getEmail(), aGroup.getEmail_pwd());
			}
			// Open a Folder
			Folder folder = store.getFolder(sentFolder);
			
			if (folder == null || !folder.exists()) {
				String theMsg = "[SendMailMethod] cannot open folder "+ sentFolder+ " for "+aGroup.getEmail();
				logger.error(theMsg);
				return;
			}
			folder.open(Folder.READ_WRITE);
			folder.appendMessages(new MimeMessage[] {message});
			store.close();
			String theMsg = "[SendMailMethod] has (probably) posted message into sent folder for "+aGroup.getEmail();
			logger.debug(theMsg);
		}catch (Exception e){
			logger.error("[SentMailMethod]->postIntoSentFolder ... did not make it (never mind!): "+e.getMessage());
		}
	}

	@Override
	public String getName()	{
		return METHOD_NAME;
	}

}

