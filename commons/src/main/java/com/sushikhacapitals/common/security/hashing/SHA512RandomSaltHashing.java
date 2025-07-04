package com.sushikhacapitals.common.security.hashing;



import lombok.extern.slf4j.Slf4j;

import java.security.NoSuchAlgorithmException;

@Slf4j
public class SHA512RandomSaltHashing implements HashingUtils {
    @Override
    public String hash(String payload) {
        String saltAppendedHashedPayload = null;
        try {
            byte[] salt = HashingHelper.getRandomSalt();
            String hashedPayload = HashingHelper.hash(payload, salt);
            String saltString = HashingHelper.saltToString(salt);
            saltAppendedHashedPayload = HashingHelper.appendSaltToHash(hashedPayload, saltString);
        } catch (NoSuchAlgorithmException e) {
            log.info("[Hashıng Utıl - Generate Hash Utıl] Error while generating hash", e);
        }
        return saltAppendedHashedPayload;
    }

    @Override
    public boolean compare(String payload, String hash) {
        try {
            if (hash.contains(":")) {
                String[] hashedValueArr = hash.split(":");
                String saltString = hashedValueArr[1];
                byte[] salt = HashingHelper.stringToSalt(saltString);
                String hashedPayload = HashingHelper.hash(payload, salt);
                String saltAppendedHashedPayload = HashingHelper.appendSaltToHash(hashedPayload, saltString);
                if (hash.equals(saltAppendedHashedPayload)) {
                    return true;
                }
            } else {
                return false;
            }
        } catch (NoSuchAlgorithmException e) {
            log.error("[Hashing Util - Compare Hash] Exception while comparing hash", e);
        }
        return false;
    }
}
