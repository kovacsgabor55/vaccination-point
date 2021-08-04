package hu.nive.ujratervezes.vaccinationpoint.vaccinationpointevent;

import hu.nive.ujratervezes.vaccinationpoint.VaccineType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateVaccinationPointEventCommand {

    @NotNull
    @Schema(description = "date of the reservation occasion vaccination", example = "2021-10-12 14:50")
    private LocalDateTime occasion;

    @NotBlank
    @NotNull
    @Schema(description = "address of the vaccination", example = "Miskolc Megyei Kórház 2. oltópont")
    private String address;

    @NotNull
    @Schema(description = "vaccination type of the patient", example = "COMIRNATY")
    private VaccineType vaccineType;
}
