package com.sushikhacapitals.core.security.manager;

import com.sushikhacapitals.core.exception.Errors;
import com.sushikhacapitals.core.security.exception.AuthException;
import com.sushikhacapitals.core.security.provider.MemberAuthenticationProvider;
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
