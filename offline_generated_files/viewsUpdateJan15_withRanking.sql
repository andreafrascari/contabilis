DROP VIEW IF EXISTS `VW_STAT_COSTO_CLIENTI_INNER_QUERY`;

CREATE  VIEW `VW_STAT_COSTO_CLIENTI_INNER_QUERY` AS 
select lsa.durata_minuti as minuti, 
cast((lsa.durata_minuti/60) as decimal(9,1)) as ore, 
lsa.creation_date as quando,
lsa.creation_user, op.costo_orario as CO, 
op.nome_e_cognome, 
cast((lsa.durata_minuti *  op.costo_orario  / 60) as decimal(9,2)) as costo, 
pra.ID_Cliente_pratiche as ID_Utente,
(select `_system_decode`.`sd_description` from `_system_decode` where ((`_system_decode`.`sd_value` = `pra`.`anno_contabile`) and (`_system_decode`.`sd_class` = 207))) AS `anno_contabile`
from LavoroSuAttivita lsa join _system_user u on lsa.creation_user = u.user_user_id join Operatore op on u.ID = op.ID__system_user_inverse_of_account	
join Attivita att on lsa.ID_Attivita_sessioni_di_lavoro = att.Id
join Pratica pra on att.ID_Pratica_attivita = pra.ID 
where lsa.deletion_flag=0;



DROP VIEW IF EXISTS `VW_STAT_COSTO_CLIENTI`;

CREATE  VIEW `VW_STAT_COSTO_CLIENTI` AS 
select 
sum(minuti) as minuti, 
sum(ore) as ore,
sum(costo) as costo,
ID_Utente,
anno_contabile
from VW_STAT_COSTO_CLIENTI_INNER_QUERY
group by ID_Utente, anno_contabile;


DROP VIEW IF EXISTS `VW_STAT_CHART_CLIENTI`;

CREATE  VIEW 
`VW_STAT_CHART_CLIENTI` AS select 1 AS `ID`,
(select `_system_decode`.`sd_description` from `_system_decode` where ((`_system_decode`.`sd_value` = `p`.`anno_contabile`) and (`_system_decode`.`sd_class` = 207))) AS `p1`,
(select `c`.`cliente` from `Cliente` `c` where (`c`.`ID` = `p`.`ID_Cliente_fatture`)) AS `p2`,
/* p3: tot ore cliente */
(select `cc`.`ore` from `VW_STAT_COSTO_CLIENTI` `cc` where ((`p`.`ID_Cliente_fatture` = `cc`.`ID_Utente`) and (`cc`.`anno_contabile` = `p1`))) AS `p3`,
/* p4: costo */
(select `cc`.`costo` from `VW_STAT_COSTO_CLIENTI` `cc` where ((`p`.`ID_Cliente_fatture` = `cc`.`ID_Utente`) and (`cc`.`anno_contabile` = `p1`))) AS `p4`,
/* p5: fatturato */
cast(sum(`VoceFattura`.`importo`) as decimal(9,2)) AS `p5`,
/* p6: % fatturato su totale */
((cast(sum(`VoceFattura`.`importo`) as signed) / cast(`t`.`p2` as signed)) * 100) AS `p6`,
/* p7: reddititivta totale */
(t.p2 -t.p5)  AS `p7`,
null as p8,
`p`.`ID_Cliente_fatture` AS `ID_cliente`,curdate() AS `creation_date`,curdate() AS `last_modification_date`,'admin' AS `creation_user`,'admin' AS `last_modification_user`,0 AS `deletion_flag`,1 AS `activation_flag` 
from (`Fattura` `p` join `VoceFattura` on((`VoceFattura`.`ID_Fattura_voci_fattura` = `p`.`ID`)))
join `VW_STAT_TOTALE_FATTURA_ANNO_NO_COMP` `t` on (`p`.`anno_contabile` = `t`.`p4`)   
where ((`p`.`deletion_flag` = 0) and (`p`.`activation_flag` = 1) and (`p`.`ID_Cliente_fatture` not in (82,303))) group by `p`.`ID_Cliente_fatture`,`p`.`anno_contabile` order by (select `_system_decode`.`sd_description` from `_system_decode` where ((`_system_decode`.`sd_value` = `p`.`anno_contabile`) and (`_system_decode`.`sd_class` = 207))),(select `c`.`cliente` from `Cliente` `c` where (`c`.`ID` = `p`.`ID_Cliente_fatture`));


DROP   VIEW if exists `VW_STAT_CHART_CLIENTI_PROFORMA`;
CREATE  VIEW 
`VW_STAT_CHART_CLIENTI_PROFORMA` AS select 1 AS `ID`,
(select `_system_decode`.`sd_description` from `_system_decode` where ((`_system_decode`.`sd_value` = `p`.`anno_contabile`) and (`_system_decode`.`sd_class` = 207))) AS `p1`,
(select `c`.`cliente` from `Cliente` `c` where (`c`.`ID` = `p`.`ID_Cliente_proforma`)) AS `p2`,
/* p3: tot ore cliente */
(select `cc`.`ore` from `VW_STAT_COSTO_CLIENTI` `cc` where ((`p`.`ID_Cliente_proforma` = `cc`.`ID_Utente`) and (`cc`.`anno_contabile` = `p1`))) AS `p3`,
/* p4: costo */
(select `cc`.`costo` from `VW_STAT_COSTO_CLIENTI` `cc` where ((`p`.`ID_Cliente_proforma` = `cc`.`ID_Utente`) and (`cc`.`anno_contabile` = `p1`))) AS `p4`,
/* p5: fatturato */
cast(sum(`VoceFattura`.`importo`) as decimal(9,2)) AS `p5`,
/* p6: % fatturato su totale */
((cast(sum(`VoceFattura`.`importo`) as signed) / cast(`t`.`p2` as signed)) * 100) AS `p6`,
/* p7: reddititivta totale */
(t.p2 -t.p5)  AS `p7`,
null as p8,
`p`.`ID_Cliente_proforma` AS `ID_cliente`,curdate() AS `creation_date`,curdate() AS `last_modification_date`,'admin' AS `creation_user`,'admin' AS `last_modification_user`,0 AS `deletion_flag`,1 AS `activation_flag` 
from (`ProForma` `p` join `VoceFattura` on((`VoceFattura`.`ID_ProForma_voci_proforma` = `p`.`ID`)))
join `VW_STAT_TOTALE_PROFORMA_ANNO_NO_COMP` `t` on (`p`.`anno_contabile` = `t`.`p4`)   
where ((`p`.`deletion_flag` = 0) and (`p`.`activation_flag` = 1) and (`p`.`ID_Cliente_proforma` not in (82,303))) group by `p`.`ID_Cliente_proforma`,`p`.`anno_contabile` order by (select `_system_decode`.`sd_description` from `_system_decode` where ((`_system_decode`.`sd_value` = `p`.`anno_contabile`) and (`_system_decode`.`sd_class` = 207))),(select `c`.`cliente` from `Cliente` `c` where (`c`.`ID` = `p`.`ID_Cliente_proforma`));



DROP VIEW if exists  `VW_Fattura_Iva` ;
CREATE VIEW `VW_Fattura_Iva` AS 
select `p`.`ID` AS `ID`,
((`p`.`importo_iva_0` * (select `_system_decode`.`sd_notes` AS `sd_notes` from `_system_decode` where ((`_system_decode`.`sd_class` = 232) and (`_system_decode`.`sd_value` = 0)))) / 100) AS `iva_0`,
((`p`.`importo_iva_1` * (select `_system_decode`.`sd_notes` AS `sd_notes` from `_system_decode` where ((`_system_decode`.`sd_class` = 232) and (`_system_decode`.`sd_value` = 1)))) / 100) AS `iva_1`,
((`p`.`importo_iva_2` * (select `_system_decode`.`sd_notes` AS `sd_notes` from `_system_decode` where ((`_system_decode`.`sd_class` = 232) and (`_system_decode`.`sd_value` = 2)))) / 100) AS `iva_2`,
((`p`.`importo_iva_3` * (select `_system_decode`.`sd_notes` AS `sd_notes` from `_system_decode` where ((`_system_decode`.`sd_class` = 232) and (`_system_decode`.`sd_value` = 3)))) / 100) AS `iva_3`,
((`p`.`importo_iva_4` * (select `_system_decode`.`sd_notes` AS `sd_notes` from `_system_decode` where ((`_system_decode`.`sd_class` = 232) and (`_system_decode`.`sd_value` = `p`.`iva_0`)))) / 100) AS `iva_4` 
from `VW_Fattura_Totale_Imponibile` `p`;

DROP VIEW IF EXISTS `VW_ProForma_Iva`;
CREATE  VIEW `VW_ProForma_Iva` AS 
select `p`.`ID` AS `ID`,
((`p`.`importo_iva_0` * (select `_system_decode`.`sd_notes` AS `sd_notes` from `_system_decode` where ((`_system_decode`.`sd_class` = 232) and (`_system_decode`.`sd_value` = 0)))) / 100) AS `iva_0`,
((`p`.`importo_iva_1` * (select `_system_decode`.`sd_notes` AS `sd_notes` from `_system_decode` where ((`_system_decode`.`sd_class` = 232) and (`_system_decode`.`sd_value` = 1)))) / 100) AS `iva_1`,
((`p`.`importo_iva_2` * (select `_system_decode`.`sd_notes` AS `sd_notes` from `_system_decode` where ((`_system_decode`.`sd_class` = 232) and (`_system_decode`.`sd_value` = 2)))) / 100) AS `iva_2`,
((`p`.`importo_iva_3` * (select `_system_decode`.`sd_notes` AS `sd_notes` from `_system_decode` where ((`_system_decode`.`sd_class` = 232) and (`_system_decode`.`sd_value` = 3)))) / 100) AS `iva_3`,
((`p`.`importo_iva_4` * (select `_system_decode`.`sd_notes` AS `sd_notes` from `_system_decode` where ((`_system_decode`.`sd_class` = 232) and (`_system_decode`.`sd_value` = `p`.`iva_0`)))) / 100) AS `iva_4` 
from `VW_ProForma_Totale_Imponibile` `p`;





DROP VIEW IF EXISTS `VW_STAT_TOTALE_FATTURA_ANNO`;

CREATE VIEW `VW_STAT_TOTALE_FATTURA_ANNO` AS select 1 AS `ID`,(select `_system_decode`.`sd_description` from `_system_decode` where ((`_system_decode`.`sd_value` = `p`.`anno_contabile`) and (`_system_decode`.`sd_class` = 207))) AS `p1`,
cast(sum(`v`.`importo`) as decimal(9,2)) AS `p2`,
(select `_system_decode`.`sd_description` from `_system_decode` where ((`_system_decode`.`sd_value` = `p`.`competenza`) and (`_system_decode`.`sd_class` = 224))) AS `p3`,
`p`.`anno_contabile` AS `p4`,NULL AS `p5`,NULL AS `p6`,curdate() AS `creation_date`,curdate() AS `last_modification_date`,'admin' AS `creation_user`,'admin' AS `last_modification_user`,0 AS `deletion_flag`,1 AS `activation_flag` from (`Fattura` `p` join `VoceFattura` `v` on((`v`.`ID_Fattura_voci_fattura` = `p`.`ID`))) where ((`p`.`deletion_flag` = 0) and (`p`.`activation_flag` = 1) and (`v`.`deletion_flag` = 0) and (`v`.`activation_flag` = 1)) group by `p`.`anno_contabile`,`p`.`competenza` order by `p`.`anno_contabile`;

 
DROP VIEW IF EXISTS `VW_STAT_TOTALE_PROFORMA_ANNO`;

CREATE VIEW `VW_STAT_TOTALE_PROFORMA_ANNO` AS select 1 AS `ID`,(select `_system_decode`.`sd_description` from `_system_decode` where ((`_system_decode`.`sd_value` = `p`.`anno_contabile`) and (`_system_decode`.`sd_class` = 207))) AS `p1`,
cast(sum(`v`.`importo`) as decimal(9,2)) AS `p2`,
(select `_system_decode`.`sd_description` from `_system_decode` where ((`_system_decode`.`sd_value` = `p`.`competenza`) and (`_system_decode`.`sd_class` = 224))) AS `p3`,
`p`.`anno_contabile` AS `p4`,NULL AS `p5`,NULL AS `p6`,curdate() AS `creation_date`,curdate() AS `last_modification_date`,'admin' AS `creation_user`,'admin' AS `last_modification_user`,0 AS `deletion_flag`,1 AS `activation_flag` from (`ProForma` `p` join `VoceFattura` `v` on((`v`.`ID_ProForma_voci_proforma` = `p`.`ID`))) where ((`p`.`deletion_flag` = 0) and (`p`.`activation_flag` = 1) and (`v`.`deletion_flag` = 0) and (`v`.`activation_flag` = 1)) group by `p`.`anno_contabile`,`p`.`competenza` order by `p`.`anno_contabile`;


DROP VIEW IF EXISTS `VW_STAT_RIEPILOGO_FATTURA`;

CREATE  VIEW `VW_STAT_RIEPILOGO_FATTURA` AS select 1 AS `ID`,(select `_system_decode`.`sd_description` from `_system_decode` where ((`_system_decode`.`sd_value` = `p`.`anno_contabile`) and (`_system_decode`.`sd_class` = 207))) AS `p1`,(select `c`.`cliente` from `Cliente` `c` where (`c`.`ID` = `p`.`ID_Cliente_fatture`)) AS `p2`,
(select `_system_decode`.`sd_description` from `_system_decode` where ((`_system_decode`.`sd_value` = `p`.`competenza`) and (`_system_decode`.`sd_class` = 224))) AS `p3`,
NULL AS `p4`,cast(sum(`VoceFattura`.`importo`) as decimal(9,2)) AS `p5`,((cast(sum(`VoceFattura`.`importo`) as signed) / cast(`t`.`p2` as signed)) * 100) AS `p6`,curdate() AS `creation_date`,curdate() AS `last_modification_date`,'admin' AS `creation_user`,'admin' AS `last_modification_user`,0 AS `deletion_flag`,1 AS `activation_flag` from ((`Fattura` `p` join `VoceFattura` on((`VoceFattura`.`ID_Fattura_voci_fattura` = `p`.`ID`))) join `VW_STAT_TOTALE_FATTURA_ANNO` `t` on((`p`.`anno_contabile` = `t`.`p4`) and (`p`.`competenza` = `t`.`p3`))) where ((`p`.`deletion_flag` = 0) and (`p`.`activation_flag` = 1)) group by `p`.`ID_Cliente_fatture`,`p`.`anno_contabile` order by (select `_system_decode`.`sd_description` from `_system_decode` where ((`_system_decode`.`sd_value` = `p`.`anno_contabile`) and (`_system_decode`.`sd_class` = 207))),(select `c`.`cliente` from `Cliente` `c` where (`c`.`ID` = `p`.`ID_Cliente_fatture`));

DROP VIEW IF EXISTS `VW_STAT_RIEPILOGO_PROFORMA`;

CREATE  VIEW `VW_STAT_RIEPILOGO_PROFORMA` AS select 1 AS `ID`,(select `_system_decode`.`sd_description` from `_system_decode` where ((`_system_decode`.`sd_value` = `p`.`anno_contabile`) and (`_system_decode`.`sd_class` = 207))) AS `p1`,(select `c`.`cliente` from `Cliente` `c` where (`c`.`ID` = `p`.`ID_Cliente_proforma`)) AS `p2`,
(select `_system_decode`.`sd_description` from `_system_decode` where ((`_system_decode`.`sd_value` = `p`.`competenza`) and (`_system_decode`.`sd_class` = 224))) AS `p3`,
cast(avg((to_days(`p`.`data_pagamento`) - to_days(`p`.`data`))) as decimal(4,1)) AS `p4`,cast(sum(`VoceFattura`.`importo`) as decimal(9,2)) AS `p5`,((cast(sum(`VoceFattura`.`importo`) as signed) / cast(`t`.`p2` as signed)) * 100) AS `p6`,curdate() AS `creation_date`,curdate() AS `last_modification_date`,'admin' AS `creation_user`,'admin' AS `last_modification_user`,0 AS `deletion_flag`,1 AS `activation_flag` from ((`ProForma` `p` join `VoceFattura` on((`VoceFattura`.`ID_ProForma_voci_proforma` = `p`.`ID`))) join `VW_STAT_TOTALE_PROFORMA_ANNO` `t` on((`p`.`anno_contabile` = `t`.`p4`)  and (`p`.`competenza` = `t`.`p3`))) where ((`p`.`deletion_flag` = 0) and (`p`.`activation_flag` = 1)) group by `p`.`ID_Cliente_proforma`,`p`.`anno_contabile` order by (select `_system_decode`.`sd_description` from `_system_decode` where ((`_system_decode`.`sd_value` = `p`.`anno_contabile`) and (`_system_decode`.`sd_class` = 207))),(select `c`.`cliente` from `Cliente` `c` where (`c`.`ID` = `p`.`ID_Cliente_proforma`));


/* senza competenza */

DROP VIEW IF EXISTS `VW_STAT_TOTALE_FATTURA_ANNO_NO_COMP`;

CREATE VIEW `VW_STAT_TOTALE_FATTURA_ANNO_NO_COMP` AS select 1 AS `ID`,
(select `_system_decode`.`sd_description` from `_system_decode` where ((`_system_decode`.`sd_value` = `p`.`anno_contabile`) and (`_system_decode`.`sd_class` = 207))) AS `p1`,
cast(sum(`v`.`importo`) as decimal(9,2)) AS `p2`,
null AS `p3`,
`p`.`anno_contabile` AS `p4`,
(select sum(c.costo) from VW_STAT_COSTO_CLIENTI c where  c.anno_contabile = p1) AS `p5`,
NULL AS `p6`,curdate() AS `creation_date`,curdate() AS `last_modification_date`,'admin' AS `creation_user`,'admin' AS `last_modification_user`,0 AS `deletion_flag`,1 AS `activation_flag`
from (`Fattura` `p` join `VoceFattura` `v` on `v`.`ID_Fattura_voci_fattura` = `p`.`ID`)
where ((`p`.`deletion_flag` = 0) and (`p`.`activation_flag` = 1) and (`v`.`deletion_flag` = 0) and (`v`.`activation_flag` = 1) and (`p`.`ID_Cliente_fatture` not in (82,303))) group by `p`.`anno_contabile` order by `p`.`anno_contabile`;

 
DROP VIEW IF EXISTS `VW_STAT_TOTALE_PROFORMA_ANNO_NO_COMP`;

CREATE VIEW `VW_STAT_TOTALE_PROFORMA_ANNO_NO_COMP` AS select 1 AS `ID`,(select `_system_decode`.`sd_description` from `_system_decode` where ((`_system_decode`.`sd_value` = `p`.`anno_contabile`) and (`_system_decode`.`sd_class` = 207))) AS `p1`,
cast(sum(`v`.`importo`) as decimal(9,2)) AS `p2`,
null AS `p3`,
`p`.`anno_contabile` AS `p4`,
(select sum(c.costo) from VW_STAT_COSTO_CLIENTI c where  c.anno_contabile = p1) AS `p5`,
NULL AS `p6`,curdate() AS `creation_date`,curdate() AS `last_modification_date`,'admin' AS `creation_user`,'admin' AS `last_modification_user`,0 AS `deletion_flag`,1 AS `activation_flag` from (`ProForma` `p` join `VoceFattura` `v` on((`v`.`ID_ProForma_voci_proforma` = `p`.`ID`))) where ((`p`.`deletion_flag` = 0) and (`p`.`activation_flag` = 1) and (`v`.`deletion_flag` = 0) and (`v`.`activation_flag` = 1) and (`p`.`ID_Cliente_proforma` not in (82,303))) group by `p`.`anno_contabile` order by `p`.`anno_contabile`;


DROP VIEW IF EXISTS `VW_STAT_RIEPILOGO_FATTURA_NO_COMP`;

CREATE  VIEW `VW_STAT_RIEPILOGO_FATTURA_NO_COMP` AS select 1 AS `ID`,(select `_system_decode`.`sd_description` from `_system_decode` where ((`_system_decode`.`sd_value` = `p`.`anno_contabile`) and (`_system_decode`.`sd_class` = 207))) AS `p1`,(select `c`.`cliente` from `Cliente` `c` where (`c`.`ID` = `p`.`ID_Cliente_fatture`)) AS `p2`,
null AS `p3`,
NULL AS `p4`,cast(sum(`VoceFattura`.`importo`) as decimal(9,2)) AS `p5`,((cast(sum(`VoceFattura`.`importo`) as signed) / cast(`t`.`p2` as signed)) * 100) AS `p6`,curdate() AS `creation_date`,curdate() AS `last_modification_date`,'admin' AS `creation_user`,'admin' AS `last_modification_user`,0 AS `deletion_flag`,1 AS `activation_flag` from ((`Fattura` `p` join `VoceFattura` on((`VoceFattura`.`ID_Fattura_voci_fattura` = `p`.`ID`))) join `VW_STAT_TOTALE_FATTURA_ANNO_NO_COMP` `t` on((`p`.`anno_contabile` = `t`.`p4`)  )) where ((`p`.`deletion_flag` = 0) and (`p`.`activation_flag` = 1) and (`p`.`ID_Cliente_fatture` not in (82,303))) group by `p`.`ID_Cliente_fatture`,`p`.`anno_contabile` order by (select `_system_decode`.`sd_description` from `_system_decode` where ((`_system_decode`.`sd_value` = `p`.`anno_contabile`) and (`_system_decode`.`sd_class` = 207))),(select `c`.`cliente` from `Cliente` `c` where (`c`.`ID` = `p`.`ID_Cliente_fatture`));

DROP VIEW IF EXISTS `VW_STAT_RIEPILOGO_PROFORMA_NO_COMP`;

CREATE  VIEW `VW_STAT_RIEPILOGO_PROFORMA_NO_COMP` AS select 1 AS `ID`,(select `_system_decode`.`sd_description` from `_system_decode` where ((`_system_decode`.`sd_value` = `p`.`anno_contabile`) and (`_system_decode`.`sd_class` = 207))) AS `p1`,
(select `c`.`cliente` from `Cliente` `c` where (`c`.`ID` = `p`.`ID_Cliente_proforma`)) AS `p2`,
null AS `p3`,
cast(avg((to_days(`p`.`data_pagamento`) - to_days(`p`.`data`))) as decimal(4,1)) AS `p4`,cast(sum(`VoceFattura`.`importo`) as decimal(9,2)) AS `p5`,
((cast(sum(`VoceFattura`.`importo`) as signed) / cast(`t`.`p2` as signed)) * 100) AS `p6`,
curdate() AS `creation_date`,curdate() AS `last_modification_date`,'admin' AS `creation_user`,'admin' AS `last_modification_user`,0 AS `deletion_flag`,1 AS `activation_flag` from ((`ProForma` `p` join `VoceFattura` on((`VoceFattura`.`ID_ProForma_voci_proforma` = `p`.`ID`))) join `VW_STAT_TOTALE_PROFORMA_ANNO_NO_COMP` `t` on((`p`.`anno_contabile` = `t`.`p4`)   )) where ((`p`.`deletion_flag` = 0) and (`p`.`activation_flag` = 1) and (`p`.`ID_Cliente_proforma` not in (82,303)))
group by `p`.`ID_Cliente_proforma`,`p`.`anno_contabile` order by (select `_system_decode`.`sd_description` from `_system_decode` where ((`_system_decode`.`sd_value` = `p`.`anno_contabile`) and (`_system_decode`.`sd_class` = 207))),(select `c`.`cliente` from `Cliente` `c` where (`c`.`ID` = `p`.`ID_Cliente_proforma`));



update  `Pratica` set stato = 3  WHERE anno_contabile <= 3;

INSERT INTO _system_instance_auth ( ID, ID__system_group_sia_group, sia_permissions, activation_flag, deletion_flag, creation_user, creation_date, last_modification_user, last_modification_date, sia_instance, sia_class ) VALUES( 86, "5", "111", "1", "0", "admin", "2015-02-03", "admin", "2015-02-03", "43", "_system_module" );
DROP TABLE IF EXISTS `VW_ProForma_Totali`;

DROP  VIEW IF EXISTS `VW_ProForma_Totali`;

CREATE VIEW `VW_ProForma_Totali` AS select `p`.`ID` AS `ID`,`p`.`numero` AS `numero`,`p`.`data` AS `data`,`p`.`data_pagamento` AS `data_pagamento`,`p`.`competenza` AS `competenza`,`p`.`ID_Cliente_proforma` AS `ID_Cliente_proforma`,`p`.`anno_contabile` AS `anno_contabile`,`p`.`spese_anticipate_desc` AS `spese_anticipate_desc`,
`p`.`stato_proforma` AS `stato_proforma`,`p`.`non_incassabile` AS `non_incassabile`,
round(`v2`.`importo`,2) AS `Rivalsa_Previdenziale`,round(`v3`.`importo_iva_0`,2) AS `Totale_Imponibile_0`,round(`v3`.`importo_iva_1`,2) AS `Totale_Imponibile_1`,round(`v3`.`importo_iva_2`,2) AS `Totale_Imponibile_2`,round(`v3`.`importo_iva_3`,2) AS `Totale_Imponibile_3`,round(`v3`.`importo_iva_4`,2) AS `Totale_Imponibile_4`,round(`v4`.`iva_0`,2) AS `Iva_0`,round(`v4`.`iva_1`,2) AS `Iva_1`,round(`v4`.`iva_2`,2) AS `Iva_2`,round(`v4`.`iva_3`,2) AS `Iva_3`,round(`v4`.`iva_4`,2) AS `Iva_4`,round(`p`.`spese_anticipate_fattura`,2) AS `Spese_Escluse_Da_Imponibile`,round(`v5`.`importo`,2) AS `Totale_Fattura`,round(`v6`.`importo`,2) AS `Ritenuta_Acconto`,round(`v7`.`importo`,2) AS `Totale_Da_Pagare`,`p`.`creation_date` AS `creation_date`,`p`.`creation_user` AS `creation_user`,`p`.`last_modification_date` AS `last_modification_date`,`p`.`last_modification_user` AS `last_modification_user`,`p`.`deletion_date` AS `deletion_date`,`p`.`deletion_user` AS `deletion_user`,`p`.`deletion_flag` AS `deletion_flag`,`p`.`activation_flag` AS `activation_flag` from ((((((`ProForma` `p` join `VW_ProForma_Rivalsa_Previdenziale` `v2` on((`v2`.`ID` = `p`.`ID`))) join `VW_ProForma_Totale_Imponibile` `v3` on((`v3`.`ID` = `p`.`ID`))) join `VW_ProForma_Iva` `v4` on((`v4`.`ID` = `p`.`ID`))) join `VW_ProForma_Totale_ProForma` `v5` on((`v5`.`ID` = `p`.`ID`))) join `VW_ProForma_Ritenuta_Acconto` `v6` on((`v6`.`ID` = `p`.`ID`))) join `VW_ProForma_Totale_Da_Pagare` `v7` on((`v7`.`ID` = `p`.`ID`)));



