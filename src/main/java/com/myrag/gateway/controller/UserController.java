package com.myrag.gateway.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myrag.gateway.Service.UserService;
import com.myrag.gateway.models.User;

import lombok.Data;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Data
    static class CreateUserRequest{
        private String email;
        private String apiKey;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest request) {
        if (userService.userExists(request.getEmail())) {
            return ResponseEntity.status(409).build();
        }
        User created = userService.createUser(request.getEmail(), request.getApiKey());
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping
    public ResponseEntity<User> getUser(@RequestParam String email) {
        Optional<User> user = userService.findByEmail(email);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }
}
