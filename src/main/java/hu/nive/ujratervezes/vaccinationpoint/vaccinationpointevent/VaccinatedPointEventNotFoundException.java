package hu.nive.ujratervezes.vaccinationpoint.vaccinationpointevent;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class VaccinatedPointEventNotFoundException  extends AbstractThrowableProblem {
    public VaccinatedPointEventNotFoundException(long id) {
        super(URI.create("vaccinationpointevents/not-found"),
                "Not Found",
                Status.NOT_FOUND,
                "Vaccinated point event not found: " + id
        );
    }
}
