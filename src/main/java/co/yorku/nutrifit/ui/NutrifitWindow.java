package co.yorku.nutrifit.ui;

import com.google.common.base.Preconditions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public abstract class NutrifitWindow extends JFrame {

    private JPanel layout;
    private NutrifitWindow previousWindow;

    public NutrifitWindow(String windowName, GridLayout layout) {
        super(windowName);
        Preconditions.checkNotNull(layout, "Layout cannot be null");
        this.layout = new JPanel(layout);

        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    public JLabel addLabel(String text, int i) {
        JLabel label = new JLabel(text);
        this.layout.add(label);
        return label;
    }

    public JTextField addTextField(int columns, int i) {
        JTextField jTextField = new JTextField(columns);
        this.layout.add(jTextField);
        return jTextField;
    }

    public JComboBox<Enum<?>> addComboBox(Enum<?>[] objects, int i) {
        JComboBox<Enum<?>> comboBox = new JComboBox<>(objects);
        this.layout.add(comboBox);
        return comboBox;
    }

    public JComboBox<String> addComboBox(String... objects) {
        JComboBox<String> comboBox = new JComboBox<>(objects);
        this.layout.add(comboBox);
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
        this.layout.add(jSpinner);
        return jSpinner;
    }

    public JButton addButton(String text, ActionListener onClick, int i) {
        JButton button = new JButton(text);
        button.addActionListener(onClick);
        this.layout.add(button);
        return button;
    }

    public void addComponent(JComponent jComponent, int i) {
        this.layout.add(jComponent);
    }

    public void addBackButton(NutrifitWindow previousWindow) {
        this.previousWindow = previousWindow;
        this.addButton("Back", event -> {
            this.previousWindow.showWindow();
            this.hideWindow();
        },1);
    }

    public void build() {
        this.getContentPane().add(layout);
        this.pack();
    }

    public void showWindow() {
        this.setVisible(true);
    }

    public void hideWindow() {
        this.setVisible(false);
    }

}
