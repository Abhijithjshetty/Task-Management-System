package com.taskmanagement.core.exception;

import lombok.Getter;

@Getter
public class CustomAuthenticationException extends RuntimeException {

    private final Errors errors;

    public CustomAuthenticationException() {
        this.errors = Errors.AUTH_EXCEPTION;
    }

    public Errors getError() {
        return errors;
    }
}
