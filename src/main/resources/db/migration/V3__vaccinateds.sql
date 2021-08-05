CREATE TABLE `vaccinateds`
(
    `id`                   BIGINT(20) NOT NULL AUTO_INCREMENT,
    `administered_vaccine` VARCHAR(20) NOT NULL COLLATE 'utf8_general_ci',
    `date_of_vaccination`  DATETIME(6) NOT NULL,
    `lot`                  VARCHAR(15) NOT NULL COLLATE 'utf8_general_ci',
    `number_series_doses`  INT(1) NOT NULL,
    `overall_number_doses` INT(1) NOT NULL,
    `vaccine_type`         VARCHAR(20) NULL DEFAULT NULL COLLATE 'utf8_general_ci',
    `patient_id`           BIGINT(20) NOT NULL,
    PRIMARY KEY (`id`)
) COLLATE='utf8_general_ci'
;
