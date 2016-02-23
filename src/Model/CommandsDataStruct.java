package Model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;


public class CommandsDataStruct {

	private static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
    /*
     * ArrayList were each index corresponds to the curr state --> so idx == 0
     * holds all rules for curr state 0 each idx has a linked list that holds
     * all of the rules (so middle numbers) of what the neighbors may look like
     * the first element is the possible neighbors states and the
     * linkedlist.next() has the next state of our curr cell
     * 
     */
    private ArrayList<LinkedList<String>> commandInfo;
    private static CommandsDataStruct instance;

    public CommandsDataStruct() {
        createDataStruct();
        readInRulesFile();
    }
    
    public static synchronized CommandsDataStruct getInstance()
	{
		if (instance == null) instance = new CommandsDataStruct();
		return instance;
	}

    public String determineNextState(int currState, String currsNeighbors) {
        LinkedList<String> rulesOfCurrState = commandInfo.get(currState);
        Iterator<String> iter = rulesOfCurrState.iterator();
        if (rulesOfCurrState.peek().equals(currsNeighbors)) {
            iter.next();
            return iter.next();
        }
        while (iter.hasNext()) {
            String next = iter.next();
            if (next.equals(currsNeighbors))
                return iter.next();
            else
                iter.next(); // skip the next value
        }
        return "We are out of bounds with our grid size";
    }

    private void readInRulesFile() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(
                             classLoader.getResource(DEFAULT_RESOURCE_PACKAGE + "CommandInfo.txt").getFile());
        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                createPossibleStates(line);
            }
            scanner.close();

        } catch (IOException e) {
            System.err.println(e);
        }
    }

    private void createPossibleStates(String line) {
        String[] diffSects = grabDiffSections(line);
        LinkedList<String> sameState = commandInfo.get(Integer.parseInt(diffSects[0]));
        // we have to take into account that our rules are four-fold directionally symmetrical
        for (int j = 0; j < 4; j++) {
            for (int i = 1; i < 3; i++) {
                sameState.add(diffSects[i]);
            }
            String temp = diffSects[1].substring(1, diffSects[1].length());
            diffSects[1] = temp + diffSects[1].substring(0, 1);
        }
    }

    private void createDataStruct() {
    	commandInfo = new ArrayList<LinkedList<String>>();
        for (int i = 0; i < 8; i++) 
        	commandInfo.add(new LinkedList<String>());
    }

    private String[] grabDiffSections(String line) {
        String[] states = new String[3];
        states[0] = line.substring(0, 1); // currState
        states[1] = sortThisStateRule(line.substring(1, line.length() - 1)); // neighbors
        states[2] = line.substring(line.length() - 1, line.length()); // nextState
        return states;
    }

    private String sortThisStateRule(String state) {
        String result = "";
        for (String each: state.split(""))
            result += each;
        return result;
    }

}
