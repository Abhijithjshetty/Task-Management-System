package com.taskmanagement.core.service;

import com.taskmanagement.core.config.Constants;
import com.taskmanagement.core.manager.UserRegistrationManager;
import com.taskmanagement.core.model.request.UserRegistrationRequest;
import com.taskmanagement.core.model.response.UserRegistrationResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/sign_up")
@Slf4j
public class UserSignUpService {

    @Autowired
    private UserRegistrationManager userRegistrationManager;

    @PostMapping
    public UserRegistrationResponse registerUser(@RequestBody @Valid UserRegistrationRequest request) {
        log.info(Constants.LOG_CLASS_USER_SIGN_UP_SERVICE + " registerUser : started");
        UserRegistrationResponse response = userRegistrationManager.register(request);
        log.info(Constants.LOG_CLASS_USER_SIGN_UP_SERVICE + " registerUser : ended");
        return response;
    }
}