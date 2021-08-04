package hu.nive.ujratervezes.vaccinationpoint.patient;

import hu.nive.ujratervezes.vaccinationpoint.validation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePatientCommand {

    @MedicalRecord
    @Schema(description = "medical number of the patient", example = "037687210")
    private String taj;

    @Name
    @Schema(description = "name of the patient", example = "Jane Doe")
    private String name;

    @DOB
    @Schema(description = "date of birth of the patient", example = "1985-10-18")
    private LocalDate dateOfBirth;

    @EMail
    @Schema(description = "E-mail of the patient", example = "janedode@example.com")
    private String email;
}
