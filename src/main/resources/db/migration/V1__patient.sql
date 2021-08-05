CREATE TABLE `patients`
(
    `id`                    BIGINT(20) NOT NULL AUTO_INCREMENT,
    `date_of_birth`         DATE         NOT NULL,
    `doses`                 INT(1) NULL DEFAULT '0',
    `e_mail`                VARCHAR(100) NOT NULL COLLATE 'utf8_general_ci',
    `last_vaccination_date` DATETIME(6) NULL DEFAULT NULL,
    `name`                  VARCHAR(50)  NOT NULL COLLATE 'utf8_general_ci',
    `taj`                   CHAR(9)      NOT NULL COLLATE 'utf8_general_ci',
    `vaccine_type`          VARCHAR(20) NULL DEFAULT NULL COLLATE 'utf8_general_ci',
    PRIMARY KEY (`id`)
) COLLATE='utf8_general_ci'
;
