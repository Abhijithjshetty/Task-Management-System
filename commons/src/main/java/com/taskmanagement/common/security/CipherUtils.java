package com.taskmanagement.common.security;

public interface CipherUtils {
    String encrypt(String payload);
    String decrypt(String payload);
}
