package com.GetApp.Get.enums;

/**
 * Enum representing the condition rating of tools and user preferences
 */
public enum ConditionRating {
    NEW("New", "Brand new, never used", 5),
    EXCELLENT("Excellent", "Like new, minimal wear", 4),
    GOOD("Good", "Well maintained, some wear", 3),
    FAIR("Fair", "Functional with noticeable wear", 2),
    POOR("Poor", "Heavily used, may need repairs", 1);

    private final String displayName;
    private final String description;
    private final int numericValue;

    ConditionRating(String displayName, String description, int numericValue) {
        this.displayName = displayName;
        this.description = description;
        this.numericValue = numericValue;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public int getNumericValue() {
        return numericValue;
    }

    /**
     * Check if this condition is better than or equal to the specified condition
     */
    public boolean isAtLeast(ConditionRating other) {
        return this.numericValue >= other.numericValue;
    }

    /**
     * Check if this condition is worse than the specified condition
     */
    public boolean isWorseThan(ConditionRating other) {
        return this.numericValue < other.numericValue;
    }

    /**
     * Get condition rating from numeric value
     */
    public static ConditionRating fromNumericValue(int value) {
        for (ConditionRating rating : values()) {
            if (rating.numericValue == value) {
                return rating;
            }
        }
        throw new IllegalArgumentException("Invalid numeric value: " + value);
    }

    /**
     * Get condition rating from display name (case insensitive)
     */
    public static ConditionRating fromDisplayName(String displayName) {
        for (ConditionRating rating : values()) {
            if (rating.displayName.equalsIgnoreCase(displayName)) {
                return rating;
            }
        }
        throw new IllegalArgumentException("Invalid display name: " + displayName);
    }

    @Override
    public String toString() {
        return displayName;
    }
}

