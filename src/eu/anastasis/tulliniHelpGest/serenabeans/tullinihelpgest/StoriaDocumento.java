package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

import eu.anastasis.serena.beans.SerenaMarshallable;

public class StoriaDocumento implements SerenaMarshallable
{

	public static final java.lang.String SOLLECITO__SI_SE_CLIENTE_NON_SCARICA = "si se cliente non scarica";
	public static final java.lang.String SOLLECITO__SI_SE_NON_RICEVUTO_MATERIALE = "si se non ricevuto materiale";
	public static final java.lang.String SOLLECITO__NO = "no";


	public static final java.lang.String INSTANCE_NAME = "StoriaDocumento";
	public static final java.lang.String SLOT_CLIENTE_DOC = "cliente_doc";
	public static final java.lang.String SLOT_ERRORE = "errore";
	public static final java.lang.String SLOT_INVERSE_OF_STORIA_DOCUMENTO = "inverse_of_storia_documento";
	public static final java.lang.String SLOT_INVIATO_IL = "inviato_il";
	public static final java.lang.String SLOT_OGGETTO_MAIL = "oggetto_mail";
	public static final java.lang.String SLOT_SCARICATO_IL = "scaricato_il";
	public static final java.lang.String SLOT_SOLLECITO = "sollecito";
	public static final java.lang.String SLOT_SOLLECITO_IL = "sollecito_il";
	public static final java.lang.String SLOT_TESTO_MAIL = "testo_mail";
	public static final java.lang.String SLOT_TESTO_SMS = "testo_sms";
	public static final java.lang.String SLOT_WORKFLOW_COMPLETATO_IL = "workflow_completato_il";

	protected Cliente cliente_doc;
	protected java.lang.String errore;
	protected Documento inverse_of_storia_documento;
	protected java.util.Date inviato_il;
	protected java.lang.String oggetto_mail;
	protected java.util.Date scaricato_il;
	protected java.lang.String sollecito;
	protected java.util.Date sollecito_il;
	protected java.lang.String testo_mail;
	protected java.lang.String testo_sms;
	protected java.util.Date workflow_completato_il;
	protected String id;

	public StoriaDocumento(){}

	public Cliente getCliente_doc() {
		return cliente_doc;
	}

	public void setCliente_doc(Cliente cliente_doc) {
		this.cliente_doc = cliente_doc;
	}

	public java.lang.String getErrore() {
		return errore;
	}

	public void setErrore(java.lang.String errore) {
		this.errore = errore;
	}

	public Documento getInverse_of_storia_documento() {
		return inverse_of_storia_documento;
	}

	public void setInverse_of_storia_documento(Documento inverse_of_storia_documento) {
		this.inverse_of_storia_documento = inverse_of_storia_documento;
	}

	public java.util.Date getInviato_il() {
		return inviato_il;
	}

	public void setInviato_il(java.util.Date inviato_il) {
		this.inviato_il = inviato_il;
	}

	public java.lang.String getOggetto_mail() {
		return oggetto_mail;
	}

	public void setOggetto_mail(java.lang.String oggetto_mail) {
		this.oggetto_mail = oggetto_mail;
	}

	public java.util.Date getScaricato_il() {
		return scaricato_il;
	}

	public void setScaricato_il(java.util.Date scaricato_il) {
		this.scaricato_il = scaricato_il;
	}

	public java.lang.String getSollecito() {
		return sollecito;
	}

	public void setSollecito(java.lang.String sollecito) {
		this.sollecito = sollecito;
	}

	public java.util.Date getSollecito_il() {
		return sollecito_il;
	}

	public void setSollecito_il(java.util.Date sollecito_il) {
		this.sollecito_il = sollecito_il;
	}

	public java.lang.String getTesto_mail() {
		return testo_mail;
	}

	public void setTesto_mail(java.lang.String testo_mail) {
		this.testo_mail = testo_mail;
	}

	public java.lang.String getTesto_sms() {
		return testo_sms;
	}

	public void setTesto_sms(java.lang.String testo_sms) {
		this.testo_sms = testo_sms;
	}

	public java.util.Date getWorkflow_completato_il() {
		return workflow_completato_il;
	}

	public void setWorkflow_completato_il(java.util.Date workflow_completato_il) {
		this.workflow_completato_il = workflow_completato_il;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public java.lang.String toString() {
		return "StoriaDocumento \n errore: "+errore+" inviato_il: "+inviato_il+" oggetto_mail: "+oggetto_mail+" scaricato_il: "+scaricato_il+" sollecito: "+sollecito+" sollecito_il: "+sollecito_il+" testo_mail: "+testo_mail+" testo_sms: "+testo_sms+" workflow_completato_il: "+workflow_completato_il;
	}

}