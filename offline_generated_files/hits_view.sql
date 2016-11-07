--
-- VIEW per ogni classe con titolo classe_hits: pregi: stampa il titolo della pagina
--
CREATE VIEW _system_cms_node_hits AS 
	SELECT node.title, node.ID,count( access.id ) AS hits ,1 AS `activation_flag`,0 AS `deletion_flag`,node.`creation_user` 
	FROM `_system_access` access, _system_cms_node node
	WHERE node.ID = access.sac_instance
	AND access.sac_class = '_system_cms_node'
	GROUP BY sac_instance
	ORDER BY hits DESC 

--
-- VIEW generica x tutte le classi: difetti: NON stampa il titolo della pagina
--

-- SELECT access.sac_instance, access.sac_class, count(access.sac_instance) as hits 
-- FROM `_system_access` access group by sac_class,sac_instance order by hits desc 


--
-- VIEW per mostrare i login effettuati
--
CREATE VIEW Login_hits AS 
	SELECT user.user_user_id, user.user_name, 
		COUNT( access.id ) AS `hits`,
		MAX( access.sac_time ) AS `last_login`,
		1 AS `activation_flag`, 
		0 AS `deletion_flag`
	FROM `_system_access` access, _system_user user
	WHERE access.sac_action = "login" 
		AND user.ID = access.sac_instance
		AND access.sac_class = '_system_user'
	GROUP BY sac_instance
	ORDER BY hits DESC


--
-- VIEW per mostrare tutte le sessioni, con il loro inizio e la loro fine
-- (ricordarsi pero' di inserire _system_user tra gli ACCESS_LOG_CLASSES in config_application
CREATE VIEW `Sessions` AS 
	SELECT 
		`a1`.`sac_session` AS `session`,
		`a1`.`sac_username` AS `username`,
		`a1`.`sac_time` AS `begin`,
		(
			SELECT MAX(`a2`.`sac_time`) AS `MAX(sac_time)` 
			FROM `_system_access` `a2` 
			WHERE ((`a2`.`sac_username` = `a1`.`sac_username`) 
			AND (`a2`.`sac_session` = `a1`.`sac_session`))
		) AS `end`,
		1 AS `activation_flag`,
		0 AS `deletion_flag` 
	FROM `_system_access` `a1` 
	WHERE (`a1`.`sac_action` = _latin1'login');