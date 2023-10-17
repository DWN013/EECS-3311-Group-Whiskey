package co.yorku.nutrifit.ui;

import co.yorku.nutrifit.database.userdata.UserDatabaseInterface;
import co.yorku.nutrifit.profile.Profile;
import co.yorku.nutrifit.ui.visualizerui.PieChartVisualizer;

import javax.swing.*;

public class NutriFitMainUI extends JFrame {

    public NutriFitMainUI(UserDatabaseInterface userDatabaseInterface, int userId) {
        super("Home Page");

        Profile profile = userDatabaseInterface.getProfile(userId);

        JPanel south = new JPanel();

        JButton visualize = new JButton("Visualize Data");
        JButton logExercise = new JButton("Log Exercise");
        JButton logMeal = new JButton("Log Meal");

        visualize.addActionListener(e -> {

            PieChartVisualizer pieChartVisualizer = new PieChartVisualizer();
            pieChartVisualizer.setSize(900, 600);
            pieChartVisualizer.pack();
            pieChartVisualizer.setVisible(true);

        });

        //ActionListener function to open log exercise via. log exercise button
        logExercise.addActionListener(e -> {

            //debug info
            System.out.println("User clicked the log exercise button");
            //Creates a new window for logging exercise data
            new ExerciseInputUI(userDatabaseInterface, userId);

        });



        logMeal.addActionListener(e -> {
            System.out.println("User clicked the log meal button");

            // Remove the current panel
            getContentPane().remove(south);

            // Create a new MealHandler instance and add its panel

            MealInputUI mealHandler = new MealInputUI(userDatabaseInterface);
            setSize(350,200);
            getContentPane().add(mealHandler.getPanel());

            revalidate(); // Revalidate the content pane to reflect the changes
        });



        south.add(visualize);
        south.add(logExercise);
        south.add(logMeal);

        getContentPane().add(south);

        setSize(900, 600);
        pack();
        setVisible(true);
    }
}
