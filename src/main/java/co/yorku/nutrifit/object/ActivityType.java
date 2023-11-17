package co.yorku.nutrifit.object;

// Enum ActivityType representing Activity types
public enum ActivityType {
    // Enum constants representing various types of activities
    ZUMBA("Zumba"),
    WALKING("Walking"),
    JUMP_ROPE("Jump Rope"),
    ICE_SKATING("Ice Skating"),
    JOGGING("Jogging"),
    BASKETBALL("Basketball"),
    RUNNING("Running"),
    SWIMMING("Swimming"),
    BIKING("Biking"),
    BOXING("Boxing");

    // Private variable to store the display name of the activity type
    String displayName;

    // Constructor to set the display name for each activity type
    private ActivityType(String displayName) {
        this.displayName = displayName;
    }

    // Getter method to retrieve the display name of the activity type
    public String getDisplayName() {
        return displayName;
    }
}
