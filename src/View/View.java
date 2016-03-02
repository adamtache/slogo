package View;

import java.io.IOException;

import Controller.SLogoException;

public interface View {
	
	public String getCommand();

	public Visualizer getVisualizer();
	
	public void updateDisplayData();
	
	public void updateCommandHistory();
}
