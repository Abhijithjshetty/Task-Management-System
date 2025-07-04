package com.sushikhacapitals.common.security;

public interface CipherUtils {
    String encrypt(String payload);
    String decrypt(String payload);
}
