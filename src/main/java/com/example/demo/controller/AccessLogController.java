package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.models.AccessLog;
import com.example.demo.service.AccessLogService;

import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/access-logs")
@Tag(name = "Access Log API", description = "Operations related to access logs")
public class AccessLogController {

    private final AccessLogService accessLogService;

    // Constructor Injection
    public AccessLogController(AccessLogService accessLogService) {
        this.accessLogService = accessLogService;
    }

    // 1️⃣ POST /api/access-logs → Create access log
    @PostMapping
    public ResponseEntity<AccessLog> createLog(
            @RequestBody AccessLog log) {

        AccessLog createdLog = accessLogService.createLog(log);
        return new ResponseEntity<>(createdLog, HttpStatus.CREATED);
    }

    // 2️⃣ GET /api/access-logs/key/{keyId} → Logs for a digital key
    @GetMapping("/key/{keyId}")
    public ResponseEntity<List<AccessLog>> getLogsForKey(
            @PathVariable Long keyId) {

        List<AccessLog> logs =
                accessLogService.getLogsForKey(keyId);

        return ResponseEntity.ok(logs);
    }

    // 3️⃣ GET /api/access-logs/guest/{guestId} → Logs for a guest
    @GetMapping("/guest/{guestId}")
    public ResponseEntity<List<AccessLog>> getLogsForGuest(
            @PathVariable Long guestId) {

        List<AccessLog> logs =
                accessLogService.getLogsForGuest(guestId);

        return ResponseEntity.ok(logs);
    }
}
