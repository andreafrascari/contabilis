package eu.anastasis.tulliniHelpGest.modules;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.modules.object.DetailEditMethod;
import eu.anastasis.serena.application.modules.object.ObjectModule;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Attivita;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Metaattivita;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Metapratica;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.MyMetapratica;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Operatore;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Pratica;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.TulliniHelpGestBinder;

/**
 * ridefinisce l'inserimento della pratica per aggiungervi le attivita ... se la pratica deriva da una metapratica
 * @author afrascari
 *
 */
public class InsertPraticaMethod extends DetailEditMethod {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(InsertPraticaMethod.class);
	private static final String METHOD_NAME = "newpratica";

	public InsertPraticaMethod(DefaultModule defaultModule,
			String[] defaultParameters) {
		super(defaultModule, defaultParameters);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected String getName()
	{
		return METHOD_NAME;
	}

	@Override
	public void postValidInsertActions(HttpServletRequest request,
			Document query, Document ret, String[] messages)
			throws SerenaException {
		super.postValidInsertActions(request, query, ret, messages);
		// metapratica? se si, copio attivita

		try {
			logger.trace("Post valid insertion per inserimento pratica da metapratica: inserimento attivita....");
			String idPratica = messages[1];
			Element metapratica = (Element) query
					.selectSingleNode(".//"+Pratica.SLOT_METAPRATICA);
			if (metapratica != null) {
				// si tratta di una MP: devo inserire le attivita
				TulliniHelpGestBinder binder = new TulliniHelpGestBinder();
				Element responsabile = (Element) query
						.selectSingleNode(".//"+Pratica.SLOT_RESPONSABILE);
				Operatore o = new Operatore();
				o.setId(responsabile.getText());
				Pratica p = new Pratica();
				p.setId(idPratica);
				ArrayList<Attivita> attivita = new ArrayList<Attivita>();
				String idMetaPratica = metapratica.getText();
				Metapratica metapr = MyMetapratica.getInstance(idMetaPratica,
						request);
				ArrayList<Metaattivita> metaattivita = metapr
						.getLista_attivita();
				if (metaattivita != null) {
					for (Metaattivita meta : metaattivita) {
						Attivita a = new Attivita();
						a.setTitolo(meta.getNome());
						a.setAssegnata_a(o);
						a.setStato_attivita(new Integer(0)); // minuti
																// fatturati: 0
						a.setMinuti_ultima_fattura(new Integer(0)); // minuti
																	// fatturati:
																	// 0
						attivita.add(a);
						logger.trace("Aggiungo "+a.getTitolo());
					}
					p.setAttivita(attivita);
					Element theQuery = binder.createElement(p);
					 updatePratica(idPratica,theQuery, request);
				}
			}
		} catch (Exception e) {
			// rollback?!?!?!?!?
			logger.error("postValidInsertActions di pratica ha riportato un errore sulla insert delle attivita ... pero la pratica e stata inserita correttamente");
		}
	}

	private String updatePratica(String id, Element theQuery, HttpServletRequest request)
			throws SerenaException {
		String newID = null;
		try {

			Element currentElement = ObjectUtils
					.getXserenaRequestStandardHeader(request.getSession()
							.getId(), ConstantsXSerena.ACTION_SET,
							Pratica.INSTANCE_NAME);
			Element serviceElement = currentElement.getParent();
			serviceElement.remove(currentElement);
			serviceElement.add((Element) theQuery.clone());
			currentElement = serviceElement.element(Pratica.INSTANCE_NAME);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION,
					ConstantsXSerena.VAL_UPDATE);
			Element cond = currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
			cond = cond.addElement("ID");
			cond.setText(id);
			
			// add insert to activities
			List<Element> attivita = currentElement.selectNodes(".//"+Attivita.INSTANCE_NAME);
			for (Element unaAtt: attivita)	{
				unaAtt.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_INSERT);
			}
			Document data = ApplicationLibrary.setData(
					currentElement.getDocument(), request);
			String[] messages2 = { "", "" };
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages2,
					Pratica.INSTANCE_NAME);
			if (res == ConstantsXSerena.XSERENA_RESULT_ERROR) {
				logger.error("cannot update pratica 2 add activities: " + messages2[0]);
				throw new SerenaException("cannot update pratica 2 add activities: "
						+ messages2[0]);
			} else if (res == ConstantsXSerena.XSERENA_RESULT_SUCCESS) {
				newID = messages2[0];
			}
		} catch (Exception e) {
			logger.error("cannot update pratica 2 add activities: " + e.getMessage());
			throw new SerenaException("cannot update pratica 2 add activities: " + e.getMessage());
		}
		return newID;

	}
	
	
protected String retrieveDefaultTemplateContext(String methodName)	{
		
		return ObjectModule.MODULE_NAME + "/" + methodName;
    }

}
