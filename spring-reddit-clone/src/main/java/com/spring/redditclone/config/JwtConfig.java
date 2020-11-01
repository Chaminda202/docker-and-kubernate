package com.spring.redditclone.config;

import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
@ConfigurationProperties(prefix = "jwt.application")
@Data
@NoArgsConstructor
public class JwtConfig {
    private String secret;
    private String tokenPrefix;
    private long validityPeriod;
    private String authorizationHeader;

    @Bean
    public SecretKey secretKey(){
        return Keys.hmacShaKeyFor(this.secret.getBytes());
    }
}