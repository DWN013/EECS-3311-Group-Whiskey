package co.yorku.nutrifit.profile;

import co.yorku.nutrifit.NutriFit;

public class ProfileUpdater {

    public boolean updateName(String newName) {

        if (newName == null) { // Check if null
            return false;
        }
        NutriFit.getInstance().getLoadedProfile().setName(newName); //Set the new name given by the user to the Profile loaded (stored instance in Nutrifit)
        return this.updateProfile(); // Return the result of updateProfile()
    }
    public boolean updateAge(String newAge) {

        if (newAge == null) { // Check if null
            return false;
        }

        int age = -1;

        // Attempt to parse
        try {
            age = Integer.parseInt(newAge);
        } catch (Exception e) {
            return false;
        }

        NutriFit.getInstance().getLoadedProfile().setAge(age); // Set the age of the profile
        return this.updateProfile(); // Return the result of updateProfile()
    }
    public boolean updateGender(String newGender) {

        if (newGender == null) { // Check if null
            return false;
        }

        NutriFit.getInstance().getLoadedProfile().setGender(newGender.equals("Male")); //The new gender will depend on which value they choose. True = Male, False = Female
        return this.updateProfile(); // Return the result of updateProfile()
    }
    public boolean updateHeight(String newHeight) {

        if (newHeight == null) { // Check if null
            return false;
        }

        int height = -1;

        // Attempt to parse
        try {
            height = Integer.parseInt(newHeight);
        } catch (Exception e) {
            return false;
        }

        NutriFit.getInstance().getLoadedProfile().setHeight(height);
        return this.updateProfile(); // Return the result of updateProfile()
    }
    public boolean updateWeight(String newWeight) {

        if (newWeight == null) { // Check if null
            return false;
        }

        int weight = -1;

        // Attempt to parse
        try {
            weight = Integer.parseInt(newWeight);
        } catch (Exception e) {
            return false;
        }

        NutriFit.getInstance().getLoadedProfile().setWeight(weight);
        return this.updateProfile(); // Return the result of updateProfile()
    }
    public boolean updateUnitOfMeasure(String newUnit) {

        if (newUnit == null) { // Check if null
            return false;
        }

        if (newUnit.equals("Metric") && !NutriFit.getInstance().getLoadedProfile().isMetric()) //Changing unit from Imperial to Metric
        {
            //Update profile will all new values for Unit, Height and Weight
            NutriFit.getInstance().getLoadedProfile().setHeight(NutriFit.getInstance().getLoadedProfile().getHeight() * 2.54f);
            NutriFit.getInstance().getLoadedProfile().setWeight(NutriFit.getInstance().getLoadedProfile().getWeight() / 2.2f);
            NutriFit.getInstance().getLoadedProfile().setUnit(true);
        }
        else if (newUnit.equals("Imperial") && NutriFit.getInstance().getLoadedProfile().isMetric()) //Changing unit from Metric to Imperial
        {
            //Changing height and weight labels according to new values calculated in unit change
            //Update profile will all new values for Unit, Height and Weight
            NutriFit.getInstance().getLoadedProfile().setHeight(NutriFit.getInstance().getLoadedProfile().getHeight() / 2.54f);
            NutriFit.getInstance().getLoadedProfile().setWeight(NutriFit.getInstance().getLoadedProfile().getWeight() * 2.2f);
            NutriFit.getInstance().getLoadedProfile().setUnit(false);
        } else {
            return true;
        }

        return this.updateProfile(); // Return the result of updateProfile()
    }
    public boolean updateProfile() {
        // This method updates the database and returns if it was successful or not
        return NutriFit.getInstance().getUserDatabase().updateProfile(NutriFit.getInstance().getLoadedProfile());
    }
}
