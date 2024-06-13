package com.hotelbooking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hotelbooking.model.Booking;
import com.hotelbooking.service.BookingService;

public class HotelBookingManagerApplicationTests {

    private BookingService bookingService;
    private LocalDate today;

    @BeforeEach
    public void setUp() {
        bookingService = new BookingService();
        today = LocalDate.now();
    }

    @Test
    public void testStoreBooking_Success() {
        assertTrue(bookingService.storeBooking("Alice", 1, today));
    }

    @Test
    public void testStoreBooking_RoomAlreadyBooked() {
        bookingService.storeBooking("Alice", 1, today);
        assertFalse(bookingService.storeBooking("Bob", 1, today));
    }

    @Test
    public void testFindAvailableRooms() {
        bookingService.storeBooking("Alice", 1, today);
        bookingService.storeBooking("Bob", 2, today);

        List<Integer> availableRooms = bookingService.findAvailableRooms(today);
        assertEquals(8, availableRooms.size());
        assertTrue(availableRooms.contains(3));
        assertTrue(availableRooms.contains(4));
        assertTrue(availableRooms.contains(5));
    }

    @Test
    public void testFindBookingsByGuest() {
        bookingService.storeBooking("Alice", 1, today);
        bookingService.storeBooking("Alice", 2, today);

        List<Booking> bookings = bookingService.findBookingsByGuest("Alice");
        assertEquals(2, bookings.size());

        assertEquals(1, bookings.get(0).getRoomNumber());
        assertEquals(2, bookings.get(1).getRoomNumber());
    }
}
