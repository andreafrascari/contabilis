package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

import eu.anastasis.serena.beans.SerenaMarshallable;

public class VoceFattura implements SerenaMarshallable
{
	public static final java.lang.String IVA__FUORI_CAMPO = "Fuori Campo";
	public static final java.lang.String IVA__ESENTE_ARTICOLO_10 = "Esente Articolo 10";
	public static final java.lang.String IVA__4 = "4%";
	public static final java.lang.String IVA__10 = "10%";
	public static final java.lang.String IVA__STD = "22%";


	public static final java.lang.String INSTANCE_NAME = "VoceFattura";
	public static final java.lang.String SLOT_IMPORTO = "importo";
	public static final java.lang.String SLOT_INVERSE_OF_VOCI_FATTURA = "inverse_of_voci_fattura";
	public static final java.lang.String SLOT_INVERSE_OF_VOCI_PROFORMA = "inverse_of_voci_proforma";
	public static final java.lang.String SLOT_IVA = "iva";
	public static final java.lang.String SLOT_OGGETTO = "oggetto";
	public static final java.lang.String SLOT_RIF_PRATICA = "rif_pratica";

	protected java.lang.Float importo;
	protected Fattura inverse_of_voci_fattura;
	protected ProForma inverse_of_voci_proforma;
	protected java.lang.String iva;
	protected java.lang.String oggetto;
	protected java.lang.Integer rif_pratica;
	protected String id;

	public VoceFattura(){}

	public java.lang.Float getImporto() {
		return importo;
	}

	public void setImporto(java.lang.Float importo) {
		this.importo = importo;
	}

	public Fattura getInverse_of_voci_fattura() {
		return inverse_of_voci_fattura;
	}

	public void setInverse_of_voci_fattura(Fattura inverse_of_voci_fattura) {
		this.inverse_of_voci_fattura = inverse_of_voci_fattura;
	}

	public ProForma getInverse_of_voci_proforma() {
		return inverse_of_voci_proforma;
	}

	public void setInverse_of_voci_proforma(ProForma inverse_of_voci_proforma) {
		this.inverse_of_voci_proforma = inverse_of_voci_proforma;
	}

	public java.lang.String getIva() {
		return iva;
	}

	public void setIva(java.lang.String iva) {
		this.iva = iva;
	}

	public java.lang.String getOggetto() {
		return oggetto;
	}

	public void setOggetto(java.lang.String oggetto) {
		this.oggetto = oggetto;
	}

	public java.lang.Integer getRif_pratica() {
		return rif_pratica;
	}

	public void setRif_pratica(java.lang.Integer rif_pratica) {
		this.rif_pratica = rif_pratica;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public java.lang.String toString() {
		return "VoceFattura \n importo: "+importo+" iva: "+iva+" oggetto: "+oggetto+" rif_pratica: "+rif_pratica;
	}

}