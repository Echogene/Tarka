package util;

import ophelia.function.ExceptionalBiConsumer;
import reading.lexing.Token;
import reading.parsing.ParseTreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Steven Weston
 */
public class TreeUtils {

	/**
	 * @param nodes A list of tokens from which to extract tokens
	 * @return The list of tokens contained in the nodes including the outer brackets.
	 */
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

	public static <E extends Exception> void recurse(
			ParseTreeNode parent,
			Predicate<ParseTreeNode> shouldWalkDown,
			ExceptionalBiConsumer<ParseTreeNode, List<ParseTreeNode>, E> parentAndChildConsumer
	) throws E {

		List<ParseTreeNode> children = parent.getChildren();
		for (ParseTreeNode child : children) {
			if (shouldWalkDown.test(child)) {
				recurse(child, shouldWalkDown, parentAndChildConsumer);
			}
		}
		parentAndChildConsumer.accept(parent, children);
	}
}
