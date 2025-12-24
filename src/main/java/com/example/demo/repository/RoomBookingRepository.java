package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Guest;
import com.example.demo.model.RoomBooking;

public interface RoomBookingRepository extends JpaRepository<RoomBooking, Long> {

    List<RoomBooking> findByGuest(Guest guest);
}
