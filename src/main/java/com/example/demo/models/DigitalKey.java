package com.example.demo.models;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Entity
public class DigitalKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private RoomBooking booking;

    @Column(unique = true, nullable = false)
    private String keyValue;

    private Timestamp issuedAt;
    private Timestamp expiresAt;
    private Boolean active;

    @PrePersist
    @PreUpdate
    protected void beforeSave() {

        if (issuedAt == null) {
            issuedAt = new Timestamp(System.currentTimeMillis());
        }
        if (active == null) {
            active = true;
        }

        if (expiresAt != null && expiresAt.before(issuedAt)) {
            throw new IllegalArgumentException(
                    "expiresAt must be >= issuedAt"
            );
        }
    }

    public DigitalKey() {}

    // ✅ GETTERS & SETTERS (REQUIRED BY SERVICES)

    public Long getId() { return id; }

    public RoomBooking getBooking() { return booking; }
    public void setBooking(RoomBooking booking) { this.booking = booking; }

    public String getKeyValue() { return keyValue; }
    public void setKeyValue(String keyValue) { this.keyValue = keyValue; }

    public Timestamp getIssuedAt() { return issuedAt; }
    public void setIssuedAt(Timestamp issuedAt) { this.issuedAt = issuedAt; }

    public Timestamp getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Timestamp expiresAt) { this.expiresAt = expiresAt; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}
