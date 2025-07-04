package com.sushikhacapitals.common.security.rsa;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;

@Slf4j
public class GenericPKCS12RsaUtilFactory implements RSAUtilFactory {
    private KeyStore keyStore;
    private String alias;
    private String password;

    public GenericPKCS12RsaUtilFactory(InputStream inputStream, String alias, String password) {
        this.alias = alias;
        this.password = password;
        try {
            keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(inputStream, password.toCharArray());
        } catch (IOException | CertificateException | NoSuchAlgorithmException | KeyStoreException e) {
            log.error("Corrupt key file. ", e);
        }
    }

    @Override
    public PublicKey generateRSAPublicKey() {
        PrivateKey key = generateRSAPrivateKey();
        try {
            if (key != null && key instanceof PrivateKey) {
                Certificate cert = keyStore.getCertificate(this.alias);
                return cert.getPublicKey();
            }
        } catch (KeyStoreException e) {
            log.error("Corrupt Public Key. ", e);
        }
        return null;
    }

    @Override
    public PrivateKey generateRSAPrivateKey() {
        try {
            return (RSAPrivateKey) this.keyStore.getKey(this.alias, this.password.toCharArray());
        } catch (KeyStoreException e) {
            log.error("Corrupt Private Key. ", e);
        } catch (NoSuchAlgorithmException e) {
            log.error("Corrupt Private Key. ", e);
        } catch (UnrecoverableKeyException e) {
            log.error("Corrupt Private Key. ", e);
        }
        return null;
    }
}
