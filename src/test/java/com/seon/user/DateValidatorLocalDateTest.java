package com.seon.user;

import com.seon.user.validation.DateValidatorLocalDate;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DateValidatorLocalDateTest {
    DateValidatorLocalDate validator = new DateValidatorLocalDate();

    @Test
    public void shouldValidateDateFormat() {

        assertTrue(validator.isValid("28/02/2022", null));
        assertFalse(validator.isValid("30/02/2022", null));
    }

}