package hu.nive.ujratervezes.vaccinationpoint.vaccinated;

import hu.nive.ujratervezes.vaccinationpoint.VaccineAdministered;
import hu.nive.ujratervezes.vaccinationpoint.VaccineType;
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
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {
        "delete from `vaccinateds`",
        "delete from `vaccination_point_events`",
        "delete from `patients`",
        "insert into `patients` (`id`, `date_of_birth`, `doses`, `e_mail`, `last_vaccination_date`, `name`, `taj`, `vaccine_type`) values (1, '1957-12-24', 1, 'johndoe@example.com', null, 'John Doe', '123456788', null)",
        "insert into `vaccination_point_events` (`id`, `address`, `occasion`, `patient_id`, `vaccine_type`) values (1, 'Miskolc Megyei Kórház 2. oltópont', NOW(), 1, 'COMIRNATY')"})
class VaccinatedControllerRestIT {

    @Autowired
    TestRestTemplate template;

    private final long patientId = 1;

    @BeforeEach
    void setUp() {
        template.delete("/api/vaccinated");
    }

    @Test
    void listAll() {
        int numberSeriesDoses = 1;
        int numberSeriesDoses2 = 2;
        int overallNumberDoses = 2;
        LocalDateTime dateOfVaccination = LocalDateTime.of(2020, 8, 24, 16, 50);
        LocalDateTime dateOfVaccination2 = LocalDateTime.of(2020, 8, 24, 16, 55);
        VaccineAdministered administered = VaccineAdministered.RIGHT_ARM;
        VaccineAdministered administered2 = VaccineAdministered.RIGHT_ARM;
        VaccineType vaccineType = VaccineType.ASTRA_ZENECA;
        VaccineType vaccineType2 = VaccineType.ASTRA_ZENECA;
        String lot = "AF2541CB";
        String lot2 = "HG76Se45";
        boolean nextVaccination = true;
        boolean nextVaccination2 = false;
        LocalDateTime nextVaccinationDate = LocalDateTime.of(2021, 11, 24, 16, 50);
        LocalDateTime nextVaccinationDate2 = LocalDateTime.of(2021, 11, 24, 16, 55);

        long id = template.postForObject("/api/vaccinated/patient/" + patientId,
                new CreateVaccinatedCommand(numberSeriesDoses, overallNumberDoses, dateOfVaccination, administered, vaccineType, lot, nextVaccination, nextVaccinationDate),
                VaccinatedDTO.class).getId();
        long id2 = template.postForObject("/api/vaccinated/patient/" + patientId,
                new CreateVaccinatedCommand(numberSeriesDoses2, overallNumberDoses, dateOfVaccination2, administered2, vaccineType2, lot2, nextVaccination2, nextVaccinationDate2),
                VaccinatedDTO.class).getId();
        List<VaccinatedDTO> result = template.exchange(
                "/api/vaccinated/all/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<VaccinatedDTO>>() {
                }).getBody();

        assertEquals(2, result.size());

        assertThat(result)
                .extracting(VaccinatedDTO::getId)
                .containsExactly(id, id2);
    }

    @Test
    void findById() {
        int numberSeriesDoses = 1;
        int overallNumberDoses = 1;
        LocalDateTime dateOfVaccination = LocalDateTime.of(2020, 8, 24, 16, 50);
        VaccineAdministered administered = VaccineAdministered.LEFT_THIGH;
        VaccineType vaccineType = VaccineType.CURE_VAC;
        String lot = "Tgde45gf";
        boolean nextVaccination = false;
        LocalDateTime nextVaccinationDate = LocalDateTime.of(2021, 11, 24, 16, 50);

        long id = template.postForObject("/api/vaccinated/patient/" + patientId,
                new CreateVaccinatedCommand(numberSeriesDoses, overallNumberDoses, dateOfVaccination, administered, vaccineType, lot, nextVaccination, nextVaccinationDate),
                VaccinatedDTO.class).getId();

        VaccinatedDTO result = template.exchange(
                "/api/vaccinated/" + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<VaccinatedDTO>() {
                }).getBody();

        assertEquals(id, result.getId());
        assertEquals(vaccineType, result.getVaccineType());
    }

    @Test
    void create() {
        int numberSeriesDoses = 1;
        int overallNumberDoses = 2;
        LocalDateTime dateOfVaccination = LocalDateTime.of(2020, 8, 24, 16, 50);
        VaccineAdministered administered = VaccineAdministered.RIGHT_THIGH;
        VaccineType vaccineType = VaccineType.COMIRNATY;
        String lot = "AF2541CB";
        boolean nextVaccination = true;
        LocalDateTime nextVaccinationDate = LocalDateTime.of(2021, 11, 24, 16, 50);

        VaccinatedDTO result = template.postForObject("/api/vaccinated/patient/" + patientId,
                new CreateVaccinatedCommand(numberSeriesDoses, overallNumberDoses, dateOfVaccination, administered, vaccineType, lot, nextVaccination, nextVaccinationDate),
                VaccinatedDTO.class);

        assertEquals(numberSeriesDoses, result.getNumberSeriesDoses());
        assertEquals(overallNumberDoses, result.getOverallNumberDoses());
        assertEquals(dateOfVaccination, result.getDateOfVaccination());
        assertEquals(administered, result.getAdministered());
        assertEquals(vaccineType, result.getVaccineType());
        assertEquals(lot, result.getLot());

        assertEquals(nextVaccinationDate, result.getPatient().getVaccinationPointEvent().getOccasion());

        assertEquals(dateOfVaccination, result.getPatient().getLastVaccinationDate());
        assertEquals(numberSeriesDoses, result.getPatient().getDoses());
        assertEquals(vaccineType, result.getPatient().getVaccineType());
    }

    @Test
    void update() {
        int numberSeriesDoses = 1;
        int overallNumberDoses = 2;
        LocalDateTime dateOfVaccination = LocalDateTime.of(2020, 8, 24, 16, 50);
        LocalDateTime dateOfVaccination2 = LocalDateTime.of(2020, 8, 24, 16, 55);
        VaccineAdministered administered = VaccineAdministered.RIGHT_THIGH;
        VaccineAdministered administered2 = VaccineAdministered.LEFT_ARM;
        VaccineType vaccineType = VaccineType.SPUTNIK_V;
        VaccineType vaccineType2 = VaccineType.SPUTNIK_V;
        String lot = "AF2541CB";
        String lot2 = "HG76Se45";

        boolean nextVaccination = true;
        LocalDateTime nextVaccinationDate = LocalDateTime.of(2021, 11, 24, 16, 50);
        LocalDateTime nextVaccinationDate2 = LocalDateTime.of(2021, 11, 24, 16, 55);

        long id = template.postForObject("/api/vaccinated/patient/" + patientId,
                new CreateVaccinatedCommand(numberSeriesDoses, overallNumberDoses, dateOfVaccination, administered, vaccineType, lot, nextVaccination, nextVaccinationDate),
                VaccinatedDTO.class).getId();
        template.put("/api/vaccinated/" + id,
                new UpdateVaccinatedCommand(numberSeriesDoses, overallNumberDoses, dateOfVaccination2, administered2, vaccineType2, lot2, nextVaccination, nextVaccinationDate2));

        VaccinatedDTO result = template.exchange(
                "/api/vaccinated/" + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<VaccinatedDTO>() {
                }).getBody();

        assertEquals(numberSeriesDoses, result.getNumberSeriesDoses());
        assertEquals(overallNumberDoses, result.getOverallNumberDoses());
        assertEquals(dateOfVaccination2, result.getDateOfVaccination());
        assertEquals(administered2, result.getAdministered());
        assertEquals(vaccineType2, result.getVaccineType());
        assertEquals(lot2, result.getLot());

        assertEquals(nextVaccinationDate2, result.getPatient().getVaccinationPointEvent().getOccasion());

        assertEquals(dateOfVaccination2, result.getPatient().getLastVaccinationDate());
        assertEquals(numberSeriesDoses, result.getPatient().getDoses());
        assertEquals(vaccineType2, result.getPatient().getVaccineType());
    }

    @Test
    void delete() {
        int numberSeriesDoses = 1;
        int numberSeriesDoses2 = 2;
        int overallNumberDoses = 2;
        LocalDateTime dateOfVaccination = LocalDateTime.of(2020, 8, 24, 16, 50);
        LocalDateTime dateOfVaccination2 = LocalDateTime.of(2020, 8, 24, 16, 55);
        VaccineAdministered administered = VaccineAdministered.RIGHT_ARM;
        VaccineAdministered administered2 = VaccineAdministered.RIGHT_ARM;
        VaccineType vaccineType = VaccineType.MODERNA;
        VaccineType vaccineType2 = VaccineType.MODERNA;
        String lot = "df34gf";
        String lot2 = "dgf345df";
        boolean nextVaccination = true;
        boolean nextVaccination2 = false;
        LocalDateTime nextVaccinationDate = LocalDateTime.of(2021, 11, 24, 16, 50);
        LocalDateTime nextVaccinationDate2 = LocalDateTime.of(2021, 11, 24, 16, 55);

        long deleteId = template.postForObject("/api/vaccinated/patient/" + patientId,
                new CreateVaccinatedCommand(numberSeriesDoses, overallNumberDoses, dateOfVaccination, administered, vaccineType, lot, nextVaccination, nextVaccinationDate),
                VaccinatedDTO.class).getId();
        long testId = template.postForObject("/api/vaccinated/patient/" + patientId,
                new CreateVaccinatedCommand(numberSeriesDoses2, overallNumberDoses, dateOfVaccination2, administered2, vaccineType2, lot2, nextVaccination2, nextVaccinationDate2),
                VaccinatedDTO.class).getId();

        template.exchange(
                "/api/vaccinated/" + deleteId,
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<VaccinatedDTO>() {
                }).getBody();

        List<VaccinatedDTO> result = template.exchange(
                "/api/vaccinated/all/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<VaccinatedDTO>>() {
                }).getBody();

        assertEquals(1, result.size());
        assertEquals(testId, result.get(0).getId());
    }

    @Test
    void deleteAll() {
        int numberSeriesDoses = 1;
        int numberSeriesDoses2 = 2;
        int overallNumberDoses = 2;
        LocalDateTime dateOfVaccination = LocalDateTime.of(2020, 8, 24, 16, 50);
        LocalDateTime dateOfVaccination2 = LocalDateTime.of(2020, 8, 24, 16, 55);
        VaccineAdministered administered = VaccineAdministered.RIGHT_ARM;
        VaccineAdministered administered2 = VaccineAdministered.RIGHT_ARM;
        VaccineType vaccineType = VaccineType.MODERNA;
        VaccineType vaccineType2 = VaccineType.MODERNA;
        String lot = "df34gf";
        String lot2 = "dgf345df";
        boolean nextVaccination = true;
        boolean nextVaccination2 = false;
        LocalDateTime nextVaccinationDate = LocalDateTime.of(2021, 11, 24, 16, 50);
        LocalDateTime nextVaccinationDate2 = LocalDateTime.of(2021, 11, 24, 16, 55);

        template.postForObject("/api/vaccinated/patient/" + patientId,
                new CreateVaccinatedCommand(numberSeriesDoses, overallNumberDoses, dateOfVaccination, administered, vaccineType, lot, nextVaccination, nextVaccinationDate),
                VaccinatedDTO.class);

        template.postForObject("/api/vaccinated/patient/" + patientId,
                new CreateVaccinatedCommand(numberSeriesDoses2, overallNumberDoses, dateOfVaccination2, administered2, vaccineType2, lot2, nextVaccination2, nextVaccinationDate2),
                VaccinatedDTO.class);

        template.exchange(
                "/api/vaccinated/delete/all",
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<VaccinatedDTO>() {
                }).getBody();

        List<VaccinatedDTO> result = template.exchange(
                "/api/vaccinated/all/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<VaccinatedDTO>>() {
                }).getBody();

        assertEquals(0, result.size());
    }

    @Test
    void notFoundVaccinatedByIdTest() {
        Problem result = template.getForObject("/api/vaccinated/1", Problem.class);

        assertEquals(URI.create("vaccinateds/not-found"), result.getType());
        assertEquals(Status.NOT_FOUND, result.getStatus());
    }
}
