package com.hotelbooking.service;

import com.hotelbooking.model.Booking;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BookingService {
    private final int numberOfRooms;
    private final Map<Integer, Map<LocalDate, Booking>> bookings;
    private final Map<String, List<Booking>> guestBookings;

    public BookingService() {
        this.numberOfRooms = 10; // Can be made configurable
        this.bookings = new ConcurrentHashMap<>();
        this.guestBookings = new ConcurrentHashMap<>();

        for (int i = 1; i <= numberOfRooms; i++) {
            bookings.put(i, new ConcurrentHashMap<>());
        }
    }

    public synchronized boolean storeBooking(String guestName, int roomNumber, LocalDate date) {
        if (roomNumber < 1 || roomNumber > numberOfRooms) {
            throw new IllegalArgumentException("Invalid room number.");
        }

        if (bookings.get(roomNumber).containsKey(date)) {
            return false; // Room is already booked on this date.
        }

        Booking booking = new Booking(guestName, roomNumber, date);
        bookings.get(roomNumber).put(date, booking);
        guestBookings.computeIfAbsent(guestName, k -> new ArrayList<>()).add(booking);
        return true;
    }

    public List<Integer> findAvailableRooms(LocalDate date) {
        List<Integer> availableRooms = new ArrayList<>();
        for (int roomNumber : bookings.keySet()) {
            if (!bookings.get(roomNumber).containsKey(date)) {
                availableRooms.add(roomNumber);
            }
        }
        return availableRooms;
    }

    public List<Booking> findBookingsByGuest(String guestName) {
        return guestBookings.getOrDefault(guestName, Collections.emptyList());
    }
}
