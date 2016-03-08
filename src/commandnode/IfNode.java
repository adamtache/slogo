package commandnode;

import exception.SLogoException;
import model.SLogoCharacterState;

public class IfNode extends BinaryNode {

	public double evaluate(SLogoCharacterState state) throws SLogoException {
		return evaluateChild(0, state) != 0 ? evaluateChild(1, state) : 0;
	}

}