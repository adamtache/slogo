package Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import CommandNode.DisplayData;
import Controller.LanguageDriver;
import Exception.SLogoException;
import View.View;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class MainModel implements Model {

	private String myCommand;
	private View myView;
	private Workspace myCurrentWorkspace;
	private LanguageDriver myLanguageDriver;

	private List<Workspace> myWorkspaces;
	private ObservableList<Workspace> myObservableWorkspaces;

	public MainModel() throws SLogoException {
		myWorkspaces = new ArrayList<Workspace>();
		myObservableWorkspaces = FXCollections.observableArrayList(myWorkspaces);
	}

	public MainModel(View view) {
		myView = view;
	}

	@Override
	public void loadLanguage () {
		myLanguageDriver = new LanguageDriver();
		try {
			myLanguageDriver.load(getView().getLanguage());
		} catch (SLogoException e) {
			//TODO: Display error
		}
	}

	public void initialize() throws SLogoException {	
		createNewWorkspace();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addListeners () {
		myObservableWorkspaces.addListener((ListChangeListener) c -> {
			getView().updateWorkspaces();
			getView().setCurrentWorkspace(getCurrentWorkspace());
		});
		myCurrentWorkspace.addListeners();

		getView().updateDisplayData();
		getView().updateCommandHistory();
		getView().updateWorkspaces();
	}

	public void readCommand(String command) throws SLogoException {
		setCommand(command);
		ParserController parseController = new ParserController(myCurrentWorkspace);
		parseController.readCommand(command);
	}

	/**
	 * @return the myCommand
	 */
	public String getCommand() {
		return myCommand;
	}

	/**
	 * @param myCommand
	 *            the myCommand to set
	 */
	public void setCommand(String myCommand) {
		this.myCommand = myCommand;
	}

	/**
	 * @return the myView
	 */
	public View getView() {
		return myView;
	}

	/**
	 * @param myView
	 *            the View to set
	 */
	public void setView(View myView) {
		this.myView = myView;
	}

	@Override
	public void createBackend() {

	}

	@Override
	public ObservableList<DisplayData> getObservableDataList() {
		return myCurrentWorkspace.getObservableDataList();
	}

	/**
	 * Create a new workspace and set it as current workspace
	 * @throws SLogoException 
	 */
	//	@Override
	public void createNewWorkspace() throws SLogoException {
		// TODO Auto-generated method stub


		Workspace myWorkspace = new Workspace(getView());
		myWorkspace.initialize();
		getObservableWorkspaces().add(myWorkspace);
		setCurrentWorkspace(myWorkspace);

	}

	@Override
	public void addWorkspace() throws SLogoException, IOException {
		// TODO Auto-generated method stub
		//Need to get View to create a new Visualizer for this workspace

		createNewWorkspace();
		
		getView().addVisualizer();

		getCurrentWorkspace().addListeners();


		//		System.out.println("HAHAHA:");
		//getView().getCurrentVisualizer().show();
		//FIX THE DEPENDENCY
		getView().updateDisplayData();

	}


	/**
	 * @return the myCurrentWorkspace
	 */
	@Override
	public Workspace getCurrentWorkspace() {
		return myCurrentWorkspace;
	}

	/**
	 * @param myCurrentWorkspace the myCurrentWorkspace to set
	 */
	public void setCurrentWorkspace(Workspace myCurrentWorkspace) {
		this.myCurrentWorkspace = myCurrentWorkspace;
	}

	/**
	 * @return the myObservableWorkspaces
	 */
	public ObservableList<Workspace> getObservableWorkspaces() {
		return myObservableWorkspaces;
	}

	/**
	 * @param myObservableWorkspaces the myObservableWorkspaces to set
	 */
	public void setObservableWorkspaces(ObservableList<Workspace> myObservableWorkspaces) {
		this.myObservableWorkspaces = myObservableWorkspaces;
	}

	/**
	 * @return the myLanguageDriver
	 */
	public LanguageDriver getMyLanguageDriver() {
		return myLanguageDriver;
	}

	/**
	 * @param myLanguageDriver the myLanguageDriver to set
	 */
	public void setMyLanguageDriver(LanguageDriver myLanguageDriver) {
		this.myLanguageDriver = myLanguageDriver;
	}


}