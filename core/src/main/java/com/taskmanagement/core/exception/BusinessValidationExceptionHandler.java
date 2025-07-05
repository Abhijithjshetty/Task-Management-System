package com.taskmanagement.core.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.taskmanagement.core.utils.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class BusinessValidationExceptionHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBusinessException(BusinessException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(ex.getError().getCode());
        errorResponse.setErrorMessage(ex.getMessage());
        errorResponse.setErrorType(ex.getError().getType());
        return errorResponse;
    }

    @ExceptionHandler(CustomAuthenticationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handleBusinessException(CustomAuthenticationException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(ex.getError().getCode());
        errorResponse.setErrorMessage(ex.getError().getMessage());
        errorResponse.setErrorType(ex.getError().getType());
        return errorResponse;
    }

    @ExceptionHandler(GenericFieldValidationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBusinessException(GenericFieldValidationException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(ex.getError().getCode());
        errorResponse.setErrorMessage(ex.getError().getMessage());
        errorResponse.setErrorMessages(ex.getErrorMessages());
        errorResponse.setErrorType(ex.getError().getType());
        return errorResponse;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(MethodArgumentNotValidException ex){
        //todo : as per field validations
        log.info("*******Field Validation Exception");
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String jsonPropertyName = JSONUtil.getJsonPropertyName(ex.getTarget(), fieldName);
            errors.put(jsonPropertyName,error.getDefaultMessage());
        });
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(Errors.GENERIC_FIELD_VALIDATION_ERROR.getCode());
        errorResponse.setErrorMessages(errors);
        errorResponse.setErrorType(Errors.GENERIC_FIELD_VALIDATION_ERROR.getType());
        return errorResponse;
    }

    @ExceptionHandler(InvalidFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleInvalidFormatException(InvalidFormatException ex) {
        log.info("*******Invalid Format Exception");

        Map<String, String> errors = new HashMap<>();
        ex.getPath().forEach(reference -> {
            String fieldName = reference.getFieldName();
            String jsonPropertyName = JSONUtil.getJsonPropertyName(ex.getTargetType(), fieldName);
            if(ex.getTargetType().isEnum()){
                errors.put(jsonPropertyName,"Invalid value, accepted values are "+ Arrays.stream(ex.getTargetType().getEnumConstants()).collect(Collectors.toList()));
            }else {
                errors.put(jsonPropertyName,"Invalid value");
            }
        });

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(Errors.GENERIC_FIELD_VALIDATION_ERROR.getCode());
        errorResponse.setErrorMessages(errors);
        errorResponse.setErrorType(Errors.GENERIC_FIELD_VALIDATION_ERROR.getType());

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleNotReadable(HttpMessageNotReadableException ex){
        log.error("******* handleNotReadable",ex.toString());
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(Errors.GENERIC_FIELD_VALIDATION_ERROR.getCode());
        errorResponse.setErrorMessage("Failed to parse the request payload");
        errorResponse.setErrorType(Errors.GENERIC_FIELD_VALIDATION_ERROR.getType());
        return errorResponse;
    }
}
