package com.example.demo.controller;

import com.example.demo.model.KeyShareRequest;
import com.example.demo.service.KeyShareRequestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/key-share")
public class KeyShareRequestController {

    private final KeyShareRequestService keyShareRequestService;

    public KeyShareRequestController(KeyShareRequestService keyShareRequestService) {
        this.keyShareRequestService = keyShareRequestService;
    }

    @PostMapping
    public KeyShareRequest create(@RequestBody KeyShareRequest request) {
        return keyShareRequestService.createShareRequest(request);
    }

    @GetMapping("/{id}")
    public KeyShareRequest getById(@PathVariable Long id) {
        return keyShareRequestService.getShareRequestById(id);
    }

    @PutMapping("/{id}/status")
    public KeyShareRequest updateStatus(@PathVariable Long id,
                                        @RequestParam String status) {
        return keyShareRequestService.updateStatus(id, status);
    }

    @GetMapping("/shared-by/{guestId}")
    public List<KeyShareRequest> sharedBy(@PathVariable Long guestId) {
        return keyShareRequestService.getRequestsSharedBy(guestId);
    }

    @GetMapping("/shared-with/{guestId}")
    public List<KeyShareRequest> sharedWith(@PathVariable Long guestId) {
        return keyShareRequestService.getRequestsSharedWith(guestId);
    }
}
