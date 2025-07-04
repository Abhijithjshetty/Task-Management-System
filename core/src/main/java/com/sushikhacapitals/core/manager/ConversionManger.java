package com.sushikhacapitals.core.manager;

import com.sushikhacapitals.core.config.Constants;
import com.sushikhacapitals.core.model.request.AddressRequest;
import com.sushikhacapitals.core.model.request.PersonalDetailsRequest;
import com.sushikhacapitals.core.model.request.ContactVerificationOTPRequest;
import com.sushikhacapitals.core.model.request.UserSignInRequest;
import com.sushikhacapitals.core.model.response.AddressResponse;
import com.sushikhacapitals.core.model.response.PersonalDetailsResponse;
import com.sushikhacapitals.core.model.response.ContactVerificationOTPResponse;
import com.sushikhacapitals.core.model.response.UserSignInResponse;
import com.sushikhacapitals.models.Address;
import com.sushikhacapitals.models.PersonalDetails;
import com.sushikhacapitals.models.OTP;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConversionManger {

    public static OTP mapContactVerificationToOTP1(UserSignInRequest request){
        log.info(Constants.LOG_CLASS_CONVERSION_MANAGER +"mapContactVerificationToOTP1"+ ":started");
        OTP otp=new OTP();
        otp.setPhone(request.getPhone());
        log.info(Constants.LOG_CLASS_CONVERSION_MANAGER +"mapContactVerificationToOTP1"+ ":ended");
        return otp;
    }

    public static OTP mapContactVerificationToOTP(ContactVerificationOTPRequest request) {
        log.info(Constants.LOG_CLASS_CONVERSION_MANAGER +"mapContactVerificationToOTP"+ ":started");
        OTP otp=new OTP();
        otp.setEmail(request.getEmail());
        log.info(Constants.LOG_CLASS_CONVERSION_MANAGER +"mapContactVerificationToOTP"+ ":ended");
        return otp;
    }
    public static ContactVerificationOTPResponse mapOTPToContactVerificationOTPResponse(OTP otp){
        log.info(Constants.LOG_CLASS_CONVERSION_MANAGER +"mapOTPToContactVerificationOTPResponse"+ ":started");
        ContactVerificationOTPResponse response=new ContactVerificationOTPResponse();
        response.setOtpId(otp.getOtpId());
        response.setEntityId(otp.getEntityId());
        response.setEntityType(otp.getEntityType());
        response.setUpdatedOn(otp.getUpdatedOn());
        response.setStatus(otp.getStatus());
        response.setCreatedOn(otp.getCreatedOn());
        response.setExpiryOn(otp.getExpiryOn());
        response.setUpdatedOn(otp.getUpdatedOn());
        response.setMediumType(otp.getMediumType());
        log.info(Constants.LOG_CLASS_CONVERSION_MANAGER +"mapOTPToContactVerificationOTPResponse"+ ":ended");
        return response;
    }

    public static UserSignInResponse mapOTPToContactVerificationOTPResponse1(OTP otp){
        log.info(Constants.LOG_CLASS_CONVERSION_MANAGER +"mapOTPToContactVerificationOTPResponse1"+ ":started");
        UserSignInResponse response=new UserSignInResponse();
        response.setOtpId(otp.getOtpId());
        response.setEntityId(otp.getEntityId());
        response.setEntityType(otp.getEntityType());
        response.setMediumType(otp.getMediumType());
        response.setUpdatedOn(otp.getUpdatedOn());
        response.setStatus(otp.getStatus());
        response.setCreatedOn(otp.getCreatedOn());
        response.setExpiryOn(otp.getExpiryOn());
        response.setUpdatedOn(otp.getUpdatedOn());
        log.info(Constants.LOG_CLASS_CONVERSION_MANAGER +"mapOTPToContactVerificationOTPResponse1"+ ":ended");
        return response;
    }

    public static PersonalDetails mapPersonalDetailsRequestToEntity(PersonalDetailsRequest request){
        log.info(Constants.LOG_CLASS_CONVERSION_MANAGER +"mapPersonalDetailsRequestToEntity"+ ":started");
        PersonalDetails personalDetails =new PersonalDetails();
        personalDetails.setFirstName(request.getFirstName());
        personalDetails.setLastName(request.getLastName());
        personalDetails.setGender(request.getGender());
        personalDetails.setEmploymentType(request.getEmploymentType());
        personalDetails.setDob(request.getDob());
        log.info(Constants.LOG_CLASS_CONVERSION_MANAGER +"mapPersonalDetailsRequestToEntity"+ ":ended");
        return  personalDetails;
    }


    public static Address mapAddressRequestToAddress(AddressRequest request) {
        log.info(Constants.LOG_CLASS_CONVERSION_MANAGER +"mapAddressRequestToAddress"+ ":started");
        Address address=new Address();
        address.setHouseNumber(request.getHouseNumber());
        address.setArea(request.getArea());
        address.setCity(request.getCity());
        address.setCountry(request.getCountry());
        address.setPincode(request.getPincode());
        address.setState(request.getState());
        log.info(Constants.LOG_CLASS_CONVERSION_MANAGER +"mapAddressRequestToAddress"+ ":ended");
        return address;
    }


    public static AddressResponse mapAddressToAddressResponse(Address address) {
        log.info(Constants.LOG_CLASS_CONVERSION_MANAGER +"mapAddressRequestToAddress"+ ":started");
        AddressResponse response=new AddressResponse();
        response.setHouseNumber(address.getHouseNumber());
        response.setArea(address.getArea());
        response.setCity(address.getCity());
        response.setCountry(address.getCountry());
        response.setPincode(address.getPincode());
        response.setState(address.getState());
        log.info(Constants.LOG_CLASS_CONVERSION_MANAGER +"mapAddressRequestToAddress"+ ":ended");
        return response;
    }

    public static PersonalDetailsResponse mapPersonalDetailsToResponse(PersonalDetails personalDetails) {
        log.info(Constants.LOG_CLASS_CONVERSION_MANAGER +"mapPersonalDetailsToResponse"+ ":started");
        PersonalDetailsResponse response = new PersonalDetailsResponse();
        response.setId(personalDetails.getId());
        response.setPersonalDetailsId(personalDetails.getPersonalDetailsId());
        response.setUserId(personalDetails.getUserId());
        response.setFirstName(personalDetails.getFirstName());
        response.setLastName(personalDetails.getLastName());
        response.setStatus(personalDetails.getStatus());
        response.setGender(personalDetails.getGender());
        response.setEmploymentType(personalDetails.getEmploymentType());
        response.setDob(personalDetails.getDob());
        response.setEmailVerified(personalDetails.getEmailVerified());
        response.setPhoneVerified(personalDetails.getPhoneVerified());
        response.setCreatedOn(personalDetails.getCreatedOn());
        response.setCreatedBy(personalDetails.getCreatedBy());
        response.setUpdatedOn(personalDetails.getUpdatedOn());
        response.setUpdatedBy(personalDetails.getUpdatedBy());
        log.info(Constants.LOG_CLASS_CONVERSION_MANAGER +"mapPersonalDetailsToResponse"+ ":ended");
        return response;
    }
}
