package com.taskmanagement.core.security.filters;

import com.taskmanagement.core.security.authentication.PasswordBasedAuthentication;
import com.taskmanagement.core.security.exception.AuthException;
import com.taskmanagement.core.security.models.UserAuth;
import com.taskmanagement.core.security.util.JWTUtil;
import com.taskmanagement.mappers.api.SessionDao;
import com.taskmanagement.mappers.api.UserDao;
import com.taskmanagement.models.Session;
import com.taskmanagement.models.User;
import com.taskmanagement.models.enums.UserStatus;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    UserDao userDao;

    @Autowired
    SessionDao sessionDao;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, ServletException {
        log.info("*******JWT Authentication Filter*******");
        try {
            String authToken = request.getHeader("Authorization");
            if (!StringUtils.isEmpty(authToken) && authToken.startsWith("Bearer")) {
                String token = authToken.substring(7);
                //if token is valid allow it
                if (jwtUtil.validateToken(token)) {
                    String username = jwtUtil.extractSubject(token);
                    Optional<User> user = userDao.findByUsername(username);
                    Optional<Session> session = sessionDao.getSession(username, token);
                    if (session.isPresent() && user.isPresent() && user.get().getStatus().equals(UserStatus.ACTIVE)) {
                        user.get().setPassword("");
                        UserAuth userAuth = new UserAuth(user.get());
                        Authentication auth = new PasswordBasedAuthentication(userAuth, true);
                        SecurityContext sc = SecurityContextHolder.getContext();
                        sc.setAuthentication(auth);
                    } else {
                        throw new AuthException("Session terminated!");
                    }
                } else {
                    throw new AuthException("Invalid Token!");
                }
            }
        } catch (Exception ex) {
            log.error("Exception:" + ex.toString());
        }
        filterChain.doFilter(request, response);
    }

}
