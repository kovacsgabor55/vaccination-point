package hu.nive.ujratervezes.vaccinationpoint.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MedicalRecordValidatorTest {

    @Mock
    private ConstraintValidatorContext context;

    @MedicalRecord
    private String medicalRecord;

    @Test
    @DisplayName("Medical record validator false test")
    void isValidFalse() throws NoSuchFieldException {
        MedicalRecordValidator validator = new MedicalRecordValidator();

        validator.initialize(MedicalRecordValidatorTest.class.getDeclaredField("medicalRecord").getAnnotation(MedicalRecord.class));

        assertFalse(validator.isValid("", context));
        assertFalse(validator.isValid("123456789", context));
        assertFalse(validator.isValid("1234", context));
        assertFalse(validator.isValid("abc", context));
    }

    @Test
    @DisplayName("Medical record validator true test")
    void isValidTrue() throws NoSuchFieldException {
        MedicalRecordValidator validator = new MedicalRecordValidator();

        validator.initialize(MedicalRecordValidatorTest.class.getDeclaredField("medicalRecord").getAnnotation(MedicalRecord.class));

        assertTrue(validator.isValid("123456788", context));
        assertTrue(validator.isValid("037687210", context));
    }
}
