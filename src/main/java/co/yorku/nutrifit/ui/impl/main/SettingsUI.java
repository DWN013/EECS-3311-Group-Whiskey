package co.yorku.nutrifit.ui.impl.main;

import co.yorku.nutrifit.ui.NutrifitWindow;
import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.profile.IProfile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class SettingsUI extends NutrifitWindow {

	private static SettingsUI instance;

	public static SettingsUI getInstance() {
		if (instance == null) {
			instance = new SettingsUI();
		}
		return instance;
	}


	public SettingsUI()
	{
		super("Settings", new GridLayout(9, 1));
		
		JComboBox<String> options = addComboBox("Name","Age","Gender","Height","Weight","Units of Measurement");
		
		NutriFit nf = NutriFit.getInstance();
		IProfile profileData = nf.getLoadedProfile();
		
		String name = "Name: " + profileData.getName();
		String age = "Age: " + profileData.getAge();
		String height = "Height: " + profileData.getHeight();
		String weight = "Weight: " + profileData.getWeight();
		boolean gender = profileData.isMale();
		boolean unit = profileData.isMetric();
		String g = "Gender: " + (gender == false ? "Female" : "Male");
		String u = "Unit of Measurement: " + (unit == false ? "Imperial" : "Metric");
		if (unit == false) {
			height = height + " in";
			weight = weight + " lbs";
		}
		else {
			height = height + " cm";
			weight = weight + " Kg";
		}
		
		this.addLabel(name);
		this.addLabel(age);
		this.addLabel(g);
		this.addLabel(height);
		this.addLabel(weight);
		this.addLabel(u);
		
		addButton("Submit", event -> {
			//Action Listener of the Submit Button Code Here!
			//String input = JOptionPane.showInputDialog("Change Age");
			/*String[] choices = { "A", "B", "C", "D", "E", "F" };
		    String input = (String) JOptionPane.showInputDialog(null, "Choose now...",
		        "The Choice of a Lifetime", JOptionPane.QUESTION_MESSAGE, null, // Use
		                                                                        // default
		                                                                        // icon
		        choices, // Array of choices
		        choices[1]); // Initial choice
		    System.out.println(input);*/
			//System.out.println(input);
		});
		this.addBackButton(NutriFitMainUI.getInstance());
		
		this.build();
	}
	
	public void showToUser()
	{
		setVisible(true);
	}

}
