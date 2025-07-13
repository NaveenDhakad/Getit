package com.example.freemaps.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

// Ignore unknown properties to prevent deserialization errors if the API adds new fields
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeocodingResponse {
    private String lat;
    private String lon;
    @JsonProperty("display_name")
    private String displayName;

    // Getters and Setters
    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return "GeocodingResponse{" +
               "lat='" + lat + '\'' +
               ", lon='" + lon + '\'' +
               ", displayName='" + displayName + '\'' +
               '}';
    }
}
