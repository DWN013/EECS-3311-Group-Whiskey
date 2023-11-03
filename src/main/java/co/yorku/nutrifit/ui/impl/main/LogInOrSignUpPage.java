package co.yorku.nutrifit.ui.impl.main;

import co.yorku.nutrifit.ui.NutrifitWindow;

import java.awt.*;

public class LogInOrSignUpPage extends NutrifitWindow {

	private static LogInOrSignUpPage instance;

	public static LogInOrSignUpPage getInstance() {
		if (instance == null) {
			instance = new LogInOrSignUpPage();
		}
		return instance;
	}

	private LogInOrSignUpPage()
	{
		super("Login Or Signup", new GridLayout(1, 3));

		addButton("Login", event -> {
			this.hideWindow();
			new LoginPage(this).showWindow();
		});

		addButton("Sign Up", event -> {
			this.hideWindow();
			new SignUpPage(this).showWindow();
		});

		addButton("Exit", event -> System.exit(0));
		this.build();
	}

}
