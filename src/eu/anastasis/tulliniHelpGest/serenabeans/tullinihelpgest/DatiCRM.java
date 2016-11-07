package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

import eu.anastasis.serena.beans.SerenaMarshallable;

public class DatiCRM implements SerenaMarshallable
{








	public static final java.lang.String IMMOBILI__SI = "si";
	public static final java.lang.String IMMOBILI__NO = "no";
	public static final java.lang.String IMMOBILI__LEASING = "leasing";



	public static final java.lang.String SOSTITUTO_IMPOSTA__SI = "si";
	public static final java.lang.String SOSTITUTO_IMPOSTA__NO = "no";



	public static final java.lang.String INSTANCE_NAME = "DatiCRM";
	public static final java.lang.String SLOT_ALLEGATO_CONTRATTO = "allegato_contratto";
	public static final java.lang.String SLOT_ALLEGATO_DELEGA_CASSETTO_FISCALE = "allegato_delega_cassetto_fiscale";
	public static final java.lang.String SLOT_ALLEGATO_DELEGA_F24_COMPENSAZIONI = "allegato_delega_f24_compensazioni";
	public static final java.lang.String SLOT_ALLEGATO_DELEGA_F24_CUMULATIVO = "allegato_delega_f24_cumulativo";
	public static final java.lang.String SLOT_ANTIRECICLAGGIO = "antireciclaggio";
	public static final java.lang.String SLOT_CASSETTO_FISCALE = "cassetto_fiscale";
	public static final java.lang.String SLOT_CONSERVAZIONE_LIBRO_GIORNALE = "conservazione_libro_giornale";
	public static final java.lang.String SLOT_CONSERVAZIONE_LIBRO_GIORNALE_DATA_VERSAMENTO_DIRITTI = "conservazione_libro_giornale_data_versamento_diritti";
	public static final java.lang.String SLOT_CONSERVAZIONE_SOSTITUTIVA = "conservazione_sostitutiva";
	public static final java.lang.String SLOT_CONSERVAZIONE_SOSTITUTIVA_DELEGA_STUDIO = "conservazione_sostitutiva_delega_studio";
	public static final java.lang.String SLOT_CONSERVAZIONE_SOSTITUTIVA_NOTE = "conservazione_sostitutiva_note";
	public static final java.lang.String SLOT_DATA_INVIO_IMPRONTA_DIGITALE = "data_invio_impronta_digitale";
	public static final java.lang.String SLOT_DF_CLIENTE = "df_cliente";
	public static final java.lang.String SLOT_F24_CUMULATIVO = "f24_cumulativo";
	public static final java.lang.String SLOT_ICI = "ici";
	public static final java.lang.String SLOT_IMMOBILI = "immobili";
	public static final java.lang.String SLOT_NOTE = "note";
	public static final java.lang.String SLOT_NOTE_ICI = "note_ici";
	public static final java.lang.String SLOT_PRIVACY = "privacy";
	public static final java.lang.String SLOT_PRIVACY_ACQUISITA = "privacy_acquisita";
	public static final java.lang.String SLOT_SOSTITUTO_IMPOSTA = "sostituto_imposta";
	public static final java.lang.String SLOT_VERIFICA_VALIDITA_FIRMA_DIGITALE = "verifica_validita_firma_digitale";

	protected java.lang.String allegato_contratto;
	protected java.lang.String allegato_delega_cassetto_fiscale;
	protected java.lang.String allegato_delega_f24_compensazioni;
	protected java.lang.String allegato_delega_f24_cumulativo;
	protected java.lang.String antireciclaggio;
	protected java.lang.String cassetto_fiscale;
	protected java.lang.String conservazione_libro_giornale;
	protected java.util.Date conservazione_libro_giornale_data_versamento_diritti;
	protected java.lang.String conservazione_sostitutiva;
	protected java.lang.String conservazione_sostitutiva_delega_studio;
	protected java.lang.String conservazione_sostitutiva_note;
	protected java.util.Date data_invio_impronta_digitale;
	protected Cliente df_cliente;
	protected java.lang.String f24_cumulativo;
	protected java.lang.String ici;
	protected java.lang.String immobili;
	protected java.lang.String note;
	protected java.lang.String note_ici;
	protected java.lang.String privacy;
	protected java.lang.String privacy_acquisita;
	protected java.lang.String sostituto_imposta;
	protected java.lang.String verifica_validita_firma_digitale;
	protected String id;

	public DatiCRM(){}

	public java.lang.String getAllegato_contratto() {
		return allegato_contratto;
	}

	public void setAllegato_contratto(java.lang.String allegato_contratto) {
		this.allegato_contratto = allegato_contratto;
	}

	public java.lang.String getAllegato_delega_cassetto_fiscale() {
		return allegato_delega_cassetto_fiscale;
	}

	public void setAllegato_delega_cassetto_fiscale(java.lang.String allegato_delega_cassetto_fiscale) {
		this.allegato_delega_cassetto_fiscale = allegato_delega_cassetto_fiscale;
	}

	public java.lang.String getAllegato_delega_f24_compensazioni() {
		return allegato_delega_f24_compensazioni;
	}

	public void setAllegato_delega_f24_compensazioni(java.lang.String allegato_delega_f24_compensazioni) {
		this.allegato_delega_f24_compensazioni = allegato_delega_f24_compensazioni;
	}

	public java.lang.String getAllegato_delega_f24_cumulativo() {
		return allegato_delega_f24_cumulativo;
	}

	public void setAllegato_delega_f24_cumulativo(java.lang.String allegato_delega_f24_cumulativo) {
		this.allegato_delega_f24_cumulativo = allegato_delega_f24_cumulativo;
	}

	public java.lang.String getAntireciclaggio() {
		return antireciclaggio;
	}

	public void setAntireciclaggio(java.lang.String antireciclaggio) {
		this.antireciclaggio = antireciclaggio;
	}

	public java.lang.String getCassetto_fiscale() {
		return cassetto_fiscale;
	}

	public void setCassetto_fiscale(java.lang.String cassetto_fiscale) {
		this.cassetto_fiscale = cassetto_fiscale;
	}

	public java.lang.String getConservazione_libro_giornale() {
		return conservazione_libro_giornale;
	}

	public void setConservazione_libro_giornale(java.lang.String conservazione_libro_giornale) {
		this.conservazione_libro_giornale = conservazione_libro_giornale;
	}

	public java.util.Date getConservazione_libro_giornale_data_versamento_diritti() {
		return conservazione_libro_giornale_data_versamento_diritti;
	}

	public void setConservazione_libro_giornale_data_versamento_diritti(java.util.Date conservazione_libro_giornale_data_versamento_diritti) {
		this.conservazione_libro_giornale_data_versamento_diritti = conservazione_libro_giornale_data_versamento_diritti;
	}

	public java.lang.String getConservazione_sostitutiva() {
		return conservazione_sostitutiva;
	}

	public void setConservazione_sostitutiva(java.lang.String conservazione_sostitutiva) {
		this.conservazione_sostitutiva = conservazione_sostitutiva;
	}

	public java.lang.String getConservazione_sostitutiva_delega_studio() {
		return conservazione_sostitutiva_delega_studio;
	}

	public void setConservazione_sostitutiva_delega_studio(java.lang.String conservazione_sostitutiva_delega_studio) {
		this.conservazione_sostitutiva_delega_studio = conservazione_sostitutiva_delega_studio;
	}

	public java.lang.String getConservazione_sostitutiva_note() {
		return conservazione_sostitutiva_note;
	}

	public void setConservazione_sostitutiva_note(java.lang.String conservazione_sostitutiva_note) {
		this.conservazione_sostitutiva_note = conservazione_sostitutiva_note;
	}

	public java.util.Date getData_invio_impronta_digitale() {
		return data_invio_impronta_digitale;
	}

	public void setData_invio_impronta_digitale(java.util.Date data_invio_impronta_digitale) {
		this.data_invio_impronta_digitale = data_invio_impronta_digitale;
	}

	public Cliente getDf_cliente() {
		return df_cliente;
	}

	public void setDf_cliente(Cliente df_cliente) {
		this.df_cliente = df_cliente;
	}

	public java.lang.String getF24_cumulativo() {
		return f24_cumulativo;
	}

	public void setF24_cumulativo(java.lang.String f24_cumulativo) {
		this.f24_cumulativo = f24_cumulativo;
	}

	public java.lang.String getIci() {
		return ici;
	}

	public void setIci(java.lang.String ici) {
		this.ici = ici;
	}

	public java.lang.String getImmobili() {
		return immobili;
	}

	public void setImmobili(java.lang.String immobili) {
		this.immobili = immobili;
	}

	public java.lang.String getNote() {
		return note;
	}

	public void setNote(java.lang.String note) {
		this.note = note;
	}

	public java.lang.String getNote_ici() {
		return note_ici;
	}

	public void setNote_ici(java.lang.String note_ici) {
		this.note_ici = note_ici;
	}

	public java.lang.String getPrivacy() {
		return privacy;
	}

	public void setPrivacy(java.lang.String privacy) {
		this.privacy = privacy;
	}

	public java.lang.String getPrivacy_acquisita() {
		return privacy_acquisita;
	}

	public void setPrivacy_acquisita(java.lang.String privacy_acquisita) {
		this.privacy_acquisita = privacy_acquisita;
	}

	public java.lang.String getSostituto_imposta() {
		return sostituto_imposta;
	}

	public void setSostituto_imposta(java.lang.String sostituto_imposta) {
		this.sostituto_imposta = sostituto_imposta;
	}

	public java.lang.String getVerifica_validita_firma_digitale() {
		return verifica_validita_firma_digitale;
	}

	public void setVerifica_validita_firma_digitale(java.lang.String verifica_validita_firma_digitale) {
		this.verifica_validita_firma_digitale = verifica_validita_firma_digitale;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public java.lang.String toString() {
		return "DatiCRM \n allegato_contratto: "+allegato_contratto+" allegato_delega_cassetto_fiscale: "+allegato_delega_cassetto_fiscale+" allegato_delega_f24_compensazioni: "+allegato_delega_f24_compensazioni+" allegato_delega_f24_cumulativo: "+allegato_delega_f24_cumulativo+" antireciclaggio: "+antireciclaggio+" cassetto_fiscale: "+cassetto_fiscale+" conservazione_libro_giornale: "+conservazione_libro_giornale+" conservazione_libro_giornale_data_versamento_diritti: "+conservazione_libro_giornale_data_versamento_diritti+" conservazione_sostitutiva: "+conservazione_sostitutiva+" conservazione_sostitutiva_delega_studio: "+conservazione_sostitutiva_delega_studio+" conservazione_sostitutiva_note: "+conservazione_sostitutiva_note+" data_invio_impronta_digitale: "+data_invio_impronta_digitale+" f24_cumulativo: "+f24_cumulativo+" ici: "+ici+" immobili: "+immobili+" note: "+note+" note_ici: "+note_ici+" privacy: "+privacy+" privacy_acquisita: "+privacy_acquisita+" sostituto_imposta: "+sostituto_imposta+" verifica_validita_firma_digitale: "+verifica_validita_firma_digitale;
	}

}