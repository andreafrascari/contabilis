-- phpMyAdmin SQL Dump
-- version 3.4.10.1deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generato il: Gen 14, 2014 alle 16:22
-- Versione del server: 5.5.34
-- Versione PHP: 5.3.10-1ubuntu3.9

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `tullini-helpgest`
--

-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_CalendarioFatturazione_Preview_Generazione`
--
DROP VIEW IF EXISTS `VW_CalendarioFatturazione_Preview_Generazione`;
CREATE TABLE IF NOT EXISTS `VW_CalendarioFatturazione_Preview_Generazione` (
`ID` int(10) unsigned
,`data` date
,`ID_ProForma_entry_calendario` int(10)
,`rata` int(10)
,`su_rate` int(10)
,`creation_date` date
,`creation_user` varchar(100)
,`last_modification_date` date
,`last_modification_user` varchar(100)
,`deletion_date` date
,`deletion_user` varchar(100)
,`deletion_flag` tinyint(1)
,`activation_flag` tinyint(1)
,`ID_Cliente_calendario_fatturazione` int(10)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_CHECK_VOCIFATTURA_ORFANE`
--
DROP VIEW IF EXISTS `VW_CHECK_VOCIFATTURA_ORFANE`;
CREATE TABLE IF NOT EXISTS `VW_CHECK_VOCIFATTURA_ORFANE` (
`ID` int(10) unsigned
,`importo` decimal(9,2)
,`oggetto` varchar(200)
,`iva` int(10)
,`rif_pratica` int(11)
,`creation_date` date
,`creation_user` varchar(100)
,`last_modification_date` date
,`last_modification_user` varchar(100)
,`deletion_date` date
,`deletion_user` varchar(100)
,`deletion_flag` tinyint(1)
,`activation_flag` tinyint(1)
,`ID_Fattura_voci_fattura` int(10)
,`ID_ProForma_voci_proforma` int(10)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_ClienteCandidato_in_attesa`
--
DROP VIEW IF EXISTS `VW_ClienteCandidato_in_attesa`;
CREATE TABLE IF NOT EXISTS `VW_ClienteCandidato_in_attesa` (
`ID` int(10) unsigned
,`cap` varchar(200)
,`cellulare` varchar(200)
,`cliente` varchar(200)
,`cliente_dal` date
,`codice_fiscale` varchar(200)
,`comune` varchar(200)
,`email` varchar(200)
,`fax` varchar(200)
,`indirizzo` varchar(200)
,`note` longtext
,`partita_iva` varchar(200)
,`preventivo` longtext
,`creation_date` date
,`creation_user` varchar(100)
,`last_modification_date` date
,`last_modification_user` varchar(100)
,`deletion_date` date
,`deletion_user` varchar(100)
,`deletion_flag` tinyint(1)
,`activation_flag` tinyint(1)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_Cliente_Per_Cliente_al`
--
DROP VIEW IF EXISTS `VW_Cliente_Per_Cliente_al`;
CREATE TABLE IF NOT EXISTS `VW_Cliente_Per_Cliente_al` (
`ID` int(10) unsigned
,`ID__system_user_inverse_of_account` int(10)
,`allegato` int(10)
,`allegato_1` int(10)
,`cellulare` varchar(200)
,`cliente` varchar(200)
,`codice_cliente` varchar(200)
,`codice_fiscale` varchar(200)
,`nickname` varchar(100)
,`email` varchar(200)
,`email2` varchar(200)
,`email3` varchar(200)
,`fax` varchar(200)
,`indirizzo` varchar(200)
,`recapito_indirizzo` varchar(200)
,`comune` varchar(200)
,`recapito_cap` varchar(200)
,`recapito_comune` varchar(200)
,`cap` varchar(200)
,`stato_cliente` int(11)
,`cessata_assistenza_il` date
,`cliente_dal` date
,`partita_iva` varchar(200)
,`note` longtext
,`telefono` varchar(200)
,`creation_date` date
,`creation_user` varchar(100)
,`last_modification_date` date
,`last_modification_user` varchar(100)
,`deletion_date` date
,`deletion_user` varchar(100)
,`deletion_flag` tinyint(1)
,`activation_flag` tinyint(1)
,`tipo_sollecito` int(10)
,`legale_rappresentante_cognome` varchar(200)
,`legale_rappresentante_nome` varchar(200)
,`legale_rappresentante_cf` varchar(200)
,`legale_rappresentante_residenza` varchar(200)
,`ID_Operatore_responsabile_di` int(10)
,`ID_ClienteCandidato_diventa_cliente` int(10)
,`ID_OLD_HG` int(11)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_Cliente_Tipo`
--
DROP VIEW IF EXISTS `VW_Cliente_Tipo`;
CREATE TABLE IF NOT EXISTS `VW_Cliente_Tipo` (
`tipi` varchar(5)
,`tipi_descr` text
,`ID` int(10)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_Documenti_Cliente`
--
DROP VIEW IF EXISTS `VW_Documenti_Cliente`;
CREATE TABLE IF NOT EXISTS `VW_Documenti_Cliente` (
`ID` int(10) unsigned
,`titolo` varchar(200)
,`abstract` longtext
,`anno_contabile` int(10)
,`data_riferimento` date
,`free_tag` varchar(200)
,`allegato` int(10)
,`allegato_1` int(10)
,`creation_date` date
,`creation_user` varchar(100)
,`last_modification_date` date
,`last_modification_user` varchar(100)
,`deletion_date` date
,`deletion_user` varchar(100)
,`deletion_flag` tinyint(1)
,`activation_flag` tinyint(1)
,`ID_DescrizioneDocumento_inverse_of_tipologia` int(10)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_Documenti_Cliente_Corrispondenza`
--
DROP VIEW IF EXISTS `VW_Documenti_Cliente_Corrispondenza`;
CREATE TABLE IF NOT EXISTS `VW_Documenti_Cliente_Corrispondenza` (
`ID` int(10) unsigned
,`titolo` varchar(200)
,`abstract` longtext
,`anno_contabile` int(10)
,`data_riferimento` date
,`free_tag` varchar(200)
,`allegato` int(10)
,`allegato_1` int(10)
,`creation_date` date
,`creation_user` varchar(100)
,`last_modification_date` date
,`last_modification_user` varchar(100)
,`deletion_date` date
,`deletion_user` varchar(100)
,`deletion_flag` tinyint(1)
,`activation_flag` tinyint(1)
,`ID_DescrizioneDocumento_inverse_of_tipologia` int(10)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_Documenti_Cliente_Documenti`
--
DROP VIEW IF EXISTS `VW_Documenti_Cliente_Documenti`;
CREATE TABLE IF NOT EXISTS `VW_Documenti_Cliente_Documenti` (
`ID` int(10) unsigned
,`titolo` varchar(200)
,`abstract` longtext
,`anno_contabile` int(10)
,`data_riferimento` date
,`free_tag` varchar(200)
,`allegato` int(10)
,`allegato_1` int(10)
,`creation_date` date
,`creation_user` varchar(100)
,`last_modification_date` date
,`last_modification_user` varchar(100)
,`deletion_date` date
,`deletion_user` varchar(100)
,`deletion_flag` tinyint(1)
,`activation_flag` tinyint(1)
,`ID_DescrizioneDocumento_inverse_of_tipologia` int(10)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_Documenti_User_Studio`
--
DROP VIEW IF EXISTS `VW_Documenti_User_Studio`;
CREATE TABLE IF NOT EXISTS `VW_Documenti_User_Studio` (
`ID` int(10) unsigned
,`titolo` varchar(200)
,`abstract` longtext
,`anno_contabile` int(10)
,`data_riferimento` date
,`free_tag` varchar(200)
,`allegato` int(10)
,`allegato_1` int(10)
,`creation_date` date
,`creation_user` varchar(100)
,`last_modification_date` date
,`last_modification_user` varchar(100)
,`deletion_date` date
,`deletion_user` varchar(100)
,`deletion_flag` tinyint(1)
,`activation_flag` tinyint(1)
,`ID_DescrizioneDocumento_inverse_of_tipologia` int(10)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_Fattura_Iva`
--
DROP VIEW IF EXISTS `VW_Fattura_Iva`;
CREATE TABLE IF NOT EXISTS `VW_Fattura_Iva` (
`ID` int(10) unsigned
,`iva_0` double
,`iva_1` double
,`iva_2` double
,`iva_3` double
,`iva_4` double
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_Fattura_Max_Numero`
--
DROP VIEW IF EXISTS `VW_Fattura_Max_Numero`;
CREATE TABLE IF NOT EXISTS `VW_Fattura_Max_Numero` (
`numero` int(11)
,`ID` int(1)
,`anno_contabile` int(10)
,`note` varchar(201)
,`data` binary(0)
,`creation_date` binary(0)
,`creation_user` binary(0)
,`deletion_flag` int(1)
,`activation_flag` int(1)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_Fattura_Max_Numero_Contabilis`
--
DROP VIEW IF EXISTS `VW_Fattura_Max_Numero_Contabilis`;
CREATE TABLE IF NOT EXISTS `VW_Fattura_Max_Numero_Contabilis` (
`numero` int(11)
,`ID` int(1)
,`anno_contabile` int(10)
,`note` varchar(201)
,`data` binary(0)
,`creation_date` binary(0)
,`creation_user` binary(0)
,`deletion_flag` int(1)
,`activation_flag` int(1)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_Fattura_Max_Numero_Studio`
--
DROP VIEW IF EXISTS `VW_Fattura_Max_Numero_Studio`;
CREATE TABLE IF NOT EXISTS `VW_Fattura_Max_Numero_Studio` (
`numero` int(11)
,`ID` int(1)
,`anno_contabile` int(10)
,`note` varchar(201)
,`data` binary(0)
,`creation_date` binary(0)
,`creation_user` binary(0)
,`deletion_flag` int(1)
,`activation_flag` int(1)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_Fattura_Ritenuta_Acconto`
--
DROP VIEW IF EXISTS `VW_Fattura_Ritenuta_Acconto`;
CREATE TABLE IF NOT EXISTS `VW_Fattura_Ritenuta_Acconto` (
`ID` int(10) unsigned
,`importo` double
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_Fattura_Rivalsa_Previdenziale`
--
DROP VIEW IF EXISTS `VW_Fattura_Rivalsa_Previdenziale`;
CREATE TABLE IF NOT EXISTS `VW_Fattura_Rivalsa_Previdenziale` (
`ID` int(10) unsigned
,`importo` double
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_Fattura_Somma_Voci`
--
DROP VIEW IF EXISTS `VW_Fattura_Somma_Voci`;
CREATE TABLE IF NOT EXISTS `VW_Fattura_Somma_Voci` (
`ID` int(10) unsigned
,`importo_iva_0` decimal(31,2)
,`iva_0` bigint(20)
,`importo_iva_1` decimal(31,2)
,`importo_iva_2` decimal(31,2)
,`importo_iva_3` decimal(31,2)
,`importo_iva_4` decimal(31,2)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_Fattura_Somma_Voci_tmp`
--
DROP VIEW IF EXISTS `VW_Fattura_Somma_Voci_tmp`;
CREATE TABLE IF NOT EXISTS `VW_Fattura_Somma_Voci_tmp` (
`ID` int(10) unsigned
,`importo_iva_0` decimal(31,2)
,`iva_0` bigint(11)
,`importo_iva_1` decimal(31,2)
,`importo_iva_2` decimal(31,2)
,`importo_iva_3` decimal(31,2)
,`importo_iva_4` decimal(31,2)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_Fattura_Totale_Da_Pagare`
--
DROP VIEW IF EXISTS `VW_Fattura_Totale_Da_Pagare`;
CREATE TABLE IF NOT EXISTS `VW_Fattura_Totale_Da_Pagare` (
`ID` int(10) unsigned
,`importo` double
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_Fattura_Totale_Fattura`
--
DROP VIEW IF EXISTS `VW_Fattura_Totale_Fattura`;
CREATE TABLE IF NOT EXISTS `VW_Fattura_Totale_Fattura` (
`ID` int(10) unsigned
,`importo` double
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_Fattura_Totale_Imponibile`
--
DROP VIEW IF EXISTS `VW_Fattura_Totale_Imponibile`;
CREATE TABLE IF NOT EXISTS `VW_Fattura_Totale_Imponibile` (
`ID` int(10) unsigned
,`importo_iva_0` double
,`iva_0` bigint(20)
,`importo_iva_1` double
,`importo_iva_2` double
,`importo_iva_3` double
,`importo_iva_4` double
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_Fattura_Totali`
--
DROP VIEW IF EXISTS `VW_Fattura_Totali`;
CREATE TABLE IF NOT EXISTS `VW_Fattura_Totali` (
`ID` int(10) unsigned
,`numero` int(11)
,`data` date
,`data_pagamento` date
,`competenza` int(10)
,`ID_Cliente_fatture` int(10)
,`anno_contabile` int(10)
,`spese_anticipate_desc` longtext
,`Rivalsa_Previdenziale` double(19,2)
,`Totale_Imponibile_0` double(19,2)
,`Totale_Imponibile_1` double(19,2)
,`Totale_Imponibile_2` double(19,2)
,`Totale_Imponibile_3` double(19,2)
,`Totale_Imponibile_4` double(19,2)
,`Iva_0` double(19,2)
,`Iva_1` double(19,2)
,`Iva_2` double(19,2)
,`Iva_3` double(19,2)
,`Iva_4` double(19,2)
,`Spese_Escluse_Da_Imponibile` decimal(9,2)
,`Totale_Fattura` double(19,2)
,`Ritenuta_Acconto` double(19,2)
,`Totale_Da_Pagare` double(19,2)
,`creation_date` date
,`creation_user` varchar(100)
,`last_modification_date` date
,`last_modification_user` varchar(100)
,`deletion_date` date
,`deletion_user` varchar(100)
,`deletion_flag` tinyint(1)
,`activation_flag` tinyint(1)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_ItemListinoXPraticheForNextYear`
--
DROP VIEW IF EXISTS `VW_ItemListinoXPraticheForNextYear`;
CREATE TABLE IF NOT EXISTS `VW_ItemListinoXPraticheForNextYear` (
`ID_cliente` int(10) unsigned
,`cliente` varchar(200)
,`ID` int(10) unsigned
,`descrizione` longtext
,`prezzo` double
,`tipo` int(10)
,`titolo` varchar(200)
,`titolo_per_fattura` varchar(200)
,`creation_date` date
,`creation_user` varchar(100)
,`last_modification_date` date
,`last_modification_user` varchar(100)
,`deletion_date` date
,`deletion_user` varchar(100)
,`deletion_flag` tinyint(1)
,`activation_flag` tinyint(1)
,`ID_DatiFatturazione_listino` int(10)
,`ID_Metapratica_in_listini` int(10)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_ItemListinoXPraticheForThisYear`
--
DROP VIEW IF EXISTS `VW_ItemListinoXPraticheForThisYear`;
CREATE TABLE IF NOT EXISTS `VW_ItemListinoXPraticheForThisYear` (
`ID_cliente` int(10) unsigned
,`cliente` varchar(200)
,`ID` int(10) unsigned
,`descrizione` longtext
,`prezzo` double
,`tipo` int(10)
,`titolo` varchar(200)
,`titolo_per_fattura` varchar(200)
,`creation_date` date
,`creation_user` varchar(100)
,`last_modification_date` date
,`last_modification_user` varchar(100)
,`deletion_date` date
,`deletion_user` varchar(100)
,`deletion_flag` tinyint(1)
,`activation_flag` tinyint(1)
,`ID_DatiFatturazione_listino` int(10)
,`ID_Metapratica_in_listini` int(10)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_LavoroSuAttivita_report`
--
DROP VIEW IF EXISTS `VW_LavoroSuAttivita_report`;
CREATE TABLE IF NOT EXISTS `VW_LavoroSuAttivita_report` (
`ID` int(10) unsigned
,`data` date
,`durata_minuti` int(10)
,`diario` longtext
,`allegato` int(10)
,`creation_date` date
,`creation_user` varchar(100)
,`last_modification_date` date
,`last_modification_user` varchar(100)
,`deletion_date` date
,`deletion_user` varchar(100)
,`deletion_flag` tinyint(1)
,`activation_flag` tinyint(1)
,`ID_Attivita_sessioni_di_lavoro` int(10)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_LavoroSuAttivita_ultime_sessioni`
--
DROP VIEW IF EXISTS `VW_LavoroSuAttivita_ultime_sessioni`;
CREATE TABLE IF NOT EXISTS `VW_LavoroSuAttivita_ultime_sessioni` (
`ID` int(10) unsigned
,`data` datetime
,`durata_minuti` int(10)
,`diario` longtext
,`allegato` int(10)
,`creation_date` date
,`creation_user` varchar(100)
,`last_modification_date` date
,`last_modification_user` varchar(100)
,`deletion_date` date
,`deletion_user` varchar(100)
,`deletion_flag` tinyint(1)
,`activation_flag` tinyint(1)
,`ID_Attivita_sessioni_di_lavoro` int(10)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_Pratica_Dormiente`
--
DROP VIEW IF EXISTS `VW_Pratica_Dormiente`;
CREATE TABLE IF NOT EXISTS `VW_Pratica_Dormiente` (
`ID` int(10) unsigned
,`tipo` int(10)
,`codice` varchar(200)
,`titolo` varchar(200)
,`note` longtext
,`data_chiusura` date
,`stato` int(10)
,`anno_contabile` int(10)
,`prezzo` double
,`titolo_per_fattura` varchar(200)
,`creation_date` date
,`creation_user` varchar(100)
,`last_modification_date` date
,`last_modification_user` varchar(100)
,`deletion_date` date
,`deletion_user` varchar(100)
,`deletion_flag` tinyint(1)
,`activation_flag` tinyint(1)
,`ID_Cliente_pratiche` int(10)
,`ID_Operatore_responsabile_di_pratiche` int(10)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_Pratiche_chiuse_senza_data_chiusura_2013`
--
DROP VIEW IF EXISTS `VW_Pratiche_chiuse_senza_data_chiusura_2013`;
CREATE TABLE IF NOT EXISTS `VW_Pratiche_chiuse_senza_data_chiusura_2013` (
`ID` int(10) unsigned
,`tipo` int(10)
,`codice` varchar(200)
,`titolo` varchar(200)
,`note` longtext
,`data_chiusura` date
,`stato` int(10)
,`anno_contabile` int(10)
,`prezzo` double
,`titolo_per_fattura` varchar(200)
,`creation_date` date
,`creation_user` varchar(100)
,`last_modification_date` date
,`last_modification_user` varchar(100)
,`deletion_date` date
,`deletion_user` varchar(100)
,`deletion_flag` tinyint(1)
,`activation_flag` tinyint(1)
,`ID_Cliente_pratiche` int(10)
,`ID_Operatore_responsabile_di_pratiche` int(10)
,`ID_Metapratica_pratiche_generate` int(11)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_Pratiche_chiuse_senza_data_chiusura_2014`
--
DROP VIEW IF EXISTS `VW_Pratiche_chiuse_senza_data_chiusura_2014`;
CREATE TABLE IF NOT EXISTS `VW_Pratiche_chiuse_senza_data_chiusura_2014` (
`ID` int(10) unsigned
,`tipo` int(10)
,`codice` varchar(200)
,`titolo` varchar(200)
,`note` longtext
,`data_chiusura` date
,`stato` int(10)
,`anno_contabile` int(10)
,`prezzo` double
,`titolo_per_fattura` varchar(200)
,`creation_date` date
,`creation_user` varchar(100)
,`last_modification_date` date
,`last_modification_user` varchar(100)
,`deletion_date` date
,`deletion_user` varchar(100)
,`deletion_flag` tinyint(1)
,`activation_flag` tinyint(1)
,`ID_Cliente_pratiche` int(10)
,`ID_Operatore_responsabile_di_pratiche` int(10)
,`ID_Metapratica_pratiche_generate` int(11)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_ProForma_Iva`
--
DROP VIEW IF EXISTS `VW_ProForma_Iva`;
CREATE TABLE IF NOT EXISTS `VW_ProForma_Iva` (
`ID` int(10) unsigned
,`iva_0` double
,`iva_1` double
,`iva_2` double
,`iva_3` double
,`iva_4` double
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_ProForma_Max_Numero`
--
DROP VIEW IF EXISTS `VW_ProForma_Max_Numero`;
CREATE TABLE IF NOT EXISTS `VW_ProForma_Max_Numero` (
`numero` int(11)
,`ID` int(1)
,`anno_contabile` int(10)
,`note` varchar(201)
,`data` binary(0)
,`creation_date` binary(0)
,`creation_user` binary(0)
,`deletion_flag` int(1)
,`activation_flag` int(1)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_ProForma_Max_Numero_Contabilis`
--
DROP VIEW IF EXISTS `VW_ProForma_Max_Numero_Contabilis`;
CREATE TABLE IF NOT EXISTS `VW_ProForma_Max_Numero_Contabilis` (
`numero` int(11)
,`ID` int(1)
,`anno_contabile` int(10)
,`note` varchar(201)
,`data` binary(0)
,`creation_date` binary(0)
,`creation_user` binary(0)
,`deletion_flag` int(1)
,`activation_flag` int(1)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_ProForma_Max_Numero_Studio`
--
DROP VIEW IF EXISTS `VW_ProForma_Max_Numero_Studio`;
CREATE TABLE IF NOT EXISTS `VW_ProForma_Max_Numero_Studio` (
`numero` int(11)
,`ID` int(1)
,`anno_contabile` int(10)
,`note` varchar(201)
,`data` binary(0)
,`creation_date` binary(0)
,`creation_user` binary(0)
,`deletion_flag` int(1)
,`activation_flag` int(1)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_ProForma_Ritenuta_Acconto`
--
DROP VIEW IF EXISTS `VW_ProForma_Ritenuta_Acconto`;
CREATE TABLE IF NOT EXISTS `VW_ProForma_Ritenuta_Acconto` (
`ID` int(10) unsigned
,`importo` double
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_ProForma_Rivalsa_Previdenziale`
--
DROP VIEW IF EXISTS `VW_ProForma_Rivalsa_Previdenziale`;
CREATE TABLE IF NOT EXISTS `VW_ProForma_Rivalsa_Previdenziale` (
`ID` int(10) unsigned
,`importo` double
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_Proforma_Sollecito_1`
--
DROP VIEW IF EXISTS `VW_Proforma_Sollecito_1`;
CREATE TABLE IF NOT EXISTS `VW_Proforma_Sollecito_1` (
`ID` int(10) unsigned
,`numero` int(11)
,`data` date
,`competenza` int(10)
,`non_incassabile` tinyint(1)
,`spese_anticipate_fattura` decimal(9,2)
,`spese_anticipate_desc` longtext
,`riv_prev` int(1)
,`ra` int(1)
,`data_pagamento` date
,`note` longtext
,`creation_date` date
,`creation_user` varchar(100)
,`last_modification_date` date
,`last_modification_user` varchar(100)
,`deletion_date` date
,`deletion_user` varchar(100)
,`deletion_flag` tinyint(1)
,`activation_flag` tinyint(1)
,`ID_Cliente_proforma` int(10)
,`stato_proforma` int(10)
,`anno_contabile` int(10)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_Proforma_Sollecito_2`
--
DROP VIEW IF EXISTS `VW_Proforma_Sollecito_2`;
CREATE TABLE IF NOT EXISTS `VW_Proforma_Sollecito_2` (
`ID` int(10) unsigned
,`numero` int(11)
,`data` date
,`competenza` int(10)
,`non_incassabile` tinyint(1)
,`spese_anticipate_fattura` decimal(9,2)
,`spese_anticipate_desc` longtext
,`riv_prev` int(1)
,`ra` int(1)
,`data_pagamento` date
,`note` longtext
,`creation_date` date
,`creation_user` varchar(100)
,`last_modification_date` date
,`last_modification_user` varchar(100)
,`deletion_date` date
,`deletion_user` varchar(100)
,`deletion_flag` tinyint(1)
,`activation_flag` tinyint(1)
,`ID_Cliente_proforma` int(10)
,`stato_proforma` int(10)
,`anno_contabile` int(10)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_Proforma_Sollecito_3`
--
DROP VIEW IF EXISTS `VW_Proforma_Sollecito_3`;
CREATE TABLE IF NOT EXISTS `VW_Proforma_Sollecito_3` (
`ID` int(10) unsigned
,`numero` int(11)
,`data` date
,`competenza` int(10)
,`non_incassabile` tinyint(1)
,`spese_anticipate_fattura` decimal(9,2)
,`spese_anticipate_desc` longtext
,`riv_prev` int(1)
,`ra` int(1)
,`data_pagamento` date
,`note` longtext
,`creation_date` date
,`creation_user` varchar(100)
,`last_modification_date` date
,`last_modification_user` varchar(100)
,`deletion_date` date
,`deletion_user` varchar(100)
,`deletion_flag` tinyint(1)
,`activation_flag` tinyint(1)
,`ID_Cliente_proforma` int(10)
,`stato_proforma` int(10)
,`anno_contabile` int(10)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_ProForma_Somma_Voci`
--
DROP VIEW IF EXISTS `VW_ProForma_Somma_Voci`;
CREATE TABLE IF NOT EXISTS `VW_ProForma_Somma_Voci` (
`ID` int(10) unsigned
,`importo_iva_0` decimal(31,2)
,`iva_0` bigint(20)
,`importo_iva_1` decimal(31,2)
,`importo_iva_2` decimal(31,2)
,`importo_iva_3` decimal(31,2)
,`importo_iva_4` decimal(31,2)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_ProForma_Somma_Voci_tmp`
--
DROP VIEW IF EXISTS `VW_ProForma_Somma_Voci_tmp`;
CREATE TABLE IF NOT EXISTS `VW_ProForma_Somma_Voci_tmp` (
`ID` int(10) unsigned
,`importo_iva_0` decimal(31,2)
,`iva_0` bigint(11)
,`importo_iva_1` decimal(31,2)
,`importo_iva_2` decimal(31,2)
,`importo_iva_3` decimal(31,2)
,`importo_iva_4` decimal(31,2)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_ProForma_Totale_Da_Pagare`
--
DROP VIEW IF EXISTS `VW_ProForma_Totale_Da_Pagare`;
CREATE TABLE IF NOT EXISTS `VW_ProForma_Totale_Da_Pagare` (
`ID` int(10) unsigned
,`importo` double
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_ProForma_Totale_Imponibile`
--
DROP VIEW IF EXISTS `VW_ProForma_Totale_Imponibile`;
CREATE TABLE IF NOT EXISTS `VW_ProForma_Totale_Imponibile` (
`ID` int(10) unsigned
,`importo_iva_0` double
,`iva_0` bigint(20)
,`importo_iva_1` double
,`importo_iva_2` double
,`importo_iva_3` double
,`importo_iva_4` double
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_ProForma_Totale_ProForma`
--
DROP VIEW IF EXISTS `VW_ProForma_Totale_ProForma`;
CREATE TABLE IF NOT EXISTS `VW_ProForma_Totale_ProForma` (
`ID` int(10) unsigned
,`importo` double
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_ProForma_Totali`
--
DROP VIEW IF EXISTS `VW_ProForma_Totali`;
CREATE TABLE IF NOT EXISTS `VW_ProForma_Totali` (
`ID` int(10) unsigned
,`numero` int(11)
,`data` date
,`data_pagamento` date
,`competenza` int(10)
,`ID_Cliente_proforma` int(10)
,`anno_contabile` int(10)
,`spese_anticipate_desc` longtext
,`stato_proforma` int(10)
,`non_incassabile` tinyint(1)
,`Rivalsa_Previdenziale` double
,`Totale_Imponibile_0` double
,`Totale_Imponibile_1` double
,`Totale_Imponibile_2` double
,`Totale_Imponibile_3` double
,`Totale_Imponibile_4` double
,`Iva_0` double
,`Iva_1` double
,`Iva_2` double
,`Iva_3` double
,`Iva_4` double
,`Spese_Escluse_Da_Imponibile` decimal(9,2)
,`Totale_Fattura` double
,`Ritenuta_Acconto` double
,`Totale_Da_Pagare` double
,`creation_date` date
,`creation_user` varchar(100)
,`last_modification_date` date
,`last_modification_user` varchar(100)
,`deletion_date` date
,`deletion_user` varchar(100)
,`deletion_flag` tinyint(1)
,`activation_flag` tinyint(1)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_Scadenze_OneOff_3mAhead`
--
DROP VIEW IF EXISTS `VW_Scadenze_OneOff_3mAhead`;
CREATE TABLE IF NOT EXISTS `VW_Scadenze_OneOff_3mAhead` (
`ID` int(10) unsigned
,`data` date
,`oggetto_scadenza` varchar(200)
,`descrizione_scadenza` longtext
,`preavviso_gg` int(10)
,`ID_Pratica_pratica_scadenza` int(10)
,`ID_Attivita_attivita_scadenza` int(10)
,`ricorrenza` int(10)
,`forza_sms` tinyint(1)
,`fine_ricorrenza` date
,`creation_date` date
,`creation_user` varchar(100)
,`last_modification_date` date
,`last_modification_user` varchar(100)
,`deletion_date` date
,`deletion_user` varchar(100)
,`deletion_flag` tinyint(1)
,`activation_flag` tinyint(1)
,`ID_Cliente_scadenze` int(10)
,`ID_Operatore_op_scadenze` int(10)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_Scadenze_OneOff_Today`
--
DROP VIEW IF EXISTS `VW_Scadenze_OneOff_Today`;
CREATE TABLE IF NOT EXISTS `VW_Scadenze_OneOff_Today` (
`ID` int(10) unsigned
,`data` date
,`oggetto_scadenza` varchar(200)
,`descrizione_scadenza` longtext
,`preavviso_gg` int(10)
,`ID_Pratica_pratica_scadenza` int(10)
,`ID_Attivita_attivita_scadenza` int(10)
,`ricorrenza` int(10)
,`forza_sms` tinyint(1)
,`fine_ricorrenza` date
,`creation_date` date
,`creation_user` varchar(100)
,`last_modification_date` date
,`last_modification_user` varchar(100)
,`deletion_date` date
,`deletion_user` varchar(100)
,`deletion_flag` tinyint(1)
,`activation_flag` tinyint(1)
,`ID_Cliente_scadenze` int(10)
,`ID_Operatore_op_scadenze` int(10)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_Scadenze_Ricorrenti`
--
DROP VIEW IF EXISTS `VW_Scadenze_Ricorrenti`;
CREATE TABLE IF NOT EXISTS `VW_Scadenze_Ricorrenti` (
`ID` int(10) unsigned
,`data` date
,`oggetto_scadenza` varchar(200)
,`descrizione_scadenza` longtext
,`preavviso_gg` int(10)
,`ID_Pratica_pratica_scadenza` int(10)
,`ID_Attivita_attivita_scadenza` int(10)
,`ricorrenza` int(10)
,`forza_sms` tinyint(1)
,`fine_ricorrenza` date
,`creation_date` date
,`creation_user` varchar(100)
,`last_modification_date` date
,`last_modification_user` varchar(100)
,`deletion_date` date
,`deletion_user` varchar(100)
,`deletion_flag` tinyint(1)
,`activation_flag` tinyint(1)
,`ID_Cliente_scadenze` int(10)
,`ID_Operatore_op_scadenze` int(10)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_STAT_RIEPILOGO_FATTURA`
--
DROP VIEW IF EXISTS `VW_STAT_RIEPILOGO_FATTURA`;
CREATE TABLE IF NOT EXISTS `VW_STAT_RIEPILOGO_FATTURA` (
`ID` int(1)
,`p1` varchar(201)
,`p2` varchar(200)
,`p3` binary(0)
,`p4` decimal(9,2)
,`p5` decimal(38,4)
,`creation_date` date
,`last_modification_date` date
,`creation_user` varchar(5)
,`last_modification_user` varchar(5)
,`deletion_flag` int(1)
,`activation_flag` int(1)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_STAT_RIEPILOGO_PROFORMA`
--
DROP VIEW IF EXISTS `VW_STAT_RIEPILOGO_PROFORMA`;
CREATE TABLE IF NOT EXISTS `VW_STAT_RIEPILOGO_PROFORMA` (
`ID` int(1)
,`p1` varchar(201)
,`p2` varchar(200)
,`p3` decimal(4,1)
,`p4` decimal(9,2)
,`p5` decimal(38,4)
,`creation_date` date
,`last_modification_date` date
,`creation_user` varchar(5)
,`last_modification_user` varchar(5)
,`deletion_flag` int(1)
,`activation_flag` int(1)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_STAT_RITARDO_MEDIO_ANNI`
--
DROP VIEW IF EXISTS `VW_STAT_RITARDO_MEDIO_ANNI`;
CREATE TABLE IF NOT EXISTS `VW_STAT_RITARDO_MEDIO_ANNI` (
`ID` int(1)
,`p1` varchar(201)
,`p2` decimal(10,4)
,`p3` bigint(21)
,`p4` binary(0)
,`p5` binary(0)
,`creation_date` date
,`last_modification_date` date
,`creation_user` varchar(5)
,`last_modification_user` varchar(5)
,`deletion_flag` int(1)
,`activation_flag` int(1)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_STAT_RITARDO_MEDIO_CLIENTI`
--
DROP VIEW IF EXISTS `VW_STAT_RITARDO_MEDIO_CLIENTI`;
CREATE TABLE IF NOT EXISTS `VW_STAT_RITARDO_MEDIO_CLIENTI` (
`ID` bigint(20)
,`p1` varchar(200)
,`p2` varchar(10)
,`p3` bigint(21)
,`p4` binary(0)
,`p5` binary(0)
,`creation_date` date
,`last_modification_date` date
,`creation_user` varchar(5)
,`last_modification_user` varchar(5)
,`deletion_flag` bigint(20)
,`activation_flag` bigint(20)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_STAT_TOTALE_FATTURA_ANNO`
--
DROP VIEW IF EXISTS `VW_STAT_TOTALE_FATTURA_ANNO`;
CREATE TABLE IF NOT EXISTS `VW_STAT_TOTALE_FATTURA_ANNO` (
`ID` int(1)
,`p1` varchar(201)
,`p2` decimal(9,2)
,`p3` int(10)
,`p4` binary(0)
,`p5` binary(0)
,`creation_date` date
,`last_modification_date` date
,`creation_user` varchar(5)
,`last_modification_user` varchar(5)
,`deletion_flag` int(1)
,`activation_flag` int(1)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_STAT_TOTALE_PROFORMA_ANNO`
--
DROP VIEW IF EXISTS `VW_STAT_TOTALE_PROFORMA_ANNO`;
CREATE TABLE IF NOT EXISTS `VW_STAT_TOTALE_PROFORMA_ANNO` (
`ID` int(1)
,`p1` varchar(201)
,`p2` decimal(9,2)
,`p3` int(10)
,`p4` binary(0)
,`p5` binary(0)
,`creation_date` date
,`last_modification_date` date
,`creation_user` varchar(5)
,`last_modification_user` varchar(5)
,`deletion_flag` int(1)
,`activation_flag` int(1)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_SuperCliente`
--
DROP VIEW IF EXISTS `VW_SuperCliente`;
CREATE TABLE IF NOT EXISTS `VW_SuperCliente` (
`ID` int(10) unsigned
,`ID__system_user_inverse_of_account` int(10)
,`allegato` int(10)
,`allegato_1` int(10)
,`cellulare` varchar(200)
,`cliente` varchar(200)
,`codice_cliente` varchar(200)
,`codice_fiscale` varchar(200)
,`nickname` varchar(100)
,`email` varchar(200)
,`email2` varchar(200)
,`email3` varchar(200)
,`fax` varchar(200)
,`indirizzo` varchar(200)
,`recapito_indirizzo` varchar(200)
,`comune` varchar(200)
,`recapito_cap` varchar(200)
,`recapito_comune` varchar(200)
,`cap` varchar(200)
,`stato_cliente` int(11)
,`cessata_assistenza_il` date
,`cliente_dal` date
,`partita_iva` varchar(200)
,`note` longtext
,`telefono` varchar(200)
,`creation_date` date
,`creation_user` varchar(100)
,`last_modification_date` date
,`last_modification_user` varchar(100)
,`deletion_date` date
,`deletion_user` varchar(100)
,`deletion_flag` tinyint(1)
,`activation_flag` tinyint(1)
,`tipo_sollecito` int(10)
,`legale_rappresentante_cognome` varchar(200)
,`legale_rappresentante_nome` varchar(200)
,`legale_rappresentante_cf` varchar(200)
,`legale_rappresentante_residenza` varchar(200)
,`ID_Operatore_responsabile_di` int(10)
,`ID_ClienteCandidato_diventa_cliente` int(10)
,`ID_OLD_HG` int(11)
,`tipo_cliente` text
,`tipo_cliente_machine` int(10)
,`codice_ateco` varchar(200)
,`codice_ateco2` varchar(200)
,`codice_ateco3` varchar(200)
,`teniamo_noi` int(10)
,`regime_contabilita` int(10)
,`regime_iva` int(10)
,`iscrizione_registro_imprese` varchar(200)
,`liquidazione_iva` int(10)
,`esercizio_solare` int(11)
,`esercizio_da` varchar(20)
,`esercizio_a` varchar(20)
,`obbligo_iscrizione_inail` int(10)
,`pat_numero` varchar(200)
,`incarico_a` int(10)
,`provincia_registro_imprese` varchar(200)
,`sezione_ordinaria` int(11)
,`iscritto_rea` int(11)
,`sezioni_speciali` int(10)
,`unita_locali` int(10)
,`sostituto_imposta` int(10)
,`immobili` int(10)
,`ici` int(10)
,`note_ici` longtext
,`cassetto_fiscale` int(10)
,`f24_cumulativo` int(10)
,`antireciclaggio` int(10)
,`privacy` int(10)
,`privacy_acquisita` int(10)
,`conservazione_sostitutiva` int(10)
,`conservazione_sostitutiva_delega_studio` int(10)
,`verifica_validita_firma_digitale` int(10)
,`conservazione_sostitutiva_note` varchar(200)
,`conservazione_libro_giornale` int(10)
,`conservazione_libro_giornale_data_versamento_diritti` date
,`data_invio_impronta_digitale` date
,`iscrizione` int(10)
,`regime` int(10)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_Workflow`
--
DROP VIEW IF EXISTS `VW_Workflow`;
CREATE TABLE IF NOT EXISTS `VW_Workflow` (
`ID` int(10) unsigned
,`workflow_completato_il` date
,`scaricato_il` date
,`inviato_il` date
,`sollecito_il` date
,`testo_sms` varchar(200)
,`oggetto_mail` varchar(200)
,`testo_mail` text
,`persistente_area_cliente` tinyint(1)
,`errore` varchar(200)
,`sollecito` int(11)
,`creation_date` date
,`creation_user` varchar(100)
,`last_modification_date` date
,`last_modification_user` varchar(100)
,`deletion_date` date
,`deletion_user` varchar(100)
,`deletion_flag` tinyint(1)
,`activation_flag` tinyint(1)
,`ID_Cliente_inverse_of_cliente_doc` int(10)
,`ID_Documento_storia_documento` int(10)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_Workflow_Documenti_AUI`
--
DROP VIEW IF EXISTS `VW_Workflow_Documenti_AUI`;
CREATE TABLE IF NOT EXISTS `VW_Workflow_Documenti_AUI` (
`ID` int(10) unsigned
,`workflow_completato_il` date
,`scaricato_il` date
,`inviato_il` date
,`sollecito_il` date
,`testo_sms` varchar(200)
,`oggetto_mail` varchar(200)
,`testo_mail` text
,`persistente_area_cliente` tinyint(1)
,`errore` varchar(200)
,`sollecito` int(11)
,`creation_date` date
,`creation_user` varchar(100)
,`last_modification_date` date
,`last_modification_user` varchar(100)
,`deletion_date` date
,`deletion_user` varchar(100)
,`deletion_flag` tinyint(1)
,`activation_flag` tinyint(1)
,`ID_Cliente_inverse_of_cliente_doc` int(10)
,`ID_Documento_storia_documento` int(10)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_Workflow_Documenti_Errore_Invio_Notifica`
--
DROP VIEW IF EXISTS `VW_Workflow_Documenti_Errore_Invio_Notifica`;
CREATE TABLE IF NOT EXISTS `VW_Workflow_Documenti_Errore_Invio_Notifica` (
`ID` int(10) unsigned
,`workflow_completato_il` date
,`scaricato_il` date
,`inviato_il` date
,`sollecito_il` date
,`testo_sms` varchar(200)
,`oggetto_mail` varchar(200)
,`testo_mail` text
,`persistente_area_cliente` tinyint(1)
,`errore` varchar(200)
,`sollecito` int(11)
,`creation_date` date
,`creation_user` varchar(100)
,`last_modification_date` date
,`last_modification_user` varchar(100)
,`deletion_date` date
,`deletion_user` varchar(100)
,`deletion_flag` tinyint(1)
,`activation_flag` tinyint(1)
,`ID_Cliente_inverse_of_cliente_doc` int(10)
,`ID_Documento_storia_documento` int(10)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_Workflow_Documenti_Non_Prervenuti_E_Sollecitati`
--
DROP VIEW IF EXISTS `VW_Workflow_Documenti_Non_Prervenuti_E_Sollecitati`;
CREATE TABLE IF NOT EXISTS `VW_Workflow_Documenti_Non_Prervenuti_E_Sollecitati` (
`ID` int(10) unsigned
,`workflow_completato_il` date
,`scaricato_il` date
,`inviato_il` date
,`sollecito_il` date
,`testo_sms` varchar(200)
,`oggetto_mail` varchar(200)
,`testo_mail` text
,`persistente_area_cliente` tinyint(1)
,`errore` varchar(200)
,`sollecito` int(11)
,`creation_date` date
,`creation_user` varchar(100)
,`last_modification_date` date
,`last_modification_user` varchar(100)
,`deletion_date` date
,`deletion_user` varchar(100)
,`deletion_flag` tinyint(1)
,`activation_flag` tinyint(1)
,`ID_Cliente_inverse_of_cliente_doc` int(10)
,`ID_Documento_storia_documento` int(10)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_Workflow_Documenti_Non_Prervenuti_Per_Sollecito`
--
DROP VIEW IF EXISTS `VW_Workflow_Documenti_Non_Prervenuti_Per_Sollecito`;
CREATE TABLE IF NOT EXISTS `VW_Workflow_Documenti_Non_Prervenuti_Per_Sollecito` (
`ID` int(10) unsigned
,`workflow_completato_il` date
,`scaricato_il` date
,`inviato_il` date
,`sollecito_il` date
,`testo_sms` varchar(200)
,`oggetto_mail` varchar(200)
,`testo_mail` text
,`persistente_area_cliente` tinyint(1)
,`errore` varchar(200)
,`sollecito` int(11)
,`creation_date` date
,`creation_user` varchar(100)
,`last_modification_date` date
,`last_modification_user` varchar(100)
,`deletion_date` date
,`deletion_user` varchar(100)
,`deletion_flag` tinyint(1)
,`activation_flag` tinyint(1)
,`ID_Cliente_inverse_of_cliente_doc` int(10)
,`ID_Documento_storia_documento` int(10)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_Workflow_Documenti_Non_Scaricati_E_Sollecitati`
--
DROP VIEW IF EXISTS `VW_Workflow_Documenti_Non_Scaricati_E_Sollecitati`;
CREATE TABLE IF NOT EXISTS `VW_Workflow_Documenti_Non_Scaricati_E_Sollecitati` (
`ID` int(10) unsigned
,`workflow_completato_il` date
,`scaricato_il` date
,`inviato_il` date
,`sollecito_il` date
,`testo_sms` varchar(200)
,`oggetto_mail` varchar(200)
,`testo_mail` text
,`persistente_area_cliente` tinyint(1)
,`errore` varchar(200)
,`sollecito` int(11)
,`creation_date` date
,`creation_user` varchar(100)
,`last_modification_date` date
,`last_modification_user` varchar(100)
,`deletion_date` date
,`deletion_user` varchar(100)
,`deletion_flag` tinyint(1)
,`activation_flag` tinyint(1)
,`ID_Cliente_inverse_of_cliente_doc` int(10)
,`ID_Documento_storia_documento` int(10)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_Workflow_Documenti_Non_Scaricati_No_Sollecito`
--
DROP VIEW IF EXISTS `VW_Workflow_Documenti_Non_Scaricati_No_Sollecito`;
CREATE TABLE IF NOT EXISTS `VW_Workflow_Documenti_Non_Scaricati_No_Sollecito` (
`ID` int(10) unsigned
,`workflow_completato_il` date
,`scaricato_il` date
,`inviato_il` date
,`sollecito_il` date
,`testo_sms` varchar(200)
,`oggetto_mail` varchar(200)
,`testo_mail` text
,`persistente_area_cliente` tinyint(1)
,`errore` varchar(200)
,`sollecito` int(11)
,`creation_date` date
,`creation_user` varchar(100)
,`last_modification_date` date
,`last_modification_user` varchar(100)
,`deletion_date` date
,`deletion_user` varchar(100)
,`deletion_flag` tinyint(1)
,`activation_flag` tinyint(1)
,`ID_Cliente_inverse_of_cliente_doc` int(10)
,`ID_Documento_storia_documento` int(10)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_Workflow_Documenti_Non_Scaricati_Per_Sollecito`
--
DROP VIEW IF EXISTS `VW_Workflow_Documenti_Non_Scaricati_Per_Sollecito`;
CREATE TABLE IF NOT EXISTS `VW_Workflow_Documenti_Non_Scaricati_Per_Sollecito` (
`ID` int(10) unsigned
,`workflow_completato_il` date
,`scaricato_il` date
,`inviato_il` date
,`sollecito_il` date
,`testo_sms` varchar(200)
,`oggetto_mail` varchar(200)
,`testo_mail` text
,`persistente_area_cliente` tinyint(1)
,`errore` varchar(200)
,`sollecito` int(11)
,`creation_date` date
,`creation_user` varchar(100)
,`last_modification_date` date
,`last_modification_user` varchar(100)
,`deletion_date` date
,`deletion_user` varchar(100)
,`deletion_flag` tinyint(1)
,`activation_flag` tinyint(1)
,`ID_Cliente_inverse_of_cliente_doc` int(10)
,`ID_Documento_storia_documento` int(10)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_Workflow_Doc_Iniziali`
--
DROP VIEW IF EXISTS `VW_Workflow_Doc_Iniziali`;
CREATE TABLE IF NOT EXISTS `VW_Workflow_Doc_Iniziali` (
`ID` int(10) unsigned
,`workflow_completato_il` date
,`scaricato_il` date
,`inviato_il` date
,`sollecito_il` date
,`testo_sms` varchar(200)
,`oggetto_mail` varchar(200)
,`testo_mail` text
,`persistente_area_cliente` tinyint(1)
,`errore` varchar(200)
,`sollecito` int(11)
,`creation_date` date
,`creation_user` varchar(100)
,`last_modification_date` date
,`last_modification_user` varchar(100)
,`deletion_date` date
,`deletion_user` varchar(100)
,`deletion_flag` tinyint(1)
,`activation_flag` tinyint(1)
,`ID_Cliente_inverse_of_cliente_doc` int(10)
,`ID_Documento_storia_documento` int(10)
);
-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `VW_Workflow_User_Studio`
--
DROP VIEW IF EXISTS `VW_Workflow_User_Studio`;
CREATE TABLE IF NOT EXISTS `VW_Workflow_User_Studio` (
`ID` int(10) unsigned
,`workflow_completato_il` date
,`scaricato_il` date
,`inviato_il` date
,`sollecito_il` date
,`testo_sms` varchar(200)
,`oggetto_mail` varchar(200)
,`testo_mail` text
,`persistente_area_cliente` tinyint(1)
,`errore` varchar(200)
,`sollecito` int(11)
,`creation_date` date
,`creation_user` varchar(100)
,`last_modification_date` date
,`last_modification_user` varchar(100)
,`deletion_date` date
,`deletion_user` varchar(100)
,`deletion_flag` tinyint(1)
,`activation_flag` tinyint(1)
,`ID_Cliente_inverse_of_cliente_doc` int(10)
,`ID_Documento_storia_documento` int(10)
);
-- --------------------------------------------------------

--
-- Struttura per la vista `VW_CalendarioFatturazione_Preview_Generazione`
--
DROP TABLE IF EXISTS `VW_CalendarioFatturazione_Preview_Generazione`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_CalendarioFatturazione_Preview_Generazione` AS select `CalendarioFatturazione`.`ID` AS `ID`,`CalendarioFatturazione`.`data` AS `data`,`CalendarioFatturazione`.`ID_ProForma_entry_calendario` AS `ID_ProForma_entry_calendario`,`CalendarioFatturazione`.`rata` AS `rata`,`CalendarioFatturazione`.`su_rate` AS `su_rate`,`CalendarioFatturazione`.`creation_date` AS `creation_date`,`CalendarioFatturazione`.`creation_user` AS `creation_user`,`CalendarioFatturazione`.`last_modification_date` AS `last_modification_date`,`CalendarioFatturazione`.`last_modification_user` AS `last_modification_user`,`CalendarioFatturazione`.`deletion_date` AS `deletion_date`,`CalendarioFatturazione`.`deletion_user` AS `deletion_user`,`CalendarioFatturazione`.`deletion_flag` AS `deletion_flag`,`CalendarioFatturazione`.`activation_flag` AS `activation_flag`,`CalendarioFatturazione`.`ID_Cliente_calendario_fatturazione` AS `ID_Cliente_calendario_fatturazione` from `CalendarioFatturazione` where (isnull(`CalendarioFatturazione`.`ID_ProForma_entry_calendario`) and (`CalendarioFatturazione`.`data` <= curdate()) and (`CalendarioFatturazione`.`deletion_flag` = 0));

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_CHECK_VOCIFATTURA_ORFANE`
--
DROP TABLE IF EXISTS `VW_CHECK_VOCIFATTURA_ORFANE`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_CHECK_VOCIFATTURA_ORFANE` AS select `VoceFattura`.`ID` AS `ID`,`VoceFattura`.`importo` AS `importo`,`VoceFattura`.`oggetto` AS `oggetto`,`VoceFattura`.`iva` AS `iva`,`VoceFattura`.`rif_pratica` AS `rif_pratica`,`VoceFattura`.`creation_date` AS `creation_date`,`VoceFattura`.`creation_user` AS `creation_user`,`VoceFattura`.`last_modification_date` AS `last_modification_date`,`VoceFattura`.`last_modification_user` AS `last_modification_user`,`VoceFattura`.`deletion_date` AS `deletion_date`,`VoceFattura`.`deletion_user` AS `deletion_user`,`VoceFattura`.`deletion_flag` AS `deletion_flag`,`VoceFattura`.`activation_flag` AS `activation_flag`,`VoceFattura`.`ID_Fattura_voci_fattura` AS `ID_Fattura_voci_fattura`,`VoceFattura`.`ID_ProForma_voci_proforma` AS `ID_ProForma_voci_proforma` from `VoceFattura` where ((not(exists(select 1 from `ProForma` `p` where (`VoceFattura`.`ID_ProForma_voci_proforma` = `p`.`ID`)))) and (not(exists(select 1 from `Fattura` `f` where (`VoceFattura`.`ID_Fattura_voci_fattura` = `f`.`ID`)))));

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_ClienteCandidato_in_attesa`
--
DROP TABLE IF EXISTS `VW_ClienteCandidato_in_attesa`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_ClienteCandidato_in_attesa` AS select `aliasClienteCandidato`.`ID` AS `ID`,`aliasClienteCandidato`.`cap` AS `cap`,`aliasClienteCandidato`.`cellulare` AS `cellulare`,`aliasClienteCandidato`.`cliente` AS `cliente`,`aliasClienteCandidato`.`cliente_dal` AS `cliente_dal`,`aliasClienteCandidato`.`codice_fiscale` AS `codice_fiscale`,`aliasClienteCandidato`.`comune` AS `comune`,`aliasClienteCandidato`.`email` AS `email`,`aliasClienteCandidato`.`fax` AS `fax`,`aliasClienteCandidato`.`indirizzo` AS `indirizzo`,`aliasClienteCandidato`.`note` AS `note`,`aliasClienteCandidato`.`partita_iva` AS `partita_iva`,`aliasClienteCandidato`.`preventivo` AS `preventivo`,`aliasClienteCandidato`.`creation_date` AS `creation_date`,`aliasClienteCandidato`.`creation_user` AS `creation_user`,`aliasClienteCandidato`.`last_modification_date` AS `last_modification_date`,`aliasClienteCandidato`.`last_modification_user` AS `last_modification_user`,`aliasClienteCandidato`.`deletion_date` AS `deletion_date`,`aliasClienteCandidato`.`deletion_user` AS `deletion_user`,`aliasClienteCandidato`.`deletion_flag` AS `deletion_flag`,`aliasClienteCandidato`.`activation_flag` AS `activation_flag` from `ClienteCandidato` `aliasClienteCandidato` where ((not(exists(select 1 AS `1` from `Cliente` `aliasCliente` where (`aliasCliente`.`ID_ClienteCandidato_diventa_cliente` = `aliasClienteCandidato`.`ID`)))) and (`aliasClienteCandidato`.`deletion_flag` = 0)) order by `aliasClienteCandidato`.`creation_date`;

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_Cliente_Per_Cliente_al`
--
DROP TABLE IF EXISTS `VW_Cliente_Per_Cliente_al`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_Cliente_Per_Cliente_al` AS select `Cliente`.`ID` AS `ID`,`Cliente`.`ID__system_user_inverse_of_account` AS `ID__system_user_inverse_of_account`,`Cliente`.`allegato` AS `allegato`,`Cliente`.`allegato_1` AS `allegato_1`,`Cliente`.`cellulare` AS `cellulare`,`Cliente`.`cliente` AS `cliente`,`Cliente`.`codice_cliente` AS `codice_cliente`,`Cliente`.`codice_fiscale` AS `codice_fiscale`,`Cliente`.`nickname` AS `nickname`,`Cliente`.`email` AS `email`,`Cliente`.`email2` AS `email2`,`Cliente`.`email3` AS `email3`,`Cliente`.`fax` AS `fax`,`Cliente`.`indirizzo` AS `indirizzo`,`Cliente`.`recapito_indirizzo` AS `recapito_indirizzo`,`Cliente`.`comune` AS `comune`,`Cliente`.`recapito_cap` AS `recapito_cap`,`Cliente`.`recapito_comune` AS `recapito_comune`,`Cliente`.`cap` AS `cap`,`Cliente`.`stato_cliente` AS `stato_cliente`,ifnull(`Cliente`.`cessata_assistenza_il`,cast('2020/12/31' as date)) AS `cessata_assistenza_il`,`Cliente`.`cliente_dal` AS `cliente_dal`,`Cliente`.`partita_iva` AS `partita_iva`,`Cliente`.`note` AS `note`,`Cliente`.`telefono` AS `telefono`,`Cliente`.`creation_date` AS `creation_date`,`Cliente`.`creation_user` AS `creation_user`,`Cliente`.`last_modification_date` AS `last_modification_date`,`Cliente`.`last_modification_user` AS `last_modification_user`,`Cliente`.`deletion_date` AS `deletion_date`,`Cliente`.`deletion_user` AS `deletion_user`,`Cliente`.`deletion_flag` AS `deletion_flag`,`Cliente`.`activation_flag` AS `activation_flag`,`Cliente`.`tipo_sollecito` AS `tipo_sollecito`,`Cliente`.`legale_rappresentante_cognome` AS `legale_rappresentante_cognome`,`Cliente`.`legale_rappresentante_nome` AS `legale_rappresentante_nome`,`Cliente`.`legale_rappresentante_cf` AS `legale_rappresentante_cf`,`Cliente`.`legale_rappresentante_residenza` AS `legale_rappresentante_residenza`,`Cliente`.`ID_Operatore_responsabile_di` AS `ID_Operatore_responsabile_di`,`Cliente`.`ID_ClienteCandidato_diventa_cliente` AS `ID_ClienteCandidato_diventa_cliente`,`Cliente`.`ID_OLD_HG` AS `ID_OLD_HG` from `Cliente`;

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_Cliente_Tipo`
--
DROP TABLE IF EXISTS `VW_Cliente_Tipo`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_Cliente_Tipo` AS select cast(group_concat(`sd`.`sd_value` separator ',') as char(5) charset utf8) AS `tipi`,group_concat(`sd`.`sd_description` separator ',') AS `tipi_descr`,`r`.`ID_Cliente` AS `ID` from (`_system_decode` `sd` join `rel_Cliente_tipo_cliente` `r` on(((`r`.`ID_tipo_cliente` = `sd`.`sd_value`) and (`sd`.`sd_class` = 200)))) group by `r`.`ID_Cliente`;

-- --------------------------------------------------------

DROP TABLE IF EXISTS `VW_Documenti_Cliente_Corrispondenza`;
CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_Documenti_Cliente_Corrispondenza` AS select `d`.`ID` AS `ID`,`d`.`titolo` AS `titolo`,`d`.`abstract` AS `abstract`,`d`.`anno_contabile` AS `anno_contabile`,`d`.`data_riferimento` AS `data_riferimento`,`d`.`free_tag` AS `free_tag`,`d`.`allegato` AS `allegato`,`d`.`allegato_1` AS `allegato_1`,`d`.`creation_date` AS `creation_date`,`d`.`creation_user` AS `creation_user`,`d`.`last_modification_date` AS `last_modification_date`,`d`.`last_modification_user` AS `last_modification_user`,`d`.`deletion_date` AS `deletion_date`,`d`.`deletion_user` AS `deletion_user`,`d`.`deletion_flag` AS `deletion_flag`,`d`.`activation_flag` AS `activation_flag`,`d`.`ID_DescrizioneDocumento_inverse_of_tipologia` AS `ID_DescrizioneDocumento_inverse_of_tipologia` from (`Documento` `d` join `DescrizioneDocumento` `t` on((`d`.`ID_DescrizioneDocumento_inverse_of_tipologia` = `t`.`ID`))) 
where ((not((`t`.`tipologia_documento` like '%UNICO%'))) and (not((`t`.`tipologia_documento` like '%BILANCI%'))) and (not((`t`.`tipologia_documento` like '%IVA%'))) and (not((`t`.`tipologia_documento` like '%730%'))) and (not((`t`.`tipologia_documento` like '%770%'))) and (not((`t`.`tipologia_documento` like '%IRAP%'))) and (not((`t`.`tipologia_documento` like '%F24%'))));

DROP TABLE IF EXISTS `VW_Documenti_Cliente_Documenti`;
CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_Documenti_Cliente_Documenti` AS select `d`.`ID` AS `ID`,`d`.`titolo` AS `titolo`,`d`.`abstract` AS `abstract`,`d`.`anno_contabile` AS `anno_contabile`,`d`.`data_riferimento` AS `data_riferimento`,`d`.`free_tag` AS `free_tag`,`d`.`allegato` AS `allegato`,`d`.`allegato_1` AS `allegato_1`,`d`.`creation_date` AS `creation_date`,`d`.`creation_user` AS `creation_user`,`d`.`last_modification_date` AS `last_modification_date`,`d`.`last_modification_user` AS `last_modification_user`,`d`.`deletion_date` AS `deletion_date`,`d`.`deletion_user` AS `deletion_user`,`d`.`deletion_flag` AS `deletion_flag`,`d`.`activation_flag` AS `activation_flag`,`d`.`ID_DescrizioneDocumento_inverse_of_tipologia` AS `ID_DescrizioneDocumento_inverse_of_tipologia` from (`Documento` `d` join `DescrizioneDocumento` `t` on((`d`.`ID_DescrizioneDocumento_inverse_of_tipologia` = `t`.`ID`))) 
where ((`t`.`tipologia_documento` like '%UNICO%') or (`t`.`tipologia_documento` like '%BILANCI%') or (`t`.`tipologia_documento` like '%IVA%') or (`t`.`tipologia_documento` like '%730%') or (`t`.`tipologia_documento` like '%770%') or (`t`.`tipologia_documento` like '%IRAP%') or (`t`.`tipologia_documento` like '%F24%'));

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_Documenti_Cliente_Documenti`
--
DROP TABLE IF EXISTS `VW_Documenti_Cliente_Documenti`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_Documenti_Cliente_Documenti` AS select `d`.`ID` AS `ID`,`d`.`titolo` AS `titolo`,`d`.`abstract` AS `abstract`,`d`.`anno_contabile` AS `anno_contabile`,`d`.`data_riferimento` AS `data_riferimento`,`d`.`free_tag` AS `free_tag`,`d`.`allegato` AS `allegato`,`d`.`allegato_1` AS `allegato_1`,`d`.`creation_date` AS `creation_date`,`d`.`creation_user` AS `creation_user`,`d`.`last_modification_date` AS `last_modification_date`,`d`.`last_modification_user` AS `last_modification_user`,`d`.`deletion_date` AS `deletion_date`,`d`.`deletion_user` AS `deletion_user`,`d`.`deletion_flag` AS `deletion_flag`,`d`.`activation_flag` AS `activation_flag`,`d`.`ID_DescrizioneDocumento_inverse_of_tipologia` AS `ID_DescrizioneDocumento_inverse_of_tipologia` from (`Documento` `d` join `DescrizioneDocumento` `t` on((`d`.`ID_DescrizioneDocumento_inverse_of_tipologia` = `t`.`ID`))) where ((`t`.`tipologia_documento` like '%UNICO%') or (`t`.`tipologia_documento` like '%BILANCI%') or (`t`.`tipologia_documento` like '%IVA%') or (`t`.`tipologia_documento` like '%730%') or (`t`.`tipologia_documento` like '%770%') or (`t`.`tipologia_documento` like '%IRAP%'));

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_Documenti_User_Studio`
--
DROP TABLE IF EXISTS `VW_Documenti_User_Studio`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_Documenti_User_Studio` AS select `d`.`ID` AS `ID`,`d`.`titolo` AS `titolo`,`d`.`abstract` AS `abstract`,`d`.`anno_contabile` AS `anno_contabile`,`d`.`data_riferimento` AS `data_riferimento`,`d`.`free_tag` AS `free_tag`,`d`.`allegato` AS `allegato`,`d`.`allegato_1` AS `allegato_1`,`d`.`creation_date` AS `creation_date`,`d`.`creation_user` AS `creation_user`,`d`.`last_modification_date` AS `last_modification_date`,`d`.`last_modification_user` AS `last_modification_user`,`d`.`deletion_date` AS `deletion_date`,`d`.`deletion_user` AS `deletion_user`,`d`.`deletion_flag` AS `deletion_flag`,`d`.`activation_flag` AS `activation_flag`,`d`.`ID_DescrizioneDocumento_inverse_of_tipologia` AS `ID_DescrizioneDocumento_inverse_of_tipologia` from (`Documento` `d` join `DescrizioneDocumento` `dd` on((`d`.`ID_DescrizioneDocumento_inverse_of_tipologia` = `dd`.`ID`))) where ((`dd`.`visibilita` in (2,3,0)) and (`d`.`deletion_flag` = 0) and (`d`.`activation_flag` = 1));

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_Fattura_Iva`
--
DROP TABLE IF EXISTS `VW_Fattura_Iva`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_Fattura_Iva` AS select `p`.`ID` AS `ID`,(((`p`.`importo_iva_0` * (select `_system_decode`.`sd_notes` AS `sd_notes` from `_system_decode` where ((`_system_decode`.`sd_class` = 232) and (`_system_decode`.`sd_value` = `p`.`iva_0`)))) / 100) + ((`p`.`importo_iva_0` * `p`.`iva_0`) / 100)) AS `iva_0`,((`p`.`importo_iva_1` * (select `_system_decode`.`sd_notes` AS `sd_notes` from `_system_decode` where ((`_system_decode`.`sd_class` = 232) and (`_system_decode`.`sd_value` = `p`.`iva_0`)))) / 100) AS `iva_1`,((`p`.`importo_iva_2` * (select `_system_decode`.`sd_notes` AS `sd_notes` from `_system_decode` where ((`_system_decode`.`sd_class` = 232) and (`_system_decode`.`sd_value` = `p`.`iva_0`)))) / 100) AS `iva_2`,((`p`.`importo_iva_3` * (select `_system_decode`.`sd_notes` AS `sd_notes` from `_system_decode` where ((`_system_decode`.`sd_class` = 232) and (`_system_decode`.`sd_value` = `p`.`iva_0`)))) / 100) AS `iva_3`,((`p`.`importo_iva_4` * (select `_system_decode`.`sd_notes` AS `sd_notes` from `_system_decode` where ((`_system_decode`.`sd_class` = 232) and (`_system_decode`.`sd_value` = `p`.`iva_0`)))) / 100) AS `iva_4` from `VW_Fattura_Totale_Imponibile` `p`;

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_Fattura_Max_Numero`
--
DROP TABLE IF EXISTS `VW_Fattura_Max_Numero`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_Fattura_Max_Numero` AS select max(`Fattura`.`numero`) AS `numero`,1 AS `ID`,`Fattura`.`anno_contabile` AS `anno_contabile`,(select `_system_decode`.`sd_description` from `_system_decode` where ((`_system_decode`.`sd_value` = `Fattura`.`anno_contabile`) and (`_system_decode`.`sd_class` = 207))) AS `note`,NULL AS `data`,NULL AS `creation_date`,NULL AS `creation_user`,0 AS `deletion_flag`,1 AS `activation_flag` from `Fattura` where (`Fattura`.`deletion_flag` = 0) group by `Fattura`.`anno_contabile`;

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_Fattura_Max_Numero_Contabilis`
--
DROP TABLE IF EXISTS `VW_Fattura_Max_Numero_Contabilis`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_Fattura_Max_Numero_Contabilis` AS select max(`Fattura`.`numero`) AS `numero`,1 AS `ID`,`Fattura`.`anno_contabile` AS `anno_contabile`,(select `_system_decode`.`sd_description` from `_system_decode` where ((`_system_decode`.`sd_value` = `Fattura`.`anno_contabile`) and (`_system_decode`.`sd_class` = 207))) AS `note`,NULL AS `data`,NULL AS `creation_date`,NULL AS `creation_user`,0 AS `deletion_flag`,1 AS `activation_flag` from `Fattura` where ((`Fattura`.`deletion_flag` = 0) and (`Fattura`.`competenza` = 0)) group by `Fattura`.`anno_contabile`;

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_Fattura_Max_Numero_Studio`
--
DROP TABLE IF EXISTS `VW_Fattura_Max_Numero_Studio`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_Fattura_Max_Numero_Studio` AS select max(`Fattura`.`numero`) AS `numero`,1 AS `ID`,`Fattura`.`anno_contabile` AS `anno_contabile`,(select `_system_decode`.`sd_description` from `_system_decode` where ((`_system_decode`.`sd_value` = `Fattura`.`anno_contabile`) and (`_system_decode`.`sd_class` = 207))) AS `note`,NULL AS `data`,NULL AS `creation_date`,NULL AS `creation_user`,0 AS `deletion_flag`,1 AS `activation_flag` from `Fattura` where ((`Fattura`.`deletion_flag` = 0) and (`Fattura`.`competenza` = 1)) group by `Fattura`.`anno_contabile`;

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_Fattura_Ritenuta_Acconto`
--
DROP TABLE IF EXISTS `VW_Fattura_Ritenuta_Acconto`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_Fattura_Ritenuta_Acconto` AS select `p`.`ID` AS `ID`,(((`p`.`ra` * (select `_system_module_parameter`.`smp_value` AS `smp_value` from `_system_module_parameter` where (`_system_module_parameter`.`smp_name` = 'RITENUTA_ACCONTO'))) / 100) * ((((`t`.`importo_iva_0` + `t`.`importo_iva_1`) + `t`.`importo_iva_2`) + `t`.`importo_iva_3`) + `t`.`importo_iva_4`)) AS `importo` from (`VW_Fattura_Somma_Voci` `t` join `Fattura` `p` on((`p`.`ID` = `t`.`ID`)));

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_Fattura_Rivalsa_Previdenziale`
--
DROP TABLE IF EXISTS `VW_Fattura_Rivalsa_Previdenziale`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_Fattura_Rivalsa_Previdenziale` AS select `p`.`ID` AS `ID`,(((((((`v`.`importo_iva_0` + `v`.`importo_iva_1`) + `v`.`importo_iva_2`) + `v`.`importo_iva_3`) + `v`.`importo_iva_4`) * `p`.`riv_prev`) * (select `_system_module_parameter`.`smp_value` AS `smp_value` from `_system_module_parameter` where (`_system_module_parameter`.`smp_name` = 'RIVALSA_PREVIDENZIALE'))) / 100) AS `importo` from (`VW_Fattura_Somma_Voci` `v` join `Fattura` `p` on((`v`.`ID` = `p`.`ID`)));

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_Fattura_Somma_Voci`
--
DROP TABLE IF EXISTS `VW_Fattura_Somma_Voci`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_Fattura_Somma_Voci` AS select `p`.`ID` AS `ID`,coalesce(`p`.`importo_iva_0`,0) AS `importo_iva_0`,coalesce(`p`.`iva_0`,0) AS `iva_0`,coalesce(`p`.`importo_iva_1`,0) AS `importo_iva_1`,coalesce(`p`.`importo_iva_2`,0) AS `importo_iva_2`,coalesce(`p`.`importo_iva_3`,0) AS `importo_iva_3`,coalesce(`p`.`importo_iva_4`,0) AS `importo_iva_4` from `VW_Fattura_Somma_Voci_tmp` `p`;

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_Fattura_Somma_Voci_tmp`
--
DROP TABLE IF EXISTS `VW_Fattura_Somma_Voci_tmp`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_Fattura_Somma_Voci_tmp` AS select `p`.`ID` AS `ID`,(select sum(`v`.`importo`) AS `sum(v.importo)` from `VoceFattura` `v` where ((`v`.`deletion_flag` = 0) and (`v`.`iva` = 0) and (`v`.`ID_Fattura_voci_fattura` = `p`.`ID`))) AS `importo_iva_0`,(select max(`v`.`iva`) AS `max(v.iva)` from `VoceFattura` `v` where ((`v`.`deletion_flag` = 0) and (`v`.`ID_Fattura_voci_fattura` = `p`.`ID`))) AS `iva_0`,(select sum(`v`.`importo`) AS `sum(v.importo)` from `VoceFattura` `v` where ((`v`.`deletion_flag` = 0) and (`v`.`iva` = 1) and (`v`.`ID_Fattura_voci_fattura` = `p`.`ID`))) AS `importo_iva_1`,(select sum(`v`.`importo`) AS `sum(v.importo)` from `VoceFattura` `v` where ((`v`.`deletion_flag` = 0) and (`v`.`iva` = 2) and (`v`.`ID_Fattura_voci_fattura` = `p`.`ID`))) AS `importo_iva_2`,(select sum(`v`.`importo`) AS `sum(v.importo)` from `VoceFattura` `v` where ((`v`.`deletion_flag` = 0) and (`v`.`iva` = 3) and (`v`.`ID_Fattura_voci_fattura` = `p`.`ID`))) AS `importo_iva_3`,(select sum(`v`.`importo`) AS `sum(v.importo)` from `VoceFattura` `v` where ((`v`.`deletion_flag` = 0) and (`v`.`iva` >= 4) and (`v`.`ID_Fattura_voci_fattura` = `p`.`ID`))) AS `importo_iva_4` from `Fattura` `p`;

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_Fattura_Totale_Da_Pagare`
--
DROP TABLE IF EXISTS `VW_Fattura_Totale_Da_Pagare`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_Fattura_Totale_Da_Pagare` AS select `t`.`ID` AS `ID`,(`t`.`importo` - `r`.`importo`) AS `importo` from (`VW_Fattura_Totale_Fattura` `t` join `VW_Fattura_Ritenuta_Acconto` `r` on((`r`.`ID` = `t`.`ID`)));

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_Fattura_Totale_Fattura`
--
DROP TABLE IF EXISTS `VW_Fattura_Totale_Fattura`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_Fattura_Totale_Fattura` AS select `p`.`ID` AS `ID`,((((((`t`.`importo_iva_0` + `i`.`iva_0`) + (`t`.`importo_iva_1` + `i`.`iva_1`)) + (`t`.`importo_iva_2` + `i`.`iva_2`)) + (`t`.`importo_iva_3` + `i`.`iva_3`)) + (`t`.`importo_iva_4` + `i`.`iva_4`)) + `p`.`spese_anticipate_fattura`) AS `importo` from ((`VW_Fattura_Totale_Imponibile` `t` join `VW_Fattura_Iva` `i` on((`t`.`ID` = `i`.`ID`))) join `Fattura` `p` on((`p`.`ID` = `t`.`ID`)));

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_Fattura_Totale_Imponibile`
--
DROP TABLE IF EXISTS `VW_Fattura_Totale_Imponibile`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_Fattura_Totale_Imponibile` AS select `p`.`ID` AS `ID`,(`v`.`importo_iva_0` + (((`v`.`importo_iva_0` * `p`.`riv_prev`) * (select `_system_module_parameter`.`smp_value` AS `smp_value` from `_system_module_parameter` where (`_system_module_parameter`.`smp_name` = 'RIVALSA_PREVIDENZIALE'))) / 100)) AS `importo_iva_0`,`v`.`iva_0` AS `iva_0`,(`v`.`importo_iva_1` + (((`v`.`importo_iva_1` * `p`.`riv_prev`) * (select `_system_module_parameter`.`smp_value` AS `smp_value` from `_system_module_parameter` where (`_system_module_parameter`.`smp_name` = 'RIVALSA_PREVIDENZIALE'))) / 100)) AS `importo_iva_1`,(`v`.`importo_iva_2` + (((`v`.`importo_iva_2` * `p`.`riv_prev`) * (select `_system_module_parameter`.`smp_value` AS `smp_value` from `_system_module_parameter` where (`_system_module_parameter`.`smp_name` = 'RIVALSA_PREVIDENZIALE'))) / 100)) AS `importo_iva_2`,(`v`.`importo_iva_3` + (((`v`.`importo_iva_3` * `p`.`riv_prev`) * (select `_system_module_parameter`.`smp_value` AS `smp_value` from `_system_module_parameter` where (`_system_module_parameter`.`smp_name` = 'RIVALSA_PREVIDENZIALE'))) / 100)) AS `importo_iva_3`,(`v`.`importo_iva_4` + (((`v`.`importo_iva_4` * `p`.`riv_prev`) * (select `_system_module_parameter`.`smp_value` AS `smp_value` from `_system_module_parameter` where (`_system_module_parameter`.`smp_name` = 'RIVALSA_PREVIDENZIALE'))) / 100)) AS `importo_iva_4` from (`VW_Fattura_Somma_Voci` `v` join `Fattura` `p` on((`v`.`ID` = `p`.`ID`)));

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_Fattura_Totali`
--
DROP TABLE IF EXISTS `VW_Fattura_Totali`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_Fattura_Totali` AS select `p`.`ID` AS `ID`,`p`.`numero` AS `numero`,`p`.`data` AS `data`,`p`.`data_pagamento` AS `data_pagamento`,`p`.`competenza` AS `competenza`,`p`.`ID_Cliente_fatture` AS `ID_Cliente_fatture`,`p`.`anno_contabile` AS `anno_contabile`,`p`.`spese_anticipate_desc` AS `spese_anticipate_desc`,round(`v2`.`importo`,2) AS `Rivalsa_Previdenziale`,round(`v3`.`importo_iva_0`,2) AS `Totale_Imponibile_0`,round(`v3`.`importo_iva_1`,2) AS `Totale_Imponibile_1`,round(`v3`.`importo_iva_2`,2) AS `Totale_Imponibile_2`,round(`v3`.`importo_iva_3`,2) AS `Totale_Imponibile_3`,round(`v3`.`importo_iva_4`,2) AS `Totale_Imponibile_4`,round(`v4`.`iva_0`,2) AS `Iva_0`,round(`v4`.`iva_1`,2) AS `Iva_1`,round(`v4`.`iva_2`,2) AS `Iva_2`,round(`v4`.`iva_3`,2) AS `Iva_3`,round(`v4`.`iva_4`,2) AS `Iva_4`,round(`p`.`spese_anticipate_fattura`,2) AS `Spese_Escluse_Da_Imponibile`,round(`v5`.`importo`,2) AS `Totale_Fattura`,round(`v6`.`importo`,2) AS `Ritenuta_Acconto`,round(`v7`.`importo`,2) AS `Totale_Da_Pagare`,`p`.`creation_date` AS `creation_date`,`p`.`creation_user` AS `creation_user`,`p`.`last_modification_date` AS `last_modification_date`,`p`.`last_modification_user` AS `last_modification_user`,`p`.`deletion_date` AS `deletion_date`,`p`.`deletion_user` AS `deletion_user`,`p`.`deletion_flag` AS `deletion_flag`,`p`.`activation_flag` AS `activation_flag` from ((((((`Fattura` `p` join `VW_Fattura_Rivalsa_Previdenziale` `v2` on((`v2`.`ID` = `p`.`ID`))) join `VW_Fattura_Totale_Imponibile` `v3` on((`v3`.`ID` = `p`.`ID`))) join `VW_Fattura_Iva` `v4` on((`v4`.`ID` = `p`.`ID`))) join `VW_Fattura_Totale_Fattura` `v5` on((`v5`.`ID` = `p`.`ID`))) join `VW_Fattura_Ritenuta_Acconto` `v6` on((`v6`.`ID` = `p`.`ID`))) join `VW_Fattura_Totale_Da_Pagare` `v7` on((`v7`.`ID` = `p`.`ID`)));

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_ItemListinoXPraticheForNextYear`
--
DROP VIEW IF EXISTS `VW_ItemListinoXPraticheForNextYear`;

CREATE VIEW `VW_ItemListinoXPraticheForNextYear` AS select `c`.`ID` AS `ID_cliente`,`c`.`cliente` AS `cliente`,`l`.`ID` AS `ID`,`l`.`descrizione` AS `descrizione`,`l`.`prezzo` AS `prezzo`,`l`.`tipo` AS `tipo`,`l`.`titolo` AS `titolo`,`l`.`titolo_per_fattura` AS `titolo_per_fattura`,`l`.`creation_date` AS `creation_date`,`l`.`creation_user` AS `creation_user`,`l`.`last_modification_date` AS `last_modification_date`,`l`.`last_modification_user` AS `last_modification_user`,`l`.`deletion_date` AS `deletion_date`,`l`.`deletion_user` AS `deletion_user`,`l`.`deletion_flag` AS `deletion_flag`,`l`.`activation_flag` AS `activation_flag`,`l`.`ID_DatiFatturazione_listino` AS `ID_DatiFatturazione_listino`,`l`.`ID_Metapratica_in_listini` AS `ID_Metapratica_in_listini` from ((`ItemListino` `l` join `DatiFatturazione` `f` on((`l`.`ID_DatiFatturazione_listino` = `f`.`ID`))) join `Cliente` `c` on(((`f`.`ID_Cliente_dati_fatturazione` = `c`.`ID`) and (not(exists(select 1 AS `1` from `Pratica` `p` where ((`p`.`ID_Cliente_pratiche` = `c`.`ID`) and (`p`.`titolo` = `l`.`titolo`) and (`p`.`deletion_flag` = 0) and (`p`.`anno_contabile` = (select `d`.`sd_value` AS `sd_value` from `_system_decode` `d` where ((`d`.`sd_class` = 207) and (`d`.`sd_description` = (select year((curdate() + interval 1 year)) AS `Year(DATE_ADD(CURDATE( ) , INTERVAL +1 YEAR) )`))))))))))));

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_ItemListinoXPraticheForThisYear`
--
DROP TABLE IF EXISTS `VW_ItemListinoXPraticheForThisYear`;

DROP  VIEW `VW_ItemListinoXPraticheForThisYear` ;
CREATE  VIEW `VW_ItemListinoXPraticheForThisYear` AS select `c`.`ID` AS `ID_cliente`,`c`.`cliente` AS `cliente`,`l`.`ID` AS `ID`,`l`.`descrizione` AS `descrizione`,`l`.`prezzo` AS `prezzo`,`l`.`tipo` AS `tipo`,`l`.`titolo` AS `titolo`,`l`.`titolo_per_fattura` AS `titolo_per_fattura`,`l`.`creation_date` AS `creation_date`,`l`.`creation_user` AS `creation_user`,`l`.`last_modification_date` AS `last_modification_date`,`l`.`last_modification_user` AS `last_modification_user`,`l`.`deletion_date` AS `deletion_date`,`l`.`deletion_user` AS `deletion_user`,`l`.`deletion_flag` AS `deletion_flag`,`l`.`activation_flag` AS `activation_flag`,`l`.`ID_DatiFatturazione_listino` AS `ID_DatiFatturazione_listino`,`l`.`ID_Metapratica_in_listini` AS `ID_Metapratica_in_listini` from ((`ItemListino` `l` join `DatiFatturazione` `f` on((`l`.`ID_DatiFatturazione_listino` = `f`.`ID`))) join `Cliente` `c` on(((`f`.`ID_Cliente_dati_fatturazione` = `c`.`ID`) and (not(exists(select 1 AS `1` from `Pratica` `p` where ((`p`.`ID_Cliente_pratiche` = `c`.`ID`) and (`p`.`titolo` = `l`.`titolo`) and (`p`.`deletion_flag`=0)  and (`p`.`anno_contabile` = (select `d`.`sd_value` AS `sd_value` from `_system_decode` `d` where ((`d`.`sd_class` = 207) and (`d`.`sd_description` = (select year(curdate()) AS `Year(CURDATE())`))))))))))));
-- --------------------------------------------------------

--
-- Struttura per la vista `VW_LavoroSuAttivita_report`
--
DROP TABLE IF EXISTS `VW_LavoroSuAttivita_report`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_LavoroSuAttivita_report` AS select `LavoroSuAttivita`.`ID` AS `ID`,cast(`LavoroSuAttivita`.`data` as date) AS `data`,`LavoroSuAttivita`.`durata_minuti` AS `durata_minuti`,`LavoroSuAttivita`.`diario` AS `diario`,`LavoroSuAttivita`.`allegato` AS `allegato`,`LavoroSuAttivita`.`creation_date` AS `creation_date`,`LavoroSuAttivita`.`creation_user` AS `creation_user`,`LavoroSuAttivita`.`last_modification_date` AS `last_modification_date`,`LavoroSuAttivita`.`last_modification_user` AS `last_modification_user`,`LavoroSuAttivita`.`deletion_date` AS `deletion_date`,`LavoroSuAttivita`.`deletion_user` AS `deletion_user`,`LavoroSuAttivita`.`deletion_flag` AS `deletion_flag`,`LavoroSuAttivita`.`activation_flag` AS `activation_flag`,`LavoroSuAttivita`.`ID_Attivita_sessioni_di_lavoro` AS `ID_Attivita_sessioni_di_lavoro` from `LavoroSuAttivita` order by cast(`LavoroSuAttivita`.`data` as date);

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_LavoroSuAttivita_ultime_sessioni`
--
DROP TABLE IF EXISTS `VW_LavoroSuAttivita_ultime_sessioni`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_LavoroSuAttivita_ultime_sessioni` AS select `l`.`ID` AS `ID`,`l`.`data` AS `data`,`l`.`durata_minuti` AS `durata_minuti`,`l`.`diario` AS `diario`,`l`.`allegato` AS `allegato`,`l`.`creation_date` AS `creation_date`,`l`.`creation_user` AS `creation_user`,`l`.`last_modification_date` AS `last_modification_date`,`l`.`last_modification_user` AS `last_modification_user`,`l`.`deletion_date` AS `deletion_date`,`l`.`deletion_user` AS `deletion_user`,`l`.`deletion_flag` AS `deletion_flag`,`l`.`activation_flag` AS `activation_flag`,`l`.`ID_Attivita_sessioni_di_lavoro` AS `ID_Attivita_sessioni_di_lavoro` from (`LavoroSuAttivita` `l` join `Attivita` `a` on((`l`.`ID_Attivita_sessioni_di_lavoro` = `a`.`ID`))) order by `l`.`data` desc limit 0,50;

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_Pratica_Dormiente`
--
DROP TABLE IF EXISTS `VW_Pratica_Dormiente`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_Pratica_Dormiente` AS select `Pratica`.`ID` AS `ID`,`Pratica`.`tipo` AS `tipo`,`Pratica`.`codice` AS `codice`,`Pratica`.`titolo` AS `titolo`,`Pratica`.`note` AS `note`,`Pratica`.`data_chiusura` AS `data_chiusura`,`Pratica`.`stato` AS `stato`,`Pratica`.`anno_contabile` AS `anno_contabile`,`Pratica`.`prezzo` AS `prezzo`,`Pratica`.`titolo_per_fattura` AS `titolo_per_fattura`,`Pratica`.`creation_date` AS `creation_date`,`Pratica`.`creation_user` AS `creation_user`,`Pratica`.`last_modification_date` AS `last_modification_date`,`Pratica`.`last_modification_user` AS `last_modification_user`,`Pratica`.`deletion_date` AS `deletion_date`,`Pratica`.`deletion_user` AS `deletion_user`,`Pratica`.`deletion_flag` AS `deletion_flag`,`Pratica`.`activation_flag` AS `activation_flag`,`Pratica`.`ID_Cliente_pratiche` AS `ID_Cliente_pratiche`,`Pratica`.`ID_Operatore_responsabile_di_pratiche` AS `ID_Operatore_responsabile_di_pratiche` from `Pratica` where ((`Pratica`.`stato` = 0) and ((`Pratica`.`tipo` = 1) or (`Pratica`.`tipo` = 2)) and ((`Pratica`.`last_modification_date` + interval 2 month) < curdate()) and (`Pratica`.`deletion_flag` = 0));

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_Pratiche_chiuse_senza_data_chiusura_2013`
--
DROP TABLE IF EXISTS `VW_Pratiche_chiuse_senza_data_chiusura_2013`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `VW_Pratiche_chiuse_senza_data_chiusura_2013` AS select `Pratica`.`ID` AS `ID`,`Pratica`.`tipo` AS `tipo`,`Pratica`.`codice` AS `codice`,`Pratica`.`titolo` AS `titolo`,`Pratica`.`note` AS `note`,`Pratica`.`data_chiusura` AS `data_chiusura`,`Pratica`.`stato` AS `stato`,`Pratica`.`anno_contabile` AS `anno_contabile`,`Pratica`.`prezzo` AS `prezzo`,`Pratica`.`titolo_per_fattura` AS `titolo_per_fattura`,`Pratica`.`creation_date` AS `creation_date`,`Pratica`.`creation_user` AS `creation_user`,`Pratica`.`last_modification_date` AS `last_modification_date`,`Pratica`.`last_modification_user` AS `last_modification_user`,`Pratica`.`deletion_date` AS `deletion_date`,`Pratica`.`deletion_user` AS `deletion_user`,`Pratica`.`deletion_flag` AS `deletion_flag`,`Pratica`.`activation_flag` AS `activation_flag`,`Pratica`.`ID_Cliente_pratiche` AS `ID_Cliente_pratiche`,`Pratica`.`ID_Operatore_responsabile_di_pratiche` AS `ID_Operatore_responsabile_di_pratiche`,`Pratica`.`ID_Metapratica_pratiche_generate` AS `ID_Metapratica_pratiche_generate` from `Pratica` where (isnull(`Pratica`.`data_chiusura`) and (`Pratica`.`stato` = 1) and (`Pratica`.`anno_contabile` = 3) and (`Pratica`.`deletion_flag` = 0));

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_Pratiche_chiuse_senza_data_chiusura_2014`
--
DROP TABLE IF EXISTS `VW_Pratiche_chiuse_senza_data_chiusura_2014`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `VW_Pratiche_chiuse_senza_data_chiusura_2014` AS select `Pratica`.`ID` AS `ID`,`Pratica`.`tipo` AS `tipo`,`Pratica`.`codice` AS `codice`,`Pratica`.`titolo` AS `titolo`,`Pratica`.`note` AS `note`,`Pratica`.`data_chiusura` AS `data_chiusura`,`Pratica`.`stato` AS `stato`,`Pratica`.`anno_contabile` AS `anno_contabile`,`Pratica`.`prezzo` AS `prezzo`,`Pratica`.`titolo_per_fattura` AS `titolo_per_fattura`,`Pratica`.`creation_date` AS `creation_date`,`Pratica`.`creation_user` AS `creation_user`,`Pratica`.`last_modification_date` AS `last_modification_date`,`Pratica`.`last_modification_user` AS `last_modification_user`,`Pratica`.`deletion_date` AS `deletion_date`,`Pratica`.`deletion_user` AS `deletion_user`,`Pratica`.`deletion_flag` AS `deletion_flag`,`Pratica`.`activation_flag` AS `activation_flag`,`Pratica`.`ID_Cliente_pratiche` AS `ID_Cliente_pratiche`,`Pratica`.`ID_Operatore_responsabile_di_pratiche` AS `ID_Operatore_responsabile_di_pratiche`,`Pratica`.`ID_Metapratica_pratiche_generate` AS `ID_Metapratica_pratiche_generate` from `Pratica` where (isnull(`Pratica`.`data_chiusura`) and (`Pratica`.`stato` = 1) and (`Pratica`.`anno_contabile` = 4) and (`Pratica`.`deletion_flag` = 0));

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_ProForma_Iva`
--
DROP TABLE IF EXISTS `VW_ProForma_Iva`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_ProForma_Iva` AS select `p`.`ID` AS `ID`,(((`p`.`importo_iva_0` * (select `_system_decode`.`sd_notes` AS `sd_notes` from `_system_decode` where ((`_system_decode`.`sd_class` = 232) and (`_system_decode`.`sd_value` = `p`.`iva_0`)))) / 100) + ((`p`.`importo_iva_0` * `p`.`iva_0`) / 100)) AS `iva_0`,((`p`.`importo_iva_1` * (select `_system_decode`.`sd_notes` AS `sd_notes` from `_system_decode` where ((`_system_decode`.`sd_class` = 232) and (`_system_decode`.`sd_value` = `p`.`iva_0`)))) / 100) AS `iva_1`,((`p`.`importo_iva_2` * (select `_system_decode`.`sd_notes` AS `sd_notes` from `_system_decode` where ((`_system_decode`.`sd_class` = 232) and (`_system_decode`.`sd_value` = `p`.`iva_0`)))) / 100) AS `iva_2`,((`p`.`importo_iva_3` * (select `_system_decode`.`sd_notes` AS `sd_notes` from `_system_decode` where ((`_system_decode`.`sd_class` = 232) and (`_system_decode`.`sd_value` = `p`.`iva_0`)))) / 100) AS `iva_3`,((`p`.`importo_iva_4` * (select `_system_decode`.`sd_notes` AS `sd_notes` from `_system_decode` where ((`_system_decode`.`sd_class` = 232) and (`_system_decode`.`sd_value` = `p`.`iva_0`)))) / 100) AS `iva_4` from `VW_ProForma_Totale_Imponibile` `p`;

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_ProForma_Max_Numero`
--
DROP TABLE IF EXISTS `VW_ProForma_Max_Numero`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_ProForma_Max_Numero` AS select max(`ProForma`.`numero`) AS `numero`,1 AS `ID`,`ProForma`.`anno_contabile` AS `anno_contabile`,(select `_system_decode`.`sd_description` from `_system_decode` where ((`_system_decode`.`sd_value` = `ProForma`.`anno_contabile`) and (`_system_decode`.`sd_class` = 207))) AS `note`,NULL AS `data`,NULL AS `creation_date`,NULL AS `creation_user`,0 AS `deletion_flag`,1 AS `activation_flag` from `ProForma` where (`ProForma`.`deletion_flag` = 0) group by `ProForma`.`anno_contabile`;

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_ProForma_Max_Numero_Contabilis`
--
DROP TABLE IF EXISTS `VW_ProForma_Max_Numero_Contabilis`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_ProForma_Max_Numero_Contabilis` AS select max(`ProForma`.`numero`) AS `numero`,1 AS `ID`,`ProForma`.`anno_contabile` AS `anno_contabile`,(select `_system_decode`.`sd_description` from `_system_decode` where ((`_system_decode`.`sd_value` = `ProForma`.`anno_contabile`) and (`_system_decode`.`sd_class` = 207))) AS `note`,NULL AS `data`,NULL AS `creation_date`,NULL AS `creation_user`,0 AS `deletion_flag`,1 AS `activation_flag` from `ProForma` where ((`ProForma`.`deletion_flag` = 0) and (`ProForma`.`competenza` = 0)) group by `ProForma`.`anno_contabile`;

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_ProForma_Max_Numero_Studio`
--
DROP TABLE IF EXISTS `VW_ProForma_Max_Numero_Studio`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_ProForma_Max_Numero_Studio` AS select max(`ProForma`.`numero`) AS `numero`,1 AS `ID`,`ProForma`.`anno_contabile` AS `anno_contabile`,(select `_system_decode`.`sd_description` from `_system_decode` where ((`_system_decode`.`sd_value` = `ProForma`.`anno_contabile`) and (`_system_decode`.`sd_class` = 207))) AS `note`,NULL AS `data`,NULL AS `creation_date`,NULL AS `creation_user`,0 AS `deletion_flag`,1 AS `activation_flag` from `ProForma` where ((`ProForma`.`deletion_flag` = 0) and (`ProForma`.`competenza` = 1)) group by `ProForma`.`anno_contabile`;

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_ProForma_Ritenuta_Acconto`
--
DROP TABLE IF EXISTS `VW_ProForma_Ritenuta_Acconto`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_ProForma_Ritenuta_Acconto` AS select `p`.`ID` AS `ID`,(((`p`.`ra` * (select `_system_module_parameter`.`smp_value` AS `smp_value` from `_system_module_parameter` where (`_system_module_parameter`.`smp_name` = 'RITENUTA_ACCONTO'))) / 100) * ((((`t`.`importo_iva_0` + `t`.`importo_iva_1`) + `t`.`importo_iva_2`) + `t`.`importo_iva_3`) + `t`.`importo_iva_4`)) AS `importo` from (`VW_ProForma_Somma_Voci` `t` join `ProForma` `p` on((`p`.`ID` = `t`.`ID`)));

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_ProForma_Rivalsa_Previdenziale`
--
DROP TABLE IF EXISTS `VW_ProForma_Rivalsa_Previdenziale`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_ProForma_Rivalsa_Previdenziale` AS select `p`.`ID` AS `ID`,(((((((`v`.`importo_iva_0` + `v`.`importo_iva_1`) + `v`.`importo_iva_2`) + `v`.`importo_iva_3`) + `v`.`importo_iva_4`) * `p`.`riv_prev`) * (select `_system_module_parameter`.`smp_value` AS `smp_value` from `_system_module_parameter` where (`_system_module_parameter`.`smp_name` = 'RIVALSA_PREVIDENZIALE'))) / 100) AS `importo` from (`VW_ProForma_Somma_Voci` `v` join `ProForma` `p` on((`v`.`ID` = `p`.`ID`)));

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_Proforma_Sollecito_1`
--
DROP TABLE IF EXISTS `VW_Proforma_Sollecito_1`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_Proforma_Sollecito_1` AS select `p`.`ID` AS `ID`,`p`.`numero` AS `numero`,`p`.`data` AS `data`,`p`.`competenza` AS `competenza`,`p`.`non_incassabile` AS `non_incassabile`,`p`.`spese_anticipate_fattura` AS `spese_anticipate_fattura`,`p`.`spese_anticipate_desc` AS `spese_anticipate_desc`,`p`.`riv_prev` AS `riv_prev`,`p`.`ra` AS `ra`,`p`.`data_pagamento` AS `data_pagamento`,`p`.`note` AS `note`,`p`.`creation_date` AS `creation_date`,`p`.`creation_user` AS `creation_user`,`p`.`last_modification_date` AS `last_modification_date`,`p`.`last_modification_user` AS `last_modification_user`,`p`.`deletion_date` AS `deletion_date`,`p`.`deletion_user` AS `deletion_user`,`p`.`deletion_flag` AS `deletion_flag`,`p`.`activation_flag` AS `activation_flag`,`p`.`ID_Cliente_proforma` AS `ID_Cliente_proforma`,`p`.`stato_proforma` AS `stato_proforma`,`p`.`anno_contabile` AS `anno_contabile` from `ProForma` `p` where ((isnull(`p`.`data_pagamento`) or (`p`.`data_pagamento` = '')) and (`p`.`stato_proforma` = 2) and (`p`.`non_incassabile` = 0) and (not(exists(select 1 AS `1` from `SollecitoPagamento` `s` where ((`s`.`ID_ProForma_solleciti_pagamento` = `p`.`ID`) and (`s`.`deletion_flag` = 0))))) and ((to_days(curdate()) - to_days((`p`.`data` + interval 45 day))) > 0));

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_Proforma_Sollecito_2`
--
DROP TABLE IF EXISTS `VW_Proforma_Sollecito_2`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_Proforma_Sollecito_2` AS select `p`.`ID` AS `ID`,`p`.`numero` AS `numero`,`p`.`data` AS `data`,`p`.`competenza` AS `competenza`,`p`.`non_incassabile` AS `non_incassabile`,`p`.`spese_anticipate_fattura` AS `spese_anticipate_fattura`,`p`.`spese_anticipate_desc` AS `spese_anticipate_desc`,`p`.`riv_prev` AS `riv_prev`,`p`.`ra` AS `ra`,`p`.`data_pagamento` AS `data_pagamento`,`p`.`note` AS `note`,`p`.`creation_date` AS `creation_date`,`p`.`creation_user` AS `creation_user`,`p`.`last_modification_date` AS `last_modification_date`,`p`.`last_modification_user` AS `last_modification_user`,`p`.`deletion_date` AS `deletion_date`,`p`.`deletion_user` AS `deletion_user`,`p`.`deletion_flag` AS `deletion_flag`,`p`.`activation_flag` AS `activation_flag`,`p`.`ID_Cliente_proforma` AS `ID_Cliente_proforma`,`p`.`stato_proforma` AS `stato_proforma`,`p`.`anno_contabile` AS `anno_contabile` from `ProForma` `p` where ((isnull(`p`.`data_pagamento`) or (`p`.`data_pagamento` = '')) and (`p`.`stato_proforma` = 2) and (`p`.`non_incassabile` = 0) and exists(select 1 from `SollecitoPagamento` `s` where ((`s`.`ID_ProForma_solleciti_pagamento` = `p`.`ID`) and (`s`.`numero_sollecito` = 0) and (`s`.`deletion_flag` = 0) and (`s`.`data_spedizione` is not null) and ((to_days(curdate()) - to_days((`s`.`data_spedizione` + interval 15 day))) > 0))) and (not(exists(select 1 from `SollecitoPagamento` `s` where ((`s`.`ID_ProForma_solleciti_pagamento` = `p`.`ID`) and (`s`.`numero_sollecito` <> 0) and (`s`.`deletion_flag` = 0))))) and ((to_days(curdate()) - to_days((`p`.`data` + interval 60 day))) > 0));

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_Proforma_Sollecito_3`
--
DROP TABLE IF EXISTS `VW_Proforma_Sollecito_3`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_Proforma_Sollecito_3` AS select `p`.`ID` AS `ID`,`p`.`numero` AS `numero`,`p`.`data` AS `data`,`p`.`competenza` AS `competenza`,`p`.`non_incassabile` AS `non_incassabile`,`p`.`spese_anticipate_fattura` AS `spese_anticipate_fattura`,`p`.`spese_anticipate_desc` AS `spese_anticipate_desc`,`p`.`riv_prev` AS `riv_prev`,`p`.`ra` AS `ra`,`p`.`data_pagamento` AS `data_pagamento`,`p`.`note` AS `note`,`p`.`creation_date` AS `creation_date`,`p`.`creation_user` AS `creation_user`,`p`.`last_modification_date` AS `last_modification_date`,`p`.`last_modification_user` AS `last_modification_user`,`p`.`deletion_date` AS `deletion_date`,`p`.`deletion_user` AS `deletion_user`,`p`.`deletion_flag` AS `deletion_flag`,`p`.`activation_flag` AS `activation_flag`,`p`.`ID_Cliente_proforma` AS `ID_Cliente_proforma`,`p`.`stato_proforma` AS `stato_proforma`,`p`.`anno_contabile` AS `anno_contabile` from `ProForma` `p` where ((isnull(`p`.`data_pagamento`) or (`p`.`data_pagamento` = '')) and (`p`.`stato_proforma` = 2) and (`p`.`non_incassabile` = 0) and exists(select 1 from `SollecitoPagamento` `s` where ((`s`.`ID_ProForma_solleciti_pagamento` = `p`.`ID`) and (`s`.`numero_sollecito` = 0) and (`s`.`deletion_flag` = 0))) and exists(select 1 from `SollecitoPagamento` `s` where ((`s`.`ID_ProForma_solleciti_pagamento` = `p`.`ID`) and (`s`.`numero_sollecito` = 1) and (`s`.`deletion_flag` = 0) and (`s`.`data_spedizione` is not null) and ((to_days(curdate()) - to_days((`s`.`data_spedizione` + interval 15 day))) > 0))) and (not(exists(select 1 from `SollecitoPagamento` `s` where ((`s`.`ID_ProForma_solleciti_pagamento` = `p`.`ID`) and (`s`.`numero_sollecito` = 2) and (`s`.`deletion_flag` = 0))))) and (not(exists(select 1 from `SollecitoPagamento` `s` where ((`s`.`ID_ProForma_solleciti_pagamento` = `p`.`ID`) and (`s`.`numero_sollecito` = 3) and (`s`.`deletion_flag` = 0))))) and ((to_days(curdate()) - to_days((`p`.`data` + interval 90 day))) > 0));

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_ProForma_Somma_Voci`
--
DROP TABLE IF EXISTS `VW_ProForma_Somma_Voci`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_ProForma_Somma_Voci` AS select `p`.`ID` AS `ID`,coalesce(`p`.`importo_iva_0`,0) AS `importo_iva_0`,coalesce(`p`.`iva_0`,0) AS `iva_0`,coalesce(`p`.`importo_iva_1`,0) AS `importo_iva_1`,coalesce(`p`.`importo_iva_2`,0) AS `importo_iva_2`,coalesce(`p`.`importo_iva_3`,0) AS `importo_iva_3`,coalesce(`p`.`importo_iva_4`,0) AS `importo_iva_4` from `VW_ProForma_Somma_Voci_tmp` `p`;

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_ProForma_Somma_Voci_tmp`
--
DROP TABLE IF EXISTS `VW_ProForma_Somma_Voci_tmp`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_ProForma_Somma_Voci_tmp` AS select `p`.`ID` AS `ID`,(select sum(`v`.`importo`) AS `sum(v.importo)` from `VoceFattura` `v` where ((`v`.`deletion_flag` = 0) and (`v`.`iva` = 0) and (`v`.`ID_ProForma_voci_proforma` = `p`.`ID`))) AS `importo_iva_0`,(select max(`v`.`iva`) AS `max(v.iva)` from `VoceFattura` `v` where ((`v`.`deletion_flag` = 0) and (`v`.`ID_ProForma_voci_proforma` = `p`.`ID`))) AS `iva_0`,(select sum(`v`.`importo`) AS `sum(v.importo)` from `VoceFattura` `v` where ((`v`.`deletion_flag` = 0) and (`v`.`iva` = 1) and (`v`.`ID_ProForma_voci_proforma` = `p`.`ID`))) AS `importo_iva_1`,(select sum(`v`.`importo`) AS `sum(v.importo)` from `VoceFattura` `v` where ((`v`.`deletion_flag` = 0) and (`v`.`iva` = 2) and (`v`.`ID_ProForma_voci_proforma` = `p`.`ID`))) AS `importo_iva_2`,(select sum(`v`.`importo`) AS `sum(v.importo)` from `VoceFattura` `v` where ((`v`.`deletion_flag` = 0) and (`v`.`iva` = 3) and (`v`.`ID_ProForma_voci_proforma` = `p`.`ID`))) AS `importo_iva_3`,(select sum(`v`.`importo`) AS `sum(v.importo)` from `VoceFattura` `v` where ((`v`.`deletion_flag` = 0) and (`v`.`iva` >= 4) and (`v`.`ID_ProForma_voci_proforma` = `p`.`ID`))) AS `importo_iva_4` from `ProForma` `p`;

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_ProForma_Totale_Da_Pagare`
--
DROP TABLE IF EXISTS `VW_ProForma_Totale_Da_Pagare`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_ProForma_Totale_Da_Pagare` AS select `t`.`ID` AS `ID`,(`t`.`importo` - `r`.`importo`) AS `importo` from (`VW_ProForma_Totale_ProForma` `t` join `VW_ProForma_Ritenuta_Acconto` `r` on((`r`.`ID` = `t`.`ID`)));

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_ProForma_Totale_Imponibile`
--
DROP TABLE IF EXISTS `VW_ProForma_Totale_Imponibile`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_ProForma_Totale_Imponibile` AS select `p`.`ID` AS `ID`,(`v`.`importo_iva_0` + (((`v`.`importo_iva_0` * `p`.`riv_prev`) * (select `_system_module_parameter`.`smp_value` AS `smp_value` from `_system_module_parameter` where (`_system_module_parameter`.`smp_name` = 'RIVALSA_PREVIDENZIALE'))) / 100)) AS `importo_iva_0`,`v`.`iva_0` AS `iva_0`,(`v`.`importo_iva_1` + (((`v`.`importo_iva_1` * `p`.`riv_prev`) * (select `_system_module_parameter`.`smp_value` AS `smp_value` from `_system_module_parameter` where (`_system_module_parameter`.`smp_name` = 'RIVALSA_PREVIDENZIALE'))) / 100)) AS `importo_iva_1`,(`v`.`importo_iva_2` + (((`v`.`importo_iva_2` * `p`.`riv_prev`) * (select `_system_module_parameter`.`smp_value` AS `smp_value` from `_system_module_parameter` where (`_system_module_parameter`.`smp_name` = 'RIVALSA_PREVIDENZIALE'))) / 100)) AS `importo_iva_2`,(`v`.`importo_iva_3` + (((`v`.`importo_iva_3` * `p`.`riv_prev`) * (select `_system_module_parameter`.`smp_value` AS `smp_value` from `_system_module_parameter` where (`_system_module_parameter`.`smp_name` = 'RIVALSA_PREVIDENZIALE'))) / 100)) AS `importo_iva_3`,(`v`.`importo_iva_4` + (((`v`.`importo_iva_4` * `p`.`riv_prev`) * (select `_system_module_parameter`.`smp_value` AS `smp_value` from `_system_module_parameter` where (`_system_module_parameter`.`smp_name` = 'RIVALSA_PREVIDENZIALE'))) / 100)) AS `importo_iva_4` from (`VW_ProForma_Somma_Voci` `v` join `ProForma` `p` on((`v`.`ID` = `p`.`ID`)));

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_ProForma_Totale_ProForma`
--
DROP TABLE IF EXISTS `VW_ProForma_Totale_ProForma`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_ProForma_Totale_ProForma` AS select `p`.`ID` AS `ID`,((((((`t`.`importo_iva_0` + `i`.`iva_0`) + (`t`.`importo_iva_1` + `i`.`iva_1`)) + (`t`.`importo_iva_2` + `i`.`iva_2`)) + (`t`.`importo_iva_3` + `i`.`iva_3`)) + (`t`.`importo_iva_4` + `i`.`iva_4`)) + `p`.`spese_anticipate_fattura`) AS `importo` from ((`VW_ProForma_Totale_Imponibile` `t` join `VW_ProForma_Iva` `i` on((`t`.`ID` = `i`.`ID`))) join `ProForma` `p` on((`p`.`ID` = `t`.`ID`)));

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_ProForma_Totali`
--
DROP TABLE IF EXISTS `VW_ProForma_Totali`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_ProForma_Totali` AS select `p`.`ID` AS `ID`,`p`.`numero` AS `numero`,`p`.`data` AS `data`,`p`.`data_pagamento` AS `data_pagamento`,`p`.`competenza` AS `competenza`,`p`.`ID_Cliente_proforma` AS `ID_Cliente_proforma`,`p`.`anno_contabile` AS `anno_contabile`,`p`.`spese_anticipate_desc` AS `spese_anticipate_desc`,`p`.`stato_proforma` AS `stato_proforma`,`p`.`non_incassabile` AS `non_incassabile`,`v2`.`importo` AS `Rivalsa_Previdenziale`,`v3`.`importo_iva_0` AS `Totale_Imponibile_0`,`v3`.`importo_iva_1` AS `Totale_Imponibile_1`,`v3`.`importo_iva_2` AS `Totale_Imponibile_2`,`v3`.`importo_iva_3` AS `Totale_Imponibile_3`,`v3`.`importo_iva_4` AS `Totale_Imponibile_4`,`v4`.`iva_0` AS `Iva_0`,`v4`.`iva_1` AS `Iva_1`,`v4`.`iva_2` AS `Iva_2`,`v4`.`iva_3` AS `Iva_3`,`v4`.`iva_4` AS `Iva_4`,`p`.`spese_anticipate_fattura` AS `Spese_Escluse_Da_Imponibile`,`v5`.`importo` AS `Totale_Fattura`,`v6`.`importo` AS `Ritenuta_Acconto`,`v7`.`importo` AS `Totale_Da_Pagare`,`p`.`creation_date` AS `creation_date`,`p`.`creation_user` AS `creation_user`,`p`.`last_modification_date` AS `last_modification_date`,`p`.`last_modification_user` AS `last_modification_user`,`p`.`deletion_date` AS `deletion_date`,`p`.`deletion_user` AS `deletion_user`,`p`.`deletion_flag` AS `deletion_flag`,`p`.`activation_flag` AS `activation_flag` from ((((((`ProForma` `p` join `VW_ProForma_Rivalsa_Previdenziale` `v2` on((`v2`.`ID` = `p`.`ID`))) join `VW_ProForma_Totale_Imponibile` `v3` on((`v3`.`ID` = `p`.`ID`))) join `VW_ProForma_Iva` `v4` on((`v4`.`ID` = `p`.`ID`))) join `VW_ProForma_Totale_ProForma` `v5` on((`v5`.`ID` = `p`.`ID`))) join `VW_ProForma_Ritenuta_Acconto` `v6` on((`v6`.`ID` = `p`.`ID`))) join `VW_ProForma_Totale_Da_Pagare` `v7` on((`v7`.`ID` = `p`.`ID`)));

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_Scadenze_OneOff_3mAhead`
--
DROP TABLE IF EXISTS `VW_Scadenze_OneOff_3mAhead`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_Scadenze_OneOff_3mAhead` AS select `Scadenza`.`ID` AS `ID`,`Scadenza`.`data` AS `data`,`Scadenza`.`oggetto_scadenza` AS `oggetto_scadenza`,`Scadenza`.`descrizione_scadenza` AS `descrizione_scadenza`,`Scadenza`.`preavviso_gg` AS `preavviso_gg`,`Scadenza`.`ID_Pratica_pratica_scadenza` AS `ID_Pratica_pratica_scadenza`,`Scadenza`.`ID_Attivita_attivita_scadenza` AS `ID_Attivita_attivita_scadenza`,`Scadenza`.`ricorrenza` AS `ricorrenza`,`Scadenza`.`forza_sms` AS `forza_sms`,`Scadenza`.`fine_ricorrenza` AS `fine_ricorrenza`,`Scadenza`.`creation_date` AS `creation_date`,`Scadenza`.`creation_user` AS `creation_user`,`Scadenza`.`last_modification_date` AS `last_modification_date`,`Scadenza`.`last_modification_user` AS `last_modification_user`,`Scadenza`.`deletion_date` AS `deletion_date`,`Scadenza`.`deletion_user` AS `deletion_user`,`Scadenza`.`deletion_flag` AS `deletion_flag`,`Scadenza`.`activation_flag` AS `activation_flag`,`Scadenza`.`ID_Cliente_scadenze` AS `ID_Cliente_scadenze`,`Scadenza`.`ID_Operatore_op_scadenze` AS `ID_Operatore_op_scadenze` from `Scadenza` where (((`Scadenza`.`data` + interval -(3) month) = curdate()) and isnull(`Scadenza`.`ricorrenza`) and (`Scadenza`.`deletion_flag` = 0) and (`Scadenza`.`activation_flag` = 1));

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_Scadenze_OneOff_Today`
--
DROP TABLE IF EXISTS `VW_Scadenze_OneOff_Today`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_Scadenze_OneOff_Today` AS select `Scadenza`.`ID` AS `ID`,`Scadenza`.`data` AS `data`,`Scadenza`.`oggetto_scadenza` AS `oggetto_scadenza`,`Scadenza`.`descrizione_scadenza` AS `descrizione_scadenza`,`Scadenza`.`preavviso_gg` AS `preavviso_gg`,`Scadenza`.`ID_Pratica_pratica_scadenza` AS `ID_Pratica_pratica_scadenza`,`Scadenza`.`ID_Attivita_attivita_scadenza` AS `ID_Attivita_attivita_scadenza`,`Scadenza`.`ricorrenza` AS `ricorrenza`,`Scadenza`.`forza_sms` AS `forza_sms`,`Scadenza`.`fine_ricorrenza` AS `fine_ricorrenza`,`Scadenza`.`creation_date` AS `creation_date`,`Scadenza`.`creation_user` AS `creation_user`,`Scadenza`.`last_modification_date` AS `last_modification_date`,`Scadenza`.`last_modification_user` AS `last_modification_user`,`Scadenza`.`deletion_date` AS `deletion_date`,`Scadenza`.`deletion_user` AS `deletion_user`,`Scadenza`.`deletion_flag` AS `deletion_flag`,`Scadenza`.`activation_flag` AS `activation_flag`,`Scadenza`.`ID_Cliente_scadenze` AS `ID_Cliente_scadenze`,`Scadenza`.`ID_Operatore_op_scadenze` AS `ID_Operatore_op_scadenze` from `Scadenza` where (((`Scadenza`.`data` + interval -(`Scadenza`.`preavviso_gg`) day) = curdate()) and isnull(`Scadenza`.`ricorrenza`) and (`Scadenza`.`deletion_flag` = 0) and (`Scadenza`.`activation_flag` = 1));

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_Scadenze_Ricorrenti`
--
DROP TABLE IF EXISTS `VW_Scadenze_Ricorrenti`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_Scadenze_Ricorrenti` AS select `Scadenza`.`ID` AS `ID`,`Scadenza`.`data` AS `data`,`Scadenza`.`oggetto_scadenza` AS `oggetto_scadenza`,`Scadenza`.`descrizione_scadenza` AS `descrizione_scadenza`,`Scadenza`.`preavviso_gg` AS `preavviso_gg`,`Scadenza`.`ID_Pratica_pratica_scadenza` AS `ID_Pratica_pratica_scadenza`,`Scadenza`.`ID_Attivita_attivita_scadenza` AS `ID_Attivita_attivita_scadenza`,`Scadenza`.`ricorrenza` AS `ricorrenza`,`Scadenza`.`forza_sms` AS `forza_sms`,`Scadenza`.`fine_ricorrenza` AS `fine_ricorrenza`,`Scadenza`.`creation_date` AS `creation_date`,`Scadenza`.`creation_user` AS `creation_user`,`Scadenza`.`last_modification_date` AS `last_modification_date`,`Scadenza`.`last_modification_user` AS `last_modification_user`,`Scadenza`.`deletion_date` AS `deletion_date`,`Scadenza`.`deletion_user` AS `deletion_user`,`Scadenza`.`deletion_flag` AS `deletion_flag`,`Scadenza`.`activation_flag` AS `activation_flag`,`Scadenza`.`ID_Cliente_scadenze` AS `ID_Cliente_scadenze`,`Scadenza`.`ID_Operatore_op_scadenze` AS `ID_Operatore_op_scadenze` from `Scadenza` where ((`Scadenza`.`ricorrenza` is not null) and (isnull(`Scadenza`.`fine_ricorrenza`) or (`Scadenza`.`fine_ricorrenza` >= curdate())) and (`Scadenza`.`deletion_flag` = 0) and (`Scadenza`.`activation_flag` = 1));

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_STAT_RIEPILOGO_FATTURA`


/* 27 settembre 2015 */
DROP VIEW IF EXISTS `VW_STAT_TOTALE_FATTURA_ANNO`;
CREATE  VIEW `VW_STAT_TOTALE_FATTURA_ANNO` AS select 1 AS `ID`,
(select `_system_decode`.`sd_description` from `_system_decode` where ((`_system_decode`.`sd_value` = `p`.`anno_contabile`) and (`_system_decode`.`sd_class` = 207))) AS `p1`,
cast(sum(`v`.`importo`) as decimal(9,2)) AS `p2`,
(select `_system_decode`.`sd_description` from `_system_decode` where ((`_system_decode`.`sd_value` = `p`.`competenza`) and (`_system_decode`.`sd_class` = 224))) AS `p3`,
`p`.`anno_contabile` AS `p4`,`p`.`competenza` AS `p5`,NULL AS `p6`,curdate() AS `creation_date`,curdate() AS `last_modification_date`,'admin' AS `creation_user`,'admin' AS `last_modification_user`,0 AS `deletion_flag`,1 AS `activation_flag` from (`Fattura` `p` join `VoceFattura` `v` on((`v`.`ID_Fattura_voci_fattura` = `p`.`ID`))) where ((`p`.`deletion_flag` = 0) and (`p`.`activation_flag` = 1) and (`v`.`deletion_flag` = 0) and (`v`.`activation_flag` = 1)) group by `p`.`anno_contabile`,`p`.`competenza` order by `p`.`anno_contabile`;



DROP VIEW IF EXISTS `VW_STAT_RIEPILOGO_FATTURA`;

CREATE VIEW `VW_STAT_RIEPILOGO_FATTURA` AS select  1 AS `ID`,
(select `_system_decode`.`sd_description` from `_system_decode` where ((`_system_decode`.`sd_value` = `p`.`anno_contabile`) and (`_system_decode`.`sd_class` = 207))) AS `p1`,
(select `c`.`cliente` from `Cliente` `c` where (`c`.`ID` = `p`.`ID_Cliente_fatture`)) AS `p2`,
(select `_system_decode`.`sd_description` from `_system_decode` where ((`_system_decode`.`sd_value` = `p`.`competenza`) and (`_system_decode`.`sd_class` = 224))) AS `p3`,
NULL AS `p4`,
cast(sum(`VoceFattura`.`importo`) as decimal(9,2)) AS `p5`,
((cast(sum(`VoceFattura`.`importo`) as signed) / cast(`t`.`p2` as signed)) * 100) AS `p6`,
curdate() AS `creation_date`,curdate() AS `last_modification_date`,'admin' AS `creation_user`,'admin' AS `last_modification_user`,0 AS `deletion_flag`,1 AS `activation_flag` from
((`Fattura` `p` join `VoceFattura` on((`VoceFattura`.`ID_Fattura_voci_fattura` = `p`.`ID`)))
inner join `VW_STAT_TOTALE_FATTURA_ANNO` `t` on `p`.`anno_contabile` = `t`.`p4` and `p`.`competenza` = `t`.`p5`)
where ((`p`.`deletion_flag` = 0) and (`p`.`activation_flag` = 1)  and (`VoceFattura`.`deletion_flag` = 0)) group by `p`.`ID_Cliente_fatture`,`p`.`anno_contabile` order by (select `_system_decode`.`sd_description` from `_system_decode` where ((`_system_decode`.`sd_value` = `p`.`anno_contabile`) and (`_system_decode`.`sd_class` = 207))),(select `c`.`cliente` from `Cliente` `c` where (`c`.`ID` = `p`.`ID_Cliente_fatture`));
 
-- --------------------------------------------------------

--
-- Struttura per la vista `VW_STAT_RIEPILOGO_PROFORMA`
--
DROP TABLE IF EXISTS `VW_STAT_RIEPILOGO_PROFORMA`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_STAT_RIEPILOGO_PROFORMA` AS select 1 AS `ID`,(select `_system_decode`.`sd_description` from `_system_decode` where ((`_system_decode`.`sd_value` = `p`.`anno_contabile`) and (`_system_decode`.`sd_class` = 207))) AS `p1`,(select `c`.`cliente` from `Cliente` `c` where (`c`.`ID` = `p`.`ID_Cliente_proforma`)) AS `p2`,cast(avg((to_days(`p`.`data_pagamento`) - to_days(`p`.`data`))) as decimal(4,1)) AS `p3`,cast(sum(`VoceFattura`.`importo`) as decimal(9,2)) AS `p4`,((cast(sum(`VoceFattura`.`importo`) as signed) / cast(`t`.`p2` as signed)) * 100) AS `p5`,curdate() AS `creation_date`,curdate() AS `last_modification_date`,'admin' AS `creation_user`,'admin' AS `last_modification_user`,0 AS `deletion_flag`,1 AS `activation_flag` from ((`ProForma` `p` join `VoceFattura` on((`VoceFattura`.`ID_ProForma_voci_proforma` = `p`.`ID`))) join `VW_STAT_TOTALE_PROFORMA_ANNO` `t` on((`p`.`anno_contabile` = `t`.`p3`))) where ((`p`.`deletion_flag` = 0) and (`p`.`activation_flag` = 1)) group by `p`.`ID_Cliente_proforma`,`p`.`anno_contabile` order by (select `_system_decode`.`sd_description` from `_system_decode` where ((`_system_decode`.`sd_value` = `p`.`anno_contabile`) and (`_system_decode`.`sd_class` = 207))),(select `c`.`cliente` from `Cliente` `c` where (`c`.`ID` = `p`.`ID_Cliente_proforma`));

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_STAT_RITARDO_MEDIO_ANNI`
--
DROP TABLE IF EXISTS `VW_STAT_RITARDO_MEDIO_ANNI`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_STAT_RITARDO_MEDIO_ANNI` AS select 1 AS `ID`,(select `_system_decode`.`sd_description` from `_system_decode` where ((`_system_decode`.`sd_value` = `ProForma`.`anno_contabile`) and (`_system_decode`.`sd_class` = 207))) AS `p1`,avg((to_days(`ProForma`.`data_pagamento`) - to_days(`ProForma`.`data`))) AS `p2`,count(0) AS `p3`,NULL AS `p4`,NULL AS `p5`,curdate() AS `creation_date`,curdate() AS `last_modification_date`,'admin' AS `creation_user`,'admin' AS `last_modification_user`,0 AS `deletion_flag`,1 AS `activation_flag` from `ProForma` where ((`ProForma`.`data_pagamento` is not null) and (`ProForma`.`data_pagamento` <> '')) group by `ProForma`.`anno_contabile` order by `ProForma`.`anno_contabile`;

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_STAT_RITARDO_MEDIO_CLIENTI`
--
DROP TABLE IF EXISTS `VW_STAT_RITARDO_MEDIO_CLIENTI`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_STAT_RITARDO_MEDIO_CLIENTI` AS select 1 AS `ID`,(select `c`.`cliente` from `Cliente` `c` where (`c`.`ID` = `ProForma`.`ID_Cliente_proforma`)) AS `p1`,cast(cast(avg((to_days(`ProForma`.`data_pagamento`) - to_days(`ProForma`.`data`))) as decimal(4,1)) as char(10) charset utf8) AS `p2`,count(0) AS `p3`,NULL AS `p4`,NULL AS `p5`,curdate() AS `creation_date`,curdate() AS `last_modification_date`,'admin' AS `creation_user`,'admin' AS `last_modification_user`,0 AS `deletion_flag`,1 AS `activation_flag` from `ProForma` where ((`ProForma`.`data_pagamento` is not null) and (`ProForma`.`data_pagamento` <> '') and (`ProForma`.`deletion_flag` = 0) and (`ProForma`.`activation_flag` = 1)) group by `ProForma`.`ID_Cliente_proforma` union select 1 AS `ID`,(select `c`.`cliente` from `Cliente` `c` where (`c`.`ID` = `ProForma`.`ID_Cliente_proforma`)) AS `p1`,cast('NON PAGATA' as char(10) charset utf8) AS `p2`,count(0) AS `p3`,NULL AS `p4`,NULL AS `p5`,curdate() AS `creation_date`,curdate() AS `last_modification_date`,'admin' AS `creation_user`,'admin' AS `last_modification_user`,0 AS `deletion_flag`,1 AS `activation_flag` from `ProForma` where ((isnull(`ProForma`.`data_pagamento`) or (`ProForma`.`data_pagamento` = '')) and (`ProForma`.`deletion_flag` = 0) and (`ProForma`.`activation_flag` = 1)) group by `ProForma`.`ID_Cliente_proforma` order by `p1`,`p2`;

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_STAT_TOTALE_FATTURA_ANNO`
--
DROP TABLE IF EXISTS `VW_STAT_TOTALE_FATTURA_ANNO`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_STAT_TOTALE_FATTURA_ANNO` AS select 1 AS `ID`,(select `_system_decode`.`sd_description` from `_system_decode` where ((`_system_decode`.`sd_value` = `p`.`anno_contabile`) and (`_system_decode`.`sd_class` = 207))) AS `p1`,cast(sum(`v`.`importo`) as decimal(9,2)) AS `p2`,`p`.`anno_contabile` AS `p3`,NULL AS `p4`,NULL AS `p5`,curdate() AS `creation_date`,curdate() AS `last_modification_date`,'admin' AS `creation_user`,'admin' AS `last_modification_user`,0 AS `deletion_flag`,1 AS `activation_flag` from (`Fattura` `p` join `VoceFattura` `v` on((`v`.`ID_Fattura_voci_fattura` = `p`.`ID`))) where ((`p`.`deletion_flag` = 0) and (`p`.`activation_flag` = 1) and (`v`.`deletion_flag` = 0) and (`v`.`activation_flag` = 1)) group by `p`.`anno_contabile` order by `p`.`anno_contabile`;

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_STAT_TOTALE_PROFORMA_ANNO`
--
DROP TABLE IF EXISTS `VW_STAT_TOTALE_PROFORMA_ANNO`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_STAT_TOTALE_PROFORMA_ANNO` AS select 1 AS `ID`,(select `_system_decode`.`sd_description` from `_system_decode` where ((`_system_decode`.`sd_value` = `p`.`anno_contabile`) and (`_system_decode`.`sd_class` = 207))) AS `p1`,cast(sum(`v`.`importo`) as decimal(9,2)) AS `p2`,`p`.`anno_contabile` AS `p3`,NULL AS `p4`,NULL AS `p5`,curdate() AS `creation_date`,curdate() AS `last_modification_date`,'admin' AS `creation_user`,'admin' AS `last_modification_user`,0 AS `deletion_flag`,1 AS `activation_flag` from (`ProForma` `p` join `VoceFattura` `v` on((`v`.`ID_ProForma_voci_proforma` = `p`.`ID`))) where ((`p`.`deletion_flag` = 0) and (`p`.`activation_flag` = 1) and (`v`.`deletion_flag` = 0) and (`v`.`activation_flag` = 1)) group by (select `_system_decode`.`sd_description` from `_system_decode` where ((`_system_decode`.`sd_value` = `p`.`anno_contabile`) and (`_system_decode`.`sd_class` = 207))) order by (select `_system_decode`.`sd_description` from `_system_decode` where ((`_system_decode`.`sd_value` = `p`.`anno_contabile`) and (`_system_decode`.`sd_class` = 207)));

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_SuperCliente`
--
DROP TABLE IF EXISTS `VW_SuperCliente`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_SuperCliente` AS select distinct `c`.`ID` AS `ID`,`c`.`ID__system_user_inverse_of_account` AS `ID__system_user_inverse_of_account`,`c`.`allegato` AS `allegato`,`c`.`allegato_1` AS `allegato_1`,`c`.`cellulare` AS `cellulare`,`c`.`cliente` AS `cliente`,`c`.`codice_cliente` AS `codice_cliente`,`c`.`codice_fiscale` AS `codice_fiscale`,`c`.`nickname` AS `nickname`,`c`.`email` AS `email`,`c`.`email2` AS `email2`,`c`.`email3` AS `email3`,`c`.`fax` AS `fax`,`c`.`indirizzo` AS `indirizzo`,`c`.`recapito_indirizzo` AS `recapito_indirizzo`,`c`.`comune` AS `comune`,`c`.`recapito_cap` AS `recapito_cap`,`c`.`recapito_comune` AS `recapito_comune`,`c`.`cap` AS `cap`,`c`.`stato_cliente` AS `stato_cliente`,`c`.`cessata_assistenza_il` AS `cessata_assistenza_il`,`c`.`cliente_dal` AS `cliente_dal`,`c`.`partita_iva` AS `partita_iva`,`c`.`note` AS `note`,`c`.`telefono` AS `telefono`,`c`.`creation_date` AS `creation_date`,`c`.`creation_user` AS `creation_user`,`c`.`last_modification_date` AS `last_modification_date`,`c`.`last_modification_user` AS `last_modification_user`,`c`.`deletion_date` AS `deletion_date`,`c`.`deletion_user` AS `deletion_user`,`c`.`deletion_flag` AS `deletion_flag`,`c`.`activation_flag` AS `activation_flag`,`c`.`tipo_sollecito` AS `tipo_sollecito`,`c`.`legale_rappresentante_cognome` AS `legale_rappresentante_cognome`,`c`.`legale_rappresentante_nome` AS `legale_rappresentante_nome`,`c`.`legale_rappresentante_cf` AS `legale_rappresentante_cf`,`c`.`legale_rappresentante_residenza` AS `legale_rappresentante_residenza`,`c`.`ID_Operatore_responsabile_di` AS `ID_Operatore_responsabile_di`,`c`.`ID_ClienteCandidato_diventa_cliente` AS `ID_ClienteCandidato_diventa_cliente`,`c`.`ID_OLD_HG` AS `ID_OLD_HG`,(select `wt`.`tipi_descr` from `VW_Cliente_Tipo` `wt` where (`wt`.`ID` = `c`.`ID`)) AS `tipo_cliente`,`d`.`tipo_cliente` AS `tipo_cliente_machine`,`d`.`codice_ateco` AS `codice_ateco`,`d`.`codice_ateco2` AS `codice_ateco2`,`d`.`codice_ateco3` AS `codice_ateco3`,`d`.`teniamo_noi` AS `teniamo_noi`,`d`.`regime_contabilita` AS `regime_contabilita`,`d`.`regime_iva` AS `regime_iva`,`d`.`iscrizione_registro_imprese` AS `iscrizione_registro_imprese`,`d`.`liquidazione_iva` AS `liquidazione_iva`,`d`.`esercizio_solare` AS `esercizio_solare`,`d`.`esercizio_da` AS `esercizio_da`,`d`.`esercizio_a` AS `esercizio_a`,`d`.`obbligo_iscrizione_inail` AS `obbligo_iscrizione_inail`,`d`.`pat_numero` AS `pat_numero`,`d`.`incarico_a` AS `incarico_a`,`d`.`provincia_registro_imprese` AS `provincia_registro_imprese`,`d`.`sezione_ordinaria` AS `sezione_ordinaria`,`d`.`iscritto_rea` AS `iscritto_rea`,`d`.`sezioni_speciali` AS `sezioni_speciali`,`d`.`unita_locali` AS `unita_locali`,`crm`.`sostituto_imposta` AS `sostituto_imposta`,`crm`.`immobili` AS `immobili`,`crm`.`ici` AS `ici`,`crm`.`note_ici` AS `note_ici`,`crm`.`cassetto_fiscale` AS `cassetto_fiscale`,`crm`.`f24_cumulativo` AS `f24_cumulativo`,`crm`.`antireciclaggio` AS `antireciclaggio`,`crm`.`privacy` AS `privacy`,`crm`.`privacy_acquisita` AS `privacy_acquisita`,`crm`.`conservazione_sostitutiva` AS `conservazione_sostitutiva`,`crm`.`conservazione_sostitutiva_delega_studio` AS `conservazione_sostitutiva_delega_studio`,`crm`.`verifica_validita_firma_digitale` AS `verifica_validita_firma_digitale`,`crm`.`conservazione_sostitutiva_note` AS `conservazione_sostitutiva_note`,`crm`.`conservazione_libro_giornale` AS `conservazione_libro_giornale`,`crm`.`conservazione_libro_giornale_data_versamento_diritti` AS `conservazione_libro_giornale_data_versamento_diritti`,`crm`.`data_invio_impronta_digitale` AS `data_invio_impronta_digitale`,`rp`.`iscrizione` AS `iscrizione`,`rp`.`regime` AS `regime` from (((`Cliente` `c` left join `DatiContabili` `d` on((`c`.`ID` = `d`.`ID_Cliente_dati_contabili`))) left join `DatiCRM` `crm` on((`c`.`ID` = `crm`.`ID_Cliente_dati_crm`))) left join `RegimePrevidenziale` `rp` on((`d`.`ID` = `rp`.`ID_DatiContabili_regimi_previdenziali`))) where (isnull(`c`.`cessata_assistenza_il`) and (`c`.`deletion_flag` = 0)) order by `c`.`cliente`;

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_Workflow`
--
DROP TABLE IF EXISTS `VW_Workflow`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_Workflow` AS select `s`.`ID` AS `ID`,`s`.`workflow_completato_il` AS `workflow_completato_il`,`s`.`scaricato_il` AS `scaricato_il`,`s`.`inviato_il` AS `inviato_il`,`s`.`sollecito_il` AS `sollecito_il`,`s`.`testo_sms` AS `testo_sms`,`s`.`oggetto_mail` AS `oggetto_mail`,`s`.`testo_mail` AS `testo_mail`,`s`.`persistente_area_cliente` AS `persistente_area_cliente`,`s`.`errore` AS `errore`,`s`.`sollecito` AS `sollecito`,`s`.`creation_date` AS `creation_date`,`s`.`creation_user` AS `creation_user`,`s`.`last_modification_date` AS `last_modification_date`,`s`.`last_modification_user` AS `last_modification_user`,`s`.`deletion_date` AS `deletion_date`,`s`.`deletion_user` AS `deletion_user`,`s`.`deletion_flag` AS `deletion_flag`,`s`.`activation_flag` AS `activation_flag`,`s`.`ID_Cliente_inverse_of_cliente_doc` AS `ID_Cliente_inverse_of_cliente_doc`,`s`.`ID_Documento_storia_documento` AS `ID_Documento_storia_documento` from `StoriaDocumento` `s` where ((`s`.`inviato_il` is not null) and (`s`.`deletion_flag` = 0));

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_Workflow_Documenti_AUI`
--
DROP TABLE IF EXISTS `VW_Workflow_Documenti_AUI`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_Workflow_Documenti_AUI` AS select `StoriaDocumento`.`ID` AS `ID`,`StoriaDocumento`.`workflow_completato_il` AS `workflow_completato_il`,`StoriaDocumento`.`scaricato_il` AS `scaricato_il`,`StoriaDocumento`.`inviato_il` AS `inviato_il`,`StoriaDocumento`.`sollecito_il` AS `sollecito_il`,`StoriaDocumento`.`testo_sms` AS `testo_sms`,`StoriaDocumento`.`oggetto_mail` AS `oggetto_mail`,`StoriaDocumento`.`testo_mail` AS `testo_mail`,`StoriaDocumento`.`persistente_area_cliente` AS `persistente_area_cliente`,`StoriaDocumento`.`errore` AS `errore`,`StoriaDocumento`.`sollecito` AS `sollecito`,`StoriaDocumento`.`creation_date` AS `creation_date`,`StoriaDocumento`.`creation_user` AS `creation_user`,`StoriaDocumento`.`last_modification_date` AS `last_modification_date`,`StoriaDocumento`.`last_modification_user` AS `last_modification_user`,`StoriaDocumento`.`deletion_date` AS `deletion_date`,`StoriaDocumento`.`deletion_user` AS `deletion_user`,`StoriaDocumento`.`deletion_flag` AS `deletion_flag`,`StoriaDocumento`.`activation_flag` AS `activation_flag`,`StoriaDocumento`.`ID_Cliente_inverse_of_cliente_doc` AS `ID_Cliente_inverse_of_cliente_doc`,`StoriaDocumento`.`ID_Documento_storia_documento` AS `ID_Documento_storia_documento` from `StoriaDocumento` where `StoriaDocumento`.`ID_Documento_storia_documento` in (select `Documento`.`ID` AS `ID` from `Documento` where (`Documento`.`free_tag` = 'AUI'));

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_Workflow_Documenti_Errore_Invio_Notifica`
--
DROP TABLE IF EXISTS `VW_Workflow_Documenti_Errore_Invio_Notifica`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_Workflow_Documenti_Errore_Invio_Notifica` AS select `StoriaDocumento`.`ID` AS `ID`,`StoriaDocumento`.`workflow_completato_il` AS `workflow_completato_il`,`StoriaDocumento`.`scaricato_il` AS `scaricato_il`,`StoriaDocumento`.`inviato_il` AS `inviato_il`,`StoriaDocumento`.`sollecito_il` AS `sollecito_il`,`StoriaDocumento`.`testo_sms` AS `testo_sms`,`StoriaDocumento`.`oggetto_mail` AS `oggetto_mail`,`StoriaDocumento`.`testo_mail` AS `testo_mail`,`StoriaDocumento`.`persistente_area_cliente` AS `persistente_area_cliente`,`StoriaDocumento`.`errore` AS `errore`,`StoriaDocumento`.`sollecito` AS `sollecito`,`StoriaDocumento`.`creation_date` AS `creation_date`,`StoriaDocumento`.`creation_user` AS `creation_user`,`StoriaDocumento`.`last_modification_date` AS `last_modification_date`,`StoriaDocumento`.`last_modification_user` AS `last_modification_user`,`StoriaDocumento`.`deletion_date` AS `deletion_date`,`StoriaDocumento`.`deletion_user` AS `deletion_user`,`StoriaDocumento`.`deletion_flag` AS `deletion_flag`,`StoriaDocumento`.`activation_flag` AS `activation_flag`,`StoriaDocumento`.`ID_Cliente_inverse_of_cliente_doc` AS `ID_Cliente_inverse_of_cliente_doc`,`StoriaDocumento`.`ID_Documento_storia_documento` AS `ID_Documento_storia_documento` from `StoriaDocumento` where ((`StoriaDocumento`.`errore` is not null) and (`StoriaDocumento`.`errore` <> '') and (`StoriaDocumento`.`deletion_flag` = 0) and (`StoriaDocumento`.`activation_flag` = 1));

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_Workflow_Documenti_Non_Prervenuti_E_Sollecitati`
--
DROP TABLE IF EXISTS `VW_Workflow_Documenti_Non_Prervenuti_E_Sollecitati`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_Workflow_Documenti_Non_Prervenuti_E_Sollecitati` AS select `StoriaDocumento`.`ID` AS `ID`,`StoriaDocumento`.`workflow_completato_il` AS `workflow_completato_il`,`StoriaDocumento`.`scaricato_il` AS `scaricato_il`,`StoriaDocumento`.`inviato_il` AS `inviato_il`,`StoriaDocumento`.`sollecito_il` AS `sollecito_il`,`StoriaDocumento`.`testo_sms` AS `testo_sms`,`StoriaDocumento`.`oggetto_mail` AS `oggetto_mail`,`StoriaDocumento`.`testo_mail` AS `testo_mail`,`StoriaDocumento`.`persistente_area_cliente` AS `persistente_area_cliente`,`StoriaDocumento`.`errore` AS `errore`,`StoriaDocumento`.`sollecito` AS `sollecito`,`StoriaDocumento`.`creation_date` AS `creation_date`,`StoriaDocumento`.`creation_user` AS `creation_user`,`StoriaDocumento`.`last_modification_date` AS `last_modification_date`,`StoriaDocumento`.`last_modification_user` AS `last_modification_user`,`StoriaDocumento`.`deletion_date` AS `deletion_date`,`StoriaDocumento`.`deletion_user` AS `deletion_user`,`StoriaDocumento`.`deletion_flag` AS `deletion_flag`,`StoriaDocumento`.`activation_flag` AS `activation_flag`,`StoriaDocumento`.`ID_Cliente_inverse_of_cliente_doc` AS `ID_Cliente_inverse_of_cliente_doc`,`StoriaDocumento`.`ID_Documento_storia_documento` AS `ID_Documento_storia_documento` from `StoriaDocumento` where (isnull(`StoriaDocumento`.`workflow_completato_il`) and (`StoriaDocumento`.`sollecito` = 2) and (`StoriaDocumento`.`sollecito_il` is not null) and ((`StoriaDocumento`.`inviato_il` + interval 3 day) <= curdate()) and (`StoriaDocumento`.`deletion_flag` = 0) and (`StoriaDocumento`.`activation_flag` = 1));

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_Workflow_Documenti_Non_Prervenuti_Per_Sollecito`
--
DROP TABLE IF EXISTS `VW_Workflow_Documenti_Non_Prervenuti_Per_Sollecito`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_Workflow_Documenti_Non_Prervenuti_Per_Sollecito` AS select `StoriaDocumento`.`ID` AS `ID`,`StoriaDocumento`.`workflow_completato_il` AS `workflow_completato_il`,`StoriaDocumento`.`scaricato_il` AS `scaricato_il`,`StoriaDocumento`.`inviato_il` AS `inviato_il`,`StoriaDocumento`.`sollecito_il` AS `sollecito_il`,`StoriaDocumento`.`testo_sms` AS `testo_sms`,`StoriaDocumento`.`oggetto_mail` AS `oggetto_mail`,`StoriaDocumento`.`testo_mail` AS `testo_mail`,`StoriaDocumento`.`persistente_area_cliente` AS `persistente_area_cliente`,`StoriaDocumento`.`errore` AS `errore`,`StoriaDocumento`.`sollecito` AS `sollecito`,`StoriaDocumento`.`creation_date` AS `creation_date`,`StoriaDocumento`.`creation_user` AS `creation_user`,`StoriaDocumento`.`last_modification_date` AS `last_modification_date`,`StoriaDocumento`.`last_modification_user` AS `last_modification_user`,`StoriaDocumento`.`deletion_date` AS `deletion_date`,`StoriaDocumento`.`deletion_user` AS `deletion_user`,`StoriaDocumento`.`deletion_flag` AS `deletion_flag`,`StoriaDocumento`.`activation_flag` AS `activation_flag`,`StoriaDocumento`.`ID_Cliente_inverse_of_cliente_doc` AS `ID_Cliente_inverse_of_cliente_doc`,`StoriaDocumento`.`ID_Documento_storia_documento` AS `ID_Documento_storia_documento` from `StoriaDocumento` where (isnull(`StoriaDocumento`.`workflow_completato_il`) and (`StoriaDocumento`.`sollecito` = 2) and isnull(`StoriaDocumento`.`sollecito_il`) and ((`StoriaDocumento`.`inviato_il` + interval 3 day) <= curdate()) and (isnull(`StoriaDocumento`.`errore`) or (`StoriaDocumento`.`errore` = '')) and (`StoriaDocumento`.`deletion_flag` = 0) and (`StoriaDocumento`.`activation_flag` = 1));

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_Workflow_Documenti_Non_Scaricati_E_Sollecitati`
--
DROP TABLE IF EXISTS `VW_Workflow_Documenti_Non_Scaricati_E_Sollecitati`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_Workflow_Documenti_Non_Scaricati_E_Sollecitati` AS select `StoriaDocumento`.`ID` AS `ID`,`StoriaDocumento`.`workflow_completato_il` AS `workflow_completato_il`,`StoriaDocumento`.`scaricato_il` AS `scaricato_il`,`StoriaDocumento`.`inviato_il` AS `inviato_il`,`StoriaDocumento`.`sollecito_il` AS `sollecito_il`,`StoriaDocumento`.`testo_sms` AS `testo_sms`,`StoriaDocumento`.`oggetto_mail` AS `oggetto_mail`,`StoriaDocumento`.`testo_mail` AS `testo_mail`,`StoriaDocumento`.`persistente_area_cliente` AS `persistente_area_cliente`,`StoriaDocumento`.`errore` AS `errore`,`StoriaDocumento`.`sollecito` AS `sollecito`,`StoriaDocumento`.`creation_date` AS `creation_date`,`StoriaDocumento`.`creation_user` AS `creation_user`,`StoriaDocumento`.`last_modification_date` AS `last_modification_date`,`StoriaDocumento`.`last_modification_user` AS `last_modification_user`,`StoriaDocumento`.`deletion_date` AS `deletion_date`,`StoriaDocumento`.`deletion_user` AS `deletion_user`,`StoriaDocumento`.`deletion_flag` AS `deletion_flag`,`StoriaDocumento`.`activation_flag` AS `activation_flag`,`StoriaDocumento`.`ID_Cliente_inverse_of_cliente_doc` AS `ID_Cliente_inverse_of_cliente_doc`,`StoriaDocumento`.`ID_Documento_storia_documento` AS `ID_Documento_storia_documento` from `StoriaDocumento` where (isnull(`StoriaDocumento`.`scaricato_il`) and (`StoriaDocumento`.`sollecito` = 1) and (`StoriaDocumento`.`sollecito_il` is not null) and ((`StoriaDocumento`.`inviato_il` + interval 3 day) <= curdate()) and (`StoriaDocumento`.`deletion_flag` = 0) and (`StoriaDocumento`.`activation_flag` = 1));

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_Workflow_Documenti_Non_Scaricati_No_Sollecito`
--
DROP TABLE IF EXISTS `VW_Workflow_Documenti_Non_Scaricati_No_Sollecito`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_Workflow_Documenti_Non_Scaricati_No_Sollecito` AS select `StoriaDocumento`.`ID` AS `ID`,`StoriaDocumento`.`workflow_completato_il` AS `workflow_completato_il`,`StoriaDocumento`.`scaricato_il` AS `scaricato_il`,`StoriaDocumento`.`inviato_il` AS `inviato_il`,`StoriaDocumento`.`sollecito_il` AS `sollecito_il`,`StoriaDocumento`.`testo_sms` AS `testo_sms`,`StoriaDocumento`.`oggetto_mail` AS `oggetto_mail`,`StoriaDocumento`.`testo_mail` AS `testo_mail`,`StoriaDocumento`.`persistente_area_cliente` AS `persistente_area_cliente`,`StoriaDocumento`.`errore` AS `errore`,`StoriaDocumento`.`sollecito` AS `sollecito`,`StoriaDocumento`.`creation_date` AS `creation_date`,`StoriaDocumento`.`creation_user` AS `creation_user`,`StoriaDocumento`.`last_modification_date` AS `last_modification_date`,`StoriaDocumento`.`last_modification_user` AS `last_modification_user`,`StoriaDocumento`.`deletion_date` AS `deletion_date`,`StoriaDocumento`.`deletion_user` AS `deletion_user`,`StoriaDocumento`.`deletion_flag` AS `deletion_flag`,`StoriaDocumento`.`activation_flag` AS `activation_flag`,`StoriaDocumento`.`ID_Cliente_inverse_of_cliente_doc` AS `ID_Cliente_inverse_of_cliente_doc`,`StoriaDocumento`.`ID_Documento_storia_documento` AS `ID_Documento_storia_documento` from `StoriaDocumento` where (isnull(`StoriaDocumento`.`scaricato_il`) and (`StoriaDocumento`.`sollecito` = 0) and (`StoriaDocumento`.`deletion_flag` = 0) and (`StoriaDocumento`.`activation_flag` = 1));

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_Workflow_Documenti_Non_Scaricati_Per_Sollecito`
--
DROP TABLE IF EXISTS `VW_Workflow_Documenti_Non_Scaricati_Per_Sollecito`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_Workflow_Documenti_Non_Scaricati_Per_Sollecito` AS select `StoriaDocumento`.`ID` AS `ID`,`StoriaDocumento`.`workflow_completato_il` AS `workflow_completato_il`,`StoriaDocumento`.`scaricato_il` AS `scaricato_il`,`StoriaDocumento`.`inviato_il` AS `inviato_il`,`StoriaDocumento`.`sollecito_il` AS `sollecito_il`,`StoriaDocumento`.`testo_sms` AS `testo_sms`,`StoriaDocumento`.`oggetto_mail` AS `oggetto_mail`,`StoriaDocumento`.`testo_mail` AS `testo_mail`,`StoriaDocumento`.`persistente_area_cliente` AS `persistente_area_cliente`,`StoriaDocumento`.`errore` AS `errore`,`StoriaDocumento`.`sollecito` AS `sollecito`,`StoriaDocumento`.`creation_date` AS `creation_date`,`StoriaDocumento`.`creation_user` AS `creation_user`,`StoriaDocumento`.`last_modification_date` AS `last_modification_date`,`StoriaDocumento`.`last_modification_user` AS `last_modification_user`,`StoriaDocumento`.`deletion_date` AS `deletion_date`,`StoriaDocumento`.`deletion_user` AS `deletion_user`,`StoriaDocumento`.`deletion_flag` AS `deletion_flag`,`StoriaDocumento`.`activation_flag` AS `activation_flag`,`StoriaDocumento`.`ID_Cliente_inverse_of_cliente_doc` AS `ID_Cliente_inverse_of_cliente_doc`,`StoriaDocumento`.`ID_Documento_storia_documento` AS `ID_Documento_storia_documento` from `StoriaDocumento` where (isnull(`StoriaDocumento`.`scaricato_il`) and (`StoriaDocumento`.`sollecito` = 1) and isnull(`StoriaDocumento`.`sollecito_il`) and ((`StoriaDocumento`.`inviato_il` + interval 3 day) <= curdate()) and (isnull(`StoriaDocumento`.`errore`) or (`StoriaDocumento`.`errore` = '')) and (`StoriaDocumento`.`deletion_flag` = 0) and (`StoriaDocumento`.`activation_flag` = 1));

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_Workflow_Doc_Iniziali`
--
DROP TABLE IF EXISTS `VW_Workflow_Doc_Iniziali`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_Workflow_Doc_Iniziali` AS select `StoriaDocumento`.`ID` AS `ID`,`StoriaDocumento`.`workflow_completato_il` AS `workflow_completato_il`,`StoriaDocumento`.`scaricato_il` AS `scaricato_il`,`StoriaDocumento`.`inviato_il` AS `inviato_il`,`StoriaDocumento`.`sollecito_il` AS `sollecito_il`,`StoriaDocumento`.`testo_sms` AS `testo_sms`,`StoriaDocumento`.`oggetto_mail` AS `oggetto_mail`,`StoriaDocumento`.`testo_mail` AS `testo_mail`,`StoriaDocumento`.`persistente_area_cliente` AS `persistente_area_cliente`,`StoriaDocumento`.`errore` AS `errore`,`StoriaDocumento`.`sollecito` AS `sollecito`,`StoriaDocumento`.`creation_date` AS `creation_date`,`StoriaDocumento`.`creation_user` AS `creation_user`,`StoriaDocumento`.`last_modification_date` AS `last_modification_date`,`StoriaDocumento`.`last_modification_user` AS `last_modification_user`,`StoriaDocumento`.`deletion_date` AS `deletion_date`,`StoriaDocumento`.`deletion_user` AS `deletion_user`,`StoriaDocumento`.`deletion_flag` AS `deletion_flag`,`StoriaDocumento`.`activation_flag` AS `activation_flag`,`StoriaDocumento`.`ID_Cliente_inverse_of_cliente_doc` AS `ID_Cliente_inverse_of_cliente_doc`,`StoriaDocumento`.`ID_Documento_storia_documento` AS `ID_Documento_storia_documento` from `StoriaDocumento` where `StoriaDocumento`.`ID_Documento_storia_documento` in (select `Documento`.`ID` AS `ID` from `Documento` where (`Documento`.`ID_DescrizioneDocumento_inverse_of_tipologia` = 52));

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_Workflow_User_Studio`
--
DROP TABLE IF EXISTS `VW_Workflow_User_Studio`;

CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_Workflow_User_Studio` AS select `s`.`ID` AS `ID`,`s`.`workflow_completato_il` AS `workflow_completato_il`,`s`.`scaricato_il` AS `scaricato_il`,`s`.`inviato_il` AS `inviato_il`,`s`.`sollecito_il` AS `sollecito_il`,`s`.`testo_sms` AS `testo_sms`,`s`.`oggetto_mail` AS `oggetto_mail`,`s`.`testo_mail` AS `testo_mail`,`s`.`persistente_area_cliente` AS `persistente_area_cliente`,`s`.`errore` AS `errore`,`s`.`sollecito` AS `sollecito`,`s`.`creation_date` AS `creation_date`,`s`.`creation_user` AS `creation_user`,`s`.`last_modification_date` AS `last_modification_date`,`s`.`last_modification_user` AS `last_modification_user`,`s`.`deletion_date` AS `deletion_date`,`s`.`deletion_user` AS `deletion_user`,`s`.`deletion_flag` AS `deletion_flag`,`s`.`activation_flag` AS `activation_flag`,`s`.`ID_Cliente_inverse_of_cliente_doc` AS `ID_Cliente_inverse_of_cliente_doc`,`s`.`ID_Documento_storia_documento` AS `ID_Documento_storia_documento` from (`StoriaDocumento` `s` join `VW_Documenti_User_Studio` `d` on((`s`.`ID_Documento_storia_documento` = `d`.`ID`))) where ((`s`.`inviato_il` is not null) and (`d`.`deletion_flag` = 0) and (`s`.`deletion_flag` = 0));



DROP view IF EXISTS `VW_ItemListino`;

CREATE  VIEW `VW_ItemListino` AS select `c`.`ID` AS `ID_cliente`,`c`.`cliente` AS `cliente`,`l`.`ID` AS `ID`,`l`.`descrizione` AS `descrizione`,`l`.`prezzo` AS `prezzo`,`l`.`tipo` AS `tipo`,`l`.`titolo` AS `titolo`,`l`.`titolo_per_fattura` AS `titolo_per_fattura`,`l`.`creation_date` AS `creation_date`,`l`.`creation_user` AS `creation_user`,`l`.`last_modification_date` AS `last_modification_date`,`l`.`last_modification_user` AS `last_modification_user`,`l`.`deletion_date` AS `deletion_date`,`l`.`deletion_user` AS `deletion_user`,`l`.`deletion_flag` AS `deletion_flag`,`l`.`activation_flag` AS `activation_flag`,`l`.`ID_DatiFatturazione_listino` AS `ID_DatiFatturazione_listino`,`l`.`ID_Metapratica_in_listini` AS `ID_Metapratica_in_listini` 
from ((`ItemListino` `l` join `DatiFatturazione` `f` on `l`.`ID_DatiFatturazione_listino` = `f`.`ID`) join `Cliente` `c` on `f`.`ID_Cliente_dati_fatturazione` = `c`.`ID`) 
WHERE l.deletion_flag=0 and l.activation_flag=1;


