package view;

import java.io.IOException;
import java.util.Observer;

import exception.SLogoException;
import model.SLogoWorkspace;

/**
 * View interface - A contract for Model
 * View in MVC Design Pattern: Model can only call methods
 * defined in this interface
 * 
 * @author Hunter
 *
 */
public interface View {
	
	public String getCommand();

	public SLogoVisualizer getCurrentVisualizer();
	
	public void addVisualizer() throws SLogoException, IOException;
	
	public void switchVisualizer(int index);
	
	public void updateDisplayData();
	
	public void updateCommandHistory();
	
	public void updateWorkspaces();

	public void updateVariables();
	
	public void setCurrentWorkspace(SLogoWorkspace workspace);
	
	public Observer getObserver();
	
	public String getLanguage();

}
	