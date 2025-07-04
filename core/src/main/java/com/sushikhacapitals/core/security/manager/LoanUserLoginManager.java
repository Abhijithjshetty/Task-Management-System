package com.sushikhacapitals.core.security.manager;

import com.sushikhacapitals.core.config.Constants;
import com.sushikhacapitals.core.exception.BusinessException;
import com.sushikhacapitals.core.exception.CustomAuthenticationException;
import com.sushikhacapitals.core.exception.Errors;
import com.sushikhacapitals.core.manager.ConversionManger;
import com.sushikhacapitals.core.security.authentication.PasswordBasedAuthentication;
import com.sushikhacapitals.core.security.models.LoanUserAuth;
import com.sushikhacapitals.core.security.models.request.LoanUserLoginRequest;
import com.sushikhacapitals.core.security.util.JWTUtil;
import com.taskmanagement.mappers.api.SessionDao;
import com.sushikhacapitals.models.OTP;
import com.sushikhacapitals.models.enums.OTPStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class LoanUserLoginManager {

    @Autowired
    SessionDao sessionDao;
    @Autowired
    JWTUtil jwtUtil;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private OTPdao otpDao;

    public LoanUserAuth userLogin(LoanUserLoginRequest loanUserLoginRequest) {
        try {
            log.info(Constants.LOG_CLASS_LOAN_USER_LOGIN_MANAGER + " loginUser: started");
            validateOTP(loanUserLoginRequest.getOtpId(), loanUserLoginRequest.getOtpValue());
            String phone = otpDao.selectUserNameByOtpId(loanUserLoginRequest.getOtpId());
            LoanUserAuth LUAuth = new LoanUserAuth();
            LUAuth.setUsername(phone);
            LUAuth.setOtp(loanUserLoginRequest.getOtpValue());
            PasswordBasedAuthentication auth = new PasswordBasedAuthentication(LUAuth, false);

            Authentication authentication = authenticationManager.authenticate(auth);
            if (authentication.isAuthenticated()) {
                LoanUserAuth userAuth=(LoanUserAuth) authentication.getDetails();
                Map<String, Object> claims=new HashMap<>();
                claims.put("USERNAME",userAuth.getUsername());
                String token = jwtUtil.generateToken(userAuth.getUsername(),claims);
                LUAuth.setToken(token);
                String subject = jwtUtil.extractSubject(token);
                sessionDao.clearByExpiryDate(subject, new Date());
                sessionDao.create(userAuth.getUserId(),userAuth.getUsername(),userAuth.getRole(),
                        token,jwtUtil.extractIssuedAt(token),
                        jwtUtil.extractExpiration(token));
                LoanUserAuth response = ((PasswordBasedAuthentication) authentication).getUserAuth();
                response.setToken(token);
                String userId = userAuth.getUserId();
                response.setUserId(userId);
                log.info(Constants.LOG_CLASS_LOAN_USER_LOGIN_MANAGER + " loginUser: ended");
                return response;
            }
            throw new CustomAuthenticationException();
        }catch (AuthenticationException ex){
            throw new CustomAuthenticationException();
        }
    }
    public void validateOTP(String otpId, String otpValue) {
        log.info(Constants.LOG_CLASS_CONTACT_VERIFICATION_OTP_MANAGER+" validateOTP:"+" Started");
        Optional<OTP> otp= otpDao.getOTP(otpId);
        if(otp.isPresent()) {
            if (otp.get().getStatus() == OTPStatus.VERIFIED) {
                throw new BusinessException(Errors.OTP_ALREADY_VERIFIED);
            }
        }
        if (otp.isEmpty() || !otp.get().getStatus().equals(OTPStatus.PENDING) ||
                !otp.get().getExpiryOn().after(new Date()) ||
                !otp.get().getValue().equals(otpValue)){
            if (otp.isPresent() && !otp.get().getStatus().equals(OTPStatus.FAILED)) {
                otpDao.updateOTP(otpId, OTPStatus.FAILED, new Date());
                log.info("Failed to validated otp, status udpated");
            }
            log.info(Constants.LOG_CLASS_CONTACT_VERIFICATION_OTP_MANAGER+" validateOTP:"+" Ended");
            throw new BusinessException(Errors.INVALID_OTP);
        } else {
            Date updatedOn=new Date();
            otpDao.updateOTP(otpId, OTPStatus.VERIFIED,updatedOn);
            log.info("OTP validated successfully!");
            OTP otpUpdated=otp.get();
            otpUpdated.setStatus(OTPStatus.VERIFIED);
            otpUpdated.setUpdatedOn(updatedOn);
            log.info(Constants.LOG_CLASS_CONTACT_VERIFICATION_OTP_MANAGER+" validateOTP:"+" Ended");
            ConversionManger.mapOTPToContactVerificationOTPResponse(otpUpdated);
        }
    }

}
