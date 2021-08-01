package hu.nive.ujratervezes.vaccinationpoint.service;

import hu.nive.ujratervezes.vaccinationpoint.pojo.command.CreatePatientCommand;
import hu.nive.ujratervezes.vaccinationpoint.pojo.dto.PatientDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PatientServiceIT {

    @Autowired
    private PatientService service;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void save() {
        String taj = "123456788";
        String name = "john Doe";
        LocalDate dob = LocalDate.of(1957, 12, 24);
        String email = "johndoe@example.com";
        CreatePatientCommand command = new CreatePatientCommand(taj, name, dob, email);
        PatientDto result = service.save(command);
        assertEquals(taj, result.getTaj());
        assertEquals(name, result.getName());
        assertEquals(dob, result.getDateOfBirth());
        assertEquals(email, result.getEmail());
    }

    @Test
    void listAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void updateById() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void deleteAll() {
    }
}