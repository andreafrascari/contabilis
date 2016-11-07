package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

import eu.anastasis.serena.beans.SerenaMarshallable;

public class Metaattivita implements SerenaMarshallable
{


	public static final java.lang.String INSTANCE_NAME = "Metaattivita";
	public static final java.lang.String SLOT_INVERSE_OF_LISTA_ATTIVITA = "inverse_of_lista_attivita";
	public static final java.lang.String SLOT_NOME = "nome";

	protected Metapratica inverse_of_lista_attivita;
	protected java.lang.String nome;
	protected String id;

	public Metaattivita(){}

	public Metapratica getInverse_of_lista_attivita() {
		return inverse_of_lista_attivita;
	}

	public void setInverse_of_lista_attivita(Metapratica inverse_of_lista_attivita) {
		this.inverse_of_lista_attivita = inverse_of_lista_attivita;
	}

	public java.lang.String getNome() {
		return nome;
	}

	public void setNome(java.lang.String nome) {
		this.nome = nome;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public java.lang.String toString() {
		return "Metaattivita \n nome: "+nome;
	}

}