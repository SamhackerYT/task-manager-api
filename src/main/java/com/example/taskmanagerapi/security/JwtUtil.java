package com.example.taskmanagerapi.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;

/**
 * Utility class for handling JWT token generation, validation, and claim extraction.
 * Compatible with JJWT 0.12.3 and Spring Boot 3.x.
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;  // Raw secret string from application.yml (at least 32 chars for HS256)

    @Value("${jwt.expiration}")
    private Long expiration;  // Token expiration in milliseconds

    /**
     * Gets the signing key from the secret (HS256 requires at least 256 bits / 32 bytes).
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        if (keyBytes.length < 32) {
            throw new IllegalArgumentException("JWT secret must be at least 32 characters long for HS256");
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Generates a basic JWT token for the given username.
     */
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return generateToken(claims, username);
    }

    /**
     * Generates a JWT token with custom claims (e.g., for roles).
     */
    public String generateToken(Map<String, Object> extraClaims, String username) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Validates the token against UserDetails (checks signature, expiration, and username match).
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Shortcut: Checks if token is valid for the user.
     */
    public Boolean isTokenValid(String token, UserDetails userDetails) {
        return validateToken(token, userDetails);
    }

    /**
     * Extracts the username (subject claim) from the token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts the expiration date from the token.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts a specific claim using a function resolver.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from the token (verifies signature first).
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Checks if the token has expired.
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}