package com.sushikhacapitals.core.manager;

import com.sushikhacapitals.core.config.Constants;
import com.sushikhacapitals.core.exception.BusinessException;
import com.sushikhacapitals.core.model.request.PersonalDetailsRequest;
import com.sushikhacapitals.core.model.response.PersonalDetailsResponse;
import com.sushikhacapitals.core.security.ContextAuthentication;
import com.taskmanagement.mappers.api.PersonalDetailsDao;
import com.sushikhacapitals.models.PersonalDetails;
import com.sushikhacapitals.models.LoanUser;
import com.sushikhacapitals.models.enums.UserStatus;
import com.sushikhacapitals.models.enums.UuidPrefix;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;


@Service
@Slf4j
@RestController
public class PersonalDetailsManager {

    @Autowired
    private ContextAuthentication authentication;
    @Autowired
    private PersonalDetailsDao personalDetailsDao;
    @Autowired
    private OTPdao otpDao;


    @Transactional
    public PersonalDetailsResponse personalDetails(PersonalDetailsRequest request) {
        try {
            log.info(Constants.LOG_CLASS_PERSONAL_DETAILS_MANAGER+" personalDetails"+"Started");
            final LoanUser user = authentication.getCurrentUser();
            PersonalDetails personalDetails=ConversionManger.mapPersonalDetailsRequestToEntity(request);
            personalDetails.setPersonalDetailsId(UuidPrefix.PDI + "-" + UUID.randomUUID());
            personalDetails.setStatus(UserStatus.ACTIVE);
            boolean isPhoneVerified = otpDao.isPhoneVerified(user.getUserId());
            boolean isEmailVerified = otpDao.isEmailVerified(user.getUserId());
            personalDetails.setPhoneVerified(isPhoneVerified);
            personalDetails.setEmailVerified(isEmailVerified);
            personalDetails.setCreatedOn(new Date());
            personalDetails.setUserId(user.getUserId());
            personalDetails.setCreatedBy(user.getUserId());
            log.info(Constants.LOG_CLASS_PERSONAL_DETAILS_MANAGER+"storing record to database for [PERSONAL_DETAILS]");
            personalDetailsDao.insert(personalDetails);
            PersonalDetailsResponse response=ConversionManger.mapPersonalDetailsToResponse(personalDetails);
            log.info(Constants.LOG_CLASS_PERSONAL_DETAILS_MANAGER+" personalDetails"+"ended");
            return response;
        } catch (BusinessException ex) {
            log.error(Constants.LOG_CLASS_PERSONAL_DETAILS_MANAGER+"personalDetails - Business Exception: {}", ex.toString());
            throw ex;
        }
    }
}

