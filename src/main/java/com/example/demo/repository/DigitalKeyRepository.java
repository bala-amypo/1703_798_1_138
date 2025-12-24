package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.DigitalKey;
import com.example.demo.model.Guest;
import com.example.demo.model.RoomBooking;

public interface DigitalKeyRepository extends JpaRepository<DigitalKey, Long> {

    Optional<DigitalKey> findByBooking(RoomBooking booking);

    Optional<DigitalKey> findByBookingAndActiveTrue(RoomBooking booking);

    List<DigitalKey> findByBooking_Guest(Guest guest);
}
