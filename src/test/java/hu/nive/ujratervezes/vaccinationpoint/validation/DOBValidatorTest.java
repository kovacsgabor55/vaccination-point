package hu.nive.ujratervezes.vaccinationpoint.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DOBValidatorTest {

    @Mock
    private ConstraintValidatorContext context;

    @DOB
    private LocalDate date;

    @Test
    @DisplayName("Date of birth validator false test")
    void isValidFalse() throws NoSuchFieldException {
        DOBValidator validator = new DOBValidator();

        validator.initialize(DOBValidatorTest.class.getDeclaredField("date").getAnnotation(DOB.class));
        LocalDate base = LocalDate.now();

        assertFalse(validator.isValid(base.plusYears(17), context));
        assertFalse(validator.isValid(base.plusYears(-1), context));
        assertFalse(validator.isValid(base, context));
    }

    @Test
    @DisplayName("Date of birth validator true test")
    void isValidTrue() throws NoSuchFieldException {
        DOBValidator validator = new DOBValidator();

        validator.initialize(DOBValidatorTest.class.getDeclaredField("date").getAnnotation(DOB.class));
        LocalDate base = LocalDate.now();

        assertTrue(validator.isValid(base.minusYears(18).minusDays(1), context));
        assertTrue(validator.isValid(base.minusYears(20), context));
        assertTrue(validator.isValid(base.minusYears(60), context));
    }
}
