package co.yorku.nutrifit.ui.impl.main;

import co.yorku.nutrifit.profile.IProfile;
import co.yorku.nutrifit.profile.ProfileUpdater;
import co.yorku.nutrifit.ui.NutrifitWindow;
import co.yorku.nutrifit.NutriFit;

import javax.swing.*;
import java.awt.*;

/*
 * Settings page that will allow user to view their profile information as well as make changes to their information
 */

public class SettingsUI extends NutrifitWindow {


	private JComboBox<String> options;
	private JLabel nameLabel;
	private JLabel ageLabel;
	private JLabel genderLabel;
	private JLabel heightLabel;
	private JLabel weightLabel;
	private JLabel unitLabel;

	public SettingsUI()
	{
		super("Settings", new GridLayout(9, 1));

		this.options = addComboBox("Name","Age","Gender","Height","Weight","Units of Measurement"); //Making a combo box at the top for selection of what user wants to edit

		//Making Labels for the user to show them what their current profile values are on the UI
		this.nameLabel = this.addLabel("");
		this.ageLabel = this.addLabel("");
		this.genderLabel = this.addLabel("");
		this.heightLabel = this.addLabel("");
		this.weightLabel = this.addLabel("");
		this.unitLabel = this.addLabel("");

		this.updateLabels();
		
		//Submit Button Action Listener for if the user chooses what value they want to edit and hits the submit button 
		addButton("Submit", event -> {
			//Action Listener of the Submit Button Code Here!
			int value = options.getSelectedIndex(); //Get the chosen value from the combo box
			boolean updated = false;
			
			if (value == 0) //Name
			{
				String newName = openTextInputDialog("Please Enter New Name:"); //Prompt user to enter a new name
				updated = new ProfileUpdater().updateName(newName);
			}
			else if (value == 1) //Age
			{
				String newAge = openTextInputDialog("Please Enter New Age (integer):"); //Prompt user for new Age
				updated = new ProfileUpdater().updateAge(newAge);
			}
			else if (value == 2) //Gender
			{
				String newGender = openDropdownDialog("Choose Gender", "Please Choose Your Gender", 0, "Male", "Female"); //Combo Box choice for user to choose new Gender
				updated = new ProfileUpdater().updateGender(newGender);
			}
			else if (value == 3) //Height
			{
				String newHeight = openTextInputDialog("Please Enter New Height (integer):"); //Prompt user to enter new Height as an integer value 
				updated = new ProfileUpdater().updateHeight(newHeight);
			}
			else if (value == 4) //Weight
			{
				String newWeight = openTextInputDialog("Please Enter New Weight (integer):"); //prompt user for new Weight value (integer) 
				updated = new ProfileUpdater().updateWeight(newWeight);
			}
			else if (value == 5) //Unit
			{
				String newUnit = openDropdownDialog("Unit of Measurement", "Enter New Unit Of Measurement", 0, "Metric", "Imperial"); //drop down box selection for user to choose new unit
				updated = new ProfileUpdater().updateUnitOfMeasure(newUnit);
			}

			if (updated) {
				updateLabels();
				showMessageDialog("Profile Successfully Edited"); //show confirmation of action to user
			} else {
				showMessageDialog("Your input was not valid."); //show confirmation of action to user
			}
		});
		this.addBackButton(NutriFitMainUI.getInstance()); //Back button shown to user if user wishes to go back to the Main Home Page of the program 
		
		this.build(); //Add all the components made to the Content Pane
	}

	private void updateLabels() {
		//Getting existing values from Loaded Profile - this will be used to show the user the existing values of their profile
		String name = "Name: " + NutriFit.getInstance().getLoadedProfile().getName();
		String age = "Age: " + NutriFit.getInstance().getLoadedProfile().getAge();
		String height = "Height: " +  String.format("%.2f", NutriFit.getInstance().getLoadedProfile().getHeight());
		String weight = "Weight: " + String.format("%.2f", NutriFit.getInstance().getLoadedProfile().getWeight());
		boolean gender = NutriFit.getInstance().getLoadedProfile().isMale();
		boolean unit = NutriFit.getInstance().getLoadedProfile().isMetric();

		String genderDisplay = "Gender: " + (!gender ? "Female" : "Male"); //Figures out what to show the user based on what the boolean value for Gender is
		String unitDisplay = "Unit of Measurement: " + (!unit ? "Imperial" : "Metric"); //Will display what unit the user is using in a String (instead of boolean)

		//Will add according units to Height and Weight according to what units the user choose
		height = height + ((!unit) ? " in" : " cm");
		weight = weight + ((!unit) ? " lbs" : " Kg");

		//Making Labels for the user to show them what their current profile values are on the UI
		this.nameLabel.setText(name);
		this.ageLabel.setText(age);
		this.genderLabel.setText(genderDisplay);
		this.heightLabel.setText(height);
		this.weightLabel.setText(weight);
		this.unitLabel.setText(unitDisplay);
	}

}
