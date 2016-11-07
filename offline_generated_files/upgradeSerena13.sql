SET @dbName = 'tullini_helpgest';

SELECT CONCAT('ALTER TABLE ', table_schema, '.', table_name,
' ADD COLUMN `owner_user` int(10) unsigned default NULL, '
' ADD COLUMN `owner_group` int(10) unsigned default NULL;',
'ALTER TABLE ', table_schema, '.', table_name,
' ADD INDEX (`owner_user`), ADD INDEX (`owner_group`);',
'UPDATE ', table_schema, '.', table_name, ' T',
' SET owner_user =(SELECT ID FROM ', table_schema, '._system_user U WHERE U.user_user_id =T.creation_user);',
'UPDATE ', table_schema, '.', table_name, ' T ',
' SET owner_group =(SELECT ID__system_group_group_users FROM ', table_schema,'.rel__system_user_user_groups_to__system_group_group_users U ',
'WHERE U.ID__system_user_user_groups =T.owner_user AND U.ID__system_group_group_users > 4 LIMIT 1);') AS ddl
INTO OUTFILE '/tmp/alter_table.sql'
FROM information_schema.tables
WHERE table_schema = @dbName
AND table_type = 'base table'
AND table_name != '_system_user'
AND table_name NOT LIKE 'rel_%';

SET @q1 = CONCAT('ALTER TABLE ', @dbName, '._system_user ADD COLUMN `owner_user` int(10) unsigned default NULL, ADD COLUMN `owner_group` int(10) unsigned default NULL; ');

PREPARE stmt1 FROM @q1;
EXECUTE stmt1;
DEALLOCATE PREPARE stmt1;

SET @q2 = CONCAT('UPDATE ', @dbName, '._system_user U SET owner_user = ID, owner_group = (SELECT ID__system_group_group_users FROM ', @dbName,'.rel__system_user_user_groups_to__system_group_group_users R WHERE U.ID = R.ID__system_user_user_groups LIMIT 1);');
PREPARE stmt2 FROM @q2;
EXECUTE stmt2;
DEALLOCATE PREPARE stmt2;

\. /tmp/alter_table.sql
