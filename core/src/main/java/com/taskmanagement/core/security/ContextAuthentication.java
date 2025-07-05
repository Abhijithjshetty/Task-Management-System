package com.taskmanagement.core.security;

import com.taskmanagement.core.security.models.UserAuth;
import com.taskmanagement.mappers.mysql.UserDao;
import com.taskmanagement.models.User;
import com.taskmanagement.models.enums.UserStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class ContextAuthentication {
    @Autowired
    UserDao userDao;

    public User getCurrentUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext.getAuthentication() != null && securityContext.getAuthentication().getDetails() != null) {
            UserAuth userDetails = (UserAuth) securityContext.getAuthentication().getDetails();
            Optional<User> user = userDao.findByUsername(userDetails.getUsername());

            if(user.isPresent()){
                User user1=user.get();
                if(user1.getStatus().equals(UserStatus.ACTIVE)){
                    user1.setPassword("");
                    return user1;
                }

            }else {
                log.error("Failed to fetch user based on email from security context");
            }
        }
        log.error("Failed to retrieve user from security context");
        return null;
    }


}
