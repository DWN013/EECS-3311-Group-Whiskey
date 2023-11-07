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

	public static SettingsUI getInstance() { //Singleton design pattern. Program holds one instance of the settings page - makes it such that we will not have to keep reloading this page when we want to show to user
		if (instance == null) { //If no instance was created
			instance = new SettingsUI(); //create an instance of settings UI
		}
		return instance; //return the instance of Settings UI 
	}


	public SettingsUI()
	{
		super("Settings", new GridLayout(9, 1));
		
		JComboBox<String> options = addComboBox("Name","Age","Gender","Height","Weight","Units of Measurement"); //Making a combo box at the top for selection of what user wants to edit 
		
		
		//Getting existing values from Loaded Profile - this will be used to show the user the existing values of their profile 
		String name = "Name: " + NutriFit.getInstance().getLoadedProfile().getName();    
		String age = "Age: " + NutriFit.getInstance().getLoadedProfile().getAge();
		String height = "Height: " +  String.format("%.2f", NutriFit.getInstance().getLoadedProfile().getHeight());
		String weight = "Weight: " + String.format("%.2f", NutriFit.getInstance().getLoadedProfile().getWeight());
		boolean gender = NutriFit.getInstance().getLoadedProfile().isMale();
		boolean unit = NutriFit.getInstance().getLoadedProfile().isMetric();
		
		String genderDisplay = "Gender: " + (gender == false ? "Female" : "Male"); //Figures out what to show the user based on what the boolean value for Gender is 
		String unitDisplay = "Unit of Measurement: " + (unit == false ? "Imperial" : "Metric"); //Will display what unit the user is using in a String (instead of boolean)
		
		//Will add according units to Height and Weight according to what units the user choose 
		if (unit == false) {
			height = height + " in";
			weight = weight + " lbs";
		}
		else {
			height = height + " cm";
			weight = weight + " Kg";
		}
		
		//Making Labels for the user to show them what thier current profile values are on the UI 
		JLabel nameLabel = this.addLabel(name);
		JLabel ageLabel = this.addLabel(age);
		JLabel genderLabel = this.addLabel(genderDisplay);
		JLabel heightLabel = this.addLabel(height);
		JLabel weightLabel = this.addLabel(weight);
		JLabel unitLabel = this.addLabel(unitDisplay);
		
		//Submit Button Action Listener for if the user chooses what value they want to edit and hits the submit button 
		addButton("Submit", event -> {
			//Action Listener of the Submit Button Code Here!
			int value = options.getSelectedIndex(); //Get the chosen value from the combo box
			
			if (value == 0) //Name
			{
				String newName = openTextInputDialog("Please Enter New Name:"); //Prompt user to enter a new name
				if (newName != null) //As long as the user did not hit "Cancel" or enter with no value
				{
					NutriFit.getInstance().getLoadedProfile().setName(newName); //Set the new name given by the user to the Profile loaded (stored instance in Nutrifit)
					nameLabel.setText("Name: " + newName); //Change the Label shown the the user to the new name 
					NutriFit.getInstance().editProfile(NutriFit.getInstance().getLoadedProfile()); //Using the Edit Profile method to change the Database 
					showMessageDialog("Profile Successfully Edited"); //Show the user a message that the profile was successfully loaded
				}
			}
			else if (value == 1) //Age
			{
				String newAge = openTextInputDialog("Please Enter New Age (integer):"); //Prompt user for new Age
				if (newAge != null)
				{
					try //To ensure the user is entering an integer value. If user enters non-number value, throw error
					{
						int numNewAge = Integer.valueOf(newAge); //Stirng to Integer conversion
						NutriFit.getInstance().getLoadedProfile().setAge(numNewAge); //Set new value for Age in Profile Instance
						ageLabel.setText("Age: " + newAge); //Change Label text to new age value for the user
						NutriFit.getInstance().editProfile(NutriFit.getInstance().getLoadedProfile()); //Change to Database with new Age value
						showMessageDialog("Profile Successfully Edited"); //Tell user edit was successful 
					}
					catch (NumberFormatException e) //Assuming user enters non-integer value
					{
						showMessageDialog("Error: Please enter a valid integer value for Age"); //Tell user in Message Prompt that they need to enter a valid integer value
					}
				}
			}
			else if (value == 2) //Gender
			{
				String newGender = openDropdownDialog("Choose Gender", "Please Choose Your Gender", 0, "Male", "Female"); //Combo Box choice for user to choose new Gender
				if (newGender != null)
				{
					NutriFit.getInstance().getLoadedProfile().setGender(newGender.equals("Male")); //The new gender will depend on which vlaue they choose. True = Male, False = Female 
					genderLabel.setText("Gender: " + newGender); //Show user new gender in Label
					NutriFit.getInstance().editProfile(NutriFit.getInstance().getLoadedProfile()); //Update database with new value 
					showMessageDialog("Profile Successfully Edited"); //Show user confirmation message 
				}
			}
			else if (value == 3) //Height
			{
				String newHeight = openTextInputDialog("Please Enter New Height (integer):"); //Prompt user to enter new Height as an integer value 
				if (newHeight != null)
				{
					try { //Will ensure user enters proper Integer value
						int numNewHeight = Integer.valueOf(newHeight); //String to Integer conversion 
						NutriFit.getInstance().getLoadedProfile().setHeight(numNewHeight); //Set new Height in Profile Instance
						String userHeightUnit = (unit==true) ? " cm" : " in"; //Figure out the proper units to add for the height according to user settings for unit of measurement 
						heightLabel.setText("Height: " + newHeight + userHeightUnit); //Show user new Height value in Label on the UI
						NutriFit.getInstance().editProfile(NutriFit.getInstance().getLoadedProfile()); //Edit Database with New height value
						showMessageDialog("Profile Successfully Edited"); //confirmation message shown to user
					}
					catch (NumberFormatException e) //if user inputs non-integer value 
					{
						showMessageDialog("Error: Please enter a valid integer value for Height"); //show User error message 
					}
				}
			}
			else if (value == 4) //Weight
			{
				String newWeight = openTextInputDialog("Please Enter New Weight (integer):"); //prompt user for new Weight value (integer) 
				if (newWeight != null)
				{
					try {
						int numNewWeight = Integer.valueOf(newWeight); //String to integer conversion
						NutriFit.getInstance().getLoadedProfile().setWeight(numNewWeight); //Set new Weight value in Profile Instance 
						String userWeightUnit = (NutriFit.getInstance().getLoadedProfile().isMetric()) ? " Kg" : " lbs"; //Based on unit of measurement, figure out which unit to add to the weight value (for the UI)
						weightLabel.setText("Weight: " + newWeight + userWeightUnit); //Change weight label for user to new value of weight
						NutriFit.getInstance().editProfile(NutriFit.getInstance().getLoadedProfile()); //Update database with new weight value
						showMessageDialog("Profile Successfully Edited"); //show confimration message to user
					}
					catch (NumberFormatException e) //f user inputs non-integer value 
					{
						showMessageDialog("Error: Please enter a valid integer value for Weight"); //Show error message to user 
					}

				}
			}
			else if (value == 5) //Unit
			{
				String newUnit = openDropdownDialog("Unit of Measurement", "Enter New Unit Of Measurement", 0, "Metric", "Imperial"); //drop down box selection for user to choose new unit
				if (newUnit != null)
				{
					//new Height and Weight values will be calculated since Unit of Measurement will change
					float newHeight = 0.0f;
					float newWeight = 0.0f;
					//As long as the user has chosen a value that is different form the current value already in the Profile instance
					//Example: Will not run if user chooses Metric, when Current unit is set to Metric
					if (newUnit.equals("Metric") && !NutriFit.getInstance().getLoadedProfile().isMetric()) //Changing unit from Imperial to Metric 
					{
						//New Height and Weight values calculated 
						newHeight = NutriFit.getInstance().getLoadedProfile().getHeight() * 2.54f;
						newWeight = NutriFit.getInstance().getLoadedProfile().getWeight() / 2.2f;
						//Changing Height and Weight labels according to new values calculated in unit change
						heightLabel.setText("Height: " + String.format("%.2f", newHeight) + " cm");
						weightLabel.setText("Weight: " + String.format("%.2f", newWeight) + " Kg");
						//Changing unit label according to new Unit chosen by user
						unitLabel.setText("Unit of Measurement: " + newUnit); //
						//Update profile will all new values for Unit, Height and Weight
						NutriFit.getInstance().getLoadedProfile().setHeight(newHeight);
						NutriFit.getInstance().getLoadedProfile().setWeight(newWeight);
						NutriFit.getInstance().getLoadedProfile().setUnit(true);
					}
					else if (newUnit.equals("Imperial") && NutriFit.getInstance().getLoadedProfile().isMetric()) //Changing unit from Metric to Imperial 
					{
						//New Height and Weigh values calculated 
						newHeight = NutriFit.getInstance().getLoadedProfile().getHeight() / 2.54f;
						newWeight = NutriFit.getInstance().getLoadedProfile().getWeight() * 2.2f;
						//Changing height and weight labels according to new values calculated in unit change 
						heightLabel.setText("Height: " + String.format("%.2f", newHeight) + " in");
						weightLabel.setText("Weight: " + String.format("%.2f", newWeight) + " lbs");
						//Changing unit label according to new unit user has chosen
						unitLabel.setText("Unit of Measurement: " + newUnit);
						//Update profile will all new values for Unit, Height and Weight
						NutriFit.getInstance().getLoadedProfile().setHeight(newHeight);
						NutriFit.getInstance().getLoadedProfile().setWeight(newWeight);
						NutriFit.getInstance().getLoadedProfile().setUnit(false);						
					}
					NutriFit.getInstance().editProfile(NutriFit.getInstance().getLoadedProfile()); //Update Database wit new Values
					showMessageDialog("Profile Successfully Edited"); //show confirmation of action to user 
				}
			}
			
		});
		this.addBackButton(NutriFitMainUI.getInstance()); //Back button shown to user if user wishes to go back to the Main Home Page of the program 
		
		this.build(); //Add all the components made to the Content Pane
	}
	
	public void showToUser() //Will show the created Content Pane to user if user wishes to see this page - makes all labels and buttons - entire Content pane visible 
	{
		setVisible(true); 
	}

}
