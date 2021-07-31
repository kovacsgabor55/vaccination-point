package hu.nive.ujratervezes.vaccinationpoint.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Constraint(validatedBy = MedicalRecordValidator.class)
public @interface MedicalRecord {

    String message() default "Invalid medical record";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
