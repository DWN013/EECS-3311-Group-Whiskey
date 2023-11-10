package co.yorku.nutrifit.object;

public class NutrientData {

    private int nutrientID;
    private int nutrientCode;
    private String symbolText;
    private String unit;
    private String name;
    private String tag;

    public NutrientData(int nutrientID, int nutrientCode, String symbolText, String unit, String name, String tag) {
        this.nutrientID = nutrientID;
        this.nutrientCode = nutrientCode;
        this.symbolText = symbolText;
        this.unit = unit;
        this.name = name;
        this.tag = tag;
    }

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
}
