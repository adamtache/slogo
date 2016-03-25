package commandnode;

import exception.SLogoException;
import model.SLogoCharacterState;

public class CustomCommandNode extends WorkspaceNode{

    private String myName;

    public CustomCommandNode(String myName){
        this.myName = myName;
    }

    public String getName(){
        return myName;
    }

    /**
     * return current value of variable in current workspace
     * @throws SLogoException 
     */
    public double evaluate(SLogoCharacterState state) throws SLogoException{
        return getWorkspace().getCustomCommand(myName) == null ? 0 : 1; 
    }

}