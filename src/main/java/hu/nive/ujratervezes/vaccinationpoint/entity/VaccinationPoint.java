package hu.nive.ujratervezes.vaccinationpoint.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import hu.nive.ujratervezes.vaccinationpoint.VaccineType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "vaccination_points")
public class VaccinationPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    @NonNull
    @Column(name = "occasion", nullable = false)
    private LocalDateTime occasion;

    @NonNull
    @Column(name = "address", nullable = false)
    private String address;

    @NonNull
    @Column(name = "vaccine_type", length = 20)
    @Enumerated(EnumType.STRING)
    private VaccineType vaccineType;
}
