package com.sushikhacapitals.core.model.request;

import com.sushikhacapitals.core.validator.annotations.EnumValidator;
import com.sushikhacapitals.models.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.OptBoolean;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationUpdateRequest {

    @Size(max = 250)
    @Pattern(regexp = "^[a-zA-Z ]+$")
    private String name;

    @Size(max = 256)
    @Pattern(regexp = "[1-9][0-9]{9}")
    private String phone;

    @Size(max = 256)
    @Email
    private String email;

    @JsonProperty("date_of_birth")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",lenient = OptBoolean.FALSE)
    private Date dob;

    @Size(min = 1, max = 100)
    @Pattern(regexp = "^[a-zA-Z ]+$")
    private String city;

    @Size(max = 10)
    @Pattern(regexp = "^\\d{1,10}$")
    private String pincode;

    @JsonProperty("status")
    @EnumValidator(enumClass = UserStatus.class)
    private UserStatus status;


}
