package commandnode;
import java.util.List;

import exception.SLogoException;
import model.SLogoCharacterState;

/**
 * @author Adam
 * Node representation of a List of commands inside of a [ ]
 * Extends CommandNode and can have unlimited commands inside
 */
public abstract class ListNode extends WorkspaceNode{

	private List<String> myInnerTokens;

	/**
	 * @return list of evaluations of commands inside ListNode
	 */
	public List<Double> getEvaluations() throws SLogoException{
		return getCommandController().evaluateTokens(clone(myInnerTokens));
	}

	/**
	 * @param index
	 * @return Evaluation of command at index
	 */
	public double evaluate(int index) throws SLogoException{
		return getEvaluations().get(index);
	}

	/**
	 * @return Evaluation of last command in list
	 */
	public double evaluate(SLogoCharacterState state) throws SLogoException {
		List<Double> evaluations = getEvaluations();
		return evaluations.get(evaluations.size() - 1);
	}

	public void setInnerCommands(List<String> tokens){
		this.myInnerTokens = tokens;
	}

	public List<String> getInnerTokens(){
		return myInnerTokens;
	}

}