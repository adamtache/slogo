package commandnode;

import controller.CommandController;
import exception.SLogoException;
import model.SLogoTurtleFactory;
import model.SLogoWorkspace;
import parser.TreeEvaluator;
import parser.TreeFactory;

public abstract class WorkspaceNode extends CommandNode{

	private SLogoWorkspace myWorkspace;
    private TreeEvaluator myTreeEvaluator;
    private TreeFactory myTreeFactory;
    private SLogoTurtleFactory myTurtleFactory;
    private CommandController myCommandController;

    public void initialize(SLogoWorkspace ws) throws SLogoException{
        this.myWorkspace = ws;
        setup();
    }
    
    private void setup() throws SLogoException{
    	myTreeEvaluator = new TreeEvaluator(myWorkspace);
        myTreeFactory = new TreeFactory(myWorkspace);
        myTurtleFactory = new SLogoTurtleFactory(myWorkspace);
        myCommandController = new CommandController(myWorkspace);
    }
    
    public SLogoWorkspace getWorkspace(){
        return myWorkspace;
    }
    
    public TreeFactory getTreeFactory(){
        return myTreeFactory;
    }

    public TreeEvaluator getTreeEvaluator(){
        return myTreeEvaluator;
    }
    
    public SLogoTurtleFactory getTurtleFactory(){
    	return myTurtleFactory;
    }
    
    public CommandController getCommandController(){
    	return myCommandController;
    }
    
}