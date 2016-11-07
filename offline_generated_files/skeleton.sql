-- SCHELETRO DI UN SITO SERE-NA
-- ----------------------------

--
-- Struttura della tabella `rel__system_user_user_groups_to__system_group_group_users`
--

DROP TABLE IF EXISTS `rel__system_user_user_groups_to__system_group_group_users`;
CREATE TABLE IF NOT EXISTS `rel__system_user_user_groups_to__system_group_group_users` (
  `ID__system_user_user_groups` int(10) NOT NULL default '0',
  `ID__system_group_group_users` int(10) NOT NULL default '0',
  PRIMARY KEY  (`ID__system_user_user_groups`,`ID__system_group_group_users`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `rel__system_user_user_groups_to__system_group_group_users`
--

INSERT INTO `rel__system_user_user_groups_to__system_group_group_users` (`ID__system_user_user_groups`, `ID__system_group_group_users`) VALUES
(1, 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `_system_alias`
--

DROP TABLE IF EXISTS `_system_alias`;
CREATE TABLE `_system_alias` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `sal_address` varchar(200),
  `sal_alias` varchar(200),
  `sal_notes` longtext,
  `creation_date` date,
  `creation_user` varchar(100),
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1),
  `activation_flag` tinyint(1),
PRIMARY KEY ( `ID` ),
UNIQUE KEY (`sal_alias`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

--
-- Struttura della tabella `_system_attachment`
--

DROP TABLE IF EXISTS `_system_attachment`;
CREATE TABLE IF NOT EXISTS `_system_attachment` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `sa_alt_text` text,
  `sa_path` text,
  `sa_link_text` text,
  `sa_content_type` varchar(101) default NULL,
  `sa_filename` text,
  `sa_type` int(11) default NULL,
  `sa_size` int(10) default NULL,
  `creation_date` date default NULL,
  `creation_user` varchar(101) default NULL,
  `last_modification_date` date default NULL,
  `last_modification_user` varchar(101) default NULL,
  `deletion_date` date default NULL,
  `deletion_user` varchar(101) default NULL,
  `deletion_flag` tinyint(1) default NULL,
  `activation_flag` tinyint(1) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `_system_attachment`
--


-- --------------------------------------------------------

--
-- Struttura della tabella `_system_banner`
--

DROP TABLE IF EXISTS `_system_banner`;
CREATE TABLE IF NOT EXISTS `_system_banner` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `_sb_link` varchar(100) default NULL,
  `_sb_order` int(10) default NULL,
  `_sb_name` varchar(100) NOT NULL default '',
  `_sb_bgcolor` varchar(100) default NULL,
  `_sb_type` int(10) default NULL,
  `_sb_attachment` int(11) default NULL,
  `_sb_is_active` tinyint(1) default NULL,
  `_sb_alt` varchar(100) default NULL,
  `creation_date` date default NULL,
  `creation_user` varchar(100) default NULL,
  `last_modification_date` date default NULL,
  `last_modification_user` varchar(100) default NULL,
  `deletion_date` date default NULL,
  `deletion_user` varchar(100) default NULL,
  `deletion_flag` tinyint(1) default NULL,
  `activation_flag` tinyint(1) default NULL,
  PRIMARY KEY  (`_sb_name`),
  UNIQUE KEY `ID` (`ID`),
  KEY `ID_2` (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

--
-- Dump dei dati per la tabella `_system_banner`
--


-- --------------------------------------------------------

--
-- Struttura della tabella `_system_class_auth`
--

DROP TABLE IF EXISTS `_system_class_auth`;
CREATE TABLE IF NOT EXISTS `_system_class_auth` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `sca_class` varchar(101) default NULL,
  `sca_permissions` varchar(101) default NULL,
  `ID__system_group_group_class_auth` int(10) default NULL,
  `creation_date` date default NULL,
  `creation_user` varchar(100) default NULL,
  `last_modification_date` date default NULL,
  `last_modification_user` varchar(100) default NULL,
  `deletion_date` date default NULL,
  `deletion_user` varchar(100) default NULL,
  `deletion_flag` tinyint(1) default NULL,
  `activation_flag` tinyint(1) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;



--
-- Dump dei dati per la tabella `_system_class_auth`
--

INSERT INTO `_system_class_auth` (`sca_class`, `sca_permissions`, `ID__system_group_group_class_auth`, `creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_date`, `deletion_user`, `deletion_flag`, `activation_flag`) VALUES
('_system_attachment', '111', 4, NOW(), 'admin', NOW(), 'admin', NULL, NULL, 0, 1),
('_system_cms_node', '111', 4, NOW(), 'admin', NOW(), 'admin', NULL, NULL, 0, 1),
('_system_cms_node_hits', '111', 4, NOW(), 'admin', NOW(), 'admin', NULL, NULL, 0, 1),
('_system_news', '111', 4, NOW(), 'admin', NOW(), 'admin', NULL, NULL, 0, 1),
('_system_menu_item', '111', 4, NOW(), 'admin', NOW(), 'admin', NULL, NULL, 0, 1),
('_system_meta_environment', '111', 4, NOW(), 'admin', NOW(), 'admin', NULL, NULL, 0, 1),
('_system_user', '111', 4, NOW(), 'admin', NOW(), 'admin', NULL, NULL, 0, 1),
('_system_group', '111', 4, NOW(), 'admin', NOW(), 'admin', NULL, NULL, 0, 1),
('_system_decode', '111', 4, NOW(), 'admin', NOW(), 'admin', NULL, NULL, 0, 1),
('_system_decode_class', '111', 4, NOW(), 'admin', NOW(), 'admin', NULL, NULL, 0, 1),
('_system_class_auth', '111', 4, NOW(), 'admin', NOW(), 'admin', NULL, NULL, 0, 1),
('_system_instance_auth', '111', 4, NOW(), 'admin', NOW(), 'admin', NULL, NULL, 0, 1),
('_system_module_admin', '111', 4, NOW(), 'admin', NOW(), 'admin', NULL, NULL, 0, 1),
('_system_error', '111', 4, NOW(), 'admin', NOW(), 'admin', NULL, NULL, 0, 1),
('_system_error_message', '111', 4, NOW(), 'admin', NOW(), 'admin', NULL, NULL, 0, 1),
('_system_banner', '111', 4, NOW(), 'admin', NOW(), 'admin', NULL, NULL, 0, 1),
('_system_city', '111', 4, NOW(), 'admin', NOW(), 'admin', NULL, NULL, 0, 1),
('_system_contact', '111', 4, NOW(), 'admin', NOW(), 'admin', NULL, NULL, 0, 1),
('_system_search_hit', '111', 4, NOW(), 'admin', NOW(), 'admin', NULL, NULL, 0, 1),
('_system_tooltip', '111', 4, NOW(), 'admin', NOW(), 'admin', NULL, NULL, 0, 1),
('_system_log', '222', 4, NOW(), 'admin', NOW(), 'admin', NULL, NULL, 0, 1),
('_system_access', '222', 4, NOW(), 'admin', NOW(), 'admin', NULL, NULL, 0, 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `_system_cms_node`
--

DROP TABLE IF EXISTS `_system_cms_node`;
CREATE TABLE IF NOT EXISTS `_system_cms_node` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `publication_to` varchar(200) collate latin1_general_ci default NULL,
  `publication_from` varchar(200) collate latin1_general_ci default NULL,
  `ID__system_menu_item_present_in_menu_as` int(10) default NULL,
  `keywords` varchar(200) collate latin1_general_ci default NULL,
  `title` varchar(200) collate latin1_general_ci default NULL,
  `body` text collate latin1_general_ci,
  `category` int(10) default NULL,
  `teaser` text collate latin1_general_ci,
  `creation_date` date default NULL,
  `creation_user` varchar(100) collate latin1_general_ci default NULL,
  `last_modification_date` date default NULL,
  `last_modification_user` varchar(100) collate latin1_general_ci default NULL,
  `deletion_date` date default NULL,
  `deletion_user` varchar(100) collate latin1_general_ci default NULL,
  `deletion_flag` tinyint(1) default NULL,
  `activation_flag` tinyint(1) default NULL,
  `extra` text collate latin1_general_ci,
  PRIMARY KEY  (`ID`),
  INDEX (`title`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci ;

--
-- Dump dei dati per la tabella `_system_cms_node`
--

INSERT INTO `_system_cms_node` (`ID`, `publication_to`, `publication_from`, `ID__system_menu_item_present_in_menu_as`, `keywords`, `title`, `body`, `category`, `teaser`, `creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_date`, `deletion_user`, `deletion_flag`, `activation_flag`, `extra`) VALUES
(1, NULL, NULL, NULL, NULL, 'Benvenuti in Sere-na!', '     <div class="introduzione home_1"><h3>Caratteristiche del sito/applicazione</h3></div><p>Inserire il titolo e la descrizione delle principali caratteristiche funzionali e di contenuto dell''applicazione o sito in cui si sta per entrare.<br /></p><div class="introduzione home_2"><h3>A chi si rivolge/ Manuale dell''utente</h3></div><p>Inserire il target d''utenza e le informazioni/servizi che ogni utente potr&#224; raggiungere/utilizzare. Oppure inserire eventuali indicazioni per l''utente su come svolgere operazioni di base o indicizzare i contenuti fornendo link veloci per gestire meglio la navigazione.</p><div class="introduzione home_3"><h3>Chi siamo</h3></div><p>Inserire un''eventuale descrizione dell''azienda/associazione/persone fisiche proprietarie dello spazio, la mission operativa, i servizi forniti.</p><p>Questa &#232; la pagina di accesso all''applicazione.<br /></p><p>Per accedere come amministratore e personalizzare la tua applicazione utilizza le seguenti credenziali:</p><p><strong>username</strong>: admin<br /><strong>password</strong>: admin</p>', NULL,  '    In questo spazio inserire il teaser della pagina, cio&egrave; una descrizione riassuntiva della mission aziendale, delle principali funzioni dell''applicazione o dei contunti descrittivi del sito, a seconda degli scopi funzionali per cui si &egrave; scelto di utilizzare un''applicazione Sere-na.<br />', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, NULL);

-- --------------------------------------------------------

--
-- Struttura della tabella `_system_decode`
--

DROP TABLE IF EXISTS `_system_decode`;
CREATE TABLE IF NOT EXISTS `_system_decode` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `sd_description` varchar(201) NOT NULL,
  `sd_value` int(10) NOT NULL,
  `sd_locale` varchar(101) DEFAULT NULL,
  `sd_notes` varchar(101) DEFAULT NULL,
  `ID__system_decode_sd_parent` int(11) DEFAULT NULL,
  `sd_image` int(11) DEFAULT NULL,
  `creation_date` date DEFAULT NULL,
  `creation_user` varchar(100) DEFAULT NULL,
  `last_modification_date` date DEFAULT NULL,
  `last_modification_user` varchar(100) DEFAULT NULL,
  `deletion_date` date DEFAULT NULL,
  `deletion_user` varchar(100) DEFAULT NULL,
  `deletion_flag` tinyint(1) DEFAULT NULL,
  `activation_flag` tinyint(1) DEFAULT NULL,
  `sd_class` int(10) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `sd_class` (`sd_class`,`sd_locale`,`sd_value`),
  UNIQUE KEY `sd_description` (`sd_class` ,`sd_locale` ,`sd_description`,`ID__system_decode_sd_parent` ),
  KEY `sd_value` (`sd_value`),
  KEY `sd_locale` (`sd_locale`),
  KEY `sd_class_2` (`sd_class`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

--
-- Dump dei dati per la tabella `_system_decode`
--

INSERT INTO `_system_decode` (`sd_description`, `sd_value`, `sd_locale`, `sd_notes`, `ID__system_decode_sd_parent`, `sd_image`, `creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_date`, `deletion_user`, `deletion_flag`, `activation_flag`, `sd_class`) VALUES
( 'home page only', 1, 'it', NULL, NULL, NULL, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 1),
( 'always: static', 2, 'it', NULL, NULL, NULL, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 1),
( 'always: dynamic', 3, 'it', NULL, NULL, NULL, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 1),
( 'always: static, reload on logon', 4, 'it', NULL, NULL, NULL, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 1),
( 'Generale', 1, 'it', NULL, NULL, NULL, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 10),
( 'Redazione', 2, 'it', NULL, NULL, NULL, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 10);

-- --------------------------------------------------------

--
-- Struttura della tabella `_system_decode_class`
--

DROP TABLE IF EXISTS `_system_decode_class`;
CREATE TABLE IF NOT EXISTS `_system_decode_class` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `sdc_description` varchar(101) DEFAULT NULL,
  `sdc_name` varchar(101) NOT NULL,
  `creation_date` date DEFAULT NULL,
  `creation_user` varchar(100) DEFAULT NULL,
  `last_modification_date` date DEFAULT NULL,
  `last_modification_user` varchar(100) DEFAULT NULL,
  `deletion_date` date DEFAULT NULL,
  `deletion_user` varchar(100) DEFAULT NULL,
  `deletion_flag` tinyint(1) DEFAULT NULL,
  `activation_flag` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `sdc_name` (`sdc_name`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `_system_decode_class`
--

INSERT INTO `_system_decode_class` (`ID`, `sdc_description`, `sdc_name`, `creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_date`, `deletion_user`, `deletion_flag`, `activation_flag`) VALUES
(1, 'Proprieta'' caricamento modulo', 'caricamento modulo', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(10, 'Categorie news', 'Categorie news', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(12, 'Classi di importazione', 'Classi di importazione', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1);


-- --------------------------------------------------------



--
-- Struttura della tabella `_system_group`
--

DROP TABLE IF EXISTS `_system_group`;
CREATE TABLE IF NOT EXISTS `_system_group` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `group_name` varchar(101) default NULL,
  `group_description` varchar(101) default NULL,
  `group_default_permissions` varchar(3) default NULL,
  `group_direct_publishing` tinyint(1) default NULL,
  `creation_date` date default NULL,
  `creation_user` varchar(100) default NULL,
  `last_modification_date` date default NULL,
  `last_modification_user` varchar(100) default NULL,
  `deletion_date` date default NULL,
  `deletion_user` varchar(100) default NULL,
  `deletion_flag` tinyint(1) default NULL,
  `activation_flag` tinyint(1) default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `group_name` (`group_name`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

--
-- Dump dei dati per la tabella `_system_group`
--

INSERT INTO `_system_group` (`ID`, `group_name`, `group_description`, `group_default_permissions`, `group_direct_publishing`, `creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_date`, `deletion_user`, `deletion_flag`, `activation_flag`) VALUES
(1, 'Admin', 'amministratori di sistema', '333', 1, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(2, 'Operatori', 'operatori accesso completo (in base al profilo?)', '110', 1, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(3, 'Operatori accesso limitato', 'operatori accesso limitato (in base al profilo?)', '110', 1, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(4, 'Everyone', 'utenti non loggati', '000', 0, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `_system_instance_auth`
--

DROP TABLE IF EXISTS `_system_instance_auth`;
CREATE TABLE IF NOT EXISTS `_system_instance_auth` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `sia_permissions` varchar(101) default NULL,
  `sia_class` varchar(101) default NULL,
  `ID__system_group_sia_group` int(10) default NULL,
  `sia_instance` int(10) default NULL,
  `creation_date` date default NULL,
  `creation_user` varchar(100) default NULL,
  `last_modification_date` date default NULL,
  `last_modification_user` varchar(100) default NULL,
  `deletion_date` date default NULL,
  `deletion_user` varchar(100) default NULL,
  `deletion_flag` tinyint(1) default NULL,
  `activation_flag` tinyint(1) default NULL,
  PRIMARY KEY  (`ID`),
  INDEX (`sia_class`, `sia_instance`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

--
-- Dump dei dati per la tabella `_system_instance_auth`
--

INSERT INTO `_system_instance_auth` (`ID`, `sia_permissions`, `sia_class`, `ID__system_group_sia_group`, `sia_instance`, `creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_date`, `deletion_user`, `deletion_flag`, `activation_flag`) VALUES
(2, '111', '_system_module', 4, 2, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(3, '111', '_system_module', 4, 5, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(4, '333', '_system_module', 1, 4, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(8, '111', '_system_module', 4, 9, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(9, '111', '_system_module', 4, 6, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(10, '111', '_system_module', 4, 7, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(13, '111', '_system_module', 4, 12, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(19, '111', '_system_module', 4, 10, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(20, '111', '_system_module', 4, 11, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(21, '111', '_system_module', 4, 13, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(22, '111', '_system_module', 4, 14, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(23, '111', '_system_module', 4, 15, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(24, '111', '_system_module', 4, 16, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(25, '111', '_system_module', 4, 17, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(26, '111', '_system_module', 4, 18, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1);
-- --------------------------------------------------------

--
-- Struttura della tabella `_system_menu_item`
--

DROP TABLE IF EXISTS `_system_menu_item`;
CREATE TABLE IF NOT EXISTS `_system_menu_item` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `smi_title` varchar(101) default NULL,
  `smi_order` int(10) default NULL,
  `smi_locale` varchar(101) default NULL,
  `smi_association` varchar(101) default NULL,
  `smi_href` varchar(101) default NULL,
  `smi_alternative_text` varchar(101) default NULL,
  `ID__system_menu_item_smi_children` int(10) default NULL,
  `creation_date` date default NULL,
  `creation_user` varchar(100) default NULL,
  `last_modification_date` date default NULL,
  `last_modification_user` varchar(100) default NULL,
  `deletion_date` date default NULL,
  `deletion_user` varchar(100) default NULL,
  `deletion_flag` tinyint(1) default NULL,
  `activation_flag` tinyint(1) default NULL,
  PRIMARY KEY  (`ID`),
  INDEX (`smi_title`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

--
-- Dump dei dati per la tabella `_system_menu_item`
--

INSERT INTO `_system_menu_item` (`ID`, `smi_title`, `smi_order`, `smi_locale`, `smi_association`, `smi_href`, `smi_alternative_text`, `ID__system_menu_item_smi_children`, `creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_date`, `deletion_user`, `deletion_flag`, `activation_flag`) VALUES
(1, 'Amministrazione', 2, 'it', NULL, '', 'Amministrazione', NULL, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(2, 'Gruppi', 1, 'it', NULL, '?q=object/filter&p=_system_group', 'Gruppi', 1, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(3, 'Utenti', 2, 'it', NULL, '?q=object/filter&p=_system_user', 'Utenti', 1, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(4, 'Voci di Menu', 7, 'it', NULL, '?q=object/filter&p=_system_menu_item', 'Voci di Menu', 1, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(5, 'Moduli', 5, 'it', NULL, '?q=object/filter&p=_system_module_admin', 'Moduli', 1, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(6, 'Metambienti', 6, 'it', NULL, '?q=object/filter&p=_system_meta_environment', 'Metambienti', 1, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(7, 'Decodifiche: istanza', 9, 'it', NULL, '?q=object/filter&p=_system_decode', 'Istanze di decodifiche', 1, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(9, 'Autorizzazioni classi', 3, 'it', NULL, '?q=object/filter&p=_system_class_auth', 'Autorizzazioni a livello di classi', 1, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(10, 'Autorizzazioni istanze', 4, 'it', NULL, '?q=object/filter&p=_system_instance_auth', 'Autorizzazioni a livello di istanze', 1, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(12, 'Pagine', 5, 'it', NULL, '?q=object/filter&p=_system_cms_node', 'Pagine', 17, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(13, 'Eventi', 5, 'it', NULL, '?q=object/filter&p=_system_news', 'Eventi', 17, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(17, 'Contenuti', 1, 'it', NULL, '', 'gestione contenuti', NULL, NOW(), NULL, NULL, NULL, NULL, NULL, 0, 1),
(19, 'Decodifiche: classi', 8, 'it', NULL, '?q=object/filter&p=_system_decode_class', 'classi di decodifica', 1, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(11, 'Gestione contenuti', 1, 'it', NULL, '', 'Amministrazione', NULL, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(20, 'Errori di sistema', 10, 'it', NULL, '?q=object/filter&p=_system_error', 'Istanze di errore', 1, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(21, 'Radice_Menu_Contatti', 0, 'it', NULL, NULL, NULL, NULL, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(22, 'Contatti', 0, 'it', NULL, '?q=object/contact/FORM=_system_contact', 'Modulo di contatto', 21, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(23, 'A', 1, 'it', NULL, 'javascript:Anastasis.FontSize.carattereNormale();', 'Caratteri normali', 21, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(24, 'A+', 2, 'it', NULL, 'javascript:Anastasis.FontSize.carattereIngrandito1();', 'Caratteri ingranditi', 21, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(25, 'A++', 3, 'it', NULL, 'javascript:Anastasis.FontSize.carattereIngrandito2();', 'caratteri ingranditi (secondo ingrandimento)', 21, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(26, 'Tooltip', 20, 'it', NULL, '?q=object/filter&p=_system_tooltip', 'Gestione Tooltip', 1, '2010-02-16', 'admin', '2010-02-16', 'admin', NULL, NULL, 0, 1),
(27, 'Alias', 30, 'it', NULL, '?q=object/filter&p=_system_alias', 'Gestione Alias', 1, NOW(), 'admin', NOW(), 'admin', NULL, NULL, 0, 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `_system_meta_environment`
--

DROP TABLE IF EXISTS `_system_meta_environment`;
CREATE TABLE IF NOT EXISTS `_system_meta_environment` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `sme_name` varchar(101) default NULL,
  `creation_date` date default NULL,
  `creation_user` varchar(100) default NULL,
  `last_modification_date` date default NULL,
  `last_modification_user` varchar(100) default NULL,
  `deletion_date` date default NULL,
  `deletion_user` varchar(100) default NULL,
  `deletion_flag` tinyint(1) default NULL,
  `activation_flag` tinyint(1) default NULL,
  PRIMARY KEY  (`ID`),
  INDEX (`sme_name`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

--
-- Dump dei dati per la tabella `_system_meta_environment`
--

INSERT INTO `_system_meta_environment` (`ID`, `sme_name`, `creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_date`, `deletion_user`, `deletion_flag`, `activation_flag`) VALUES
(1, 'standard', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `_system_module`
--

DROP TABLE IF EXISTS `_system_module`;
CREATE TABLE IF NOT EXISTS `_system_module` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `ID__system_menu_item_sm_menu` int(10) default NULL,
  `sm_name` varchar(101) default NULL,
  `sm_order` int(10) default NULL,
  `sm_java_class` varchar(101) default NULL,
  `sm_alternative_text` varchar(201) default NULL,
  `sm_description` varchar(201) default NULL,
  `sm_show` varchar(201) default NULL,
  `sm_position` int(10) default NULL,
  `sm_default_parameters` varchar(200) default NULL,
  `sm_default_call` varchar(100) default NULL,
  `sm_auto_load` int(11) default NULL,
  `ID__system_meta_environment_sme_modules` int(10) default NULL,
  `creation_date` date default NULL,
  `creation_user` varchar(100) default NULL,
  `last_modification_date` date default NULL,
  `last_modification_user` varchar(100) default NULL,
  `deletion_date` date default NULL,
  `deletion_user` varchar(100) default NULL,
  `deletion_flag` tinyint(1) default NULL,
  `activation_flag` tinyint(1) default NULL,
  PRIMARY KEY  (`ID`),
  INDEX (`sm_name`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

--
-- Dump dei dati per la tabella `_system_module`
--

INSERT INTO `_system_module` (`ID`, `ID__system_menu_item_sm_menu`, `sm_name`, `sm_order`, `sm_java_class`, `sm_alternative_text`, `sm_description`, `sm_show`, `sm_position`, `sm_default_parameters`, `sm_default_call`, `sm_auto_load`, `ID__system_meta_environment_sme_modules`, `creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_date`, `deletion_user`, `deletion_flag`, `activation_flag`) VALUES
(2, NULL, 'object', 1, 'eu.anastasis.serena.application.modules.object.ObjectModule', NULL, NULL, NULL, 5, '', NULL, 0, 1, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(4, 1, 'Amministrazione', 4, 'eu.anastasis.serena.application.modules.menu.MenuModule', 'Menu amministrazione sistema', 'Menu amministrazione sistema', 'Amministrazione', 2, NULL, 'call', 3, 1, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(5, NULL, 'login', 1, 'eu.anastasis.serena.application.modules.login.LoginModule', 'Entra nell''applicazione per accedere alle tue funzionalita''', 'Entra nell''applicazione per accedere alle tue funzionalita''', 'Accesso redazione', 2, NULL, 'call', 3, 1, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(6, NULL, 'search', 1, 'eu.anastasis.serena.application.modules.searchEngine.SearchModule', 'risultati ricerca', NULL, 'risultati ricerca', 5, '', NULL, 0, 1, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(7, NULL, 'Calendar', 3, 'eu.anastasis.serena.application.modules.CalendarModule', NULL, NULL, 'Calendario eventi', 2, 'calendario/static', NULL, 2, 1, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 0),
(9, 17, 'Contenuti', 2, 'eu.anastasis.serena.application.modules.menu.MenuModule', 'Menu Redazione sistema', 'Menu amministrazione sistema', 'Contenuti', 2, NULL, 'call', 3, 1, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(10, NULL, 'homepage', 1, 'eu.anastasis.serena.application.modules.object.ObjectModule', 'home page', NULL, 'home page', 5, 'ID=1/class=_system_cms_node/dimension=1', 'detail', 1, 1, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(11, NULL, 'map', 2, 'eu.anastasis.serena.application.modules.map.MapModule', '5', 'Modulo mappe', NULL, 5, NULL, NULL, NULL, 1, NOW(), 'admin', NULL, NULL, NULL, '0', 0, 1),
(12, 0, 'report', 1, 'eu.anastasis.serena.application.modules.BirtReport.ReportModule', '', 'Modulo creazione Report', '', 5, '', '', NULL, 1, NOW(), 'admin', NULL, NULL, NULL, '0', 0, 1),
(13, NULL, 'stat', 20, 'eu.anastasis.serena.application.modules.stat.StatModule', 'stat', 'stat', 'stat', 5, NULL, NULL, NULL, 1, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(14, NULL, 'userRegistration', 1, 'eu.anastasis.serena.application.modules.userRegistration.UserRegistrationModule', 'Registrazione...', 'Registrazione, recupero password e attivazione', 'Registrazione', 5, NULL, NULL, NULL, 1, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(15, 21, 'Contatti', 1, 'eu.anastasis.serena.application.modules.menu.MenuModule', 'Menu Orizzontale', 'Menu contatti e accessibilita', 'Menu contatti e accessibilita', 0, NULL, 'call', 4, 1, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(16, NULL, 'htmlajax', 677, 'eu.anastasis.serena.application.modules.ajax.HTMLAjaxModule', NULL, NULL, NULL, 5, NULL, NULL, NULL, 1, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(17, NULL, 'ajax', 678, 'eu.anastasis.serena.application.modules.ajax.AjaxModule', NULL, NULL, NULL, 5, NULL, NULL, NULL, 1, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(18, NULL, 'alias', 679, 'eu.anastasis.serena.application.modules.alias.AliasModule', NULL, NULL, NULL, 5, NULL, NULL, NULL, 1, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(19, NULL, 'fileimport', 2, 'eu.anastasis.serena.application.modules.fileimport.FileImportModule', NULL, NULL, 'File Import', 5, NULL, NULL, NULL, 1, '2010-05-25', 'admin', '2010-05-25', 'admin', NULL, NULL, 0, 0);


-- --------------------------------------------------------

--
-- Struttura della tabella `_system_news`
--

DROP TABLE IF EXISTS `_system_news`;
CREATE TABLE IF NOT EXISTS `_system_news` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `title` varchar(200) default NULL,
  `teaser` longtext,
  `thumbnail` int(10) default NULL,
  `category` int(10) default NULL,
  `source` int(10) default NULL,
  `event_date` datetime default NULL,
  `body` longtext,
  `image` int(10) default NULL,
  `publication_from` datetime default NULL,
  `publication_to` datetime default NULL,
  `attach` int(11) default NULL,
  `attach_1` int(10) default NULL,
  `attach_2` int(10) default NULL,
  `link_1` int(10) default NULL,
  `link_2` int(10) default NULL,
  `creation_date` date default NULL,
  `creation_user` varchar(100) default NULL,
  `last_modification_date` date default NULL,
  `last_modification_user` varchar(100) default NULL,
  `deletion_date` date default NULL,
  `deletion_user` varchar(100) default NULL,
  `deletion_flag` tinyint(1) default NULL,
  `activation_flag` tinyint(1) default NULL,
  PRIMARY KEY  (`ID`),
  INDEX (`title`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8  ;

--
-- Dump dei dati per la tabella `_system_news`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `_system_tooltip`
--

CREATE TABLE IF NOT EXISTS `_system_tooltip` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `stt_method` varchar(200) NOT NULL,
  `stt_identifier` varchar(200) NOT NULL,
  `stt_module` varchar(200) NOT NULL,
  `stt_description` longtext NOT NULL,
  `stt_class` varchar(200) default NULL,
  `creation_date` date default NULL,
  `creation_user` varchar(100) default NULL,
  `last_modification_date` date default NULL,
  `last_modification_user` varchar(100) default NULL,
  `deletion_date` date default NULL,
  `deletion_user` varchar(100) default NULL,
  `deletion_flag` tinyint(1) default NULL,
  `activation_flag` tinyint(1) default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `stt_method` (`stt_method`,`stt_identifier`,`stt_module`,`stt_class`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `_system_user`
--

DROP TABLE IF EXISTS `_system_user`;
CREATE TABLE IF NOT EXISTS `_system_user` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `user_name` varchar(101)  NOT NULL,
  `user_sercret_pharse` varchar(101) default NULL,
  `user_password` varchar(101)  NOT NULL,
  `user_secret_answer` varchar(101) default NULL,
  `user_user_id` varchar(101) NOT NULL,
  `user_email` varchar(101) default NULL,
  `user_phone_number` varchar(101) default NULL,
  `user_uniqueId` varchar(101) default NULL,
  `user_password_modification_date` date default NULL,
  `creation_date` date default NULL,
  `creation_user` varchar(100) default NULL,
  `last_modification_date` date default NULL,
  `last_modification_user` varchar(100) default NULL,
  `deletion_date` date default NULL,
  `deletion_user` varchar(100) default NULL,
  `deletion_flag` tinyint(1) default NULL,
  `activation_flag` tinyint(1) default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `user_user_id` (`user_user_id`),
  UNIQUE KEY `user_uniqueId` (`user_uniqueId`),
  INDEX (`user_name`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

--
-- Dump dei dati per la tabella `_system_user`
--

INSERT INTO `_system_user` (`ID`, `user_name`, `user_sercret_pharse`, `user_password`, `user_secret_answer`, `user_user_id`, `user_email`, `user_phone_number`, `user_uniqueId`, `user_password_modification_date`, `creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_date`, `deletion_user`, `deletion_flag`, `activation_flag`) VALUES
(1, 'Utente amministratore', NULL, 'z/rjFsM5HQI=', NULL, 'admin', '', '',NULL,NULL, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(2, 'Utente per i cron', NULL, '', NULL, 'cron', '', '',NULL,NULL, NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `_system_access`
--

DROP TABLE IF EXISTS `_system_access`;
CREATE TABLE `_system_access` (
`ID` int(10) unsigned NOT NULL auto_increment,
`sac_session` varchar(200) default NULL,
`sac_notes` varchar(200) default NULL,
`sac_action` varchar(200) default NULL,
`sac_class` varchar(200) default NULL,
`sac_instance` varchar(200) default NULL,
`sac_username` varchar(200) default NULL,
`sac_time` datetime default NULL,
`sac_instance_title` varchar(200) default NULL,
`creation_date` date default NULL,
`creation_user` varchar(100) default NULL,
`last_modification_date` date default NULL,
`last_modification_user` varchar(100) default NULL,
`deletion_date` date default NULL,
`deletion_user` varchar(100) default NULL,
`deletion_flag` tinyint(1) default NULL,
`activation_flag` tinyint(1) default NULL,
	PRIMARY KEY  (`ID`),
	INDEX (`sac_class`, `sac_username`, `sac_action`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ;

--
-- Struttura della tabella `_system_log`
--
DROP TABLE IF EXISTS `_system_log`;
CREATE TABLE `_system_log` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `username` varchar(100) default NULL,
  `date` varchar(100) default NULL,
  `time` varchar(100) default NULL,
  `operation` text,
  `creation_date` date default NULL,
  `creation_user` varchar(100) default NULL,
  `last_modification_date` date default NULL,
  `last_modification_user` varchar(100) default NULL,
  `deletion_date` date default NULL,
  `deletion_user` varchar(100) default NULL,
  `deletion_flag` tinyint(1) default NULL,
  `activation_flag` tinyint(1) default NULL,
  PRIMARY KEY  (`ID`),
  INDEX (`username`, `date`)
) ENGINE=InnoDB   DEFAULT CHARSET=utf8 ;



CREATE TABLE `_system_module_parameter` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `smp_name` varchar(200),
  `smp_notes` longtext,
  `smp_value` varchar(200),
  `creation_date` date,
  `creation_user` varchar(100),
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1),
  `activation_flag` tinyint(1),
`ID__system_module_sm_parameters` int(10),
INDEX(`ID__system_module_sm_parameters`),
PRIMARY KEY ( `ID` ),
UNIQUE KEY (`smp_name`, `ID__system_module_sm_parameters`)
) TYPE=InnoDB;


CREATE TABLE `_system_file_import` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `sfi_title` varchar(200),
  `sfi_import_class` int(10),
  `sfi_notes` longtext,
  `sfi_res` longtext,
  `sfi_file` int(10),
  `sfi_done` tinyint(1),
  `sfi_override` tinyint(1),
  `creation_date` date,
  `creation_user` varchar(100),
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1),
  `activation_flag` tinyint(1),
PRIMARY KEY ( `ID` ),
UNIQUE KEY (`sfi_title`, `sfi_import_class`)
) TYPE=InnoDB;
--
-- Struttura della tabella `_system_error`
--

DROP TABLE IF EXISTS `_system_error`;
CREATE TABLE IF NOT EXISTS `_system_error` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `code` varchar(200) default NULL,
  `documentation` longtext NOT NULL,
  `creation_date` date default NULL,
  `creation_user` varchar(100) default NULL,
  `last_modification_date` date default NULL,
  `last_modification_user` varchar(100) default NULL,
  `deletion_date` date default NULL,
  `deletion_user` varchar(100) default NULL,
  `deletion_flag` tinyint(1) default NULL,
  `activation_flag` tinyint(1) default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

--
-- Dump dei dati per la tabella `_system_error`
--

INSERT INTO `_system_error` (`ID`, `code`, `documentation`, `creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_date`, `deletion_user`, `deletion_flag`, `activation_flag`) VALUES
(1, 'generic_error', ' Errore generico, utilizzato nella gestione delle eccezioni non meglio specificate.<br /><br />Parametri:<br />Non ha parametri<br /> ', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(2, 'missing_required_field', 'Errore di validazione. E'' assente un campo obbligatorio.<br /><br />Parametri:<br />@PAR_0@ nome del campo che ha generato l''errore<br /> ', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(3, 'invalid_captcha', 'Errore generato se fallisce il riconoscimento della captcha<br /><br />Parametri:<br />Non ha parametri<br /> ', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(4, 'invalid_param_in_query_string', 'Errore generato se nella querystring vengono individuati parametri non validi<br /><br />Parametri:<br />Non ha parametri<br />', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(5, 'existing_user', ' Errore generato dalla form di registrazione quando tenta di registrarsi un utente gi&#224; presente nel database.<br /><br />Parametri:<br /> Non ha parametri', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(6, 'field_too_short', 'Errore di validazione. Campo troppo corto<br /><br />Parametri:<br />@PAR_0@ campo che ha generato l''errore<br />@PAR_1@ lunghezza minima<br /> ', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(7, 'field_too_long', 'Errore di validazione. Campo troppo lungo<br /><br />Parametri:<br />@PAR_0@ campo che ha generato l''errore<br />@PAR_1@ lunghezza massima ', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(8, 'data_is_not_a_number', 'Errore di validazione. Il campo, dichiarato come numero, non corrisponde ad un numero.<br /><br />Parametri:<br />@PAR_0@ nome del campo che ha generato l''errore ', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(9, 'data_is_not_a_valid_email', '   Errore di validazione. Il campo, dichiarato come email, non corrisponde ad un indirizzo valido<br /><br />Parametri:<br />@PAR_0@ nome del campo che ha generato l''errore  ', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(12, 'expiring_password_email_body', ' Corpo del messaggio per l''email di avviso di scadenza password<br /><br />Parametri<br />@PAR_0@=nome dell''applicazione<br />@PAR_1@=id utente<br />', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(13, 'expiring_password_email_obj', 'Oggetto dell''email mandata in caso di scadenza password<br /><br />Parametri:<br />Nessun parametro<br />', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(15, 'unrecognized_captcha', 'Errore di riconoscimento del captcha. Il dato immesso non corrisponde a quello richiesto<br /><br />Parametri:<br />Nessun parametro.<br /> ', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(16, 'no_permission', 'Messaggio di errore generato nel caso in cui l''utente tenti un''azione non permessa ', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(17, 'generic_sql_error', 'Errore sql non ulteriormente specificato  ', '2009-09-01', 'admin', '2009-09-01', 'admin', NULL, NULL, 0, 1),
(18, 'missing_required_parameter', ' Errore nella chiamata del metodo. E'' assente un parametro obbigatorio<br /><br />Parametri:<br />@PAR_0@ nome del modulo chiamato<br />@PAR_1@ nome del metodo chiamato<br />@PAR_2@ nome del parametro mancante<br /><br /><br />', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(19, 'invalid_tax_code', 'Codice fiscale non valido.<br /><br />Non ha parametri<br /> ', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(20, 'invalid_city', ' Il comune selezionato non &egrave; valido ', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(21, 'tax_code_not_corresponding_to_others_data', 'Il codice fiscale non corrisponde agli altri dati anagrafici. Assicurarsi di non aver scritto il numero cinque al posto di una lettera esse (o viceversa) o il numero zero al posto di una lettera o (o viceversa).', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(22, 'no_permission_for_module_anonimous', ' Errore di permessi specifico per l''accesso ad un modulo per l''utente anonimo<br />', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(23, 'no_permission_for_module_general', ' Errore di permessi specifico per l''accesso ad un modulo generato per tutti gli utenti non anonimi', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(24, 'object_already_exists', 'Errore generato in caso di duplicate entry exception ', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(25, 'token_error', 'Il token manca o &#232; errato. ',NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(26, 'session_expired', 'Sessione scaduta', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(27, 'generic_template_error', 'Generato in caso di assenza o errori nel template', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(28, 'no_method', 'Errore di chiamata.<br />E'' stato richiesto un Serena Method che non esiste.<br /> ', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(29, 'no_user', 'Nome Utente o password non validi.', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(30, 'expired_password', 'La password fornita &#232; scaduta', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(31, 'invalid_url', 'Url non valido', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(32, 'confirm_password_error', 'il campo conferma password non corrisponde alla password', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(33, 'confirm_email_error', 'il campo conferma email non corrisponde alla email', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),	
(34, 'credit_not_enough', 'Non si hanno abbastanza crediti per l''operazione richiesta', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1),
(35, 'recover_password_failed', 'L''invio della mail &egrave; fallito. Assicurarsi che nel proprio profilo sia specificato un indirizzo email corretto', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `_system_error_message`
--

DROP TABLE IF EXISTS `_system_error_message`;
CREATE TABLE IF NOT EXISTS `_system_error_message` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `language` varchar(200) default NULL,
  `message` varchar(800) default NULL,
  `creation_date` date default NULL,
  `creation_user` varchar(100) default NULL,
  `last_modification_date` date default NULL,
  `last_modification_user` varchar(100) default NULL,
  `deletion_date` date default NULL,
  `deletion_user` varchar(100) default NULL,
  `deletion_flag` tinyint(1) default NULL,
  `activation_flag` tinyint(1) default NULL,
  `ID__system_error_messages` int(10) default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `real_key` (`language`,`ID__system_error_messages`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

--
-- Dump dei dati per la tabella `_system_error_message`
--

INSERT INTO `_system_error_message` (`ID`, `language`, `message`, `creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_date`, `deletion_user`, `deletion_flag`, `activation_flag`, `ID__system_error_messages`) VALUES
(1, 'IT', 'Si &egrave; verificato un errore non previsto', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 1),
(2, 'EN', 'An unknown error has occurred', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 1),
(3, 'IT', '@PAR_0@ &egrave; un campo obbligatorio', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 2),
(4, 'IT', 'Capcha non valido', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 3),
(5, 'EN', 'invalid captcha', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 3),
(6, 'EN', '@PAR_0@  is a required field', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 2),
(7, 'IT', 'Chiamata al modulo non valida', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 4),
(8, 'EN', 'The module received an invalid call', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 4),
(9, 'IT', 'Utente esistente. Inserisci un altro username', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 5),
(10, 'EN', 'Existing user. Try another username', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 5),
(11, 'IT', 'Il campo @PAR_0@ deve contenere almeno @PAR_1@ caratteri', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 6),
(12, 'EN', 'The field @PAR_0@ must contains at least @PAR_1@ characters', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 6),
(13, 'IT', 'Il campo @PAR_0@ puo'' contenere al massimo @PAR_1@ caratteri', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 7),
(14, 'EN', 'The field @PAR_0@ can contains at most @PAR_1@ characters', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 7),
(15, 'IT', 'Il valore di @PAR_0@ deve essere numerico', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 8),
(16, 'EN', '@PAR_0@ must have a numeric format', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 8),
(17, 'IT', 'Il valore di @PAR_0@ deve essere un indirizzo email valido', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 9),
(18, 'EN', '@PAR_0@ must contains a valid email address', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 9),
(19, 'IT', 'Attenzione: password in scadenza per il sito @PAR_0@', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 13),
(20, 'IT', 'La tua password per il sito @PAR_0@ scadra'' tra @PAR_2@ giorni. Sei pregato di aggiornarla. Grazie, lo staff del sito', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 12),(21, 'IT', 'Riconoscimento captcha fallito', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 15),
(22, 'IT', 'Permessi non sufficienti per compiere questa azione. E'' possibile che sia necessario effettuare il login.', '2009-03-23', 'afrascari', '2009-03-23', 'afrascari', NULL, NULL, 0, 1, 16),
(23, 'IT', 'L''applicazione ha generato un errore sql. Contattare l''amministratore di sistema', '2009-09-01', 'admin', '2009-09-01', 'admin', NULL, NULL, 0, 1, 17),
(24, 'IT', 'Il parametro @PAR_2@ &egrave; obbligatorio per il metodo richiesto (@PAR_0@/@PAR_1@)', '2009-09-11', 'admin', '2009-09-11', 'admin', NULL, NULL, 0, 1, 18),
(25, 'IT', 'codice fiscale non valido', NOW(), 'admin',NULL,NULL, NULL, NULL, 0, 1, 19),
(26, 'EN', 'invalid tax code', NOW(), 'admin',NULL, NULL, NULL, NULL, 0, 1, 19),
(27, 'IT', 'Il comune selezionato non &egrave; valido',NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 20),
(28, 'EN', 'The city selected is invalid', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 20),
(29, 'IT', 'Il codice fiscale non corrisponde agli altri dati anagrafici', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 21),
(30, 'EN', 'TO TRANSLATE Il codice fiscale non corrisponde agli altri dati anagrafici',NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 21),
(31, 'IT', 'Sessione scaduta oppure non si dispongono delle autorizzazioni per visualizzare questa pagina. Accedere al sistema con le proprie credenziali e riprovare',NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 22),
(32, 'IT', 'Il tuo profilo utente non ti consente di accedere a questa pagina. Contattare l''amministratore del sistema per maggiori informazioni',NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 23),
(33, 'IT', 'Attenzione l''oggetto inserito &egrave; gi&agrave; presente nella base dati', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 24),
(34, 'IT', 'Si &egrave; verificato un errore: non &egrave; stato possibile portare a termine con successo la richiesta.<br />Le possibili cause sono:<br /><ul><li>Il salvataggio &egrave; stato ripetuto in seguito alla pressione del tasto &quot;indietro&quot;</li></ul>', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 25),
(35, 'EN', 'Session expired after inactivity timeout: please go back to home page and re-login if necessary', '2010-01-21', 'admin', NULL, NULL, NULL, NULL, 0, 1, 26),
(36, 'IT', 'Sessione scaduta per inattivita'': si consiglia di tornare alla home e rieffettuare il logon se necessario', '2010-01-21', 'admin', NULL, NULL, NULL, NULL, 0, 1, 26),
(37, 'IT', 'Pagina inesistente o non disponibile',NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 27),
(38, 'EN', 'Page not found',NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 27),
(39, 'IT', 'Pagina inesistente o non disponibile', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 28),
(40, 'EN', 'Page not found. ', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 28),
(41, 'IT', 'ACCESSO NEGATO. Username e/o Password Errate', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 29),
(42, 'EN', 'ACCESS DENIED. Wrong username and/or password', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 29),
(43, 'IT', 'ACCESSO NEGATO. La password &egrave; scaduta', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 30),
(44, 'EN', 'ACCESS DENIED. Expired password', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 30),
(45, 'IT', 'Indirizzo non valido', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 31),
(46, 'EN', 'Invalid address', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 31),
(47, 'IT', 'Il campo conferma password non corrisponde alla password', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 32),
(48, 'EN', 'Confirm passord field doesn''t match witdh password', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 32),
(49, 'IT', 'Il campo conferma email non corrisponde alla email', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 33),
(50, 'EN', 'Confirm email field doesn''t match witdh email', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 33),
(51, 'IT', 'Non si hanno abbastanza crediti per l''operazione richiesta', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 34),
(52, 'EN', 'Not enough credits to complete the request', NOW(), 'admin', NULL, NULL, NULL, NULL, 0, 1, 34);


-- --------------------------------------------------------

--
-- Struttura della tabella `_system_report`
--

CREATE TABLE `_system_report` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `title` varchar(200) NOT NULL,
  `body` longtext NOT NULL,
  `header` longtext,
  `footer` longtext,
  `header_first_page` longtext,
  `footer_last_page` longtext,
  `state` varchar(200),
  `attachment` int(10),
  `creation_date` date,
  `creation_user` varchar(100),
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1),
  `activation_flag` tinyint(1),
PRIMARY KEY ( `ID` ),
INDEX (`creation_user`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)) TYPE=InnoDB;
