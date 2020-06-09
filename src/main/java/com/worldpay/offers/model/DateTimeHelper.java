package com.worldpay.offers.model;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Class intended to be used to format LocalDateTime objects.
 * Helps for mocking in unit tests.
 */
@Component
public class DateTimeHelper {

    /**
     * @return LocalDateTime - current time truncated to seconds
     */
    public LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }

}
