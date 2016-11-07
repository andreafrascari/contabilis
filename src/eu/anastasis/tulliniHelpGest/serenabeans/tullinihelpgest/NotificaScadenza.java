package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

import eu.anastasis.serena.beans.SerenaMarshallable;

public class NotificaScadenza implements SerenaMarshallable
{



	public static final java.lang.String INSTANCE_NAME = "NotificaScadenza";
	public static final java.lang.String SLOT_DATA = "data";
	public static final java.lang.String SLOT_DESCRIZIONE_SCADENZA = "descrizione_scadenza";
	public static final java.lang.String SLOT_FATTO = "fatto";
	public static final java.lang.String SLOT_INVERSE_OF_AVVISI_SCADENZE_CLIENTE = "inverse_of_avvisi_scadenze_cliente";
	public static final java.lang.String SLOT_INVERSE_OF_NOTIFICHE_GENERATE = "inverse_of_notifiche_generate";
	public static final java.lang.String SLOT_INVERSE_OF_NOTIFICHE_SCADENZE_OPERATORE = "inverse_of_notifiche_scadenze_operatore";
	public static final java.lang.String SLOT_OGGETTO_SCADENZA = "oggetto_scadenza";

	protected java.util.Date data;
	protected java.lang.String descrizione_scadenza;
	protected java.lang.String fatto;
	protected Cliente inverse_of_avvisi_scadenze_cliente;
	protected Scadenza inverse_of_notifiche_generate;
	protected Operatore inverse_of_notifiche_scadenze_operatore;
	protected java.lang.String oggetto_scadenza;
	protected String id;

	public NotificaScadenza(){}

	public java.util.Date getData() {
		return data;
	}

	public void setData(java.util.Date data) {
		this.data = data;
	}

	public java.lang.String getDescrizione_scadenza() {
		return descrizione_scadenza;
	}

	public void setDescrizione_scadenza(java.lang.String descrizione_scadenza) {
		this.descrizione_scadenza = descrizione_scadenza;
	}

	public java.lang.String getFatto() {
		return fatto;
	}

	public void setFatto(java.lang.String fatto) {
		this.fatto = fatto;
	}

	public Cliente getInverse_of_avvisi_scadenze_cliente() {
		return inverse_of_avvisi_scadenze_cliente;
	}

	public void setInverse_of_avvisi_scadenze_cliente(Cliente inverse_of_avvisi_scadenze_cliente) {
		this.inverse_of_avvisi_scadenze_cliente = inverse_of_avvisi_scadenze_cliente;
	}

	public Scadenza getInverse_of_notifiche_generate() {
		return inverse_of_notifiche_generate;
	}

	public void setInverse_of_notifiche_generate(Scadenza inverse_of_notifiche_generate) {
		this.inverse_of_notifiche_generate = inverse_of_notifiche_generate;
	}

	public Operatore getInverse_of_notifiche_scadenze_operatore() {
		return inverse_of_notifiche_scadenze_operatore;
	}

	public void setInverse_of_notifiche_scadenze_operatore(Operatore inverse_of_notifiche_scadenze_operatore) {
		this.inverse_of_notifiche_scadenze_operatore = inverse_of_notifiche_scadenze_operatore;
	}

	public java.lang.String getOggetto_scadenza() {
		return oggetto_scadenza;
	}

	public void setOggetto_scadenza(java.lang.String oggetto_scadenza) {
		this.oggetto_scadenza = oggetto_scadenza;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public java.lang.String toString() {
		return "NotificaScadenza \n data: "+data+" descrizione_scadenza: "+descrizione_scadenza+" fatto: "+fatto+" oggetto_scadenza: "+oggetto_scadenza;
	}

}