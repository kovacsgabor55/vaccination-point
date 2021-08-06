package hu.nive.ujratervezes.vaccinationpoint.vaccinationpointevent;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateVaccinationPointEventCommand {

    @NotNull
    @Schema(description = "date of the reservation occasion vaccination", example = "2021-11-12T14:50:00")
    private LocalDateTime occasion;
}
