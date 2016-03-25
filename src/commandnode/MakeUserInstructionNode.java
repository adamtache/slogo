package commandnode;

import exception.SLogoException;
import model.SLogoCharacterState;

public class MakeUserInstructionNode extends TernaryVariableNode{

    private static final int COMMAND_NAME_INDEX = 0;
    private static final int VARIABLE_LIST_INDEX = 1;
    private static final int COMMAND_LIST_INDEX = 2;
    
    public double evaluate(SLogoCharacterState state) throws SLogoException {
    	System.out.println(getChildren());
    	childListCheck(VARIABLE_LIST_INDEX);
    	childListCheck(COMMAND_LIST_INDEX);
    	String commandName = (((CustomCommandNode) getChildren().get(COMMAND_NAME_INDEX)).getName());
        ListNode varNodeList = ((ListNode) getChildren().get(VARIABLE_LIST_INDEX));
        ListNode commandList = ((ListNode) getChildren().get(COMMAND_LIST_INDEX));
        getWorkspace().createCustomCommand(commandName, varNodeList, commandList);
        return 1;
    }

}