package logic.type.match.arguments;

import logic.type.match.initial.InitialMatch;
import reading.parsing.ParseTreeNode;

import java.util.Collections;
import java.util.List;

/**
 * A match with its arguments defined.  This extends {@link InitialMatch} because it's totally correct to use class
 * hierarchy as a way of signifying an algorithm's order.
 * @author Steven Weston
 */
public class ArgumentedMatch extends InitialMatch {

	/**
	 * The arguments of the match.  These will be opening brackets or variable names.
	 */
	private final List<ParseTreeNode> arguments;

	protected ArgumentedMatch(List<ParseTreeNode> surroundedNodes, List<ParseTreeNode> arguments) {

		super(surroundedNodes);
		this.arguments = Collections.unmodifiableList(arguments);
	}

	public List<ParseTreeNode> getArguments() {
		return arguments;
	}
}
