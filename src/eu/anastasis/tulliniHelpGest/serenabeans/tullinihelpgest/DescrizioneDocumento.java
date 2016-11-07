package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

import eu.anastasis.serena.beans.SerenaMarshallable;

public class DescrizioneDocumento implements SerenaMarshallable
{

	public static final java.lang.String AZIONE_CONSEGUENTE_CARICAMENTO__B_EMAIL_CLIENTE = "B email cliente";
	public static final java.lang.String AZIONE_CONSEGUENTE_CARICAMENTO__E_COMUNICAZIONE_INTERNA_SEGRETERIA_STUDIO = "E comunicazione interna segreteria studio";
	public static final java.lang.String AZIONE_CONSEGUENTE_CARICAMENTO__A_NESSUNA = "A nessuna";
	public static final java.lang.String AZIONE_CONSEGUENTE_CARICAMENTO__C_EMAIL_CLIENTE_LINK = "C email cliente link";
	public static final java.lang.String AZIONE_CONSEGUENTE_CARICAMENTO__D_EMAIL_CLIENTE_LINK_RICHIESTO_LOGON = "D email cliente link richiesto logon";
	public static final java.lang.String AZIONE_CONSEGUENTE_CARICAMENTO__F_COMUNICAZIONE_INTERNA_SOLO_NUOVI_DOCUMENTI_NO_REVISIONI = "F comunicazione interna solo nuovi documenti no revisioni";
	public static final java.lang.String AZIONE_CONSEGUENTE_CARICAMENTO__G_EMAIL_MAILING_LIST = "G email mailing list";

	public static final java.lang.String SOLLECITO__SI_SE_CLIENTE_NON_SCARICA = "si se cliente non scarica";
	public static final java.lang.String SOLLECITO__SI_SE_NON_RICEVUTO_MATERIALE = "si se non ricevuto materiale";
	public static final java.lang.String SOLLECITO__NO = "no";

	public static final java.lang.String VISIBILITA__DOCUMENTO_INTERNO_VISIBILE_USER_E_ADMIN = "documento interno visibile user e admin";
	public static final java.lang.String VISIBILITA__DOCUMENTO_INTERNO_ADMIN = "documento interno admin";
	public static final java.lang.String VISIBILITA__DOCUMENTO_CLIENTE_VISIBILE_CLIENTE_USER_E_ADMIN = "documento cliente visibile cliente user e admin";
	public static final java.lang.String VISIBILITA__DOCUMENTO_CLIENTE_VISIBILE_SOLO_USER_E_ADMIN = "documento cliente visibile solo user e admin";


	public static final java.lang.String INSTANCE_NAME = "DescrizioneDocumento";
	public static final java.lang.String SLOT_AZIONE_CONSEGUENTE_CARICAMENTO = "azione_conseguente_caricamento";
	public static final java.lang.String SLOT_CONTENUTO_AZIONE = "contenuto_azione";
	public static final java.lang.String SLOT_INVERSE_OF_TIPOLOGIA = "inverse_of_tipologia";
	public static final java.lang.String SLOT_SOLLECITO = "sollecito";
	public static final java.lang.String SLOT_TEMPLATE_OGGETTO_INVIO = "template_oggetto_invio";
	public static final java.lang.String SLOT_TEMPLATE_SMS = "template_sms";
	public static final java.lang.String SLOT_TEMPLATE_TESTO_INVIO = "template_testo_invio";
	public static final java.lang.String SLOT_TIPOLOGIA_DOCUMENTO = "tipologia_documento";
	public static final java.lang.String SLOT_VISIBILITA = "visibilita";

	protected java.lang.String azione_conseguente_caricamento;
	protected java.lang.String contenuto_azione;
	protected java.util.ArrayList<Documento> inverse_of_tipologia;
	protected java.lang.String sollecito;
	protected java.lang.String template_oggetto_invio;
	protected java.lang.String template_sms;
	protected java.lang.String template_testo_invio;
	protected java.lang.String tipologia_documento;
	protected java.lang.String visibilita;
	protected String id;

	public DescrizioneDocumento(){}

	public java.lang.String getAzione_conseguente_caricamento() {
		return azione_conseguente_caricamento;
	}

	public void setAzione_conseguente_caricamento(java.lang.String azione_conseguente_caricamento) {
		this.azione_conseguente_caricamento = azione_conseguente_caricamento;
	}

	public java.lang.String getContenuto_azione() {
		return contenuto_azione;
	}

	public void setContenuto_azione(java.lang.String contenuto_azione) {
		this.contenuto_azione = contenuto_azione;
	}

	public java.util.ArrayList<Documento> getInverse_of_tipologia() {
		return inverse_of_tipologia;
	}

	public void setInverse_of_tipologia(java.util.ArrayList<Documento> inverse_of_tipologia) {
		this.inverse_of_tipologia = inverse_of_tipologia;
	}

	public java.lang.String getSollecito() {
		return sollecito;
	}

	public void setSollecito(java.lang.String sollecito) {
		this.sollecito = sollecito;
	}

	public java.lang.String getTemplate_oggetto_invio() {
		return template_oggetto_invio;
	}

	public void setTemplate_oggetto_invio(java.lang.String template_oggetto_invio) {
		this.template_oggetto_invio = template_oggetto_invio;
	}

	public java.lang.String getTemplate_sms() {
		return template_sms;
	}

	public void setTemplate_sms(java.lang.String template_sms) {
		this.template_sms = template_sms;
	}

	public java.lang.String getTemplate_testo_invio() {
		return template_testo_invio;
	}

	public void setTemplate_testo_invio(java.lang.String template_testo_invio) {
		this.template_testo_invio = template_testo_invio;
	}

	public java.lang.String getTipologia_documento() {
		return tipologia_documento;
	}

	public void setTipologia_documento(java.lang.String tipologia_documento) {
		this.tipologia_documento = tipologia_documento;
	}

	public java.lang.String getVisibilita() {
		return visibilita;
	}

	public void setVisibilita(java.lang.String visibilita) {
		this.visibilita = visibilita;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public java.lang.String toString() {
		return "DescrizioneDocumento \n azione_conseguente_caricamento: "+azione_conseguente_caricamento+" contenuto_azione: "+contenuto_azione+" sollecito: "+sollecito+" template_oggetto_invio: "+template_oggetto_invio+" template_sms: "+template_sms+" template_testo_invio: "+template_testo_invio+" tipologia_documento: "+tipologia_documento+" visibilita: "+visibilita;
	}

}