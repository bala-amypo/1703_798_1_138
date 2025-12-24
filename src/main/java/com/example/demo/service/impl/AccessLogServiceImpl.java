package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.AccessLog;
import com.example.demo.model.DigitalKey;
import com.example.demo.model.Guest;
import com.example.demo.model.KeyShareRequest;
import com.example.demo.repository.AccessLogRepository;
import com.example.demo.repository.DigitalKeyRepository;
import com.example.demo.repository.GuestRepository;
import com.example.demo.repository.KeyShareRequestRepository;
import com.example.demo.service.AccessLogService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class AccessLogServiceImpl implements AccessLogService {

    private final AccessLogRepository accessLogRepository;
    private final DigitalKeyRepository digitalKeyRepository;
    private final GuestRepository guestRepository;
    private final KeyShareRequestRepository keyShareRequestRepository;

    public AccessLogServiceImpl(
            AccessLogRepository accessLogRepository,
            DigitalKeyRepository digitalKeyRepository,
            GuestRepository guestRepository,
            KeyShareRequestRepository keyShareRequestRepository) {

        this.accessLogRepository = accessLogRepository;
        this.digitalKeyRepository = digitalKeyRepository;
        this.guestRepository = guestRepository;
        this.keyShareRequestRepository = keyShareRequestRepository;
    }

    @Override
    public AccessLog createLog(AccessLog log) {

        if (log.getAccessTime().isAfter(Instant.now())) {
            throw new IllegalArgumentException("Access time cannot be in the future");
        }

        DigitalKey key = digitalKeyRepository.findById(log.getDigitalKey().getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Key not found"));

        Guest guest = guestRepository.findById(log.getGuest().getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Guest not found"));

        boolean success = false;

        if (key.getActive() &&
            key.getBooking().getGuest().getId().equals(guest.getId())) {
            success = true;
        }

        if (!success) {
            List<KeyShareRequest> shares =
                    keyShareRequestRepository.findBySharedWithId(guest.getId());

            for (KeyShareRequest req : shares) {
                if (req.getDigitalKey().getId().equals(key.getId())) {
                    success = true;
                    break;
                }
            }
        }

        log.setResult(success ? "SUCCESS" : "DENIED");
        log.setReason(success ? "Access granted" : "Access denied");

        return accessLogRepository.save(log);
    }

    @Override
    public List<AccessLog> getLogsForKey(Long keyId) {
        return accessLogRepository.findByDigitalKeyId(keyId);
    }

    @Override
    public List<AccessLog> getLogsForGuest(Long guestId) {
        return accessLogRepository.findByGuestId(guestId);
    }
}
