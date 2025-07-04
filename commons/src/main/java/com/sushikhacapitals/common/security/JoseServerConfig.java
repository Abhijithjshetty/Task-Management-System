package com.sushikhacapitals.common.security;

import java.security.interfaces.RSAPrivateKey;

public interface JoseServerConfig {
   // RSAPublicKey loadRSAPublicKey();
    RSAPrivateKey getRSAPrivateKey();
    String getSigningKeyFingerPrint();
}
