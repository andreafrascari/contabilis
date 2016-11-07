package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

import eu.anastasis.serena.beans.SerenaMarshallable;

public class Pratica implements SerenaMarshallable
{

	public static final java.lang.String ANNO_CONTABILE__2010 = "2010";
	public static final java.lang.String ANNO_CONTABILE__2011 = "2011";
	public static final java.lang.String ANNO_CONTABILE__2012 = "2012";
	public static final java.lang.String ANNO_CONTABILE__2013 = "2013";
	public static final java.lang.String ANNO_CONTABILE__2014 = "2014";
	public static final java.lang.String ANNO_CONTABILE__2015 = "2015";
	public static final java.lang.String ANNO_CONTABILE__2016 = "2016";
	public static final java.lang.String ANNO_CONTABILE__2017 = "2017";
	public static final java.lang.String ANNO_CONTABILE__2018 = "2018";
	public static final java.lang.String ANNO_CONTABILE__2019 = "2019";
	public static final java.lang.String ANNO_CONTABILE__2020 = "2020";

	public static final java.lang.String STATO__APERTA = "Aperta";
	public static final java.lang.String STATO__CHIUSA = "Chiusa";
	public static final java.lang.String STATO__SOSPESA = "Sospesa";
	public static final java.lang.String STATO__FATTURATA = "Fatturata";

	public static final java.lang.String TIPO__A_FORFAIT = "a forfait";
	public static final java.lang.String TIPO__A_ORA = "a ora";
	public static final java.lang.String TIPO__A_PRESTAZIONE = "a prestazione";


	public static final java.lang.String INSTANCE_NAME = "Pratica";
	public static final java.lang.String SLOT_ANNO_CONTABILE = "anno_contabile";
	public static final java.lang.String SLOT_ATTIVITA = "attivita";
	public static final java.lang.String SLOT_CLIENTE_PRATICA = "cliente_pratica";
	public static final java.lang.String SLOT_CODICE = "codice";
	public static final java.lang.String SLOT_DATA_CHIUSURA = "data_chiusura";
	public static final java.lang.String SLOT_METAPRATICA = "metapratica";
	public static final java.lang.String SLOT_NOTE = "note";
	public static final java.lang.String SLOT_PRATICA_SCADENZA = "pratica_scadenza";
	public static final java.lang.String SLOT_PREZZO = "prezzo";
	public static final java.lang.String SLOT_RESPONSABILE = "responsabile";
	public static final java.lang.String SLOT_STATO = "stato";
	public static final java.lang.String SLOT_TICKETASSOCIATI = "ticketAssociati";
	public static final java.lang.String SLOT_TIPO = "tipo";
	public static final java.lang.String SLOT_TITOLO = "titolo";
	public static final java.lang.String SLOT_TITOLO_PER_FATTURA = "titolo_per_fattura";

	protected java.lang.String anno_contabile;
	protected java.util.ArrayList<Attivita> attivita;
	protected Cliente cliente_pratica;
	protected java.lang.String codice;
	protected java.util.Date data_chiusura;
	protected Metapratica metapratica;
	protected java.lang.String note;
	protected Scadenza pratica_scadenza;
	protected java.lang.Float prezzo;
	protected Operatore responsabile;
	protected java.lang.String stato;
	protected java.util.ArrayList<HDTicket> ticketAssociati;
	protected java.lang.String tipo;
	protected java.lang.String titolo;
	protected java.lang.String titolo_per_fattura;
	protected String id;

	public Pratica(){}

	public java.lang.String getAnno_contabile() {
		return anno_contabile;
	}

	public void setAnno_contabile(java.lang.String anno_contabile) {
		this.anno_contabile = anno_contabile;
	}

	public java.util.ArrayList<Attivita> getAttivita() {
		return attivita;
	}

	public void setAttivita(java.util.ArrayList<Attivita> attivita) {
		this.attivita = attivita;
	}

	public Cliente getCliente_pratica() {
		return cliente_pratica;
	}

	public void setCliente_pratica(Cliente cliente_pratica) {
		this.cliente_pratica = cliente_pratica;
	}

	public java.lang.String getCodice() {
		return codice;
	}

	public void setCodice(java.lang.String codice) {
		this.codice = codice;
	}

	public java.util.Date getData_chiusura() {
		return data_chiusura;
	}

	public void setData_chiusura(java.util.Date data_chiusura) {
		this.data_chiusura = data_chiusura;
	}

	public Metapratica getMetapratica() {
		return metapratica;
	}

	public void setMetapratica(Metapratica metapratica) {
		this.metapratica = metapratica;
	}

	public java.lang.String getNote() {
		return note;
	}

	public void setNote(java.lang.String note) {
		this.note = note;
	}

	public Scadenza getPratica_scadenza() {
		return pratica_scadenza;
	}

	public void setPratica_scadenza(Scadenza pratica_scadenza) {
		this.pratica_scadenza = pratica_scadenza;
	}

	public java.lang.Float getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(java.lang.Float prezzo) {
		this.prezzo = prezzo;
	}

	public Operatore getResponsabile() {
		return responsabile;
	}

	public void setResponsabile(Operatore responsabile) {
		this.responsabile = responsabile;
	}

	public java.lang.String getStato() {
		return stato;
	}

	public void setStato(java.lang.String stato) {
		this.stato = stato;
	}

	public java.util.ArrayList<HDTicket> getTicketAssociati() {
		return ticketAssociati;
	}

	public void setTicketAssociati(java.util.ArrayList<HDTicket> ticketAssociati) {
		this.ticketAssociati = ticketAssociati;
	}

	public java.lang.String getTipo() {
		return tipo;
	}

	public void setTipo(java.lang.String tipo) {
		this.tipo = tipo;
	}

	public java.lang.String getTitolo() {
		return titolo;
	}

	public void setTitolo(java.lang.String titolo) {
		this.titolo = titolo;
	}

	public java.lang.String getTitolo_per_fattura() {
		return titolo_per_fattura;
	}

	public void setTitolo_per_fattura(java.lang.String titolo_per_fattura) {
		this.titolo_per_fattura = titolo_per_fattura;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public java.lang.String toString() {
		return "Pratica \n anno_contabile: "+anno_contabile+" codice: "+codice+" data_chiusura: "+data_chiusura+" note: "+note+" prezzo: "+prezzo+" stato: "+stato+" tipo: "+tipo+" titolo: "+titolo+" titolo_per_fattura: "+titolo_per_fattura;
	}

}