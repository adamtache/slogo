package parser;

import java.util.ArrayList;
import java.util.List;
import commandnode.BracketNode;
import commandnode.CommandNode;
import commandnode.CustomCommandNode;
import commandnode.CustomFunctionNode;
import commandnode.ListNode;
import commandnode.MakeUserInstructionNode;
import commandnode.Node;
import commandnode.NumericNode;
import commandnode.ParenthesisNode;
import exception.SLogoException;
import model.LanguageLoader;
import model.ResourceLoader;
import model.SLogoWorkspace;
import commandnode.VariableNode;
import commandnode.WorkspaceNode;

/**
 * SLogo's TreeFactory creates a root Node with children representing a complete tree for evaluation of a command
 * @author Adam Tache
 */
public class TreeFactory {

	private ResourceLoader myResourceLoader;
	private LanguageLoader myLanguageLoader;
	private SLogoWorkspace myWorkspace;
	private String INVALID_TOKENS_ERROR;
	private String COMMAND_PATH;
	private String CLASS_EXTENSION;
	private String COMMAND;
	private String NOT_IMPLEMENTED_ERROR;

	public TreeFactory (SLogoWorkspace myWorkspace) throws SLogoException {
		this.myWorkspace = myWorkspace;
		myResourceLoader = new ResourceLoader();
		myLanguageLoader = new LanguageLoader();
		myLanguageLoader.load(myWorkspace.getView().getLanguage());
		initResources();
	}

	/**
	 * Initializes resources used throughout class
	 */
	private void initResources(){
		INVALID_TOKENS_ERROR = myResourceLoader.getString("InvalidCommandTokens");
		COMMAND_PATH = myResourceLoader.getString("CommandNode");
		CLASS_EXTENSION = getResourceLoader().getString("Node");
		COMMAND = getResourceLoader().getString("COMMAND");
		NOT_IMPLEMENTED_ERROR = getResourceLoader().getString("IMPLEMENTED");
	}

	/**
	 * @return Root created from tokens
	 * Uses createRoot helper method to create correct type of root
	 * @param List<String> tokens - command tokens formatted by SLogoParser
	 */
	public Node createRoot(List<String> tokens) throws SLogoException {
		Node myRoot = createNode(tokens.remove(0));
		createChildren(myRoot, tokens);
		return myRoot;
	}

	/** @param Node node
	 * 	Initializes Workspace properties in WorkspaceNode when node is a WorkspaceNode
	 */
	private void initialize(Node node) throws SLogoException{
		if(node instanceof WorkspaceNode){
			((WorkspaceNode)node).initialize(myWorkspace);
		}
	}

	/**
	 * Creates Node from specific token using Reflection, calling the corresponding class name + "Node"
	 * @param String strNode - create the necessary node from the string passed
	 */
	private Node createNode(String rootToken) throws SLogoException {
		Node node;
		String rootName = myLanguageLoader.getTranslation(rootToken.toLowerCase());
		if (isNumeric(rootToken)) {
			node = new NumericNode(Double.parseDouble(rootToken));
		} else if (isVariable(rootName)) {
			node = new NumericNode(0);
		} else if (isCustom(rootToken)) {
			node = new CustomFunctionNode((myWorkspace.getCustomCommand(rootName)));
		} else {
			try {
				node = (Node) Class.forName(COMMAND_PATH + rootName + CLASS_EXTENSION).newInstance();
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				throw new SLogoException(COMMAND + rootName + NOT_IMPLEMENTED_ERROR);
			}
		}
		initialize(node);
		return node;
	}

	/**
	 * @param Node myRoot
	 * Create children for myRoot until its current children matches its required children 
	 * @param List<String> tokens - a list of formated command parts
	 */
	private void createChildren(Node myRoot, List<String> tokens) throws SLogoException{
		while (myRoot.numCurrentChildren() != myRoot.numRequiredChildren()) {
			Node nextChild = createNextChild(myRoot, tokens);
			if(nextChild == null){
				throw new SLogoException(INVALID_TOKENS_ERROR);
			}
			myRoot.addChild(nextChild);
		}
	}
	
	/**
	 * Create next child in list of tokens
	 * @param List<String> tokens - a list of formated command parts
	 */
	private Node createNextChild(Node myRoot, List<String> tokens) throws SLogoException {
		if (tokens.isEmpty()) {
			return null;
		}
		Node child = null;
		String nextToken = tokens.remove(0);
		if (myRoot instanceof MakeUserInstructionNode && ((CommandNode)myRoot).numCurrentChildren() == 0) {
			child = new CustomCommandNode(nextToken);
		}
		else if (isOpenBracket(nextToken) || isOpenParenthesis(nextToken)) {
			child = createListNode(nextToken, tokens);
		}
		else if (isVariable(nextToken)) {
			child = new VariableNode(nextToken);
		} 
		else {
			child = createNode(nextToken);
			createChildren(child, tokens);
		}
		initialize(child);
		return child;
	}
	
	/**
	 * @param String token - token deemed to match open list element by createNextChild method, so a ( or [
	 * @param List<String> tokens - a list of formated command parts
	 */
	private ListNode createListNode(String token, List<String> tokens) throws SLogoException{
		ListNode child = null;
		if (isOpenBracket(token)){
			child = new BracketNode();
		}
		else if(isOpenParenthesis(token)){
			child = new ParenthesisNode();
		}
		((ListNode) child).setInnerCommands(createInnerListTokens(tokens));
		return child;
	}

	/**
	 * Creates list of inner tokens for ListNode representing tokens between outermost enclosures
	 * i.e. [ returns tokens inside ]
	 * Inner tokens can be list tokens themselves 
	 * 
	 * @param List<String> tokens
	 */
	private List<String> createInnerListTokens(List<String> commandParts) throws SLogoException {
		List<String> innerCommands = new ArrayList<>();
		int openBrackets = 1;
		int closedBrackets = 0;
		String currCommand;
		while (openBrackets != closedBrackets) {
			currCommand = commandParts.remove(0);
			if (isOpenBracket(currCommand) || isOpenParenthesis(currCommand)) {
				openBrackets++;
			}
			else if (isClosedBracket(currCommand) || isClosedParenthesis(currCommand)) {
				closedBrackets++;
			}
			if(openBrackets != closedBrackets) {
				innerCommands.add(currCommand);
			}
		}
		return innerCommands;
	}

	/**
	 * Determine if the string inputed has a string regrex equal to numeric notation
	 * 
	 * @param String str - string to be compared
	 */
	private boolean isNumeric(String str) {
		return str.matches("[-+]?\\d*\\.?\\d+");
	}

	/**
	 * Determine if the string inputed is an open parenthesis
	 * 
	 * @param String str - string to be compared
	 */
	private boolean isOpenBracket(String str) {
		return str.equals("[");
	}

	/**
	 * Determine if the string inputed is an open parenthesis
	 * 
	 * @param String str - string to be compared
	 */
	private boolean isOpenParenthesis(String str) {
		return str.equals("(");
	}

	/**
	 * Determine if the string inputed is regrex equal to variable notation
	 * 
	 * @param String str - string to be compared
	 */
	private boolean isVariable(String str) {
		return str.matches(":[a-zA-Z_]+");
	}

	/**
	 * Determine if the string inputed is a closed bracket
	 * 
	 * @param String str - string to be compared
	 */
	private boolean isClosedBracket(String str) {
		return str.equals("]");
	}

	/**
	 * Determine if the string inputed is a closed parenthesis
	 * 
	 * @param String str - string to be compared
	 */
	private boolean isClosedParenthesis(String currCommand) {
		return currCommand.equals(")");
	}

	/**
	 * Determine if the string inputed is a custom command in current workspace
	 * 
	 * @param String str - string to be compared
	 */
	private boolean isCustom(String str){
		return myWorkspace.getCustomCommand(str) != null;
	}

	/**
	 * @return the myResourceLoader
	 */
	private ResourceLoader getResourceLoader () {
		return myResourceLoader;
	}

}