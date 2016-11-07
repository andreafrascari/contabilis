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

CREATE TABLE `Attivita` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `titolo` varchar(200),
  `stato_attivita` int(10),
  `minuti_ultima_fattura` int(10),
  `note` longtext,
      `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
`ID_Operatore_attivita_assegnate` int(10) NOT NULL,
INDEX(`ID_Operatore_attivita_assegnate`),
`ID_Pratica_attivita` int(10) NOT NULL,
INDEX(`ID_Pratica_attivita`),
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`ID_Pratica_attivita`, `titolo`)
) ENGINE=InnoDB;

CREATE TABLE `CalendarioFatturazione` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `data` DATE,
  `rata` int(10) NOT NULL,
  `su_rate` int(10) NOT NULL,
  `ID_ProForma_entry_calendario` int(10),
  `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
`ID_Cliente_calendario_fatturazione` int(10) NOT NULL,
INDEX(`ID_Cliente_calendario_fatturazione`),
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`data`, `ID_Cliente_calendario_fatturazione`)
) ENGINE=InnoDB;

CREATE TABLE `Cliente` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `cliente` varchar(200) NOT NULL,
  `codice_cliente` varchar(200) NOT NULL,
  `codice_fiscale` varchar(200),
  `partita_iva` varchar(200),
  `nickname` varchar(200),
   NOT NULL,
  `email` varchar(200),
  `email2` varchar(200),
  `email3` varchar(200),
  `cellulare` varchar(200),
  `telefono` varchar(200),
  `fax` varchar(200),
  `tipo_sollecito` int(10),
  `stato_cliente` int(10) NOT NULL,
  `cliente_dal` DATE NOT NULL,
  `cessata_assistenza_il` DATE,
  `indirizzo` varchar(200) NOT NULL,
  `cap` varchar(200) NOT NULL,
  `comune` varchar(200) NOT NULL,
  `recapito_indirizzo` varchar(200),
  `recapito_cap` varchar(200),
  `recapito_comune` varchar(200),
  `legale_rappresentante_cognome` varchar(200),
  `legale_rappresentante_nome` varchar(200),
  `legale_rappresentante_cf` varchar(200),
  `legale_rappresentante_residenza` varchar(200),
  `note` longtext,
                `ID_ClienteCandidato_diventa_cliente` int(10),
      `allegato` int(10),
  `allegato_1` int(10),
    `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
`ID_Operatore_responsabile_di` int(10) NOT NULL,
INDEX(`ID_Operatore_responsabile_di`),
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`codice_fiscale`)
) ENGINE=InnoDB;

CREATE TABLE `ClienteCandidato` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `cliente` varchar(200),
  `cliente_dal` DATE NOT NULL,
  `codice_fiscale` varchar(200) NOT NULL,
  `partita_iva` varchar(200),
  `indirizzo` varchar(200) NOT NULL,
  `comune` varchar(200) NOT NULL,
  `cap` varchar(200) NOT NULL,
  `cellulare` varchar(200),
  `email` varchar(200),
  `fax` varchar(200),
  `preventivo` longtext,
  `note` longtext,
  `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`cliente`)
) ENGINE=InnoDB;

CREATE TABLE `DatiCRM` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `ID_Cliente_dati_crm` int(10),
  `sostituto_imposta` int(10) NOT NULL,
  `immobili` int(10) NOT NULL,
  `ici` int(10),
  `note_ici` longtext,
  `cassetto_fiscale` int(10) NOT NULL,
  `f24_cumulativo` int(10) NOT NULL,
  `antireciclaggio` int(10) NOT NULL,
  `privacy` int(10) NOT NULL,
  `privacy_acquisita` int(10),
  `conservazione_sostitutiva` int(10),
  `conservazione_sostitutiva_delega_studio` int(10),
  `verifica_validita_firma_digitale` int(10),
  `conservazione_sostitutiva_note` varchar(200),
  `conservazione_libro_giornale` int(10) NOT NULL,
  `conservazione_libro_giornale_data_versamento_diritti` DATE,
  `data_invio_impronta_digitale` DATE,
  `note` longtext,
  `allegato_contratto` int(10),
  `allegato_delega_f24_cumulativo` int(10),
  `allegato_delega_f24_compensazioni` int(10),
  `allegato_delega_cassetto_fiscale` int(10),
  `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`ID_Cliente_dati_crm`)
) ENGINE=InnoDB;

CREATE TABLE `DatiContabili` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `tipo_cliente` int(10),
  `codice_ateco` varchar(200),
  `codice_ateco2` varchar(200),
  `codice_ateco3` varchar(200),
  `teniamo_noi` int(10) NOT NULL,
  `regime_contabilita` int(10) NOT NULL,
  `esercizio_da` varchar(200),
  `esercizio_a` varchar(200),
  `regime_iva` int(10) NOT NULL,
  `liquidazione_iva` int(10),
  `provincia_registro_imprese` int(10),
  `iscrizione_registro_imprese` varchar(200),
  `sezioni_speciali` int(10),
  `unita_locali` int(10),
  `note` longtext,
    `obbligo_iscrizione_inail` int(10),
  `pat_numero` varchar(200),
  `incarico_a` int(10),
  `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
`ID_Cliente_dati_contabili` int(10) NOT NULL,
INDEX(`ID_Cliente_dati_contabili`),
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`ID_Cliente_dati_contabili`, `tipo_cliente`)
) ENGINE=InnoDB;

CREATE TABLE `DatiFatturazione` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `ID_Cliente_dati_fatturazione` int(10),
  `ID_ClienteCandidato_preventivo_listino` int(10),
  `n_rate` int(10) NOT NULL,
  `data_prima_rata` DATE,
    `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)) ENGINE=InnoDB;

CREATE TABLE `DescrizioneDocumento` (
  `ID` int(10) unsigned NOT NULL auto_increment,
    `tipologia_documento` varchar(200),
  `visibilita` int(10) NOT NULL,
  `azione_conseguente_caricamento` int(10),
  `sollecito` int(10),
  `template_oggetto_invio` varchar(200),
  `template_testo_invio` longtext,
  `template_sms` varchar(134),
  `contenuto_azione` longtext,
  `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`tipologia_documento`)
) ENGINE=InnoDB;

CREATE TABLE `Documento` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `titolo` varchar(200),
  `abstract` longtext,
  `anno_contabile` int(10),
  `data_riferimento` DATE,
  `free_tag` varchar(200),
      `allegato` int(10) NOT NULL,
  `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
`ID_DescrizioneDocumento_inverse_of_tipologia` int(10) NOT NULL,
INDEX(`ID_DescrizioneDocumento_inverse_of_tipologia`),
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`titolo`, `ID_DescrizioneDocumento_inverse_of_tipologia`, `data_riferimento`)
) ENGINE=InnoDB;

CREATE TABLE `DocumentoCliente` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `data` DATE,
  `oggetto` varchar(200),
  `allegato` int(10) NOT NULL,
  `note` longtext,
  `archiviato` tinyint(1),
  `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
`ID_Cliente_documenti_per_lo_studio` int(10) NOT NULL,
INDEX(`ID_Cliente_documenti_per_lo_studio`),
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`ID_Cliente_documenti_per_lo_studio`, `data`, `oggetto`)
) ENGINE=InnoDB;

CREATE TABLE `Fattura` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `anno_contabile` int(10),
  `numero` int(10),
  `data` DATE NOT NULL,
  `competenza` int(10) NOT NULL,
  `ID_ProForma_fattura` int(10),
    `spese_anticipate_fattura` double,
  `spese_anticipate_desc` longtext,
  `riv_prev` tinyint(1),
  `ra` tinyint(1),
  `data_pagamento` DATE,
  `note` longtext,
  `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
`ID_Cliente_fatture` int(10) NOT NULL,
INDEX(`ID_Cliente_fatture`),
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`anno_contabile`, `numero`)
) ENGINE=InnoDB;

CREATE TABLE `HDChiamataTelefonica` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `data` DATETIME NOT NULL,
  `numero_locale` varchar(200) NOT NULL,
  `numero_remoto` varchar(200) NOT NULL,
  `stato_chiamata` int(10) NOT NULL,
  `durata` int(10) NOT NULL,
  `note` longtext,
  `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)) ENGINE=InnoDB;

CREATE TABLE `HDGruppoAssistenza` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `nome` varchar(200) NOT NULL,
  `alias` varchar(200),
  `email` varchar(200) NOT NULL,
  `email_pwd` varchar(200) NOT NULL,
  `port` varchar(200),
  `server` varchar(200) NOT NULL,
  `note` longtext,
    `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)) ENGINE=InnoDB;

CREATE TABLE `HDRispostaStandard` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `diagnosi_std` int(10),
  `risposta_std` longtext NOT NULL,
  `allegato` int(10),
  `allegato_1` int(10),
  `note` longtext,
  `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)) ENGINE=InnoDB;

CREATE TABLE `HDThreadStep` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `th_data` DATETIME NOT NULL,
  `th_direzione` int(10) NOT NULL,
  `canale` int(10) NOT NULL,
  `th_messaggio` longtext NOT NULL,
  `ID_HDChiamataTelefonica_ticket` int(10),
  `ID_LavoroSuAttivita_daTicket` int(10),
  `note` longtext,
  `allegato` int(10),
  `allegato_1` int(10),
  `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
`ID_HDTicket_thread` int(10) NOT NULL,
INDEX(`ID_HDTicket_thread`),
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)) ENGINE=InnoDB;

CREATE TABLE `HDTicket` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `identificativo` varchar(200) NOT NULL,
  `dataArrivo` DATETIME NOT NULL,
  `contattoNonRiconosciuto` varchar(200),
  `statoTicket` int(10) NOT NULL,
  `priorita_ticket` int(10),
  `data_scadenza` DATE,
  `diagnosi_std` int(10),
  `diagnosi_libero` varchar(200),
  `azioneRichiesta` int(10),
  `rispondere_a` varchar(200),
    `allegato` int(10),
  `allegato_1` int(10),
  `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
`ID_Cliente_contatti_studio` int(10) NOT NULL,
INDEX(`ID_Cliente_contatti_studio`),
`ID_HDGruppoAssistenza_tickets` int(10) NOT NULL,
INDEX(`ID_HDGruppoAssistenza_tickets`),
`ID_Operatore_ticketAssegnati` int(10) NOT NULL,
INDEX(`ID_Operatore_ticketAssegnati`),
`ID_Pratica_ticketAssociati` int(10) NOT NULL,
INDEX(`ID_Pratica_ticketAssociati`),
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)) ENGINE=InnoDB;

CREATE TABLE `IndicizzazioneListino` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `indice` double NOT NULL,
  `anno_contabile` int(10),
  `note` longtext,
  `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`anno_contabile`)
) ENGINE=InnoDB;

CREATE TABLE `ItemListino` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `tipo` int(10) NOT NULL,
  `titolo` varchar(200),
  `titolo_per_fattura` varchar(200),
  `prezzo` double,
  `descrizione` longtext,
  `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
`ID_DatiFatturazione_listino` int(10) NOT NULL,
INDEX(`ID_DatiFatturazione_listino`),
`ID_Metapratica_in_listini` int(10) NOT NULL,
INDEX(`ID_Metapratica_in_listini`),
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`ID_DatiFatturazione_listino`, `titolo`)
) ENGINE=InnoDB;

CREATE TABLE `LavoroSuAttivita` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `durata_minuti` int(10) NOT NULL,
  `data` DATETIME NOT NULL,
  `diario` longtext NOT NULL,
  `allegato` int(10),
  `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
`ID_Attivita_sessioni_di_lavoro` int(10) NOT NULL,
INDEX(`ID_Attivita_sessioni_di_lavoro`),
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)) ENGINE=InnoDB;

CREATE TABLE `Metaattivita` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `nome` varchar(200),
  `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
`ID_Metapratica_lista_attivita` int(10) NOT NULL,
INDEX(`ID_Metapratica_lista_attivita`),
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`nome`, `ID_Metapratica_lista_attivita`)
) ENGINE=InnoDB;

CREATE TABLE `Metapratica` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `titolo` varchar(200),
  `tipo` int(10) NOT NULL,
  `titolo_per_fattura` varchar(200),
  `prezzo` double,
    `descrizione` longtext,
      `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`titolo`)
) ENGINE=InnoDB;

CREATE TABLE `Metasollecito` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `numero_sollecito` int(10),
  `oggetto` varchar(200) NOT NULL,
  `testo` longtext NOT NULL,
  `note` longtext,
  `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`numero_sollecito`)
) ENGINE=InnoDB;

CREATE TABLE `NotificaScadenza` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `data` DATE NOT NULL,
  `oggetto_scadenza` varchar(200) NOT NULL,
  `descrizione_scadenza` longtext,
  `fatto` int(10) NOT NULL,
  `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
`ID_Cliente_avvisi_scadenze` int(10) NOT NULL,
INDEX(`ID_Cliente_avvisi_scadenze`),
`ID_Operatore_notifiche_scadenze` int(10) NOT NULL,
INDEX(`ID_Operatore_notifiche_scadenze`),
`ID_Scadenza_notifiche_generate` int(10) NOT NULL,
INDEX(`ID_Scadenza_notifiche_generate`),
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)) ENGINE=InnoDB;

CREATE TABLE `Operatore` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `nome_e_cognome` varchar(200),
  `email` varchar(200) NOT NULL,
  `cellulare` varchar(200),
  `costo_orario` double NOT NULL,
  `rivendita_oraria` double NOT NULL,
          `pwd_autorizzativa` varchar(200),
      `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`nome_e_cognome`)
) ENGINE=InnoDB;

CREATE TABLE `Pratica` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `titolo` varchar(200),
  `data_chiusura` DATE,
  `anno_contabile` int(10) NOT NULL,
  `stato` int(10) NOT NULL,
  `codice` varchar(200),
  `prezzo` double,
  `tipo` int(10) NOT NULL,
  `titolo_per_fattura` varchar(200),
  `note` longtext,
    `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
`ID_Cliente_pratiche` int(10) NOT NULL,
INDEX(`ID_Cliente_pratiche`),
`ID_Metapratica_pratiche_generate` int(10) NOT NULL,
INDEX(`ID_Metapratica_pratiche_generate`),
`ID_Operatore_responsabile_di_pratiche` int(10) NOT NULL,
INDEX(`ID_Operatore_responsabile_di_pratiche`),
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`ID_Cliente_pratiche`, `titolo`)
) ENGINE=InnoDB;

CREATE TABLE `ProForma` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `anno_contabile` int(10),
  `numero` int(10),
  `stato_proforma` int(10) NOT NULL,
  `data` DATE NOT NULL,
  `data_pagamento` DATE,
    `competenza` int(10) NOT NULL,
  `non_incassabile` tinyint(1),
  `spese_anticipate_fattura` double,
  `spese_anticipate_desc` longtext,
  `riv_prev` tinyint(1),
  `ra` tinyint(1),
    `note` longtext,
  `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
`ID_Cliente_proforma` int(10) NOT NULL,
INDEX(`ID_Cliente_proforma`),
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`anno_contabile`, `numero`)
) ENGINE=InnoDB;

CREATE TABLE `ProfiloDinamicoClienti` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `nome` varchar(200),
  `note` longtext,
  `query` longtext NOT NULL,
    `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`nome`)
) ENGINE=InnoDB;

CREATE TABLE `RegimePrevidenziale` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `regime` int(10) NOT NULL,
  `iscrizione` int(10) NOT NULL,
  `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
`ID_DatiContabili_regimi_previdenziali` int(10) NOT NULL,
INDEX(`ID_DatiContabili_regimi_previdenziali`),
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)) ENGINE=InnoDB;

CREATE TABLE `RevisioneDocumento` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `allegato` int(10),
  `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
`ID_Documento_revisioni` int(10) NOT NULL,
INDEX(`ID_Documento_revisioni`),
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`allegato`)
) ENGINE=InnoDB;

CREATE TABLE `Scadenza` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `ID_Pratica_pratica_scadenza` int(10),
  `ID_Attivita_attivita_scadenza` int(10),
    `destinarario_personalizzato` int(10),
  `data` DATE NOT NULL,
  `preavviso_gg` int(10) NOT NULL,
  `oggetto_scadenza` varchar(134) NOT NULL,
  `descrizione_scadenza` longtext,
  `forza_sms` tinyint(1),
  `ricorrenza` int(10),
  `fine_ricorrenza` DATE,
    `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
`ID_Cliente_scadenze` int(10) NOT NULL,
INDEX(`ID_Cliente_scadenze`),
`ID_Operatore_op_scadenze` int(10) NOT NULL,
INDEX(`ID_Operatore_op_scadenze`),
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)) ENGINE=InnoDB;

CREATE TABLE `Sms` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `numero_destinatario` varchar(200),
  `testo` varchar(160) NOT NULL,
  `qualita` int(10) NOT NULL,
  `ora_spedizione` DATETIME,
  `ora_ricezione` DATETIME,
  `note` longtext,
  `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
`ID_Cliente_sms_inviati` int(10) NOT NULL,
INDEX(`ID_Cliente_sms_inviati`),
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)) ENGINE=InnoDB;

CREATE TABLE `SollecitoPagamento` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `numero_sollecito` int(10),
  `oggetto` varchar(200) NOT NULL,
  `descrizione` longtext NOT NULL,
  `data_spedizione` DATE,
  `note` longtext,
  `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
`ID_ProForma_solleciti_pagamento` int(10) NOT NULL,
INDEX(`ID_ProForma_solleciti_pagamento`),
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)) ENGINE=InnoDB;

CREATE TABLE `SpesaAnticipata` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `data` DATE NOT NULL,
  `oggetto` varchar(200) NOT NULL,
  `importo` double NOT NULL,
  `fatturata` tinyint(1),
  `note` longtext,
  `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
`ID_Attivita_spese_anticipate` int(10) NOT NULL,
INDEX(`ID_Attivita_spese_anticipate`),
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)) ENGINE=InnoDB;

CREATE TABLE `StoriaDocumento` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `inviato_il` DATE,
  `scaricato_il` DATE,
  `sollecito_il` DATE,
  `workflow_completato_il` DATE,
  `testo_sms` varchar(134),
  `oggetto_mail` varchar(200),
  `testo_mail` longtext,
  `sollecito` int(10),
  `errore` varchar(200),
  `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
`ID_Cliente_inverse_of_cliente_doc` int(10) NOT NULL,
INDEX(`ID_Cliente_inverse_of_cliente_doc`),
`ID_Documento_storia_documento` int(10) NOT NULL,
INDEX(`ID_Documento_storia_documento`),
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`ID_Documento_storia_documento`)
) ENGINE=InnoDB;

CREATE TABLE `StoricoInvii` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `data` DATETIME NOT NULL,
  `destinatario` varchar(200) NOT NULL,
  `tipo_sollecito` int(10) NOT NULL,
  `messaggio` longtext NOT NULL,
  `esito` varchar(200),
  `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)) ENGINE=InnoDB;

CREATE TABLE `StoricoModifiche` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `classe` varchar(200),
  `istanza` varchar(200),
  `data_modifica` DATE,
  `html_versione` longtext,
  `note` longtext,
  `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`classe`, `istanza`, `data_modifica`)
) ENGINE=InnoDB;

CREATE TABLE `VoceFattura` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `importo` double NOT NULL,
  `oggetto` varchar(200) NOT NULL,
  `iva` int(10) NOT NULL,
  `rif_pratica` int(10),
  `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
`ID_Fattura_voci_fattura` int(10) NOT NULL,
INDEX(`ID_Fattura_voci_fattura`),
`ID_ProForma_voci_proforma` int(10) NOT NULL,
INDEX(`ID_ProForma_voci_proforma`),
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)) ENGINE=InnoDB;

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
