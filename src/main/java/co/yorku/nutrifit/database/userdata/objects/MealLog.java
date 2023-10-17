package co.yorku.nutrifit.database.userdata.objects;

import java.util.Date;
import java.util.Map;

public class MealLog {

    private Date date;
    private MealType mealType;
    private Map<String, Integer> ingredientsAndQuantities;

    public MealLog(Date date, MealType mealType, Map<String, Integer> ingredientsAndQuantities) {
        this.date = date;
        this.mealType = mealType;
        this.ingredientsAndQuantities = ingredientsAndQuantities;
    }

    public Date getDate() {
        return date;
    }

    public MealType getMealType() {
        return mealType;
    }

    public Map<String, Integer> getIngredientsAndQuantities() {
        return ingredientsAndQuantities;
    }
}
