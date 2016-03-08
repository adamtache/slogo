package commandnode;

import exception.SLogoException;
import model.SLogoCharacterState;

public class LogarithmNode extends UnaryNode{
	
	public double evaluate(SLogoCharacterState state) throws SLogoException {
		return Math.log(evaluateChild(0, state));
		
	}

}