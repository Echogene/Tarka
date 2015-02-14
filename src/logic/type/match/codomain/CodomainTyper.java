package logic.type.match.codomain;

import logic.type.match.constant.ConstantsTypedMatch;

/**
 * Take the match for a function and assign some types to its codomain.
 * @author Steven Weston
 */
public interface CodomainTyper {

	CodomainTypedMatch findCodomainType(ConstantsTypedMatch match) throws CodomainTyperException;
}
