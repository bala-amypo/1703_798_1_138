package com.example.demo.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "access_logs")
public class AccessLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private DigitalKey digitalKey;

    @ManyToOne
    private Guest guest;

    private Instant accessTime;

    private String result;

    private String reason;

    public AccessLog() {
    }

    public AccessLog(DigitalKey digitalKey, Guest guest,
                     Instant accessTime, String result,
                     String reason) {
        this.digitalKey = digitalKey;
        this.guest = guest;
        this.accessTime = accessTime;
        this.result = result;
        this.reason = reason;
    }

    // getters and setters

    public Long getId() { return id; }

    public DigitalKey getDigitalKey() { return digitalKey; }

    public void setDigitalKey(DigitalKey digitalKey) { this.digitalKey = digitalKey; }

    public Guest getGuest() { return guest; }

    public void setGuest(Guest guest) { this.guest = guest; }

    public Instant getAccessTime() { return accessTime; }

    public void setAccessTime(Instant accessTime) { this.accessTime = accessTime; }

    public String getResult() { return result; }

    public void setResult(String result) { this.result = result; }

    public String getReason() { return reason; }

    public void setReason(String reason) { this.reason = reason; }
}
