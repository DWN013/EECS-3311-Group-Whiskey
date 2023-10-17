package co.yorku.nutrifit.ui;

import co.yorku.nutrifit.database.userdata.UserDatabaseInterface;
import co.yorku.nutrifit.profile.Profile;
import co.yorku.nutrifit.ui.visualizerui.PieChartVisualizer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class NutriFitMainUI extends JFrame {

    public NutriFitMainUI(UserDatabaseInterface userDatabaseInterface, int userId) {
        super("Home Page");

        Profile profile = userDatabaseInterface.getProfile(userId);
        System.out.println("Loaded profile for " + profile.getId() + " -> " + profile.getName());

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
        logMeal.addActionListener(e -> System.out.println("User clicked the log meal button"));

        south.add(visualize);
        south.add(logExercise);
        south.add(logMeal);

        getContentPane().add(south);

        setSize(900, 600);
        pack();
        setVisible(true);
    }
}
