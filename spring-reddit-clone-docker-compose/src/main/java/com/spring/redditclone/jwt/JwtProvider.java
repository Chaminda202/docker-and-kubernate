package com.spring.redditclone.jwt;

import com.spring.redditclone.config.JwtConfig;
import com.spring.redditclone.exception.SpringRedditException;
import com.spring.redditclone.exception.TokenExpiredException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    public String generateToken(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(this.secretKey)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusMillis(this.jwtConfig.getValidityPeriod() * 1000)))
                .compact();
    }

    public String generateTokenByUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .signWith(this.secretKey)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusMillis(this.jwtConfig.getValidityPeriod() * 1000)))
                .compact();
    }


    public boolean validateToken(String jwt) {
        try{
            Jwts.parser()
                    .setSigningKey(this.secretKey).parseClaimsJws(jwt);
            return true;
        } catch (ExpiredJwtException ex) {
            throw new TokenExpiredException("Token has been expired", ex);
        } catch (Exception e) {
            throw new SpringRedditException("Invalid access token");
        }
    }

    public String getUsernameJwt(String jwt) {
        Claims claims = Jwts
                .parser()
                .setSigningKey(this.secretKey)
                .parseClaimsJws(jwt)
                .getBody();
        return claims.getSubject();
    }

    public Date getExpirationJwt(String jwt) {
        Claims claims = Jwts
                .parser()
                .setSigningKey(this.secretKey)
                .parseClaimsJws(jwt)
                .getBody();
        return claims.getExpiration();
    }
}
