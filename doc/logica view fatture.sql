procediamo backwards, e si parte da qui (riepilogo che mette tutto insieme):


CREATE  VIEW `VW_Fattura_Totali` AS 
select `p`.`ID` AS `ID`,`p`.`numero` AS `numero`,`p`.`data` AS `data`,`p`.`data_pagamento` AS `data_pagamento`,`p`.`competenza` AS `competenza`,`p`.`ID_Cliente_fatture` AS `ID_Cliente_fatture`,`p`.`anno_contabile` AS `anno_contabile`,`p`.`spese_anticipate_desc` AS `spese_anticipate_desc`,
round(`v2`.`importo`,2) AS `Rivalsa_Previdenziale`,
round(`v3`.`importo_iva_0`,2) AS `Totale_Imponibile_0`,
round(`v3`.`importo_iva_1`,2) AS `Totale_Imponibile_1`,
round(`v3`.`importo_iva_2`,2) AS `Totale_Imponibile_2`,
round(`v3`.`importo_iva_3`,2) AS `Totale_Imponibile_3`,
round(`v3`.`importo_iva_4`,2) AS `Totale_Imponibile_4`,
round(`v4`.`iva_0`,2) AS `Iva_0`,
round(`v4`.`iva_1`,2) AS `Iva_1`,
round(`v4`.`iva_2`,2) AS `Iva_2`,
round(`v4`.`iva_3`,2) AS `Iva_3`,
round(`v4`.`iva_4`,2) AS `Iva_4`,
round(`p`.`spese_anticipate_fattura`,2) AS `Spese_Escluse_Da_Imponibile`,
round(`v5`.`importo`,2) AS `Totale_Fattura`,
round(`v6`.`importo`,2) AS `Ritenuta_Acconto`,
round(`v7`.`importo`,2) AS `Totale_Da_Pagare`,
`p`.`creation_date` AS `creation_date`,`p`.`creation_user` AS `creation_user`,`p`.`last_modification_date` AS `last_modification_date`,`p`.`last_modification_user` AS `last_modification_user`,`p`.`deletion_date` AS `deletion_date`,`p`.`deletion_user` AS `deletion_user`,`p`.`deletion_flag` AS `deletion_flag`,`p`.`activation_flag` AS `activation_flag` 
from ((((((`Fattura` `p` join `VW_Fattura_Rivalsa_Previdenziale` `v2` on((`v2`.`ID` = `p`.`ID`))) 
join `VW_Fattura_Totale_Imponibile` `v3` on((`v3`.`ID` = `p`.`ID`))) 
join `VW_Fattura_Iva` `v4` on((`v4`.`ID` = `p`.`ID`))) 
join `VW_Fattura_Totale_Fattura` `v5` on((`v5`.`ID` = `p`.`ID`))) 
join `VW_Fattura_Ritenuta_Acconto` `v6` on((`v6`.`ID` = `p`.`ID`))) 
join `VW_Fattura_Totale_Da_Pagare` `v7` on((`v7`.`ID` = `p`.`ID`)));


1) Seguiamo la direzione (DELICATA!) calcoli iva ... VW_Fattura_Iva
Il valore e' indicato nel campo notes della decodifica 232 ....  eccetto per iva_4 - quella normale - che cambia nel tempo.
Sono le view stesse a dedurre il valore corretto dalla voce fattura, PRESUPPONENDO SIA SEMPRE QUELLO CON VALUE DECODIFICA PIU' ALTO
(per questo, la VW_Fattura_Somma_Voci_tmp torna un select max(`v`.`iva`) as iva_0)
N.B. se questo foose mappato in un iva_4 .... sarebbe tutto piu' chiaro!!!!!


CREATE VIEW `VW_Fattura_Iva` AS 
select `p`.`ID` AS `ID`,
((`p`.`importo_iva_0` * (select `_system_decode`.`sd_notes` AS `sd_notes` from `_system_decode` where ((`_system_decode`.`sd_class` = 232) and (`_system_decode`.`sd_value` = 0)))) / 100) AS `iva_0`,
((`p`.`importo_iva_1` * (select `_system_decode`.`sd_notes` AS `sd_notes` from `_system_decode` where ((`_system_decode`.`sd_class` = 232) and (`_system_decode`.`sd_value` = 1)))) / 100) AS `iva_1`,
((`p`.`importo_iva_2` * (select `_system_decode`.`sd_notes` AS `sd_notes` from `_system_decode` where ((`_system_decode`.`sd_class` = 232) and (`_system_decode`.`sd_value` = 2)))) / 100) AS `iva_2`,
((`p`.`importo_iva_3` * (select `_system_decode`.`sd_notes` AS `sd_notes` from `_system_decode` where ((`_system_decode`.`sd_class` = 232) and (`_system_decode`.`sd_value` = 3)))) / 100) AS `iva_3`,
((`p`.`importo_iva_4` * (select `_system_decode`.`sd_notes` AS `sd_notes` from `_system_decode` where ((`_system_decode`.`sd_class` = 232) and (`_system_decode`.`sd_value` = `p`.`iva_0`)))) / 100) AS `iva_4` 
from `VW_Fattura_Totale_Imponibile` `p`;


CREATE VIEW `VW_Fattura_Somma_Voci` AS 
select `p`.`ID` AS `ID`,
coalesce(`p`.`importo_iva_0`,0) AS `importo_iva_0`,
coalesce(`p`.`iva_0`,0) AS `iva_0`,
coalesce(`p`.`importo_iva_1`,0) AS `importo_iva_1`,
coalesce(`p`.`importo_iva_2`,0) AS `importo_iva_2`,
coalesce(`p`.`importo_iva_3`,0) AS `importo_iva_3`,
coalesce(`p`.`importo_iva_4`,0) AS `importo_iva_4` 
from `VW_Fattura_Somma_Voci_tmp` `p`;

CREATE VIEW `VW_Fattura_Somma_Voci_tmp` AS 
select `p`.`ID` AS `ID`,
(select sum(`v`.`importo`) from `VoceFattura` `v` where ((`v`.`deletion_flag` = 0) and (`v`.`iva` = 0) and (`v`.`ID_Fattura_voci_fattura` = `p`.`ID`))) AS `importo_iva_0`,
(select max(`v`.`iva`) AS `max(v.iva)` from `VoceFattura` `v` where ((`v`.`deletion_flag` = 0) and (`v`.`ID_Fattura_voci_fattura` = `p`.`ID`))) AS `iva_0`,
(select sum(`v`.`importo`) AS `sum(v.importo)` from `VoceFattura` `v` where ((`v`.`deletion_flag` = 0) and (`v`.`iva` = 1) and (`v`.`ID_Fattura_voci_fattura` = `p`.`ID`))) AS `importo_iva_1`,
(select sum(`v`.`importo`) AS `sum(v.importo)` from `VoceFattura` `v` where ((`v`.`deletion_flag` = 0) and (`v`.`iva` = 2) and (`v`.`ID_Fattura_voci_fattura` = `p`.`ID`))) AS `importo_iva_2`,
(select sum(`v`.`importo`) AS `sum(v.importo)` from `VoceFattura` `v` where ((`v`.`deletion_flag` = 0) and (`v`.`iva` = 3) and (`v`.`ID_Fattura_voci_fattura` = `p`.`ID`))) AS `importo_iva_3`,
(select sum(`v`.`importo`) AS `sum(v.importo)` from `VoceFattura` `v` where ((`v`.`deletion_flag` = 0) and (`v`.`iva` >= 4) and (`v`.`ID_Fattura_voci_fattura` = `p`.`ID`))) AS `importo_iva_4` from `Fattura` `p`;


2) VW_Fattura_Totale_Imponibile

si ricapitolano i totali iva esclusa ma con rivalsa previdenziale

CREATE VIEW `VW_Fattura_Totale_Imponibile` AS 
select `p`.`ID` AS `ID`,(`v`.`importo_iva_0` + (((`v`.`importo_iva_0` * `p`.`riv_prev`) * (select `_system_module_parameter`.`smp_value` AS `smp_value` from `_system_module_parameter` where (`_system_module_parameter`.`smp_name` = 'RIVALSA_PREVIDENZIALE'))) / 100)) AS `importo_iva_0`,
`v`.`iva_0` AS `iva_0`,
(`v`.`importo_iva_1` + (((`v`.`importo_iva_1` * `p`.`riv_prev`) * (select `_system_module_parameter`.`smp_value` AS `smp_value` from `_system_module_parameter` where (`_system_module_parameter`.`smp_name` = 'RIVALSA_PREVIDENZIALE'))) / 100)) AS `importo_iva_1`,
(`v`.`importo_iva_2` + (((`v`.`importo_iva_2` * `p`.`riv_prev`) * (select `_system_module_parameter`.`smp_value` AS `smp_value` from `_system_module_parameter` where (`_system_module_parameter`.`smp_name` = 'RIVALSA_PREVIDENZIALE'))) / 100)) AS `importo_iva_2`,
(`v`.`importo_iva_3` + (((`v`.`importo_iva_3` * `p`.`riv_prev`) * (select `_system_module_parameter`.`smp_value` AS `smp_value` from `_system_module_parameter` where (`_system_module_parameter`.`smp_name` = 'RIVALSA_PREVIDENZIALE'))) / 100)) AS `importo_iva_3`,
(`v`.`importo_iva_4` + (((`v`.`importo_iva_4` * `p`.`riv_prev`) * (select `_system_module_parameter`.`smp_value` AS `smp_value` from `_system_module_parameter` where (`_system_module_parameter`.`smp_name` = 'RIVALSA_PREVIDENZIALE'))) / 100)) AS `importo_iva_4` 
from (`VW_Fattura_Somma_Voci` `v` join `Fattura` `p` on((`v`.`ID` = `p`.`ID`)));











