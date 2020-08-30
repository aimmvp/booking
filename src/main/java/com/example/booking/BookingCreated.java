package com.example.booking;

public class BookingCreated {
    String eventType;   // event identification

    Long bookingId;        // 예약ID
    Long roomId;    // 회의실ID
    String bookingUserId;   // 예약사용자ID (5자리)
    String useStartDtm;     // 사용시작일시 202008300900
    String useEndDtm;       // 사용종료일시 202008301000

    public BookingCreated() {
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

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getBookingUserId() {
        return bookingUserId;
    }

    public void setBookingUserId(String bookingUserId) {
        this.bookingUserId = bookingUserId;
    }

    public String getUseStartDtm() {
        return useStartDtm;
    }

    public void setUseStartDtm(String useStartDtm) {
        this.useStartDtm = useStartDtm;
    }

    public String getUseEndDtm() {
        return useEndDtm;
    }

    public void setUseEndDtm(String useEndDtm) {
        this.useEndDtm = useEndDtm;
    }
}
