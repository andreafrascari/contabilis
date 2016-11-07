package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

import eu.anastasis.serena.beans.SerenaMarshallable;

public class CalendarioFatturazione implements SerenaMarshallable
{


	public static final java.lang.String INSTANCE_NAME = "CalendarioFatturazione";
	public static final java.lang.String SLOT_DATA = "data";
	public static final java.lang.String SLOT_PER_CLIENTE = "per_cliente";
	public static final java.lang.String SLOT_PROFORMA_EMESSA = "proforma_emessa";
	public static final java.lang.String SLOT_RATA = "rata";
	public static final java.lang.String SLOT_SU_RATE = "su_rate";

	protected java.util.Date data;
	protected Cliente per_cliente;
	protected ProForma proforma_emessa;
	protected java.lang.Integer rata;
	protected java.lang.Integer su_rate;
	protected String id;

	public CalendarioFatturazione(){}

	public java.util.Date getData() {
		return data;
	}

	public void setData(java.util.Date data) {
		this.data = data;
	}

	public Cliente getPer_cliente() {
		return per_cliente;
	}

	public void setPer_cliente(Cliente per_cliente) {
		this.per_cliente = per_cliente;
	}

	public ProForma getProforma_emessa() {
		return proforma_emessa;
	}

	public void setProforma_emessa(ProForma proforma_emessa) {
		this.proforma_emessa = proforma_emessa;
	}

	public java.lang.Integer getRata() {
		return rata;
	}

	public void setRata(java.lang.Integer rata) {
		this.rata = rata;
	}

	public java.lang.Integer getSu_rate() {
		return su_rate;
	}

	public void setSu_rate(java.lang.Integer su_rate) {
		this.su_rate = su_rate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public java.lang.String toString() {
		return "CalendarioFatturazione \n data: "+data+" rata: "+rata+" su_rate: "+su_rate;
	}

}