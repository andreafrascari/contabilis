package eu.anastasis.tulliniHelpGest.helpDesk;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.google.gson.Gson;
import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.application.core.modules.JSONMethod;
import eu.anastasis.serena.exceptions.JSONException;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.HDRispostaStandard;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.TulliniHelpGestBeanDataManager;

/**
 * Metodo JSON per il recupero del bean Cliente da una qualsiasi delle informazioni di contatto
 * @author andrea
 *
 */
public class Risposta2RispostaStandardMethod extends JSONMethod
{

	private static final Logger logger = Logger.getLogger(Risposta2RispostaStandardMethod.class);
	
	
	

	public Risposta2RispostaStandardMethod(DefaultModule parentModule, String[] defaultParameters)
	{
		super(parentModule, defaultParameters);
		// TODO Auto-generated constructor stub
	}

	public static final String METHOD_NAME = "a2rispesteStandard";

	@Override
	protected String getName()
	{
		return METHOD_NAME;
	}

	@Override
	public String doMethod(HttpServletRequest request, HttpServletResponse response) throws JSONException
	{
	try {
		String diagnosi = request.getParameter("id");
		String risposta = request.getParameter("risposta");
		String note = request.getParameter("note");
		
		HDRispostaStandard hdrs = new HDRispostaStandard();
		hdrs.setDiagnosi_std(diagnosi);
		hdrs.setRisposta_std(risposta);
		hdrs.setNote(note);
		new TulliniHelpGestBeanDataManager().insertHDRispostaStandard(request, hdrs);
		logger.trace("DONE!");
	} catch  (Exception any)	{
		String msg = "Impossibile salvare risposta in archivio: "+any.getMessage();
		logger.error(msg);
		throw new JSONException(msg);
	}
	return new Gson().toJson("Inserimento avvenuto correttamente");
}		

	
}	
 