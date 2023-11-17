package co.yorku.nutrifit.visualizer.impl;

import co.yorku.nutrifit.visualizer.IVisualizer;
import co.yorku.nutrifit.visualizer.calculators.AvgUserFoodPlateCalculator;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;

import java.util.Date;
import java.util.Map;

/*
 * Visualizer class for Average User Food Plate visualizer
 * This class will take users inputed fromDate and toDate, get calculated values and visualize this data in a JFreeChart
 * This chart will be created as either a bar graph or Pie chart depending on users choice
 */

public class AvgUserPlateVisualizer extends IVisualizer {

    private AvgUserFoodPlateCalculator avgUserFoodPlateCalculator; //Hold instance of the Visualizers calculator class to calculate the data for the visualizer

    public AvgUserPlateVisualizer(AvgUserFoodPlateCalculator avgUserFoodPlateCalculator, Dataset dataset) {
        super(dataset);
        this.avgUserFoodPlateCalculator = avgUserFoodPlateCalculator;
    }

    //Returns the Chart Name to print in the JFreeChart
    @Override
    public String getChartName() {
        return "Average User Plate";
    }

    //Returns the y-axis Name to print in the JFreeChart (Horizontal values)
    @Override
    public String getBarGraphCategoryAxisLabel() {
        return "Food Group";
    }

    //Returns the x-axis Name to print in the JFreeChart (Vertical values)
    @Override
    public String getBarGraphValueAxisLabel() {
        return "Food Group Percentage";
    }

    //Checks if bar graph should be shown in percentages (which is true and should be)
    @Override
    public boolean isBargraphShownInPercentage() {
        return true;
    }

    //Checks is the chart is expandable (which is true) 
    @Override
    public boolean isChartExpandable() {
        return true;
    }

    //Will build a bar graph with the data (upon users request)
    @Override
    public DefaultCategoryDataset buildBargraphDataset(String expandInfo, Date fromDate, Date toDate) {

        Map<String, Double> avgUserFoodPlate = avgUserFoodPlateCalculator.getPlate(expandInfo, fromDate, toDate);

        //Traverse the Hash Map
        for(Map.Entry<String, Double> stringIntegerEntry : avgUserFoodPlate.entrySet()){

        	//Take the value in the Hash map and convert into a percentage amount
            int asPercentage = (int) (stringIntegerEntry.getValue() * 100);

            //Use percentage value and append this to the bar graph
            ((DefaultCategoryDataset) getDataset()).setValue(stringIntegerEntry.getValue(), "Food Group Category", stringIntegerEntry.getKey() + " (" + getDecimalFormat().format(asPercentage) + "%)");
        }
        return ((DefaultCategoryDataset) getDataset()); //Return the dataset of the Bar Graph
    }

    //Will build a pie chart with the data (upon users request)
    @Override
    public DefaultPieDataset<String> buildPiechartDataset(String expandInfo, Date fromDate, Date toDate) {

        Map<String, Double> avgUserFoodPlate = avgUserFoodPlateCalculator.getPlate(expandInfo, fromDate, toDate);

        //Traverse the Hash Map
        for(Map.Entry<String, Double> stringIntegerEntry : avgUserFoodPlate.entrySet()){

        	//Take the value in the Hash map and convert into a percentage amount
            int asPercentage = (int) (stringIntegerEntry.getValue() * 100);

            //Use percentage value and append this to the pie chart
            ((DefaultPieDataset<String>) getDataset()).setValue(stringIntegerEntry.getKey() + " (" + getDecimalFormat().format(asPercentage) + "%)", stringIntegerEntry.getValue() * 100);
        }
        return ((DefaultPieDataset<String>) getDataset()); //return the dataset of the Pie Chart
    }

    //If the user has clicked on the "Update Date Range" button in the UI, will take new date values and create the graphs again
    @Override
    public void onDateRangeUpdate(String type, String expandData, Date newFromDate, Date newToDate) {

    	//If the user has multiple visualizers open, and they want to update one visualizer, this will ensure that the program is not going to update the wrong visualizer
        if (!type.equals(this.getChartName())) return;

        //If current dataset type for this visualizer is a bar graph
        if (getDataset() instanceof DefaultCategoryDataset) {
            ((DefaultCategoryDataset) getDataset()).clear();
            this.buildBargraphDataset(expandData, newFromDate, newToDate); //rebuild bar graph data
        } 
        //Otherwise, this will update the pie chart
        else if (getDataset() instanceof DefaultPieDataset) {
            ((DefaultPieDataset<String>) getDataset()).clear();
            this.buildPiechartDataset(expandData, newFromDate, newToDate); //rebuild pie chart data
        }
    }
}