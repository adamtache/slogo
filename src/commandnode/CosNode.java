package commandnode;

import exception.SLogoException;
import model.SLogoCharacterState;

public class CosNode extends UnaryNode{

	public double evaluate(SLogoCharacterState state) throws SLogoException {
		return Math.cos(Math.toRadians(evaluateChild(0, state)));
	}

}