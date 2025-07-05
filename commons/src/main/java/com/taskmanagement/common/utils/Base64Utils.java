package com.taskmanagement.common.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Utils {

    public byte[] toByteCode(String base64String) {
        return Base64.getEncoder().encode(base64String.getBytes());
    }

    public String fromByteCode(byte[] bytes) {
        return new String(Base64.getDecoder().decode(bytes));
    }

    public String fromByteCode(String bytes) {
        return new String(Base64.getDecoder().decode(bytes.getBytes()));
    }

    public static String encode(String subject) {
        return Base64.getEncoder().encodeToString(subject.getBytes(StandardCharsets.UTF_8));
    }

    public static String decode(String subject) {
        byte[] decodedBytes = Base64.getDecoder().decode(subject);
        return new String(decodedBytes);
    }

}
