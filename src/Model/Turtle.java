package Model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Turtle implements Character {

	TurtleState myState;
	String myName;
	
	public Turtle(String myName, double xCoor, double yCoor, boolean penDown, double direction, boolean isHidden) {
		myState = new TurtleState(xCoor, yCoor, direction, isHidden, penDown);
		this.myName = myName;
	}

	public void setState(CharacterState myState) {
		this.myState = (TurtleState) myState;
	}

	public CharacterState getState() {
		return myState;
	}

	public String getName() {
		return myName;
	}

}