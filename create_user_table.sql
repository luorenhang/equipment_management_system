CREATE TABLE `user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `role` VARCHAR(20) NOT NULL DEFAULT 'USER',
  `status` INT(1) NOT NULL DEFAULT 1,
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `tenant` INT(11) NOT NULL DEFAULT 1,
  `rdm_version` INT(11) DEFAULT 0,
  `rdm_extension_type` VARCHAR(50) DEFAULT '',
  `class_name` VARCHAR(100) DEFAULT 'User',
  `creator` VARCHAR(50) DEFAULT 'system',
  `modifier` VARCHAR(50) DEFAULT 'system',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  UNIQUE KEY `uk_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
