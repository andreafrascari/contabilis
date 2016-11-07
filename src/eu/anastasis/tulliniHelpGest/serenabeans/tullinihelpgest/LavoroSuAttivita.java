package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

import eu.anastasis.serena.beans.SerenaMarshallable;

public class LavoroSuAttivita implements SerenaMarshallable
{


	public static final java.lang.String INSTANCE_NAME = "LavoroSuAttivita";
	public static final java.lang.String SLOT_ALLEGATO = "allegato";
	public static final java.lang.String SLOT_DATICKET = "daTicket";
	public static final java.lang.String SLOT_DATA = "data";
	public static final java.lang.String SLOT_DIARIO = "diario";
	public static final java.lang.String SLOT_DURATA_MINUTI = "durata_minuti";
	public static final java.lang.String SLOT_INVERSE_OF_SESSIONI_DI_LAVORO = "inverse_of_sessioni_di_lavoro";

	protected java.lang.String allegato;
	protected HDThreadStep daTicket;
	protected java.util.Date data;
	protected java.lang.String diario;
	protected java.lang.Integer durata_minuti;
	protected Attivita inverse_of_sessioni_di_lavoro;
	protected String id;

	public LavoroSuAttivita(){}

	public java.lang.String getAllegato() {
		return allegato;
	}

	public void setAllegato(java.lang.String allegato) {
		this.allegato = allegato;
	}

	public HDThreadStep getDaTicket() {
		return daTicket;
	}

	public void setDaTicket(HDThreadStep daTicket) {
		this.daTicket = daTicket;
	}

	public java.util.Date getData() {
		return data;
	}

	public void setData(java.util.Date data) {
		this.data = data;
	}

	public java.lang.String getDiario() {
		return diario;
	}

	public void setDiario(java.lang.String diario) {
		this.diario = diario;
	}

	public java.lang.Integer getDurata_minuti() {
		return durata_minuti;
	}

	public void setDurata_minuti(java.lang.Integer durata_minuti) {
		this.durata_minuti = durata_minuti;
	}

	public Attivita getInverse_of_sessioni_di_lavoro() {
		return inverse_of_sessioni_di_lavoro;
	}

	public void setInverse_of_sessioni_di_lavoro(Attivita inverse_of_sessioni_di_lavoro) {
		this.inverse_of_sessioni_di_lavoro = inverse_of_sessioni_di_lavoro;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public java.lang.String toString() {
		return "LavoroSuAttivita \n allegato: "+allegato+" data: "+data+" diario: "+diario+" durata_minuti: "+durata_minuti;
	}

}