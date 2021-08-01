package hu.nive.ujratervezes.vaccinationpoint.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import hu.nive.ujratervezes.vaccinationpoint.VaccineAdministered;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "vaccinated")
public class Vaccinated {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @NonNull
    @Column(name = "number_series_doses", nullable = false)
    private int numberSeriesDoses;

    @NonNull
    @Column(name = "overall_number_doses", nullable = false)
    private int overallNumberDoses;

    @NonNull
    @Column(name = "date_of_vaccination", nullable = false)
    private LocalDateTime dateOfVaccination;

    @NonNull
    @Column(name = "administered_vaccine", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private VaccineAdministered administered;

    @NonNull
    @Column(name = "lot", nullable = false, length = 15)
    private String lot;
}
