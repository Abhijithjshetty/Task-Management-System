package com.sushikhacapitals.common.security.hashing;

import lombok.extern.slf4j.Slf4j;

import java.security.NoSuchAlgorithmException;

@Slf4j
public class SHA512StaticSaltHashing implements HashingUtils {

    private String aesKey;

    public SHA512StaticSaltHashing(String aesKey) {
        this.aesKey = aesKey;
    }

    @Override
    public String hash(String payload) {
        String hashedPayload = null;
        try {
            byte[] salt = HashingHelper.stringToSalt(this.aesKey);
            hashedPayload = HashingHelper.hash(payload, salt);
        } catch (NoSuchAlgorithmException e) {
            log.error("[Hashing Util - Generate Hash Util] Error while generating hash", e);
        }
        return hashedPayload;
    }
}
