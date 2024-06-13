package com.hotelbooking.model;

import java.time.LocalDate;

public class Booking {
    private String guestName;
    private int roomNumber;
    private LocalDate date;

    public Booking() {}

    public Booking(String guestName, int roomNumber, LocalDate date) {
        this.guestName = guestName;
        this.roomNumber = roomNumber;
        this.date = date;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
    public String toString() {
        return "Booking{" +
                "guestName='" + guestName + '\'' +
                ", roomNumber=" + roomNumber +
                ", date=" + date +
                '}';
    }
}
