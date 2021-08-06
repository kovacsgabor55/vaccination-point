package hu.nive.ujratervezes.vaccinationpoint.vaccinated;

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
@RequestMapping("/api/vaccinated/")
@Tag(name = "Operations on vaccinateds")
public class VaccinatedController {

    private final VaccinatedService service;

    @GetMapping("all")
    @Operation(summary = "Lists all vaccinateds")
    @ApiResponse(responseCode = "200", description = "Vaccinateds has been listed")
    @ApiResponse(responseCode = "400", description = "Bad request, Vaccinateds cannot be listed")
    public List<VaccinatedDTO> listAll() {
        return service.listAll();
    }

    @GetMapping("{id}")
    @Operation(summary = "Finds a exact vaccinated by id")
    @ApiResponse(responseCode = "200", description = "Vaccinated has been found")
    @ApiResponse(responseCode = "400", description = "Bad request, Vaccinated cannot be found")
    @ApiResponse(responseCode = "404", description = "Vaccinated has not been found")
    public VaccinatedDTO findById(@PathVariable("id") long id) {
        return service.findById(id);
    }

    @PostMapping("patient/{patientId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates an vaccinated by patients id")
    @ApiResponse(responseCode = "201", description = "Vaccinated has been created")
    @ApiResponse(responseCode = "400", description = "Bad request, vaccinated cannot be created")
    public VaccinatedDTO create(@PathVariable("patientId") long patientId, @Valid @RequestBody CreateVaccinatedCommand command) {
        return service.create(patientId, command);
    }

    @PutMapping("{id}")
    @Operation(summary = "Updates an exact vaccinated by id")
    @ApiResponse(responseCode = "200", description = "Vaccinated has been updated")
    @ApiResponse(responseCode = "400", description = "Bad request, vaccinated cannot be updated")
    public VaccinatedDTO updateById(@PathVariable("id") int id, @Valid @RequestBody UpdateVaccinatedCommand command) {
        return service.updateById(id, command);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletes an exact vaccinated by id")
    @ApiResponse(responseCode = "204", description = "Vaccinated has been deleted")
    @ApiResponse(responseCode = "400", description = "Bad request, vaccinated cannot be deleted")
    public void deleteById(@PathVariable("id") long id) {
        service.deleteById(id);
    }

    @DeleteMapping("delete/all")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletes all vaccinateds")
    @ApiResponse(responseCode = "204", description = "Vaccinateds has been deleted")
    @ApiResponse(responseCode = "400", description = "Bad request, vaccinateds cannot be deleted")
    public void deleteAll() {
        service.deleteAll();
    }
}
