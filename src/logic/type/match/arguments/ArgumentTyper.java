package logic.type.match.arguments;

import logic.type.match.subfunction.SubFunctionsTypedMatch;

/**
 * @author Steven Weston
 */
public interface ArgumentTyper {

	ArgumentsTypedMatch restrictArgumentTypes(SubFunctionsTypedMatch match) throws ArgumentTyperException;
}
