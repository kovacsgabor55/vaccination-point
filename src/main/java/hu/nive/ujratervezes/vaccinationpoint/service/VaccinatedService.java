package hu.nive.ujratervezes.vaccinationpoint.service;

import hu.nive.ujratervezes.vaccinationpoint.entity.Patient;
import hu.nive.ujratervezes.vaccinationpoint.entity.Vaccinated;
import hu.nive.ujratervezes.vaccinationpoint.entity.VaccinationPoint;
import hu.nive.ujratervezes.vaccinationpoint.errorhandling.PatientNotFoundException;
import hu.nive.ujratervezes.vaccinationpoint.pojo.command.CreateVaccinatedCommand;
import hu.nive.ujratervezes.vaccinationpoint.pojo.command.UpdateVaccinatedCommand;
import hu.nive.ujratervezes.vaccinationpoint.pojo.dto.VaccinatedDTO;
import hu.nive.ujratervezes.vaccinationpoint.repository.PatientRepository;
import hu.nive.ujratervezes.vaccinationpoint.repository.VaccinatedRepository;
import hu.nive.ujratervezes.vaccinationpoint.repository.VaccinationPointRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VaccinatedService {

    private final ModelMapper modelMapper;

    private final PatientRepository patientRepository;

    private final VaccinationPointRepository vaccinationPointRepository;

    private final VaccinatedRepository repository;

    //TODO exception
    @Transactional
    public VaccinatedDTO save(long patientId, CreateVaccinatedCommand command) {
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
        return modelMapper.map(newVaccinated, VaccinatedDTO.class);
    }

    //TODO exception
    @Transactional
    public VaccinatedDTO updateById(long id, UpdateVaccinatedCommand command) {

        Vaccinated vaccinated = repository.findById(id).orElseThrow(() -> new PatientNotFoundException(id));
        Patient patient = vaccinated.getPatient();

        patient.setLastVaccinationDate(command.getDateOfVaccination());
        patient.setDoses(command.getNumberSeriesDoses());
        patient.setVaccineType(command.getVaccineType());
        if (command.isNextVaccination()) {
            patient.getVaccinationPoint().setOccasion(command.getNextVaccinationDate());
        } else {
            patient.setVaccinationPoint(null);
            vaccinationPointRepository.deleteById(patient.getVaccinationPoint().getId());
        }
        return modelMapper.map(vaccinated, VaccinatedDTO.class);
    }

    public List<VaccinatedDTO> listAll() {
        return repository.findAll().stream().map(item -> modelMapper.map(item, VaccinatedDTO.class))
                .collect(Collectors.toList());
    }

    public VaccinatedDTO findById(long id) {
        return modelMapper.map(repository.findById(id)
                        .orElseThrow(() -> new PatientNotFoundException(id)),
                VaccinatedDTO.class);
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
