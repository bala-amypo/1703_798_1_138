package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.models.RoomBooking;
import com.example.demo.service.RoomBookingService;

import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/bookings")
@Tag(name = "Room Booking API", description = "Operations related to room bookings")
public class RoomBookingController {

    private final RoomBookingService roomBookingService;

    // Constructor Injection
    public RoomBookingController(RoomBookingService roomBookingService) {
        this.roomBookingService = roomBookingService;
    }

    // 1️⃣ POST /api/bookings → Create booking
    @PostMapping
    public ResponseEntity<RoomBooking> createBooking(
            @RequestBody RoomBooking booking) {

        RoomBooking createdBooking =
                roomBookingService.createBooking(booking);

        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }

    // 2️⃣ PUT /api/bookings/{id} → Update booking
    @PutMapping("/{id}")
    public ResponseEntity<RoomBooking> updateBooking(
            @PathVariable Long id,
            @RequestBody RoomBooking booking) {

        RoomBooking updatedBooking =
                roomBookingService.updateBooking(id, booking);

        return ResponseEntity.ok(updatedBooking);
    }

    // 3️⃣ GET /api/bookings/{id} → Get booking by ID
    @GetMapping("/{id}")
    public ResponseEntity<RoomBooking> getBookingById(
            @PathVariable Long id) {

        RoomBooking booking =
                roomBookingService.getBookingById(id);

        return ResponseEntity.ok(booking);
    }

    // 4️⃣ GET /api/bookings/guest/{guestId} → List bookings for guest
    @GetMapping("/guest/{guestId}")
    public ResponseEntity<List<RoomBooking>> getBookingsForGuest(
            @PathVariable Long guestId) {

        List<RoomBooking> bookings =
                roomBookingService.getBookingsForGuest(guestId);

        return ResponseEntity.ok(bookings);
    }

    // 5️⃣ PUT /api/bookings/{id}/deactivate → Deactivate booking
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<String> deactivateBooking(
            @PathVariable Long id) {

        roomBookingService.deactivateBooking(id);
        return ResponseEntity.ok("Booking deactivated successfully");
    }
}
