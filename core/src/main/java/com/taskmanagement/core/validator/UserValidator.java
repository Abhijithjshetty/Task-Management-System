package com.taskmanagement.core.validator;


import com.taskmanagement.core.config.Constants;
import com.taskmanagement.core.exception.BusinessException;
import com.taskmanagement.core.exception.Errors;
import com.taskmanagement.core.model.request.UserRegistrationRequest;
import com.taskmanagement.mappers.api.TaskUserDao;
import com.taskmanagement.models.TaskUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
public class UserValidator {

    @Autowired
    private TaskUserDao taskUserDao;


    public static void fieldValidationOwnerRegistrationRequest(UserRegistrationRequest userRegistrationRequest){
        GenericFieldValidator.fieldValidation(userRegistrationRequest);
    }


    public void validateEmailUniqueness(String email) throws BusinessException {
        log.info(Constants.LOG_CLASS_USER_REGISTRATION_VALIDATION + "validateEmailUniqueness" + ": started");
        Optional<TaskUser> taskUserOptional = taskUserDao.selectUserByEmail(email);
        if (taskUserOptional.isPresent()) {
            throw new BusinessException(Errors.EMAIL_ALREADY_IN_USE);
        }
        log.info(Constants.LOG_CLASS_USER_REGISTRATION_VALIDATION + "validateEmailUniqueness" + ": ended");
    }

}
