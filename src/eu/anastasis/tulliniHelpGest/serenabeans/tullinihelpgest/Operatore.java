package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

import eu.anastasis.serena.beans.SerenaMarshallable;

public class Operatore implements SerenaMarshallable
{


	public static final java.lang.String INSTANCE_NAME = "Operatore";
	public static final java.lang.String SLOT_ATTIVITA_ASSEGNATE = "attivita_assegnate";
	public static final java.lang.String SLOT_CELLULARE = "cellulare";
	public static final java.lang.String SLOT_COSTO_ORARIO = "costo_orario";
	public static final java.lang.String SLOT_EMAIL = "email";
	public static final java.lang.String SLOT_NOME_E_COGNOME = "nome_e_cognome";
	public static final java.lang.String SLOT_NOTIFICHE_SCADENZE = "notifiche_scadenze";
	public static final java.lang.String SLOT_OP_SCADENZE = "op_scadenze";
	public static final java.lang.String SLOT_PWD_AUTORIZZATIVA = "pwd_autorizzativa";
	public static final java.lang.String SLOT_RESPONSABILE_DI = "responsabile_di";
	public static final java.lang.String SLOT_RESPONSABILE_DI_PRATICHE = "responsabile_di_pratiche";
	public static final java.lang.String SLOT_RIVENDITA_ORARIA = "rivendita_oraria";
	public static final java.lang.String SLOT_TICKETASSEGNATI = "ticketAssegnati";

	protected java.util.ArrayList<Attivita> attivita_assegnate;
	protected java.lang.String cellulare;
	protected java.lang.Float costo_orario;
	protected java.lang.String email;
	protected java.lang.String nome_e_cognome;
	protected java.util.ArrayList<NotificaScadenza> notifiche_scadenze;
	protected java.util.ArrayList<Scadenza> op_scadenze;
	protected java.lang.String pwd_autorizzativa;
	protected java.util.ArrayList<Cliente> responsabile_di;
	protected java.util.ArrayList<Pratica> responsabile_di_pratiche;
	protected java.lang.Float rivendita_oraria;
	protected java.util.ArrayList<HDTicket> ticketAssegnati;
	protected String id;

	public Operatore(){}

	public java.util.ArrayList<Attivita> getAttivita_assegnate() {
		return attivita_assegnate;
	}

	public void setAttivita_assegnate(java.util.ArrayList<Attivita> attivita_assegnate) {
		this.attivita_assegnate = attivita_assegnate;
	}

	public java.lang.String getCellulare() {
		return cellulare;
	}

	public void setCellulare(java.lang.String cellulare) {
		this.cellulare = cellulare;
	}

	public java.lang.Float getCosto_orario() {
		return costo_orario;
	}

	public void setCosto_orario(java.lang.Float costo_orario) {
		this.costo_orario = costo_orario;
	}

	public java.lang.String getEmail() {
		return email;
	}

	public void setEmail(java.lang.String email) {
		this.email = email;
	}

	public java.lang.String getNome_e_cognome() {
		return nome_e_cognome;
	}

	public void setNome_e_cognome(java.lang.String nome_e_cognome) {
		this.nome_e_cognome = nome_e_cognome;
	}

	public java.util.ArrayList<NotificaScadenza> getNotifiche_scadenze() {
		return notifiche_scadenze;
	}

	public void setNotifiche_scadenze(java.util.ArrayList<NotificaScadenza> notifiche_scadenze) {
		this.notifiche_scadenze = notifiche_scadenze;
	}

	public java.util.ArrayList<Scadenza> getOp_scadenze() {
		return op_scadenze;
	}

	public void setOp_scadenze(java.util.ArrayList<Scadenza> op_scadenze) {
		this.op_scadenze = op_scadenze;
	}

	public java.lang.String getPwd_autorizzativa() {
		return pwd_autorizzativa;
	}

	public void setPwd_autorizzativa(java.lang.String pwd_autorizzativa) {
		this.pwd_autorizzativa = pwd_autorizzativa;
	}

	public java.util.ArrayList<Cliente> getResponsabile_di() {
		return responsabile_di;
	}

	public void setResponsabile_di(java.util.ArrayList<Cliente> responsabile_di) {
		this.responsabile_di = responsabile_di;
	}

	public java.util.ArrayList<Pratica> getResponsabile_di_pratiche() {
		return responsabile_di_pratiche;
	}

	public void setResponsabile_di_pratiche(java.util.ArrayList<Pratica> responsabile_di_pratiche) {
		this.responsabile_di_pratiche = responsabile_di_pratiche;
	}

	public java.lang.Float getRivendita_oraria() {
		return rivendita_oraria;
	}

	public void setRivendita_oraria(java.lang.Float rivendita_oraria) {
		this.rivendita_oraria = rivendita_oraria;
	}

	public java.util.ArrayList<HDTicket> getTicketAssegnati() {
		return ticketAssegnati;
	}

	public void setTicketAssegnati(java.util.ArrayList<HDTicket> ticketAssegnati) {
		this.ticketAssegnati = ticketAssegnati;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public java.lang.String toString() {
		return "Operatore \n cellulare: "+cellulare+" costo_orario: "+costo_orario+" email: "+email+" nome_e_cognome: "+nome_e_cognome+" pwd_autorizzativa: "+pwd_autorizzativa+" rivendita_oraria: "+rivendita_oraria;
	}

}