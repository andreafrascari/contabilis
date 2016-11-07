DROP VIEW IF EXISTS `VW_Pratiche_chiuse_senza_data_chiusura_2013`;
CREATE  VIEW `VW_Pratiche_chiuse_senza_data_chiusura_2013` AS select `Pratica`.`ID` AS `ID`,`Pratica`.`tipo` AS `tipo`,`Pratica`.`codice` AS `codice`,`Pratica`.`titolo` AS `titolo`,`Pratica`.`note` AS `note`,`Pratica`.`data_chiusura` AS `data_chiusura`,`Pratica`.`stato` AS `stato`,`Pratica`.`anno_contabile` AS `anno_contabile`,`Pratica`.`prezzo` AS `prezzo`,`Pratica`.`titolo_per_fattura` AS `titolo_per_fattura`,`Pratica`.`creation_date` AS `creation_date`,`Pratica`.`creation_user` AS `creation_user`,`Pratica`.`last_modification_date` AS `last_modification_date`,`Pratica`.`last_modification_user` AS `last_modification_user`,`Pratica`.`deletion_date` AS `deletion_date`,`Pratica`.`deletion_user` AS `deletion_user`,`Pratica`.`deletion_flag` AS `deletion_flag`,`Pratica`.`activation_flag` AS `activation_flag`,`Pratica`.`ID_Cliente_pratiche` AS `ID_Cliente_pratiche`,`Pratica`.`ID_Operatore_responsabile_di_pratiche` AS `ID_Operatore_responsabile_di_pratiche`,`Pratica`.`ID_Metapratica_pratiche_generate` AS `ID_Metapratica_pratiche_generate` from `Pratica` where (isnull(`Pratica`.`data_chiusura`) and (`Pratica`.`stato` = 1) and (`Pratica`.`anno_contabile` = 3) and deletion_flag = 0);

DROP VIEW IF EXISTS `VW_Pratiche_chiuse_senza_data_chiusura_2014`;
CREATE  VIEW `VW_Pratiche_chiuse_senza_data_chiusura_2014` AS select `Pratica`.`ID` AS `ID`,`Pratica`.`tipo` AS `tipo`,`Pratica`.`codice` AS `codice`,`Pratica`.`titolo` AS `titolo`,`Pratica`.`note` AS `note`,`Pratica`.`data_chiusura` AS `data_chiusura`,`Pratica`.`stato` AS `stato`,`Pratica`.`anno_contabile` AS `anno_contabile`,`Pratica`.`prezzo` AS `prezzo`,`Pratica`.`titolo_per_fattura` AS `titolo_per_fattura`,`Pratica`.`creation_date` AS `creation_date`,`Pratica`.`creation_user` AS `creation_user`,`Pratica`.`last_modification_date` AS `last_modification_date`,`Pratica`.`last_modification_user` AS `last_modification_user`,`Pratica`.`deletion_date` AS `deletion_date`,`Pratica`.`deletion_user` AS `deletion_user`,`Pratica`.`deletion_flag` AS `deletion_flag`,`Pratica`.`activation_flag` AS `activation_flag`,`Pratica`.`ID_Cliente_pratiche` AS `ID_Cliente_pratiche`,`Pratica`.`ID_Operatore_responsabile_di_pratiche` AS `ID_Operatore_responsabile_di_pratiche`,`Pratica`.`ID_Metapratica_pratiche_generate` AS `ID_Metapratica_pratiche_generate` from `Pratica` where (isnull(`Pratica`.`data_chiusura`) and (`Pratica`.`stato` = 1) and (`Pratica`.`anno_contabile` = 4) and deletion_flag = 0);

DROP VIEW IF EXISTS `VW_Pratiche_chiuse_senza_data_chiusura_2015`;
CREATE  VIEW `VW_Pratiche_chiuse_senza_data_chiusura_2015` AS select `Pratica`.`ID` AS `ID`,`Pratica`.`tipo` AS `tipo`,`Pratica`.`codice` AS `codice`,`Pratica`.`titolo` AS `titolo`,`Pratica`.`note` AS `note`,`Pratica`.`data_chiusura` AS `data_chiusura`,`Pratica`.`stato` AS `stato`,`Pratica`.`anno_contabile` AS `anno_contabile`,`Pratica`.`prezzo` AS `prezzo`,`Pratica`.`titolo_per_fattura` AS `titolo_per_fattura`,`Pratica`.`creation_date` AS `creation_date`,`Pratica`.`creation_user` AS `creation_user`,`Pratica`.`last_modification_date` AS `last_modification_date`,`Pratica`.`last_modification_user` AS `last_modification_user`,`Pratica`.`deletion_date` AS `deletion_date`,`Pratica`.`deletion_user` AS `deletion_user`,`Pratica`.`deletion_flag` AS `deletion_flag`,`Pratica`.`activation_flag` AS `activation_flag`,`Pratica`.`ID_Cliente_pratiche` AS `ID_Cliente_pratiche`,`Pratica`.`ID_Operatore_responsabile_di_pratiche` AS `ID_Operatore_responsabile_di_pratiche`,`Pratica`.`ID_Metapratica_pratiche_generate` AS `ID_Metapratica_pratiche_generate` from `Pratica` where (isnull(`Pratica`.`data_chiusura`) and (`Pratica`.`stato` = 1) and (`Pratica`.`anno_contabile` = 5) and deletion_flag = 0);




drop view if exists VW_DEL_CLIENTI_PROFORMA_DA_OTT_13;
create view VW_DEL_CLIENTI_PROFORMA_DA_OTT_13 as

SELECT '2013/10/01' as data, `ID_Cliente_proforma` as cliente FROM `ProForma` WHERE `data` = '2013/10/01' 
union
SELECT '2013/10/09' as data, `ID_Cliente_proforma` as cliente FROM `ProForma` WHERE `data` = '2013/10/09' 
union
SELECT '2013/10/17' as data, `ID_Cliente_proforma` as cliente FROM `ProForma` WHERE `data` = '2013/10/17' 
union
SELECT '2013/10/22' as data, `ID_Cliente_proforma` as cliente FROM `ProForma` WHERE `data` = '2013/10/22' 
union
SELECT '2013/11/28' as data, `ID_Cliente_proforma` as cliente FROM `ProForma` WHERE `data` = '2013/11/28' 
union
SELECT '2013/12/01' as data, `ID_Cliente_proforma` as cliente FROM `ProForma` WHERE `data` = '2013/12/01' 
union
SELECT '2013/12/02' as data, `ID_Cliente_proforma` as cliente FROM `ProForma` WHERE `data` = '2013/12/02' 
union
SELECT '2013/12/09' as data, `ID_Cliente_proforma` as cliente FROM `ProForma` WHERE `data` = '2013/12/09' 
union
SELECT '2013/12/16' as data, `ID_Cliente_proforma` as cliente FROM `ProForma` WHERE `data` = '2013/12/16' 
union
SELECT '2013/12/18' as data, `ID_Cliente_proforma` as cliente FROM `ProForma` WHERE `data` = '2013/12/18' 
union
SELECT '2013/12/20' as data, `ID_Cliente_proforma` as cliente FROM `ProForma` WHERE `data` = '2013/12/20' 
limit 0,1000;



delete from Pratica;

Insert into Pratica select * from Pratica_backup;


/* tutte quelle che esistevano al 1/10/13 .... riportate a quello stato (dopo il giro di fatturazione ok */
update `Pratica` p set p.stato = (Select pdic.stato from Pratica_02_10_13 pdic where pdic.ID = p.ID)
where exists (Select 1 from Pratica_02_10_13 pdic2 where pdic2.ID = p.ID);


/* per ogni data proforma, le SOLE PRATICHE (2013???) del cliente VENGONO RIPORTATE ALLO STATO GIUSTO: */
	

/* '2013/10/09' */
update `Pratica` p set p.stato = (Select pdic.stato from Pratica_10_10_13 pdic where pdic.ID = p.ID)
where exists (Select 1 from Pratica_10_10_13 pdic2 where pdic2.ID = p.ID)
and p.ID_Cliente_pratiche in (select cliente from VW_DEL_CLIENTI_PROFORMA_DA_OTT_13 where data = '2013/10/09');

/* 2013/10/17 */
update `Pratica` p set p.stato = (Select pdic.stato from Pratica_18_10_13 pdic where pdic.ID = p.ID)
where exists (Select 1 from Pratica_18_10_13 pdic2 where pdic2.ID = p.ID)
and p.ID_Cliente_pratiche in (select cliente from VW_DEL_CLIENTI_PROFORMA_DA_OTT_13 where data = '2013/10/17');

/* 2013/10/22 */
update `Pratica` p set p.stato = (Select pdic.stato from Pratica_23_10_13 pdic where pdic.ID = p.ID)
where exists (Select 1 from Pratica_23_10_13 pdic2 where pdic2.ID = p.ID)
and p.ID_Cliente_pratiche in (select cliente from VW_DEL_CLIENTI_PROFORMA_DA_OTT_13 where data = '2013/10/22');

/* 2013/11/28 */
update `Pratica` p set p.stato = (Select pdic.stato from Pratica_29_11_13 pdic where pdic.ID = p.ID)
where exists (Select 1 from Pratica_29_11_13 pdic2 where pdic2.ID = p.ID)
and p.ID_Cliente_pratiche in (select cliente from VW_DEL_CLIENTI_PROFORMA_DA_OTT_13 where data = '2013/11/28');

/* 2013/12/01 */
update `Pratica` p set p.stato = (Select pdic.stato from Pratica_02_12_13 pdic where pdic.ID = p.ID)
where exists (Select 1 from Pratica_02_12_13 pdic2 where pdic2.ID = p.ID)
and p.ID_Cliente_pratiche in (select cliente from VW_DEL_CLIENTI_PROFORMA_DA_OTT_13 where data = '2013/12/01');

/* 2013/12/02 */
update `Pratica` p set p.stato = (Select pdic.stato from Pratica_03_12_13 pdic where pdic.ID = p.ID)
where exists (Select 1 from Pratica_03_12_13 pdic2 where pdic2.ID = p.ID)
and p.ID_Cliente_pratiche in (select cliente from VW_DEL_CLIENTI_PROFORMA_DA_OTT_13 where data = '2013/12/02');


/* 2013/12/09 */
update `Pratica` p set p.stato = (Select pdic.stato from Pratica_10_12_13 pdic where pdic.ID = p.ID)
where exists (Select 1 from Pratica_10_12_13 pdic2 where pdic2.ID = p.ID)
and p.ID_Cliente_pratiche in (select cliente from VW_DEL_CLIENTI_PROFORMA_DA_OTT_13 where data = '2013/12/09');


/* 2013/12/18 */
update `Pratica` p set p.stato = (Select pdic.stato from Pratica_19_12_13 pdic where pdic.ID = p.ID)
where exists (Select 1 from Pratica_19_12_13 pdic2 where pdic2.ID = p.ID)
and p.ID_Cliente_pratiche in (select cliente from VW_DEL_CLIENTI_PROFORMA_DA_OTT_13 where data = '2013/12/18');

/* 2013/12/20 */
/* non modifica niente ... att a ore */
update `Pratica` p set p.stato = (Select pdic.stato from Pratica_21_12_13 pdic where pdic.ID = p.ID)
where exists (Select 1 from Pratica_21_12_13 pdic2 where pdic2.ID = p.ID)
and p.ID_Cliente_pratiche in (select cliente from VW_DEL_CLIENTI_PROFORMA_DA_OTT_13 where data = '2013/12/20');

/*
Pratica_10_10_13
Pratica_18_10_13
Pratica_23_10_13

Pratica_29_11_13

Pratica_02_12_13
Pratica_03_12_13
Pratica_10_12_13
Pratica_17_12_13
Pratica_19_12_13
Pratica_21_12_13
*/




/* settate "fatturate" a mano dal 1/10: */
insert into Pratiche_fatturate_a_mano
SELECT *
FROM  `_system_log` 
WHERE  `onclass` LIKE  'Pratica'
AND  `operation` LIKE  '%<stato>&lt;![CDATA[Fatturata]]&gt;</stato>%'
AND  `creation_date` >=  '2013/10/01'
LIMIT 0 , 1000;


Update Pratica p set p.stato = 3 where p.ID in (select m.oninstance from Pratiche_fatturate_a_mano m);


/* attivita modificate autyomaticamente -> sospette */
SELECT * FROM `Attivita` a WHERE a.last_modification_date >= '2013/10/01' and a.last_modification_date <> a.creation_date and not exists 
(select 1 from   `_system_log` sl
WHERE  `onclass` =  'Attivita'
AND  sl.`creation_date` >=  '2013/10/01' and sl.oninstance = a.ID)



/************** DOMANI! */
/* poi, pratiche ancora chiuse MALE: */
SELECT distinct last_modification_date FROM `VW_Pratiche_chiuse_senza_data_chiusura_2013` WHERE 1;


/* TUTTE IL 20/12 !!!!!!!! => aggiornare queste allo stato al 19/12!!!!!!!!!! */

update `Pratica` p set p.stato = (Select pdic.stato from Pratica_19_12_13 pdic where pdic.ID = p.ID)
where exists (Select 1 from Pratica_tmp pdic2 where pdic2.ID = p.ID)
and exists (Select 1 from Pratica_19_12_13 pdic3 where pdic3.ID = p.ID);

/* poi .... minuti fatturati attivita ....*/



/*********************** UPGRADE CLIENTI ZIP *******************/


/* clienti zip */
CREATE TABLE `DocumentoCliente` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `allegato` int(10) NOT NULL,
  `archiviato` tinyint(1),
  `data` DATE NOT NULL,
  `note` longtext,
  `oggetto` varchar(200) NOT NULL,
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
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`ID_Cliente_documenti_per_lo_studio`, `data`, `oggetto`,`deletion_flag`)
) ENGINE=InnoDB;


/* REMEMBER: 
 * - permessi classe 220 a DocumentoCliente per clienti
 * - notifica mail a segreteria in config_app
 * - permessi istanza 111 a mod 42 per clienti
 */ 

ALTER TABLE `_system_menu_item` CHANGE `smi_href` `smi_href` VARCHAR( 501 ) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ;


INSERT INTO `_system_menu_item` (`ID`, `smi_title`, `smi_order`, `smi_locale`, `smi_association`, `smi_href`, `smi_alternative_text`, `ID__system_menu_item_smi_children`, `creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_date`, `deletion_user`, `deletion_flag`, `activation_flag`) VALUES
(108, 'Documenti PER lo Studio', 9, 'it', NULL, '?q=object/list&p=DocumentoCliente/_a_da_cliente/_v_@FUN_GET_USER_INFO(param=id)@&t=DocumentoCliente_cliente&title=Documenti inviati allo Studio&order-by=data desc', 'Documenti PER lo Studio', 104, '2014-01-17', 'admin', '2014-01-17', 'admin', NULL, NULL, 0, 1),
(107, 'Le mie scadenze', 11, 'it', NULL, '?q=object/list&p=NotificaScadenza/_a_ID_Cliente_avvisi_scadenze/_v_@FUN_CLIENTE_FROM_SYS_USER()@&t=NotificaScadenza_cliente', 'Le mie scadenze', 104, '2014-01-17', 'admin', '2014-01-17', 'admin', NULL, NULL, 0, 1),
(106, 'La mia bacheca', 1, 'it', NULL, '?q=homepage_clienti/detai', 'La mia bacheca', 104, '2014-01-17', 'admin', '2014-01-17', 'admin', NULL, NULL, 0, 1),
(105, 'Corrispondenza DALLO Studio', 7, 'it', NULL, '?q=object/list&p=Documento/_a_storia_documento/_c_StoriaDocumento/_a_cliente_doc/_v_@FUN_CLIENTE_FROM_SYS_USER()@&data_source=cliente-corrispondenza&t=Documento_cliente&title=Corrispondenza Studio&order-by=data_riferimento desc', 'Corrispondenza DALLO Studio', 104, '2014-01-17', 'admin', '2014-01-17', 'admin', NULL, NULL, 0, 1),
(104, 'Root cliente', 99, 'it', NULL, NULL, NULL, NULL, '2014-01-17', 'admin', '2014-01-17', 'admin', NULL, NULL, 0, 1),
(103, 'Documenti DALLO Studio', 5, 'it', NULL, '?q=object/list&p=Documento/_a_storia_documento/_c_StoriaDocumento/_a_cliente_doc/_v_@FUN_CLIENTE_FROM_SYS_USER()@&data_source=cliente-documenti&t=Documento_cliente&title=Documenti dallo Studio&order-by=data_riferimento desc', 'Documenti DALLO Studio', 104, '2014-01-17', 'admin', '2014-01-17', 'admin', NULL, NULL, 0, 1);


INSERT INTO `_system_module` (`ID`, `ID__system_menu_item_sm_menu`, `sm_name`, `sm_order`, `sm_java_class`, `sm_alternative_text`, `sm_description`, `sm_show`, `sm_position`, `sm_default_parameters`, `sm_default_call`, `sm_auto_load`, `ID__system_meta_environment_sme_modules`, `creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_date`, `deletion_user`, `deletion_flag`, `activation_flag`) VALUES
(42, 104, 'Menu Area Clienti', 4, 'eu.anastasis.serena.application.modules.menu.MenuModule', 'Menu Area Clienti', 'Menu Area Clienti', 'Servizi attivi', 2, NULL, 'call', 3, 1, '2011-07-15', 'admin', '2014-01-17', 'admin', '2012-04-16', 'admin', 0, 1);


DROP VIEW IF EXISTS `VW_Documenti_Cliente_Corrispondenza`;
CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_Documenti_Cliente_Corrispondenza` AS select `d`.`ID` AS `ID`,`d`.`titolo` AS `titolo`,`d`.`abstract` AS `abstract`,`d`.`anno_contabile` AS `anno_contabile`,`d`.`data_riferimento` AS `data_riferimento`,`d`.`free_tag` AS `free_tag`,`d`.`allegato` AS `allegato`,`d`.`allegato_1` AS `allegato_1`,`d`.`creation_date` AS `creation_date`,`d`.`creation_user` AS `creation_user`,`d`.`last_modification_date` AS `last_modification_date`,`d`.`last_modification_user` AS `last_modification_user`,`d`.`deletion_date` AS `deletion_date`,`d`.`deletion_user` AS `deletion_user`,`d`.`deletion_flag` AS `deletion_flag`,`d`.`activation_flag` AS `activation_flag`,`d`.`ID_DescrizioneDocumento_inverse_of_tipologia` AS `ID_DescrizioneDocumento_inverse_of_tipologia` from (`Documento` `d` join `DescrizioneDocumento` `t` on((`d`.`ID_DescrizioneDocumento_inverse_of_tipologia` = `t`.`ID`))) 
where ((not((`t`.`tipologia_documento` like '%UNICO%'))) and (not((`t`.`tipologia_documento` like '%BILANCI%'))) and (not((`t`.`tipologia_documento` like '%IVA%'))) and (not((`t`.`tipologia_documento` like '%730%'))) and (not((`t`.`tipologia_documento` like '%770%'))) and (not((`t`.`tipologia_documento` like '%IRAP%'))) and (not((`t`.`tipologia_documento` like '%F24%'))));

DROP VIEW IF EXISTS `VW_Documenti_Cliente_Documenti`;
CREATE ALGORITHM=UNDEFINED DEFINER=`tullini`@`localhost` SQL SECURITY DEFINER VIEW `VW_Documenti_Cliente_Documenti` AS select `d`.`ID` AS `ID`,`d`.`titolo` AS `titolo`,`d`.`abstract` AS `abstract`,`d`.`anno_contabile` AS `anno_contabile`,`d`.`data_riferimento` AS `data_riferimento`,`d`.`free_tag` AS `free_tag`,`d`.`allegato` AS `allegato`,`d`.`allegato_1` AS `allegato_1`,`d`.`creation_date` AS `creation_date`,`d`.`creation_user` AS `creation_user`,`d`.`last_modification_date` AS `last_modification_date`,`d`.`last_modification_user` AS `last_modification_user`,`d`.`deletion_date` AS `deletion_date`,`d`.`deletion_user` AS `deletion_user`,`d`.`deletion_flag` AS `deletion_flag`,`d`.`activation_flag` AS `activation_flag`,`d`.`ID_DescrizioneDocumento_inverse_of_tipologia` AS `ID_DescrizioneDocumento_inverse_of_tipologia` from (`Documento` `d` join `DescrizioneDocumento` `t` on((`d`.`ID_DescrizioneDocumento_inverse_of_tipologia` = `t`.`ID`))) 
where ((`t`.`tipologia_documento` like '%UNICO%') or (`t`.`tipologia_documento` like '%BILANCI%') or (`t`.`tipologia_documento` like '%IVA%') or (`t`.`tipologia_documento` like '%730%') or (`t`.`tipologia_documento` like '%770%') or (`t`.`tipologia_documento` like '%IRAP%') or (`t`.`tipologia_documento` like '%F24%'));


/* onclick="loading(this,{text:'Attendere prego....'});"  in generazione fatture */ 
