package hu.nive.ujratervezes.vaccinationpoint.vaccinated;

import hu.nive.ujratervezes.vaccinationpoint.patient.Patient;
import hu.nive.ujratervezes.vaccinationpoint.vaccinationpointevent.VaccinationPointEvent;
import hu.nive.ujratervezes.vaccinationpoint.patient.PatientNotFoundException;
import hu.nive.ujratervezes.vaccinationpoint.patient.PatientRepository;
import hu.nive.ujratervezes.vaccinationpoint.vaccinationpointevent.VaccinationPointEventRepository;
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

    private final VaccinationPointEventRepository vaccinationPointEventRepository;

    private final VaccinatedRepository repository;

    //TODO exception
    @Transactional
    public VaccinatedDTO save(long patientId, CreateVaccinatedCommand command) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new PatientNotFoundException(patientId));
        long vaccinationPointId = patient.getVaccinationPointEvent().getId();
        VaccinationPointEvent vaccinationPointEvent = vaccinationPointEventRepository.findById(vaccinationPointId).orElseThrow(() -> new PatientNotFoundException(vaccinationPointId));

        Vaccinated newVaccinated = new Vaccinated(patient, command.getNumberSeriesDoses(), command.getOverallNumberDoses(), command.getDateOfVaccination(), command.getAdministered(), command.getVaccineType(), command.getLot());
        repository.save(newVaccinated);

        patient.setLastVaccinationDate(command.getDateOfVaccination());
        patient.setDoses(command.getNumberSeriesDoses());
        patient.setVaccineType(command.getVaccineType());
        if (command.isNextVaccination()) {
            vaccinationPointEvent.setOccasion(command.getNextVaccinationDate());
        } else {
            patient.setVaccinationPointEvent(null);
            vaccinationPointEventRepository.deleteById(vaccinationPointId);
        }
        return modelMapper.map(newVaccinated, VaccinatedDTO.class);
    }

    //TODO exception
    @Transactional
    public VaccinatedDTO updateById(long id, UpdateVaccinatedCommand command) {
        Vaccinated vaccinated = repository.findById(id).orElseThrow(() -> new PatientNotFoundException(id));
        Patient patient = vaccinated.getPatient();

        vaccinated.setVaccineType(command.getVaccineType());
        vaccinated.setDateOfVaccination(command.getDateOfVaccination());
        vaccinated.setAdministered(command.getAdministered());
        vaccinated.setLot(command.getLot());
        vaccinated.setNumberSeriesDoses(command.getNumberSeriesDoses());
        vaccinated.setOverallNumberDoses(command.getOverallNumberDoses());

        patient.setLastVaccinationDate(command.getDateOfVaccination());
        patient.setDoses(command.getNumberSeriesDoses());
        patient.setVaccineType(command.getVaccineType());
        if (command.isNextVaccination()) {
            patient.getVaccinationPointEvent().setOccasion(command.getNextVaccinationDate());
        } else {
            patient.setVaccinationPointEvent(null);
            vaccinationPointEventRepository.deleteById(patient.getId());
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
