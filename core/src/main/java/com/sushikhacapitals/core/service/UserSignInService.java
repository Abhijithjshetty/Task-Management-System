package com.sushikhacapitals.core.service;

import com.sushikhacapitals.core.config.Constants;
import com.sushikhacapitals.core.manager.OTPVerificationManager;
import com.sushikhacapitals.core.model.request.UserSignInRequest;
import com.sushikhacapitals.core.model.response.UserSignInResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/public/generate_otp")
public class UserSignInService {

    @Autowired
    private OTPVerificationManager manager;

    @PostMapping()
    public UserSignInResponse generateOTP(@RequestBody @Valid UserSignInRequest request) {
        log.info(Constants.LOG_CLASS_CONTACT_VERIFICATION_OTP_SERVICE + "generateOtp" + ":started");
        UserSignInResponse response = manager.generateOtp(request);
        log.info(Constants.LOG_CLASS_CONTACT_VERIFICATION_OTP_SERVICE + "generateOtp" + ":ended");
        return response;
    }
}
