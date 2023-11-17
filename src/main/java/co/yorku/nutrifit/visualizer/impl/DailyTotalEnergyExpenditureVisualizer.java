package co.yorku.nutrifit.visualizer.impl;

import co.yorku.nutrifit.visualizer.IVisualizer;
import co.yorku.nutrifit.visualizer.calculators.DailyTotalEnergyExpenditureCalculator;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;

import java.util.Date;
import java.util.Map;

/*
 * Visualizer class for Total Daily Energy Expenditure visualizer
 * This class will take user given fromDate and toDate values, get calculated data and use that data to create a JFreechart
 * Will create a chart either as a bar graph or Pie chart depending on users choice
 */

public class DailyTotalEnergyExpenditureVisualizer extends IVisualizer {

    private DailyTotalEnergyExpenditureCalculator dailyTotalEnergyExpenditureCalculator; //Hold instance of the Visualizers calculator class to calculate the data for the visualizer

    public DailyTotalEnergyExpenditureVisualizer(DailyTotalEnergyExpenditureCalculator dailyTotalEnergyExpenditureCalculator, Dataset dataset) {
        super(dataset);
        this.dailyTotalEnergyExpenditureCalculator = dailyTotalEnergyExpenditureCalculator;
    }

    //Returns the Chart Name to print in the JFreeChart
    @Override
    public String getChartName() {
        return "Total Daily Energy Expenditure";
    }

  //Returns the y-axis Name to print in the JFreeChart (Horizontal values)
    @Override
    public String getBarGraphCategoryAxisLabel() {
        return "Date";
    }

  //Returns the x-axis Name to print in the JFreeChart (Vertical values)
    @Override
    public String getBarGraphValueAxisLabel() {
        return "Calories";
    }

    //Will build a bar graph with the data (upon users request)
    @Override
    public DefaultCategoryDataset buildBargraphDataset(String expandInfo, Date fromDate, Date toDate) {
    
    	Map<String, Integer> tdeeValues = dailyTotalEnergyExpenditureCalculator.getTDEE(fromDate, toDate); //Get Hash map of the data (calculated values (from calculator class))
    	
    	//Traverse the Hash Map of the data
    	for (Map.Entry<String, Integer> stringIntegerEntry : tdeeValues.entrySet()) {
            ((DefaultCategoryDataset) getDataset()).setValue(stringIntegerEntry.getValue(), "Total Daily Energy Expenditure", stringIntegerEntry.getKey()); //This will create each column in the bar graph 
        }

        return ((DefaultCategoryDataset) getDataset()); //return the Bar Graph dataset for the JFreeChart
    }

    //Will build a pie chart with the data (upon users request)
    @Override
    public DefaultPieDataset<String> buildPiechartDataset(String expandInfo, Date fromDate, Date toDate) {

    	Map<String, Integer> tdeeValues = dailyTotalEnergyExpenditureCalculator.getTDEE(fromDate, toDate); //Get Hash map of the data (calculated values (from calculator class))

    	//Traverse the Hash Map of the data
        for (Map.Entry<String, Integer> stringIntegerEntry : tdeeValues.entrySet()) {
            ((DefaultPieDataset<String>) getDataset()).setValue(stringIntegerEntry.getKey(), stringIntegerEntry.getValue()); //This will create each column in the bar graph
        }

        return ((DefaultPieDataset<String>) getDataset()); //return the Pie Chart dataset for the JFreeChart
    }

    //If the user has clicked on the "Update Date Range" button in the UI, will take new date values and create the graphs again
    @Override
    public void onDateRangeUpdate(String type, String expandData, Date newFromDate, Date newToDate) {

    	//If the user has multiple visualizers open, and they want to update one visualizer, this will ensure that the program is not going to update the wrong visualizer
        if (!type.equals(this.getChartName())) return;

        ///If current dataset type for this visualizer is a bar graph
        if (getDataset() instanceof DefaultCategoryDataset) {
            ((DefaultCategoryDataset) getDataset()).clear();
            this.buildBargraphDataset(expandData, newFromDate, newToDate); //rebuild bar graph
        } 
        //Otherwise, this will update the pie chart
        else if (getDataset() instanceof DefaultPieDataset) {
            ((DefaultPieDataset<String>) getDataset()).clear();
            this.buildPiechartDataset(expandData, newFromDate, newToDate); //rebuild pie chart
        }
    }
}
