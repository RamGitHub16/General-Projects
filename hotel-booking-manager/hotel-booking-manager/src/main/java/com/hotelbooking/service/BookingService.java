package com.hotelbooking.service;

import com.hotelbooking.model.Booking;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BookingService {
    private final int numberOfRooms = 10; // Set your number of rooms here
    private final Map<Integer, Map<Date, Booking>> bookings = new ConcurrentHashMap<>();
    private final Map<String, List<Booking>> guestBookings = new ConcurrentHashMap<>();

    public BookingService() {
        for (int i = 1; i <= numberOfRooms; i++) {
            bookings.put(i, new ConcurrentHashMap<>());
        }
    }

    public synchronized boolean storeBooking(String guestName, int roomNumber, Date date) {
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

    public List<Integer> findAvailableRooms(Date date) {
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
