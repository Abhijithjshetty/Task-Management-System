package com.sushikhacapitals.core.model.request;

import com.sushikhacapitals.core.validator.annotations.EnumValidator;
import com.sushikhacapitals.models.enums.Country;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddressRequest {
    @NotBlank
    @Size(min = 1, max = 100)
    @Pattern(regexp = "^[a-zA-Z0-9\\s,./]{1,}$")
    @JsonProperty("house_number")
    private String houseNumber;

    @NotBlank
    @Size(min = 1, max = 100)
    @Pattern(regexp = "^[a-zA-Z0-9\\s,./]{1,}$")
    private String area;

    @NotBlank
    @Size(min = 1, max = 100)
    @Pattern(regexp = "^[a-zA-Z ]+$")
    private String city;

    @NotBlank
    @Size(min = 1, max = 100)
    @Pattern(regexp = "^[a-zA-Z ]+$")
    private String state;

    @NotNull
    @EnumValidator(enumClass = Country.class)
    private Country country;

    @NotBlank
    @Size(max = 10)
    @Pattern(regexp = "^\\d{1,10}$")
    private String pincode;
}
