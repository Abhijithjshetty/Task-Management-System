package com.sushikhacapitals.common.security.hashing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public interface HashingUtils {

    class Type {
        public static final String STATIC_SALT_HASH = "StaticSaltHash";
        public static final String RANDOM_SALT_HASH = "RandomSaltHash";
    }

    String hash(String payload);

    default boolean compare(String payload, String hash) {
        Logger log = LoggerFactory.getLogger(this.getClass());
        try {
            return hash(payload).equals(hash);
        } catch (RuntimeException e) {
            log.error("[HASHING UTIL - COMPARE HASH] Exception while comparing hash", e);
            return false;
        }
    }
}
