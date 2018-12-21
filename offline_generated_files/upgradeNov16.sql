UPDATE `_system_decode` SET `sd_description` = 'TULLINI' WHERE `_system_decode`.`ID` = 8443;
INSERT INTO `_system_decode` (`ID`, `sd_description`, `sd_value`, `sd_locale`, `sd_notes`, `ID__system_decode_sd_parent`, `sd_image`, `creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_date`, `deletion_user`, `deletion_flag`, `activation_flag`, `sd_class`, `owner_user`, `owner_group`) VALUES ('0', 'STUDIO', '2', 'it', NULL, NULL, NULL, '2011-07-15', 'admin', NULL, NULL, NULL, NULL, '0', '1', '224', '3', NULL);

ALTER TABLE `Pratica` CHANGE `ID_Operatore_responsabile_di_pratiche` `ID_Operatore_responsabile_di_pratiche` INT(10) NULL;

DROP  VIEW IF EXISTS `VW_ProForma_Max_Numero_Studio`;
CREATE  VIEW `VW_ProForma_Max_Numero_Studio`  AS  select max(`ProForma`.`numero`) AS `numero`,1 AS `ID`,`ProForma`.`anno_contabile` AS `anno_contabile`,(select `_system_decode`.`sd_description` from `_system_decode` where ((`_system_decode`.`sd_value` = `ProForma`.`anno_contabile`) and (`_system_decode`.`sd_class` = 207))) AS `note`,NULL AS `data`,NULL AS `creation_date`,NULL AS `creation_user`,0 AS `deletion_flag`,1 AS `activation_flag` from `ProForma` where ((`ProForma`.`deletion_flag` = 0) and (`ProForma`.`competenza` = 2)) group by `ProForma`.`anno_contabile` ;

DROP  VIEW IF EXISTS `VW_Fattura_Max_Numero_Studio`;
CREATE VIEW `VW_Fattura_Max_Numero_Studio`  AS  select max(`Fattura`.`numero`) AS `numero`,1 AS `ID`,`Fattura`.`anno_contabile` AS `anno_contabile`,(select `_system_decode`.`sd_description` from `_system_decode` where ((`_system_decode`.`sd_value` = `Fattura`.`anno_contabile`) and (`_system_decode`.`sd_class` = 207))) AS `note`,NULL AS `data`,NULL AS `creation_date`,NULL AS `creation_user`,0 AS `deletion_flag`,1 AS `activation_flag` from `Fattura` where ((`Fattura`.`deletion_flag` = 0) and (`Fattura`.`competenza` = 2)) group by `Fattura`.`anno_contabile` ;

DROP  VIEW IF EXISTS `VW_ProForma_Max_Numero_Tullini`;
CREATE  VIEW `VW_ProForma_Max_Numero_Tullini`  AS  select max(`ProForma`.`numero`) AS `numero`,1 AS `ID`,`ProForma`.`anno_contabile` AS `anno_contabile`,(select `_system_decode`.`sd_description` from `_system_decode` where ((`_system_decode`.`sd_value` = `ProForma`.`anno_contabile`) and (`_system_decode`.`sd_class` = 207))) AS `note`,NULL AS `data`,NULL AS `creation_date`,NULL AS `creation_user`,0 AS `deletion_flag`,1 AS `activation_flag` from `ProForma` where ((`ProForma`.`deletion_flag` = 0) and (`ProForma`.`competenza` = 1)) group by `ProForma`.`anno_contabile` ;

DROP  VIEW IF EXISTS `VW_Fattura_Max_Numero_Tullini`;
CREATE VIEW `VW_Fattura_Max_Numero_Tullini`  AS  select max(`Fattura`.`numero`) AS `numero`,1 AS `ID`,`Fattura`.`anno_contabile` AS `anno_contabile`,(select `_system_decode`.`sd_description` from `_system_decode` where ((`_system_decode`.`sd_value` = `Fattura`.`anno_contabile`) and (`_system_decode`.`sd_class` = 207))) AS `note`,NULL AS `data`,NULL AS `creation_date`,NULL AS `creation_user`,0 AS `deletion_flag`,1 AS `activation_flag` from `Fattura` where ((`Fattura`.`deletion_flag` = 0) and (`Fattura`.`competenza` = 1)) group by `Fattura`.`anno_contabile` ;
