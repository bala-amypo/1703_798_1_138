package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.KeyShareRequest;

public interface KeyShareRequestRepository
        extends JpaRepository<KeyShareRequest, Long> {

    List<KeyShareRequest> findBySharedById(Long guestId);

    List<KeyShareRequest> findBySharedWithId(Long guestId);
}
