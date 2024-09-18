package com.example.tracker.service.auth;

import com.example.tracker.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        // Extract JWT token from Authorization header
        final String authHeader = request.getHeader("Authorization");
        final String jwt;

        // Validate Authorization header
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        // Revoke the token
        jwt = authHeader.substring(7);
        var token = tokenRepository.findByToken(jwt).orElseThrow(null);
        if (token != null) {
            token.setExpired(true);
            token.setRevoked(true);
            tokenRepository.save(token);
        }
    }
}
