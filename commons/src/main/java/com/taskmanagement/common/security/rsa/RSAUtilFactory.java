package com.taskmanagement.common.security.rsa;



import com.taskmanagement.common.utils.RSAUtil;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface RSAUtilFactory {
    PublicKey generateRSAPublicKey();
    PrivateKey generateRSAPrivateKey();
    default RSAUtil getRSAUtil() {
        return new RSAUtil(generateRSAPrivateKey(), generateRSAPublicKey());
    }
}
