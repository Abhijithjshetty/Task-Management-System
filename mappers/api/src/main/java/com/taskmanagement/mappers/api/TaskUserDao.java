package com.taskmanagement.mappers.api;

import com.taskmanagement.models.TaskUser;

import java.util.List;
import java.util.Optional;

public interface TaskUserDao {
    void insert(TaskUser taskUser);

    Optional<TaskUser> selectUserByEmail(String email);


    Optional<TaskUser> getUserByRegistrationId(String taskUserId);


    Long selectTotalCount(String taskUserId);

    List<TaskUser> selectRecordsInRange(String taskUserId, Long calculatedOffset, Long pageSize);

}
