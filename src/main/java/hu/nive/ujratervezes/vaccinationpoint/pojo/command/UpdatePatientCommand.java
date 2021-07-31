package hu.nive.ujratervezes.vaccinationpoint.pojo.command;

import hu.nive.ujratervezes.vaccinationpoint.validation.DOB;
import hu.nive.ujratervezes.vaccinationpoint.validation.EMail;
import hu.nive.ujratervezes.vaccinationpoint.validation.MedicalRecord;
import hu.nive.ujratervezes.vaccinationpoint.validation.Name;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
