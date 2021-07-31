package hu.nive.ujratervezes.vaccinationpoint.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DOBValidator implements ConstraintValidator<DOB, LocalDate> {

    private int minimumAge;

    @Override
    public void initialize(DOB constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        minimumAge = constraintAnnotation.minimumAge();
    }

    @Override
    public boolean isValid(LocalDate dob, ConstraintValidatorContext constraintValidatorContext) {
        return (dob.isAfter(LocalDate.of(1990, 01, 01)) && dob.isAfter(LocalDate.now().plusYears(minimumAge)));
    }
}
