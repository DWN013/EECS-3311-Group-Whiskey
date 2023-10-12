package co.yorku.nutrifit.meal.impl;

import co.yorku.nutrifit.database.userdata.UserDatabaseInterface;
import co.yorku.nutrifit.meal.Ingredients;
import co.yorku.nutrifit.meal.Meal;

public class MealHandler implements Meal {

    private UserDatabaseInterface userDatabaseInterface;
    private Ingredients ingredients;

    // log data
    // get general view
    // show breakdown of ingredients


    public MealHandler(UserDatabaseInterface userDatabaseInterface, Ingredients ingredients) {
        this.userDatabaseInterface = userDatabaseInterface;
        this.ingredients = ingredients;
    }
}
