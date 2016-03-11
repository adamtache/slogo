package commandnode;

import exception.SLogoException;
import model.SLogoCharacterState;

/**
 * Node representation of Random, a Math command
 */
public class RandomNode extends UnaryNode{
	
	public double evaluate(SLogoCharacterState state) throws SLogoException {
		return Math.random()*evaluateChild(0, state);
	}
	
}