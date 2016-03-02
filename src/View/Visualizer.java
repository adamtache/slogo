package View;

import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import Controller.SLogoException;
import Model.DisplayData;
import Model.Model;
import Model.Position;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class Visualizer implements Observer {
	private ObservableList<DisplayData> myObservableDataList;
	
	private int myWidth;
	private int myHeight;
	
	private FXMLLoader myLoader;
	private GUIController myGUIController;
	private Parent root;

	// Visualization Primitives
	// private Group myRoot;
	private Scene myScene;
	private Stage myStage;

	private Model myModel;
	
	
	
	//private DisplayData myDisplayData;
	public Visualizer(Model model, int width, int height) {
		//myDisplayData = data;
		myModel = model;
		myWidth = width;
		myHeight = height;
	}
	
	public Visualizer(Model model) {
		//myDisplayData = data;
		myWidth = 331;
		myHeight = 331;
		myModel = model;
	}

	
	public void initialize() throws SLogoException, IOException {

		SLogoPromptBuilder myPrompt = new SLogoPromptBuilder();
		myPrompt.promptScreen();

		// GUI Initialization
		myLoader = new FXMLLoader(getClass().getResource("UI.fxml"));
		root = (Parent) myLoader.load();
		myGUIController = (GUIController) myLoader.getController();
		myGUIController.setModel(myModel);
		
		myScene = new Scene(root);
		myStage = new Stage();
		myStage.setScene(myScene);
		myStage.setTitle("SLogo");
		show();
	}

	public void updateStates() {

	}

	public void show() throws SLogoException {
		getStage().show();
	}

	public void hide() {

	}

	public void setBackgroundColor() {

	}
	
	
	
	
	

	@Override
	public void update(Observable observable, Object arg1) {
		// TODO 
//		myDisplayData = (DisplayData) observable;
//		myDisplayData.addLine(createLine(myDisplayData.getPosition()));
		//update rotation (angle)
		//update penDown
		//update penColor
		//update Image
	}
	
	public void setImage (ImageView image) {
		
		
	}

	public void setPenDown (boolean penDown) {
		
		
	}
	
	public void setPenColor (Color color) {
		
	}
	
	public void rotate (DisplayData displaydata) {
		
	}
	
	public Line createLine(Position position) {
		Line newLine = new Line();
		newLine.setStartX(position.xPrevious());
		newLine.setStartY(position.yPrevious());
		newLine.setEndX(position.xCurrent());
		newLine.setEndY(position.yCurrent());
		newLine.setFill(Color.BLACK);
		newLine.setStrokeWidth(1.0f);
		return newLine;
	}
	
	/**
	 * This method updates turtles' attributes and position
	 * Caller is Workspace (MyCurrentWorkspace in MainModel)
	 */
	public void updateDisplayData () {
		
		
	}
	
	/**
	 * This method updates command history display in GUI.
	 * Caller is Workspace (MyCurrentWorkspace in MainModel)
	 */
	public void updateCommandHistory () {
		
	}
	
	
	
	
	public int getWidth() {
		return myWidth;
	}

	public void setWidth(int width) {
		this.myWidth = width;
	}

	public int getHeight() {
		return myHeight;
	}

	public void setHeight(int height) {
		this.myHeight = height;
	}

	/**
	 * @return the myLoader
	 */
	public FXMLLoader getLoader() {
		return myLoader;
	}

	/**
	 * @param myLoader
	 *            the myLoader to set
	 */
	public void setLoader(FXMLLoader myLoader) {
		this.myLoader = myLoader;
	}

	/**
	 * @return the myGUIController
	 */
	public GUIController getGUIController() {
		return myGUIController;
	}

	/**
	 * @param myGUIController
	 *            the myGUIController to set
	 */
	public void setGUIController(GUIController myGUIController) {
		this.myGUIController = myGUIController;
	}

	/**
	 * @return the myModel
	 */
	public Model getMyModel() {
		return myModel;
	}

	/**
	 * @param myModel
	 *            the myModel to set
	 */
	public void setMyModel(Model myModel) {
		this.myModel = myModel;
	}

	/**
	 * @return the myScene
	 */
	public Scene getScene() {
		return myScene;
	}

	/**
	 * @param myScene the myScene to set
	 */
	public void setScene(Scene myScene) {
		this.myScene = myScene;
	}

	/**
	 * @return the myStage
	 */
	public Stage getStage() {
		return myStage;
	}

	/**
	 * @param myStage the myStage to set
	 */
	public void setStage(Stage myStage) {
		this.myStage = myStage;
	}
}
