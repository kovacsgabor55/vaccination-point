package hu.nive.ujratervezes.vaccinationpoint.service;

import hu.nive.ujratervezes.vaccinationpoint.entity.Patient;
import hu.nive.ujratervezes.vaccinationpoint.entity.VaccinationPoint;
import hu.nive.ujratervezes.vaccinationpoint.errorandling.PatientNotFoundException;
import hu.nive.ujratervezes.vaccinationpoint.pojo.command.CreateVaccinationPointCommand;
import hu.nive.ujratervezes.vaccinationpoint.pojo.dto.VaccinationPointDto;
import hu.nive.ujratervezes.vaccinationpoint.repository.PatientRepository;
import hu.nive.ujratervezes.vaccinationpoint.repository.VaccinationPointRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class VaccinationPointService {

    private final ModelMapper modelMapper;

    private final VaccinationPointRepository repository;

    private final PatientRepository patientRepository;

    @Transactional
    public VaccinationPointDto save(long id, CreateVaccinationPointCommand command) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException(id));
        VaccinationPoint uj = new VaccinationPoint(patient, command.getOccasion(), command.getAddress(), command.getVaccineType());
        repository.save(uj);
        patient.setVaccinationPoint(uj);
        return modelMapper.map(uj, VaccinationPointDto.class);
    }
}
