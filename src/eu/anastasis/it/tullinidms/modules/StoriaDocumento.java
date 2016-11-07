package eu.anastasis.it.tullinidms.modules;

public class StoriaDocumento {
	
	private String ID = null;
	
	private String documento = null;
	private String cliente_doc = null;

	private String sollecito = null;
		
	private String oggetto_mail = null;
	private String testo_sms = null;
	private String testo_mail = null;
	
	private String inviato_il = null;
	private String scaricato_il = null;
	private String sollecito_il = null;
	private String workflow_completato_il = null;
	
	private String errore = null;
	
	public static final String MY_CLASS= "StoriaDocumento";
	
	
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getSollecito() {
		return sollecito;
	}
	public void setSollecito(String sollecito) {
		this.sollecito = sollecito;
	}

	public String getErrore() {
		return errore;
	}
	public void setErrore(String errore) {
		this.errore = errore;
	}
	 
	public String getOggetto_mail() {
		return oggetto_mail;
	}
	public void setOggetto_mail(String oggetto_mail) {
		this.oggetto_mail = oggetto_mail;
	}
	public String getTesto_sms() {
		return testo_sms;
	}
	public void setTesto_sms(String testo_sms) {
		this.testo_sms = testo_sms;
	}
	public String getTesto_mail() {
		return testo_mail;
	}
	public void setTesto_mail(String testo_mail) {
		this.testo_mail = testo_mail;
	}
	
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public String getCliente_doc() {
		return cliente_doc;
	}
	public void setCliente_doc(String cliente_doc) {
		this.cliente_doc = cliente_doc;
	}
	public String getInviato_il() {
		return inviato_il;
	}
	public void setInviato_il(String inviato_il) {
		this.inviato_il = inviato_il;
	}
	public String getScaricato_il() {
		return scaricato_il;
	}
	public void setScaricato_il(String scaricato_il) {
		this.scaricato_il = scaricato_il;
	}
	public String getSollecito_il() {
		return sollecito_il;
	}
	public void setSollecito_il(String sollecito_il) {
		this.sollecito_il = sollecito_il;
	}
	public String getWorkflow_completato_il() {
		return workflow_completato_il;
	}
	public void setWorkflow_completato_il(String workflow_completato_il) {
		this.workflow_completato_il = workflow_completato_il;
	}
	

}
