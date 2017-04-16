package logic.function.set.intersection;

import logic.Nameable;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.factory.validation.checking.checkers.FunctionOrVariableChecker;
import logic.function.factory.validation.checking.checkers.NumberedChecker;
import logic.function.factory.validation.checking.checkers.OperatorChecker;
import logic.function.set.SetFunction;

import java.util.Arrays;
import java.util.List;

import static logic.function.factory.validation.checking.CheckerWithNumber.Number.MANY;

/**
 * @author Steven Weston
 */
public class MultaryIntersectionFactory<T extends Nameable> extends AbstractIntersectionFactory<T> {

	public MultaryIntersectionFactory(Class<T> universeType) {
		super(getCheckers(), universeType);
	}

	private static List<CheckerWithNumber> getCheckers() {
		return Arrays.asList(
				new OperatorChecker(Intersection.MULTARY_SYMBOL),
				new NumberedChecker(MANY, new FunctionOrVariableChecker(SetFunction.class))
		);
	}
}
