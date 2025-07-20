package com.GetApp.Get.Entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "user_search_preferences")
public class UserSearchPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "User is required")
    private User user;

    @Column(name = "preferred_radius_km")
    private Integer preferredRadiusKm = 10;

    @Column(name = "auto_location_enabled")
    private Boolean autoLocationEnabled = true;

    @Column(name = "preferred_categories", columnDefinition = "JSON")
    private String preferredCategories; // JSON array of category IDs

    @Column(name = "max_daily_rate", precision = 10, scale = 2)
    private BigDecimal maxDailyRate;

    @Column(name = "min_rating", precision = 3, scale = 2)
    private BigDecimal minRating;

    @Column(name = "delivery_preferred")
    private Boolean deliveryPreferred = false;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructors
    public UserSearchPreference() {}

    public UserSearchPreference(User user, Integer preferredRadiusKm) {
        this.user = user;
        this.preferredRadiusKm = preferredRadiusKm;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getPreferredRadiusKm() {
        return preferredRadiusKm;
    }

    public void setPreferredRadiusKm(Integer preferredRadiusKm) {
        this.preferredRadiusKm = preferredRadiusKm;
    }

    public Boolean getAutoLocationEnabled() {
        return autoLocationEnabled;
    }

    public void setAutoLocationEnabled(Boolean autoLocationEnabled) {
        this.autoLocationEnabled = autoLocationEnabled;
    }

    public String getPreferredCategories() {
        return preferredCategories;
    }

    public void setPreferredCategories(String preferredCategories) {
        this.preferredCategories = preferredCategories;
    }

    public BigDecimal getMaxDailyRate() {
        return maxDailyRate;
    }

    public void setMaxDailyRate(BigDecimal maxDailyRate) {
        this.maxDailyRate = maxDailyRate;
    }

    public BigDecimal getMinRating() {
        return minRating;
    }

    public void setMinRating(BigDecimal minRating) {
        this.minRating = minRating;
    }

    public Boolean getDeliveryPreferred() {
        return deliveryPreferred;
    }

    public void setDeliveryPreferred(Boolean deliveryPreferred) {
        this.deliveryPreferred = deliveryPreferred;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSearchPreference that = (UserSearchPreference) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "UserSearchPreference{" +
                "id=" + id +
                ", preferredRadiusKm=" + preferredRadiusKm +
                ", autoLocationEnabled=" + autoLocationEnabled +
                ", deliveryPreferred=" + deliveryPreferred +
                '}';
    }
}

