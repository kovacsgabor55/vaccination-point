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

    //TODO exception
    @Transactional
    public VaccinationPointEventDTO save(long id, CreateVaccinationPointEventCommand command) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException(id));
        VaccinationPointEvent newVaccinationPointEvent = new VaccinationPointEvent(patient, command.getOccasion(), command.getAddress(), command.getVaccineType());
        repository.save(newVaccinationPointEvent);
        patient.setVaccinationPointEvent(newVaccinationPointEvent);
        return modelMapper.map(newVaccinationPointEvent, VaccinationPointEventDTO.class);
    }

    //TODO exception
    @Transactional
    public VaccinationPointEventDTO updateById(long id, UpdateVaccinationPointEventCommand command) {
        VaccinationPointEvent item = repository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
        item.setOccasion(command.getOccasion());
        return modelMapper.map(item, VaccinationPointEventDTO.class);
    }

    //TODO exception
    public VaccinationPointEventDTO findById(long id) {
        return modelMapper.map(repository.findById(id)
                        .orElseThrow(() -> new PatientNotFoundException(id)),
                VaccinationPointEventDTO.class);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }

    public List<VaccinationPointEventDTO> listAll() {
        return repository.findAll().stream().map(item -> modelMapper.map(item, VaccinationPointEventDTO.class))
                .collect(Collectors.toList());
    }
}
