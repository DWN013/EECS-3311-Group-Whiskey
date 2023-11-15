package co.yorku.nutrifit.visualizer.calculators;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.object.FoodGroupData;
import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CFGPlateCalculator {

    public Map<String, Double> getFoodGroupData(String specificParentFoodGroup) {

        Map<String, Double> data = Maps.newHashMap();
        if (specificParentFoodGroup == null) {
            NutriFit.getInstance().getNutrientDatabase().getAllFoodGroups().stream().filter(f -> f.getParentFoodGroupID() == -1).forEach(f -> data.put(f.getFoodGroupName(), ((double) f.getPercentage()) / 100.0));
            return data;
        }

        List<FoodGroupData> foodGroupDataList = NutriFit.getInstance().getNutrientDatabase().getAllFoodGroups();

        FoodGroupData parentGroupFood = foodGroupDataList.stream().filter(f -> f.getFoodGroupName().equals(specificParentFoodGroup)).findFirst().orElse(null);
        if (parentGroupFood == null) {
            return Collections.emptyMap(); // should never be the case... but just incase
        }

        List<FoodGroupData> childrenFoodGroups = NutriFit.getInstance().getNutrientDatabase().getAllFoodGroups().stream().filter(f -> f.getParentFoodGroupID() == parentGroupFood.getFoodGroupID()).collect(Collectors.toList());
        int total = childrenFoodGroups.size();

        for (FoodGroupData childrenFoodGroup : childrenFoodGroups) {
            data.put(childrenFoodGroup.getFoodGroupName(), 1.0 / (double) total);
        }

        return data;
    }
}
