package com.example.demo.models;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "room_booking")
public class RoomBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "guest_id")
    private Guest guest;

    @Column(name = "room_number")
    private String roomNumber;

    @Column(name = "check_in_date")
    private LocalDate checkInDate;

    @Column(name = "check_out_date")
    private LocalDate checkOutDate;

    @Column(nullable = false)
    private boolean active;

    // 🔴 THIS IS WHAT WAS MISSING
    @ManyToMany
    @JoinTable(
        name = "room_booking_roommates",
        joinColumns = @JoinColumn(name = "room_booking_id"),
        inverseJoinColumns = @JoinColumn(name = "roommates_id")
    )
    private List<Guest> roommates;

    // ===== getters & setters =====

    public Long getId() { return id; }

    public Guest getGuest() { return guest; }
    public void setGuest(Guest guest) { this.guest = guest; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public LocalDate getCheckInDate() { return checkInDate; }
    public void setCheckInDate(LocalDate checkInDate) { this.checkInDate = checkInDate; }

    public LocalDate getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(LocalDate checkOutDate) { this.checkOutDate = checkOutDate; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    // ✅ THIS FIXES YOUR ERROR
    public List<Guest> getRoommates() { return roommates; }
    public void setRoommates(List<Guest> roommates) { this.roommates = roommates; }
}
