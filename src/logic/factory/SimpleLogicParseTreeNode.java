package logic.factory;

import javafx.util.Pair;
import reading.lexing.Token;
import reading.parsing.ParseTreeNode;

import java.util.ArrayList;
import java.util.List;

/**
* @author Steven Weston
*/
public class SimpleLogicParseTreeNode implements ParseTreeNode {
	protected SimpleLogicParseTreeNode mother;
	protected List<ParseTreeNode> children;
	protected Token token;
	protected SimpleLogicParseTreeNode spouse;
	protected int depth;

	public SimpleLogicParseTreeNode(Token token) {
		this(null, token);
	}

	public SimpleLogicParseTreeNode(SimpleLogicParseTreeNode mother, Token token) {
		this.mother   = mother;
		this.children = new ArrayList<>();
		this.token    = token;
		this.spouse   = null;

		this.depth = 0;
		if (mother != null) {
			mother.addChild(this);
			this.depth = mother.depth + 1;
		}
	}

	public void addChild(SimpleLogicParseTreeNode child) {
		children.add(child);
	}

	@Override
	public SimpleLogicParseTreeNode getMother() {
		return mother;
	}

	@Override
	public ParseTreeNode getFather() {
		return mother.getSpouse();
	}

	public List<ParseTreeNode> getChildren() {
		return children;
	}

	public Token getToken() {
		return token;
	}

	public SimpleLogicParseTreeNode getSpouse() {
		return spouse;
	}

	public void marry(SimpleLogicParseTreeNode spouse) {
		this.spouse   = spouse;
		spouse.spouse = this;
	}

	public List<ParseTreeNode> getAllDescendants() {
		List<ParseTreeNode> output = new ArrayList<>();
		for (ParseTreeNode n : children) {
			output.add(n);
			output.addAll(n.getAllDescendants());
		}
		return output;
	}

	@Override
	public Pair<ParseTreeNode, ParseTreeNode> getParents() {
		return new Pair<ParseTreeNode, ParseTreeNode>(getMother(), getFather());
	}

	public int getDepth() {
		return depth;
	}

	@Override
	public String toString() {
		return token.getValue();
	}
}
