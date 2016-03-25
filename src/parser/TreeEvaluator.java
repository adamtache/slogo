package parser;

import java.util.ArrayList;
import java.util.List;

import commandnode.Node;
import exception.SLogoException;
import model.SLogoCharacter;
import model.SLogoWorkspace;

/**
 * TreeEvaluator evaluates a list of nodes and applies the effects to Character
 * states by altering ObservableDataList
 *
 */
public class TreeEvaluator {

	private SLogoWorkspace myWorkspace;

	public TreeEvaluator(SLogoWorkspace myWorkspace) {
		this.myWorkspace = myWorkspace;
	}

	/**
	 * Executes command tree by executing root for all currently active turtles
	 * If multiple turtle command such as tell is evaluated, active turtle list may change
	 * Uses helper method getTurtleIDs to check for multiple turtle command
	 * If this is the case, breaks out of current loop of now inactive turtles
	 * 
	 * @param Node myRoot
	 * @return evaluation of myRoot
	 */
	public double evaluate(Node myRoot) throws SLogoException{
		List<SLogoCharacter> activeTurtles = myWorkspace.getActiveTurtlesList();
		List<Integer> activeIDs = getTurtleIDs(activeTurtles);
		double evaluation = 0;
		for(SLogoCharacter character: activeTurtles){
			evaluation = evaluateRoot(myRoot, character);
			List<SLogoCharacter> newActiveTurtles = myWorkspace.getActiveTurtlesList();
			List<Integer> newActiveIDs = getTurtleIDs(newActiveTurtles);
			if(!newActiveIDs.equals(activeIDs)){
				break;
			}
		}
		return evaluation;
	}
	
	/**
	 * update a specific turtle due to the evaluation of a specific command 
	 * 
	 * @param Node myRoot - tree to evaluate 
	 * @param SLogoCharacter - the character whose state must be updated based 
	 * on evaluation 
	 * @return double evaluation value for the command with the given turtle state
	 */
	private double evaluateRoot(Node myRoot, SLogoCharacter character) throws SLogoException {
		double evaluation = myRoot.evaluate(character.getState());
		myWorkspace.getObservableDataList().get(myWorkspace.getCharacterList().indexOf(character))
		.updateData();
		return evaluation;
	}

	/**
	 * Helper method for checking Turtle IDs to see if multiple turtle command affected current turtle list
	 * 
	 * @param List<SLogoCharacter> activeTurtles - the list of active turtles
	 * @return List<Integer> list of ID's of turtles
	 */
	private List<Integer> getTurtleIDs(List<SLogoCharacter> activeTurtles){
		List<Integer> activeIDs = new ArrayList<>();
		for(SLogoCharacter turtle : activeTurtles){
			activeIDs.add(turtle.getState().getID());
		}
		return activeIDs;
	}

}