
package com.sushikhacapitals.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sushikhacapitals.core.config.Constants;
import com.sushikhacapitals.core.exception.GenericFieldValidationException;
import com.sushikhacapitals.core.manager.UserRegistrationManager;
import com.sushikhacapitals.core.model.request.UserRegistrationUpdateRequest;
import com.sushikhacapitals.core.model.response.UserRegistrationListResponse;
import com.sushikhacapitals.core.model.response.UserRegistrationResponse;
import com.sushikhacapitals.core.validator.UserValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
@SecurityRequirement(name="jwtAuthentication")
public class UserRegistrationSecuredService {

    @Autowired
    private UserRegistrationManager userRegistrationManager;

    @Autowired
    private ObjectMapper objectMapper;

    @PutMapping()
    @PreAuthorize("hasAuthority('USER')")
    public UserRegistrationResponse updateOwner(@RequestParam(value="payload", required = false) String payload,
                                                @RequestParam(value="profile_pic", required=false) MultipartFile profilePic) {
        try {
            log.info(Constants.LOG_CLASS_USER_REGISTRATION_SERVICE + "updateUser" + ":started");
            UserRegistrationUpdateRequest userRegistrationUpdateRequest = objectMapper.readValue(payload, UserRegistrationUpdateRequest.class);
            UserValidator.fieldValidationOwnerRegistrationRequest1(userRegistrationUpdateRequest);
            UserRegistrationResponse response = userRegistrationManager.updateUser(userRegistrationUpdateRequest, profilePic);
            log.info(Constants.LOG_CLASS_USER_REGISTRATION_SERVICE + "updateUser" + ":ended");
            return response;
        } catch (JsonProcessingException e) {
            log.error("Exception: {}", e.toString());
            throw new GenericFieldValidationException();
        }
    }

    @GetMapping("/profile_pic")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Get Image", description = "Retrieves an Image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Image not found")
    })
    public ResponseEntity<byte[]> getImage() throws IOException {
        log.info(Constants.LOG_CLASS_USER_REGISTRATION_SERVICE + "getImage" + ":started");
        ResponseEntity<byte[]> response = userRegistrationManager.getImage();
        log.info(Constants.LOG_CLASS_USER_REGISTRATION_SERVICE + "getImage" + ":ended");
        return response;
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('USER')")
    public UserRegistrationListResponse getUserList(
            @RequestParam(value = "page_size", required = false) Long pageSize,
            @RequestParam(value = "offset", required = false) Long offset){
        log.info(Constants.LOG_CLASS_USER_REGISTRATION_SERVICE + "getUserList" + ":started");
        UserRegistrationListResponse response = userRegistrationManager.getUserList(offset, pageSize);
        log.info(Constants.LOG_CLASS_USER_REGISTRATION_SERVICE + "getUserList" + ":ended");
        return response;
    }


}
