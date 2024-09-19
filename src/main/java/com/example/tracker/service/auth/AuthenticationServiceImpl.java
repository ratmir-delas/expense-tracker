package com.example.tracker.service.auth;

import com.example.tracker.dto.auth.*;
import com.example.tracker.model.token.Token;
import com.example.tracker.model.token.TokenType;
import com.example.tracker.model.user.Role;
import com.example.tracker.model.user.User;
import com.example.tracker.repository.TokenRepository;
import com.example.tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        // Create a new user
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        // Save the user and generate tokens
        var savedUser = userRepository.save(user);
        return getAuthenticationResponse(savedUser);
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // Authenticate the user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return getAuthenticationResponse(user);
    }

    @Override
    public TokenRefreshResponse refresh(TokenRefreshRequest request) {
        // Validate the refresh token
        String requestRefreshToken = request.getRefreshToken();
        var refreshToken = tokenRepository.findByToken(requestRefreshToken)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
        if (refreshToken.isRevoked()) {
            throw new RuntimeException("Expired refresh token");
        }

        // Generate and save a new access token
        var user = refreshToken.getUser();
        var newAccessToken = jwtService.generateAccessToken(user);
        revokeAccessTokens(user);
        saveUserToken(user, newAccessToken, TokenType.ACCESS);

        return TokenRefreshResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(requestRefreshToken)  // Return the same refresh token as it’s still valid
                .build();
    }

    private AuthenticationResponse getAuthenticationResponse(User user) {
        // Generate and save tokens
        var jwtToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAccessTokens(user);
        saveUserToken(user, jwtToken, TokenType.ACCESS);
        saveUserToken(user, refreshToken, TokenType.REFRESH);
        // Return the tokens
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void revokeAllUserTokens(User user) {
        var userTokens = tokenRepository.findAllPermittedTokensByUserId(user.getId());
        if (!userTokens.isEmpty()) {
            userTokens.forEach(token -> {
                token.setRevoked(true);
            });
            tokenRepository.saveAll(userTokens);
        }
    }

    private void revokeAccessTokens(User user) {
        var userTokens = tokenRepository.findAllActivePermittedTokensByUserId(user.getId());
        if (!userTokens.isEmpty()) {
            userTokens.stream()
                    .filter(token -> token.getType() == TokenType.ACCESS)
                    .forEach(token -> {
                        token.setRevoked(true);
                    });
            tokenRepository.saveAll(userTokens);
        }
    }

    private void saveUserToken(User savedUser, String jwtToken, TokenType tokenType) {
        var token = Token.builder()
                .user(savedUser)
                .token(jwtToken)
                .type(tokenType)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }
}
