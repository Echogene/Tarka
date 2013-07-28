package logic.factory;

import javafx.util.Pair;
import reading.lexing.Token;
import reading.parsing.ParseTree;
import reading.parsing.ParseTreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Steven Weston
 */
public class SimpleLogicParseTree implements ParseTree {
	protected List<ParseTreeNode> nodes;

	public SimpleLogicParseTree() {
		this.nodes  = new ArrayList<>();
	}

	public List<ParseTreeNode> getNodes() {
		return nodes;
	}

	@Override
	public String toString() {
		String output = "";
		for (ParseTreeNode n : nodes) {
			int currentDepth = n.getDepth();
			for (int i = 0; i < currentDepth; i++) {
				output += "\t";
			}
			output += n.toString() + "\n";
		}
		return output;
	}

	public ParseTree getSubtreeAt(ParseTreeNode node) {
		ParseTree output = new SimpleLogicParseTree();
		output.getNodes().addAll(node.getAllDescendants());
		return output;
	}

	public String getSubstringAt(ParseTreeNode node) {
		String output = node.getToken().getValue();
		for(ParseTreeNode c : node.getAllDescendants()) {
			output += c.getToken().getValue();
		}
		if (node.getSpouse() != null) {
			output += node.getSpouse().getToken().getValue();
		}
		return output;
	}

	public static class Node implements ParseTreeNode {
		protected Node parent;
		protected List<ParseTreeNode> children;
		protected Token token;
		protected Node spouse;
		protected int depth;

		public Node(Token token) {
			this(null, token);
		}

		public Node(Node parent, Token token) {
			this.parent   = parent;
			this.children = new ArrayList<>();
			this.token    = token;
			this.spouse   = null;

			this.depth = 0;
			if (parent != null) {
				parent.addChild(this);
				this.depth = parent.depth + 1;
			}
		}

		public void addChild(Node child) {
			children.add(child);
		}

		public Node getMother() {
			return parent;
		}

		public List<ParseTreeNode> getChildren() {
			return children;
		}

		public Token getToken() {
			return token;
		}

		public Node getSpouse() {
			return spouse;
		}

		public void marry(Node spouse) {
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
			return new Pair<ParseTreeNode, ParseTreeNode>(getMother(), getMother().getSpouse());
		}

		public int getDepth() {
			return depth;
		}

		@Override
		public String toString() {
			return token.getValue();
		}
	}
}
