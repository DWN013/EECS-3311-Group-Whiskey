package co.yorku.nutrifit.visualizer.calulcators;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.logs.LogIterator;
import co.yorku.nutrifit.logs.impl.Meal;
import co.yorku.nutrifit.object.FoodInfo;
import com.google.common.collect.Maps;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class AvgUserFoodPlateCalculator {

    public LinkedHashMap<String, Double>  getPlate(Date dateFrom, Date dateTo){

        LogIterator logIterator = NutriFit.getInstance().getUserDatabase().getUserMealLogs(
                NutriFit.getInstance().getLoadedProfile().getId(), dateFrom, dateTo
        );
        logIterator.sortByDateAscending();
        //Linked hashmap for food group data (takes kind of food group and how often it appears)
        LinkedHashMap<String, Integer> fGdata = Maps.newLinkedHashMap();
        //count total amount of food group ids (ex. fats oils, carbs, show up) then go to DB get food group name and visualize it
        //key == foodID once have foodID get foodGroupID and get the name

        while (logIterator.hasNext()) {
            Meal meal = (Meal) logIterator.getNext();

            for (Map.Entry<Integer, Integer> integerIntegerEntry : meal.getFoodIDAndAmounts().entrySet()){
                //ID of the specific food item, gets the key of the entry (ie. 17)
                int foodID = integerIntegerEntry.getKey();
                //Gets information about that specific food item via the ID
                FoodInfo foodInfo = NutriFit.getInstance().getNutrientDatabase().getFoodInfo(foodID);
                //Gets the food group of that specific item by using the food group ID
                String foodGroup = NutriFit.getInstance().getNutrientDatabase().getFoodGroupName(foodInfo.getFoodGroupID());

                fGdata.put(foodGroup, fGdata.getOrDefault(foodGroup, 0) + 1);

            }
        }

        int totalFoodGroups = fGdata.values().stream().mapToInt(m -> m).sum();
        LinkedHashMap<String, Double> percentages = Maps.newLinkedHashMap();

        for (Map.Entry<String, Integer> stringIntegerEntry : fGdata.entrySet()) {
            percentages.put(stringIntegerEntry.getKey(), stringIntegerEntry.getValue() / (double) totalFoodGroups);
        }

        //Returns hashmap with all food groups and total amount
        return percentages;
    }
}
