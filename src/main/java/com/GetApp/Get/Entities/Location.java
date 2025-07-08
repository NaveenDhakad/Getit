package com.GetApp.Get.Entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationId;

    private String locationName;
    private String address;
    private String city;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    private List<Bike> bikes;

    public Location() {
        super();
    }

    public Location(Long locationId, String locationName, String address, String city, List<Bike> bikes) {
        this.locationId = locationId;
        this.locationName = locationName;
        this.address = address;
        this.city = city;
        this.bikes = bikes;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Bike> getBikes() {
        return bikes;
    }

    public void setBikes(List<Bike> bikes) {
        this.bikes = bikes;
    }
}

