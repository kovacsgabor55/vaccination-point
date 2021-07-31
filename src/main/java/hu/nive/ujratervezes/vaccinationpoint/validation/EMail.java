package hu.nive.ujratervezes.vaccinationpoint.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Constraint(validatedBy = EMailAddressValidator.class)
public @interface EMail {

    String message() default "Invalid E-Mail address";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String emailRegex() default "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
}
