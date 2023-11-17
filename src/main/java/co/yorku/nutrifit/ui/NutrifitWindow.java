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

/*
 * This class allows us to easily create JPanel elements - we use this class to create our UI elements easily and efficiently
 */

public abstract class NutrifitWindow extends JFrame {

    private JPanel mainJPanel;
    private NutrifitWindow previousWindow;

    public NutrifitWindow(String windowName, GridLayout mainJPanel) {
        super(windowName);
        //Edge case checking
        Preconditions.checkNotNull(mainJPanel, "mainJPanel cannot be null");
        
        //Add the given window to the main page
        this.mainJPanel = new JPanel(mainJPanel);

        //method to add window listener - incase the user wants to exit the window by clicking the "X" at the top right of the window
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (showConfirmationDialog("Are you sure you want to exit Nutrifit?") == JOptionPane.YES_OPTION){ //prompt for a confirmation
                    NutriFit.getInstance().close(); //if yes, then close the window
                } 
                else { //If not, then take the user back to the main menu page
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

    //Method allows us to create a JLabel in the UI with given text
    public JLabel addLabel(String text) {
        JLabel label = new JLabel(text);
        this.mainJPanel.add(label);
        return label;
    }

    //Method allows us to create Text Field in the UI with a given amount of columns
    public JTextField addTextField(int columns) {
        JTextField jTextField = new JTextField(columns);
        this.mainJPanel.add(jTextField);
        return jTextField;
    }

    //Method allows us to create Combo Box in the UI with given Enum objects 
    public JComboBox<Enum<?>> addComboBox(Enum<?>[] objects) {
        JComboBox<Enum<?>> comboBox = new JComboBox<>(objects);
        this.mainJPanel.add(comboBox);
        return comboBox;
    }
    
    //Method allows us to create Combo Box in the UI with given strings
    public JComboBox<String> addComboBox(String... objects) {
        JComboBox<String> comboBox = new JComboBox<>(objects);
        this.mainJPanel.add(comboBox);
        return comboBox;
    }

  //Method allows us to create Combo Box in the UI given an Array List of String objects 
    public JComboBox<String> addComboBox(List<String> objects) {
        return this.addComboBox(objects.toArray(new String[0]));
    }

  //Method allows us to create Spinner in the UI (easier to prompt user to enter integer value)
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

  //Method allows us to create button in the UI given text for button name and will allow us to initialize the action listener for the button as well
    public JButton addButton(String text, ActionListener onClick) {
        JButton button = new JButton(text);
        button.addActionListener(onClick);
        this.mainJPanel.add(button);
        return button;
    }
    
  //Method allows us to create Radio Button in the UI to prompt user for choice 
    public JRadioButton addRadioButton(String text, boolean selected, ActionListener onClick) {
        JRadioButton jRadioButton = new JRadioButton(text);
        jRadioButton.setSelected(selected);
        jRadioButton.addActionListener(onClick);
        this.mainJPanel.add(jRadioButton);
        return jRadioButton;
    }

  //Method allows us to create button group in the UI - we can display many radio buttons in a group
    public void createButtonGroup(JRadioButton... buttons) {
        ButtonGroup buttonGroup = new ButtonGroup();
        for (JRadioButton button : buttons) {
            buttonGroup.add(button);
        }
    }

  //Method allows us to create Dropdown Dialog - similar to combo box
    public String openDropdownDialog(String windowName, String question, int defaultOption, String... options) {
        return (String) JOptionPane.showInputDialog(null, question,
                windowName, JOptionPane.QUESTION_MESSAGE, null, // Use
                // default
                // icon
                options, // Array of choices
                options[defaultOption]);
    }

    //Method allows us to prompt user for Text Input
    public String openTextInputDialog(String question) {
        return (String) JOptionPane.showInputDialog(null, question);
    }

  //Method allows us to prompt user with message
    public void showMessageDialog(String text) {
        JOptionPane.showMessageDialog(null, text);
    }

    //Method allows us to prompt user to confirm their choice
    public int showConfirmationDialog(String text) {
        return JOptionPane.showConfirmDialog(null, text);
    }

  //Method allows us to add a JComponent to the Panel
    public void addComponent(JComponent jComponent) {
        this.mainJPanel.add(jComponent);
    }

    //Method allows us to add a "Back" button in the UI - so we can go back to previous windows in the program (with action listener)
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

    //Method allows us to add a "Back" button in the UI - so we can go back to previous windows in the program
    public void addBackButton(NutrifitWindow previousWindow) {
        this.addBackButton(previousWindow, null);
    }

    //Method will add the panel to the content pane and the page will be built 
    public void build() {
        this.getContentPane().add(mainJPanel);
        this.pack();
    }
    
    //Show the content pane - the created page to the user
    public void showWindow() {
        this.setVisible(true);
    }

    //Hide all the elements in the page - ultimately hiding a created window/page
    public void hideWindow() {
        this.setVisible(false);
    }

    //Get the main Panel 
    public JPanel getMainJPanel() {
        return mainJPanel;
    }
}
