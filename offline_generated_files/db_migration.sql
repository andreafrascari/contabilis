UPDATE `_system_menu_item` SET `smi_href` = '?q=object/filter&p=ProForma' WHERE `_system_menu_item`.`ID` =50 LIMIT 1 ;
UPDATE `_system_menu_item` SET `smi_href` = '?q=object/filter&p=Fattura' WHERE `_system_menu_item`.`ID` =53 LIMIT 1 ;

INSERT INTO `_system_menu_item` (
		`ID` ,
		`smi_title` ,
		`smi_order` ,
		`smi_locale` ,
		`smi_association` ,
		`smi_href` ,
		`smi_alternative_text` ,
		`ID__system_menu_item_smi_children` ,
		`creation_date` ,
		`creation_user` ,
		`last_modification_date` ,
		`last_modification_user` ,
		`deletion_date` ,
		`deletion_user` ,
		`deletion_flag` ,
		`activation_flag`
		)
		VALUES (
		NULL , 'Storico', '9', 'it', NULL , '?q=object/filter&p=ProForma&data_source=storico&title=Storico Proforma', 'Storico Proforma', '50', '2012-03-19', 'admin', '2012-03-19', 'admin', NULL , NULL , '0', '1'
		);



CREATE TABLE IF NOT EXISTS `ProForma_Storico` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `numero` int(11) NOT NULL,
  `data` date NOT NULL,
  `competenza` int(10) DEFAULT NULL,
  `non_incassabile` tinyint(1) DEFAULT NULL,
  `spese_anticipate_fattura` double NOT NULL DEFAULT '0',
  `spese_anticipate_desc` longtext COLLATE latin1_general_ci,
  `riv_prev` int(1) NOT NULL DEFAULT '0',
  `ra` int(1) NOT NULL DEFAULT '0',
  `data_pagamento` date DEFAULT NULL,
  `note` longtext COLLATE latin1_general_ci,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) COLLATE latin1_general_ci NOT NULL,
  `last_modification_date` date DEFAULT NULL,
  `last_modification_user` varchar(100) COLLATE latin1_general_ci DEFAULT NULL,
  `deletion_date` date DEFAULT NULL,
  `deletion_user` varchar(100) COLLATE latin1_general_ci DEFAULT NULL,
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
  `ID_Cliente_proforma` int(10) NOT NULL,
  `stato_proforma` int(10) NOT NULL,
  `anno_contabile` int(10) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID_Cliente_proforma` (`ID_Cliente_proforma`),
  KEY `creation_user` (`creation_user`),
  KEY `creation_date` (`creation_date`),
  KEY `activation_flag` (`activation_flag`),
  KEY `deletion_flag` (`deletion_flag`),
  KEY `data` (`data`),
  KEY `anno_contabile` (`anno_contabile`),
  KEY `riv_prev` (`riv_prev`),
  KEY `ra` (`ra`),
  KEY `spese_anticipate_fattura` (`spese_anticipate_fattura`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=1 ;


CREATE TABLE IF NOT EXISTS `Fattura_Storico` (
		  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
		  `ID_ProForma_fattura` int(10) DEFAULT NULL,
		  `numero` int(11) NOT NULL,
		  `data` date NOT NULL,
		  `competenza` int(10) NOT NULL,
		  `spese_anticipate_fattura` double NOT NULL DEFAULT '0',
		  `spese_anticipate_desc` longtext COLLATE latin1_general_ci,
		  `riv_prev` int(1) NOT NULL DEFAULT '0',
		  `ra` int(1) NOT NULL DEFAULT '0',
		  `data_pagamento` date DEFAULT NULL,
		  `note` longtext COLLATE latin1_general_ci,
		  `creation_date` date NOT NULL,
		  `creation_user` varchar(100) COLLATE latin1_general_ci NOT NULL,
		  `last_modification_date` date DEFAULT NULL,
		  `last_modification_user` varchar(100) COLLATE latin1_general_ci DEFAULT NULL,
		  `deletion_date` date DEFAULT NULL,
		  `deletion_user` varchar(100) COLLATE latin1_general_ci DEFAULT NULL,
		  `deletion_flag` tinyint(1) NOT NULL,
		  `activation_flag` tinyint(1) NOT NULL,
		  `ID_Cliente_fatture` int(10) NOT NULL,
		  `anno_contabile` int(10) NOT NULL,
		  PRIMARY KEY (`ID`),
		  KEY `ID_Cliente_fatture` (`ID_Cliente_fatture`),
		  KEY `creation_user` (`creation_user`),
		  KEY `creation_date` (`creation_date`),
		  KEY `activation_flag` (`activation_flag`),
		  KEY `deletion_flag` (`deletion_flag`),
		  KEY `data` (`data`),
		  KEY `ID_ProForma_fattura` (`ID_ProForma_fattura`),
		  KEY `anno_contabile` (`anno_contabile`)
		) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=1 ;



INSERT INTO `_system_decode` (
`ID` ,
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
VALUES 
(NULL , '2009', '-1', 'it', NULL , NULL , NULL , NULL , NULL , NULL , NULL , NULL , NULL , '0', '1', '207'),
(NULL , '2008', '-2', 'it', NULL , NULL , NULL , NULL , NULL , NULL , NULL , NULL , NULL , '0', '1', '207'),
(NULL , '2007', '-3', 'it', NULL , NULL , NULL , NULL , NULL , NULL , NULL , NULL , NULL , '0', '1', '207');


/* ProForma */
ALTER TABLE `ProForma` DROP INDEX `numero` ;
ALTER TABLE `VoceFattura` ADD INDEX ( `iva` ) ;
ALTER TABLE `VoceFattura` ADD INDEX ( `importo` ) ;
ALTER TABLE `ProForma` ADD INDEX ( `riv_prev` ) ;
ALTER TABLE `ProForma` ADD INDEX ( `ra` ) ;
ALTER TABLE `ProForma` ADD INDEX ( `spese_anticipate_fattura` ) ;

/* excel 2 db: 
 *  1. excel -> repace \n
 *  2. excel -> formattazione colonne importi in UK
 *  3. excel sostituire 20% -> 20 e 21% -> 21 e colonne numeriche
 * esportare in csv
 * importare in mysql in tabella esistente
 */


insert into `ProForma` (`ID`, `numero`, `data`, `competenza`, `non_incassabile`, `spese_anticipate_fattura`,  
`riv_prev`, `ra`, `data_pagamento`, `note`,
`creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_flag`, `activation_flag`, 
`ID_Cliente_proforma`, `stato_proforma`, `anno_contabile`)

select `ID`, `numproforma`, `datadoc`, (2 - `IDcompetenza`),  `nonincassabile`,`speseant`,
`riv`,`ra`,`dataproforma`,`noteprof`,   
`datadoc`, 'fabio',`datadoc`,'fabio',0,1,
`IDcliente`,2, (select sd_value from _system_decode where sd_description = YEAR(`datadoc`) and sd_class = 207)
from _import_proforma;


insert into `VoceFattura` (`importo`, `oggetto`, `iva`, 
		`creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`,  `deletion_flag`, `activation_flag`, 
		`ID_ProForma_voci_proforma`)
select `imp1m`, `descriz1`, iva1,`datadoc`, 'fabio',`datadoc`,'fabio',0,1,ID from _import_proforma;

insert into `VoceFattura` (`importo`, `oggetto`, `iva`, 
		`creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`,  `deletion_flag`, `activation_flag`, 
		`ID_ProForma_voci_proforma`)
select `imp2m`, `descriz2`, iva2,`datadoc`, 'fabio',`datadoc`,'fabio',0,1,ID from _import_proforma where imp2m <> 0;

insert into `VoceFattura` (`importo`, `oggetto`, `iva`, 
		`creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`,  `deletion_flag`, `activation_flag`, 
		`ID_ProForma_voci_proforma`)
select `imp3m`, `descriz3`, iva3,`datadoc`, 'fabio',`datadoc`,'fabio',0,1,ID from _import_proforma where imp2m <> 0;

/* iva da decodifica */
update VoceFattura set iva = 4 where iva = 21;

/*allienamento id cliente */
update ProForma set ID_Cliente_proforma = (select ID from Cliente where ID_OLD_HG = ID_Cliente_proforma);

/*storicizzazione */
insert into ProForma_Storico select * from ProForma where anno_contabile <=0;
delete from ProForma where anno_contabile <=0;

/* Fatture */
ALTER TABLE `Fattura` DROP INDEX `numero` ;
ALTER TABLE `Fattura` ADD INDEX ( `riv_prev` ) ;
ALTER TABLE `Fattura` ADD INDEX ( `ra` ) ;
ALTER TABLE `Fattura` ADD INDEX ( `spese_anticipate_fattura` ) ;

/* excel 2 db: 
 *  1. excel -> repace \n
 *  2. excel -> formattazione colonne importi in UK
 *  3. excel sostituire 20% -> 20 e 21% -> 21 e colonne numeriche
 * esportare in csv
 * importare in mysql in tabella esistente
 */


insert into `Fattura` (`ID`, `numero`, `data`, `competenza`,   
`riv_prev`, `ra`, `data_pagamento`,  
`creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_flag`, `activation_flag`, 
`ID_Cliente_fatture`,`anno_contabile`)

select `ID`, `numfattura`, `datafattura`, (2 - `IDcompetenza`),  
`riv`,`ra`,`dataaspa`,   
`datadoc`, 'fabio',`datadoc`,'fabio',0,1,
`IDcliente`, (select sd_value from _system_decode where sd_description = YEAR(`datafattura`) and sd_class = 207)
from _import_fatture;


insert into `VoceFattura` (`importo`, `oggetto`, `iva`, 
		`creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`,  `deletion_flag`, `activation_flag`, 
		`ID_Fattura_voci_fattura`)
select `imp1m`, `descriz1`, iva1,`datadoc`, 'fabio',`datadoc`,'fabio',0,1,ID from _import_fatture;

insert into `VoceFattura` (`importo`, `oggetto`, `iva`, 
		`creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`,  `deletion_flag`, `activation_flag`, 
		`ID_Fattura_voci_fattura`)
select `imp2m`, `descriz2`, iva2,`datadoc`, 'fabio',`datadoc`,'fabio',0,1,ID from _import_fatture where imp2m <> 0;

insert into `VoceFattura` (`importo`, `oggetto`, `iva`, 
		`creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`,  `deletion_flag`, `activation_flag`, 
		`ID_Fattura_voci_fattura`)
select `imp3m`, `descriz3`, iva3,`datadoc`, 'fabio',`datadoc`,'fabio',0,1,ID from _import_fatture where imp2m <> 0;

/* iva da decodifica */
update VoceFattura set iva = 4 where iva = 21;

/*allienamento id cliente */
update Fattura set ID_Cliente_fatture = (select ID from Cliente where ID_OLD_HG = ID_Cliente_fatture);

/*proforma*/
update Fattura set ID_ProForma_fattura = (select ID from _import_proforma where IDfattura = ID);

/*storicizzazione */
insert into Fattura_Storico select * from Fattura where anno_contabile <=0;
delete from Fattura where anno_contabile <=0;



/* Pratiche */
ALTER TABLE `Pratica` DROP INDEX `ID_Cliente_pratiche_2`;

/* convert datetimes: */
update `_import_pratiche` set datainizio = STR_TO_DATE(INIZIO, '%d/%m/%Y'),datafine = STR_TO_DATE(FINE, '%d/%m/%Y');
update `_import_pratiche` set datafine = null where FINE is null or FINE  = '';

update _import_pratiche set STATO = '1' where STATO = 'C';  /* a prestazione */
update _import_pratiche set STATO = '0' where STATO = 'A'; /* a ora */

/* di default, tutte a ore -> tipo 1*/
insert into  `Pratica` 
(`ID`, `tipo`, `titolo`,`stato`, `note`, `data_chiusura`,  `anno_contabile`,
		`creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_flag`, `activation_flag`, 
		`ID_Cliente_pratiche`, `ID_Operatore_responsabile_di_pratiche`)
select 
ID ,1,	NOME ,	STATO ,	NOTE 	, FINE, 
(select sd_value from _system_decode where sd_description = YEAR(`datainizio`) and sd_class = 207), 
INIZIO, 'fabio',INIZIO, 'fabio',0,1,
CLIENTE ,RESPONSABILE 	 	 	
from _import_pratiche;

update Pratica set ID_Cliente_pratiche = (select ID from Cliente where ID_OLD_HG = ID_Cliente_pratiche);

update Pratica set tipo = 2 where stato = 1;  /* a prestazione */



/* Attivita */

ALTER TABLE `Attivita` DROP INDEX `ID_Pratica_attivita_2` ;
/* convert datetimes: */
update `_import_attivita` set creation_date= STR_TO_DATE(data, '%Y-%m-%d'),ultimasessione = STR_TO_DATE(data_fine, '%d/%m/%Y %h.%i.%s');

insert into  `Attivita` (`ID`, `titolo`, `note`, `stato_attivita`, `creation_date`, 
		`creation_user`, `last_modification_date`, `last_modification_user`,  `deletion_flag`, `activation_flag`, 
		`ID_Operatore_attivita_assegnate`, `ID_Pratica_attivita`, `minuti_ultima_fattura`)
SELECT `ID`, `descrizione`, `note`, 0, `creation_date`, 
		'fabio',`creation_date`,'fabio',0,1,
		ID_utente , `ID_pratica`,0
		FROM `_import_attivita`;


insert into LavoroSuAttivita (`data`, `durata_minuti`, `diario`, `creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_flag`, `activation_flag`, `ID_Attivita_sessioni_di_lavoro`) 
select ultimasessione, tempo_totale ,'da importazione',creation_date ,'fabio',`creation_date`,'fabio',0,1,ID
FROM `_import_attivita`;

insert into SpesaAnticipata (data,importo,ID_Attivita_spese_anticipate,oggetto, note,creation_date ,creation_user ,last_modification_date ,last_modification_user ,deletion_flag ,activation_flag ,fatturata)
select data, spese,ID, descrizione,note,creation_date ,'fabio',`creation_date`,'fabio',0,1,0
FROM `_import_attivita`;

/* Solleciti */
/*  .... sono doppi! */
insert into `SollecitoPagamento` (`data`, `descrizione`, `note`, `numero_sollecito`, `creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_flag`, `activation_flag`, `ID_ProForma_solleciti_pagamento`)
select data,'da importazione', 	nota ,numerosollecito 	,data, 'fabio',data, 'fabio', 0,1,	IDproforme from _import_solleciti
GROUP BY numerosollecito, IDproforme;

/* to do */
ALTER TABLE `Operatore` ADD `rivendita_oraria` DOUBLE NULL DEFAULT NULL; 
delete FROM `SpesaAnticipata` WHERE importo = 0;


update Cliente c1 set c1.codice_cliente = (select c2.codice_cliente from Cliente_DMS c2 where c1.ID = c2.ID) where exists 
(select 1 from Cliente_DMS c3 where c1.ID = c3.ID);

/* importare l'excel di linda in _import_codcli3, rincominare le colonne c3 e c6 poi: */

update Cliente set codice_cliente = (select concat(c6,',',c3) from _import_codcli3  where codice_cliente = c6) where exists (select 1 from _import_codcli3  where codice_cliente = c6 and c3 is not null);


/*???? */
update Cliente c set c.`ID__system_user_inverse_of_account` = (select d.ID__system_user_inverse_of_account from Cliente_DMS d  where c.ID = d.ID) where c.ID <= 359 and c.ID__system_user_inverse_of_account is null;

SELECT c.`ID`, `ID__system_user_inverse_of_account`, `cliente`, `codice_cliente` ,user_user_id, user_name from Cliente c inner join _system_user u where u.ID = ID__system_user_inverse_of_account;


update `Cliente` set `ID__system_user_inverse_of_account` = null where `ID__system_user_inverse_of_account` between 16 and 40;


/* clienti consistency checks */
SELECT cliente, c.Id, r.`ID_tipo_cliente` FROM Cliente c inner join `rel_Cliente_tipo_cliente` r on c.ID = r.`ID_Cliente`;


select c.cliente, c.Id, d.tipo_cliente from DatiContabili d inner join Cliente c on c.ID = d.ID_Cliente_dati_contabili;

select c.cliente, c.Id, d.tipo_cliente from DatiContabili d inner join Cliente c on c.ID = d.ID_Cliente_dati_contabili
where not exists (select 1 from  `rel_Cliente_tipo_cliente` r where r.ID_Cliente = c.ID and r.ID_tipo_cliente = d.tipo_cliente)
and c.cessata_assistenza_il is null;


SELECT c.cliente, c.Id, sd_description AS tipo
FROM DatiContabili d
INNER JOIN Cliente c ON c.ID = d.ID_Cliente_dati_contabili
INNER JOIN _system_decode ON sd_value = d.tipo_cliente
WHERE sd_class =200
and c.cessata_assistenza_il is null
AND NOT
EXISTS (
SELECT 1
FROM `rel_Cliente_tipo_cliente` r
WHERE r.ID_Cliente = c.ID
AND r.ID_tipo_cliente = d.tipo_cliente
);



update `ProForma` set riv_prev = 1 where riv_prev > 1;
update `Fattura` set riv_prev = 1 where riv_prev > 1;
update `ProForma` set ra = 1 where ra > 1;
update `Fattura` set ra = 1 where ra > 1;