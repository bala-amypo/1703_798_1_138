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

    public DigitalKeyServiceImpl(
            DigitalKeyRepository digitalKeyRepository,
            RoomBookingRepository bookingRepository,
            GuestRepository guestRepository) {
        this.digitalKeyRepository = digitalKeyRepository;
        this.bookingRepository = bookingRepository;
        this.guestRepository = guestRepository;
    }

    @Override
    public DigitalKey generateKey(Long bookingId) {

        RoomBooking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "RoomBooking not found with id: " + bookingId));

        if (!booking.isActive()) {
            throw new IllegalStateException(
                    "Cannot generate digital key for inactive booking");
        }

        // ❌ Prevent duplicates
        digitalKeyRepository.findByBooking(booking)
                .ifPresent(k -> {
                    throw new IllegalStateException(
                            "Digital key already exists for this booking");
                });

        DigitalKey key = new DigitalKey();
        key.setBooking(booking);
        key.setKeyValue(UUID.randomUUID().toString());
        key.setIssuedAt(new Timestamp(System.currentTimeMillis()));
        key.setExpiresAt(
                Timestamp.valueOf(
                        booking.getCheckOutDate().atStartOfDay()
                )
        );
        key.setActive(true);

        return digitalKeyRepository.save(key);
    }

    @Override
    public DigitalKey getKeyById(Long id) {
        return digitalKeyRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "DigitalKey not found with id: " + id));
    }

    @Override
    public DigitalKey getActiveKeyForBooking(Long bookingId) {

        RoomBooking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "RoomBooking not found with id: " + bookingId));

        return digitalKeyRepository
                .findByBookingAndActiveTrue(booking)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No active digital key found for booking id: " + bookingId));
    }

    @Override
    public List<DigitalKey> getKeysForGuest(Long guestId) {

        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Guest not found with id: " + guestId));

        return digitalKeyRepository.findByBooking_Guest(guest);
    }
}
