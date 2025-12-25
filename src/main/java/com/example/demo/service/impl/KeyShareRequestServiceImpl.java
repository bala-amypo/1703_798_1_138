package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.KeyShareRequestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class KeyShareRequestServiceImpl implements KeyShareRequestService {

    private final KeyShareRequestRepository repository;
    private final DigitalKeyRepository keyRepo;
    private final GuestRepository guestRepo;

    public KeyShareRequestServiceImpl(KeyShareRequestRepository repository,
                                      DigitalKeyRepository keyRepo,
                                      GuestRepository guestRepo) {
        this.repository = repository;
        this.keyRepo = keyRepo;
        this.guestRepo = guestRepo;
    }

    @Override
    @Transactional
    public KeyShareRequest createShareRequest(KeyShareRequest request) {

        if (request.getShareEnd().isBefore(request.getShareStart())) {
            throw new IllegalArgumentException("Share end must be after share start");
        }

        if (request.getSharedBy().getId()
                .equals(request.getSharedWith().getId())) {
            throw new IllegalArgumentException("sharedBy and sharedWith cannot be same");
        }

        DigitalKey key = keyRepo.findById(request.getDigitalKey().getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Key not found"));

        Guest by = guestRepo.findById(request.getSharedBy().getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Sender not found"));

        Guest with = guestRepo.findById(request.getSharedWith().getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Receiver not found"));

        request.setDigitalKey(key);
        request.setSharedBy(by);
        request.setSharedWith(with);

        return repository.save(request);
    }

    @Override
    public List<KeyShareRequest> getRequestsSharedBy(Long guestId) {
        return repository.findBySharedById(guestId);
    }

    @Override
    public List<KeyShareRequest> getRequestsSharedWith(Long guestId) {
        return repository.findBySharedWithId(guestId);
    }

    @Override
    public KeyShareRequest getRequestById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Share request not found: " + id));
    }
}
