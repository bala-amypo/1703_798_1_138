package com.example.demo.service;

import com.example.demo.model.Guest;
import java.util.List;
import java.util.Optional;

public interface GuestService {

    Guest createGuest(Guest guest); 

    Guest loginGuest(String email, String password);

    Guest updateGuest(Long id, Guest guest); 
    Guest getGuestById(Long id);
    List<Guest> getAllGuests();
    void deactivateGuest(Long id); 
    void deleteGuest(Long id);
}