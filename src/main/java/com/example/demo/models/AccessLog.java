package com.example.demo.models;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Entity
public class AccessLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many logs → One DigitalKey
    @ManyToOne
    @JoinColumn(name = "digital_key_id", nullable = false)
    private DigitalKey digitalKey;

    // Many logs → One Guest
    @ManyToOne
    @JoinColumn(name = "guest_id", nullable = false)
    private Guest guest;

    private Timestamp accessTime;

    // SUCCESS / DENIED
    private String result;

    private String reason;

    // Validate accessTime
    @PrePersist
    @PreUpdate
    protected void validateAccessTime() {

        if (accessTime == null) {
            accessTime = new Timestamp(System.currentTimeMillis());
        }

        Timestamp now = new Timestamp(System.currentTimeMillis());

        if (accessTime.after(now)) {
            throw new IllegalArgumentException(
                    "accessTime cannot be in the future"
            );
        }
    }

    // Required by JPA
    public AccessLog() {}

    // getters & setters

    public Long getId() { return id; }

    public DigitalKey getDigitalKey() { return digitalKey; }
    public void setDigitalKey(DigitalKey digitalKey) {
        this.digitalKey = digitalKey;
    }

    public Guest getGuest() { return guest; }
    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public Timestamp getAccessTime() { return accessTime; }
    public void setAccessTime(Timestamp accessTime) {
        this.accessTime = accessTime;
    }

    public String getResult() { return result; }
    public void setResult(String result) {
        this.result = result;
    }

    public String getReason() { return reason; }
    public void setReason(String reason) {
        this.reason = reason;
    }
}
