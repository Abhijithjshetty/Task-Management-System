package com.sushikhacapitals.core.model.request;

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
public class UserRegistrationRequest {

    @Size(max = 250)
    @NotBlank()
    @Pattern(regexp = "^[a-zA-Z ]+$")
    private String name;

    @Size(max = 256)
    @NotBlank()
    @Pattern(regexp = "[1-9][0-9]{9}")
    private String phone;

    @Size(max = 256)
    @NotBlank()
    @Email
    private String email;

    @JsonProperty("date_of_birth")
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",lenient = OptBoolean.FALSE)
    private Date dob;

    @NotBlank
    @Size(min = 1, max = 100)
    @Pattern(regexp = "^[a-zA-Z ]+$")
    private String city;

    @NotBlank
    @Size(max = 10)
    @Pattern(regexp = "^\\d{1,10}$")
    private String pincode;

}
