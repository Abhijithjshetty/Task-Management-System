package com.sushikhacapitals.core.manager;

import com.sushikhacapitals.common.utils.FileUtils;
import com.sushikhacapitals.core.config.Constants;
import com.sushikhacapitals.core.exception.BusinessException;
import com.sushikhacapitals.core.exception.Errors;
import com.sushikhacapitals.core.model.request.UserRegistrationRequest;
import com.sushikhacapitals.core.model.request.UserRegistrationUpdateRequest;
import com.sushikhacapitals.core.model.response.UserRegistrationListResponse;
import com.sushikhacapitals.core.model.response.UserRegistrationResponse;
import com.sushikhacapitals.core.security.ContextAuthentication;
import com.sushikhacapitals.core.validator.UserValidator;
import com.taskmanagement.mappers.api.SushikhaUserDao;
import com.sushikhacapitals.models.LoanUser;
import com.sushikhacapitals.models.SushikhaUser;
import com.sushikhacapitals.models.enums.UserStatus;
import com.sushikhacapitals.models.enums.UuidPrefix;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@Slf4j
@RestController
public class UserRegistrationManager {

    @Autowired
    private SushikhaUserDao sushikhaUserDao;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private ContextAuthentication authentication;


    @Transactional
    public UserRegistrationResponse register(UserRegistrationRequest request) {
        try {
            log.info(Constants.LOG_CLASS_USER_REGISTRATION_MANAGER + " register : Started");
            SushikhaUser sushikhaUser = UserRegistrationConversionManager.mapUserRegistrationRequestToEntity(request);
            userValidator.validatePhoneUniqueness(request.getPhone());
            userValidator.validateEmailUniqueness(request.getEmail());
            userValidator.validateAge(request.getDob());
            sushikhaUser.setSushikhaUserId(UuidPrefix.USR + "-" + UUID.randomUUID());
            sushikhaUser.setStatus(UserStatus.ACTIVE);
            sushikhaUser.setEmailVerified(false);
            sushikhaUser.setPhoneVerified(false);
            sushikhaUser.setCreatedOn(new Date());
            log.info(Constants.LOG_CLASS_USER_REGISTRATION_MANAGER+"storing record to database for [SUSHIKHA_CAPITAL_USER]");
            sushikhaUserDao.insert(sushikhaUser);
            log.info(Constants.LOG_CLASS_USER_REGISTRATION_MANAGER+"stored record to database for [SUSHIKHA_CAPITAL_USER]");

            UserRegistrationResponse response = UserRegistrationConversionManager.mapUserEntityToRegistrationResponse(sushikhaUser);
            return response;
        } catch (BusinessException ex) {
            log.error(Constants.LOG_CLASS_USER_REGISTRATION_MANAGER + " register - Business Exception: {}", ex.toString());
            throw ex;
        }
    }

    public UserRegistrationResponse updateUser(UserRegistrationUpdateRequest userRegistrationUpdateRequest, MultipartFile profilePic) {
        try {
            log.info(Constants.LOG_CLASS_USER_REGISTRATION_MANAGER + " updateUser: started");
            final LoanUser user = authentication.getCurrentUser();
            String sushikhaUserId = user.getUserId();
            Optional<SushikhaUser> sushikhaUserOptional = sushikhaUserDao.getUserByRegistrationId(sushikhaUserId);
            if (sushikhaUserOptional.isEmpty()) {
                throw new BusinessException(Errors.USER_ID_DOES_NOT_EXIST);
            }
            byte[] profile = null;

            if (!ObjectUtils.isEmpty(profilePic)) {
                log.info(Constants.LOG_CLASS_USER_REGISTRATION_MANAGER + " converting profile pic");
                profile = FileUtils.convertMultipartImageToByte(profilePic);
            }
            SushikhaUser sushikhaUser = sushikhaUserOptional.get();
            if (StringUtils.isNotEmpty(userRegistrationUpdateRequest.getEmail())) {
                userValidator.validateEmailUniqueness(userRegistrationUpdateRequest.getEmail());
                sushikhaUser.setEmail(userRegistrationUpdateRequest.getEmail());
            }
            if(StringUtils.isNotEmpty(userRegistrationUpdateRequest.getPhone())) {
                userValidator.validatePhoneUniqueness(userRegistrationUpdateRequest.getPhone());
                sushikhaUser.setPhone(userRegistrationUpdateRequest.getPhone());
            }
            if(userRegistrationUpdateRequest.getDob() != null) {
                userValidator.validateAge(userRegistrationUpdateRequest.getDob());
                sushikhaUser.setDob(userRegistrationUpdateRequest.getDob());
            }
            if(StringUtils.isNotEmpty(userRegistrationUpdateRequest.getCity())) {
                sushikhaUser.setCity(userRegistrationUpdateRequest.getCity());
            }
            if(StringUtils.isNotEmpty(userRegistrationUpdateRequest.getPincode())) {
                sushikhaUser.setPincode(userRegistrationUpdateRequest.getPincode());
            }
            if(userRegistrationUpdateRequest.getStatus() != null) {
                sushikhaUser.setStatus(userRegistrationUpdateRequest.getStatus());
            }
            if(profilePic != null) {
                sushikhaUser.setProfilePic(profile);
            }
            boolean isEmailChanged = !StringUtils.equals(sushikhaUser.getEmail(), userRegistrationUpdateRequest.getEmail());
            boolean isPhoneChanged = !StringUtils.equals(sushikhaUser.getPhone(), userRegistrationUpdateRequest.getPhone());
            if (isEmailChanged || isPhoneChanged) {
                sushikhaUser.setEmailVerified(false);
                sushikhaUser.setPhoneVerified(false);
            }
            sushikhaUser.setUpdatedOn(new Date());
            sushikhaUserDao.update(sushikhaUser);
            UserRegistrationResponse response = UserRegistrationConversionManager.mapUserEntityToRegistrationResponse(sushikhaUser);
            log.info(Constants.LOG_CLASS_USER_REGISTRATION_MANAGER + " updateUser: ended");
            return response;
        } catch (BusinessException ex) {
            log.error(Constants.LOG_CLASS_USER_REGISTRATION_MANAGER + " updateUser: Business Exception: {}", ex.toString());
            throw ex;
        }
    }


    public ResponseEntity<byte[]> getImage() {
        try {
            log.info(Constants.LOG_CLASS_USER_REGISTRATION_MANAGER + "getImage: started");
            final LoanUser user = authentication.getCurrentUser();
            String sushikhaUserId = user.getUserId();
            Optional<Byte[]> optionalByteArray = sushikhaUserDao.getProfilePicByUserId(sushikhaUserId);
            if (optionalByteArray.isEmpty()) {
                throw new BusinessException(Errors.DOCUMENT_OR_IMAGE_IS_NULL);
            }
            Byte[] byteArrayLogoWrapper = optionalByteArray.get();
            byte[] byteArrayLogo = ArrayUtils.toPrimitive(byteArrayLogoWrapper);
            String contentType = FileUtils.determineImageType(byteArrayLogo);
            if (contentType == null) {
                throw new BusinessException(Errors.DOCUMENT_OR_IMAGE_IS_INVALID);
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType));
            headers.setContentLength(byteArrayLogo.length);
            log.info(Constants.LOG_CLASS_USER_REGISTRATION_MANAGER + "getImage" + ": ended");
            return new ResponseEntity<>(byteArrayLogo, headers, HttpStatus.OK);
        } catch (BusinessException ex) {
            log.error(Constants.LOG_CLASS_USER_REGISTRATION_MANAGER+"getImage - Business Exception: {}", ex.toString());
            throw ex;
        }
    }

    public UserRegistrationListResponse getUserList(Long offset, Long pageSize) {
        try{
            log.info(Constants.LOG_CLASS_USER_REGISTRATION_MANAGER + "getUserList: started");
            final LoanUser user = authentication.getCurrentUser();
            String sushikhaUserId = user.getUserId();
            UserRegistrationListResponse response = new UserRegistrationListResponse();
            Long totalCount = sushikhaUserDao.selectTotalCount(sushikhaUserId);
            response.setTotal(totalCount);
            if (totalCount == 0) {
                log.info(Constants.LOG_CLASS_USER_REGISTRATION_MANAGER + "getUserList" + ": ended");
                return response;
            }

            Long calculatedOffset = (offset != null && pageSize != null) ? (offset > 0 ? offset * pageSize : pageSize) : null;
            List<SushikhaUser> records = sushikhaUserDao.selectRecordsInRange(sushikhaUserId, calculatedOffset, pageSize);
            List<UserRegistrationResponse> responses = new ArrayList<>();
            for (SushikhaUser record : records) {
                responses.add(UserRegistrationConversionManager.mapUserEntityToRegistrationResponse(record));
            }
            response.setOffset(offset);
            response.setPageSize(pageSize);
            response.setUserRegistrationResponseList(responses);

            log.info(Constants.LOG_CLASS_USER_REGISTRATION_MANAGER + "getUserList" + ": ended");
            return response;
        } catch (BusinessException ex) {
            log.error(Constants.LOG_CLASS_USER_REGISTRATION_MANAGER + " getUserList: Business Exception: " + ex);
            throw ex;
        }
    }
}

