package hu.nive.ujratervezes.vaccinationpoint.service;

import hu.nive.ujratervezes.vaccinationpoint.entity.Patient;
import hu.nive.ujratervezes.vaccinationpoint.errorandling.PatientNotFoundException;
import hu.nive.ujratervezes.vaccinationpoint.pojo.command.CreatePatientCommand;
import hu.nive.ujratervezes.vaccinationpoint.pojo.command.UpdatePatientCommand;
import hu.nive.ujratervezes.vaccinationpoint.pojo.dto.PatientDto;
import hu.nive.ujratervezes.vaccinationpoint.repository.PatientRepository;
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

    public PatientDto save(CreatePatientCommand command) {
        Patient item = new Patient(command.getTaj(), command.getName(), command.getDateOfBirth(), command.getEmail());
        repository.save(item);
        return modelMapper.map(item, PatientDto.class);
    }

    public List<PatientDto> listAll() {
        return repository.findAll().stream().map(item -> modelMapper.map(item, PatientDto.class))
                .collect(Collectors.toList());
    }

    public PatientDto findById(long id) {
        return modelMapper.map(repository.findById(id)
                        .orElseThrow(() -> new PatientNotFoundException(id)),
                PatientDto.class);
    }

    @Transactional
    public PatientDto updateById(long id, UpdatePatientCommand command) {
        Patient item = repository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
        item.setTaj(command.getTaj());
        item.setName(command.getName());
        item.setDateOfBirth(command.getDateOfBirth());
        item.setEmail(command.getEmail());
        return modelMapper.map(item, PatientDto.class);
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
