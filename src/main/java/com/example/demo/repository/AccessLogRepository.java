package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.AccessLog;
import com.example.demo.model.DigitalKey;
import com.example.demo.model.Guest;

public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {

    List<AccessLog> findByDigitalKey(DigitalKey digitalKey);

    List<AccessLog> findByGuest(Guest guest);
}
