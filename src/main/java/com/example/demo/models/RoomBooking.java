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

    public RoomBookingController(RoomBookingService roomBookingService) {
        this.roomBookingService = roomBookingService;
    }

    // 1️⃣ Create booking
    // POST /api/bookings?guestId=1
    @PostMapping
    public ResponseEntity<RoomBooking> createBooking(
            @RequestParam Long guestId,
            @RequestBody RoomBooking booking) {

        RoomBooking createdBooking =
                roomBookingService.createBooking(booking, guestId);

        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }

    // 2️⃣ Update booking (dates / room only)
    @PutMapping("/{id}")
    public ResponseEntity<RoomBooking> updateBooking(
            @PathVariable Long id,
            @RequestBody RoomBooking booking) {

        RoomBooking updatedBooking =
                roomBookingService.updateBooking(id, booking);

        return ResponseEntity.ok(updatedBooking);
    }

    // 3️⃣ Get booking by ID
    @GetMapping("/{id}")
    public ResponseEntity<RoomBooking> getBookingById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                roomBookingService.getBookingById(id)
        );
    }

    // 4️⃣ Get all bookings for a guest
    @GetMapping("/guest/{guestId}")
    public ResponseEntity<List<RoomBooking>> getBookingsForGuest(
            @PathVariable Long guestId) {

        return ResponseEntity.ok(
                roomBookingService.getBookingsForGuest(guestId)
        );
    }

    // 5️⃣ Deactivate booking
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<String> deactivateBooking(
            @PathVariable Long id) {

        roomBookingService.deactivateBooking(id);
        return ResponseEntity.ok("Booking deactivated successfully");
    }
}
