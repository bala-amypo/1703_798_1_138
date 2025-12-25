package com.example.demo.controller;

import com.example.demo.model.RoomBooking;
import com.example.demo.service.RoomBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class RoomBookingController {

    @Autowired
    private RoomBookingService bookingService;

    @PostMapping
    public ResponseEntity<RoomBooking> createBooking(@RequestBody RoomBooking booking) {
        RoomBooking created = bookingService.createBooking(booking);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomBooking> updateBooking(@PathVariable Long id, @RequestBody RoomBooking booking) {
        RoomBooking updated = bookingService.updateBooking(id, booking);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomBooking> getBooking(@PathVariable Long id) {
        RoomBooking booking = bookingService.getBookingById(id);
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/guest/{guestId}")
    public ResponseEntity<List<RoomBooking>> getBookingsForGuest(@PathVariable Long guestId) {
        List<RoomBooking> bookings = bookingService.getBookingsForGuest(guestId);
        return ResponseEntity.ok(bookings);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateBooking(@PathVariable Long id) {
        bookingService.deactivateBooking(id);
        return ResponseEntity.noContent().build();
    }
}