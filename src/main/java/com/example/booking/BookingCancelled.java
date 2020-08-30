package com.example.booking;

public class BookingCancelled {
    String eventType;   // event identification

    Long bookingId;        // 예약ID

    public BookingCancelled() {
        this.eventType = this.getClass().getSimpleName();
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
}
