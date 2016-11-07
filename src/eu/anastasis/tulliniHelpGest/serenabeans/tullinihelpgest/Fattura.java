package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

import eu.anastasis.serena.beans.SerenaMarshallable;

public class Fattura implements SerenaMarshallable
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

	public static final java.lang.String COMPETENZA__CONTABILIS = "CONTABILIS";
	public static final java.lang.String COMPETENZA__STUDIO = "STUDIO";


	public static final java.lang.String INSTANCE_NAME = "Fattura";
	public static final java.lang.String SLOT_ANNO_CONTABILE = "anno_contabile";
	public static final java.lang.String SLOT_COMPETENZA = "competenza";
	public static final java.lang.String SLOT_DA_PROFORMA = "da_proforma";
	public static final java.lang.String SLOT_DATA = "data";
	public static final java.lang.String SLOT_DATA_PAGAMENTO = "data_pagamento";
	public static final java.lang.String SLOT_INVERSE_OF_FATTURE = "inverse_of_fatture";
	public static final java.lang.String SLOT_NOTE = "note";
	public static final java.lang.String SLOT_NUMERO = "numero";
	public static final java.lang.String SLOT_RA = "ra";
	public static final java.lang.String SLOT_RIV_PREV = "riv_prev";
	public static final java.lang.String SLOT_SPESE_ANTICIPATE_DESC = "spese_anticipate_desc";
	public static final java.lang.String SLOT_SPESE_ANTICIPATE_FATTURA = "spese_anticipate_fattura";
	public static final java.lang.String SLOT_VOCI_FATTURA = "voci_fattura";

	protected java.lang.String anno_contabile;
	protected java.lang.String competenza;
	protected ProForma da_proforma;
	protected java.util.Date data;
	protected java.util.Date data_pagamento;
	protected Cliente inverse_of_fatture;
	protected java.lang.String note;
	protected java.lang.Integer numero;
	protected java.lang.Boolean ra;
	protected java.lang.Boolean riv_prev;
	protected java.lang.String spese_anticipate_desc;
	protected java.lang.Float spese_anticipate_fattura;
	protected java.util.ArrayList<VoceFattura> voci_fattura;
	protected String id;

	public Fattura(){}

	public java.lang.String getAnno_contabile() {
		return anno_contabile;
	}

	public void setAnno_contabile(java.lang.String anno_contabile) {
		this.anno_contabile = anno_contabile;
	}

	public java.lang.String getCompetenza() {
		return competenza;
	}

	public void setCompetenza(java.lang.String competenza) {
		this.competenza = competenza;
	}

	public ProForma getDa_proforma() {
		return da_proforma;
	}

	public void setDa_proforma(ProForma da_proforma) {
		this.da_proforma = da_proforma;
	}

	public java.util.Date getData() {
		return data;
	}

	public void setData(java.util.Date data) {
		this.data = data;
	}

	public java.util.Date getData_pagamento() {
		return data_pagamento;
	}

	public void setData_pagamento(java.util.Date data_pagamento) {
		this.data_pagamento = data_pagamento;
	}

	public Cliente getInverse_of_fatture() {
		return inverse_of_fatture;
	}

	public void setInverse_of_fatture(Cliente inverse_of_fatture) {
		this.inverse_of_fatture = inverse_of_fatture;
	}

	public java.lang.String getNote() {
		return note;
	}

	public void setNote(java.lang.String note) {
		this.note = note;
	}

	public java.lang.Integer getNumero() {
		return numero;
	}

	public void setNumero(java.lang.Integer numero) {
		this.numero = numero;
	}

	public java.lang.Boolean getRa() {
		return ra;
	}

	public void setRa(java.lang.Boolean ra) {
		this.ra = ra;
	}

	public java.lang.Boolean getRiv_prev() {
		return riv_prev;
	}

	public void setRiv_prev(java.lang.Boolean riv_prev) {
		this.riv_prev = riv_prev;
	}

	public java.lang.String getSpese_anticipate_desc() {
		return spese_anticipate_desc;
	}

	public void setSpese_anticipate_desc(java.lang.String spese_anticipate_desc) {
		this.spese_anticipate_desc = spese_anticipate_desc;
	}

	public java.lang.Float getSpese_anticipate_fattura() {
		return spese_anticipate_fattura;
	}

	public void setSpese_anticipate_fattura(java.lang.Float spese_anticipate_fattura) {
		this.spese_anticipate_fattura = spese_anticipate_fattura;
	}

	public java.util.ArrayList<VoceFattura> getVoci_fattura() {
		return voci_fattura;
	}

	public void setVoci_fattura(java.util.ArrayList<VoceFattura> voci_fattura) {
		this.voci_fattura = voci_fattura;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public java.lang.String toString() {
		return "Fattura \n anno_contabile: "+anno_contabile+" competenza: "+competenza+" data: "+data+" data_pagamento: "+data_pagamento+" note: "+note+" numero: "+numero+" ra: "+ra+" riv_prev: "+riv_prev+" spese_anticipate_desc: "+spese_anticipate_desc+" spese_anticipate_fattura: "+spese_anticipate_fattura;
	}

}