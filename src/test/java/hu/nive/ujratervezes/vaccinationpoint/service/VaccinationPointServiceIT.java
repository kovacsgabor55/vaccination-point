package hu.nive.ujratervezes.vaccinationpoint.service;

import hu.nive.ujratervezes.vaccinationpoint.VaccineType;
import hu.nive.ujratervezes.vaccinationpoint.pojo.command.CreatePatientCommand;
import hu.nive.ujratervezes.vaccinationpoint.pojo.command.CreateVaccinationPointCommand;
import hu.nive.ujratervezes.vaccinationpoint.pojo.dto.VaccinationPointDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VaccinationPointServiceIT {

    @Autowired
    private PatientService patientService;

    @Autowired
    private VaccinationPointService service;

    private long patientId = 0;

    @BeforeEach
    void setUp() {
        String taj = "123456788";
        String name = "john Doe";
        LocalDate dob = LocalDate.of(1957, 12, 24);
        String email = "johndoe@example.com";
        CreatePatientCommand command = new CreatePatientCommand(taj, name, dob, email);
        patientId = patientService.save(command).getId();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void save() {
        LocalDateTime occasion = LocalDateTime.of(2021, 10, 12, 14, 50);
        String address = "Miskolc Megyei kórház 2. oltópont";
        VaccineType vaccineType = VaccineType.COMIRNATY;
        CreateVaccinationPointCommand command = new CreateVaccinationPointCommand(occasion, address, vaccineType);
        VaccinationPointDto result = service.save(patientId, command);
        assertEquals(occasion, result.getOccasion());
        assertEquals(address, result.getAddress());
        assertEquals(vaccineType, result.getVaccineType());
        assertEquals(patientId, result.getPatientID());
        System.out.println(result.getPatientID());
    }
}