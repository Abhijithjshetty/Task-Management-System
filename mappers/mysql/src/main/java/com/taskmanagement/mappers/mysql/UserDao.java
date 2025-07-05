package com.taskmanagement.mappers.mysql;

import com.taskmanagement.models.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.Optional;

@Mapper
public interface UserDao extends com.taskmanagement.mappers.api.UserDao {

    void insertUser(@Param("user") User user);

    Optional<User> findByUsername(@Param("username") String username);

    Optional<User> findByUserId(String userId);

}