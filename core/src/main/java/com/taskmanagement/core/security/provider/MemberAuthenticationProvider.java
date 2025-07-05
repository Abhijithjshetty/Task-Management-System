package com.taskmanagement.core.security.provider;


import com.taskmanagement.common.security.AESUtil;

import com.taskmanagement.core.security.authentication.PasswordBasedAuthentication;
import com.taskmanagement.core.security.exception.AuthException;
import com.taskmanagement.core.security.models.UserAuth;
import com.taskmanagement.core.utils.HashingUtil;
import com.taskmanagement.mappers.api.TaskUserDao;
import com.taskmanagement.mappers.api.UserDao;

import com.taskmanagement.models.TaskUser;
import com.taskmanagement.models.User;
import com.taskmanagement.models.enums.UserStatus;
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
    UserDao userDao;

    @Autowired
    HashingUtil hashingUtil;
    //todo: make it generic later as per user type
    @Autowired
    TaskUserDao taskUserDao;

    @Autowired
    AESUtil aesUtil;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            PasswordBasedAuthentication auth = (PasswordBasedAuthentication) authentication;
            UserAuth userAuth = getUserDetails(auth);
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

    private UserAuth getUserDetails(PasswordBasedAuthentication auth){
        UserAuth userAuth=auth.getUserAuth();
        Optional<User> user = userDao.findByUsername(userAuth.getUsername());
        if (user.isPresent() && user.get().getStatus().equals(UserStatus.ACTIVE)) {
            if(userAuth.getPassword().equals(aesUtil.decrypt(user.get().getPassword()))) {
                //to remove the password
                user.get().setPassword("");
                userAuth=new UserAuth(user.get());
                return userAuth;
            }
        }else if(!user.isPresent()){
            //fetching by email
            Optional<TaskUser> taskUser=taskUserDao.selectUserByEmail(hashingUtil.generateHash(userAuth.getUsername()));
            if(taskUser.isPresent()){
                user = userDao.findByUserId(taskUser.get().getTaskUserId());
                if (user.isPresent() && user.get().getStatus().equals(UserStatus.ACTIVE)) {
                    if(userAuth.getPassword().equals(aesUtil.decrypt(user.get().getPassword()))) {
                        //to remove the password
                        user.get().setPassword("");
                        userAuth=new UserAuth(user.get());
                        return userAuth;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(PasswordBasedAuthentication.class);
    }
}
