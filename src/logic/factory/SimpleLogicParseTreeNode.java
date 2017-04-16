package logic.factory;

import reading.lexing.Token;
import reading.parsing.ParseTreeNode;

import java.util.ArrayList;
import java.util.List;

/**
* @author Steven Weston
*/
public class SimpleLogicParseTreeNode implements ParseTreeNode {

	private final SimpleLogicParseTreeNode mother;
	private final List<ParseTreeNode> children;
	private final Token token;
	private SimpleLogicParseTreeNode spouse;
	private final int depth;

	public SimpleLogicParseTreeNode(Token token) {
		this(null, token);
	}

	public SimpleLogicParseTreeNode(SimpleLogicParseTreeNode mother, Token token) {
		this.mother   = mother;
		this.children = new ArrayList<>();
		this.token    = token;
		this.spouse   = null;

		if (mother == null) {
			this.depth = 0;
		} else {
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

	public List<ParseTreeNode> getDescendants() {
		List<ParseTreeNode> output = new ArrayList<>();
		for (ParseTreeNode n : children) {
			output.add(n);
			output.addAll(n.getDescendants());
		}
		return output;
	}

	@Override
	public List<ParseTreeNode> getMaternalAncestors() {
		List<ParseTreeNode> output = new ArrayList<>();
		if (getMother() != null) {
			output.add(getMother());
			output.addAll(getMother().getMaternalAncestors());
		}
		return output;
	}

	public int getDepth() {
		return depth;
	}

	@Override
	public String toString() {
		return token.getValue();
	}
}
