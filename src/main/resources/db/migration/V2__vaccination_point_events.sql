CREATE TABLE `vaccination_point_events`
(
    `id`           BIGINT(20) NOT NULL AUTO_INCREMENT,
    `address`      VARCHAR(255) NOT NULL COLLATE 'utf8_general_ci',
    `occasion`     DATETIME(6) NOT NULL,
    `vaccine_type` VARCHAR(20)  NOT NULL COLLATE 'utf8_general_ci',
    `patient_id`   BIGINT(20) NOT NULL,
    PRIMARY KEY (`id`)
) COLLATE='utf8_general_ci'
;
