package eu.anastasis.it.tullinidms.modules;

public class Documento {
	
	public static final String MY_CLASS= "Documento";
	public static final String AUI_TAG= "AUI";

	private String ID = null;
	private String last_modification_date = null;
	private String last_modification_user = null;
	private String titolo = null;
	private String tipo = null;
	private String data= null;
	private String allegato = null;
	private String anno_contabile = null;
	
	public String getAnno_contabile() {
		return anno_contabile;
	}
	public void setAnno_contabile(String anno_contabile) {
		this.anno_contabile = anno_contabile;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getLast_modification_date() {
		return last_modification_date;
	}
	public void setLast_modification_date(String last_modification_date) {
		this.last_modification_date = last_modification_date;
	}
	public String getLast_modification_user() {
		return last_modification_user;
	}
	public void setLast_modification_user(String last_modification_user) {
		this.last_modification_user = last_modification_user;
	}
	public String getAllegato() {
		return allegato;
	}
	public void setAllegato(String allegato) {
		this.allegato = allegato;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
}
