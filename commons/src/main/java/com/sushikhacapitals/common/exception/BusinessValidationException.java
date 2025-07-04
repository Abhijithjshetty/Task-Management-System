package com.sushikhacapitals.common.exception;

public class BusinessValidationException extends RuntimeException {
    private String code;
    private String message;

    public BusinessValidationException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BusinessValidationException(String message) {
        super(message);
        this.message = message;
    }
}
