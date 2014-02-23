package util;

import reading.lexing.Token;
import reading.parsing.ParseTreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

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

	public static <T> String prettyPrint(
			List<T> list,
			Function<T, String> presentationFunction,
			Function<T, List<T>> childrenFunction,
			int indentation
	) {
		StringBuilder output = new StringBuilder();
		for (T parent : list) {
			output.append(presentationFunction.apply(parent));
			output.append("\n");
			List<T> children = childrenFunction.apply(parent);
			if (children == null || children.isEmpty()) {
				continue;
			}
			String childString = prettyPrint(children, presentationFunction, childrenFunction, indentation);
			String[] split = childString.split("\n");
			for (int i = 0; i < split.length; i++) {
				String s = split[i];
				String trimmed = s.trim();
				Boolean isAnotherChild = false;
				for (int j = i + 1; j < split.length; j++) {
					String t = split[j].trim();
					if (!isPartOfTree(t)) {
						isAnotherChild = true;
					}
				}
				if (isPartOfTree(trimmed)) {
					if (isAnotherChild) {
						output.append("│");
					} else {
						output.append(" ");
					}
					repeatedlyAppend(indentation, output, " ");
				} else {
					if (isAnotherChild) {
						output.append("├");
					} else {
						output.append("└");
					}
					repeatedlyAppend(indentation, output, "─");
				}
				output.append(s);
				output.append("\n");
			}
		}
		return output.toString();
	}

	// Not sure what to call this...
	private static boolean isPartOfTree(String trimmed) {
		return trimmed.startsWith("└") || trimmed.startsWith("├") || trimmed.startsWith("│");
	}

	private static void repeatedlyAppend(int times, StringBuilder sb, String toAppend) {
		for (int i = 0; i < times; i++) {
			sb.append(toAppend);
		}
	}
}
