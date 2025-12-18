package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.models.DigitalKey;

public interface DigitalKeyRepository extends JpaRepository<DigitalKey, Long> {

    boolean existsByKeyValue(String keyValue);
}
