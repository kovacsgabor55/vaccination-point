package hu.nive.ujratervezes.vaccinationpoint.repository;

import hu.nive.ujratervezes.vaccinationpoint.entity.Vaccinated;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VaccinatedRepository extends JpaRepository<Vaccinated, Long> {
}
