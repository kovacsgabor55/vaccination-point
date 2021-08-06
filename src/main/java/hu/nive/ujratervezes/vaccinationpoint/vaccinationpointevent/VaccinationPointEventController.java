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
@RequestMapping("/api/vaccinationpointevents/")
@Tag(name = "Operations on vaccination point event")
public class VaccinationPointEventController {

    private final VaccinationPointEventService service;

    @GetMapping
    @Operation(summary = "List all vaccination point events")
    @ApiResponse(responseCode = "200", description = "Vaccination point events has been listed")
    @ApiResponse(responseCode = "400", description = "Bad request, vaccination point events cannot be listed")
    public List<VaccinationPointEventDTO> listAll() {
        return service.listAll();
    }

    @GetMapping("{id}")
    @Operation(summary = "Find an exact vaccination point event by id")
    @ApiResponse(responseCode = "200", description = "Vaccination point event has been found")
    @ApiResponse(responseCode = "400", description = "Bad request, vaccination point event cannot be found")
    @ApiResponse(responseCode = "404", description = "Vaccination point event has not been found")
    public VaccinationPointEventDTO findById(@PathVariable("id") long id) {
        return service.findById(id);
    }

    @PostMapping("patients/{patientId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates an vaccination point event by patient id")
    @ApiResponse(responseCode = "201", description = "Vaccination point event has been created")
    @ApiResponse(responseCode = "400", description = "Bad request, vaccination point event cannot be created")
    public VaccinationPointEventDTO create(@PathVariable("patientId") long patientId, @Valid @RequestBody CreateVaccinationPointEventCommand command) {
        return service.create(patientId, command);
    }

    @PutMapping("{id}")
    @Operation(summary = "Updates an exact vaccination point event by id")
    @ApiResponse(responseCode = "200", description = "Vaccination point event has been updated")
    @ApiResponse(responseCode = "200", description = "Vaccination point event has been updated")
    @ApiResponse(responseCode = "400", description = "Bad request, vaccination point event cannot be updated")
    public VaccinationPointEventDTO updateById(@PathVariable("id") int id, @Valid @RequestBody UpdateVaccinationPointEventCommand command) {
        return service.updateById(id, command);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletes an exact vaccination point event by id")
    @ApiResponse(responseCode = "204", description = "Vaccination point event has been deleted")
    @ApiResponse(responseCode = "400", description = "Bad request, vaccination point event cannot be deleted")
    public void deleteById(@PathVariable("id") long id) {
        service.deleteById(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletes all vaccination point events")
    @ApiResponse(responseCode = "204", description = "Vaccination point events has been deleted")
    @ApiResponse(responseCode = "400", description = "Bad request, vaccination point events cannot be deleted")
    public void deleteAll() {
        service.deleteAll();
    }
}
