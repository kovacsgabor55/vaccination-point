package hu.nive.ujratervezes.vaccinationpoint.vaccinationpointevent;

import hu.nive.ujratervezes.vaccinationpoint.VaccineType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql(statements = {
        "delete from `vaccinateds`",
        "delete from `vaccination_point_events`",
        "delete from `patients`",
        "insert into `patients` (`id`, `date_of_birth`, `doses`, `e_mail`, `last_vaccination_date`, `name`, `taj`, `vaccine_type`) values (1, '1957-12-24', 0, 'johndoe@example.com', null, 'John Doe', '123456788', null)",
        "insert into `patients` (`id`, `date_of_birth`, `doses`, `e_mail`, `last_vaccination_date`, `name`, `taj`, `vaccine_type`) values (2, '1985-10-18', 0, 'janedoe@example.com', null, 'Jane Doe', '037687210', null)"})
class VaccinationPointEventServiceIT {

    @Autowired
    private VaccinationPointEventService service;

    private final long patientId = 1;
    private final long patientId2 = 2;

    @Test
    void create() {
        LocalDateTime occasion = LocalDateTime.of(2021, 10, 12, 14, 50);
        String address = "Miskolc Megyei Kórház 2. oltópont";
        VaccineType vaccineType = VaccineType.COMIRNATY;
        CreateVaccinationPointEventCommand command = new CreateVaccinationPointEventCommand(occasion, address, vaccineType);

        VaccinationPointEventDTO result = service.create(patientId, command);

        assertEquals(occasion, result.getOccasion());
        assertEquals(address, result.getAddress());
        assertEquals(vaccineType, result.getVaccineType());
        assertEquals(patientId, result.getPatientID());
    }

    @Test
    void updateById() {
        LocalDateTime occasion = LocalDateTime.of(2021, 10, 12, 14, 50);
        String address = "Miskolc Megyei Kórház 2. oltópont";
        VaccineType vaccineType = VaccineType.COMIRNATY;
        CreateVaccinationPointEventCommand createCommand = new CreateVaccinationPointEventCommand(occasion, address, vaccineType);
        LocalDateTime newOccasion = occasion.plusMonths(3);
        UpdateVaccinationPointEventCommand updateCommand = new UpdateVaccinationPointEventCommand(newOccasion);

        long id = service.create(patientId, createCommand).getId();
        VaccinationPointEventDTO result = service.updateById(id, updateCommand);

        assertEquals(newOccasion, result.getOccasion());
        assertEquals(address, result.getAddress());
        assertEquals(vaccineType, result.getVaccineType());
    }

    @Test
    void findById() {
        LocalDateTime occasion = LocalDateTime.of(2021, 10, 12, 14, 50);
        String address = "Miskolc Megyei Kórház 2. oltópont";
        VaccineType vaccineType = VaccineType.COMIRNATY;
        CreateVaccinationPointEventCommand command = new CreateVaccinationPointEventCommand(occasion, address, vaccineType);

        long id = service.create(patientId, command).getId();

        VaccinationPointEventDTO result = service.findById(id);

        assertEquals(occasion, result.getOccasion());
        assertEquals(address, result.getAddress());
        assertEquals(vaccineType, result.getVaccineType());
        assertEquals(patientId, result.getPatientID());
    }

    @Test
    void deleteAll() {
        LocalDateTime occasion = LocalDateTime.of(2021, 10, 12, 14, 50);
        LocalDateTime occasion2 = LocalDateTime.of(2021, 10, 12, 14, 50);
        String address = "Miskolc Megyei Kórház 2. oltópont";
        String address2 = "Miskolc Megyei Kórház 2. oltópont";
        VaccineType vaccineType = VaccineType.COMIRNATY;
        VaccineType vaccineType2 = VaccineType.SINOPHARM;

        CreateVaccinationPointEventCommand command = new CreateVaccinationPointEventCommand(occasion, address, vaccineType);
        CreateVaccinationPointEventCommand command2 = new CreateVaccinationPointEventCommand(occasion2, address2, vaccineType2);

        service.create(patientId, command);
        service.create(patientId2, command2);

        service.deleteAll();

        assertEquals(0, service.listAll().size());
    }

    @Test
    void deleteById() {
        LocalDateTime occasion = LocalDateTime.of(2021, 10, 12, 14, 50);
        LocalDateTime occasion2 = LocalDateTime.of(2021, 10, 12, 14, 55);
        String address = "Miskolc Megyei Kórház 2. oltópont";
        String address2 = "Miskolc Megyei Kórház 2. oltópont";
        VaccineType vaccineType = VaccineType.COMIRNATY;
        VaccineType vaccineType2 = VaccineType.SINOPHARM;

        CreateVaccinationPointEventCommand command = new CreateVaccinationPointEventCommand(occasion, address, vaccineType);
        CreateVaccinationPointEventCommand command2 = new CreateVaccinationPointEventCommand(occasion2, address2, vaccineType2);

        long deleteId = service.create(patientId, command).getId();
        long testId = service.create(patientId2, command2).getId();

        service.deleteById(deleteId);

        List<VaccinationPointEventDTO> result = service.listAll();

        assertEquals(1, result.size());

        assertEquals(testId, result.get(0).getId());
    }

    @Test
    void listAll() {
        LocalDateTime occasion = LocalDateTime.of(2021, 10, 12, 14, 50);
        LocalDateTime occasion2 = LocalDateTime.of(2021, 10, 12, 14, 55);
        String address = "Miskolc Megyei Kórház 2. oltópont";
        String address2 = "Miskolc Megyei Kórház 2. oltópont";
        VaccineType vaccineType = VaccineType.COMIRNATY;
        VaccineType vaccineType2 = VaccineType.SINOPHARM;

        CreateVaccinationPointEventCommand command = new CreateVaccinationPointEventCommand(occasion, address, vaccineType);
        CreateVaccinationPointEventCommand command2 = new CreateVaccinationPointEventCommand(occasion2, address2, vaccineType2);

        long id1 = service.create(patientId, command).getId();
        long id2 = service.create(patientId2, command2).getId();

        List<VaccinationPointEventDTO> result = service.listAll();

        assertEquals(2, result.size());

        assertThat(result)
                .extracting(VaccinationPointEventDTO::getId)
                .containsExactly(id1, id2);
    }
}
