package hu.nive.ujratervezes.vaccinationpoint.patient;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class PatientNotFoundException extends AbstractThrowableProblem {
    public PatientNotFoundException(long id) {
        super(URI.create("patients/not-found"),
                "Not Found",
                Status.NOT_FOUND,
                "Patient not found: " + id
        );
    }
}
