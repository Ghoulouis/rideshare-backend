package com.rideshare.rideshare.auth.service;

import com.rideshare.rideshare.auth.dto.LoginRequest;
import com.rideshare.rideshare.auth.dto.LoginResponse;
import com.rideshare.rideshare.auth.dto.RegisterRequest;
import com.rideshare.rideshare.auth.security.JwtService;
import com.rideshare.rideshare.user.entity.User;
import com.rideshare.rideshare.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String register(RegisterRequest request) {

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .build();

        userRepository.save(user);

        return "Register success";
    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow();

        boolean matches = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        if (!matches) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtService.generateToken(
                user.getId().toString()
        );

        return LoginResponse.builder()
                .token(token)
                .build();
    }
}
