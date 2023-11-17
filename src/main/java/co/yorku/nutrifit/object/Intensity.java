package co.yorku.nutrifit.object;

/*
 * Enum Intensity representing different levels of intensity
 */

public enum Intensity {
    VERY_LOW("Very Low"), // Very low intensity
    LOW("Low"),      // Low intensity
    MEDIUM("Medium"),   // Medium intensity
    HIGH("High"),     // High intensity
    VERY_HIGH("Very High"); // Very high intensity

    String displayName; // Display name variable

    // Constructor
    Intensity(String displayName) {
        this.displayName = displayName;
    }

    // Get display name method
    public String getDisplayName() {
        return displayName;
    }
}

