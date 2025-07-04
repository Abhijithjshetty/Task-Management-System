package com.sushikhacapitals.core.model.response;

import com.sushikhacapitals.models.enums.Country;
import lombok.Data;

@Data
public class AddressResponse {
    private String houseNumber;
    private String area;
    private String city;
    private String state;
    private Country country;
    private String pincode;
}
