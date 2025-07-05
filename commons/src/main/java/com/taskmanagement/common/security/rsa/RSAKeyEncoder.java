package com.taskmanagement.common.security.rsa;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class RSAKeyEncoder {
    public static String encode(PublicKey publicKey) {
        return base64Encode(publicKey.getEncoded());
    }

    public static String encode(PrivateKey privateKey) {
        return base64Encode(privateKey.getEncoded());
    }

    private static String base64Encode(byte[] keyEncode) {
        return new String(Base64.getEncoder().encode(keyEncode));
    }
}
