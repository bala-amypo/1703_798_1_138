package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.models.Guest;

public interface GuestRepository extends JpaRepository<Guest, Long> {

    boolean existsByEmail(String email);
}
