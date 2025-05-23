CREATE TABLE IF NOT EXISTS `ss_auth`.`user` (
    `username` VARCHAR(45) NOT NULL,
    `password` TEXT NULL,
    PRIMARY KEY(`username`)
);

CREATE TABLE IF NOT EXISTS `ss_auth`.`user` (
    `username` VARCHAR(45) NOT NULL,
    `code` VARCHAR(45) NULL,
    PRIMARY KEY(`username`)
);