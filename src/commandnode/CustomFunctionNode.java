package commandnode;

import java.util.ArrayList;
import java.util.List;

import exception.SLogoException;
import model.SLogoCharacterState;
import model.SLogoCustomCommand;
import model.SLogoVariable;

public class CustomFunctionNode extends VariableCommand {

    private SLogoCustomCommand myCommand;

    public CustomFunctionNode(SLogoCustomCommand myCommand) {
        this.myCommand = myCommand;
        setNumChildren(myCommand.getVariableNames().size());
    }

    public double evaluate(SLogoCharacterState state) throws SLogoException {
        List<Node> myChildren = getChildren();
        List<String> customVariables = myCommand.getVariableNames();
        List<SLogoVariable> myVariables = new ArrayList<>();
        for (int x = 0; x < myChildren.size(); x++) {
            SLogoVariable myVar = getWorkspace().createVariable(
                          customVariables.get(x), myChildren.get(x).evaluate(state));
            myVariables.add(myVar);
        }
        List<Node> myCustomVariables = new ArrayList<>();
        for (String var : customVariables) {
            myCustomVariables.add(new VariableNode(var));
        }
        ListNode customVars = new BracketNode();
        customVars.setInnerCommands(customVariables);
        getWorkspace().createCustomCommand(myCommand.getName(), customVars, 
                                           myCommand.getMyCommands());
        return myCommand.getMyCommands().evaluate(state);
    }
    
}