package commandnode;

import exception.SLogoException;
import model.SLogoCharacterState;

public class ClearStampsNode extends StampingNode{

    /**
     * Clears a stamp using clearStampTurtles method in TurtleCommand, called from StampingNode
     * 
     */
	public double evaluate(SLogoCharacterState state) throws SLogoException {
		super.clearStampTurtles();
		return 0;
	}

}
