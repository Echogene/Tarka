package logic.type.match.arguments;

import logic.type.match.initial.InitialMatch;

/**
 * Tests inital matches and gives them arguments.
 * @author Steven Weston
 */
public interface ArgumentMatcher {

	/**
	 * @param match the initial match to give arguments
	 * @return the initial match to augment with arguments
	 * @throws ArgumentMatchException if the match somehow failed to match some argument restrictions.
	 */
	ArgumentedMatch findArguments(InitialMatch match) throws ArgumentMatchException;
}
