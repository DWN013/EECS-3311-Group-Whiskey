package co.yorku.nutrifit.meal;

import java.util.Map;

public interface IMeal {

    int calculateTotalMealCalories(Map<String, Integer> ingredientsAndQuantities);

    // TODO: nutrient info?

}
