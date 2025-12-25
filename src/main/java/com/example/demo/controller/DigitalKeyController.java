package com.example.demo.controller;

import com.example.demo.model.DigitalKey;
import com.example.demo.service.DigitalKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/digital-keys")
public class DigitalKeyController {

    @Autowired
    private DigitalKeyService digitalKeyService;

 
    @PostMapping("/generate/{bookingId}")
    public ResponseEntity<DigitalKey> generateKey(@PathVariable Long bookingId) {
        DigitalKey newKey = digitalKeyService.generateKey(bookingId); //
        return ResponseEntity.ok(newKey);
    }

  
    @GetMapping("/{id}")
    public ResponseEntity<DigitalKey> getKey(@PathVariable Long id) {
        DigitalKey key = digitalKeyService.getKeyById(id); //
        return ResponseEntity.ok(key);
    }

   
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<DigitalKey> getActiveKey(@PathVariable Long bookingId) {
        DigitalKey activeKey = digitalKeyService.getActiveKeyForBooking(bookingId); //
        return ResponseEntity.ok(activeKey);
    }

  
    @GetMapping("/guest/{guestId}")
    public ResponseEntity<List<DigitalKey>> getKeysForGuest(@PathVariable Long guestId) {
        List<DigitalKey> keys = digitalKeyService.getKeysForGuest(guestId); //
        return ResponseEntity.ok(keys);
    }
}