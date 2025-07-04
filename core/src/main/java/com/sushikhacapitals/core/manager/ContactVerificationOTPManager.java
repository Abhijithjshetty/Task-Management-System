package com.sushikhacapitals.core.manager;

import com.sushikhacapitals.core.config.Constants;
import com.sushikhacapitals.core.exception.BusinessException;
import com.sushikhacapitals.core.exception.Errors;
import com.sushikhacapitals.core.model.request.ContactVerificationOTPRequest;
import com.sushikhacapitals.core.model.response.ContactVerificationOTPResponse;
import com.sushikhacapitals.core.security.ContextAuthentication;
import com.sushikhacapitals.core.utils.HashingUtil;
import com.sushikhacapitals.core.utils.NotificationUtil;
import com.sushikhacapitals.core.utils.OTPGenerator;
import com.taskmanagement.mappers.api.SushikhaUserDao;
import com.sushikhacapitals.models.LoanUser;
import com.sushikhacapitals.models.OTP;
import com.sushikhacapitals.models.enums.EntityType;
import com.sushikhacapitals.models.enums.OTPMediumType;
import com.sushikhacapitals.models.enums.OTPStatus;
import com.sushikhacapitals.models.enums.UuidPrefix;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@Slf4j
public class ContactVerificationOTPManager {

    @Autowired
    private OTPdao otpdao;
    @Autowired
    private SushikhaUserDao sushikhaUserDao;
    @Autowired
    private NotificationUtil notificationUtil;
    @Autowired
    private HashingUtil hashingUtil;
    @Autowired
    private ContextAuthentication authentication;

    @Value("${otp.expiry-time}")
    private Integer expiryTime;

    @Value("${otp.email-enabled}")
    private Boolean emailEnabled;


    @Value("${support.phone-number}")
    public String supportPhoneNumber;

    @Value("${support.email-address}")
    public String supportEmailAddress;


    public ContactVerificationOTPResponse generateOtp(ContactVerificationOTPRequest request) {
        log.info(Constants.LOG_CLASS_CONTACT_VERIFICATION_OTP_MANAGER+" generateOtp:"+" Started");
        final LoanUser user = authentication.getCurrentUser();
        String sushikhaUserId = user.getUserId();
        OTP otp = ConversionManger.mapContactVerificationToOTP(request);
        String otpValue= OTPGenerator.generateRandomNumericOTP(4);
        String email = request.getEmail();
        if(StringUtils.isEmpty(email)){
            throw new BusinessException(Errors.INVALID_DATA_FOR_OTP);
        }
        otp.setEntity(email);
        if(emailEnabled) {
            sendEmail(email, otpValue);
        }
        otp.setMediumType(OTPMediumType.EMAIL);
        otp.setOtpId(UuidPrefix.OTP+"-"+ UUID.randomUUID());
        otp.setEntityId(sushikhaUserId);
        otp.setEntityType(EntityType.USER);
        otp.setValue(otpValue);
        otp.setStatus(OTPStatus.PENDING);
        Date optCreatedOn= new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(optCreatedOn);
        calendar.add(Calendar.MINUTE,expiryTime);
        otp.setCreatedOn(optCreatedOn);
        otp.setExpiryOn(calendar.getTime());
        otp.setGeneratedBy(sushikhaUserId);
        otpdao.insertOTP(otp);
        ContactVerificationOTPResponse response= ConversionManger.mapOTPToContactVerificationOTPResponse(otp);
        log.info(Constants.LOG_CLASS_CONTACT_VERIFICATION_OTP_MANAGER+" generateOtp:"+" Ended");
        return response;
    }


    private void sendEmail(String email, String otpValue) {
        String emailBody = String.format(Constants.EMAIL_VERIFICATION_OTP_TEMPLATE, otpValue, supportEmailAddress, supportPhoneNumber);
        notificationUtil.sendHtmlEmail(email,"Contact Verification ["+otpValue+"]",emailBody);

    }


    public ContactVerificationOTPResponse validateOTP(String otpId, String otpValue) {
        log.info(Constants.LOG_CLASS_CONTACT_VERIFICATION_OTP_MANAGER+" validateOTP:"+" Started");
        Optional<OTP> otp= otpdao.getOTP(otpId);
        if(otp.isPresent()) {
            if (otp.get().getStatus() == OTPStatus.VERIFIED) {
                throw new BusinessException(Errors.EMAIL_ALREADY_VERIFIED);
            }
        }
        if (otp.isEmpty() || !otp.get().getStatus().equals(OTPStatus.PENDING) ||
                !otp.get().getExpiryOn().after(new Date()) ||
                !otp.get().getValue().equals(otpValue)){
            if (otp.isPresent() && !otp.get().getStatus().equals(OTPStatus.FAILED)) {
                otpdao.updateOTP(otpId, OTPStatus.FAILED, new Date());
                log.info("Failed to validated otp, status updated");
            }
            log.info(Constants.LOG_CLASS_CONTACT_VERIFICATION_OTP_MANAGER+" validateOTP:"+" Ended");
            throw new BusinessException(Errors.INVALID_OTP);
        } else {
            Date updatedOn=new Date();
            otpdao.updateOTP(otpId, OTPStatus.VERIFIED,updatedOn);
            log.info("OTP validated successfully!");
            OTP otpUpdated=otp.get();
            otpUpdated.setStatus(OTPStatus.VERIFIED);
            otpUpdated.setUpdatedOn(updatedOn);
            log.info(Constants.LOG_CLASS_CONTACT_VERIFICATION_OTP_MANAGER+" validateOTP:"+" Ended");
            return ConversionManger.mapOTPToContactVerificationOTPResponse(otpUpdated);
        }
    }

}


