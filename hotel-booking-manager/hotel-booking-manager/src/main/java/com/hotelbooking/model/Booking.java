package com.hotelbooking.model;

import java.util.Date;

public class Booking {
    private String guestName;
    private int roomNumber;
    private Date date;

    public Booking(String guestName, int roomNumber, Date date) {
        this.guestName = guestName;
        this.roomNumber = roomNumber;
        this.date = date;
    }

    public String getGuestName() {
        return guestName;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public Date getDate() {
        return date;
    }
}
