package hu.nive.ujratervezes.vaccinationpoint.vaccinated;

import com.fasterxml.jackson.annotation.JsonBackReference;
import hu.nive.ujratervezes.vaccinationpoint.VaccineAdministered;
import hu.nive.ujratervezes.vaccinationpoint.VaccineType;
import hu.nive.ujratervezes.vaccinationpoint.patient.Patient;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "vaccinateds")
public class Vaccinated {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @NonNull
    @Column(name = "number_series_doses", nullable = false, columnDefinition = "int(1)")
    private int numberSeriesDoses;

    @NonNull
    @Column(name = "overall_number_doses", nullable = false, columnDefinition = "int(1)")
    private int overallNumberDoses;

    @NonNull
    @Column(name = "date_of_vaccination", nullable = false)
    private LocalDateTime dateOfVaccination;

    @NonNull
    @Column(name = "administered_vaccine", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private VaccineAdministered administered;

    @NonNull
    @Column(name = "vaccine_type", length = 20)
    @Enumerated(EnumType.STRING)
    private VaccineType vaccineType;

    @NonNull
    @Column(name = "lot", nullable = false, length = 15)
    private String lot;
}
