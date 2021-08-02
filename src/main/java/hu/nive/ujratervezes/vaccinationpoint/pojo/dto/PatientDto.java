package hu.nive.ujratervezes.vaccinationpoint.pojo.dto;

import hu.nive.ujratervezes.vaccinationpoint.VaccineType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {

    @Schema(description = "unique identifier of the patient", example = "1")
    private Long id;

    @Schema(description = "medical number of the patient", example = "123456788")
    private String taj;

    @Schema(description = "name of the patient", example = "John Doe")
    private String name;

    @Schema(description = "date of birth of the patient", example = "1957-12-24")
    private LocalDate dateOfBirth;

    @Schema(description = "E-mail of the patient", example = "johndoe@example.com")
    private String email;

    private VaccinationPointDto vaccinationPoint;

    private List<VaccinatedDto> vaccinateds = new ArrayList<>();

    @Schema(description = "last vaccination date of the patient")
    private LocalDateTime lastVaccinationDate;

    @Schema(description = "number of doses vaccinated of the patient", example = "1")
    private int doses;

    @Schema(description = "vaccination type of the patient", example = "COMIRNATY")
    private VaccineType vaccineType;
}
