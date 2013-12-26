package logic.function.factory.validation.checking.checkers;

import javafx.util.Pair;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Steven Weston
 */
public class NonVoidFunctionOrVariableChecker extends DisjunctiveChecker {

	public NonVoidFunctionOrVariableChecker(
			List<Pair<String, String>> acceptedBracketPairs,
			List<Class> acceptedFunctionClasses
	) {
		super(new FunctionfulChecker(acceptedBracketPairs, acceptedFunctionClasses), new VariableChecker());
	}

	public NonVoidFunctionOrVariableChecker(List<Class> acceptedFunctionClasses) {
		//noinspection unchecked
		super(new FunctionNonVoidChecker(Collections.EMPTY_LIST, acceptedFunctionClasses), new VariableChecker());
	}

	public NonVoidFunctionOrVariableChecker(Class... acceptedFunctionClasses) {
		//noinspection unchecked
		super(new FunctionNonVoidChecker(Collections.EMPTY_LIST, Arrays.asList(acceptedFunctionClasses)), new VariableChecker());
	}

	public NonVoidFunctionOrVariableChecker(Class acceptedFunctionClass) {
		//noinspection unchecked
		super(new FunctionNonVoidChecker(Collections.EMPTY_LIST, Arrays.asList(acceptedFunctionClass)), new VariableChecker());
	}

	public NonVoidFunctionOrVariableChecker() {
		super(new FunctionNonVoidChecker(), new VariableChecker());
	}
}
