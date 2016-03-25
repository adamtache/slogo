package model;
import java.util.ArrayList;
import java.util.List;

import commandnode.ListNode;

/**
 * CustomCommand class that represents each user-defined custom command
 * Also contains variables and commands
 *
 */
public class SLogoCustomCommand{

    private String myName;
    private ListNode myVariables;
    private ListNode myCommands;

    /**
     * Default constructor that takes in name, variables, and commands
     * 
     * @param myName
     * @param myVariables
     * @param myCommands
     */
    public SLogoCustomCommand(String myName, ListNode myVariables, ListNode myCommands){
        this.myName = myName;
        this.myVariables = myVariables;
        this.myCommands = myCommands;
    }

    public ListNode getMyVariables(){
        return myVariables;
    }

    public ListNode getMyCommands(){
        return myCommands;
    }

    public void setMyCommands(ListNode myCommands){
        this.myCommands = myCommands;
    }

    public void setMyVariables(ListNode myVariables){
        this.myVariables = myVariables;
    }

    /**
     * Returns a list of variables used for this particular custom command
     * 
     * @return
     */
    public List<String> getVariableNames(){
        List<String> varNames = new ArrayList<>();
        myVariables.getInnerTokens().stream()
                                      .forEach(var -> varNames.add(var.toString()));
        return varNames;
    }

    public String getName(){
        return myName;
    }

    public void setName(String myName){
        this.myName = myName;
    }

    /**
     * Runs the custom command, and if successful, return 0
     * 
     * @return
     */
    public double run() {
        return 0;
    }

}