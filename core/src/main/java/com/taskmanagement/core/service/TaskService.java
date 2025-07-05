package com.taskmanagement.core.service;

import com.taskmanagement.core.config.Constants;
import com.taskmanagement.core.manager.TaskManager;
import com.taskmanagement.core.model.request.TaskRequest;
import com.taskmanagement.core.model.request.TaskUpdateRequest;
import com.taskmanagement.core.model.response.TaskListResponse;
import com.taskmanagement.core.model.response.TaskResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/task")
@Slf4j
@AllArgsConstructor
@SecurityRequirement(name = "jwtAuthentication")
public class TaskService {

    @Autowired
    private TaskManager manager;

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Create Task", description = "Create a new task")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public TaskResponse createTask(@RequestBody @Valid TaskRequest request) {
        log.info(Constants.LOG_CLASS_TASK_SERVICE + " createTask : started");
        TaskResponse response = manager.createTask(request);
        log.info(Constants.LOG_CLASS_TASK_SERVICE + " createTask : ended");
        return response;
    }

    @PutMapping("/{taskId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Update Task", description = "Update an existing task by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task updated successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    public TaskResponse updateTask(@PathVariable String taskId,
                                   @RequestBody @Valid TaskUpdateRequest request) {
        log.info(Constants.LOG_CLASS_TASK_SERVICE + " updateTask : started");
        TaskResponse response = manager.updateTask(taskId, request);
        log.info(Constants.LOG_CLASS_TASK_SERVICE + " updateTask : ended");
        return response;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Get Tasks", description = "Get all tasks for user or admin")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved tasks")
    })
    public TaskListResponse getAllTasks(@RequestParam(value = "page_size", required = false) Long pageSize,
                                        @RequestParam(value = "offset", required = false) Long offset) {
        log.info(Constants.LOG_CLASS_TASK_SERVICE + " getAllTasks : started");
        TaskListResponse records = manager.getAllTasks(pageSize, offset);
        log.info(Constants.LOG_CLASS_TASK_SERVICE + " getAllTasks : ended");
        return records;
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @Operation(summary = "Get Task by ID", description = "Get a single user task by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task found"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    public TaskListResponse getTaskById(@RequestParam(value = "page_size", required = false) Long pageSize,
                                        @RequestParam(value = "offset", required = false) Long offset) {
        log.info(Constants.LOG_CLASS_TASK_SERVICE + " getTaskById : started");
        TaskListResponse response = manager.getTaskById(pageSize, offset);
        log.info(Constants.LOG_CLASS_TASK_SERVICE + " getTaskById : ended");
        return response;
    }

    @DeleteMapping("/{taskId}")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Delete Task", description = "Delete a task by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    public TaskResponse deleteTask(@PathVariable String taskId) {
        log.info(Constants.LOG_CLASS_TASK_SERVICE + " deleteTask : started");
        TaskResponse response = manager.deleteTask(taskId);
        log.info(Constants.LOG_CLASS_TASK_SERVICE + " deleteTask : ended");
        return response;
    }
}
