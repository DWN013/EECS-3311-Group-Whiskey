package co.yorku.nutrifit.visualizer.calulcators;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.exercise.calculators.ActivityRatingCalculator;
import co.yorku.nutrifit.exercise.calculators.BMRCalculator;
import co.yorku.nutrifit.profile.IProfile;

public class DailyTotalEnergyExpenditureCalculator{
    private ActivityRatingCalculator actRating;
    private BMRCalculator bmr;

    // BMR * Activity Rating == How many cals I lose in a day sitting on my bum
    public double getDTEE(){
        double act_rating = actRating.getUserActivityRating();
        int  bmrCalc = bmr.getBMR(NutriFit.getInstance().getLoadedProfile());

        return (act_rating * bmrCalc);
    }
}
