package hu.nive.ujratervezes.vaccinationpoint.entity;

import hu.nive.ujratervezes.vaccinationpoint.Vaccine_type;
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
    //@Column(name = "patient_id", nullable = false)
    @OneToOne(mappedBy = "vaccinationPoint")
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
    private Vaccine_type vaccineType;
}
