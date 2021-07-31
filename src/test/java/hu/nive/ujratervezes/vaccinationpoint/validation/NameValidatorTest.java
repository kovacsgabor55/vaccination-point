package hu.nive.ujratervezes.vaccinationpoint.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NameValidatorTest {

    @Mock
    private ConstraintValidatorContext context;

    @Name
    private String name;

    @Test
    @DisplayName("Name validator false test")
    void isValidFalse() throws NoSuchFieldException {
        NameValidator validator = new NameValidator();

        validator.initialize(NameValidatorTest.class.getDeclaredField("name").getAnnotation(Name.class));

        assertFalse(validator.isValid("   Bal Bal", context));
        assertFalse(validator.isValid("Bal Bal  ", context));
        assertFalse(validator.isValid("  Bal Bal  ", context));
        assertFalse(validator.isValid("balbal", context));
        assertFalse(validator.isValid("  balbal", context));
        assertFalse(validator.isValid("balbal   ", context));
        assertFalse(validator.isValid("  balbal   ", context));
        assertFalse(validator.isValid("balbal", context));
        assertFalse(validator.isValid("", context));
        assertFalse(validator.isValid("   ", context));
        assertFalse(validator.isValid(null, context));
    }

    @Test
    @DisplayName("Name validator true test")
    void isValidTrue() throws NoSuchFieldException {
        NameValidator validator = new NameValidator();

        validator.initialize(NameValidatorTest.class.getDeclaredField("name").getAnnotation(Name.class));

        assertTrue(validator.isValid("Bal Bal", context));
        assertTrue(validator.isValid("Bal Bal Bal", context));
    }
}
