package hu.nive.ujratervezes.vaccinationpoint.patient;

import hu.nive.ujratervezes.vaccinationpoint.validation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePatientCommand {

    @MedicalRecord
    @Schema(description = "medical number of the patient", example = "123456788")
    private String taj;

    @Name
    @Schema(description = "name of the patient", example = "John Doe")
    private String name;

    @DOB
    @Schema(description = "date of birth of the patient", example = "1957-12-24")
    private LocalDate dateOfBirth;

    @EMail
    @Schema(description = "E-mail of the patient", example = "johndoe@example.com")
    private String email;
}
