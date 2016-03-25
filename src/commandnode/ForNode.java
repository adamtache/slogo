package commandnode;

import exception.SLogoException;
import model.SLogoCharacterState;
import model.SLogoVariable;

/**
 * Node representation of For command, a Control Structure command using variable
 */
public class ForNode extends BinaryVariableNode {

	private final static int CONTROL_LIST_INDEX = 0;
	private final static int COMMAND_LIST_INDEX= 1;
	private final static int VARIABLE_INDEX = 0;
	private final static int START_INDEX = 1;
	private final static int END_INDEX = 2;
	private final static int INCREMENT_INDEX = 3;

	/**
	 * Children are two ListNode children
	 * Child one is a ListNode with four values, variable, start, end, and increment
	 * Child two is a ListNode with commands run for each value specified in range by a for-loop
	 * @return value of final command in list executed
	 * Loop runs from start to end inclusive using increment between each run
	 */
	public double evaluate(SLogoCharacterState state) throws SLogoException {
		childListCheck(CONTROL_LIST_INDEX);
		childListCheck(COMMAND_LIST_INDEX);
		ListNode controlList = ((ListNode) (getChildren().get(CONTROL_LIST_INDEX)));
		int start = (int) Math.floor(controlList.getEvaluations().get(START_INDEX));
		int end = (int) Math.floor(controlList.getEvaluations().get(END_INDEX));
		int increment = (int) Math.floor(controlList.getEvaluations().get(INCREMENT_INDEX));
		SLogoVariable var = getWorkspace().createVariable(controlList.getInnerTokens().get(VARIABLE_INDEX), start);
		ListNode commandList = ((ListNode) (getChildren().get(COMMAND_LIST_INDEX)));
		double evaluation = 0;
		for(int x = start; x <= end; x += increment){
			evaluation = commandList.evaluate(state);
			getWorkspace().setVarValue(var.getName(), x);
		}
		return evaluation;
	}

}