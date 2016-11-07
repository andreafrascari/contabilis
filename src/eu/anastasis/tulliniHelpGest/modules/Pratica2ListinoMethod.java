package eu.anastasis.tulliniHelpGest.modules;


import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.anastasis.serena.application.core.modules.DefaultMethod;
import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.serena.presentation.templates.ActiveTemplate;
import eu.anastasis.serena.presentation.templates.TemplateFactory;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Cliente;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.DatiFatturazione;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.ItemListino;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Pratica;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.TulliniHelpGestBeanDataManager;

/**
 * Genera le pratiche dell'anno corrente di un cliente a partire dal listino
 * 
 * @author afrascari
 * 
 */
public class Pratica2ListinoMethod extends DefaultMethod {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(Pratica2ListinoMethod.class);

	private static final String METHOD_NAME = "pratica2listino";

	public Pratica2ListinoMethod(DefaultModule parentModule,
			String[] defaultParameters) {
		super(parentModule, defaultParameters);
	}

	@Override
	protected String getName() {
		return METHOD_NAME;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String doMethod(HttpServletRequest request,
			HttpServletResponse response) throws SerenaException {
		ActiveTemplate template = new ActiveTemplate(
				TemplateFactory.getTemplate(retrieveDefaultTemplateContext(),
						retrieveTemplateContext(), getName()));
		String mess = "";
		try {
			String id_pratica = request.getParameter("ID");
			TulliniHelpGestBeanDataManager tdm = new TulliniHelpGestBeanDataManager();
			Pratica p = tdm.getPratica(request, 3, id_pratica);
			String id_utente = p.getCliente_pratica().getId();
			template.replaceTag("CLIENTE", id_utente);
			final Document document = DocumentHelper.createDocument();
			document.setXMLEncoding("UTF-8");
			Element anElement = document.addElement(DatiFatturazione.SLOT_INVERSE_OF_DATI_FATTURAZIONE);
			anElement = anElement.addElement(Cliente.INSTANCE_NAME);
			anElement = anElement.addElement("ID");
			anElement.setText(id_utente);
			List<DatiFatturazione> cl = tdm.getDatiFatturazioneList(request, document.getRootElement(), 0);
			
			if (cl.isEmpty())	{
				mess = "Impossibile creare item listino da pratica perche il cliente non ha ancora un listino!";
				logger.error(mess);
				template.replaceTagInBlock("ERROR_MESSAGE", "RESULT_ERROR", mess);
				template.publishBlock("RESULT_ERROR");
				template.publish();
				return template.getContenuto();
				
			}
			ItemListino il = new ItemListino();
			il.setDa_metapratica(p.getMetapratica());
			il.setDescrizione(p.getNote());
			il.setInverse_of_prestazioni_extra_forfait(cl.get(0));
			il.setPrezzo(p.getPrezzo());
			il.setTipo(p.getTipo());
			il.setTitolo(p.getTitolo());
			il.setTitolo_per_fattura(p.getTitolo_per_fattura());

			tdm.insertItemListino(request, il);

			template.replaceTagInBlock("MESS", "RESULT_SUCCESS", mess);
			template.publishBlock("RESULT_SUCCESS");
			template.publish();
			return template.getContenuto();
		} catch (Exception e) {
			mess = "Impossibile creare item listino da pratica a causa di un errore: contattare l'assistenza.";
			logger.error(mess);
			template.replaceTagInBlock("ERROR_MESSAGE", "RESULT_ERROR", mess);
			template.publishBlock("RESULT_ERROR");
			template.publish();
			return template.getContenuto();
		}
	}

}
