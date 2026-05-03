package com.eAuction.e_backend.vo;

import com.eAuction.e_backend.Entity.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
public class Util {

    @Value("${jwt.secret}")
    private String secretKeyString;
    // Using the recommended SecretKey type for JJWT 0.12.x
    private static SecretKey SECRET_KEY;
    private static final long EXPIRATION_TIME = 1800000; // 30 min

    @PostConstruct
    public void init() {
        if (secretKeyString == null || secretKeyString.trim().isEmpty()) {
            throw new IllegalStateException("JWT secret key must be configured in application.properties");
        }
        // Decode Base64 string to byte[] and create SecretKey for HMAC-SHA
        byte[] keyBytes = Base64.getDecoder().decode(secretKeyString);
        SECRET_KEY = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Users userDetails) {
        Map<String, Object> claims = new HashMap<>();

        // Storing roles as plain strings is much cleaner for JWT payloads
        List<String> roles = List.of(userDetails.getType().split(","));
        claims.put("roles", roles);

        return createToken(claims, userDetails.getUserName());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY) // No need to specify algorithm, it infers from the key
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Boolean isTokenValid(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        // Updated to the modern parserBuilder equivalent in JJWT 0.12.x
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}