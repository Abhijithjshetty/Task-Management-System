package com.sushikhacapitals.core.security.service;


import com.sushikhacapitals.core.config.Constants;
import com.sushikhacapitals.core.security.manager.LoanUserLoginManager;
import com.sushikhacapitals.core.security.models.LoanUserAuth;
import com.sushikhacapitals.core.security.models.request.LoanUserLoginRequest;
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
    LoanUserLoginManager loanUserLoginManager;

    @PostMapping()
    public LoanUserAuth ownerLogin(@Valid @RequestBody LoanUserLoginRequest loanUserLoginRequest){
        log.info(Constants.LOG_CLASS_LOAN_USER_LOGIN_SERVICE+" loginUser: started ");
        LoanUserAuth auth = loanUserLoginManager.userLogin(loanUserLoginRequest);
        log.info(Constants.LOG_CLASS_LOAN_USER_LOGIN_SERVICE+" end: started ");
        return auth;
    }
}
