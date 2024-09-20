package com.example.tracker.service.auth;

import com.example.tracker.model.user.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

public interface JwtService {

    String extractUsername(String jwt);

    Long extractUserId(String jwt);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    String generateAccessToken(User user);

    String generateRefreshToken(User user);

    String generateToken(Map<String, Object> extraClaims, User user, long expirationMillis);

    boolean isTokenValid(String token, UserDetails userDetails);

}
