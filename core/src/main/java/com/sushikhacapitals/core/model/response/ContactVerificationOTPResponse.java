package com.sushikhacapitals.core.model.response;


import com.sushikhacapitals.models.enums.EntityType;
import com.sushikhacapitals.models.enums.OTPMediumType;
import com.sushikhacapitals.models.enums.OTPStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContactVerificationOTPResponse {
    @JsonProperty("otp_id")
    private String otpId;

    @JsonProperty("entity_id")
    private String entityId;

    @JsonProperty("value")
    private String value;

    @JsonProperty("created_on")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "IST")
    private Date createdOn;

    @JsonProperty("expiry_on")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "IST")
    private Date expiryOn;

    @JsonProperty("medium_type")
    private OTPMediumType mediumType;

    @JsonProperty("entity_type")
    private EntityType entityType;

    @JsonProperty("status")
    private OTPStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "IST")
    @JsonProperty("updated_on")
    private Date updatedOn;


}
