package hu.nive.ujratervezes.vaccinationpoint.patient;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/patients/")
@Tag(name = "Operations on patients")
public class PatientController {

    private final PatientService service;

    @GetMapping
    @Operation(summary = "Lists all patients")
    @ApiResponse(responseCode = "200", description = "Patients has been listed")
    @ApiResponse(responseCode = "400", description = "Bad request, patients cannot be listed")
    public List<PatientDTO> listAll() {
        return service.listAll();
    }

    @GetMapping("{id}")
    @Operation(summary = "Finds an exact patient by id")
    @ApiResponse(responseCode = "200", description = "Patient has been found")
    @ApiResponse(responseCode = "400", description = "Bad request, patient cannot be found")
    @ApiResponse(responseCode = "404", description = "Patient has not been found")
    public PatientDTO findById(@PathVariable("id") long id) {
        return service.findById(id);
    }

    @GetMapping("taj/{taj}")
    @Operation(summary = "Finds an exact patient by taj")
    @ApiResponse(responseCode = "200", description = "Patient has been found")
    @ApiResponse(responseCode = "400", description = "Bad request, patient cannot be found")
    @ApiResponse(responseCode = "404", description = "Patient has not been found")
    public PatientDTO findById(@PathVariable("taj") String taj) {
        return service.findByTaj(taj);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates a patient")
    @ApiResponse(responseCode = "201", description = "Patient has been created")
    @ApiResponse(responseCode = "400", description = "Bad request, patient cannot be created")
    public PatientDTO create(@Valid @RequestBody CreatePatientCommand command) {
        return service.create(command);
    }

    @PutMapping("{id}")
    @Operation(summary = "Updates an exact patient by id")
    @ApiResponse(responseCode = "200", description = "Patient has been updated")
    @ApiResponse(responseCode = "400", description = "Bad request, patient cannot be updated")
    public PatientDTO updateById(@PathVariable("id") int id, @Valid @RequestBody UpdatePatientCommand command) {
        return service.updateById(id, command);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletes an exact patient by id")
    @ApiResponse(responseCode = "204", description = "Patient has been deleted")
    @ApiResponse(responseCode = "400", description = "Bad request, patient cannot be deleted")
    public void deleteById(@PathVariable("id") long id) {
        service.deleteById(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletes all patients")
    @ApiResponse(responseCode = "204", description = "Patients has been deleted")
    @ApiResponse(responseCode = "400", description = "Bad request, patients cannot be deleted")
    public void deleteAll() {
        service.deleteAll();
    }
}
