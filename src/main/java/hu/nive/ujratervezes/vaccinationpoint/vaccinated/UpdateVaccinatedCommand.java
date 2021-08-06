package hu.nive.ujratervezes.vaccinationpoint.vaccinated;

import hu.nive.ujratervezes.vaccinationpoint.VaccineAdministered;
import hu.nive.ujratervezes.vaccinationpoint.VaccineType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateVaccinatedCommand {

    @Positive
    @Schema(description = "number of vaccination series of the vaccinated", example = "1")
    private int numberSeriesDoses;

    @Positive
    @Schema(description = "overall number of doses of the vaccinated", example = "2")
    private int overallNumberDoses;

    @NotNull
    @Schema(description = "date of vaccination of the vaccinated", example = "2020-08-24T16:55:00")
    private LocalDateTime dateOfVaccination;

    @NotNull
    @Schema(description = "administered of the vaccinated", example = "LEFT_THIGH")
    private VaccineAdministered administered;

    @NotNull
    @Schema(description = "vaccination type of the vaccinated", example = "SPUTNIK_V")
    private VaccineType vaccineType;

    @NotNull
    @NotBlank
    @Schema(description = "lot number of the vaccinated", example = "EShg45dD")
    private String lot;

    @Schema(description = "need next vaccination", example = "true")
    private boolean nextVaccination;

    @NotNull
    @Schema(description = "date of vaccination of the vaccinated", example = "2021-11-24T16:55:00")
    private LocalDateTime nextVaccinationDate;
}
