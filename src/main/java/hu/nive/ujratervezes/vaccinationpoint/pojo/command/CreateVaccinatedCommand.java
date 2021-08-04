package hu.nive.ujratervezes.vaccinationpoint.pojo.command;

import hu.nive.ujratervezes.vaccinationpoint.VaccineAdministered;
import hu.nive.ujratervezes.vaccinationpoint.VaccineType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateVaccinatedCommand {

    @Positive
    @Schema(description = "number of vaccination series of the vaccinated", example = "1")
    private int numberSeriesDoses;

    @Positive
    @Schema(description = "overall number of doses of the vaccinated", example = "2")
    private int overallNumberDoses;

    @Schema(description = "date of vaccination of the vaccinated", example = "2020-08-24 16:50")
    private LocalDateTime dateOfVaccination;

    @NotNull
    @Schema(description = "administered of the vaccinated", example = "RIGHT_THIGH")
    private VaccineAdministered administered;

    @NotNull
    @Schema(description = "vaccination type of the vaccinated", example = "COMIRNATY")
    private VaccineType vaccineType;

    @NotNull
    @NotBlank
    @Schema(description = "lot number of the vaccinated", example = "AF2541CB")
    private String lot;

    @Schema(description = "", example = "true")
    private boolean nextVaccination;

    @NotNull
    @Schema(description = "date of vaccination of the vaccinated", example = "2021-11-24 16:50")
    private LocalDateTime nextVaccinationDate;
}
