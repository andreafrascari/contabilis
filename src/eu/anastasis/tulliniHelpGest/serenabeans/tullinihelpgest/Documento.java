package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

import eu.anastasis.serena.beans.SerenaMarshallable;

public class Documento implements SerenaMarshallable
{
	public static final java.lang.String ANNO_CONTABILE__2010 = "2010";
	public static final java.lang.String ANNO_CONTABILE__2011 = "2011";
	public static final java.lang.String ANNO_CONTABILE__2012 = "2012";
	public static final java.lang.String ANNO_CONTABILE__2013 = "2013";
	public static final java.lang.String ANNO_CONTABILE__2014 = "2014";
	public static final java.lang.String ANNO_CONTABILE__2015 = "2015";
	public static final java.lang.String ANNO_CONTABILE__2016 = "2016";
	public static final java.lang.String ANNO_CONTABILE__2017 = "2017";
	public static final java.lang.String ANNO_CONTABILE__2018 = "2018";
	public static final java.lang.String ANNO_CONTABILE__2019 = "2019";
	public static final java.lang.String ANNO_CONTABILE__2020 = "2020";


	public static final java.lang.String INSTANCE_NAME = "Documento";
	public static final java.lang.String SLOT_ABSTRACT = "abstract";
	public static final java.lang.String SLOT_ALLEGATO = "allegato";
	public static final java.lang.String SLOT_ANNO_CONTABILE = "anno_contabile";
	public static final java.lang.String SLOT_DATA_RIFERIMENTO = "data_riferimento";
	public static final java.lang.String SLOT_FREE_TAG = "free_tag";
	public static final java.lang.String SLOT_REVISIONI = "revisioni";
	public static final java.lang.String SLOT_STORIA_DOCUMENTO = "storia_documento";
	public static final java.lang.String SLOT_TIPOLOGIA = "tipologia";
	public static final java.lang.String SLOT_TITOLO = "titolo";

	protected java.lang.String allegato;
	protected java.lang.String anno_contabile;
	protected java.util.Date data_riferimento;
	protected java.lang.String free_tag;
	protected java.util.ArrayList<RevisioneDocumento> revisioni;
	protected java.util.ArrayList<StoriaDocumento> storia_documento;
	protected DescrizioneDocumento tipologia;
	protected java.lang.String titolo;
	protected String id;

	public Documento(){}


	public java.lang.String getAllegato() {
		return allegato;
	}

	public void setAllegato(java.lang.String allegato) {
		this.allegato = allegato;
	}

	public java.lang.String getAnno_contabile() {
		return anno_contabile;
	}

	public void setAnno_contabile(java.lang.String anno_contabile) {
		this.anno_contabile = anno_contabile;
	}

	public java.util.Date getData_riferimento() {
		return data_riferimento;
	}

	public void setData_riferimento(java.util.Date data_riferimento) {
		this.data_riferimento = data_riferimento;
	}

	public java.lang.String getFree_tag() {
		return free_tag;
	}

	public void setFree_tag(java.lang.String free_tag) {
		this.free_tag = free_tag;
	}

	public java.util.ArrayList<RevisioneDocumento> getRevisioni() {
		return revisioni;
	}

	public void setRevisioni(java.util.ArrayList<RevisioneDocumento> revisioni) {
		this.revisioni = revisioni;
	}

	public java.util.ArrayList<StoriaDocumento> getStoria_documento() {
		return storia_documento;
	}

	public void setStoria_documento(java.util.ArrayList<StoriaDocumento> storia_documento) {
		this.storia_documento = storia_documento;
	}

	public DescrizioneDocumento getTipologia() {
		return tipologia;
	}

	public void setTipologia(DescrizioneDocumento tipologia) {
		this.tipologia = tipologia;
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
		return "Documento \n allegato: "+allegato+" anno_contabile: "+anno_contabile+" data_riferimento: "+data_riferimento+" free_tag: "+free_tag+" titolo: "+titolo;
	}

}