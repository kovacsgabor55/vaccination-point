package hu.nive.ujratervezes.vaccinationpoint;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
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

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Vaccination point administered system")
                        .version("1.0.0")
                        .description("Administered patient registration vaccination point booking occasion and vaccination."));
    }
}
