package com.example.demo.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "key_share_requests")
public class KeyShareRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private DigitalKey digitalKey;

    @ManyToOne
    private Guest sharedBy;

    @ManyToOne
    private Guest sharedWith;

    private Instant shareStart;

    private Instant shareEnd;

    private String status = "PENDING";

    private Instant createdAt;

    public KeyShareRequest() {
    }

    public KeyShareRequest(DigitalKey digitalKey, Guest sharedBy,
                           Guest sharedWith, Instant shareStart,
                           Instant shareEnd, String status) {
        this.digitalKey = digitalKey;
        this.sharedBy = sharedBy;
        this.sharedWith = sharedWith;
        this.shareStart = shareStart;
        this.shareEnd = shareEnd;
        this.status = status;
    }

    @PrePersist
    public void onCreate() {
        this.createdAt = Instant.now();
    }

    // getters and setters

    public Long getId() { return id; }

    public DigitalKey getDigitalKey() { return digitalKey; }

    public void setDigitalKey(DigitalKey digitalKey) { this.digitalKey = digitalKey; }

    public Guest getSharedBy() { return sharedBy; }

    public void setSharedBy(Guest sharedBy) { this.sharedBy = sharedBy; }

    public Guest getSharedWith() { return sharedWith; }

    public void setSharedWith(Guest sharedWith) { this.sharedWith = sharedWith; }

    public Instant getShareStart() { return shareStart; }

    public void setShareStart(Instant shareStart) { this.shareStart = shareStart; }

    public Instant getShareEnd() { return shareEnd; }

    public void setShareEnd(Instant shareEnd) { this.shareEnd = shareEnd; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public Instant getCreatedAt() { return createdAt; }
}
