package com.sushikhacapitals.core.service;

import com.sushikhacapitals.core.config.Constants;
import com.sushikhacapitals.core.manager.ContactVerificationOTPManager;
import com.sushikhacapitals.core.model.request.ContactVerificationOTPRequest;
import com.sushikhacapitals.core.model.request.ValidateOTPRequest;
import com.sushikhacapitals.core.model.response.ContactVerificationOTPResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/otp")
@Slf4j
@SecurityRequirement(name="jwtAuthentication")
public class ContactVerificationOTPService {

    @Autowired
    ContactVerificationOTPManager manager;

    @PostMapping("/generate_otp")
    public ContactVerificationOTPResponse generateOTP(@RequestBody ContactVerificationOTPRequest request) {
        log.info(Constants.LOG_CLASS_CONTACT_VERIFICATION_OTP_SERVICE +"generateOtp"+ ":started");
        ContactVerificationOTPResponse response=manager.generateOtp(request);
        log.info(Constants.LOG_CLASS_CONTACT_VERIFICATION_OTP_SERVICE +"generateOtp"+ ":ended");
        return response;
    }

    @PostMapping("/validate_otp")
    public ContactVerificationOTPResponse validateOTP(@RequestBody ValidateOTPRequest request) {
        log.info(Constants.LOG_CLASS_CONTACT_VERIFICATION_OTP_SERVICE +"validateOTP"+ ":started");
        ContactVerificationOTPResponse response=manager.validateOTP(request.getOtpId(),request.getOtpValue());
        log.info(Constants.LOG_CLASS_CONTACT_VERIFICATION_OTP_SERVICE +"validateOTP"+ ":ended");
        return response;
    }


}


