package logic.function.set.union;

import logic.Nameable;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.factory.validation.checking.checkers.FunctionOrVariableChecker;
import logic.function.factory.validation.checking.checkers.OperatorChecker;
import logic.function.set.SetFunction;

import java.util.Arrays;
import java.util.List;

/**
 * @author Steven Weston
 */
public class BinaryUnionFactory<T extends Nameable> extends AbstractUnionFactory<T> {

	public BinaryUnionFactory() {
		super(getCheckers());
	}

	private static List<CheckerWithNumber> getCheckers() {
		return Arrays.asList(
				new FunctionOrVariableChecker(SetFunction.class),
				new OperatorChecker(Union.BINARY_SYMBOL),
				new FunctionOrVariableChecker(SetFunction.class)
		);
	}
}
