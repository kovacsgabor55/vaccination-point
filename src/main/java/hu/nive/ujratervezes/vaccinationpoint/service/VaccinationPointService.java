package hu.nive.ujratervezes.vaccinationpoint.service;

import hu.nive.ujratervezes.vaccinationpoint.entity.Patient;
import hu.nive.ujratervezes.vaccinationpoint.entity.VaccinationPoint;
import hu.nive.ujratervezes.vaccinationpoint.errorhandling.PatientNotFoundException;
import hu.nive.ujratervezes.vaccinationpoint.pojo.command.CreateVaccinationPointCommand;
import hu.nive.ujratervezes.vaccinationpoint.pojo.command.UpdateVaccinationPointCommand;
import hu.nive.ujratervezes.vaccinationpoint.pojo.dto.VaccinationPointDTO;
import hu.nive.ujratervezes.vaccinationpoint.repository.PatientRepository;
import hu.nive.ujratervezes.vaccinationpoint.repository.VaccinationPointRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VaccinationPointService {

    private final ModelMapper modelMapper;

    private final VaccinationPointRepository repository;

    private final PatientRepository patientRepository;

    //TODO exception
    @Transactional
    public VaccinationPointDTO save(long id, CreateVaccinationPointCommand command) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException(id));
        VaccinationPoint newVaccinationPoint = new VaccinationPoint(patient, command.getOccasion(), command.getAddress(), command.getVaccineType());
        repository.save(newVaccinationPoint);
        patient.setVaccinationPoint(newVaccinationPoint);
        return modelMapper.map(newVaccinationPoint, VaccinationPointDTO.class);
    }

    //TODO exception
    @Transactional
    public VaccinationPointDTO updateById(long id, UpdateVaccinationPointCommand command) {
        VaccinationPoint item = repository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
        item.setOccasion(command.getOccasion());
        return modelMapper.map(item, VaccinationPointDTO.class);
    }

    //TODO exception
    public VaccinationPointDTO findById(long id) {
        return modelMapper.map(repository.findById(id)
                        .orElseThrow(() -> new PatientNotFoundException(id)),
                VaccinationPointDTO.class);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }

    public List<VaccinationPointDTO> listAll() {
        return repository.findAll().stream().map(item -> modelMapper.map(item, VaccinationPointDTO.class))
                .collect(Collectors.toList());
    }
}
