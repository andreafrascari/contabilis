package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

import eu.anastasis.serena.beans.SerenaMarshallable;

public class Metapratica implements SerenaMarshallable
{

	public static final java.lang.String TIPO__A_FORFAIT = "a forfait";
	public static final java.lang.String TIPO__A_ORA = "a ora";
	public static final java.lang.String TIPO__A_PRESTAZIONE = "a prestazione";


	public static final java.lang.String INSTANCE_NAME = "Metapratica";
	public static final java.lang.String SLOT_DESCRIZIONE = "descrizione";
	public static final java.lang.String SLOT_IN_LISTINI = "in_listini";
	public static final java.lang.String SLOT_LISTA_ATTIVITA = "lista_attivita";
	public static final java.lang.String SLOT_PRATICHE_GENERATE = "pratiche_generate";
	public static final java.lang.String SLOT_PREZZO = "prezzo";
	public static final java.lang.String SLOT_TIPO = "tipo";
	public static final java.lang.String SLOT_TITOLO = "titolo";
	public static final java.lang.String SLOT_TITOLO_PER_FATTURA = "titolo_per_fattura";

	protected java.lang.String descrizione;
	protected java.util.ArrayList<ItemListino> in_listini;
	protected java.util.ArrayList<Metaattivita> lista_attivita;
	protected java.util.ArrayList<Pratica> pratiche_generate;
	protected java.lang.Float prezzo;
	protected java.lang.String tipo;
	protected java.lang.String titolo;
	protected java.lang.String titolo_per_fattura;
	protected String id;

	public Metapratica(){}

	public java.lang.String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(java.lang.String descrizione) {
		this.descrizione = descrizione;
	}

	public java.util.ArrayList<ItemListino> getIn_listini() {
		return in_listini;
	}

	public void setIn_listini(java.util.ArrayList<ItemListino> in_listini) {
		this.in_listini = in_listini;
	}

	public java.util.ArrayList<Metaattivita> getLista_attivita() {
		return lista_attivita;
	}

	public void setLista_attivita(java.util.ArrayList<Metaattivita> lista_attivita) {
		this.lista_attivita = lista_attivita;
	}

	public java.util.ArrayList<Pratica> getPratiche_generate() {
		return pratiche_generate;
	}

	public void setPratiche_generate(java.util.ArrayList<Pratica> pratiche_generate) {
		this.pratiche_generate = pratiche_generate;
	}

	public java.lang.Float getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(java.lang.Float prezzo) {
		this.prezzo = prezzo;
	}

	public java.lang.String getTipo() {
		return tipo;
	}

	public void setTipo(java.lang.String tipo) {
		this.tipo = tipo;
	}

	public java.lang.String getTitolo() {
		return titolo;
	}

	public void setTitolo(java.lang.String titolo) {
		this.titolo = titolo;
	}

	public java.lang.String getTitolo_per_fattura() {
		return titolo_per_fattura;
	}

	public void setTitolo_per_fattura(java.lang.String titolo_per_fattura) {
		this.titolo_per_fattura = titolo_per_fattura;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public java.lang.String toString() {
		return "Metapratica \n descrizione: "+descrizione+" prezzo: "+prezzo+" tipo: "+tipo+" titolo: "+titolo+" titolo_per_fattura: "+titolo_per_fattura;
	}

}