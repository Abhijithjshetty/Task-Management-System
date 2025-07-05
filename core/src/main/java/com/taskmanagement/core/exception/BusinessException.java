package com.taskmanagement.core.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final Errors errors;
    public BusinessException(Errors errors) {
        super(errors.getMessage());
        this.errors = errors;
    }
    public Errors getError() {
        return errors;
    }
}
