package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

import eu.anastasis.serena.beans.SerenaMarshallable;

public class Cliente implements SerenaMarshallable
{

	public static final java.lang.String NON_PROFIT__ACR = "ACR";

	public static final java.lang.String STATO_CLIENTE__IN_ATTESA_DI_VERIFICA = "in attesa di verifica";
	public static final java.lang.String STATO_CLIENTE__VERIFICATO_DAL_RESPONSABILE = "verificato dal responsabile";

	public static final java.lang.String TIPO_CLIENTE__PF = "PF";
	public static final java.lang.String TIPO_CLIENTE__SP = "SP";
	public static final java.lang.String TIPO_CLIENTE__P = "P";
	public static final java.lang.String TIPO_CLIENTE__I = "I";
	public static final java.lang.String TIPO_CLIENTE__AP = "AP";
	public static final java.lang.String TIPO_CLIENTE__NP = "NP";
	public static final java.lang.String TIPO_CLIENTE__SC = "SC";
	public static final java.lang.String TIPO_CLIENTE__CP = "CP";
	public static final java.lang.String TIPO_CLIENTE__F = "F"; // forfait, aggiunto manualemente

	public static final java.lang.String TIPO_SOLLECITO__SMS = "sms";
	public static final java.lang.String TIPO_SOLLECITO__MAIL = "mail";


	public static final java.lang.String INSTANCE_NAME = "Cliente";
	public static final java.lang.String SLOT_ALLEGATO = "allegato";
	public static final java.lang.String SLOT_ALLEGATO_1 = "allegato_1";
	public static final java.lang.String SLOT_AVVISI_SCADENZE = "avvisi_scadenze";
	public static final java.lang.String SLOT_CALENDARIO_FATTURAZIONE = "calendario_fatturazione";
	public static final java.lang.String SLOT_CAP = "cap";
	public static final java.lang.String SLOT_CELLULARE = "cellulare";
	public static final java.lang.String SLOT_CESSATA_ASSISTENZA_IL = "cessata_assistenza_il";
	public static final java.lang.String SLOT_CLIENTE = "cliente";
	public static final java.lang.String SLOT_CLIENTE_DAL = "cliente_dal";
	public static final java.lang.String SLOT_CODICE_CLIENTE = "codice_cliente";
	public static final java.lang.String SLOT_CODICE_FISCALE = "codice_fiscale";
	public static final java.lang.String SLOT_COMUNE = "comune";
	public static final java.lang.String SLOT_CONTATTI_STUDIO = "contatti_studio";
	public static final java.lang.String SLOT_DATI_CONTABILI = "dati_contabili";
	public static final java.lang.String SLOT_DATI_CRM = "dati_crm";
	public static final java.lang.String SLOT_DATI_FATTURAZIONE = "dati_fatturazione";
	public static final java.lang.String SLOT_DOCUMENTI_PER_LO_STUDIO = "documenti_per_lo_studio";
	public static final java.lang.String SLOT_EMAIL = "email";
	public static final java.lang.String SLOT_EMAIL2 = "email2";
	public static final java.lang.String SLOT_EMAIL3 = "email3";
	public static final java.lang.String SLOT_FATTURE = "fatture";
	public static final java.lang.String SLOT_FAX = "fax";
	public static final java.lang.String SLOT_INDIRIZZO = "indirizzo";
	public static final java.lang.String SLOT_INVERSE_OF_CLIENTE_DOC = "inverse_of_cliente_doc";
	public static final java.lang.String SLOT_INVERSE_OF_DIVENTA_CLIENTE = "inverse_of_diventa_cliente";
	public static final java.lang.String SLOT_LEGALE_RAPPRESENTANTE_CF = "legale_rappresentante_cf";
	public static final java.lang.String SLOT_LEGALE_RAPPRESENTANTE_COGNOME = "legale_rappresentante_cognome";
	public static final java.lang.String SLOT_LEGALE_RAPPRESENTANTE_NOME = "legale_rappresentante_nome";
	public static final java.lang.String SLOT_LEGALE_RAPPRESENTANTE_RESIDENZA = "legale_rappresentante_residenza";
	public static final java.lang.String SLOT_NICKNAME = "nickname";
	public static final java.lang.String SLOT_NON_PROFIT = "non_profit";
	public static final java.lang.String SLOT_NOTE = "note";
	public static final java.lang.String SLOT_OPERATORE_RESPONSABILE = "operatore_responsabile";
	public static final java.lang.String SLOT_PARTITA_IVA = "partita_iva";
	public static final java.lang.String SLOT_PRATICHE = "pratiche";
	public static final java.lang.String SLOT_PROFORMA = "proforma";
	public static final java.lang.String SLOT_RECAPITO_CAP = "recapito_cap";
	public static final java.lang.String SLOT_RECAPITO_COMUNE = "recapito_comune";
	public static final java.lang.String SLOT_RECAPITO_INDIRIZZO = "recapito_indirizzo";
	public static final java.lang.String SLOT_SCADENZE = "scadenze";
	public static final java.lang.String SLOT_SEZ_CONTATTI = "sez_contatti";
	public static final java.lang.String SLOT_SEZ_LEGALE_RAPPRESENTANTE = "sez_legale_rappresentante";
	public static final java.lang.String SLOT_SEZ_RECAPITO = "sez_recapito";
	public static final java.lang.String SLOT_SEZ_SEDE_LEGALE = "sez_sede_legale";
	public static final java.lang.String SLOT_SMS_INVIATI = "sms_inviati";
	public static final java.lang.String SLOT_STATO_CLIENTE = "stato_cliente";
	public static final java.lang.String SLOT_TELEFONO = "telefono";
	public static final java.lang.String SLOT_TIPO_CLIENTE = "tipo_cliente";
	public static final java.lang.String SLOT_TIPO_SOLLECITO = "tipo_sollecito";

	protected java.lang.String allegato;
	protected java.lang.String allegato_1;
	protected java.util.ArrayList<NotificaScadenza> avvisi_scadenze;
	protected java.util.ArrayList<CalendarioFatturazione> calendario_fatturazione;
	protected java.lang.String cap;
	protected java.lang.String cellulare;
	protected java.util.Date cessata_assistenza_il;
	protected java.lang.String cliente;
	protected java.util.Date cliente_dal;
	protected java.lang.String codice_cliente;
	protected java.lang.String codice_fiscale;
	protected java.lang.String comune;
	protected java.util.ArrayList<HDTicket> contatti_studio;
	protected java.util.ArrayList<DatiContabili> dati_contabili;
	protected DatiCRM dati_crm;
	protected DatiFatturazione dati_fatturazione;
	protected java.util.ArrayList<DocumentoCliente> documenti_per_lo_studio;
	protected java.lang.String email;
	protected java.lang.String email2;
	protected java.lang.String email3;
	protected java.util.ArrayList<Fattura> fatture;
	protected java.lang.String fax;
	protected java.lang.String indirizzo;
	protected java.util.ArrayList<StoriaDocumento> inverse_of_cliente_doc;
	protected ClienteCandidato inverse_of_diventa_cliente;
	protected java.lang.String legale_rappresentante_cf;
	protected java.lang.String legale_rappresentante_cognome;
	protected java.lang.String legale_rappresentante_nome;
	protected java.lang.String legale_rappresentante_residenza;
	protected java.lang.String nickname;
	protected java.lang.String non_profit;
	protected java.lang.String note;
	protected Operatore operatore_responsabile;
	protected java.lang.String partita_iva;
	protected java.util.ArrayList<Pratica> pratiche;
	protected java.util.ArrayList<ProForma> proforma;
	protected java.lang.String recapito_cap;
	protected java.lang.String recapito_comune;
	protected java.lang.String recapito_indirizzo;
	protected java.util.ArrayList<Scadenza> scadenze;
	protected java.lang.String sez_contatti;
	protected java.lang.String sez_legale_rappresentante;
	protected java.lang.String sez_recapito;
	protected java.lang.String sez_sede_legale;
	protected java.util.ArrayList<Sms> sms_inviati;
	protected java.lang.String stato_cliente;
	protected java.lang.String telefono;
	protected java.lang.String tipo_cliente;
	protected java.lang.String tipo_sollecito;
	protected String id;

	public Cliente(){}

	public java.lang.String getAllegato() {
		return allegato;
	}

	public void setAllegato(java.lang.String allegato) {
		this.allegato = allegato;
	}

	public java.lang.String getAllegato_1() {
		return allegato_1;
	}

	public void setAllegato_1(java.lang.String allegato_1) {
		this.allegato_1 = allegato_1;
	}

	public java.util.ArrayList<NotificaScadenza> getAvvisi_scadenze() {
		return avvisi_scadenze;
	}

	public void setAvvisi_scadenze(java.util.ArrayList<NotificaScadenza> avvisi_scadenze) {
		this.avvisi_scadenze = avvisi_scadenze;
	}

	public java.util.ArrayList<CalendarioFatturazione> getCalendario_fatturazione() {
		return calendario_fatturazione;
	}

	public void setCalendario_fatturazione(java.util.ArrayList<CalendarioFatturazione> calendario_fatturazione) {
		this.calendario_fatturazione = calendario_fatturazione;
	}

	public java.lang.String getCap() {
		return cap;
	}

	public void setCap(java.lang.String cap) {
		this.cap = cap;
	}

	public java.lang.String getCellulare() {
		return cellulare;
	}

	public void setCellulare(java.lang.String cellulare) {
		this.cellulare = cellulare;
	}

	public java.util.Date getCessata_assistenza_il() {
		return cessata_assistenza_il;
	}

	public void setCessata_assistenza_il(java.util.Date cessata_assistenza_il) {
		this.cessata_assistenza_il = cessata_assistenza_il;
	}

	public java.lang.String getCliente() {
		return cliente;
	}

	public void setCliente(java.lang.String cliente) {
		this.cliente = cliente;
	}

	public java.util.Date getCliente_dal() {
		return cliente_dal;
	}

	public void setCliente_dal(java.util.Date cliente_dal) {
		this.cliente_dal = cliente_dal;
	}

	public java.lang.String getCodice_cliente() {
		return codice_cliente;
	}

	public void setCodice_cliente(java.lang.String codice_cliente) {
		this.codice_cliente = codice_cliente;
	}

	public java.lang.String getCodice_fiscale() {
		return codice_fiscale;
	}

	public void setCodice_fiscale(java.lang.String codice_fiscale) {
		this.codice_fiscale = codice_fiscale;
	}

	public java.lang.String getComune() {
		return comune;
	}

	public void setComune(java.lang.String comune) {
		this.comune = comune;
	}

	public java.util.ArrayList<HDTicket> getContatti_studio() {
		return contatti_studio;
	}

	public void setContatti_studio(java.util.ArrayList<HDTicket> contatti_studio) {
		this.contatti_studio = contatti_studio;
	}

	public java.util.ArrayList<DatiContabili> getDati_contabili() {
		return dati_contabili;
	}

	public void setDati_contabili(java.util.ArrayList<DatiContabili> dati_contabili) {
		this.dati_contabili = dati_contabili;
	}

	public DatiCRM getDati_crm() {
		return dati_crm;
	}

	public void setDati_crm(DatiCRM dati_crm) {
		this.dati_crm = dati_crm;
	}

	public DatiFatturazione getDati_fatturazione() {
		return dati_fatturazione;
	}

	public void setDati_fatturazione(DatiFatturazione dati_fatturazione) {
		this.dati_fatturazione = dati_fatturazione;
	}

	public java.util.ArrayList<DocumentoCliente> getDocumenti_per_lo_studio() {
		return documenti_per_lo_studio;
	}

	public void setDocumenti_per_lo_studio(java.util.ArrayList<DocumentoCliente> documenti_per_lo_studio) {
		this.documenti_per_lo_studio = documenti_per_lo_studio;
	}

	public java.lang.String getEmail() {
		return email;
	}

	public void setEmail(java.lang.String email) {
		this.email = email;
	}

	public java.lang.String getEmail2() {
		return email2;
	}

	public void setEmail2(java.lang.String email2) {
		this.email2 = email2;
	}

	public java.lang.String getEmail3() {
		return email3;
	}

	public void setEmail3(java.lang.String email3) {
		this.email3 = email3;
	}

	public java.util.ArrayList<Fattura> getFatture() {
		return fatture;
	}

	public void setFatture(java.util.ArrayList<Fattura> fatture) {
		this.fatture = fatture;
	}

	public java.lang.String getFax() {
		return fax;
	}

	public void setFax(java.lang.String fax) {
		this.fax = fax;
	}

	public java.lang.String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(java.lang.String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public java.util.ArrayList<StoriaDocumento> getInverse_of_cliente_doc() {
		return inverse_of_cliente_doc;
	}

	public void setInverse_of_cliente_doc(java.util.ArrayList<StoriaDocumento> inverse_of_cliente_doc) {
		this.inverse_of_cliente_doc = inverse_of_cliente_doc;
	}

	public ClienteCandidato getInverse_of_diventa_cliente() {
		return inverse_of_diventa_cliente;
	}

	public void setInverse_of_diventa_cliente(ClienteCandidato inverse_of_diventa_cliente) {
		this.inverse_of_diventa_cliente = inverse_of_diventa_cliente;
	}

	public java.lang.String getLegale_rappresentante_cf() {
		return legale_rappresentante_cf;
	}

	public void setLegale_rappresentante_cf(java.lang.String legale_rappresentante_cf) {
		this.legale_rappresentante_cf = legale_rappresentante_cf;
	}

	public java.lang.String getLegale_rappresentante_cognome() {
		return legale_rappresentante_cognome;
	}

	public void setLegale_rappresentante_cognome(java.lang.String legale_rappresentante_cognome) {
		this.legale_rappresentante_cognome = legale_rappresentante_cognome;
	}

	public java.lang.String getLegale_rappresentante_nome() {
		return legale_rappresentante_nome;
	}

	public void setLegale_rappresentante_nome(java.lang.String legale_rappresentante_nome) {
		this.legale_rappresentante_nome = legale_rappresentante_nome;
	}

	public java.lang.String getLegale_rappresentante_residenza() {
		return legale_rappresentante_residenza;
	}

	public void setLegale_rappresentante_residenza(java.lang.String legale_rappresentante_residenza) {
		this.legale_rappresentante_residenza = legale_rappresentante_residenza;
	}

	public java.lang.String getNickname() {
		return nickname;
	}

	public void setNickname(java.lang.String nickname) {
		this.nickname = nickname;
	}

	public java.lang.String getNon_profit() {
		return non_profit;
	}

	public void setNon_profit(java.lang.String non_profit) {
		this.non_profit = non_profit;
	}

	public java.lang.String getNote() {
		return note;
	}

	public void setNote(java.lang.String note) {
		this.note = note;
	}

	public Operatore getOperatore_responsabile() {
		return operatore_responsabile;
	}

	public void setOperatore_responsabile(Operatore operatore_responsabile) {
		this.operatore_responsabile = operatore_responsabile;
	}

	public java.lang.String getPartita_iva() {
		return partita_iva;
	}

	public void setPartita_iva(java.lang.String partita_iva) {
		this.partita_iva = partita_iva;
	}

	public java.util.ArrayList<Pratica> getPratiche() {
		return pratiche;
	}

	public void setPratiche(java.util.ArrayList<Pratica> pratiche) {
		this.pratiche = pratiche;
	}

	public java.util.ArrayList<ProForma> getProforma() {
		return proforma;
	}

	public void setProforma(java.util.ArrayList<ProForma> proforma) {
		this.proforma = proforma;
	}

	public java.lang.String getRecapito_cap() {
		return recapito_cap;
	}

	public void setRecapito_cap(java.lang.String recapito_cap) {
		this.recapito_cap = recapito_cap;
	}

	public java.lang.String getRecapito_comune() {
		return recapito_comune;
	}

	public void setRecapito_comune(java.lang.String recapito_comune) {
		this.recapito_comune = recapito_comune;
	}

	public java.lang.String getRecapito_indirizzo() {
		return recapito_indirizzo;
	}

	public void setRecapito_indirizzo(java.lang.String recapito_indirizzo) {
		this.recapito_indirizzo = recapito_indirizzo;
	}

	public java.util.ArrayList<Scadenza> getScadenze() {
		return scadenze;
	}

	public void setScadenze(java.util.ArrayList<Scadenza> scadenze) {
		this.scadenze = scadenze;
	}

	public java.lang.String getSez_contatti() {
		return sez_contatti;
	}

	public void setSez_contatti(java.lang.String sez_contatti) {
		this.sez_contatti = sez_contatti;
	}

	public java.lang.String getSez_legale_rappresentante() {
		return sez_legale_rappresentante;
	}

	public void setSez_legale_rappresentante(java.lang.String sez_legale_rappresentante) {
		this.sez_legale_rappresentante = sez_legale_rappresentante;
	}

	public java.lang.String getSez_recapito() {
		return sez_recapito;
	}

	public void setSez_recapito(java.lang.String sez_recapito) {
		this.sez_recapito = sez_recapito;
	}

	public java.lang.String getSez_sede_legale() {
		return sez_sede_legale;
	}

	public void setSez_sede_legale(java.lang.String sez_sede_legale) {
		this.sez_sede_legale = sez_sede_legale;
	}

	public java.util.ArrayList<Sms> getSms_inviati() {
		return sms_inviati;
	}

	public void setSms_inviati(java.util.ArrayList<Sms> sms_inviati) {
		this.sms_inviati = sms_inviati;
	}

	public java.lang.String getStato_cliente() {
		return stato_cliente;
	}

	public void setStato_cliente(java.lang.String stato_cliente) {
		this.stato_cliente = stato_cliente;
	}

	public java.lang.String getTelefono() {
		return telefono;
	}

	public void setTelefono(java.lang.String telefono) {
		this.telefono = telefono;
	}

	public java.lang.String getTipo_cliente() {
		return tipo_cliente;
	}

	public void setTipo_cliente(java.lang.String tipo_cliente) {
		this.tipo_cliente = tipo_cliente;
	}

	public java.lang.String getTipo_sollecito() {
		return tipo_sollecito;
	}

	public void setTipo_sollecito(java.lang.String tipo_sollecito) {
		this.tipo_sollecito = tipo_sollecito;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public java.lang.String toString() {
		return "Cliente \n allegato: "+allegato+" allegato_1: "+allegato_1+" cap: "+cap+" cellulare: "+cellulare+" cessata_assistenza_il: "+cessata_assistenza_il+" cliente: "+cliente+" cliente_dal: "+cliente_dal+" codice_cliente: "+codice_cliente+" codice_fiscale: "+codice_fiscale+" comune: "+comune+" email: "+email+" email2: "+email2+" email3: "+email3+" fax: "+fax+" indirizzo: "+indirizzo+" legale_rappresentante_cf: "+legale_rappresentante_cf+" legale_rappresentante_cognome: "+legale_rappresentante_cognome+" legale_rappresentante_nome: "+legale_rappresentante_nome+" legale_rappresentante_residenza: "+legale_rappresentante_residenza+" nickname: "+nickname+" non_profit: "+non_profit+" note: "+note+" partita_iva: "+partita_iva+" recapito_cap: "+recapito_cap+" recapito_comune: "+recapito_comune+" recapito_indirizzo: "+recapito_indirizzo+" sez_contatti: "+sez_contatti+" sez_legale_rappresentante: "+sez_legale_rappresentante+" sez_recapito: "+sez_recapito+" sez_sede_legale: "+sez_sede_legale+" stato_cliente: "+stato_cliente+" telefono: "+telefono+" tipo_cliente: "+tipo_cliente+" tipo_sollecito: "+tipo_sollecito;
	}

}