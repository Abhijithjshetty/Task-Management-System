package com.sushikhacapitals.core.manager;

import com.sushikhacapitals.core.config.Constants;
import com.sushikhacapitals.core.model.request.UserRegistrationRequest;
import com.sushikhacapitals.core.model.response.UserRegistrationResponse;
import com.sushikhacapitals.models.SushikhaUser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserRegistrationConversionManager {

    public static SushikhaUser mapUserRegistrationRequestToEntity(UserRegistrationRequest request){
        log.info(Constants.LOG_CLASS_USER_REGISTRATION_CONVERSION_MANAGER +"mapUserRegistrationRequestToEntity"+ ":started");
        SushikhaUser sushikhaUser =new SushikhaUser();
        sushikhaUser.setName(request.getName());
        sushikhaUser.setEmail(request.getEmail());
        sushikhaUser.setPhone(request.getPhone());
        sushikhaUser.setDob(request.getDob());
        sushikhaUser.setCity(request.getCity());
        sushikhaUser.setPincode(request.getPincode());
        log.info(Constants.LOG_CLASS_USER_REGISTRATION_CONVERSION_MANAGER +"mapUserRegistrationRequestToEntity"+ ":ended");
        return  sushikhaUser;
    }

    public static UserRegistrationResponse mapUserEntityToRegistrationResponse(SushikhaUser sushikhaUser) {
        log.info(Constants.LOG_CLASS_USER_REGISTRATION_CONVERSION_MANAGER +"mapUserEntityToRegistrationResponse"+ ":started");
        UserRegistrationResponse response = new UserRegistrationResponse();
        response.setId(sushikhaUser.getId());
        response.setSushikhaUserId(sushikhaUser.getSushikhaUserId());
        response.setName(sushikhaUser.getName());
        response.setPhone(sushikhaUser.getPhone());
        response.setEmail(sushikhaUser.getEmail());
        response.setDob(sushikhaUser.getDob());
        response.setCity(sushikhaUser.getCity());
        response.setPincode(sushikhaUser.getPincode());
        response.setCreatedOn(sushikhaUser.getCreatedOn());
        response.setUpdatedOn(sushikhaUser.getUpdatedOn());
        response.setEmailVerified(sushikhaUser.getEmailVerified());
        response.setPhoneVerified(sushikhaUser.getPhoneVerified());
        log.info(Constants.LOG_CLASS_USER_REGISTRATION_CONVERSION_MANAGER +"mapUserEntityToRegistrationResponse"+ ":ended");
        return response;
    }

}
