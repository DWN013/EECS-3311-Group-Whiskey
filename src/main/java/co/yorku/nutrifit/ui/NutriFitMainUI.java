package co.yorku.nutrifit.ui;

import co.yorku.nutrifit.ui.visualizerui.PieChartVisualizer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NutriFitMainUI extends JFrame {

    public NutriFitMainUI() {
        super("Home Page");

        JPanel south = new JPanel();

        JButton createProfileButton = new JButton("Create Profile");
        JButton visualize = new JButton("Visualize Data");
        JButton logExercise = new JButton("Log Exercise");
        JButton logMeal = new JButton("Log Meal");

        createProfileButton.addActionListener(e -> System.out.println("User clicked the create profile button"));
        visualize.addActionListener(e -> {

            PieChartVisualizer pieChartVisualizer = new PieChartVisualizer();
            pieChartVisualizer.setSize(900, 600);
            pieChartVisualizer.pack();
            pieChartVisualizer.setVisible(true);

        });
        logExercise.addActionListener(e -> System.out.println("User clicked the log exercise button"));
        logMeal.addActionListener(e -> System.out.println("User clicked the log meal button"));

        south.add(createProfileButton);
        south.add(visualize);
        south.add(logExercise);
        south.add(logMeal);

        getContentPane().add(south);
    }
}
