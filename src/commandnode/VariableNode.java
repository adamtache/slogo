package commandnode;

import model.SLogoCharacterState;

/**
 * Node representation of Variable command
 */
public class VariableNode extends WorkspaceNode{

	private String myName;
    
    public VariableNode(String myName){
        this.myName = myName;
    }

    public String getName(){
        return myName;
    }

    /**
     * Returns current value of variable in relevant workspace
     */
    public double evaluate(SLogoCharacterState state){
        return getWorkspace().getVarValueByName(myName);
    }

}