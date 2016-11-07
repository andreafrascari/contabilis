UPDATE `_system_class_auth` 
SET `deletion_flag`=0, `activation_flag`=1
WHERE `deletion_flag` is null AND `activation_flag` is null;

UPDATE `_system_group` 
SET `deletion_flag`=0, `activation_flag`=1
WHERE `deletion_flag` is null AND `activation_flag` is null;

UPDATE `_system_instance_auth` 
SET `deletion_flag`=0, `activation_flag`=1
WHERE `deletion_flag` is null AND `activation_flag` is null;

UPDATE `_system_meta_environment` 
SET `deletion_flag`=0, `activation_flag`=1
WHERE `deletion_flag` is null AND `activation_flag` is null;

UPDATE `_system_module` 
SET `deletion_flag`=0, `activation_flag`=1
WHERE `deletion_flag` is null AND `activation_flag` is null;

UPDATE `_system_user` 
SET `deletion_flag`=0, `activation_flag`=1
WHERE `deletion_flag` is null AND `activation_flag` is null;

