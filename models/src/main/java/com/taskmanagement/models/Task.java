package com.taskmanagement.models;

import com.taskmanagement.models.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    private Long id;

    private String taskId;

    private String title;

    private String description;

    private TaskStatus status;

    private Date dueDate;

    private String createdBy;

    private String updatedBy;

    private String deletedBy;

    private Date createdOn;

    private Date updatedOn;

    private Date deletedOn;

}
