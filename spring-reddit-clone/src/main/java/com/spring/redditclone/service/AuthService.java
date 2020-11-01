package com.spring.redditclone.service;

import com.spring.redditclone.model.AuthenticationResponse;
import com.spring.redditclone.model.LoginRequest;
import com.spring.redditclone.model.RefreshTokenRequest;
import com.spring.redditclone.model.RegisterRequest;
import com.spring.redditclone.model.entity.User;

public interface AuthService {
    void signup(RegisterRequest registerRequest);
    void accountVerification(String token);
    AuthenticationResponse login(LoginRequest loginRequest);
    User getCurrentUser();
    AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
    void logout(RefreshTokenRequest refreshTokenRequest);
    boolean isLoggedIn();
}
