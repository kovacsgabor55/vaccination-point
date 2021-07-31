package hu.nive.ujratervezes.vaccinationpoint.pojo.dto;

import hu.nive.ujratervezes.vaccinationpoint.Vaccine_type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {
    private Long id;
    private String taj;
    private String name;
    private LocalDate dateOfBirth;
    private String eMail;
    //TODO connection the vaccination dto
    private LocalDateTime lastVaccinationDate;
    private int doses;
    private Vaccine_type vaccineType;
}
