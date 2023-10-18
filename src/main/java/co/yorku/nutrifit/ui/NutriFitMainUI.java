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
        JButton logMeal = new JButton("Log Meal");

        visualize.addActionListener(e -> {

            PieChartVisualizer pieChartVisualizer = new PieChartVisualizer();
            pieChartVisualizer.setSize(1300, 600);
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

            new MealInputUI(userDatabaseInterface);

            revalidate(); // Revalidate the content pane to reflect the changes
        });



        south.add(visualize);
        south.add(logExercise);
        south.add(logMeal);

        getContentPane().add(south);

        pack();
        setVisible(true);
    }
}
