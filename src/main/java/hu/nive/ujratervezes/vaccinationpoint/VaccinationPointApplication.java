package hu.nive.ujratervezes.vaccinationpoint;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VaccinationPointApplication {

    public static void main(String[] args) {
        SpringApplication.run(VaccinationPointApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
