package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import commandnode.Node;
import controller.CommandController;
import exception.SLogoException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import parser.SLogoParser;
import parser.TreeFactory;
import view.View;

/**
 * Model class that houses workspaces and saves metadata
 * for SLogo IDE
 *
 */
public class SLogoModel implements Model {
    private View myView;
    private SLogoWorkspace myCurrentWorkspace;
    private List<SLogoWorkspace> myWorkspaces;
    private ObservableList<SLogoWorkspace> myObservableWorkspaces;

    /**
     * Default constructor
     * 
     * @throws SLogoException
     */
    public SLogoModel() throws SLogoException {
        myWorkspaces = new ArrayList<>();
        myObservableWorkspaces = FXCollections.observableArrayList(myWorkspaces);
    }

    /**
     * Initializes by creating the first workspace
     * 
     * @throws SLogoException
     */
    public void initialize() throws SLogoException {
        createNewWorkspace();
    }

    /**
     * Adds Listeners to all the Observable lists that are present
     * in model
     * 
     * Called at initialization
     * 
     */
    public void addListeners () {
        myCurrentWorkspace.addListeners();
        getView().updateDisplayData();
    }

    /**
     * Creates a parser instance and feeds the command to the parser
     * called by View through interface
     * @return 
     * 
     */
    @Override
    public void readCommand(String command) throws SLogoException {
        CommandController myCommandController = new CommandController(myCurrentWorkspace);
        List<String> tokens = myCommandController.getCommandTokens(command);
        myCommandController.evaluateTokens(tokens);
    }

    /**
     * Switches workspaces given the index
     * Used in conjunction with switchVisualizer
     * 
     */
    @Override
    public void switchWorkspace(int index) throws SLogoException {
        if (index >= getObservableWorkspaces().size()) {
            throw new SLogoException(new ResourceLoader().getString("InvalidWorkspace"));
        }
        setCurrentWorkspace(getObservableWorkspaces().get(index));
        getView().switchVisualizer(index);
    }

    /**
     * @return the myView
     */
    public View<?> getView() {
        return myView;
    }

    public ObservableList<SLogoDisplayData> getObservableDataList() {
        return myCurrentWorkspace.getObservableDataList();
    }

    /**
     * Create a new workspace and set it as current workspace
     * @throws SLogoException 
     */
    public void createNewWorkspace() throws SLogoException {
        SLogoWorkspace myWorkspace = new SLogoWorkspace(getView());
        myWorkspace.initialize();
        getObservableWorkspaces().add(myWorkspace);
        setCurrentWorkspace(myWorkspace);
    }

    /**
     * Adds a new workspace and initializes it
     * Also calls addVisualizer that adds a visualizer in the frontend
     * 
     */
    @Override
    public void addWorkspace() throws SLogoException, IOException {
        getView().addVisualizer();
        createNewWorkspace();
        getCurrentWorkspace().addListeners();
        getView().updateDisplayData();
    }

    /**
     * @return the myCurrentWorkspace
     */
    public SLogoWorkspace getCurrentWorkspace() {
        return myCurrentWorkspace;
    }

    /**
     * @param myCurrentWorkspace the myCurrentWorkspace to set
     */
    public void setCurrentWorkspace(SLogoWorkspace myCurrentWorkspace) {
        this.myCurrentWorkspace = myCurrentWorkspace;
    }

    /**
     * @return the myObservableWorkspaces
     */
    public ObservableList<SLogoWorkspace> getObservableWorkspaces() {
        return myObservableWorkspaces;
    }

    /**
     * @param myView the myView to set
     */
    public void setView(View myView) {
        this.myView = myView;
    }

}