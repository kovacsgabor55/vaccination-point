package hu.nive.ujratervezes.vaccinationpoint.vaccinated;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class VaccinatedNotFoundException  extends AbstractThrowableProblem {
    public VaccinatedNotFoundException(long id) {
        super(URI.create("vaccinateds/not-found"),
                "Not Found",
                Status.NOT_FOUND,
                "Vaccinated not found: " + id
        );
    }
}
