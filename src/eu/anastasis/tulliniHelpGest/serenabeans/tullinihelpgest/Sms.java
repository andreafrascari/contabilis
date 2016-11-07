package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

import eu.anastasis.serena.beans.SerenaMarshallable;

public class Sms implements SerenaMarshallable
{

	public static final java.lang.String QUALITA__ALTA_CON_NOTIFICA = "alta con notifica";
	public static final java.lang.String QUALITA__ALTA = "alta";
	public static final java.lang.String QUALITA__MEDIA = "media";
	public static final java.lang.String QUALITA__BASSA = "bassa";


	public static final java.lang.String INSTANCE_NAME = "Sms";
	public static final java.lang.String SLOT_A_CLIENTE = "a_cliente";
	public static final java.lang.String SLOT_NOTE = "note";
	public static final java.lang.String SLOT_NUMERO_DESTINATARIO = "numero_destinatario";
	public static final java.lang.String SLOT_ORA_RICEZIONE = "ora_ricezione";
	public static final java.lang.String SLOT_ORA_SPEDIZIONE = "ora_spedizione";
	public static final java.lang.String SLOT_QUALITA = "qualita";
	public static final java.lang.String SLOT_TESTO = "testo";

	protected Cliente a_cliente;
	protected java.lang.String note;
	protected java.lang.String numero_destinatario;
	protected java.util.Date ora_ricezione;
	protected java.util.Date ora_spedizione;
	protected java.lang.String qualita;
	protected java.lang.String testo;
	protected String id;

	public Sms(){}

	public Cliente getA_cliente() {
		return a_cliente;
	}

	public void setA_cliente(Cliente a_cliente) {
		this.a_cliente = a_cliente;
	}

	public java.lang.String getNote() {
		return note;
	}

	public void setNote(java.lang.String note) {
		this.note = note;
	}

	public java.lang.String getNumero_destinatario() {
		return numero_destinatario;
	}

	public void setNumero_destinatario(java.lang.String numero_destinatario) {
		this.numero_destinatario = numero_destinatario;
	}

	public java.util.Date getOra_ricezione() {
		return ora_ricezione;
	}

	public void setOra_ricezione(java.util.Date ora_ricezione) {
		this.ora_ricezione = ora_ricezione;
	}

	public java.util.Date getOra_spedizione() {
		return ora_spedizione;
	}

	public void setOra_spedizione(java.util.Date ora_spedizione) {
		this.ora_spedizione = ora_spedizione;
	}

	public java.lang.String getQualita() {
		return qualita;
	}

	public void setQualita(java.lang.String qualita) {
		this.qualita = qualita;
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
		return "Sms \n note: "+note+" numero_destinatario: "+numero_destinatario+" ora_ricezione: "+ora_ricezione+" ora_spedizione: "+ora_spedizione+" qualita: "+qualita+" testo: "+testo;
	}

}