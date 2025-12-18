package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Guest;
import com.example.demo.models.KeyShareRequest;

public interface KeyShareRequestRepository
        extends JpaRepository<KeyShareRequest, Long> {

    List<KeyShareRequest> findBySharedBy(Guest sharedBy);

    List<KeyShareRequest> findBySharedWith(Guest sharedWith);
}
