package com.example.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.models.Guest;
import com.example.demo.repository.GuestRepository;
import com.example.demo.service.GuestService;

@Service
public class GuestServiceImpl implements GuestService {

    private final GuestRepository guestRepository;

    // Constructor Injection (BEST PRACTICE)
    public GuestServiceImpl(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    // 1️⃣ Create Guest (check duplicate email)
    @Override
    public Guest createGuest(Guest guest) {

        if (guestRepository.existsByEmail(guest.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        return guestRepository.save(guest);
    }

    // 2️⃣ Update Guest
    @Override
    public Guest updateGuest(Long id, Guest guest) {

        Guest existingGuest = getGuestById(id);

        existingGuest.setFullName(guest.getFullName());
        existingGuest.setEmail(guest.getEmail());
        existingGuest.setPhoneNumber(guest.getPhoneNumber());
        existingGuest.setVerified(guest.getVerified());
        existingGuest.setRole(guest.getRole());

        return guestRepository.save(existingGuest);
    }

    // 3️⃣ Get Guest by ID
    @Override
    public Guest getGuestById(Long id) {

        return guestRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Guest not found with id: " + id));
    }

    // 4️⃣ Get all Guests
    @Override
    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }

    // 5️⃣ Deactivate Guest
    @Override
    public void deactivateGuest(Long id) {

        Guest guest = getGuestById(id);
        guest.setActive(false);
        guestRepository.save(guest);
    }
}
