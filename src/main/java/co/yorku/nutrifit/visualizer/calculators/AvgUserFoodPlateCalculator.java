package co.yorku.nutrifit.visualizer.calculators;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.logs.LogIterator;
import co.yorku.nutrifit.logs.impl.Meal;
import co.yorku.nutrifit.object.FoodGroupData;
import co.yorku.nutrifit.object.FoodInfo;
import co.yorku.nutrifit.object.daterange.DateRange;
import com.google.common.collect.Maps;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/*
    This is the AvgUserFoodPlateCalculator.
    It's purpose is to calculate the user's average food plate
    over a period of time (Food Groups)

 */
public class AvgUserFoodPlateCalculator {

    // This method returns a LinkedHashMap of Type String and double
    // Which contain either a Food Group and Percentage
    public LinkedHashMap<String, Double> getPlate(String parentFoodGroupName, DateRange dateRange) {

        // Query Database for all logs for the date range
        LogIterator logIterator = NutriFit.getInstance().getUserDatabase().getUserMealLogs(
                NutriFit.getInstance().getLoadedProfile().getId(), dateRange
        );
        logIterator.sortByDateAscending(); // Short by ascending

        // Holds the Good Group Name and the amount of time it occurs in each meal
        LinkedHashMap<String, Integer> fGdata = Maps.newLinkedHashMap();

        while (logIterator.hasNext()) {
            Meal meal = (Meal) logIterator.getNext();

            for (Map.Entry<Integer, Integer> integerIntegerEntry : meal.getFoodIDAndAmounts().entrySet()){

                //ID of the specific food item, gets the key of the entry (ie. 17)
                int foodID = integerIntegerEntry.getKey();
                //Gets information about that specific food item via the ID
                FoodInfo foodInfo = NutriFit.getInstance().getNutrientDatabase().getFoodInfo(foodID);
                //Gets the food group of that specific item by using the food group ID
                String foodGroup = NutriFit.getInstance().getNutrientDatabase().getFoodGroupName(foodInfo.getFoodGroupID());

                // Add to the food group to the map
                fGdata.put(foodGroup, fGdata.getOrDefault(foodGroup, 0) + 1);
            }
        }

        //Returns hashmap with all food groups and total amount
        return this.calculateFoodGroupPercentage(parentFoodGroupName, NutriFit.getInstance().getNutrientDatabase().getAllFoodGroups(), fGdata);
    }

    private LinkedHashMap<String, Double> calculateFoodGroupPercentage(String parentFoodGroupName, List<FoodGroupData> foodGroupDataList, Map<String, Integer> fGdata) {

        LinkedHashMap<String, Integer> totals = Maps.newLinkedHashMap(); // Data to be finalized and returned
        int total = 0;

        for (Map.Entry<String, Integer> stringIntegerEntry : fGdata.entrySet()) { // Loop through all the food groups the user has consumed over the time period

            // Get the food group data from the queried data
            FoodGroupData foodGroupData = getFoodGroupData(stringIntegerEntry.getKey());
            if (foodGroupData == null) continue; // If no food group was found, continue

            // Get the Parent Food group of the current food group
            FoodGroupData parentFoodGroup = foodGroupDataList.stream().filter(f -> f.getFoodGroupID() == foodGroupData.getParentFoodGroupID()).findFirst().orElse(null);
            if (parentFoodGroup == null) continue; // If no parent was found, continue

            if (parentFoodGroupName == null) {
                totals.put(parentFoodGroup.getFoodGroupName(), totals.getOrDefault(parentFoodGroup.getFoodGroupName(), 0) + 1);
                total++;
            } else if (parentFoodGroup.getFoodGroupName().equals(parentFoodGroupName)) {
                totals.put(foodGroupData.getFoodGroupName(), totals.getOrDefault(foodGroupData.getFoodGroupName(), 0) + 1);
                total++;
            }
        }

        return calculatePercentages(totals, total);
    }

    private static LinkedHashMap<String, Double> calculatePercentages(LinkedHashMap<String, Integer> totals, double total) {
        LinkedHashMap<String, Double> percentages = Maps.newLinkedHashMap(); // Create the map that will be returned

        for (Map.Entry<String, Integer> stringIntegerEntry : totals.entrySet()) { // Loop through all the children food groups based on the parent food group's children we want to display
            percentages.put(stringIntegerEntry.getKey(), (double) stringIntegerEntry.getValue() / total); // Calculate the average for how many times this specific food group shows up
        }
        return percentages;
    }

    private FoodGroupData getFoodGroupData(String foodGroupName) {
        return NutriFit.getInstance().getNutrientDatabase().getAllFoodGroups().stream().filter(f -> f.getFoodGroupName().equals(foodGroupName)).findFirst().orElse(null);
    }

}
