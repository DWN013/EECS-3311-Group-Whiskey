package co.yorku.nutrifit.ui.visualizerui;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.logs.LogIterator;
import co.yorku.nutrifit.logs.impl.Exercise;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/*

This class is temp to show our one visualization working

 */

public class BarChartVisualizer extends JFrame {

    public BarChartVisualizer() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Temp code, just grab all of the user's logs
        Date fromDate = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(99999));
        Date toDate = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(99999));

        Map<String, Integer> caloriesBurnedEachDay = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        LogIterator logs = NutriFit.getInstance().getUserDatabase().getUserExerciseLogs(NutriFit.getInstance().getLoadedProfile().getId(), fromDate, toDate);
        logs.sortByDateAscending();

        System.out.println(logs.getTotalEntries());

        while (logs.hasNext()) {
            Exercise userExerciseLog = (Exercise) logs.getNext();

            Date exerciseDay = userExerciseLog.getDate();
            calendar.setTime(exerciseDay);

            String dayKey = calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR);

            caloriesBurnedEachDay.put(dayKey, caloriesBurnedEachDay.getOrDefault(dayKey, 0) + userExerciseLog.getTotalCaloriesBurned());

        }

        for (Map.Entry<String, Integer> stringIntegerEntry : caloriesBurnedEachDay.entrySet().stream().sorted(Comparator.comparingLong(l -> new Date(l.getKey()).getTime())).collect(Collectors.toList())) {
            dataset.setValue(stringIntegerEntry.getValue(), "Calories Burned", stringIntegerEntry.getKey());
        }


        JFreeChart barChart = ChartFactory.createBarChart(
                "Calories Burned Each Day",
                "Day",
                "Calories Burned",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(400, 300));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);

        getContentPane().add(chartPanel);

    }

}
