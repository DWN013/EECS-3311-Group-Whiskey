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
		
		String name = "Name: " + NutriFit.getInstance().getLoadedProfile().getName();    
		String age = "Age: " + NutriFit.getInstance().getLoadedProfile().getAge();
		String height = "Height: " +  String.format("%.2f", NutriFit.getInstance().getLoadedProfile().getHeight());
		String weight = "Weight: " + String.format("%.2f", NutriFit.getInstance().getLoadedProfile().getWeight());
		boolean gender = NutriFit.getInstance().getLoadedProfile().isMale();
		boolean unit = NutriFit.getInstance().getLoadedProfile().isMetric();
		String genderDisplay = "Gender: " + (gender == false ? "Female" : "Male");
		String unitDisplay = "Unit of Measurement: " + (unit == false ? "Imperial" : "Metric");
		if (unit == false) {
			height = height + " in";
			weight = weight + " lbs";
		}
		else {
			height = height + " cm";
			weight = weight + " Kg";
		}
		
		JLabel nameLabel = this.addLabel(name);
		JLabel ageLabel = this.addLabel(age);
		JLabel genderLabel = this.addLabel(genderDisplay);
		JLabel heightLabel = this.addLabel(height);
		JLabel weightLabel = this.addLabel(weight);
		JLabel unitLabel = this.addLabel(unitDisplay);
		
		addButton("Submit", event -> {
			//Action Listener of the Submit Button Code Here!
			int value = options.getSelectedIndex();
			
			if (value == 0) //Name
			{
				String newName = openTextInputDialog("Please Enter New Name:");
				if (newName != null)
				{
					NutriFit.getInstance().getLoadedProfile().setName(newName);
					nameLabel.setText("Name: " + newName);
					NutriFit.getInstance().editProfile(NutriFit.getInstance().getLoadedProfile());
					showMessageDialog("Profile Successfully Edited");
				}
			}
			else if (value == 1) //Age
			{
				String newAge = openTextInputDialog("Please Enter New Age (integer):");
				if (newAge != null)
				{
					try 
					{
						int numNewAge = Integer.valueOf(newAge);
						NutriFit.getInstance().getLoadedProfile().setAge(numNewAge);
						ageLabel.setText("Age: " + newAge);
						NutriFit.getInstance().editProfile(NutriFit.getInstance().getLoadedProfile());
						showMessageDialog("Profile Successfully Edited");
					}
					catch (NumberFormatException e)
					{
						showMessageDialog("Error: Please enter a valid integer value for Age");
					}
				}
			}
			else if (value == 2) //Gender
			{
				String newGender = openDropdownDialog("Choose Gender", "Please Choose Your Gender", 0, "Male", "Female");
				if (newGender != null)
				{
					NutriFit.getInstance().getLoadedProfile().setGender(newGender.equals("Male"));
					genderLabel.setText("Gender: " + newGender);
					NutriFit.getInstance().editProfile(NutriFit.getInstance().getLoadedProfile());
					showMessageDialog("Profile Successfully Edited");
				}
			}
			else if (value == 3) //Height
			{
				String newHeight = openTextInputDialog("Please Enter New Height (integer):");
				if (newHeight != null)
				{
					try {
						int numNewHeight = Integer.valueOf(newHeight);
						NutriFit.getInstance().getLoadedProfile().setHeight(numNewHeight);
						String userHeightUnit = (unit==true) ? " cm" : " in";
						heightLabel.setText("Height: " + newHeight + userHeightUnit);
						NutriFit.getInstance().editProfile(NutriFit.getInstance().getLoadedProfile());
						showMessageDialog("Profile Successfully Edited");
					}
					catch (NumberFormatException e)
					{
						showMessageDialog("Error: Please enter a valid integer value for Height");
					}
				}
			}
			else if (value == 4) //Weight
			{
				String newWeight = openTextInputDialog("Please Enter New Weight (integer):");
				if (newWeight != null)
				{
					try {
						int numNewWeight = Integer.valueOf(newWeight);
						NutriFit.getInstance().getLoadedProfile().setWeight(numNewWeight);
						String userWeightUnit = (NutriFit.getInstance().getLoadedProfile().isMetric()) ? " Kg" : " lbs";
						weightLabel.setText("Weight: " + newWeight + userWeightUnit);
						NutriFit.getInstance().editProfile(NutriFit.getInstance().getLoadedProfile());
						showMessageDialog("Profile Successfully Edited");
					}
					catch (NumberFormatException e)
					{
						showMessageDialog("Error: Please enter a valid integer value for Weight");
					}

				}
			}
			else if (value == 5) //Unit
			{
				String newUnit = openDropdownDialog("Unit of Measurement", "Enter New Unit Of Measurement", 0, "Metric", "Imperial");
				if (newUnit != null)
				{
					float newHeight = 0.0f;
					float newWeight = 0.0f;
					if (newUnit.equals("Metric") && !NutriFit.getInstance().getLoadedProfile().isMetric())
					{
						newHeight = NutriFit.getInstance().getLoadedProfile().getHeight() * 2.54f;
						newWeight = NutriFit.getInstance().getLoadedProfile().getWeight() / 2.2f;
						heightLabel.setText("Height: " + String.format("%.2f", newHeight) + " cm");
						weightLabel.setText("Weight: " + String.format("%.2f", newWeight) + " Kg");
						unitLabel.setText("Unit of Measurement: " + newUnit);
						NutriFit.getInstance().getLoadedProfile().setHeight(newHeight);
						NutriFit.getInstance().getLoadedProfile().setWeight(newWeight);
						NutriFit.getInstance().getLoadedProfile().setUnit(true);
					}
					else if (newUnit.equals("Imperial") && NutriFit.getInstance().getLoadedProfile().isMetric())
					{
						newHeight = NutriFit.getInstance().getLoadedProfile().getHeight() / 2.54f;
						newWeight = NutriFit.getInstance().getLoadedProfile().getWeight() * 2.2f;
						heightLabel.setText("Height: " + String.format("%.2f", newHeight) + " in");
						weightLabel.setText("Weight: " + String.format("%.2f", newWeight) + " lbs");
						unitLabel.setText("Unit of Measurement: " + newUnit);
						NutriFit.getInstance().getLoadedProfile().setHeight(newHeight);
						NutriFit.getInstance().getLoadedProfile().setWeight(newWeight);
						NutriFit.getInstance().getLoadedProfile().setUnit(false);						
					}
					NutriFit.getInstance().editProfile(NutriFit.getInstance().getLoadedProfile());
					showMessageDialog("Profile Successfully Edited");
				}
			}
			
		});
		this.addBackButton(NutriFitMainUI.getInstance());
		
		this.build();
	}
	
	public void showToUser()
	{
		setVisible(true);
	}

}
