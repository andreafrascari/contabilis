package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

import eu.anastasis.serena.beans.SerenaMarshallable;

public class StoricoInvii implements SerenaMarshallable
{

	public static final java.lang.String TIPO_SOLLECITO__SMS = "sms";
	public static final java.lang.String TIPO_SOLLECITO__MAIL = "mail";


	public static final java.lang.String INSTANCE_NAME = "StoricoInvii";
	public static final java.lang.String SLOT_DATA = "data";
	public static final java.lang.String SLOT_DESTINATARIO = "destinatario";
	public static final java.lang.String SLOT_ESITO = "esito";
	public static final java.lang.String SLOT_MESSAGGIO = "messaggio";
	public static final java.lang.String SLOT_TIPO_SOLLECITO = "tipo_sollecito";

	protected java.util.Date data;
	protected java.lang.String destinatario;
	protected java.lang.String esito;
	protected java.lang.String messaggio;
	protected java.lang.String tipo_sollecito;
	protected String id;

	public StoricoInvii(){}

	public java.util.Date getData() {
		return data;
	}

	public void setData(java.util.Date data) {
		this.data = data;
	}

	public java.lang.String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(java.lang.String destinatario) {
		this.destinatario = destinatario;
	}

	public java.lang.String getEsito() {
		return esito;
	}

	public void setEsito(java.lang.String esito) {
		this.esito = esito;
	}

	public java.lang.String getMessaggio() {
		return messaggio;
	}

	public void setMessaggio(java.lang.String messaggio) {
		this.messaggio = messaggio;
	}

	public java.lang.String getTipo_sollecito() {
		return tipo_sollecito;
	}

	public void setTipo_sollecito(java.lang.String tipo_sollecito) {
		this.tipo_sollecito = tipo_sollecito;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public java.lang.String toString() {
		return "StoricoInvii \n data: "+data+" destinatario: "+destinatario+" esito: "+esito+" messaggio: "+messaggio+" tipo_sollecito: "+tipo_sollecito;
	}

}