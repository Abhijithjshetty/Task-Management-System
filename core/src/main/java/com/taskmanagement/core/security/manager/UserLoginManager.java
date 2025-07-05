package com.taskmanagement.core.security.manager;

import com.taskmanagement.core.config.Constants;
import com.taskmanagement.core.exception.CustomAuthenticationException;
import com.taskmanagement.core.security.authentication.PasswordBasedAuthentication;

import com.taskmanagement.core.security.models.UserAuth;
import com.taskmanagement.core.security.models.request.UserLoginRequest;
import com.taskmanagement.core.security.util.JWTUtil;
import com.taskmanagement.mappers.api.SessionDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class UserLoginManager {

    @Autowired
    SessionDao sessionDao;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    public UserAuth userLogin(UserLoginRequest userLoginRequest) {
        try {
            log.info(Constants.LOG_CLASS_USER_LOGIN_MANAGER + " loginUser: started");
            UserAuth TUAuth = new UserAuth();
            TUAuth.setUsername(userLoginRequest.getUsername());
            TUAuth.setPassword(userLoginRequest.getPassword());
            PasswordBasedAuthentication auth = new PasswordBasedAuthentication(TUAuth, false);

            Authentication authentication = authenticationManager.authenticate(auth);
            if (authentication.isAuthenticated()) {
                UserAuth userAuth = (UserAuth) authentication.getDetails();
                Map<String, Object> claims = new HashMap<>();
                claims.put("USERNAME", userAuth.getUsername());
                String token = jwtUtil.generateToken(userAuth.getUsername(), claims);
                TUAuth.setToken(token);
                sessionDao.clear(userAuth.getUsername());
                sessionDao.create(userAuth.getUserId(), userAuth.getUsername(), userAuth.getRole(),
                        token, jwtUtil.extractIssuedAt(token),
                        jwtUtil.extractExpiration(token));
                UserAuth response = ((PasswordBasedAuthentication) authentication).getUserAuth();
                response.setToken(token);
                log.info(Constants.LOG_CLASS_USER_LOGIN_MANAGER + " loginUser: ended");
                return response;
            }
            throw new CustomAuthenticationException();
        } catch (AuthenticationException ex) {
            throw new CustomAuthenticationException();
        }
    }
}