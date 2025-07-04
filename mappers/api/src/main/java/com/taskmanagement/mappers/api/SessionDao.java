package com.taskmanagement.mappers.api;

import com.sushikhacapitals.models.Session;
import com.sushikhacapitals.models.enums.UserRole;

import java.util.Date;
import java.util.Optional;

public interface SessionDao {
    void create(String userId, String username, UserRole userRole, String token, Date createdOn, Date expiryAt);

    void clear(String token);

    Optional<Session> getSession(String subject, String token);

    void clearByExpiryDate(String username, Date date);
}