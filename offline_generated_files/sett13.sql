ALTER TABLE  `VoceFattura` CHANGE  `importo`  `importo` DECIMAL( 9, 2 ) NOT NULL;

ALTER TABLE  `ProForma` CHANGE  `spese_anticipate_fattura`  `spese_anticipate_fattura` DECIMAL( 9, 2 ) NOT NULL DEFAULT  '0';

ALTER TABLE  `Fattura` CHANGE  `spese_anticipate_fattura`  `spese_anticipate_fattura` DECIMAL( 9, 2 ) NOT NULL DEFAULT  '0';

DROP TABLE  `_import_attivita` ,
`_import_clienti_virgin` ,
`_import_codcli3` ,
`_import_dati_contabili_virgin` ,
`_import_fatture` ,
`_import_pratiche` ,
`_import_proforma` ;


delete FROM `Pratica`  WHERE not exists
(select 1 from Cliente  c where c.ID = `ID_Cliente_pratiche`);

SELECT ID
FROM `Pratica`
GROUP BY `titolo` , `anno_contabile` , `deletion_flag` , `ID_Cliente_pratiche`
HAVING count( * ) >1
ORDER BY id;


update Pratica set titolo = concat (titolo,' PRATICA DUPLICATA 1 VOLTA!') where ID in
(520,1103,1264,3404,4370,4400,4471,4686,4733,5156,5551,6115,6145,6148,6149,6162,6163,6164,6165,6167,6168,6169,6172,6182,6184,6216,6284,6346,6361,6362,6378,6612,6696,6711,6726,6734,6735,6852,6889);

update Pratica set titolo = concat (titolo,' PRATICA DUPLICATA 2 VOLTE!') where ID in
(6171,6363,6367,6623,6629,6630,6645,6872,6885);

update Pratica set titolo = concat (titolo,' PRATICA DUPLICATA 3 VOLTE!') where ID in
(6238,6620,6626,6886);

update Pratica set titolo = concat (titolo,' PRATICA DUPLICATA 4 VOLTE!') where ID in
(6372,6621,6879);

update Pratica set titolo = concat (titolo,' PRATICA DUPLICATA 5 VOLTE!') where ID in
(6622,6633);

update Pratica set titolo = concat (titolo,' PRATICA DUPLICATA 12 VOLTE!') where ID in
(6905);



ALTER TABLE `Pratica` ADD UNIQUE (
`titolo` ,
`anno_contabile` ,
`deletion_flag` ,
`ID_Cliente_pratiche`
);


INSERT INTO _system_menu_item ( ID, smi_href, smi_order, ID__system_menu_item_smi_children, smi_locale, smi_title, activation_flag, smi_alternative_text, deletion_flag, creation_user, creation_date, last_modification_user, last_modification_date ) VALUES( 100, "?q=object/filter&p=_system_news", "3", "32", "it", "Bacheca ufficio", "1", "Bacheca ufficio", "0", "fabio", "2013-09-18", "fabio", "2013-09-18" );


--
DROP VIEW IF EXISTS `VW_LavoroSuAttivita_report`;

CREATE VIEW `VW_LavoroSuAttivita_report` AS select `LavoroSuAttivita`.`ID` AS `ID`,cast(`LavoroSuAttivita`.`data` as date) AS `data`,`LavoroSuAttivita`.`durata_minuti` AS `durata_minuti`,`LavoroSuAttivita`.`diario` AS `diario`,`LavoroSuAttivita`.`allegato` AS `allegato`,`LavoroSuAttivita`.`creation_date` AS `creation_date`,`LavoroSuAttivita`.`creation_user` AS `creation_user`,`LavoroSuAttivita`.`last_modification_date` AS `last_modification_date`,`LavoroSuAttivita`.`last_modification_user` AS `last_modification_user`,`LavoroSuAttivita`.`deletion_date` AS `deletion_date`,`LavoroSuAttivita`.`deletion_user` AS `deletion_user`,`LavoroSuAttivita`.`deletion_flag` AS `deletion_flag`,`LavoroSuAttivita`.`activation_flag` AS `activation_flag`,`LavoroSuAttivita`.`ID_Attivita_sessioni_di_lavoro` AS `ID_Attivita_sessioni_di_lavoro` from `LavoroSuAttivita` order by cast(`LavoroSuAttivita`.`data` as date);

-- --------------------------------------------------------

--
-- Struttura per la vista `VW_LavoroSuAttivita_ultime_sessioni`
--
DROP VIEW IF EXISTS `VW_LavoroSuAttivita_ultime_sessioni`;

CREATE VIEW `VW_LavoroSuAttivita_ultime_sessioni` AS select `l`.`ID` AS `ID`,`l`.`data` AS `data`,`l`.`durata_minuti` AS `durata_minuti`,`l`.`diario` AS `diario`,`l`.`allegato` AS `allegato`,`l`.`creation_date` AS `creation_date`,`l`.`creation_user` AS `creation_user`,`l`.`last_modification_date` AS `last_modification_date`,`l`.`last_modification_user` AS `last_modification_user`,`l`.`deletion_date` AS `deletion_date`,`l`.`deletion_user` AS `deletion_user`,`l`.`deletion_flag` AS `deletion_flag`,`l`.`activation_flag` AS `activation_flag`,`l`.`ID_Attivita_sessioni_di_lavoro` AS `ID_Attivita_sessioni_di_lavoro` from (`LavoroSuAttivita` `l` join `Attivita` `a` on((`l`.`ID_Attivita_sessioni_di_lavoro` = `a`.`ID`))) order by `l`.`data` desc limit 0,50;



update `ProForma` set `data_pagamento` = null where `data_pagamento` = '0000-00-00';

drop view if exists VW_STAT_RITARDO_MEDIO_CLIENTI;
create  view VW_STAT_RITARDO_MEDIO_CLIENTI as 
SELECT 1 as ID, (select cliente from Cliente c where c.ID = `ID_Cliente_proforma`) as p1, cast(cast(avg(datediff(`data_pagamento`,`data`)) as decimal(4,1)) as char(10)) as p2, count(*) as p3,null as p4, null as p5, curdate() as creation_date, curdate() as last_modification_date, 'admin' as creation_user, 'admin' as last_modification_user, 0 as deletion_flag, 1 as activation_flag   FROM `ProForma` WHERE (`data_pagamento` is not null and `data_pagamento` <> '')  and deletion_flag=0 and activation_flag=1
group by `ID_Cliente_proforma`
union 
SELECT 1 as ID, (select cliente from Cliente c where c.ID = `ID_Cliente_proforma`) as p1, cast('NON PAGATA' as char(10)) as p2, count(*) as p3,null as p4, null as p5, curdate() as creation_date, curdate() as last_modification_date, 'admin' as creation_user, 'admin' as last_modification_user, 0 as deletion_flag, 1 as activation_flag   FROM `ProForma` WHERE (`data_pagamento` is null or `data_pagamento`  = '') and deletion_flag=0 and activation_flag=1
group by `ID_Cliente_proforma`
order by p1,p2;


drop view if exists `VW_STAT_TOTALE_PROFORMA_ANNO` ;
create  view VW_STAT_TOTALE_PROFORMA_ANNO as 
SELECT 1 as ID, (select sd_description from _system_decode where sd_value = anno_contabile and sd_class = 207) as p1, 
cast(sum(importo) as decimal (9,2)) as p2,
anno_contabile as p3,null as p4, null as p5, 
curdate() as creation_date, curdate() as last_modification_date, 'admin' as creation_user, 'admin' as last_modification_user, 0 as deletion_flag, 1 as activation_flag   
FROM `ProForma` p INNER JOIN VoceFattura v on ID_ProForma_voci_proforma = p.ID where p.deletion_flag=0 and p.activation_flag=1  and v.deletion_flag=0 and v.activation_flag=1 
group by p1
order by p1;


drop view if exists `VW_STAT_TOTALE_FATTURA_ANNO` ;
create  view VW_STAT_TOTALE_FATTURA_ANNO as 
SELECT 1 as ID, (select sd_description from _system_decode where sd_value = anno_contabile and sd_class = 207) as p1, 
cast(sum(importo) as decimal (9,2)) as p2,
anno_contabile as p3,null as p4, null as p5, 
curdate() as creation_date, curdate() as last_modification_date, 'admin' as creation_user, 'admin' as last_modification_user, 0 as deletion_flag, 1 as activation_flag   
FROM `Fattura` p INNER JOIN VoceFattura v on ID_Fattura_voci_fattura = p.ID where p.deletion_flag=0 and p.activation_flag=1 and v.deletion_flag=0 and v.activation_flag=1  
group by anno_contabile
order by anno_contabile;


drop view if exists VW_STAT_RIEPILOGO_PROFORMA; 

create  view VW_STAT_RIEPILOGO_PROFORMA as 
SELECT 1 as ID, 
(select sd_description from _system_decode where sd_value = anno_contabile and sd_class = 207) as p1, 
(select cliente from Cliente c where c.ID = `ID_Cliente_proforma`) as p2, 
cast(avg(datediff(`data_pagamento`,`data`)) as decimal(4,1)) as p3, 
cast(sum(importo) as decimal (9,2)) as p4 ,
(cast(sum(importo) as signed) / cast(t.p2 as signed) * 100) as p5,
curdate() as creation_date, curdate() as last_modification_date, 'admin' as creation_user, 'admin' as last_modification_user, 0 as deletion_flag, 1 as activation_flag   
FROM `ProForma` p INNER JOIN VoceFattura on ID_ProForma_voci_proforma = p.ID
INNER JOIN VW_STAT_TOTALE_PROFORMA_ANNO t on anno_contabile = t.p3
where p.deletion_flag=0 and p.activation_flag=1
group by `ID_Cliente_proforma`, anno_contabile
order by p1,p2;


drop view if exists VW_STAT_RIEPILOGO_FATTURA;
create  view VW_STAT_RIEPILOGO_FATTURA as 
SELECT 1 as ID, 
(select sd_description from _system_decode where sd_value = anno_contabile and sd_class = 207) as p1, 
(select cliente from Cliente c where c.ID = `ID_Cliente_fatture`) as p2, 
null as p3, 
cast(sum(importo) as decimal (9,2)) as p4,
(cast(sum(importo) as signed) / cast(t.p2 as signed) * 100) as p5,
curdate() as creation_date, curdate() as last_modification_date, 'admin' as creation_user, 'admin' as last_modification_user, 0 as deletion_flag, 1 as activation_flag   
FROM `Fattura` p INNER JOIN VoceFattura on ID_Fattura_voci_fattura = p.ID 
INNER JOIN VW_STAT_TOTALE_FATTURA_ANNO t on anno_contabile = t.p3
where p.deletion_flag=0 and p.activation_flag=1
group by `ID_Cliente_fatture`, anno_contabile
order by p1,p2;




/*
SELECT 1 as ID, (select cliente from Cliente c where c.ID = `ID_Cliente_proforma`) as p1, avg(datediff(`data_pagamento`,`data`)) as p2, count(*) as p3,null as p4, null as p5, curdate() as creation_date, curdate() as last_modification_date, 'admin' as creation_user, 'admin' as last_modification_user, 0 as deletion_flag, 1 as activation_flag   FROM `ProForma` WHERE `data_pagamento` is not null and `data_pagamento` <> '' 
group by `ID_Cliente_proforma`
order by p2;
*/


drop view if exists VW_STAT_RITARDO_MEDIO_ANNI;
create  view VW_STAT_RITARDO_MEDIO_ANNI as 
SELECT 1 as ID, (select sd_description from _system_decode where sd_value = anno_contabile and sd_class = 207) as p1, avg(datediff(`data_pagamento`,`data`)) as p2, count(*) as p3,null as p4, null as p5, curdate() as creation_date, curdate() as last_modification_date, 'admin' as creation_user, 'admin' as last_modification_user, 0 as deletion_flag, 1 as activation_flag   FROM `ProForma` WHERE `data_pagamento` is not null and `data_pagamento` <> '' 
group by anno_contabile
order by anno_contabile;


drop view if exists VW_CHECK_VOCIFATTURA_ORFANE;
create  view VW_CHECK_VOCIFATTURA_ORFANE as 
select * from  VoceFattura   where not exists (select 1 FROM `ProForma` p where   ID_ProForma_voci_proforma = p.ID) and  not exists (select 1 FROM Fattura f where   ID_Fattura_voci_fattura	 = f.ID);

delete FROM  VW_CHECK_VOCIFATTURA_ORFANE;
delete from VoceFattura where deletion_flag=1;

DROP VIEW IF EXISTS `VW_ProForma_Totali`;
CREATE VIEW `VW_ProForma_Totali` AS select `p`.`ID` AS `ID`,`p`.`numero` AS `numero`,`p`.`data` AS `data`,`p`.`data_pagamento` AS `data_pagamento`,`p`.`competenza` AS `competenza`,`p`.`ID_Cliente_proforma` AS `ID_Cliente_proforma`,`p`.`anno_contabile` AS `anno_contabile`,`p`.`spese_anticipate_desc` AS `spese_anticipate_desc`,`p`.`stato_proforma` AS `stato_proforma`,`p`.`non_incassabile` AS `non_incassabile`,`v2`.`importo` AS `Rivalsa_Previdenziale`,`v3`.`importo_iva_0` AS `Totale_Imponibile_0`,`v3`.`importo_iva_1` AS `Totale_Imponibile_1`,`v3`.`importo_iva_2` AS `Totale_Imponibile_2`,`v3`.`importo_iva_3` AS `Totale_Imponibile_3`,`v3`.`importo_iva_4` AS `Totale_Imponibile_4`,`v4`.`iva_0` AS `Iva_0`,`v4`.`iva_1` AS `Iva_1`,`v4`.`iva_2` AS `Iva_2`,`v4`.`iva_3` AS `Iva_3`,`v4`.`iva_4` AS `Iva_4`,`p`.`spese_anticipate_fattura` AS `Spese_Escluse_Da_Imponibile`,`v5`.`importo` AS `Totale_Fattura`,`v6`.`importo` AS `Ritenuta_Acconto`,`v7`.`importo` AS `Totale_Da_Pagare`,`p`.`creation_date` AS `creation_date`,`p`.`creation_user` AS `creation_user`,`p`.`last_modification_date` AS `last_modification_date`,`p`.`last_modification_user` AS `last_modification_user`,`p`.`deletion_date` AS `deletion_date`,`p`.`deletion_user` AS `deletion_user`,`p`.`deletion_flag` AS `deletion_flag`,`p`.`activation_flag` AS `activation_flag` from ((((((`ProForma` `p` join `VW_ProForma_Rivalsa_Previdenziale` `v2` on((`v2`.`ID` = `p`.`ID`))) join `VW_ProForma_Totale_Imponibile` `v3` on((`v3`.`ID` = `p`.`ID`))) join `VW_ProForma_Iva` `v4` on((`v4`.`ID` = `p`.`ID`))) join `VW_ProForma_Totale_ProForma` `v5` on((`v5`.`ID` = `p`.`ID`))) join `VW_ProForma_Ritenuta_Acconto` `v6` on((`v6`.`ID` = `p`.`ID`))) join `VW_ProForma_Totale_Da_Pagare` `v7` on((`v7`.`ID` = `p`.`ID`)));


SELECT p.ID, v.ID as VID,
 (select sd_description from _system_decode where sd_value = anno_contabile and sd_class = 207) as p1, 
cast(importo as decimal (9,2)) as p2
FROM `ProForma` p INNER JOIN VoceFattura v on ID_ProForma_voci_proforma = p.ID where p.deletion_flag=0 and p.activation_flag=1  and v.deletion_flag=0 and v.activation_flag=1 
order by ID,VID limit 0,10000;











update `VoceFattura` set `iva` = 6 where iva = 20;

update `Pratica` set `tipo` = 1, stato=0 WHERE `titolo` LIKE 'ANTICIPO SPESE PER CLIENTI';
UPDATE `Metapratica` SET `tipo` = 1 WHERE `Metapratica`.`ID` =3674; 

INSERT INTO `tullini-helpgest`.`_system_decode` (
`sd_description` ,
`sd_value` ,
`sd_locale` ,
`sd_notes` ,
`ID__system_decode_sd_parent` ,
`sd_image` ,
`creation_date` ,
`creation_user` ,
`last_modification_date` ,
`last_modification_user` ,
`deletion_date` ,
`deletion_user` ,
`deletion_flag` ,
`activation_flag` ,
`sd_class`
)
VALUES ('22%', '5', 'it', '22', NULL , NULL , '2011-07-15', 'admin', NULL , NULL , NULL , NULL , '0', '1', '232');


INSERT INTO `tullini-helpgest`.`_system_decode` (
`sd_description` ,
`sd_value` ,
`sd_locale` ,
`sd_notes` ,
`ID__system_decode_sd_parent` ,
`sd_image` ,
`creation_date` ,
`creation_user` ,
`last_modification_date` ,
`last_modification_user` ,
`deletion_date` ,
`deletion_user` ,
`deletion_flag` ,
`activation_flag` ,
`sd_class`
)
VALUES ('20%', '6', 'it', '20', NULL , NULL , '2011-07-15', 'admin', NULL , NULL , NULL , NULL , '0', '1', '232');



DROP VIEW IF EXISTS `VW_ProForma_Somma_Voci_tmp`;

CREATE VIEW `VW_ProForma_Somma_Voci_tmp` AS select `p`.`ID` AS `ID`,
(select sum(`v`.`importo`) AS `sum(v.importo)` from `VoceFattura` `v` where ((`v`.`deletion_flag` = 0) and (`v`.`iva` = 0) and (`v`.`ID_ProForma_voci_proforma` = `p`.`ID`))) AS `importo_iva_0`,
(select max(`v`.`iva`) AS `max(v.iva)` from `VoceFattura` `v` where (`v`.`deletion_flag` = 0 and `v`.`ID_ProForma_voci_proforma` = `p`.`ID`  )) AS `iva_0`,
(select sum(`v`.`importo`) AS `sum(v.importo)` from `VoceFattura` `v` where ((`v`.`deletion_flag` = 0) and (`v`.`iva` = 1) and (`v`.`ID_ProForma_voci_proforma` = `p`.`ID`))) AS `importo_iva_1`,
(select sum(`v`.`importo`) AS `sum(v.importo)` from `VoceFattura` `v` where ((`v`.`deletion_flag` = 0) and (`v`.`iva` = 2) and (`v`.`ID_ProForma_voci_proforma` = `p`.`ID`))) AS `importo_iva_2`,
(select sum(`v`.`importo`) AS `sum(v.importo)` from `VoceFattura` `v` where ((`v`.`deletion_flag` = 0) and (`v`.`iva` = 3) and (`v`.`ID_ProForma_voci_proforma` = `p`.`ID`))) AS `importo_iva_3`,
(select sum(`v`.`importo`) AS `sum(v.importo)` from `VoceFattura` `v` where ((`v`.`deletion_flag` = 0) and (`v`.`iva` >= 4) and (`v`.`ID_ProForma_voci_proforma` = `p`.`ID`))) AS `importo_iva_4` from `ProForma` `p`;


DROP VIEW IF EXISTS `VW_ProForma_Iva`;
CREATE VIEW `VW_ProForma_Iva` AS select `p`.`ID` AS `ID`,(((`p`.`importo_iva_0` * (select `_system_decode`.`sd_notes` AS `sd_notes` from `_system_decode` where ((`_system_decode`.`sd_class` = 232) and (`_system_decode`.`sd_value` = iva_0)))) / 100) + ((`p`.`importo_iva_0` * `p`.`iva_0`) / 100)) AS `iva_0`,((`p`.`importo_iva_1` * (select `_system_decode`.`sd_notes` AS `sd_notes` from `_system_decode` where ((`_system_decode`.`sd_class` = 232) and (`_system_decode`.`sd_value` = iva_0)))) / 100) AS `iva_1`,((`p`.`importo_iva_2` * (select `_system_decode`.`sd_notes` AS `sd_notes` from `_system_decode` where ((`_system_decode`.`sd_class` = 232) and (`_system_decode`.`sd_value` = iva_0)))) / 100) AS `iva_2`,((`p`.`importo_iva_3` * (select `_system_decode`.`sd_notes` AS `sd_notes` from `_system_decode` where ((`_system_decode`.`sd_class` = 232) and (`_system_decode`.`sd_value` = iva_0)))) / 100) AS `iva_3`,((`p`.`importo_iva_4` * (select `_system_decode`.`sd_notes` AS `sd_notes` from `_system_decode` where ((`_system_decode`.`sd_class` = 232) and (`_system_decode`.`sd_value` = p.iva_0)))) / 100) AS `iva_4` from `VW_ProForma_Totale_Imponibile` `p`;



DROP VIEW IF EXISTS `VW_Fattura_Somma_Voci_tmp`;
CREATE VIEW `VW_Fattura_Somma_Voci_tmp` AS select `p`.`ID` AS `ID`,
(select sum(`v`.`importo`) AS `sum(v.importo)` from `VoceFattura` `v` where ((`v`.`deletion_flag` = 0) and (`v`.`iva` = 0) and (`v`.`ID_Fattura_voci_fattura` = `p`.`ID`))) AS `importo_iva_0`,
(select max(`v`.`iva`) AS `max(v.iva)` from `VoceFattura` `v` where (`v`.`deletion_flag` = 0 and `v`.`ID_Fattura_voci_fattura` = `p`.`ID`  )) AS `iva_0`,
(select sum(`v`.`importo`) AS `sum(v.importo)` from `VoceFattura` `v` where ((`v`.`deletion_flag` = 0) and (`v`.`iva` = 1) and (`v`.`ID_Fattura_voci_fattura` = `p`.`ID`))) AS `importo_iva_1`,
(select sum(`v`.`importo`) AS `sum(v.importo)` from `VoceFattura` `v` where ((`v`.`deletion_flag` = 0) and (`v`.`iva` = 2) and (`v`.`ID_Fattura_voci_fattura` = `p`.`ID`))) AS `importo_iva_2`,
(select sum(`v`.`importo`) AS `sum(v.importo)` from `VoceFattura` `v` where ((`v`.`deletion_flag` = 0) and (`v`.`iva` = 3) and (`v`.`ID_Fattura_voci_fattura` = `p`.`ID`))) AS `importo_iva_3`,
(select sum(`v`.`importo`) AS `sum(v.importo)` from `VoceFattura` `v` where ((`v`.`deletion_flag` = 0) and (`v`.`iva` >= 4) and (`v`.`ID_Fattura_voci_fattura` = `p`.`ID`))) AS `importo_iva_4` from `Fattura` `p`;


DROP VIEW IF EXISTS `VW_Fattura_Iva`;
CREATE VIEW `VW_Fattura_Iva` AS select `p`.`ID` AS `ID`,(((`p`.`importo_iva_0` * (select `_system_decode`.`sd_notes` AS `sd_notes` from `_system_decode` where ((`_system_decode`.`sd_class` = 232) and (`_system_decode`.`sd_value` = iva_0)))) / 100) + ((`p`.`importo_iva_0` * `p`.`iva_0`) / 100)) AS `iva_0`,((`p`.`importo_iva_1` * (select `_system_decode`.`sd_notes` AS `sd_notes` from `_system_decode` where ((`_system_decode`.`sd_class` = 232) and (`_system_decode`.`sd_value` = iva_0)))) / 100) AS `iva_1`,((`p`.`importo_iva_2` * (select `_system_decode`.`sd_notes` AS `sd_notes` from `_system_decode` where ((`_system_decode`.`sd_class` = 232) and (`_system_decode`.`sd_value` = iva_0)))) / 100) AS `iva_2`,((`p`.`importo_iva_3` * (select `_system_decode`.`sd_notes` AS `sd_notes` from `_system_decode` where ((`_system_decode`.`sd_class` = 232) and (`_system_decode`.`sd_value` = iva_0)))) / 100) AS `iva_3`,((`p`.`importo_iva_4` * (select `_system_decode`.`sd_notes` AS `sd_notes` from `_system_decode` where ((`_system_decode`.`sd_class` = 232) and (`_system_decode`.`sd_value` = iva_0)))) / 100) AS `iva_4` from `VW_Fattura_Totale_Imponibile` `p`;



DROP VIEW IF EXISTS `VW_Pratiche_chiuse_senza_data_chiusura_2013`;
create view VW_Pratiche_chiuse_senza_data_chiusura_2013 as 
SELECT *
FROM `Pratica`
WHERE `tipo` =2
AND `data_chiusura` IS  NULL
AND `stato` =1
AND `anno_contabile` =3;