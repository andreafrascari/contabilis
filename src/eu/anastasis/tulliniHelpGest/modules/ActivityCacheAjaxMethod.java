package eu.anastasis.tulliniHelpGest.modules;


import java.util.HashMap;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.anastasis.serena.application.core.modules.AjaxMethod;
import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.auth.User;
import eu.anastasis.serena.common.XMessage;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.tulliniHelpGest.helpDesk.ThreadStep2SessioneDiLavoro;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Attivita;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.HDTicket;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Pratica;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.TulliniHelpGestBeanDataManager;

import org.apache.log4j.Logger;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


/**
 * Prenotazione di un attività pe ril tassametro
 * Si tratta di una coda FIFO di N elementi Attivita da "presentare" al tassametro
 * @author afrascari
 *
 */
public class ActivityCacheAjaxMethod extends AjaxMethod
{

	private static final String METHOD_NAME = "activitycache";
	private static final int QUEUE_SIZE = 5;
	private static final String PAR_ID_ACTIVITY = "id_att";
	private static final String PAR_DESC_ACTIVITY = "desc_att";
	private static final String PAR_DESC_FILE = "desc_pra";
	private static final String PAR_CLIENTE = "cliente";
	private static final String PAR_TICKET = "ticket";
	
	// #(userID,LinkedList(Attivita) 
	private static HashMap<String,LinkedList<Attivita>> ActivityCache=null;
	
	private static final Logger logger = Logger.getLogger(ActivityCacheAjaxMethod.class);
	
	public ActivityCacheAjaxMethod(DefaultModule parentModule, String[] defaultParameters) {
		super(parentModule, defaultParameters);
		// TODO Auto-generated constructor stub
	}
	
	public ActivityCacheAjaxMethod()	{
		super(null, null);
	}

	@Override
	public String doMethod(HttpServletRequest request,
			HttpServletResponse response) throws SerenaException {
		XMessage msg=null;
		// get from QS
		String idAttivita=getMethodParameter(request, PAR_ID_ACTIVITY,null);
		String nomePratica =getMethodParameter(request, PAR_DESC_FILE,null);
		String nomeAttivita =getMethodParameter(request, PAR_DESC_ACTIVITY,null);
		String cliente =getMethodParameter(request, PAR_CLIENTE,null);
		String ticket =getMethodParameter(request, PAR_TICKET,null);
		try
		{
			
			msg=new XMessage("reply");
			Element mp=DocumentHelper.createElement("result");
			Element node = mp.addElement("message");
			msg.add(mp);
			Attivita attivita=null;
			if (ticket!=null && !ticket.isEmpty())	{
				// nov 15: novita: PRENOTA x TICKET!!!!
				attivita =  prenotaSessionePerTicket(request, ticket);
			} else {
				// aggiungo in testa
				attivita = new Attivita();
				attivita.setId(idAttivita);
				attivita.setTitolo(cliente + ": "+ nomePratica+" - "+nomeAttivita);
			}
			if (attivita!=null)	{
				addToCodaAttivita(request, attivita);
				node.setText("Tassametro pronto per attivita:\n "+attivita.getTitolo());			
			} else {
				node.setText("Tassametro: nessuna attivita da prenotare! Assegnare a pratica...");
			}
		} catch (Exception e)
		{
			String errorMessage = "Impossibile prenotare il tassametro con attivita " + nomeAttivita;
			msg=XMessage.getMessaggioErrore(errorMessage);
			logger.error(errorMessage, e);
			return msg.asXML();
		}
		return msg.asXML();
	}
	
	private void addToCodaAttivita(HttpServletRequest request, Attivita attivita)	{
		if (ActivityCache==null)	{
			ActivityCache = new HashMap<String,LinkedList<Attivita>>();
		}
		// get from session
		User loggedUser = ApplicationLibrary.retrieveCurrentUser(request);
		String userID = loggedUser.getId();

		LinkedList<Attivita> codaFifo=ActivityCache.get(userID);
		if (codaFifo==null)	{
			codaFifo = new LinkedList<Attivita>();
			ActivityCache.put(userID, codaFifo);
		}
		codaFifo.addFirst(attivita);
		// se abbiamo superato i 5 element, rimuovo la coda 
		if (codaFifo.size()>QUEUE_SIZE)	{
			codaFifo.removeLast();
		}
	}

	private Attivita prenotaSessionePerTicket(HttpServletRequest request, String ticket) throws SerenaException	{ 
	try	{
		
		TulliniHelpGestBeanDataManager tdm =  new TulliniHelpGestBeanDataManager();
		HDTicket hdt = tdm.getHDTicket(request, 2, ticket);
		Pratica p = hdt.getAssegnatoApratica();
		if (p==null)	{
			// TODO prcess errore nessuna pratica
			throw new SerenaException("Nessuna pratica!");
		}
		Attivita a = new ThreadStep2SessioneDiLavoro().fetchOrCreateAttivitaHD(request,p);

		if (a.getInverse_of_attivita()!=null)	{
			Pratica fakeP = new Pratica(); // to be used as update
			fakeP.setId(p.getId());
			a.setInverse_of_attivita(fakeP);
		}
		return a;
	} catch(Exception e){
		
		// log
		throw new SerenaException("",e);
	}
}
		
	@Override
	protected String getName()
	{
		return METHOD_NAME;
	}
	
	public LinkedList<Attivita> getQueueForUserId(String userID){
		try	{
			return ActivityCache.get(userID);
		} catch (Exception e) {
			logger.warn("No activity queue for user "+userID);
		}
		return null;
	}
		
}

