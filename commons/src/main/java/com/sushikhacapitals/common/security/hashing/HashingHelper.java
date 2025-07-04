package com.sushikhacapitals.common.security.hashing;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Slf4j
public class HashingHelper {

    public static String hash(String password, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt);
        byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public static String appendSaltToHash(String hashedPayload, String saltString) {
        StringBuilder saltAppendedHashedPayload = new StringBuilder(hashedPayload);
        saltAppendedHashedPayload.append(":");
        saltAppendedHashedPayload.append(saltString);
        return saltAppendedHashedPayload.toString();
    }

    public static byte[] getRandomSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public static String saltToString(byte[] salt) {
        return Base64.getEncoder().encodeToString(salt);
    }

    public static byte[] stringToSalt(String saltString) {
        return Base64.getDecoder().decode(saltString.getBytes());
    }
}
