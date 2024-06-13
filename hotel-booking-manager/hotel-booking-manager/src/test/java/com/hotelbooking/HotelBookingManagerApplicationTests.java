package com.hotelbooking;

import com.hotelbooking.model.Booking;
import com.hotelbooking.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HotelBookingManagerApplicationTests {
    private BookingService bookingService;
    private Date today;

    @BeforeEach
    public void setUp() {
        bookingService = new BookingService();
        today = new Date();
    }

    @Test
    public void testStoreBooking() {
        assertTrue(bookingService.storeBooking("Alice", 1, today));
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
