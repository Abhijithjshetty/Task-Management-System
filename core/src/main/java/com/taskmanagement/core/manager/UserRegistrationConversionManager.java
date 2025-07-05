package com.taskmanagement.core.manager;

import com.taskmanagement.core.config.Constants;
import com.taskmanagement.core.model.request.UserRegistrationRequest;
import com.taskmanagement.core.model.response.UserRegistrationResponse;
import com.taskmanagement.models.TaskUser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserRegistrationConversionManager {

    public static TaskUser mapUserRegistrationRequestToEntity(UserRegistrationRequest request){
        log.info(Constants.LOG_CLASS_USER_REGISTRATION_CONVERSION_MANAGER +"mapUserRegistrationRequestToEntity"+ ":started");
        TaskUser user =new TaskUser();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setDob(request.getDob());
        user.setGender(request.getGender());
        user.setRole(request.getRole());
        log.info(Constants.LOG_CLASS_USER_REGISTRATION_CONVERSION_MANAGER +"mapUserRegistrationRequestToEntity"+ ":ended");
        return  user;
    }

    public static UserRegistrationResponse mapUserEntityToRegistrationResponse(TaskUser taskUser) {
        log.info(Constants.LOG_CLASS_USER_REGISTRATION_CONVERSION_MANAGER +"mapUserEntityToRegistrationResponse"+ ":started");
        UserRegistrationResponse response = new UserRegistrationResponse();
        response.setId(taskUser.getId());
        response.setTaskUserId(taskUser.getTaskUserId());
        response.setName(taskUser.getName());
        response.setEmail(taskUser.getEmail());
        response.setDob(taskUser.getDob());
        response.setGender(taskUser.getGender());
        response.setRole(taskUser.getRole());
        response.setCreatedOn(taskUser.getCreatedOn());
        log.info(Constants.LOG_CLASS_USER_REGISTRATION_CONVERSION_MANAGER +"mapUserEntityToRegistrationResponse"+ ":ended");
        return response;
    }

}
