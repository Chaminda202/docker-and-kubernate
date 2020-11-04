package com.spring.redditclone.controller;

import com.spring.redditclone.model.AuthenticationResponse;
import com.spring.redditclone.model.LoginRequest;
import com.spring.redditclone.model.RefreshTokenRequest;
import com.spring.redditclone.model.RegisterRequest;
import com.spring.redditclone.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = "signup")
    public ResponseEntity<Void> signup(@RequestBody RegisterRequest registerRequest) {
        this.authService.signup(registerRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "accountVerification/{token}")
    public ResponseEntity<Void> accountVerification(@PathVariable(value = "token") String token)  {
        this.authService.accountVerification(token);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        AuthenticationResponse response = this.authService.login(loginRequest);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "refresh/token")
    public ResponseEntity<AuthenticationResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        AuthenticationResponse response = this.authService.refreshToken(refreshTokenRequest);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        this.authService.logout(refreshTokenRequest);
        return ResponseEntity.noContent().build();
    }
}
