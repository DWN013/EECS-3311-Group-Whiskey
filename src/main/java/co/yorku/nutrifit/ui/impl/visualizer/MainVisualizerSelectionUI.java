package co.yorku.nutrifit.ui.impl.visualizer;

import co.yorku.nutrifit.ui.NutrifitWindow;
import co.yorku.nutrifit.ui.impl.main.NutriFitMainUI;
import co.yorku.nutrifit.visualizer.factory.VisualizerUI;
import co.yorku.nutrifit.visualizer.factory.impl.BargraphUI;
import co.yorku.nutrifit.visualizer.factory.impl.PieChartUI;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/*
 * Visualizer selection Menu - main menu that allows user to see all the types of visualizers that they can choose from
 * As well as the user can choose if they want to see the data displayed in a bar graph or a pie chart
 */

public class MainVisualizerSelectionUI extends NutrifitWindow {

    private static MainVisualizerSelectionUI instance; //stores instance into this variable

    //Implementation of Singleton Design Pattern - store instance of MainVisualizerSelectionUI page
    public static MainVisualizerSelectionUI getInstance() {
        if (instance == null) {
            instance = new MainVisualizerSelectionUI();
        }
        return instance;
    }

    private VisualizerUI visualizerUI;

    public MainVisualizerSelectionUI() {
        super("Visualizers", new GridLayout(5, 2));
        this.visualizerUI = new BargraphUI();

        //Give user choice between a bar graph or pie chart representation for the visualizer
        JRadioButton barGraphRadioButton = addRadioButton("Bar Graph", true, event -> this.visualizerUI = new BargraphUI());
        JRadioButton pieChartRadioButton = addRadioButton("Pie Chart", false, event -> this.visualizerUI = new PieChartUI());

        //Default dates to show  chart (after choice is made, user can update date range themselves) 
        Date defaultFromDate = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(14));
        Date defaultToDate = new Date(System.currentTimeMillis());

        //Setting default time for the default date
        defaultFromDate.setHours(0);
        defaultFromDate.setMinutes(0);
        defaultFromDate.setSeconds(0);

        defaultToDate.setHours(23);
        defaultToDate.setMinutes(59);
        defaultToDate.setSeconds(59);
        
        //Button for Average Food Plate Visualizer - allows user to see on average what food groups they eat during given time frame
        addButton("Average Food Plate Visualizer", event -> {
            this.hideWindow();
            new NutrifitVisualizer("Average User Food Plate Visualizer", this, this.visualizerUI.buildAverageUserPlateUI(defaultFromDate, defaultToDate), defaultFromDate, defaultToDate).showWindow();
        });

        //Button for Calorie Intake Visualizer - allow user to see how many calories they have per day
        addButton("Calorie Intake Visualizer", event -> {
            this.hideWindow();
            new NutrifitVisualizer("Calorie Intake Visualizer", this, this.visualizerUI.buildCalorieUI(defaultFromDate, defaultToDate), defaultFromDate, defaultToDate).showWindow();

        });

        //Button for CFG Plate Visualizer - allow user to see the recommended Canada Food Guide plate
        addButton("CFG Plate Visualizer", event -> {
            this.hideWindow();
            new NutrifitVisualizer("Canada Food Guide Plate Visualizer", this, this.visualizerUI.buildCFGPlateUI(defaultFromDate, defaultToDate), defaultFromDate, defaultToDate).showWindow();

        });
        
        //Button to see Total Daily Energy Expenditure Visualizer - allows user to see how much their TDEE is per day
        addButton("Energy Expenditure Visualizer", event -> {
            this.hideWindow();
            new NutrifitVisualizer("Energy Expenditure Visualizer", this, this.visualizerUI.buildDailyTotalEnergyExpenditure(defaultFromDate, defaultToDate), defaultFromDate, defaultToDate).showWindow();

        });

        //Button to see Nutrient Visualizer - allows user to see their nutrient intake per day
        addButton("Nutrient Visualizer", event -> {
            this.hideWindow();
            new NutrifitVisualizer("Nutrient Visualizer", this, this.visualizerUI.buildNutrientUI(defaultFromDate, defaultToDate), defaultFromDate, defaultToDate).showWindow();
        });

        this.createButtonGroup(barGraphRadioButton, pieChartRadioButton);
        this.addBackButton(NutriFitMainUI.getInstance()); //Add back button for user to go back to main UI page
        this.build();
    }


}
