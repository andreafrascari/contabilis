CREATE TABLE `Car4uFleet` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `targa` varchar(100) not null,
    `cliente` varchar(100) not null,
  `note` longtext null,
      `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
INDEX(`cliente`),
PRIMARY KEY ( `ID` ),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`),
UNIQUE KEY (`targa`)
) ENGINE=InnoDB;

INSERT INTO `tullini`.`DescrizioneDocumento` ( `tipologia_documento`, `visibilita`, `azione_conseguente_caricamento`, `sollecito`, `template_testo_invio`, `template_oggetto_invio`, `template_sms`, `contenuto_azione`, `creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_date`, `deletion_user`, `deletion_flag`, `activation_flag`, `owner_user`, `owner_group`) VALUES ( 'MULTA', '2', '3', '1', '<p>Gentile cliente,<br />
<br />
Ti inviamo il Mod. @TIPO@ di prossima scadenza, che potrai scaricare dalla Tua area riservata del ns. servizio DMS cliccando sul seguente link: @LINK@<br />
<br />
La scadenza è indicata in calce al Mod. @TIPO@ di seguito alla dicitura "Riferimento:".</p>

<p><strong>Importante!</strong><strong> Nel caso in cui il Mod. @TIPO@ presenti un "Saldo Finale" pari a zero, lo stesso potrà essere presentato <span style="text-decoration: underline;">esclusivamente</span> attraverso le procedure F24 On line o F24 Web predisposte dall''Agenzia delle Entrate (è necessario essere in possesso delle credenziali di accesso); in alternativa, per la presentazione occorrerà conferire apposito incarico allo Studio.</strong></p>

<p><strong>In considerazione delle diverse modalità di presentazione dei modelli previste <span style="text-decoration: underline;">a decorrere dall''1/10/2014</span>, prima di provvedere al versamento si raccomanda di fare riferimento al seguente<a href="http://www.studiotullini.it/index.php?option=com_content&task=view&id=65&Itemid=34" target="_blank"> link</a>, tenendo presente che le modalità indicate sono distinte per i contribuenti in possesso di partita IVA rispetto agli altri soggetti.</strong></p>

<p>Si precisa infine che per i Sigg.ri Clienti che hanno conferito apposito incarico, il Mod. @TIPO@ riporta in calce la dicitura "Telematico Entratel": in questo caso l''invio del Mod. @TIPO@ sarà eseguito direttamente dallo Studio e, pertanto, la presente comunicazione ha solo valore di preavviso di addebito sul conto corrente indicato.<br />
<br />
Cliccando sul seguente <a href="http://www1.agenziaentrate.gov.it/documentazione/versamenti/codici/ricerca/elencoTributi.php?Q1=Tutte&Q3=Tutte&Q2=Tutte&Q4=Tutte" id="anastasis_link_1226645347" title="link">link</a> è disponibile la legenda completa dei codici tributo riportati nel Mod. @TIPO@.<br />
<br />
Ti informiamo infine che il documento resterà presente nella Tua area riservata per future necessità.<br />
<br />
<strong>Attenzione! </strong>A seguito dell''entrata in vigore dell''art. 31 del D.L. 78/2010 (<span style="text-decoration: underline;">divieto di compensazione</span> in presenza di debiti erariali scaduti di importo superiore ad Euro 1.500,00), Ti preghiamo di verificare attentamente il Mod. F24 allegato: nel caso in cui siano indicate somme nella colonna "importi a credito compensati" della "Sezione Erario" o della "Sezione Regioni", Ti preghiamo di comunicarci <span style="text-decoration: underline;">immediatamente</span> l''esistenza di cartelle di pagamento scadute o il mancato pagamento di una o più rate di ruoli rateizzati,<span style="text-decoration: underline;"> inoltrando la presente comunicazione a </span><a href="mailto:segreteria@studiotullini.it?subject=Richiesta%20verifica%20Mod.%20F24&body=Presenza%20di%20cartelle%20di%20pagamento%20scadute,%20chiedo%20di%20essere%20contattato." target="_blank"><span style="text-decoration: underline;">segreteria@studiotullini.it</span></a><span style="text-decoration: underline;"> ed indicando in oggetto "Richiesta verifica Mod. F24</span>"; sarà cura dello Studio mettersi in contatto per verificare l''eventuale necessità di ricalcolo del presente Mod. F24. In mancanza, lo Studio declina qualsiasi responsabilità per l''indebito utilizzo di crediti.<br />
<br />
Cordiali Saluti,<br />
Studio Tullini/Contabilis Srl</p>
', '@NOME@ - Mod. @TIPO@ in scadenza', 'E'' stato predisposto un Mod. @TIPO@ di prossima scadenza. Per informazioni contattare lo Studio. Cordiali saluti', NULL, '2011-08-18', 'admin', '2014-11-07', 'fabio', '2011-10-05', 'fabio', '0', '1', '3', NULL);

INSERT INTO `_system_decode` ( `sd_description`, `sd_value`, `sd_locale`, `sd_notes`, `ID__system_decode_sd_parent`, `sd_image`, `creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_date`, `deletion_user`, `deletion_flag`, `activation_flag`, `sd_class`) VALUES
( 'eu.anastasis.tulliniHelpGest.utils.Car4uFleetImporter', 0, 'it', NULL, NULL, NULL, '2015-03-10', 'admin', '2015-03-10', 'admin', NULL, NULL, 0, 1, 12);

INSERT INTO _system_instance_auth ( ID, ID__system_group_sia_group, sia_permissions, activation_flag, deletion_flag, creation_user, creation_date, last_modification_user, last_modification_date, sia_instance, sia_class ) VALUES( 87, "5", "111", "1", "0", "admin", "2015-03-09", "admin", "2015-03-09", "19", "_system_module" );
INSERT INTO _system_instance_auth ( ID, ID__system_group_sia_group, sia_permissions, activation_flag, deletion_flag, creation_user, creation_date, last_modification_user, last_modification_date, sia_instance, sia_class ) VALUES( 88, "1", "111", "1", "0", "admin", "2015-03-09", "admin", "2015-03-09", "19", "_system_module" );
INSERT INTO _system_instance_auth ( ID, ID__system_group_sia_group, sia_permissions, activation_flag, deletion_flag, creation_user, creation_date, last_modification_user, last_modification_date, sia_instance, sia_class ) VALUES( 89, "2", "111", "1", "0", "admin", "2015-03-09", "admin", "2015-03-09", "19", "_system_module" );




drop view if exists `VW_CalendarioFatturazione_Preview_Generazione`;
CREATE  VIEW `VW_CalendarioFatturazione_Preview_Generazione` AS 
select `CalendarioFatturazione`.`ID` AS `ID`,`CalendarioFatturazione`.`data` AS `data`,`CalendarioFatturazione`.`ID_ProForma_entry_calendario` AS `ID_ProForma_entry_calendario`,`CalendarioFatturazione`.`rata` AS `rata`,`CalendarioFatturazione`.`su_rate` AS `su_rate`,`CalendarioFatturazione`.`creation_date` AS `creation_date`,`CalendarioFatturazione`.`creation_user` AS `creation_user`,`CalendarioFatturazione`.`last_modification_date` AS `last_modification_date`,`CalendarioFatturazione`.`last_modification_user` AS `last_modification_user`,`CalendarioFatturazione`.`deletion_date` AS `deletion_date`,`CalendarioFatturazione`.`deletion_user` AS `deletion_user`,`CalendarioFatturazione`.`deletion_flag` AS `deletion_flag`,`CalendarioFatturazione`.`activation_flag` AS `activation_flag`,`CalendarioFatturazione`.`ID_Cliente_calendario_fatturazione` AS `ID_Cliente_calendario_fatturazione` from 
`CalendarioFatturazione`  inner join Cliente cl on cl.ID = CalendarioFatturazione.ID_Cliente_calendario_fatturazione
where isnull(`CalendarioFatturazione`.`ID_ProForma_entry_calendario`) and (`CalendarioFatturazione`.`data` <= curdate()) 
and (`CalendarioFatturazione`.`deletion_flag` = 0) and cl.deletion_flag=0 and cl.cessata_assistenza_il is null;
