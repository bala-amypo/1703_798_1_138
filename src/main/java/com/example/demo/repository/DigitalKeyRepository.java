package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.DigitalKey;
import com.example.demo.models.Guest;
import com.example.demo.models.RoomBooking;

public interface DigitalKeyRepository extends JpaRepository<DigitalKey, Long> {

    boolean existsByKeyValue(String keyValue);

    Optional<DigitalKey> findByBookingAndActiveTrue(RoomBooking booking);

    List<DigitalKey> findByBooking_Guest(Guest guest);
}
