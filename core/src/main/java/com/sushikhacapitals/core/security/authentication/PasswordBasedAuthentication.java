package com.sushikhacapitals.core.security.authentication;

import com.sushikhacapitals.core.security.models.LoanUserAuth;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


@Data
@Slf4j
public class PasswordBasedAuthentication implements Authentication {

    private LoanUserAuth userAuth;

    private boolean isAuthenticated;

    private String ipAddress;


    public PasswordBasedAuthentication(LoanUserAuth userAuth, boolean isAuthenticated) {
        this.userAuth = userAuth;
        this.isAuthenticated = isAuthenticated;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userAuth.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return userAuth;
    }

    @Override
    public Object getPrincipal() {
        return userAuth.getUsername();
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean b){
        this.isAuthenticated = b;
    }

    @Override
    public String getName() {
        return null;
    }


}
