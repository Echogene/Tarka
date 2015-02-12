package logic.type.match.initial;

import reading.parsing.ParseTreeNode;

import java.util.Collections;
import java.util.List;

/**
 * This represents some nodes that have matched a pattern describing a function.
 * @author Steven Weston
 */
public class InitialMatch {

	/**
	 * The original, surrounded nodes that make up this function.
	 */
	protected final List<ParseTreeNode> originalNodes;

	protected InitialMatch(List<ParseTreeNode> surroundedNodes) {

		originalNodes = Collections.unmodifiableList(surroundedNodes);
	}

	public List<ParseTreeNode> getOriginalNodes() {

		return originalNodes;
	}
}
