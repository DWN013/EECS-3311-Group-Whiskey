package co.yorku.nutrifit.ui.impl.exercise;

import co.yorku.nutrifit.exercise.calculators.WeightLossCalculator;
import co.yorku.nutrifit.ui.NutrifitWindow;
import com.toedter.calendar.JDateChooser;

import java.awt.*;
import java.util.Date;

/*
 * Allow user to view their expected weight loss under current exercise and meal patterns (taking into account previous 2 weeks of exercise and meal)
 */

public class ViewWeightLoss extends NutrifitWindow {
    private WeightLossCalculator weightLossCalc; //Store instance of weightLossCalculator to use to calculate the weight loss for user
    public ViewWeightLoss() {
        super("Weight Loss by X Date", new GridLayout());

        //Date input (to get date in future from user)
        addLabel("Date");
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        this.addComponent(dateChooser);

        //A "Submit" button, once clicked will compute the calculations
        addButton("Calculate", event -> {
        	
        	//Get date inputed by user
            Date formattedDate = dateChooser.getDate();
            
            //Initialize weight loss calculator
            this.weightLossCalc = new WeightLossCalculator();
            
            //Get project weight loss by using future date
            double kgGainedOrLost = weightLossCalc.getWeightLossForDate(formattedDate);

            //Decision tree for if the user gained or lost weight and what message will be displayed
            if(kgGainedOrLost > 0.0) {
                showMessageDialog(String.format("You're gonna gain %.2f kg!", kgGainedOrLost));
            } else if (kgGainedOrLost < 0.0){
                showMessageDialog(String.format("You're gonna lose %.2f kg!", (kgGainedOrLost*-1)));
            } else {
                showMessageDialog("You will maintain your current weight.");
            }
        });

        //Create back button that will take user to exercise main menu
        this.addBackButton(MainExerciseMenu.getInstance());
        this.build();
    }
}
