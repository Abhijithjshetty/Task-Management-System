package com.taskmanagement.core.exception;

import lombok.Getter;

@Getter
public enum Errors {
    INVALID_ACCOUNT("ERT301","Invalid account" ,"BUSINESS_VALIDATION" ),
    EMAIL_ALREADY_IN_USE("ERT302", "Email address is already in use", "BUSINESS_VALIDATION"),
    USER_ID_DOES_NOT_EXIST("ERT306", "User id does not exist", "BUSINESS_VALIDATION"),
    TASK_ID_DOES_NOT_EXIST("ERT306", "Task id does not exist", "BUSINESS_VALIDATION"),

    // Internal server error common for all uncatched generic exceptions
    INTERNAL_SERVER_ERROR("ERT500", "Internal Server ERROR", "BUSINESS_VALIDATION"),

    // Field validation Server ERROR
    GENERIC_FIELD_VALIDATION_ERROR("ERT200", "Generic field validation error", "FIELD_VALIDATION"),

    // AUTH Exception
    AUTH_EXCEPTION("ERT401", "Authentication Failed", "AUTHENTICATION_VALIDATION");


    private final String code;
    private final String message;
    private final String type;
    private String customMessage;


    public void setCustomMessage(String message){
        this.customMessage=message;
    }

    Errors(String code, String message, String type) {
        this.code = code;
        this.message = message;
        this.type = type;
        this.customMessage="";
    }
    public String getMessage(){
        if(this.customMessage.equals("")){
            return this.message;
        }else{
            return this.customMessage;
        }
    }


}