package commandnode;

import exception.SLogoException;
import model.SLogoCharacterState;
import model.SLogoWorkspace;
import commandnode.VariableNode;

public class MakeNode extends BinaryNode {
	
	public double evaluate(SLogoCharacterState state) throws SLogoException {
		VariableNode varNode = ((VariableNode) getChildren().get(0));
		String varName = varNode.getVarName();
		double varValue = evaluateChild(1, state);
		varNode.setValue(varValue);
		SLogoWorkspace ws = varNode.getWorkspace();
		ws.getMyVarMap().put(varName, varValue);
		return varValue;
	}

}