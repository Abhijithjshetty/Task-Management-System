package com.sushikhacapitals.core.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class ContactVerificationOTPRequest {

    @NotBlank()
    @Email
    @JsonProperty("email")
    private String email;

}
