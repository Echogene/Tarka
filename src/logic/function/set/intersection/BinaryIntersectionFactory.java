package logic.function.set.intersection;

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
public class BinaryIntersectionFactory<T extends Nameable> extends AbstractIntersectionFactory<T> {

	public BinaryIntersectionFactory(Class<T> universeType) {
		super(getCheckers(), universeType);
	}

	private static List<CheckerWithNumber> getCheckers() {
		return Arrays.asList(
				new FunctionOrVariableChecker(SetFunction.class),
				new OperatorChecker(Intersection.BINARY_SYMBOL),
				new FunctionOrVariableChecker(SetFunction.class)
		);
	}
}
