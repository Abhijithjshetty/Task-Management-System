package com.sushikhacapitals.common.config;

import com.sushikhacapitals.common.security.AESUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SMSKeyProvider {

    private final AESUtil aesUtil;


    @Value("${sms.api-key}")
    private String encryptedSMSApiKey;


    private String decryptedSMSApiKey;


    public SMSKeyProvider(AESUtil aesUtil) {
        this.aesUtil = aesUtil;
    }


    @PostConstruct
    private void decryptSMSApiKey() {
        try {
            decryptedSMSApiKey = aesUtil.decrypt(encryptedSMSApiKey);
            log.info("SMS API key decrypted successfully.");
        } catch (Exception e) {
            log.error("Failed to decrypt SMS API key: {}", e.getMessage());

        }
    }


    public String getDecryptedSMSApiKey() {
        return decryptedSMSApiKey;
    }
}