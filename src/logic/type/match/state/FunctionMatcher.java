package logic.type.match.state;

import logic.type.match.arguments.ArgumentMatcher;
import logic.type.match.codomain.CodomainTyper;
import logic.type.match.constant.ConstantTyper;
import logic.type.match.initial.InitialFunctionMatcher;

/**
 * @author Steven Weston
 */
public interface FunctionMatcher
	extends
		InitialFunctionMatcher,
		ArgumentMatcher,
		ConstantTyper,
		CodomainTyper
{

}
