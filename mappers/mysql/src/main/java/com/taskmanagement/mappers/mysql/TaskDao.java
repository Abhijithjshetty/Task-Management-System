package com.taskmanagement.mappers.mysql;

import com.taskmanagement.models.Task;
import com.taskmanagement.models.enums.TaskStatus;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;


public interface TaskDao extends com.taskmanagement.mappers.api.TaskDao{
    void insert(@Param("task") Task task);

    void update(@Param("task") Task task);

    Long selectTotalCount(@Param("status") TaskStatus status, @Param("userId") String userId);

    List<Task> selectRecordsInRange(@Param("status") TaskStatus status,
                                    @Param("userId") String userId,
                                    @Param("calculatedOffset") Long calculatedOffset,
                                    @Param("pageSize") Long pageSize);

    Optional<Task> getTaskById(@Param("taskId") String taskId);

    void delete(@Param("task") Task task);

}
