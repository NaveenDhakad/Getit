package com.GetApp.Get.Entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class BikeCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    private String categoryName;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Bike> bikes;

    public BikeCategory() {
        super();
    }

    public BikeCategory(Long categoryId, String categoryName, List<Bike> bikes) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.bikes = bikes;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Bike> getBikes() {
        return bikes;
    }

    public void setBikes(List<Bike> bikes) {
        this.bikes = bikes;
    }
}

