INSERT INTO _system_decode ( ID, sd_description, sd_class, sd_locale, activation_flag, deletion_flag, creation_user, creation_date, last_modification_user, last_modification_date, sd_value ) VALUES( 8515, "COM", "212", "it", "1", "0", "admin", "2012-07-23", "admin", "2012-07-23", "9" );

drop view if exists VW_Fattura_Totali;
create view VW_Fattura_Totali as
SELECT  p.`ID`,   `numero`, `data`, data_pagamento,`competenza`, ID_Cliente_fatture, `anno_contabile`, `spese_anticipate_desc`, 
v2.importo as Rivalsa_Previdenziale,
v3.importo_iva_0 as Totale_Imponibile_0,
v3.importo_iva_1   as Totale_Imponibile_1,
v3.importo_iva_2   as Totale_Imponibile_2,
v3.importo_iva_3   as Totale_Imponibile_3,
v3. importo_iva_4   as Totale_Imponibile_4,
v4.iva_0 as Iva_0,
v4.iva_1 as Iva_1,
v4.iva_2 as Iva_2,
v4.iva_3 as Iva_3,
v4.iva_4 as Iva_4,
 p.spese_anticipate_fattura as Spese_Escluse_Da_Imponibile,
v5.importo as Totale_Fattura,
v6.importo as Ritenuta_Acconto,
v7.importo as Totale_Da_Pagare,
`creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_date`, `deletion_user`, `deletion_flag`, `activation_flag`
from Fattura p inner join VW_Fattura_Rivalsa_Previdenziale v2 on v2.ID = p.ID
inner join VW_Fattura_Totale_Imponibile v3 on v3.ID = p.ID
inner join VW_Fattura_Iva v4 on v4.ID = p.ID
inner join  VW_Fattura_Totale_Fattura v5 on v5.ID = p.ID
inner join VW_Fattura_Ritenuta_Acconto v6 on v6.ID = p.ID
inner join VW_Fattura_Totale_Da_Pagare v7 on v7.ID = p.ID;


UPDATE `_system_menu_item` SET `smi_href` = '?q=object/list&p=Pratica/_a_responsabile/_c_Operatore/_a_account_operatore/_c__system_user/_a_ID/_v_@FUN_GET_USER_INFO(param=id)@' WHERE `_system_menu_item`.`ID` =78;


INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(234,'qualita sms',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(234,0,'it','alta con notifica',1,0),
(234,1,'it','alta',1,0),
(234,2,'it','media',1,0),
(234,3,'it','bassa',1,0);

CREATE TABLE `Sms` (
		  `ID` int(10) unsigned NOT NULL auto_increment,
		  `numero_destinatario` varchar(200) NOT NULL,
		  `testo` varchar(200) NOT NULL,
		  `qualita` int(10) NOT NULL,
		  `ora_spedizione` DATETIME,
		  `ora_ricezione` DATETIME,
		  `note` longtext,
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
		INDEX (`creation_user`),
		INDEX (`creation_date`),
		INDEX (`activation_flag`),
		INDEX (`deletion_flag`)) TYPE=InnoDB;

drop view if exists VW_Pratica_Dormiente;
CREATE VIEW VW_Pratica_Dormiente AS
SELECT * FROM `Pratica` WHERE stato = 0 and (tipo = 1 or tipo = 2) and 
DATE_ADD( `last_modification_date` , INTERVAL + 2  MONTH ) < CURDATE( ) and deletion_flag=0;



/* da fare: */

insert into ProForma_Storico select * from ProForma where anno_contabile =1;
delete from ProForma where anno_contabile =1;
insert into Fattura_Storico select * from Fattura where anno_contabile =1;
delete from Fattura where anno_contabile =1;

delete FROM `ProForma` WHERE `ID` =6384;
delete FROM `ProForma` WHERE deletion_flag=1;

ALTER TABLE `ProForma` ADD UNIQUE (
`data` ,
`competenza` ,
`deletion_flag` ,
`ID_Cliente_proforma`
);

INSERT INTO _system_menu_item ( ID, smi_href, smi_order, ID__system_menu_item_smi_children, smi_locale, smi_title, activation_flag, smi_alternative_text, deletion_flag, creation_user, creation_date, last_modification_user, last_modification_date ) VALUES( 90, "?q=object/detail_edit&p=Sms&a;&t=SmsSend", "3", "44", "it", "Invio SMS", "1", "Invio SMS a cliente o numero generico", "0", "admin", "2012-07-25", "admin", "2012-07-25" );
INSERT INTO _system_menu_item ( ID, smi_href, smi_order, ID__system_menu_item_smi_children, smi_locale, smi_title, activation_flag, smi_alternative_text, deletion_flag, creation_user, creation_date, last_modification_user, last_modification_date ) VALUES( 91, "?q=object/filter&p=Sms", "5", "44", "it", "SMS inviati", "1", "SMS inviati", "0", "admin", "2012-07-25", "admin", "2012-07-25" );

/* SETTEMBRE */
CREATE TABLE `StoricoModifiche` (
		  `ID` int(10) unsigned NOT NULL auto_increment,
		  `classe` varchar(200) NOT NULL,
		  `istanza` varchar(200) NOT NULL,
		  `data_modifica` DATE NOT NULL,
		  `note` longtext,
		  `html_versione` longtext NOT NULL,
		  `creation_date` date NOT NULL,
		  `creation_user` varchar(100) NOT NULL,
		  `last_modification_date` date,
		  `last_modification_user` varchar(100),
		  `deletion_date` date,
		  `deletion_user` varchar(100),
		  `deletion_flag` tinyint(1) NOT NULL,
		  `activation_flag` tinyint(1) NOT NULL,
		PRIMARY KEY ( `ID` ),
		INDEX (`creation_user`),
		INDEX (`creation_date`),
		INDEX (`activation_flag`),
		INDEX (`deletion_flag`),
		UNIQUE KEY (`classe`, `istanza`, `data_modifica`)
		) TYPE=InnoDB;


delete from _system_menu_item where ID = 74;

/* todo 7/9 */

UPDATE `_system_decode` SET `sd_description` = 'clienti: il cliente specificato' WHERE `_system_decode`.`ID` =8462;
INSERT INTO `_system_decode` (`ID`, `sd_description`, `sd_value`, `sd_locale`, `sd_notes`, `ID__system_decode_sd_parent`, `sd_image`, `creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_date`, `deletion_user`, `deletion_flag`, `activation_flag`, `sd_class`) VALUES
(8520, 'clienti: tutti i clienti', 74, 'it', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 220);
UPDATE `_system_decode` SET `sd_description` = 'operatori: l''operatore specificato' WHERE `_system_decode`.`ID` =8463;

ALTER TABLE `DatiContabili` CHANGE `teniamo_noi` `teniamo_noi` INT( 10 ) NULL DEFAULT NULL ;

INSERT INTO _system_menu_item ( ID, smi_href, smi_order, ID__system_menu_item_smi_children, smi_locale, smi_title, activation_flag, smi_alternative_text, deletion_flag, creation_user, creation_date, last_modification_user, last_modification_date ) VALUES( 92, "?q=object/filter&p=SollecitoPagamento", "77", "45", "it", "Solleciti di pagamento inviati", "1", "Solleciti di pagamento inviati", "0", "admin", "2012-09-07", "admin", "2012-09-07" );
INSERT INTO _system_menu_item ( ID, smi_href, smi_order, ID__system_menu_item_smi_children, smi_locale, smi_title, activation_flag, smi_alternative_text, deletion_flag, creation_user, creation_date, last_modification_user, last_modification_date ) VALUES( 93, "?q=object/list&p=ProForma/_a_data_pagamento/_v_null", "5", "50", "it", "Proforma non pagate", "1", "Proforma non pagate (data pagamento non registrata)", "0", "admin", "2012-09-07", "admin", "2012-09-07" );

ALTER TABLE `SollecitoPagamento` ADD`data_spedizione` DATE;

UPDATE _system_menu_item SET smi_href = "?q=object/list&p=ProForma&data_source=sollecito-1&t=ProForma_Solleciti" , smi_association = NULL , smi_order = "21" , ID__system_menu_item_smi_children = "45" , smi_locale = "it" , smi_title = "Generazione Primi solleciti" , activation_flag = "1" , smi_alternative_text = "Generazione Primi solleciti", last_modification_user = "admin" , last_modification_date = "2012-09-07"  WHERE ID = 57 ;
UPDATE _system_menu_item SET smi_href = "?q=object/list&p=ProForma&data_source=sollecito-2&t=ProForma_Solleciti" , smi_association = NULL , smi_order = "21" , ID__system_menu_item_smi_children = "45" , smi_locale = "it" , smi_title = "Generazione Secondi solleciti" , activation_flag = "1" , smi_alternative_text = "Generazione Secondi solleciti" , last_modification_user = "admin" , last_modification_date = "2012-09-07"  WHERE ID = 58 ;
UPDATE _system_menu_item SET smi_href = "?q=object/list&p=ProForma&data_source=sollecito-3&t=ProForma_Solleciti" , smi_association = NULL , smi_order = "21" , ID__system_menu_item_smi_children = "45" , smi_locale = "it" , smi_title = "Generazione Terzi solleciti" , activation_flag = "1" , smi_alternative_text = "Generazione Terzi solleciti" , last_modification_user = "admin" , last_modification_date = "2012-09-07"  WHERE ID = 59 ;


INSERT INTO _system_module_parameter (  ID__system_module_sm_parameters, smp_name, smp_value, activation_flag, deletion_flag, creation_user, creation_date, last_modification_user, last_modification_date ) VALUES( "33", "COORDINATE_BANCARIE_CONTABILIS", "Coordinate Bancarie: intestatario Contabilis S.r.l.<br />c/c 000000035962 presso Cassa di Risparmio di Ferrara Spa<br />Agenzia Ferrara - CIN Italia C - ABI 06155 - CAB 13000<br />Cod. Iban IT64C0615513000000000035962  ", "1", "0", "admin", "2012-09-07", "admin", "2012-09-07" );
INSERT INTO _system_module_parameter (  ID__system_module_sm_parameters, smp_name, smp_value, activation_flag, deletion_flag, creation_user, creation_date, last_modification_user, last_modification_date ) VALUES( "33", "COORDINATE_BANCARIE_STUDIO", "Coordinate Bancarie: intestatario Tullini Fabio<br />c/c 000000259700 presso Banca FIN-ECO Spa<br />Cod. Iban IT30T0301503200000000259700<br />Cod. Agenzia 00699 - CIN Italia T - ABI 03015 - CAB 03200", "1", "0", "admin", "2012-09-07", "admin", "2012-09-07" );

/* modificia tpl metasolleciti con @COORDINATE_BANCARIE@ */

/* tutti i menu static! */


drop view if exists VW_Proforma_Sollecito_2;
create view  VW_Proforma_Sollecito_2 as
SELECT * FROM `ProForma` p WHERE (data_pagamento is null or data_pagamento ='') and stato_proforma = 2  and non_incassabile = 0 
/* esiste il primo sollecito, spedito e spedito almeno 15 giorni fa */
and exists (Select 1 from SollecitoPagamento s where s.ID_ProForma_solleciti_pagamento = p.ID and s.numero_sollecito=0 and s.deletion_flag=0 and s.data_spedizione is not null and  (DATEDIFF(CURDATE() , DATE_ADD(s.data_spedizione, INTERVAL 15 DAY)) > 0))
/* ma non ne esitono altri */
and not exists (Select 1 from SollecitoPagamento s where s.ID_ProForma_solleciti_pagamento = p.ID and s.numero_sollecito<>0 and s.deletion_flag=0)
and (DATEDIFF(CURDATE() , DATE_ADD(p.data, INTERVAL 60 DAY)) > 0);

drop view if exists VW_Proforma_Sollecito_3;
create view  VW_Proforma_Sollecito_3 as
SELECT * FROM `ProForma` p WHERE (data_pagamento is null or data_pagamento ='') and stato_proforma = 2   and non_incassabile = 0
/* esiste il primo sollecito */
and exists (Select 1 from SollecitoPagamento s where s.ID_ProForma_solleciti_pagamento = p.ID and s.numero_sollecito=0 and s.deletion_flag=0)
/* esiste il SECONDO sollecito, spedito e spedito almeno 15 giorni fa */
and exists (Select 1 from SollecitoPagamento s where s.ID_ProForma_solleciti_pagamento = p.ID and s.numero_sollecito=1 and s.deletion_flag=0 and s.data_spedizione is not null and  (DATEDIFF(CURDATE() , DATE_ADD(s.data_spedizione, INTERVAL 15 DAY)) > 0))
/* non esite IL TERZO */
and not exists (Select 1 from SollecitoPagamento s where s.ID_ProForma_solleciti_pagamento = p.ID and s.numero_sollecito=2 and s.deletion_flag=0)
/* non esite IL NON SOLLECITARE */
and not exists (Select 1 from SollecitoPagamento s where s.ID_ProForma_solleciti_pagamento = p.ID and s.numero_sollecito=3 and s.deletion_flag=0)
and (DATEDIFF(CURDATE() , DATE_ADD(p.data, INTERVAL 90 DAY)) > 0);

SELECT * FROM ProForma p where data_pagamento is null and exists (select 1 from `Fattura` f WHERE f.`ID_ProForma_fattura` = p.ID and f.deletion_flag=0);

update ProForma p set data_pagamento = (select data from `Fattura` f WHERE f.`ID_ProForma_fattura` = p.ID and f.deletion_flag=0) where p.data_pagamento is null;

drop view if exists VW_LavoroSuAttivita_report;
create view VW_LavoroSuAttivita_report as
SELECT `ID`, Date(`data`) as data, `durata_minuti`, `diario`, `allegato`, `creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_date`, `deletion_user`, `deletion_flag`, `activation_flag`, `ID_Attivita_sessioni_di_lavoro` FROM `LavoroSuAttivita` order by data;


ALTER TABLE `_system_attachment` CHANGE `sa_filename` `sa_filename` VARCHAR( 200 ) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ;

SELECT *
FROM _system_attachment a
WHERE NOT
EXISTS (
SELECT 1
FROM `Documento` d
WHERE d.`allegato` = a.ID
OR d.`allegato_1` = a.ID
) and not 
EXISTS (
SELECT 1
FROM `RevisioneDocumento` r
WHERE r.`allegato` = a.ID
)
and not 
EXISTS (
SELECT 1
FROM `LavoroSuAttivita` s
WHERE s.`allegato` = a.ID
)
;


SELECT id
FROM `_system_attachment`
GROUP BY `sa_filename`
HAVING count( * ) >1;

ALTER TABLE `_system_attachment` ADD UNIQUE (
`sa_filename`
);

ALTER TABLE `_system_attachment` CHANGE `sa_filename` `sa_filename` VARCHAR( 200 ) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ;

//+ indici!!!!!!





