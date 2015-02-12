package logic.type.match.constant;

import logic.type.match.arguments.ArgumentedMatch;

/**
 * Take the match and assign the possible types to each constant defined in the universe.
 * @author Steven Weston
 */
public interface ConstantTyper {

	/**
	 * @param argumentedMatch the match with its arguments
	 * @return the match that has types for all constants defined in the universe
	 * @throws ConstantTyperException if something bad happens
	 */
	ConstantsTypedMatch typeConstants(ArgumentedMatch argumentedMatch) throws ConstantTyperException;
}
