package hu.nive.ujratervezes.vaccinationpoint.vaccinationpointevent;

import hu.nive.ujratervezes.vaccinationpoint.patient.Patient;
import hu.nive.ujratervezes.vaccinationpoint.patient.PatientNotFoundException;
import hu.nive.ujratervezes.vaccinationpoint.patient.PatientRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VaccinationPointEventService {

    private final ModelMapper modelMapper;

    private final VaccinationPointEventRepository repository;

    private final PatientRepository patientRepository;

    public List<VaccinationPointEventDTO> listAll() {
        return repository.findAll().stream().map(item -> modelMapper.map(item, VaccinationPointEventDTO.class))
                .collect(Collectors.toList());
    }

    public VaccinationPointEventDTO findById(long id) {
        return modelMapper.map(repository.findById(id)
                        .orElseThrow(() -> new VaccinatedPointEventNotFoundException(id)),
                VaccinationPointEventDTO.class);
    }

    @Transactional
    public VaccinationPointEventDTO create(long id, CreateVaccinationPointEventCommand command) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException(id));
        VaccinationPointEvent newVaccinationPointEvent = new VaccinationPointEvent(patient, command.getOccasion(), command.getAddress(), command.getVaccineType());
        repository.save(newVaccinationPointEvent);
        patient.setVaccinationPointEvent(newVaccinationPointEvent);
        return modelMapper.map(newVaccinationPointEvent, VaccinationPointEventDTO.class);
    }

    @Transactional
    public VaccinationPointEventDTO updateById(long id, UpdateVaccinationPointEventCommand command) {
        VaccinationPointEvent item = repository.findById(id)
                .orElseThrow(() -> new VaccinatedPointEventNotFoundException(id));
        item.setOccasion(command.getOccasion());
        return modelMapper.map(item, VaccinationPointEventDTO.class);
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
