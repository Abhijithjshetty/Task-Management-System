package com.sushikhacapitals.core.security;

import com.sushikhacapitals.core.security.models.LoanUserAuth;
import com.taskmanagement.mappers.mysql.LoanUserDao;
import com.sushikhacapitals.models.LoanUser;
import com.sushikhacapitals.models.enums.UserStatus;
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
    LoanUserDao userDao;

    public LoanUser getCurrentUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext.getAuthentication() != null && securityContext.getAuthentication().getDetails() != null) {
            LoanUserAuth userDetails = (LoanUserAuth) securityContext.getAuthentication().getDetails();
            Optional<LoanUser> user = userDao.findByUsername(userDetails.getUsername());

            if(user.isPresent()){
                LoanUser user1=user.get();
                if(user1.getStatus().equals(UserStatus.ACTIVE)){
                    user1.setOtp("");
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
