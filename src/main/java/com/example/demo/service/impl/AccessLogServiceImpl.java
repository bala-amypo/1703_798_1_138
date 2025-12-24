package com.example.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.AccessLog;
import com.example.demo.model.DigitalKey;
import com.example.demo.model.Guest;
import com.example.demo.repository.AccessLogRepository;
import com.example.demo.repository.DigitalKeyRepository;
import com.example.demo.repository.GuestRepository;
import com.example.demo.service.AccessLogService;

@Service
public class AccessLogServiceImpl implements AccessLogService {

    private final AccessLogRepository accessLogRepository;
    private final DigitalKeyRepository digitalKeyRepository;
    private final GuestRepository guestRepository;

    public AccessLogServiceImpl(
            AccessLogRepository accessLogRepository,
            DigitalKeyRepository digitalKeyRepository,
            GuestRepository guestRepository) {
        this.accessLogRepository = accessLogRepository;
        this.digitalKeyRepository = digitalKeyRepository;
        this.guestRepository = guestRepository;
    }

    // 1️⃣ Create log (USED BY CONTROLLER)
    @Override
    public AccessLog createLog(AccessLog log) {

        Long keyId = log.getDigitalKey().getId();

        DigitalKey key = digitalKeyRepository.findById(keyId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "DigitalKey not found with id: " + keyId));

        if (!key.isActive()) {
            throw new IllegalStateException("Digital key is inactive");
        }

        log.setDigitalKey(key);

        return accessLogRepository.save(log);
    }

    // 2️⃣ Get logs for a digital key
    @Override
    public List<AccessLog> getLogsForKey(Long keyId) {

        DigitalKey key = digitalKeyRepository.findById(keyId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "DigitalKey not found with id: " + keyId));

        return accessLogRepository.findByDigitalKey(key);
    }

    // 3️⃣ Get logs for a guest
    @Override
    public List<AccessLog> getLogsForGuest(Long guestId) {

        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Guest not found with id: " + guestId));

        return accessLogRepository.findByGuest(guest);
    }
}
