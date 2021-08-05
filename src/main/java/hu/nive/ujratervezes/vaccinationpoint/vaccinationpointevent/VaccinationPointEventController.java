package hu.nive.ujratervezes.vaccinationpoint.vaccinationpointevent;

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
@RequestMapping("/api/vaccinationpointevent/")
@Tag(name = "Operations on vaccination point event")
public class VaccinationPointEventController {

    private final VaccinationPointEventService service;

    @GetMapping("all")
    @Operation(summary = "list all vaccination point events")
    @ApiResponse(responseCode = "200", description = "vaccination point events has been listed")
    public List<VaccinationPointEventDTO> listAll() {
        return service.listAll();
    }

    @GetMapping("{id}")
    @Operation(summary = "find an vaccination point event by id")
    @ApiResponse(responseCode = "200", description = "vaccination point event has been found")
    public VaccinationPointEventDTO findById(@PathVariable("id") long id) {
        return service.findById(id);
    }

    //TODO validation
    @PostMapping("patient/{patientId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "creates an vaccination point event by patient id")
    @ApiResponse(responseCode = "201", description = "vaccination point event has been created")
    public VaccinationPointEventDTO create(@PathVariable("patientId") long patientId, @Valid @RequestBody CreateVaccinationPointEventCommand command) {
        return service.create(patientId, command);
    }

    //TODO validation
    @PutMapping("{id}")
    @Operation(summary = "modify an vaccination point event")
    @ApiResponse(responseCode = "200", description = "vaccination point event has been modified")
    public VaccinationPointEventDTO updateById(@PathVariable("id") int id, @Valid @RequestBody UpdateVaccinationPointEventCommand command) {
        return service.updateById(id, command);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "delete an vaccination point event")
    @ApiResponse(responseCode = "200", description = "vaccination point event has been deleted")
    public void deleteById(@PathVariable("id") long id) {
        service.deleteById(id);
    }

    @DeleteMapping("delete/all")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "delete all vaccination point events")
    @ApiResponse(responseCode = "200", description = "vaccination point events has been deleted")
    public void deleteAll() {
        service.deleteAll();
    }
}
