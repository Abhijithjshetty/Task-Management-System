package com.taskmanagement.core.security.manager;

import com.taskmanagement.core.exception.Errors;
import com.taskmanagement.core.security.exception.AuthException;
import com.taskmanagement.core.security.provider.MemberAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class MemberAuthenticationManager implements AuthenticationManager {
    @Autowired
    MemberAuthenticationProvider authenticationProvider;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if(authenticationProvider.supports(authentication.getClass())){
            return authenticationProvider.authenticate(authentication);
        }
        throw new AuthException(Errors.AUTH_EXCEPTION.getMessage());
    }
}
