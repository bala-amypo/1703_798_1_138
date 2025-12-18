package com.example.demo.models;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
    @JoinTable(
        name = "room_booking_roommates",
        joinColumns = @JoinColumn(name = "room_booking_id"),
        inverseJoinColumns = @JoinColumn(name = "guest_id")
    )
    private Set<Guest> roommates;

    // ✅ Default active = true
    @PrePersist
    protected void onCreate() {
        if (this.active == null) {
            this.active = true;
        }

        // ✅ Date rule enforcement
        if (checkInDate != null && checkOutDate != null &&
            !checkInDate.isBefore(checkOutDate)) {
            throw new IllegalArgumentException(
                "checkInDate must be before checkOutDate"
            );
        }
    }

    // ✅ REQUIRED by JPA
    public RoomBooking() {
    }

    public RoomBooking(Long id, Guest guest, String roomNumber,
                       LocalDate checkInDate, LocalDate checkOutDate,
                       Boolean active, Set<Guest> roommates) {
        this.id = id;
        this.guest = guest;
        this.roomNumber = roomNumber;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.active = active;
        this.roommates = roommates;
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
