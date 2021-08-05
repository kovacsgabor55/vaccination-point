package hu.nive.ujratervezes.vaccinationpoint;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VaccinationPointApplicationTests {

    @Test
    void contextLoads() {
        String[] args = new String[0];
        VaccinationPointApplication.main(args);
    }
}
