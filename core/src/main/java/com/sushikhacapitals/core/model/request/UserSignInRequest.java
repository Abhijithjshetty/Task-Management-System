package com.sushikhacapitals.core.model.request;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class UserSignInRequest {
    @NotBlank
    @JsonProperty("phone")
    private String phone;
}
