package logic.function.reflexive;

import javafx.util.Pair;
import logic.Nameable;
import logic.function.factory.FunctionFactory;
import logic.function.factory.validation.checking.CheckerWithNumber;

import java.util.List;

/**
 * @author Steven Weston
 */
public abstract class ReflexiveFunctionFactory<T extends Nameable, F extends ReflexiveFunction<T>>
		extends FunctionFactory<T, T, F> {

	protected ReflexiveFunctionFactory(List<CheckerWithNumber> checkers, List<Pair<String, String>> acceptedBracketPairs, Class<T> universeType) {
		super(checkers, acceptedBracketPairs, universeType);
	}
}
