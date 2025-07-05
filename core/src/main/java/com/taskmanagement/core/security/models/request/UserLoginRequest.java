package com.taskmanagement.core.security.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class UserLoginRequest {
    @NotBlank
    @JsonProperty("username")
    private String username;

    @NotBlank
    @JsonProperty("password")
    private String password;
}
