package com.taskmanagement.mappers.api;

import com.taskmanagement.models.Task;
import com.taskmanagement.models.enums.TaskStatus;

import java.util.List;
import java.util.Optional;


public interface TaskDao {
    void insert(Task task);

    void update(Task task);

    Long selectTotalCount(TaskStatus status, String userId);

    List<Task> selectRecordsInRange(TaskStatus status, String userId, Long calculatedOffset, Long pageSize);

    Optional<Task> getTaskById(String taskId);

    void delete(Task task);

}
