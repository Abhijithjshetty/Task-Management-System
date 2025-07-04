package com.sushikhacapitals.common.security.rsa;

import com.sushikhacapitals.common.utils.ResourceUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;

@Slf4j
public class PKCSRsaUtilFactory implements RSAUtilFactory {
    private String keyPath;
    private String keyAlias;
    private String keyPassword;
    private KeyStore keyStore;

    public PKCSRsaUtilFactory(String keyPath, String keyAlias, String keyPassword) {
        this.keyPath = keyPath;
        this.keyAlias = keyAlias;
        this.keyPassword = keyPassword;
        try(InputStream inputStream = ResourceUtils.loadResourceAsStream(keyPath)) {
            this.keyStore = KeyStore.getInstance("PKCS12");
            this.keyStore.load(inputStream, keyPassword.toCharArray());
        } catch (IOException | CertificateException | NoSuchAlgorithmException| KeyStoreException e) {
            log.error("Corrupt key file. ", e);
        }
    }

    @Override
    public PublicKey generateRSAPublicKey() {
        PrivateKey key = generateRSAPrivateKey();
        try {
            if (key != null && key instanceof PrivateKey) {
                Certificate cert = keyStore.getCertificate(this.keyAlias);
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
            return (RSAPrivateKey) this.keyStore.getKey(this.keyAlias, this.keyPassword.toCharArray());
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
