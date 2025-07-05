package com.taskmanagement.common.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.SecureRandom;
import java.util.Base64;

@Slf4j
public class AESUtil implements InitializingBean {
    private static SecretKey key;
    private static final int KEY_SIZE_BITS = 128;
    private static final int KEY_SIZE_BYTES = KEY_SIZE_BITS / 8;
    private static final int T_LEN = 128;
    private static final int IV_SIZE_BYTES = 12; // 96 bits for GCM
    private static Cipher encryptionCipher;
    private static Cipher decryptionCipher;

    // Inject the key from application.yml
    @Value("${encryption.aes-key}")
    private String aesKey;

    @Value("${encryption.aes-iv}")
    private String aesIv;

    public String encrypt(String message) {
        try {
            byte[] ivBytes = generateRandomIV(); // Generate a random IV for each encryption

            // Initialize the encryption cipher with the random IV
            encryptionCipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(T_LEN, ivBytes));

            byte[] messageBytes = message.getBytes();
            byte[] encryptedBytes = encryptionCipher.doFinal(messageBytes);

            // Concatenate the IV and the encrypted data
            byte[] result = new byte[ivBytes.length + encryptedBytes.length];
            System.arraycopy(ivBytes, 0, result, 0, ivBytes.length);
            System.arraycopy(encryptedBytes, 0, result, ivBytes.length, encryptedBytes.length);

            return encode(result);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String decrypt(String encryptedMessage) {
        try {
            // Decode the input
            byte[] inputBytes = decode(encryptedMessage);

            // Extract the IV
            byte[] ivBytes = new byte[IV_SIZE_BYTES];
            System.arraycopy(inputBytes, 0, ivBytes, 0, IV_SIZE_BYTES);

            // Initialize the decryption cipher with the IV
            decryptionCipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(T_LEN, ivBytes));

            // Decrypt the message
            byte[] encryptedData = new byte[inputBytes.length - IV_SIZE_BYTES];
            System.arraycopy(inputBytes, IV_SIZE_BYTES, encryptedData, 0, encryptedData.length);
            byte[] decryptedBytes = decryptionCipher.doFinal(encryptedData);

            return new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    private byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }

    private byte[] generateRandomIV() {
        byte[] iv = new byte[IV_SIZE_BYTES];
        new SecureRandom().nextBytes(iv);
        return iv;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(aesKey);

            if (keyBytes.length != KEY_SIZE_BYTES) {
                throw new InvalidKeyException("Invalid AES key length");
            }

            key = new SecretKeySpec(keyBytes, "AES");

            // Use a different IV for encryption and decryption
            encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
            decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Exception:", e.toString());
        }
    }
}

