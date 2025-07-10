package com.GetApp.Get.Entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Bike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bikeId;

    private String model;
    private String status;
    private Double ratePerHour;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private BikeCategory category;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToMany(mappedBy = "bike", cascade = CascadeType.ALL)
    private List<Booking> bookings;

    @Override
    public String toString() {
        return "Bike{" +
                "bikeId=" + bikeId +
                ", model='" + model + '\'' +
                ", status='" + status + '\'' +
                ", ratePerHour=" + ratePerHour +
                ", category=" + category +
                ", location=" + location +
                ", bookings=" + bookings +
                '}';
    }

    public Bike() {
        super();
    }

    public Bike(Long bikeId, String model, String status, Double ratePerHour, BikeCategory category, Location location, List<Booking> bookings) {
        this.bikeId = bikeId;
        this.model = model;
        this.status = status;
        this.ratePerHour = ratePerHour;
        this.category = category;
        this.location = location;
        this.bookings = bookings;
    }

    public Long getBikeId() {
        return bikeId;
    }

    public void setBikeId(Long bikeId) {
        this.bikeId = bikeId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getRatePerHour() {
        return ratePerHour;
    }

    public void setRatePerHour(Double ratePerHour) {
        this.ratePerHour = ratePerHour;
    }

    public BikeCategory getCategory() {
        return category;
    }

    public void setCategory(BikeCategory category) {
        this.category = category;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}
