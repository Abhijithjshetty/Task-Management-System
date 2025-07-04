package com.taskmanagement.models;

import com.taskmanagement.models.enums.UserRole;
import com.taskmanagement.models.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String userId;
    private String username;
    private String password;
    private UserStatus status;
    private UserRole userRole;
    private Date createdOn;
    private Date updatedOn;
}
