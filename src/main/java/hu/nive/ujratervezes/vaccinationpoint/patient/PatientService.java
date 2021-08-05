package hu.nive.ujratervezes.vaccinationpoint.patient;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PatientService {

    private final ModelMapper modelMapper;

    private final PatientRepository repository;

    public List<PatientDTO> listAll() {
        return repository.findAll().stream().map(item -> modelMapper.map(item, PatientDTO.class))
                .collect(Collectors.toList());
    }

    public PatientDTO findById(long id) {
        return modelMapper.map(repository.findById(id)
                        .orElseThrow(() -> new PatientNotFoundException(id)),
                PatientDTO.class);
    }

    public PatientDTO findByTaj(String taj) {
        return modelMapper.map(repository.findByTaj(taj)
                        .orElseThrow(() -> new PatientNotFoundException(taj)),
                PatientDTO.class);
    }

    public PatientDTO create(CreatePatientCommand command) {
        Patient item = new Patient(command.getTaj(), command.getName(), command.getDateOfBirth(), command.getEmail());
        repository.save(item);
        return modelMapper.map(item, PatientDTO.class);
    }

    @Transactional
    public PatientDTO updateById(long id, UpdatePatientCommand command) {
        Patient item = repository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
        item.setTaj(command.getTaj());
        item.setName(command.getName());
        item.setDateOfBirth(command.getDateOfBirth());
        item.setEmail(command.getEmail());
        return modelMapper.map(item, PatientDTO.class);
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
