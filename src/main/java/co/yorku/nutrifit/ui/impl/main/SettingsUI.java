package co.yorku.nutrifit.ui.impl.main;

import co.yorku.nutrifit.ui.NutrifitWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class SettingsUI extends NutrifitWindow {

	private static SettingsUI instance;

	public static SettingsUI getInstance() {
		if (instance == null) {
			instance = new SettingsUI();
		}
		return instance;
	}


	public SettingsUI()
	{
		super("Settings", new GridLayout(1, 3));

		this.addBackButton(NutriFitMainUI.getInstance());
		this.build();
	}
	
	public void showToUser()
	{
		setVisible(true);
	}

}
