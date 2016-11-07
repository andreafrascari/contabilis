package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

import eu.anastasis.serena.beans.SerenaMarshallable;

public class Scadenza implements SerenaMarshallable
{

	public static final java.lang.String DESTINARARIO_PERSONALIZZATO__TIPO_CLINTE_PF = "tipo clinte PF";
	public static final java.lang.String DESTINARARIO_PERSONALIZZATO__CONTABILITA_NOI = "contabilita noi";

	public static final java.lang.String RICORRENZA__GIORNO = "giorno";
	public static final java.lang.String RICORRENZA__SETTIMANA = "settimana";
	public static final java.lang.String RICORRENZA__MESE = "mese";
	public static final java.lang.String RICORRENZA__ANNO = "anno";


	public static final java.lang.String INSTANCE_NAME = "Scadenza";
	public static final java.lang.String SLOT_DATA = "data";
	public static final java.lang.String SLOT_DESCRIZIONE_SCADENZA = "descrizione_scadenza";
	public static final java.lang.String SLOT_DESTINARARIO_PERSONALIZZATO = "destinarario_personalizzato";
	public static final java.lang.String SLOT_FINE_RICORRENZA = "fine_ricorrenza";
	public static final java.lang.String SLOT_FORZA_SMS = "forza_sms";
	public static final java.lang.String SLOT_INVERSE_OF_ATTIVITA_SCADENZA = "inverse_of_attivita_scadenza";
	public static final java.lang.String SLOT_INVERSE_OF_OP_SCADENZE = "inverse_of_op_scadenze";
	public static final java.lang.String SLOT_INVERSE_OF_PRATICA_SCADENZA = "inverse_of_pratica_scadenza";
	public static final java.lang.String SLOT_INVERSE_OF_SCADENZE = "inverse_of_scadenze";
	public static final java.lang.String SLOT_NOTIFICHE_GENERATE = "notifiche_generate";
	public static final java.lang.String SLOT_OGGETTO_SCADENZA = "oggetto_scadenza";
	public static final java.lang.String SLOT_PREAVVISO_GG = "preavviso_gg";
	public static final java.lang.String SLOT_PROFILI_CLIENTI = "profili_clienti";
	public static final java.lang.String SLOT_RICORRENZA = "ricorrenza";

	protected java.util.Date data;
	protected java.lang.String descrizione_scadenza;
	protected java.lang.String destinarario_personalizzato;
	protected java.util.Date fine_ricorrenza;
	protected java.lang.Boolean forza_sms;
	protected Attivita inverse_of_attivita_scadenza;
	protected Operatore inverse_of_op_scadenze;
	protected Pratica inverse_of_pratica_scadenza;
	protected Cliente inverse_of_scadenze;
	protected java.util.ArrayList<NotificaScadenza> notifiche_generate;
	protected java.lang.String oggetto_scadenza;
	protected java.lang.Integer preavviso_gg;
	protected java.util.ArrayList<ProfiloDinamicoClienti> profili_clienti;
	protected java.lang.String ricorrenza;
	protected String id;

	public Scadenza(){}

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

	public java.lang.String getDestinarario_personalizzato() {
		return destinarario_personalizzato;
	}

	public void setDestinarario_personalizzato(java.lang.String destinarario_personalizzato) {
		this.destinarario_personalizzato = destinarario_personalizzato;
	}

	public java.util.Date getFine_ricorrenza() {
		return fine_ricorrenza;
	}

	public void setFine_ricorrenza(java.util.Date fine_ricorrenza) {
		this.fine_ricorrenza = fine_ricorrenza;
	}

	public java.lang.Boolean getForza_sms() {
		return forza_sms;
	}

	public void setForza_sms(java.lang.Boolean forza_sms) {
		this.forza_sms = forza_sms;
	}

	public Attivita getInverse_of_attivita_scadenza() {
		return inverse_of_attivita_scadenza;
	}

	public void setInverse_of_attivita_scadenza(Attivita inverse_of_attivita_scadenza) {
		this.inverse_of_attivita_scadenza = inverse_of_attivita_scadenza;
	}

	public Operatore getInverse_of_op_scadenze() {
		return inverse_of_op_scadenze;
	}

	public void setInverse_of_op_scadenze(Operatore inverse_of_op_scadenze) {
		this.inverse_of_op_scadenze = inverse_of_op_scadenze;
	}

	public Pratica getInverse_of_pratica_scadenza() {
		return inverse_of_pratica_scadenza;
	}

	public void setInverse_of_pratica_scadenza(Pratica inverse_of_pratica_scadenza) {
		this.inverse_of_pratica_scadenza = inverse_of_pratica_scadenza;
	}

	public Cliente getInverse_of_scadenze() {
		return inverse_of_scadenze;
	}

	public void setInverse_of_scadenze(Cliente inverse_of_scadenze) {
		this.inverse_of_scadenze = inverse_of_scadenze;
	}

	public java.util.ArrayList<NotificaScadenza> getNotifiche_generate() {
		return notifiche_generate;
	}

	public void setNotifiche_generate(java.util.ArrayList<NotificaScadenza> notifiche_generate) {
		this.notifiche_generate = notifiche_generate;
	}

	public java.lang.String getOggetto_scadenza() {
		return oggetto_scadenza;
	}

	public void setOggetto_scadenza(java.lang.String oggetto_scadenza) {
		this.oggetto_scadenza = oggetto_scadenza;
	}

	public java.lang.Integer getPreavviso_gg() {
		return preavviso_gg;
	}

	public void setPreavviso_gg(java.lang.Integer preavviso_gg) {
		this.preavviso_gg = preavviso_gg;
	}

	public java.util.ArrayList<ProfiloDinamicoClienti> getProfili_clienti() {
		return profili_clienti;
	}

	public void setProfili_clienti(java.util.ArrayList<ProfiloDinamicoClienti> profili_clienti) {
		this.profili_clienti = profili_clienti;
	}

	public java.lang.String getRicorrenza() {
		return ricorrenza;
	}

	public void setRicorrenza(java.lang.String ricorrenza) {
		this.ricorrenza = ricorrenza;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public java.lang.String toString() {
		return "Scadenza \n data: "+data+" descrizione_scadenza: "+descrizione_scadenza+" destinarario_personalizzato: "+destinarario_personalizzato+" fine_ricorrenza: "+fine_ricorrenza+" forza_sms: "+forza_sms+" oggetto_scadenza: "+oggetto_scadenza+" preavviso_gg: "+preavviso_gg+" ricorrenza: "+ricorrenza;
	}

}