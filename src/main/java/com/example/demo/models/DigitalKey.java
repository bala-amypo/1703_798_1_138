package com.example.demo.models;

import java.sql.Timestamp;

import jakarta.persistence.*;

@Entity
@Table(
    name = "digital_key",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "booking_id"),
        @UniqueConstraint(columnNames = "key_value")
    }
)
public class DigitalKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "booking_id", nullable = false)
    private RoomBooking booking;

    @Column(name = "key_value", nullable = false, unique = true)
    private String keyValue;

    @Column(nullable = false)
    private Timestamp issuedAt;

    @Column(nullable = false)
    private Timestamp expiresAt;

    @Column(nullable = false)
    private boolean active;

    @PrePersist
    protected void onCreate() {
        Timestamp now = new Timestamp(System.currentTimeMillis());

        if (issuedAt == null) {
            issuedAt = now;
        }

        if (expiresAt == null) {
            expiresAt = issuedAt;
        }

        active = true;
    }

    // Getters & setters

    public Long getId() { return id; }

    public RoomBooking getBooking() { return booking; }
    public void setBooking(RoomBooking booking) { this.booking = booking; }

    public String getKeyValue() { return keyValue; }
    public void setKeyValue(String keyValue) { this.keyValue = keyValue; }

    public Timestamp getIssuedAt() { return issuedAt; }
    public void setIssuedAt(Timestamp issuedAt) { this.issuedAt = issuedAt; }

    public Timestamp getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Timestamp expiresAt) { this.expiresAt = expiresAt; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
