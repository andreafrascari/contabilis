package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

import eu.anastasis.serena.beans.SerenaMarshallable;

public class Attivita implements SerenaMarshallable
{


	public static final java.lang.String INSTANCE_NAME = "Attivita";
	public static final java.lang.String SLOT_ASSEGNATA_A = "assegnata_a";
	public static final java.lang.String SLOT_ATTIVITA_SCADENZA = "attivita_scadenza";
	public static final java.lang.String SLOT_INVERSE_OF_ATTIVITA = "inverse_of_attivita";
	public static final java.lang.String SLOT_MINUTI_ULTIMA_FATTURA = "minuti_ultima_fattura";
	public static final java.lang.String SLOT_NOTE = "note";
	public static final java.lang.String SLOT_SESSIONI_DI_LAVORO = "sessioni_di_lavoro";
	public static final java.lang.String SLOT_SPESE_ANTICIPATE = "spese_anticipate";
	public static final java.lang.String SLOT_STATO_ATTIVITA = "stato_attivita";
	public static final java.lang.String SLOT_TITOLO = "titolo";

	protected Operatore assegnata_a;
	protected Scadenza attivita_scadenza;
	protected Pratica inverse_of_attivita;
	protected java.lang.Integer minuti_ultima_fattura;
	protected java.lang.String note;
	protected java.util.ArrayList<LavoroSuAttivita> sessioni_di_lavoro;
	protected java.util.ArrayList<SpesaAnticipata> spese_anticipate;
	protected java.lang.Integer stato_attivita;
	protected java.lang.String titolo;
	protected String id;

	public Attivita(){}

	public Operatore getAssegnata_a() {
		return assegnata_a;
	}

	public void setAssegnata_a(Operatore assegnata_a) {
		this.assegnata_a = assegnata_a;
	}

	public Scadenza getAttivita_scadenza() {
		return attivita_scadenza;
	}

	public void setAttivita_scadenza(Scadenza attivita_scadenza) {
		this.attivita_scadenza = attivita_scadenza;
	}

	public Pratica getInverse_of_attivita() {
		return inverse_of_attivita;
	}

	public void setInverse_of_attivita(Pratica inverse_of_attivita) {
		this.inverse_of_attivita = inverse_of_attivita;
	}

	public java.lang.Integer getMinuti_ultima_fattura() {
		return minuti_ultima_fattura;
	}

	public void setMinuti_ultima_fattura(java.lang.Integer minuti_ultima_fattura) {
		this.minuti_ultima_fattura = minuti_ultima_fattura;
	}

	public java.lang.String getNote() {
		return note;
	}

	public void setNote(java.lang.String note) {
		this.note = note;
	}

	public java.util.ArrayList<LavoroSuAttivita> getSessioni_di_lavoro() {
		return sessioni_di_lavoro;
	}

	public void setSessioni_di_lavoro(java.util.ArrayList<LavoroSuAttivita> sessioni_di_lavoro) {
		this.sessioni_di_lavoro = sessioni_di_lavoro;
	}

	public java.util.ArrayList<SpesaAnticipata> getSpese_anticipate() {
		return spese_anticipate;
	}

	public void setSpese_anticipate(java.util.ArrayList<SpesaAnticipata> spese_anticipate) {
		this.spese_anticipate = spese_anticipate;
	}

	public java.lang.Integer getStato_attivita() {
		return stato_attivita;
	}

	public void setStato_attivita(java.lang.Integer stato_attivita) {
		this.stato_attivita = stato_attivita;
	}

	public java.lang.String getTitolo() {
		return titolo;
	}

	public void setTitolo(java.lang.String titolo) {
		this.titolo = titolo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public java.lang.String toString() {
		return "Attivita \n minuti_ultima_fattura: "+minuti_ultima_fattura+" note: "+note+" stato_attivita: "+stato_attivita+" titolo: "+titolo;
	}

}