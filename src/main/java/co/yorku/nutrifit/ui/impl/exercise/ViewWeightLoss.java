package co.yorku.nutrifit.ui.impl.exercise;

import co.yorku.nutrifit.exercise.calculators.WeightLossCalculator;
import co.yorku.nutrifit.ui.NutrifitWindow;
import com.toedter.calendar.JDateChooser;

import java.awt.*;
import java.util.Date;

/*
Basically, what this is class is doing is getting the "activity rating" (aka. how much the user actually moves/exercises)
from class "DailyTotalEnergyExpenditureCalculator"
 */

public class ViewWeightLoss extends NutrifitWindow {
    private WeightLossCalculator weightLossCalc;
    public ViewWeightLoss() {
        super("Weight Loss by X Date", new GridLayout());

        //Date input
        addLabel("Date");
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        this.addComponent(dateChooser);

        addButton("Calculate", event -> {
            Date formattedDate = dateChooser.getDate();
            System.out.println(formattedDate);
            //Initialize weight loss calculator
            this.weightLossCalc = new WeightLossCalculator();
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

        this.addBackButton(MainExerciseMenu.getInstance());
        this.build();
    }
}
