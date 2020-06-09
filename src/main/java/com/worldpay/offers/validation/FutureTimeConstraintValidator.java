package com.worldpay.offers.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

/**
 * Validates the incoming expiryTime, ensuring that it is in the future.
 */
public class FutureTimeConstraintValidator implements ConstraintValidator<FutureTimeConstraint, LocalDateTime> {

    @Override
    public void initialize(FutureTimeConstraint localDateTimeConstraint) {
    }

    @Override
    public boolean isValid(LocalDateTime localDateTime, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValid = localDateTime != null
            && localDateTime.isAfter(LocalDateTime.now());

        if(!isValid) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                "Input date must be in the future and in the format of 'yyyy-MM-ddThh:mm:ss' ")
                .addConstraintViolation();
        }

        return isValid;
    }

}
