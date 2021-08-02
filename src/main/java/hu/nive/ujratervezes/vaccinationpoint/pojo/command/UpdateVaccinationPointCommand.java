package hu.nive.ujratervezes.vaccinationpoint.pojo.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateVaccinationPointCommand {

    @Schema(description = "date of the reservation occasion vaccination", example = "2021-11-12 14:50")
    private LocalDateTime occasion;
}
