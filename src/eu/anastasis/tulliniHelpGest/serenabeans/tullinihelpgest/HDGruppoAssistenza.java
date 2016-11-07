package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

import eu.anastasis.serena.beans.SerenaMarshallable;

public class HDGruppoAssistenza implements SerenaMarshallable
{


	public static final java.lang.String INSTANCE_NAME = "HDGruppoAssistenza";
	public static final java.lang.String SLOT_ALIAS = "alias";
	public static final java.lang.String SLOT_EMAIL = "email";
	public static final java.lang.String SLOT_EMAIL_PWD = "email_pwd";
	public static final java.lang.String SLOT_NOME = "nome";
	public static final java.lang.String SLOT_NOTE = "note";
	public static final java.lang.String SLOT_PORT = "port";
	public static final java.lang.String SLOT_SERVER = "server";
	public static final java.lang.String SLOT_TICKETS = "tickets";

	protected java.lang.String alias;
	protected java.lang.String email;
	protected java.lang.String email_pwd;
	protected java.lang.String nome;
	protected java.lang.String note;
	protected java.lang.String port;
	protected java.lang.String server;
	protected java.util.ArrayList<HDTicket> tickets;
	protected String id;

	public HDGruppoAssistenza(){}

	public java.lang.String getAlias() {
		return alias;
	}

	public void setAlias(java.lang.String alias) {
		this.alias = alias;
	}

	public java.lang.String getEmail() {
		return email;
	}

	public void setEmail(java.lang.String email) {
		this.email = email;
	}

	public java.lang.String getEmail_pwd() {
		return email_pwd;
	}

	public void setEmail_pwd(java.lang.String email_pwd) {
		this.email_pwd = email_pwd;
	}

	public java.lang.String getNome() {
		return nome;
	}

	public void setNome(java.lang.String nome) {
		this.nome = nome;
	}

	public java.lang.String getNote() {
		return note;
	}

	public void setNote(java.lang.String note) {
		this.note = note;
	}

	public java.lang.String getPort() {
		return port;
	}

	public void setPort(java.lang.String port) {
		this.port = port;
	}

	public java.lang.String getServer() {
		return server;
	}

	public void setServer(java.lang.String server) {
		this.server = server;
	}

	public java.util.ArrayList<HDTicket> getTickets() {
		return tickets;
	}

	public void setTickets(java.util.ArrayList<HDTicket> tickets) {
		this.tickets = tickets;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public java.lang.String toString() {
		return "HDGruppoAssistenza \n alias: "+alias+" email: "+email+" email_pwd: "+email_pwd+" nome: "+nome+" note: "+note+" port: "+port+" server: "+server;
	}

}