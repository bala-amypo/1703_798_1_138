package com.example.demo.service;

import com.example.demo.models.Guest;
import java.util.List;

public interface GuestService {

    Guest createGuest(Guest guest);

    Guest updateGuest(Long id, Guest guest);

    // ✅ FIXED HERE
    Guest getGuestById(Long id);

    List<Guest> getAllGuests();

    void deactivateGuest(Long id);
}
