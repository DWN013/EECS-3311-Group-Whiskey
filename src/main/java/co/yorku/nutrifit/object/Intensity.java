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

    String displayName;

    Intensity(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

