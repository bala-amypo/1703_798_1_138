package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.models.Guest;
import com.example.demo.service.GuestService;

import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/guests")
@Tag(name = "Guest API", description = "Operations related to guests")
public class GuestController {

    private final GuestService guestService;

    // Constructor Injection
    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    // 1️⃣ POST /api/guests → Create guest
    @PostMapping
    public ResponseEntity<Guest> createGuest(@RequestBody Guest guest) {
        Guest createdGuest = guestService.createGuest(guest);
        return new ResponseEntity<>(createdGuest, HttpStatus.CREATED);
    }

    // 2️⃣ PUT /api/guests/{id} → Update guest
    @PutMapping("/{id}")
    public ResponseEntity<Guest> updateGuest(
            @PathVariable Long id,
            @RequestBody Guest guest) {

        Guest updatedGuest = guestService.updateGuest(id, guest);
        return ResponseEntity.ok(updatedGuest);
    }

    // 3️⃣ GET /api/guests/{id} → Get guest by ID
    @GetMapping("/{id}")
    public ResponseEntity<Guest> getGuestById(@PathVariable Long id) {
        Guest guest = guestService.getGuestById(id);
        return ResponseEntity.ok(guest);
    }

    // 4️⃣ GET /api/guests → List all guests
    @GetMapping
    public ResponseEntity<List<Guest>> getAllGuests() {
        return ResponseEntity.ok(guestService.getAllGuests());
    }

    // 5️⃣ PUT /api/guests/{id}/deactivate → Deactivate guest
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<String> deactivateGuest(@PathVariable Long id) {
        guestService.deactivateGuest(id);
        return ResponseEntity.ok("Guest deactivated successfully");
    }
}
