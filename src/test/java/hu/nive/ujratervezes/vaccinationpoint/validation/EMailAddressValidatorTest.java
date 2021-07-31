package hu.nive.ujratervezes.vaccinationpoint.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EMailAddressValidatorTest {

    @Mock
    private ConstraintValidatorContext context;

    @EMail
    private String email;

    @Test
    @DisplayName("E-mail validator false test")
    void isValidFalse() throws NoSuchFieldException {
        EMailAddressValidator validator = new EMailAddressValidator();

        validator.initialize(EMailAddressValidatorTest.class.getDeclaredField("email").getAnnotation(EMail.class));

        assertFalse(validator.isValid("", context));
        assertFalse(validator.isValid(" ", context));
        assertFalse(validator.isValid("@ghffg.com", context));
        assertFalse(validator.isValid("userinvalid.com", context));
    }

    @Test
    @DisplayName("E-mail validator true test")
    void isValidTrue() throws NoSuchFieldException {
        EMailAddressValidator validator = new EMailAddressValidator();

        validator.initialize(EMailAddressValidatorTest.class.getDeclaredField("email").getAnnotation(EMail.class));

        assertTrue(validator.isValid("gmail@chucknorris.com", context));
        assertTrue(validator.isValid("webmaster@muller.de", context));
    }
}
