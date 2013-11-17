package logic.function.factory.validation.checking.checkers;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Steven Weston
 */
public class FunctionOrVariableChecker extends DisjunctiveChecker {

	public FunctionOrVariableChecker(
			List<Pair<String, String>> acceptedBracketPairs,
			List<Class> acceptedFunctionClasses
	) {
		super(new FunctionfulChecker(acceptedBracketPairs, acceptedFunctionClasses), new VariableChecker());
	}

	public FunctionOrVariableChecker(List<Class> acceptedFunctionClasses) {
		super(new FunctionfulChecker(new ArrayList<>(), acceptedFunctionClasses), new VariableChecker());
	}

	public FunctionOrVariableChecker(Class acceptedFunctionClass) {
		super(new FunctionfulChecker(new ArrayList<>(), Arrays.asList(acceptedFunctionClass)), new VariableChecker());
	}

	public FunctionOrVariableChecker() {
		super(new FunctionfulChecker(), new VariableChecker());
	}
}
