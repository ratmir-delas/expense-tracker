package com.example.tracker.service;

import com.example.tracker.model.auth.AuthenticationRequest;
import com.example.tracker.model.auth.AuthenticationResponse;
import com.example.tracker.model.auth.RegisterRequest;

public interface AuthenticationService {

    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

}
