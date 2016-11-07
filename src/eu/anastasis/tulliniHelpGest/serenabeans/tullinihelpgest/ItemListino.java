package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

import eu.anastasis.serena.beans.SerenaMarshallable;

public class ItemListino implements SerenaMarshallable
{

	public static final java.lang.String TIPO__A_FORFAIT = "a forfait";
	public static final java.lang.String TIPO__A_ORA = "a ora";
	public static final java.lang.String TIPO__A_PRESTAZIONE = "a prestazione";


	public static final java.lang.String INSTANCE_NAME = "ItemListino";
	public static final java.lang.String SLOT_DA_METAPRATICA = "da_metapratica";
	public static final java.lang.String SLOT_DESCRIZIONE = "descrizione";
	public static final java.lang.String SLOT_INVERSE_OF_PRESTAZIONI_EXTRA_FORFAIT = "inverse_of_prestazioni_extra_forfait";
	public static final java.lang.String SLOT_PREZZO = "prezzo";
	public static final java.lang.String SLOT_TIPO = "tipo";
	public static final java.lang.String SLOT_TITOLO = "titolo";
	public static final java.lang.String SLOT_TITOLO_PER_FATTURA = "titolo_per_fattura";

	protected Metapratica da_metapratica;
	protected java.lang.String descrizione;
	protected DatiFatturazione inverse_of_prestazioni_extra_forfait;
	protected java.lang.Float prezzo;
	protected java.lang.String tipo;
	protected java.lang.String titolo;
	protected java.lang.String titolo_per_fattura;
	protected String id;

	public ItemListino(){}

	public Metapratica getDa_metapratica() {
		return da_metapratica;
	}

	public void setDa_metapratica(Metapratica da_metapratica) {
		this.da_metapratica = da_metapratica;
	}

	public java.lang.String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(java.lang.String descrizione) {
		this.descrizione = descrizione;
	}

	public DatiFatturazione getInverse_of_prestazioni_extra_forfait() {
		return inverse_of_prestazioni_extra_forfait;
	}

	public void setInverse_of_prestazioni_extra_forfait(DatiFatturazione inverse_of_prestazioni_extra_forfait) {
		this.inverse_of_prestazioni_extra_forfait = inverse_of_prestazioni_extra_forfait;
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
		return "ItemListino \n descrizione: "+descrizione+" prezzo: "+prezzo+" tipo: "+tipo+" titolo: "+titolo+" titolo_per_fattura: "+titolo_per_fattura;
	}

}