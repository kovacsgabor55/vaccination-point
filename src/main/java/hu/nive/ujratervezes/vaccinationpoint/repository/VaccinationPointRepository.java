package hu.nive.ujratervezes.vaccinationpoint.repository;

import hu.nive.ujratervezes.vaccinationpoint.entity.VaccinationPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VaccinationPointRepository extends JpaRepository<VaccinationPoint, Long> {
}
