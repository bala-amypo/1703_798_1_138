package com.example.demo.service.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Guest;
import com.example.demo.repository.GuestRepository;
import com.example.demo.service.GuestService;

@Service
public class GuestServiceImpl implements GuestService {

    private final GuestRepository guestRepository;
    private final PasswordEncoder passwordEncoder;

    // ✅ MUST match test constructor
    public GuestServiceImpl(GuestRepository guestRepository,
                            PasswordEncoder passwordEncoder) {
        this.guestRepository = guestRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Guest createGuest(Guest guest) {
        if (guestRepository.existsByEmail(guest.getEmail())) {
            throw new IllegalArgumentException("Email already");
        }

        guest.setPassword(passwordEncoder.encode(guest.getPassword()));
        guest.setActive(true);

        return guestRepository.save(guest);
    }

    @Override
    public Guest updateGuest(Long id, Guest guest) {
        Guest existing = getGuestById(id);

        existing.setFullName(guest.getFullName());
        existing.setPhoneNumber(guest.getPhoneNumber());
        existing.setVerified(guest.getVerified());
        existing.setRole(guest.getRole());
        existing.setActive(guest.getActive());

        return guestRepository.save(existing);
    }

    @Override
    public Guest getGuestById(Long id) {
        return guestRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Guest not found"));
    }

    @Override
    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }

    @Override
    public void deactivateGuest(Long id) {
        Guest guest = getGuestById(id);
        guest.setActive(false);
        guestRepository.save(guest);
    }
}
