package com.example.demo.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.models.DigitalKey;
import com.example.demo.models.Guest;
import com.example.demo.models.RoomBooking;
import com.example.demo.repository.DigitalKeyRepository;
import com.example.demo.repository.GuestRepository;
import com.example.demo.repository.RoomBookingRepository;
import com.example.demo.service.DigitalKeyService;

@Service
public class DigitalKeyServiceImpl implements DigitalKeyService {

    private final DigitalKeyRepository digitalKeyRepository;
    private final RoomBookingRepository bookingRepository;
    private final GuestRepository guestRepository;

    // Constructor Injection
    public DigitalKeyServiceImpl(DigitalKeyRepository digitalKeyRepository,
                                 RoomBookingRepository bookingRepository,
                                 GuestRepository guestRepository) {
        this.digitalKeyRepository = digitalKeyRepository;
        this.bookingRepository = bookingRepository;
        this.guestRepository = guestRepository;
    }

    // 1️⃣ Generate Key (booking must be active)
    @Override
    public DigitalKey generateKey(Long bookingId) {

        RoomBooking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                            "RoomBooking not found with id: " + bookingId
                        ));

        if (!Boolean.TRUE.equals(booking.getActive())) {
            throw new IllegalStateException(
                "Cannot generate key for inactive booking"
            );
        }

        DigitalKey key = new DigitalKey();
        key.setBooking(booking);
        key.setKeyValue(generateUniqueKey());
        key.setIssuedAt(new Timestamp(System.currentTimeMillis()));
        key.setActive(true);

        return digitalKeyRepository.save(key);
    }

    // 2️⃣ Get key by ID
    @Override
    public DigitalKey getKeyById(Long id) {

        return digitalKeyRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                            "DigitalKey not found with id: " + id
                        ));
    }

    // 3️⃣ Get active key for a booking
    @Override
    public DigitalKey getActiveKeyForBooking(Long bookingId) {

        RoomBooking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                            "RoomBooking not found with id: " + bookingId
                        ));

        return digitalKeyRepository
                .findByBookingAndActiveTrue(booking)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                            "No active key found for booking id: " + bookingId
                        ));
    }

    // 4️⃣ Get all keys for a guest
    @Override
    public List<DigitalKey> getKeysForGuest(Long guestId) {

        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                            "Guest not found with id: " + guestId
                        ));

        return digitalKeyRepository.findByBooking_Guest(guest);
    }

    // 🔑 Utility: generate unique key
    private String generateUniqueKey() {
        return UUID.randomUUID().toString();
    }
}
