package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

import eu.anastasis.serena.beans.SerenaMarshallable;

public class DocumentoCliente implements SerenaMarshallable
{


	public static final java.lang.String INSTANCE_NAME = "DocumentoCliente";
	public static final java.lang.String SLOT_ALLEGATO = "allegato";
	public static final java.lang.String SLOT_ARCHIVIATO = "archiviato";
	public static final java.lang.String SLOT_DA_CLIENTE = "da_cliente";
	public static final java.lang.String SLOT_DATA = "data";
	public static final java.lang.String SLOT_NOTE = "note";
	public static final java.lang.String SLOT_OGGETTO = "oggetto";

	protected java.lang.String allegato;
	protected java.lang.Boolean archiviato;
	protected Cliente da_cliente;
	protected java.util.Date data;
	protected java.lang.String note;
	protected java.lang.String oggetto;
	protected String id;

	public DocumentoCliente(){}

	public java.lang.String getAllegato() {
		return allegato;
	}

	public void setAllegato(java.lang.String allegato) {
		this.allegato = allegato;
	}

	public java.lang.Boolean getArchiviato() {
		return archiviato;
	}

	public void setArchiviato(java.lang.Boolean archiviato) {
		this.archiviato = archiviato;
	}

	public Cliente getDa_cliente() {
		return da_cliente;
	}

	public void setDa_cliente(Cliente da_cliente) {
		this.da_cliente = da_cliente;
	}

	public java.util.Date getData() {
		return data;
	}

	public void setData(java.util.Date data) {
		this.data = data;
	}

	public java.lang.String getNote() {
		return note;
	}

	public void setNote(java.lang.String note) {
		this.note = note;
	}

	public java.lang.String getOggetto() {
		return oggetto;
	}

	public void setOggetto(java.lang.String oggetto) {
		this.oggetto = oggetto;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public java.lang.String toString() {
		return "DocumentoCliente \n allegato: "+allegato+" archiviato: "+archiviato+" data: "+data+" note: "+note+" oggetto: "+oggetto;
	}

}