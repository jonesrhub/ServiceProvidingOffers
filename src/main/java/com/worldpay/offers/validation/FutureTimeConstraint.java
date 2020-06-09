package com.worldpay.offers.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom constraint annotation which validates the input of expiryTime.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy=FutureTimeConstraintValidator.class)
public @interface FutureTimeConstraint {

    String message() default "Expiry date must be in the future and in the format of 'yyy-MM-dd hh:mm:ss' ";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
