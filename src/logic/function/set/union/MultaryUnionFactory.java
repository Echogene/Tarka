package logic.function.set.union;

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
public class MultaryUnionFactory<T extends Nameable> extends AbstractUnionFactory<T> {

	public MultaryUnionFactory(Class<T> universeType) {
		super(getCheckers(), universeType);
	}

	private static List<CheckerWithNumber> getCheckers() {
		return Arrays.asList(
				new OperatorChecker(Union.MULTARY_SYMBOL),
				new NumberedChecker(MANY, new FunctionOrVariableChecker(SetFunction.class))
		);
	}
}
