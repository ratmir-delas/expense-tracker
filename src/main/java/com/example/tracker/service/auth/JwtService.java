package com.example.tracker.service.auth;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

public interface JwtService {

    String extractUsername(String jwt);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    String generateAccessToken(UserDetails userDetails);

    String generateRefreshToken(UserDetails userDetails);

    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails, long expirationMillis);

    boolean isTokenValid(String token, UserDetails userDetails);

}
