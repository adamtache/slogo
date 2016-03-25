package commandnode;

import java.util.ArrayList;
import java.util.List;

import exception.SLogoException;
import model.SLogoCharacter;
import model.SLogoCharacterState;
import model.SLogoDisplayData;

public abstract class TurtleCommand extends WorkspaceNode {

    /**
     * Super class that is extended by all turtle command classes. A concise and abstract way of extending functionality for
     * turtle commands who need access to the list of active and all turtles contained within the workspace
     */

	/**
     * Constant that differentiates a normal turtle from a stamped image of a turtle
     */
    private static final int STAMP_ID = -1;

    /**
     * closed way of allowing the super class to handle interacting with the specific turtle factory instance in order to 
     * create and return a turtle with a specific index
     * 
     * @param int turtleIndexToGrab - index of turtle that we want
     * @return SLogoCharacter 
     */
    protected SLogoCharacter grabTurtle(int turtleIndexToGrab) throws SLogoException {
        return getTurtleFactory().createTurtle(getTurtleFactory().getDefaultX(), 
        		getTurtleFactory().getDefaultX(), turtleIndexToGrab, 
               getTurtleFactory().getDefaultDirection(), getTurtleFactory().getDefaultHidden(), 
               getTurtleFactory().getDefaultShape());
    }
    
    protected SLogoCharacter createStampTurtle(SLogoCharacterState state) throws SLogoException {
    	return getTurtleFactory().createTurtle((int)state.getXCoor(), (int)state.getYCoor(), 
    			STAMP_ID, state.getDirection(), state.getHidden(), state.getShapeIndex());
    }
    
    protected void clearStampTurtles(){
    	List<SLogoCharacter> stampsTurtleStateToRemove = new ArrayList<>();
    	List<SLogoDisplayData> stampsDisplayDataToRemove = new ArrayList<>();
    	for (SLogoCharacter turtle: getWorkspace().getCharacterList()) {
    		if (turtle.getState().getID() == STAMP_ID) {
    			stampsTurtleStateToRemove.add(turtle);
    		}
    	}
    	for (SLogoDisplayData stateData : getWorkspace().getObservableDataList()) {
			if (stateData.getID() == STAMP_ID) {
				stampsDisplayDataToRemove.add(stateData);
			}
		}
    	// avoid concurrent modification exceptions by creating new lists we iterate through to remove the turtle's elements
    	if (!stampsTurtleStateToRemove.isEmpty()) {
    		for (SLogoCharacter turtle: stampsTurtleStateToRemove) {
    			turtle.getState().setHidden(true);
    			getWorkspace().getCharacterList()
    							.remove(getWorkspace().getCharacterList().indexOf(turtle));
    			
    		}
    	}
    	if (!stampsDisplayDataToRemove.isEmpty()) {
    		for (SLogoDisplayData turtle: stampsDisplayDataToRemove) {
    			getWorkspace().getObservableDataList().remove(getWorkspace().getObservableDataList()
    					.indexOf(turtle));
    		}
    	}
    }

}
