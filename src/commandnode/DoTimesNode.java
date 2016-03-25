package commandnode;

import exception.SLogoException;
import model.SLogoCharacterState;
import model.SLogoVariable;

/**
 * Node representation of DoTimes command, a Control Structure command using variable
 */
public class DoTimesNode extends BinaryVariableNode {

	private final static int CONTROL_LIST_INDEX = 0;
    private final static int COMMAND_LIST_INDEX= 1;
    
    /**
     * Children are two ListNode children
     * Child one is a ListNode with two values, a variable and a limit
     * Child two is a ListNode with commands run for each value in limit using variable
     * @return value of final command in list executed
     * 
     */
    public double evaluate(SLogoCharacterState state) throws SLogoException {
    	childListCheck(CONTROL_LIST_INDEX);
    	childListCheck(COMMAND_LIST_INDEX);
    	ListNode controlList = ((ListNode) (getChildren().get(CONTROL_LIST_INDEX)));
        SLogoVariable var = getWorkspace().createVariable(controlList
                                          .getInnerTokens().get(0), 1);
        double limit = Double.parseDouble(controlList.getInnerTokens().get(1));
        ListNode commandList = ((ListNode) (getChildren().get(COMMAND_LIST_INDEX)));
        double evaluation = 0;
        for (int x = 1; x <= limit; x++){
        	evaluation = commandList.evaluate(state);
            var.setValue(x);
        }
        return evaluation;
    }

}