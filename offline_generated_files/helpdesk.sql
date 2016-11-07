CREATE TABLE `HDChiamataTelefonica` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `data` DATETIME NOT NULL,
  `durata` int(10) NOT NULL,
  `note` longtext,
  `numero_locale` varchar(200) NOT NULL,
  `numero_remoto` varchar(200) NOT NULL,
  `stato_chiamata` int(10) NOT NULL,
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
  `alias` varchar(200),
  `email` varchar(200) NOT NULL,
  `email_pwd` varchar(200) NOT NULL,
  `nome` varchar(200) NOT NULL,
  `note` longtext,
  `port` varchar(200) NOT NULL,
  `server` varchar(200) NOT NULL,
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
  `allegato` int(10),
  `allegato_1` int(10),
  `diagnosi_std`  varchar(200) NOT NULL,
  `note` longtext,
  `risposta_std` longtext NOT NULL,
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

ALTER TABLE `HDRispostaStandard` ADD UNIQUE (
`diagnosi_std`
);

CREATE TABLE `HDThreadStep` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `allegato` int(10),
  `allegato_1` int(10),
  `canale` int(10) NOT NULL,
  `ID_HDChiamataTelefonica_ticket` int(10),
  `ID_LavoroSuAttivita_daTicket` int(10),
  `note` longtext,
  `th_data` DATETIME NOT NULL,
  `th_direzione` int(10) NOT NULL,
  `th_messaggio` longtext NOT NULL,
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
  `allegato` int(10),
  `allegato_1` int(10),
  `azioneRichiesta` int(10),
  `contattoNonRiconosciuto` varchar(200),
  `dataArrivo` DATETIME NOT NULL,
  `data_scadenza` DATE,
  `diagnosi_libero` varchar(200),
  `diagnosi_std` int(10),
  `identificativo` varchar(200) NOT NULL,
  `priorita_ticket` int(10),
  `rispondere_a` varchar(200),
  `statoTicket` int(10) NOT NULL,
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
`ID_Cliente_contatti_studio` int(10)  NULL,
INDEX(`ID_Cliente_contatti_studio`),
`ID_HDGruppoAssistenza_tickets` int(10)  NULL,
INDEX(`ID_HDGruppoAssistenza_tickets`),
`ID_Operatore_ticketAssegnati` int(10)  NULL,
INDEX(`ID_Operatore_ticketAssegnati`),
`ID_Pratica_ticketAssociati` int(10) NULL,
INDEX(`ID_Pratica_ticketAssociati`),
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)) ENGINE=InnoDB;

INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(235,'stato_chiamata',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(235,0,'it','ricevuta',1,0),
(235,1,'it','inviata',1,0),
(235,2,'it','persa',1,0);

INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(237,'canale',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(237,0,'it','email',1,0),
(237,1,'it','telefono',1,0),
(237,2,'it','fax',1,0),
(237,3,'it','sms',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(238,'th_direzione',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(238,0,'it','cliente studio',1,0),
(238,1,'it','studio cliente',1,0),
(238,2,'it','spedito studio cliente',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(239,'azioneRichiesta',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(239,0,'it','richiama lui',1,0),
(239,1,'it','inviare sms',1,0),
(239,2,'it','richiamare al',1,0),
(239,3,'it','inviare mail',1,0),
(239,4,'it','nessuna',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(240,'diagnosi_std',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(240,0,'it','richiesta preventivo',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(241,'priorita_ticket',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(241,0,'it','urgente',1,0),
(241,1,'it','normale',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(242,'statoTicket',1,0);

INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(242,0,'it','in attesa',1,0),
(242,1,'it','assegnato',1,0),
(242,2,'it','aperto',1,0),
(242,3,'it','chiuso',1,0),
(242,4,'it','insolubile',1,0);


INSERT INTO `_system_module` ( `ID__system_menu_item_sm_menu`, `sm_name`, `sm_order`, `sm_java_class`, `sm_alternative_text`, `sm_description`, `sm_show`, `sm_position`, `sm_default_parameters`, `sm_default_call`, `sm_auto_load`, `ID__system_meta_environment_sme_modules`, `creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_date`, `deletion_user`, `deletion_flag`, `activation_flag`, `owner_user`, `owner_group`) VALUES
( NULL, 'ticket', 1, 'eu.anastasis.tulliniHelpGest.helpDesk.TicketModule', NULL, NULL, NULL, 1, NULL, NULL, NULL, 1, '2010-12-09', 'admin', '2010-12-09', 'admin', NULL, NULL, 0, 1, 1, 1);

INSERT INTO _system_instance_auth (  ID__system_group_sia_group, sia_permissions, activation_flag, deletion_flag, creation_user, creation_date, last_modification_user, last_modification_date, sia_instance, sia_class ) VALUES(  "6", "111", "1", "0", "admin", "2015-04-28", "admin", "2015-04-28", "44", "_system_module" );
INSERT INTO _system_instance_auth (  ID__system_group_sia_group, sia_permissions, activation_flag, deletion_flag, creation_user, creation_date, last_modification_user, last_modification_date, sia_instance, sia_class ) VALUES( "1", "111", "1", "0", "admin", "2015-04-28", "admin", "2015-04-28", "44", "_system_module" );
INSERT INTO _system_instance_auth ( ID__system_group_sia_group, sia_permissions, activation_flag, deletion_flag, creation_user, creation_date, last_modification_user, last_modification_date, sia_instance, sia_class ) VALUES(  "5", "111", "1", "0", "admin", "2015-04-28", "admin", "2015-04-28", "44", "_system_module" );
INSERT INTO _system_instance_auth ( ID__system_group_sia_group, sia_permissions, activation_flag, deletion_flag, creation_user, creation_date, last_modification_user, last_modification_date, sia_instance, sia_class ) VALUES(  "2", "111", "1", "0", "admin", "2015-04-28", "admin", "2015-04-28", "44", "_system_module" );
INSERT INTO _system_instance_auth (  ID__system_group_sia_group, sia_permissions, activation_flag, deletion_flag, creation_user, creation_date, last_modification_user, last_modification_date, sia_instance, sia_class ) VALUES(  "4", "111", "1", "0", "admin", "2015-04-28", "admin", "2015-04-28", "44", "_system_module" );

INSERT INTO `_system_module_parameter` ( `smp_name`, `smp_notes`, `smp_value`, `creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_date`, `deletion_user`, `deletion_flag`, `activation_flag`, `ID__system_module_sm_parameters`, `owner_user`, `owner_group`) VALUES
( 'TICKET_CCN_EMAIL_ADDRESS', 'invece della sent box, i messaggi del Ticketing System vanno in ccn a questo indirizzo mail', '', '2010-12-24', 'admin', '2010-12-24', 'admin', NULL, NULL, 0, 1, 21, 1, 1),
( 'POLLING_FREQUENCY', 'frequenza di polling in minuti del Mail2Ticket', '1', '2010-12-24', 'admin', '2010-12-24', 'admin', NULL, NULL, 0, 1, 21, 1, 1),
( 'ANSWERING_MACHINE_EMAIL', 'mail della segreteria telefonica per riconoscerne i messaggi', 'wms@anastasis.it', '2011-01-05', 'ltoselli', '2011-01-05', 'ltoselli', NULL, NULL, 0, 1, 21, 4, 2),
( 'ANSWERING_MACHINE_PHONE_DETECTION_PATTERN', 'pattern che precede il numero di telefono', '[0-9]{6,20}', '2011-01-05', 'ltoselli', '2011-01-05', 'ltoselli', NULL, NULL, 0, 1, 21, 4, 2),
( 'POLLING_ENCODING', 'Encoding da usare per leggere le mailbox', 'UTF-8', '2010-12-24', 'admin', '2010-12-24', 'admin', NULL, NULL, 0, 1, 21, 1, 1);


/* config app menu NO DRUPAL!!!!!! */


drop view if exists VW_NotificaScadenza_Prossime;
create view VW_NotificaScadenza_Prossime as
SELECT * FROM `NotificaScadenza` WHERE deletion_flag=0 and `data` >= curdate() ;

UPDATE _system_menu_item SET smi_locale = "it" , ID__system_menu_item_smi_children = "50" , activation_flag = "1" , smi_alternative_text = "Proforma da validare" , smi_title = "Proforma da validare" , smi_href = "?q=object/list&p=ProForma/_a_stato_proforma/_v_da validare/_a_non_incassabile/_v_false/_o_non_incassabile/_v_null" , smi_association = NULL , smi_order = "3" , last_modification_user = "fabio" , last_modification_date = "2015-05-29"  WHERE ID = 51 ;

INSERT INTO _system_menu_item ( ID, smi_href, smi_order, ID__system_menu_item_smi_children, smi_locale, smi_title, activation_flag, smi_alternative_text, deletion_flag, creation_user, creation_date, last_modification_user, last_modification_date ) VALUES( 109, "?q=object/filter&p=HDTicket", "51", "41", "it", "Ticket HelpDesk", "1", "Tutti i ticket di HelpDesk", "0", "fabio", "2015-05-29", "fabio", "2015-05-29" );

 INSERT INTO _system_module_parameter ( ID, ID__system_module_sm_parameters, smp_name, smp_notes, smp_value, activation_flag, deletion_flag, creation_user, creation_date, last_modification_user, last_modification_date ) VALUES( 25, "44", "HD_MAIL_COLLETTIVE", "queste mail alimentano i ""ticket studio"": per le altre account, i ticket vengono assegnati direttamente", "<p>segreteria,contabilita</p>
", "1", "0", "fabio", "2015-05-31", "fabio", "2015-05-31" );

INSERT INTO _system_menu_item ( ID, smi_href, smi_order, ID__system_menu_item_smi_children, smi_locale, smi_title, activation_flag, smi_alternative_text, deletion_flag, creation_user, creation_date, last_modification_user, last_modification_date ) VALUES( 111, "?q=object/detail_edit&p=_system_file_import&a", "41", "32", "it", "Importazione xls Car4U", "1", "Importazione xls Car4U", "0", "fabio", "2015-07-19", "fabio", "2015-07-19" );

INSERT INTO _system_menu_item ( ID, smi_href, smi_order, ID__system_menu_item_smi_children, smi_locale, smi_title, activation_flag, smi_alternative_text, deletion_flag, creation_user, creation_date, last_modification_user, last_modification_date ) VALUES( 110, "?q=object/list&p=HDGruppoAssistenza", "23", "32", "it", "Configurazione Email HelpDesk", "1", "Configurazione Email HelpDesk", "0", "fabio", "2015-07-19", "fabio", "2015-07-19" );

UPDATE _system_menu_item SET smi_locale = "it" , ID__system_menu_item_smi_children = NULL , activation_flag = "1" , smi_alternative_text = "Scadenze ed Sms" , smi_title = "Scadenze ed Sms" , smi_href = NULL , smi_association = NULL , smi_order = "2" , last_modification_user = "fabio" , last_modification_date = "2015-07-19"  WHERE ID = 42 ;

UPDATE _system_menu_item SET smi_locale = "it" , ID__system_menu_item_smi_children = "42" , activation_flag = "1" , smi_alternative_text = "Invio SMS a cliente o numero generico" , smi_title = "Invio SMS" , smi_href = "?q=object/detail_edit&p=Sms&a;&t=SmsSend" , smi_association = NULL , smi_order = "13" , last_modification_user = "fabio" , last_modification_date = "2015-07-19"  WHERE ID = 90 ;

UPDATE _system_menu_item SET smi_locale = "it" , ID__system_menu_item_smi_children = "42" , activation_flag = "1" , smi_alternative_text = "SMS inviati" , smi_title = "SMS inviati" , smi_href = "?q=object/filter&p=Sms" , smi_association = NULL , smi_order = "65" , last_modification_user = "fabio" , last_modification_date = "2015-07-19"  WHERE ID = 91;

UPDATE _system_menu_item SET deletion_date = "2015-07-19" , deletion_user = "fabio" , deletion_flag = "1"  WHERE ID = 44 ;

UPDATE `_system_module` SET `deletion_date` = '2015-07-19',
`deletion_user` = 'admin',
`deletion_flag` = '1',
`activation_flag` = '0' WHERE `_system_module`.`ID` =35;

UPDATE `tullini`.`_system_module` SET `sm_name` = 'Scadenze ed SMS',
`sm_show` = 'Scadenze ed SMS' WHERE `_system_module`.`ID` =37;

/******************** 23 luglio  ****************/

INSERT INTO `tullini`.`_system_module` (
`ID__system_menu_item_sm_menu` ,
`sm_name` ,
`sm_order` ,
`sm_java_class` ,
`sm_alternative_text` ,
`sm_description` ,
`sm_show` ,
`sm_position` ,
`sm_default_parameters` ,
`sm_default_call` ,
`sm_auto_load` ,
`ID__system_meta_environment_sme_modules` ,
`creation_date` ,
`creation_user` ,
`last_modification_date` ,
`last_modification_user` ,
`deletion_date` ,
`deletion_user` ,
`deletion_flag` ,
`activation_flag` ,
`owner_user` ,
`owner_group`
)
VALUES (
NULL , 'homepage_adminstudio', '1', 'eu.anastasis.serena.application.modules.StaticHtmlModule', 'home page studio', NULL , 'home page studio', '5', 'homepage/adminstudio', 'detail', '1', '1', '2011-07-15', 'admin', '2011-08-24', 'admin', NULL , NULL , '0', '1', '3', NULL
);

