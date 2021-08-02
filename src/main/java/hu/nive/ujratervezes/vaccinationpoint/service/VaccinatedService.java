package hu.nive.ujratervezes.vaccinationpoint.service;

import hu.nive.ujratervezes.vaccinationpoint.entity.Patient;
import hu.nive.ujratervezes.vaccinationpoint.entity.Vaccinated;
import hu.nive.ujratervezes.vaccinationpoint.entity.VaccinationPoint;
import hu.nive.ujratervezes.vaccinationpoint.errorandling.PatientNotFoundException;
import hu.nive.ujratervezes.vaccinationpoint.pojo.command.CreateVaccinatedCommand;
import hu.nive.ujratervezes.vaccinationpoint.pojo.dto.VaccinatedDto;
import hu.nive.ujratervezes.vaccinationpoint.repository.PatientRepository;
import hu.nive.ujratervezes.vaccinationpoint.repository.VaccinatedRepository;
import hu.nive.ujratervezes.vaccinationpoint.repository.VaccinationPointRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class VaccinatedService {

    private final ModelMapper modelMapper;

    private final PatientRepository patientRepository;

    private final VaccinationPointRepository vaccinationPointRepository;

    private final VaccinatedRepository repository;

    //TODO exception
    @Transactional
    public VaccinatedDto save(long patientId, CreateVaccinatedCommand command) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new PatientNotFoundException(patientId));
        long vaccinationPointId = patient.getVaccinationPoint().getId();
        VaccinationPoint vaccinationPoint = vaccinationPointRepository.findById(vaccinationPointId).orElseThrow(() -> new PatientNotFoundException(vaccinationPointId));

        Vaccinated newVaccinated = new Vaccinated(patient, command.getNumberSeriesDoses(), command.getOverallNumberDoses(), command.getDateOfVaccination(), command.getAdministered(), command.getVaccineType(), command.getLot());
        repository.save(newVaccinated);

        patient.setLastVaccinationDate(command.getDateOfVaccination());
        patient.setDoses(command.getNumberSeriesDoses());
        patient.setVaccineType(command.getVaccineType());
        if (command.isNextVaccination()) {
            vaccinationPoint.setOccasion(command.getNextVaccinationDate());

        } else {
            patient.setVaccinationPoint(null);
            vaccinationPointRepository.deleteById(vaccinationPointId);
        }
        return modelMapper.map(newVaccinated, VaccinatedDto.class);
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
