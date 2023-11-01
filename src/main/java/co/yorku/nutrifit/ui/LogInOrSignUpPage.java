package co.yorku.nutrifit.ui;


import co.yorku.nutrifit.ui.ProfileSelectionUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.List;

public class LogInOrSignUpPage extends JFrame{
	
	public LogInOrSignUpPage ()
	{
		//Creating the buttons for this page
		JButton logIn = new JButton("Log In");
		JButton signUp = new JButton("Sign Up");
		JButton exit = new JButton("Exit");
		
		//Add created buttons to a JPanel
		JPanel firstPagePanel = new JPanel(new GridLayout(3,1));
		
		//Create a new Instance of ProfileSelectionUI which we will create instance for. This will create next screen based on users choice
		ProfileSelectionUI ps = new ProfileSelectionUI().getInstance();
		
		//Action Listeners
		
		logIn.addActionListener(e -> { //if User wants to log in with existing profile
			
			//ProfileSelectionUI ps = new ProfileSelectionUI().getInstance();
			ps.logIn(); //create the log in page in the ProfileSelectionUIClass
			ps.showToUser(); //make visible to user
			dispatchEvent(new WindowEvent(LogInOrSignUpPage.this, WindowEvent.WINDOW_CLOSING)); //close existing window
			
		});
		
		signUp.addActionListener(e -> {
			
			ps.signUp();//create the sign up page in the ProfileSelectionUIClass
			ps.showToUser(); //mae visible to user
			dispatchEvent(new WindowEvent(LogInOrSignUpPage.this, WindowEvent.WINDOW_CLOSING)); //close existing window 
			
		});
		
		exit.addActionListener(e -> { //if User wants to exit program
			System.exit(0);
		});
		
		
		//Add buttons to JPanel created
		firstPagePanel.add(logIn);
		firstPagePanel.add(signUp);
		firstPagePanel.add(exit);
		
		//Add Panel to the ContentPane(screen)
		getContentPane().add(firstPagePanel);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);		
		setVisible(false); //Hide visibility until needed to show 
	}
	
	public void showToUser() //This method will show to the user the Content Pane we created with the Log In/ sign Up buttons 
	{
		setVisible(true);
	}

}
