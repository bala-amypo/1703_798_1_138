package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.Guest;
import com.example.demo.repository.GuestRepository;
import com.example.demo.security.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // =====================================================
    // REGISTER
    // =====================================================
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(
            @RequestBody RegisterRequest request) {

        // Check email exists
        if (guestRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Email already exists"));
        }

        // Create Guest
        Guest guest = new Guest();
        guest.setEmail(request.getEmail());
        guest.setPassword(passwordEncoder.encode(request.getPassword()));
        guest.setFullName(request.getFullName());
        guest.setPhoneNumber(request.getPhoneNumber());
        guest.setRole(request.getRole());
        guest.setActive(true);

        guestRepository.save(guest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "User registered successfully"));
    }

    // =====================================================
    // LOGIN
    // =====================================================
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(
            @RequestBody LoginRequest request) {

        // 1️⃣ Authenticate user
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );

        // 2️⃣ Fetch user from DB
        Guest guest = guestRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        // 3️⃣ Generate JWT
        String token = jwtTokenProvider.generateToken(
                guest.getId(),
                guest.getEmail(),
                guest.getRole()
        );

        // 4️⃣ Response data (NO additionalProp issue)
        Map<String, Object> data = new HashMap<>();
        data.put("guestId", guest.getId());
        data.put("email", guest.getEmail());
        data.put("token", token);

        return ResponseEntity.ok(
                new ApiResponse(true, "Login successful", data)
        );
    }
}
