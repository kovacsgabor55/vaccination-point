package hu.nive.ujratervezes.vaccinationpoint.controller;

import hu.nive.ujratervezes.vaccinationpoint.pojo.command.CreatePatientCommand;
import hu.nive.ujratervezes.vaccinationpoint.pojo.dto.PatientDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.net.URI;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PatientControllerRestIT {

    @Autowired
    TestRestTemplate template;

    @BeforeEach
    void init() {
        template.delete("/api/patient");
    }

    @Test
    void get() {
    }

    @Test
    void findById() {
    }

    @Test
    void createNewPatient() {
        PatientDto result = template.postForObject("/api/patient/",
                new CreatePatientCommand("123456788", "John Doe", LocalDate.of(1957, 12, 24), "johndoe@example.com"),
                PatientDto.class);

        assertEquals("123456788", result.getTaj());
        assertEquals("John Doe", result.getName());
        assertEquals("1957-12-24", result.getDateOfBirth().toString());
        assertEquals("johndoe@example.com", result.getEmail());
    }

    @Test
    void delete() {
    }

    @Test
    void deleteAll() {
    }

    @Test
    void notFoundPatientTest() {
        Problem result = template.getForObject("/api/patient/1", Problem.class);

        assertEquals(URI.create("patients/not-found"), result.getType());
        assertEquals(Status.NOT_FOUND, result.getStatus());
    }

   /* @Test
    void createPatientWithInvalidTaj() {
        Problem result = template.postForObject("/api/patient",
                new CreatePatientCommand("123456789", "John Doe", LocalDate.of(1957, 12, 24), "johndoe@example.com"),
                Problem.class);

        assertEquals(Status.BAD_REQUEST, result.getStatus());
    }

    @Test
    void createPatientWithInvalidName() {
        Problem result = template.postForObject("/api/patient",
                new CreatePatientCommand("123456788", "JohnDoe", LocalDate.of(1957, 12, 24), "johndoe@example.com"),
                Problem.class);

        assertEquals(Status.BAD_REQUEST, result.getStatus());
    }

    @Test
    void createPatientWithInvalidDOB() {
        Problem result = template.postForObject("/api/patient",
                new CreatePatientCommand("123456788", "John Doe", LocalDate.now(), "johndoe@example.com"),
                Problem.class);

        assertEquals(Status.BAD_REQUEST, result.getStatus());
    }

    @Test
    void createPatientWithInvalidEmail() {
        Problem result = template.postForObject("/api/patient",
                new CreatePatientCommand("123456788", "John Doe", LocalDate.of(1957, 12, 24), "johndoeexample.com"),
                Problem.class);

        assertEquals(Status.BAD_REQUEST, result.getStatus());
    }*/
}
