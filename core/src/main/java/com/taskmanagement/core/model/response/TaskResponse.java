package com.taskmanagement.core.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.OptBoolean;
import com.taskmanagement.models.enums.TaskStatus;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("task_id")
    private String taskId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("status")
    private TaskStatus status;

    @JsonProperty("due_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", lenient = OptBoolean.FALSE)
    private Date dueDate;

    @JsonProperty("created_on")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "IST")
    private Date createdOn;

    @JsonProperty("updated_on")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "IST")
    private Date updatedOn;

    @JsonProperty("deleted_on")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "IST")
    private Date deletedOn;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("updated_by")
    private String updatedBy;

    @JsonProperty("deleted_by")
    private String deletedBy;

}
