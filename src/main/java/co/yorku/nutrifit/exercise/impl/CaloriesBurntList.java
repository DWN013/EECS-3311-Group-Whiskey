package co.yorku.nutrifit.exercise.impl;

import co.yorku.nutrifit.object.ActivityType;
import co.yorku.nutrifit.object.Intensity;

/*
 * Calorie Data File for Exercises
 * List that has stored the amount of calories that the user will burn doing a specific exercise, at some intensity or a person of a specific weight class (3-dimensional array)
 */

/*
 * Calorie List exercises:
 * Array Row 0 = Zumba
 * Array Row 1 = Walking
 * Array Row 2 = Jump Rope
 * Array Row 3 = Ice Skating
 * Array Row 4 = Jogging
 * Array Row 5 = Basketball
 * Array Row 6 = Running
 * Array Row 7 = Swimming
 * Array Row 8 = Biking
 * Array Row 9 = Boxing
 * */

public class CaloriesBurntList {
	
	private float [] [] [] activityCalorieList = { 
			//For the weight aspect - 0=130lbs, 1=155lbs, 2=180lbs, 3=205+lbs
			{{177,211,245,279},{236,317,368,419},{266,317,368,419},{325,387,449,512},{354,422,490,558}}, 
			{{118,141,163,186}, {148,176,204,233}, {177,211,245,279}, {195,232,270,307}, {224,267,311,354}},
			{{354,423,493,560},{472,563,654,745},{590,704,817,931},{708,844,981,1117},{826,980,1140,1302}}, 
			{{245,287,329,372},{325,387,449,512},{413,493,572,651},{531,633,735,838},{649,770,890,1025}}, 
			{{295,352,409,465},{354,422,490,588},{372,443,515,586},{472,563,654,745},{531,633,735,838}}, 
			{{237,285,319,367},{266,317,368,419},{354,422,490,558},{413,492,572,651},{472,563,654,745}}, 
			{{590,704,817,931},{679,809,940,1070},{797,950,1103,1256},{885,1056,1226,1396},{944,1126,1308,1489}}, 
			{{354,422,490,558},{413,493,572,651},{472,563,654,745},{590,704,817,931},{649,774,899,1024}}, 
			{{236,281,327,372},{354,422,490,558},{472,563,654,745},{590,704,817,931},{708,844,981,1117}}, 
			{{264,348,405,479},{304,382,440,518},{354,422,490,558},{442,527,612,698},{531,633,735,838}} 
			};
	
	public CaloriesBurntList() {}
	
	//Gets calories burned for an exercise
	public float getCaloriesBurnedForActivityType(ActivityType activityType, Intensity intensity, float weight) //Assuming weight = pounds
	{
		float value = 0;
		if (weight <= 130)
		{
			value = activityCalorieList[activityType.ordinal()][intensity.ordinal()][0];
		}
		else if (weight <= 155)
		{
			value = activityCalorieList[activityType.ordinal()][intensity.ordinal()][1];
		}
		else if (weight <= 180)
		{
			value = activityCalorieList[activityType.ordinal()][intensity.ordinal()][2];
		}
		else
		{
			value = activityCalorieList[activityType.ordinal()][intensity.ordinal()][3];
		}
		//because these values are calories burnt for an hour of doing this exercise,
		//we divide by the number of seconds in an hour to get calories burnt per second
		value = value / 3600.00f;
		return value;
	}

}
