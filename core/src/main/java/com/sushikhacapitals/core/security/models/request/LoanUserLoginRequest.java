package com.sushikhacapitals.core.security.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class LoanUserLoginRequest {
    @NotBlank
    @JsonProperty("otp_id")
    private String otpId;

    @NotNull
    @JsonProperty("otp_value")
    private String otpValue;
}
