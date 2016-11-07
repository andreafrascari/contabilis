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
import eu.anastasis.serena.application.index.util.ModuleParameterConfiguration;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.auth.exceptions.PermissionException;
import eu.anastasis.serena.common.SerenaDate;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.dataManager.DataManagerException;
import eu.anastasis.serena.dataManager.ObjectAlreadyExistsException;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.serena.presentation.templates.ActiveTemplate;
import eu.anastasis.serena.presentation.templates.TemplateFactory;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Attivita;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Cliente;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Operatore;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Pratica;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.TulliniHelpGestBinder;

public class MigraPratichePendentiMethod extends DefaultMethod 
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MigraPratichePendentiMethod.class);


	private static final String METHOD_NAME = "migrapratichependenti";

	public MigraPratichePendentiMethod(DefaultModule parentModule,String[] defaultParameters)
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
			String anno_corrente = new Integer(new SerenaDate().getYear()).toString();
			String consentitaFinoA = ModuleParameterConfiguration.getInstance().getValue("helpgest","MIGRAZIONE_PRATICHE_FINO_A");
			if (new SerenaDate().getMonth()> new Integer(consentitaFinoA.trim()).intValue())	{
				String errorMessage = "Operazione consentita solo al mese "+consentitaFinoA;
				logger.error(errorMessage);
				template.replaceTagInBlock("ERROR_MESSAGE", "RESULT_ERROR",errorMessage );
				template.publishBlock("RESULT_ERROR");
				template.publish();
				return template.getContenuto();
			}
			String anno_passato = new Integer(new Integer(anno_corrente).intValue()-1).toString();
						
			Element itemCandidati = getPratiche(request,anno_passato);
			if (itemCandidati==null){
				mess= "Nessuna pratica da migrare da anno contabile passato";
				template.replaceTagInBlock("MESS", "RESULT_SUCCESS",mess );
				template.publishBlock("RESULT_SUCCESS");
				template.publish();
				return template.getContenuto();
			}
			List<Element> itemList = itemCandidati.selectNodes(".//"+Pratica.INSTANCE_NAME);
			TulliniHelpGestBinder binder = new TulliniHelpGestBinder();
			mess +=initOutput(mess);
			mess = addOutputEntry("Pratiche da processare "+itemList.size(),mess);
			// per ogni  pratica da migrare:
			Pratica p=null;
			Pratica p0=null;
			for (Element anItem: itemList)	{
				try	{
					logger.trace("processing pratica " + anItem.elementText("ID"));
					System.out.println("processing pratica " + anItem.elementText("ID"));
					p0 = binder.createPratica(anItem);
					if (p0==null){
						String msg = "Ignorata pratica<strong> "+anItem.elementText("ID")+"</strong>: il sistema non riesce a processarla causa caratteri illegali (simboli strani?)";	
						mess = addOutputEntry(msg,mess);
						logger.info(msg);
						continue;						
					}
					if (p0.getCliente_pratica()==null){
						String msg = "Ignorata pratica<strong> "+anItem.elementText("ID")+"</strong> in quanto non associata ad un cliente attivo.";	
						mess = addOutputEntry(msg,mess);
						logger.info(msg);
						continue;						
					}
					if (p0.getResponsabile()==null){
						String msg = "Ignorata pratica<strong> "+anItem.elementText("ID")+"</strong> in quanto non associata ad un responsabile attivo.";	
						mess = addOutputEntry(msg,mess);
						logger.info(msg);
						continue;						
					}
					p = new Pratica();
					p.setAnno_contabile(anno_corrente);
					p.setCliente_pratica(p0.getCliente_pratica());
					p.setNote(p0.getNote());
					p.setPrezzo(p0.getPrezzo());
					p.setResponsabile(p0.getResponsabile());
					p.setStato(p0.getStato());
					p.setTipo(p0.getTipo());
					p.setTitolo(p0.getTitolo());
					p.setTitolo_per_fattura(p0.getTitolo_per_fattura());
					processPratica(request,p0,p);
					String msg = "Copiata pratica <strong>"+p0.getId() + " " + p0.getTitolo()+"</strong> di "+p0.getCliente_pratica().getCliente();	
					mess = addOutputEntry(msg,mess);

				} catch (ObjectAlreadyExistsException e) 	{
					String msg = "Ignorata pratica<strong> "+p0.getId() + " " + p0.getTitolo()+"</strong> di "+p0.getCliente_pratica().getCliente()+ " in quanto gia registrata;";	
					mess = addOutputEntry(msg,mess);
					logger.info(msg);
					continue;
				} catch (Exception e) {
					String msg = "Ignorata pratica<strong> "+p0.getId() + " " + p0.getTitolo()+"</strong> di "+p0.getCliente_pratica().getCliente()+ " a causa di un errore;";	
					mess = addOutputEntry(msg,mess);
					logger.info(msg);
					continue;
				}
			}
			if (mess.isEmpty())
				mess = "Nessuna proforma da generare in data odierna.";
			mess = finaliseOutput(mess);
			template.replaceTagInBlock("MESS", "RESULT_SUCCESS",mess );
			template.publishBlock("RESULT_SUCCESS");
			template.publish();
			return template.getContenuto();
			
		} catch (Exception e)
		{
			String errorMessage = "Impossibile inserire le proforma a causa di un errore: contattare l'assistenza.";
			logger.error(errorMessage, e);
			template.replaceTagInBlock("ERROR_MESSAGE", "RESULT_ERROR",errorMessage );
			template.publishBlock("RESULT_ERROR");
			template.publish();
			return template.getContenuto();
		}
	}
 

	/**
	 * CORE della procedura: processa la pratica:
	 * 1) la pratica a prestazione viene CHIUSA per l'anno corrente, e creata tale quale per l'anno successivo
	 * 2) la pratica a ore viene MARCATA FATTURATA per l'anno corrente, e creata tale quale per l'anno successivo
	 * @param request
	 * @param p0
	 * @param p
	 * @throws SerenaException
	 * @throws ObjectAlreadyExistsException
	 */
	private void processPratica(HttpServletRequest request, Pratica p0, Pratica p) throws SerenaException,ObjectAlreadyExistsException {
	 	String newId = insertPratica(request,p);
	 	migraAttivita2Pratica(request,newId,p0);
		 if (p0.getTipo().equals(Pratica.TIPO__A_PRESTAZIONE)){
			 	chiudiPratica(p0,request);
		 } else if (p0.getTipo().equals(Pratica.TIPO__A_ORA)){
			 	marcaPraticaFatturata(p0,request);
		 } else {
			 logger.error("pratica di tipo " + p0.getTipo() + " non gestita");
			throw new SerenaException("pratica di tipo " + p0.getTipo() + " non gestita");
		 }
	}
 
	private String insertPratica(HttpServletRequest request, Pratica p) throws SerenaException, ObjectAlreadyExistsException {
		String newID =null;
		try	{
			Element theElement = new TulliniHelpGestBinder().createElement(p);
			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(request.getSession().getId(), ConstantsXSerena.ACTION_SET, Pratica.INSTANCE_NAME);
			Element serviceElement  = currentElement.getParent();
			serviceElement.remove(currentElement);
			serviceElement.add((Element)theElement.clone());
			currentElement = serviceElement.element(Pratica.INSTANCE_NAME);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_INSERT);
			Document data = ApplicationLibrary.setData(currentElement.getDocument(),request, true);
			String[] messages2={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages2,Pratica.INSTANCE_NAME);
			if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)	{
				newID = messages2[1];
				logger.trace("pratica: "+newID + " inserita correttamente.");
			} else if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("cannot insert pratica: "+messages2[0]);
				throw new SerenaException("cannot insert pratica: "+messages2[0]);
			}		
		} catch (ObjectAlreadyExistsException e)
		{
			logger.error("cannot insert Pratica - oggetto esistente: "+p.getTitolo());
			throw e;
			
		} catch (Exception e) {
			logger.error("cannot insert pratica "+e.getMessage());
			throw new SerenaException("cannot insert pratica "+e.getMessage(),e);
		}
		return newID;
	}

	private void migraAttivita2Pratica(HttpServletRequest request, String newId, Pratica p0)  throws SerenaException {
		try	{
			ArrayList<Attivita> attivita = p0.getAttivita();
			if (attivita==null || attivita.isEmpty()){
				logger.error("pratica senza attivita: " + p0.getTitolo() + " "+p0.getId());
				return;
			}
			for (Attivita anAtt: attivita)	{
				Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(request.getSession().getId(), ConstantsXSerena.ACTION_SET, Attivita.INSTANCE_NAME);
				currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_UPDATE);
				Element anItem = currentElement.addElement(Attivita.SLOT_INVERSE_OF_ATTIVITA);
				anItem = anItem.addElement(Pratica.INSTANCE_NAME);
				anItem.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_INSERT);
				anItem = anItem.addElement("ID");
				anItem.setText(newId);
				Element condition = currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
				condition = condition.addElement("ID");
				condition.setText(anAtt.getId());
				Document data = ApplicationLibrary.setData(currentElement.getDocument(),request, true);
				String[] messages2={"",""};
				int res = ConstantsXSerena.getXserenaRequestResult(data, messages2,Attivita.INSTANCE_NAME);
				if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
				{
					logger.error("cannot update attivita: "+messages2[0]);
					throw new SerenaException("cannot update attivita: "+messages2[0]);
				}			
			}
		} catch (Exception e) {
			logger.error("cannot update attivita"+e.getMessage());
			throw new SerenaException("cannot update attivita"+e.getMessage());
		}
		
	}
 

	 
	private void chiudiPratica(Pratica daAggiornare, HttpServletRequest request)  throws SerenaException {
		try	{
			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(request.getSession().getId(), ConstantsXSerena.ACTION_SET, Pratica.INSTANCE_NAME);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_UPDATE);
			Element condition = currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
			condition = condition.addElement("ID");
			condition.setText(daAggiornare.getId());
			Element item  = currentElement.addElement(Pratica.SLOT_STATO);
			item.setText(Pratica.STATO__CHIUSA);
			item  = currentElement.addElement(Pratica.SLOT_DATA_CHIUSURA);
			item.setText(new SerenaDate().toString());
			Document data = ApplicationLibrary.setData(currentElement.getDocument(),request, true);
			String[] messages2={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages2,Pratica.INSTANCE_NAME);
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("cannot update stato chiusa in pratiche: "+messages2[0]);
				throw new SerenaException("cannot update stato chiusa in pratiche: "+messages2[0]);
			}			
		} catch (Exception e) {
			logger.error("cannot update stato chiusa in pratiche"+e.getMessage());
			throw new SerenaException("cannot update stato chiusa in pratiche"+e.getMessage());
		}
	}
	
	private void marcaPraticaFatturata(Pratica daAggiornare, HttpServletRequest request)  throws SerenaException {
		try	{
			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(request.getSession().getId(), ConstantsXSerena.ACTION_SET, Pratica.INSTANCE_NAME);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_UPDATE);
			Element condition = currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
			condition = condition.addElement("ID");
			condition.setText(daAggiornare.getId());
			Element item  = currentElement.addElement(Pratica.SLOT_STATO);
			item.setText(Pratica.STATO__FATTURATA);
			Document data = ApplicationLibrary.setData(currentElement.getDocument(),request, true);
			String[] messages2={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages2,Pratica.INSTANCE_NAME);
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("cannot update stato fatturata in pratiche: "+messages2[0]);
				throw new SerenaException("cannot update stato fatturata in pratiche: "+messages2[0]);
			}			
		} catch (Exception e) {
			logger.error("cannot update stato fatturata in pratiche"+e.getMessage());
			throw new SerenaException("cannot update stato fatturata in pratiche"+e.getMessage());
		}
	}

 
 

	/**
	 * Torna la lista delle pratiche DA MIGRARE: anno contabile = "anno passato" && (stato = aperta || stato = sospesa) && (tipo = ore || tipo = a prestazione) 
	 * @param request	
	 * @param anno_contabile
	 * @param idcliente
	 * @return
	 * @throws SerenaException
	 */
	private Element getPratiche(HttpServletRequest request,
			String anno_contabile) throws SerenaException	{
		try {
			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(request.getSession().getId(), ConstantsXSerena.ACTION_GET, Pratica.INSTANCE_NAME);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_SELECT);
			currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET, ConstantsXSerena.TARGET_SPECIFIED);
			
			Element cond=currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
			Element item = cond.addElement(Pratica.SLOT_ANNO_CONTABILE);
			item.setText(anno_contabile);
			
			Element or =cond.addElement(ConstantsXSerena.TAG_OR);
			item =or.addElement(Pratica.SLOT_STATO);
			item.setText(Pratica.STATO__APERTA);			
			item =or.addElement(Pratica.SLOT_STATO);
			item.setText(Pratica.STATO__SOSPESA);
			
			or =cond.addElement(ConstantsXSerena.TAG_OR);
			item =or.addElement(Pratica.SLOT_TIPO);
			item.setText(Pratica.TIPO__A_ORA);
			item =or.addElement(Pratica.SLOT_TIPO);
			item.setText(Pratica.TIPO__A_PRESTAZIONE);

			currentElement.addElement("ID");
			currentElement.addElement(Pratica.SLOT_PREZZO);
			currentElement.addElement(Pratica.SLOT_TIPO);
			currentElement.addElement(Pratica.SLOT_TITOLO);
			currentElement.addElement(Pratica.SLOT_STATO);
			currentElement.addElement(Pratica.SLOT_TITOLO_PER_FATTURA);

			Element cliente = currentElement.addElement(Pratica.SLOT_CLIENTE_PRATICA);
			cliente = cliente.addElement(Cliente.INSTANCE_NAME);
			cliente.addElement("ID");

			Element responsabile = currentElement.addElement(Pratica.SLOT_RESPONSABILE);
			responsabile = responsabile.addElement(Operatore.INSTANCE_NAME);
			responsabile.addElement("ID");
			
			Element att = currentElement.addElement(Pratica.SLOT_ATTIVITA);
			att = att.addElement(Attivita.INSTANCE_NAME);
			att.addElement("ID");

			/*
			att.addElement(Attivita.SLOT_STATO_ATTIVITA);
			att.addElement(Attivita.SLOT_MINUTI_ULTIMA_FATTURA);
			att.addElement(Attivita.SLOT_TITOLO);
			Element spese = att.addElement(Attivita.SLOT_SPESE_ANTICIPATE);
			spese = spese.addElement(SpesaAnticipata.INSTANCE_NAME);
			spese.addElement(SpesaAnticipata.SLOT_DATA);
			spese.addElement(SpesaAnticipata.SLOT_IMPORTO);
			spese.addElement(SpesaAnticipata.SLOT_OGGETTO);
			Element sessioni= att.addElement(Attivita.SLOT_SESSIONI_DI_LAVORO);
			sessioni = sessioni.addElement(LavoroSuAttivita.INSTANCE_NAME);
			sessioni.addElement(LavoroSuAttivita.SLOT_DATA);
			sessioni.addElement(LavoroSuAttivita.SLOT_DIARIO);
			sessioni.addElement(LavoroSuAttivita.SLOT_DURATA_MINUTI);
			Element assegnata = att.addElement(Attivita.SLOT_ASSEGNATA_A);
			assegnata = assegnata.addElement(Operatore.INSTANCE_NAME);
			assegnata.addElement("ID");
			assegnata.addElement(Operatore.SLOT_NOME_E_COGNOME);
			 */
			
			Document data = ApplicationLibrary.getData(currentElement.getDocument(),request);
			
			Element dataElem = ApplicationLibrary.prepareDataForPresentation(data);
			String[] messages={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(dataElem, messages,Pratica.INSTANCE_NAME);
			
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("getPratiche for proforma has resulted into sql error:"+messages[0]);
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
			logger.error("getPratiche for proforma has resulted into sql error"+e.getMessage());
			throw new SerenaException(e.getMessage());
		} catch (DataManagerException e) {
			logger.error("getPratiche for proforma has resulted into sql error"+e.getMessage());
			throw new SerenaException(e.getMessage());
		}
	}
 
	
	private String initOutput(String output)	{
		return  output + "<ul>";
	}
	
	private String finaliseOutput(String output)	{
		return  output + "</ul>";
	}
	
	private String addOutputEntry(String entry, String output)	{
		return output + "<li>" + entry + "</li>";
	}


	 
}
