package hu.nive.ujratervezes.vaccinationpoint.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<Name, String> {
    @Override
    public void initialize(Name constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return name != null &&
                name.length() > 2 &&
                Character.isUpperCase(name.charAt(0)) &&
                !name.endsWith(" ") &&
                name.contains(" ");
    }
}
