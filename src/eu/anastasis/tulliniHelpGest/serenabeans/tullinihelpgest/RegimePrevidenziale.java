package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

import eu.anastasis.serena.beans.SerenaMarshallable;

public class RegimePrevidenziale implements SerenaMarshallable
{


	public static final java.lang.String REGIME__ENPAB = "ENPAB";


	public static final java.lang.String INSTANCE_NAME = "RegimePrevidenziale";
	public static final java.lang.String SLOT_INVERSE_OF_REGIMI_PREVIDENZIALI = "inverse_of_regimi_previdenziali";
	public static final java.lang.String SLOT_ISCRIZIONE = "iscrizione";
	public static final java.lang.String SLOT_REGIME = "regime";

	protected DatiContabili inverse_of_regimi_previdenziali;
	protected java.lang.String iscrizione;
	protected java.lang.String regime;
	protected String id;

	public RegimePrevidenziale(){}

	public DatiContabili getInverse_of_regimi_previdenziali() {
		return inverse_of_regimi_previdenziali;
	}

	public void setInverse_of_regimi_previdenziali(DatiContabili inverse_of_regimi_previdenziali) {
		this.inverse_of_regimi_previdenziali = inverse_of_regimi_previdenziali;
	}

	public java.lang.String getIscrizione() {
		return iscrizione;
	}

	public void setIscrizione(java.lang.String iscrizione) {
		this.iscrizione = iscrizione;
	}

	public java.lang.String getRegime() {
		return regime;
	}

	public void setRegime(java.lang.String regime) {
		this.regime = regime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public java.lang.String toString() {
		return "RegimePrevidenziale \n iscrizione: "+iscrizione+" regime: "+regime;
	}

}