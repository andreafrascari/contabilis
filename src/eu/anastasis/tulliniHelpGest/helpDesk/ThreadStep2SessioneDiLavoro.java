package eu.anastasis.tulliniHelpGest.helpDesk;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import eu.anastasis.serena.application.core.modules.DefaultMethod;
import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Attivita;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.HDChiamataTelefonica;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.HDThreadStep;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.HDTicket;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.LavoroSuAttivita;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.MyOperatore;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Operatore;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Pratica;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.TulliniHelpGestBeanDataManager;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.serena.presentation.templates.ActiveTemplate;
import eu.anastasis.serena.presentation.templates.TemplateFactory;


/**
 * Metodo chiamato dal centralino asterix per registrare la chiamata corrente.
 * Aggiorna un registro statico
 * @author afrascari
 *
 */
public class ThreadStep2SessioneDiLavoro extends DefaultMethod 

{
	
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ThreadStep2SessioneDiLavoro.class);
	
	public static final String METHOD_NAME = "hdths2sessioneDiLavoro";
	
	private static final String NOME_ATTIVITA_HD = "Helpdesk clienti";

	
	// ?q=ticket/phonecall_start&local=051223344&remote=051471975&direction=clistu
	
	public ThreadStep2SessioneDiLavoro(DefaultModule parentModule, String[] defaultParameters) {
		super(parentModule, defaultParameters);
		// TODO Auto-generated constructor stub
	}
	
	public ThreadStep2SessioneDiLavoro() {
		super(null, null);
	}
			
	@Override
	public String doMethod(HttpServletRequest request,
			HttpServletResponse response) throws SerenaException {
		ActiveTemplate template = new ActiveTemplate(TemplateFactory.getTemplate(
				retrieveDefaultTemplateContext(),retrieveTemplateContext(), getName()));
		String mess="";
		String idHDTHS = request.getParameter("ID");
		String minuti = request.getParameter("INP_durata");
		String ticket = request.getParameter("TICKET");
		try	{
			if (idHDTHS==null || idHDTHS.isEmpty() || minuti==null || minuti.isEmpty())	{
				//TODO process error
			}
			TulliniHelpGestBeanDataManager tdm =  new TulliniHelpGestBeanDataManager();
			HDThreadStep t = tdm.getHDThreadStep(request, 2, idHDTHS);
			HDTicket hdt = t.getInverse_of_thread();
			hdt = tdm.getHDTicket(request, 2, hdt.getId());
			Pratica p = hdt.getAssegnatoApratica();
			if (p==null)	{
				// TODO prcess errore nessuna pratica
				return "Nessuna pratica associata.";
			}
			p = tdm.getPratica(request, 2, p.getId());
			Attivita a = fetchOrCreateAttivitaHD(request,p);
	
			if (a.getInverse_of_attivita()!=null)	{
				Pratica fakeP = new Pratica(); // to be used as update
				fakeP.setId(p.getId());
				a.setInverse_of_attivita(fakeP);
			}
			
			LavoroSuAttivita l = new LavoroSuAttivita();
			l.setData(t.getTh_data());
			l.setDurata_minuti(new Integer(minuti));
			l.setDiario(t.getTh_messaggio());
	
			l.setInverse_of_sessioni_di_lavoro(a);
			
			HDThreadStep fakeT = new HDThreadStep();
			fakeT.setId(idHDTHS);
			l.setDaTicket(fakeT);
		
			String idSessioneDiLavoro = tdm.insertLavoroSuAttivita(request, l);
			
			template.replaceTagInBlock("TICKET", "RESULT_SUCCESS",ticket );
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

	public Attivita fetchOrCreateAttivitaHD(HttpServletRequest request, Pratica p) throws SerenaException	{
		ArrayList<Attivita> attivita = p.getAttivita();
		for (Attivita a:attivita)	{
			if (a.getTitolo().equals(NOME_ATTIVITA_HD))	{
				a.setAssegnata_a(null); // deve gia esistere!
				a.setInverse_of_attivita(null); // deve gia esistere!
				return a;
			}
		}
		Attivita newA = new Attivita();
		newA.setTitolo(NOME_ATTIVITA_HD);
		newA.setInverse_of_attivita(p);
		Operatore o = new MyOperatore().getInstanceFromAccount(ApplicationLibrary.retrieveCurrentUser(request).getId());
		newA.setAssegnata_a(o);

		return newA;
	}

	
	

	@Override
	public String getName()	{
		return METHOD_NAME;
	}
	

	
}

