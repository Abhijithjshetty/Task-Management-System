package com.taskmanagement.mappers.mysql;

import com.taskmanagement.models.Session;
import com.taskmanagement.models.enums.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.Optional;

public interface SessionDao extends com.taskmanagement.mappers.api.SessionDao {
    void create(@Param("userId") String userId, @Param("username") String username,
                @Param("userRole") UserRole userRole, @Param("token") String token, @Param("createdOn") Date createdOn,
                @Param("expiryAt") Date expiryAt);

    void clear(@Param("token") String token);

    Optional<Session> getSession(@Param("username") String username, @Param("token") String token);

    void clearByExpiryDate(@Param("username") String username,
                           @Param("date") Date date);

}