package com.example.demo.controller;

import com.example.demo.model.KeyShareRequest;
import com.example.demo.service.KeyShareRequestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/key-share-requests")
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
        return keyShareRequestService.getRequestById(id);
    }

    @GetMapping("/shared-by/{guestId}")
    public List<KeyShareRequest> getSharedBy(@PathVariable Long guestId) {
        return keyShareRequestService.getRequestsSharedBy(guestId);
    }

    @GetMapping("/shared-with/{guestId}")
    public List<KeyShareRequest> getSharedWith(@PathVariable Long guestId) {
        return keyShareRequestService.getRequestsSharedWith(guestId);
    }
}
