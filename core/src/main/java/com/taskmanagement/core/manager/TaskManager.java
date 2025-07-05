package com.taskmanagement.core.manager;

import com.taskmanagement.core.config.Constants;
import com.taskmanagement.core.exception.BusinessException;
import com.taskmanagement.core.exception.Errors;
import com.taskmanagement.core.model.request.TaskRequest;
import com.taskmanagement.core.model.request.TaskUpdateRequest;
import com.taskmanagement.core.model.response.TaskListResponse;
import com.taskmanagement.core.model.response.TaskResponse;
import com.taskmanagement.core.security.ContextAuthentication;
import com.taskmanagement.mappers.api.TaskDao;
import com.taskmanagement.mappers.api.UserDao;
import com.taskmanagement.models.Task;
import com.taskmanagement.models.User;
import com.taskmanagement.models.enums.TaskStatus;
import com.taskmanagement.models.enums.UuidPrefix;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;

@Slf4j
@Component
@AllArgsConstructor
public class TaskManager {

    @Autowired
    private ContextAuthentication authentication;
    @Autowired
    private TaskDao taskDao;
    @Autowired
    private UserDao userDao;

    public TaskResponse createTask(TaskRequest request) {
        log.info(Constants.LOG_CLASS_TASK_MANAGER + " createTask : started");
        User user = authentication.getCurrentUser();
        Task task = TaskConversionManager.mapTaskRequestToEntity(request);
        task.setTaskId(UuidPrefix.TASK + "-" + UUID.randomUUID());
        task.setStatus(TaskStatus.PENDING);
        task.setCreatedOn(new Date());
        task.setCreatedBy(user.getUserId());
        taskDao.insert(task);
        log.info(Constants.LOG_CLASS_TASK_MANAGER + " createTask : ended");
        TaskResponse response = TaskConversionManager.mapTaskToTaskResponse(task);
        return response;
    }

    public TaskResponse updateTask(String taskId, TaskUpdateRequest request) {
        log.info(Constants.LOG_CLASS_TASK_MANAGER + " updateTask : started");
        final User user = authentication.getCurrentUser();
        Optional<Task> taskOptional = taskDao.getTaskById(taskId);
        if (taskOptional.isEmpty()) {
            throw new BusinessException(Errors.TASK_ID_DOES_NOT_EXIST);
        }
        Task task = taskOptional.get();
        task.setStatus(request.getStatus());
        task.setUpdatedBy(user.getUserId());
        task.setUpdatedOn(new Date());

        taskDao.update(task);
        log.info(Constants.LOG_CLASS_TASK_MANAGER + " updateTask : ended");
        TaskResponse response = TaskConversionManager.mapTaskToTaskResponse(task);
        return response;
    }

    public TaskListResponse getTaskById(Long pageSize, Long offset) {
        try {
            log.info(Constants.LOG_CLASS_TASK_MANAGER + "getCalenderList" + ": started");
            final User user = authentication.getCurrentUser();
            String userId = user.getUserId();
            TaskListResponse response = new TaskListResponse();
            Long totalCount = taskDao.selectTotalCount(userId);
            response.setTotal(totalCount);
            if (totalCount == 0) {
                log.info(Constants.LOG_CLASS_TASK_MANAGER + "getCalenderList" + ": ended");
                return response;
            }
            Long calculatedOffset = (offset != null && pageSize != null) ? (offset > 0 ? offset * pageSize : offset) : null;
            List<Task> records = taskDao.selectRecordsInRange(userId, calculatedOffset, pageSize);
            List<TaskResponse> responses = new ArrayList<>();
            for (Task record : records) {
                responses.add(TaskConversionManager.mapTaskToTaskResponse(record));
            }
            response.setOffset(offset);
            response.setPageSize(pageSize);
            response.setTaskResponseList(responses);
            log.info(Constants.LOG_CLASS_TASK_MANAGER + "getCalenderList" + ": ended");
            return response;
        } catch (BusinessException ex) {
            log.error(Constants.LOG_CLASS_TASK_MANAGER + " getCalenderList: Business Exception: " + ex.toString());
            throw ex;
        }
    }

    public TaskListResponse getAllTasks(Long pageSize, Long offset) {
        try {
            log.info(Constants.LOG_CLASS_TASK_MANAGER + "getCalenderList" + ": started");
            final User user = authentication.getCurrentUser();
            TaskListResponse response = new TaskListResponse();
            Long totalCount = taskDao.selectTotalCount(null);
            response.setTotal(totalCount);
            if (totalCount == 0) {
                log.info(Constants.LOG_CLASS_TASK_MANAGER + "getCalenderList" + ": ended");
                return response;
            }
            Long calculatedOffset = (offset != null && pageSize != null) ? (offset > 0 ? offset * pageSize : offset) : null;
            List<Task> records = taskDao.selectRecordsInRange(null, calculatedOffset, pageSize);
            List<TaskResponse> responses = new ArrayList<>();
            for (Task record : records) {
                responses.add(TaskConversionManager.mapTaskToTaskResponse(record));
            }
            response.setOffset(offset);
            response.setPageSize(pageSize);
            response.setTaskResponseList(responses);
            log.info(Constants.LOG_CLASS_TASK_MANAGER + "getCalenderList" + ": ended");
            return response;
        } catch (BusinessException ex) {
            log.error(Constants.LOG_CLASS_TASK_MANAGER + " getCalenderList: Business Exception: " + ex.toString());
            throw ex;
        }
    }

    public TaskResponse deleteTask(String taskId) {
        try {
            log.info(Constants.LOG_CLASS_TASK_MANAGER + "deleteTask" + ":started");
            final User user = authentication.getCurrentUser();
            Optional<Task> taskOptional = taskDao.getTaskById(taskId);
            if (taskOptional.isEmpty()) {
                throw new BusinessException(Errors.TASK_ID_DOES_NOT_EXIST);
            }
            Task task = taskOptional.get();
            task.setStatus(TaskStatus.DELETED);
            task.setDeletedOn(new Date());
            task.setDeletedBy(user.getUserId());
            taskDao.delete(task);
            TaskResponse response = TaskConversionManager.mapTaskToTaskResponse(task);
            log.info(Constants.LOG_CLASS_TASK_MANAGER + "deleteTask" + ": ended");
            return response;
        } catch (BusinessException ex) {
            log.error(Constants.LOG_CLASS_TASK_MANAGER + "deleteTask: Business Exception: " + ex);
            throw ex;
        }
    }
}
