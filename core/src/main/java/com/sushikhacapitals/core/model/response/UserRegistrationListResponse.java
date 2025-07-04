package com.sushikhacapitals.core.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRegistrationListResponse {
    @JsonProperty("user_registration_list")
    List<UserRegistrationResponse> userRegistrationResponseList;
    private Long total;
    private Long offset;
    @JsonProperty("page_size")
    private Long pageSize;
}
