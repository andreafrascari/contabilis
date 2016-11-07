package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

import eu.anastasis.serena.beans.SerenaMarshallable;

public class IndicizzazioneListino implements SerenaMarshallable
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


	public static final java.lang.String INSTANCE_NAME = "IndicizzazioneListino";
	public static final java.lang.String SLOT_ANNO_CONTABILE = "anno_contabile";
	public static final java.lang.String SLOT_INDICE = "indice";
	public static final java.lang.String SLOT_NOTE = "note";

	protected java.lang.String anno_contabile;
	protected java.lang.Float indice;
	protected java.lang.String note;
	protected String id;

	public IndicizzazioneListino(){}

	public java.lang.String getAnno_contabile() {
		return anno_contabile;
	}

	public void setAnno_contabile(java.lang.String anno_contabile) {
		this.anno_contabile = anno_contabile;
	}

	public java.lang.Float getIndice() {
		return indice;
	}

	public void setIndice(java.lang.Float indice) {
		this.indice = indice;
	}

	public java.lang.String getNote() {
		return note;
	}

	public void setNote(java.lang.String note) {
		this.note = note;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public java.lang.String toString() {
		return "IndicizzazioneListino \n anno_contabile: "+anno_contabile+" indice: "+indice+" note: "+note;
	}

}