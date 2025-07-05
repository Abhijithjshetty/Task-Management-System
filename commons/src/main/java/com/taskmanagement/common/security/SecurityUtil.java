package com.taskmanagement.common.security;

import com.taskmanagement.common.exception.SignatureVerificationException;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jose.util.Base64URL;
import jakarta.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.Map;

@Slf4j
public class SecurityUtil {

	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	public static String encryptAndSign(String message, RSAPublicKey encryptionKey, RSAPrivateKey signingKey,
                                        String signingKeyFingerPrint, String encryptionCertFingerPrint, String clientid) throws JOSEException {

        JWEEncrypter encrypter = new RSAEncrypter(encryptionKey);
        JWEHeader jweHeader = new JWEHeader.Builder(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A128GCM)
            .x509CertSHA256Thumbprint(new Base64URL(encryptionCertFingerPrint))
            .keyID(clientid)
            .build();
        Payload payload = new Payload(message);
        JWEObject jweObject = new JWEObject(jweHeader, payload);
        jweObject.encrypt(encrypter);
        String encryptedString = jweObject.serialize();
        JWSSigner jwsSigner = new RSASSASigner(signingKey);
        JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.PS256)
            .x509CertSHA256Thumbprint(new Base64URL(signingKeyFingerPrint))
            .keyID(clientid).build();
        JWSObject jwsObject = new JWSObject(jwsHeader, new Payload(encryptedString));
        jwsObject.sign(jwsSigner);
        return jwsObject.serialize();
	}

	public static String verifyAndDecrypt(String encryptedSignedMessage, RSAPublicKey verificationKey,
                                          RSAPrivateKey decryptionKey) throws ParseException, SignatureVerificationException, JOSEException  {

        JWSObject jwsObject = JWSObject.parse(encryptedSignedMessage);
        JWSVerifier verifier = new RSASSAVerifier(verificationKey);
        if (!jwsObject.verify(verifier)) {
            throw new SignatureVerificationException("Unable to verify message signature");
        }
        String encryptedMessage = jwsObject.getPayload().toString();
        JWEObject jweObject = JWEObject.parse(encryptedMessage);
        JWEDecrypter decrypter = new RSADecrypter(decryptionKey);
        jweObject.decrypt(decrypter);
        return jweObject.getPayload().toString();
	}

	public static String jwsSign(String payload, RSAPrivateKey signingKey) throws JOSEException {
        JWSSigner jwsSigner = new RSASSASigner(signingKey);
        JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.RS256).type(JOSEObjectType.JWT).build();
        JWSObject jwsObject = new JWSObject(jwsHeader, new Payload(payload));
        jwsObject.sign(jwsSigner);
        return jwsObject.serialize();
    }

    public static Boolean jswVerify(String signedMessage, RSAPublicKey verificationKey) throws ParseException, JOSEException {
	    JWSObject jwsObject = JWSObject.parse(signedMessage);
        JWSVerifier verifier = new RSASSAVerifier(verificationKey);
        if (jwsObject.verify(verifier)) {
            return true;
        }else return false;

    }

    public static void verify(String encryptedSignedMessage, RSAPublicKey verificationKey,
                              String clientid) throws ParseException, SignatureVerificationException, JOSEException  {

        JWSObject jwsObject = JWSObject.parse(encryptedSignedMessage);
        JWSHeader jwsHeader = jwsObject.getHeader();
        JWSVerifier verifier = new RSASSAVerifier(verificationKey);
        if (!jwsObject.verify(verifier)) {
            throw new SignatureVerificationException("Unable to verify message signature");
        }else log.info("verified");
        log.info(jwsHeader.getCustomParam(clientid).toString());
        log.info(jwsHeader.getX509CertSHA256Thumbprint().toString());
    }

    public static String encryptAndSign(String message,
                                        RSAPublicKey encryptionKey,
                                        RSAPrivateKey signingKey,
                                        String signingKeyFingerPrint,
                                        String encryptionCertFingerPrint,
                                        Map<String, String> customJwsHeader,
                                        Map<String, String> customJweHeader,
                                        String clientid) throws JOSEException {

        JWEEncrypter encrypter = new RSAEncrypter(encryptionKey);


        JWEHeader.Builder jweHeaderBuilder = new JWEHeader.Builder(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A128GCM);
        jweHeaderBuilder.x509CertSHA256Thumbprint(new Base64URL(encryptionCertFingerPrint));
        if (customJweHeader != null) {
            for (Map.Entry<String, String> entry : customJwsHeader.entrySet()) {
                jweHeaderBuilder.customParam(entry.getKey(), entry.getValue());
            }
        }
        jweHeaderBuilder.keyID(clientid);
        JWEHeader jweHeader = jweHeaderBuilder.build();

        Payload payload = new Payload(message);
        JWEObject jweObject = new JWEObject(jweHeader, payload);
        jweObject.encrypt(encrypter);
        String encryptedString = jweObject.serialize();
        JWSSigner jwsSigner = new RSASSASigner(signingKey);

        JWSHeader.Builder jwsHeaderBuilder = new JWSHeader.Builder(JWSAlgorithm.PS256);
        jwsHeaderBuilder.x509CertSHA256Thumbprint(new Base64URL(signingKeyFingerPrint));
        jwsHeaderBuilder.keyID(clientid);

        if (customJwsHeader != null) {
            for (Map.Entry<String, String> entry : customJwsHeader.entrySet()) {
                jwsHeaderBuilder.customParam(entry.getKey(), entry.getValue());
            }
        }

        JWSHeader jwsHeader = jwsHeaderBuilder.build();

        JWSObject jwsObject = new JWSObject(jwsHeader, new Payload(encryptedString));
        jwsObject.sign(jwsSigner);
        return jwsObject.serialize();
    }

    public static String jweDecrypt(String data, String keyInfo) throws Exception {

        try {
            SecretKey key = new SecretKeySpec(DatatypeConverter.parseHexBinary(keyInfo), "AES");
            JWEObject jweObject = JWEObject.parse(data);
            jweObject.decrypt(new DirectDecrypter(key));
            String jweDecryptedData = jweObject.getPayload().toString();
            return jweDecryptedData;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
