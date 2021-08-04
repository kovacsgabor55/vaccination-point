package hu.nive.ujratervezes.vaccinationpoint.controller;

import hu.nive.ujratervezes.vaccinationpoint.pojo.command.CreateVaccinationPointCommand;
import hu.nive.ujratervezes.vaccinationpoint.pojo.command.UpdateVaccinationPointCommand;
import hu.nive.ujratervezes.vaccinationpoint.pojo.dto.VaccinationPointDTO;
import hu.nive.ujratervezes.vaccinationpoint.service.VaccinationPointService;
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
@RequestMapping("/api/vaccinationpoint/")
@Tag(name = "Operations on vaccination point")
public class VaccinationPointController {

    private final VaccinationPointService service;

    @GetMapping("all")
    @Operation(summary = "list all vaccination points")
    @ApiResponse(responseCode = "200", description = "vaccination points has been listed")
    public List<VaccinationPointDTO> get() {
        return service.listAll();
    }

    @GetMapping("{id}")
    @Operation(summary = "find an vaccination point by id")
    @ApiResponse(responseCode = "200", description = "vaccination point has been found")
    public VaccinationPointDTO findById(@PathVariable("id") long id) {
        return service.findById(id);
    }

    //TODO validation
    @PostMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "creates an vaccination point by patient id")
    @ApiResponse(responseCode = "201", description = "vaccination point has been created")
    public VaccinationPointDTO create(@PathVariable("id") long id, @Valid @RequestBody CreateVaccinationPointCommand command) {
        return service.save(id,command);
    }

    //TODO validation
    @PutMapping("{id}")
    @Operation(summary = "modify an vaccination point")
    @ApiResponse(responseCode = "200", description = "vaccination point has been modified")
    public VaccinationPointDTO update(@PathVariable("id") int id, @Valid @RequestBody UpdateVaccinationPointCommand command) {
        return service.updateById(id, command);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "delete an vaccination point")
    @ApiResponse(responseCode = "200", description = "vaccination point has been deleted")
    public void delete(@PathVariable("id") long id) {
        service.deleteById(id);
    }

    @DeleteMapping("deleteall")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "delete all vaccination points")
    @ApiResponse(responseCode = "200", description = "vaccination points has been deleted")
    public void deleteAll() {
        service.deleteAll();
    }
}
