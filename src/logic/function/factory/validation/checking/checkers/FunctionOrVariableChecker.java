package logic.function.factory.validation.checking.checkers;

import javafx.util.Pair;
import logic.function.Function;

import java.util.List;

/**
 * @author Steven Weston
 */
public class FunctionOrVariableChecker extends DisjunctiveChecker {

	public FunctionOrVariableChecker(
			List<Pair<String, String>> acceptedBracketPairs,
			List<Class<? extends Function<?, ?>>> acceptedFunctionClasses
	) {
		super(new FunctionChecker(acceptedBracketPairs, acceptedFunctionClasses), new VariableChecker());
	}

	public FunctionOrVariableChecker() {
		super(new FunctionChecker(), new VariableChecker());
	}
}
