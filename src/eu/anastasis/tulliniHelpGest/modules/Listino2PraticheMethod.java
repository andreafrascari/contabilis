package eu.anastasis.tulliniHelpGest.modules;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;

import eu.anastasis.serena.application.core.modules.DefaultMethod;
import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.auth.exceptions.PermissionException;
import eu.anastasis.serena.common.SerenaDate;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.dataManager.DataManagerException;
import eu.anastasis.serena.dataManager.ObjectAlreadyExistsException;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.serena.persistence.DecodingSystem;
import eu.anastasis.serena.presentation.templates.ActiveTemplate;
import eu.anastasis.serena.presentation.templates.TemplateFactory;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Attivita;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.ItemListino;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Metaattivita;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Metapratica;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.MyIndicizzazioneListino;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.MyMetapratica;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Operatore;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Cliente;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Pratica;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.TulliniHelpGestBinder;

/**
 * Genera le pratiche dell'anno corrente di un cliente a partire dal listino
 * @author afrascari
 *
 */
public class Listino2PraticheMethod extends DefaultMethod 
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Listino2PraticheMethod.class);
	private static final String MY_CLASS="ItemListino";
	private static final String DATA_SOURCE_PRATICHE_ANNO_CORRENTE="genera-pratiche-anno-corrente";
	private static final String DATA_SOURCE_ITEM_LISTINO="item-listino";
	private static final String INDICIZZA_DA_DATA="creation_date"; 

	
	private static final String METHOD_NAME = "listino2pratiche";

	private static final String PARAM_INDICIZZA_LISTINO = "I";

	private static final String VAL_INDICIZZA_SOLO_PRATICHE = "1";
	private static final String VAL_INDICIZZA_ANCHE_LISTINO = "2";
	private static final String VAL_NON_INDICIZZARE = "0";

	public Listino2PraticheMethod(DefaultModule parentModule,String[] defaultParameters)
	{
		super(parentModule, defaultParameters);
	}

	@Override
	protected String getName()
	{
		return METHOD_NAME;
	}

 	
	@SuppressWarnings("unchecked")
	@Override
	public String doMethod(HttpServletRequest request,HttpServletResponse response) throws SerenaException
	{
		ActiveTemplate template = new ActiveTemplate(TemplateFactory.getTemplate(
				retrieveDefaultTemplateContext(),retrieveTemplateContext(), getName()));
		String mess="";
		try	{
			ArrayList<String> newIDCollection = new ArrayList<String>();
			String id_utente = request.getParameter("ID");
			String id_responsabile = request.getParameter("OP");
			String indicizza = request.getParameter(PARAM_INDICIZZA_LISTINO);
			template.replaceTag("CLIENTE", id_utente);
			if (id_utente==null){
				// gestire errore
			}
			Cliente c= new Cliente();
			c.setId(id_utente);
			Operatore o = null;
			if (id_responsabile!=null){
				o = new Operatore();
				o.setId(id_responsabile);
			} else {
				mess= "Impossibile creare le pratiche in quanto manca operatore responsabile.";
				logger.error(mess);
				template.replaceTagInBlock("ERROR_MESSAGE", "RESULT_ERROR",mess );
				template.publishBlock("RESULT_ERROR");
				template.publish();
				return template.getContenuto();		
			}
			// se rischiesto, indicizziamo prima il liastino
			if (VAL_INDICIZZA_ANCHE_LISTINO.equals(indicizza))	{
				Element itemCandidati = getItemCandidati(id_utente,request,false);
				List<Element> itemList = itemCandidati.selectNodes(".//"+MY_CLASS);
				for (Element anItem: itemList)	{
					indicizzaPrezzoListino(request,anItem);
				}
			}
			Element itemCandidati = getItemCandidati(id_utente,request,true);
			if (itemCandidati==null){
				mess= "Nessuna pratica da creare per quest'anno.<br />Le pratiche sono gi&agrave; state create, oppure il listino del cliente &egrave; vuoto.";
				template.replaceTagInBlock("MESS", "RESULT_SUCCESS",mess );
				template.publishBlock("RESULT_SUCCESS");
				template.publish();
				return template.getContenuto();
			}
			List<Element> itemList = itemCandidati.selectNodes(".//"+MY_CLASS);
			// anno corrente
			String anno_contabile = new Integer(new SerenaDate().getYear()).toString();
			Pratica p = null;
			DecodingSystem ds = new DecodingSystem();
			TulliniHelpGestBinder binder = new TulliniHelpGestBinder();

			// per ogni item listino:
			for (Element anItem: itemList)	{
				// creao la pratica
				p = new Pratica();
				// vi copio i dati dall'item
				p.setAnno_contabile(anno_contabile);
				p.setTipo(anItem.elementText("tipo"));
				p.setTitolo(anItem.elementText("titolo"));
				p.setStato(Pratica.STATO__APERTA);
				if (anItem.element("titolo_per_fattura")!=null)	{
					p.setTitolo_per_fattura(anItem.elementText("titolo_per_fattura"));
				}
				p.setCliente_pratica(c);
				if (anItem.element("descrizione")!=null)	{
					p.setNote(anItem.elementText("descrizione"));
				}
				String prezzo = anItem.elementText("prezzo");
				if (prezzo!=null && !prezzo.isEmpty())	{
					p.setPrezzo(indicizzaPrezzo(request,indicizza, anItem, prezzo));
				}
				if (o!=null){
					// p.setResponsabile(o); nov 16: su richiesta di Luana, non viene piu' impostato il responsabile della pratica
				}
				// metapratica? se si, copio attivita
				if (anItem.element("da_metapratica").element("Metapratica")!=null){
					ArrayList<Attivita> attivita = new ArrayList<Attivita>();
					String idMetaPratica = anItem.element("da_metapratica").element("Metapratica").elementText("ID");
					Metapratica metapr = MyMetapratica.getInstance(idMetaPratica, request);
					ArrayList<Metaattivita> metaattivita = metapr.getLista_attivita();
					if (metaattivita!=null)	{
					for (Metaattivita meta: metaattivita){
							Attivita a = new Attivita();
							a.setTitolo(meta.getNome());
							a.setAssegnata_a(o);
							a.setStato_attivita(new Integer(0));	// minuti fatturati: 0
							a.setMinuti_ultima_fattura(new Integer(0));	// minuti fatturati: 0
							attivita.add(a);
						}
						p.setAttivita(attivita);
					}
				}
				
				try	{
					Element theQuery = binder.createElement(p);
					String aNewID = insertPratica(theQuery,request);
					if (aNewID!=null)	{
						newIDCollection.add(aNewID);
						mess+="Pratica "+p.getTitolo()+ " registrata correttamente;<br />";
					}
				} catch (SerenaException e) {
					// catching exceptions for each pratica
					mess+="Pratica "+p.getTitolo()+ " non registrata a causa di un errore/duplicazione;<br />";
				}
			}
			template.replaceTagInBlock("MESS", "RESULT_SUCCESS",mess );
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
	
	/**
	 * indicizza il prezzo dell'item di listino, e lo aggiorna
	 * @param request
	 * @param anItem
	 * @throws SerenaException
	 */
	private void indicizzaPrezzoListino(HttpServletRequest request, Element anItem) throws SerenaException
	{
		String prezzo = anItem.elementText("prezzo");
		if (prezzo!=null && !prezzo.isEmpty())	{
			String dataListino = anItem.elementText(INDICIZZA_DA_DATA);
			Float res =  new MyIndicizzazioneListino().attualizzaValore(prezzo, dataListino);
			int prezzoD = (int)Math.floor(res.floatValue());	// si approssima x difetto
			prezzo = new Integer(prezzoD).toString();
			updateItemListino(request,anItem.elementText("ID"),prezzo);
		}		
	}

	/*
	private Float indicizzaPrezzo(boolean indicizza, String prezzo,String dataListino) throws SerenaException {
		Float res =  indicizza?new MyIndicizzazioneListino().attualizzaValore(prezzo, dataListino):new Float(prezzo);
		return res;
	}
	*/

	private Float indicizzaPrezzo(HttpServletRequest request, String indicizza, Element anItem,String prezzo) throws SerenaException {
		if (VAL_INDICIZZA_SOLO_PRATICHE.equals(indicizza))	{
			String dataListino = anItem.elementText(INDICIZZA_DA_DATA);
			Float res =  new MyIndicizzazioneListino().attualizzaValore(prezzo, dataListino);
			int prezzoD = (int)Math.floor(res.floatValue());	// si approssima x difetto
			prezzo = new Integer(prezzoD).toString();
		}
		return new Float(prezzo);
	}
	
	private void updateItemListino(HttpServletRequest request,String id, String prezzo) throws SerenaException
	{
		try	{

			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(request.getSession().getId(), ConstantsXSerena.ACTION_SET, ItemListino.INSTANCE_NAME);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_UPDATE);
			Element prezzoEl = currentElement.addElement(ItemListino.SLOT_PREZZO);
			prezzoEl.setText(prezzo);
			currentElement=currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
			currentElement=currentElement.addElement("ID");
			currentElement.setText(id);
			Document data = ApplicationLibrary.setData(currentElement.getDocument(),request);
			String[] messages2={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages2,Pratica.INSTANCE_NAME);
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("cannot insert pratica: "+messages2[0]);
				throw new SerenaException("cannot insert pratica: "+messages2[0]);
			}
		} catch (Exception e)
		{
			String msg = "Impossibile aggiornare prezzo item di listino "+id; 
			logger.error(msg+e.getMessage());
			throw new SerenaException(msg);
		}
	}

	private String insertPratica(Element theQuery, HttpServletRequest request) throws SerenaException {
		String newID = null;
		try	{

			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(request.getSession().getId(), ConstantsXSerena.ACTION_SET, Pratica.INSTANCE_NAME);
			Element serviceElement  = currentElement.getParent();
			serviceElement.remove(currentElement);
			serviceElement.add((Element)theQuery.clone());
			currentElement = serviceElement.element(Pratica.INSTANCE_NAME);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_INSERT);
		
			Document data = ApplicationLibrary.setData(currentElement.getDocument(),request);
			String[] messages2={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages2,Pratica.INSTANCE_NAME);
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("cannot insert pratica: "+messages2[0]);
				throw new SerenaException("cannot insert pratica: "+messages2[0]);
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)	{
				newID = messages2[0];
			}		
		} catch (ObjectAlreadyExistsException e)
		{
			logger.error("cannot insert pratica - oggetto esistente  ");
			throw new SerenaException("cannot insert pratica - oggetto esistente  "+e.getMessage());
			
		} catch (Exception e) {
			logger.error("cannot insert pratica"+e.getMessage());
			throw new SerenaException("cannot insert pratica"+e.getMessage());
		}
		return newID;
		
	}

	protected Element getItemCandidati(String id_utente,HttpServletRequest request, boolean escludiPratichePresenti) throws SerenaException	{
		try {
			logger.debug("[GeneratorePratiche]-> is running");
			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(MY_CLASS, ConstantsXSerena.ACTION_GET, MY_CLASS);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_SELECT);
			currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET, ConstantsXSerena.TARGET_ALL);
			currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET_LEVELS, "2");
			if (escludiPratichePresenti)	{
				currentElement.addAttribute(ConstantsXSerena.ATTR_FORCED_DATASOURCE,DATA_SOURCE_PRATICHE_ANNO_CORRENTE);
			} else {
				currentElement.addAttribute(ConstantsXSerena.ATTR_FORCED_DATASOURCE,DATA_SOURCE_ITEM_LISTINO);
			}
			currentElement=currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
			currentElement=currentElement.addElement("ID_cliente");
			currentElement.setText(id_utente);
			
			Document data = ApplicationLibrary.getData(currentElement.getDocument(),request);
			
			Element dataElem = ApplicationLibrary.prepareDataForPresentation(data);
			String[] messages={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(dataElem, messages,MY_CLASS);
			
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("getItemCandidati has resulted into sql error:"+messages[0]);
				throw new SerenaException(messages[0],"generic_sql_error");
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_EMPTY)
			{
				return null;
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)
			{
				return dataElem;
			}
			else
				return null;
		} catch (PermissionException e) {
			logger.error("getItemCandidati has resulted into sql error"+e.getMessage());
			throw new SerenaException(e.getMessage());
		} catch (DataManagerException e) {
			logger.error("getItemCandidati has resulted into sql error"+e.getMessage());
			throw new SerenaException(e.getMessage());
		}
	}
	 
}
