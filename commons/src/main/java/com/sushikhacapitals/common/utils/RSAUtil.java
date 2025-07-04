package com.sushikhacapitals.common.utils;


import com.sushikhacapitals.common.security.CipherUtils;
import com.sushikhacapitals.common.security.rsa.RSAKeyEncoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

@Slf4j
public class RSAUtil implements CipherUtils {
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private String transform = "RSA";

    public RSAUtil(PrivateKey privateKey, PublicKey publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public RSAUtil(PrivateKey privateKey, PublicKey publicKey, String transform) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.transform = transform;
    }

    public String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance(transform);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] doFinalBytes = cipher.doFinal(data.getBytes("UTF-8"));
            String base64Encoded = Base64.encodeBase64String(doFinalBytes);
            return base64Encoded;
        } catch (NoSuchAlgorithmException e) {
            log.error("Algorithm Not Found. ", e);
        } catch (InvalidKeyException e) {
            log.error("Cipher error.", e);
        } catch (NoSuchPaddingException e) {
            log.error("Cipher error.", e);
        } catch (BadPaddingException e) {
            log.error("Cipher error.", e);
        } catch (UnsupportedEncodingException e) {
            log.error("Cipher error.", e);
        } catch (IllegalBlockSizeException e) {
            log.error("Cipher error.", e);
        }

        return null;
    }

    public String decrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance(transform);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(cipher.doFinal(Base64.decodeBase64(data)), "UTF-8");
        } catch (NoSuchAlgorithmException e) {
            log.error("Algorithm Not Found. ", e);
        } catch (InvalidKeyException e) {
            log.error("Cipher error.", e);
        } catch (NoSuchPaddingException e) {
            log.error("Cipher error.", e);
        } catch (BadPaddingException e) {
            log.error("Cipher error.", e);
        } catch (UnsupportedEncodingException e) {
            log.error("Cipher error.", e);
        } catch (IllegalBlockSizeException e) {
            log.error("Cipher error.", e);
        } catch (Exception e) {
            log.error("RSA Exception.", e);
        }

        return null;
    }

    public String publicKeyEncoded() {
        return RSAKeyEncoder.encode(this.publicKey);
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }
}
