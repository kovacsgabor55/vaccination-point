package hu.nive.ujratervezes.vaccinationpoint.vaccinated;

import hu.nive.ujratervezes.vaccinationpoint.VaccineAdministered;
import hu.nive.ujratervezes.vaccinationpoint.VaccineType;
import hu.nive.ujratervezes.vaccinationpoint.patient.PatientDTO;
import hu.nive.ujratervezes.vaccinationpoint.patient.PatientService;
import hu.nive.ujratervezes.vaccinationpoint.vaccinationpointevent.VaccinationPointEventDTO;
import hu.nive.ujratervezes.vaccinationpoint.vaccinationpointevent.VaccinationPointEventService;
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
        "insert into `patients` (`id`, `date_of_birth`, `doses`, `e_mail`, `last_vaccination_date`, `name`, `taj`, `vaccine_type`) values (1, '1957-12-24', 1, 'johndoe@example.com', null, 'John Doe', '123456788', null)",
        "insert into `vaccination_point_events` (`id`, `address`, `occasion`, `patient_id`, `vaccine_type`) values (1, 'Miskolc Megyei Kórház 2. oltópont', NOW(), 1, 'COMIRNATY')"})
class VaccinatedServiceTest {

    @Autowired
    private VaccinatedService service;

    @Autowired
    private PatientService patientService;

    @Autowired
    private VaccinationPointEventService vaccinationPointEventService;

    private final long patientId = 1;
    private final long vaccinationPointEventId = 1;

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
        CreateVaccinatedCommand command = new CreateVaccinatedCommand(numberSeriesDoses, overallNumberDoses, dateOfVaccination, administered, vaccineType, lot, nextVaccination, nextVaccinationDate);

        VaccinatedDTO result = service.create(patientId, command);
        PatientDTO resultPatient = patientService.findById(patientId);
        VaccinationPointEventDTO resultEvent = vaccinationPointEventService.findById(vaccinationPointEventId);

        assertEquals(numberSeriesDoses, result.getNumberSeriesDoses());
        assertEquals(overallNumberDoses, result.getOverallNumberDoses());
        assertEquals(dateOfVaccination, result.getDateOfVaccination());
        assertEquals(administered, result.getAdministered());
        assertEquals(vaccineType, result.getVaccineType());
        assertEquals(lot, result.getLot());

        assertEquals(nextVaccinationDate, resultEvent.getOccasion());

        assertEquals(dateOfVaccination, resultPatient.getLastVaccinationDate());
        assertEquals(numberSeriesDoses, resultPatient.getDoses());
        assertEquals(vaccineType, resultPatient.getVaccineType());
    }

    @Test
    void saveLast() {
        int numberSeriesDoses = 1;
        int overallNumberDoses = 1;
        LocalDateTime dateOfVaccination = LocalDateTime.of(2020, 8, 24, 16, 50);
        VaccineAdministered administered = VaccineAdministered.RIGHT_THIGH;
        VaccineType vaccineType = VaccineType.JANSSEN;
        String lot = "ZHG433DS";
        boolean nextVaccination = false;
        LocalDateTime nextVaccinationDate = LocalDateTime.of(2021, 11, 24, 16, 50);
        CreateVaccinatedCommand command = new CreateVaccinatedCommand(numberSeriesDoses, overallNumberDoses, dateOfVaccination, administered, vaccineType, lot, nextVaccination, nextVaccinationDate);

        VaccinatedDTO result = service.create(patientId, command);
        PatientDTO resultPatient = patientService.findById(patientId);
        List<VaccinationPointEventDTO> resultEvents = vaccinationPointEventService.listAll();

        assertEquals(numberSeriesDoses, result.getNumberSeriesDoses());
        assertEquals(overallNumberDoses, result.getOverallNumberDoses());
        assertEquals(dateOfVaccination, result.getDateOfVaccination());
        assertEquals(administered, result.getAdministered());
        assertEquals(vaccineType, result.getVaccineType());
        assertEquals(lot, result.getLot());

        assertEquals(0, resultEvents.size());

        assertEquals(dateOfVaccination, resultPatient.getLastVaccinationDate());
        assertEquals(numberSeriesDoses, resultPatient.getDoses());
        assertEquals(vaccineType, resultPatient.getVaccineType());
    }


    @Test
    void updateById() {
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
        CreateVaccinatedCommand createCommand = new CreateVaccinatedCommand(numberSeriesDoses, overallNumberDoses, dateOfVaccination, administered, vaccineType, lot, nextVaccination, nextVaccinationDate);
        UpdateVaccinatedCommand modifyCommand = new UpdateVaccinatedCommand(numberSeriesDoses, overallNumberDoses, dateOfVaccination2, administered2, vaccineType2, lot2, nextVaccination, nextVaccinationDate2);

        long id = service.create(patientId, createCommand).getId();
        VaccinatedDTO result = service.updateById(id, modifyCommand);
        PatientDTO resultPatient = patientService.findById(patientId);
        VaccinationPointEventDTO resultEvent = vaccinationPointEventService.findById(vaccinationPointEventId);

        assertEquals(numberSeriesDoses, result.getNumberSeriesDoses());
        assertEquals(overallNumberDoses, result.getOverallNumberDoses());
        assertEquals(dateOfVaccination2, result.getDateOfVaccination());
        assertEquals(administered2, result.getAdministered());
        assertEquals(vaccineType2, result.getVaccineType());
        assertEquals(lot2, result.getLot());

        assertEquals(nextVaccinationDate2, resultEvent.getOccasion());

        assertEquals(dateOfVaccination2, resultPatient.getLastVaccinationDate());
        assertEquals(numberSeriesDoses, resultPatient.getDoses());
        assertEquals(vaccineType2, resultPatient.getVaccineType());
    }

    @Test
    void updateByIdLast() {
        int numberSeriesDoses = 1;
        int numberSeriesDoses2 = 2;
        int overallNumberDoses = 2;
        LocalDateTime dateOfVaccination = LocalDateTime.of(2020, 8, 24, 16, 50);
        LocalDateTime dateOfVaccination2 = LocalDateTime.of(2020, 8, 24, 16, 55);
        VaccineAdministered administered = VaccineAdministered.RIGHT_THIGH;
        VaccineAdministered administered2 = VaccineAdministered.RIGHT_THIGH;
        VaccineType vaccineType = VaccineType.COMIRNATY;
        VaccineType vaccineType2 = VaccineType.COMIRNATY;
        String lot = "AF2541CB";
        String lot2 = "HG76Se45";

        boolean nextVaccination = false;
        LocalDateTime nextVaccinationDate = LocalDateTime.of(2021, 11, 24, 16, 50);
        LocalDateTime nextVaccinationDate2 = LocalDateTime.of(2021, 11, 24, 16, 55);
        CreateVaccinatedCommand createCommand = new CreateVaccinatedCommand(numberSeriesDoses, overallNumberDoses, dateOfVaccination, administered, vaccineType, lot, nextVaccination, nextVaccinationDate);
        UpdateVaccinatedCommand modifyCommand = new UpdateVaccinatedCommand(numberSeriesDoses2, overallNumberDoses, dateOfVaccination2, administered2, vaccineType2, lot2, nextVaccination, nextVaccinationDate2);

        long id = service.create(patientId, createCommand).getId();
        VaccinatedDTO result = service.updateById(id, modifyCommand);
        PatientDTO resultPatient = patientService.findById(patientId);
        List<VaccinationPointEventDTO> resultEvents = vaccinationPointEventService.listAll();

        assertEquals(numberSeriesDoses2, result.getNumberSeriesDoses());
        assertEquals(overallNumberDoses, result.getOverallNumberDoses());
        assertEquals(dateOfVaccination2, result.getDateOfVaccination());
        assertEquals(administered2, result.getAdministered());
        assertEquals(vaccineType2, result.getVaccineType());
        assertEquals(lot2, result.getLot());

        assertEquals(0, resultEvents.size());

        assertEquals(dateOfVaccination2, resultPatient.getLastVaccinationDate());
        assertEquals(numberSeriesDoses2, resultPatient.getDoses());
        assertEquals(vaccineType2, resultPatient.getVaccineType());
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
        CreateVaccinatedCommand createCommand = new CreateVaccinatedCommand(numberSeriesDoses, overallNumberDoses, dateOfVaccination, administered, vaccineType, lot, nextVaccination, nextVaccinationDate);
        CreateVaccinatedCommand createCommand2 = new CreateVaccinatedCommand(numberSeriesDoses2, overallNumberDoses, dateOfVaccination2, administered2, vaccineType2, lot2, nextVaccination2, nextVaccinationDate2);

        long id = service.create(patientId, createCommand).getId();
        long id2 = service.create(patientId, createCommand2).getId();
        List<VaccinatedDTO> result = service.listAll();

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
        LocalDateTime nextVaccinationDate = null;
        CreateVaccinatedCommand createCommand = new CreateVaccinatedCommand(numberSeriesDoses, overallNumberDoses, dateOfVaccination, administered, vaccineType, lot, nextVaccination, nextVaccinationDate);

        long id = service.create(patientId, createCommand).getId();

        VaccinatedDTO result = service.findById(id);

        assertEquals(id, result.getId());
        assertEquals(vaccineType, result.getVaccineType());
    }

    @Test
    void deleteById() {
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
        CreateVaccinatedCommand createCommand = new CreateVaccinatedCommand(numberSeriesDoses, overallNumberDoses, dateOfVaccination, administered, vaccineType, lot, nextVaccination, nextVaccinationDate);
        CreateVaccinatedCommand createCommand2 = new CreateVaccinatedCommand(numberSeriesDoses2, overallNumberDoses, dateOfVaccination2, administered2, vaccineType2, lot2, nextVaccination2, nextVaccinationDate2);

        long deleteId = service.create(patientId, createCommand).getId();
        long testId = service.create(patientId, createCommand2).getId();

        service.deleteById(deleteId);

        List<VaccinatedDTO> result = service.listAll();

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
        CreateVaccinatedCommand createCommand = new CreateVaccinatedCommand(numberSeriesDoses, overallNumberDoses, dateOfVaccination, administered, vaccineType, lot, nextVaccination, nextVaccinationDate);
        CreateVaccinatedCommand createCommand2 = new CreateVaccinatedCommand(numberSeriesDoses2, overallNumberDoses, dateOfVaccination2, administered2, vaccineType2, lot2, nextVaccination2, nextVaccinationDate2);

        service.create(patientId, createCommand);
        service.create(patientId, createCommand2);

        service.deleteAll();

        assertEquals(0, service.listAll().size());
    }
}
