package com.taskmanagement.core.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Constants {


    public static final String LOG_CLASS_USER_LOGIN_MANAGER = "<<<<UserLogInManager";
    public static final String LOG_CLASS_USER_LOGIN_SERVICE= "<<<<UserLogInService";

    public static final String LOG_CLASS_USER_SIGN_UP_SERVICE = "<<<<UserRegistrationService";
    public static final String LOG_CLASS_USER_REGISTRATION_SERVICE = "<<<<UserRegistrationService";
    public static final String LOG_CLASS_USER_REGISTRATION_MANAGER = "<<<<UserRegistrationManager";
    public static final String LOG_CLASS_USER_REGISTRATION_CONVERSION_MANAGER = "<<<<UserRegistrationConversionManager";
    public static final String LOG_CLASS_USER_REGISTRATION_VALIDATION= "<<<<UserRegistrationValidation";

    public static final String LOG_CLASS_TASK_MANAGER = "<<<<TaskManager";
    public static final String LOG_CLASS_TASK_SERVICE= "<<<<TaskService";
    public static final String LOG_CLASS_TASK_CONVERSION_MANAGER= "<<<<TaskConversionManager";


}


