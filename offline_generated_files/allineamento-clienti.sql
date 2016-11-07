/* 1: clienti */




update  `Cliente` c set c.`codice_fiscale` = (select i.`codicefiscale` from _import_clienti i where i.`codice` = c.codice_cliente);

update  `Cliente` c set c.`indirizzo` = (select i.`indirizzo` from _import_clienti i where i.`codice` = c.codice_cliente);

update  `Cliente` c set c.`comune` = (select i.`citta` from _import_clienti i where i.`codice` = c.codice_cliente);

update  `Cliente` c set c.`cap` = (select i.`cap` from _import_clienti i where i.`codice` = c.codice_cliente);

update  `Cliente` c set c.`ID_Operatore_responsabile_di` = (select i.`ID_utente` from _import_clienti i where i.`codice` = c.codice_cliente);

update  `Cliente` c set c.`partita_iva` = (select i.`p_iva` from _import_clienti i where i.`codice` = c.codice_cliente);

update  `Cliente` c set c.`cliente_dal` = (select i.`datainizio` from _import_clienti i where i.`codice` = c.codice_cliente);

update  `Cliente` c set c.`cessata_assistenza_il` = (select i.`datafine` from _import_clienti i where i.`codice` = c.codice_cliente);

update  `Cliente` c set c.`recapito_cap` = (select i.`caprec` from _import_clienti i where i.`codice` = c.codice_cliente);

update  `Cliente` c set c.`recapito_comune` = (select i.`cittarec` from _import_clienti i where i.`codice` = c.codice_cliente);

update  `Cliente` c set c.`recapito_indirizzo` = (select i.`indirizzorec` from _import_clienti i where i.`codice` = c.codice_cliente);

update  `Cliente` c set c.`note` = (select i.`notelibere` from _import_clienti i where i.`codice` = c.codice_cliente);

insert into  `Cliente`  (`cliente` ,`codice_cliente`, `codice_fiscale`, `indirizzo`, `recapito_indirizzo`, `comune`, `recapito_cap`, `recapito_comune`, `cap`, `cessata_assistenza_il`, `cliente_dal`, `partita_iva`, `note`,  `creation_date`, `creation_user`, `deletion_flag`, `activation_flag`, `ID_Operatore_responsabile_di` )
SELECT `nome`, `codice`, `codicefiscale`,`indirizzo`,  `indirizzorec`, `citta`,`caprec`,`cittarec`,`cap`,`datafine`,     `datainizio`, `p_iva`,  `notelibere`, curdate(), 'admin', 0, 1, `ID_utente` FROM `_import_clienti` i 
where i.`codice` not in (select c1.codice_cliente from Cliente c1 INNER JOIN _import_clienti i1 on c1.codice_cliente = i1.codice)


update Cliente c set ID_OLD_HG = (select i.ID from  _import_clienti i where c.codice_cliente = i.codice);


/* 2 CRM */

delete from DatiCRM;

insert into DatiCRM ( `ID_Cliente_dati_crm`, `sostituto_imposta`, `immobili`, `ici`, `note_ici`, `cassetto_fiscale`, `f24_cumulativo`, `antireciclaggio`, `privacy`, `creation_date`, `creation_user`, `deletion_flag`, `activation_flag`)
select ID,`sostimposte`, `immobili`, `ici`, `icinote`, `cassettofisc`, `cumulativo`, `antiriciclaggio`, `privacy`,
curdate(), 'admin', 0, 1 from _import_clienti;

ALTER TABLE `DatiCRM` DROP INDEX `ID_Cliente_dati_crm` ;
update DatiCRM d set ID_Cliente_dati_crm = (select ID from Cliente c where c.ID_OLD_HG = d.ID_Cliente_dati_crm);
ALTER TABLE `DatiCRM` ADD UNIQUE (`ID_Cliente_dati_crm`);


 
/* 3 Dati contabili*/

 
delete from `_import_dati_contabili` where ID_cliente is null;
delete from `_import_dati_contabili`where contabilita is null or contabilita = '';

update  `_import_dati_contabili` set tipo = (select sd_value from _system_decode where sd_description = tipo and sd_class = 200);
update  `_import_dati_contabili` set teniamonoi= 1 where teniamonoi = -1;
update  `_import_dati_contabili` set inail= 1 where inail = -1;
update  `_import_dati_contabili` set iscrizione= 1 where iscrizione = -1;
update  `_import_dati_contabili` set unitalocali= 1 where unitalocali = -1;
update  `_import_dati_contabili` set opzioniunitalocali= 1 where opzioniunitalocali = -1;
update  `_import_dati_contabili` set opzioninp = 1 where opzioninp  = -1;
update  `_import_dati_contabili` set inail= 1 where inail = -1;
update  `_import_dati_contabili` set iscrizione2= 1 where iscrizione2 = -1;
update  `_import_dati_contabili` set incaricoa= 1 where incaricoa = -1;
 
delete from  `_import_dati_contabili_clean`;

insert into  `_import_dati_contabili_clean` (
`ID_cliente`, `tipo`, `teniamonoi`, `contabilita`, `IVA`, `esercizioda`, `esercizioa`, `iscrizione`, `previdenziale1`, `previdenziale2`, `rid`, `sezionespeciale`, `codice`, `unitalocali`, `opzioniunitalocali`, `opzioninp`, `inail`, `liquidazioneiva`, `iscrizione2`, `incaricoa`, `pat`, `notelibere`) 
SELECT DISTINCT `ID_cliente`, `tipo`, `teniamonoi`, `contabilita`, `IVA`, `esercizioda`, `esercizioa`, `iscrizione`, `previdenziale1`, `previdenziale2`, `rid`, `sezionespeciale`, `codice`, `unitalocali`, `opzioniunitalocali`, `opzioninp`, `inail`, `liquidazioneiva`, `iscrizione2`, `incaricoa`, `pat`, `notelibere` FROM `_import_dati_contabili` ;


delete from DatiContabili;


insert into DatiContabili (`tipo_cliente`,  `teniamo_noi`, `regime_contabilita`, `regime_iva`,   `liquidazione_iva`,  `esercizio_da`, `esercizio_a`,  `obbligo_iscrizione_inail`, `pat_numero`, `incarico_a`, `note`, `sezioni_speciali`, `unita_locali`, `provincia_registro_imprese`,
`creation_date`, `creation_user`,  `deletion_flag`, `activation_flag`, `ID_Cliente_dati_contabili`)
SELECT  `tipo`, `teniamonoi`, `contabilita`, `IVA`,  `liquidazioneiva`,`esercizioda`, `esercizioa`,`inail`,`pat`,`incaricoa`,   `notelibere` , `sezionespeciale`,`unitalocali`, concat (`rid`,' - ', `codice`),
 curdate(), 'admin', 0, 1,`ID_cliente`
FROM `_import_dati_contabili_clean`;

ALTER TABLE `DatiContabili` DROP INDEX `tipo_cliente`;
update DatiContabili d set 	ID_Cliente_dati_contabili = (select ID from Cliente c where c.ID_OLD_HG = d.ID_Cliente_dati_contabili);
ALTER TABLE `DatiContabili` ADD UNIQUE (
`tipo_cliente` ,
`ID_Cliente_dati_contabili`
);


update DatiContabili d set codice_ateco = 
(select ateco from _import_clienti i inner join Cliente c on c.codice_cliente = i.codice where c.ID = d.ID_Cliente_dati_contabili);

update DatiContabili set esercizio_solare = 1 where esercizio_da is null;
update DatiContabili set esercizio_solare = 0 where esercizio_da is not null;

update DatiContabili set sezione_ordinaria = 1 where sezioni_speciali is null;
update DatiContabili set sezione_ordinaria = 0 where sezioni_speciali is not null;

update `_import_dati_contabili_clean` set `contabilita` = 1 where `contabilita` = 'Semplificata';
update `_import_dati_contabili_clean` set `contabilita` = 2 where `contabilita` = 'Ordinario';
update `_import_dati_contabili_clean` set `contabilita` = 5 where `contabilita` = '398/91';
update `_import_dati_contabili_clean` set `contabilita` = 7 where `contabilita` = 'NIP';
update `_import_dati_contabili_clean` set `contabilita` = 8 where `contabilita` = 'ESONERO AGR.';
update `_import_dati_contabili_clean` set `contabilita` = 9 where `contabilita` = 'MINIMI';
update `_import_dati_contabili_clean` set `contabilita` = NULL where `contabilita` IN ('LIQ. IVA NOI');

update DatiContabili d set regime_contabilita = 
(select contabilita from _import_dati_contabili_clean i inner join Cliente c on c.ID_OLD_HG = i.ID_cliente where c.ID = d.ID_Cliente_dati_contabili and d.tipo_cliente = i.tipo);


update `_import_dati_contabili_clean` set `IVA` = 7 where `IVA` = 'NIP';
update `_import_dati_contabili_clean` set `IVA` = 8 where `IVA` = 'MINIMI';
update `_import_dati_contabili_clean` set `IVA` = 4 where `IVA` = 'Autotrasportatori';
update `_import_dati_contabili_clean` set `IVA` = 5 where `IVA` = '398/91';
update `_import_dati_contabili_clean` set `IVA` = 9 where `IVA` = 'ESONERO AGR.';
update `_import_dati_contabili_clean` set `IVA` = 1 where `IVA` = 'Ordinario' and liquidazioneiva = 'Mensile';
update `_import_dati_contabili_clean` set `IVA` = 3 where `IVA` = 'Ordinario' and liquidazioneiva = 'Trimestrale';
update `_import_dati_contabili_clean` set `contabilita` = NULL where `contabilita` IN ('LIQ. IVA NOI');


update DatiContabili d set regime_iva = 
(select IVA from _import_dati_contabili_clean i inner join Cliente c on c.ID_OLD_HG = i.ID_cliente where c.ID = d.ID_Cliente_dati_contabili and d.tipo_cliente = i.tipo);


