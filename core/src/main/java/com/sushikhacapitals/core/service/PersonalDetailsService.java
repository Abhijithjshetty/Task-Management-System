package com.sushikhacapitals.core.service;

import com.sushikhacapitals.core.config.Constants;
import com.sushikhacapitals.core.manager.PersonalDetailsManager;
import com.sushikhacapitals.core.model.request.PersonalDetailsRequest;
import com.sushikhacapitals.core.model.response.PersonalDetailsResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/personal_details")
@Slf4j
@SecurityRequirement(name="jwtAuthentication")
public class PersonalDetailsService {
    @Autowired
    private PersonalDetailsManager manager;

    @PostMapping()
    @PreAuthorize("hasAuthority('USER')")
    public PersonalDetailsResponse personalDetails(@RequestBody @Valid PersonalDetailsRequest request) {
        log.info(Constants.LOG_CLASS_PERSONAL_DETAILS_SERVICE +"personalDetails"+ ":started");
        PersonalDetailsResponse response= manager.personalDetails(request);
        log.info(Constants.LOG_CLASS_PERSONAL_DETAILS_SERVICE+"personalDetails"+ ":ended");
        return response;
    }


}