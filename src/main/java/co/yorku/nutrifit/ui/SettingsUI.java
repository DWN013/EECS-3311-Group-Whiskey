package co.yorku.nutrifit.ui;

import co.yorku.nutrifit.NutriFit;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.List;

public class SettingsUI extends JFrame{
	
	public SettingsUI()
	{
		
		JButton back = new JButton("Back");
		JPanel firstPagePanel = new JPanel(new GridLayout(9,2));
		
		back.addActionListener(e -> { //if User to go bakc to main UI Page
			//NutriFit.getInstance().setLoadedProfile(NutriFit.getInstance().getUserDatabase().getProfile(userid));
            NutriFitMainUI.getInstance().showToUser();
            dispatchEvent(new WindowEvent(SettingsUI.this, WindowEvent.WINDOW_CLOSING));
		});
		
		
		
		//Add buttons to JPanel created
		firstPagePanel.add(back);
				
		//Add Panel to the ContentPane(screen)
		getContentPane().add(firstPagePanel);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);		
		setVisible(false); //Hide visibility until needed to show 

	}
	
	public void showToUser()
	{
		setVisible(true);
	}

}
