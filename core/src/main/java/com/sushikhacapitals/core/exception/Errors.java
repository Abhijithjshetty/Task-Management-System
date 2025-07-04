package com.sushikhacapitals.core.exception;

import lombok.Getter;

@Getter
public enum Errors {
    INVALID_ACCOUNT("ERT301","Invalid account" ,"BUSINESS_VALIDATION" ),
    EMAIL_ALREADY_IN_USE("ERT302", "Email address is already in use", "BUSINESS_VALIDATION"),
    PHONE_ALREADY_IN_USE("ERT303", "Phone number is already in use", "BUSINESS_VALIDATION"),
    PHONE_NUMBER_DOES_NOT_EXIST("ERT303", "Phone number does not exist", "BUSINESS_VALIDATION"),
    DOCUMENT_OR_IMAGE_IS_NULL("ERT304","Document or image is null" , "BUSINESS_VALIDATION"),
    DOCUMENT_OR_IMAGE_IS_INVALID("ERT305","Document or image is invalid" , "BUSINESS_VALIDATION"),
    USER_ID_DOES_NOT_EXIST("ERT306", "User id does not exist", "BUSINESS_VALIDATION"),
    USER_AGE_IS_LESS_THAN_18("ERT307", "User Age is less than 18", "BUSINESS_VALIDATION"),
    INVALID_DATA_FOR_OTP("ERT308","Invalid data to send OTP" ,"BUSINESS_VALIDATION"),
    INVALID_OTP_REF_ID("ERT309", "INVALID OTP Reference", "BUSINESS_VALIDATION"),
    INVALID_OTP("ERT310","Invalid OTP" ,"BUSINESS_VALIDATION"),
    OTP_ALREADY_VERIFIED("ERT311","OTP already verified" ,"BUSINESS_VALIDATION"),
    EMAIL_ALREADY_VERIFIED("ERT312","Email already verified" ,"BUSINESS_VALIDATION"),

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