package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

import eu.anastasis.serena.beans.SerenaMarshallable;

public class HDThreadStep implements SerenaMarshallable
{

	public static final java.lang.String CANALE__EMAIL = "email";
	public static final java.lang.String CANALE__TELEFONO = "telefono";
	public static final java.lang.String CANALE__FAX = "fax";
	public static final java.lang.String CANALE__SMS = "sms";

	public static final java.lang.String TH_DIREZIONE__CLIENTE_STUDIO = "cliente studio";
	public static final java.lang.String TH_DIREZIONE__STUDIO_CLIENTE = "studio cliente";
	public static final java.lang.String TH_DIREZIONE__SPEDITO_STUDIO_CLIENTE = "spedito studio cliente";

	public static final java.lang.String INSTANCE_NAME = "HDThreadStep";
	public static final java.lang.String SLOT_ALLEGATO = "allegato";
	public static final java.lang.String SLOT_ALLEGATO_1 = "allegato_1";
	public static final java.lang.String SLOT_CANALE = "canale";
	public static final java.lang.String SLOT_CHIAMATA_TELEFONICA = "chiamata_telefonica";
	public static final java.lang.String SLOT_CONTABILIZZAZIONE = "contabilizzazione";
	public static final java.lang.String SLOT_INVERSE_OF_THREAD = "inverse_of_thread";
	public static final java.lang.String SLOT_NOTE = "note";
	public static final java.lang.String SLOT_TH_DATA = "th_data";
	public static final java.lang.String SLOT_TH_DIREZIONE = "th_direzione";
	public static final java.lang.String SLOT_TH_MESSAGGIO = "th_messaggio";

	protected java.lang.String allegato;
	protected java.lang.String allegato_1;
	protected java.lang.String canale;
	protected HDChiamataTelefonica chiamata_telefonica;
	protected LavoroSuAttivita contabilizzazione;
	protected HDTicket inverse_of_thread;
	protected java.lang.String note;
	protected java.util.Date th_data;
	protected java.lang.String th_direzione;
	protected java.lang.String th_messaggio;
	protected String id;

	public HDThreadStep(){}

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

	public java.lang.String getCanale() {
		return canale;
	}

	public void setCanale(java.lang.String canale) {
		this.canale = canale;
	}

	public HDChiamataTelefonica getChiamata_telefonica() {
		return chiamata_telefonica;
	}

	public void setChiamata_telefonica(HDChiamataTelefonica chiamata_telefonica) {
		this.chiamata_telefonica = chiamata_telefonica;
	}

	public LavoroSuAttivita getContabilizzazione() {
		return contabilizzazione;
	}

	public void setContabilizzazione(LavoroSuAttivita contabilizzazione) {
		this.contabilizzazione = contabilizzazione;
	}

	public HDTicket getInverse_of_thread() {
		return inverse_of_thread;
	}

	public void setInverse_of_thread(HDTicket inverse_of_thread) {
		this.inverse_of_thread = inverse_of_thread;
	}

	public java.lang.String getNote() {
		return note;
	}

	public void setNote(java.lang.String note) {
		this.note = note;
	}

	public java.util.Date getTh_data() {
		return th_data;
	}

	public void setTh_data(java.util.Date th_data) {
		this.th_data = th_data;
	}

	public java.lang.String getTh_direzione() {
		return th_direzione;
	}

	public void setTh_direzione(java.lang.String th_direzione) {
		this.th_direzione = th_direzione;
	}

	public java.lang.String getTh_messaggio() {
		return th_messaggio;
	}

	public void setTh_messaggio(java.lang.String th_messaggio) {
		this.th_messaggio = th_messaggio;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public java.lang.String toString() {
		return "HDThreadStep \n allegato: "+allegato+" allegato_1: "+allegato_1+" canale: "+canale+" note: "+note+" th_data: "+th_data+" th_direzione: "+th_direzione+" th_messaggio: "+th_messaggio;
	}

}