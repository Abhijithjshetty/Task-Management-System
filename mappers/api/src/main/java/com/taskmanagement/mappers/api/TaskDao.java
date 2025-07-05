package com.taskmanagement.mappers.api;

import com.taskmanagement.models.Task;
import java.util.List;
import java.util.Optional;


public interface TaskDao {
    void insert(Task task);

    void update(Task task);

    Long selectTotalCount(String userId);

    List<Task> selectRecordsInRange(String userId, Long calculatedOffset, Long pageSize);

    Optional<Task> getTaskById(String taskId);

    void delete(Task task);

}
