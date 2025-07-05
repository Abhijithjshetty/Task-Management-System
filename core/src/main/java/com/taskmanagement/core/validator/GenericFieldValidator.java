package com.taskmanagement.core.validator;

import com.taskmanagement.core.exception.GenericFieldValidationException;
import com.taskmanagement.core.utils.JSONUtil;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GenericFieldValidator {
    public static <T> void fieldValidation(T targetObject) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(targetObject);
        // Convert ConstraintViolations to Spring Errors
        Map<String, String> errors = new HashMap<>();
        for (ConstraintViolation<T> violation : violations) {
            String propertyName = JSONUtil.getJsonPropertyName(violation);
            String message = violation.getMessage();
            errors.put(propertyName, message);
        }
        if (errors.size() > 0) {
            GenericFieldValidationException exception = new GenericFieldValidationException();
            exception.setErrorMessages(errors);
            throw exception;
        }
    }

}
