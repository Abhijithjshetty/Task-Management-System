package com.taskmanagement.core.manager;

import com.taskmanagement.core.config.Constants;
import com.taskmanagement.core.model.request.TaskRequest;
import com.taskmanagement.core.model.response.TaskResponse;
import com.taskmanagement.models.Task;
import com.taskmanagement.models.User;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class TaskConversionManager {

    public static Task mapTaskRequestToEntity(TaskRequest request) {
        log.info(Constants.LOG_CLASS_TASK_CONVERSION_MANAGER + " mapTaskRequestToEntity : started");
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        log.info(Constants.LOG_CLASS_TASK_CONVERSION_MANAGER + " mapTaskRequestToEntity : ended");
        return task;
    }

    public static TaskResponse mapTaskToTaskResponse(Task task) {
        log.info(Constants.LOG_CLASS_TASK_CONVERSION_MANAGER + " mapTaskToTaskResponse : started");
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTaskId(task.getTaskId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus());
        response.setDueDate(task.getDueDate());
        response.setCreatedOn(task.getCreatedOn());
        response.setCreatedBy(task.getCreatedBy());
        response.setUpdatedOn(task.getUpdatedOn());
        response.setUpdatedBy(task.getUpdatedBy());
        response.setDeletedOn(task.getDeletedOn());
        response.setDeletedBy(task.getDeletedBy());
        log.info(Constants.LOG_CLASS_TASK_CONVERSION_MANAGER + " mapTaskToTaskResponse : ended");
        return response;
    }
}
