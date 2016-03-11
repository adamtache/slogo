package model;

import exception.SLogoException;
import javafx.scene.paint.Color;

/**
 * SLogo's highest State in hierarchy, to support multiple types of character
 * states
 *
 */

public abstract class SLogoCharacterState {

	private double myXCoordinate;
	private double myYCoordinate;
	private double myDirection;
	private boolean isHidden;
	private SLogoPen myPen;
	private String myImage;
	private int myID;
	private int myShapeIndex;
	private int myBGColorIndex;
	private Color myBGColor;
	private boolean isCleared;

	public SLogoCharacterState(SLogoPen myPen, double xCoor, double yCoor, double direction,
					      boolean isHidden, int shapeIndex) throws SLogoException {
		this.myPen = myPen;
		this.myXCoordinate = xCoor;
		this.myYCoordinate = yCoor;
		this.myDirection = direction;
		this.isHidden = isHidden;
		this.myShapeIndex = shapeIndex;
		setImage(myShapeIndex);
		setBackgroundColor(myBGColorIndex);
	}
	
	public SLogoPen getPen() {
		return myPen;
	}

	public void setHidden(boolean hide) {
		this.isHidden = hide;
	}

	public boolean getHidden() {
		return isHidden;
	}

	public void setXCoor(double xCoor) {
		this.myXCoordinate = xCoor;
	}

	public void setYCoor(double yCoor) {
		this.myYCoordinate = yCoor;
	}

	public double getXCoor() {
		return myXCoordinate;
	}

	public double getYCoor() {
		return myYCoordinate;
	}

	public double getDirection() {
		return myDirection;
	}

	public void setDirection(double direction) {
		this.myDirection = direction;
	}

	public String getImage() {
		return myImage;
	}

	public void setBackgroundColor(int myBGColorIndex) throws SLogoException {
		ColorLoader colorLoader = new ColorLoader();
		String colorString = colorLoader.getString(myBGColorIndex+"");
		if(colorString == null)
			throw new SLogoException("Invalid color index");
		else
			this.myBGColor = colorLoader.getColor(myBGColorIndex);
	}
	
	public Color getBGColor(){
		return myBGColor;
	}
	
	public void setImage(int myShapeIndex) throws SLogoException {
		TurtleImageLoader imageLoader = new TurtleImageLoader();
		String indexImage = imageLoader.getString(myShapeIndex+"");
		if(indexImage == null)
			throw new SLogoException("Invalid shape index");
		else
			this.myImage = indexImage;
	}
	
	public int getID(){
		return myID;
	}
	
	public void setID(int myID){
		this.myID = myID;
	}
	
	public int getShapeIndex(){
		return myShapeIndex;
	}
	
	public void setShapeIndex(int myShapeIndex) throws SLogoException{
		this.myShapeIndex = myShapeIndex;
		try {
			setImage(myShapeIndex);
		} catch (SLogoException e) {
			
		}
	}
	
	public boolean isCleared(){
		return isCleared;
	}
	
	public void setCleared(){
		isCleared = true;
	}
	
}