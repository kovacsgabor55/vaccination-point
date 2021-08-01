package hu.nive.ujratervezes.vaccinationpoint.entity;

import hu.nive.ujratervezes.vaccinationpoint.VaccineType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
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

    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL)
    private VaccinationPoint vaccinationPoint;

    @OneToMany(mappedBy = "patient", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Vaccinated> vaccinateds = new ArrayList<>();

    @Column(name = "last_vaccination_date")
    private LocalDateTime lastVaccinationDate;

    private int doses;

    @Column(name = "vaccine_type", length = 20)
    @Enumerated(EnumType.STRING)
    private VaccineType vaccineType;
}
