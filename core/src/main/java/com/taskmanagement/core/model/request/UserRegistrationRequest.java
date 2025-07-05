package com.taskmanagement.core.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.OptBoolean;
import com.taskmanagement.core.validator.annotations.EnumValidator;
import com.taskmanagement.models.enums.Gender;
import com.taskmanagement.models.enums.UserRole;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationRequest {

    @Size(max = 250)
    @NotBlank()
    @Pattern(regexp = "^[a-zA-Z ]+$")
    private String name;

    @Size(max = 256)
    @NotBlank()
    @Email
    private String email;

    @JsonProperty("date_of_birth")
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",lenient = OptBoolean.FALSE)
    private Date dob;

    @NotNull
    @JsonProperty("gender")
    @EnumValidator(enumClass = Gender.class)
    private Gender gender;

    @NotBlank
    @JsonProperty("password")
    @Pattern(regexp = "^(?!.*\\s)(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$#%!@]).{6,20}$")
    private String password;

    @JsonProperty("role")
    @EnumValidator(enumClass = UserRole.class)
    private UserRole role;

}
