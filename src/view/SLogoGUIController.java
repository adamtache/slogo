package view;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import exception.SLogoException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import model.Model;
import model.ResourceLoader;
import model.SLogoCustomCommand;
import model.SLogoDisplayData;
import model.SLogoVariable;

/**
 * A controller class used in conjunction with SceneBuilder's
 * UI.fxml file
 *
 * @author Hunter, Michelle
 */
public class SLogoGUIController implements Initializable, Observer {

    private static final String HELP_URL = "http://www.cs.duke.edu/courses/"
            + "compsci308/spring16/assign/03_slogo/commands.php";
    private static final String CSS_PATH = "view/splashstyle.css";
    private static final int POPUP_WIDTH = 900;
    private static final int POPUP_HEIGHT = 550;
    private static final int PANE_WIDTH = 200;
    private static final int PANE_HEIGHT = 150;
    private static final int RGB_CONST = 255;
    private static final int ERROR_INDEX = 7;
    private static final String ERROR = "ERROR";

    private ResourceLoader myResourceLoader;
    private ResourceLoader myErrorLoader;
    private WebView	myBrowser;
    private WebEngine myWebEngine;
    private ListView<String> myHistoryPaneView;
    private ListView<String> myPropertiesPaneView = new ListView<String>();
    private ListView<String> myVariableView;
    private ListView<String> myCustomCommandView;
    private ObservableList<String> myProperties;
    private Stage myCurrentStage;
    private Color myCanvasColor;
    private ColorPicker myColorPicker;
    private HBox myColorHBox;
    private SLogoCustomizerBuilder myCustomizer;
    private SLogoPropertiesData myPropertiesData;
    private Model myModel;
    private String myCommand;

    /**
     * The following are FXML-JavaFX component links
     * 
     */
    //Help button
    @FXML
    private Button myHelpButton;

    //Textfield
    @FXML
    private TextField myTextField;

    //Run button
    @FXML
    private Button myRunButton;

    //Main Pane
    @FXML
    private Pane myCanvas;

    //Drop-down menuButton - Choose Project
    @FXML
    private MenuButton myMenuButton;

    //MenuButton's MenuItem list
    @FXML
    private MenuItem myProject1;
    @FXML
    private MenuItem myProject2;
    @FXML
    private MenuItem myProject3;
    @FXML
    private MenuItem myProject4;
    @FXML
    private MenuItem myProject5;

    //Command History Pane where ObservableList<CommandNode> will go
    @FXML
    private ScrollPane myCommandHistoryPane;

    //myVariablePane where ObservableList<Variable> will go
    @FXML
    private ScrollPane myVariablePane;
    
    //CustomCommands Pane where ObservableList<SLogoCustomCommand> will go
    @FXML
    private ScrollPane myCustomPane;

    //Displays the properties of a turtle
    @FXML
    private Pane myPropertyPane;

    //This adds workspace
    @FXML
    private Button myAddWorkspaceButton;

    @FXML
    private Button myCustomizeButton;

    //History
    @FXML
    private List<String> myHistory;

    /**
     * All GUI elements are initialized in this method
     * and FXML settings are read
     * 
     */
    @Override 
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        myResourceLoader = new ResourceLoader("default.properties");
        myErrorLoader = new ResourceLoader("error.properties");

        myHistory = new ArrayList<String>();
        myPropertyPane.getChildren().add(myPropertiesPaneView);
        try {
            myCustomizer = new SLogoCustomizerBuilder(this);
        } catch (SLogoException e) {
            e.showErrorDialog(getErrorLoader().getString("CustomizerError"));
        }
        myCustomizer.hide();
        myVariableView = new ListView<String>();
        myCustomCommandView = new ListView<String>();

        assignMenuAction();
        assignHelpAction();
        assignRunAction();
        customize();
        assignAddWorkspaceAction();
    }

    /**
     * Assigns an action to Run button
     * 
     */
    private void assignRunAction () {
        myRunButton.setOnAction(e -> {
            myCommand = myTextField.getText();
            myTextField.clear();
            run(myCommand);
        });  
    }

    /**
     * Assigns an action to addWorkspace Button
     * Dependency is the model interface
     * 
     */
    private void assignAddWorkspaceAction () {
        myAddWorkspaceButton.setOnAction(e -> {
            myCurrentStage = (Stage) myAddWorkspaceButton.getScene().getWindow();
            myCurrentStage.hide();
            try {
                getModel().addWorkspace();
            } catch (Exception e1) {}
        });
    }

    /**
     * Assigns an action to the HELP Button
     */
    private void assignHelpAction () {
        myHelpButton.setOnAction(e -> {
            popup(HELP_URL);
        });
    }

    /**
     * Manually assigns an action to each MenuItem
     * 
     * This is not the optimal design, but it was done this way
     * because FXML (created by SceneBuilder) does not support
     * dynamic addition of MenuItems to MenuButton
     * 
     */
    private void assignMenuAction () {
        myProject1.setOnAction(e -> {
            try {
                getModel().switchWorkspace(0);
            } catch (Exception e1) {}
        });
        myProject2.setOnAction(e -> {
            try {
                getModel().switchWorkspace(1);
            } catch (Exception e1) {}
        });
        myProject3.setOnAction(e -> {
            try {
                getModel().switchWorkspace(2);
            } catch (Exception e1) {}
        });
        myProject4.setOnAction(e -> {
            try {
                getModel().switchWorkspace(3);
            } catch (Exception e1) {}
        });
        myProject5.setOnAction(e -> {
            try {
                getModel().switchWorkspace(4);
            } catch (Exception e1) {}
        });
    }

    /**
     * Defines and assigns an action to Run button
     * Notifies Model that there is a command to be processed
     * 
     * @param command
     */
    public void run(String command){
        setCommand(myCommand);
        /*
         * TODO: Call Model's readCommand that calls
         * View's getCommand
         * and passes the command to the parser
         */
        try {
            getModel().readCommand(command);
        } catch (SLogoException e1) {
            command = "ERROR: " + command;
        }

        myHistory.add(command);
        displayHistory();
        displayProperties();
        myPropertiesData.notifyObservers();
    }

    /**
     * Lets the user view the commands
     * 
     * NEEDS TO BE REVISED!!!
     * 
     * @param link
     */
    private void popup(String link){
        myBrowser = new WebView();
        myWebEngine = myBrowser.getEngine();
        myWebEngine.load(link);
        VBox vbox = new VBox();
        Scene scene = new Scene(vbox);
        Stage stage = new Stage();        
        vbox.getChildren().addAll(myBrowser);
        VBox.setVgrow(myBrowser, Priority.ALWAYS);
        stage.setTitle(getResourceLoader().getString("PopupTitle"));
        stage.setWidth(POPUP_WIDTH);
        stage.setHeight(POPUP_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Lists commands in the command history pane
     * 
     */
    private void displayHistory(){
        myHistoryPaneView = new ListView<String>();
        ObservableList<String> items =FXCollections.observableArrayList(myHistory);
        myHistoryPaneView.setItems(items);
        myCommandHistoryPane.setContent(myHistoryPaneView);

        myHistoryPaneView.getSelectionModel().selectedItemProperty().addListener
        (new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, 
                                String oldValue, String newValue) {
                if(newValue.contains(ERROR)) {
                    myCommand = newValue.substring(ERROR_INDEX);
                } else {
                    myCommand = newValue;
                }
                run(myCommand);
            }
        });
    }   

    /**
     * Displays user-created variables in the scroll pane
     * 
     * @param variables
     */
    public void displayVariables (ObservableList<SLogoVariable> variables) {
        ObservableList<String> list = FXCollections
                .observableArrayList(new ArrayList<String>());
        variables.stream().forEach(n -> list.add(n.getName() + "  :  " + n.getValue()));
        getVariableView().setItems((ObservableList<String>) list);
        myVariablePane.setContent(getVariableView());
    }

    /**
     * Displays user-created commands in the scroll pane
     * 
     * @param customs
     */
    public void displayCustomCommands (ObservableList<SLogoCustomCommand> customs) {
        ObservableList<String> list = FXCollections
                .observableArrayList(new ArrayList<String>());
        customs.stream().forEach(n -> list.add(n.getName() + "  :  " 
                + ((SLogoCustomCommand)n).getVariableNames()));
        getCustomCommandView().setItems((ObservableList<String>) list);
        //myCustomPane.setContent(getCustomCommandView());
        System.out.println("CUSTOM COMMAND CREATED");
    }

    /**
     * Returns ListView of custom commands
     * 
     * @return
     */
    private ListView<String> getCustomCommandView() {
        return myCustomCommandView;
    }

    /**
     * Displays properties
     * 
     */
    public void displayProperties(){
        myPropertiesPaneView.setItems(myProperties);
        myPropertiesPaneView.setPrefSize(PANE_WIDTH, PANE_HEIGHT);
    }

    /**
     * Updates property info on the bottom left side of the screen
     * 
     * @param displayData
     */
    public void updateProperties(SLogoDisplayData displayData){
        myProperties = FXCollections.observableArrayList (
                        ("Direction: " + Double.toString(displayData.getDirection())),
                        ("X position: " + displayData.getX()),
                        ("Y position: " + displayData.getY()),
                        ("Pen Down: " + displayData.getPen().getDown()),
                        ("Pen Color: " + displayData.getPen().getColorIndex()),
                        ("Pen Size: " + displayData.getPen().getSize()));
        myPropertiesPaneView.setPrefSize(PANE_WIDTH, PANE_HEIGHT);
        myPropertiesPaneView.setItems(myProperties);
    }

    /**
     * Assigns an action to the customize button
     * 
     */
    private void customize(){
        myCustomizeButton.setOnMouseClicked(e -> {
            myCustomizer.setPropertiesData(myPropertiesData);
            myCustomizer.show();    		
        });
    }

    /**
     * Converts Color object to its hex string
     * 
     * @param color
     * @return
     */
    public String toRGBCode (Color color) {
        return String.format( "#%02X%02X%02X",
                              (int) (color.getRed() * RGB_CONST),
                              (int) (color.getGreen() * RGB_CONST),
                              (int) (color.getBlue() * RGB_CONST));
    }

    /**
     * Getter for Customizer Button
     * 
     * @return
     */
    public SLogoCustomizerBuilder getCustomizer() {
        return myCustomizer;
    }

    /**
     * Colorpicker method for Customize button
     * 
     * @return
     */
    private HBox chooseColor(){		
        Label colorLabel = new Label(getResourceLoader().getString("ColorLabel"));

        myColorPicker = new ColorPicker();
        myColorPicker.setOnAction(e -> {
            myCanvasColor = myColorPicker.getValue();
        });

        myColorHBox = new HBox();
        myColorHBox.getStylesheets().add(CSS_PATH);
        myColorHBox.getChildren().addAll(colorLabel, myColorPicker);

        return myColorHBox;
    }

    /**
     * Returns current pane color
     * 
     * @return
     */
    public Color getPaneColor(){
        return myCanvasColor;
    }

    /**
     * Sets new pane color
     * 
     * @throws SLogoException
     */
    public void setPaneColor() throws SLogoException{
        this.myCanvasColor = new SLogoCustomizerBuilder(this).getMyPaneColor();
    }

    /**
     * Sets myCommand
     * 
     * @param myCommand
     */
    public void setCommand(String myCommand) {
        this.myCommand = myCommand;
    }

    /**
     * Returns current canvas
     * 
     * @return
     */
    public Pane getCanvas() {
        return myCanvas;
    }

    /**
     * Adds a node to canvas
     * 
     * @param list
     */
    public void addToCanvas(Node list) {
        getCanvas().getChildren().add(list);
    }

    /**
     * Adds a list of nodes to canvas
     * 
     * @param nodelist
     */
    public void addToCanvas(List<Line> nodelist) {
        getCanvas().getChildren().addAll(nodelist);
    }

    /**
     * Sets a new PropertiesData
     * 
     * @param propertiesData
     */
    public void setPropertiesData(SLogoPropertiesData propertiesData) {
        this.myPropertiesData = propertiesData;
        myPropertiesData.addObserver(this);
    }

    /**
     * @return the myModel
     */
    public Model getModel() {
        return myModel;
    }

    /**
     * @param myModel the myModel to set
     */
    public void setModel(Model myModel) {
        this.myModel = myModel;
    }

    /**
     * @return the myMenuButton
     */
    public MenuButton getMenuButton() {
        return myMenuButton;
    }

    /**
     * @param myMenuButton the myMenuButton to set
     */
    public void setMenuButton(MenuButton myMenuButton) {
        this.myMenuButton = myMenuButton;
    }

    /**
     * updates canvas color
     */
    @Override
    public void update(Observable o, Object arg) {
        getCanvas().setStyle("-fx-background-color: " 
                + toRGBCode(myPropertiesData.getPaneColor()));
    }

    /**
     * @return the myCommand
     */
    public String getCommand() {
        return myCommand;
    }

    /**
     * @return the myVariableView
     */
    public ListView<String> getVariableView() {
        return myVariableView;
    }

    /**
     * @param myVariableView the myVariableView to set
     */
    public void setVariableView(ListView<String> myVariableView) {
        this.myVariableView = myVariableView;
    }

    /**
     * @return the myResourceLoader
     */
    public ResourceLoader getResourceLoader () {
        return myResourceLoader;
    }

    /**
     * @param myResourceLoader the myResourceLoader to set
     */
    public void setResourceLoader (ResourceLoader myResourceLoader) {
        this.myResourceLoader = myResourceLoader;
    }

    /**
     * @return the myErrorLoader
     */
    public ResourceLoader getErrorLoader () {
        return myErrorLoader;
    }

    /**
     * @param myErrorLoader the myErrorLoader to set
     */
    public void setErrorLoader (ResourceLoader myErrorLoader) {
        this.myErrorLoader = myErrorLoader;
    }
}