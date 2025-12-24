package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.DigitalKey;
import com.example.demo.model.Guest;
import com.example.demo.model.KeyShareRequest;
import com.example.demo.repository.DigitalKeyRepository;
import com.example.demo.repository.GuestRepository;
import com.example.demo.repository.KeyShareRequestRepository;
import com.example.demo.service.KeyShareRequestService;
import org.springframework.stereotype.Service;

@Service
public class KeyShareRequestServiceImpl implements KeyShareRequestService {

    private final KeyShareRequestRepository keyShareRequestRepository;
    private final DigitalKeyRepository digitalKeyRepository;
    private final GuestRepository guestRepository;

    public KeyShareRequestServiceImpl(
            KeyShareRequestRepository keyShareRequestRepository,
            DigitalKeyRepository digitalKeyRepository,
            GuestRepository guestRepository) {

        this.keyShareRequestRepository = keyShareRequestRepository;
        this.digitalKeyRepository = digitalKeyRepository;
        this.guestRepository = guestRepository;
    }

    @Override
    public KeyShareRequest createShareRequest(KeyShareRequest request) {

        if (!request.getShareEnd().isAfter(request.getShareStart())) {
            throw new IllegalArgumentException("Share end must be after start");
        }

        if (request.getSharedBy().getId().equals(request.getSharedWith().getId())) {
            throw new IllegalArgumentException("sharedBy and sharedWith cannot be same");
        }

        DigitalKey key = digitalKeyRepository.findById(request.getDigitalKey().getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Key not found"));

        if (!key.getActive()) {
            throw new IllegalStateException("Key is inactive");
        }

        Guest sharedBy = guestRepository.findById(request.getSharedBy().getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Guest not found"));

        Guest sharedWith = guestRepository.findById(request.getSharedWith().getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Guest not found"));

        request.setStatus("PENDING");
        return keyShareRequestRepository.save(request);
    }

    @Override
    public KeyShareRequest updateStatus(Long requestId, String status) {

        KeyShareRequest request = keyShareRequestRepository.findById(requestId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Share request not found: " + requestId));

        request.setStatus(status);
        return keyShareRequestRepository.save(request);
    }

    @Override
    public KeyShareRequest getShareRequestById(Long id) {

        return keyShareRequestRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Share request not found: " + id));
    }

    @Override
    public java.util.List<KeyShareRequest> getRequestsSharedBy(Long guestId) {
        return keyShareRequestRepository.findBySharedById(guestId);
    }

    @Override
    public java.util.List<KeyShareRequest> getRequestsSharedWith(Long guestId) {
        return keyShareRequestRepository.findBySharedWithId(guestId);
    }
}
