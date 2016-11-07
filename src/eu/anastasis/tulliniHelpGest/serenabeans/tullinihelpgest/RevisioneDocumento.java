package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

import eu.anastasis.serena.beans.SerenaMarshallable;

public class RevisioneDocumento implements SerenaMarshallable
{


	public static final java.lang.String INSTANCE_NAME = "RevisioneDocumento";
	public static final java.lang.String SLOT_ALLEGATO = "allegato";
	public static final java.lang.String SLOT_INVERSE_OF_REVISIONI = "inverse_of_revisioni";

	protected java.lang.String allegato;
	protected Documento inverse_of_revisioni;
	protected String id;

	public RevisioneDocumento(){}

	public java.lang.String getAllegato() {
		return allegato;
	}

	public void setAllegato(java.lang.String allegato) {
		this.allegato = allegato;
	}

	public Documento getInverse_of_revisioni() {
		return inverse_of_revisioni;
	}

	public void setInverse_of_revisioni(Documento inverse_of_revisioni) {
		this.inverse_of_revisioni = inverse_of_revisioni;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public java.lang.String toString() {
		return "RevisioneDocumento \n allegato: "+allegato;
	}

}