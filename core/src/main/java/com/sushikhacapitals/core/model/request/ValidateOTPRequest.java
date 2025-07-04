package com.sushikhacapitals.core.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ValidateOTPRequest {
    @NotBlank
    @JsonProperty("otp_id")
    private String otpId;
    @NotNull
    @JsonProperty("otp_value")
    private String otpValue;

}
