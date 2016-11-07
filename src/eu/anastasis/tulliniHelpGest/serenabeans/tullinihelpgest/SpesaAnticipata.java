package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

import eu.anastasis.serena.beans.SerenaMarshallable;

public class SpesaAnticipata implements SerenaMarshallable
{


	public static final java.lang.String INSTANCE_NAME = "SpesaAnticipata";
	public static final java.lang.String SLOT_DATA = "data";
	public static final java.lang.String SLOT_FATTURATA = "fatturata";
	public static final java.lang.String SLOT_IMPORTO = "importo";
	public static final java.lang.String SLOT_INVERSE_OF_SPESE_ANTICIPATE = "inverse_of_spese_anticipate";
	public static final java.lang.String SLOT_NOTE = "note";
	public static final java.lang.String SLOT_OGGETTO = "oggetto";

	protected java.util.Date data;
	protected java.lang.Boolean fatturata;
	protected java.lang.Float importo;
	protected Attivita inverse_of_spese_anticipate;
	protected java.lang.String note;
	protected java.lang.String oggetto;
	protected String id;

	public SpesaAnticipata(){}

	public java.util.Date getData() {
		return data;
	}

	public void setData(java.util.Date data) {
		this.data = data;
	}

	public java.lang.Boolean getFatturata() {
		return fatturata;
	}

	public void setFatturata(java.lang.Boolean fatturata) {
		this.fatturata = fatturata;
	}

	public java.lang.Float getImporto() {
		return importo;
	}

	public void setImporto(java.lang.Float importo) {
		this.importo = importo;
	}

	public Attivita getInverse_of_spese_anticipate() {
		return inverse_of_spese_anticipate;
	}

	public void setInverse_of_spese_anticipate(Attivita inverse_of_spese_anticipate) {
		this.inverse_of_spese_anticipate = inverse_of_spese_anticipate;
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
		return "SpesaAnticipata \n data: "+data+" fatturata: "+fatturata+" importo: "+importo+" note: "+note+" oggetto: "+oggetto;
	}

}