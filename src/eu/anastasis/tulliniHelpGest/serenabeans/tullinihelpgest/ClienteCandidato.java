package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

import eu.anastasis.serena.beans.SerenaMarshallable;

public class ClienteCandidato implements SerenaMarshallable
{


	public static final java.lang.String INSTANCE_NAME = "ClienteCandidato";
	public static final java.lang.String SLOT_CAP = "cap";
	public static final java.lang.String SLOT_CELLULARE = "cellulare";
	public static final java.lang.String SLOT_CLIENTE = "cliente";
	public static final java.lang.String SLOT_CLIENTE_DAL = "cliente_dal";
	public static final java.lang.String SLOT_CODICE_FISCALE = "codice_fiscale";
	public static final java.lang.String SLOT_COMUNE = "comune";
	public static final java.lang.String SLOT_DIVENTA_CLIENTE = "diventa_cliente";
	public static final java.lang.String SLOT_EMAIL = "email";
	public static final java.lang.String SLOT_FAX = "fax";
	public static final java.lang.String SLOT_INDIRIZZO = "indirizzo";
	public static final java.lang.String SLOT_NOTE = "note";
	public static final java.lang.String SLOT_PARTITA_IVA = "partita_iva";
	public static final java.lang.String SLOT_PREVENTIVO = "preventivo";
	public static final java.lang.String SLOT_PREVENTIVO_LISTINO = "preventivo_listino";

	protected java.lang.String cap;
	protected java.lang.String cellulare;
	protected java.lang.String cliente;
	protected java.util.Date cliente_dal;
	protected java.lang.String codice_fiscale;
	protected java.lang.String comune;
	protected Cliente diventa_cliente;
	protected java.lang.String email;
	protected java.lang.String fax;
	protected java.lang.String indirizzo;
	protected java.lang.String note;
	protected java.lang.String partita_iva;
	protected java.lang.String preventivo;
	protected DatiFatturazione preventivo_listino;
	protected String id;

	public ClienteCandidato(){}

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

	public Cliente getDiventa_cliente() {
		return diventa_cliente;
	}

	public void setDiventa_cliente(Cliente diventa_cliente) {
		this.diventa_cliente = diventa_cliente;
	}

	public java.lang.String getEmail() {
		return email;
	}

	public void setEmail(java.lang.String email) {
		this.email = email;
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

	public java.lang.String getNote() {
		return note;
	}

	public void setNote(java.lang.String note) {
		this.note = note;
	}

	public java.lang.String getPartita_iva() {
		return partita_iva;
	}

	public void setPartita_iva(java.lang.String partita_iva) {
		this.partita_iva = partita_iva;
	}

	public java.lang.String getPreventivo() {
		return preventivo;
	}

	public void setPreventivo(java.lang.String preventivo) {
		this.preventivo = preventivo;
	}

	public DatiFatturazione getPreventivo_listino() {
		return preventivo_listino;
	}

	public void setPreventivo_listino(DatiFatturazione preventivo_listino) {
		this.preventivo_listino = preventivo_listino;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public java.lang.String toString() {
		return "ClienteCandidato \n cap: "+cap+" cellulare: "+cellulare+" cliente: "+cliente+" cliente_dal: "+cliente_dal+" codice_fiscale: "+codice_fiscale+" comune: "+comune+" email: "+email+" fax: "+fax+" indirizzo: "+indirizzo+" note: "+note+" partita_iva: "+partita_iva+" preventivo: "+preventivo;
	}

}