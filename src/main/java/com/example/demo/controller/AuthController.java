package com.example.demo.controller;

import com.example.demo.model.Guest;
import com.example.demo.service.GuestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints for user registration and login")
public class AuthController {

    @Autowired
    private GuestService guestService;

    @PostMapping("/register")
    public ResponseEntity<Guest> register(@RequestBody Guest guest) {
        return ResponseEntity.ok(guestService.createGuest(guest));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginRequest) {
        Guest guest = guestService.loginGuest(
            loginRequest.get("email"), 
            loginRequest.get("password")
        );
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login successful");
        response.put("guestId", guest.getId());
        response.put("email", guest.getEmail());
        response.put("token", "dummy-jwt-token-string"); 

        return ResponseEntity.ok(response);
    }
}