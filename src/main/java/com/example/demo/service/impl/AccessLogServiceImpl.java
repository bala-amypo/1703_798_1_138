package com.example.demo.service.impl;

import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.models.AccessLog;
import com.example.demo.models.DigitalKey;
import com.example.demo.repository.AccessLogRepository;
import com.example.demo.repository.DigitalKeyRepository;

@Service
public class AccessLogServiceImpl {

    private final AccessLogRepository accessLogRepository;
    private final DigitalKeyRepository digitalKeyRepository;

    public AccessLogServiceImpl(
            AccessLogRepository accessLogRepository,
            DigitalKeyRepository digitalKeyRepository) {
        this.accessLogRepository = accessLogRepository;
        this.digitalKeyRepository = digitalKeyRepository;
    }

    public AccessLog logAccess(Long digitalKeyId, String result, String reason) {

        DigitalKey key = digitalKeyRepository.findById(digitalKeyId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "DigitalKey not found with id: " + digitalKeyId));

        // ✅ CORRECT METHOD
        if (!key.isActive()) {
            throw new IllegalStateException("Digital key is inactive");
        }

        AccessLog log = new AccessLog();
        log.setDigitalKey(key);
        log.setResult(result);
        log.setReason(reason);

        return accessLogRepository.save(log);
    }
}
