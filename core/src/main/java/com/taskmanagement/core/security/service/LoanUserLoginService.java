package com.taskmanagement.core.security.service;


import com.taskmanagement.core.config.Constants;
import com.taskmanagement.core.security.manager.UserLoginManager;
import com.taskmanagement.core.security.models.UserAuth;
import com.taskmanagement.core.security.models.request.UserLoginRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/public/login")
@Slf4j
public class LoanUserLoginService {

    @Autowired
    UserLoginManager userLoginManager;

    @PostMapping()
    public UserAuth ownerLogin(@Valid @RequestBody UserLoginRequest userLoginRequest){
        log.info(Constants.LOG_CLASS_USER_LOGIN_SERVICE+" loginUser: started ");
        UserAuth auth = userLoginManager.userLogin(userLoginRequest);
        log.info(Constants.LOG_CLASS_USER_LOGIN_SERVICE+" end: started ");
        return auth;
    }
}
