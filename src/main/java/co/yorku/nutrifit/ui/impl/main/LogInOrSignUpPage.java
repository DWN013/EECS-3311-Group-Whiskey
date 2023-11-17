package co.yorku.nutrifit.ui.impl.main;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.ui.NutrifitWindow;

import java.awt.*;

/*
 * Main page shown to user - Prompt user to log in or sign up to/with their account
 * User must log in or sign up before they can access the application 
 */

public class LogInOrSignUpPage extends NutrifitWindow {

	private static LogInOrSignUpPage instance; //store instance of the created LogInOrSignUpPage

	//Implementation fo the Singleton Design Pattern - allows us to keep instance of this UI page
	public static LogInOrSignUpPage getInstance() {
		if (instance == null) {
			instance = new LogInOrSignUpPage();
		}
		return instance;
	}

	private LogInOrSignUpPage()
	{
		super("Login Or Signup", new GridLayout(1, 3));

		//Log in Button - if user has existing profile
		addButton("Login", event -> {
			this.hideWindow();
			new LoginPage(this).showWindow(); //Create new Log In UI page and show it
		});

		//Sign up button - if user wants to create new account
		addButton("Sign Up", event -> {
			this.hideWindow();
			new SignUpPage(this).showWindow(); //create new Sign Up UI Window and show it
		});

		//Exit button gives user option to exit the program 
		addButton("Exit", event -> NutriFit.getInstance().close());
		this.build();
	}

}
