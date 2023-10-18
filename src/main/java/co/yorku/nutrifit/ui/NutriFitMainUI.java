package co.yorku.nutrifit.ui;

import co.yorku.nutrifit.database.userdata.IUserDatabase;
import co.yorku.nutrifit.profile.IProfile;
import co.yorku.nutrifit.ui.visualizerui.PieChartVisualizer;

import javax.swing.*;

public class NutriFitMainUI extends JFrame {

    public NutriFitMainUI(IUserDatabase userDatabaseInterface, int userId) {
        super("Home Page");

        IProfile profile = userDatabaseInterface.getProfile(userId);

        JPanel south = new JPanel();

        JButton visualize = new JButton("Visualize Data");
        JButton logExercise = new JButton("Log Exercise");
        JButton showExerciseLog = new JButton("Show Exercise Log");
        JButton logMeal = new JButton("Log Meal");
        JButton showMealLog = new JButton("Show Meal Log");

        visualize.addActionListener(e -> {
            PieChartVisualizer pieChartVisualizer = new PieChartVisualizer();
            pieChartVisualizer.setSize(1300, 600);
            pieChartVisualizer.setVisible(true);
        });

        //ActionListener function to open log exercise via. log exercise button
        logExercise.addActionListener(e -> new ExerciseInputUI(userDatabaseInterface, userId));
        showExerciseLog.addActionListener(e -> new ExerciseDisplayUI(userDatabaseInterface, profile));
        showMealLog.addActionListener(e -> new MealDisplayUI(userDatabaseInterface, profile));
        logMeal.addActionListener(e -> new MealInputUI(userDatabaseInterface, profile));



        south.add(visualize);
        south.add(logExercise);
        south.add(showExerciseLog);
        south.add(logMeal);
        south.add(showMealLog);

        getContentPane().add(south);

        pack();
        setVisible(true);
    }
}
