package com.sushikhacapitals.common.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;

public class JoseServerPKCS12Config implements JoseServerConfig {
    private RSAPrivateKey serverPrivateKey;
    private String signingKeyFingerPrint;


    public JoseServerPKCS12Config(File privateKeyResource,
                                  String privateKeyAlias,
                                  String privateKeyPassword,
                                  String signingKeyFingerPrint)

        throws IOException, KeyStoreException, CertificateException,
        NoSuchAlgorithmException, UnrecoverableKeyException {

        try (InputStream privateKeyStream = new FileInputStream(privateKeyResource)){
            KeyStore keystore = null;
            RSAPrivateKey key = null;

            keystore = KeyStore.getInstance("PKCS12");
            keystore.load(privateKeyStream, privateKeyPassword.toCharArray());
            key = (RSAPrivateKey) keystore.getKey(privateKeyAlias, privateKeyPassword.toCharArray());

            this.serverPrivateKey = key;
            this.signingKeyFingerPrint = signingKeyFingerPrint;
        }

    }

    @Override
    public RSAPrivateKey getRSAPrivateKey() {
        return this.serverPrivateKey;
    }

    @Override
    public String getSigningKeyFingerPrint() {
        return this.signingKeyFingerPrint;
    }


}
