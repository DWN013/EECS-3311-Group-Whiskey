package co.yorku.nutrifit.visualizer.calculators;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.logs.LogIterator;
import co.yorku.nutrifit.logs.impl.Meal;
import co.yorku.nutrifit.object.FoodGroupData;
import co.yorku.nutrifit.object.FoodInfo;
import com.google.common.collect.Maps;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*

    This is the AvgUserFoodPlateCalculator.
    It's purpose is to calculate the user's average food plate
    over a period of time (Food Groups)

 */
public class AvgUserFoodPlateCalculator {

    // This method returns a LinkedHashMap of Type String and double
    // Which contain either a Food Group and Percentage
    public LinkedHashMap<String, Double> getPlate(String parentFoodGroupName, Date dateFrom, Date dateTo) {

        // Query Database for all logs for the date range
        LogIterator logIterator = NutriFit.getInstance().getUserDatabase().getUserMealLogs(
                NutriFit.getInstance().getLoadedProfile().getId(), dateFrom, dateTo
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

        List<FoodGroupData> foodGroupDataList = NutriFit.getInstance().getNutrientDatabase().getAllFoodGroups(); // Query all food groups from the database

        if (parentFoodGroupName == null) { // If no parent food group name was provided, we will show all parent food groups instead of all the children

            LinkedHashMap<String, Integer> data = Maps.newLinkedHashMap(); // Data to be finalized and returned
            int totalEntries = 0; // Total number of occurrences of food groups

            for (Map.Entry<String, Integer> stringIntegerEntry : fGdata.entrySet()) { // Loop through all the food groups the user has consumed over the time period

                // Get the food group data from the queried data
                FoodGroupData foodGroupData = foodGroupDataList.stream().filter(f -> f.getFoodGroupName().equals(stringIntegerEntry.getKey())).findFirst().orElse(null);
                if (foodGroupData == null) continue; // If no food group was found, continue

                // Get the Parent Food group of the current food group
                FoodGroupData parentFoodGroup = foodGroupDataList.stream().filter(f -> f.getFoodGroupID() == foodGroupData.getParentFoodGroupID()).findFirst().orElse(null);
                if (parentFoodGroup == null) continue; // If no parent was found, continue

                // Add the parent food group and add 1 to the number of occurrences of the food group
                data.put(parentFoodGroup.getFoodGroupName(), data.getOrDefault(parentFoodGroup.getFoodGroupName(), 0) + 1);
                totalEntries++; // Increment the total number of food groups
            }


            LinkedHashMap<String, Double> percentages = Maps.newLinkedHashMap(); // Create the map that will be returned
            for (Map.Entry<String, Integer> stringIntegerEntry : data.entrySet()) { // Loop through all the parent food groups and the number of occurrences of that food group
                percentages.put(stringIntegerEntry.getKey(), stringIntegerEntry.getValue() / (double) totalEntries); // Calculate the average and put it in our percentages map to be returned
            }

            return percentages; // Return the data
        }

        LinkedHashMap<String, Integer> totals = Maps.newLinkedHashMap(); // Data to be finalized and returned
        int total = 0; // Total number of occurences of food groups

        for (Map.Entry<String, Integer> stringIntegerEntry : fGdata.entrySet()) { // Loop through all the food groups the user has consumed over the time period

            // Get the food group data from the queried data
            FoodGroupData foodGroupData = foodGroupDataList.stream().filter(f -> f.getFoodGroupName().equals(stringIntegerEntry.getKey())).findFirst().orElse(null);
            if (foodGroupData == null) continue; // If no food group was found, continue

            // Get the Parent Food group of the current food group
            FoodGroupData parentFoodGroup = foodGroupDataList.stream().filter(f -> f.getFoodGroupID() == foodGroupData.getParentFoodGroupID()).findFirst().orElse(null);
            if (parentFoodGroup == null) continue; // If no parent was found, continue

            // If the parent food group is not equal to the food group we want the children of, then continue
            if (!parentFoodGroup.getFoodGroupName().equals(parentFoodGroupName)) continue;

            // Add the child food group for the specified parent and add or increment it's amount
            totals.put(foodGroupData.getFoodGroupName(), totals.getOrDefault(foodGroupData.getFoodGroupName(), 0) + 1);
            total++;
        }

        LinkedHashMap<String, Double> percentages = Maps.newLinkedHashMap(); // Create the map that will be returned

        for (Map.Entry<String, Integer> stringIntegerEntry : totals.entrySet()) { // Loop through all the children food groups based on the parent food group's children we want to display
            percentages.put(stringIntegerEntry.getKey(), (double) stringIntegerEntry.getValue() / (double) total); // Calculate the average for how many times this specific food group shows up
        }

        //Returns hashmap with all food groups and total amount
        return percentages;
    }
}
