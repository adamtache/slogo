package Model;

import Controller.SLogoException;

public class EqualNode extends CommandNode{

	private int NUM_CHILDREN = 2;
	
	public EqualNode(){
		setNumChildren(NUM_CHILDREN);
	}
	
	public double evaluate(CharacterState state) throws SLogoException {
		double expr1 = getChildren().get(0).evaluate(state);
		for(int x=1; x<getChildren().size(); x++){
			if(expr1 != getChildren().get(x).evaluate(state))
				return 0;
		}
		return 1;
	}
}