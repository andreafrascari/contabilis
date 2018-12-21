package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

import eu.anastasis.serena.beans.SerenaMarshallable;

public class HDRispostaStandard implements SerenaMarshallable
{

	public static final java.lang.String DIAGNOSI_STD__RICHIESTA_PREVENTIVO = "richiesta preventivo";


	public static final java.lang.String INSTANCE_NAME = "HDRispostaStandard";
	public static final java.lang.String SLOT_ALLEGATO = "allegato";
	public static final java.lang.String SLOT_ALLEGATO_1 = "allegato_1";
	public static final java.lang.String SLOT_DIAGNOSI_STD = "diagnosi_std";
	public static final java.lang.String SLOT_NOTE = "note";
	public static final java.lang.String SLOT_RISPOSTA_STD = "risposta_std";

	protected java.lang.String allegato;
	protected java.lang.String allegato_1;
	protected java.lang.String diagnosi_std;
	protected java.lang.String note;
	protected java.lang.String risposta_std;
	protected String id;

	public HDRispostaStandard(){}

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

	public java.lang.String getDiagnosi_std() {
		return diagnosi_std;
	}

	public void setDiagnosi_std(java.lang.String diagnosi_std) {
		this.diagnosi_std = diagnosi_std;
	}

	public java.lang.String getNote() {
		return note;
	}

	public void setNote(java.lang.String note) {
		this.note = note;
	}

	public java.lang.String getRisposta_std() {
		return risposta_std;
	}

	public void setRisposta_std(java.lang.String risposta_std) {
		this.risposta_std = risposta_std;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public java.lang.String toString() {
		return "HDRispostaStandard \n allegato: "+allegato+" allegato_1: "+allegato_1+" diagnosi_std: "+diagnosi_std+" note: "+note+" risposta_std: "+risposta_std;
	}

}