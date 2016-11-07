Alter TABLE `Cliente` 
add  `legale_rappresentante_cognome` varchar(200),
add  `legale_rappresentante_nome` varchar(200),
add  `legale_rappresentante_cf` varchar(200),
add  `legale_rappresentante_residenza` varchar(200);


drop view if exists VW_Scadenze_OneOff_Today;

CREATE VIEW VW_Scadenze_OneOff_Today AS SELECT *
FROM Scadenza
WHERE 
DATE_ADD( `data` , INTERVAL - `preavviso_gg` DAY ) =CURDATE( ) and ricorrenza is null and deletion_flag=0 and  activation_flag=1;


drop view if exists VW_Scadenze_OneOff_3mAhead;

CREATE VIEW VW_Scadenze_OneOff_3mAhead AS SELECT *
FROM Scadenza
WHERE 
DATE_ADD( `data` , INTERVAL - 3 MONTH) =CURDATE( ) and ricorrenza is null and deletion_flag=0 and  activation_flag=1;



drop view if exists VW_Scadenze_Ricorrenti;

CREATE VIEW VW_Scadenze_Ricorrenti AS SELECT *
FROM Scadenza
WHERE 
ricorrenza is not null and (`fine_ricorrenza` is null or `fine_ricorrenza` >= CURDATE( )) and deletion_flag=0 and  activation_flag=1;


drop view if exists VW_ItemListinoXPraticheForThisYear;
create view VW_ItemListinoXPraticheForThisYear as
SELECT c.ID as ID_cliente, c.cliente, l.*  FROM `ItemListino` l inner join DatiFatturazione f on l.`ID_DatiFatturazione_listino`=f.ID 
inner join Cliente c on f.ID_Cliente_dati_fatturazione=c.ID and not exists
(select 1 from Pratica p where p.ID_Cliente_pratiche = c.ID and p.titolo = l.titolo and p.anno_contabile = (select sd_value from _system_decode d where d.sd_class = 207 and sd_description = (Select Year(CURDATE()))));


drop view if exists VW_ItemListinoXPraticheForNextYear;
create view VW_ItemListinoXPraticheForNextYear as
SELECT c.ID as ID_cliente,  c.cliente, l.*  FROM `ItemListino` l inner join DatiFatturazione f on l.`ID_DatiFatturazione_listino`=f.ID 
inner join Cliente c on f.ID_Cliente_dati_fatturazione=c.ID and not exists
(select 1 from Pratica p where p.ID_Cliente_pratiche = c.ID and p.titolo = l.titolo and p.anno_contabile = (select sd_value from _system_decode d where d.sd_class = 207 and sd_description = (Select Year(DATE_ADD(CURDATE( ) , INTERVAL +1 YEAR) ))));







/* Importi proforma */


/* 1: scorporo importi per ogni aliquota iva (aliquita_X => decodifica con sd_value = X) 
 * Se si introduce una nuova aliquoita iva in _system_decode, si deve aggiungere la "riga" anche a queste view.
 * ATTENZIONE: nella voce iva_0 possono confluire ive "passate" (>=20%) identificate con valore diretto nel campo iva in luogo dell'sd_value di _system_decode
 * */
drop view if exists VW_ProForma_Somma_Voci_tmp;
create view VW_ProForma_Somma_Voci_tmp as
	SELECT p.ID,
		(select sum(v.importo) from VoceFattura v where v.deletion_flag=0 and (v.iva=0 or v.iva>=20) and v.ID_ProForma_voci_proforma = p.ID) as importo_iva_0,
		(select max(v.iva) from VoceFattura v where v.deletion_flag=0  and v.ID_ProForma_voci_proforma = p.ID and v.iva>=20) as iva_0,
		(select sum(v.importo) from VoceFattura v where v.deletion_flag=0 and v.iva=1 and v.ID_ProForma_voci_proforma = p.ID) as importo_iva_1,
		(select sum(v.importo) from VoceFattura v where v.deletion_flag=0 and v.iva=2 and v.ID_ProForma_voci_ProForma = p.ID) as importo_iva_2,
		(select sum(v.importo) from VoceFattura v where v.deletion_flag=0 and v.iva=3 and v.ID_ProForma_voci_ProForma = p.ID) as importo_iva_3,
		(select sum(v.importo) from VoceFattura v where v.deletion_flag=0 and v.iva=4 and v.ID_ProForma_voci_ProForma = p.ID) as importo_iva_4
	FROM `ProForma` p;

/* 1a: sostituiamo i NULL con 0 per poter poi fare le somme */
drop view if exists VW_ProForma_Somma_Voci;
create view VW_ProForma_Somma_Voci as
	SELECT p.ID,
		COALESCE(importo_iva_0,0) as importo_iva_0,
		COALESCE(iva_0,0) as iva_0,
		COALESCE(importo_iva_1,0) as importo_iva_1,
		COALESCE(importo_iva_2,0) as importo_iva_2,
		COALESCE(importo_iva_3,0) as importo_iva_3,
		COALESCE(importo_iva_4,0) as importo_iva_4
	FROM VW_ProForma_Somma_Voci_tmp p;

/* 2: rivalsa previdenziale come somma degli importi * riv_prev (quindi puo' essere 0) */
drop view if exists VW_ProForma_Rivalsa_Previdenziale;
create view VW_ProForma_Rivalsa_Previdenziale as 
	SELECT p.ID, ((importo_iva_0 +  importo_iva_1+  importo_iva_2 + importo_iva_3 + importo_iva_4) * p.riv_prev * (SELECT smp_value FROM `_system_module_parameter` WHERE smp_name = 'RIVALSA_PREVIDENZIALE' ) /100 ) as importo FROM VW_ProForma_Somma_Voci v INNER JOIN `ProForma` p on v.ID = p.ID;

/* 3: calcolo imponibile (comprensivo di eventuale rivalsa previdenziale) per ogni aliquota iva */
drop view if exists VW_ProForma_Totale_Imponibile;
create view VW_ProForma_Totale_Imponibile as 
	SELECT p.ID,
	(importo_iva_0 + (importo_iva_0 * p.riv_prev * (SELECT smp_value FROM `_system_module_parameter` WHERE smp_name = 'RIVALSA_PREVIDENZIALE' ) /100 )) as importo_iva_0,
	iva_0,
	(importo_iva_1 + (importo_iva_1 * p.riv_prev * (SELECT smp_value FROM `_system_module_parameter` WHERE smp_name = 'RIVALSA_PREVIDENZIALE' ) /100 )) as importo_iva_1,
	(importo_iva_2 + (importo_iva_2 * p.riv_prev * (SELECT smp_value FROM `_system_module_parameter` WHERE smp_name = 'RIVALSA_PREVIDENZIALE' ) /100 )) as importo_iva_2,
	(importo_iva_3 + (importo_iva_3 * p.riv_prev * (SELECT smp_value FROM `_system_module_parameter` WHERE smp_name = 'RIVALSA_PREVIDENZIALE' ) /100 )) as importo_iva_3,
	(importo_iva_4 + (importo_iva_4 * p.riv_prev * (SELECT smp_value FROM `_system_module_parameter` WHERE smp_name = 'RIVALSA_PREVIDENZIALE' ) /100 )) as importo_iva_4
FROM VW_ProForma_Somma_Voci v INNER JOIN `ProForma` p on v.ID = p.ID;

/* 4: calcolo iva per ogni aliquota, partendo dal totale imponibile (attenzione: il vaore dell'iva e' nelle sd_notes) */
drop view if exists VW_ProForma_Iva;
create view VW_ProForma_Iva as
SELECT p.ID,
	((importo_iva_0 * (SELECT sd_notes FROM `_system_decode` WHERE sd_class=232 and sd_value=0) / 100) + (importo_iva_0 * iva_0 / 100)) as iva_0,
	(importo_iva_1 * (SELECT sd_notes FROM `_system_decode` WHERE sd_class=232 and sd_value=1) / 100) as iva_1,
	(importo_iva_2 * (SELECT sd_notes FROM `_system_decode` WHERE sd_class=232 and sd_value=2) / 100) as iva_2,
	(importo_iva_3 * (SELECT sd_notes FROM `_system_decode` WHERE sd_class=232 and sd_value=3) / 100) as iva_3,
	(importo_iva_4 * (SELECT sd_notes FROM `_system_decode` WHERE sd_class=232 and sd_value=4) / 100) as iva_4
FROM VW_ProForma_Totale_Imponibile p;



/* 5: totale ProForma come somma, per ogni aliquota iva, di importo+iva. Sommato poi alle spese anticipate */
drop view if exists VW_ProForma_Totale_ProForma;
create view VW_ProForma_Totale_ProForma as 
	SELECT p.ID, 
	((importo_iva_0 + i.iva_0) + (importo_iva_1 + iva_1) + (importo_iva_2 + iva_2) + (importo_iva_3 + iva_3) + (importo_iva_4 + iva_4)  + p.spese_anticipate_fattura) as importo 
	FROM VW_ProForma_Totale_Imponibile t INNER JOIN VW_ProForma_Iva i on t.ID = i.ID INNER JOIN ProForma p on p.ID = t.ID;

/* 6: ritenuta di acconto (eventulmente 0) cacolata sulla somma delle voci (senza la riv prev!) */
drop view if exists VW_ProForma_Ritenuta_Acconto;
create view VW_ProForma_Ritenuta_Acconto as 
	SELECT p.ID, 
	(p.ra * (SELECT smp_value FROM `_system_module_parameter` WHERE smp_name = 'RITENUTA_ACCONTO') / 100 
	* (importo_iva_0  + importo_iva_1 + importo_iva_2 + importo_iva_3 + importo_iva_4)) as importo 
	FROM VW_ProForma_Somma_Voci t INNER JOIN ProForma p on p.ID = t.ID;

/* 7: totale da pagare: totale - ritenuta */
drop view if exists VW_ProForma_Totale_Da_Pagare;
create view VW_ProForma_Totale_Da_Pagare as 
	SELECT t.ID, (t.importo - r.importo) as importo FROM VW_ProForma_Totale_ProForma t INNER JOIN VW_ProForma_Ritenuta_Acconto r on r.ID = t.ID;

/* 8: view per l'applicazione, con tutti i valori significativi */
drop view if exists VW_ProForma_Totali;
/*
create view VW_ProForma_Totali as
	SELECT  p.`ID`,   `numero`, `data`, `competenza`, ID_Cliente_proforma, `anno_contabile`, `spese_anticipate_desc`,`stato_proforma`, `non_incassabile`,
		(select importo from VW_ProForma_Rivalsa_Previdenziale v2 where v2.ID = p.ID) as Rivalsa_Previdenziale,
		(select importo_iva_0 from VW_ProForma_Totale_Imponibile v3 where v3.ID = p.ID) as Totale_Imponibile_0,
		(select importo_iva_1 from VW_ProForma_Totale_Imponibile v3 where v3.ID = p.ID) as Totale_Imponibile_1,
		(select importo_iva_2 from VW_ProForma_Totale_Imponibile v3 where v3.ID = p.ID) as Totale_Imponibile_2,
		(select importo_iva_3 from VW_ProForma_Totale_Imponibile v3 where v3.ID = p.ID) as Totale_Imponibile_3,
		(select importo_iva_4 from VW_ProForma_Totale_Imponibile v3 where v3.ID = p.ID) as Totale_Imponibile_4,
		(select iva_0 from VW_ProForma_Iva v4 where v4.ID = p.ID) as Iva_0,
		(select iva_1 from VW_ProForma_Iva v4 where v4.ID = p.ID) as Iva_1,
		(select iva_2 from VW_ProForma_Iva v4 where v4.ID = p.ID) as Iva_2,
		(select iva_3 from VW_ProForma_Iva v4 where v4.ID = p.ID) as Iva_3,
		(select iva_4 from VW_ProForma_Iva v4 where v4.ID = p.ID) as Iva_4,
		 p.spese_anticipate_fattura as Spese_Escluse_Da_Imponibile,
		(select importo from VW_ProForma_Totale_ProForma v5 where v5.ID = p.ID) as Totale_Fattura,
		(select importo from VW_ProForma_Ritenuta_Acconto v6 where v6.ID = p.ID) as Ritenuta_Acconto,
		(select importo from VW_ProForma_Totale_Da_Pagare v7 where v7.ID = p.ID) as Totale_Da_Pagare,
		`creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_date`, `deletion_user`, `deletion_flag`, `activation_flag`
	from ProForma p;
*/
create view VW_ProForma_Totali as
SELECT  p.`ID`,   `numero`, `data`, `competenza`, ID_Cliente_proforma, `anno_contabile`, `spese_anticipate_desc`,`stato_proforma`, `non_incassabile`,
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
from ProForma p inner join VW_ProForma_Rivalsa_Previdenziale v2 on v2.ID = p.ID
inner join VW_ProForma_Totale_Imponibile v3 on v3.ID = p.ID
inner join VW_ProForma_Iva v4 on v4.ID = p.ID
inner join  VW_ProForma_Totale_ProForma v5 on v5.ID = p.ID
inner join VW_ProForma_Ritenuta_Acconto v6 on v6.ID = p.ID
inner join VW_ProForma_Totale_Da_Pagare v7 on v7.ID = p.ID;


/* Importi Fattura */

/* 1: scorporo importi per ogni aliquota iva (aliquita_X => decodifica con sd_value = X) 
 * Se si introduce una nuova aliquoita iva in _system_decode, si deve aggiungere la "riga" anche a queste view.
 * */
drop view if exists VW_Fattura_Somma_Voci_tmp;
create view VW_Fattura_Somma_Voci_tmp as
	SELECT p.ID,
		(select sum(v.importo) from VoceFattura v where v.deletion_flag=0 and (v.iva=0 or v.iva>=20) and v.ID_Fattura_voci_Fattura = p.ID) as importo_iva_0,
		(select max(v.iva) from VoceFattura v where v.deletion_flag=0  and v.ID_Fattura_voci_fattura = p.ID and v.iva>=20) as iva_0,
		(select sum(v.importo) from VoceFattura v where v.deletion_flag=0 and v.iva=1 and v.ID_Fattura_voci_Fattura = p.ID) as importo_iva_1,
		(select sum(v.importo) from VoceFattura v where v.deletion_flag=0 and v.iva=2 and v.ID_Fattura_voci_Fattura = p.ID) as importo_iva_2,
		(select sum(v.importo) from VoceFattura v where v.deletion_flag=0 and v.iva=3 and v.ID_Fattura_voci_Fattura = p.ID) as importo_iva_3,
		(select sum(v.importo) from VoceFattura v where v.deletion_flag=0 and v.iva=4 and v.ID_Fattura_voci_Fattura = p.ID) as importo_iva_4
	FROM `Fattura` p;

/* 1a: sostituiamo i NULL con 0 per poter poi fare le somme */
drop view if exists VW_Fattura_Somma_Voci;
create view VW_Fattura_Somma_Voci as
	SELECT p.ID,
		COALESCE(importo_iva_0,0) as importo_iva_0,
		COALESCE(iva_0,0) as iva_0,
		COALESCE(importo_iva_1,0) as importo_iva_1,
		COALESCE(importo_iva_2,0) as importo_iva_2,
		COALESCE(importo_iva_3,0) as importo_iva_3,
		COALESCE(importo_iva_4,0) as importo_iva_4
	FROM VW_Fattura_Somma_Voci_tmp p;

/* 2: rivalsa previdenziale come somma degli importi * riv_prev (quindi puo' essere 0) */
drop view if exists VW_Fattura_Rivalsa_Previdenziale;
create view VW_Fattura_Rivalsa_Previdenziale as 
	SELECT p.ID, ((importo_iva_0 +  importo_iva_1+  importo_iva_2 + importo_iva_3 + importo_iva_4) * p.riv_prev * (SELECT smp_value FROM `_system_module_parameter` WHERE smp_name = 'RIVALSA_PREVIDENZIALE' ) /100 ) as importo FROM VW_Fattura_Somma_Voci v INNER JOIN `Fattura` p on v.ID = p.ID;

/* 3: calcolo imponibile (comprensivo di eventuale rivalsa previdenziale) per ogni aliquota iva */
drop view if exists VW_Fattura_Totale_Imponibile;
create view VW_Fattura_Totale_Imponibile as 
	SELECT p.ID,
	(importo_iva_0 + (importo_iva_0 * p.riv_prev * (SELECT smp_value FROM `_system_module_parameter` WHERE smp_name = 'RIVALSA_PREVIDENZIALE' ) /100 )) as importo_iva_0,
	iva_0,
	(importo_iva_1 + (importo_iva_1 * p.riv_prev * (SELECT smp_value FROM `_system_module_parameter` WHERE smp_name = 'RIVALSA_PREVIDENZIALE' ) /100 )) as importo_iva_1,
	(importo_iva_2 + (importo_iva_2 * p.riv_prev * (SELECT smp_value FROM `_system_module_parameter` WHERE smp_name = 'RIVALSA_PREVIDENZIALE' ) /100 )) as importo_iva_2,
	(importo_iva_3 + (importo_iva_3 * p.riv_prev * (SELECT smp_value FROM `_system_module_parameter` WHERE smp_name = 'RIVALSA_PREVIDENZIALE' ) /100 )) as importo_iva_3,
	(importo_iva_4 + (importo_iva_4 * p.riv_prev * (SELECT smp_value FROM `_system_module_parameter` WHERE smp_name = 'RIVALSA_PREVIDENZIALE' ) /100 )) as importo_iva_4
FROM VW_Fattura_Somma_Voci v INNER JOIN `Fattura` p on v.ID = p.ID;

/* 4: calcolo iva per ogni aliquota, partendo dal totale imponibile (attenzione: il vaore dell'iva e' nelle sd_notes) */
drop view if exists VW_Fattura_Iva;
create view VW_Fattura_Iva as
SELECT p.ID,
((importo_iva_0 * (SELECT sd_notes FROM `_system_decode` WHERE sd_class=232 and sd_value=0) / 100) + (importo_iva_0 * iva_0 / 100)) as iva_0,
	(importo_iva_1 * (SELECT sd_notes FROM `_system_decode` WHERE sd_class=232 and sd_value=1) / 100) as iva_1,
	(importo_iva_2 * (SELECT sd_notes FROM `_system_decode` WHERE sd_class=232 and sd_value=2) / 100) as iva_2,
	(importo_iva_3 * (SELECT sd_notes FROM `_system_decode` WHERE sd_class=232 and sd_value=3) / 100) as iva_3,
	(importo_iva_4 * (SELECT sd_notes FROM `_system_decode` WHERE sd_class=232 and sd_value=4) / 100) as iva_4
FROM VW_Fattura_Totale_Imponibile p;



/* 5: totale fattura come somma, per ogni aliquota iva, di importo+iva. Sommato poi alle spese anticipate */
drop view if exists VW_Fattura_Totale_Fattura;
create view VW_Fattura_Totale_Fattura as 
	SELECT p.ID, 
	((importo_iva_0 + i.iva_0) + (importo_iva_1 + iva_1) + (importo_iva_2 + iva_2) + (importo_iva_3 + iva_3) + (importo_iva_4 + iva_4)  + p.spese_anticipate_fattura) as importo 
	FROM VW_Fattura_Totale_Imponibile t INNER JOIN VW_Fattura_Iva i on t.ID = i.ID INNER JOIN Fattura p on p.ID = t.ID;

/* 6: ritenuta di acconto (eventulmente 0) cacolata sulla somma delle voci (senza la riv prev!) */
drop view if exists VW_Fattura_Ritenuta_Acconto;
create view VW_Fattura_Ritenuta_Acconto as 
	SELECT p.ID, 
	(p.ra * (SELECT smp_value FROM `_system_module_parameter` WHERE smp_name = 'RITENUTA_ACCONTO') / 100 
	* (importo_iva_0  + importo_iva_1 + importo_iva_2 + importo_iva_3 + importo_iva_4)) as importo 
	FROM VW_Fattura_Somma_Voci t INNER JOIN Fattura p on p.ID = t.ID;

/* 7: totale da pagare: totale - ritenuta */
drop view if exists VW_Fattura_Totale_Da_Pagare;
create view VW_Fattura_Totale_Da_Pagare as 
	SELECT t.ID, (t.importo - r.importo) as importo FROM VW_Fattura_Totale_Fattura t INNER JOIN VW_Fattura_Ritenuta_Acconto r on r.ID = t.ID;

/* 8: view per l'applicazione, con tutti i valori significativi */
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










/* NEXT NUMERO Fattura/Pratica */
/* versione con univocita sull'anno: */

drop view if exists VW_Fattura_Max_Numero;
create view VW_Fattura_Max_Numero as
SELECT MAX( numero ) AS numero, 1 AS ID, anno_contabile,
(SELECT sd_description FROM _system_decode WHERE sd_value = anno_contabile AND sd_class =207) AS note, 
NULL AS data, NULL AS  `creation_date` , NULL AS  `creation_user` , 0 AS  `deletion_flag` , 1 AS  `activation_flag` 
FROM Fattura
WHERE deletion_flag =0
GROUP BY anno_contabile;

drop view if exists VW_Fattura_Max_Numero_Studio;
create view VW_Fattura_Max_Numero_Studio as
SELECT MAX( numero ) AS numero, 1 AS ID, anno_contabile,
(SELECT sd_description FROM _system_decode WHERE sd_value = anno_contabile AND sd_class =207) AS note, 
NULL AS data, NULL AS  `creation_date` , NULL AS  `creation_user` , 0 AS  `deletion_flag` , 1 AS  `activation_flag` 
	FROM Fattura WHERE deletion_flag= 0  and competenza = 1
GROUP BY anno_contabile;

drop view if exists VW_Fattura_Max_Numero_Contabilis;
create view VW_Fattura_Max_Numero_Contabilis as
SELECT MAX( numero ) AS numero, 1 AS ID, anno_contabile,
(SELECT sd_description FROM _system_decode WHERE sd_value = anno_contabile AND sd_class =207) AS note, 
NULL AS data, NULL AS  `creation_date` , NULL AS  `creation_user` , 0 AS  `deletion_flag` , 1 AS  `activation_flag` 
	FROM Fattura WHERE deletion_flag= 0  and competenza = 0
GROUP BY anno_contabile;
			
			
drop view if exists VW_ProForma_Max_Numero;
create view VW_ProForma_Max_Numero as
SELECT MAX( numero ) AS numero, 1 AS ID, anno_contabile,
(SELECT sd_description FROM _system_decode WHERE sd_value = anno_contabile AND sd_class =207) AS note, 
NULL AS data, NULL AS  `creation_date` , NULL AS  `creation_user` , 0 AS  `deletion_flag` , 1 AS  `activation_flag` 
FROM ProForma WHERE deletion_flag= 0
GROUP BY anno_contabile;

drop view if exists VW_ProForma_Max_Numero_Studio;
create view VW_ProForma_Max_Numero_Studio as
SELECT MAX( numero ) AS numero, 1 AS ID, anno_contabile,
(SELECT sd_description FROM _system_decode WHERE sd_value = anno_contabile AND sd_class =207) AS note, 
NULL AS data, NULL AS  `creation_date` , NULL AS  `creation_user` , 0 AS  `deletion_flag` , 1 AS  `activation_flag` 	
	FROM ProForma WHERE deletion_flag= 0 and competenza = 1
GROUP BY anno_contabile;

drop view if exists VW_ProForma_Max_Numero_Contabilis;
create view VW_ProForma_Max_Numero_Contabilis as
SELECT MAX( numero ) AS numero, 1 AS ID, anno_contabile,
(SELECT sd_description FROM _system_decode WHERE sd_value = anno_contabile AND sd_class =207) AS note, 
NULL AS data, NULL AS  `creation_date` , NULL AS  `creation_user` , 0 AS  `deletion_flag` , 1 AS  `activation_flag` 
FROM ProForma WHERE deletion_flag= 0 and competenza = 0
GROUP BY anno_contabile;

			
/* versione con univocita assoluta (escluso il 2011 (== 1) */
			
			/*
drop view if exists VW_ProForma_Max_Numero;
create view VW_ProForma_Max_Numero as
	SELECT MAX( numero ) AS numero, 1 as ID,1 as anno_contabile, null as data,
	null as `creation_date`, null as `creation_user`, 0 as `deletion_flag`, 1 as `activation_flag`	
	FROM ProForma WHERE deletion_flag= 0 and anno_contabile <> 1;

drop view if exists VW_ProForma_Max_Numero_Studio;
create view VW_ProForma_Max_Numero_Studio as
	SELECT MAX( numero ) AS numero, 1 as ID,1 as anno_contabile, null as data,
	null as `creation_date`, null as `creation_user`, 0 as `deletion_flag`, 1 as `activation_flag`	
	FROM ProForma WHERE deletion_flag= 0 and competenza = 1 and  anno_contabile <> 1;

drop view if exists VW_ProForma_Max_Numero_Contabilis;
create view VW_ProForma_Max_Numero_Contabilis as
	SELECT MAX( numero ) AS numero, 1 as ID,1 as anno_contabile, null as data,
	null as `creation_date`, null as `creation_user`, 0 as `deletion_flag`, 1 as `activation_flag`	
	FROM ProForma WHERE deletion_flag= 0 and competenza = 0 and anno_contabile <> 1;
	
	
drop view if exists VW_Fattura_Max_Numero;
create view VW_Fattura_Max_Numero as
	SELECT MAX( numero ) AS numero,1 as ID, 1 as anno_contabile, null as data,
	null as `creation_date`, null as `creation_user`, 0 as `deletion_flag`, 1 as `activation_flag`	
	FROM Fattura WHERE deletion_flag= 0 and anno_contabile <> 1;

drop view if exists VW_Fattura_Max_Numero_Studio;
create view VW_Fattura_Max_Numero_Studio as
	SELECT MAX( numero ) AS numero,1 as ID, 1 as anno_contabile, null as data,
	null as `creation_date`, null as `creation_user`, 0 as `deletion_flag`, 1 as `activation_flag`	
	FROM Fattura WHERE deletion_flag= 0  and competenza = 1 and anno_contabile <> 1;

drop view if exists VW_Fattura_Max_Numero_Contabilis;
create view VW_Fattura_Max_Numero_Contabilis as
	SELECT MAX( numero ) AS numero,1 as ID, 1 as anno_contabile, null as data,
	null as `creation_date`, null as `creation_user`, 0 as `deletion_flag`, 1 as `activation_flag`	
	FROM Fattura WHERE deletion_flag= 0  and competenza = 0 and anno_contabile <> 1;
	
*/
			
drop view if exists VW_Workflow_Doc_Iniziali;
create view  VW_Workflow_Doc_Iniziali as
SELECT * FROM `StoriaDocumento` WHERE `ID_Documento_storia_documento` in (select ID from Documento 
where `ID_DescrizioneDocumento_inverse_of_tipologia` =52);


drop view if exists VW_Workflow_Documenti_AUI;
create view  VW_Workflow_Documenti_AUI as
SELECT * FROM `StoriaDocumento` WHERE `ID_Documento_storia_documento` in (select ID from Documento 
where `free_tag` ='AUI');



drop view if exists VW_Proforma_Sollecito_1;
create view  VW_Proforma_Sollecito_1 as
SELECT * FROM `ProForma` p WHERE (data_pagamento is null or data_pagamento ='') and stato_proforma = 2   and non_incassabile = 0
/* non esitono solleciti */
and not exists (Select 1 from SollecitoPagamento s where s.ID_ProForma_solleciti_pagamento = p.ID and s.deletion_flag=0) 
and (DATEDIFF(CURDATE() , DATE_ADD(p.data, INTERVAL 45 DAY)) > 0);

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
/* esiste il SECONDO sollecito */
and exists (Select 1 from SollecitoPagamento s where s.ID_ProForma_solleciti_pagamento = p.ID and s.numero_sollecito=1 and s.deletion_flag=0)
/* non esite IL TERZO */
and not exists (Select 1 from SollecitoPagamento s where s.ID_ProForma_solleciti_pagamento = p.ID and s.numero_sollecito=2 and s.deletion_flag=0)
/* non esite IL NON SOLLECITARE */
and not exists (Select 1 from SollecitoPagamento s where s.ID_ProForma_solleciti_pagamento = p.ID and s.numero_sollecito=3 and s.deletion_flag=0)
and (DATEDIFF(CURDATE() , DATE_ADD(p.data, INTERVAL 90 DAY)) > 0);

drop view if exists VW_Pratica_Ultima_Sessione_Lavoro;
create view  VW_Pratica_Ultima_Sessione_Lavoro as
select p.*, (Select max(data) from LavoroSuAttivita l inner join Attivita a on l.ID_Attivita_sessioni_di_lavoro = a.ID where l.deletion_flag=0 and a.ID_Pratica_attivita = p.ID) as ultima_sessione from Pratica p;

drop view if exists VW_Pratica_Aperta_Oltre_2_Settimane;
create view  VW_Pratica_Aperta_Oltre_2_Settimane as
select p.* from Pratica p INNER JOIN VW_Pratica_Ultima_Sessione_Lavoro u on p.ID = u.ID 
where (ultima_sessione < DATE_SUB(curdate(),INTERVAL 2 WEEK));


drop view if exists VW_ClienteCandidato_in_attesa;
create  view  VW_ClienteCandidato_in_attesa as
SELECT * from ClienteCandidato aliasClienteCandidato  WHERE not  EXISTS (SELECT 1 FROM Cliente aliasCliente WHERE  aliasCliente.ID_ClienteCandidato_diventa_cliente = aliasClienteCandidato.ID)  and  deletion_flag = 0  ORDER BY creation_date;

drop view if exists VW_LavoroSuAttivita_ultime_sessioni;
create view VW_LavoroSuAttivita_ultime_sessioni as
SELECT l.ID , l.data 	, l.durata_minuti 	, l.diario 	, l.allegato 	,l.creation_date 	,l.creation_user 	, l.last_modification_date 	, l.last_modification_user 	, l.deletion_date 	, l.deletion_user 	, l.deletion_flag 	, l.activation_flag 	, l.ID_Attivita_sessioni_di_lavoro 
FROM LavoroSuAttivita l inner join Attivita a on l.ID_Attivita_sessioni_di_lavoro = a.ID ORDER BY  data desc limit 0,50;


drop view if exists VW_CalendarioFatturazione_Preview_Generazione;
create view VW_CalendarioFatturazione_Preview_Generazione as
select * FROM CalendarioFatturazione WHERE  ID_ProForma_entry_calendario is null and  data <= curdate() and  deletion_flag = 0;


/* gen 13  */
drop view if exists VW_Documenti_Cliente_Documenti;
create view VW_Documenti_Cliente_Documenti as
select d.* FROM Documento d INNER JOIN DescrizioneDocumento t on d.ID_DescrizioneDocumento_inverse_of_tipologia = t.ID WHERE  
t.tipologia_documento like '%UNICO%' or t.tipologia_documento like '%BILANCI%' or t.tipologia_documento like '%IVA%' or t.tipologia_documento like '%730%' or t.tipologia_documento like '%770%' or t.tipologia_documento like '%IRAP%';

drop view if exists VW_Documenti_Cliente_Corrispondenza;
create view VW_Documenti_Cliente_Corrispondenza as
select d.* FROM Documento d INNER JOIN DescrizioneDocumento t on d.ID_DescrizioneDocumento_inverse_of_tipologia = t.ID WHERE  
t.tipologia_documento not like '%UNICO%' and t.tipologia_documento not like '%BILANCI%' and t.tipologia_documento not like '%IVA%' and t.tipologia_documento not like '%730%' and t.tipologia_documento not like '%770%' and t.tipologia_documento not  like '%IRAP%';

INSERT INTO `_system_class_auth` (`ID`, `sca_class`, `sca_permissions`, `ID__system_group_group_class_auth`, `creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_date`, `deletion_user`, `deletion_flag`, `activation_flag`) VALUES
(25, 'NotificaScadenza', '222', 3, '2013-01-09', 'admin', '2013-01-09', 'admin', NULL, NULL, 0, 1);


drop view if exists VW_Cliente_Tipo;
create view  VW_Cliente_Tipo as 
select  convert (group_concat(sd_value) , char(5)) as tipi,group_concat(sd_description) as tipi_descr, r.ID_Cliente as ID from _system_decode sd inner join rel_Cliente_tipo_cliente r on r.ID_tipo_cliente = sd.sd_value and sd_class = 200 group by  r.ID_Cliente; 

drop view if exists VW_SuperCliente;
create view  VW_SuperCliente as 
SELECT c.*, 
 
(select  tipi_descr from VW_Cliente_Tipo wt where wt.ID = c.ID) as `tipo_cliente`,
d.tipo_cliente as  `tipo_cliente_machine`,
d.`codice_ateco`, d.`codice_ateco2`, d.`codice_ateco3`, d.`teniamo_noi`, d.`regime_contabilita`, d.`regime_iva`, d.`iscrizione_registro_imprese`, d.`liquidazione_iva`, d.`esercizio_solare`, d.`esercizio_da`, d.`esercizio_a`, d.`obbligo_iscrizione_inail`, d.`pat_numero`, d.`incarico_a`, d.`provincia_registro_imprese`, d.`sezione_ordinaria`, d.`iscritto_rea`, d.`sezioni_speciali`, d.`unita_locali`,
crm.`sostituto_imposta`, crm.`immobili`, crm.`ici`, crm.`note_ici`, crm.`cassetto_fiscale`, crm.`f24_cumulativo`, crm.`antireciclaggio`, crm.`privacy`, crm.`privacy_acquisita`, crm.`conservazione_sostitutiva`, crm.`conservazione_sostitutiva_delega_studio`, crm.`verifica_validita_firma_digitale`, crm.`conservazione_sostitutiva_note`, crm.`conservazione_libro_giornale`, crm.`conservazione_libro_giornale_data_versamento_diritti`, crm.`data_invio_impronta_digitale`,
rp.iscrizione,	rp.regime
from  Cliente c   left join DatiContabili d on c.ID = d.`ID_Cliente_dati_contabili`
left join DatiCRM crm on c.ID = crm.`ID_Cliente_dati_crm`
left join RegimePrevidenziale rp on d.ID = rp.`ID_DatiContabili_regimi_previdenziali`
where cessata_assistenza_il is   null and c.deletion_flag = 0  
order by cliente;


update `DatiContabili` set `esercizio_da` =   concat(SUBSTRING(esercizio_da ,9,2),'/',SUBSTRING(esercizio_da ,6,2));
update `DatiContabili` set `esercizio_a` =   concat(SUBSTRING(esercizio_a ,9,2),'/',SUBSTRING(esercizio_a ,6,2));


drop view if exists VW_Cliente_Per_Cliente_al;
create view  VW_Cliente_Per_Cliente_al as 
SELECT `ID`, `ID__system_user_inverse_of_account`, `allegato`, `allegato_1`, `cellulare`, `cliente`, `codice_cliente`, `codice_fiscale`, `nickname`, `email`, `email2`, `email3`, `fax`, `indirizzo`, `recapito_indirizzo`, `comune`, `recapito_cap`, `recapito_comune`, `cap`, `stato_cliente`, ifnull(`cessata_assistenza_il`,cast('2020/12/31' as date)) as `cessata_assistenza_il`, `cliente_dal`, `partita_iva`, `note`, `telefono`, `creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_date`, `deletion_user`, `deletion_flag`, `activation_flag`, `tipo_sollecito`, `legale_rappresentante_cognome`, `legale_rappresentante_nome`, `legale_rappresentante_cf`, `legale_rappresentante_residenza`, `ID_Operatore_responsabile_di`, `ID_ClienteCandidato_diventa_cliente`, `ID_OLD_HG`   FROM `Cliente`;