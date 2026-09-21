package com.manav.SpringAI.controller;

import com.manav.SpringAI.dto.LoginRequest;
import com.manav.SpringAI.dto.RegisterRequest;
import com.manav.SpringAI.dto.UserResponse;
import com.manav.SpringAI.model.User;
import com.manav.SpringAI.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        if (request.getEmail() == null || request.getEmail().isBlank()) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", "Email is required"));
        }

        if (request.getPassword() == null || request.getPassword().isBlank()) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", "Password is required"));
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "Email already exists"));
        }

        User user = new User();

        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        String name = request.getName();

        if (name != null && !name.isBlank()) {
            user.setName(name);
        } else {
            user.setName(request.getEmail().split("@")[0]);
        }

        user.setAuthProvider("LOCAL");
        user.setProfession("Developer");
        user.setCompany("Independent");
        user.setAge(22);
        user.setAvatar(
                "https://api.dicebear.com/7.x/avataaars/svg?seed="
                        + user.getName()
        );
        user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        return ResponseEntity.ok(convertToResponse(savedUser));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        if (request.getEmail() == null || request.getEmail().isBlank()) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", "Email is required"));
        }

        if (request.getPassword() == null || request.getPassword().isBlank()) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", "Password is required"));
        }

        Optional<User> userOpt =
                userRepository.findByEmail(request.getEmail());

        if (userOpt.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid email or password"));
        }

        User user = userOpt.get();

        if (user.getPassword() == null ||
                !passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                )) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid email or password"));
        }

        return ResponseEntity.ok(convertToResponse(user));
    }

    private UserResponse convertToResponse(User user) {

        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getAuthProvider(),
                user.getName(),
                user.getAvatar(),
                user.getAge(),
                user.getProfession(),
                user.getCompany()
        );
    }
}