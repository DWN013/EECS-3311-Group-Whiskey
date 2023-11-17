package co.yorku.nutrifit.object;

/*
 * Class NutrientData for storing information related to nutrients
 */

public class NutrientData {

    // Private instance variables representing nutrient data
    private int nutrientID;    // Unique ID for the nutrient
    private int nutrientCode;  // Code representing the nutrient
    private String symbolText; // Text symbolizing the nutrient
    private String unit;       // Unit for the nutrient
    private String name;       // Name of the nutrient
    private String tag;        // Tag associated with the nutrient

    // Constructor to initialize NutrientData with specific attributes
    public NutrientData(int nutrientID, int nutrientCode, String symbolText, String unit, String name, String tag) {
        this.nutrientID = nutrientID;
        this.nutrientCode = nutrientCode;
        this.symbolText = symbolText;
        this.unit = unit;
        this.name = name;
        this.tag = tag;
    }

    // Getter methods to retrieve various attributes of the nutrient
    public int getNutrientID() {
        return nutrientID;
    }

    public int getNutrientCode() {
        return nutrientCode;
    }

    public String getSymbolText() {
        return symbolText;
    }

    public String getUnit() {
        return unit;
    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }


    // Override equal method
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof NutrientData)) return false; // Check if the object we are comparing to is NutrientData
        return ((NutrientData) obj).getNutrientID() == this.getNutrientID(); // cast it and check the nutrient ids
    }

    // Override the hashCode method
    @Override
    public int hashCode() {
        return + this.nutrientID * this.nutrientCode * this.symbolText.hashCode() * this.unit.hashCode() * this.name.hashCode() * this.tag.hashCode(); // Calculate hash code by multiplying everything
    }
}
