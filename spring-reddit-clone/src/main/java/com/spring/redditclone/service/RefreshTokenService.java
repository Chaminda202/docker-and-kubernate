package com.spring.redditclone.service;

import com.spring.redditclone.model.RefreshTokenRequest;

public interface RefreshTokenService {
    String createRefreshToken(RefreshTokenRequest refreshTokenRequest);
    void deleteRefreshToken(RefreshTokenRequest refreshTokenRequest);
}
