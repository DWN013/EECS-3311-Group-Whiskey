package co.yorku.nutrifit.meal;

import javax.swing.*;

public interface Meal {
    JPanel getPanel();
    JPanel createPanel();
    void updatePanel(JPanel mealPanel, JLabel dateLabel, JTextField dateField, JLabel mealTypeLabel, JComboBox<String> mealTypeDropdown, JLabel ingredientsLabel, JTextField ingredientsField, JLabel quantityLabel, JComboBox<String> quantityDropdown);
}
