package com.example.tracker.service.auth;

import com.example.tracker.dto.auth.*;

public interface AuthenticationService {

    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    TokenRefreshResponse refresh(TokenRefreshRequest request);

}
