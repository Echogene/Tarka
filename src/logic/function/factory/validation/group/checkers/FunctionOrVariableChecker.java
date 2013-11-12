package logic.function.factory.validation.group.checkers;

import javafx.util.Pair;

import java.util.List;

/**
 * @author Steven Weston
 */
public class FunctionOrVariableChecker extends DisjunctiveChecker {

	public FunctionOrVariableChecker(List<Pair<String, String>> acceptedBracketPairs) {
		super(new FunctionChecker(acceptedBracketPairs), new VariableChecker());
	}

	public FunctionOrVariableChecker() {
		super(new FunctionChecker(), new VariableChecker());
	}
}
