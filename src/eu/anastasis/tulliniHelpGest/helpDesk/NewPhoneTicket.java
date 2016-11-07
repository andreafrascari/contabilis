package eu.anastasis.tulliniHelpGest.helpDesk;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import eu.anastasis.serena.application.core.modules.DefaultMethod;
import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.modules.object.ObjectIndexer;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Cliente;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.HDThreadStep;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.HDTicket;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.MyOperatore;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Operatore;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.TulliniHelpGestBeanDataManager;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.TulliniHelpGestBinder;
import eu.anastasis.serena.common.BeanCache;
import eu.anastasis.serena.common.SerenaDate;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.serena.presentation.templates.ActiveTemplate;
import eu.anastasis.serena.presentation.templates.TemplateFactory;

public class NewPhoneTicket extends DefaultMethod 

{
	
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(NewPhoneTicket.class);
	
	public static final String METHOD_NAME = "nuovo";
	
	public NewPhoneTicket(DefaultModule parentModule, String[] defaultParameters) {
		super(parentModule, defaultParameters);
		// TODO Auto-generated constructor stub
	}
			
	@Override
	public String doMethod(HttpServletRequest request,
			HttpServletResponse response) throws SerenaException {

		ActiveTemplate template = new ActiveTemplate(TemplateFactory.getTemplate(
				retrieveDefaultTemplateContext(),retrieveTemplateContext(), getName()));

		try {
			String noClient=null;
			String clientID = request.getParameter("INP_daCliente");
			if (clientID==null){
				noClient = request.getParameter("INP_contattoNonRiconosciuto");
			}

			String oggetto = request.getParameter("INP_identificativo");
			String messaggio = request.getParameter("INP_th_messaggio");
			String risposta = request.getParameter("INP_th_risposta");
			String assegna = request.getParameter("INP_assegnato_a");
			Operatore current = new MyOperatore().getInstanceFromAccount(ApplicationLibrary.retrieveCurrentUser(request).getId());
			String newID =null;
			if (risposta!=null && !risposta.isEmpty()){
				newID = saveAndClose( request,  clientID,  current.getId(), noClient,  oggetto,  messaggio, risposta);
			} else {
				newID = saveAndQueue( request,  clientID,  current.getId(), noClient,  oggetto,  messaggio, assegna);
			}
			template.replaceTagInBlock("TICKET", "RESULT_SUCCESS",newID );
			template.publishBlock("RESULT_SUCCESS");
			template.publish();
			return template.getContenuto();
			
		} catch (Exception e)
		{
			String errorMessage = "Impossibile inserire le pratiche a causa di un errore: "+e.getMessage();
			logger.error(errorMessage, e);
			template.replaceTagInBlock("ERROR_MESSAGE", "RESULT_ERROR",errorMessage );
			template.publishBlock("RESULT_ERROR");
			template.publish();
			return template.getContenuto();
		}
}

	@Override
	public String getName()	{
		return METHOD_NAME;
	}	
	
	protected String saveAndClose(HttpServletRequest request, String theCli, String theOp, String noClient, String oggetto, String message, String answer) throws SerenaException	{
		try {
			Operatore me = new Operatore();
			me.setId(theOp);
			HDTicket hdt = new HDTicket();
			hdt.setAssegnato_a(me);
			hdt.setDaCliente(new Cliente());
			hdt.setDataArrivo(new Date());
			hdt.setIdentificativo(oggetto);
			hdt.setPriorita_ticket(HDTicket.PRIORITA_TICKET__NORMALE);
			hdt.setStatoTicket(HDTicket.STATOTICKET__APERTO);
			
			if (theCli!=null){
				Cliente fakeCli = new Cliente();
				fakeCli.setId(theCli);
				hdt.setDaCliente(fakeCli);	
			} else if (noClient!=null && !noClient.isEmpty()) {
				hdt.setContattoNonRiconosciuto(noClient);
			} else {
				String err = "Non posso aprire un ticket, non avendo riconosciuto il cliente e non avendo dati";
				logger.error(err);
				throw new SerenaException(err);
			}
			HDThreadStep hdts = new HDThreadStep();
			hdts.setTh_data(new Date());
			hdts.setCanale(HDThreadStep.CANALE__TELEFONO);
			hdts.setTh_messaggio(message);
			hdts.setTh_direzione(HDThreadStep.TH_DIREZIONE__CLIENTE_STUDIO);
	
			HDThreadStep hdts2 = new HDThreadStep();
			hdts2.setTh_data(new Date());
			hdts2.setCanale(HDThreadStep.CANALE__TELEFONO);
			hdts2.setTh_messaggio(answer);
			hdts2.setTh_direzione(HDThreadStep.TH_DIREZIONE__STUDIO_CLIENTE);

			ArrayList<HDThreadStep> theThread = new ArrayList<HDThreadStep>();
			theThread.add(hdts);
			theThread.add(hdts2);
			
			hdt.setThread(theThread);
			
			Document hdtXml = new TulliniHelpGestBinder().createDocument(hdt);
			
			Element root = ((Element)hdtXml.selectSingleNode(".//"+HDTicket.INSTANCE_NAME));
			root.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_INSERT);
		
			String theSession = "newticket";
			Element query = ObjectUtils.getXserenaRequestStandardHeader(theSession, ConstantsXSerena.ACTION_SET,HDTicket.INSTANCE_NAME);
			
			Element daddy = query.getParent();
			daddy.remove(query);
			daddy.add(hdtXml.getRootElement());
			
			Document data = ApplicationLibrary.setData(daddy.getDocument(),request);
			
			String[] messages={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages,HDTicket.INSTANCE_NAME);
	
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("saveAndClose has resulted into error:"+messages[0]);
				throw new SerenaException(messages[0],"generic_sql_error");
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)	{
				logger.debug("saveAndClose has inserted ticket "+hdt.getIdentificativo());
				String ticketID = messages[1];
				this.indexInstance(daddy.getDocument(), HDTicket.INSTANCE_NAME, ticketID);
				return ticketID;
			}		
		} catch (Exception e) {
			logger.error("saveAndClose has resulted into sql error", e);
			throw new SerenaException("saveAndClose has resulted into sql error",e);
		} 
		return null;
	}


	protected String saveAndQueue(HttpServletRequest request, String theCli, String theOp, String noClient, String oggetto, String message , String assegna) throws SerenaException	{
		try {
			Operatore me = new Operatore();
			me.setId(theOp);
			HDTicket hdt = new HDTicket();
			hdt.setAssegnato_a(me);
			hdt.setDaCliente(new Cliente());
			hdt.setDataArrivo(new Date());
			hdt.setIdentificativo(oggetto);
			hdt.setPriorita_ticket(HDTicket.PRIORITA_TICKET__NORMALE);
			hdt.setStatoTicket(HDTicket.STATOTICKET__ASSEGNATO);
			if (assegna!=null && !assegna.isEmpty())	{
				Operatore o = new TulliniHelpGestBeanDataManager().getOperatore(request, 0, assegna);
				hdt.setAssegnato_a(o);
			}
			if (theCli!=null){
				Cliente fakeCli = new Cliente();
				fakeCli.setId(theCli);
				hdt.setDaCliente(fakeCli);	
			} else if (noClient!=null && !noClient.isEmpty()) {
				hdt.setContattoNonRiconosciuto(noClient);
			} else {
				String err = "Non posso aprire un ticket, non avendo riconosciuto il cliente e non avendo dati";
				logger.error(err);
				throw new SerenaException(err);
			}
			
			/* altri param:
			@tag_@
			@tag_priorita_ticket@
			@tag_data_scadenza@
			@tag_diagnosi_libero@
			@tag_rispondere_a@
			*/
			String ar = request.getParameter("INP_azioneRichiesta");
			if (ar!=null){
				hdt.setAzioneRichiesta(ar);
			}
			String pr = request.getParameter("INP_priorita_ticket");
			if (pr!=null)	{
				hdt.setPriorita_ticket(pr);
			}
			String ds = request.getParameter("INP_dataScadenza");
			if (ds!=null)	{
				hdt.setData_scadenza(new SerenaDate(ds).getDate().getTime());
			}
			String dl = request.getParameter("INP_diagnosiLibero");
			if (dl!=null)	{
				hdt.setDiagnosi_libero(dl);
			}
			String ra = request.getParameter("INP_rispondere_a");
			if (ra!=null)	{
				hdt.setRispondere_a(ra);
			}
			
			HDThreadStep hdts = new HDThreadStep();
			hdts.setTh_data(new Date());
			hdts.setCanale(HDThreadStep.CANALE__TELEFONO);
			hdts.setTh_messaggio(message);
			hdts.setTh_direzione(HDThreadStep.TH_DIREZIONE__CLIENTE_STUDIO);
	
			ArrayList<HDThreadStep> theThread = new ArrayList<HDThreadStep>();
			theThread.add(hdts);
			
			hdt.setThread(theThread);
			
			Document hdtXml = new TulliniHelpGestBinder().createDocument(hdt);
			
			Element root = ((Element)hdtXml.selectSingleNode(".//"+HDTicket.INSTANCE_NAME));
			root.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_INSERT);
		
			String theSession = "newticket";
			Element query = ObjectUtils.getXserenaRequestStandardHeader(theSession, ConstantsXSerena.ACTION_SET,HDTicket.INSTANCE_NAME);
			
			Element daddy = query.getParent();
			daddy.remove(query);
			daddy.add(hdtXml.getRootElement());
			
			Document data = ApplicationLibrary.setData(daddy.getDocument(),request);
			
			String[] messages={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages,HDTicket.INSTANCE_NAME);
	
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("saveAndClose has resulted into error:"+messages[0]);
				throw new SerenaException(messages[0],"generic_sql_error");
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)	{
				logger.debug("saveAndClose has inserted ticket "+hdt.getIdentificativo());
				String ticketID = messages[1];
				this.indexInstance(daddy.getDocument(), HDTicket.INSTANCE_NAME, ticketID);
				return ticketID;
			}		
		} catch (Exception e) {
			logger.error("saveAndClose has resulted into sql error", e);
			throw new SerenaException("saveAndClose has resulted into sql error",e);
		} 
		return null;
	}

	
	protected String saveAndAssign(HttpServletRequest request, String theCli, String theOp, String noClient, String oggetto, String message, String answer) throws SerenaException	{
		try {
			Operatore me = new Operatore();
			me.setId(theOp);
			HDTicket hdt = new HDTicket();
			hdt.setAssegnato_a(me);
			hdt.setDaCliente(new Cliente());
			hdt.setDataArrivo(new Date());
			hdt.setIdentificativo(oggetto);
			hdt.setPriorita_ticket(HDTicket.PRIORITA_TICKET__NORMALE);
			hdt.setStatoTicket(HDTicket.STATOTICKET__ASSEGNATO);
			if (theCli!=null){
				Cliente fakeCli = new Cliente();
				fakeCli.setId(theCli);
				hdt.setDaCliente(fakeCli);	
			} else if (noClient!=null && !noClient.isEmpty()) {
				hdt.setContattoNonRiconosciuto(noClient);
			} else {
				String err = "Non posso aprire un ticket, non avendo riconosciuto il cliente e non avendo dati";
				logger.error(err);
				throw new SerenaException(err);
			}
			
			/* altri param:
			@tag_@
			@tag_priorita_ticket@
			@tag_data_scadenza@
			@tag_diagnosi_libero@
			@tag_rispondere_a@
			*/
			String ar = request.getParameter("INP_azioneRichiesta");
			if (ar!=null){
				hdt.setAzioneRichiesta(ar);;
			}
			String pr = request.getParameter("INP_priorita_ticket");
			if (pr!=null)	{
				hdt.setPriorita_ticket(pr);
			}
			String ds = request.getParameter("INP_azioneRichiesta");
			if (ds!=null)	{
				hdt.setData_scadenza(new SerenaDate(ds).getDate().getTime());
			}
			String dl = request.getParameter("INP_azioneRichiesta");
			if (dl!=null)	{
				hdt.setDiagnosi_libero(dl);
			}
			String ra = request.getParameter("INP_azioneRichiesta");
			if (ra!=null)	{
				hdt.setRispondere_a(ra);
			}
			
			HDThreadStep hdts = new HDThreadStep();
			hdts.setTh_data(new Date());
			hdts.setCanale(HDThreadStep.CANALE__TELEFONO);
			hdts.setTh_messaggio(message);
			hdts.setTh_direzione(HDThreadStep.TH_DIREZIONE__CLIENTE_STUDIO);
	
			ArrayList<HDThreadStep> theThread = new ArrayList<HDThreadStep>();
			theThread.add(hdts);
			
			hdt.setThread(theThread);
			
			Document hdtXml = new TulliniHelpGestBinder().createDocument(hdt);
			
			Element root = ((Element)hdtXml.selectSingleNode(".//"+HDTicket.INSTANCE_NAME));
			root.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_INSERT);
		
			String theSession = "newticket";
			Element query = ObjectUtils.getXserenaRequestStandardHeader(theSession, ConstantsXSerena.ACTION_SET,HDTicket.INSTANCE_NAME);
			
			Element daddy = query.getParent();
			daddy.remove(query);
			daddy.add(hdtXml.getRootElement());
			
			Document data = ApplicationLibrary.setData(daddy.getDocument(),request);
			
			String[] messages={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages,HDTicket.INSTANCE_NAME);
	
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("saveAndClose has resulted into error:"+messages[0]);
				throw new SerenaException(messages[0],"generic_sql_error");
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)	{
				logger.debug("saveAndClose has inserted ticket "+hdt.getIdentificativo());
				String ticketID = messages[1];
				this.indexInstance(daddy.getDocument(), HDTicket.INSTANCE_NAME, ticketID);
				return ticketID;
			}		
		} catch (Exception e) {
			logger.error("saveAndClose has resulted into sql error", e);
			throw new SerenaException("saveAndClose has resulted into sql error",e);
		} 
		return null;
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


}
