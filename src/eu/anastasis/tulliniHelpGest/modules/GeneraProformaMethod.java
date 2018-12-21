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
import eu.anastasis.serena.persistence.DecodingSystem;
import eu.anastasis.serena.presentation.templates.ActiveTemplate;
import eu.anastasis.serena.presentation.templates.TemplateFactory;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Attivita;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.CalendarioFatturazione;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.DatiContabili;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.LavoroSuAttivita;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.MyProForma;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.MySpesaAnticipata;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Operatore;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Cliente;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Pratica;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.ProForma;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.SpesaAnticipata;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.TulliniHelpGestBinder;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.MyVoceFattura;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.VoceFattura;
import eu.anastasis.tulliniHelpGest.utils.UtilsProvider;

/**
 * Questa funzione genera automaticamente tutte le proforma in scadenza. Il
 * sistema parte dal "calendario di fatturazione ", riempito automaticamente per
 * ogni cliente ad inizio anno in base a:
 * 
 * data prima proforma numero rate
 * 
 * Da questo calendario seleziona i clienti che hanno fatture "in scadenza",
 * ovvero con data minore o uguale alla data odierna.
 * 
 * Per ognuno di questi clienti, vengono reperite le pratiche NON SOSPESE
 * dell'anno contabile corrente.
 * 
 * Se ci sono pratiche, viene creata una proforma così composta: importo: ogni
 * pratica "a forfait" concorre all'importo complessivo per il suo costo diviso
 * il numero di rate per il cliente; ogni pratica "a prestazione" CHIUSA e NON
 * FATTURATA concorre all'importo complessivo per il suo costo intero; ogni
 * pratica "a ore" concorre all'importo complessivo in base alle ore di lavoro
 * segnate sulle relative attività NON FATTURATE e al costo orario
 * dell'operatore che le ha svolte (le attività cambiano poi stato in
 * "fatturate") spese anticipate: somma di tutte le spese anticape attribuite ad
 * ogni pratica
 * 
 * Alla proforma viene asseganto stato "in attesa", numero (?), viene associata
 * alla relativa entry del calendario di fatturazione (in modo che questa entry
 * non ricompaia alla prossima generazione massiva) e viene registrata
 * 
 * @author afrascari
 * 
 */
public class GeneraProformaMethod extends DefaultMethod {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(GeneraProformaMethod.class);

	private static final String METHOD_NAME = "generaproforma";

	private static final String PARAM_ANNO = "anno";

	public GeneraProformaMethod(DefaultModule parentModule,
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
			ArrayList<String> newIDCollection = new ArrayList<String>();
			ArrayList<Attivita> attivitaDaAggiornare = new ArrayList<Attivita>(); // lista
																					// attivita
																					// di
																					// cui
																					// aggiornare
																					// lo
																					// stato
																					// in
																					// "fatturate"
			ArrayList<Pratica> praticheDaAggiornare = new ArrayList<Pratica>(); // lista
																				// attivita
																				// di
																				// cui
																				// aggiornare
																				// lo
																				// stato
																				// in
																				// "fatturate"
			ArrayList<SpesaAnticipata> speseDaAggiornare = new ArrayList<SpesaAnticipata>(); // lista
																								// attivita
																								// di
																								// cui
																								// aggiornare
																								// lo
																								// stato
																								// in
																								// "fatturate"
			String anno_contabile = request.getParameter(PARAM_ANNO);
			if (anno_contabile == null || anno_contabile.isEmpty()) {
				// anno corrente
				anno_contabile = new Integer(new SerenaDate().getYear())
						.toString();
			}
			logger.info("Invocata generazione massiva proforma per anno contabile "
					+ anno_contabile);
			Element itemCandidati = getItemCandidati(request);
			if (itemCandidati == null) {
				mess = "Nessuna proforma da creare in data odierna";
				template.replaceTagInBlock("MESS", "RESULT_SUCCESS", mess);
				template.publishBlock("RESULT_SUCCESS");
				template.publish();
				return template.getContenuto();
			}
			// reperimento proforma da creare da calendario fatturazione
			List<Element> itemList = itemCandidati.selectNodes(".//"
					+ CalendarioFatturazione.INSTANCE_NAME);
			ProForma p = null;
			DecodingSystem ds = new DecodingSystem();
			TulliniHelpGestBinder binder = new TulliniHelpGestBinder();
			mess += initOutput(mess);
			// per ogni proforma da creare:
			for (Element anItem : itemList) {
				CalendarioFatturazione cf = binder
						.createCalendarioFatturazione(anItem);
				int rataCorrente = cf.getRata().intValue();
				Cliente c = new Cliente();
				c.setId(cf.getPer_cliente().getId());
				c.setCliente(cf.getPer_cliente().getCliente());
				List<String> tipologieCliente = getTipologieCliente(request,c.getId());
				if (tipologieCliente!=null)	{
					String nrate = anItem
							.elementText(CalendarioFatturazione.SLOT_SU_RATE);
					p = new ProForma();
					p.setAnno_contabile(anno_contabile);
					p.setData(cf.getData());
					p.setEntry_calendario(cf);
					p.setInverse_of_proforma(c);
					/*
					 * prima p.setCompetenza(ProForma.COMPETENZA__CONTABILIS);
					 * // default p.setRa(false); p.setRiv_prev(false);
					 */
					// dal 2018
					p.setCompetenza(ProForma.COMPETENZA__STUDIO); // default
					p.setRiv_prev(true);
					if (clienteHasRitenutaAcconto(tipologieCliente))	{
						p.setRa(true); // solo se partita iva, quindi no persone
										// fisiche e forfettari
					}
					p.setStato_proforma(ProForma.STATO_PROFORMA__DA_VALIDARE);
					// quali pratiche/attività/spese anticipate vanno fatturate?
					Element pratiche = getPratiche(request, anno_contabile,
							c.getId());
					if (pratiche != null) {
						// ogni pratica "movimentata" diverrà una voce proforma
						List<Element> praticheCandidate = pratiche
								.selectNodes(".//" + Pratica.INSTANCE_NAME);
						ArrayList<MyVoceFattura> voci = new ArrayList<MyVoceFattura>();
						// si utilizza per calcolare i totali di importi e
						// descrizione di tutte le spese di tutte le attivita di
						// tutte le pratiche:
						MySpesaAnticipata virtualSpesa4Total = new MySpesaAnticipata();
						virtualSpesa4Total.init();
						// cicle sulle pratiche (pratica) candidate alla
						// proforma corrente (p)
						for (Element praticaElem : praticheCandidate) {
							Pratica pratica = binder.createPratica(praticaElem);
							MyVoceFattura vf = null;
							try {
								vf = getMyVoceFattura(pratica, new Integer(
										nrate).intValue(),
										attivitaDaAggiornare,
										praticheDaAggiornare, rataCorrente);
								getSommaSpeseAnticipate(pratica,
										virtualSpesa4Total, speseDaAggiornare);
								if (vf != null) {
									voci.add(vf);
								}
							} catch (Exception e) {
								String msg = "Ignorato cliente <strong>"
										+ c.getCliente()
										+ "</strong> nonstante la scadenza del "
										+ cf.getData()
										+ " per <strong>forfait non prezzato</strong>";
								mess = addOutputEntry(msg, mess);
								logger.info(msg);
								continue;
							}
						}
						if (voci.isEmpty()) {
							// potrebbero essere spese anticpate! fabio:Va bene
							// così, se anticipiamo delle spese significa che
							// abbiamo un incarico, quindi prima o poi ci sarà
							// qualcosa da fatturare.
							String msg = "Nessuna pratica da fatturare per il cliente <strong>"
									+ c.getCliente()
									+ "</strong> nonstante la scadenza del "
									+ cf.getData();
							if (virtualSpesa4Total.getImporto() != null
									&& virtualSpesa4Total.getImporto()
											.floatValue() > 0) {
								msg += " ... ci sarebbero pero' "
										+ virtualSpesa4Total.getImporto()
												.floatValue()
										+ " di spese anticipate!";
							}
							mess = addOutputEntry(msg, mess);
							logger.info(msg);
							continue;
						} else {
							ArrayList<VoceFattura> processedVoci = aggregaVociForfait(
									voci, anItem);
							p.setVoci_proforma(processedVoci);
							p.setSpese_anticipate_fattura(virtualSpesa4Total
									.getImporto());
							p.setSpese_anticipate_desc(virtualSpesa4Total
									.getOggetto());
						}
					} else {
						String msg = "Nessuna pratica da fatturare per il cliente <strong>"
								+ c.getCliente()
								+ "</strong> nonstante la scadenza del "
								+ cf.getData();
						mess = addOutputEntry(msg, mess);
						logger.info(msg);
						continue;
					}
					/*
					 * queste? dipendono dalla "competenza" (Tullini o SRL ...
					 * ma qui non la conosco) p.setRa(ra);
					 * p.setRiv_prev(riv_prev);
					 */
					p.setNumero(MyProForma.getNextNumberContabilis(
							anno_contabile, request));
					try {
						Element theQuery = binder.createElement(p);
						// registrazione proforma
						String aNewID = insertProforma(theQuery, request);
						if (aNewID != null) {
							newIDCollection.add(aNewID);
							String msg = "Proforma ID "
									+ aNewID
									+ " registrata correttamente: <a href=\"?q=object/detail&amp;p="
									+ ProForma.INSTANCE_NAME + "/_a_ID/_v_"
									+ aNewID
									+ "\" title=\"vai alla proforma\">vai</a>";
							mess = addOutputEntry(msg, mess);
							try {
								if (attivitaDaAggiornare.size() > 0) {
									aggiornaStatoAttivita(attivitaDaAggiornare,
											request);
								}
								if (praticheDaAggiornare.size() > 0) {
									aggiornaStatoPratica(praticheDaAggiornare,
											request);
								}
								if (speseDaAggiornare.size() > 0) {
									aggiornaStatoSpese(speseDaAggiornare,
											request);
								}
							} catch (Exception e) {
								msg = "ATTENZIONE: il sistema non &egrave; riuscito ad aggiornare lo stato a <em>fatturate</em> delle attivit&agrave; ad ora computate nella profoma";
								mess = addOutputEntry(msg, mess);
							}
						}
					} catch (SerenaException e) {
						// catching exceptions for each pratica
						String msg = "Proforma cliente <strong>"
								+ c.getCliente()
								+ "</strong> non registrata a causa di un errore/duplicazione;";
						mess = addOutputEntry(msg, mess);
					}
				} else {
					logger.error("Attenzione: cliente senza dati contabili: " + cf.getPer_cliente().getCliente());
				}
			}
			if (mess.isEmpty())
				mess = "Nessuna proforma da generare in data odierna.";
			mess = finaliseOutput(mess);
			template.replaceTagInBlock("MESS", "RESULT_SUCCESS", mess);
			template.publishBlock("RESULT_SUCCESS");
			template.publish();
			return template.getContenuto();

		} catch (Exception e) {
			String errorMessage = "Impossibile inserire le proforma a causa di un errore: contattare l'assistenza.";
			logger.error(errorMessage, e);
			template.replaceTagInBlock("ERROR_MESSAGE", "RESULT_ERROR",
					errorMessage);
			template.publishBlock("RESULT_ERROR");
			template.publish();
			return template.getContenuto();
		}
	}

	private boolean clienteHasRitenutaAcconto(List<String> tipologieCliente) {
		return !tipologieCliente.contains(Cliente.TIPO_CLIENTE__PF) && !tipologieCliente.contains(Cliente.TIPO_CLIENTE__F);
	}

	private ArrayList<String> getTipologieCliente(HttpServletRequest request, String idcliente) throws SerenaException {
		ArrayList<String> daticontabili = new ArrayList<String>();
		try {
			Element currentElement = ObjectUtils
					.getXserenaRequestStandardHeader(request.getSession()
							.getId(), ConstantsXSerena.ACTION_GET,
							DatiContabili.INSTANCE_NAME);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION,
					ConstantsXSerena.VAL_SELECT);
			currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET,
					ConstantsXSerena.TARGET_SPECIFIED);
			currentElement.addElement("ID");
			currentElement.addElement(DatiContabili.SLOT_TIPO_CLIENTE);

			currentElement = currentElement
					.addElement(ConstantsXSerena.TAG_CONDITION);

			Element item = currentElement
					.addElement(DatiContabili.SLOT_INVERSE_OF_DATI_CONTABILI);
			item = item.addElement(Cliente.INSTANCE_NAME);
			item = item.addElement("ID");
			item.setText(idcliente);

			Document data = ApplicationLibrary.getData(
					currentElement.getDocument(), request);

			Element dataElem = ApplicationLibrary
					.prepareDataForPresentation(data);
			String[] messages = { "", "" };
			int res = ConstantsXSerena.getXserenaRequestResult(dataElem,
					messages, DatiContabili.INSTANCE_NAME);

			if (res == ConstantsXSerena.XSERENA_RESULT_ERROR) {
				logger.error("getDatiContabili has resulted into sql error:"
						+ messages[0]);
				throw new SerenaException(messages[0], "generic_sql_error");
			} else if (res == ConstantsXSerena.XSERENA_RESULT_EMPTY) {
				return null;
			} else if (res == ConstantsXSerena.XSERENA_RESULT_SUCCESS) {
				List<Element> theList = dataElem.selectNodes(".//"+DatiContabili.INSTANCE_NAME);
				for (Element e: theList){
					daticontabili.add(e.elementText(DatiContabili.SLOT_TIPO_CLIENTE));
				}
				return daticontabili;
			} else
				return null;
		} catch (PermissionException e) {
			logger.error("getDatiContabili for proforma has resulted into sql error"
					+ e.getMessage());
			throw new SerenaException(e.getMessage());
		} catch (DataManagerException e) {
			logger.error("getDatiContabili for proforma has resulted into sql error"
					+ e.getMessage());
			throw new SerenaException(e.getMessage());
		}
	}

	/**
	 * Aggrega le voci a forfait in un unico item con descrizione presa da
	 * parametro (acconto o saldo)
	 * 
	 * @param voci
	 * @param entryCalendario
	 */
	private ArrayList<VoceFattura> aggregaVociForfait(
			ArrayList<MyVoceFattura> voci, Element entryCalendario)
			throws SerenaException {
		String accontoDesc = ModuleParameterConfiguration.getInstance()
				.getValue("helpgest", "VOCE_FORFAIT_ACCONTO");
		String saldoDesc = ModuleParameterConfiguration.getInstance().getValue(
				"helpgest", "VOCE_FORFAIT_SALDO");
		String nrate = entryCalendario
				.elementText(CalendarioFatturazione.SLOT_SU_RATE);
		String dataEntry = entryCalendario
				.elementText(CalendarioFatturazione.SLOT_DATA);
		int mese = new SerenaDate(dataEntry).getMonth();

		// saldo o acconto? acconto se 1 sola rata oppure mese dicembre
		String forfaitDesc = ("1".equals(nrate) || mese == 12) ? saldoDesc
				: accontoDesc;
		MyVoceFattura forfait = new MyVoceFattura();
		ArrayList<VoceFattura> processedVoci = new ArrayList<VoceFattura>();
		forfait.setOggetto(forfaitDesc);
		forfait.setIva(getIva());
		float importoForfait = 0;
		for (MyVoceFattura vf : voci) {
			if (vf.isFromForfait()) {
				importoForfait += vf.getImporto();
				forfait.setRif_pratica(vf.getRif_pratica()); // il rif non e'
																// preciso
																// perche' e'
																// quello
																// dell'ultima
																// .... ma e' lo
																// stesso
			} else {
				processedVoci.add((VoceFattura) vf);
			}
		}
		if (importoForfait > 0) {
			forfait.setImporto(importoForfait);
			processedVoci.add((VoceFattura) forfait);
		}
		return processedVoci;
	}

	/**
	 * aggiornamento di: ore fatturate
	 * 
	 * @param daAggiornare
	 * @param request
	 * @throws SerenaException
	 */
	private void aggiornaStatoAttivita(ArrayList<Attivita> daAggiornare,
			HttpServletRequest request) throws SerenaException {
		try {
			for (Attivita anAtt : daAggiornare) {
				Element currentElement = ObjectUtils
						.getXserenaRequestStandardHeader(request.getSession()
								.getId(), ConstantsXSerena.ACTION_SET,
								Attivita.INSTANCE_NAME);
				currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION,
						ConstantsXSerena.VAL_UPDATE);
				Element condition = currentElement
						.addElement(ConstantsXSerena.TAG_CONDITION);
				condition = condition.addElement("ID");
				condition.setText(anAtt.getId());
				Element anItem = currentElement
						.addElement(Attivita.SLOT_STATO_ATTIVITA);
				anItem.setText(anAtt.getStato_attivita().toString());
				anItem = currentElement
						.addElement(Attivita.SLOT_MINUTI_ULTIMA_FATTURA);
				anItem.setText(anAtt.getMinuti_ultima_fattura().toString());
				Document data = ApplicationLibrary.setData(
						currentElement.getDocument(), request, true);
				String[] messages2 = { "", "" };
				int res = ConstantsXSerena.getXserenaRequestResult(data,
						messages2, SpesaAnticipata.INSTANCE_NAME);
				if (res == ConstantsXSerena.XSERENA_RESULT_ERROR) {
					logger.error("cannot update ore fatturate in attivita: "
							+ messages2[0]);
					throw new SerenaException(
							"cannot update ore fatturate in attivita: "
									+ messages2[0]);
				}
			}
		} catch (Exception e) {
			logger.error("cannot update ore fatturate attivita"
					+ e.getMessage());
			throw new SerenaException("cannot update  ore fatturate attivita"
					+ e.getMessage());
		}

	}

	private void aggiornaStatoSpese(ArrayList<SpesaAnticipata> daAggiornare,
			HttpServletRequest request) throws SerenaException {
		try {
			// 1 tutte le spese di queste attivita fatturate
			Element currentElement = ObjectUtils
					.getXserenaRequestStandardHeader(request.getSession()
							.getId(), ConstantsXSerena.ACTION_SET,
							SpesaAnticipata.INSTANCE_NAME);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION,
					ConstantsXSerena.VAL_UPDATE);
			Element condition = currentElement
					.addElement(ConstantsXSerena.TAG_CONDITION);
			condition = condition.addElement("ID");
			condition.addAttribute(ConstantsXSerena.ATTR_OPERATOR,
					ConstantsXSerena.VAL_IN);
			String inClause = "";
			for (SpesaAnticipata anExp : daAggiornare) {
				inClause += anExp.getId() + ",";
			}
			inClause = inClause.substring(0, inClause.length() - 1);
			condition.setText(inClause);
			currentElement = currentElement
					.addElement(SpesaAnticipata.SLOT_FATTURATA);
			currentElement.setText("1");
			Document data = ApplicationLibrary.setData(
					currentElement.getDocument(), request, true);
			String[] messages = { "", "" };
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages,
					SpesaAnticipata.INSTANCE_NAME);
			if (res == ConstantsXSerena.XSERENA_RESULT_ERROR) {
				logger.error("cannot update stato fatturate in spese anticipate: "
						+ messages[0]);
				throw new SerenaException(
						"cannot update stato fatturate in spese anticipate: "
								+ messages[0]);
			}
		} catch (Exception e) {
			logger.error("cannot update stato fatturate in spese anticipate"
					+ e.getMessage());
			throw new SerenaException(
					"cannot update stato fatturate in spese anticipate"
							+ e.getMessage());
		}

	}

	private void aggiornaStatoPratica(ArrayList<Pratica> daAggiornare,
			HttpServletRequest request) throws SerenaException {
		try {
			Element currentElement = ObjectUtils
					.getXserenaRequestStandardHeader(request.getSession()
							.getId(), ConstantsXSerena.ACTION_SET,
							Pratica.INSTANCE_NAME);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION,
					ConstantsXSerena.VAL_UPDATE);
			Element condition = currentElement
					.addElement(ConstantsXSerena.TAG_CONDITION);
			condition = condition.addElement("ID");
			condition.addAttribute(ConstantsXSerena.ATTR_OPERATOR,
					ConstantsXSerena.VAL_IN);
			String inClause = "";
			for (Pratica anAtt : daAggiornare) {
				inClause += anAtt.getId() + ",";
			}
			inClause = inClause.substring(0, inClause.length() - 1);
			condition.setText(inClause);
			currentElement = currentElement.addElement(Pratica.SLOT_STATO);
			currentElement.setText(Pratica.STATO__FATTURATA);
			Document data = ApplicationLibrary.setData(
					currentElement.getDocument(), request, true);
			String[] messages2 = { "", "" };
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages2,
					Pratica.INSTANCE_NAME);
			if (res == ConstantsXSerena.XSERENA_RESULT_ERROR) {
				logger.error("cannot update stato fatturate in pratiche: "
						+ messages2[0]);
				throw new SerenaException(
						"cannot update stato fatturate in pratiche: "
								+ messages2[0]);
			}
		} catch (Exception e) {
			logger.error("cannot update stato fatturate in pratiche"
					+ e.getMessage());
			throw new SerenaException(
					"cannot update stato fatturate in pratiche"
							+ e.getMessage());
		}

	}

	/**
	 * Torna la voce fattura corrispondente alla pratica, con oggetto e importo
	 * 
	 * @param pratica
	 * @param nRate
	 * @return
	 * @throws SerenaException
	 */
	private MyVoceFattura getMyVoceFattura(Pratica pratica, int nRate,
			ArrayList<Attivita> attDaAggiornare,
			ArrayList<Pratica> praDaAggiornare, int rataCorrente)
			throws SerenaException {
		MyVoceFattura vf = new MyVoceFattura();
		if (pratica.getTitolo_per_fattura() != null
				&& !pratica.getTitolo_per_fattura().isEmpty()) {
			vf.setOggetto(pratica.getTitolo_per_fattura());
		} else {
			vf.setOggetto(pratica.getTitolo());
		}
		if (vf.getOggetto() == null || vf.getOggetto().isEmpty()) {
			String message = "Voce fattura senza oggetto a causa di pratica senza titolo!!!"
					+ pratica.getId() + " metto titolo fittizio...";
			vf.setOggetto("Da pratica n. " + pratica.getId());
			logger.fatal(message);
		}
		String iva = getIva();
		// calcolo importo:
		float importo = 0;
		if (pratica.getTipo().equals(Pratica.TIPO__A_FORFAIT)) {
			// forfait: IGNORO STATO: il costo della pratica / il numero di rate
			if (pratica.getPrezzo() == null) {
				String message = "Pratica a forfait senza prezzo: cliente ignorato causa pratica forfait: "
						+ pratica.getId();
				logger.fatal(message);
				throw new SerenaException(message);
			}
			importo = new Float(pratica.getPrezzo()).floatValue() / nRate;
			vf.setFromForfait(true);
			if (rataCorrente == nRate) {
				praDaAggiornare.add(pratica); // le pratiche a forfait diventano
												// FATTURATE solo all'ULTIMA
												// RATA
			}
		} else if (pratica.getTipo().equals(Pratica.TIPO__A_PRESTAZIONE)) {
			if (pratica.getStato().equals(Pratica.STATO__CHIUSA)) {
				// prestazione: SE CHIUSA: il costo della pratica interno
				if (pratica.getPrezzo() == null) {
					String message = "Pratica a prestazione senza prezzo: considero 0: "
							+ pratica.getId();
					logger.warn(message);
					importo = new Float(0);
				} else {
					importo = new Float(pratica.getPrezzo()).floatValue();
				}
				praDaAggiornare.add(pratica); // le pratiche a prestazione
												// diventano SEMPRE FATTURATE
			} else {
				logger.warn("Pratica " + pratica.getId() + "("
						+ pratica.getTitolo()
						+ ") a prestazione viene ignorata perche non CHIUSA");
				return null;
			}
		} else if (pratica.getTipo().equals(Pratica.TIPO__A_ORA)) {
			// ad ora: calcolo in base alle ore lavorate * costi orari operatori
			// attività
			ArrayList<Attivita> attivita = pratica.getAttivita();
			for (Attivita att : attivita) {
				int costo = getPrezzoPerAttivita(att);
				if (costo > 0) {
					importo += costo;
					attDaAggiornare.add(att);
					if (pratica.getStato().equals(Pratica.STATO__CHIUSA)) {
						praDaAggiornare.add(pratica); // le pratiche a ore
														// diventano FATTURATE
														// SOLO se erano chiuse
					}
				}
			}
		} else {
			String mess = "Pratica " + pratica.getId() + "("
					+ pratica.getTitolo() + ") ha tipo sconosciuto";
			logger.error(mess);
			throw new SerenaException(mess);
		}
		if (importo > 0) {
			vf.setImporto(importo);
			vf.setIva(iva);
			vf.setRif_pratica(new Integer(pratica.getId()));
		} else {
			vf = null;
		}
		return vf;
	}

	/**
	 * Calcola il prezzo applicato per l'attività come somma dei minuti di tutte
	 * le sessioni di lavoro * costo orario operatore / 60
	 * 
	 * @param att
	 * @return
	 * @throws SerenaException
	 */
	private int getPrezzoPerAttivita(Attivita att) throws SerenaException {
		try {
			int ret = 0;
			int minutiFatturati = att.getStato_attivita().intValue();
			ArrayList<LavoroSuAttivita> sessioniDiLavoro = att
					.getSessioni_di_lavoro();
			if (sessioniDiLavoro.size() > 0) {
				int minuti = 0;
				eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.MyOperatore o = new eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.MyOperatore();
				o = o.getInstance(att.getAssegnata_a().getId());
				int costoHF = o.getRivendita_oraria().intValue();
				for (LavoroSuAttivita lsa : sessioniDiLavoro) {
					minuti += lsa.getDurata_minuti().intValue();
				}
				logger.trace("Attivita " + att.getId() + " minuti di lavoro: "
						+ minuti + " di cui " + minutiFatturati
						+ " gia fatturate");
				// aggiorni minuti fatturati attivita
				att.setMinuti_ultima_fattura(att.getStato_attivita()); // memo
																		// valore
																		// precedente
																		// di
																		// minuti
																		// fatturati
																		// per
																		// rollback
				att.setStato_attivita(minuti);
				// sottraggo i minuti gia fatturati:
				minuti -= minutiFatturati;
				ret = costoHF * minuti / 60;
			} else {
				logger.warn("Attivita " + att.getId()
						+ " senza sessioni di lavoro");
			}
			return ret;
		} catch (Exception e) {
			String msg = "Attivita "
					+ att.getId()
					+ " impossibile determinare il costo quindi non entra in proforma "
					+ e.getMessage();
			logger.error(msg);
			throw new SerenaException(msg);
		}
	}

	/**
	 * desume somma e concatenzaione descrizione della spese anticipate
	 * 
	 * @param pratica
	 * @return
	 */
	private void getSommaSpeseAnticipate(Pratica pratica, MySpesaAnticipata va,
			ArrayList<SpesaAnticipata> daAggiornare) {
		ArrayList<Attivita> attivita = pratica.getAttivita();
		float costoSpeseAnticipate = 0;
		StringBuffer spesaDesc = new StringBuffer();
		boolean any = false;
		for (Attivita att : attivita) {
			ArrayList<SpesaAnticipata> spese = att.getSpese_anticipate();
			if (spese.size() > 0) {
				for (SpesaAnticipata sa : spese) {
					if (!sa.getFatturata()) {
						daAggiornare.add(sa);
						any = true;
						costoSpeseAnticipate += new Float(sa.getImporto())
								.floatValue();
						logger.trace("Calcolo spese anticipate: aggiungo "
								+ sa.getOggetto() + ": " + sa.getImporto()
								+ " totale = " + costoSpeseAnticipate);
						String data = new SerenaDate(sa.getData()).toString();
						spesaDesc.append("<strong>"
								+ sa.getOggetto()
								+ "</strong> ("
								+ data
								+ "): &euro; "
								+ new UtilsProvider().formatImporto(sa
										.getImporto()) + ";<br />");
					}
				}
			}
		}
		// if (costoSpeseAnticipate>0){ // nel caso sfortunato di importi +/-
		// che si annullano a vicenda, non metteva nulla!
		if (any) {
			va.appendOggetto(spesaDesc.toString());
			va.addImporto(costoSpeseAnticipate);
		}
	}

	/**
	 * Torna la lista delle pratiche APERTE OPPURE CHIUSE CON DATA CHIUSURA (per
	 * evitare quelle "in attesa") del cliente per l'anno contabile CORRENTE
	 * VECCHIA VERSIONE: NON SOSPESE e NON FATTURATE
	 * 
	 * @param request
	 * @param anno_contabile
	 * @param idcliente
	 * @return
	 * @throws SerenaException
	 */
	private Element getPratiche(HttpServletRequest request,
			String anno_contabile, String idcliente) throws SerenaException {
		try {
			Element currentElement = ObjectUtils
					.getXserenaRequestStandardHeader(request.getSession()
							.getId(), ConstantsXSerena.ACTION_GET,
							Pratica.INSTANCE_NAME);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION,
					ConstantsXSerena.VAL_SELECT);
			currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET,
					ConstantsXSerena.TARGET_SPECIFIED);
			currentElement.addElement("ID");
			currentElement.addElement(Pratica.SLOT_PREZZO);
			currentElement.addElement(Pratica.SLOT_TIPO);
			currentElement.addElement(Pratica.SLOT_TITOLO);
			currentElement.addElement(Pratica.SLOT_STATO);
			currentElement.addElement(Pratica.SLOT_TITOLO_PER_FATTURA);
			Element att = currentElement.addElement(Pratica.SLOT_ATTIVITA);
			att = att.addElement(Attivita.INSTANCE_NAME);
			att.addElement(Attivita.SLOT_STATO_ATTIVITA);
			att.addElement(Attivita.SLOT_MINUTI_ULTIMA_FATTURA);
			att.addElement(Attivita.SLOT_TITOLO);
			Element spese = att.addElement(Attivita.SLOT_SPESE_ANTICIPATE);
			spese = spese.addElement(SpesaAnticipata.INSTANCE_NAME);
			spese.addElement(SpesaAnticipata.SLOT_DATA);
			spese.addElement(SpesaAnticipata.SLOT_IMPORTO);
			spese.addElement(SpesaAnticipata.SLOT_OGGETTO);
			Element sessioni = att.addElement(Attivita.SLOT_SESSIONI_DI_LAVORO);
			sessioni = sessioni.addElement(LavoroSuAttivita.INSTANCE_NAME);
			sessioni.addElement(LavoroSuAttivita.SLOT_DATA);
			sessioni.addElement(LavoroSuAttivita.SLOT_DIARIO);
			sessioni.addElement(LavoroSuAttivita.SLOT_DURATA_MINUTI);
			Element assegnata = att.addElement(Attivita.SLOT_ASSEGNATA_A);
			assegnata = assegnata.addElement(Operatore.INSTANCE_NAME);
			assegnata.addElement("ID");
			assegnata.addElement(Operatore.SLOT_NOME_E_COGNOME);

			currentElement = currentElement
					.addElement(ConstantsXSerena.TAG_CONDITION);

			Element item = currentElement
					.addElement(Pratica.SLOT_CLIENTE_PRATICA);
			item = item.addElement(Cliente.INSTANCE_NAME);
			item = item.addElement("ID");
			item.setText(idcliente);
			item = currentElement.addElement(Pratica.SLOT_ANNO_CONTABILE);
			item.setText(anno_contabile);

			Element stato = currentElement.addElement(ConstantsXSerena.TAG_OR);
			item = stato.addElement(Pratica.SLOT_STATO);
			item.setText(Pratica.STATO__APERTA);
			Element and = stato.addElement(ConstantsXSerena.TAG_AND);
			item = and.addElement(Pratica.SLOT_STATO);
			item.setText(Pratica.STATO__CHIUSA);
			item = and.addElement(Pratica.SLOT_DATA_CHIUSURA);
			item.addAttribute(ConstantsXSerena.ATTR_OPERATOR,
					ConstantsXSerena.VAL_NOT);
			item.setText(ConstantsXSerena.VAL_NULL);

			Document data = ApplicationLibrary.getData(
					currentElement.getDocument(), request);

			Element dataElem = ApplicationLibrary
					.prepareDataForPresentation(data);
			String[] messages = { "", "" };
			int res = ConstantsXSerena.getXserenaRequestResult(dataElem,
					messages, Pratica.INSTANCE_NAME);

			if (res == ConstantsXSerena.XSERENA_RESULT_ERROR) {
				logger.error("getPratiche for proforma has resulted into sql error:"
						+ messages[0]);
				throw new SerenaException(messages[0], "generic_sql_error");
			} else if (res == ConstantsXSerena.XSERENA_RESULT_EMPTY) {
				return null;
			} else if (res == ConstantsXSerena.XSERENA_RESULT_SUCCESS) {
				return dataElem;
			} else
				return null;
		} catch (PermissionException e) {
			logger.error("getPratiche for proforma has resulted into sql error"
					+ e.getMessage());
			throw new SerenaException(e.getMessage());
		} catch (DataManagerException e) {
			logger.error("getPratiche for proforma has resulted into sql error"
					+ e.getMessage());
			throw new SerenaException(e.getMessage());
		}
	}

	private String insertProforma(Element theQuery, HttpServletRequest request)
			throws SerenaException {
		String newID = null;
		try {

			Element currentElement = ObjectUtils
					.getXserenaRequestStandardHeader(request.getSession()
							.getId(), ConstantsXSerena.ACTION_SET,
							ProForma.INSTANCE_NAME);
			Element serviceElement = currentElement.getParent();
			serviceElement.remove(currentElement);
			serviceElement.add((Element) theQuery.clone());
			currentElement = serviceElement.element(ProForma.INSTANCE_NAME);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION,
					ConstantsXSerena.VAL_INSERT);

			Document data = ApplicationLibrary.setData(
					currentElement.getDocument(), request, true);
			String[] messages2 = { "", "" };
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages2,
					ProForma.INSTANCE_NAME);
			if (res == ConstantsXSerena.XSERENA_RESULT_ERROR) {
				logger.error("cannot insert prpforma: " + messages2[0]);
				throw new SerenaException("cannot insert prpforma: "
						+ messages2[0]);
			} else if (res == ConstantsXSerena.XSERENA_RESULT_SUCCESS) {
				newID = messages2[1];
			}
		} catch (ObjectAlreadyExistsException e) {
			logger.error("cannot insert prpforma - oggetto esistente  ");
			throw new SerenaException(
					"cannot insert prpforma - oggetto esistente  "
							+ e.getMessage());

		} catch (Exception e) {
			logger.error("cannot insert prpforma" + e.getMessage());
			throw new SerenaException("cannot insert prpforma" + e.getMessage());
		}
		return newID;

	}

	/**
	 * GLi item candidati sono le entry del calendario ancora senza proforma
	 * assiciata con data <= oggi
	 * 
	 * @param request
	 * @return
	 * @throws SerenaException
	 */
	protected Element getItemCandidati(HttpServletRequest request)
			throws SerenaException {
		try {
			Element currentElement = ObjectUtils
					.getXserenaRequestStandardHeader(request.getSession()
							.getId(), ConstantsXSerena.ACTION_GET,
							CalendarioFatturazione.INSTANCE_NAME);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION,
					ConstantsXSerena.VAL_SELECT);
			currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET,
					ConstantsXSerena.TARGET_ALL);
			currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET_LEVELS,
					"2");
			currentElement.addAttribute(
					ConstantsXSerena.ATTR_FORCED_DATASOURCE,
					"preview-generazione");
			/*
			 * currentElement=currentElement.addElement(ConstantsXSerena.
			 * TAG_CONDITION); Element item
			 * =currentElement.addElement("proforma_emessa");
			 * item.setText(ConstantsXSerena.VAL_NULL); item
			 * =currentElement.addElement("data");
			 * item.addAttribute(ConstantsXSerena
			 * .ATTR_OPERATOR,ConstantsXSerena.VAL_LESS_THAN); item.setText(new
			 * SerenaDate().toString());
			 */
			Document data = ApplicationLibrary.getData(
					currentElement.getDocument(), request);

			Element dataElem = ApplicationLibrary
					.prepareDataForPresentation(data);
			String[] messages = { "", "" };
			int res = ConstantsXSerena.getXserenaRequestResult(dataElem,
					messages, CalendarioFatturazione.INSTANCE_NAME);

			if (res == ConstantsXSerena.XSERENA_RESULT_ERROR) {
				logger.error("getItemCandidati for proforma has resulted into sql error:"
						+ messages[0]);
				throw new SerenaException(messages[0], "generic_sql_error");
			} else if (res == ConstantsXSerena.XSERENA_RESULT_EMPTY) {
				return null;
			} else if (res == ConstantsXSerena.XSERENA_RESULT_SUCCESS) {
				return dataElem;
			} else
				return null;
		} catch (PermissionException e) {
			logger.error("getItemCandidati for proforma has resulted into sql error"
					+ e.getMessage());
			throw new SerenaException(e.getMessage());
		} catch (DataManagerException e) {
			logger.error("getItemCandidati for proforma has resulted into sql error"
					+ e.getMessage());
			throw new SerenaException(e.getMessage());
		}
	}

	private String initOutput(String output) {
		return output + "<ul>";
	}

	private String finaliseOutput(String output) {
		return output + "</ul>";
	}

	private String addOutputEntry(String entry, String output) {
		return output + "<li>" + entry + "</li>";
	}

	protected String getIva() throws SerenaException {
		try {
			return ModuleParameterConfiguration.getInstance().getValue(
					"helpgest", "IVA_DEFAULT");
		} catch (Exception e) {
			String message = "DEFAULT (system par) IVA IS MISSING!!!!!";
			logger.fatal(message);
			throw new SerenaException(message);
		}
	}

}
