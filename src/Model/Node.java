package Model;


public class Node extends CommandNode {

	String commandType;
	String commandName;
	int noOfChildren;
	int[] args;
	Node next;

	public Node(String type, String name, int args) {
		this.commandType = type;
		this.commandName = name;
		this.noOfChildren = args;
		this.args = new int[noOfChildren];
	}

	@Override
	public void evaluate(String myCommand) {
		// TODO Auto-generated method stub

	}

	@Override
	public void intermediateCanvasUpdate() {
		// TODO Auto-generated method stub

	}


	@Override
	public Node grabNode() {
		// TODO Auto-generated method stub
		return null;
	}

}
