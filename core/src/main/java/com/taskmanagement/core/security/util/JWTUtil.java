package com.taskmanagement.core.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private static final String IAT = "iat";
    private static final String EXT = "ext";
    // Generate token for user
    public String generateToken(String username,Map<String, Object> claims) {
        return createToken(claims, username);
    }

    // Create token
    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + expiration*60*60*1000);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS512, Base64.getDecoder().decode(secret))
                .compact();
    }


    // Extract user information from token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(Base64.getDecoder().decode(secret)).parseClaimsJws(token).getBody();
    }

    // Extract username from token
    public String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract "iat" (issued at) from token
    public Date extractIssuedAt(String token) {
        return extractClaim(token, claims -> claims.get(IAT, Date.class));
    }

    // Extract "ext" (expiration time) from token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    // Check if the token is expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = extractClaim(token, Claims::getExpiration);
        return expiration.before(new Date());
    }

    // Validate token
    public Boolean validateToken(String token) {
        return (!isTokenExpired(token));
    }
}