package com.taskmanagement.models;

import com.taskmanagement.models.enums.UserStatus;
import lombok.Data;
import java.util.Date;

@Data
public class TaskUser {
    private Long id;
    private String taskUserId;
    private String name;
    private String email;
    private String phone;
    private Date dob;
    private UserStatus status;
    private Date createdOn;
    private Date updatedOn;
}