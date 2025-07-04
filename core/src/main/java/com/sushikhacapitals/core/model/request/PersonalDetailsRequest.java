package com.sushikhacapitals.core.model.request;

import com.sushikhacapitals.core.validator.annotations.EnumValidator;
import com.sushikhacapitals.models.enums.EmploymentType;
import com.sushikhacapitals.models.enums.Gender;
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
public class PersonalDetailsRequest {

    @NotBlank()
    @Pattern(regexp = "^[a-zA-Z ]+$")
    @JsonProperty("first_name")
    private String firstName;

    @NotBlank()
    @Pattern(regexp = "^[a-zA-Z ]+$")
    @JsonProperty("last_name")
    private String lastName;

    @NotNull
    @JsonProperty("date_of_birth")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",lenient = OptBoolean.FALSE)
    private Date dob;

    @NotNull
    @JsonProperty("gender")
    @EnumValidator(enumClass = Gender.class)
    private Gender gender;

    @NotNull
    @JsonProperty("employment_type")
    @EnumValidator(enumClass = EmploymentType.class)
    private EmploymentType employmentType;
}
