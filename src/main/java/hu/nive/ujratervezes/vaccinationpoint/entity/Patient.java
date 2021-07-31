package hu.nive.ujratervezes.vaccinationpoint.entity;

import hu.nive.ujratervezes.vaccinationpoint.Vaccine_type;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false, unique = true, length = 9)
    private String taj;

    @NonNull
    @Column(nullable = false, length = 50)
    private String name;

    @NonNull
    @Column(nullable = false, name = "date_of_birth")
    private LocalDate dateOfBirth;

    @NonNull
    @Column(name = "e_mail", nullable = false, length = 100)
    private String email;

    //TODO connection the vaccination entity

    @Column(name = "last_vaccination_date")
    private LocalDateTime lastVaccinationDate;

    private int doses;

    @Column(name = "vaccine_type", length = 20)
    @Enumerated(EnumType.STRING)
    private Vaccine_type vaccineType;
}
