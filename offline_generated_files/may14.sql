INSERT INTO `Operatore` (`ID`, `nome_e_cognome`, `email`, `cellulare`, `creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_date`, `deletion_user`, `deletion_flag`, `activation_flag`, `ID__system_user_inverse_of_account`, `pwd_autorizzativa`, `costo_orario`, `rivendita_oraria`) VALUES
(104, 'Metascadenze DoubleCheck', '*@*.it', NULL, '2014-05-07', 'fabio', '2014-05-07', 'fabio', NULL, NULL, 0, 1, NULL, NULL, 0, 0);


INSERT INTO  `tullinihelpgest_14_1_14`.`_system_module` (
`ID` ,
`ID__system_menu_item_sm_menu` ,
`sm_name` ,
`sm_order` ,
`sm_java_class` ,
`sm_alternative_text` ,
`sm_description` ,
`sm_show` ,
`sm_position` ,
`sm_default_parameters` ,
`sm_default_call` ,
`sm_auto_load` ,
`ID__system_meta_environment_sme_modules` ,
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
NULL , NULL ,  'previewMetascadenzeCalendar',  '2',  'eu.anastasis.tulliniHelpGest.modules.PreviewMetascadenzeCalendarModule', NULL , NULL ,  'Doublecheck metascadenze',  '2', NULL , NULL ,  '2',  '1', NULL ,  'afrascari',  '2012-01-24',  'admin',  '2012-01-24',  'admin',  '0',  '1'
);

INSERT INTO _system_instance_auth ( ID, ID__system_group_sia_group, sia_permissions, activation_flag, deletion_flag, creation_user, creation_date, last_modification_user, last_modification_date, sia_instance, sia_class ) VALUES( 84, "1", "111", "1", "0", "admin", "2014-05-08", "admin", "2014-05-08", "43", "_system_module" );
INSERT INTO _system_instance_auth ( ID, ID__system_group_sia_group, sia_permissions, activation_flag, deletion_flag, creation_user, creation_date, last_modification_user, last_modification_date, sia_instance, sia_class ) VALUES( 85, "2", "111", "1", "0", "admin", "2014-05-08", "admin", "2014-05-08", "43", "_system_module" );


UPDATE _system_instance_auth SET deletion_date = "2014-05-08" , deletion_user = "admin" , deletion_flag = "1"  WHERE ID = 45; 
UPDATE _system_instance_auth SET deletion_date = "2014-05-08" , deletion_user = "admin" , deletion_flag = "1"  WHERE ID = 46;
UPDATE _system_instance_auth SET deletion_date = "2014-05-08" , deletion_user = "admin" , deletion_flag = "1"  WHERE ID = 47; 
