
DROP TABLE IF EXISTS `_system_log`;
CREATE TABLE IF NOT EXISTS `_system_log` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `onclass` varchar(101) NOT NULL,
  `oninstance` int(11) NOT NULL,
  `operation` text NOT NULL,
  `result` varchar(200) NOT NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date DEFAULT NULL,
  `last_modification_user` varchar(100) DEFAULT NULL,
  `deletion_date` date DEFAULT NULL,
  `deletion_user` varchar(100) DEFAULT NULL,
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `date` (`date`,`onclass`,`oninstance`),
  KEY `date_2` (`date`),
  KEY `onclass` (`onclass`),
  KEY `oninstance` (`oninstance`),
  KEY `creation_date` (`creation_date`),
  KEY `creation_user` (`creation_user`),
  KEY `activation_flag` (`activation_flag`),
  KEY `deletion_flag` (`deletion_flag`),
  KEY `result` (`result`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;