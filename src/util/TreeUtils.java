package util;

import reading.lexing.Token;
import reading.parsing.ParseTreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Steven Weston
 */
public class TreeUtils {

	public static List<Token> extractTokens(List<ParseTreeNode> nodes) {
		List<Token> output = new ArrayList<>(nodes.size());
		if (!nodes.isEmpty()) {
			output.add(nodes.get(0).getMother().getToken());
			for (ParseTreeNode n : nodes) {
				output.add(n.getToken());
			}
			output.add(nodes.get(0).getFather().getToken());
		}
		return output;
	}

	public static List<ParseTreeNode> surroundWithParentNodes(List<ParseTreeNode> nodes) {
		List<ParseTreeNode> output = new ArrayList<>(nodes.size());
		if (!nodes.isEmpty()) {
			output.add(nodes.get(0).getMother());
			output.addAll(nodes);
			output.add(nodes.get(0).getFather());
		}
		return output;
	}
}
