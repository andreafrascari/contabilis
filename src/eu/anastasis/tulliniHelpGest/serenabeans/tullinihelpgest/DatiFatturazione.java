package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

import eu.anastasis.serena.beans.SerenaMarshallable;

public class DatiFatturazione implements SerenaMarshallable
{


	public static final java.lang.String INSTANCE_NAME = "DatiFatturazione";
	public static final java.lang.String SLOT_DATA_PRIMA_RATA = "data_prima_rata";
	public static final java.lang.String SLOT_INVERSE_OF_DATI_FATTURAZIONE = "inverse_of_dati_fatturazione";
	public static final java.lang.String SLOT_INVERSE_OF_PREVENTIVO_LISTINO = "inverse_of_preventivo_listino";
	public static final java.lang.String SLOT_LISTINO = "listino";
	public static final java.lang.String SLOT_N_RATE = "n_rate";

	protected java.util.Date data_prima_rata;
	protected Cliente inverse_of_dati_fatturazione;
	protected ClienteCandidato inverse_of_preventivo_listino;
	protected java.util.ArrayList<ItemListino> listino;
	protected java.lang.Integer n_rate;
	protected String id;

	public DatiFatturazione(){}

	public java.util.Date getData_prima_rata() {
		return data_prima_rata;
	}

	public void setData_prima_rata(java.util.Date data_prima_rata) {
		this.data_prima_rata = data_prima_rata;
	}

	public Cliente getInverse_of_dati_fatturazione() {
		return inverse_of_dati_fatturazione;
	}

	public void setInverse_of_dati_fatturazione(Cliente inverse_of_dati_fatturazione) {
		this.inverse_of_dati_fatturazione = inverse_of_dati_fatturazione;
	}

	public ClienteCandidato getInverse_of_preventivo_listino() {
		return inverse_of_preventivo_listino;
	}

	public void setInverse_of_preventivo_listino(ClienteCandidato inverse_of_preventivo_listino) {
		this.inverse_of_preventivo_listino = inverse_of_preventivo_listino;
	}

	public java.util.ArrayList<ItemListino> getListino() {
		return listino;
	}

	public void setListino(java.util.ArrayList<ItemListino> listino) {
		this.listino = listino;
	}

	public java.lang.Integer getN_rate() {
		return n_rate;
	}

	public void setN_rate(java.lang.Integer n_rate) {
		this.n_rate = n_rate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public java.lang.String toString() {
		return "DatiFatturazione \n data_prima_rata: "+data_prima_rata+" n_rate: "+n_rate;
	}

}