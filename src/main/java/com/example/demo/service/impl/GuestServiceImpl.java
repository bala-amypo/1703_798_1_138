package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Guest;
import com.example.demo.repository.GuestRepository;
import com.example.demo.service.GuestService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestServiceImpl implements GuestService {

    private final GuestRepository guestRepository;
    private final PasswordEncoder passwordEncoder;

    public GuestServiceImpl(GuestRepository guestRepository,
                            PasswordEncoder passwordEncoder) {
        this.guestRepository = guestRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Guest createGuest(Guest guest) {

        if (guestRepository.existsByEmail(guest.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        guest.setPassword(passwordEncoder.encode(guest.getPassword()));
        return guestRepository.save(guest);
    }

    @Override
    public Guest updateGuest(Long id, Guest updated) {

        Guest existing = guestRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Guest not found: " + id));

        existing.setFullName(updated.getFullName());
        existing.setPhoneNumber(updated.getPhoneNumber());
        existing.setVerified(updated.getVerified());
        existing.setActive(updated.getActive());
        existing.setRole(updated.getRole());

        return guestRepository.save(existing);
    }

    @Override
    public Guest getGuestById(Long id) {

        return guestRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Guest not found: " + id));
    }

    @Override
    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }

    @Override
    public void deactivateGuest(Long id) {

        Guest guest = guestRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Guest not found: " + id));

        guest.setActive(false);
        guestRepository.save(guest);
    }
}
