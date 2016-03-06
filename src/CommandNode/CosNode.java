package CommandNode;

import Model.*;
import View.*;
import Exception.*;
import Controller.*;
import deprecated_to_be_deleted.*;
import CommandNode.*;

public class CosNode extends TrigNode{

	public double evaluate(SLogoCharacterState state) throws SLogoException {
		return Math.cos(getChildren().get(0).evaluate(state));
	}

}