package commandnode;

import exception.SLogoException;
import model.SLogoCharacterState;

public class RandomNode extends UnaryNode{
	
	public double evaluate(SLogoCharacterState state) throws SLogoException {
		return Math.random()*getChildren().get(0).evaluate(state);
	}
	
}