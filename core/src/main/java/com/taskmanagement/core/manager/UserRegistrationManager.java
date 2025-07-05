package com.taskmanagement.core.manager;


import com.sushikhacapitals.common.security.AESUtil;
import com.taskmanagement.core.config.Constants;
import com.taskmanagement.core.exception.BusinessException;
import com.taskmanagement.core.model.request.UserRegistrationRequest;
import com.taskmanagement.core.model.response.UserRegistrationListResponse;
import com.taskmanagement.core.model.response.UserRegistrationResponse;
import com.taskmanagement.core.security.ContextAuthentication;
import com.taskmanagement.core.validator.UserValidator;
import com.taskmanagement.mappers.api.TaskUserDao;
import com.taskmanagement.mappers.api.UserDao;
import com.taskmanagement.models.TaskUser;
import com.taskmanagement.models.User;
import com.taskmanagement.models.enums.UserStatus;
import com.taskmanagement.models.enums.UuidPrefix;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Service
@Slf4j
@RestController
public class UserRegistrationManager {

    @Autowired
    private TaskUserDao taskUserDao;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private ContextAuthentication authentication;
    @Autowired
    private UserDao userDao;
    @Autowired
    private AESUtil aesUtil;


    @Transactional
    public UserRegistrationResponse register(UserRegistrationRequest request) {
        try {
            log.info(Constants.LOG_CLASS_USER_REGISTRATION_MANAGER + " register : Started");
            TaskUser taskUser = UserRegistrationConversionManager.mapUserRegistrationRequestToEntity(request);
            userValidator.validateEmailUniqueness(request.getEmail());
            taskUser.setTaskUserId(UuidPrefix.USR + "-" + UUID.randomUUID());
            taskUser.setStatus(UserStatus.ACTIVE);
            taskUser.setCreatedOn(new Date());
            log.info(Constants.LOG_CLASS_USER_REGISTRATION_MANAGER+"storing record to database for [TASK_USER]");
            taskUserDao.insert(taskUser);
            log.info(Constants.LOG_CLASS_USER_REGISTRATION_MANAGER+"stored record to database for [TASK_USER]");
            User user=new User();
            user.setUserId(taskUser.getTaskUserId());
            user.setStatus(UserStatus.ACTIVE);
            user.setPassword(aesUtil.encrypt(request.getPassword()));
            user.setUsername(UuidPrefix.USR+"-"+taskUser.getId());
            user.setUserRole(request.getRole());
            log.info(Constants.LOG_CLASS_USER_REGISTRATION_MANAGER+"storing record to database for [USER]");
            userDao.insertUser(user);
            UserRegistrationResponse response = UserRegistrationConversionManager.mapUserEntityToRegistrationResponse(taskUser);
            return response;
        } catch (BusinessException ex) {
            log.error(Constants.LOG_CLASS_USER_REGISTRATION_MANAGER + " register - Business Exception: {}", ex.toString());
            throw ex;
        }
    }



    public UserRegistrationListResponse getUserList(Long offset, Long pageSize) {
        try{
            log.info(Constants.LOG_CLASS_USER_REGISTRATION_MANAGER + "getUserList: started");
            final User user = authentication.getCurrentUser();
            String sushikhaUserId = user.getUserId();
            UserRegistrationListResponse response = new UserRegistrationListResponse();
            Long totalCount = taskUserDao.selectTotalCount(sushikhaUserId);
            response.setTotal(totalCount);
            if (totalCount == 0) {
                log.info(Constants.LOG_CLASS_USER_REGISTRATION_MANAGER + "getUserList" + ": ended");
                return response;
            }

            Long calculatedOffset = (offset != null && pageSize != null) ? (offset > 0 ? offset * pageSize : pageSize) : null;
            List<TaskUser> records = taskUserDao.selectRecordsInRange(sushikhaUserId, calculatedOffset, pageSize);
            List<UserRegistrationResponse> responses = new ArrayList<>();
            for (TaskUser record : records) {
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

