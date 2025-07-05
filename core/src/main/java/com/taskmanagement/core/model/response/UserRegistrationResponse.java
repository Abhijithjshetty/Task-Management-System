package com.taskmanagement.core.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.OptBoolean;
import com.taskmanagement.models.enums.Gender;
import com.taskmanagement.models.enums.UserRole;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRegistrationResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("task_user_id")
    private String taskUserId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",lenient = OptBoolean.FALSE)
    @JsonProperty("date_of_birth")
    private Date dob;

    @JsonProperty("gender")
    private Gender gender;

    @JsonProperty("role")
    private UserRole role;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "IST")
    @JsonProperty("created_on")
    private Date createdOn;

}
