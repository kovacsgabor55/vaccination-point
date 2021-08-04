package hu.nive.ujratervezes.vaccinationpoint.pojo.dto;

import hu.nive.ujratervezes.vaccinationpoint.VaccineAdministered;
import hu.nive.ujratervezes.vaccinationpoint.VaccineType;
import hu.nive.ujratervezes.vaccinationpoint.entity.Patient;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class VaccinatedDTO {

    @Schema(description = "unique identifier of the vaccinated", example = "1")
    private Long id;

    @Schema(description = "", example = "")
    private Patient patient;

    @Schema(description = "number of vaccination series of the vaccinated", example = "1")
    private int numberSeriesDoses;

    @Schema(description = "overall number of doses of the vaccinated", example = "2")
    private int overallNumberDoses;

    @Schema(description = "date of vaccination of the vaccinated", example = "2021-12-24 16:50")
    private LocalDateTime dateOfVaccination;

    @Schema(description = "administered of the vaccinated", example = "RIGHT_THIGH")
    private VaccineAdministered administered;

    @Schema(description = "vaccination type of the vaccinated", example = "COMIRNATY")
    private VaccineType vaccineType;

    @Schema(description = "lot number of the vaccinated", example = "AF2541CB")
    private String lot;
}