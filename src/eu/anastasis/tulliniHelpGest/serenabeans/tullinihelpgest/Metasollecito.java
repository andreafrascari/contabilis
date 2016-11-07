package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

import eu.anastasis.serena.beans.SerenaMarshallable;

public class Metasollecito implements SerenaMarshallable
{

	public static final java.lang.String NUMERO_SOLLECITO__PRIMO = "primo";
	public static final java.lang.String NUMERO_SOLLECITO__SECONDO = "secondo";
	public static final java.lang.String NUMERO_SOLLECITO__TERZO = "terzo";
	public static final java.lang.String NUMERO_SOLLECITO__NON_SOLLECITARE = "non sollecitare";


	public static final java.lang.String INSTANCE_NAME = "Metasollecito";
	public static final java.lang.String SLOT_NOTE = "note";
	public static final java.lang.String SLOT_NUMERO_SOLLECITO = "numero_sollecito";
	public static final java.lang.String SLOT_OGGETTO = "oggetto";
	public static final java.lang.String SLOT_TESTO = "testo";

	protected java.lang.String note;
	protected java.lang.String numero_sollecito;
	protected java.lang.String oggetto;
	protected java.lang.String testo;
	protected String id;

	public Metasollecito(){}

	public java.lang.String getNote() {
		return note;
	}

	public void setNote(java.lang.String note) {
		this.note = note;
	}

	public java.lang.String getNumero_sollecito() {
		return numero_sollecito;
	}

	public void setNumero_sollecito(java.lang.String numero_sollecito) {
		this.numero_sollecito = numero_sollecito;
	}

	public java.lang.String getOggetto() {
		return oggetto;
	}

	public void setOggetto(java.lang.String oggetto) {
		this.oggetto = oggetto;
	}

	public java.lang.String getTesto() {
		return testo;
	}

	public void setTesto(java.lang.String testo) {
		this.testo = testo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public java.lang.String toString() {
		return "Metasollecito \n note: "+note+" numero_sollecito: "+numero_sollecito+" oggetto: "+oggetto+" testo: "+testo;
	}

}