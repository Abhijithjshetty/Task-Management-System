package com.sushikhacapitals.core.validator;

import com.sushikhacapitals.core.config.Constants;
import com.sushikhacapitals.core.exception.BusinessException;
import com.sushikhacapitals.core.exception.Errors;
import com.sushikhacapitals.core.model.request.UserRegistrationRequest;
import com.sushikhacapitals.core.model.request.UserRegistrationUpdateRequest;
import com.taskmanagement.mappers.api.SushikhaUserDao;
import com.sushikhacapitals.models.SushikhaUser;
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
    private SushikhaUserDao sushikhaUserDao;


    public static void fieldValidationOwnerRegistrationRequest(UserRegistrationRequest userRegistrationRequest){
        GenericFieldValidator.fieldValidation(userRegistrationRequest);
    }

    public static void fieldValidationOwnerRegistrationRequest1(UserRegistrationUpdateRequest userRegistrationUpdateRequest) {
        GenericFieldValidator.fieldValidation(userRegistrationUpdateRequest);
    }

    public void validateEmailUniqueness(String email) throws BusinessException {
        log.info(Constants.LOG_CLASS_USER_REGISTRATION_VALIDATION + "validateEmailUniqueness" + ": started");
        Optional<SushikhaUser> sushikhaUserOptional = sushikhaUserDao.selectUserByEmail(email);
        if (sushikhaUserOptional.isPresent()) {
            throw new BusinessException(Errors.EMAIL_ALREADY_IN_USE);
        }
        log.info(Constants.LOG_CLASS_USER_REGISTRATION_VALIDATION + "validateEmailUniqueness" + ": ended");
    }

    public void validatePhoneUniqueness(String phone) throws BusinessException {
        log.info(Constants.LOG_CLASS_USER_REGISTRATION_VALIDATION + "validatePhoneUniqueness" + ": started");
        Optional<SushikhaUser> sushikhaUserOptional = sushikhaUserDao.selectUserByPhone(phone);
        if (sushikhaUserOptional.isPresent()) {
            throw new BusinessException(Errors.PHONE_ALREADY_IN_USE);
        }
        log.info(Constants.LOG_CLASS_USER_REGISTRATION_VALIDATION + "validatePhoneUniqueness" + ": ended");
    }
    public void validateAge(Date dob) {
        log.info(Constants.LOG_CLASS_USER_REGISTRATION_VALIDATION + "validateAge" + ": started");
        LocalDate birthDate = dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long years = ChronoUnit.YEARS.between(birthDate, LocalDate.now());
        if (years <= 18) {
            throw new BusinessException(Errors.USER_AGE_IS_LESS_THAN_18);
        }
        log.info(Constants.LOG_CLASS_USER_REGISTRATION_VALIDATION + "validateAge" + ": ended");
    }
}
