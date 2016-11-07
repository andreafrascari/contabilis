package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

import eu.anastasis.serena.beans.SerenaMarshallable;

public class SollecitoPagamento implements SerenaMarshallable
{

	public static final java.lang.String NUMERO_SOLLECITO__PRIMO = "primo";
	public static final java.lang.String NUMERO_SOLLECITO__SECONDO = "secondo";
	public static final java.lang.String NUMERO_SOLLECITO__TERZO = "terzo";
	public static final java.lang.String NUMERO_SOLLECITO__NON_SOLLECITARE = "non sollecitare";


	public static final java.lang.String INSTANCE_NAME = "SollecitoPagamento";
	public static final java.lang.String SLOT_DATA_SPEDIZIONE = "data_spedizione";
	public static final java.lang.String SLOT_DESCRIZIONE = "descrizione";
	public static final java.lang.String SLOT_INVERSE_OF_SOLLECITI_PAGAMENTO = "inverse_of_solleciti_pagamento";
	public static final java.lang.String SLOT_NOTE = "note";
	public static final java.lang.String SLOT_NUMERO_SOLLECITO = "numero_sollecito";
	public static final java.lang.String SLOT_OGGETTO = "oggetto";

	protected java.util.Date data_spedizione;
	protected java.lang.String descrizione;
	protected ProForma inverse_of_solleciti_pagamento;
	protected java.lang.String note;
	protected java.lang.String numero_sollecito;
	protected java.lang.String oggetto;
	protected String id;

	public SollecitoPagamento(){}

	public java.util.Date getData_spedizione() {
		return data_spedizione;
	}

	public void setData_spedizione(java.util.Date data_spedizione) {
		this.data_spedizione = data_spedizione;
	}

	public java.lang.String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(java.lang.String descrizione) {
		this.descrizione = descrizione;
	}

	public ProForma getInverse_of_solleciti_pagamento() {
		return inverse_of_solleciti_pagamento;
	}

	public void setInverse_of_solleciti_pagamento(ProForma inverse_of_solleciti_pagamento) {
		this.inverse_of_solleciti_pagamento = inverse_of_solleciti_pagamento;
	}

	public java.lang.String getNote() {
		return note;
	}

	public void setNote(java.lang.String note) {
		this.note = note;
	}

	public java.lang.String getNumero_sollecito() {
		return numero_sollecito;
	}

	public void setNumero_sollecito(java.lang.String numero_sollecito) {
		this.numero_sollecito = numero_sollecito;
	}

	public java.lang.String getOggetto() {
		return oggetto;
	}

	public void setOggetto(java.lang.String oggetto) {
		this.oggetto = oggetto;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public java.lang.String toString() {
		return "SollecitoPagamento \n data_spedizione: "+data_spedizione+" descrizione: "+descrizione+" note: "+note+" numero_sollecito: "+numero_sollecito+" oggetto: "+oggetto;
	}

}