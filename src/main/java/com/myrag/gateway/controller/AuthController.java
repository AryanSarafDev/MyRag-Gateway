package com.myrag.gateway.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myrag.gateway.Service.AuthService;
import com.myrag.gateway.controller.AuthController.TokenResponse;
import com.myrag.gateway.models.User;

import lombok.Data;


@Controller
@RequestMapping("/api/v1/auth")
public class AuthController {
private final AuthService authService;

public AuthController(AuthService authService) {
    this.authService = authService;
}


@Data
static class RegisterRequest {
    private String email;
    private String password;
    private String apiKey;
}

@Data
static class LoginRequest {
    private String email;
    private String password;}

@Data
static class TokenResponse {
    private String token;
    public TokenResponse(String token) {
        this.token = token;
    }
}

@PostMapping("/register")
public ResponseEntity<User> register(@RequestBody RegisterRequest request) {
    User user = authService.register(request.getEmail(), request.getPassword(), request.getApiKey());
    return ResponseEntity.ok(user);
}

@PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        Optional<String> token = authService.login(
            request.getEmail(),
            request.getPassword()
        );


        return token
            .map(t -> ResponseEntity.ok(new TokenResponse(t)))
            .orElse(ResponseEntity.status(401).build());
    }


}
