package com.sushikhacapitals.core.manager;

import com.sushikhacapitals.core.config.Constants;
import com.sushikhacapitals.core.model.request.UserSignInRequest;
import com.sushikhacapitals.core.model.response.UserSignInResponse;
import com.sushikhacapitals.core.utils.NotificationUtil;
import com.sushikhacapitals.core.utils.OTPGenerator;
import com.taskmanagement.mappers.api.LoanUserDao;
import com.sushikhacapitals.models.LoanUser;
import com.sushikhacapitals.models.OTP;
import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;
import com.twilio.rest.api.v2010.account.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class OTPVerificationManager {

    @Autowired
    private OTPdao otpdao;
    @Autowired
    private LoanUserDao loanUserDao;
    @Autowired
    private NotificationUtil notificationUtil;

    @Value("${otp.expiry-time}")
    private Integer expiryTime;

    @Value("${otp.email-enabled}")
    private Boolean emailEnabled;

    @Value("${otp.phone-enabled}")
    private Boolean phoneEnabled;

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.phone-number}")
    private String twilioPhoneNumber;

    public UserSignInResponse generateOtp(UserSignInRequest request) {
        log.info(Constants.LOG_CLASS_CONTACT_VERIFICATION_OTP_MANAGER + " generateOtp: Started");

        Optional<LoanUser> loanUserOptional = loanUserDao.findByUsername(request.getPhone());

        String entityId;
        if (loanUserOptional.isPresent()) {
            entityId = loanUserOptional.get().getUserId();
            log.info("Existing user found. Using entityId: {}", entityId);
        } else {
            entityId = UuidPrefix.USR + "-" + UUID.randomUUID();
            log.info("New user. Generated entityId: {}", entityId);
        }

        Optional<OTP> existingOtp = otpdao.getOTPByEntityId(entityId);
        if (existingOtp.isPresent()) {
            otpdao.updateOTP(existingOtp.get().getOtpId(), OTPStatus.OVERRIDDEN, new Date());
            log.info("Existing OTP marked as Overridden");
        }

        OTP otp = ConversionManger.mapContactVerificationToOTP1(request);
        String otpValue = OTPGenerator.generateRandomNumericOTP(4);
        sendPhone(request.getPhone(), otpValue);

        otp.setEntity(request.getPhone());
        otp.setEntityId(entityId);
        otp.setMediumType(OTPMediumType.PHONE);
        otp.setEntityType(EntityType.USER);
        otp.setOtpId(UuidPrefix.OTP + "-" + UUID.randomUUID());
        otp.setValue(otpValue);
        otp.setStatus(OTPStatus.PENDING);
        Date otpCreatedOn = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(otpCreatedOn);
        calendar.add(Calendar.MINUTE, expiryTime);
        otp.setCreatedOn(otpCreatedOn);
        otp.setExpiryOn(calendar.getTime());
        otp.setGeneratedBy(entityId);
        log.info(Constants.LOG_CLASS_CONTACT_VERIFICATION_OTP_MANAGER + " storing record to database for [CONTACT_VERIFICATION_OTP]");
        otpdao.insertOTP(otp);
        log.info(Constants.LOG_CLASS_CONTACT_VERIFICATION_OTP_MANAGER + " stored record to database for [CONTACT_VERIFICATION_OTP]");

        if (loanUserOptional.isEmpty()) {
            LoanUser user = new LoanUser();
            user.setUserId(entityId);
            user.setStatus(UserStatus.ACTIVE);
            user.setOtp(otpValue);
            user.setUsername(request.getPhone());
            user.setUserRole(UserRole.USER);

            log.info(Constants.LOG_CLASS_CONTACT_VERIFICATION_OTP_MANAGER + " storing record to database for [LOAN_USER]");
            loanUserDao.insertUser(user);
            log.info(Constants.LOG_CLASS_CONTACT_VERIFICATION_OTP_MANAGER + " stored record to database for [LOAN_USER]");
        } else {
            log.info("Updating OTP for existing user with ID: " + entityId);
            loanUserDao.updateUserOtp(entityId, otpValue);
        }

        UserSignInResponse response = ConversionManger.mapOTPToContactVerificationOTPResponse1(otp);
        log.info(Constants.LOG_CLASS_CONTACT_VERIFICATION_OTP_MANAGER + " generateOtp: Ended");
        return response;
    }


    private void sendPhone(String phone, String otpValue) {
        log.info("Sending OTP SMS to phone: {}", phone);

        String messageBody = "Your OTP is: " + otpValue + ". Valid for 5 minutes.";

        try {
            Twilio.init(accountSid, authToken);
            Message message = Message.creator(
                    new PhoneNumber("+91" + phone),
                    new PhoneNumber(twilioPhoneNumber),
                    messageBody
            ).create();

            log.info("SMS sent successfully. SID: {}", message.getSid());
        } catch (Exception e) {
            log.error("Failed to send SMS: ", e);
        }
    }
}




