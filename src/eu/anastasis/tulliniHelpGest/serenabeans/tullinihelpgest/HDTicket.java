package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

import eu.anastasis.serena.beans.SerenaMarshallable;

public class HDTicket implements SerenaMarshallable
{

	public static final java.lang.String AZIONERICHIESTA__RICHIAMA_LUI = "richiama lui";
	public static final java.lang.String AZIONERICHIESTA__INVIARE_SMS = "inviare sms";
	public static final java.lang.String AZIONERICHIESTA__RICHIAMARE_AL = "richiamare al";
	public static final java.lang.String AZIONERICHIESTA__INVIARE_MAIL = "inviare mail";
	public static final java.lang.String AZIONERICHIESTA__INESSUNA = "nessuna";

	public static final java.lang.String DIAGNOSI_STD__RICHIESTA_PREVENTIVO = "richiesta preventivo";

	public static final java.lang.String PRIORITA_TICKET__URGENTE = "urgente";
	public static final java.lang.String PRIORITA_TICKET__NORMALE = "normale";

	public static final java.lang.String STATOTICKET__IN_ATTESA = "in attesa";
	public static final java.lang.String STATOTICKET__ASSEGNATO = "assegnato";
	public static final java.lang.String STATOTICKET__APERTO = "aperto";
	public static final java.lang.String STATOTICKET__CHIUSO = "chiuso";
	public static final java.lang.String STATOTICKET__INSOLUBILE = "insolubile";


	public static final java.lang.String INSTANCE_NAME = "HDTicket";
	public static final java.lang.String SLOT_ALLEGATO = "allegato";
	public static final java.lang.String SLOT_ALLEGATO_1 = "allegato_1";
	public static final java.lang.String SLOT_ASSEGNATOAPRATICA = "assegnatoApratica";
	public static final java.lang.String SLOT_ASSEGNATO_A = "assegnato_a";
	public static final java.lang.String SLOT_AZIONERICHIESTA = "azioneRichiesta";
	public static final java.lang.String SLOT_CONTATTONONRICONOSCIUTO = "contattoNonRiconosciuto";
	public static final java.lang.String SLOT_DACLIENTE = "daCliente";
	public static final java.lang.String SLOT_DATAARRIVO = "dataArrivo";
	public static final java.lang.String SLOT_DATA_SCADENZA = "data_scadenza";
	public static final java.lang.String SLOT_DIAGNOSI_LIBERO = "diagnosi_libero";
	public static final java.lang.String SLOT_DIAGNOSI_STD = "diagnosi_std";
	public static final java.lang.String SLOT_EMAILASSISTENZA = "emailAssistenza";
	public static final java.lang.String SLOT_IDENTIFICATIVO = "identificativo";
	public static final java.lang.String SLOT_PRIORITA_TICKET = "priorita_ticket";
	public static final java.lang.String SLOT_RISPONDERE_A = "rispondere_a";
	public static final java.lang.String SLOT_STATOTICKET = "statoTicket";
	public static final java.lang.String SLOT_THREAD = "thread";

	protected java.lang.String allegato;
	protected java.lang.String allegato_1;
	protected Pratica assegnatoApratica;
	protected Operatore assegnato_a;
	protected java.lang.String azioneRichiesta;
	protected java.lang.String contattoNonRiconosciuto;
	protected Cliente daCliente;
	protected java.util.Date dataArrivo;
	protected java.util.Date data_scadenza;
	protected java.lang.String diagnosi_libero;
	protected java.lang.String diagnosi_std;
	protected HDGruppoAssistenza emailAssistenza;
	protected java.lang.String identificativo;
	protected java.lang.String priorita_ticket;
	protected java.lang.String rispondere_a;
	protected java.lang.String statoTicket;
	protected java.util.ArrayList<HDThreadStep> thread;
	protected String id;

	public HDTicket(){}

	public java.lang.String getAllegato() {
		return allegato;
	}

	public void setAllegato(java.lang.String allegato) {
		this.allegato = allegato;
	}

	public java.lang.String getAllegato_1() {
		return allegato_1;
	}

	public void setAllegato_1(java.lang.String allegato_1) {
		this.allegato_1 = allegato_1;
	}

	public Pratica getAssegnatoApratica() {
		return assegnatoApratica;
	}

	public void setAssegnatoApratica(Pratica assegnatoApratica) {
		this.assegnatoApratica = assegnatoApratica;
	}

	public Operatore getAssegnato_a() {
		return assegnato_a;
	}

	public void setAssegnato_a(Operatore assegnato_a) {
		this.assegnato_a = assegnato_a;
	}

	public java.lang.String getAzioneRichiesta() {
		return azioneRichiesta;
	}

	public void setAzioneRichiesta(java.lang.String azioneRichiesta) {
		this.azioneRichiesta = azioneRichiesta;
	}

	public java.lang.String getContattoNonRiconosciuto() {
		return contattoNonRiconosciuto;
	}

	public void setContattoNonRiconosciuto(java.lang.String contattoNonRiconosciuto) {
		this.contattoNonRiconosciuto = contattoNonRiconosciuto;
	}

	public Cliente getDaCliente() {
		return daCliente;
	}

	public void setDaCliente(Cliente daCliente) {
		this.daCliente = daCliente;
	}

	public java.util.Date getDataArrivo() {
		return dataArrivo;
	}

	public void setDataArrivo(java.util.Date dataArrivo) {
		this.dataArrivo = dataArrivo;
	}

	public java.util.Date getData_scadenza() {
		return data_scadenza;
	}

	public void setData_scadenza(java.util.Date data_scadenza) {
		this.data_scadenza = data_scadenza;
	}

	public java.lang.String getDiagnosi_libero() {
		return diagnosi_libero;
	}

	public void setDiagnosi_libero(java.lang.String diagnosi_libero) {
		this.diagnosi_libero = diagnosi_libero;
	}

	public java.lang.String getDiagnosi_std() {
		return diagnosi_std;
	}

	public void setDiagnosi_std(java.lang.String diagnosi_std) {
		this.diagnosi_std = diagnosi_std;
	}

	public HDGruppoAssistenza getEmailAssistenza() {
		return emailAssistenza;
	}

	public void setEmailAssistenza(HDGruppoAssistenza emailAssistenza) {
		this.emailAssistenza = emailAssistenza;
	}

	public java.lang.String getIdentificativo() {
		return identificativo;
	}

	public void setIdentificativo(java.lang.String identificativo) {
		this.identificativo = identificativo;
	}

	public java.lang.String getPriorita_ticket() {
		return priorita_ticket;
	}

	public void setPriorita_ticket(java.lang.String priorita_ticket) {
		this.priorita_ticket = priorita_ticket;
	}

	public java.lang.String getRispondere_a() {
		return rispondere_a;
	}

	public void setRispondere_a(java.lang.String rispondere_a) {
		this.rispondere_a = rispondere_a;
	}

	public java.lang.String getStatoTicket() {
		return statoTicket;
	}

	public void setStatoTicket(java.lang.String statoTicket) {
		this.statoTicket = statoTicket;
	}

	public java.util.ArrayList<HDThreadStep> getThread() {
		return thread;
	}

	public void setThread(java.util.ArrayList<HDThreadStep> thread) {
		this.thread = thread;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public java.lang.String toString() {
		return "HDTicket \n allegato: "+allegato+" allegato_1: "+allegato_1+" azioneRichiesta: "+azioneRichiesta+" contattoNonRiconosciuto: "+contattoNonRiconosciuto+" dataArrivo: "+dataArrivo+" data_scadenza: "+data_scadenza+" diagnosi_libero: "+diagnosi_libero+" diagnosi_std: "+diagnosi_std+" identificativo: "+identificativo+" priorita_ticket: "+priorita_ticket+" rispondere_a: "+rispondere_a+" statoTicket: "+statoTicket;
	}

}