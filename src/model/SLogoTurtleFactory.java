package model;

import exception.SLogoException;

/**
 * SLogo's Turtle Factory class to create turtles
 * 
 */
public class SLogoTurtleFactory {

	private static final double DEFAULT_DIRECTION = 0; 
	private static final boolean DEFAULT_HIDDEN = false;
	private static final int DEFAULT_X = 0;
	private static final int DEFAULT_Y  = 0;
	private static final int DEFAULT_ID = 0;
	private static final int DEFAULT_SHAPE_INDEX = 1;
	private static final int STAMP_ID = -1;
	private SLogoWorkspace myWorkspace;
	private int myLastID = 0;

	public SLogoTurtleFactory(SLogoWorkspace myWorkspace) {
		this.myWorkspace = myWorkspace;
	}

	/**
	 * Initializes a turtle with given ID. Makes it available to frontend. If turtle already initialized and is currently contained in the workspace
	 * then that turtle instance is grabbed and added to the active turtles list
	 * @return myTurtle: created turtle
	 * 
	 * @param myX
	 * @param myY
	 * @param requestedID
	 */
	public SLogoCharacter createTurtle(int myX, int myY, double myDirection, 
			int myShape, int requestedID) throws SLogoException {
		if (hasTurtleBeenCreated(requestedID) && requestedID > -1) {
			return myWorkspace.getCharacterList().get(requestedID);
		}
		SLogoPen myPen = new SLogoPen();
		SLogoTurtle myTurtle;
		if (requestedID == STAMP_ID) {
			myTurtle = new SLogoTurtle(STAMP_ID, myPen, myX, myY, myDirection, 
					DEFAULT_HIDDEN, myShape);
		} else {
			myTurtle = new SLogoTurtle(myLastID++, myPen, myX, myY, DEFAULT_DIRECTION, 
					DEFAULT_HIDDEN, DEFAULT_SHAPE_INDEX);
		}
		myWorkspace.getCharacterList().add(myTurtle);
		SLogoDisplayData turtleData = new SLogoDisplayData(myTurtle.getState());
		turtleData.addObserver(myWorkspace.getView().getObserver());
		myWorkspace.getObservableDataList().add(turtleData);
		return myTurtle;
	}

	/**
	 * Checks whether ID is available
	 */
	private boolean hasTurtleBeenCreated(int requestedID) {
		return (requestedID < myLastID);
	}

	/**
	 * Creates a turtle with default parameters
	 */
	public SLogoCharacter createDefaultTurtle() throws SLogoException {
		return createTurtle(DEFAULT_X, DEFAULT_Y, DEFAULT_DIRECTION, 
				DEFAULT_SHAPE_INDEX, myLastID);
	}

	public SLogoCharacter createDefaultTurtleWithID(int ID) throws SLogoException {
		return createTurtle(DEFAULT_X, DEFAULT_Y, DEFAULT_DIRECTION, 
				DEFAULT_SHAPE_INDEX, ID);
	}

	public int getDefaultX() {
		return DEFAULT_X;
	}

	public int getDefaultY() {
		return DEFAULT_Y;
	}

	public int getDefaultID() {
		return DEFAULT_ID;
	}
}