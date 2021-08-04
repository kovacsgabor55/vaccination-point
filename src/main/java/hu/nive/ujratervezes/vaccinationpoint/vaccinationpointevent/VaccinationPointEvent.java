package hu.nive.ujratervezes.vaccinationpoint.vaccinationpointevent;

import com.fasterxml.jackson.annotation.JsonBackReference;
import hu.nive.ujratervezes.vaccinationpoint.VaccineType;
import hu.nive.ujratervezes.vaccinationpoint.patient.Patient;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "vaccination_point_events")
public class VaccinationPointEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id", nullable = false)
    private Patient patient;

    @NonNull
    @Column(name = "occasion", nullable = false)
    private LocalDateTime occasion;

    @NonNull
    @Column(name = "address", nullable = false)
    private String address;

    @NonNull
    @Column(name = "vaccine_type", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private VaccineType vaccineType;
}
