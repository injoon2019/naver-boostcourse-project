package com.ntscorp.intern.commons.exception.customexception;

public class InvalidInputException extends IllegalArgumentException {
    public InvalidInputException() {
    }

    public InvalidInputException(String s) {
        super(s);
    }
}
