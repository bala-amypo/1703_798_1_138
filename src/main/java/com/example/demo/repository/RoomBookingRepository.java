package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.models.RoomBooking;

public interface RoomBookingRepository extends JpaRepository<RoomBooking, Long> {
}
