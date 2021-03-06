package hu.nive.ujratervezes.vaccinationpoint.patient;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByTaj(String taj);
}
