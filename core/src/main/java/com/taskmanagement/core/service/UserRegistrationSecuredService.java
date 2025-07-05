
package com.taskmanagement.core.service;


import com.taskmanagement.core.config.Constants;
import com.taskmanagement.core.manager.UserRegistrationManager;
import com.taskmanagement.core.model.response.UserRegistrationListResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
@SecurityRequirement(name="jwtAuthentication")
public class UserRegistrationSecuredService {

    @Autowired
    private UserRegistrationManager userRegistrationManager;

    @GetMapping()
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public UserRegistrationListResponse getUserList(
            @RequestParam(value = "page_size", required = false) Long pageSize,
            @RequestParam(value = "offset", required = false) Long offset){
        log.info(Constants.LOG_CLASS_USER_REGISTRATION_SERVICE + "getUserList" + ":started");
        UserRegistrationListResponse response = userRegistrationManager.getUserList(offset, pageSize);
        log.info(Constants.LOG_CLASS_USER_REGISTRATION_SERVICE + "getUserList" + ":ended");
        return response;
    }


}
