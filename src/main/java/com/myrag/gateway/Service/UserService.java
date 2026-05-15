package com.myrag.gateway.Service;

import java.util.Optional;

import com.myrag.gateway.models.User;
import com.myrag.gateway.repository.UserRepository;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String email, String apiKey){
        User user = new User();
        user.setEmail(email);
        user.setHashedApiKey(apiKey);
        return userRepository.save(user);
    }

     public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean userExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public java.util.List<User> getAllActiveUsers() {
        return userRepository.findByActiveTrue();
    }

}
