package commandnode;

import java.util.List;

import exception.SLogoException;
import model.SLogoCharacter;
import model.SLogoCharacterState;
import model.SLogoTurtle;

public class TellNode extends TurtleCommand {
	
	private static final int TURTLE_LIST_INDEX = 0;
    private static final int PROGRAMMINGINDEXING = 1;
    
    /**
     * 
     * Interacts with the super class in order to extend functionality for turtle commands that need access to the workspace
     * Creates the list of active turtles that our commands must interact with
     * 
     */
    public double evaluate(SLogoCharacterState state) throws SLogoException {
    	childListCheck(TURTLE_LIST_INDEX);
        super.getWorkspace().resetActiveTurtles();
        List<Node> commands = ((ListNode) getChildren().get(TURTLE_LIST_INDEX)).getCommands();
        List<SLogoCharacter> activeTurtles = super.getWorkspace().getActiveTurtlesList();
        for (Node command : commands) {
            double turtleToGrab = ((NumericNode) command).getNumericValue();
            // creating the turtle from factory automatically adds our turtle to
            // our list of created turtles
            SLogoCharacter newActiveTurtle = super.grabTurtle((int) turtleToGrab 
                                                              - PROGRAMMINGINDEXING);
            super.getWorkspace().addActiveTurtle(newActiveTurtle);
        }
        // return the id of the last created active turtle
        return ((SLogoTurtle) activeTurtles.get(activeTurtles.size() 
                                             - PROGRAMMINGINDEXING)).getCurrTurtlesID();
    }
}