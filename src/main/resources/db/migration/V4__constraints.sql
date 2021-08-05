ALTER TABLE `patients`
    ADD CONSTRAINT `UK_patients` UNIQUE (`taj`);
ALTER TABLE `vaccinateds`
    ADD CONSTRAINT `FK_vaccinateds_to_patients` FOREIGN KEY (`patient_id`) REFERENCES `vaccination`.`patients` (`id`) ON UPDATE RESTRICT ON DELETE RESTRICT;
ALTER TABLE `vaccination_point_events`
    ADD CONSTRAINT `FK_VPE_to_patients` FOREIGN KEY (`patient_id`) REFERENCES `vaccination`.`patients` (`id`) ON UPDATE RESTRICT ON DELETE RESTRICT;
