package com.hotelbooking.service;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.hotelbooking.model.Booking;

public class BookingManager {
    private final int totalRooms;
    private final Map<Date, Set<Integer>> bookingsByDate;
    private final Map<String, List<Booking>> bookingsByGuest;

    public BookingManager(int totalRooms) {
        this.totalRooms = totalRooms;
        this.bookingsByDate = new ConcurrentHashMap<>();
        this.bookingsByGuest = new ConcurrentHashMap<>();
    }

    public synchronized boolean storeBooking(String guestName, int roomNumber, Date date) {
        validateRoomNumber(roomNumber);

        bookingsByDate.putIfAbsent(date, Collections.synchronizedSet(new HashSet<>()));

        Set<Integer> bookedRooms = bookingsByDate.get(date);
        if (bookedRooms.contains(roomNumber)) {
            return false;
        } else {
            bookedRooms.add(roomNumber);
            Booking booking = new Booking(guestName, roomNumber, date);
            bookingsByGuest.putIfAbsent(guestName, new CopyOnWriteArrayList<>());
            bookingsByGuest.get(guestName).add(booking);
            return true;
        }
    }

    public List<Integer> findAvailableRooms(Date date) {
        Set<Integer> bookedRooms = bookingsByDate.getOrDefault(date, Collections.emptySet());
        List<Integer> availableRooms = new ArrayList<>();
        for (int i = 1; i <= totalRooms; i++) {
            if (!bookedRooms.contains(i)) {
                availableRooms.add(i);
            }
        }
        return availableRooms;
    }

    public List<Booking> findBookingsByGuest(String guestName) {
        return new ArrayList<>(bookingsByGuest.getOrDefault(guestName, Collections.emptyList()));
    }

    private void validateRoomNumber(int roomNumber) {
        if (roomNumber < 1 || roomNumber > totalRooms) {
            throw new IllegalArgumentException("Invalid room number");
        }
    }
}
