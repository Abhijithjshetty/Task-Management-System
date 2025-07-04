package com.sushikhacapitals.core.model.response;

import com.sushikhacapitals.models.enums.EmploymentType;
import com.sushikhacapitals.models.enums.Gender;
import com.sushikhacapitals.models.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.OptBoolean;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonalDetailsResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("personal_details_id")
    private String personalDetailsId;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", lenient = OptBoolean.FALSE)
    @JsonProperty("date_of_birth")
    private Date dob;

    @JsonProperty("employment_type")
    private EmploymentType employmentType;

    @JsonProperty("gender")
    private Gender gender;

    @JsonProperty("status")
    private UserStatus status;

    @JsonProperty("email_verified")
    private Boolean emailVerified;

    @JsonProperty("phone_verified")
    private Boolean phoneVerified;

    @JsonProperty("created_on")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "IST")
    private Date createdOn;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("updated_on")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "IST")
    private Date updatedOn;

    @JsonProperty("updated_by")
    private String updatedBy;
}