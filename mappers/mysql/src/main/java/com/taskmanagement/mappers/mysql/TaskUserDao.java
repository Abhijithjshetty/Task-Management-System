package com.taskmanagement.mappers.mysql;

import com.taskmanagement.models.TaskUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

public interface TaskUserDao extends com.taskmanagement.mappers.api.TaskUserDao {
    void insert(@Param("taskUser") TaskUser taskUser);

    Optional<TaskUser> selectUserByEmail(@Param("email") String email);

    Optional<TaskUser> getUserByRegistrationId(@Param("taskUserId") String taskUserId);

    Long selectTotalCount(@Param("taskUserId") String taskUserId);

    List<TaskUser> selectRecordsInRange(@Param("taskUserId") String taskUserId,
                                            @Param("calculatedOffset") Long calculatedOffset,
                                            @Param("pageSize") Long pageSize);




}
