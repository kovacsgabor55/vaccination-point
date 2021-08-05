package hu.nive.ujratervezes.vaccinationpoint.vaccinationpointevent;

import hu.nive.ujratervezes.vaccinationpoint.VaccineType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {
        "delete from `vaccinateds`",
        "delete from `vaccination_point_events`",
        "delete from `patients`",
        "insert into `patients` (`id`, `date_of_birth`, `doses`, `e_mail`, `last_vaccination_date`, `name`, `taj`, `vaccine_type`) values (1, '1957-12-24', 0, 'johndoe@example.com', null, 'John Doe', '123456788', null)",
        "insert into `patients` (`id`, `date_of_birth`, `doses`, `e_mail`, `last_vaccination_date`, `name`, `taj`, `vaccine_type`) values (2, '1985-10-18', 0, 'janedoe@example.com', null, 'Jane Doe', '037687210', null)"})
class VaccinationPointEventControllerRestIT {

    @Autowired
    TestRestTemplate template;

    private final long patientId = 1;
    private final long patientId2 = 2;

    @BeforeEach
    void setUp() {
        template.delete("/api/vaccinationpointevent");
    }

    @Test
    void listAll() {
        LocalDateTime occasion = LocalDateTime.of(2021, 10, 12, 14, 50);
        LocalDateTime occasion2 = LocalDateTime.of(2021, 10, 12, 14, 55);
        String address = "Miskolc Megyei Kórház 2. oltópont";
        String address2 = "Miskolc Megyei Kórház 2. oltópont";
        VaccineType vaccineType = VaccineType.COMIRNATY;
        VaccineType vaccineType2 = VaccineType.SINOPHARM;

        template.postForObject("/api/vaccinationpointevent/patient/" + patientId,
                new CreateVaccinationPointEventCommand(occasion, address, vaccineType),
                VaccinationPointEventDTO.class);

        template.postForObject("/api/vaccinationpointevent/patient/" + patientId2,
                new CreateVaccinationPointEventCommand(occasion2, address2, vaccineType2),
                VaccinationPointEventDTO.class);

        List<VaccinationPointEventDTO> result = template.exchange(
                "/api/vaccinationpointevent/all/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<VaccinationPointEventDTO>>() {
                }).getBody();

        assertThat(result)
                .extracting(VaccinationPointEventDTO::getVaccineType)
                .containsExactly(vaccineType, vaccineType2);
    }

    @Test
    void findById() {
        LocalDateTime occasion = LocalDateTime.of(2021, 10, 12, 14, 50);
        LocalDateTime occasion2 = LocalDateTime.of(2021, 10, 12, 14, 55);
        String address = "Miskolc Megyei Kórház 2. oltópont";
        String address2 = "Miskolc Megyei Kórház 2. oltópont";
        VaccineType vaccineType = VaccineType.COMIRNATY;
        VaccineType vaccineType2 = VaccineType.SINOPHARM;

        long id = template.postForObject("/api/vaccinationpointevent/patient/" + patientId,
                new CreateVaccinationPointEventCommand(occasion, address, vaccineType),
                VaccinationPointEventDTO.class).getId();

        template.postForObject("/api/vaccinationpointevent/patient/" + patientId2,
                new CreateVaccinationPointEventCommand(occasion2, address2, vaccineType2),
                VaccinationPointEventDTO.class);

        VaccinationPointEventDTO result = template.exchange(
                "/api/vaccinationpointevent/" + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<VaccinationPointEventDTO>() {
                }).getBody();

        assertEquals(id, result.getId());
    }

    @Test
    void create() {
        LocalDateTime occasion = LocalDateTime.of(2021, 10, 12, 14, 50);
        String address = "Miskolc Megyei Kórház 2. oltópont";
        VaccineType vaccineType = VaccineType.COMIRNATY;

        VaccinationPointEventDTO result = template.postForObject("/api/vaccinationpointevent/patient/" + patientId,
                new CreateVaccinationPointEventCommand(occasion, address, vaccineType),
                VaccinationPointEventDTO.class);

        assertEquals(occasion, result.getOccasion());
        assertEquals(address, result.getAddress());
        assertEquals(vaccineType, result.getVaccineType());
        assertEquals(patientId, result.getPatientID());
    }

    @Test
    void update() {
        LocalDateTime occasion = LocalDateTime.of(2021, 10, 12, 14, 50);
        String address = "Miskolc Megyei Kórház 2. oltópont";
        VaccineType vaccineType = VaccineType.COMIRNATY;
        LocalDateTime occasion2 = occasion.plusMonths(3);

        long id = template.postForObject("/api/vaccinationpointevent/patient/" + patientId,
                new CreateVaccinationPointEventCommand(occasion, address, vaccineType),
                VaccinationPointEventDTO.class).getId();

        template.put("/api/vaccinationpointevent/" + id,
                new UpdateVaccinationPointEventCommand(occasion2));

        VaccinationPointEventDTO result = template.exchange(
                "/api/vaccinationpointevent/" + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<VaccinationPointEventDTO>() {
                }).getBody();

        assertEquals(occasion2, result.getOccasion());
        assertEquals(address, result.getAddress());
        assertEquals(vaccineType, result.getVaccineType());
        assertEquals(patientId, result.getPatientID());
    }

    @Test
    void delete() {
        LocalDateTime occasion = LocalDateTime.of(2021, 10, 12, 14, 50);
        LocalDateTime occasion2 = LocalDateTime.of(2021, 10, 12, 14, 50);
        String address = "Miskolc Megyei Kórház 2. oltópont";
        String address2 = "Miskolc Megyei Kórház 2. oltópont";
        VaccineType vaccineType = VaccineType.COMIRNATY;
        VaccineType vaccineType2 = VaccineType.SINOPHARM;

        long deleteId = template.postForObject("/api/vaccinationpointevent/patient/" + patientId,
                new CreateVaccinationPointEventCommand(occasion, address, vaccineType),
                VaccinationPointEventDTO.class).getId();
        long testId = template.postForObject("/api/vaccinationpointevent/patient/" + patientId2,
                new CreateVaccinationPointEventCommand(occasion2, address2, vaccineType2),
                VaccinationPointEventDTO.class).getId();


        template.exchange(
                "/api/vaccinationpointevent/" + deleteId,
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<VaccinationPointEventDTO>() {
                }).getBody();

        List<VaccinationPointEventDTO> result = template.exchange(
                "/api/vaccinationpointevent/all/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<VaccinationPointEventDTO>>() {
                }).getBody();

        assertThat(result)
                .extracting(VaccinationPointEventDTO::getId)
                .containsExactly(testId)
                .doesNotContainSequence(deleteId);
        assertEquals(1, result.size());
    }

    @Test
    void deleteAll() {
        LocalDateTime occasion = LocalDateTime.of(2021, 10, 12, 14, 50);
        LocalDateTime occasion2 = LocalDateTime.of(2021, 10, 12, 14, 50);
        String address = "Miskolc Megyei Kórház 2. oltópont";
        String address2 = "Miskolc Megyei Kórház 2. oltópont";
        VaccineType vaccineType = VaccineType.COMIRNATY;
        VaccineType vaccineType2 = VaccineType.SINOPHARM;

        template.postForObject("/api/vaccinationpointevent/patient/" + patientId,
                new CreateVaccinationPointEventCommand(occasion, address, vaccineType),
                VaccinationPointEventDTO.class);
        template.postForObject("/api/vaccinationpointevent/patient/" + patientId2,
                new CreateVaccinationPointEventCommand(occasion2, address2, vaccineType2),
                VaccinationPointEventDTO.class);

        template.exchange(
                "/api/vaccinationpointevent/delete/all",
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<VaccinationPointEventDTO>() {
                }).getBody();

        List<VaccinationPointEventDTO> result = template.exchange(
                "/api/vaccinationpointevent/all/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<VaccinationPointEventDTO>>() {
                }).getBody();

        assertEquals(0, result.size());
    }
}
