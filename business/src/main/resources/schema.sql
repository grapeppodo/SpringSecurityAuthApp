
CREATE TABLE IF NOT EXISTS `ss_business`.`user` (
    `username` VARCHAR(45) NOT NULL,
    `password` TEXT NULL,
    `code` VARCHAR(45) NULL,
    PRIMARY KEY(`username`)
);