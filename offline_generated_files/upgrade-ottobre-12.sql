

CREATE TABLE `ProfiloDinamicoClienti` (
		  `ID` int(10) unsigned NOT NULL auto_increment,
		    `nome` varchar(200),
		  `note` longtext,
		  `query` longtext NOT NULL,
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
		UNIQUE KEY (`nome`)
		) ENGINE=InnoDB;



CREATE TABLE IF NOT EXISTS `rel_Scadenza_profili_clienti_to_ProfiloDinamicoClienti_inverse` (
  `ID_Scadenza_profili_clienti` int(10) NOT NULL,
  `ID_ProfiloDinamicoClienti_inverse_of_profili_clienti` int(10) NOT NULL,
PRIMARY KEY  (`ID_Scadenza_profili_clienti` ,`ID_ProfiloDinamicoClienti_inverse_of_profili_clienti`),
INDEX (`ID_Scadenza_profili_clienti`),
INDEX (`ID_ProfiloDinamicoClienti_inverse_of_profili_clienti`)
) ENGINE=InnoDB;


INSERT INTO _system_menu_item ( ID, smi_href, smi_order, ID__system_menu_item_smi_children, smi_locale, smi_title, activation_flag, smi_alternative_text, deletion_flag, creation_user, creation_date, last_modification_user, last_modification_date ) VALUES( 95, "?q=object/filter&p=ProfiloDinamicoClienti", "19", "43", "it", "Profili Dinamici", "1", "Esecuzione Profili Dinamici", "0", "admin", "2012-10-16", "admin", "2012-10-16" );
INSERT INTO _system_menu_item ( ID, smi_href, smi_order, ID__system_menu_item_smi_children, smi_locale, smi_title, activation_flag, smi_alternative_text, deletion_flag, creation_user, creation_date, last_modification_user, last_modification_date ) VALUES( 96, "?q=object/filter&p=SuperCliente", "3", "95", "it", "Creazione nuovo profilo", "1", "Creazione nuovo profilo", "0", "admin", "2012-10-16", "admin", "2012-10-16" );

TRUNCATE TABLE `rel_Scadenza_destinarario_personalizzato` ;


alter table  `Pratica` 
add column ID_Metapratica_pratiche_generate int;

INSERT INTO _system_module_parameter ( ID, ID__system_module_sm_parameters, smp_name, smp_notes, smp_value, activation_flag, deletion_flag, creation_user, creation_date, last_modification_user, last_modification_date ) VALUES( 19, "33", "MIGRAZIONE_PRATICHE_FINO_A", "inserire il mese come numero", "1"  , "1", "0", "admin", "2012-11-21", "admin", "2012-11-21" );
