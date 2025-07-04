package com.sushikhacapitals.common.utils;


import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class JWTUtils {
    private final Key privateKey;
    private final Key publicKey;

    public JWTUtils(Key privateKey, Key publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public JWTUtils(SecretKey secret) {
        this.privateKey = secret;
        this.publicKey = secret;
    }

    public String generateToken(Map<String, Object> headers, Map<String, Object> claims, int expirationOffset) {
        JwtBuilder builder = Jwts.builder();
        builder.setHeader(headers);
        builder.setClaims(claims);

        builder.setIssuedAt(new Date(System.currentTimeMillis()));

        if(expirationOffset != 0) {
            builder.setExpiration(new Date(System.currentTimeMillis() + expirationOffset));
        } else {
            builder.setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 10)));
        }

        builder.signWith(privateKey);
        return builder.compact();
    }

    public Jws<Claims> parseToken(String jws) throws ExpiredJwtException {

        return Jwts.parserBuilder()
            .setSigningKey(publicKey)
            .build().parseClaimsJws(jws);
    }

    public Boolean isTokenExpired(String token) throws ExpiredJwtException {
        try {
            Jws<Claims> jwt = parseToken(token);
            return jwt.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }
}
