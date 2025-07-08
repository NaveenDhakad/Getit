package com.GetApp.Get.Entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double totalAmount;
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "bike_id")
    private Bike bike;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    private Payment payment;



    public Booking(Long bookingId, LocalDateTime startTime, LocalDateTime endTime, Double totalAmount, String status, User user, Bike bike, Payment payment) {
        this.bookingId = bookingId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalAmount = totalAmount;
        this.status = status;
        this.user = user;
        this.bike = bike;
        this.payment = payment;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
}
