package hu.nive.ujratervezes.vaccinationpoint.vaccinationpointevent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VaccinationPointEventRepository extends JpaRepository<VaccinationPointEvent, Long> {

    @Override
    @Modifying
    @Query("DELETE FROM VaccinationPointEvent v WHERE v.id =:id")
    void deleteById(@Param("id") Long id);


    @Override
    @Modifying
    @Query("DELETE FROM VaccinationPointEvent")
    void deleteAll();
}
