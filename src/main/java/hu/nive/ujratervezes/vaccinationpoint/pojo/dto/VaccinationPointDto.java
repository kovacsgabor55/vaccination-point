package hu.nive.ujratervezes.vaccinationpoint.pojo.dto;

import hu.nive.ujratervezes.vaccinationpoint.VaccineType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VaccinationPointDto {

    @Schema(description = "unique identifier of the vaccination", example = "1")
    private Long id;

    @Schema(description = "unique identifier of the patient", example = "1")
    private long patientID;

    @Schema(description = "date of the reservation occasion vaccination", example = "2021-10-12 14:50")
    private LocalDateTime occasion;

    @Schema(description = "address of the vaccination", example = "Miskolc Megyei kórház 2. oltópont")
    private String address;

    @Schema(description = "vaccination type of the patient", example = "COMIRNATY")
    private VaccineType vaccineType;
}
