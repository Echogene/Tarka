package logic.factory;

import reading.parsing.ParseTree;
import reading.parsing.ParseTreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Steven Weston
 */
public class SimpleLogicParseTree implements ParseTree {

	private final List<ParseTreeNode> nodes;

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

	@Override
	public ParseTree getSubtreeAt(ParseTreeNode node) {
		ParseTree output = new SimpleLogicParseTree();
		output.getNodes().addAll(node.getDescendants());
		return output;
	}

	@Override
	public String getSubstringAt(ParseTreeNode node) {
		String output = node.getValue();
		for(ParseTreeNode c : node.getDescendants()) {
			output += c.getValue();
		}
		if (node.getSpouse() != null) {
			output += node.getSpouse().getValue();
		}
		return output;
	}
}
