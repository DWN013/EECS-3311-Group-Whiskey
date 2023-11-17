package co.yorku.nutrifit.visualizer.calculators;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.object.FoodGroupData;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
    This is the CFGPlateCalculator.
    It's purpose is to calculate the average food groups
    in accordance with the CFG (Canada Food Guide)
 */
public class CFGPlateCalculator {

    // This method returns a LinkedHashMap of type String, Double which contain
    // a Food Group Name and the percentage of that food group that should be consumed
    public Map<String, Double> getFoodGroupData(String specificParentFoodGroup) {

        Map<String, Double> data = Maps.newHashMap(); // Data that will be returned
        if (specificParentFoodGroup == null) { // If the Specific Food Group is not specified we will then show all the parent food groups
            // Query the Database for all the parent food groups
            // Filter out all the food groups that are not parent ones
            // Add the parent food groups to the data map
            NutriFit.getInstance().getNutrientDatabase().getAllFoodGroups().stream().filter(f -> f.getParentFoodGroupID() == -1).forEach(f -> data.put(f.getFoodGroupName(), ((double) f.getPercentage()) / 100.0));
            return data; // Return the data
        }

        List<FoodGroupData> foodGroupDataList = NutriFit.getInstance().getNutrientDatabase().getAllFoodGroups(); // Query Database for all the food groups

        // Search the returned data for the parent food group
        FoodGroupData parentGroupFood = foodGroupDataList.stream().filter(f -> f.getFoodGroupName().equals(specificParentFoodGroup)).findFirst().orElse(null);
        if (parentGroupFood == null) { // If we cannot find the parent food group
            return data; // Return an empty list
        }

        // Search all the food groups where the parent id matches the specific parent we are looking food
        List<FoodGroupData> childrenFoodGroups = NutriFit.getInstance().getNutrientDatabase().getAllFoodGroups().stream().filter(f -> f.getParentFoodGroupID() == parentGroupFood.getFoodGroupID()).collect(Collectors.toList());
        int total = childrenFoodGroups.size(); // Get the total amount of children food groups that fall under the specific parent food group

        // Loop through every food group and add it to our map.
        // We are showing all the children food groups as a equal percentage
        for (FoodGroupData childrenFoodGroup : childrenFoodGroups) {
            data.put(childrenFoodGroup.getFoodGroupName(), 1.0 / (double) total);
        }

        return data; // Return the data
    }
}
