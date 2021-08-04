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
    @Operation(summary = "list all vaccinateds")
    @ApiResponse(responseCode = "200", description = "vaccinateds has been listed")
    public List<VaccinatedDTO> get() {
        return service.listAll();
    }

    @GetMapping("{id}")
    @Operation(summary = "find an vaccinated by id")
    @ApiResponse(responseCode = "200", description = "vaccinated has been found")
    public VaccinatedDTO findById(@PathVariable("id") long id) {
        return service.findById(id);
    }

    @PostMapping("{patientId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "creates an vaccinated")
    @ApiResponse(responseCode = "201", description = "vaccinated has been created")
    public VaccinatedDTO create(@PathVariable("patientId") long patientId, @Valid @RequestBody CreateVaccinatedCommand command) {
        return service.save(patientId, command);
    }

    @PutMapping("{id}")
    @Operation(summary = "modify an vaccinated")
    @ApiResponse(responseCode = "200", description = "vaccinated has been modified")
    public VaccinatedDTO update(@PathVariable("id") int id, @Valid @RequestBody UpdateVaccinatedCommand command) {
        return service.updateById(id, command);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "delete an vaccinated")
    @ApiResponse(responseCode = "200", description = "vaccinated has been deleted")
    public void delete(@PathVariable("id") long id) {
        service.deleteById(id);
    }

    @DeleteMapping("deleteall")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "delete all vaccinateds")
    @ApiResponse(responseCode = "200", description = "vaccinateds has been deleted")
    public void deleteAll() {
        service.deleteAll();
    }
}
