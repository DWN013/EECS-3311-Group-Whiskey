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
	private NutriFit nf;
	private IProfile userProfile;

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
		
		nf = NutriFit.getInstance();
		userProfile = nf.getLoadedProfile();
		
		String name = "Name: " + userProfile.getName();
		String age = "Age: " + userProfile.getAge();
		String height = "Height: " +  String.format("%.2f", userProfile.getHeight());
		String weight = "Weight: " + String.format("%.2f", userProfile.getWeight());
		boolean gender = userProfile.isMale();
		boolean unit = userProfile.isMetric();
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
		
		JLabel nameLabel = this.addLabel(name);
		JLabel ageLabel = this.addLabel(age);
		JLabel genderLabel = this.addLabel(g);
		JLabel heightLabel = this.addLabel(height);
		JLabel weightLabel = this.addLabel(weight);
		JLabel unitLabel = this.addLabel(u);
		
		addButton("Submit", event -> {
			//Action Listener of the Submit Button Code Here!
			//System.out.println("LETS GO!");
			int value = options.getSelectedIndex();
			
			if (value == 0) //Name
			{
				String newName = openTextInputDialog("Please Enter New Name:");
				if (newName != null)
				{
					userProfile.setName(newName);
					nameLabel.setText("Name: " + newName);
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
						//System.out.println(numNewAge);
						userProfile.setAge(numNewAge);
						ageLabel.setText("Age: " + newAge);
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
					boolean temp = userProfile.isMale();
					if (newGender.equals("Male"))
					{
						if (temp == false)
						{
							userProfile.setGender(true);
						}
					}
					else
					{
						if (temp == true)
						{
							userProfile.setGender(false);
						}
					}
					genderLabel.setText("Gender: " + newGender);
				}
			}
			else if (value == 3) //Height
			{
				String newHeight = openTextInputDialog("Please Enter New Height (integer):");
				if (newHeight != null)
				{
					try {
						int numNewHeight = Integer.valueOf(newHeight);
						//System.out.println(numNewAge);
						userProfile.setHeight(numNewHeight);
						String t = (unit==true) ? " cm" : " in";
						heightLabel.setText("Height: " + newHeight + t);
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
						//System.out.println(numNewAge);
						userProfile.setWeight(numNewWeight);
						String t = (unit==true) ? " Kg" : " lbs";
						weightLabel.setText("Weight: " + newWeight + t);
					}
					catch (NumberFormatException e)
					{
						showMessageDialog("Error: Please enter a valid integer value for Weight");
					}

				}
			}
			else if (value == 5) //Unit
			{
				//System.out.println("Chosen to change Unit!");
				//System.out.println(userProfile.getHeight() + "  " + userProfile.getWeight());
				String newUnit = openDropdownDialog("Unit of Measurement", "Enter New Unit Of Measurement", 0, "Metric", "Imperial");
				if (newUnit != null)
				{
					float tempHeight = 0.0f;
					float tempWeight = 0.0f;
					if (newUnit.equals("Metric"))
					{
						//System.out.println("Want to change to Metric");
						if (userProfile.isMetric() == false) //changing from imperial to Metric 
						{
							tempHeight = userProfile.getHeight() * 2.54f;
							tempWeight = userProfile.getWeight() / 2.2f;
							heightLabel.setText("Height: " + String.format("%.2f", tempHeight) + " cm");
							weightLabel.setText("Weight: " + String.format("%.2f", tempWeight) + " Kg");
							unitLabel.setText("Unit of Measurement: " + newUnit);
							userProfile.setHeight(tempHeight);
							userProfile.setWeight(tempWeight);
							userProfile.setUnit(true);
						}
					}
					else
					{
						//System.out.println("Want to change to imperial");
						if (userProfile.isMetric() == true) //changing from metric to imperial 
						{
							//.out.println(userProfile.isMetric());
							tempHeight = userProfile.getHeight() / 2.54f;
							tempWeight = userProfile.getWeight() * 2.2f;
							heightLabel.setText("Height: " + String.format("%.2f", tempHeight) + " in");
							weightLabel.setText("Weight: " + String.format("%.2f", tempWeight) + " lbs");
							unitLabel.setText("Unit of Measurement: " + newUnit);
							userProfile.setHeight(tempHeight);
							userProfile.setWeight(tempWeight);
							userProfile.setUnit(false);						
						}
					}
				}
			}
			//System.out.println("Now edit the Profile!");
			nf.editProfile(userProfile);
			
		});
		this.addBackButton(NutriFitMainUI.getInstance());
		
		this.build();
	}
	
	public void showToUser()
	{
		setVisible(true);
	}

}
