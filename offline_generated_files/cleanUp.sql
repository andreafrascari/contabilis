TRUNCATE TABLE `Attivita` ;
TRUNCATE TABLE `Fattura` ;
TRUNCATE TABLE `LavoroSuAttivita`;
TRUNCATE TABLE `Pratica`;
TRUNCATE TABLE `rel_Scadenza_destinarario_personalizzato`;
TRUNCATE TABLE `StoricoInvii`;
TRUNCATE TABLE `SpesaAnticipata`;


TRUNCATE TABLE `CalendarioFatturazione` ;
update `Attivita` set `stato_attivita` = 0;
update `SpesaAnticipata` set `fatturata` = 0;
TRUNCATE TABLE `ProForma`;
TRUNCATE TABLE `VoceFattura`;


ALTER TABLE `VoceFattura` ADD `rif_pratica` INT NULL DEFAULT NULL ;
ALTER TABLE `Operatore` ADD `rivendita_oraria` DOUBLE NOT NULL ;
ALTER TABLE `Operatore` CHANGE `costo_orario` `costo_orario` DOUBLE NOT NULL ;
ALTER TABLE `SpesaAnticipata` ADD`fatturata` tinyint(1);
ALTER TABLE `Attivita` ADD`minuti_ultima_fattura` int(10);

drop view if exists VW_Pratica_Ultima_Sessione_Lavoro;
create view  VW_Pratica_Ultima_Sessione_Lavoro as
select p.*, (Select max(data) from LavoroSuAttivita l inner join Attivita a on l.ID_Attivita_sessioni_di_lavoro = a.ID where l.deletion_flag=0 and a.ID_Pratica_attivita = p.ID) as ultima_sessione from Pratica p;

drop view if exists VW_Pratica_Aperta_Oltre_2_Settimane;
create view  VW_Pratica_Aperta_Oltre_2_Settimane as
select p.* from Pratica p INNER JOIN VW_Pratica_Ultima_Sessione_Lavoro u on p.ID = u.ID 
where (ultima_sessione < DATE_SUB(curdate(),INTERVAL 2 WEEK));

INSERT INTO _system_menu_item (smi_href, smi_order, ID__system_menu_item_smi_children, smi_locale, smi_title, activation_flag, smi_alternative_text, deletion_flag, creation_user, creation_date, last_modification_user, last_modification_date ) VALUES("?q=object/list&p=Pratica&data_source=aperte-oltre-2-settimane&title=Pratiche aperte e inattive da oltre 2 settimane", "3", "41", "it", "Pratiche aperte e inattive da oltre 2 settimane", "1", "Pratiche aperte e inattive da oltre 2 settimane", "0", "admin", "2012-04-23", "admin", "2012-04-23" );

drop view if exists VW_Fattura_Max_Numero;
create view VW_Fattura_Max_Numero as
	SELECT MAX( numero ) AS numero,1 as ID, null as data,
	null as `creation_date`, null as `creation_user`, 0 as `deletion_flag`, 1 as `activation_flag`	
	FROM Fattura WHERE anno_contabile = (
			SELECT sd_value
			FROM _system_decode
			WHERE sd_description = Year( CURDATE( ) )
			AND sd_class =207 );


drop view if exists VW_ProForma_Max_Numero;
create view VW_ProForma_Max_Numero as
	SELECT MAX( numero ) AS numero, 1 as ID,null as data,
	null as `creation_date`, null as `creation_user`, 0 as `deletion_flag`, 1 as `activation_flag`	
	FROM ProForma WHERE anno_contabile = (
			SELECT sd_value
			FROM _system_decode
			WHERE sd_description = Year( CURDATE( ) )
			AND sd_class =207 );


UPDATE _system_menu_item SET smi_title = "Proforma" , smi_alternative_text = "Tutte le Proforma" , smi_locale = "it" , smi_association = NULL , smi_href = "?q=object/filter&p=ProForma_Totali" , activation_flag = "1" , smi_order = "5" , ID__system_menu_item_smi_children = "45" , last_modification_user = "admin" , last_modification_date = "2012-04-23"  WHERE ID = 50 ;

drop view if exists VW_Fattura_Somma_Voci_tmp;
create view VW_Fattura_Somma_Voci_tmp as
	SELECT p.ID,
		(select sum(v.importo) from VoceFattura v where v.deletion_flag=0 and v.iva=0 and v.ID_Fattura_voci_Fattura = p.ID) as importo_iva_0,
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
	(importo_iva_1 + (importo_iva_1 * p.riv_prev * (SELECT smp_value FROM `_system_module_parameter` WHERE smp_name = 'RIVALSA_PREVIDENZIALE' ) /100 )) as importo_iva_1,
	(importo_iva_2 + (importo_iva_2 * p.riv_prev * (SELECT smp_value FROM `_system_module_parameter` WHERE smp_name = 'RIVALSA_PREVIDENZIALE' ) /100 )) as importo_iva_2,
	(importo_iva_3 + (importo_iva_3 * p.riv_prev * (SELECT smp_value FROM `_system_module_parameter` WHERE smp_name = 'RIVALSA_PREVIDENZIALE' ) /100 )) as importo_iva_3,
	(importo_iva_4 + (importo_iva_4 * p.riv_prev * (SELECT smp_value FROM `_system_module_parameter` WHERE smp_name = 'RIVALSA_PREVIDENZIALE' ) /100 )) as importo_iva_4
FROM VW_Fattura_Somma_Voci v INNER JOIN `Fattura` p on v.ID = p.ID;

/* 4: calcolo iva per ogni aliquota, partendo dal totale imponibile (attenzione: il vaore dell'iva e' nelle sd_notes) */
drop view if exists VW_Fattura_Iva;
create view VW_Fattura_Iva as
SELECT p.ID,
	(importo_iva_0 * (SELECT sd_notes FROM `_system_decode` WHERE sd_class=232 and sd_value=0) / 100) as iva_0,
	(importo_iva_1 * (SELECT sd_notes FROM `_system_decode` WHERE sd_class=232 and sd_value=1) / 100) as iva_1,
	(importo_iva_2 * (SELECT sd_notes FROM `_system_decode` WHERE sd_class=232 and sd_value=2) / 100) as iva_2,
	(importo_iva_3 * (SELECT sd_notes FROM `_system_decode` WHERE sd_class=232 and sd_value=3) / 100) as iva_3,
	(importo_iva_4 * (SELECT sd_notes FROM `_system_decode` WHERE sd_class=232 and sd_value=4) / 100) as iva_4
FROM VW_Fattura_Totale_Imponibile p;



/* 5: totale fattura come somma, per ogni aliquota iva, di importo+iva. Sommato poi alle spese anticipate */
drop view if exists VW_Fattura_Totale_Fattura;
create view VW_Fattura_Totale_Fattura as 
	SELECT p.ID, 
	((importo_iva_0 + iva_0) + (importo_iva_1 + iva_1) + (importo_iva_2 + iva_2) + (importo_iva_3 + iva_3) + (importo_iva_4 + iva_4)  + p.spese_anticipate_fattura) as importo 
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
	SELECT  p.`ID`,   `numero`, `data`, `competenza`, ID_Cliente_fatture, `anno_contabile`, `spese_anticipate_desc`, 
		(select importo from VW_Fattura_Rivalsa_Previdenziale v2 where v2.ID = p.ID) as Rivalsa_Previdenziale,
		(select importo_iva_0 from VW_Fattura_Totale_Imponibile v3 where v3.ID = p.ID) as Totale_Imponibile_0,
		(select importo_iva_1 from VW_Fattura_Totale_Imponibile v3 where v3.ID = p.ID) as Totale_Imponibile_1,
		(select importo_iva_2 from VW_Fattura_Totale_Imponibile v3 where v3.ID = p.ID) as Totale_Imponibile_2,
		(select importo_iva_3 from VW_Fattura_Totale_Imponibile v3 where v3.ID = p.ID) as Totale_Imponibile_3,
		(select importo_iva_4 from VW_Fattura_Totale_Imponibile v3 where v3.ID = p.ID) as Totale_Imponibile_4,
		(select iva_0 from VW_Fattura_Iva v4 where v4.ID = p.ID) as Iva_0,
		(select iva_1 from VW_Fattura_Iva v4 where v4.ID = p.ID) as Iva_1,
		(select iva_2 from VW_Fattura_Iva v4 where v4.ID = p.ID) as Iva_2,
		(select iva_3 from VW_Fattura_Iva v4 where v4.ID = p.ID) as Iva_3,
		(select iva_4 from VW_Fattura_Iva v4 where v4.ID = p.ID) as Iva_4,
		 p.spese_anticipate_fattura as Spese_Escluse_Da_Imponibile,
		(select importo from VW_Fattura_Totale_Fattura v5 where v5.ID = p.ID) as Totale_Fattura,
		(select importo from VW_Fattura_Ritenuta_Acconto v6 where v6.ID = p.ID) as Ritenuta_Acconto,
		(select importo from VW_Fattura_Totale_Da_Pagare v7 where v7.ID = p.ID) as Totale_Da_Pagare,
		`creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_date`, `deletion_user`, `deletion_flag`, `activation_flag`
	from Fattura p;