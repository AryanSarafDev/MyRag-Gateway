package com.myrag.gateway.Service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.myrag.gateway.models.User;
import com.myrag.gateway.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public User register(String email, String password, String apiKey) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setHashedApiKey(apiKey);
        return userRepository.save(user);
    }

    public Optional<String> login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            return Optional.empty();
        }

        boolean passwordMatches = passwordEncoder.matches(password, user.get().getPassword());

        if (!passwordMatches) {
            return Optional.empty();
        }

        String token = jwtService.generateToken(email);
        return Optional.of(token);

    }
}
