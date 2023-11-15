package co.yorku.nutrifit.object;

// Enum MealType representing different meal types
public enum MealType {
    // Enum constants representing meal types: BREAKFAST, LUNCH, DINNER, SNACK
    BREAKFAST("Breakfast"),
    LUNCH("Lunch"),
    DINNER("Dinner"),
    SNACK("Snack");

    // Private variable to store the display name of the meal type
    private String displayName;

    // Constructor to set the display name for each meal type
    MealType(String displayName) {
        this.displayName = displayName;
    }

    // Getter method to retrieve the display name of the meal type
    public String getDisplayName() {
        return displayName;
    }
}
