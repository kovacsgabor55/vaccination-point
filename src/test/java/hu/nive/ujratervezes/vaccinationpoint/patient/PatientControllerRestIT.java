package hu.nive.ujratervezes.vaccinationpoint.patient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {
        "delete from `vaccinateds`",
        "delete from `vaccination_point_events`",
        "delete from `patients`"})
class PatientControllerRestIT {

    @Autowired
    TestRestTemplate template;

    @BeforeEach
    void init() {
        template.delete("/api/patient");
    }

    @Test
    void listAll() {
        template.postForObject("/api/patient/",
                new CreatePatientCommand("123456788", "John Doe", LocalDate.of(1957, 12, 24), "johndoe@example.com"),
                PatientDTO.class);

        template.postForObject("/api/patient/",
                new CreatePatientCommand("037687210", "Jane Doe", LocalDate.of(1985, 10, 18), "janedoe@example.com"),
                PatientDTO.class);

        List<PatientDTO> result = template.exchange(
                "/api/patient/all/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PatientDTO>>() {
                }).getBody();

        assertThat(result)
                .extracting(PatientDTO::getTaj)
                .containsExactly("123456788", "037687210");
    }

    @Test
    void findById() {
        long id = template.postForObject("/api/patient/",
                new CreatePatientCommand("123456788", "John Doe", LocalDate.of(1957, 12, 24), "johndoe@example.com"),
                PatientDTO.class).getId();

        template.postForObject("/api/patient/",
                new CreatePatientCommand("037687210", "Jane Doe", LocalDate.of(1985, 10, 18), "janedoe@example.com"),
                PatientDTO.class);

        PatientDTO result = template.exchange(
                "/api/patient/" + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PatientDTO>() {
                }).getBody();

        assertEquals(id, result.getId());
    }

    @Test
    void findByTaj() {
        long id = template.postForObject("/api/patient/",
                new CreatePatientCommand("123456788", "John Doe", LocalDate.of(1957, 12, 24), "johndoe@example.com"),
                PatientDTO.class).getId();

        template.postForObject("/api/patient/",
                new CreatePatientCommand("037687210", "Jane Doe", LocalDate.of(1985, 10, 18), "janedoe@example.com"),
                PatientDTO.class);

        PatientDTO result = template.exchange(
                "/api/patient/taj/123456788",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PatientDTO>() {
                }).getBody();

        assertEquals(id, result.getId());
    }

    @Test
    void createNewPatient() {
        PatientDTO result = template.postForObject("/api/patient/",
                new CreatePatientCommand("123456788", "John Doe", LocalDate.of(1957, 12, 24), "johndoe@example.com"),
                PatientDTO.class);

        assertEquals("123456788", result.getTaj());
        assertEquals("John Doe", result.getName());
        assertEquals("1957-12-24", result.getDateOfBirth().toString());
        assertEquals("johndoe@example.com", result.getEmail());
    }

    @Test
    void update() {
        long id = template.postForObject("/api/patient/",
                new CreatePatientCommand("123456788", "John Doe", LocalDate.of(1957, 12, 24), "johndoe@example.com"),
                PatientDTO.class).getId();

        template.put("/api/patient/" + id,
                new UpdatePatientCommand("037687210", "Jane Doe", LocalDate.of(1985, 10, 18), "janedoe@example.com"));

        List<PatientDTO> result = template.exchange(
                "/api/patient/all/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PatientDTO>>() {
                }).getBody();


        assertEquals("037687210", result.get(0).getTaj());
    }

    @Test
    void delete() {
        long id = template.postForObject("/api/patient/",
                new CreatePatientCommand("123456788", "John Doe", LocalDate.of(1957, 12, 24), "johndoe@example.com"),
                PatientDTO.class).getId();

        template.postForObject("/api/patient/",
                new CreatePatientCommand("037687210", "Jane Doe", LocalDate.of(1985, 10, 18), "janedoe@example.com"),
                PatientDTO.class);

        template.exchange(
                "/api/patient/" + id,
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<PatientDTO>() {
                }).getBody();

        List<PatientDTO> result = template.exchange(
                "/api/patient/all/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PatientDTO>>() {
                }).getBody();

        assertThat(result)
                .extracting(PatientDTO::getTaj)
                .containsExactly("037687210")
                .doesNotContainSequence("123456788");
        assertEquals(1, result.size());
    }

    @Test
    void deleteAll() {
        template.postForObject("/api/patient/",
                new CreatePatientCommand("123456788", "John Doe", LocalDate.of(1957, 12, 24), "johndoe@example.com"),
                PatientDTO.class);

        template.postForObject("/api/patient/",
                new CreatePatientCommand("037687210", "Jane Doe", LocalDate.of(1985, 10, 18), "janedoe@example.com"),
                PatientDTO.class);

        template.exchange(
                "/api/patient/delete/all",
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<PatientDTO>() {
                }).getBody();

        List<PatientDTO> result = template.exchange(
                "/api/patient/all/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PatientDTO>>() {
                }).getBody();

        assertThat(result)
                .extracting(PatientDTO::getTaj)
                .doesNotContainSequence("037687210", "123456788");
        assertEquals(0, result.size());
    }

    @Test
    void notFoundPatientByIdTest() {
        Problem result = template.getForObject("/api/patient/1", Problem.class);

        assertEquals(URI.create("patients/not-found"), result.getType());
        assertEquals(Status.NOT_FOUND, result.getStatus());
    }

    @Test
    void notFoundPatientByTajTest() {
        Problem result = template.getForObject("/api/patient/taj/037687210", Problem.class);

        assertEquals(URI.create("patients/not-found"), result.getType());
        assertEquals(Status.NOT_FOUND, result.getStatus());
    }
}
