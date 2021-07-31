package hu.nive.ujratervezes.vaccinationpoint.pojo.command;

import hu.nive.ujratervezes.vaccinationpoint.validation.DOB;
import hu.nive.ujratervezes.vaccinationpoint.validation.EMail;
import hu.nive.ujratervezes.vaccinationpoint.validation.MedicalRecord;
import hu.nive.ujratervezes.vaccinationpoint.validation.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePatientCommand {
    @MedicalRecord
    private String taj;
    @Name
    private String name;
    @DOB
    private LocalDate dateOfBirth;
    @EMail
    private String eMail;
}
