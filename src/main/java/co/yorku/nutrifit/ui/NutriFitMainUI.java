package co.yorku.nutrifit.ui;

import co.yorku.nutrifit.database.userdata.IUserDatabase;
import co.yorku.nutrifit.logs.impl.Meal;
import co.yorku.nutrifit.profile.IProfile;
import co.yorku.nutrifit.ui.visualizerui.BarChartVisualizer;

import javax.swing.*;

public class NutriFitMainUI extends JFrame {

    private static NutriFitMainUI instance;

    public static NutriFitMainUI getInstance() {
        if (instance == null) {
            instance = new NutriFitMainUI();
        }
        return instance;
    }

    private NutriFitMainUI() {
        super("Home Page");

        JPanel south = new JPanel();

        JButton visualize = new JButton("Visualize Calories Burned");
        JButton logExercise = new JButton("Log Exercise");
        JButton showExerciseLog = new JButton("Show Exercise Log");
        JButton logMeal = new JButton("Log Meal");
        JButton showMealLog = new JButton("Show Meal Log");

        visualize.addActionListener(e -> {
            BarChartVisualizer barChartVisualizer = new BarChartVisualizer();
            barChartVisualizer.setSize(1300, 600);
            barChartVisualizer.setVisible(true);
        });

        //ActionListener function to open log exercise via. log exercise button
        logExercise.addActionListener(e -> ExerciseInputUI.getInstance().showToUser());
        showExerciseLog.addActionListener(e -> new ExerciseDisplayUI().showToUser());
        showMealLog.addActionListener(e -> new MealDisplayUI().showToUser());
        logMeal.addActionListener(e -> {
            MealInputUI.getInstance().clearInputtedIngredients();
            MealInputUI.getInstance().showToUser();
        });

        south.add(visualize);
        south.add(logExercise);
        south.add(showExerciseLog);
        south.add(logMeal);
        south.add(showMealLog);

        getContentPane().add(south);

        pack();
        setVisible(false);
    }

    public void showToUser() {
        setVisible(true);
    }
}
