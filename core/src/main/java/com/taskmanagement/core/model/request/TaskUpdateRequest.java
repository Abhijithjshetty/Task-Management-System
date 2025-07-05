package com.taskmanagement.core.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.taskmanagement.core.validator.annotations.EnumValidator;
import com.taskmanagement.models.enums.TaskStatus;
import lombok.Data;


@Data
public class TaskUpdateRequest {

    @JsonProperty("status")
    @EnumValidator(enumClass = TaskStatus.class)
    private TaskStatus status;

}
