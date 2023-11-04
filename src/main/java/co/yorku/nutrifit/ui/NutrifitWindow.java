package co.yorku.nutrifit.ui;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.ui.impl.main.LogInOrSignUpPage;
import co.yorku.nutrifit.ui.impl.main.NutriFitMainUI;
import com.google.common.base.Preconditions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public abstract class NutrifitWindow extends JFrame {

    private JPanel mainJPanel;
    private NutrifitWindow previousWindow;

    public NutrifitWindow(String windowName, GridLayout mainJPanel) {
        super(windowName);
        Preconditions.checkNotNull(mainJPanel, "mainJPanel cannot be null");
        this.mainJPanel = new JPanel(mainJPanel);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (showConfirmationDialog("Are you sure you want to exit Nutrifit?") == JOptionPane.YES_OPTION){
                    NutriFit.getInstance().close();
                } else {
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (NutriFit.getInstance().isProfileLoaded()) {
                                NutriFitMainUI.getInstance().showWindow();
                            } else {
                                LogInOrSignUpPage.getInstance().showWindow();
                            }
                        }
                    }, 1);
                }
            }
        });

        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    public JLabel addLabel(String text) {
        JLabel label = new JLabel(text);
        this.mainJPanel.add(label);
        return label;
    }

    public JTextField addTextField(int columns) {
        JTextField jTextField = new JTextField(columns);
        this.mainJPanel.add(jTextField);
        return jTextField;
    }

    public JComboBox<Enum<?>> addComboBox(Enum<?>[] objects) {
        JComboBox<Enum<?>> comboBox = new JComboBox<>(objects);
        this.mainJPanel.add(comboBox);
        return comboBox;
    }

    public JComboBox<String> addComboBox(String... objects) {
        JComboBox<String> comboBox = new JComboBox<>(objects);
        this.mainJPanel.add(comboBox);
        return comboBox;
    }

    public JComboBox<String> addComboBox(List<String> objects) {
        return this.addComboBox(objects.toArray(new String[0]));
    }

    public JSpinner addSpinner() {
        JSpinner jSpinner = new JSpinner(new SpinnerNumberModel(
                1,
                1,
                1000000,
                1
        ));
        this.mainJPanel.add(jSpinner);
        return jSpinner;
    }

    public JButton addButton(String text, ActionListener onClick) {
        JButton button = new JButton(text);
        button.addActionListener(onClick);
        this.mainJPanel.add(button);
        return button;
    }

    public JRadioButton addRadioButton(String text, boolean selected, ActionListener onClick) {
        JRadioButton jRadioButton = new JRadioButton(text);
        jRadioButton.setSelected(selected);
        jRadioButton.addActionListener(onClick);
        this.mainJPanel.add(jRadioButton);
        return jRadioButton;
    }

    public void createButtonGroup(JRadioButton... buttons) {
        ButtonGroup buttonGroup = new ButtonGroup();
        for (JRadioButton button : buttons) {
            buttonGroup.add(button);
        }
    }

    public String openDropdownDialog(String windowName, String question, int defaultOption, String... options) {
        return (String) JOptionPane.showInputDialog(null, question,
                windowName, JOptionPane.QUESTION_MESSAGE, null, // Use
                // default
                // icon
                options, // Array of choices
                options[defaultOption]);
    }

    public String openTextInputDialog(String question) {
        return (String) JOptionPane.showInputDialog(null, question);
    }

    public void showMessageDialog(String text) {
        JOptionPane.showMessageDialog(null, text);
    }

    public int showConfirmationDialog(String text) {
        return JOptionPane.showConfirmDialog(null, text);
    }

    public void addComponent(JComponent jComponent) {
        this.mainJPanel.add(jComponent);
    }


    public void addBackButton(NutrifitWindow previousWindow, ActionListener preAction) {
        this.previousWindow = previousWindow;
        this.addButton("Back", event -> {
            if (preAction != null) {
                preAction.actionPerformed(event);
            }
            this.previousWindow.showWindow();
            this.hideWindow();
        });
    }

    public void addBackButton(NutrifitWindow previousWindow) {
        this.addBackButton(previousWindow, null);
    }

    public void build() {
        this.getContentPane().add(mainJPanel);
        this.pack();
    }

    public void showWindow() {
        this.setVisible(true);
    }

    public void hideWindow() {
        this.setVisible(false);
    }

    public JPanel getMainJPanel() {
        return mainJPanel;
    }
}
