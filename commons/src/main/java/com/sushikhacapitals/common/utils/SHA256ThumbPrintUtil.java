package com.sushikhacapitals.common.utils;

import com.nimbusds.jose.util.Base64URL;
import com.nimbusds.jose.util.X509CertUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class SHA256ThumbPrintUtil {
    public static String generateSHA256ThumbPrin(byte[] cert) {
       try {

           X509Certificate certificate = X509CertUtils.parse(cert);
           return Base64URL.encode(MessageDigest.getInstance("SHA-256").digest(certificate.getEncoded())).toString();
       }catch (CertificateException | NoSuchAlgorithmException | NullPointerException e){
           return "";
       }
    }
}
