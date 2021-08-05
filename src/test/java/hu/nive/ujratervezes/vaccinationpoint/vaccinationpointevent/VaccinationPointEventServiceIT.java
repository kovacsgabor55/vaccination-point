package hu.nive.ujratervezes.vaccinationpoint.vaccinationpointevent;

import hu.nive.ujratervezes.vaccinationpoint.VaccineType;
import hu.nive.ujratervezes.vaccinationpoint.patient.CreatePatientCommand;
import hu.nive.ujratervezes.vaccinationpoint.patient.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql(statements = {
        "delete from vaccinateds",
        "delete from vaccination_point_events",
        "delete from patients"})
class VaccinationPointEventServiceIT {

    @Autowired
    private PatientService patientService;

    @Autowired
    private VaccinationPointEventService service;

    private long patientId = 0;

    @BeforeEach
    void setUp() {
        String taj = "123456788";
        String name = "John Doe";
        LocalDate dob = LocalDate.of(1957, 12, 24);
        String email = "johndoe@example.com";

        CreatePatientCommand command = new CreatePatientCommand(taj, name, dob, email);

        patientId = patientService.save(command).getId();
    }

    @Test
    void save() {
        LocalDateTime occasion = LocalDateTime.of(2021, 10, 12, 14, 50);
        String address = "Miskolc Megyei Kórház 2. oltópont";
        VaccineType vaccineType = VaccineType.COMIRNATY;
        CreateVaccinationPointEventCommand command = new CreateVaccinationPointEventCommand(occasion, address, vaccineType);

        VaccinationPointEventDTO result = service.save(patientId, command);

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

        long id = service.save(patientId, createCommand).getId();
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

        long id = service.save(patientId, command).getId();

        VaccinationPointEventDTO result = service.findById(id);

        assertEquals(occasion, result.getOccasion());
        assertEquals(address, result.getAddress());
        assertEquals(vaccineType, result.getVaccineType());
        assertEquals(patientId, result.getPatientID());
    }

    @Test
    void deleteAll() {
        String taj = "037687210";
        String name = "Jane Doe";
        LocalDate dob = LocalDate.of(1985, 10, 18);
        String email = "janedoe@example.com";

        CreatePatientCommand createPatientCommand = new CreatePatientCommand(taj, name, dob, email);

        long patientId2 = patientService.save(createPatientCommand).getId();

        LocalDateTime occasion = LocalDateTime.of(2021, 10, 12, 14, 50);
        LocalDateTime occasion2 = LocalDateTime.of(2021, 10, 12, 14, 50);
        String address = "Miskolc Megyei Kórház 2. oltópont";
        String address2 = "Miskolc Megyei Kórház 2. oltópont";
        VaccineType vaccineType = VaccineType.COMIRNATY;
        VaccineType vaccineType2 = VaccineType.SINOPHARM;

        CreateVaccinationPointEventCommand command = new CreateVaccinationPointEventCommand(occasion, address, vaccineType);
        CreateVaccinationPointEventCommand command2 = new CreateVaccinationPointEventCommand(occasion2, address2, vaccineType2);

        service.save(patientId, command);
        service.save(patientId2, command2);

        service.deleteAll();

        assertEquals(0, service.listAll().size());
    }

    @Test
    void deleteById() {
        String taj = "037687210";
        String name = "Jane Doe";
        LocalDate dob = LocalDate.of(1985, 10, 18);
        String email = "janedoe@example.com";

        CreatePatientCommand createPatientCommand = new CreatePatientCommand(taj, name, dob, email);

        long patientId2 = patientService.save(createPatientCommand).getId();

        LocalDateTime occasion = LocalDateTime.of(2021, 10, 12, 14, 50);
        LocalDateTime occasion2 = LocalDateTime.of(2021, 10, 12, 14, 55);
        String address = "Miskolc Megyei Kórház 2. oltópont";
        String address2 = "Miskolc Megyei Kórház 2. oltópont";
        VaccineType vaccineType = VaccineType.COMIRNATY;
        VaccineType vaccineType2 = VaccineType.SINOPHARM;

        CreateVaccinationPointEventCommand command = new CreateVaccinationPointEventCommand(occasion, address, vaccineType);
        CreateVaccinationPointEventCommand command2 = new CreateVaccinationPointEventCommand(occasion2, address2, vaccineType2);

        long deleteId = service.save(patientId, command).getId();
        long testId = service.save(patientId2, command2).getId();

        service.deleteById(deleteId);

        List<VaccinationPointEventDTO> result = service.listAll();

        assertEquals(1, result.size());

        assertEquals(testId, result.get(0).getId());
    }

    @Test
    void listAll() {
        String taj = "037687210";
        String name = "Jane Doe";
        LocalDate dob = LocalDate.of(1985, 10, 18);
        String email = "janedoe@example.com";

        CreatePatientCommand createPatientCommand = new CreatePatientCommand(taj, name, dob, email);

        long patientId2 = patientService.save(createPatientCommand).getId();

        LocalDateTime occasion = LocalDateTime.of(2021, 10, 12, 14, 50);
        LocalDateTime occasion2 = LocalDateTime.of(2021, 10, 12, 14, 55);
        String address = "Miskolc Megyei Kórház 2. oltópont";
        String address2 = "Miskolc Megyei Kórház 2. oltópont";
        VaccineType vaccineType = VaccineType.COMIRNATY;
        VaccineType vaccineType2 = VaccineType.SINOPHARM;

        CreateVaccinationPointEventCommand command = new CreateVaccinationPointEventCommand(occasion, address, vaccineType);
        CreateVaccinationPointEventCommand command2 = new CreateVaccinationPointEventCommand(occasion2, address2, vaccineType2);

        long id1 = service.save(patientId, command).getId();
        long id2 = service.save(patientId2, command2).getId();

        List<VaccinationPointEventDTO> result = service.listAll();

        assertEquals(2, result.size());

        assertThat(result)
                .extracting(VaccinationPointEventDTO::getId)
                .containsExactly(id1, id2);
    }
}
