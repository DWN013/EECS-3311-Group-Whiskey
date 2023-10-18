package co.yorku.nutrifit.object;

public enum ActivityType {

    ZUMBA("Zumba"),
    WALKING("Walking"),
    JUMP_ROPE("Jump Rope"),
    ICE_SKATING("Ice Skating"),
    JOGGING("Jogging"),
    BASKETBALL("Basketball"),
    RUNNING("Running"),
    SWIMMING("Swimming"),
    BIKING("Biking"),
    BOXING("Boxing")
    ;

    String displayName;

    private ActivityType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
