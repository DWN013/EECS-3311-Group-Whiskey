package co.yorku.nutrifit.exercise.calculators;

import co.yorku.nutrifit.profile.IProfile;

//Metric = Kg (weight), centi (height), milli et.
//Imperial = Feet, Inches and pounds
// 1 Kg = 2.2 pounds    weight
// 1 inch = 2.54 centi  height


public class BMRCalculator {
	
	
	public BMRCalculator ()
	{
		
		
	}
	
	public int getBMR(IProfile p)
	{
		double bmr = 0.0;
		boolean gender = p.isMale();
		boolean isMetric = p.isMetric();
		double weight = p.getWeight();
		//NEED TO BE RE IMPLEMENTED WHEN HEIGHT BECOMES AN INTEGER
		double height = p.getHeight();
		int age = p.getAge();
		if (isMetric == false)
		{
			weight = weight / 2.2;
			height = height * 2.54;
		}
		bmr = (9.99 * weight) + (6.25*height) - (4.92*age);
		if (gender == true) //isMale
		{
			bmr = bmr + 5;
		}
		else
		{
			bmr = bmr - 161;
		}
		return (int)bmr;
	}


}
