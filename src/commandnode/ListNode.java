package commandnode;

import java.util.List;

import exception.SLogoException;
import model.SLogoCharacterState;

/**
 * @author Adam
 * Node representation of a List of commands inside of a [ ]
 * Extends CommandNode and can have unlimited commands inside
 */
public class ListNode extends CommandNode{

	private List<Node> myCommands;
	
	/**
	 * @param myCommands, roots of individual commands to be placed inside the ListNode
	 */
	public ListNode(List<Node> myCommands){
		this.myCommands = myCommands;
	}
	
	public ListNode(String[] array) {
		// TODO: Temp
	}

	/**
	 * @return Evaluation of last command
	 */
	public double evaluate(SLogoCharacterState state) throws SLogoException {
		double evaluation = 0;
		for(Node node : myCommands){
			evaluation = node.evaluate(state);
		}
		return evaluation;
	}
	
	public List<Node> getCommands(){
		return myCommands;
	}

}