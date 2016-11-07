package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

import eu.anastasis.serena.beans.SerenaMarshallable;

public class ProfiloDinamicoClienti implements SerenaMarshallable
{


	public static final java.lang.String INSTANCE_NAME = "ProfiloDinamicoClienti";
	public static final java.lang.String SLOT_INVERSE_OF_PROFILI_CLIENTI = "inverse_of_profili_clienti";
	public static final java.lang.String SLOT_NOME = "nome";
	public static final java.lang.String SLOT_NOTE = "note";
	public static final java.lang.String SLOT_QUERY = "query";

	protected java.util.ArrayList<Scadenza> inverse_of_profili_clienti;
	protected java.lang.String nome;
	protected java.lang.String note;
	protected java.lang.String query;
	protected String id;

	public ProfiloDinamicoClienti(){}

	public java.util.ArrayList<Scadenza> getInverse_of_profili_clienti() {
		return inverse_of_profili_clienti;
	}

	public void setInverse_of_profili_clienti(java.util.ArrayList<Scadenza> inverse_of_profili_clienti) {
		this.inverse_of_profili_clienti = inverse_of_profili_clienti;
	}

	public java.lang.String getNome() {
		return nome;
	}

	public void setNome(java.lang.String nome) {
		this.nome = nome;
	}

	public java.lang.String getNote() {
		return note;
	}

	public void setNote(java.lang.String note) {
		this.note = note;
	}

	public java.lang.String getQuery() {
		return query;
	}

	public void setQuery(java.lang.String query) {
		this.query = query;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public java.lang.String toString() {
		return "ProfiloDinamicoClienti \n nome: "+nome+" note: "+note+" query: "+query;
	}

}