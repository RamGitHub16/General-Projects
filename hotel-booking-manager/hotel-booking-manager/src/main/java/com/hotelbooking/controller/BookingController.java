package com.hotelbooking.controller;

import com.hotelbooking.model.Booking;
import com.hotelbooking.model.BookingRequest;
import com.hotelbooking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    
    @PostMapping
    public String storeBooking(@RequestBody BookingRequest bookingRequest) {
        boolean success = bookingService.storeBooking(bookingRequest.getGuestName(), bookingRequest.getRoomNumber(), bookingRequest.getDate());
        return success ? "Booking successful" : "Room already booked";
    }

    @GetMapping("/available")
    public List<Integer> findAvailableRooms(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        return bookingService.findAvailableRooms(date);
    }

    @GetMapping("/guest")
    public List<Booking> findBookingsByGuest(@RequestParam String guestName) {
        return bookingService.findBookingsByGuest(guestName);
    }
}

