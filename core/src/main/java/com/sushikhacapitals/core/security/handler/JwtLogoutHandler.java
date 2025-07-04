package com.sushikhacapitals.core.security.handler;

import com.sushikhacapitals.core.security.util.JWTUtil;
import com.taskmanagement.mappers.api.SessionDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;


@Slf4j
public class JwtLogoutHandler implements LogoutHandler {
    @Autowired
    private SessionDao sessionDao;

    @Autowired
    private JWTUtil jwtUtil;
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        try {
            String authToken = request.getHeader("Authorization");
            if (!StringUtils.isEmpty(authToken)
            && authToken.startsWith("Bearer ")
            ) {
                String token = authToken.substring(7);
                sessionDao.clear(token);
                log.info("Session cleared!");
                if (authentication != null) {
                    new SecurityContextLogoutHandler().logout(request, response, authentication);
                }
            }
        }catch (Exception ex){
            log.error("Exception :"+ex.getMessage());
        }
    }
}
