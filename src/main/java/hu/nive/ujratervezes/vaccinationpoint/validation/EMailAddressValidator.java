package hu.nive.ujratervezes.vaccinationpoint.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EMailAddressValidator implements ConstraintValidator<EMail, String> {

    private Pattern validEmailAddressRegex;

    @Override
    public void initialize(EMail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        validEmailAddressRegex = Pattern.compile(constraintAnnotation.emailRegex(), Pattern.CASE_INSENSITIVE);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        Matcher matcher = validEmailAddressRegex.matcher(email);
        return matcher.find();
    }
}
