package com.example.demo.models;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;

@Entity
public class RoomBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ Many bookings → One guest
    @ManyToOne
    @JoinColumn(name = "guest_id", nullable = false)
    private Guest guest;

    private String roomNumber;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private Boolean active;

    // ✅ Many roommates → Many guests
    @ManyToMany
    private Set<Guest> roommates;

    // ✅ Default active = true
    @PrePersist
    protected void onCreate() {
        if (this.active == null) {
            this.active = true;
        }
    }

    // ✅ REQUIRED by JPA
    public RoomBooking() {
    }

    // getters & setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Guest getGuest() { return guest; }
    public void setGuest(Guest guest) { this.guest = guest; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public LocalDate getCheckInDate() { return checkInDate; }
    public void setCheckInDate(LocalDate checkInDate) { this.checkInDate = checkInDate; }

    public LocalDate getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(LocalDate checkOutDate) { this.checkOutDate = checkOutDate; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public Set<Guest> getRoommates() { return roommates; }
    public void setRoommates(Set<Guest> roommates) { this.roommates = roommates; }
}
