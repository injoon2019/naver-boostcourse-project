package com.ntscorp.intern.commons.validator;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ReservationValidator {
    private static final String NAME_REGULAR_EXPRESSION = "^[가-힣]{2,4}";
    private static final String TELEPHONE_REGULAR_EXPRESSION = "^\\d{3}-\\d{3,4}-\\d{4}$";
    private static final String EMAIL_REGULAR_EXPRESSION =
            "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$";

    public boolean validName (String name) {
        return Pattern.matches(NAME_REGULAR_EXPRESSION, name);
    }

    public boolean validPhoneNumber (String phoneNumber) {
        return Pattern.matches(TELEPHONE_REGULAR_EXPRESSION, phoneNumber);
    }

    public boolean validEmail (String reservationEmail) {
        return Pattern.matches(EMAIL_REGULAR_EXPRESSION, reservationEmail);
    }
}
