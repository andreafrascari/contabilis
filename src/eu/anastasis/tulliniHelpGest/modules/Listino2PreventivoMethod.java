package eu.anastasis.tulliniHelpGest.modules;

import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import eu.anastasis.serena.application.core.modules.DefaultMethod;
import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.index.util.ModuleParameterConfiguration;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.serena.presentation.templates.ActiveTemplate;
import eu.anastasis.serena.presentation.templates.TemplateFactory;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.ClienteCandidato;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.DatiFatturazione;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.ItemListino;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.TulliniHelpGestBeanDataManager;
import eu.anastasis.tulliniHelpGest.utils.UtilsProvider;

/**
 * Metodo ajax per desumere il preventivo (o meglio, la descrizione di voci e importi)
 * dagli item di listino del cliente candidato id
 * @author afrascari
 *
 */
public class Listino2PreventivoMethod extends DefaultMethod 
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Listino2PreventivoMethod.class);


	private static final String METHOD_NAME = "listino2preventivo";
	
	private static final String TITOLO_FORFAIT = "VOCE_PREVENTIVO_FORFAIT";
	private static final String TITOLO_ORE  = "VOCE_PREVENTIVO_ORE";
	private static final String HEADER  = "HEADER_PREVENTIVO";
	private static final String FOOTER  = "FOOTER_PREVENTIVO";
	
	public Listino2PreventivoMethod(DefaultModule parentModule,String[] defaultParameters)
	{
		super(parentModule, defaultParameters);
	}

	@Override
	protected String getName()
	{
		return METHOD_NAME;
	}

	@Override
	public String doMethod(HttpServletRequest request,HttpServletResponse response) throws SerenaException
	{
		String id=request.getParameter("id");
		ActiveTemplate template = new ActiveTemplate(TemplateFactory.getTemplate(
				retrieveDefaultTemplateContext(),retrieveTemplateContext(), getName()));
		String mess="";
		try	{
			TulliniHelpGestBeanDataManager thgDM = new TulliniHelpGestBeanDataManager(); 
			ClienteCandidato c = thgDM.getClienteCandidato(request, 3, id);
			DatiFatturazione listino = c.getPreventivo_listino();
			if (listino==null || c.getPreventivo_listino()==null || c.getPreventivo_listino().getListino().size()==0)	{
				String errorMessage = "Listino non presente o vuoto.";
				logger.error(errorMessage);
				template.replaceTagInBlock("ERROR_MESSAGE", "RESULT_ERROR",errorMessage );
				template.publishBlock("RESULT_ERROR");
				template.publish();
				return template.getContenuto();
			}
			String testo_preventivo ="";
			try	{
				ArrayList<ItemListino> item_listino = listino.getListino();
				testo_preventivo = getPreventivo(item_listino);
			} catch (Exception e) {
				String errorMessage = "Impossibile generare il preventivo preventivo: controllare item di listino e parametri di configurazione (es: item a prestazione o forfait senza importo)";
				logger.error(errorMessage, e);
				template.replaceTagInBlock("ERROR_MESSAGE", "RESULT_ERROR",errorMessage );
				template.publishBlock("RESULT_ERROR");
				template.publish();
				return template.getContenuto();
			}
			try	{
				updateClienteCandidato(request, id,testo_preventivo);
			} catch (SerenaException e) {
				String errorMessage = "Impossibile aggiornare il cliente candidato " + id + " con il preventivo";
				logger.error(errorMessage, e);
				template.replaceTagInBlock("ERROR_MESSAGE", "RESULT_ERROR",errorMessage );
				template.publishBlock("RESULT_ERROR");
				template.publish();
				return template.getContenuto();
			}
		} catch (Exception e)
		{
			mess = "Impossibile trovare cliente candidato " + id;
			template.replaceTagInBlock("ERROR_MESSAGE", "RESULT_ERROR",mess );
			template.publishBlock("RESULT_ERROR");
			template.publish();
			return template.getContenuto();
		}
		template.replaceTag("ID", id);
		template.publishBlock("RESULT_SUCCESS");
		template.publish();
		return template.getContenuto();
	}

	private void updateClienteCandidato(HttpServletRequest request, String id, String testo_preventivo) throws SerenaException {
		try	
		{
		Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(request.getSession().getId(), ConstantsXSerena.ACTION_SET, ClienteCandidato.INSTANCE_NAME);
		currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_UPDATE);
		Element theCond= currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
		Element anItem = theCond.addElement("ID"); // la chiave è il titolo!!!!!!
		anItem.setText(id);
		currentElement = currentElement.addElement(ClienteCandidato.SLOT_PREVENTIVO);
		currentElement.setText(testo_preventivo);
		String[] messages={"",""};
		Document resultData=ApplicationLibrary.setData(currentElement.getDocument(),request);
		Element data = ApplicationLibrary.prepareDataForPresentation(resultData);
		int res = ConstantsXSerena.getXserenaRequestResult(data, messages, ClienteCandidato.INSTANCE_NAME);

		if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
		{
			logger.error("cannot update ClienteCandidato " + messages[0]);
			throw new SerenaException(messages[0],"generic_sql_error");
		}
		else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)
		{	// OK
		}
	} catch (Exception e) {
		logger.error("cannot update ClienteCandidato", e);
		throw new SerenaException("");
	}
}

	/**
	 * composizione testo preventivo dagli item listino:
		 * 3 voci:
		 *  - COSTANTE PRESTAZIONI A FORFAIT (euro "somma importi item a forfait)
		 *  	lista titoli item a forfait
		 *  		eventuale descrizione item
		 *  - per ogni item a prestazione: titolo per fattura (eurpo importo prestazione)
		 *  	descrizione
		 *  - COSTANTE PRESTAZIONI AD ORA/RICHIESTA
		 *  	lista titoli item
		 *  		eventuale descrizione item
	 * @param c
	 * @return
	 */
	private String getPreventivo(ArrayList<ItemListino> item_listino) throws SerenaException {
		final String preventivo_tpl = "@HEADER@@FORFAIT@@PRESTAZIONE@@ORE@@FOOTER@";
		final String prestazione_tpl="<h4>@TITOLO@ (Euro @IMPORTO@)</h4>@DESCR@";
		final String forfait_tpl="<h4>@TITOLO@</h4><ul>@DESCR@</ul>";
		final String item_forfait_tpl="<li>@TITOLO@@DESCR@</li>";
		final String descr_item_forfait_tpl="<br /><em>@TESTO@</em>";
		
		String header = ModuleParameterConfiguration.getInstance().getValue("helpgest", HEADER);
		String footer = ModuleParameterConfiguration.getInstance().getValue("helpgest", FOOTER);
		if (header==null || footer==null){
			String msg = "Impossibile generare il preventivo, mancano module parameter HEADER e FOOTER";
			logger.error(msg);
			throw new SerenaException(msg);
		}
		String preventivo="";
		String forfait_collection="";
		String prestazione_collection="";
		String ore_collection="";
		float importo_forfait = 0;
		for (ItemListino l: item_listino)	{
			String titolo = l.getTitolo_per_fattura();
			if (titolo==null || titolo.isEmpty()){
				titolo = l.getTitolo();
			}
			if (l.getTipo().equals(ItemListino.TIPO__A_FORFAIT))	{
				importo_forfait+=l.getPrezzo().floatValue();
				String item = item_forfait_tpl.replace("@TITOLO@", titolo);
				String desc = l.getDescrizione();
				if (desc==null || desc.isEmpty()){
					item = item.replace("@DESCR@", "");
				} else {
					desc = descr_item_forfait_tpl.replace("@TESTO@", desc);
					item = item.replace("@DESCR@", desc);
				}
				forfait_collection+=item;				
			} else if (l.getTipo().equals(ItemListino.TIPO__A_PRESTAZIONE))	{
				String prestazione = prestazione_tpl.replace("@TITOLO@", titolo);
				prestazione = prestazione.replace("@IMPORTO@",new UtilsProvider().formatImporto(l.getPrezzo()));
				String desc = l.getDescrizione();
				if (desc==null || desc.isEmpty()){
					prestazione = prestazione.replace("@DESCR@","");
				} else {
					prestazione = prestazione.replace("@DESCR@",desc);
				}
				prestazione_collection+=prestazione;
			} if (l.getTipo().equals(ItemListino.TIPO__A_ORA))	{
				String item = item_forfait_tpl.replace("@TITOLO@", titolo);
				String desc = l.getDescrizione();
				if (desc==null || desc.isEmpty()){
					item = item.replace("@DESCR@", "");
				} else {
					desc = descr_item_forfait_tpl.replace("@TESTO@", desc);
					item = item.replace("@DESCR@", desc);
				}
				ore_collection+=item;		
			}  
		}
		preventivo = preventivo_tpl.replace("@HEADER@", header);
		preventivo = preventivo.replace("@FOOTER@", footer);
		if (forfait_collection.isEmpty())	{
			preventivo =preventivo.replace("@FORFAIT@", "");
		} else {
			String titolo = ModuleParameterConfiguration.getInstance().getValue("helpgest",TITOLO_FORFAIT);
			titolo = titolo.replace("@IMPORTO@", new UtilsProvider().formatImporto(importo_forfait));
			String forfait = forfait_tpl.replace("@TITOLO@", titolo);
			forfait = forfait.replace("@DESCR@", forfait_collection);
			preventivo =preventivo.replace("@FORFAIT@",	forfait);
		}
		if (prestazione_collection.isEmpty())	{
			preventivo =preventivo.replace("@PRESTAZIONE@", "");
		} else {
			preventivo =preventivo.replace("@PRESTAZIONE@",	prestazione_collection);
		}
		if (ore_collection.isEmpty())	{
			preventivo =preventivo.replace("@ORE@", "");
		} else {
			String titolo = ModuleParameterConfiguration.getInstance().getValue("helpgest",TITOLO_ORE);
			String ore = forfait_tpl.replace("@TITOLO@", titolo);
			ore = ore.replace("@DESCR@", ore_collection);
			preventivo =preventivo.replace("@ORE@",	ore);
		}
		return preventivo;
	}

	
	 
}
