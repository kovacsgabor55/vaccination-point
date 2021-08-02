package hu.nive.ujratervezes.vaccinationpoint.service;

import hu.nive.ujratervezes.vaccinationpoint.VaccineAdministered;
import hu.nive.ujratervezes.vaccinationpoint.VaccineType;
import hu.nive.ujratervezes.vaccinationpoint.pojo.command.CreatePatientCommand;
import hu.nive.ujratervezes.vaccinationpoint.pojo.command.CreateVaccinatedCommand;
import hu.nive.ujratervezes.vaccinationpoint.pojo.command.CreateVaccinationPointCommand;
import hu.nive.ujratervezes.vaccinationpoint.pojo.dto.VaccinatedDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class VaccinatedServiceTest {

    @Autowired
    private PatientService patientService;

    @Autowired
    private VaccinationPointService vaccinationPointService;

    @Autowired
    private VaccinatedService service;

    private long patientId = 0;

    @BeforeEach
    void setUp() {
        patientService.deleteAll();
        vaccinationPointService.deleteAll();
        String taj = "123456788";
        String name = "john Doe";
        LocalDate dob = LocalDate.of(1957, 12, 24);
        String email = "johndoe@example.com";

        CreatePatientCommand createPatientCommand = new CreatePatientCommand(taj, name, dob, email);

        patientId = patientService.save(createPatientCommand).getId();

        LocalDateTime occasion = LocalDateTime.of(2021, 10, 12, 14, 50);
        String address = "Miskolc Megyei Kórház 2. oltópont";
        VaccineType vaccineType = VaccineType.COMIRNATY;
        CreateVaccinationPointCommand createVaccinationPointCommand = new CreateVaccinationPointCommand(occasion, address, vaccineType);

        vaccinationPointService.save(patientId, createVaccinationPointCommand).getId();

        service.deleteAll();
    }

    @Test
    void save() {
        int numberSeriesDoses = 1;
        int overallNumberDoses = 2;
        LocalDateTime dateOfVaccination = LocalDateTime.of(2020, 8, 24, 16, 50);
        VaccineAdministered administered = VaccineAdministered.RIGHT_THIGH;
        VaccineType vaccineType = VaccineType.COMIRNATY;
        String lot = "AF2541CB";
        boolean nextVaccination = true;
        LocalDateTime nextVaccinationDate = LocalDateTime.of(2021, 11, 24, 16, 50);
        CreateVaccinatedCommand command = new CreateVaccinatedCommand(numberSeriesDoses, overallNumberDoses, dateOfVaccination, administered, vaccineType, lot, nextVaccination, nextVaccinationDate);

        VaccinatedDto result = service.save(patientId, command);

        assertEquals(numberSeriesDoses, result.getNumberSeriesDoses());
        assertEquals(overallNumberDoses, result.getOverallNumberDoses());
        assertEquals(dateOfVaccination, result.getDateOfVaccination());
        assertEquals(administered, result.getAdministered());
        assertEquals(vaccineType, result.getVaccineType());
        assertEquals(lot, result.getLot());

        assertEquals(nextVaccinationDate, result.getPatient().getVaccinationPoint().getOccasion());

        assertEquals(dateOfVaccination, result.getPatient().getLastVaccinationDate());
        assertEquals(numberSeriesDoses, result.getPatient().getDoses());
        assertEquals(vaccineType, result.getPatient().getVaccineType());
    }

    @Test
    void saveLast() {
        int numberSeriesDoses = 1;
        int overallNumberDoses = 1;
        LocalDateTime dateOfVaccination = LocalDateTime.of(2020, 8, 24, 16, 50);
        VaccineAdministered administered = VaccineAdministered.RIGHT_THIGH;
        VaccineType vaccineType = VaccineType.JASSEN;
        String lot = "ZHG433DS";
        boolean nextVaccination = false;
        LocalDateTime nextVaccinationDate = null;
        CreateVaccinatedCommand command = new CreateVaccinatedCommand(numberSeriesDoses, overallNumberDoses, dateOfVaccination, administered, vaccineType, lot, nextVaccination, nextVaccinationDate);

        VaccinatedDto result = service.save(patientId, command);

        assertEquals(numberSeriesDoses, result.getNumberSeriesDoses());
        assertEquals(overallNumberDoses, result.getOverallNumberDoses());
        assertEquals(dateOfVaccination, result.getDateOfVaccination());
        assertEquals(administered, result.getAdministered());
        assertEquals(vaccineType, result.getVaccineType());
        assertEquals(lot, result.getLot());

        assertEquals(null, result.getPatient().getVaccinationPoint());

        assertEquals(dateOfVaccination, result.getPatient().getLastVaccinationDate());
        assertEquals(numberSeriesDoses, result.getPatient().getDoses());
        assertEquals(vaccineType, result.getPatient().getVaccineType());
    }
}
