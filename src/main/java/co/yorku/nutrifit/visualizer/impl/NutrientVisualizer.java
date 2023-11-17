package co.yorku.nutrifit.visualizer.impl;

import co.yorku.nutrifit.visualizer.IVisualizer;
import co.yorku.nutrifit.visualizer.calculators.NutrientCalculator;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/*
 * Visualizer class for the Nutrient Intake
 * This class will take the fromDate and toDate given by the user, get the data calculated and create the JFreeChart with the data
 * Will create a chart either as a bar graph or Pie chart depending on users choice
 */

public class NutrientVisualizer extends IVisualizer {

    private NutrientCalculator nutrientCalculator; //Hold instance of the Visualizers calculator class to calculate the data for the visualizer

    public NutrientVisualizer(NutrientCalculator nutrientCalculator, Dataset dataset) {
        super(dataset);
        this.nutrientCalculator = nutrientCalculator;
    }

    //Returns the Chart Name to print in the JFreeChart
    @Override
    public String getChartName() {
        return "Average Nutrients Per Day";
    }

    //Returns the y-axis Name to print in the JFreeChart (Horizontal values)
    @Override
    public String getBarGraphCategoryAxisLabel() {
        return "Date";
    }

    //Returns the x-axis Name to print in the JFreeChart (Vertical values)
    @Override
    public String getBarGraphValueAxisLabel() {
        return "Nutrient Consumed";
    }

    //This will allow the program to tell if the user is able to click on a portion of the chart and expand values
    @Override
    public boolean isChartExpandable() {
        return true;
    }


    //Will build a bar graph with the data (upon users request)
    @Override
    public DefaultCategoryDataset buildBargraphDataset(String expandInfo, Date fromDate, Date toDate) {

        LinkedHashMap<String, Map<String, Double>> data = this.nutrientCalculator.getNutrientInfoPerDate(fromDate, toDate); //Get Hash Map of calculated data from the calculator class
        boolean expand = expandInfo != null; //Determines if the user has clicked to expand the chart

        //Traverse the Hash Map of data
        for (Map.Entry<String, Map<String, Double>> stringMapEntry : data.entrySet()) {

        	//get the day of the logged nutrient (which is the key to the hash map)
            String day = stringMapEntry.getKey(); 
            //Will hold the value for the total nutrients of that day 
            double dayTotal = 0.0;

            //traverse the hash map again for all values (nutrients) associated to the first entry (day) of the data map (this hash map works in a way of a 2-dimensional array) 
            for (Map.Entry<String, Double> stringDoubleEntry : stringMapEntry.getValue().entrySet()) {

            	//get nutrient name which is stored as the key of the value
                String nutrientName = stringDoubleEntry.getKey();
                //get average which is the value in the Hash Map 
                double average = stringDoubleEntry.getValue();

                //Determine if the user clicked to expand the chart or not
                if (!expand) {
                    dayTotal += average; //if not expanded chart, add the average value of nutrient to the total for that day 
                } else {
                    ((DefaultCategoryDataset) getDataset()).setValue(average, nutrientName, stringMapEntry.getKey()); //if user expanded the chart, add the average value for nutrient to the bar chart directly
                }
            }
            //If chart not expanded
            if (!expand) {
                ((DefaultCategoryDataset) getDataset()).setValue(dayTotal, "Day", day); //Add the day total of average nutrients to the chart
            }
        }
        return ((DefaultCategoryDataset) getDataset()); //return Bar Chart
    }

    @Override
    public DefaultPieDataset<String> buildPiechartDataset(String expandInfo, Date fromDate, Date toDate) {

        LinkedHashMap<String, Map<String, Double>> data = this.nutrientCalculator.getNutrientInfoPerDate(fromDate, toDate); //Get Hash Map of calculated data from the calculator class
        boolean expand = expandInfo != null; //Determines if the user has clicked to expand the chart

        //Traverse the Hash Map of Data
        for (Map.Entry<String, Map<String, Double>> stringMapEntry : data.entrySet()) {

        	//get the day of the logged nutrient (which is the key to the hash map)
            String day = stringMapEntry.getKey();
            //Will hold the value for the total nutrients of that day 
            double dayTotal = 0.0;

            //traverse the hash map again for all values (nutrients) associated to the first entry (day) of the data map (this hash map works in a way of a 2-dimensional array) 
            for (Map.Entry<String, Double> stringDoubleEntry : stringMapEntry.getValue().entrySet()) {

            	//get nutrient name which is stored as the key of the value
                String nutrientName = stringDoubleEntry.getKey();
                //get average which is the value in the Hash Map
                double average = stringDoubleEntry.getValue();

                //Determine if the user clicked to expand the chart or not
                if (!expand) {
                    dayTotal += average; //if not expanded chart, add the average value of nutrient to the total for that day 
                } else {
                    ((DefaultPieDataset<String>) getDataset()).setValue(nutrientName, average); //if user expanded the chart, add the average value for nutrient to the bar chart directly
                }
            }
            //If chart not expanded
            if (!expand) {
                ((DefaultPieDataset<String>) getDataset()).setValue(day, dayTotal); //Add the day total of average nutrients to the chart
            }
        }

        return ((DefaultPieDataset<String>) getDataset()); //return Pie Chart
    }

    //If the user has clicked on the "Update Date Range" button in the UI, will take new date values and create the graphs again
    @Override
    public void onDateRangeUpdate(String type, String expandData, Date newFromDate, Date newToDate) {

    	//If the user has multiple visualizers open, and they want to update one visualizer, this will ensure that the program is not going to update the wrong visualizer
        if (!type.equals(this.getChartName())) return;

        //If user selected Bar Graph - will update the chart as a bar graph
        if (getDataset() instanceof DefaultCategoryDataset) {
            ((DefaultCategoryDataset) getDataset()).clear();
            this.buildBargraphDataset(expandData, newFromDate, newToDate); //build new bar graph
        } 
        //Otherwise, this will update the pie chart
        else if (getDataset() instanceof DefaultPieDataset) {
            ((DefaultPieDataset<String>) getDataset()).clear();
            this.buildPiechartDataset(expandData, newFromDate, newToDate); //build new pie chart
        }
    }
}
