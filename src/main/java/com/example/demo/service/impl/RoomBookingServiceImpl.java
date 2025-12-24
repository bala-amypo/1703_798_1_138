package com.example.demo.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Guest;
import com.example.demo.model.RoomBooking;
import com.example.demo.repository.GuestRepository;
import com.example.demo.repository.RoomBookingRepository;
import com.example.demo.service.RoomBookingService;

@Service
public class RoomBookingServiceImpl implements RoomBookingService {

    private final RoomBookingRepository bookingRepository;
    private final GuestRepository guestRepository;

    public RoomBookingServiceImpl(RoomBookingRepository bookingRepository,
                                  GuestRepository guestRepository) {
        this.bookingRepository = bookingRepository;
        this.guestRepository = guestRepository;
    }

    // 1️⃣ Create Booking
    @Override
    public RoomBooking createBooking(RoomBooking booking, Long guestId) {

        validateDates(
                booking.getCheckInDate(),
                booking.getCheckOutDate()
        );

        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Guest not found with id: " + guestId
                        ));

        booking.setGuest(guest);   // 🔥 fixes 500 error
        booking.setActive(true);

        return bookingRepository.save(booking);
    }

    // 2️⃣ Update Booking (DO NOT change guest)
    @Override
    public RoomBooking updateBooking(Long id, RoomBooking booking) {

        RoomBooking existing = getBookingById(id);

        validateDates(
                booking.getCheckInDate(),
                booking.getCheckOutDate()
        );

        existing.setRoomNumber(booking.getRoomNumber());
        existing.setCheckInDate(booking.getCheckInDate());
        existing.setCheckOutDate(booking.getCheckOutDate());
        existing.setRoommates(booking.getRoommates());

        return bookingRepository.save(existing);
    }

    // 3️⃣ Get Booking by ID
    @Override
    public RoomBooking getBookingById(Long id) {

        return bookingRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "RoomBooking not found with id: " + id
                        ));
    }

    // 4️⃣ Get bookings for a guest
    @Override
    public List<RoomBooking> getBookingsForGuest(Long guestId) {

        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Guest not found with id: " + guestId
                        ));

        return bookingRepository.findByGuest(guest);
    }

    // 5️⃣ Deactivate booking
    @Override
    public void deactivateBooking(Long id) {

        RoomBooking booking = getBookingById(id);
        booking.setActive(false);
        bookingRepository.save(booking);
    }

    // 🔒 Date validation
    private void validateDates(LocalDate checkIn, LocalDate checkOut) {

        if (checkIn == null || checkOut == null) {
            throw new IllegalArgumentException(
                    "Check-in and Check-out dates must not be null"
            );
        }

        if (!checkIn.isBefore(checkOut)) {
            throw new IllegalArgumentException(
                    "checkInDate must be before checkOutDate"
            );
        }
    }
}
