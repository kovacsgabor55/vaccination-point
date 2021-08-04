package hu.nive.ujratervezes.vaccinationpoint.patient;

import hu.nive.ujratervezes.vaccinationpoint.VaccineType;
import hu.nive.ujratervezes.vaccinationpoint.vaccinated.VaccinatedDTO;
import hu.nive.ujratervezes.vaccinationpoint.vaccinationpointevent.VaccinationPointEventDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class PatientDTO {

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

    private VaccinationPointEventDTO vaccinationPoint;

    private List<VaccinatedDTO> vaccinateds = new ArrayList<>();

    @Schema(description = "last vaccination date of the patient")
    private LocalDateTime lastVaccinationDate;

    @Schema(description = "number of doses vaccinated of the patient", example = "1")
    private int doses;

    @Schema(description = "vaccination type of the patient", example = "COMIRNATY")
    private VaccineType vaccineType;
}
