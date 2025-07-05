package com.taskmanagement.core.utils;

import com.taskmanagement.core.exception.BusinessException;
import com.taskmanagement.core.exception.Errors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class HashingUtil {

    @Value("${hashing.secret-key}")
    private String secretKeyProperty;

    public String generateHash(String input) {
        try {
            String saltedInput = input + secretKeyProperty;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(saltedInput.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("Error generating hash: " + e.getMessage());
            throw new BusinessException(Errors.INTERNAL_SERVER_ERROR);
        }
    }
}