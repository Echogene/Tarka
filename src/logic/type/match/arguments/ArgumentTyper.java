package logic.type.match.arguments;

import logic.type.match.subfunction.SubFunctionsTypedMatch;

/**
 * @author Steven Weston
 */
public interface ArgumentTyper {

	/**
	 * Restrict the argument types of the given match to the ones possible as defined by this class.
	 * @param match the match of which to restrict the argument types
	 * @return an extension of the match that has restricted the arguments' types
	 * @throws ArgumentTyperException if an argument has no possible types
	 */
	ArgumentsTypedMatch restrictArgumentTypes(SubFunctionsTypedMatch match) throws ArgumentTyperException;
}
