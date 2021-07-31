package hu.nive.ujratervezes.vaccinationpoint.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class MedicalRecordValidator implements ConstraintValidator<MedicalRecord, String> {
    private static final int MEDICAL_RECORD_LENGTH = 9;

    @Override
    public void initialize(MedicalRecord constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String medicalRecord, ConstraintValidatorContext constraintValidatorContext) {
        Objects.requireNonNull(medicalRecord);
        if (medicalRecord.length() != MEDICAL_RECORD_LENGTH) {
            return false;
        }
        char[] medicalRecordChars = medicalRecord.substring(0, 8).toCharArray();
        int checkSum = 0;
        for (int i = 0; i < medicalRecordChars.length; i++) {
            if (i % 2 == 0) {
                checkSum += Character.getNumericValue(medicalRecordChars[i]) * 3;
            } else {
                checkSum += Character.getNumericValue(medicalRecordChars[i]) * 7;
            }
        }
        return checkSum % 10 == Integer.parseInt(medicalRecord.substring(8));
    }
}
