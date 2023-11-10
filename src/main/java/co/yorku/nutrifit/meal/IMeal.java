package co.yorku.nutrifit.meal;

import co.yorku.nutrifit.logs.impl.Meal;
import co.yorku.nutrifit.object.FoodInfo;

import java.util.Map;

public interface IMeal {

    int calculateTotalMealCalories(Map<Integer, Integer> foodInfoMap);

}
