package com.example.demo.controller;

import com.example.demo.model.AccessLog;
import com.example.demo.service.AccessLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/access-logs")
public class AccessLogController {

    @Autowired
    private AccessLogService accessLogService;

    @PostMapping
    public ResponseEntity<AccessLog> recordEntry(@RequestBody AccessLog log) {
        return ResponseEntity.ok(accessLogService.createLog(log));
    }

    @GetMapping
    public ResponseEntity<List<AccessLog>> getAllLogs() {
        return ResponseEntity.ok(accessLogService.getAllLogs());
    }

    @GetMapping("/key/{keyId}")
    public ResponseEntity<List<AccessLog>> getLogsByKey(@PathVariable Long keyId) {
        return ResponseEntity.ok(accessLogService.getLogsForKey(keyId));
    }

    @GetMapping("/guest/{guestId}")
    public ResponseEntity<List<AccessLog>> getLogsByGuest(@PathVariable Long guestId) {
        return ResponseEntity.ok(accessLogService.getLogsForGuest(guestId));
    }
}