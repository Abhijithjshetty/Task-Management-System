package com.taskmanagement.mappers.api;


import com.taskmanagement.models.User;

import java.util.Optional;

public interface UserDao {
    void insertUser(User user);

    Optional<User> findByUsername(String username);

    Optional<User> findByUserId(String userId);

}
