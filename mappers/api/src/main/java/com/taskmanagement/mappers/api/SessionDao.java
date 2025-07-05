package com.taskmanagement.mappers.api;


import com.taskmanagement.models.Session;
import com.taskmanagement.models.enums.UserRole;

import java.util.Date;
import java.util.Optional;

public interface SessionDao {
    void create(String userId, String username, UserRole userRole, String token, Date createdOn, Date expiryAt);

    void clear(String token);

    Optional<Session> getSession(String subject, String token);

    void clearByExpiryDate(String username, Date date);
}