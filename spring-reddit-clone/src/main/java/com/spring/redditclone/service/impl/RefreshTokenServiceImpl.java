package com.spring.redditclone.service.impl;

import com.spring.redditclone.exception.RecordNotExistException;
import com.spring.redditclone.model.RefreshTokenRequest;
import com.spring.redditclone.model.entity.RefreshToken;
import com.spring.redditclone.model.entity.User;
import com.spring.redditclone.repository.RefreshTokenRepository;
import com.spring.redditclone.repository.UserRepository;
import com.spring.redditclone.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Override
    public String createRefreshToken(RefreshTokenRequest refreshTokenRequest) {
        User user = this.userRepository.findByUsername(refreshTokenRequest.getUsername())
                .orElseThrow(() -> new RecordNotExistException("User Entity not found by username: " + refreshTokenRequest.getUsername()));
        RefreshToken refreshToken = RefreshToken.builder()
                .createdDate(Instant.now())
                .token(UUID.randomUUID().toString())
                .user(user)
                .build();
        this.refreshTokenRepository.save(refreshToken);
        return refreshToken.getToken();
    }

    @Override
    public void deleteRefreshToken(RefreshTokenRequest refreshTokenRequest) {
        User user = this.userRepository.findByUsername(refreshTokenRequest.getUsername())
                .orElseThrow(() -> new RecordNotExistException("User Entity not found by username: " + refreshTokenRequest.getUsername()));
        RefreshToken refreshToken = this.refreshTokenRepository.findByTokenAndUser(refreshTokenRequest.getRefreshToken(), user)
                .orElseThrow(() -> new RecordNotExistException("RefreshToken Entity not found by username: " + refreshTokenRequest.getUsername()
                        + " and refresh token: " + refreshTokenRequest.getRefreshToken()));
        this.refreshTokenRepository.delete(refreshToken);
    }
}
