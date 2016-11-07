-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.0.27-community-nt


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO,MYSQL323' */;


--
-- Create schema (null)
--




-- Parte dinamica

ALTER TABLE `Attivita` 
  CHANGE `titolo` `titolo` varchar(200),
  CHANGE `stato_attivita` `stato_attivita` int(10),
  CHANGE `minuti_ultima_fattura` `minuti_ultima_fattura` int(10),
  CHANGE `note` `note` longtext,
    CHANGE `ID_Operatore_attivita_assegnate` `ID_Operatore_attivita_assegnate` int(10) NOT NULL,
INDEX(`ID_Operatore_attivita_assegnate`),
CHANGE `ID_Pratica_attivita` `ID_Pratica_attivita` int(10) NOT NULL,
INDEX(`ID_Pratica_attivita`),
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`ID_Pratica_attivita`, `titolo`)

;

ALTER TABLE `CalendarioFatturazione` 
  CHANGE `data` `data` DATE,
  CHANGE `rata` `rata` int(10) NOT NULL,
  CHANGE `su_rate` `su_rate` int(10) NOT NULL,
  CHANGE `ID_ProForma_entry_calendario` `ID_ProForma_entry_calendario` int(10),
CHANGE `ID_Cliente_calendario_fatturazione` `ID_Cliente_calendario_fatturazione` int(10) NOT NULL,
INDEX(`ID_Cliente_calendario_fatturazione`),
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`data`, `ID_Cliente_calendario_fatturazione`)

;

ALTER TABLE `Cliente` 
  CHANGE `cliente` `cliente` varchar(200) NOT NULL,
  CHANGE `codice_cliente` `codice_cliente` varchar(200) NOT NULL,
  CHANGE `codice_fiscale` `codice_fiscale` varchar(200),
  CHANGE `partita_iva` `partita_iva` varchar(200),
  CHANGE `nickname` `nickname` varchar(200),
   NOT NULL,
  CHANGE `email` `email` varchar(200),
  CHANGE `email2` `email2` varchar(200),
  CHANGE `email3` `email3` varchar(200),
  CHANGE `cellulare` `cellulare` varchar(200),
  CHANGE `telefono` `telefono` varchar(200),
  CHANGE `fax` `fax` varchar(200),
  CHANGE `tipo_sollecito` `tipo_sollecito` int(10),
  CHANGE `stato_cliente` `stato_cliente` int(10) NOT NULL,
  CHANGE `cliente_dal` `cliente_dal` DATE NOT NULL,
  CHANGE `cessata_assistenza_il` `cessata_assistenza_il` DATE,
  CHANGE `indirizzo` `indirizzo` varchar(200) NOT NULL,
  CHANGE `cap` `cap` varchar(200) NOT NULL,
  CHANGE `comune` `comune` varchar(200) NOT NULL,
  CHANGE `recapito_indirizzo` `recapito_indirizzo` varchar(200),
  CHANGE `recapito_cap` `recapito_cap` varchar(200),
  CHANGE `recapito_comune` `recapito_comune` varchar(200),
  CHANGE `legale_rappresentante_cognome` `legale_rappresentante_cognome` varchar(200),
  CHANGE `legale_rappresentante_nome` `legale_rappresentante_nome` varchar(200),
  CHANGE `legale_rappresentante_cf` `legale_rappresentante_cf` varchar(200),
  CHANGE `legale_rappresentante_residenza` `legale_rappresentante_residenza` varchar(200),
  CHANGE `note` `note` longtext,
                  CHANGE `ID_ClienteCandidato_diventa_cliente` `ID_ClienteCandidato_diventa_cliente` int(10),
      CHANGE `allegato` `allegato` int(10),
  CHANGE `allegato_1` `allegato_1` int(10),
  CHANGE `ID_Operatore_responsabile_di` `ID_Operatore_responsabile_di` int(10) NOT NULL,
INDEX(`ID_Operatore_responsabile_di`),
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`codice_fiscale`)

;

ALTER TABLE `ClienteCandidato` 
  CHANGE `cliente` `cliente` varchar(200),
  CHANGE `cliente_dal` `cliente_dal` DATE NOT NULL,
  CHANGE `codice_fiscale` `codice_fiscale` varchar(200) NOT NULL,
  CHANGE `partita_iva` `partita_iva` varchar(200),
  CHANGE `indirizzo` `indirizzo` varchar(200) NOT NULL,
  CHANGE `comune` `comune` varchar(200) NOT NULL,
  CHANGE `cap` `cap` varchar(200) NOT NULL,
  CHANGE `cellulare` `cellulare` varchar(200),
  CHANGE `email` `email` varchar(200),
  CHANGE `fax` `fax` varchar(200),
  CHANGE `preventivo` `preventivo` longtext,
  CHANGE `note` `note` longtext,
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`cliente`)

;

ALTER TABLE `DatiCRM` 
  CHANGE `ID_Cliente_dati_crm` `ID_Cliente_dati_crm` int(10),
  CHANGE `sostituto_imposta` `sostituto_imposta` int(10) NOT NULL,
  CHANGE `immobili` `immobili` int(10) NOT NULL,
  CHANGE `ici` `ici` int(10),
  CHANGE `note_ici` `note_ici` longtext,
  CHANGE `cassetto_fiscale` `cassetto_fiscale` int(10) NOT NULL,
  CHANGE `f24_cumulativo` `f24_cumulativo` int(10) NOT NULL,
  CHANGE `antireciclaggio` `antireciclaggio` int(10) NOT NULL,
  CHANGE `privacy` `privacy` int(10) NOT NULL,
  CHANGE `privacy_acquisita` `privacy_acquisita` int(10),
  CHANGE `conservazione_sostitutiva` `conservazione_sostitutiva` int(10),
  CHANGE `conservazione_sostitutiva_delega_studio` `conservazione_sostitutiva_delega_studio` int(10),
  CHANGE `verifica_validita_firma_digitale` `verifica_validita_firma_digitale` int(10),
  CHANGE `conservazione_sostitutiva_note` `conservazione_sostitutiva_note` varchar(200),
  CHANGE `conservazione_libro_giornale` `conservazione_libro_giornale` int(10) NOT NULL,
  CHANGE `conservazione_libro_giornale_data_versamento_diritti` `conservazione_libro_giornale_data_versamento_diritti` DATE,
  CHANGE `data_invio_impronta_digitale` `data_invio_impronta_digitale` DATE,
  CHANGE `note` `note` longtext,
  CHANGE `allegato_contratto` `allegato_contratto` int(10),
  CHANGE `allegato_delega_f24_cumulativo` `allegato_delega_f24_cumulativo` int(10),
  CHANGE `allegato_delega_f24_compensazioni` `allegato_delega_f24_compensazioni` int(10),
  CHANGE `allegato_delega_cassetto_fiscale` `allegato_delega_cassetto_fiscale` int(10),
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`ID_Cliente_dati_crm`)

;

ALTER TABLE `DatiContabili` 
  CHANGE `tipo_cliente` `tipo_cliente` int(10),
  CHANGE `codice_ateco` `codice_ateco` varchar(200),
  CHANGE `codice_ateco2` `codice_ateco2` varchar(200),
  CHANGE `codice_ateco3` `codice_ateco3` varchar(200),
  CHANGE `teniamo_noi` `teniamo_noi` int(10) NOT NULL,
  CHANGE `regime_contabilita` `regime_contabilita` int(10) NOT NULL,
  CHANGE `esercizio_da` `esercizio_da` varchar(200),
  CHANGE `esercizio_a` `esercizio_a` varchar(200),
  CHANGE `regime_iva` `regime_iva` int(10) NOT NULL,
  CHANGE `liquidazione_iva` `liquidazione_iva` int(10),
  CHANGE `provincia_registro_imprese` `provincia_registro_imprese` int(10),
  CHANGE `iscrizione_registro_imprese` `iscrizione_registro_imprese` varchar(200),
  CHANGE `sezioni_speciali` `sezioni_speciali` int(10),
  CHANGE `unita_locali` `unita_locali` int(10),
  CHANGE `note` `note` longtext,
    CHANGE `obbligo_iscrizione_inail` `obbligo_iscrizione_inail` int(10),
  CHANGE `pat_numero` `pat_numero` varchar(200),
  CHANGE `incarico_a` `incarico_a` int(10),
CHANGE `ID_Cliente_dati_contabili` `ID_Cliente_dati_contabili` int(10) NOT NULL,
INDEX(`ID_Cliente_dati_contabili`),
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`ID_Cliente_dati_contabili`, `tipo_cliente`)

;

ALTER TABLE `DatiFatturazione` 
  CHANGE `ID_Cliente_dati_fatturazione` `ID_Cliente_dati_fatturazione` int(10),
  CHANGE `ID_ClienteCandidato_preventivo_listino` `ID_ClienteCandidato_preventivo_listino` int(10),
  CHANGE `n_rate` `n_rate` int(10) NOT NULL,
  CHANGE `data_prima_rata` `data_prima_rata` DATE,
  DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)
;

ALTER TABLE `DescrizioneDocumento` 
    CHANGE `tipologia_documento` `tipologia_documento` varchar(200),
  CHANGE `visibilita` `visibilita` int(10) NOT NULL,
  CHANGE `azione_conseguente_caricamento` `azione_conseguente_caricamento` int(10),
  CHANGE `sollecito` `sollecito` int(10),
  CHANGE `template_oggetto_invio` `template_oggetto_invio` varchar(200),
  CHANGE `template_testo_invio` `template_testo_invio` longtext,
  CHANGE `template_sms` `template_sms` varchar(134),
  CHANGE `contenuto_azione` `contenuto_azione` longtext,
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`tipologia_documento`)

;

ALTER TABLE `Documento` 
  CHANGE `titolo` `titolo` varchar(200),
  CHANGE `abstract` `abstract` longtext,
  CHANGE `anno_contabile` `anno_contabile` int(10),
  CHANGE `data_riferimento` `data_riferimento` DATE,
  CHANGE `free_tag` `free_tag` varchar(200),
      CHANGE `allegato` `allegato` int(10) NOT NULL,
CHANGE `ID_DescrizioneDocumento_inverse_of_tipologia` `ID_DescrizioneDocumento_inverse_of_tipologia` int(10) NOT NULL,
INDEX(`ID_DescrizioneDocumento_inverse_of_tipologia`),
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`titolo`, `ID_DescrizioneDocumento_inverse_of_tipologia`, `data_riferimento`)

;

ALTER TABLE `DocumentoCliente` 
  CHANGE `data` `data` DATE,
  CHANGE `oggetto` `oggetto` varchar(200),
  CHANGE `allegato` `allegato` int(10) NOT NULL,
  CHANGE `note` `note` longtext,
  CHANGE `archiviato` `archiviato` tinyint(1),
CHANGE `ID_Cliente_documenti_per_lo_studio` `ID_Cliente_documenti_per_lo_studio` int(10) NOT NULL,
INDEX(`ID_Cliente_documenti_per_lo_studio`),
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`ID_Cliente_documenti_per_lo_studio`, `data`, `oggetto`)

;

ALTER TABLE `Fattura` 
  CHANGE `anno_contabile` `anno_contabile` int(10),
  CHANGE `numero` `numero` int(10),
  CHANGE `data` `data` DATE NOT NULL,
  CHANGE `competenza` `competenza` int(10) NOT NULL,
  CHANGE `ID_ProForma_fattura` `ID_ProForma_fattura` int(10),
    CHANGE `spese_anticipate_fattura` `spese_anticipate_fattura` double,
  CHANGE `spese_anticipate_desc` `spese_anticipate_desc` longtext,
  CHANGE `riv_prev` `riv_prev` tinyint(1),
  CHANGE `ra` `ra` tinyint(1),
  CHANGE `data_pagamento` `data_pagamento` DATE,
  CHANGE `note` `note` longtext,
CHANGE `ID_Cliente_fatture` `ID_Cliente_fatture` int(10) NOT NULL,
INDEX(`ID_Cliente_fatture`),
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`anno_contabile`, `numero`)

;

ALTER TABLE `HDChiamataTelefonica` 
  CHANGE `data` `data` DATETIME NOT NULL,
  CHANGE `numero_locale` `numero_locale` varchar(200) NOT NULL,
  CHANGE `numero_remoto` `numero_remoto` varchar(200) NOT NULL,
  CHANGE `stato_chiamata` `stato_chiamata` int(10) NOT NULL,
  CHANGE `durata` `durata` int(10) NOT NULL,
  CHANGE `note` `note` longtext,
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)
;

ALTER TABLE `HDGruppoAssistenza` 
  CHANGE `nome` `nome` varchar(200) NOT NULL,
  CHANGE `alias` `alias` varchar(200),
  CHANGE `email` `email` varchar(200) NOT NULL,
  CHANGE `email_pwd` `email_pwd` varchar(200) NOT NULL,
  CHANGE `port` `port` varchar(200),
  CHANGE `server` `server` varchar(200) NOT NULL,
  CHANGE `note` `note` longtext,
  DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)
;

ALTER TABLE `HDRispostaStandard` 
  CHANGE `diagnosi_std` `diagnosi_std` int(10),
  CHANGE `risposta_std` `risposta_std` longtext NOT NULL,
  CHANGE `allegato` `allegato` int(10),
  CHANGE `allegato_1` `allegato_1` int(10),
  CHANGE `note` `note` longtext,
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)
;

ALTER TABLE `HDThreadStep` 
  CHANGE `th_data` `th_data` DATETIME NOT NULL,
  CHANGE `th_direzione` `th_direzione` int(10) NOT NULL,
  CHANGE `canale` `canale` int(10) NOT NULL,
  CHANGE `th_messaggio` `th_messaggio` longtext NOT NULL,
  CHANGE `ID_HDChiamataTelefonica_ticket` `ID_HDChiamataTelefonica_ticket` int(10),
  CHANGE `ID_LavoroSuAttivita_daTicket` `ID_LavoroSuAttivita_daTicket` int(10),
  CHANGE `note` `note` longtext,
  CHANGE `allegato` `allegato` int(10),
  CHANGE `allegato_1` `allegato_1` int(10),
CHANGE `ID_HDTicket_thread` `ID_HDTicket_thread` int(10) NOT NULL,
INDEX(`ID_HDTicket_thread`),
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)
;

ALTER TABLE `HDTicket` 
  CHANGE `identificativo` `identificativo` varchar(200) NOT NULL,
  CHANGE `dataArrivo` `dataArrivo` DATETIME NOT NULL,
  CHANGE `contattoNonRiconosciuto` `contattoNonRiconosciuto` varchar(200),
  CHANGE `statoTicket` `statoTicket` int(10) NOT NULL,
  CHANGE `priorita_ticket` `priorita_ticket` int(10),
  CHANGE `data_scadenza` `data_scadenza` DATE,
  CHANGE `diagnosi_std` `diagnosi_std` int(10),
  CHANGE `diagnosi_libero` `diagnosi_libero` varchar(200),
  CHANGE `azioneRichiesta` `azioneRichiesta` int(10),
  CHANGE `rispondere_a` `rispondere_a` varchar(200),
    CHANGE `allegato` `allegato` int(10),
  CHANGE `allegato_1` `allegato_1` int(10),
CHANGE `ID_Cliente_contatti_studio` `ID_Cliente_contatti_studio` int(10) NOT NULL,
INDEX(`ID_Cliente_contatti_studio`),
CHANGE `ID_HDGruppoAssistenza_tickets` `ID_HDGruppoAssistenza_tickets` int(10) NOT NULL,
INDEX(`ID_HDGruppoAssistenza_tickets`),
CHANGE `ID_Operatore_ticketAssegnati` `ID_Operatore_ticketAssegnati` int(10) NOT NULL,
INDEX(`ID_Operatore_ticketAssegnati`),
CHANGE `ID_Pratica_ticketAssociati` `ID_Pratica_ticketAssociati` int(10) NOT NULL,
INDEX(`ID_Pratica_ticketAssociati`),
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)
;

ALTER TABLE `IndicizzazioneListino` 
  CHANGE `indice` `indice` double NOT NULL,
  CHANGE `anno_contabile` `anno_contabile` int(10),
  CHANGE `note` `note` longtext,
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`anno_contabile`)

;

ALTER TABLE `ItemListino` 
  CHANGE `tipo` `tipo` int(10) NOT NULL,
  CHANGE `titolo` `titolo` varchar(200),
  CHANGE `titolo_per_fattura` `titolo_per_fattura` varchar(200),
  CHANGE `prezzo` `prezzo` double,
  CHANGE `descrizione` `descrizione` longtext,
CHANGE `ID_DatiFatturazione_listino` `ID_DatiFatturazione_listino` int(10) NOT NULL,
INDEX(`ID_DatiFatturazione_listino`),
CHANGE `ID_Metapratica_in_listini` `ID_Metapratica_in_listini` int(10) NOT NULL,
INDEX(`ID_Metapratica_in_listini`),
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`ID_DatiFatturazione_listino`, `titolo`)

;

ALTER TABLE `LavoroSuAttivita` 
  CHANGE `durata_minuti` `durata_minuti` int(10) NOT NULL,
  CHANGE `data` `data` DATETIME NOT NULL,
  CHANGE `diario` `diario` longtext NOT NULL,
  CHANGE `allegato` `allegato` int(10),
CHANGE `ID_Attivita_sessioni_di_lavoro` `ID_Attivita_sessioni_di_lavoro` int(10) NOT NULL,
INDEX(`ID_Attivita_sessioni_di_lavoro`),
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)
;

ALTER TABLE `Metaattivita` 
  CHANGE `nome` `nome` varchar(200),
CHANGE `ID_Metapratica_lista_attivita` `ID_Metapratica_lista_attivita` int(10) NOT NULL,
INDEX(`ID_Metapratica_lista_attivita`),
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`nome`, `ID_Metapratica_lista_attivita`)

;

ALTER TABLE `Metapratica` 
  CHANGE `titolo` `titolo` varchar(200),
  CHANGE `tipo` `tipo` int(10) NOT NULL,
  CHANGE `titolo_per_fattura` `titolo_per_fattura` varchar(200),
  CHANGE `prezzo` `prezzo` double,
    CHANGE `descrizione` `descrizione` longtext,
    DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`titolo`)

;

ALTER TABLE `Metasollecito` 
  CHANGE `numero_sollecito` `numero_sollecito` int(10),
  CHANGE `oggetto` `oggetto` varchar(200) NOT NULL,
  CHANGE `testo` `testo` longtext NOT NULL,
  CHANGE `note` `note` longtext,
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`numero_sollecito`)

;

ALTER TABLE `NotificaScadenza` 
  CHANGE `data` `data` DATE NOT NULL,
  CHANGE `oggetto_scadenza` `oggetto_scadenza` varchar(200) NOT NULL,
  CHANGE `descrizione_scadenza` `descrizione_scadenza` longtext,
  CHANGE `fatto` `fatto` int(10) NOT NULL,
CHANGE `ID_Cliente_avvisi_scadenze` `ID_Cliente_avvisi_scadenze` int(10) NOT NULL,
INDEX(`ID_Cliente_avvisi_scadenze`),
CHANGE `ID_Operatore_notifiche_scadenze` `ID_Operatore_notifiche_scadenze` int(10) NOT NULL,
INDEX(`ID_Operatore_notifiche_scadenze`),
CHANGE `ID_Scadenza_notifiche_generate` `ID_Scadenza_notifiche_generate` int(10) NOT NULL,
INDEX(`ID_Scadenza_notifiche_generate`),
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)
;

ALTER TABLE `Operatore` 
  CHANGE `nome_e_cognome` `nome_e_cognome` varchar(200),
  CHANGE `email` `email` varchar(200) NOT NULL,
  CHANGE `cellulare` `cellulare` varchar(200),
  CHANGE `costo_orario` `costo_orario` double NOT NULL,
  CHANGE `rivendita_oraria` `rivendita_oraria` double NOT NULL,
          CHANGE `pwd_autorizzativa` `pwd_autorizzativa` varchar(200),
    DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`nome_e_cognome`)

;

ALTER TABLE `Pratica` 
  CHANGE `titolo` `titolo` varchar(200),
  CHANGE `data_chiusura` `data_chiusura` DATE,
  CHANGE `anno_contabile` `anno_contabile` int(10) NOT NULL,
  CHANGE `stato` `stato` int(10) NOT NULL,
  CHANGE `codice` `codice` varchar(200),
  CHANGE `prezzo` `prezzo` double,
  CHANGE `tipo` `tipo` int(10) NOT NULL,
  CHANGE `titolo_per_fattura` `titolo_per_fattura` varchar(200),
  CHANGE `note` `note` longtext,
    CHANGE `ID_Cliente_pratiche` `ID_Cliente_pratiche` int(10) NOT NULL,
INDEX(`ID_Cliente_pratiche`),
CHANGE `ID_Metapratica_pratiche_generate` `ID_Metapratica_pratiche_generate` int(10) NOT NULL,
INDEX(`ID_Metapratica_pratiche_generate`),
CHANGE `ID_Operatore_responsabile_di_pratiche` `ID_Operatore_responsabile_di_pratiche` int(10) NOT NULL,
INDEX(`ID_Operatore_responsabile_di_pratiche`),
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`ID_Cliente_pratiche`, `titolo`)

;

ALTER TABLE `ProForma` 
  CHANGE `anno_contabile` `anno_contabile` int(10),
  CHANGE `numero` `numero` int(10),
  CHANGE `stato_proforma` `stato_proforma` int(10) NOT NULL,
  CHANGE `data` `data` DATE NOT NULL,
  CHANGE `data_pagamento` `data_pagamento` DATE,
    CHANGE `competenza` `competenza` int(10) NOT NULL,
  CHANGE `non_incassabile` `non_incassabile` tinyint(1),
  CHANGE `spese_anticipate_fattura` `spese_anticipate_fattura` double,
  CHANGE `spese_anticipate_desc` `spese_anticipate_desc` longtext,
  CHANGE `riv_prev` `riv_prev` tinyint(1),
  CHANGE `ra` `ra` tinyint(1),
    CHANGE `note` `note` longtext,
CHANGE `ID_Cliente_proforma` `ID_Cliente_proforma` int(10) NOT NULL,
INDEX(`ID_Cliente_proforma`),
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`anno_contabile`, `numero`)

;

ALTER TABLE `ProfiloDinamicoClienti` 
  CHANGE `nome` `nome` varchar(200),
  CHANGE `note` `note` longtext,
  CHANGE `query` `query` longtext NOT NULL,
  DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`nome`)

;

ALTER TABLE `RegimePrevidenziale` 
  CHANGE `regime` `regime` int(10) NOT NULL,
  CHANGE `iscrizione` `iscrizione` int(10) NOT NULL,
CHANGE `ID_DatiContabili_regimi_previdenziali` `ID_DatiContabili_regimi_previdenziali` int(10) NOT NULL,
INDEX(`ID_DatiContabili_regimi_previdenziali`),
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)
;

ALTER TABLE `RevisioneDocumento` 
  CHANGE `allegato` `allegato` int(10),
CHANGE `ID_Documento_revisioni` `ID_Documento_revisioni` int(10) NOT NULL,
INDEX(`ID_Documento_revisioni`),
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`allegato`)

;

ALTER TABLE `Scadenza` 
  CHANGE `ID_Pratica_pratica_scadenza` `ID_Pratica_pratica_scadenza` int(10),
  CHANGE `ID_Attivita_attivita_scadenza` `ID_Attivita_attivita_scadenza` int(10),
    ADD`destinarario_personalizzato` int(10),
  CHANGE `data` `data` DATE NOT NULL,
  CHANGE `preavviso_gg` `preavviso_gg` int(10) NOT NULL,
  CHANGE `oggetto_scadenza` `oggetto_scadenza` varchar(134) NOT NULL,
  CHANGE `descrizione_scadenza` `descrizione_scadenza` longtext,
  CHANGE `forza_sms` `forza_sms` tinyint(1),
  CHANGE `ricorrenza` `ricorrenza` int(10),
  CHANGE `fine_ricorrenza` `fine_ricorrenza` DATE,
  CHANGE `ID_Cliente_scadenze` `ID_Cliente_scadenze` int(10) NOT NULL,
INDEX(`ID_Cliente_scadenze`),
CHANGE `ID_Operatore_op_scadenze` `ID_Operatore_op_scadenze` int(10) NOT NULL,
INDEX(`ID_Operatore_op_scadenze`),
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)
;

ALTER TABLE `Sms` 
  CHANGE `numero_destinatario` `numero_destinatario` varchar(200),
  CHANGE `testo` `testo` varchar(160) NOT NULL,
  CHANGE `qualita` `qualita` int(10) NOT NULL,
  CHANGE `ora_spedizione` `ora_spedizione` DATETIME,
  CHANGE `ora_ricezione` `ora_ricezione` DATETIME,
  CHANGE `note` `note` longtext,
CHANGE `ID_Cliente_sms_inviati` `ID_Cliente_sms_inviati` int(10) NOT NULL,
INDEX(`ID_Cliente_sms_inviati`),
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)
;

ALTER TABLE `SollecitoPagamento` 
  CHANGE `numero_sollecito` `numero_sollecito` int(10),
  CHANGE `oggetto` `oggetto` varchar(200) NOT NULL,
  CHANGE `descrizione` `descrizione` longtext NOT NULL,
  CHANGE `data_spedizione` `data_spedizione` DATE,
  CHANGE `note` `note` longtext,
CHANGE `ID_ProForma_solleciti_pagamento` `ID_ProForma_solleciti_pagamento` int(10) NOT NULL,
INDEX(`ID_ProForma_solleciti_pagamento`),
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)
;

ALTER TABLE `SpesaAnticipata` 
  CHANGE `data` `data` DATE NOT NULL,
  CHANGE `oggetto` `oggetto` varchar(200) NOT NULL,
  CHANGE `importo` `importo` double NOT NULL,
  CHANGE `fatturata` `fatturata` tinyint(1),
  CHANGE `note` `note` longtext,
CHANGE `ID_Attivita_spese_anticipate` `ID_Attivita_spese_anticipate` int(10) NOT NULL,
INDEX(`ID_Attivita_spese_anticipate`),
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)
;

ALTER TABLE `StoriaDocumento` 
  CHANGE `inviato_il` `inviato_il` DATE,
  CHANGE `scaricato_il` `scaricato_il` DATE,
  CHANGE `sollecito_il` `sollecito_il` DATE,
  CHANGE `workflow_completato_il` `workflow_completato_il` DATE,
  CHANGE `testo_sms` `testo_sms` varchar(134),
  CHANGE `oggetto_mail` `oggetto_mail` varchar(200),
  CHANGE `testo_mail` `testo_mail` longtext,
  CHANGE `sollecito` `sollecito` int(10),
  CHANGE `errore` `errore` varchar(200),
CHANGE `ID_Cliente_inverse_of_cliente_doc` `ID_Cliente_inverse_of_cliente_doc` int(10) NOT NULL,
INDEX(`ID_Cliente_inverse_of_cliente_doc`),
CHANGE `ID_Documento_storia_documento` `ID_Documento_storia_documento` int(10) NOT NULL,
INDEX(`ID_Documento_storia_documento`),
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`ID_Documento_storia_documento`)

;

ALTER TABLE `StoricoInvii` 
  CHANGE `data` `data` DATETIME NOT NULL,
  CHANGE `destinatario` `destinatario` varchar(200) NOT NULL,
  CHANGE `tipo_sollecito` `tipo_sollecito` int(10) NOT NULL,
  CHANGE `messaggio` `messaggio` longtext NOT NULL,
  CHANGE `esito` `esito` varchar(200),
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)
;

ALTER TABLE `StoricoModifiche` 
  CHANGE `classe` `classe` varchar(200),
  CHANGE `istanza` `istanza` varchar(200),
  CHANGE `data_modifica` `data_modifica` DATE,
  CHANGE `html_versione` `html_versione` longtext,
  CHANGE `note` `note` longtext,
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`classe`, `istanza`, `data_modifica`)

;

ALTER TABLE `VoceFattura` 
  CHANGE `importo` `importo` double NOT NULL,
  CHANGE `oggetto` `oggetto` varchar(200) NOT NULL,
  CHANGE `iva` `iva` int(10) NOT NULL,
  CHANGE `rif_pratica` `rif_pratica` int(10),
CHANGE `ID_Fattura_voci_fattura` `ID_Fattura_voci_fattura` int(10) NOT NULL,
INDEX(`ID_Fattura_voci_fattura`),
CHANGE `ID_ProForma_voci_proforma` `ID_ProForma_voci_proforma` int(10) NOT NULL,
INDEX(`ID_ProForma_voci_proforma`),
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)
;

CREATE TABLE IF NOT EXISTS `rel_Scadenza_profili_clienti_to_ProfiloDinamicoClienti_inverse` (
  `ID_Scadenza_profili_clienti` int(10) NOT NULL,
  `ID_ProfiloDinamicoClienti_inverse_of_profili_clienti` int(10) NOT NULL,
PRIMARY KEY  (`ID_Scadenza_profili_clienti` ,`ID_ProfiloDinamicoClienti_inverse_of_profili_clienti`),
INDEX (`ID_Scadenza_profili_clienti`),
INDEX (`ID_ProfiloDinamicoClienti_inverse_of_profili_clienti`)
) TYPE=InnoDB;


CREATE TABLE IF NOT EXISTS `rel_Cliente_tipo_cliente` (
`ID_Cliente` int(10) NOT NULL,
`ID_tipo_cliente` int(10) NOT NULL,
PRIMARY KEY  (`ID_Cliente`,`ID_tipo_cliente`)
) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS `rel_Cliente_non_profit` (
`ID_Cliente` int(10) NOT NULL,
`ID_non_profit` int(10) NOT NULL,
PRIMARY KEY  (`ID_Cliente`,`ID_non_profit`)
) ENGINE=InnoDB;


-- Fine parte dinamica 



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
