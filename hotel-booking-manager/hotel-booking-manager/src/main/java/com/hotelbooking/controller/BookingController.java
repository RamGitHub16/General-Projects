package com.hotelbooking.controller;

import com.hotelbooking.model.Booking;
import com.hotelbooking.model.BookingRequest;
import com.hotelbooking.service.BookingService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<String> storeBooking(@Valid @RequestBody BookingRequest bookingRequest) {
        try {
            boolean success = bookingService.storeBooking(bookingRequest.getGuestName(), bookingRequest.getRoomNumber(), bookingRequest.getDate());
            return success ? new ResponseEntity<>("Booking successful", HttpStatus.CREATED)
                           : new ResponseEntity<>("Room already booked", HttpStatus.CONFLICT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/available")
    public ResponseEntity<List<Integer>> findAvailableRooms(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Integer> availableRooms = bookingService.findAvailableRooms(date);
        return new ResponseEntity<>(availableRooms, HttpStatus.OK);
    }

    @GetMapping("/guest")
    public ResponseEntity<List<Booking>> findBookingsByGuest(@RequestParam String guestName) {
        List<Booking> bookings = bookingService.findBookingsByGuest(guestName);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }
}


