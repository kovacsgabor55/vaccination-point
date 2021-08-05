package hu.nive.ujratervezes.vaccinationpoint.patient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql(statements = {
        "delete from vaccinateds",
        "delete from vaccination_point_events",
        "delete from patients"})
class PatientServiceIT {

    @Autowired
    private PatientService service;

    @Test
    void save() {
        String taj = "123456788";
        String name = "John Doe";
        LocalDate dob = LocalDate.of(1957, 12, 24);
        String email = "johndoe@example.com";
        CreatePatientCommand command = new CreatePatientCommand(taj, name, dob, email);

        PatientDTO result = service.save(command);

        assertEquals(taj, result.getTaj());
        assertEquals(name, result.getName());
        assertEquals(dob, result.getDateOfBirth());
        assertEquals(email, result.getEmail());
    }

    @Test
    void listAll() {
        String taj = "123456788";
        String taj2 = "037687210";
        String name = "John Doe";
        String name2 = "Jane Doe";
        LocalDate dob = LocalDate.of(1957, 12, 24);
        LocalDate dob2 = LocalDate.of(1985, 10, 18);
        String email = "johndoe@example.com";
        String email2 = "janedoe@example.com";
        CreatePatientCommand command = new CreatePatientCommand(taj, name, dob, email);
        CreatePatientCommand command2 = new CreatePatientCommand(taj2, name2, dob2, email2);

        service.save(command);
        service.save(command2);
        List<PatientDTO> result = service.listAll();

        assertEquals(2, result.size());
        assertEquals(taj, result.get(0).getTaj());
        assertEquals(taj2, result.get(1).getTaj());

    }

    @Test
    void findById() {
        String taj = "123456788";
        String name = "John Doe";
        LocalDate dob = LocalDate.of(1957, 12, 24);
        String email = "johndoe@example.com";
        CreatePatientCommand command = new CreatePatientCommand(taj, name, dob, email);

        long id = service.save(command).getId();
        PatientDTO result = service.findById(id);

        assertEquals(name, result.getName());
        assertEquals(id, result.getId());
    }

    @Test
    void updateById() {
        String taj = "123456788";
        String name = "John Doe";
        LocalDate dob = LocalDate.of(1957, 12, 24);
        String email = "johndoe@example.com";
        CreatePatientCommand createCommand = new CreatePatientCommand(taj, name, dob, email);
        String tajMod = "037687210";
        String nameMod = "Jane Doe";
        LocalDate dobMod = LocalDate.of(1985, 10, 18);
        String emailMod = "janedode@example.com";
        UpdatePatientCommand updateCommand = new UpdatePatientCommand(tajMod, nameMod, dobMod, emailMod);

        long id = service.save(createCommand).getId();
        PatientDTO result = service.updateById(id, updateCommand);

        assertEquals(tajMod, result.getTaj());
        assertEquals(nameMod, result.getName());
        assertEquals(dobMod, result.getDateOfBirth());
        assertEquals(emailMod, result.getEmail());
    }

    @Test
    void deleteById() {
        String taj = "123456788";
        String taj2 = "037687210";
        String name = "John Doe";
        LocalDate dob = LocalDate.of(1957, 12, 24);
        String email = "johndoe@example.com";
        CreatePatientCommand command = new CreatePatientCommand(taj, name, dob, email);
        CreatePatientCommand command2 = new CreatePatientCommand(taj2, name, dob, email);

        long id = service.save(command).getId();
        service.save(command2);
        service.deleteById(id);

        assertEquals(1, service.listAll().size());
        assertEquals(taj2, service.listAll().get(0).getTaj());
    }

    @Test
    void deleteAll() {
        String taj = "123456788";
        String taj2 = "037687210";
        String name = "John Doe";
        LocalDate dob = LocalDate.of(1957, 12, 24);
        String email = "johndoe@example.com";
        CreatePatientCommand command = new CreatePatientCommand(taj, name, dob, email);
        CreatePatientCommand command2 = new CreatePatientCommand(taj2, name, dob, email);

        service.save(command);
        service.save(command2);
        service.deleteAll();

        assertEquals(0, service.listAll().size());
    }
}
