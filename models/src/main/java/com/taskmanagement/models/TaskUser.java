package com.taskmanagement.models;

import com.taskmanagement.models.enums.Gender;
import com.taskmanagement.models.enums.UserRole;
import com.taskmanagement.models.enums.UserStatus;
import lombok.Data;
import java.util.Date;

@Data
public class TaskUser {
    private Long id;
    private String taskUserId;
    private String name;
    private String email;
    private Gender gender;
    private Date dob;
    private UserRole role;
    private UserStatus status;
    private Date createdOn;
}