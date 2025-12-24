package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.KeyShareRequest;
import com.example.demo.service.KeyShareRequestService;

import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/key-share")
@Tag(name = "Key Share Request API", description = "Operations related to key sharing requests")
public class KeyShareRequestController {

    private final KeyShareRequestService keyShareRequestService;

    // Constructor Injection
    public KeyShareRequestController(KeyShareRequestService keyShareRequestService) {
        this.keyShareRequestService = keyShareRequestService;
    }

    // 1️⃣ POST /api/key-share → Create share request
    @PostMapping
    public ResponseEntity<KeyShareRequest> createShareRequest(
            @RequestBody KeyShareRequest request) {

        KeyShareRequest created =
                keyShareRequestService.createShareRequest(request);

        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // 2️⃣ PUT /api/key-share/{id}/status → Update status
    @PutMapping("/{id}/status")
    public ResponseEntity<KeyShareRequest> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        KeyShareRequest updated =
                keyShareRequestService.updateStatus(id, status);

        return ResponseEntity.ok(updated);
    }

    // 3️⃣ GET /api/key-share/{id} → Get request by ID
    @GetMapping("/{id}")
    public ResponseEntity<KeyShareRequest> getRequestById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                keyShareRequestService.getShareRequestById(id)
        );
    }

    // 4️⃣ GET /api/key-share/shared-by/{guestId}
    @GetMapping("/shared-by/{guestId}")
    public ResponseEntity<List<KeyShareRequest>> getRequestsSharedBy(
            @PathVariable Long guestId) {

        return ResponseEntity.ok(
                keyShareRequestService.getRequestsSharedBy(guestId)
        );
    }

    // 5️⃣ GET /api/key-share/shared-with/{guestId}
    @GetMapping("/shared-with/{guestId}")
    public ResponseEntity<List<KeyShareRequest>> getRequestsSharedWith(
            @PathVariable Long guestId) {

        return ResponseEntity.ok(
                keyShareRequestService.getRequestsSharedWith(guestId)
        );
    }
}
