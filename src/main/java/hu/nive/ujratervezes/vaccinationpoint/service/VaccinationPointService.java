package hu.nive.ujratervezes.vaccinationpoint.service;

import hu.nive.ujratervezes.vaccinationpoint.pojo.dto.VaccinationPointDto;
import hu.nive.ujratervezes.vaccinationpoint.repository.VaccinationPointRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VaccinationPointService {

    private final ModelMapper modelMapper;

    private final VaccinationPointRepository repository;

    public VaccinationPointDto save() {
        return null;
    }
}
