package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

import eu.anastasis.serena.beans.SerenaMarshallable;

public class StoricoModifiche implements SerenaMarshallable
{


	public static final java.lang.String INSTANCE_NAME = "StoricoModifiche";
	public static final java.lang.String SLOT_CLASSE = "classe";
	public static final java.lang.String SLOT_DATA_MODIFICA = "data_modifica";
	public static final java.lang.String SLOT_HTML_VERSIONE = "html_versione";
	public static final java.lang.String SLOT_ISTANZA = "istanza";
	public static final java.lang.String SLOT_NOTE = "note";

	protected java.lang.String classe;
	protected java.util.Date data_modifica;
	protected java.lang.String html_versione;
	protected java.lang.String istanza;
	protected java.lang.String note;
	protected String id;

	public StoricoModifiche(){}

	public java.lang.String getClasse() {
		return classe;
	}

	public void setClasse(java.lang.String classe) {
		this.classe = classe;
	}

	public java.util.Date getData_modifica() {
		return data_modifica;
	}

	public void setData_modifica(java.util.Date data_modifica) {
		this.data_modifica = data_modifica;
	}

	public java.lang.String getHtml_versione() {
		return html_versione;
	}

	public void setHtml_versione(java.lang.String html_versione) {
		this.html_versione = html_versione;
	}

	public java.lang.String getIstanza() {
		return istanza;
	}

	public void setIstanza(java.lang.String istanza) {
		this.istanza = istanza;
	}

	public java.lang.String getNote() {
		return note;
	}

	public void setNote(java.lang.String note) {
		this.note = note;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public java.lang.String toString() {
		return "StoricoModifiche \n classe: "+classe+" data_modifica: "+data_modifica+" html_versione: "+html_versione+" istanza: "+istanza+" note: "+note;
	}

}