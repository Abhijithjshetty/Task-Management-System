package com.sushikhacapitals.core.exception;

import lombok.Data;

import java.util.Map;

@Data
public class GenericFieldValidationException extends RuntimeException {

    private final Errors errors;

    private Map<String,String> errorMessages;

    public GenericFieldValidationException() {
        this.errors = Errors.GENERIC_FIELD_VALIDATION_ERROR;
    }
    public GenericFieldValidationException(Errors errors) {
        this.errors = errors;
    }

    public Errors getError() {
        return errors;
    }
}
