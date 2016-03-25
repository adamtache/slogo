package model;

import java.util.ArrayList;
import java.util.List;
import commandnode.ListNode;
import exception.SLogoException;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import parser.TreeEvaluator;
import parser.TreeFactory;
import view.SLogoPropertiesData;
import view.View;

/**
 * Workspace class that serves as a container for data objects
 *
 */
public class SLogoWorkspace {

	private View myView;
	private SLogoTurtleFactory myTurtleFactory;
	private List<SLogoCharacter> myCharacters;
	private List<SLogoCharacter> myActiveTurtles;
	private ObservableList<SLogoDisplayData> myObservableDataList;
	private ObservableList<SLogoVariable> myObservableVariableList;
	private ObservableList<SLogoCustomCommand> myObservableCustomList;
	private SLogoPropertiesData myPropertiesData;

	/**
	 * Default constructor that initializes lists
	 * 
	 * @param view
	 * @throws SLogoException
	 */
	public SLogoWorkspace(View view) throws SLogoException {
		myView = view;
		createObservableLists();
		myCharacters = new ArrayList<>();
		myTurtleFactory = new SLogoTurtleFactory(this);
		myPropertiesData = myView.getCurrentVisualizer().getPropertiesData();
	}

	/**
	 * Initializes RootEvaluator and ActiveTurtles
	 * 
	 * @throws SLogoException
	 */
	public void initialize() throws SLogoException {
		myActiveTurtles = new ArrayList<>();
		resetActiveTurtles();
		myActiveTurtles.add(myTurtleFactory.createDefaultTurtle());
	}

	/**
	 * Observable lists initialized using ArrayLists
	 * 
	 */
	private void createObservableLists() {
		myObservableDataList = FXCollections.
				observableArrayList(new ArrayList<SLogoDisplayData>());
		myObservableVariableList =  FXCollections.
				observableArrayList(new ArrayList<SLogoVariable>());
		myObservableCustomList =  FXCollections.
				observableArrayList(new ArrayList<SLogoCustomCommand>());
	}

	/**
	 * Listeners added at initialization
	 * 
	 */
	public void addListeners() {
		myObservableDataList.addListener((ListChangeListener) 
				change -> getView().updateDisplayData());

		myObservableVariableList.addListener((ListChangeListener) 
				change -> getView().updateVariables(myObservableVariableList));

		myObservableCustomList.addListener((ListChangeListener) 
				change -> getView().updateCustoms(myObservableCustomList));
	}

	/**
	 * Creates a new custom command and modifies the old custom command if needed
	 * 
	 * @params commandName varList, commandList
	 * @return created custom command
	 */
	public SLogoCustomCommand createCustomCommand(String commandName, 
			ListNode varList, 
			ListNode commandList){
		boolean created = false;
		SLogoCustomCommand varModified = 
				new SLogoCustomCommand(commandName, varList, commandList);
		for (SLogoCustomCommand custom : myObservableCustomList) {
			if (custom.getName().equals(commandName)) {
				((SLogoCustomCommand) custom).setMyCommands(commandList);
				((SLogoCustomCommand) custom).setMyVariables(varList);
				created = true;
			}
		}
		if(created) {
			myObservableCustomList.remove(varModified);
		}
		myObservableCustomList.add(
				new SLogoCustomCommand(commandName, varList, commandList));
		return getCustomCommand(commandName);
	}

	/**
	 * Variable lookup by name
	 * 
	 * @param varName
	 * @return created custom variable
	 */
	public double getVarValueByName(String varName){
		for(SLogoVariable var : myObservableVariableList){
			if(var.getName().equals(varName)) {
				return var.getValue();
			}
		}
		return 0;
	}
	
	public void setVarValue(String varName, double value){
		for(SLogoVariable var : myObservableVariableList){
			if(var.getName().equals(varName)) {
				var.setValue(value);
			}
		}
	}

	/**
	 * See if a Variable is currently in the list and returns if it's present
	 * 
	 * @param varName
	 * @return
	 */
	public SLogoVariable lookupVariable(String varName){
		for(SLogoVariable var : myObservableVariableList){
			if(var.getName().equals(varName)) {
				return var;
			}
		}
		return null;
	}

	/**
	 * See if a Variable is currently in the list and returns if it's present
	 * 
	 * @param varName
	 * @return
	 */
	public SLogoCustomCommand getCustomCommand(String varName){
		for(SLogoCustomCommand custom : myObservableCustomList){
			if(custom.getName().equals(varName)) {
				return custom;
			}
		}
		return null;
	}

	/**
	 * Creates a new variable and modifies the variable if needed
	 * 
	 * @param varName
	 * @param varValue
	 * @return
	 */
	public SLogoVariable createVariable(String varName, double varValue){
		boolean created = false;
		SLogoVariable varModified = new SLogoVariable(varName, varValue);
		for(SLogoVariable var : myObservableVariableList){
			if(var.getName().equals(varName)) {
				varModified = (SLogoVariable) var;
				created = true;
			}
		}
		if(created) {
			myObservableVariableList.remove(varModified);
		}
		myObservableVariableList.add(new SLogoVariable(varName, varValue));
		return lookupVariable(varName);
	}

	/**
	 * Returns a list of characters
	 * 
	 * @return
	 */
	public List<SLogoCharacter> getCharacterList() {
		return myCharacters;
	}

	/**
	 * Adds a Turtle to myCharacters (List of SLogoTurtle)
	 * 
	 */
	public void addTurtle(SLogoTurtle myTurtle){
		myCharacters.add(myTurtle);
	}

	/**
	 * Returns turtleFactory currently being used
	 * 
	 * @return
	 */
	public SLogoTurtleFactory getTurtleFactory() {
		return myTurtleFactory;
	}

	/**
	 * Returns the ActiveTurtle List
	 * 
	 * @return
	 */
	public List<SLogoCharacter> getActiveTurtlesList() {
		return myActiveTurtles;
	}

	/**
	 * Adds an active turtle to the list
	 * 
	 * @param turtle
	 */
	public void addActiveTurtle(SLogoCharacter turtle) {
		myActiveTurtles.add(turtle);
	}

	/**
	 * Clears the list
	 * 
	 */
	public void resetActiveTurtles() {
		myActiveTurtles.clear();
	}

	/**
	 * @return the myView
	 */
	public View getView() {
		return myView;
	}

	/**
	 * @return the myObservableDataList
	 */
	public ObservableList<SLogoDisplayData> getObservableDataList() {
		return myObservableDataList;
	}

	/**
	 * @return the myRootEvaluator
	 */
	public TreeEvaluator getTreeEvaluator() {
		return new TreeEvaluator(this);
	}

	/**
	 * @return the myPropertiesData
	 */
	public SLogoPropertiesData getMyPropertiesData () {
		return myPropertiesData;
	}

}