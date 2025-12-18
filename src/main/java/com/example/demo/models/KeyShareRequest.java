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
public class KeyShareRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many requests → One DigitalKey
    @ManyToOne
    @JoinColumn(name = "digital_key_id", nullable = false)
    private DigitalKey digitalKey;

    // Many requests → One Guest (sender)
    @ManyToOne
    @JoinColumn(name = "shared_by_id", nullable = false)
    private Guest sharedBy;

    // Many requests → One Guest (receiver)
    @ManyToOne
    @JoinColumn(name = "shared_with_id", nullable = false)
    private Guest sharedWith;

    private Timestamp shareStart;

    private Timestamp shareEnd;

    // PENDING / APPROVED / REJECTED
    private String status;

    private Timestamp createdAt;

    // Auto-generate createdAt
    @PrePersist
    protected void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
        if (this.status == null) {
            this.status = "PENDING";
        }
    }

    // Rule validations
    @PrePersist
    @PreUpdate
    protected void validate() {

        // shareEnd > shareStart
        if (shareStart != null && shareEnd != null &&
            !shareEnd.after(shareStart)) {
            throw new IllegalArgumentException(
                "shareEnd must be after shareStart"
            );
        }

        // sharedBy ≠ sharedWith
        if (sharedBy != null && sharedWith != null &&
            sharedBy.getId().equals(sharedWith.getId())) {
            throw new IllegalArgumentException(
                "sharedBy and sharedWith cannot be the same guest"
            );
        }
    }

    // Required by JPA
    public KeyShareRequest() {}

    // getters & setters
    public Long getId() { return id; }

    public DigitalKey getDigitalKey() { return digitalKey; }
    public void setDigitalKey(DigitalKey digitalKey) { this.digitalKey = digitalKey; }

    public Guest getSharedBy() { return sharedBy; }
    public void setSharedBy(Guest sharedBy) { this.sharedBy = sharedBy; }

    public Guest getSharedWith() { return sharedWith; }
    public void setSharedWith(Guest sharedWith) { this.sharedWith = sharedWith; }

    public Timestamp getShareStart() { return shareStart; }
    public void setShareStart(Timestamp shareStart) { this.shareStart = shareStart; }

    public Timestamp getShareEnd() { return shareEnd; }
    public void setShareEnd(Timestamp shareEnd) { this.shareEnd = shareEnd; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getCreatedAt() { return createdAt; }
}
