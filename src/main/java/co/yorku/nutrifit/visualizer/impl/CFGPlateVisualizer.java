package co.yorku.nutrifit.visualizer.impl;

import co.yorku.nutrifit.visualizer.IVisualizer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class CFGPlateVisualizer extends IVisualizer {



    public CFGPlateVisualizer(Dataset dataset) {
        super(dataset);

    }



    @Override
    public String getChartName() {
        return "CFG Plate";
    }

    @Override
    public String getBarGraphCategoryAxisLabel() {
        return "Food Categories";
    }

    @Override
    public String getBarGraphValueAxisLabel() {
        return "Percentage";
    }

    @Override
    public boolean isBargraphShownInPercentage() {
        return true;
    }

    @Override
    public DefaultCategoryDataset buildBargraphDataset(Date fromDate, Date toDate) {
        ((DefaultCategoryDataset) getDataset()).setValue((double)48/100, "Vegetables and Fruits", "Vegetables and Vegetable Products");
        ((DefaultCategoryDataset) getDataset()).setValue((double)48/100, "Vegetables and Fruits", "Fruits and Fruit Juices");

        ((DefaultCategoryDataset) getDataset()).setValue((double)24/100, "Whole Grain Foods", "Breakfast Cereals");
        ((DefaultCategoryDataset) getDataset()).setValue((double)24/100, "Whole Grain Foods", "Cereals, Grains, and Pasta");
        ((DefaultCategoryDataset) getDataset()).setValue((double)24/100, "Whole Grain Foods", "Baked Products");

        ((DefaultCategoryDataset) getDataset()).setValue((double)23/100, "Proteins", "Dairy and Egg Products");
        ((DefaultCategoryDataset) getDataset()).setValue((double)23/100, "Proteins", "Poultry Products");
        ((DefaultCategoryDataset) getDataset()).setValue((double)23/100, "Proteins", "Pork Products");
        ((DefaultCategoryDataset) getDataset()).setValue((double)23/100, "Proteins", "Nuts and Seeds");
        ((DefaultCategoryDataset) getDataset()).setValue((double)23/100, "Proteins", "Beef Products");
        ((DefaultCategoryDataset) getDataset()).setValue((double)23/100, "Proteins", "Finfish and Shellfish Products");
        ((DefaultCategoryDataset) getDataset()).setValue((double)23/100, "Proteins", "Legumes and Legume Products");
        ((DefaultCategoryDataset) getDataset()).setValue((double)23/100, "Proteins", "Lamb, Veal, and Game");

        ((DefaultCategoryDataset) getDataset()).setValue((double)5/100, "Others", "Spices and Herbs");
        ((DefaultCategoryDataset) getDataset()).setValue((double)5/100, "Others", "Babyfoods");
        ((DefaultCategoryDataset) getDataset()).setValue((double)5/100, "Others", "Fats and Oils");
        ((DefaultCategoryDataset) getDataset()).setValue((double)5/100, "Others", "Soups, Sauces, and Gravies");
        ((DefaultCategoryDataset) getDataset()).setValue((double)5/100, "Others", "Sausages and Luncheon Meats");
        ((DefaultCategoryDataset) getDataset()).setValue((double)5/100, "Others", "Beverages");
        ((DefaultCategoryDataset) getDataset()).setValue((double)5/100, "Others", "Mixed Dishes");
        ((DefaultCategoryDataset) getDataset()).setValue((double)5/100, "Others", "Fast Foods");
        ((DefaultCategoryDataset) getDataset()).setValue((double)5/100, "Others", "Sweets");




        return ((DefaultCategoryDataset) getDataset());
    }

    @Override
    public DefaultPieDataset<String> buildPiechartDataset(boolean expand, Date fromDate, Date toDate) {
        ((DefaultPieDataset<String>) getDataset()).setValue("Vegetables and Fruits (48%)", 48.0);
        ((DefaultPieDataset<String>) getDataset()).setValue("Proteins (23%)", 23.0);
        ((DefaultPieDataset<String>) getDataset()).setValue("Whole Grain Foods (24%)", 24.0);
        ((DefaultPieDataset<String>) getDataset()).setValue("Others (5%)", 5.0);

        return ((DefaultPieDataset<String>) getDataset());
    }

    @Override
    public void onDateRangeUpdate(String type, Date newFromDate, Date newToDate) {
        if (!type.equals(this.getChartName())) return;

        if (getDataset() instanceof DefaultCategoryDataset) {
            ((DefaultCategoryDataset) getDataset()).clear();
            this.buildBargraphDataset(newFromDate, newToDate);
        } else if (getDataset() instanceof DefaultPieDataset) {
            ((DefaultPieDataset<String>) getDataset()).clear();
            this.buildPiechartDataset(false, newFromDate, newToDate);
        }
    }


}
