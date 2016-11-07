ALTER TABLE `_system_module_parameter` CHANGE `smp_value` `smp_value` LONGTEXT CHARACTER SET latin1 COLLATE latin1_general_ci NOT NULL ;




/* 31/5 */

delete FROM `LavoroSuAttivita` WHERE `durata_minuti` = 0;
update `LavoroSuAttivita` set data = creation_date WHERE `data` = '0000-00-00 00:00:00';


UPDATE _system_menu_item SET smi_locale = "it" , ID__system_menu_item_smi_children = "43" , activation_flag = "1" , smi_alternative_text = "Clienti Candidati (in attesa)" , smi_title = "Clienti Candidati" , smi_href = "?q=object/filter&p=ClienteCandidato&data_source=in-attesa" , smi_association = NULL , smi_order = "5" , last_modification_user = "fabio" , last_modification_date = "2012-05-31"  WHERE ID = 46;
INSERT INTO `tullini-helpgest`.`_system_menu_item` (
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
NULL , 'Tutti i clienti candidati', '1', 'it', NULL , '?q=object/filter&p=ClienteCandidato', 'Tutti i clienti candidati (anche diventati clienti)', '46', '2012-02-24', 'admin', '2012-05-31', 'fabio', NULL , NULL , '0', '1'
);

delete from `_system_menu_item` where ID = 75;

INSERT INTO `_system_menu_item` (`ID`, `smi_title`, `smi_order`, `smi_locale`, `smi_association`, `smi_href`, `smi_alternative_text`, `ID__system_menu_item_smi_children`, `creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_date`, `deletion_user`, `deletion_flag`, `activation_flag`) VALUES
(75, 'Le mie ultime sessioni di lavoro', 9, 'it', NULL, '?q=object/list&p=LavoroSuAttivita/_a_creation_user/_v_@FUN_GET_USER_INFO(param=username)@&data_source=ultime-sessioni', 'Le mie ultime sessioni di lavoro', 41, '2012-04-17', 'admin', '2012-04-17', 'admin', NULL, NULL, 0, 1);

update `Attivita` set creation_user = (select  `user_user_id` from _system_user u inner join Operatore o on u.ID = o.ID__system_user_inverse_of_account where `ID_Operatore_attivita_assegnate` = o.id);


/* dopo telefonata */

delete from ProForma;
delete from Fattura;
delete from ProForma_Storico;
delete from Fattura_Storico;

insert into `ProForma` (`ID`, `numero`, `data`, `competenza`, `non_incassabile`, `spese_anticipate_fattura`,  
		`riv_prev`, `ra`, `data_pagamento`, `note`,
		`creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_flag`, `activation_flag`, 
		`ID_Cliente_proforma`, `stato_proforma`, `anno_contabile`)

		select `ID`, `numproforma`, `datadoc`, (2 - `IDcompetenza`),  `nonincassabile`,`speseant`,
		`riv`,`ra`,`dataproforma`,`noteprof`,   
		`datadoc`, 'fabio',`datadoc`,'fabio',0,1,
		`IDcliente`,2, (select sd_value from _system_decode where sd_description = YEAR(`datadoc`) and sd_class = 207)
		from _import_proforma;

update ProForma set ID_Cliente_proforma = (select ID from Cliente where ID_OLD_HG = ID_Cliente_proforma);

/*storicizzazione */
insert into ProForma_Storico select * from ProForma where anno_contabile <=0;
delete from ProForma where anno_contabile <=0;


insert into `Fattura` (`ID`, `numero`, `data`, `competenza`,   
		`riv_prev`, `ra`, `data_pagamento`,  
		`creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_flag`, `activation_flag`, 
		`ID_Cliente_fatture`,`anno_contabile`)

		select `ID`, `numfattura`, `datafattura`, (2 - `IDcompetenza`),  
		`riv`,`ra`,`dataaspa`,   
		`datadoc`, 'fabio',`datadoc`,'fabio',0,1,
		`IDcliente`, (select sd_value from _system_decode where sd_description = YEAR(`datafattura`) and sd_class = 207)
		from _import_fatture;


/*allienamento id cliente */
update Fattura set ID_Cliente_fatture = (select ID from Cliente where ID_OLD_HG = ID_Cliente_fatture);

/*proforma*/
update Fattura f set ID_ProForma_fattura = (select p.ID from _import_proforma p where p.IDfattura = f.ID);

/*storicizzazione */
insert into Fattura_Storico select * from Fattura where anno_contabile <=0;
delete from Fattura where anno_contabile <=0;


INSERT INTO `tullini-helpgest`.`_system_module_parameter` (
`ID` ,
`smp_name` ,
`smp_notes` ,
`smp_value` ,
`creation_date` ,
`creation_user` ,
`last_modification_date` ,
`last_modification_user` ,
`deletion_date` ,
`deletion_user` ,
`deletion_flag` ,
`activation_flag` ,
`ID__system_module_sm_parameters`
)
VALUES (
NULL , 'VOCE_FORFAIT_ACCONTO', NULL , 'Acconto su assistenza fiscale e contabile a forfait', '2012-05-29', 'admin', '2012-05-29', 'admin', NULL , NULL , '0', '1', '33'
);

INSERT INTO `tullini-helpgest`.`_system_module_parameter` (
`ID` ,
`smp_name` ,
`smp_notes` ,
`smp_value` ,
`creation_date` ,
`creation_user` ,
`last_modification_date` ,
`last_modification_user` ,
`deletion_date` ,
`deletion_user` ,
`deletion_flag` ,
`activation_flag` ,
`ID__system_module_sm_parameters`
)
VALUES (
NULL , 'VOCE_FORFAIT_SALDO', NULL , 'Saldo assistenza fiscale e contabile a forfait', '2012-05-29', 'admin', '2012-05-29', 'admin', NULL , NULL , '0', '1', '33'
);


update Cliente set cessata_assistenza_il = null where cessata_assistenza_il = '0000-00-00' or  cessata_assistenza_il = '';


/* giugno */
ALTER TABLE `SollecitoPagamento` CHANGE `data` `oggetto` VARCHAR( 200 ) CHARACTER SET latin1 COLLATE latin1_general_ci NOT NULL ;

update `Cliente` set email = 'andreafrascari@katamail.com', email2 = 'andreafrascari@katamail.com', email3 = 'andreafrascari@katamail.com', fax = 'andreafrascari@katamail.com', cellulare = '3287684316';