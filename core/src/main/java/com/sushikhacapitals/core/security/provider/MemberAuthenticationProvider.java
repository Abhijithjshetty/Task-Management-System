package com.sushikhacapitals.core.security.provider;


import com.sushikhacapitals.common.security.AESUtil;
import com.sushikhacapitals.core.security.authentication.PasswordBasedAuthentication;
import com.sushikhacapitals.core.security.exception.AuthException;
import com.sushikhacapitals.core.security.models.LoanUserAuth;
import com.sushikhacapitals.core.utils.HashingUtil;
import com.taskmanagement.mappers.api.SushikhaUserDao;
import com.taskmanagement.mappers.api.LoanUserDao;
import com.sushikhacapitals.models.LoanUser;
import com.sushikhacapitals.models.enums.UserStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@Slf4j
public class MemberAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    LoanUserDao userDao;

    @Autowired
    HashingUtil hashingUtil;

    @Autowired
    SushikhaUserDao sushikhaUserDao;

    @Autowired
    AESUtil aesUtil;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            PasswordBasedAuthentication auth = (PasswordBasedAuthentication) authentication;
            LoanUserAuth userAuth = getUserDetails(auth);
            if (userAuth != null) {
                auth.setUserAuth(userAuth);
                auth.setAuthenticated(true);
                return auth;
            } else {
                throw new AuthException("Invalid Username or Password!");
            }
        }catch (Exception ex){
            log.error("Exception:"+ex.getMessage());
            throw new AuthException("Failed to authenticate user!");
        }
    }

    private LoanUserAuth getUserDetails(PasswordBasedAuthentication auth){
        LoanUserAuth userAuth=auth.getUserAuth();
        Optional<LoanUser> user = userDao.findByUsername(userAuth.getUsername());
        if (user.isPresent() && user.get().getStatus().equals(UserStatus.ACTIVE)) {
            if(userAuth.getOtp().equals(user.get().getOtp())) {
                //to remove the password
                user.get().setOtp("");
                userAuth=new LoanUserAuth(user.get());
                return userAuth;
            }
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(PasswordBasedAuthentication.class);
    }
}
