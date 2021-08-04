package hu.nive.ujratervezes.vaccinationpoint.vaccinated;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VaccinatedRepository extends JpaRepository<Vaccinated, Long> {
}
