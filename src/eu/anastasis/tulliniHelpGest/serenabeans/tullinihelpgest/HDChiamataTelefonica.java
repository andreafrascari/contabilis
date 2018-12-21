package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

import eu.anastasis.serena.beans.SerenaMarshallable;

public class HDChiamataTelefonica implements SerenaMarshallable
{

	public static final java.lang.String STATO_CHIAMATA__RICEVUTA = "ricevuta";
	public static final java.lang.String STATO_CHIAMATA__INVIATA = "inviata";
	public static final java.lang.String STATO_CHIAMATA__PERSA = "persa";


	public static final java.lang.String INSTANCE_NAME = "HDChiamataTelefonica";
	public static final java.lang.String SLOT_DATA = "data";
	public static final java.lang.String SLOT_DURATA = "durata";
	public static final java.lang.String SLOT_NOTE = "note";
	public static final java.lang.String SLOT_NUMERO_LOCALE = "numero_locale";
	public static final java.lang.String SLOT_NUMERO_REMOTO = "numero_remoto";
	public static final java.lang.String SLOT_STATO_CHIAMATA = "stato_chiamata";
	public static final java.lang.String SLOT_TICKET = "ticket";

	protected java.util.Date data;
	protected java.lang.Integer durata;
	protected java.lang.String note;
	protected java.lang.String numero_locale;
	protected java.lang.String numero_remoto;
	protected java.lang.String stato_chiamata;
	protected HDThreadStep ticket;
	protected String id;

	public HDChiamataTelefonica(){}

	public java.util.Date getData() {
		return data;
	}

	public void setData(java.util.Date data) {
		this.data = data;
	}

	public java.lang.Integer getDurata() {
		return durata;
	}

	public void setDurata(java.lang.Integer durata) {
		this.durata = durata;
	}

	public java.lang.String getNote() {
		return note;
	}

	public void setNote(java.lang.String note) {
		this.note = note;
	}

	public java.lang.String getNumero_locale() {
		return numero_locale;
	}

	public void setNumero_locale(java.lang.String numero_locale) {
		this.numero_locale = numero_locale;
	}

	public java.lang.String getNumero_remoto() {
		return numero_remoto;
	}

	public void setNumero_remoto(java.lang.String numero_remoto) {
		this.numero_remoto = numero_remoto;
	}

	public java.lang.String getStato_chiamata() {
		return stato_chiamata;
	}

	public void setStato_chiamata(java.lang.String stato_chiamata) {
		this.stato_chiamata = stato_chiamata;
	}

	public HDThreadStep getTicket() {
		return ticket;
	}

	public void setTicket(HDThreadStep ticket) {
		this.ticket = ticket;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public java.lang.String toString() {
		return "HDChiamataTelefonica \n data: "+data+" durata: "+durata+" note: "+note+" numero_locale: "+numero_locale+" numero_remoto: "+numero_remoto+" stato_chiamata: "+stato_chiamata;
	}

}