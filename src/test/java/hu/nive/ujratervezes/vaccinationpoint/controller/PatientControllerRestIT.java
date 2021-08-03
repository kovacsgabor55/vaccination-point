package hu.nive.ujratervezes.vaccinationpoint.controller;

import hu.nive.ujratervezes.vaccinationpoint.pojo.command.CreatePatientCommand;
import hu.nive.ujratervezes.vaccinationpoint.pojo.command.UpdatePatientCommand;
import hu.nive.ujratervezes.vaccinationpoint.pojo.dto.PatientDto;
import hu.nive.ujratervezes.vaccinationpoint.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PatientControllerRestIT {

    @Autowired
    TestRestTemplate template;

    @Autowired
    PatientRepository repository;

    @BeforeEach
    void init() {
        repository.deleteAll();
        template.delete("/api/patient");

    }

    @Test
    void get() {
        template.postForObject("/api/patient/",
                new CreatePatientCommand("123456788", "John Doe", LocalDate.of(1957, 12, 24), "johndoe@example.com"),
                PatientDto.class);

        template.postForObject("/api/patient/",
                new CreatePatientCommand("037687210", "Jane Doe", LocalDate.of(1985, 10, 18), "janedoe@example.com"),
                PatientDto.class);

        List<PatientDto> result = template.exchange(
                "/api/patient/all/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PatientDto>>() {
                }).getBody();

        assertThat(result)
                .extracting(PatientDto::getTaj)
                .containsExactly("123456788", "037687210");
    }

    @Test
    void findById() {
        long id = template.postForObject("/api/patient/",
                new CreatePatientCommand("123456788", "John Doe", LocalDate.of(1957, 12, 24), "johndoe@example.com"),
                PatientDto.class).getId();

        template.postForObject("/api/patient/",
                new CreatePatientCommand("037687210", "Jane Doe", LocalDate.of(1985, 10, 18), "janedoe@example.com"),
                PatientDto.class);

        PatientDto result = template.exchange(
                "/api/patient/" + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PatientDto>() {
                }).getBody();

        assertEquals(id, result.getId());
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
    void update() {
        long id = template.postForObject("/api/patient/",
                new CreatePatientCommand("123456788", "John Doe", LocalDate.of(1957, 12, 24), "johndoe@example.com"),
                PatientDto.class).getId();

        template.put("/api/patient/" + id,
                new UpdatePatientCommand("037687210", "Jane Doe", LocalDate.of(1985, 10, 18), "janedoe@example.com"));

        List<PatientDto> result = template.exchange(
                "/api/patient/all/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PatientDto>>() {
                }).getBody();


        assertEquals("037687210", result.get(0).getTaj());
    }

    @Test
    void delete() {
        long id = template.postForObject("/api/patient/",
                new CreatePatientCommand("123456788", "John Doe", LocalDate.of(1957, 12, 24), "johndoe@example.com"),
                PatientDto.class).getId();

        template.postForObject("/api/patient/",
                new CreatePatientCommand("037687210", "Jane Doe", LocalDate.of(1985, 10, 18), "janedoe@example.com"),
                PatientDto.class);

        template.exchange(
                "/api/patient/" + id,
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<PatientDto>() {
                }).getBody();

        List<PatientDto> result = template.exchange(
                "/api/patient/all/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PatientDto>>() {
                }).getBody();

        assertThat(result)
                .extracting(PatientDto::getTaj)
                .containsExactly("037687210")
                .doesNotContainSequence("123456788");
    }

    @Test
    void deleteAll() {
        template.postForObject("/api/patient/",
                new CreatePatientCommand("123456788", "John Doe", LocalDate.of(1957, 12, 24), "johndoe@example.com"),
                PatientDto.class);

        template.postForObject("/api/patient/",
                new CreatePatientCommand("037687210", "Jane Doe", LocalDate.of(1985, 10, 18), "janedoe@example.com"),
                PatientDto.class);

        template.exchange(
                "/api/patient/deleteall",
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<PatientDto>() {
                }).getBody();

        List<PatientDto> result = template.exchange(
                "/api/patient/all/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PatientDto>>() {
                }).getBody();

        assertThat(result)
                .extracting(PatientDto::getTaj)
                .doesNotContainSequence("037687210", "123456788");
        assertEquals(0, result.size());
    }

    @Test
    void notFoundPatientTest() {
        Problem result = template.getForObject("/api/patient/1", Problem.class);

        assertEquals(URI.create("patients/not-found"), result.getType());
        assertEquals(Status.NOT_FOUND, result.getStatus());
    }

}
