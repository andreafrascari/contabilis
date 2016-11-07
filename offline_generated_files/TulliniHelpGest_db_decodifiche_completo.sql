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


INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(200,'tipo_cliente',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(200,0,'it','PF',1,0),
(200,1,'it','SP',1,0),
(200,2,'it','P',1,0),
(200,3,'it','I',1,0),
(200,4,'it','AP',1,0),
(200,5,'it','NP',1,0),
(200,6,'it','SC',1,0),
(200,7,'it','CP',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(212,'non_profit',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(212,0,'it','ACR',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(208,'tipo_sollecito',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(208,0,'it','sms',1,0),
(208,1,'it','mail',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(227,'stato_cliente',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(227,0,'it','in attesa di verifica',1,0),
(227,1,'it','verificato dal responsabile',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(211,'sostituto_imposta',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(211,0,'it','si',1,0),
(211,1,'it','no',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(210,'immobili',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(210,0,'it','si',1,0),
(210,1,'it','no',1,0),
(210,2,'it','leasing',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(211,'ici',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(211,'cassetto_fiscale',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(211,'f24_cumulativo',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(211,'antireciclaggio',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(211,'privacy',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(211,'privacy_acquisita',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(211,'conservazione_sostitutiva',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(211,'conservazione_sostitutiva_delega_studio',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(211,'verifica_validita_firma_digitale',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(211,'conservazione_libro_giornale',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(200,'tipo_cliente',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(200,0,'it','PF',1,0),
(200,1,'it','SP',1,0),
(200,2,'it','P',1,0),
(200,3,'it','I',1,0),
(200,4,'it','AP',1,0),
(200,5,'it','NP',1,0),
(200,6,'it','SC',1,0),
(200,7,'it','CP',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(211,'teniamo_noi',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(215,'regime_contabilita',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(215,0,'it','ESONERO AGR',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(216,'regime_iva',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(216,0,'it','398/91',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(214,'liquidazione_iva',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(214,0,'it','annuale',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(DA VALORIZZARE,'provincia_registro_imprese',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(217,'sezioni_speciali',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(217,0,'it','piccolo imprenditore',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(211,'unita_locali',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(211,'obbligo_iscrizione_inail',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(213,'incarico_a',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(213,0,'it','Studio',1,0),
(213,1,'it','CDL',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(209,'visibilita',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(209,0,'it','documento interno visibile user e admin',1,0),
(209,1,'it','documento interno admin',1,0),
(209,2,'it','documento cliente visibile cliente user e admin',1,0),
(209,3,'it','documento cliente visibile solo user e admin',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(201,'azione_conseguente_caricamento',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(201,0,'it','B email cliente',1,0),
(201,1,'it','E comunicazione interna segreteria studio',1,0),
(201,2,'it','A nessuna',1,0),
(201,3,'it','C email cliente link',1,0),
(201,4,'it','D email cliente link richiesto logon',1,0),
(201,5,'it','F comunicazione interna solo nuovi documenti no revisioni',1,0),
(201,6,'it','G email mailing list',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(202,'sollecito',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(202,0,'it','si se cliente non scarica',1,0),
(202,1,'it','si se non ricevuto materiale',1,0),
(202,2,'it','no',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(207,'anno_contabile',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(207,0,'it','2010',1,0),
(207,1,'it','2011',1,0),
(207,2,'it','2012',1,0),
(207,3,'it','2013',1,0),
(207,4,'it','2014',1,0),
(207,5,'it','2015',1,0),
(207,6,'it','2016',1,0),
(207,7,'it','2017',1,0),
(207,8,'it','2018',1,0),
(207,9,'it','2019',1,0),
(207,10,'it','2020',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(207,'anno_contabile',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(207,0,'it','2010',1,0),
(207,1,'it','2011',1,0),
(207,2,'it','2012',1,0),
(207,3,'it','2013',1,0),
(207,4,'it','2014',1,0),
(207,5,'it','2015',1,0),
(207,6,'it','2016',1,0),
(207,7,'it','2017',1,0),
(207,8,'it','2018',1,0),
(207,9,'it','2019',1,0),
(207,10,'it','2020',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(224,'competenza',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(224,0,'it','CONTABILIS',1,0),
(224,1,'it','STUDIO',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(235,'stato_chiamata',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(235,0,'it','ricevuta',1,0),
(235,1,'it','inviata',1,0),
(235,2,'it','persa',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(236,'diagnosi_std',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(236,0,'it','richiesta preventivo',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(238,'th_direzione',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(238,0,'it','cliente studio',1,0),
(238,1,'it','studio cliente',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(237,'canale',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(237,0,'it','email',1,0),
(237,1,'it','telefono',1,0),
(237,2,'it','fax',1,0),
(237,3,'it','sms',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(242,'statoTicket',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(242,0,'it','in attesa',1,0),
(242,1,'it','assegnato',1,0),
(242,2,'it','aperto',1,0),
(242,3,'it','chiuso con successo',1,0),
(242,4,'it','chiuso senza successo',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(241,'priorita_ticket',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(241,0,'it','urgente',1,0),
(241,1,'it','normale',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(240,'diagnosi_std',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(240,0,'it','richiesta preventivo',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(239,'azioneRichiesta',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(239,0,'it','richiama lui',1,0),
(239,1,'it','inviare sms',1,0),
(239,2,'it','richiamare al',1,0),
(239,3,'it','inviare mail',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(207,'anno_contabile',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(207,0,'it','2010',1,0),
(207,1,'it','2011',1,0),
(207,2,'it','2012',1,0),
(207,3,'it','2013',1,0),
(207,4,'it','2014',1,0),
(207,5,'it','2015',1,0),
(207,6,'it','2016',1,0),
(207,7,'it','2017',1,0),
(207,8,'it','2018',1,0),
(207,9,'it','2019',1,0),
(207,10,'it','2020',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(229,'tipo',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(229,0,'it','a forfait',1,0),
(229,1,'it','a ora',1,0),
(229,2,'it','a prestazione',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(229,'tipo',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(229,0,'it','a forfait',1,0),
(229,1,'it','a ora',1,0),
(229,2,'it','a prestazione',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(233,'numero_sollecito',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(233,0,'it','primo',1,0),
(233,1,'it','secondo',1,0),
(233,2,'it','terzo',1,0),
(233,3,'it','non sollecitare',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(211,'fatto',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(207,'anno_contabile',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(207,0,'it','2010',1,0),
(207,1,'it','2011',1,0),
(207,2,'it','2012',1,0),
(207,3,'it','2013',1,0),
(207,4,'it','2014',1,0),
(207,5,'it','2015',1,0),
(207,6,'it','2016',1,0),
(207,7,'it','2017',1,0),
(207,8,'it','2018',1,0),
(207,9,'it','2019',1,0),
(207,10,'it','2020',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(223,'stato',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(223,0,'it','Aperta',1,0),
(223,1,'it','Chiusa',1,0),
(223,2,'it','Sospesa',1,0),
(223,3,'it','Fatturata',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(229,'tipo',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(229,0,'it','a forfait',1,0),
(229,1,'it','a ora',1,0),
(229,2,'it','a prestazione',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(207,'anno_contabile',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(207,0,'it','2010',1,0),
(207,1,'it','2011',1,0),
(207,2,'it','2012',1,0),
(207,3,'it','2013',1,0),
(207,4,'it','2014',1,0),
(207,5,'it','2015',1,0),
(207,6,'it','2016',1,0),
(207,7,'it','2017',1,0),
(207,8,'it','2018',1,0),
(207,9,'it','2019',1,0),
(207,10,'it','2020',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(230,'stato_proforma',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(230,0,'it','da validare',1,0),
(230,1,'it','validata',1,0),
(230,2,'it','spedita',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(224,'competenza',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(224,0,'it','CONTABILIS',1,0),
(224,1,'it','STUDIO',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(218,'regime',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(218,0,'it','ENPAB',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(211,'iscrizione',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(243,'destinarario_personalizzato',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(243,0,'it','tipo clinte PF',1,0),
(243,1,'it','contabilita noi',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(221,'ricorrenza',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(221,0,'it','giorno',1,0),
(221,1,'it','settimana',1,0),
(221,2,'it','mese',1,0),
(221,3,'it','anno',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(234,'qualita',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(234,0,'it','alta con notifica',1,0),
(234,1,'it','alta',1,0),
(234,2,'it','media',1,0),
(234,3,'it','bassa',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(233,'numero_sollecito',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(233,0,'it','primo',1,0),
(233,1,'it','secondo',1,0),
(233,2,'it','terzo',1,0),
(233,3,'it','non sollecitare',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(202,'sollecito',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(202,0,'it','si se cliente non scarica',1,0),
(202,1,'it','si se non ricevuto materiale',1,0),
(202,2,'it','no',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(208,'tipo_sollecito',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(208,0,'it','sms',1,0),
(208,1,'it','mail',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(232,'iva',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(232,0,'it','Fuori Campo',1,0),
(232,1,'it','Esente Articolo 10',1,0),
(232,2,'it','4%',1,0),
(232,3,'it','10%',1,0),
(232,4,'it','STD',1,0);


-- Fine parte dinamica 


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
