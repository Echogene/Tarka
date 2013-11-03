package logic.factory;

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
	public ParseTreeNode getFirstNode() {
		return nodes.get(0);
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

	@Override
	public ParseTree getSubtreeAt(ParseTreeNode node) {
		ParseTree output = new SimpleLogicParseTree();
		output.getNodes().addAll(node.getAllDescendants());
		return output;
	}

	@Override
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
}
