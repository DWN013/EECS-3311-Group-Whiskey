package co.yorku.nutrifit.meal;

import java.util.Map;

/*
 * Interface to calculate total meal calories based on foods (given by user) 
 */

public interface IMeal {

    int calculateTotalMealCalories(Map<Integer, Integer> foodInfoMap);

}
