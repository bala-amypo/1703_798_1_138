package com.example.demo.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.models.AccessLog;
import com.example.demo.models.DigitalKey;
import com.example.demo.models.Guest;
import com.example.demo.repository.AccessLogRepository;
import com.example.demo.repository.DigitalKeyRepository;
import com.example.demo.repository.GuestRepository;
import com.example.demo.service.AccessLogService;

@Service
public class AccessLogServiceImpl implements AccessLogService {

    private final AccessLogRepository accessLogRepository;
    private final DigitalKeyRepository digitalKeyRepository;
    private final GuestRepository guestRepository;

    // Constructor Injection
    public AccessLogServiceImpl(AccessLogRepository accessLogRepository,
                                DigitalKeyRepository digitalKeyRepository,
                                GuestRepository guestRepository) {
        this.accessLogRepository = accessLogRepository;
        this.digitalKeyRepository = digitalKeyRepository;
        this.guestRepository = guestRepository;
    }

    // 1️⃣ Create Log (check key validity → SUCCESS / DENIED)
    @Override
    public AccessLog createLog(AccessLog log) {

        DigitalKey key = digitalKeyRepository.findById(
                        log.getDigitalKey().getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("DigitalKey not found"));

        Guest guest = guestRepository.findById(
                        log.getGuest().getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Guest not found"));

        log.setDigitalKey(key);
        log.setGuest(guest);

        // Default access time
        log.setAccessTime(new Timestamp(System.currentTimeMillis()));

        // 🔑 Key validity check
        if (Boolean.TRUE.equals(key.getActive())) {
            log.setResult("SUCCESS");
            log.setReason("Access granted");
        } else {
            log.setResult("DENIED");
            log.setReason("Digital key inactive or expired");
        }

        return accessLogRepository.save(log);
    }

    // 2️⃣ Get logs for a DigitalKey
    @Override
    public List<AccessLog> getLogsForKey(Long keyId) {

        DigitalKey key = digitalKeyRepository.findById(keyId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "DigitalKey not found with id: " + keyId
                        ));

        return accessLogRepository.findByDigitalKey(key);
    }

    // 3️⃣ Get logs for a Guest
    @Override
    public List<AccessLog> getLogsForGuest(Long guestId) {

        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Guest not found with id: " + guestId
                        ));

        return accessLogRepository.findByGuest(guest);
    }
}
