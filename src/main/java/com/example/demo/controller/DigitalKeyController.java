package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.DigitalKey;
import com.example.demo.service.DigitalKeyService;

import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/digital-keys")
@Tag(name = "Digital Key API", description = "Operations related to digital keys")
public class DigitalKeyController {

    private final DigitalKeyService digitalKeyService;

    // Constructor Injection
    public DigitalKeyController(DigitalKeyService digitalKeyService) {
        this.digitalKeyService = digitalKeyService;
    }

    // 1️⃣ POST /api/digital-keys/generate/{bookingId} → Generate key
    @PostMapping("/generate/{bookingId}")
    public ResponseEntity<DigitalKey> generateKey(
            @PathVariable Long bookingId) {

        DigitalKey key = digitalKeyService.generateKey(bookingId);
        return new ResponseEntity<>(key, HttpStatus.CREATED);
    }

    // 2️⃣ GET /api/digital-keys/{id} → Get key by ID
    @GetMapping("/{id}")
    public ResponseEntity<DigitalKey> getKeyById(
            @PathVariable Long id) {

        DigitalKey key = digitalKeyService.getKeyById(id);
        return ResponseEntity.ok(key);
    }

    // 3️⃣ GET /api/digital-keys/booking/{bookingId} → Get active key for booking
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<DigitalKey> getActiveKeyForBooking(
            @PathVariable Long bookingId) {

        DigitalKey key =
                digitalKeyService.getActiveKeyForBooking(bookingId);

        return ResponseEntity.ok(key);
    }

    // 4️⃣ GET /api/digital-keys/guest/{guestId} → List keys for guest
    @GetMapping("/guest/{guestId}")
    public ResponseEntity<List<DigitalKey>> getKeysForGuest(
            @PathVariable Long guestId) {

        List<DigitalKey> keys =
                digitalKeyService.getKeysForGuest(guestId);

        return ResponseEntity.ok(keys);
    }
}
