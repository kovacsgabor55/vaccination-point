package hu.nive.ujratervezes.vaccinationpoint.vaccinated;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VaccinatedRepository extends JpaRepository<Vaccinated, Long> {

    @Override
    @Modifying
    @Query("DELETE FROM Vaccinated v WHERE v.id =:id")
    void deleteById(@Param("id") Long id);


    @Override
    @Modifying
    @Query("DELETE FROM Vaccinated")
    void deleteAll();
}
