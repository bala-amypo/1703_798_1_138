package com.example.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.DigitalKey;
import com.example.demo.model.Guest;
import com.example.demo.model.KeyShareRequest;
import com.example.demo.repository.DigitalKeyRepository;
import com.example.demo.repository.GuestRepository;
import com.example.demo.repository.KeyShareRequestRepository;
import com.example.demo.service.KeyShareRequestService;

@Service
public class KeyShareRequestServiceImpl implements KeyShareRequestService {

    private final KeyShareRequestRepository requestRepository;
    private final GuestRepository guestRepository;
    private final DigitalKeyRepository digitalKeyRepository;

    // Constructor Injection
    public KeyShareRequestServiceImpl(KeyShareRequestRepository requestRepository,
                                      GuestRepository guestRepository,
                                      DigitalKeyRepository digitalKeyRepository) {
        this.requestRepository = requestRepository;
        this.guestRepository = guestRepository;
        this.digitalKeyRepository = digitalKeyRepository;
    }

    // 1️⃣ Create Share Request (validate users & dates handled in entity)
    @Override
    public KeyShareRequest createShareRequest(KeyShareRequest request) {

        // Ensure DigitalKey exists
        DigitalKey key = digitalKeyRepository.findById(
                        request.getDigitalKey().getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("DigitalKey not found"));

        // Ensure sharedBy exists
        Guest sharedBy = guestRepository.findById(
                        request.getSharedBy().getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("SharedBy guest not found"));

        // Ensure sharedWith exists
        Guest sharedWith = guestRepository.findById(
                        request.getSharedWith().getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("SharedWith guest not found"));

        request.setDigitalKey(key);
        request.setSharedBy(sharedBy);
        request.setSharedWith(sharedWith);

        // Date & user validation is already handled by @PrePersist
        return requestRepository.save(request);
    }

    // 2️⃣ Update status (PENDING / APPROVED / REJECTED)
    @Override
    public KeyShareRequest updateStatus(Long requestId, String status) {

        KeyShareRequest request = getShareRequestById(requestId);

        if (!status.equals("PENDING") &&
                !status.equals("APPROVED") &&
                !status.equals("REJECTED")) {
            throw new IllegalArgumentException("Invalid status value");
        }

        request.setStatus(status);
        return requestRepository.save(request);
    }

    // 3️⃣ Get request by ID
    @Override
    public KeyShareRequest getShareRequestById(Long id) {

        return requestRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "KeyShareRequest not found with id: " + id
                        ));
    }

    // 4️⃣ Requests shared by a Guest
    @Override
    public List<KeyShareRequest> getRequestsSharedBy(Long guestId) {

        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Guest not found with id: " + guestId
                        ));

        return requestRepository.findBySharedBy(guest);
    }

    // 5️⃣ Requests shared with a Guest
    @Override
    public List<KeyShareRequest> getRequestsSharedWith(Long guestId) {

        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Guest not found with id: " + guestId
                        ));

        return requestRepository.findBySharedWith(guest);
    }
}
