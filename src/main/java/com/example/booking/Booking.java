package com.example.booking;

import com.example.booking.config.kafka.KafkaListener;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;

import javax.persistence.*;

@Entity
public class Booking {
    @Id
    @GeneratedValue
    Long id;        // 예약ID(자동생성)
    Long roomId;    // 회의실ID
    String bookingUserId;   // 예약사용자ID (5자리)
    String useStartDtm;     // 사용시작일시 202008300900
    String useEndDtm;       // 사용종료일시 202008301000

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    // Event Triggers
    @PostPersist
    public void eventPublishForPostPersist() {

        // Create event instance
        BookingCreated bookingCreated = new BookingCreated();

        // Set Attributes
        bookingCreated.setBookingId(this.getId());
        bookingCreated.setRoomId(this.getRoomId());
        bookingCreated.setBookingUserId(this.bookingUserId);
        bookingCreated.setUseStartDtm(this.useStartDtm);
        bookingCreated.setUseEndDtm(this.useEndDtm);

        // Convert event to JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;

        try {
            json = objectMapper.writeValueAsString(bookingCreated);
        }catch(JsonProcessingException e) {
            throw new RuntimeException("JSON format exception", e);
        }
        System.out.println(json);

        // Send message to kafka
        KafkaListener processor = BookingApplication.applicationContext.getBean(KafkaListener.class);
        MessageChannel outputChannel = processor.outboundTopic();

        outputChannel.send(MessageBuilder
                .withPayload(json)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build());
    }
    @PostUpdate
    public void eventPublishForPostUpdate() {

        // Create event instance
        BookingChanged bookingChanged = new BookingChanged();

        // Set Attributes
        bookingChanged.setBookingId(this.getId());
        bookingChanged.setRoomId(this.getRoomId());
        bookingChanged.setBookingUserId(this.bookingUserId);
        bookingChanged.setUseStartDtm(this.useStartDtm);
        bookingChanged.setUseEndDtm(this.useEndDtm);

        // Convert event to JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;

        try {
            json = objectMapper.writeValueAsString(bookingChanged);
        }catch(JsonProcessingException e) {
            throw new RuntimeException("JSON format exception", e);
        }
        System.out.println(json);

        // Send message to kafka
//        Source processor = BookingApplication.applicationContext.getBean(Source.class);
//        MessageChannel outputChannel = processor.outboundTopic();
//
//        outputChannel.send(MessageBuilder
//                .withPayload(json)
//                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
//                .build());
    }

    @PreRemove
    public void eventPublishForPreRemove() {

        BookingCancelled bookingCancelled = new BookingCancelled();

        bookingCancelled.setBookingId(this.getId());
        // Convert event to JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;

        try {
            json = objectMapper.writeValueAsString(bookingCancelled);
        }catch(JsonProcessingException e) {
            throw new RuntimeException("JSON format exception", e);
        }
        System.out.println(json);

        // Send message to kafka
//        Source processor = BookingApplication.applicationContext.getBean(Source.class);
//        MessageChannel outputChannel = processor.outboundTopic();
//
//        outputChannel.send(MessageBuilder
//                .withPayload(json)
//                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
//                .build());
    }
}
