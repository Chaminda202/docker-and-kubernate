package com.spring.redditclone.repository;

import com.spring.redditclone.model.entity.RefreshToken;
import com.spring.redditclone.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByTokenAndUser(String token, User user);
}
