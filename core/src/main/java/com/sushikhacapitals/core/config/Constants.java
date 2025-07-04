package com.sushikhacapitals.core.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Constants {

    public static final String EMAIL_VERIFICATION_OTP_TEMPLATE =
            "<body>\n" +
            "  <div>\n" +
            "    <h1>Email From Sushikha Capitals</h1>\n" +
            "    <p>Your OTP for email verification is: <b>%s</b></p>\n" +
            "    <p>If you did not request this OTP, please ignore this email.</p>\n" +
            "    <p>If you have any questions or need assistance, feel free to reach out to our support team at [%s] or call us at [%s].</p>\n" +
            "    <p>Thank you for choosing Sushikha Capitals. We look forward to providing you with an exceptional experience!</p>\n" +
            "    <p>Best Regards,<br>Sushikha Capitals Team</p>\n" +
            "  </div>\n" +
            "</body>";

    public static final String LOG_CLASS_LOAN_USER_LOGIN_MANAGER = "<<<<UserLogInManager";
    public static final String LOG_CLASS_LOAN_USER_LOGIN_SERVICE= "<<<<UserLogInService";

    public static final String LOG_CLASS_USER_SIGN_UP_SERVICE = "<<<<UserRegistrationService";
    public static final String LOG_CLASS_USER_REGISTRATION_SERVICE = "<<<<UserRegistrationService";
    public static final String LOG_CLASS_USER_REGISTRATION_MANAGER = "<<<<UserRegistrationManager";
    public static final String LOG_CLASS_USER_REGISTRATION_CONVERSION_MANAGER = "<<<<UserRegistrationConversionManager";
    public static final String LOG_CLASS_USER_REGISTRATION_VALIDATION= "<<<<UserRegistrationValidation";


    public static final String LOG_CLASS_CONTACT_VERIFICATION_OTP_MANAGER="<<<<< ContactVerificationOTPManager";
    public static final String LOG_CLASS_CONTACT_VERIFICATION_OTP_SERVICE = "<<<<< ContactVerificationOTPService";

    public static final String LOG_CLASS_CONVERSION_MANAGER="<<<<< ConversionManger";
    public static final String LOG_CLASS_PERSONAL_DETAILS_SERVICE = "<<<<< PersonalDetailsService";
    public static final String LOG_CLASS_PERSONAL_DETAILS_MANAGER = "<<<<< PersonalDetailsManager";

}


