package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

import eu.anastasis.serena.beans.SerenaMarshallable;

public class DatiContabili implements SerenaMarshallable
{

	public static final java.lang.String INCARICO_A__STUDIO = "Studio";
	public static final java.lang.String INCARICO_A__CDL = "CDL";

	public static final java.lang.String LIQUIDAZIONE_IVA__ANNUALE = "annuale";



	public static final java.lang.String REGIME_CONTABILITA__ESONERO_AGR = "ESONERO AGR";

	public static final java.lang.String REGIME_IVA__398_91 = "398 91";

	public static final java.lang.String SEZIONI_SPECIALI__PICCOLO_IMPRENDITORE = "piccolo imprenditore";


	public static final java.lang.String TIPO_CLIENTE__PF = "PF";
	public static final java.lang.String TIPO_CLIENTE__SP = "SP";
	public static final java.lang.String TIPO_CLIENTE__P = "P";
	public static final java.lang.String TIPO_CLIENTE__I = "I";
	public static final java.lang.String TIPO_CLIENTE__AP = "AP";
	public static final java.lang.String TIPO_CLIENTE__NP = "NP";
	public static final java.lang.String TIPO_CLIENTE__SC = "SC";
	public static final java.lang.String TIPO_CLIENTE__CP = "CP";



	public static final java.lang.String INSTANCE_NAME = "DatiContabili";
	public static final java.lang.String SLOT_CODICE_ATECO = "codice_ateco";
	public static final java.lang.String SLOT_CODICE_ATECO2 = "codice_ateco2";
	public static final java.lang.String SLOT_CODICE_ATECO3 = "codice_ateco3";
	public static final java.lang.String SLOT_ESERCIZIO_A = "esercizio_a";
	public static final java.lang.String SLOT_ESERCIZIO_DA = "esercizio_da";
	public static final java.lang.String SLOT_INCARICO_A = "incarico_a";
	public static final java.lang.String SLOT_INVERSE_OF_DATI_CONTABILI = "inverse_of_dati_contabili";
	public static final java.lang.String SLOT_ISCRIZIONE_REGISTRO_IMPRESE = "iscrizione_registro_imprese";
	public static final java.lang.String SLOT_LIQUIDAZIONE_IVA = "liquidazione_iva";
	public static final java.lang.String SLOT_NOTE = "note";
	public static final java.lang.String SLOT_OBBLIGO_ISCRIZIONE_INAIL = "obbligo_iscrizione_inail";
	public static final java.lang.String SLOT_PAT_NUMERO = "pat_numero";
	public static final java.lang.String SLOT_PROVINCIA_REGISTRO_IMPRESE = "provincia_registro_imprese";
	public static final java.lang.String SLOT_REGIME_CONTABILITA = "regime_contabilita";
	public static final java.lang.String SLOT_REGIME_IVA = "regime_iva";
	public static final java.lang.String SLOT_REGIMI_PREVIDENZIALI = "regimi_previdenziali";
	public static final java.lang.String SLOT_SEZ_CONTABILITA = "sez_contabilita";
	public static final java.lang.String SLOT_SEZ_INAIL = "sez_inail";
	public static final java.lang.String SLOT_SEZ_PREVIDENZA = "sez_previdenza";
	public static final java.lang.String SLOT_SEZ_REGISTRO_IMPRESE = "sez_registro_imprese";
	public static final java.lang.String SLOT_SEZIONI_SPECIALI = "sezioni_speciali";
	public static final java.lang.String SLOT_TENIAMO_NOI = "teniamo_noi";
	public static final java.lang.String SLOT_TIPO_CLIENTE = "tipo_cliente";
	public static final java.lang.String SLOT_UNITA_LOCALI = "unita_locali";

	protected java.lang.String codice_ateco;
	protected java.lang.String codice_ateco2;
	protected java.lang.String codice_ateco3;
	protected java.util.Date esercizio_a;
	protected java.util.Date esercizio_da;
	protected java.lang.String incarico_a;
	protected Cliente inverse_of_dati_contabili;
	protected java.lang.String iscrizione_registro_imprese;
	protected java.lang.String liquidazione_iva;
	protected java.lang.String note;
	protected java.lang.String obbligo_iscrizione_inail;
	protected java.lang.String pat_numero;
	protected java.lang.String provincia_registro_imprese;
	protected java.lang.String regime_contabilita;
	protected java.lang.String regime_iva;
	protected java.util.ArrayList<RegimePrevidenziale> regimi_previdenziali;
	protected java.lang.String sez_contabilita;
	protected java.lang.String sez_inail;
	protected java.lang.String sez_previdenza;
	protected java.lang.String sez_registro_imprese;
	protected java.lang.String sezioni_speciali;
	protected java.lang.String teniamo_noi;
	protected java.lang.String tipo_cliente;
	protected java.lang.String unita_locali;
	protected String id;

	public DatiContabili(){}

	public java.lang.String getCodice_ateco() {
		return codice_ateco;
	}

	public void setCodice_ateco(java.lang.String codice_ateco) {
		this.codice_ateco = codice_ateco;
	}

	public java.lang.String getCodice_ateco2() {
		return codice_ateco2;
	}

	public void setCodice_ateco2(java.lang.String codice_ateco2) {
		this.codice_ateco2 = codice_ateco2;
	}

	public java.lang.String getCodice_ateco3() {
		return codice_ateco3;
	}

	public void setCodice_ateco3(java.lang.String codice_ateco3) {
		this.codice_ateco3 = codice_ateco3;
	}

	public java.util.Date getEsercizio_a() {
		return esercizio_a;
	}

	public void setEsercizio_a(java.util.Date esercizio_a) {
		this.esercizio_a = esercizio_a;
	}

	public java.util.Date getEsercizio_da() {
		return esercizio_da;
	}

	public void setEsercizio_da(java.util.Date esercizio_da) {
		this.esercizio_da = esercizio_da;
	}

	public java.lang.String getIncarico_a() {
		return incarico_a;
	}

	public void setIncarico_a(java.lang.String incarico_a) {
		this.incarico_a = incarico_a;
	}

	public Cliente getInverse_of_dati_contabili() {
		return inverse_of_dati_contabili;
	}

	public void setInverse_of_dati_contabili(Cliente inverse_of_dati_contabili) {
		this.inverse_of_dati_contabili = inverse_of_dati_contabili;
	}

	public java.lang.String getIscrizione_registro_imprese() {
		return iscrizione_registro_imprese;
	}

	public void setIscrizione_registro_imprese(java.lang.String iscrizione_registro_imprese) {
		this.iscrizione_registro_imprese = iscrizione_registro_imprese;
	}

	public java.lang.String getLiquidazione_iva() {
		return liquidazione_iva;
	}

	public void setLiquidazione_iva(java.lang.String liquidazione_iva) {
		this.liquidazione_iva = liquidazione_iva;
	}

	public java.lang.String getNote() {
		return note;
	}

	public void setNote(java.lang.String note) {
		this.note = note;
	}

	public java.lang.String getObbligo_iscrizione_inail() {
		return obbligo_iscrizione_inail;
	}

	public void setObbligo_iscrizione_inail(java.lang.String obbligo_iscrizione_inail) {
		this.obbligo_iscrizione_inail = obbligo_iscrizione_inail;
	}

	public java.lang.String getPat_numero() {
		return pat_numero;
	}

	public void setPat_numero(java.lang.String pat_numero) {
		this.pat_numero = pat_numero;
	}

	public java.lang.String getProvincia_registro_imprese() {
		return provincia_registro_imprese;
	}

	public void setProvincia_registro_imprese(java.lang.String provincia_registro_imprese) {
		this.provincia_registro_imprese = provincia_registro_imprese;
	}

	public java.lang.String getRegime_contabilita() {
		return regime_contabilita;
	}

	public void setRegime_contabilita(java.lang.String regime_contabilita) {
		this.regime_contabilita = regime_contabilita;
	}

	public java.lang.String getRegime_iva() {
		return regime_iva;
	}

	public void setRegime_iva(java.lang.String regime_iva) {
		this.regime_iva = regime_iva;
	}

	public java.util.ArrayList<RegimePrevidenziale> getRegimi_previdenziali() {
		return regimi_previdenziali;
	}

	public void setRegimi_previdenziali(java.util.ArrayList<RegimePrevidenziale> regimi_previdenziali) {
		this.regimi_previdenziali = regimi_previdenziali;
	}

	public java.lang.String getSez_contabilita() {
		return sez_contabilita;
	}

	public void setSez_contabilita(java.lang.String sez_contabilita) {
		this.sez_contabilita = sez_contabilita;
	}

	public java.lang.String getSez_inail() {
		return sez_inail;
	}

	public void setSez_inail(java.lang.String sez_inail) {
		this.sez_inail = sez_inail;
	}

	public java.lang.String getSez_previdenza() {
		return sez_previdenza;
	}

	public void setSez_previdenza(java.lang.String sez_previdenza) {
		this.sez_previdenza = sez_previdenza;
	}

	public java.lang.String getSez_registro_imprese() {
		return sez_registro_imprese;
	}

	public void setSez_registro_imprese(java.lang.String sez_registro_imprese) {
		this.sez_registro_imprese = sez_registro_imprese;
	}

	public java.lang.String getSezioni_speciali() {
		return sezioni_speciali;
	}

	public void setSezioni_speciali(java.lang.String sezioni_speciali) {
		this.sezioni_speciali = sezioni_speciali;
	}

	public java.lang.String getTeniamo_noi() {
		return teniamo_noi;
	}

	public void setTeniamo_noi(java.lang.String teniamo_noi) {
		this.teniamo_noi = teniamo_noi;
	}

	public java.lang.String getTipo_cliente() {
		return tipo_cliente;
	}

	public void setTipo_cliente(java.lang.String tipo_cliente) {
		this.tipo_cliente = tipo_cliente;
	}

	public java.lang.String getUnita_locali() {
		return unita_locali;
	}

	public void setUnita_locali(java.lang.String unita_locali) {
		this.unita_locali = unita_locali;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public java.lang.String toString() {
		return "DatiContabili \n codice_ateco: "+codice_ateco+" codice_ateco2: "+codice_ateco2+" codice_ateco3: "+codice_ateco3+" esercizio_a: "+esercizio_a+" esercizio_da: "+esercizio_da+" incarico_a: "+incarico_a+" iscrizione_registro_imprese: "+iscrizione_registro_imprese+" liquidazione_iva: "+liquidazione_iva+" note: "+note+" obbligo_iscrizione_inail: "+obbligo_iscrizione_inail+" pat_numero: "+pat_numero+" provincia_registro_imprese: "+provincia_registro_imprese+" regime_contabilita: "+regime_contabilita+" regime_iva: "+regime_iva+" sez_contabilita: "+sez_contabilita+" sez_inail: "+sez_inail+" sez_previdenza: "+sez_previdenza+" sez_registro_imprese: "+sez_registro_imprese+" sezioni_speciali: "+sezioni_speciali+" teniamo_noi: "+teniamo_noi+" tipo_cliente: "+tipo_cliente+" unita_locali: "+unita_locali;
	}

}