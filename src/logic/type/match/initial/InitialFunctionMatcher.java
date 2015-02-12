package logic.type.match.initial;

import reading.parsing.ParseTreeNode;

/**
 * Tests parse tree nodes against a pattern representing a function.
 * @author Steven Weston
 */
public interface InitialFunctionMatcher {

	/**
	 * @param mother the mother whose children represent the nodes to test the pattern specified by this matcher.
	 * @return the potential function that matches the pattern specified by this matcher.
	 * @throws InitialMatchException if the mother's children cannot possibly match the pattern.
	 */
	InitialMatch findPotentialMatch(ParseTreeNode mother) throws InitialMatchException;
}
