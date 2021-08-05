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
@RequestMapping("/api/patient/")
@Tag(name = "Operations on patients")
public class PatientController {

    private final PatientService service;

    @GetMapping("all")
    @Operation(summary = "list all patients")
    @ApiResponse(responseCode = "200", description = "patients has been listed")
    public List<PatientDTO> listAll() {
        return service.listAll();
    }

    @GetMapping("{id}")
    @Operation(summary = "find an patient by id")
    @ApiResponse(responseCode = "200", description = "patient has been found")
    public PatientDTO findById(@PathVariable("id") long id) {
        return service.findById(id);
    }

    @GetMapping("taj/{taj}")
    @Operation(summary = "find an patient by taj")
    @ApiResponse(responseCode = "200", description = "patient has been found")
    public PatientDTO findById(@PathVariable("taj") String taj) {
        return service.findByTaj(taj);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "creates an patient")
    @ApiResponse(responseCode = "201", description = "patient has been created")
    public PatientDTO create(@Valid @RequestBody CreatePatientCommand command) {
        return service.create(command);
    }

    @PutMapping("{id}")
    @Operation(summary = "modify an patient")
    @ApiResponse(responseCode = "200", description = "patient has been modified")
    public PatientDTO updateById(@PathVariable("id") int id, @Valid @RequestBody UpdatePatientCommand command) {
        return service.updateById(id, command);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "delete an patient")
    @ApiResponse(responseCode = "200", description = "patient has been deleted")
    public void deleteById(@PathVariable("id") long id) {
        service.deleteById(id);
    }

    @DeleteMapping("delete/all")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "delete all patients")
    @ApiResponse(responseCode = "200", description = "patients has been deleted")
    public void deleteAll() {
        service.deleteAll();
    }
}
